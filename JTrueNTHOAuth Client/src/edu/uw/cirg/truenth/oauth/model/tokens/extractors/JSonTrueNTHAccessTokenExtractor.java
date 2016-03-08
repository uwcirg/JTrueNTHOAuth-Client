package edu.uw.cirg.truenth.oauth.model.tokens.extractors;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;

import org.scribe.utils.Preconditions;

import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHOAuthConstants;
import edu.uw.cirg.truenth.oauth.model.tokens.TrueNTHAccessToken;

/**
 * TrueNTH access token extractor for JsonObject instances.
 * 
 * @author Victor de Lima Soares
 * @since Oct 22, 2015
 *
 */
public class JSonTrueNTHAccessTokenExtractor implements TrueNTHAccessTokenExtractor<JsonObject> {

    /**
     * Reads a TrueNTH access token from a JsonObject instance.
     * 
     * @param data
     *            Data containing the token.
     * @return Token extracted.
     * @throws IllegalArgumentException
     *             If an error message is received or when the data is
     *             incomplete.
     */
    @Override
    public TrueNTHAccessToken extract(JsonObject data) {

	JsonString error = data.getJsonString(TrueNTHOAuthConstants.ERROR);
	if (error != null) { throw new IllegalArgumentException("Error: " + error.toString() + "\n" + data.toString()); }
	try {
	    
	    String accessToken = data.getString(TrueNTHAccessToken.Parameters.ACCESS_TOKEN.toString()).trim();
	    long expiresIn = data.getJsonNumber(TrueNTHAccessToken.Parameters.EXPIRES_IN.toString()).longValue();
	    String refreshToken = data.getString(TrueNTHAccessToken.Parameters.REFRESH_TOKEN.toString()).trim();
	    String scope = data.getString(TrueNTHAccessToken.Parameters.SCOPE.toString()).trim();
	    String tokenType = data.getString(TrueNTHAccessToken.Parameters.TOKEN_TYPE.toString()).trim();

	    return new TrueNTHAccessToken(accessToken, expiresIn, refreshToken, scope, tokenType);
	} catch (NullPointerException e) {
	    throw new IllegalArgumentException("Error: data incomplete: \n" + data.toString());
	}
    }

    /**
     * Reads a TrueNTH access token from a String instance, JSon formated.
     * 
     * <p>
     * This method should expects a JSon formated string.
     * </p>
     * 
     * @param data
     *            Data containing the token.
     * @return Token extracted.
     */
    @Override
    public TrueNTHAccessToken extract(String data) {

	Preconditions.checkEmptyString(data, "No data to extract");
	JsonObject json = Json.createReader(new StringReader(data)).readObject();
	return extract(json);
    }
}
