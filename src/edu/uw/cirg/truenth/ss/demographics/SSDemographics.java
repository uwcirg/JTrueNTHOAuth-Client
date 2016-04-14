/*******************************************************************************
 * Copyright (c) 2015, 2016, University of Washington, School of Nursing
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *******************************************************************************/
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
    private String   lastName;
    private String   gender;
    private String   photoUrl;
    private String   trueNTHUsername;
    private long     trueNTHID;

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
