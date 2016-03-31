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

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

/**
 * JSON demographic information extractor.
 *
 * @author Victor de Lima Soares
 * @since Mar 25, 2016
 */
public class SSDemographicsExtractorJson implements SSDemographicsExtractor<JsonObject> {

    /**
     * Extracts: birthday.
     *
     * <pre>
     * BIRTH_DATE: "yyyy-mm-dd"
     * </pre>
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>Birthday, if it can be extracted;</li>
     *         <li>new date instance (January 1, 1970), otherwise.</li>
     *         </ul>
     */
    @Override
    public Calendar extractBirthday(final JsonObject data) {

	final Calendar birthday = Calendar.getInstance();

	if (data == null) { return birthday; }

	final JsonString date = data.getJsonString(SSDemographicsProtocolProperties.BIRTH_DATE.toString());

	if (date == null) { return birthday; }

	try {
	    SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");
	    birthday.setTime(birthdayFormat.parse(date.getString()));
	    return birthday;
	} catch (final ParseException e) {
	    return birthday;
	}
    }

    /**
     * Extracts: email address.
     *
     * Input format:
     *
     * <pre>
     * TELECOM: [
     * 	{
     * 		TELECOM_SYSTEM: TELECOM_SYSTEM_EMAIL,
     * 		<b>TELECOM_SYSTEM_VALUE: "bob25mary@gmail.com"</b>
     * 	}
     * ]
     * </pre>
     *
     * @param data
     *            JSON object, data origin.
     *
     *            <p>
     *            This method will use the first
     *            TELECOM_SYSTEM=TELECOM_SYSTEM_EMAIL field inside the data
     *            source, if any.
     *            </p>
     *
     * @return <ul>
     *         <li>Email address, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     *
     * @see SSDemographicsProtocolProperties
     */
    @Override
    public String extractEmail(final JsonObject data) {

	if (data == null) { return null; }

	final JsonArray telecoms = data.getJsonArray(SSDemographicsProtocolProperties.TELECOM.toString());

	if (telecoms == null) { return null; }

	for (int index = 0; index < telecoms.size(); index++) {
	    final JsonObject telcom = telecoms.getJsonObject(index);
	    final String system = telcom.getString(SSDemographicsProtocolProperties.TELECOM_SYSTEM.toString());

	    if (SSDemographicsProtocolProperties.TELECOM_SYSTEM_EMAIL.toString().equals(system)) { return telcom
		    .getString(SSDemographicsProtocolProperties.TELECOM_SYSTEM_VALUE.toString()); }
	}

	return null;
    }

    /**
     * Extracts: first name.
     *
     * Input format:
     *
     * <pre>
     * NAME: {
     * 	FAMILY: "Truenth",
     * 	<b>NAME_GIVEN: "Bob"</b>
     * }
     * </pre>
     *
     * <p>
     * This method will use the first NAME_GIVEN field inside the data source,
     * if any.
     * </p>
     *
     * @param data
     *            JSON object, data origin.
     *
     * @return <ul>
     *         <li>First name, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     *
     * @see SSDemographicsProtocolProperties
     */
    @Override
    public String extractFirstName(final JsonObject data) {

	if (data == null) { return null; }

	final JsonObject name = data.getJsonObject(SSDemographicsProtocolProperties.NAME.toString());
	if (name == null) { return null; }

	final JsonString firstName = name.getJsonString(SSDemographicsProtocolProperties.NAME_GIVEN.toString());
	return (firstName != null) ? firstName.getString() : null;
    }

    /**
     * Extracts: gender.
     *
     * <pre>
     * GENDER: "gender" : "<code>" // male | female | other | unknown
     * </pre>
     *
     * @param data
     *            JSON object, data origin.
     *
     * @return <ul>
     *         <li>Gender (male | female), if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     *
     * @see SSDemographicsProtocolProperties
     */
    @Override
    public String extractGender(final JsonObject data) {

	if (data == null) { return null; }

	final JsonString rawGender = data.getJsonString(SSDemographicsProtocolProperties.GENDER.toString());

	if (rawGender == null) { return null; }
	
	String gender = rawGender.getString();
	
	 if (SSDemographicsProtocolProperties.GENDER_MALE.toString().equals(gender)
		    || SSDemographicsProtocolProperties.GENDER_FEMALE.toString().equals(gender)) { return gender; }
	 
	 return null;
    }

