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
package edu.uw.cirg.truenth.oauth.builder;

import java.io.OutputStream;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.SignatureType;
import org.scribe.utils.Preconditions;

import edu.uw.cirg.truenth.oauth.TrueNTHOAuthService;
import edu.uw.cirg.truenth.oauth.builder.api.TrueNTHOAuthProvider;
import edu.uw.cirg.truenth.oauth.model.TrueNTHOAuthConfig;

/**
 * "Extension" of ServiceBuilder to include server configuration parameters.
 * 
 * <p>
 * Due to the private nature of
 * <code>ServiceBuilder<code>'s variables, and lack of getters/setters, extension was not used.
 * </p>
 * 
 * @author Victor de Lima Soares
 * @since Sep 21, 2015
 */
public class TrueNTHServiceBuilder {

    /**
     * URL that receives the access token requests.
     */
    private String	       accessTokenEndpointURL;

    /**
     * The redirection URL where users authenticate.
     */
    private String	       baseAuthorizationURL;

    private String	       apiKey;
    private String	       apiSecret;
    private String	       callbackURL;
    private TrueNTHOAuthProvider api;
    private String	       scope;
    private SignatureType	signatureType;
    private OutputStream	 debugStream;

    /**
     * Central Services roles URL.
     */
    private String	       rolesURL;

    /**
     * Resource URL.
     */
    private String	       resourceURL;

    /**
     * Central Services base URL.
     * <p>
     * This URL points to central services base URL, it should not be used for
     * OAuth operation, but for fetching static resources, such as css. It is
     * mainly used for templates.
     * </p>
     */
    private String	       baseURL;

    /**
     * Default constructor
     */
    public TrueNTHServiceBuilder() {

	this.signatureType = SignatureType.Header;
    }

    /**
     * Configures the {@link TrueNTHOAuthProvider} ({@link Api}).
     * 
     * @param apiClass
     *            The class of one of the existent {@link TrueNTHOAuthProvider}
     *            s.
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder provider(Class<? extends TrueNTHOAuthProvider> apiClass) {

	this.api = createApi(apiClass);
	return this;
    }

    private TrueNTHOAuthProvider createApi(Class<? extends TrueNTHOAuthProvider> apiClass) {

	Preconditions.checkNotNull(apiClass, "Api class cannot be null");
	TrueNTHOAuthProvider api;
	try {
	    api = apiClass.newInstance();
	} catch (Exception e) {
	    throw new OAuthException("Error while creating the Api object", e);
	}
	return api;
    }

    /**
     * Configures the {@link TrueNTHServiceBuilder} ({@link Api}).
     *
     * Overloaded version. Let's you use an instance instead of a class.
     *
     * @param api
     *            instance of {@link Api}s
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder provider(TrueNTHOAuthProvider api) {

	Preconditions.checkNotNull(api, "Api cannot be null");
	this.api = api;
	return this;
    }

    /**
     * Adds an OAuth base Authorization URL.
     * 
     * @param baseAuthorizationURL
     *            Base Authorization URL. Must be a valid URL.
     * 
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder baseAuthorizationURL(String baseAuthorizationURL) {

	Preconditions.checkValidUrl(baseAuthorizationURL, "baseAuthorizationURL is not a valid URL");
	this.baseAuthorizationURL = baseAuthorizationURL;
	return this;
    }

    /**
     * Configures the Central Services roles URL.
     * 
     * @param rolesURL
     *            Central Services' roles URL. Must be a valid URL.
     * @return the {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder rolesURL(String rolesURL) {

	Preconditions.checkValidUrl(rolesURL, "rolesURL is not a valid URL");
	this.rolesURL = rolesURL;
	return this;
    }

    /**
     * Configures the Central Services' resource URL.
     * 
     * @param resourceURL
     *            Central Services' resource URL. Must be a valid URL.
     * @return the {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder resourceURL(String resourceURL) {

	Preconditions.checkValidUrl(resourceURL, "resourceURL is not a valid URL");
	this.resourceURL = resourceURL;
	return this;
    }

    /**
     * Configures the Central Services' base URL.
     * 
     * @param baseURL
     *            Central Services' base URL. Must be a valid URL.
     * @return the {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder baseURL(String baseURL) {

	Preconditions.checkValidUrl(baseURL, "baseURL is not a valid URL");
	this.baseURL = baseURL;
	return this;
    }

    /**
     * Adds an OAuth access token endpoint URL.
     * 
     * @param accessTokenEndpointURL
     *            Access token end point URL. Must be a valid URL.
     * 
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder accessTokenEndpointURL(String accessTokenEndpointURL) {

	Preconditions.checkValidUrl(accessTokenEndpointURL, "accessTokenEndpointURL is not a valid URL");
	this.accessTokenEndpointURL = accessTokenEndpointURL;
	return this;
    }

    /**
     * Adds an OAuth callbackURL URL.
     * 
     * @param callbackURL
     *            Callback URL. Must be a valid URL.
     * 
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder callbackURL(String callback) {

	Preconditions.checkValidUrl(callback, "Callback is not a valid URL");
	this.callbackURL = callback;
	return this;
    }

    /**
     * Configures the App key.
     * 
     * @param apiKey
     *            The App key for your application.
     * @return the {@link TrueNTHServiceBuilder} instance for method chaining
     */
    public TrueNTHServiceBuilder apiKey(String apiKey) {

	Preconditions.checkEmptyString(apiKey, "Invalid Api key");
	this.apiKey = apiKey;
	return this;
    }

