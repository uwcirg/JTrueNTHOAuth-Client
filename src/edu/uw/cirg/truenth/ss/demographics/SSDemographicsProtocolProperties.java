package edu.uw.cirg.truenth.ss.demographics;

/**
 * Constants for TrueNTH (SS) demographics API, protocol fields.
 *
 * @author Victor de Lima Soares
 * @since Mar 25, 2016
 */
public enum SSDemographicsProtocolProperties {

    BIRTH_DATE("birthDate"),
    COMMUNICATION("communication"),
    GENDER("gender"),
    GENDER_CODING("coding"),
    GENDER_CODING_CODE("code"),
    GENDER_CODING_CODE_FEMALE("F"),
    GENDER_CODING_CODE_MALE("M"),
    GENDER_CODING_DISPLAY("display"),
    GENDER_CODING_DISPLAY_FEMALE("female"),
    GENDER_CODING_DISPLAY_MALE("male"),
    IDENTIFIER("identifier"),
    IDENTIFIER_LABEL("label"),
    IDENTIFIER_TRUENTH_ID("Truenth identifier"),
    IDENTIFIER_TRUENTH_USERNAME("Truenth username"),
    IDENTIFIER_VALUE("value"),
    NAME("name"),
    NAME_FAMILY("family"),
    NAME_GIVEN("given"),
    PHOTO("photo"),
    PHOTO_URL("url"),
    TELECOM("telecom"),
    TELECOM_SYSTEM("system"),
    TELECOM_SYSTEM_EMAIL("email"),
    TELECOM_SYSTEM_VALUE("value");

    private String propertyName;

    /**
     * Constructor.
     *
     * @param propName
     *            Property name.
     */
    private SSDemographicsProtocolProperties(final String propName) {

	propertyName = propName;
    }

    /**
     * Returns the property name represented by the enum constant.
     */
    @Override
    public String toString() {

	return propertyName;
    }

}