    /**
     * Extracts: last name.
     *
     * Input format:
     *
     * <pre>
     * NAME: {
     * 	<b>FAMILY: "Truenth"</b>,
     * 	NAME_GIVEN: "Bob"
     * }
     * </pre>
     *
     * <p>
     * This method will use the first NAME_FAMILY field inside the data source,
     * if any.
     * </p>
     *
     * @param data
     *            JSON object, data origin.
     *
     * @return <ul>
     *         <li>Last name, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     *
     * @see SSDemographicsProtocolProperties
     */
    @Override
    public String extractLastName(final JsonObject data) {

	if (data == null) { return null; }

	final JsonObject name = data.getJsonObject(SSDemographicsProtocolProperties.NAME.toString());

	if (name == null) { return null; }

	final JsonString lastName = name.getJsonString(SSDemographicsProtocolProperties.NAME_FAMILY.toString());
	return (lastName != null) ? lastName.getString() : null;
    }

    /**
     * Extracts: TrueNTH profile picture URL.
     *
     * <pre>
     * PHOTO: {
     * 	PHOTO_URL: url
     * }
     * </pre>
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>Image URL, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     */
    @Override
    public URL extractPhotoUrl(final JsonObject data) {

	if (data == null) { return null; }

	final JsonArray photo = data.getJsonArray(SSDemographicsProtocolProperties.PHOTO.toString());

	if ((photo == null) || photo.isEmpty()) { return null; }

	try {
	    return new URL(photo.getJsonObject(0).getString(SSDemographicsProtocolProperties.PHOTO_URL.toString()));
	} catch (final Exception ex) {
	    return null;
	}

    }

    /**
     * Extracts: TrueNTH ID.
     *
     * Input format:
     *
     * <pre>
     * IDENTIFIER: [
     * 	 {
     * 		IDENTIFIER_LABEL: IDENTIFIER_TRUENTH_ID,
     * 		<b>IDENTIFIER_VALUE: 9999</b>
     * 	 },
     * 	 {
     * 		IDENTIFIER_LABEL: IDENTIFIER_TRUENTH_USERNAME,
     * 		IDENTIFIER_VALUE: "username"
     * 	 }
     * ]
     * </pre>
     *
     * Input Example:
     *
     * <pre>
     * "identifier": [
     *       {
     *           "label": "Truenth identifier",
     *           <b>"value": 10015</b>
     *       },
     *       {
     *           "label": "Truenth username",
     *           "value": "Bob Truenth"
     *       }
     *  ]
     * </pre>
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>TrueNTH ID, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     */
    @Override
    public long extractTrueNTHID(final JsonObject data) {

	if (data == null) { return -1; }

	final JsonArray idArray = data.getJsonArray(SSDemographicsProtocolProperties.IDENTIFIER.toString());

	if (idArray == null) { return -1; }

	for (int index = 0; index < idArray.size(); index++) {

	    final JsonObject id = idArray.getJsonObject(index);

	    final String idType = id.getString(SSDemographicsProtocolProperties.IDENTIFIER_LABEL.toString());

	    if (SSDemographicsProtocolProperties.IDENTIFIER_TRUENTH_ID.toString().equals(idType)) { return id.getJsonNumber(
		    SSDemographicsProtocolProperties.IDENTIFIER_VALUE.toString()).longValue(); }
	}

	return -1;
    }

    /**
     * Extracts: TrueNTH Username.
     *
     * Input format:
     *
     * <pre>
     * IDENTIFIER: [
     * 	 {
     * 		IDENTIFIER_LABEL: IDENTIFIER_TRUENTH_ID,
     * 		IDENTIFIER_VALUE: 9999
     * 	 },
     * 	 {
     * 		IDENTIFIER_LABEL: IDENTIFIER_TRUENTH_USERNAME,
     * 		<b>IDENTIFIER_VALUE: "username"</b>
     * 	 }
     * ]
     * </pre>
     *
     * Input Example:
     *
     * <pre>
     * "identifier": [
     *       {
     *           "label": "Truenth identifier",
     *           <b>"value": 10015</b>
     *       },
     *       {
     *           "label": "Truenth username",
     *           <b>"value": "Bob Truenth"</b>
     *       }
     *  ]
     * </pre>
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>TrueNTH username, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     */
    @Override
    public String extractTrueNTHUsername(final JsonObject data) {

	if (data == null) { return null; }

	final JsonArray idArray = data.getJsonArray(SSDemographicsProtocolProperties.IDENTIFIER.toString());

	if (idArray == null) { return null; }

	for (int index = 0; index < idArray.size(); index++) {

	    final JsonObject id = idArray.getJsonObject(index);

	    final String idType = id.getString(SSDemographicsProtocolProperties.IDENTIFIER_LABEL.toString());

	    if (SSDemographicsProtocolProperties.IDENTIFIER_TRUENTH_USERNAME.toString().equals(idType)) { return id
		    .getString(SSDemographicsProtocolProperties.IDENTIFIER_VALUE.toString()); }
	}

	return null;
    }

}