    /**
     * Configures the api secret.
     * 
     * @param apiSecret
     *            The api secret for your application.
     * @return the {@link TrueNTHServiceBuilder} instance for method chaining
     */
    public TrueNTHServiceBuilder apiSecret(String apiSecret) {

	Preconditions.checkEmptyString(apiSecret, "Invalid Api secret");
	this.apiSecret = apiSecret;
	return this;
    }

    /**
     * Configures the OAuth scope.
     * 
     * <p>
     * This is only necessary in some APIs.
     * </p>
     * 
     * @param scope
     *            The OAuth scope.
     * @return the {@link TrueNTHServiceBuilder} instance for method chaining
     */
    public TrueNTHServiceBuilder scope(String scope) {

	Preconditions.checkEmptyString(scope, "Invalid OAuth scope");
	this.scope = scope;
	return this;
    }

    /**
     * Configures the signature type, choose between header, querystring, etc.
     * 
     * <p>
     * Defaults to Header.
     * </p>
     * 
     * @param scope
     *            The OAuth scope
     * @return the {@link ServiceBuilder} instance for method chaining
     */
    public TrueNTHServiceBuilder signatureType(SignatureType type) {

	Preconditions.checkNotNull(type, "Signature type can't be null");
	this.signatureType = type;
	return this;
    }

    public TrueNTHServiceBuilder debugStream(OutputStream stream) {

	Preconditions.checkNotNull(stream, "debug stream can't be null");
	this.debugStream = stream;
	return this;
    }

    public TrueNTHServiceBuilder debug() {

	this.debugStream(System.out);
	return this;
    }

    /**
     * Returns the fully configured {@link OAuthService}
     * 
     * @return Fully configured {@link TrueNTHOAuthService}
     */
    public TrueNTHOAuthService build() {

	Preconditions.checkNotNull(api, "Must specify a valid api through the provider() method");

	Preconditions.checkEmptyString(accessTokenEndpointURL, "Must provide an access token endpoint URL");
	Preconditions.checkEmptyString(baseAuthorizationURL, "Must provide an base authorization URL");
	Preconditions.checkEmptyString(callbackURL, "Must provide an callback URL");

	Preconditions.checkEmptyString(apiKey, "Must provide an api key");
	Preconditions.checkEmptyString(apiSecret, "Must provide an api secret");

	return api.createService(new TrueNTHOAuthConfig(apiKey, apiSecret, accessTokenEndpointURL, baseAuthorizationURL, baseURL, resourceURL,
		rolesURL, callbackURL, signatureType, scope, debugStream));
    }

    public String getAccessTokenEndpointURL() {

	return accessTokenEndpointURL;
    }

    public String getBaseAuthorizationURL() {

	return baseAuthorizationURL;
    }

    public String getApiKey() {

	return apiKey;
    }

    public String getApiSecret() {

	return apiSecret;
    }

    public String getCallbackURL() {

	return callbackURL;
    }

    public TrueNTHOAuthProvider getApi() {

	return api;
    }

    public String getScope() {

	return scope;
    }

    public SignatureType getSignatureType() {

	return signatureType;
    }

    public OutputStream getDebugStream() {

	return debugStream;
    }

}
