/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.samples.crud.jaxb.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fcalliss
 */
@XmlRootElement
@XmlType(propOrder={
    "studentID",
    "studentName",
    "midTerm",
    "midTermF",
    "assignment",
    "assignmentF",
    "quiz",
    "quizF",
    "classLabs",
    "classLabsF"})
//    "midTermW",
//    "assignmentW",
//    "quizW",
//    "classLabsW"
public class Student {
    
    private static final Logger LOG = LoggerFactory.getLogger(Student.class);
    
    private String      StudentName;
    private int        StudentID;
    
    private int         MidTerm;
    private int         Assignment;
    private int         Quiz;
    private int         ClassLabs;
    
    private String      MidTermF;
    private String      AssignmentF;
    private String      QuizF;
    private String      ClassLabsF;
    
//    private static int         MidTermW = 0;
//    private static int         AssignmentW = 0;
//    private static int         QuizW = 0;
//    private static int         ClassLabsW = 0;

    public Student() {
        LOG.info("Creating an Appointment object");
    }
       
//    public int getMidTermW() {
//        return MidTermW;
//    }
//     
//      
//    public int getAssignmentW() {
//        return MidTermW;
//    }
//
//    public int getQuizW() {
//        return MidTermW;
//    }
//    
//    public int getClassLabsW() {
//        return MidTermW;
//    }
//
    public String getStudentName() {
        return StudentName;
    }
    
//    @XmlElement
//    public void setMidTermW(int MidTermW) {
//        LOG.info("Setting the id to {}", MidTermW);
//        
//        if(Student.MidTermW == 0)
//            Student.MidTermW = MidTermW;
//        
//        LOG.debug("The updated appointment = {}", Student.MidTermW);
//    }
//    
//    @XmlElement
//    public void setAssignmentW(int AssignmentW) {
//        LOG.info("Setting the id to {}", AssignmentW);
//        
//        if(Student.AssignmentW == 0)
//            Student.AssignmentW = AssignmentW;
//        
//        LOG.debug("The updated appointment = {}", Student.AssignmentW);
//    }
//    
//    @XmlElement
//    public void setQuizW(int QuizW) {
//        LOG.info("Setting the id to {}", QuizW);
//        
//        if(Student.QuizW == 0)
//            Student.QuizW = QuizW;
//        
//        LOG.debug("The updated appointment = {}", Student.QuizW);
//    }
//    
//    @XmlElement
//    public void setClassLabsW(int ClassLabsW) {
//        LOG.info("Setting the id to {}", ClassLabsW);
//        
//        if(Student.ClassLabsW == 0)
//            Student.ClassLabsW = ClassLabsW;
//        
//        LOG.debug("The updated appointment = {}", Student.ClassLabsW);
//    }
//    




    @XmlElement
    public void setStudentName(String StudentName) {
        LOG.info("Setting the title to {}", StudentName);
        
        this.StudentName = StudentName;
        
        LOG.debug("The updated appointment = {}", this);
    }

    public int getStudentID() {
        return StudentID;
    }

    @XmlAttribute
    public void setStudentID(int StudentID) {
        LOG.info("Setting the priority to {}", StudentID);
        
        this.StudentID = StudentID;
        
        LOG.debug("The updated appointment = {}", this);
    }

    
    
    
    public int getMidTerm() {
        return MidTerm;
    }

    @XmlElement
    public void setMidTerm(int MidTerm) {
        LOG.info("Setting the id to {}", MidTerm);
        
        this.MidTerm = MidTerm;
        
        LOG.debug("The updated appointment = {}", this);
    }
    
    
    
    
   public int getAssignment() {
        return Assignment;
    }

    @XmlElement
    public void setAssignment(int Assignment) {
        LOG.info("Setting the id to {}", Assignment);
        
        this.Assignment = Assignment;
        
        LOG.debug("The updated appointment = {}", this);
    }
    
    
    
    
    public int getQuiz() {
        return Quiz;
    }

    @XmlElement
    public void setQuiz(int Quiz) {
        LOG.info("Setting the id to {}", Quiz);
        
        this.Quiz = Quiz;
        
        LOG.debug("The updated appointment = {}", this);
    }
    
    
    
    
    public int getClassLabs() {
        return ClassLabs;
    }

    @XmlElement
    public void setClassLabs(int ClassLabs) {
        LOG.info("Setting the id to {}", ClassLabs);
        
        this.ClassLabs = ClassLabs;
        
        LOG.debug("The updated appointment = {}", this);
    }
    
    
    
    
    public String getMidTermF() {
        return MidTermF;
    }

    @XmlElement
    public void setMidTermF(String MidTermF) {
        LOG.info("Setting the id to {}", MidTermF);
        
        this.MidTermF = MidTermF;
        
        LOG.debug("The updated appointment = {}", this);
    }
    
    
    
    public String getAssignmentF() {
        return AssignmentF;
    }

    @XmlElement
    public void setAssignmentF(String AssignmentF) {
        LOG.info("Setting the id to {}", AssignmentF);
        
        this.AssignmentF = AssignmentF;
        
        LOG.debug("The updated appointment = {}", this);
    }
    
    
    
    
    public String getQuizF() {
        return QuizF;
    }

    @XmlElement
    public void setQuizF(String QuizF) {
        LOG.info("Setting the id to {}", QuizF);
        
        this.QuizF = QuizF;
        
        LOG.debug("The updated appointment = {}", this);
    }
    
    public String getClassLabsF() {
        return ClassLabsF;
    }

    @XmlElement
    public void setClassLabsF(String ClassLabsF) {
        LOG.info("Setting the id to {}", ClassLabsF);
        
        this.ClassLabsF = ClassLabsF;
        
        LOG.debug("The updated appointment = {}", this);
    }
    
    @Override
    public String toString() {
        return "Student{" + "name=" + StudentName + ", id=" + StudentID + ", Marks = " + MidTerm + " " + Assignment + " " +Quiz + " " +ClassLabs + " " + ", FeedBack = " + MidTermF + " " + AssignmentF + " " +QuizF + " " +ClassLabsF + " "+", Weightage = "+ " " +'}';
    }
    
}
