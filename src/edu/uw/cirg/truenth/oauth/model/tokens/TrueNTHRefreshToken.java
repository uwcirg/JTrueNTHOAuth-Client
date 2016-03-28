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

import java.io.Serializable;

import org.scribe.utils.Preconditions;

/**
 * Refresh token model.
 *
 * @author Victor de Lima Soares
 * @since Oct 23, 2015
 *
 */
public class TrueNTHRefreshToken implements Serializable {

    private static final long serialVersionUID = 4351396514937408267L;

    private String	    token;

    public TrueNTHRefreshToken(final String token) {

	setToken(token);
    }

    @Override
    public boolean equals(final Object obj) {

	if (this == obj) { return true; }
	if (!(obj instanceof TrueNTHRefreshToken)) { return false; }

	final TrueNTHRefreshToken other = (TrueNTHRefreshToken) obj;
	return token.equals(other.token);
    }

    public String getToken() {

	return token;
    }

    @Override
    public int hashCode() {

	return token.hashCode();
    }

    private void setToken(final String token) {

	Preconditions.checkEmptyString(token, "Refresh token cannot be null or empty.");
	this.token = token;
    }

    @Override
    public String toString() {

	return getToken();
    }

}
