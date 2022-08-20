package com.tecfrac.helpdesk.openfire.utils;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.IQ;

import com.tecfrac.helpdesk.openfire.beans.RequestInfo;
import com.tecfrac.helpdesk.openfire.component.DeskComponent;

public class IQUtils {

    private static final Logger log = LoggerFactory.getLogger(DeskComponent.class);

    public static RequestInfo getRequestInfo(IQ iq) {
        log.info("create request info");
        Long ticketId = Long.parseLong(iq.getChildElement().attributeValue("ticket-id"));
        System.out.println("ticketId:" + ticketId);
        String userJID = iq.getFrom().getNode();
        System.out.println("userJID:" + userJID);
        RequestInfo requestInfo = new RequestInfo();
//        Element content = iq.getExtension("message", DeskComponent.NAMESPACE).getElement();
//        String body = content.element("body").getText();
        requestInfo.setUserJID(userJID);
//        requestInfo.setBody(body);
        requestInfo.setTicketId(ticketId);
        requestInfo.setAction(iq.getChildElement().attributeValue("action"));
        return requestInfo;
    }

    public static IQ failureResponse(IQ iq, String reason) throws Exception {
        IQ response = null;
        try {
            response = IQ.createResultIQ(iq);
        } catch (IllegalArgumentException e) {
            log.info("could not make result iq");
            throw new Exception(e.getMessage());
        }
        final Element failure = response.setChildElement("failure", iq.getChildElement().getNamespaceURI());
        failure.setText(reason);
        log.info("response: ");
        log.info(response.toXML());
        log.info("");
        return response;
    }

    public static IQ successResponse(IQ iq, String ticketId, String message) throws Exception {
        IQ response = null;
        try {
            response = IQ.createResultIQ(iq);
        } catch (IllegalArgumentException e) {
            log.info("could not make result iq");
            throw new Exception(e.getMessage());
        }
        Element success = response.setChildElement("success", iq.getChildElement().getNamespaceURI());
        success.addElement("ticket-id").setText(ticketId);
        success.addElement("data").setText(message);
        log.info("response: ");
        log.info(response.toXML());
        log.info("");
        return response;
    }

}
