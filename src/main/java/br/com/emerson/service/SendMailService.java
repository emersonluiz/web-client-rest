package br.com.emerson.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/send")
public interface SendMailService {

    @GET
    @Path("hello")
    Response sendHelloMail();

    @GET
    Response sendMail();

}
