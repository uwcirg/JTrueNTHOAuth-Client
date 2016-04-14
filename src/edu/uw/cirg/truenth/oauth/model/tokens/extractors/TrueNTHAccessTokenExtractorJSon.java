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
package edu.uw.cirg.truenth.oauth.model.tokens.extractors;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;

import org.scribe.utils.Preconditions;

import edu.uw.cirg.truenth.oauth.model.definitions.TrueNTHOAuthConstants;
import edu.uw.cirg.truenth.oauth.model.tokens.TrueNTHAccessToken;

/**
 * TrueNTH access token extractor for JsonObject instances.
 *
 * @author Victor de Lima Soares
 * @since Oct 22, 2015
 *
 */
public class TrueNTHAccessTokenExtractorJSon implements TrueNTHAccessTokenExtractor<JsonObject> {

    /**
     * Reads a TrueNTH access token from a JsonObject instance.
     *
     * @param data
     *            Data containing the token.
     * @return Token extracted.
     * @throws IllegalArgumentException
     *             If an error message is received or when the data is
     *             incomplete.
     */
    @Override
    public TrueNTHAccessToken extract(final JsonObject data) {

	final JsonString error = data.getJsonString(TrueNTHOAuthConstants.ERROR);
	if (error != null) { throw new IllegalArgumentException("Error: " + error.toString() + "\n" + data.toString()); }
	try {

	    final String accessToken = data.getString(TrueNTHAccessToken.Parameters.ACCESS_TOKEN.toString()).trim();
	    final long expiresIn = data.getJsonNumber(TrueNTHAccessToken.Parameters.EXPIRES_IN.toString()).longValue();
	    final String refreshToken = data.getString(TrueNTHAccessToken.Parameters.REFRESH_TOKEN.toString()).trim();
	    final String scope = data.getString(TrueNTHAccessToken.Parameters.SCOPE.toString()).trim();
	    final String tokenType = data.getString(TrueNTHAccessToken.Parameters.TOKEN_TYPE.toString()).trim();

	    return new TrueNTHAccessToken(accessToken, expiresIn, refreshToken, scope, tokenType);
	} catch (final NullPointerException e) {
	    throw new IllegalArgumentException("Error: data incomplete: \n" + data.toString());
	}
    }

    /**
     * Reads a TrueNTH access token from a String instance, JSon formated.
     *
     * <p>
     * This method should expects a JSon formated string.
     * </p>
     *
     * @param data
     *            Data containing the token.
     * @return Token extracted.
     */
    @Override
    public TrueNTHAccessToken extract(final String data) {

	Preconditions.checkEmptyString(data, "No data to extract");
	final JsonObject json = Json.createReader(new StringReader(data)).readObject();
	return extract(json);
    }
}
