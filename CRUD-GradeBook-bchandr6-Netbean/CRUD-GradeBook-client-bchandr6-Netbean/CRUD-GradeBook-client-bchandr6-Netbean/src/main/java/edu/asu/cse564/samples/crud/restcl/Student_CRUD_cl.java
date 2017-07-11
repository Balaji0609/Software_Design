/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.samples.crud.restcl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jersey REST client generated for REST resource:AppointmentResource
 * [Appointment]<br>
 * USAGE:
 * <pre>
        Student_CRUD_cl client = new Student_CRUD_cl();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author fcalliss
 */
public class Student_CRUD_cl {
    
    private static final Logger LOG = LoggerFactory.getLogger(Student_CRUD_cl.class);
    
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/CRUD-GradeBook-server-bchandr6-Netbean/webresources";

    public Student_CRUD_cl() {        
        LOG.info("Creating a Appointment_CRUD REST client");

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("Appointment");
        LOG.debug("webResource = {}", webResource.getURI());
    }

    public ClientResponse createAppointment(Object requestEntity) throws UniformInterfaceException {
        LOG.info("Initiating a Create request");
        LOG.debug("XML String = {}", (String) requestEntity);
        
        return webResource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse deleteAppointment(Object requestEntity, String id) throws UniformInterfaceException {
        LOG.info("Initiating a Delete request");
        LOG.debug("XML String = {}", (String) requestEntity);
        LOG.debug("Id = {}", id);
        
        return webResource.path(id).type(MediaType.APPLICATION_XML).delete(ClientResponse.class, requestEntity);
    }

    public ClientResponse updateAppointment(Object requestEntity, String id) throws UniformInterfaceException {
        LOG.info("Initiating an Update request");
        LOG.debug("XML String = {}", (String) requestEntity);
        LOG.debug("Id = {}", id);
        
        return webResource.path(id).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }

    public <T> T retrieveAppointment(Class<T> responseType, String id) throws UniformInterfaceException {
        LOG.info("Initiating a Retrieve request");
        LOG.debug("responseType = {}", responseType.getClass());
        LOG.debug("Id = {}", id);
        
        //WebResource resource = webResource;
        //resource = resource.path(id);
        
        return webResource.path(id).accept(MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        LOG.info("Closing the REST Client");
        
        client.destroy();
    }
    
}
