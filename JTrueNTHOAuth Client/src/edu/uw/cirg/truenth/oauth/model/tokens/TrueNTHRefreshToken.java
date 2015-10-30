package edu.uw.cirg.truenth.oauth.model.tokens;

import java.io.Serializable;

import org.scribe.utils.Preconditions;

/**
 * Refresh token model.
 * 
 * @author Victor de Lima Soares
 * @since 0.5 Oct 23, 2015
 * @version 1.0
 *
 */
public class TrueNTHRefreshToken implements Serializable {

    private static final long serialVersionUID = 4351396514937408267L;
    
    private String token;

    public TrueNTHRefreshToken(String token) {

	setToken(token);
    }

    public String getToken() {

	return token;
    }

    private void setToken(String token) {

	Preconditions.checkEmptyString(token, "Refresh token cannot be null or empty.");
	this.token = token;
    }

    @Override
    public int hashCode() {

	return token.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) return true;
	if (!(obj instanceof TrueNTHRefreshToken)) return false;
	
	TrueNTHRefreshToken other = (TrueNTHRefreshToken) obj;
	return token.equals(other.token);
    }
    
    @Override
    public String toString() {
        return getToken();
    }

}
