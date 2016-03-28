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
public class TrueNTHDemographicsJsonExtractor implements TrueNTHDemographicsExtractor<JsonObject> {

    private static SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");

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
    public long extractTrueNTHID(JsonObject data) {

	if (data == null) return -1;

	JsonArray idArray = data.getJsonArray(TrueNTHDemographics.IDENTIFIER.toString());

	if (idArray == null) return -1;

	for (int index = 0; index < idArray.size(); index++) {

	    JsonObject id = idArray.getJsonObject(index);

	    String idType = id.getString(TrueNTHDemographics.IDENTIFIER_LABEL.toString());

	    if (TrueNTHDemographics.IDENTIFIER_TRUENTH_ID.toString().equals(idType)) { return id.getJsonNumber(
		    TrueNTHDemographics.IDENTIFIER_VALUE.toString()).longValue(); }
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
    public String extractTrueNTHUsername(JsonObject data) {

	if (data == null) return null;

	JsonArray idArray = data.getJsonArray(TrueNTHDemographics.IDENTIFIER.toString());

	if (idArray == null) return null;

	for (int index = 0; index < idArray.size(); index++) {

	    JsonObject id = idArray.getJsonObject(index);

	    String idType = id.getString(TrueNTHDemographics.IDENTIFIER_LABEL.toString());

	    if (TrueNTHDemographics.IDENTIFIER_TRUENTH_USERNAME.toString().equals(idType)) { return id.getString(TrueNTHDemographics.IDENTIFIER_VALUE
		    .toString()); }
	}

	return null;
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
     * @see TrueNTHDemographics
     */
    @Override
    public String extractEmail(JsonObject data) {

	if (data == null) return null;

	JsonArray telecoms = data.getJsonArray(TrueNTHDemographics.TELECOM.toString());

	if (telecoms == null) return null;

	for (int index = 0; index < telecoms.size(); index++) {
	    JsonObject telcom = telecoms.getJsonObject(index);
	    String system = telcom.getString(TrueNTHDemographics.TELECOM_SYSTEM.toString());

	    if (TrueNTHDemographics.TELECOM_SYSTEM_EMAIL.toString().equals(system)) { return telcom
		    .getString(TrueNTHDemographics.TELECOM_SYSTEM_VALUE.toString()); }
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
     * @see TrueNTHDemographics
     */
    @Override
    public String extractFirstName(JsonObject data) {

	if (data == null) return null;

	JsonObject name = data.getJsonObject(TrueNTHDemographics.NAME.toString());
	if (name == null) return null;

	JsonString firstName = name.getJsonString(TrueNTHDemographics.NAME_GIVEN.toString());
	return (firstName != null) ? firstName.getString() : null;
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
     * @see TrueNTHDemographics
     */
    @Override
    public String extractLastName(JsonObject data) {

	if (data == null) return null;

	JsonObject name = data.getJsonObject(TrueNTHDemographics.NAME.toString());

	if (name == null) return null;

	JsonString lastName = name.getJsonString(TrueNTHDemographics.NAME_FAMILY.toString());
	return (lastName != null) ? lastName.getString() : null;
    }

    /**
     * Extracts: gender.
     * 
     * <pre>
     * GENDER: { 
     * 	GENDER_CODING: [
     * 		{ 
     * 			<b>GENDER_CODING_CODE: "M"</b>,
     * 			"display": "Male", 
     * 			"system": "http://hl7.org/fhir/v3/AdministrativeGender" 
     * 		} 
     * 	]
     * }
     * </pre>
     * 
     * <p>
     * This method will use the first GENDER_CODING_CODE field inside the data
     * source, if any.
     * </p>
     * 
     * @param data
     *            JSON object, data origin.
     * 
     * @return <ul>
     *         <li>Gender, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     * 
     * @see TrueNTHDemographics
     */
    @Override
    public String extractGender(JsonObject data) {

	if (data == null) return null;

	JsonObject gender = data.getJsonObject(TrueNTHDemographics.GENDER.toString());

	if (gender == null) return null;

	JsonArray genderCodingArray = gender.getJsonArray(TrueNTHDemographics.GENDER_CODING.toString());

	if (genderCodingArray == null) return null;

	for (int index = 0; index < genderCodingArray.size(); index++) {
	    JsonObject genderCoding = genderCodingArray.getJsonObject(index);
	    String code = genderCoding.getString(TrueNTHDemographics.GENDER_CODING_CODE.toString());

	    if (TrueNTHDemographics.GENDER_CODING_CODE_MALE.toString().equals(code)
		    || TrueNTHDemographics.GENDER_CODING_CODE_FEMALE.toString().equals(code)) return code;
	}

	return null;
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
    public URL extractPhotoUrl(JsonObject data) {

	if (data == null) return null;

	JsonArray photo = data.getJsonArray(TrueNTHDemographics.PHOTO.toString());

	if (photo == null || photo.isEmpty()) return null;

	try {
	    return new URL(photo.getJsonObject(0).getString(TrueNTHDemographics.PHOTO_URL.toString()));
	} catch (Exception ex) {
	    return null;
	}

    }

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
    public Calendar extractBirthday(JsonObject data) {

	Calendar birthday = Calendar.getInstance();

	if (data == null) return birthday;

	JsonString date = data.getJsonString(TrueNTHDemographics.BIRTH_DATE.toString());

	if (date == null) return birthday;

	try {
	    birthday.setTime(birthdayFormat.parse(date.getString()));
	    return birthday;
	} catch (ParseException e) {
	    return birthday;
	}
    }

}
