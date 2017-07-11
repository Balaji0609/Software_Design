package com.mycompany.gradeappealserver.activities;

import com.mycompany.gradeappealserver.model.Appeal;
import com.mycompany.gradeappealserver.model.AppealStatus;
import com.mycompany.gradeappealserver.model.Identifier;
import com.mycompany.gradeappealserver.repositories.AppealRepository;
import com.mycompany.gradeappealserver.representations.AppealRepresentation;
import com.mycompany.gradeappealserver.representations.RestbucksUri;

/**
 *
 * @author Balaji Chandrasekaran
 */



public class RemoveAppealActivity {
    public AppealRepresentation delete(RestbucksUri appealUri) throws Exception {
 
        Identifier identifier = appealUri.getId();

        AppealRepository appealRepository = AppealRepository.current();

        if (appealRepository.appealNotPlaced(identifier)) {
            throw new Exception();
        }

        Appeal appeal = appealRepository.get(identifier);

        if (appeal.getStatus() == AppealStatus.APPROVED ) {
            throw new Exception();
        }

        if(appeal.getStatus() == AppealStatus.PENDING) {
            appealRepository.remove(identifier);
        }

        return new AppealRepresentation(appeal);
    }
}
