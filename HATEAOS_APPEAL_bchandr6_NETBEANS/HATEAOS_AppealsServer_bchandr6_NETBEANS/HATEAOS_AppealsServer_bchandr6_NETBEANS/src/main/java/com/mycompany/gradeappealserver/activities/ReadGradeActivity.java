package com.mycompany.gradeappealserver.activities;


import com.mycompany.gradeappealserver.model.Grade;
import com.mycompany.gradeappealserver.model.Identifier;
import com.mycompany.gradeappealserver.repositories.GradeRepository;
import com.mycompany.gradeappealserver.representations.GradeRepresentations;
import com.mycompany.gradeappealserver.representations.RestbucksUri;

/**
 *
 * @author Balaji Chandrasekaran
 */
public class ReadGradeActivity 
{
    
        public GradeRepresentations retrieveByUri(RestbucksUri gradeUri) throws Exception 
        {
        Identifier identifier  = gradeUri.getId();
        
        Grade grade = GradeRepository.current().get(identifier);
        
        if(grade == null) {
            throw new Exception();
        }
        
        return GradeRepresentations.createResponseGradeRepresentation(grade, gradeUri);
    
}
     
}