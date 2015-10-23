package edu.uw.cirg.truenth.oauth.model.tokens;

import org.scribe.model.Token;

import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHTokenType;

/**
 * TrueNTH token model.
 * 
 * @author Victor de Lima Soares
 * @since 0.5 Oct 22, 2015
 * @version 1.0
 *
 */
public abstract class TrueNTHToken extends Token {

    private static final long serialVersionUID = 5316699289087986474L;

    private TrueNTHTokenType  tokenType;

    /**
     * Constructor.
     *
     * @since 0.5
     * @param token
     *            Token value. Can't be null.
     * @param tokenType
     *            Token type.
     */
    public TrueNTHToken(String token, String tokenType) {

	this(token, null, tokenType);
    }

    /**
     * Constructor with raw data for record keeping.
     *
     * @since 0.5
     * @param token
     *            Token value. Can't be null.
     * @param tokenType
     *            Token type. Can't be null and should represent a supported
     *            token type.
     * @param rawResponse
     *            Raw token data.
     * @see TrueNTHTokenType
     */
    public TrueNTHToken(String token, String rawResponse, String tokenType) {

	super(token, "", rawResponse);
	setTokenType(tokenType);
    }

    /**
     * Set the token type.
     * 
     * @since 0.5
     * @param tokenType
     * @throws IllegalArgumentException
     *             If {@link TrueNTHTokenType} has no constant with the
     *             specified name.
     * @throws NullPointerException
     *             If name is null.
     * @see TrueNTHTokenType
     */
    private void setTokenType(String tokenType) {

	this.tokenType = TrueNTHTokenType.getByName(tokenType);
    }

    /**
     * Access token type.
     * 
     * @since 0.5
     * @return Token type.
     */
    public TrueNTHTokenType getTokenType() {

	return tokenType;
    }
    
    @Override
    public boolean equals(Object o)
    {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      TrueNTHToken other = (TrueNTHToken) o;
      String token = getToken();
      return token.equals(other.getToken()) && tokenType==other.getTokenType();
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = getToken().hashCode() ;
	result = prime * result + tokenType.hashCode();
	return result;
    }

}
