package edu.uw.cirg.truenth.oauth.model.definitions;

/**
 * TrueNTH specific OAuth Constants.
 * 
 * @author Victor de Lima Soares
 * @since Sep 11, 2015
 */
public class TrueNTHOAuthConstants {

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
     */
    public static final String NEXT       = "next";

    /**
     * Used to internally to redirect users.
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
     */
    public static final String REDIRECT   = "redirect";
    
    /**
     * Error messages identification, sent by CS.
     */
    public static final String ERROR = "error";
}
