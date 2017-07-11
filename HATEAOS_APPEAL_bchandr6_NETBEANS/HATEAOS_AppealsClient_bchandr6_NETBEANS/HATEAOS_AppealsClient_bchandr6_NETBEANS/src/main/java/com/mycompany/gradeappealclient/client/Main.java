package com.mycompany.gradeappealclient.client;

import com.mycompany.gradeappealclient.representations.GradeRepresentations;
import com.mycompany.gradeappealclient.representations.AppealRepresentation;
import com.mycompany.gradeappealclient.representations.Link;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.mycompany.gradeappealclient.model.Grade;
import com.mycompany.gradeappealclient.model.Appeal;
import static com.mycompany.gradeappealclient.model.AppealStatus.APPROVED;
import static com.mycompany.gradeappealclient.model.AppealStatus.REJECTED;
import com.mycompany.gradeappealclient.representations.RestbucksUri;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Balaji Chandrasekaran
 */
public class Main 
{
    
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final String GRADEAPPEAL_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
    private static final String ENTRY_POINT_URI = "http://localhost:8080/HATEAOS_AppealsServer_bchandr6_NETBEANS/webresources/grades";
     private static final String ENTRY_POINT_URI1 = "http://localhost:8080/HATEAOS_AppealsServer_bchandr6_NETBEANS/webresources/appeal";
     private static final String WRONG_CASE_URI = "http://localhost:8080/HATEAOS_AppealsServer_bchandr6_NETBEANS/webresrources/getappeal";
     
