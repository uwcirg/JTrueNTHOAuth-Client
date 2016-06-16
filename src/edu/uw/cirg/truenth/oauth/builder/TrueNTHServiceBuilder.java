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
package edu.uw.cirg.truenth.oauth.builder;

import java.io.OutputStream;

import org.scribe.exceptions.OAuthException;
import org.scribe.model.SignatureType;
import org.scribe.utils.Preconditions;

import edu.uw.cirg.truenth.oauth.TrueNTHOAuthService;
import edu.uw.cirg.truenth.oauth.builder.api.TrueNTHOAuthProvider;
import edu.uw.cirg.truenth.oauth.model.TrueNTHOAuthConfig;

/**
 * Extension of ServiceBuilder to include server configuration parameters.
 *
 * <p>
 * Due to the private nature of ServiceBuilder's variables, and the lack of
 * getters/setters, extension was not used.
 * </p>
 *
 * @author Victor de Lima Soares
 * @since Sep 21, 2015
 */
public class TrueNTHServiceBuilder {

    private String	       accessTokenEndpointURL;
    private String	       accessTokenStatusEndpointURL;

    private TrueNTHOAuthProvider api;

    private String	       apiKey;
    private String	       apiSecret;

    private String	       baseAuthorizationURL;
    private String	       baseURL;
    private String	       callbackURL;
    private OutputStream	 debugStream;

    private String	       resourceURL;
    private String	       rolesURL;
    private String	       scope;
    private SignatureType	signatureType;

    /**
     * Default constructor.
     *
     * <p>
     * It automatically sets the signature type to "Header" and the scope to
     * email.
     * </p>
     */
    public TrueNTHServiceBuilder() {

	scope = "email";
	signatureType = SignatureType.Header;
    }

    /**
     * Configures the SS' OAuth access token request URL.
     *
     * <p>
     * Example: https://stg.us.truenth.org/oauth/token (staging server).
     * </p>
     *
     * @param accessTokenEndpointURL
     *            Access token request URL. Must be a valid URL.
     *
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder accessTokenEndpointURL(final String accessTokenEndpointURL) {

	Preconditions.checkValidUrl(accessTokenEndpointURL, "accessTokenEndpointURL is not a valid URL");
	this.accessTokenEndpointURL = accessTokenEndpointURL;
	return this;
    }

    /**
     * Configures the SS' OAuth access token status request URL.
     *
     * <p>
     * Example: https://stg.us.truenth.org/oauth/token-status (staging server).
     * </p>
     *
     * @param accessTokenStatusEndpointURL
     *            Access token request status URL. Must be a valid URL.
     *
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder accessTokenStatusEndpointURL(final String accessTokenStatusEndpointURL) {

	Preconditions.checkValidUrl(accessTokenStatusEndpointURL, "accessTokenStatusEndpointURL is not a valid URL");
	this.accessTokenStatusEndpointURL = accessTokenStatusEndpointURL;
	return this;
    }

    /**
     * Configures the application key (application ID).
     *
     * @param apiKey
     *            The application key (ID), configured in SS.
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder apiKey(final String apiKey) {

	Preconditions.checkEmptyString(apiKey, "Invalid Api key");
	this.apiKey = apiKey;
	return this;
    }

    /**
     * Configures the application secret.
     *
     * @param apiSecret
     *            The application secret, configured in SS.
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder apiSecret(final String apiSecret) {

	Preconditions.checkEmptyString(apiSecret, "Invalid Api secret");
	this.apiSecret = apiSecret;
	return this;
    }

    /**
     * Configures the OAuth base authorization URL.
     *
     * <p>
     * This URL will be the base for building new authorization URLs, not being
     * directly suitable for that use as encoding needs to be applied and
     * parameters added.
     * </p>
     *
     * <p>
     * Example: https://stg.us.truenth.org/oauth/authorize (staging server).
     * </p>
     *
     * @param baseAuthorizationURL
     *            Base Authorization URL. Must be a valid URL.
     *
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder baseAuthorizationURL(final String baseAuthorizationURL) {

	Preconditions.checkValidUrl(baseAuthorizationURL, "baseAuthorizationURL is not a valid URL");
	this.baseAuthorizationURL = baseAuthorizationURL;
	return this;
    }

    /**
     * Configures the Shared Services' base URL.
     *
     * <p>
     * This URL points to Shared Services' base URL, it should not be used for
     * OAuth operations, but for fetching static resources, such as CSS. It is
     * mainly used for templates.
     * </p>
     *
     * <p>
     * Example: https://stg.us.truenth.org (staging server).
     * </p>
     *
     * @param baseURL
     *            Shared Services' base URL. Must be a valid URL.
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder baseURL(final String baseURL) {

	Preconditions.checkValidUrl(baseURL, "baseURL is not a valid URL");
	this.baseURL = baseURL;
	return this;
    }

    /**
     * Returns the fully configured TrueNTHOAuthService.
     *
     * @return Fully configured {@link TrueNTHOAuthService}.
     */
    public TrueNTHOAuthService build() {

	Preconditions.checkNotNull(api, "Must specify a valid api through the provider() method");

	Preconditions.checkEmptyString(accessTokenEndpointURL, "Must provide an access token endpoint URL");
	Preconditions.checkEmptyString(baseAuthorizationURL, "Must provide an base authorization URL");
	Preconditions.checkEmptyString(callbackURL, "Must provide an callback URL");

	Preconditions.checkEmptyString(apiKey, "Must provide an api key");
	Preconditions.checkEmptyString(apiSecret, "Must provide an api secret");

	return api.createService(new TrueNTHOAuthConfig(getApiKey(), getApiSecret(), getAccessTokenEndpointURL(), getAccessTokenStatusEndpointURL(),
		getBaseAuthorizationURL(), getBaseURL(), getResourceURL(), getRolesURL(), getCallbackURL(), getSignatureType(), getScope(),
		getDebugStream()));
    }

