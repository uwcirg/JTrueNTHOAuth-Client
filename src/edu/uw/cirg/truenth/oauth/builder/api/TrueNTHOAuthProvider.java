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
import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHOAuthConstants;
import edu.uw.cirg.truenth.oauth.model.tokens.extractors.JSonTrueNTHAccessTokenExtractor;
import edu.uw.cirg.truenth.oauth.model.tokens.extractors.TrueNTHAccessTokenExtractor;

/**
 * OAuth API provider.
 * 
 * <p>
 * Based on <i>org.scribe.builder.api.Api</i> and <i>DefaultApi20</i>.
 * </p>
 * 
 * <p>
 * This class has similar goals as <i>DefaultApi20</i>, but the particularities
 * of our CS called for a custom implementation that the default APIs would not
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
     * Returns the chosen method to obtain an access token.
     * 
     * <p>
     * CS requires POST.
     * </p>
     * 
     * @return Access method.
     */
    public Verb getAccessTokenVerb() {

	return Verb.POST;
    }

    /**
     * Returns the URL that receives the access token requests.
     * 
     * @param config
     *            TrueNTH OAuth configuration.
     * @return request token URL
     */
    public String getAccessTokenEndpoint(TrueNTHOAuthConfig config) {

	return config.getAccessTokenEndpoint();
    }

    /**
     * Returns the redirection URL where users authenticate.
     * 
     * @param config
     *            OAuth configuration.
     * @return the URL where you should redirect your users.
     */
    public String getAuthorizationUrl(TrueNTHOAuthConfig config) {

	String baseURL = config.getBaseAuthorizationURL();

	String callback = OAuthEncoder.encode((config.getCallback()));

	if (config.hasScope()) {
	    return baseURL + String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(), callback, OAuthEncoder.encode(config.getScope()));
	} else {
	    return baseURL + String.format(AUTHORIZE_URL, config.getApiKey(), callback);
	}

    }

    /**
     * Returns the redirection URL where users authenticate.
     * 
     * <p>
     * Same as
     * <code>getAuthorizationUrl(config, numberEncodings, callbackParameters, null)</code>
     * .
     * </p>
     * 
     * @param config
     *            OAuth configuration.
     * @param numberEncodings
     *            Number of encoding operations to be applied on the callback
     *            URL. Two encoding operations are necessary to communicate
     *            properly with CS after browser redirections in POP UPs.
     * @param callbackParameters
     *            Additional parameters to add with the callback URL. This
     *            parameter list will be added to the callback URL. Those
     *            parameters are destined to the callback target.
     * @return the URL where users will be redirected.
     * 
     * @see #getAuthorizationUrl(TrueNTHOAuthConfig, int, ParameterList,
     *      ParameterList)
     */
    public String getAuthorizationUrl(TrueNTHOAuthConfig config, int numberEncodings, ParameterList callbackParameters) {

	return getAuthorizationUrl(config, numberEncodings, callbackParameters, null);
    }

    /**
     * Returns the redirection URL where users authenticate.
     * 
     * @param config
     *            OAuth configuration.
     * @param numberEncodings
     *            Number of encoding operations to be applied on the callback
     *            URL. Two encoding operations are necessary to communicate
     *            properly with CS after browser redirections in POP UPs.
     * @param callbackParameters
     *            Additional parameters to add with the callback URL. This
     *            parameter list will be added to the callback URL. Those
     *            parameters are destined to the callback target. For instance,
     *            {@link TrueNTHOAuthConstants#REDIRECT}.
     * @param parameters
     *            Additional parameters to add on the Authorization URL. This
     *            parameter list should be used for especial circumstances to
     *            fine tune requests directed to the the OAuth server; for
     *            instance, CS's "next" parameter:
     *            {@link TrueNTHOAuthConstants#NEXT}.
     * @return the URL where users will be redirected.
     */
    public String getAuthorizationUrl(TrueNTHOAuthConfig config, int numberEncodings, ParameterList callbackParameters, ParameterList parameters) {

	String baseURL = config.getBaseAuthorizationURL();

	String callback = config.getCallback();

	if (callbackParameters != null) callback = callbackParameters.appendTo(callback);

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

    /**
     * Creates an {@link TrueNTHOAuthService}
     * 
     * @param config
     *            OAuth API configuration.
     * @return Fully configured {@link TrueNTHOAuthService}
     * @see TrueNTHOAuthService
     */
    public TrueNTHOAuthService createService(TrueNTHOAuthConfig config) {

	return new TrueNTHOAuthService(this, config);
    }

    /**
     * Creates an {@link TrueNTHOAuthService}
     * 
     * 
     * @param config
     *            OAuth API configuration.
     * @return Fully configured {@link TrueNTHOAuthService}.
     * 
     * @throws InvalidParameterException
     *             config is not a instance of TrueNTHOAuthConfig.
     * 
     * @see TrueNTHOAuthService
     * @deprecated Use {@link #createService(TrueNTHOAuthConfig)}.
     */
    @Deprecated
    @Override
    public TrueNTHOAuthService createService(OAuthConfig config) {

	if (config instanceof TrueNTHOAuthConfig) return createService((TrueNTHOAuthConfig) config);

	throw new InvalidParameterException("config is not a instance of TrueNTHOAuthConfig");
    }

    /**
     * Returns the access token extractor.
     * 
     * <p>
     * This method uses a JSON based extractor as required by CS.
     * </p>
     * 
     * @return access token extractor
     */
    public TrueNTHAccessTokenExtractor<JsonObject> getAccessTokenExtractor() {

	return new JSonTrueNTHAccessTokenExtractor();
    }

}
