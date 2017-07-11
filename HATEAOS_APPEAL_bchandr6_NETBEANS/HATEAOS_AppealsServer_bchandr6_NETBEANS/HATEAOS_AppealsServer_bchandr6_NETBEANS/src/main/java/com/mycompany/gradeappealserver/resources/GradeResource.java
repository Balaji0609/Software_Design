
package com.mycompany.gradeappealserver.resources;

import com.mycompany.gradeappealserver.activities.CreateGradeActivity;
import com.mycompany.gradeappealserver.activities.ReadGradeActivity;
import com.mycompany.gradeappealserver.representations.GradeRepresentations;
import com.mycompany.gradeappealserver.representations.RestbucksUri;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Balaji Chandrasekaran
 */
@Path("/grades")
public class GradeResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradeResource.class);

    private @Context UriInfo uriInfo;
    

    public GradeResource() {
        LOG.info("OrderResource constructor");
    }

    @GET
    @Path("/{gradeId}")
    @Produces("application/vnd.cse564-appeals+xml ")
    public Response getTheGrade() {
        LOG.info("Retrieving a grade Resource");
        
        Response response;
        
        try {
            GradeRepresentations graderesponseRepresentation = new ReadGradeActivity().retrieveByUri(new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(graderesponseRepresentation).build();
        } catch(Exception e) {
            LOG.debug("No such grade");
            response = Response.status(Response.Status.NOT_FOUND).build();
        } 
        
        LOG.debug("Retrieved the grade resource", response);
        
        return response;
    }
    
    
    
    @POST
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response createGrade(String gradeRepresentation) {
        LOG.info("Creating a grade Resource");
        
        Response response;
        
        try {
            GradeRepresentations rq = GradeRepresentations.fromXmlString(gradeRepresentation);
            GradeRepresentations responseRepresentation=new CreateGradeActivity().create(rq.getGrade(), new RestbucksUri(uriInfo.getRequestUri()));//new GradeRepresentations(GradeRepresentations.fromXmlString(gradeRepresentation).getGrade(), new Link("self", new RestbucksUri("localhost")));
            response = Response.ok().entity(responseRepresentation).build();
        } catch (Exception e) {
            LOG.debug("Invalid link or URI {}", gradeRepresentation);
            response = Response.status(Status.BAD_REQUEST).build();
        }  
        
        LOG.debug("Resulting response for creating the grade resource is {}", response);
        
        return response;
    }
}
