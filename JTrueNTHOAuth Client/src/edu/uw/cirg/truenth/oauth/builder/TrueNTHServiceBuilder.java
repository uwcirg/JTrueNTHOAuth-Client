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
 * @since 0.5 Sep 21, 2015
 * @version 2.0
 *
 */
public class TrueNTHServiceBuilder {

    /**
     * URL that receives the access token requests.
     * 
     * @since 0.5
     */
    private String	       accessTokenEndpointURL;

    /**
     * The redirection URL where users authenticate.
     * 
     * @since 0.5
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
     * 
     * @since 1.5
     */
    private String	       rolesURL;

    /**
     * Resource URL.
     * 
     * @since 1.5
     */
    private String	       resourceURL;

    /**
     * Central Services base URL.
     * <p>
     * This URL points to central services base URL, it should not be used for
     * OAuth operation, but for fetching static resources, such as css. It is
     * mainly used for templates.
     * </p>
     * 
     * @since 1.5
     */
    private String	       baseURL;

    /**
     * Default constructor
     * 
     * @since 0.5
     */
    public TrueNTHServiceBuilder() {

	this.signatureType = SignatureType.Header;
    }

    /**
     * Configures the {@link TrueNTHOAuthProvider} ({@link Api}).
     * 
     * @since 0.5
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
     * @since 0.5
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
     * @since 0.5
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
     * @since 1.5
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
     * @since 1.5
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
     * @since 1.5
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
     * @since 0.5
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
     * @since 0.5
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
     * @since 0.5
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
     * @since 0.5
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
     * @since 0.5
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
     * @since 0.5
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
     * @since 0.5
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
