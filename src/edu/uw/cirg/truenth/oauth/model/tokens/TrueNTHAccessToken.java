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
package edu.uw.cirg.truenth.oauth.model.tokens;

import org.scribe.utils.Preconditions;

import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHTokenType;

/**
 * TrueNTH access token model.
 *
 * <p>
 * Immutable after construction.
 * </p>
 *
 * <p>
 * Expected format(JSon): <br>
 * {<br>
 * "access_token": "string",<br>
 * "expires_in": number,<br>
 * "refresh_token": "string",<br>
 * "scope": "string",<br>
 * "token_type": "string" <br>
 * }
 * </p>
 * <p>
 * {<br>
 * "access_token": "lRk6RTR", <br>
 * "token_type": "Bearer", <br>
 * "expires_in": 3600, <br>
 * "refresh_token": "beKTdy", <br>
 * "scope": "email"<br>
 * }
 * </p>
 *
 * @author Victor de Lima Soares
 * @since Oct 22, 2015
 */
public class TrueNTHAccessToken extends TrueNTHToken {

    /**
     * Parameters for a TrueNTH access token.
     *
     * <p>
     * These parameters represent a name convention to read and write tokens.
     * </p>
     *
     * @author Victor de Lima Soares
     * @since Oct 22, 2015
     *
     */
    public enum Parameters {

	ACCESS_TOKEN("access_token"), EXPIRES_IN("expires_in"), REFRESH_TOKEN("refresh_token"), SCOPE("scope"), TOKEN_TYPE("token_type");

	private String parameter;

	private Parameters(final String parameter) {

	    this.parameter = parameter;
	}

	@Override
	public String toString() {

	    return parameter;
	}

    }

    private static final long   serialVersionUID = 329140790394969559L;
    private long		expiresIn;
    private TrueNTHRefreshToken refreshToken;

    private String	      scope;

    /**
     * Constructor.
     *
     * @param token
     *            Token string.
     * @param expiresIn
     *            Token expiration time, after creation.
     * @param refreshToken
     *            Token to be used when this token is no longer valid.
     * @param scope
     *            Scope to which this token is valid.
     * @param tokenType
     *            Token type. This parameter should be a string representation
     *            of one of constants defined in {@link TrueNTHTokenType}.
     */
    public TrueNTHAccessToken(final String token, final long expiresIn, final String refreshToken, final String scope, final String tokenType) {

	super(token, tokenType);
	Preconditions.checkEmptyString(token, "Token cannot be null or empty.");
	setExpiresIn(expiresIn);
	setRefreshToken(refreshToken);
	setScope(scope);

    }

    @Override
    public boolean equals(final Object obj) {

	if (this == obj) { return true; }
	if (!super.equals(obj)) { return false; }
	if (!(obj instanceof TrueNTHAccessToken)) { return false; }

	final TrueNTHAccessToken other = (TrueNTHAccessToken) obj;

	if (expiresIn != other.expiresIn) { return false; }
	if (refreshToken == null) {
	    if (other.refreshToken != null) { return false; }
	} else if (!refreshToken.equals(other.refreshToken)) { return false; }
	if (scope == null) {
	    if (other.scope != null) { return false; }
	} else if (!scope.equals(other.scope)) { return false; }
	return true;
    }

    /**
     * Access token's life span.
     *
     * @return Token expiration time, after creation.
     */
    public long getExpiresIn() {

	return expiresIn;
    }

    /**
     * Access the refresh token to be used when this token is no longer valid.
     *
     * @return Refresh token.
     */
    public TrueNTHRefreshToken getRefreshToken() {

	return refreshToken;
    }

    /**
     * Access the scope where this token is valid.
     *
     * @return Token's scope.
     */
    public String getScope() {

	return scope;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = super.hashCode();
	result = (prime * result) + (int) (expiresIn ^ (expiresIn >>> 32));
	result = (prime * result) + refreshToken.hashCode();
	result = (prime * result) + ((scope == null) ? 0 : scope.hashCode());
	return result;
    }

    /**
     * Sets token's life span.
     *
     * @param expiresIn
     *            The amount of time this token will remain valid.
     */
    private void setExpiresIn(final long expiresIn) {

	this.expiresIn = expiresIn;
    }

    /**
     * Sets refresh token.
     *
     * @param refreshToken
     *            Token to be used when this token is no longer valid.
     */
    private void setRefreshToken(final String refreshToken) {

	this.refreshToken = new TrueNTHRefreshToken(refreshToken);
    }

    /**
     * Sets token scope.
     *
     * @param scope
     *            Scope to which this token is valid.
     */
    private void setScope(final String scope) {

	this.scope = scope;
    }

}
