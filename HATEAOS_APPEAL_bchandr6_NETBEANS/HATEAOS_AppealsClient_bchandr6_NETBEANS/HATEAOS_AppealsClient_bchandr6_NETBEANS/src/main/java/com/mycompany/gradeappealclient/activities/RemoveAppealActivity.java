package com.mycompany.gradeappealclient.activities;

import com.mycompany.gradeappealclient.model.Appeal;
import com.mycompany.gradeappealclient.model.AppealStatus;
import com.mycompany.gradeappealclient.model.Identifier;
import com.mycompany.gradeappealclient.repositories.AppealRepository;
import com.mycompany.gradeappealclient.representations.AppealRepresentation;
import com.mycompany.gradeappealclient.representations.RestbucksUri;

/**
 *
 * @author Balaji Chandrasekaran
 */



public class RemoveAppealActivity {
    public AppealRepresentation delete(RestbucksUri appealUri) throws Exception {
        // Discover the URI of the order that has been cancelled
        
        Identifier identifier = appealUri.getId();

        AppealRepository appealRepository = AppealRepository.current();

        if (appealRepository.appealNotPlaced(identifier)) {
            throw new Exception();
        }

        Appeal appeal = appealRepository.get(identifier);

        // Can't delete a ready or preparing order
        if (appeal.getStatus() == AppealStatus.APPROVED ) {
            throw new Exception();
        }

        if(appeal.getStatus() == AppealStatus.PENDING) { // An unpaid order is being cancelled 
            appealRepository.remove(identifier);
        }

        return new AppealRepresentation(appeal);
    }
}
