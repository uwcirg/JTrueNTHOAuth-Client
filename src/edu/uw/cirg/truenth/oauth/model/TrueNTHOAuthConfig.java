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
package edu.uw.cirg.truenth.oauth.model;

import java.io.OutputStream;

import org.scribe.model.OAuthConfig;
import org.scribe.model.SignatureType;

import edu.uw.cirg.truenth.oauth.builder.api.TrueNTHOAuthProvider;

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
     * URL that receives the access token status requests.
     */
    private final String accessTokenStatusEndpoint;

    /**
     * The redirection URL where users authenticate.
     */
    private final String baseAuthorizationURL;

    /**
     * Shared Services base URL.
     * <p>
     * This URL points to Shared Services base URL, it should not be used for
     * OAuth operations, but for fetching static resources, such as SSS. It is
     * mainly used for templates.
     * </p>
     */
    private final String baseURL;

    /**
     * Resource URL.
     */
    private final String resourceURL;

    /**
     * Shared Services' roles URL.
     */
    private final String rolesURL;

    /**
     * Constructor.
     *
     * @param key
     *            APP key, distributed by SS (application ID).
     * @param secret
     *            APP secret, distributed by SS.
     * @param accessTokenEndpointURL
     *            URL that receives the access tokens requests.
     * @param accessTokenStatusEndpointURL
     *            URL that receives the access tokens status requests.
     * @param baseAuthorizationURL
     *            The redirection URL where users authenticate.
     * @param baseURL
     *            Shared Services' base URL.
     * @param resourceURL
     *            Shared Services' resource URL (protected API base).
     * @param rolesURL
     *            Shared Services' roles URL.
     * @param callback
     *            Point where to redirect users after receiving a code to
     *            request access tokens.
     * @param signatureType
     *            Type of signature, to be used when issuing requests.
     * @param scope
     *            Authorization scope.
     * @param stream
     *            Output stream.
     */
    public TrueNTHOAuthConfig(final String key, final String secret, final String accessTokenEndpointURL, final String accessTokenStatusEndpointURL,
	    final String baseAuthorizationURL, final String baseURL, final String resourceURL, final String rolesURL, final String callback,
	    final SignatureType signatureType, final String scope, final OutputStream stream) {

	super(key, secret, callback, signatureType, scope, stream);
	accessTokenEndpoint = accessTokenEndpointURL;
	accessTokenStatusEndpoint = accessTokenStatusEndpointURL;
	this.baseAuthorizationURL = baseAuthorizationURL;

	this.baseURL = baseURL;
	this.resourceURL = resourceURL;
	this.rolesURL = rolesURL;

    }

    /**
     * Returns the URL that receives the access token requests.
     *
     * @return Request token URL.
     */
    public String getAccessTokenEndpoint() {

	return accessTokenEndpoint;
    }

    /**
     * Returns the URL that receives the access token status requests.
     *
     * @return Request token status URL.
     */
    public String getAccessTokenStatusEndpoint() {

	return accessTokenStatusEndpoint;
    }

    /**
     * Returns the redirection URL where users authenticate.
     *
     * <p>
     * This URL will be the base for building new authorization URLs, not being
     * directly suitable for that use as encoding needs to be applied and
     * parameters added.
     * </p>
     *
     * @see TrueNTHOAuthProvider#getAuthorizationUrl(TrueNTHOAuthConfig)
     * @return Base for authorization URLs.
     */
    public String getBaseAuthorizationURL() {

	return baseAuthorizationURL;
    }

    /**
     * Returns the configured Shared Services base URL.
     *
     * <p>
     * This URL points to Shared Services base URL, it should not be used for
     * OAuth operations, but for fetching static resources, such as CSS. It is
     * mainly used for templates.
     * </p>
     *
     * @return Configured Shared Services base URL.
     */
    public String getBaseURL() {

	return baseURL;
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
     * Returns the configured roles URL.
     *
     * @return Roles URL.
     */
    public String getRolesURL() {

	return rolesURL;
    }

}
