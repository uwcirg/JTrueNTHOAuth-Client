package edu.uw.cirg.truenth.ss.demographics;

import java.util.Calendar;

/**
 * Basic representation for TrueNTH (SS) demographics.
 *
 * <p>
 * This class provides a basic bean to hold demographic information, as provided
 * by SS. For most purposes, this class should be enough, but a local
 * interpretation of such information might be necessary.
 * </p>
 * 
 * @author Victor de Lima Soares
 * @since Apr 14, 2016
 *
 */
public class SSDemographics {

    private Calendar birthday;
    private String   email;
    private String   firstName;
    private  String   lastName;
    private String   gender;
    private String   photoUrl;
    private String   trueNTHUsername;
    private long trueNTHID;
    
    public Calendar getBirthday() {
    
        return birthday;
    }
    
    public void setBirthday(Calendar birthday) {
    
        this.birthday = birthday;
    }
    
    public String getEmail() {
    
        return email;
    }
    
    public void setEmail(String email) {
    
        this.email = email;
    }
    
    public String getFirstName() {
    
        return firstName;
    }
    
    public void setFirstName(String firstName) {
    
        this.firstName = firstName;
    }
    
    public String getLastName() {
    
        return lastName;
    }
    
    public void setLastName(String lastName) {
    
        this.lastName = lastName;
    }
    
    public String getGender() {
    
        return gender;
    }
    
    public void setGender(String gender) {
    
        this.gender = gender;
    }
    
    public String getPhotoUrl() {
    
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
    
        this.photoUrl = photoUrl;
    }
    
    public String getTrueNTHUsername() {
    
        return trueNTHUsername;
    }
    
    public void setTrueNTHUsername(String trueNTHUsername) {
    
        this.trueNTHUsername = trueNTHUsername;
    }
    
    public long getTrueNTHID() {
    
        return trueNTHID;
    }
    
    public void setTrueNTHID(long trueNTHID) {
    
        this.trueNTHID = trueNTHID;
    }
    
}
