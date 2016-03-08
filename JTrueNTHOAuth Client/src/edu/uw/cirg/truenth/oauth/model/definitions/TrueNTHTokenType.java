package edu.uw.cirg.truenth.oauth.model.definitions;

/**
 * Provides token types supported by this API and our CS.
 * 
 * @author Victor de Lima Soares
 * @since Sep 11, 2015
 */
public enum TrueNTHTokenType {

    BEARER("Bearer");

    private String tokenType;

    private TrueNTHTokenType(String tokenType) {

	this.tokenType = tokenType;
    }

    public String toString() {

	return tokenType;
    }

    public static TrueNTHTokenType getByName(String tokenType) {

	if (TrueNTHTokenType.BEARER.toString().equals(tokenType)) return TrueNTHTokenType.BEARER;

	tokenType = tokenType.trim().toUpperCase();
	return valueOf(tokenType);

    }
}
