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

import java.net.MalformedURLException;
import java.net.URL;
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
    private boolean  gender;
    private URL      photoUrl;
    private String   trueNTHUsername;
    private long     trueNTHID;

    public Calendar getBirthday() {

	return birthday;
    }

    public void setBirthday(final Calendar birthday) {

	this.birthday = birthday;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(final String email) {

	this.email = email;
    }

    public String getFirstName() {

	return firstName;
    }

    public void setFirstName(final String firstName) {

	this.firstName = firstName;
    }

    public String getLastName() {

	return lastName;
    }

    public void setLastName(final String lastName) {

	this.lastName = lastName;
    }

    /**
     * Return the gender as a boolean value.
     *
     * @return <ul>
     *         <li>true, if its male;</li>
     *         <li>false, otherwise.</li>
     *         </ul>
     */
    public boolean getGender() {

	return gender;
    }

    /**
     * Sets the gender attribute.
     *
     * <p>
     * The gender is represented as a boolean flag: true for male, false for
     * female.
     * </p>
     *
     * @param gender
     *            ("male"|"female").
     */
    public void setGender(final String gender) {

	final String male = SSDemographicsProtocolProperties.GENDER_MALE.toString();

	this.gender = male.equals(gender);
    }

    /**
     * Sets the gender attribute.
     *
     * <p>
     * The gender is represented as a boolean flag: true for male, false for
     * female.
     * </p>
     *
     * @param isMale
     *            True for male, false for female.
     */
    public void setGender(final boolean isMale) {

	gender = isMale;
    }

    public URL getPhotoUrl() {

	return photoUrl;
    }

    public void setPhotoUrl(final URL url) {

	photoUrl = url;
    }

    public void setPhotoUrl(final String url) throws MalformedURLException {

	setPhotoUrl((url == null) ? null : new URL(url));
    }

    public String getTrueNTHUsername() {

	return trueNTHUsername;
    }

    public void setTrueNTHUsername(final String trueNTHUsername) {

	this.trueNTHUsername = trueNTHUsername;
    }

    public long getTrueNTHID() {

	return trueNTHID;
    }

    public void setTrueNTHID(final long trueNTHID) {

	this.trueNTHID = trueNTHID;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = (prime * result) + ((birthday == null) ? 0 : birthday.hashCode());
	result = (prime * result) + ((email == null) ? 0 : email.hashCode());
	result = (prime * result) + ((firstName == null) ? 0 : firstName.hashCode());
	result = (prime * result) + (gender ? 1231 : 1237);
	result = (prime * result) + ((lastName == null) ? 0 : lastName.hashCode());
	result = (prime * result) + ((photoUrl == null) ? 0 : photoUrl.hashCode());
	result = (prime * result) + (int) (trueNTHID ^ (trueNTHID >>> 32));
	result = (prime * result) + ((trueNTHUsername == null) ? 0 : trueNTHUsername.hashCode());
	return result;
    }

    @Override
    public boolean equals(final Object obj) {

	if (this == obj) { return true; }
	if (obj == null) { return false; }
	if (!(obj instanceof SSDemographics)) { return false; }
	final SSDemographics other = (SSDemographics) obj;
	if (birthday == null) {
	    if (other.birthday != null) { return false; }
	} else if (!birthday.equals(other.birthday)) { return false; }
	if (email == null) {
	    if (other.email != null) { return false; }
	} else if (!email.equals(other.email)) { return false; }
	if (firstName == null) {
	    if (other.firstName != null) { return false; }
	} else if (!firstName.equals(other.firstName)) { return false; }
	if (gender != other.gender) { return false; }
	if (lastName == null) {
	    if (other.lastName != null) { return false; }
	} else if (!lastName.equals(other.lastName)) { return false; }
	if (photoUrl == null) {
	    if (other.photoUrl != null) { return false; }
	} else if (!photoUrl.equals(other.photoUrl)) { return false; }
	if (trueNTHID != other.trueNTHID) { return false; }
	if (trueNTHUsername == null) {
	    if (other.trueNTHUsername != null) { return false; }
	} else if (!trueNTHUsername.equals(other.trueNTHUsername)) { return false; }
	return true;
    }

}
