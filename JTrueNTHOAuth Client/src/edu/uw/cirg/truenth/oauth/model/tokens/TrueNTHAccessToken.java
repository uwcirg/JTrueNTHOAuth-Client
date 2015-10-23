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
 * @since 0.5 Oct 22, 2015
 * @version 1.0
 *
 */
public class TrueNTHAccessToken extends TrueNTHToken {

    private static final long serialVersionUID = 329140790394969559L;
    private long	      expiresIn;
    private String	    refreshToken;
    private String	    scope;

    /**
     * Constructor.
     * 
     * @since 0.5
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
    public TrueNTHAccessToken(String token, long expiresIn, String refreshToken, String scope, String tokenType) {

	super(token, tokenType);
	Preconditions.checkEmptyString(token, "Token cannot be null or empty.");
	setExpiresIn(expiresIn);
	setRefreshToken(refreshToken);
	setScope(scope);

    }

    /**
     * Access token's life span.
     * 
     * @since 0.5
     * @return Token expiration time, after creation.
     */
    public long getExpiresIn() {

	return expiresIn;
    }

    /**
     * Access the refresh token to be used when this token is no longer valid.
     * 
     * @since 0.5
     * @return Refresh token.
     */
    public String getRefreshToken() {

	return refreshToken;
    }

    /**
     * Access the scope where this token is valid.
     * 
     * @since 0.5
     * @return Token's scope.
     */
    public String getScope() {

	return scope;
    }

    /**
     * Sets token's life span.
     * 
     * @since 0.5
     * @param expiresIn
     *            The amount of time this token will remain valid.
     */
    private void setExpiresIn(long expiresIn) {

	this.expiresIn = expiresIn;
    }

    /**
     * Sets refresh token.
     * 
     * @since 0.5
     * @param refreshToken
     *            Token to be used when this token is no longer valid.
     */
    private void setRefreshToken(String refreshToken) {

	Preconditions.checkEmptyString(refreshToken, "Refresh token cannot be null or empty.");
	this.refreshToken = refreshToken;
    }

    /**
     * Sets token scope.
     * 
     * @since 0.5
     * @param scope
     *            Scope to which this token is valid.
     */
    private void setScope(String scope) {

	this.scope = scope;
    }

    /**
     * Parameters for a TrueNTH access token.
     * 
     * <p>
     * This parameters represent a name convention to read and write tokens.
     * </p>
     * 
     * @author Victor de Lima Soares
     * @since 0.5 Oct 22, 2015
     * @version 1.0
     *
     */
    public enum Parameters {

	ACCESS_TOKEN("access_token"), TOKEN_TYPE("token_type"), EXPIRES_IN("expires_in"), REFRESH_TOKEN("refresh_token"), SCOPE("scope");

	private String parameter;

	private Parameters(String parameter) {

	    this.parameter = parameter;
	}

	public String toString() {

	    return parameter;
	}

    }
}
