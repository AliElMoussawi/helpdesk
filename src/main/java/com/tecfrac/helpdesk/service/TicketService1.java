package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanPair;
import com.tecfrac.helpdesk.repository.TicketPriorityRepository;
import com.tecfrac.helpdesk.exception.HelpDeskException;
import com.tecfrac.helpdesk.model.ModelGroup;
import com.tecfrac.helpdesk.model.ModelTag;
import com.tecfrac.helpdesk.model.ModelTicket;
import com.tecfrac.helpdesk.model.ModelTicketMessage;
import com.tecfrac.helpdesk.model.ModelTicketPriority;
import com.tecfrac.helpdesk.model.ModelTicketStatus;
import com.tecfrac.helpdesk.model.ModelTicketType;
import com.tecfrac.helpdesk.model.ModelUser;
import com.tecfrac.helpdesk.model.ModelUserType;
import com.tecfrac.helpdesk.repository.GroupRepository;
import com.tecfrac.helpdesk.repository.MessageRepository;
import com.tecfrac.helpdesk.repository.TagRepository;
import com.tecfrac.helpdesk.repository.TicketMessageRepository;
import com.tecfrac.helpdesk.repository.TicketRepository;
import com.tecfrac.helpdesk.repository.TicketStatusRepository;
import com.tecfrac.helpdesk.repository.TicketTypeRepository;
import com.tecfrac.helpdesk.repository.UserGroupRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.request.RequestAddTicket;
import com.tecfrac.helpdesk.request.RequestGetTicket;
import com.tecfrac.helpdesk.request.RequestMessageTicket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TicketService1 {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TicketTypeRepository ticketTypeRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private TicketMessageRepository ticketMessageRepository;
    int[] listOfStatus = {ModelTicketStatus.CLOSED, ModelTicketStatus.NEW, ModelTicketStatus.OPEN, ModelTicketStatus.PENDING, ModelTicketStatus.SOLVED, ModelTicketStatus.SUSPENDED};
    List<Integer> status = Arrays.stream(listOfStatus) // IntStream
            .boxed() // Stream<Integer>
            .collect(Collectors.toList());

    private List<ModelGroup> getUserGroupsId(ModelUser user) {
        List<ModelGroup> group = new ArrayList<>();
        if (user.getUserType().getId() == ModelUserType.Administrator) {
            return groupRepository.findAllByCompanyId(user.getCompany().getId());
        } else if (user.getUserType().getId() != ModelUserType.User) {
            group.add(userGroupRepository.findByUserId(user.getId()).getGroup());

        }
        return group;
    }

    public ModelTicket mergeTicketsInto(Long id, List<Long> ticketsIds, ModelUser user) {
        ModelTicket ticket = ticketRepository.findById(id).get();
        ModelUser assignee = ticket.getAssignedUser();
        String deletedIds = "#";
        for (Long ticketId : ticketsIds) {
            String deleted = deletedTicketsForever(ticketId).getId().toString();
            deletedIds += deleted + ",";
        }
        deletedIds = deletedIds.substring(0, deletedIds.length() - 1);
        ModelTicketMessage message = new ModelTicketMessage();
        message.setDateCreation(new Date());
        message.setMessage("Requests " + deletedIds + " were closed and merged into this request");
        message.setUser(user);
        message.setTicket(ticket);
        message.setUseType(user.getUserType());
        message.setInternal(Boolean.FALSE);
        messageRepository.save(message);

        return ticket;
    }

    public ModelTicket deletedTicketsForever(Long id) {
        ModelTicket ticket = ticketRepository.findById(id).get();
        ticketRepository.deleteById(id);
        return ticket;
    }

    public List<ModelTicketMessage> getTicketMessges(Long id, ModelUser user) {
        if (user.getUserType().getId() != 1) {
            return ticketMessageRepository.findAllByticketIdOrderByDateCreationDesc(id);
            //(id, getUserGroupId());
        }
        return ticketMessageRepository.findAllByticketIdAndInternalNotOrderByDateCreationDesc(id, true);
    }

    public BeanPair<Long, String> getLastMessages(Long id, ModelUser user) {
        user = userRepository.findById(user.getId()).get();
        BeanPair<Long, String> message = new BeanPair<>(user.getId(), "");
        String lastMessage = "";
        if (user.getUserType().getId() == ModelUserType.User) {
            System.out.println("user type " + ModelUserType.User);
            lastMessage = ticketMessageRepository.findTopByticketIdAndInternalNotOrderByIdDesc(id, true).getMessage();
        } else {
            lastMessage = ticketMessageRepository.findTopByticketIdOrderByIdDesc(id).getMessage();
        }
        message.setSecond(lastMessage);
        return message;
    }

    private static Date recentlyUpdated() {
        Date date = new Date();
        long MILLIS_IN_A_15MIN = 1000 * 60 * 15;

        return new Date(date.getTime() - MILLIS_IN_A_15MIN);
    }

    public Set<ModelTag> tag(List<String> tags, ModelTicket ticket) {
        Set<ModelTag> modelTags = new HashSet<>();
        for (String Tag : tags) {
            if (tagRepository.findByTag(Tag) == null) {
                ModelTag tag = new ModelTag();
                tag.setTag(Tag);
                tag.setTicketId(ticket.getId());
                tagRepository.save(tag);
                modelTags.add(tag);
            }
        }
        return modelTags;
    }

    public ModelTicket updateTicket(RequestAddTicket ticketDetails, ModelUser user) throws HelpDeskException {
        ModelTicket ticket = ticketRepository.findById(ticketDetails.getId()).get();

        if (ticketDetails.getStatusId().equals(ModelTicketStatus.SUSPENDED)) {
            return spamTicket(ticketDetails.getId(), user).getFirst();
        } else if (ticket.getStatus().getId() == ModelTicketStatus.CLOSED && (!ticketDetails.getStatusId().equals(ModelTicketStatus.CLOSED))) {
            undeleteTicket(ticket);
        }
        return addOrEditTicket(ticket, ticketDetails, user);
    }

    public ModelTicket addTicket(@RequestParam(required = true) RequestAddTicket request, ModelUser user) throws HelpDeskException {
        return addOrEditTicket(null, request, user);
    }

    public ModelTicket addOrEditTicket(ModelTicket ticket, @RequestParam(required = true) RequestAddTicket request, ModelUser user) throws HelpDeskException {
        Set<ModelTag> tags = new HashSet<>();
        ModelTicketStatus status = null;
        if (ticket != null) {

            if (request.getTypeId() != null) {
                Optional<ModelTicketType> ticketType = ticketTypeRepository.findById(request.getTypeId());
                ticket.setTicketType(ticketType.get());
            }
            if (request.getPriorityId() != null) {
                Optional<ModelTicketPriority> ticketPriority = ticketPriorityRepository.findById(request.getPriorityId());
                ticket.setPriority(ticketPriority.get());
            }
            if (request.getStatusId() != null) {
                status = ticketStatusRepository.findById(request.getStatusId()).orElse(ticketStatusRepository.findById(ModelTicketStatus.OPEN));
            }
        }
        if (ticket == null) {
            ticket = new ModelTicket();
            ticket.setRequested(new Date());
            status = ticketStatusRepository.findById(ModelTicketStatus.NEW);
        }
        if (ticket.getSubject() == null || request.getSubject() != null) {
            ticket.setSubject(request.getSubject());
        }

        ModelUser requester;
        if (request.getRequesterId() != null) {
            requester = userRepository.findById(request.getRequesterId()).get();
        } else {
            requester = user;
        }
        ticket.setRequester(requester);
        if (status != null) {

            ticket.setStatus(status);
        } else {
            ticket.setStatus(ticketStatusRepository.findById(ModelTicketStatus.OPEN));
        }
        ModelGroup group = ticket.getAssignedGroup();
        if (request.getAssignedGroupId() != null) {
            group = groupRepository.findById(request.getAssignedGroupId()).get();
        }
        ticket.setAssignedGroup(group);
        ModelUser assignee = user;
        if (request.getAssignedUserId() != null) {
            assignee = userRepository.findById(request.getAssignedUserId()).get();
        }
        ticket.setAssignedUser(assignee);
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            tags = tag(request.getTags(), ticket);
        }
        if (request.getTypeId() != null) {
            ticket.setTicketType(ticketTypeRepository.findById(request.getTypeId()).get());
        } else {
            ticket.setTicketType(ticketTypeRepository.findById((ModelTicketType.Problem)));

        }
        if (request.getPriorityId() != null) {
            ticket.setPriority(ticketPriorityRepository.findById(request.getPriorityId()).get());
        } else {
            ticket.setPriority(ticketPriorityRepository.findById(ModelTicketPriority.Normal));
        }
        ModelTicketMessage message = null;
        if (request.getMessage() != null) {
            message = new ModelTicketMessage();
            message.setDateCreation(new Date());
            message.setMessage(request.getMessage());
            message.setUser(user);
            message.setTicket(ticket);
            message.setUseType(user.getUserType());
            if (user.getUserType().getId() != ModelUserType.User) {// 0 the index of the normal user 

                message.setInternal(request.getInternal());
            } else {
                message.setInternal(Boolean.FALSE);
            }
            RequestMessageTicket requestMessageTicket = new RequestMessageTicket();
            requestMessageTicket.setMessage(request.getMessage());
            requestMessageTicket.setRequesterId(ticket.getRequester().getId());
            requestMessageTicket.setSubject(ticket.getSubject());
            messageRepository.save(message);
        }
        ticketRepository.save(ticket);
        return ticket;
    }

    public List<ModelTicket> unAssignedTickets(ModelUser user) {
        return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUser(Arrays.asList(ModelTicketStatus.SOLVED, ModelTicketStatus.CLOSED, ModelTicketStatus.SUSPENDED), getUserGroupsId(user), null);
    }

    public List<ModelTicket> recTickets(List<Integer> list) {

        return ticketRepository.findAllByStatusIdNotInAndUpdatedGreaterThanEqual(list, recentlyUpdated());
    }

    public int countRecUpdated() {
        int[] listOfStatus = {ModelTicketStatus.CLOSED, ModelTicketStatus.SOLVED, ModelTicketStatus.SUSPENDED};
        List<Integer> status = Arrays.stream(listOfStatus).boxed().collect(Collectors.toList());
        return recTickets(status).size();
    }

