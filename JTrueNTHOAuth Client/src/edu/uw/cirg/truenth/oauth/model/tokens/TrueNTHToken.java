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
package edu.uw.cirg.truenth.oauth.model.tokens;

import org.scribe.model.Token;

import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHTokenType;

/**
 * TrueNTH token model.
 * 
 * @author Victor de Lima Soares
 * @since Oct 22, 2015
 *
 */
public abstract class TrueNTHToken extends Token {

    private static final long serialVersionUID = 5316699289087986474L;

    private TrueNTHTokenType  tokenType;

    /**
     * Constructor.
     *
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
     * @return Token type.
     */
    public TrueNTHTokenType getTokenType() {

	return tokenType;
    }
    
    @Override
    public boolean equals(Object obj)
    {
      if (this == obj) return true;
      if (!(obj instanceof TrueNTHToken)) return false;

      TrueNTHToken other = (TrueNTHToken) obj;
      String token = getToken();
      return token.equals(other.getToken()) && tokenType==other.getTokenType();
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result =  getToken().hashCode();
	result = prime * result + tokenType.hashCode();
	return result;
    }

}
