package edu.uw.cirg.truenth.oauth.model.tokens.extractors;

import org.scribe.extractors.AccessTokenExtractor;

import edu.uw.cirg.truenth.oauth.model.tokens.TrueNTHAccessToken;

/**
 * TrueNTH access token extractor.
 * 
 * @author Victor de Lima Soares
 * @since 0.5 Oct 22, 2015
 * @version 1.0
 *
 */
public interface TrueNTHAccessTokenExtractor<T> extends AccessTokenExtractor {

    /**
     * Reads a TrueNTH access token from a object of type T.
     * 
     * @since 0.5
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
     * @since 0.5
     * @param data
     *            Data containing the token.
     * @return Token extracted.
     */
    TrueNTHAccessToken extract(String data);
}
