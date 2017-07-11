package com.mycompany.gradeappealclient.activities;

import com.mycompany.gradeappealclient.model.Appeal;
import com.mycompany.gradeappealclient.model.Identifier;
import com.mycompany.gradeappealclient.repositories.AppealRepository;
import com.mycompany.gradeappealclient.representations.AppealRepresentation;
import com.mycompany.gradeappealclient.representations.RestbucksUri;

/**
 *
 * @author Balaji Chandrasekaran
 */
public class ReadAppealActivity {
    
        public AppealRepresentation retrieveByUri(RestbucksUri appealUri) throws Exception {
        Identifier identifier  = appealUri.getId();
        
        Appeal appeal = AppealRepository.current().get(identifier);
        
        if(appeal == null) {
            throw new Exception();
        }
        
        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
    
}

    
    
    
}
