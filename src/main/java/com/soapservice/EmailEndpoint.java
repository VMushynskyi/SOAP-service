package com.soapservice;


import email.com.web_service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EmailEndpoint {
    private static final String NAMESPACE_URI = "http://email/com/web-service";

    private EmailRepository emailRepository;

    @Autowired
    public EmailEndpoint(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmailByAddressRequest")
    @ResponsePayload
    public GetEmailResponse getEmailByAddress(@RequestPayload GetEmailByAddressRequest request) {
        GetEmailResponse response = new GetEmailResponse();
        response.setEmail(emailRepository.findEmailByAddress(request.getAddress()));
        response.setCode(setCode("200", "OK"));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmailBySubjectRequest")
    @ResponsePayload
    public GetEmailResponse getEmailBySubject(@RequestPayload GetEmailBySubjectRequest request) {
        GetEmailResponse response = new GetEmailResponse();
        response.setEmail(emailRepository.findEmailBySubject(request.getSubject()));
        response.setCode(setCode("200", "OK"));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEmailRequest")
    @ResponsePayload
    public GetAllEmailResponse getEmails(@RequestPayload GetAllEmailRequest request) {
        GetAllEmailResponse response = new GetAllEmailResponse();
        EmailsList emailsList = new EmailsList();
        emailsList.getEmail().addAll(emailRepository.findEmails());
        response.setEmailsList(emailsList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmailRequest")
    @ResponsePayload
    public DeleteEmailResponse deleteEmail(@RequestPayload DeleteEmailRequest request) {
        DeleteEmailResponse response = new DeleteEmailResponse();
        emailRepository.deleteEmail(request.getId());
        response.setCode(setCode("204", "Deleted"));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setEmailRequest")
    @ResponsePayload
    public SetEmailResponse setEmail(@RequestPayload SetEmailRequest request) {
        SetEmailResponse response = new SetEmailResponse();
        emailRepository.setRandomEmail();
        response.setCode(setCode("200", "Added"));
        return response;
    }

    private static Code setCode(String codeNumber, String message) {
        Code cd = new Code();
        cd.setCodeStatus(codeNumber);
        cd.setCodeMessage(message);
        return cd;
    }
}
