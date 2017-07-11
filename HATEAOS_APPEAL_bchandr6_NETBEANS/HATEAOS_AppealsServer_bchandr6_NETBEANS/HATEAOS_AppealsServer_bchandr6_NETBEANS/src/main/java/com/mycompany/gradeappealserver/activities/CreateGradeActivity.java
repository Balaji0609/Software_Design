package com.mycompany.gradeappealserver.activities;

/**
 *
 * @author Balaji Chandrasekaran
 */
import com.mycompany.gradeappealserver.model.Grade;
import com.mycompany.gradeappealserver.model.Identifier;
import com.mycompany.gradeappealserver.representations.GradeRepresentations;
import com.mycompany.gradeappealserver.representations.Link;
import com.mycompany.gradeappealserver.representations.Representations;
import com.mycompany.gradeappealserver.representations.RestbucksUri;
import com.mycompany.gradeappealserver.repositories.GradeRepository;

public class CreateGradeActivity {

    public GradeRepresentations create(Grade grade, RestbucksUri requestUri) {
                
        Identifier identifier = GradeRepository.current().store(grade);
        
        RestbucksUri gradeUri = new RestbucksUri(requestUri.getBaseUri() + "/grades/" + identifier.toString());

        return new GradeRepresentations(grade,
                new Link(Representations.SELF_REL_VALUE, gradeUri));
    }
}
