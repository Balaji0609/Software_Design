package com.mycompany.gradeappealclient.activities;


import com.mycompany.gradeappealclient.model.Identifier;
import com.mycompany.gradeappealclient.representations.RestbucksUri;
import com.mycompany.gradeappealclient.representations.GradeRepresentations;
import com.mycompany.gradeappealclient.repositories.GradeRepository;
import com.mycompany.gradeappealclient.model.Grade;

/**
 *
 * @author Balaji Chandrasekaran
 */
public class UpdateGradeActivity {
    public GradeRepresentations update(Grade grade, RestbucksUri gradeUri) throws Exception {
        Identifier gradeIdentifier = gradeUri.getId();

        GradeRepository repository = GradeRepository.current();
        
        if (GradeRepository.current().gradeNotPlaced(gradeIdentifier)) { // Defensive check to see if we have the grade
            throw new Exception();
        }

        if (!gradeCanBeChanged(gradeIdentifier)) {
            throw new Exception();
        }

        Grade storedGrade = repository.get(gradeIdentifier);

        return GradeRepresentations.createResponseGradeRepresentation(storedGrade, gradeUri); 
    }
    
    private boolean gradeCanBeChanged(Identifier identifier) {
        return true;
    }
    
}
