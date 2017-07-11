package com.mycompany.gradeappealclient.activities;

/**
 *
 * @author Balaji Chandrasekaran
 */
import com.mycompany.gradeappealclient.model.Grade;
import com.mycompany.gradeappealclient.model.Identifier;
import com.mycompany.gradeappealclient.representations.GradeRepresentations;
import com.mycompany.gradeappealclient.representations.Link;
import com.mycompany.gradeappealclient.representations.Representations;
import com.mycompany.gradeappealclient.representations.RestbucksUri;
import com.mycompany.gradeappealclient.repositories.GradeRepository;

public class CreateGradeActivity {

    public GradeRepresentations create(Grade grade, RestbucksUri requestUri)
    {
                     
        Identifier identifier = GradeRepository.current().store(grade);
        
        RestbucksUri gradeUri = new RestbucksUri(requestUri.getBaseUri() + "/grades/" + identifier.toString());

        return new GradeRepresentations(grade,
                new Link(Representations.SELF_REL_VALUE, gradeUri));
    
  
    }
}
