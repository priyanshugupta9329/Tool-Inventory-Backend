package com.example.toolinventorysystem.services.serviceImpl;

import com.example.toolinventorysystem.services.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailServiceImpl implements EmailService {
    @Value("${send.grid.from.mail}")
    private String SENDGRID_FROM_MAIL;
    @Value("${send.grid.api.key}")
    private String SENDGRID_MAIL_API_KEY;

    public void sendMail(String subject, String body, String toMail) {
        try {
            log.info("sending invite mail with user credentials");
            Email from = new Email(SENDGRID_FROM_MAIL);
            from.setName("Tool Inventory"); //Sets name of email(email name) as usually in mail received email id will not be shown
            Email to = new Email(toMail);
            Content content = new Content("text/html", body);
            Mail mail = new Mail(from, subject, to, content);
            SendGrid sendGrid = new SendGrid(SENDGRID_MAIL_API_KEY);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println("Mail Send response is : " + response.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("could not send the mail ");
        }
    }
}

