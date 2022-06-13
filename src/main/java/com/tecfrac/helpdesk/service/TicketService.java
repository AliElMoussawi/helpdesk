/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecfrac.helpdesk.service;

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
import com.tecfrac.helpdesk.repository.TicketRepository;
import com.tecfrac.helpdesk.repository.TicketStatusRepository;
import com.tecfrac.helpdesk.repository.TicketTypeRepository;
import com.tecfrac.helpdesk.repository.UserRepository;
import com.tecfrac.helpdesk.request.RequestAddTicket;
import com.tecfrac.helpdesk.request.RequestMessageTicket;
import com.tecfrac.helpdesk.service.AuthenticationService.Pair;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author CLICK ONCE
 */
@Service
public class TicketService {

    private JavaMailSender javaMailSender;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    EmailService emailService;

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

    //first i have to create an post/get/put/delete api's
    public ModelTicket addTicket(@RequestParam(required = true) RequestAddTicket request) throws HelpDeskException {
        return addOrEditTicket(null, request);
    }

    public ModelTicket addOrEditTicket(ModelTicket ticket, @RequestParam(required = true) RequestAddTicket request) throws HelpDeskException {
        Set<ModelTag> tags = new HashSet<ModelTag>();
        ModelTicketStatus status = null;
        if (ticket != null) {
            if (request.getTypeId() != null) {
                Optional<ModelTicketType> ticketType = ticketTypeRepository.findById(request.getTypeId());
                ticket.setTicketType(ticketType.get());
            }
            System.out.println("priority:" + request.getPriortiyId());
            if (request.getPriortiyId() != null) {
                Optional<ModelTicketPriority> ticketPriority = ticketPriorityRepository.findById(request.getPriortiyId());

                ticket.setPriority(ticketPriority.get());
            }
            if (request.getStatusId() != null) {
                status = ticketStatusRepository.findById(request.getStatusId()).orElse(ticketStatusRepository.findById(ModelTicketStatus.Open));
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
            ticket.setStatus(ticketStatusRepository.findById(ModelTicketStatus.Open));
        }
        //  ticket.setValid(request.getValid() == null ? true : request.getValid());
        ModelGroup group = ticket.getAssignedGroup();
        if (request.getAssignedGroupId() != null) {
            group = groupRepository.findById((int) request.getAssignedGroupId());
        }
        ticket.setAssignedGroup(group);
        ModelUser assignee = beanSession.getUser();
        System.out.println("assignee: " + assignee.getUsername());
        if (request.getAssignedUserId() != null) {
            assignee = userRepository.findById(request.getAssignedUserId()).get();
        }
        ticket.setAssignedUser(assignee);
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            tags = tag(request.getTags(), ticket);
            //  ticket.setModelTags(tags);
        }
        ModelTicketMessage message = null;
        if (request.getMessage() != null) {
            System.out.println("message : " + request.getMessage());
            message = new ModelTicketMessage();
            message.setDateCreation(new Date());
            message.setMessage(request.getMessage());
            message.setUser(beanSession.getUser());
            message.setTicket(ticket);
            message.setUseType(beanSession.getUser().getUserType());
            if (beanSession.getUser().getUserType().getId() != ModelUserType.NewUser) {// 0 the index of the normal user 

                message.setInTernal(request.getInternal());
            } else {
                message.setInTernal(Boolean.FALSE);
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

    public ModelTicket updateTicket(Integer ticketId, RequestAddTicket newInfo) throws HelpDeskException {
        ModelTicket ticket = ticketRepository.findById(ticketId).get();
        return addOrEditTicket(ticket, newInfo);
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

    public ModelTicket getTicket(Integer ticketId) {
        return ticketRepository.findById(ticketId).get();
    }

    public List<ModelTicket> allTickets() {
        return ticketRepository.findAll();
    }

    public List<ModelTicket> userUnSolvedTickets(Integer userId) {
        return ticketRepository.findAllByAssignedUserIdAndStatusIdOrStatusIdNot(userId, ModelTicketStatus.Solved, ModelTicketStatus.Closed);
    }

    public List<ModelTicket> unAssignedTickets() {
        return ticketRepository.findAllByAssignedUserIdNullAndStatusIdOrStatusIdNot(ModelTicketStatus.Solved, ModelTicketStatus.Closed);

    }

    public List<ModelTicket> unSolvedTickets() {
        return ticketRepository.findAllByStatusIdOrStatusIdNot(ModelTicketStatus.Solved, ModelTicketStatus.Closed);
    }

    public List<ModelTicket> recUpdatedTickets() {
        return ticketRepository.findAllByUpdatedNot(null);
    }

    public List<ModelTicket> newTickets() {
        return ticketRepository.findAllByStatusId(ModelTicketStatus.NEW);
    }

    public List<ModelTicket> pendingTickets() {
        return ticketRepository.findAllByStatusId(ModelTicketStatus.Pending);

    }

    public List<ModelTicket> solvedTickets() {
        return ticketRepository.findAllByStatusId(ModelTicketStatus.Solved);
    }

    public List<ModelTicket> unSolvedGroupTickets(int groupId) {
        return ticketRepository.findAllByAssignedGroupIdAndStatusIdNot(groupId, ModelTicketStatus.Solved);
    }

    public List<ModelTicket> deletedTickets() {
        return ticketRepository.findAllByStatusId(0);
    }

    public String deletedTicketsForever(Integer id) {
        ticketRepository.deleteById(id);
        return id + "";
    }

    public ModelTicket deleteTicket(Integer ticketId) {
        Optional<ModelTicket> ticket = ticketRepository.findById(ticketId);
        ticket.get().setStatus(ticketStatusRepository.findById(0));
        ticket.get().setUpdated(new Date());
        ticket.get().setDeletedBy(beanSession.getUser());
        ticketRepository.save(ticket.get());
        return ticket.get();
    }

    public ModelTicket undeleteTicket(Integer ticketId) {
        Optional<ModelTicket> ticket = ticketRepository.findById(ticketId);
        ticket.get().setStatus(ticketStatusRepository.findById(2));
        ticket.get().setDeletedBy(null);
        ticketRepository.save(ticket.get());
        ModelUser user = ticket.get().getRequester();
        user.setBlocked(false);
        userRepository.save(user);
        return ticket.get();
    }

    public Pair<ModelTicket, ModelUser> spamTicket(Integer ticketId) {
        ModelTicket ticket = deleteTicket(ticketId);
        ModelUser user = ticket.getRequester();
        user.setBlocked(true);
        userRepository.save(user);
        List<ModelTicket> tickets = ticketRepository.findAllByRequesterId(user.getId());
        for (ModelTicket t : tickets) {
            deleteTicket(t.getId());
        }
        return new Pair<>(ticket, user);

    }

    public List<ModelTicket> allTicketsByStatusId(Integer statusId) {
        return ticketRepository.findAllByStatusId(statusId);
    }

    public List<ModelTicket> suspendedTickets() {
        return ticketRepository.findAllByStatusId(ModelTicketStatus.Suspended);
    }

    public List<ModelTicket> groupDeletedTickets() {
        return ticketRepository.findAllByStatusIdAndAssignedGroupId(0, beanSession.getUser());
    }

}
