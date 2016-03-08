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

    /**
     * Sets token's life span.
     * 
     * @param expiresIn
     *            The amount of time this token will remain valid.
     */
    private void setExpiresIn(long expiresIn) {

	this.expiresIn = expiresIn;
    }

    /**
     * Sets refresh token.
     * 
     * @param refreshToken
     *            Token to be used when this token is no longer valid.
     */
    private void setRefreshToken(String refreshToken) {

	this.refreshToken = new TrueNTHRefreshToken(refreshToken);
    }

    /**
     * Sets token scope.
     * 
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
     * @since Oct 22, 2015
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

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + (int) (expiresIn ^ (expiresIn >>> 32));
	result = prime * result + refreshToken.hashCode();
	result = prime * result + ((scope == null) ? 0 : scope.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) return true;
	if (!super.equals(obj)) return false;
	if (!(obj instanceof TrueNTHAccessToken)) return false;

	TrueNTHAccessToken other = (TrueNTHAccessToken) obj;

	if (expiresIn != other.expiresIn) return false;
	if (refreshToken == null) {
	    if (other.refreshToken != null) return false;
	} else if (!refreshToken.equals(other.refreshToken)) return false;
	if (scope == null) {
	    if (other.scope != null) return false;
	} else if (!scope.equals(other.scope)) return false;
	return true;
    }

}
