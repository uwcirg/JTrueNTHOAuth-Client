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
 * @since 0.5 Sep 21, 2015
 * @version 1.0
 */
public class TrueNTHOAuthConfig extends OAuthConfig {

    /**
     * URL that receives the access token requests.
     * 
     * @since 0.5
     */
    private final String accessTokenEndpoint;

    /**
     * The redirection URL where users authenticate.
     * 
     * @since 0.5
     */
    private final String baseAuthorizationURL;

    /**
     * Constructor.
     * 
     * @since 0.5
     * @param key
     *            APP key, distributed by CS.
     * @param secret
     *            APP secret, distributed by CS.
     * @param accessTokenEndpointURL
     *            URL that receives the access token requests.
     * @param baseAuthorizationURL
     *            The redirection URL where users authenticate.
     * @param callback
     *            Point where to redirect users after receiving a code to
     *            request access tokens.
     * @param signatureType
     *            Type of signature, to be used when issuing requests.
     * @param scope
     *            Authorization scope.
     * @param stream
     */
    public TrueNTHOAuthConfig(String key, String secret, String accessTokenEndpointURL, String baseAuthorizationURL, String callback,
	    SignatureType signatureType, String scope, OutputStream stream) {

	super(key, secret, callback, signatureType, scope, stream);
	this.accessTokenEndpoint = accessTokenEndpointURL;
	this.baseAuthorizationURL = baseAuthorizationURL;

    }

    public String getAccessTokenEndpoint() {

	return accessTokenEndpoint;
    }

    public String getBaseAuthorizationURL() {

	return baseAuthorizationURL;
    }

}
