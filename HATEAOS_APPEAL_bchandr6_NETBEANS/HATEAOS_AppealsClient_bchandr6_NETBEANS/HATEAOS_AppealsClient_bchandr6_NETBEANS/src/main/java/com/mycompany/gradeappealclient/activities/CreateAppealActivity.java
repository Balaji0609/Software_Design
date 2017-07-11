package com.mycompany.gradeappealclient.activities;

/**
 *
 * @author Balaji Chandrasekaran
 */
import com.mycompany.gradeappealclient.model.Appeal;
import com.mycompany.gradeappealclient.model.AppealStatus;
import com.mycompany.gradeappealclient.model.Identifier;
import com.mycompany.gradeappealclient.representations.AppealRepresentation;
import com.mycompany.gradeappealclient.representations.Link;
import com.mycompany.gradeappealclient.representations.Representations;
import com.mycompany.gradeappealclient.representations.RestbucksUri;
import com.mycompany.gradeappealclient.repositories.AppealRepository;

public class CreateAppealActivity {
    public AppealRepresentation create(Appeal appeal, RestbucksUri requestUri) {
        appeal.setStatus(AppealStatus.PENDING);
                
        Identifier identifier = AppealRepository.current().store(appeal);
        
        RestbucksUri appealUri = new RestbucksUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
        return new AppealRepresentation(appeal, 
                new Link(Representations.RELATIONS_URI + "Delete", appealUri), 
                new Link(Representations.RELATIONS_URI + "update", appealUri),
                new Link(Representations.SELF_REL_VALUE, appealUri));
    }
}
