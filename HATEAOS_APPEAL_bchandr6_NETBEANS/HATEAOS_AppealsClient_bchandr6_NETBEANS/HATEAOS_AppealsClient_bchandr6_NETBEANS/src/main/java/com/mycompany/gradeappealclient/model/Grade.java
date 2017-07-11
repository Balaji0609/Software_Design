package com.mycompany.gradeappealclient.model;

import com.mycompany.gradeappealclient.representations.Representations;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Balaji Chandrasekaran
 */
@XmlRootElement(name="grade", namespace = Representations.RESTBUCKS_NAMESPACE)
public class Grade {

    @XmlElement(name = "gradeLetter", namespace = Representations.RESTBUCKS_NAMESPACE)
    public char letterGrade;
    
    public Grade(){
        
    }
    
    public Grade(char g)
    {

     this.letterGrade = g;
}
   
    public void setValue(char gradeLetter)
    {
        this.letterGrade=gradeLetter;
    }
    
   public char getValue()
    {
        return letterGrade;
    }
}