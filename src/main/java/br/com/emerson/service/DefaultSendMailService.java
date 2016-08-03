package br.com.emerson.service;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.emerson.webclient.WebClientRest;

public class DefaultSendMailService implements SendMailService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSendMailService.class);
    private WebClientRest client;

    @Inject
    public DefaultSendMailService(WebClientRest client) {
        this.client = client;
    }

    @Override
    public Response sendHelloMail() {

        try {
            boolean response = client.send();
            return Response.ok().entity(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e).build();
        }

    }

    @Override
    public Response sendMail() {
        try {
            String json = "{\"to\":[\"test@test.com\"],\"subject\":\"Test Attach File\",\"text\":\"This is a test\"}";
            InputStream inputStream = new FileInputStream("src/main/resources/persons.xlsx");
            client.sendWithAttachment(json, inputStream, "persons.xlsx");

            logger.info("Sending...");
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Error on sending mail!");
            return Response.serverError().entity(e).build();
        }
    }

}
