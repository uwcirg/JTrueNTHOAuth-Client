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
package edu.uw.cirg.truenth.oauth.builder.api;

import java.security.InvalidParameterException;

import javax.json.JsonObject;

import org.scribe.builder.api.Api;
import org.scribe.model.OAuthConfig;
import org.scribe.model.ParameterList;
import org.scribe.model.Verb;
import org.scribe.utils.OAuthEncoder;

import edu.uw.cirg.truenth.oauth.TrueNTHOAuthService;
import edu.uw.cirg.truenth.oauth.model.TrueNTHOAuthConfig;
import edu.uw.cirg.truenth.oauth.model.tokens.extractors.TrueNTHAccessTokenExtractor;
import edu.uw.cirg.truenth.oauth.model.tokens.extractors.TrueNTHAccessTokenExtractorJSon;

/**
 * SS' OAuth API provider.
 *
 * <p>
 * Based on <i>org.scribe.builder.api.Api</i> and <i>DefaultApi20</i>.
 * </p>
 *
 * <p>
 * This class has similar goals as <i>DefaultApi20</i>, but the particularities
 * of SS called for a custom implementation that the default APIs would not
 * satisfy.
 * </p>
 *
 * <p>
 * Originally, this class was aligned with Scribe's service implementations, but
 * as new requirements for dynamic server configuration emerged, the use of
 * {@link TrueNTHOAuthConfig} became imperative.
 * </p>
 *
 * @author Victor de Lima Soares
 * @since Sep 11, 2015
 *
 * @see TrueNTHOAuthService
 * @see TrueNTHOAuthConfig
 */
public class TrueNTHOAuthProvider implements Api {

    private static final String AUTHORIZE_URL	= "?client_id=%s&response_type=code&redirect_uri=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";

    /**
     * Creates an {@link TrueNTHOAuthService}
     *
     *
     * @param config
     *            SS' OAuth API configuration.
     *            
     * @return Fully configured {@link TrueNTHOAuthService}.
     *
     * @throws InvalidParameterException
     *             If config is not a instance of TrueNTHOAuthConfig.
     *
     * @see TrueNTHOAuthService
     * @deprecated Use {@link #createService(TrueNTHOAuthConfig)}.
     */
    @Deprecated
    @Override
    public TrueNTHOAuthService createService(final OAuthConfig config) {

	if (config instanceof TrueNTHOAuthConfig) { return createService((TrueNTHOAuthConfig) config); }

	throw new InvalidParameterException("Configuration object is not an instance of TrueNTHOAuthConfig");
    }

    /**
     * Creates an {@link TrueNTHOAuthService}
     *
     * @param config
     *            SS' OAuth API configuration.
     *            
     * @return Fully configured {@link TrueNTHOAuthService}
     * 
     * @see TrueNTHOAuthService
     */
    public TrueNTHOAuthService createService(final TrueNTHOAuthConfig config) {

	return new TrueNTHOAuthService(this, config);
    }

    /**
     * Returns the URL that receives access token requests.
     *
     * @param config
     *            TrueNTH OAuth configuration.
     *            
     * @return request token URL.
     */
    public String getAccessTokenEndpoint(final TrueNTHOAuthConfig config) {

	return config.getAccessTokenEndpoint();
    }

    /**
     * Returns the URL that receives access token status requests.
     *
     * @param config
     *            TrueNTH OAuth configuration.
     *            
     * @return request Token status URL.
     */
    public String getAccessTokenStatusEndpoint(final TrueNTHOAuthConfig config) {

	return config.getAccessTokenStatusEndpoint();
    }

    /**
     * Returns the access token extractor.
     *
     * <p>
     * This method uses a JSON based extractor as required by SS.
     * </p>
     *
     * @return access Token extractor.
     */
    public TrueNTHAccessTokenExtractor<JsonObject> getAccessTokenExtractor() {

	return new TrueNTHAccessTokenExtractorJSon();
    }

    /**
     * Returns the chosen method for obtaining an access token.
     *
     * <p>
     * SS requires POST.
     * </p>
     *
     * @return Access method.
     */
    public Verb getAccessTokenVerb() {

	return Verb.POST;
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
     * The callback URL is encoded only once.
     * </p>
     *
     * @return The URL where users should be redirected.
     *
     * @param config
     *            OAuth configuration.
     *            
     * @return The URL where the system should redirect users.
     */
    public String getAuthorizationUrl(final TrueNTHOAuthConfig config) {

	final String baseURL = config.getBaseAuthorizationURL();

	final String callback = OAuthEncoder.encode(config.getCallback());

	if (config.hasScope()) {
	    return baseURL + String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), callback, OAuthEncoder.encode(config.getScope()));
	} else {
	    return baseURL + String.format(AUTHORIZE_URL, config.getApiKey(), callback);
	}

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
     * @param config
     *            SS' OAuth configuration.
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
    public String getAuthorizationUrl(final TrueNTHOAuthConfig config, final int numberEncodings, final ParameterList callbackParameters,
	    final ParameterList parameters) {

	String baseURL = config.getBaseAuthorizationURL();

	String callback = config.getCallback();

	if (callbackParameters != null) {
	    callback = callbackParameters.appendTo(callback);
	}

	for (int i = 0; i < numberEncodings; i++) {
	    callback = OAuthEncoder.encode(callback);
	}

	if (config.hasScope()) {
	    baseURL += String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), callback, OAuthEncoder.encode(config.getScope()));
	} else {
	    baseURL += String.format(AUTHORIZE_URL, config.getApiKey(), callback);
	}

	return (parameters == null) ? baseURL : parameters.appendTo(baseURL);
    }

}
