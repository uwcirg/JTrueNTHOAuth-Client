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
package edu.uw.cirg.truenth.oauth.model.tokens.extractors;

import org.scribe.extractors.AccessTokenExtractor;

import edu.uw.cirg.truenth.oauth.model.tokens.TrueNTHAccessToken;

/**
 * TrueNTH access token extractor.
 * 
 * @author Victor de Lima Soares
 * @since Oct 22, 2015
 *
 */
public interface TrueNTHAccessTokenExtractor<T> extends AccessTokenExtractor {

    /**
     * Reads a TrueNTH access token from a object of type T.
     * 
     * @param data
     *            Data containing the token.
     * @return Token extracted.
     */
    TrueNTHAccessToken extract(T data);

    /**
     * Reads a TrueNTH access token from a String instance.
     * 
     * <p>
     * This method should be able to recognize string representation of the T
     * instance.
     * </p>
     * 
     * @param data
     *            Data containing the token.
     * @return Token extracted.
     */
    TrueNTHAccessToken extract(String data);
}
