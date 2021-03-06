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
package edu.uw.cirg.truenth.oauth.model.definitions;

/**
 * TrueNTH specific OAuth Constants.
 *
 * @author Victor de Lima Soares
 * @since Sep 11, 2015
 */
public class TrueNTHOAuthConstants {

    /**
     * Error messages' identification, sent by SS.
     */
    public static final String ERROR    = "error";

    /**
     * Name of "next" parameter.
     *
     * <p>
     * <b>Removed from the SS's protocols</b>.
     * </p>
     *
     * <p>
     * Used by third parties to redirect users after an OAuth call. Per SS
     * rules.
     * </p>
     *
     * <p>
     * This parameter should be embedded into the authorization URLs, as a
     * regular parameter.
     * </p>
     *
     */
    @Deprecated
    public static final String NEXT     = "next";

    /**
     * Used to internally redirect users.
     *
     * <p>
     * Used internally to redirect users after an OAuth call.
     * </p>
     *
     * <p>
     * This parameter is similar to <code>next</code>, as it is destined to
     * perform redirections made by the local server that originated the OAuth
     * calls. <code>next</code> was used the redirect clients coming from SS,
     * after an OAuth login operation, but was removed from the protocol.
     * </p>
     *
     * <p>
     * On normal circumstances, this parameter should be preferred as it allows
     * the server to control redirection with more flexibility. Additionally,
     * redirection can occur faster, and more efficiently.
     * </p>
     *
     * <p>
     * This parameter should be embedded into the callback URLs.
     * </p>
     */
    public static final String REDIRECT = "redirect";
}
