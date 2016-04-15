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
import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHUrlPlaceHolders;
import edu.uw.cirg.truenth.oauth.model.tokens.TrueNTHAccessToken;

/**
 * OAuth service.
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
     * Default constructor.
     *
     * @param trueNTHOAuthProvider
     *            OAuth2.0 api information.
     * @param config
     *            OAuth 2.0 configuration parameter object.
     */
    public TrueNTHOAuthService(final TrueNTHOAuthProvider trueNTHOAuthProvider, final TrueNTHOAuthConfig config) {

	api = trueNTHOAuthProvider;
	this.config = config;
	Preconditions.checkValidUrl(config.getCallback(), "Callback URL must be a valid URL.");
    }

    /**
     * Fetches an access token and returns as a token object.
     *
     * @param requestToken
     *            This parameter will not be used and can be safely set to null.
     * @param verifier
     *            Code to fetch the access token.
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
     * Returns the redirection URL where users authenticate.
     *
     * @return the URL where you should redirect your users.
     *
     * @see TrueNTHOAuthProvider#getAuthorizationUrl(TrueNTHOAuthConfig)
     */
    public String getAuthorizationUrl() {

	return api.getAuthorizationUrl(config);
    }

    /**
     * Returns the redirection URL.
     *
     * <p>
     * This is just a convenience method. Same as:
     * {@link #getAuthorizationUrl(int, ParameterList, ParameterList)}
     * </p>
     *
     *
     * @param numberEncodings
     *            Number of encoding operations to be applied on the callback
     *            URL. Two encoding operations are necessary to communicate
     *            properly with SS after browser redirections in POP UPs.
     * @param callbackParameters
     *            Additional parameters to add with the callback URL. This
     *            parameter list will be added to the callback URL. Those
     *            parameters are destined to the callback target.
     * @return the URL where users will be redirected.
     */
    public String getAuthorizationUrl(final int numberEncodings, final ParameterList callbackParameters) {

	return getAuthorizationUrl(numberEncodings, callbackParameters, null);
    }

    /**
     * Returns the redirection URL.
     *
     *
     * @param numberEncodings
     *            Number of encoding operations to be applied on the callback
     *            URL. Two encoding operations are necessary to communicate
     *            properly with SS after browser redirections in POP UPs.
     * @param callbackParameters
     *            Additional parameters to add with the callback URL. This
     *            parameter list will be added to the callback URL. Those
     *            parameters are destined to the callback target.
     * @param parameters
     *            Additional parameters to add on the Authorization URL. This
     *            parameter list should be used for especial circumstances to
     *            fine tune requests directed to the the OAuth server; for
     *            instance SS's "next" parameter. Parameters coming form the
     *            OAuthConfig will be automatically appended (by the provider);
     *            such as "scope".
     * @return the URL where users will be redirected.
     */
    public String getAuthorizationUrl(final int numberEncodings, final ParameterList callbackParameters, final ParameterList parameters) {

	final String baseURL = api.getAuthorizationUrl(config, numberEncodings, callbackParameters, parameters);

	return baseURL;
    }

    /**
     * Returns the redirection URL where users authenticate.
     *
     * <p>
     * Same as {@link #getAuthorizationUrl()}
     * </p>
     *
     * @param requestToken
     *            Access token.
     * @return the URL where you should redirect your users.
     * @see TrueNTHOAuthProvider#getAuthorizationUrl(TrueNTHOAuthConfig)
     */
    @Override
    public String getAuthorizationUrl(final Token requestToken) {

	return api.getAuthorizationUrl(config);
    }

    /**
     * Returns the configured Shared Services' base URL.
     *
     * <p>
     * This URL points to Shared Services base URL, it should not be used for
     * OAuth operation, but for fetching static resources, such as css. It is
     * mainly used for templates.
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
     * @return Nothing will be returned, and an exception will be thrown.
     */
    @Override
    @Deprecated
    public Token getRequestToken() {

	throw new UnsupportedOperationException("Unsupported operation, please use 'getAuthorizationUrl' and redirect your users there");
    }

    /**
     * Returns the configured resource URL (API base).
     *
     * @return Resource URL.
     */
    public String getResourceURL() {

	return getConfig().getResourceURL();
    }

    /**
     * Returns the configured roles URL.
     *
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
     * Replaces the userId place holder the the String representation of the
     * TrueNTH user ID.
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
}
