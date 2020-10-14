package com.soapservice;

import com.github.javafaker.Faker;
import email.com.web_service.Email;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailRepository {
    private static List<Email> emailsList = new ArrayList<>();

    @PostConstruct
    public void initData() {
        emailsList.add(createEmail("email@gmail.com", "First subject", "Hello buddy"));
        emailsList.add(createEmail("email1@gmail.com", "Second subject", "Hello buddy too"));
        emailsList.add(createRandomEmail());
        emailsList.add(createRandomEmail());
        emailsList.add(createRandomEmail());
        emailsList.add(createRandomEmail());
    }

    private Email createRandomEmail() {
        Email email = new Email();
        Faker fake = new Faker();
        String address = fake.internet().emailAddress();
        System.out.println(address);
        email.setAddress(address);
        String subject = fake.name().fullName();
        System.out.println(subject);
        email.setSubject(subject);
        String body = fake.lorem().characters();
        System.out.println(body);
        email.setBody(fake.lorem().characters());
        return email;
    }

    private Email createEmail(String address, String subject, String body) {
        Email email = new Email();
        email.setAddress(address);
        email.setSubject(subject);
        email.setBody(body);
        return email;
    }

    public Email findEmailByAddress(String address) {
        return emailsList.stream().filter(element -> element.getAddress().equals(address)).findFirst().get();
    }

    public Email findEmailBySubject(String subject) {
        return emailsList.stream().filter(element -> element.getSubject().equals(subject)).findFirst().get();
    }

    public List<Email> findEmails() {
        return emailsList;
    }

    public void deleteEmail(int id) {
        emailsList.remove(id);
    }

    public void setRandomEmail() {
        emailsList.add(createRandomEmail());
    }
}