    public static void main(String[] args) throws Exception 
    {
        URI serviceUri = new URI(ENTRY_POINT_URI);
        URI serviceUri1 = new URI(ENTRY_POINT_URI1);
        //URI serviceUri2 = new URI(WRONG_CASE_URI);
        
        
        happyPathTest(serviceUri, serviceUri1);
        AbandonPathTest(serviceUri1);
        AppealFollowUp(serviceUri1);
        AppealReject(serviceUri1);
        //BadPathTest(serviceUri2);

        System.out.println();
        System.out.println();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("End of Process. Thank you. ");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        
        System.out.println();
    
        
    }
    
    
    private static void happyPathTest(URI serviceUri, URI serviceUri1) throws Exception 
    {
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println();
        System.out.println("Welcome to Grade Appeal System");
        System.out.println();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        
        System.out.println();
        System.out.println();
        LOG.info(" ||HAPPY CASE|| with Service URI {}. Case where student Submits an appeal and appeal in processed", serviceUri);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        
        System.out.println();
        System.out.println();

        // Place the appeal
        LOG.info("STEP 1 = Professor posts the grades on the site");
        System.out.println();
        LOG.info(String.format("About to start HAPPY CASE. Placing grades at [%s] via POST", serviceUri.toString()));
        Client client = Client.create(); //initialize client - from jersey
        LOG.debug("Client CREATED {}", client);
        Grade grade = new Grade('A');
        GradeRepresentations gradeRepresentation = client.resource(serviceUri).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(GradeRepresentations.class, grade);
        LOG.debug("Created Grade Representation {} denoted by the URI {}", gradeRepresentation, gradeRepresentation.getSelfLink().getUri().toString());
        LOG.info(String.format("Grades are posted at the following uri [%s]", gradeRepresentation.getSelfLink().getUri().toString()));          
        
        System.out.println();
        System.out.println();

        //compose an appeal
        LOG.info("STEP 2 = Students APPEALS the grade");
        System.out.println();
        StringBuilder AppealRequest = new StringBuilder();
        AppealRequest.append("I have a questions. Please consider?");
        Appeal appeal = new Appeal(AppealRequest);
        AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
        LOG.debug("Created Appeal Representation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        LOG.info(String.format("Appeals are posted at the following uri [%s]", appealRepresentation.getSelfLink().getUri().toString())); 
       
        System.out.println();
        System.out.println();

        // Get an appeal
        LOG.debug("STEP 3 = Obtain the appeal which is posted");
        System.out.println();
        LOG.info(String.format("About to request appeal from [%s] using the GET method", appealRepresentation.getSelfLink().getUri().toString()));
        Link appealLink = appealRepresentation.getSelfLink();
        AppealRepresentation postAppealRepresentation = client.resource(appealLink.getUri()).accept(GRADEAPPEAL_MEDIA_TYPE).get(AppealRepresentation.class);
        LOG.info(String.format("Appeal have been placed successfully, current status [%s], placed at %s", postAppealRepresentation.getStatus(), postAppealRepresentation.getSelfLink())); 
    
        System.out.println();
        System.out.println();
 
        LOG.debug("STEP 4 = Professor obtains the appeal inorder to review the same");
        System.out.println();
        LOG.info(String.format("About to update appeal at [%s] using the POST method", postAppealRepresentation.getUpdateLink().getUri().toString()));
        Appeal ap = new Appeal(postAppealRepresentation.getAppeal().getStatus());
        Link upLink = postAppealRepresentation.getUpdateLink();
        ap.setStatus(APPROVED);
        AppealRepresentation upRepresentation = client.resource(upLink.getUri()).accept(upLink.getMediaType()).type(upLink.getMediaType()).post(AppealRepresentation.class, ap );
        LOG.debug("The updated Appeal representation link is {}", upRepresentation);
        LOG.info(String.format("Appeal updated by Professor at [%s] and status of the appeal is %s", upRepresentation.getSelfLink().getUri().toString(),ap.getStatus()));
        
        
        System.out.println();
        System.out.println();

        LOG.debug("STEP 5 = Professors approves the appeal, and gets the grade in order to increase the grade");
        System.out.println();
        LOG.debug("Obtain the grade");
        LOG.info(String.format("About to request grade from [%s] using the GET method", gradeRepresentation.getSelfLink().getUri().toString()));
        Link gradeLink = gradeRepresentation.getSelfLink();
        GradeRepresentations postGradeRepresentation = client.resource(gradeLink.getUri()).accept(GRADEAPPEAL_MEDIA_TYPE).get(GradeRepresentations.class);
        LOG.info(String.format("Final grade is placed at the following ", postGradeRepresentation.getSelfLink())); 
     
        Grade newgrade = new Grade('B');
        Link newLink = postGradeRepresentation.getUpdateLink();

        LOG.info("Grade is updated"); 
              
       
       
         //Try to update a different order
        System.out.println();
        System.out.println();
        LOG.info(" ||BAD START||. Same as Happy case but you use an incorrect entry URI");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        
        System.out.println();
        System.out.println();

        LOG.info("STEP 1 = Updating an appeal that contains a INCORRECT URI");
        System.out.println();
        LOG.info(String.format("About to update an order with bad URI [%s] using the POST method", appealRepresentation.getUpdateLink().getUri().toString() + "/bad-uri"));
        StringBuilder AppealRequest1 = new StringBuilder();
        AppealRequest1.append("I have a questions. Would you please consider the rectified appeal?");
        LOG.debug("Creating the base appeal at {}", appeal);
        Link badLink = new Link("BAD", new RestbucksUri(appealRepresentation.getSelfLink().getUri().toString() + "/BAD-URI"), GRADEAPPEAL_MEDIA_TYPE);
        LOG.debug("Creating the bad link {}", badLink);
        ClientResponse badUpdateResponse = client.resource(badLink.getUri()).accept(badLink.getMediaType()).type(badLink.getMediaType()).post(ClientResponse.class, new AppealRepresentation(appeal));
        LOG.debug("Created Bad Update Response {}", badUpdateResponse);
        LOG.info(String.format("Tried to update appeal with bad URI at [%s] using the POST method, the outcome is [%d]", badLink.getUri().toString(), badUpdateResponse.getStatus()));
        
  
        System.out.println();
        System.out.println();
   
        LOG.debug("STEP 2 = Updating appeal with proper URI");
        System.out.println();
        LOG.info(String.format("About to update appeal at [%s] using the POST method", postAppealRepresentation.getUpdateLink().getUri().toString()));
        StringBuilder AppealRequest2 = new StringBuilder();
        AppealRequest2.append("I have a couple of questions. Could you Please consider the rectified appeal?");
        Appeal appeal3 = new Appeal(AppealRequest2);        

        Link updateLink = postAppealRepresentation.getUpdateLink();
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).post(AppealRepresentation.class, appeal3);
        LOG.debug("Created updated Appeal representation link {}", updatedRepresentation);
        LOG.info(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
    }
    
    private static void AbandonPathTest(URI serviceUri1) throws Exception
    {
     
        System.out.println();        
        Client client = Client.create(); //initialize client - from jersey
        System.out.println();
        System.out.println();
        LOG.info(" ||ABANDONED CASE||. Students post appeal, and abandons the appeal before professor review");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        
        System.out.println();
        System.out.println();
        
        LOG.info("STEP 1 = Student Posts an appeal");
        System.out.println();
        StringBuilder AppealRequest = new StringBuilder();
        AppealRequest.append("I have a questions. Could you Please consider?");
        Appeal appeal = new Appeal(AppealRequest);
        AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        LOG.info(String.format("Appeals are posted at [%s]", appealRepresentation.getSelfLink().getUri().toString())); 
       
        System.out.println();
        System.out.println();
                // Get an appeal
        LOG.debug("STEP 2 = obtain the appeal");
        System.out.println();
        LOG.info(String.format("About to request appeal from [%s] using the GET method", appealRepresentation.getSelfLink().getUri().toString()));
        Link appealLink = appealRepresentation.getSelfLink();
        AppealRepresentation postAppealRepresentation = client.resource(appealLink.getUri()).accept(GRADEAPPEAL_MEDIA_TYPE).get(AppealRepresentation.class);
        LOG.info(String.format("Appeal placed, current status [%s], placed at %s", postAppealRepresentation.getStatus(), postAppealRepresentation.getSelfLink())); 
        
        System.out.println();
        System.out.println();        
        
        LOG.debug("STEP 3 = Student abandons the appeal");
        System.out.println();
        LOG.info(String.format("Trying to remove the appeal from [%s] using the DELETE method. ", postAppealRepresentation.getDeleteLink().getUri().toString()));
        Link deleteLink = postAppealRepresentation.getSelfLink();
        ClientResponse finalResponse = client.resource(deleteLink.getUri()).delete(ClientResponse.class);
        LOG.info(String.format("Tried to delete appeal and the HTTP status is [%d]", finalResponse.getStatus()));
        if(finalResponse.getStatus() == 200) {
            LOG.info(String.format("Appeal status [%s],", finalResponse.getEntity(AppealRepresentation.class).getStatus()));
        }
    }
    
    private static void AppealFollowUp(URI serviceUri1) throws Exception 
    {
     
       System.out.println();
       System.out.println();
       LOG.info(" ||FORGOTTEN CASE||. Students post appeal and professor forgets case, student follows up.");
       System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
       
       System.out.println();
       System.out.println();
       
       Client client = Client.create(); 
       LOG.info("STEP 1 = Students Post an appeal");
       System.out.println();
       StringBuilder AppealRequest = new StringBuilder();
       AppealRequest.append("I have a questions. Could you Please consider?");
       Appeal appeal = new Appeal(AppealRequest);
       AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
       LOG.debug("Created AppealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
       LOG.info(String.format("Appeals posted at [%s]", appealRepresentation.getSelfLink().getUri().toString())); 

        System.out.println();
        System.out.println();

       LOG.info("STEP 2 = Student updates the appeal - with follow up comments");
       System.out.println();
       LOG.info(String.format("About to update appeal at [%s] using the POST method", appealRepresentation.getUpdateLink().getUri().toString()));
       StringBuilder AppealRequest2 = new StringBuilder();
       AppealRequest2.append("I have a couple of questions. Could you Please consider the rectified appeal?");
       Appeal appeal3 = new Appeal(AppealRequest2);        

       Link updateLink = appealRepresentation.getUpdateLink();
       AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).post(AppealRepresentation.class, appeal3);
       LOG.debug("Updated Appeal representation link {}", updatedRepresentation);
       LOG.info(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
       LOG.info("Appeal updated with follow up comments");
       
       
       System.out.println();
       System.out.println();
       LOG.info(" ||BAD ID||. Same as forgotten case but the client has forgotten/lost the id of the appeal when following up");
       System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
       
       System.out.println();
       System.out.println();
      
       LOG.info("creating appeal for this case");
       System.out.println();
       LOG.info("STEP 1 = Updating an appeal that contains a CONTAINS LOST ID");
       System.out.println();
       StringBuilder AppealRequest_new = new StringBuilder();
       AppealRequest_new.append("I have a questions. Could you Please consider?");
       Appeal appeal_new = new Appeal(AppealRequest);
       AppealRepresentation appealRepresentation_new = client.resource(serviceUri1).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, appeal_new);
       LOG.debug("Created AppealRepresentation {} denoted by the URI {}", appealRepresentation_new, appealRepresentation_new.getSelfLink().getUri().toString());
       LOG.info(String.format("Appeals posted at [%s]", appealRepresentation_new.getSelfLink().getUri().toString())); 

        System.out.println();
        System.out.println();

       LOG.info("STEP 2 = Student updates the appeal - with follow up comments");
       System.out.println();
       LOG.info(String.format("About to update appeal at [%s] using the POST method", appealRepresentation_new.getUpdateLink().getUri().toString()));
       StringBuilder AppealRequest2_new = new StringBuilder();
       AppealRequest2.append("I have a couple of questions. Could you Please consider the rectified appeal?");
       Appeal appeal3_new = new Appeal(AppealRequest2_new);        
       Link updateLink_new = appealRepresentation_new.getUpdateLink();
       AppealRepresentation updatedRepresentation_new = client.resource(updateLink_new.getUri()).accept(updateLink_new.getMediaType()).type(updateLink_new.getMediaType()).post(AppealRepresentation.class, appeal3_new);
       LOG.debug("Updated Appeal representation link {}", updatedRepresentation);
       LOG.info(String.format("Appeal updated at [%s]", updatedRepresentation_new.getSelfLink().getUri().toString()));
       LOG.info("Appeal updated with follow up comments");
       
    }

    private static void AppealReject(URI serviceUri1) throws Exception 
    {

        System.out.println();
        System.out.println();
        LOG.info(" ||REJECTS||. Case where appeal is posted, and professor rejects it.");
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"); 

        System.out.println();
        System.out.println();    
        
        Client client = Client.create();
        LOG.info("STEP 1 = Students Post an appeal");
        System.out.println();
        StringBuilder AppealRequest = new StringBuilder();
        AppealRequest.append("I have a questions. Please consider?");
        Appeal appeal = new Appeal(AppealRequest);
        AppealRepresentation appealRepresentation = client.resource(serviceUri1).accept(GRADEAPPEAL_MEDIA_TYPE).type(GRADEAPPEAL_MEDIA_TYPE).post(AppealRepresentation.class, appeal);
        LOG.debug("Created AppealRepresentation {} denoted by the URI {}", appealRepresentation, appealRepresentation.getSelfLink().getUri().toString());
        LOG.info(String.format("Appeals posted at [%s]", appealRepresentation.getSelfLink().getUri().toString())); 

        System.out.println();
        System.out.println();
        // Get an appeal
        LOG.debug("STEP 2 = Get the appeal");
        System.out.println();
        LOG.info(String.format("About to request appeal from [%s] via GET", appealRepresentation.getSelfLink().getUri().toString()));
        Link appealLink = appealRepresentation.getSelfLink();
        AppealRepresentation postAppealRepresentation = client.resource(appealLink.getUri()).accept(GRADEAPPEAL_MEDIA_TYPE).get(AppealRepresentation.class);
        LOG.info(String.format("Appeal placed, current status [%s], placed at %s", postAppealRepresentation.getStatus(), postAppealRepresentation.getSelfLink())); 

        System.out.println();
        System.out.println();
        
        LOG.debug("STEP 3 = Professor gets the appeal, revies and rejects the appeal");
        System.out.println();
        LOG.info(String.format("About to update appeal at [%s] via POST", postAppealRepresentation.getUpdateLink().getUri().toString()));
        Appeal app = new Appeal(postAppealRepresentation.getAppeal().getStatus());
        Link upLin = postAppealRepresentation.getUpdateLink();
        app.setStatus(REJECTED);
        AppealRepresentation uRepresentation = client.resource(upLin.getUri()).accept(upLin.getMediaType()).type(upLin.getMediaType()).post(AppealRepresentation.class, app );
        LOG.debug("Created updated Appeal representation link {}", uRepresentation);
        LOG.info(String.format("Appeal updated by Professor at [%s] and status is %s", uRepresentation.getSelfLink().getUri().toString(),app.getStatus()));
     
    }
}