    /**
     * Configures the OAuth callbackURL URL.
     *
     * <p>
     * Local application's URL for processing token request responses. The users
     * will land into this URL after they obtain an authorization grant, only
     * "code" is used by SS.
     * </p>
     *
     * @param callback
     *            Callback URL. Must be a valid URL.
     *
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder callbackURL(final String callback) {

	Preconditions.checkValidUrl(callback, "Callback is not a valid URL");
	callbackURL = callback;
	return this;
    }

    private TrueNTHOAuthProvider createApi(final Class<? extends TrueNTHOAuthProvider> apiClass) {

	Preconditions.checkNotNull(apiClass, "Api class cannot be null");
	TrueNTHOAuthProvider api;
	try {
	    api = apiClass.newInstance();
	} catch (final Exception e) {
	    throw new OAuthException("Error while creating the Api object", e);
	}
	return api;
    }

    /**
     * Configures the debug stream as System.out.
     *
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder debug() {

	debugStream(System.out);
	return this;
    }

    /**
     * Configures the debug stream.
     *
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder debugStream(final OutputStream stream) {

	Preconditions.checkNotNull(stream, "debug stream can't be null");
	debugStream = stream;
	return this;
    }

    /**
     * Returns the SS' URL that receives access token requests.
     *
     * @return Access token request URL.
     */
    public String getAccessTokenEndpointURL() {

	return accessTokenEndpointURL;
    }

    /**
     * Returns the SS' URL that receives access token status requests.
     *
     * @return Access token request status URL.
     */
    public String getAccessTokenStatusEndpointURL() {

	return accessTokenStatusEndpointURL;
    }

    /**
     * Access the provider instance.
     *
     * @return The provider instance used to build the service.
     */
    public TrueNTHOAuthProvider getApi() {

	return api;
    }

    /**
     * Returns the application key (application ID), configured in SS.
     *
     * @return Application key.
     */
    public String getApiKey() {

	return apiKey;
    }

    /**
     * Returns the application secret, configured in SS.
     *
     * @return Application secret.
     */
    public String getApiSecret() {

	return apiSecret;
    }

    /**
     * Returns the OAuth base authorization URL.
     *
     * <p>
     * This URL will be the base for building new authorization URLs, not being
     * directly suitable for that use as encoding needs to be applied and
     * parameters added.
     * </p>
     *
     * <p>
     * Example: https://stg.us.truenth.org/oauth/authorize (staging server).
     * </p>
     *
     * @return Base authorization URL.
     */
    public String getBaseAuthorizationURL() {

	return baseAuthorizationURL;
    }

