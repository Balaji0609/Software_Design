package com.mycompany.gradeappealserver.activities;

import com.mycompany.gradeappealserver.model.Appeal;
import com.mycompany.gradeappealserver.model.Identifier;
import com.mycompany.gradeappealserver.repositories.AppealRepository;
import com.mycompany.gradeappealserver.representations.AppealRepresentation;
import com.mycompany.gradeappealserver.representations.RestbucksUri;


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
