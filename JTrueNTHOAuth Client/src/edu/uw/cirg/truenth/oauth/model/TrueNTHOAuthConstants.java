package edu.uw.cirg.truenth.oauth.model;

/**
 * TrueNTH specific OAuth Constants.
 * 
 * @author Victor de Lima Soares
 * @since 0.5 Sep 11, 2015
 * @version 2.0
 */
public class TrueNTHOAuthConstants {

    /**
     * Name of "grant type" parameter.
     * 
     * @since 0.5
     * @see TrueNTHGrantType
     */
    public static final String GRANT_TYPE = "grant_type";

    /**
     * Name of "next" parameter.
     * 
     * <p>
     * Used by third parties to redirect users after an OAuth call. Per CS
     * rules.
     * </p>
     * <p>
     * This parameter should be embedded into the authorization URLs, as a regular parameter.
     * </p>
     * 
     * @since 1.5
     */
    public static final String NEXT       = "next";

    /**
     * Used to internally redirect users.
     * 
     * <p>
     * Used internally to redirect users after an OAuth call.
     * </p>
     * <p>
     * This parameter differs from <code>next</code>, as it is destined to
     * perform redirections made by the server that originated the OAuth calls.
     * On the other hand, <code>next</code> is used the redirect clients from
     * CS.
     * </p>
     * <p>
     * On normal circumstances, this parameter should be preferred as it allows
     * the server to control redirection with more flexibility. Additionally,
     * redirection can occur faster, as soon as the server gets the access token.
     * </p>
     * <p>
     * This parameter should be embedded into the callback URLs.
     * </p>
     * 
     * @since 1.5
     */
    public static final String REDIRECT   = "redirect";
}