    /**
     * Returns the OAuth callbackURL URL.
     *
     * <p>
     * Local application's URL for processing token request responses. The users
     * will land into this URL after they obtain an authorization grant, only
     * "code" is used by SS.
     * </p>
     *
     * @return OAuth callbackURL URL
     */
    public String getCallbackURL() {

	return callbackURL;
    }

    /**
     * Returns the the debug stream.
     *
     * @return The debug stream
     */
    public OutputStream getDebugStream() {

	return debugStream;
    }

    /**
     * Returns the scope of the resources to be accessed through the service.
     *
     * @return OAuth API scope.
     */
    public String getScope() {

	return scope;
    }

    /**
     * Type of signature to be used by the service (header or querystring).
     *
     * @return Signature type.
     */
    public SignatureType getSignatureType() {

	return signatureType;
    }

    /**
     * Returns the Shared Services' base URL.
     *
     * <p>
     * This URL points to Shared Services' base URL, it should not be used for
     * OAuth operations, but for fetching static resources, such as CSS. It is
     * mainly used for templates.
     * </p>
     *
     * <p>
     * Example: https://stg.us.truenth.org (staging server).
     * </p>
     *
     * @return The SS' base URL.
     */
    public String getBaseURL() {

	return baseURL;
    }

    /**
     * Returns the resources URL.
     *
     * <p>
     * This URL is the base for obtaining protected resources.
     * </p>
     *
     * <p>
     * Example: https://stg.us.truenth.org/api
     * </p>
     *
     * <p>
     * Derived from the example: https://stg.us.truenth.org/api/demographics
     * </p>
     *
     * @return The resourceURL.
     */
    public String getResourceURL() {

	return resourceURL;
    }

    /**
     * Returns the URL used to fetch users' roles.
     *
     * <p>
     * The following example demonstrates a URL used to obtain the roles granted
     * to a specific user, where #userId is a placeholder for the user's ID.
     * This library understands this placeholder and automatically replaces it
     * when requests are made.
     * <p>
     * <p>
     * Example: https://stg.us.truenth.org/api/user/#userId/roles
     * </p>
     *
     *
     * @return The rolesURL.
     */
    public String getRolesURL() {

	return rolesURL;
    }

    /**
     * Configures the API TrueNTHOAuthProvider.
     *
     * @param apiClass
     *            The provider class to be used.
     *
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder provider(final Class<? extends TrueNTHOAuthProvider> apiClass) {

	api = createApi(apiClass);
	return this;
    }

    /**
     * Configures the API TrueNTHOAuthProvider.
     *
     * @param api
     *            Instance of {@link TrueNTHOAuthProvider}.
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder provider(final TrueNTHOAuthProvider api) {

	Preconditions.checkNotNull(api, "Api cannot be null");
	this.api = api;
	return this;
    }

    /**
     * Configures the Shared Services' resource URL.
     *
     * @param resourceURL
     *            Shared Services' resource URL. Must be a valid URL.
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder resourceURL(final String resourceURL) {

	Preconditions.checkValidUrl(resourceURL, "resourceURL is not a valid URL");
	this.resourceURL = resourceURL;
	return this;
    }

    /**
     * Configures the Shared Services roles URL.
     *
     * @param rolesURL
     *            Shared Services' roles URL. Must be a valid URL.
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining.
     */
    public TrueNTHServiceBuilder rolesURL(final String rolesURL) {

	Preconditions.checkValidUrl(rolesURL, "rolesURL is not a valid URL");
	this.rolesURL = rolesURL;
	return this;
    }

    /**
     * Configures the OAuth scope.
     *
     * <p>
     * This is only necessary in some APIs.
     * </p>
     *
     * <p>
     * Example: email.
     * </p>
     *
     * @param scope
     *            The OAuth scope.
     * @return The {@link TrueNTHServiceBuilder} instance for method chaining
     */
    public TrueNTHServiceBuilder scope(final String scope) {

	Preconditions.checkEmptyString(scope, "Invalid OAuth scope");
	this.scope = scope;
	return this;
    }

    /**
     * Configures the signature type, chosen between header or querystring.
     *
     * <p>
     * Defaults to Header.
     * </p>
     *
     * @param type
     *            The OAuth signature type.
     * @return The ServiceBuilder instance for method chaining
     */
    public TrueNTHServiceBuilder signatureType(final SignatureType type) {

	Preconditions.checkNotNull(type, "Signature type can't be null");
	signatureType = type;
	return this;
    }

}
