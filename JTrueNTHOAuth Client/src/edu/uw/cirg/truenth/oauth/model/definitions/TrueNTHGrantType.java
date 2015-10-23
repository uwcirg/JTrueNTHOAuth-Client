package edu.uw.cirg.truenth.oauth.model.definitions;

/**
 * Provides grant types supported by this API and our CS.
 * 
 * @author Victor de Lima Soares
 * @since 0.5 Sep 11, 2015
 * @version 2.0
 *
 */
public enum TrueNTHGrantType {
    CODE("authorization_code");

    /**
     * Name of "grant type" parameter.
     * 
     * @since 1.5
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
