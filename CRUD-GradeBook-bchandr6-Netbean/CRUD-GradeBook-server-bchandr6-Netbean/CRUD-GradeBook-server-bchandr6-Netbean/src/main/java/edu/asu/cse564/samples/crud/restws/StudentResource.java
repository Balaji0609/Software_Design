/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.samples.crud.restws;

import java.net.URI;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.cse564.samples.crud.jaxb.model.Student;
import edu.asu.cse564.samples.crud.jaxb.utils.Converter;


/**
 * REST Web Service
 *
 * @author fcalliss
 */
@Path("Appointment")
public class StudentResource {
       
    private static final Logger LOG = LoggerFactory.getLogger(StudentResource.class);
    
    private static Student appointment = null;

    @Context
    private UriInfo context;

    private static HashMap<Integer, Student> dg = new HashMap();

    /**
     * Creates a new instance of AppointmentResource
     */
    public StudentResource() {
        LOG.info("Creating an Appointment Resource");
    }

    
    // completed*****************************************
    
    
    
    /**
     * POST method for creating an instance of ScratchResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response createResource(String content) 
    {
        int flag = 0;
        LOG.info("Creating the instance Appointment Resource {}", appointment);
        LOG.debug("POST request");
        LOG.debug("Request Content = {}", content);
        
        Response response = null;
              
        try 
        {
            appointment = (Student) Converter.convertFromXmlToObject(content, Student.class);
            LOG.debug("The XML {} was converted to the object {}", content, appointment);
            LOG.info("Creating a {} {} Status Response", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());
            flag = appointment.getStudentID();
        } 
        catch (JAXBException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with Appointment Resource", content);

            response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
        } 
        catch (RuntimeException e) {
            LOG.debug("Catch All exception");

            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
        }


        if (!dg.containsKey((Integer)flag))
        {
            try
            {
                dg.put(appointment.getStudentID(), appointment);
                String xmlString = Converter.convertFromObjectToXml(appointment, Student.class);

                URI locationURI = URI.create(context.getAbsolutePath() + "/" + Integer.toString(appointment.getStudentID()));

                response = Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
                LOG.debug("Generated response {}", response);
            } 
            catch (RuntimeException e) 
            {
                LOG.debug("Catch All exception");
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                LOG.debug("Attempting to create an Appointment Resource and setting it to {}", content);
            }
        } 
        else 
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
            LOG.debug("Cannot create Appointment Resource as values is already set to {}", appointment);
                      
            response = Response.status(Response.Status.CONFLICT).entity(content).build();
            LOG.debug("Generated response {}", response);
        }
        
        return response;
    }

    
    // completed ***************
    
    /**
     * Retrieves representation of an instance of edu.asu.cse446.sample.crud.restws.StudentResource
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getResource(@PathParam("id") String id) 
    {
        LOG.info("Retrieving the Appointment Resource {}", appointment);
        LOG.debug("GET request");
        LOG.debug("PathParam id = {}", id);
        
        Response response;
        
        if (dg.isEmpty())
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
            LOG.debug("No Appointment Resource to return");
            
            response = Response.status(Response.Status.GONE).entity("No Appointment Resource to return").build();
        } 
        else 
        {
           // LOG.debug("appointment.getId() = {}", appointment.getId());
            if (dg.containsKey(Integer.parseInt(id)))
            {
                
                appointment = dg.get(Integer.parseInt(id));
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                LOG.debug("Retrieving the Appointment Resource {}", appointment);
         
                String xmlString = Converter.convertFromObjectToXml(appointment, Student.class);
                
                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } 
            else 
            {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                LOG.debug("Retrieving the Appointment Resource {}", appointment);
                
                response = Response.status(Response.Status.NOT_FOUND).entity("No Appointment Resource to return").build();
            }
        }        
        
        LOG.debug("Returning the value {}", response);
        
        return response;
    }
    
    
    
    
    
    

    /**
     * PUT method for updating an instance of ScratchResource
     * @param id
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateResource(@PathParam("id") String id, String content) 
    {
        LOG.info("Updating the Appointment Resource {} with {}", appointment, content);
        LOG.debug("PUT request");
        LOG.debug("PathParam id = {}", id);
        LOG.debug("Request Content = {}", content);
        
        Response response;
        
        if (!dg.isEmpty() && dg.containsKey(Integer.parseInt(id)))
        {
            LOG.debug("Attempting to update the Appointment Resource {}", appointment);
            
            try 
            {
                appointment = (Student) Converter.convertFromXmlToObject(content, Student.class);
                dg.put(Integer.parseInt(id), appointment);
                LOG.debug("The XML {} was converted to the object {}", content, appointment);         
                LOG.debug("Updated Appointment Resource to {}", appointment);
                
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
            
                String xmlString = Converter.convertFromObjectToXml(appointment, Student.class);
                
                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } 
            catch (JAXBException e) {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with Appointment Resource", content);
                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            } 
            catch (RuntimeException e) {
                LOG.debug("Catch All exception");
                
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        } 
        else 
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
            LOG.debug("Cannot update Appointment Resource {} as it has not yet been created", appointment);
                      
            response = Response.status(Response.Status.CONFLICT).entity(content).build();
        }

        LOG.debug("Generated response {}", response);
        
        return response;
    }

    
    
    
    // completed ***************************************************
    /**
     * Retrieves representation of an instance of edu.asu.cse446.sample.crud.restws.StudentResource
     * @param id
     * @param content
     * @return an instance of java.lang.String
     */
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteResource(@PathParam("id") String id, String content) 
    {
        LOG.info("Updating the Appointment Resource {} with {}", appointment, content);
        LOG.debug("PUT request");
        LOG.debug("PathParam id = {}", id);
        LOG.debug("Request Content = {}", content);
        
        Response response;
        
        if (!dg.isEmpty() && dg.containsKey(Integer.parseInt(id)))
        {
            LOG.debug("Attempting to update the Appointment Resource {}", appointment);
            
            try 
            {
                appointment = (Student) Converter.convertFromXmlToObject(content, Student.class);
                dg.put(Integer.parseInt(id), appointment);
                LOG.debug("The XML {} was converted to the object {}", content, appointment);         
                LOG.debug("Updated Appointment Resource to {}", appointment);
                
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
            
                String xmlString = Converter.convertFromObjectToXml(appointment, Student.class);
                
                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } 
            catch (JAXBException e) {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with Appointment Resource", content);
                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            } 
            catch (RuntimeException e) {
                LOG.debug("Catch All exception");
                
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        } 
        else 
        {
            LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
            LOG.debug("Cannot update Appointment Resource {} as it has not yet been created", appointment);
                      
            response = Response.status(Response.Status.NOT_FOUND).entity(content).build();
        }

        LOG.debug("Generated response {}", response);
        
        return response;
//        LOG.info("Removing the Appointment Resource {}", appointment);
//        LOG.debug("DELETE request");
//        LOG.debug("PathParam id = {}", id);
//        
//        Response response;
//        
//        if (dg.isEmpty())
//        {
//            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
//            LOG.debug("No Appointment Resource to delete");
//            
//            response = Response.status(Response.Status.GONE).build();
//        } 
//        else 
//        {
//            if (dg.containsKey(Integer.parseInt(id)))
//            {
//                try
//                {
//                    appointment = (Student) Converter.convertFromXmlToObject(content, Student.class);
//                    dg.put(Integer.parseInt(id), appointment);
//                LOG.debug("The XML {} was converted to the object {}", content, appointment);
//                LOG.debug("Updated Student Resource to {}", appointment);
//
//                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
//
//                String xmlString = Converter.convertFromObjectToXml(appointment, Student.class);
//
//                response = Response.status(Response.Status.OK).entity(xmlString).build();
//                }
//                catch (JAXBException e) 
//                {
//                    LOG.info("DONT COME HERE");
//                    LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
//                    LOG.debug("XML is {} is incompatible with Appointment Resource", content);
//                
//                    response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
//                } 
//            } 
//            else 
//            {
//                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
//                LOG.debug("Retrieving the Appointment Resource {}", appointment);
//                
//                response = Response.status(Response.Status.NOT_FOUND).build();
//            }
//        }        
//        
//        LOG.debug("Generated response {}", response);
//        
//        return response;
    }
}

