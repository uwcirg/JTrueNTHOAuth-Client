package edu.uw.cirg.truenth.oauth.model.definitions;

/**
 * Provides grant types supported by this API and our CS.
 * 
 * @author Victor de Lima Soares
 * @since Sep 11, 2015
 */
public enum TrueNTHGrantType {
    CODE("authorization_code");

    /**
     * Name of "grant type" parameter.
     */
    public static final String PARAMETER = "grant_type";

    private String	     grantType;

    private TrueNTHGrantType(String grantType) {

	this.grantType = grantType;
    }

    public String toString() {

	return grantType;
    }

    public static String getParameterName() {

	return PARAMETER;
    }
}
