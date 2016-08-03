package br.com.emerson.webclient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.AttachmentBuilder;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Named
public class WebClientRest {

    @Value("${webmail.path}")
    private String mailURL;

    public boolean send() {
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJaxbJsonProvider());
        WebClient webClient = WebClient.create(this.mailURL, providers);
        Response response = webClient.path("/email/hello").get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        }
        return false;
    }

    public boolean sendWithAttachment(String json, InputStream inputStream, String fileName) {
        Attachment fileJson = new AttachmentBuilder().object(json).mediaType("application/json").contentDisposition(new ContentDisposition("form-data; name=\"model\"")).build();
        Attachment fileAttachment = new Attachment("file", inputStream, new ContentDisposition("form-data; name=\"file\"; filename=\"" + fileName + "\""));
        MultipartBody multipartBody = new MultipartBody(Arrays.asList(fileJson, fileAttachment), MediaType.MULTIPART_FORM_DATA_TYPE, true);

        Response response = WebClient.create(this.mailURL).type(MediaType.MULTIPART_FORM_DATA).path("/email").post(multipartBody);
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        }
        return false;
    }

}
