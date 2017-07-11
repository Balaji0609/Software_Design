package com.mycompany.gradeappealserver.model;

import java.util.Random;

/**
 *
 * @author Balaji Chandrasekaran
 */
public class Grade {
    
    public char letterGrade;
    
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