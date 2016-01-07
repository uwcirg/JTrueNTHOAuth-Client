package edu.uw.cirg.truenth.oauth;

import org.scribe.model.OAuthConfig;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.ParameterList;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.Preconditions;

import edu.uw.cirg.truenth.oauth.builder.api.TrueNTHOAuthProvider;
import edu.uw.cirg.truenth.oauth.model.TrueNTHOAuthConfig;
import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHGrantType;
import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHTokenType;
import edu.uw.cirg.truenth.oauth.model.tokens.TrueNTHAccessToken;

/**
 * OAuth service.
 * 
 * @author Victor de Lima Soares
 * @since 0.5 Sep 11, 2015
 * @version 4.0
 *
 */
public class TrueNTHOAuthService implements OAuthService {

    /**
     * OAuth version.
     * 
     * @since 0.5
     */
    private static final String	VERSION = "2.0";

    private final TrueNTHOAuthProvider api;
    private final TrueNTHOAuthConfig   config;

    /**
     * Default constructor.
     * 
     * @since 0.5
     * @param trueNTHOAuthProvider
     *            OAuth2.0 api information
     * @param config
     *            OAuth 2.0 configuration param object
     */
    public TrueNTHOAuthService(TrueNTHOAuthProvider trueNTHOAuthProvider, TrueNTHOAuthConfig config) {

	this.api = trueNTHOAuthProvider;
	this.config = config;
	Preconditions.checkValidUrl(config.getCallback(), "Callback URL must be a valid URL.");
    }

    /**
     * Fetches an access token and returns as a token object - Scribe's model.
     * 
     * @since 0.5
     * @param requestToken
     *            This parameter will not be used and can be safely set to null.
     * @param verifier
     *            Code to fetch the access token.
     * @return access token.
     */
    @Override
    public TrueNTHAccessToken getAccessToken(Token requestToken, Verifier verifier) {

	OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint(config));
	request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
	request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
	request.addBodyParameter(TrueNTHGrantType.PARAMETER, TrueNTHGrantType.CODE.toString());
	request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
	request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
	if (config.hasScope()) request.addBodyParameter(OAuthConstants.SCOPE, config.getScope());
	Response response = request.send();
	return (TrueNTHAccessToken) api.getAccessTokenExtractor().extract(response.getBody());
    }

    /**
     * Fetches request token.
     * 
     * @since 0.5
     * @throws UnsupportedOperationException
     *             This operation is not supported by CS; thus, this workflow
     *             shall not be used.
     * @return Nothing will be returned, and an exception will be thrown.
     */
    @Override
    @Deprecated
    public Token getRequestToken() {

	throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
    }

    /**
     * Access current OAuth version.
     * 
     * @since 0.5
     * @return OAuth version.
     */
    @Override
    public String getVersion() {

	return VERSION;
    }

    /**
     * Signs a request.
     * <p>
     * This method appends the access token into the request, according to the
     * configured signature type.
     * </p>
     * <p>
     * It supports two signature types:
     * </p>
     * <ul>
     * <li><code>SignatureType.Header</code></li>
     * <li><code>SignatureType.QueryString</code></li>
     * </ul>
     * <p>
     * SignatureType.Header: Appends "BEARER" token as an authentication header.
     * </p>
     * <p>
     * SignatureType.QueryString: Append "oauth_token" as a query string
     * parameter.
     * </p>
     * 
     * @since 0.5
     */
    @Override
    public void signRequest(Token accessToken, OAuthRequest request) {

	switch (config.getSignatureType()) {
	    case Header:
		request.addHeader(OAuthConstants.HEADER, TrueNTHTokenType.BEARER.toString() + " " + accessToken.getToken());
		break;
	    case QueryString:
		request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken.getToken());
		break;
	}
    }

    /**
     * Returns the redirection URL where users authenticate.
     * 
     * <p>
     * Same as {@link #getAuthorizationUrl()}
     * </p>
     * 
     * @since 0.5
     * @param requestToken
     *            Access token.
     * @return the URL where you should redirect your users.
     * @see TrueNTHOAuthProvider#getAuthorizationUrl(OAuthConfig)
     */
    @Override
    public String getAuthorizationUrl(Token requestToken) {

	return api.getAuthorizationUrl(config);
    }

    /**
     * Returns the redirection URL where users authenticate.
     * 
     * @since 0.5
     * @param requestToken
     *            Access token.
     * @return the URL where you should redirect your users.
     * 
     * @see TrueNTHOAuthProvider#getAuthorizationUrl(OAuthConfig)
     */
    public String getAuthorizationUrl() {

	return api.getAuthorizationUrl(config);
    }

    /**
     * Returns the redirection URL.
     * 
     * @since 1.5
     * @param numberEncodings
     *            Number of encoding operations to be applied on the callback
     *            URL. Two encoding operations are necessary to communicate
     *            properly with CS after browser redirections in POP UPs.
     * @param callbackParameters
     *            Additional parameters to add with the callback URL. This
     *            parameter list will be added to the callback URL. Those
     *            parameters are destined to the callback target.
     * @param parameters
     *            Additional parameters to add on the Authorization URL. This
     *            parameter list should be used for especial circumstances to
     *            fine tune requests directed to the the OAuth server; for
     *            instance CS's "next" parameter. Parameters coming form the
     *            OAuthConfig will be automatically appended (by the provider);
     *            such as "scope".
     * @return the URL where users will be redirected.
     */
    public String getAuthorizationUrl(int numberEncodings, ParameterList callbackParameters, ParameterList parameters) {

	String baseURL = api.getAuthorizationUrl(config, numberEncodings, callbackParameters, parameters);

	return baseURL;
    }

    /**
     * Returns the redirection URL.
     * 
     * <p>
     * This is just a convenience method. Same as:
     * {@link #getAuthorizationUrl(int, ParameterList, ParameterList)}
     * </p>
     * 
     * @since 1.5
     * @param numberEncodings
     *            Number of encoding operations to be applied on the callback
     *            URL. Two encoding operations are necessary to communicate
     *            properly with CS after browser redirections in POP UPs.
     * @param callbackParameters
     *            Additional parameters to add with the callback URL. This
     *            parameter list will be added to the callback URL. Those
     *            parameters are destined to the callback target.
     * @return the URL where users will be redirected.
     */
    public String getAuthorizationUrl(int numberEncodings, ParameterList callbackParameters) {

	return getAuthorizationUrl(numberEncodings, callbackParameters, null);
    }

    /**
     * Returns the service configuration object.
     * 
     * @since 1.5
     * @return Service configuration.
     */
    public TrueNTHOAuthConfig getConfig() {

	return config;
    }
    
    
    /**
     * Returns the configured roles URL.
     * 
     * @see 2.5
     * @return Roles URL.
     */
    public String getRolesURL() {

	return getConfig().getRolesURL();
    }

    /**
     * Returns the configured resource URL (API base).
     * 
     * @see 2.5
     * @return Resource URL.
     */
    public String getResourceURL() {

	return getConfig().getResourceURL();
    }

    /**
     * Returns the configured Central Services' base URL.
     * 
     * <p>
     * This URL points to central services base URL, it should not be used for
     * OAuth operation, but for fetching static resources, such as css. It is
     * mainly used for templates.
     * </p>
     * @see 2.5
     * @return Configured Central Services base URL.
     */
    public String getBaseURL() {
    
        return getConfig().getBaseURL();
    }
}