//    public List<ModelTicket> allTicketsByStatusId(RequestGetTicket getTicket) {
//        ModelUser user = beanSession.getUser();
//        List<Long> notStatus = status.stream()
//                .filter(element -> !getTicket.getStatusIds().contains(element))
//                .collect(Collectors.toList());
//        if (user.getUserType().getId() != ModelUserType.User) {
//            if (getTicket.isOnlyMine()) {
//                if (getTicket.isRecently()) {
//                    return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUserAndUpdatedGreaterThanEqual(notStatus, getUserGroupsId(), user, recentlyUpdated());
//                }
//
//                return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUser(notStatus, getUserGroupsId(), user);
//            }
//            if (getTicket.isRecently()) {
//                return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndUpdatedGreaterThanEqual(notStatus, getUserGroupsId(), recentlyUpdated());
//            }
//
//            return ticketRepository.findAllByStatusIdNotInAndAssignedGroupIn(notStatus, getUserGroupsId());
//        }
//
//        return ticketRepository.findAllByStatusIdNotInAndRequester(getTicket.getStatusIds(), user);
//
//    }
    public List<ModelTicket> allTicketsByStatusId1(Long id, ModelUser user) {
        if (id == 1) {
            int[] listOfStatus = {ModelTicketStatus.SOLVED, ModelTicketStatus.CLOSED, ModelTicketStatus.SUSPENDED
            };
            return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUser(Arrays.stream(listOfStatus).boxed().collect(Collectors.toList()), getUserGroupsId(user), user);
        } else if (id == 2) {
            int[] listOfStatus = {ModelTicketStatus.SOLVED, ModelTicketStatus.CLOSED, ModelTicketStatus.SUSPENDED
            };
            return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUser(Arrays.stream(listOfStatus).boxed().collect(Collectors.toList()), getUserGroupsId(user), null);
        } else if (id == 3) {
            int[] listOfStatus = {ModelTicketStatus.SOLVED, ModelTicketStatus.CLOSED, ModelTicketStatus.SUSPENDED};
            return ticketRepository.findAllByStatusIdNotInAndAssignedGroupIn(Arrays.stream(listOfStatus).boxed().collect(Collectors.toList()), getUserGroupsId(user));
        } else if (id == 4) {
            int[] listOfStatus = {ModelTicketStatus.CLOSED, ModelTicketStatus.SOLVED, ModelTicketStatus.SUSPENDED};
            status = Arrays.stream(listOfStatus) // IntStream
                    .boxed() // Stream<Integer>
                    .collect(Collectors.toList());
            return ticketRepository.findAllByStatusIdNotInAndUpdatedGreaterThanEqual(status, recentlyUpdated());
        } else if (id == 5) {
            return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(user), ModelTicketStatus.NEW);

        } else if (id == 6) {
            return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(user), ModelTicketStatus.PENDING);

        } else if (id == 7) {
            int[] b = {ModelTicketStatus.NEW, ModelTicketStatus.CLOSED, ModelTicketStatus.OPEN, ModelTicketStatus.PENDING, ModelTicketStatus.SUSPENDED};
            List<Integer> status = Arrays.stream(listOfStatus) // IntStream
                    .boxed() // Stream<Integer>
                    .collect(Collectors.toList());
            return ticketRepository.findAllByStatusIdNotInAndUpdatedGreaterThanEqual(status, recentlyUpdated());
        } else if (id
                == 8) {
            return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(user), ModelTicketStatus.SUSPENDED);

        } else if (id
                == 9) {
            return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(user), ModelTicketStatus.CLOSED);

        } else {
            return null;
        }

    }

    public ModelTicket deleteTicket(Long ticketId, ModelUser user) {
        Optional<ModelTicket> ticket = ticketRepository.findById(ticketId);
        ticket.get().setStatus(ticketStatusRepository.findById(ModelTicketStatus.CLOSED));
        ticket.get().setUpdated(new Date());
        ticket.get().setDeletedBy(user);
        ticketRepository.save(ticket.get());
        return ticket.get();
    }

    public ModelTicket undeleteTicket(ModelTicket ticket) {
        ticket.setStatus(ticketStatusRepository.findById(ModelTicketStatus.OPEN));
        ticket.setDeletedBy(null);
        ticketRepository.save(ticket);
        ModelUser user = ticket.getRequester();
        user.setBlocked(false);
        userRepository.save(user);
        return ticket;
    }

    public BeanPair<ModelTicket, ModelUser> spamTicket(Long ticketId, ModelUser beanUser) {
        ModelTicket ticket = deleteTicket(ticketId, beanUser);
        ModelUser user = ticket.getRequester();
        user.setBlocked(true);
        userRepository.save(user);
        List<ModelTicket> tickets = ticketRepository.findAllByAssignedGroupInAndRequesterId(getUserGroupsId(beanUser), user.getId());
        for (ModelTicket t : tickets) {
            deleteTicket(t.getId(), beanUser);
        }
        return new BeanPair<>(ticket, user);

    }

    public List<BeanPair<Long, Long>> countTickets(ModelUser user) {
        List<BeanPair<Long, Long>> countStatus = ticketRepository.countAllByStatusId(getUserGroupsId(user));
        for (BeanPair<Long, Long> i : countStatus) {
            System.out.println(i.getFirst() + "  " + i.getSecond());
        }

//        if (beanSession.getUser().getUserType().getId() == ModelUserType.Agent || beanSession.getUser().getUserType().getId() == ModelUserType.LightAgent) {
//            List<Object[]> countUnsolvedUnassigned = ticketRepository.countAllUnsolvedUnassigned(getUserId(), getUserGroupsId().get(0).getId());
//            return countStatus;
//        }
        return countStatus;
    }

}
