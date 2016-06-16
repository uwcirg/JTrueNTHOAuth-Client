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
package edu.uw.cirg.truenth.oauth;

import java.io.StringReader;
import java.net.URL;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.ParameterList;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import edu.uw.cirg.truenth.oauth.builder.TrueNTHServiceBuilder;
import edu.uw.cirg.truenth.oauth.builder.api.TrueNTHOAuthProvider;
import edu.uw.cirg.truenth.oauth.model.TrueNTHOAuthConfig;
import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHGrantType;
import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHTokenType;
import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHUrlPlaceHolders;
import edu.uw.cirg.truenth.oauth.model.tokens.TrueNTHAccessToken;
import edu.uw.cirg.truenth.ss.roles.SSRole;
import edu.uw.cirg.truenth.ss.roles.SSRoleExtractorJson;

/**
 * OAuth service.
 *
 * <p>
 * This class produces the main artifacts to issue requests towards SS,
 * providing instances that centralize all configuration and addressing elements
 * to access SS' OAuth APIs. Each SS instance will have its own client side
 * counterpart represented by an instance of this class.
 * </p>
 *
 * <p>
 * Services:
 * </p>
 * <ul>
 * <li>Obtain access tokens;</li>
 * <li>Obtain updated access tokens;</li>
 * <li>Create authorization URLs;</li>
 * <li>Access configured SS' URLs;</li>
 * <li>Issue requests;</li>
 * <li>Sign requests.</li>
 * </ul>
 *
 * <p>
 * SS' API specification: https://stg.us.truenth.org/dist/
 * </p>
 *
 * @author Victor de Lima Soares
 * @since Sep 11, 2015
 */
public class TrueNTHOAuthService implements OAuthService {

    /**
     * OAuth version.
     */
    private static final String	VERSION = "2.0";

    private final TrueNTHOAuthProvider api;
    private final TrueNTHOAuthConfig   config;

    /**
     * Builds the service with a predefined configuration.
     *
     * <p>
     * This library provides a helper class to build the configuration and
     * automatically construct the service. Please check the
     * TrueNTHServiceBuilder class.
     * </p>
     *
     * @see TrueNTHServiceBuilder
     * @param trueNTHOAuthProvider
     *            OAuth2.0 API provider.
     * @param config
     *            OAuth 2.0 Configuration.
     */
    public TrueNTHOAuthService(final TrueNTHOAuthProvider trueNTHOAuthProvider, final TrueNTHOAuthConfig config) {

	api = trueNTHOAuthProvider;
	this.config = config;
    }

