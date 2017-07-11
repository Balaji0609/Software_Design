package com.mycompany.gradeappealserver.model;

/**
 *
 * @author Balaji Chandrasekaran
 */
import javax.xml.bind.annotation.XmlEnumValue;


public enum AppealStatus {
    @XmlEnumValue(value="Approved")
    APPROVED,
    @XmlEnumValue(value="Pending")
    PENDING, 
    @XmlEnumValue(value="Rejected")
    REJECTED, 
    
}