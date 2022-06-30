package com.tecfrac.helpdesk.service;

import com.tecfrac.helpdesk.bean.BeanPair;
import com.tecfrac.helpdesk.repository.TicketPriorityRepository;
import com.tecfrac.helpdesk.bean.BeanSession;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TicketService {}
    /*
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private BeanSession beanSession;

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

    private List<ModelGroup> getUserGroupsId(ModelUser user) {
        List<ModelGroup> group = new ArrayList<>();
        if (user.getUserType().getId() == ModelUserType.Administrator) {
            return groupRepository.findAllByCompanyId(user.getCompany().getId());
        } else if (user.getUserType().getId() != ModelUserType.User) {
            group.add(userGroupRepository.findByUserId(user.getId()).getGroup());

        }
        return group;
    }

    private Long getUserId() {
        return beanSession.getUser().getId();
    }

    public ModelTicket mergeTicketsInto(Long id, List<Long> ticketsIds, ModelUser user) {
        ModelTicket ticket = ticketRepository.findById(id).get();
        ModelUser assignee = ticket.getAssignedUser();
        String deletedIds = "#";
        for (Long ticketId : ticketsIds) {
            String deleted = deletedTicketsForever(ticketId, user).getId().toString();
            deletedIds += deleted + ",";
        }
        deletedIds = deletedIds.substring(0, deletedIds.length() - 1);
        ModelTicketMessage message = new ModelTicketMessage();
        message.setDateCreation(new Date());
        message.setMessage("Requests " + deletedIds + " were closed and merged into this request");
        message.setUser(beanSession.getUser());
        message.setTicket(ticket);
        message.setUseType(beanSession.getUser().getUserType());
        message.setInternal(Boolean.FALSE);
        messageRepository.save(message);

        return ticket;
    }

    public ModelTicket deletedTicketsForever(Long id, ModelUser user) {
        ModelTicket ticket = ticketRepository.findById(id).get();
        ticketRepository.deleteByAssignedGroupInAndId(getUserGroupsId(user), id);
        return ticket;
    }

    public List<ModelTicketMessage> getTicketMessges(Long id) {
        if (beanSession.getUser().getUserType().getId() != 1) {
            return ticketMessageRepository.findAllByticketIdOrderByDateCreationDesc(id);
            //(id, getUserGroupId());
        }
        return ticketMessageRepository.findAllByticketIdAndInternalNotOrderByDateCreationDesc(id, true);
    }

    public BeanPair<Long, String> getLastMessages(Long id) {
        ModelUser user = beanSession.getUser();
        user = userRepository.findById(1).get();
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

        if (ticketDetails.getStatusId() == ModelTicketStatus.SUSPENDED) {
            return spamTicket(ticketDetails.getId(), user).getFirst();
        } else if (ticket.getStatus().getId() == ModelTicketStatus.CLOSED && ticketDetails.getStatusId() != ModelTicketStatus.CLOSED) {
            undeleteTicket(ticket);
        }
        return addOrEditTicket(ticket, ticketDetails);
    }

    public ModelTicket addTicket(@RequestParam(required = true) RequestAddTicket request) throws HelpDeskException {
        return addOrEditTicket(null, request);
    }

    public ModelTicket addOrEditTicket(ModelTicket ticket, @RequestParam(required = true) RequestAddTicket request) throws HelpDeskException {
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
            requester = beanSession.getUser();
        }
        ticket.setRequester(requester);
        if (status != null) {

            ticket.setStatus(status);
        } else {
            ticket.setStatus(ticketStatusRepository.findById(ModelTicketStatus.OPEN));
        }
        ModelGroup group = ticket.getAssignedGroup();
        if (request.getAssignedGroupId() != null) {
            group = groupRepository.findById((int) request.getAssignedGroupId());
        }
        ticket.setAssignedGroup(group);
        ModelUser assignee = beanSession.getUser();
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
//            System.out.println("message : " + request.getMessage());
            message = new ModelTicketMessage();
            message.setDateCreation(new Date());
            message.setMessage(request.getMessage());
            message.setUser(beanSession.getUser());
            message.setTicket(ticket);
            message.setUseType(beanSession.getUser().getUserType());
            if (beanSession.getUser().getUserType().getId() != ModelUserType.User) {// 0 the index of the normal user 

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

    public ModelTicket getTicket(Long ticketId) {
        return ticketRepository.findById(ticketId).get();
    }

    public List<ModelTicket> userUnSolvedTickets(ModelUser user) {
        return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUser(Arrays.asList(ModelTicketStatus.SOLVED, ModelTicketStatus.CLOSED, ModelTicketStatus.SUSPENDED), getUserGroupsId(user), beanSession.getUser());
    }

    public List<ModelTicket> unAssignedTickets(ModelUser user) {
        return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUser(Arrays.asList(ModelTicketStatus.SOLVED, ModelTicketStatus.CLOSED, ModelTicketStatus.SUSPENDED), getUserGroupsId(user), null);
    }

    public List<ModelTicket> recTickets(List<Long> list) {

        return ticketRepository.findAllByStatusIdNotInAndUpdatedGreaterThanEqual(list, recentlyUpdated());
    }

    public List<ModelTicket> recUpdatedTickets() {
        Long[] listOfStatus = {ModelTicketStatus.CLOSED, ModelTicketStatus.SOLVED, ModelTicketStatus.SUSPENDED};
        List<Long> status = Arrays.asList(listOfStatus);
        return recTickets(status);
    }

    public int countRecUpdated() {
        return recUpdatedTickets().size();
    }

    public List<ModelTicket> allTicketsByStatusId(RequestGetTicket getTicket, ModelUser beanUser) {
        ModelUser user = beanSession.getUser();
        if (user.getUserType().getId() != ModelUserType.User) {
            if (getTicket.isOnlyMine()) {
                if (getTicket.isRecently()) {
                    return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUserAndUpdatedGreaterThanEqual(getTicket.getStatusIds(),
                            getUserGroupsId(beanUser),
                            user,
                            recentlyUpdated());
                }
                return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndAssignedUser(getTicket.getStatusIds(), getUserGroupsId(beanUser), user);
            }
            if (getTicket.isRecently()) {
                return ticketRepository.findAllByStatusIdNotInAndAssignedGroupInAndUpdatedGreaterThanEqual(getTicket.getStatusIds(),
                        getUserGroupsId(beanUser), recentlyUpdated());
            }

            return ticketRepository.findAllByStatusIdNotInAndAssignedGroupIn(getTicket.getStatusIds(),
                    getUserGroupsId(beanUser));
        }
        return ticketRepository.findAllByStatusIdNotInAndRequester(getTicket.getStatusIds(), user);
    }

    public int countRecSolved() {
        return solvedTickets().size();
    }

    public ModelTicket deleteTicket(Long ticketId) {
        Optional<ModelTicket> ticket = ticketRepository.findById(ticketId);
        ticket.get().setStatus(ticketStatusRepository.findById(ModelTicketStatus.CLOSED));
        ticket.get().setUpdated(new Date());
        ticket.get().setDeletedBy(beanSession.getUser());
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
        ModelTicket ticket = deleteTicket(ticketId);
        ModelUser user = ticket.getRequester();
        user.setBlocked(true);
        userRepository.save(user);
        List<ModelTicket> tickets = ticketRepository.findAllByAssignedGroupInAndRequesterId(getUserGroupsId(beanUser), user.getId());
        for (ModelTicket t : tickets) {
            deleteTicket(t.getId());
        }
        return new BeanPair<>(ticket, user);

    }

    public List<BeanPair<Long, Long>> countTickets(ModelUser beanUser) {
        List<BeanPair<Long, Long>> countStatus = ticketRepository.countAllByStatusId(getUserGroupsId(beanUser));
        for (BeanPair<Long, Long> i : countStatus) {
            System.out.println(i.getFirst() + "  " + i.getSecond());
        }
//        if (beanSession.getUser().getUserType().getId() == ModelUserType.Agent || beanSession.getUser().getUserType().getId() == ModelUserType.LightAgent) {
//            List<Object[]> countUnsolvedUnassigned = ticketRepository.countAllUnsolvedUnassigned(getUserId(), getUserGroupsId().get(0).getId());
//            return countStatus;
//        }
        return countStatus;
    }
//    public List<BeanPair<Long, Long>> countTickets() {
//        if (beanSession.getUser().getUserType().getId() == ModelUserType.Agent || beanSession.getUser().getUserType().getId() == ModelUserType.LightAgent) {
//            System.out.println(getUserGroupsId().toString());
//            List<Object[]> countStatus = ticketRepository.countAllByStatusId(getUserGroupsId().get(0).getId());
//            List<Object[]> countUnsolvedUnassigned = ticketRepository.countAllUnsolvedUnassigned(getUserId(), getUserGroupsId().get(0).getId());
//            Long countrecupdated = countRecUpdated();
//            Long countrecSolved = countRecSolved();
//            Long countDeleted = 0;
//            Long countPending = 0;
//            Long countSuspended = 0;
//            Long countNew = 0;
//            for (int i = 0; i < countUnsolvedUnassigned.size(); i++) {
//                Long status = Long.valueOf(String.valueOf(countStatus.get(i)[0]));
//                Long value = Long.valueOf(String.valueOf(countStatus.get(i)[0]));
//            }
//            List<BeanPair<Long, Long>> counter = new ArrayList<>();
//            for (int i = 0; i < 9; i++) {
//
//                BeanPair<Long, Long> countView = new BeanPair<Long, Long>(i, 0);
//
//                if (i < 3) {
//                    countView.setSecond(Long.valueOf(String.valueOf(countUnsolvedUnassigned.get(0)[i])));
//                } else if (i == 3) {
//                    countView.setSecond(countrecupdated);
//                    countPending = Long.valueOf(String.valueOf(countStatus.get(i)[1]));
//
//                }
//                if (i < countStatus.size()) {
//                    int status = Long.valueOf(String.valueOf(countStatus.get(i)[0]));
//                    if (status == 0) {
//                        countDeleted = Long.valueOf(String.valueOf(countStatus.get(i)[1])) - 1;
//                    } else if (status == 1) {
//                        countNew = Long.valueOf(String.valueOf(countStatus.get(i)[1]));
//                    } else if (status == 3) {
//                        countSuspended = Long.valueOf(String.valueOf(countStatus.get(i)[1]));
//                    } else if (status == 5) {
//                        countSuspended = Long.valueOf(String.valueOf(countStatus.get(i)[1]));
//                    }
//                }
//                counter.add(countView);
//            }
//            counter.get(4).setSecond(countNew); 
//            counter.get(5).setSecond(countPending);
//            counter.get(6).setSecond(countrecSolved);
//            counter.get(7).setSecond(countSuspended);
//            counter.get(8).setSecond(countDeleted);;
//            return counter;
//        }
//        return null;
//    }
////////// for deletion

    public List<ModelTicket> suspendedTickets(ModelUser beanUser) {
        return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(beanUser), ModelTicketStatus.SUSPENDED);
    }
    /////////// for deletion

    public List<ModelTicket> pendingTickets(ModelUser beanUser) {
        return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(beanUser), ModelTicketStatus.PENDING);
    }

    //////////// for deletion
    public List<ModelTicket> statusTickets(Long statusId, ModelUser beanUser) {
        return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(beanUser), statusId);
    }
    //////////////////// for deletion

    public List<ModelTicket> solvedTickets() {
        Long[] b = {ModelTicketStatus.NEW, ModelTicketStatus.CLOSED, ModelTicketStatus.OPEN, ModelTicketStatus.PENDING, ModelTicketStatus.SUSPENDED};
        List<Long> status = Arrays.asList(b);

        return recTickets(status);
    }
////////////// for deletion

    public List<ModelTicket> unSolvedGroupTickets(int groupId) {
        return ticketRepository.findAllByAssignedGroupAndStatusIdNot(groupId, ModelTicketStatus.SOLVED);
    }
/////////// for deletion

    public List<ModelTicket> deletedTickets(ModelUser user) {
        return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(user), ModelTicketStatus.CLOSED);
    }
/////////////

    public List<ModelTicket> unSolvedTickets(ModelUser user) {
        return ticketRepository.findAllByStatusIdNotInAndAssignedGroupIn(Arrays.asList(ModelTicketStatus.SOLVED, ModelTicketStatus.CLOSED, ModelTicketStatus.SUSPENDED), getUserGroupsId(user));
    }
    //////////for deletion

    public List<ModelTicket> newGroupTickets(ModelUser user) {
        return ticketRepository.findAllByAssignedGroupInAndStatusId(getUserGroupsId(user), ModelTicketStatus.NEW);
    }
///////

}*/
