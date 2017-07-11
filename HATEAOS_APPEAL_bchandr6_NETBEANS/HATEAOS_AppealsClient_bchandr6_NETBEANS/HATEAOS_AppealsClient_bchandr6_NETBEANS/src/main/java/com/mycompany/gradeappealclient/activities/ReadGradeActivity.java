package com.mycompany.gradeappealclient.activities;

import com.mycompany.gradeappealclient.model.Grade;
import com.mycompany.gradeappealclient.model.Identifier;
import com.mycompany.gradeappealclient.repositories.GradeRepository;
import com.mycompany.gradeappealclient.representations.GradeRepresentations;
import com.mycompany.gradeappealclient.representations.RestbucksUri;



/**
 *
 * @author Balaji Chandrasekaran
 */
public class ReadGradeActivity {
    
        public GradeRepresentations retrieveByUri(RestbucksUri gradeUri) throws Exception {
        Identifier identifier  = gradeUri.getId();
        
        Grade grade = GradeRepository.current().get(identifier);
        
        if(grade == null) {
            throw new Exception();
        }
        
        return GradeRepresentations.createResponseGradeRepresentation(grade, gradeUri);
    
}
    
}