    /**
     * Fetches an access token and returns as a token object.
     *
     * @param requestToken
     *            This parameter will not be used and can be safely set to null
     *            (it comes from a deprecated API).
     *
     * @param verifier
     *            Authorization Code to obtain an access token.
     *
     * @return access token.
     */
    @Override
    public TrueNTHAccessToken getAccessToken(final Token requestToken, final Verifier verifier) {

	final OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint(config));
	request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
	request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
	request.addBodyParameter(TrueNTHGrantType.PARAMETER, TrueNTHGrantType.CODE.toString());
	request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
	request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
	if (config.hasScope()) {
	    request.addBodyParameter(OAuthConstants.SCOPE, config.getScope());
	}
	final Response response = request.send();
	return api.getAccessTokenExtractor().extract(response.getBody());
    }

    /**
     * Fetches an access token and returns as a token object.
     *
     * @param requestToken
     *            This parameter will not be used and can be safely set to null
     *            (it comes from a deprecated API).
     *
     * @param code
     *            Authorization Code to obtain an access token.
     *
     * @return access token.
     */
    public TrueNTHAccessToken getAccessToken(final String code) {

	final Verifier verifierCode = new Verifier(code);
	return getAccessToken(null, verifierCode);
    }

    /**
     * Fetches an updated access token, via status, and returns as a new token
     * object.
     *
     * @param accessToken
     *            Token that will be updated, if valid.
     * @return Updated access token, or null if an error has occurred.
     */
    public TrueNTHAccessToken getAccessTokenStatus(final Token accessToken) {

	try {

	    final URL url = new URL(api.getAccessTokenStatusEndpoint(config));

	    final String json = getResource(url, accessToken).getBody();

	    return api.getAccessTokenExtractor().extract(json);

	} catch (final Exception ex) {
	    return null;
	}
    }

    /**
     * Checks if the access token is active in the SS instance.
     *
     * @param accessToken
     *            Token that will be verified.
     *
     * @return <ul>
     *         <li>True, if the token is still valid;</li>
     *         <li>False, if the token is invalid or expired;</li>
     *         <li>False, if the verification is not possible.</li>
     *         <ul>
     */
    public boolean isAccessTokenActive(final Token accessToken) {

	try {

	    final URL url = new URL(api.getAccessTokenStatusEndpoint(config));

	    final int responseCode = getResource(url, accessToken).getCode();

	    return responseCode == 200;

	} catch (final Exception ex) {
	    return false;
	}
    }

    /**
     * Returns the redirection URL where users authenticate.
     *
     * <p>
     * This URL will be used to redirect users to SS, which will authenticate
     * them and redirect them back to the local callback URL.
     * </p>
     *
     * <p>
     * This URL will point to the SS' Authorization address. In that location,
     * users will be authenticated to receive an Authorization Code.
     * </p>
     *
     * <p>
     * With the Authorization Code, the users will be redirected back to the
     * callback URL, where the local system receives the Authorization code to
     * be exchanged for an Access Token.
     * </p>
     *
     * @return The URL where users should be redirected.
     *
     * @see TrueNTHOAuthProvider#getAuthorizationUrl(TrueNTHOAuthConfig)
     */
    public String getAuthorizationUrl() {

	return api.getAuthorizationUrl(config);
    }

    /**
     * Returns the URL where users authenticate and authorize clients.
     *
     * <p>
     * This URL will be used to redirect users to SS, which will authenticate
     * them and redirect them back to the local callback URL.
     * </p>
     *
     * <p>
     * This URL will point to the SS' Authorization address. In that location,
     * users will be authenticated to receive an Authorization Code.
     * </p>
     *
     * <p>
     * With the Authorization Code, the users will be redirected back to the
     * callback URL, where the local system receives the Authorization Code to
     * be exchanged for an Access Token.
     * </p>
     *
     * <p>
     * This is just a convenience method. Same as:
     * <code>getAuthorizationUrl(numberEncodings, callbackParameters, null)</code>
     * </p>
     *
     *
     * @param numberEncodings
     *            Number of URL encoding operations to be applied on the
     *            callback URL. For instance, two encoding operations are
     *            necessary to communicate properly with SS after browser
     *            redirections in POP UPs.
     *
     * @param callbackParameters
     *            Additional parameters to add into the callback URL. Those
     *            parameters are destined to the callback target.
     *
     * @return The URL where users will be redirected.
     *
     * @see #getAuthorizationUrl(int, ParameterList, ParameterList)
     */
    public String getAuthorizationUrl(final int numberEncodings, final ParameterList callbackParameters) {

	return getAuthorizationUrl(numberEncodings, callbackParameters, null);
    }

    /**
     * Returns the URL where users authenticate and authorize clients.
     *
     * <p>
     * This URL will be used to redirect users to SS, which will authenticate
     * them and redirect them back to the local callback URL.
     * </p>
     *
     * <p>
     * This URL will point to the SS' Authorization address. In that location,
     * users will be authenticated to receive an Authorization Code.
     * </p>
     *
     * <p>
     * With the Authorization Code, the users will be redirected back to the
     * callback URL, where the local system receives the Authorization Code to
     * be exchanged for an Access Token.
     * </p>
     *
     *
     * @param numberEncodings
     *            Number of URL encoding operations to be applied on the
     *            callback URL. For instance, two encoding operations are
     *            necessary to communicate properly with SS after browser
     *            redirections in POP UPs.
     *
     * @param callbackParameters
     *            Additional parameters to add into the callback URL. Those
     *            parameters are destined to the callback target.
     *
     * @param parameters
     *            Additional parameters to add into the Authorization URL. This
     *            parameter list should be used for special circumstances to
     *            fine tune requests directed to the OAuth server; for instance
     *            SS's "next" parameter. Parameters coming form the OAuthConfig
     *            will be automatically appended (by the provider); such as
     *            "scope".
     *
     * @return The URL where users will be redirected.
     */
    public String getAuthorizationUrl(final int numberEncodings, final ParameterList callbackParameters, final ParameterList parameters) {

	return api.getAuthorizationUrl(config, numberEncodings, callbackParameters, parameters);
    }

    /**
     * Returns the URL where users authenticate and authorize clients.
     *
     * <p>
     * Same as {@link #getAuthorizationUrl()}
     * </p>
     *
     * @param requestToken
     *            Access token (not used).
     *
     * @return The URL where you should redirect your users.
     *
     * @see TrueNTHOAuthProvider#getAuthorizationUrl(TrueNTHOAuthConfig)
     */
    @Override
    @Deprecated
    public String getAuthorizationUrl(final Token requestToken) {

	return api.getAuthorizationUrl(config);
    }

    /**
     * Returns the configured Shared Services' base URL.
     *
     * <p>
     * This URL points to Shared Services base URL, it should not be used for
     * OAuth operation, but for fetching static resources, such as CSS. It is
     * mainly used for templates and cases where the resource must come form the
     * same SS instance.
     * </p>
     *
     * @return Configured Shared Services base URL.
     */
    public String getBaseURL() {

	return getConfig().getBaseURL();
    }

    /**
     * Returns the service configuration object.
     *
     * @return Service configuration.
     */
    public TrueNTHOAuthConfig getConfig() {

	return config;
    }

    /**
     * Fetches request token.
     *
     * @throws UnsupportedOperationException
     *             This operation is not supported by SS; thus, this workflow
     *             shall not be used.
     *
     * @return Nothing will be returned, and an exception will be thrown.
     */
    @Override
    @Deprecated
    public Token getRequestToken() {

	throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
    }

    /**
     * Returns the configured OAuth API base URL (API base).
     *
     * <p>
     * For instance: https://stg.us.truenth.org/api
     * </p>
     *
     * @return API base URL.
     */
    public String getResourceURL() {

	return getConfig().getResourceURL();
    }

    /**
     * Returns the configured roles URL.
     *
     * <p>
     * This method returns the generic address of users' roles. It contains the
     * #userId wildcard, and it does not represent a real location.
     * </p>
     *
     * <p>
     * For instance: https://stg.us.truenth.org/api/user/#userId/roles
     * </p>
     *
     * @return Roles URL.
     */
    public String getRolesURL() {

	return getConfig().getRolesURL();
    }

    /**
     * Returns the configured roles URL, for a specific user.
     *
     * <p>
     * Replaces the userId place holder with the String representation of the
     * TrueNTH User ID.
     * </p>
     *
     * @param userId
     *            TrueNTH User ID.
     * @return Roles URL.
     */
    public String getRolesURL(final long userId) {

	return getRolesURL().replaceFirst(TrueNTHUrlPlaceHolders.USER_ID, String.valueOf(userId));
    }

    /**
     * Access current OAuth version.
     *
     * @return OAuth version.
     */
    @Override
    public String getVersion() {

	return VERSION;
    }

    /**
     * Signs a request.
     *
     * <p>
     * This method appends the access token into the request, according to the
     * configured signature type.
     * </p>
     *
     * <p>
     * It supports the following two signature types:
     * </p>
     *
     * <ul>
     * <li><code>SignatureType.Header</code>;</li>
     * <li><code>SignatureType.QueryString</code>;</li>
     * </ul>
     *
     * <p>
     * SignatureType.Header: Appends "BEARER" as an authentication header.
     * </p>
     *
     * <p>
     * SignatureType.QueryString: Append "oauth_token" as a query string
     * parameter.
     * </p>
     *
     */
    @Override
    public void signRequest(final Token accessToken, final OAuthRequest request) {

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
     * Get a resource that is know to be a JSON object.
     *
     * @param path
     *            Resource relative path. It should be relative to the Resource
     *            URL.
     *
     * @param accessToken
     *            Access Token.
     *
     * @return The JSON object extracted from the response, or null in case of
     *         exceptions.
     *
     * @see #getResourceURL()
     */
    public JsonObject getResourceJson(final String path, final Token accessToken) {

	try {

	    final URL url = new URL(getResourceURL().concat(path));

	    return getResourceJson(url, accessToken);

	} catch (final Exception e) {

	    return null;
	}
    }

    /**
     * Get a resource that is know to be a JSON object.
     *
     * @param address
     *            Complete URL, which points to the desired resource.
     *
     * @param accessToken
     *            Access Token.
     *
     * @return The JSON object extracted from the response, or null in case of
     *         exceptions.
     */
    private JsonObject getResourceJson(final URL url, final Token accessToken) {

	try {

	    final OAuthRequest request = new OAuthRequest(Verb.GET, url.toString());
	    signRequest(accessToken, request);

	    final String json = getResource(url, accessToken).getBody();

	    return Json.createReader(new StringReader(json)).readObject();

	} catch (final Exception e) {

	    return null;
	}
    }

    /**
     * Get a generic OAuth protected resource.
     *
     * @param path
     *            Resource relative path. It should be relative to the Resource
     *            URL.
     *
     * @param accessToken
     *            Access Token.
     *
     * @return The received response, without and treatment, or null in case of
     *         exceptions.
     *
     * @see #getResourceURL()
     */
    public Response getResource(final String path, final Token accessToken) {

	try {

	    final URL url = new URL(getResourceURL().concat(path));

	    return getResource(url, accessToken);

	} catch (final Exception e) {

	    return null;
	}
    }

    /**
     * Get a generic OAuth protected resource.
     *
     * @param address
     *            Complete URL, which points to the desired resource.
     *
     * @param accessToken
     *            Access Token.
     *
     * @return The received response, without and treatment, or null in case of
     *         exceptions.
     *
     */
    private Response getResource(final URL address, final Token accessToken) {

	try {

	    final OAuthRequest request = new OAuthRequest(Verb.GET, address.toString());
	    signRequest(accessToken, request);

	    return request.send();

	} catch (final Exception e) {

	    return null;
	}
    }

    /**
     * Get user roles.
     *
     * @param trueNTHUserId
     *            TrueNTH User ID.
     * @param accessToken
     *            Access Token.
     * @return A list of the roles associated with the user.
     */
    public List<SSRole> getTrueNTHRoles(final long trueNTHUserId, final TrueNTHAccessToken accessToken) {

	try {

	    final URL url = new URL(getRolesURL(trueNTHUserId));

	    final JsonObject json = getResourceJson(url, accessToken);

	    final SSRoleExtractorJson roleExtractor = new SSRoleExtractorJson();

	    return roleExtractor.extractRoles(json);

	} catch (final Exception e) {

	    return null;
	}
    }
}
