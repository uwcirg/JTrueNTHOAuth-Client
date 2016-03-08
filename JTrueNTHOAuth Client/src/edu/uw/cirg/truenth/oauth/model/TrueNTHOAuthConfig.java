package edu.uw.cirg.truenth.oauth.model;

import java.io.OutputStream;

import org.scribe.model.OAuthConfig;
import org.scribe.model.SignatureType;

/**
 * Extension of OAuthConfig to include OAuth providers' server configuration.
 * 
 * <p>
 * This class was designed to use in multithreading environments, it is tread
 * safe <b>after creation</b>. It is immutable and does not rely on locks.
 * </p>
 * 
 * @author Victor de Lima Soares
 * @since Sep 21, 2015
 */
public class TrueNTHOAuthConfig extends OAuthConfig {

    /**
     * URL that receives the access token requests.
     */
    private final String accessTokenEndpoint;

    /**
     * The redirection URL where users authenticate.
     */
    private final String baseAuthorizationURL;

    /**
     * Central Services' roles URL.
     */
    private final String rolesURL;

    /**
     * Resource URL.
     */
    private final String resourceURL;

    /**
     * Central Services base URL.
     * <p>
     * This URL points to central services base URL, it should not be used for
     * OAuth operation, but for fetching static resources, such as css. It is
     * mainly used for templates.
     * </p>
     */
    private final String baseURL;

    /**
     * Constructor.
     * 
     * @param key
     *            APP key, distributed by CS.
     * @param secret
     *            APP secret, distributed by CS.
     * @param accessTokenEndpointURL
     *            URL that receives the access token requests.
     * @param baseAuthorizationURL
     *            The redirection URL where users authenticate.
     * @param baseURL
     *            Central Services' base URL.
     * @param resourceURL
     *            Central Services' resource URL (protected API base).
     * @param rolesURL
     *            Central Services' roles URL.
     * @param callback
     *            Point where to redirect users after receiving a code to
     *            request access tokens.
     * @param signatureType
     *            Type of signature, to be used when issuing requests.
     * @param scope
     *            Authorization scope.
     * @param stream
     */
    public TrueNTHOAuthConfig(String key, String secret, String accessTokenEndpointURL, String baseAuthorizationURL, String baseURL,
	    String resourceURL, String rolesURL, String callback, SignatureType signatureType, String scope, OutputStream stream) {

	super(key, secret, callback, signatureType, scope, stream);
	this.accessTokenEndpoint = accessTokenEndpointURL;
	this.baseAuthorizationURL = baseAuthorizationURL;

	this.baseURL = baseURL;
	this.resourceURL = resourceURL;
	this.rolesURL = rolesURL;

    }

    public String getAccessTokenEndpoint() {

	return accessTokenEndpoint;
    }

    public String getBaseAuthorizationURL() {

	return baseAuthorizationURL;
    }

    /**
     * Returns the configured roles URL.
     * 
     * @return Roles URL.
     */
    public String getRolesURL() {

	return rolesURL;
    }

    /**
     * Returns the configured resource URL (API base).
     * 
     * @return Resource URL.
     */
    public String getResourceURL() {

	return resourceURL;
    }

    /**
     * Returns the configured Central Services base URL.
     * 
     * <p>
     * This URL points to central services base URL, it should not be used for
     * OAuth operation, but for fetching static resources, such as css. It is
     * mainly used for templates.
     * </p>
     * 
     * @return Configured Central Services base URL.
     */
    public String getBaseURL() {

	return baseURL;
    }

}
