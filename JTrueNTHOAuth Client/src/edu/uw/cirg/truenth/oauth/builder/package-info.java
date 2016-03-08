/**
 * Service builders.
 * 
 * <p>
 * This package contains the necessary classes to build a working service given a defined provider.
 * </p>
 * <p>
 * Definitions:
 * </p>
 * <ul>
 * <li>Provider: Defines the core communication configuration, according to a provider's defined protocol and applies provider's protocol when requested for data; for instance, it generates a provider specific authorization URL. </li>
 * <li>Service: Offers client services, such as requesting tokens.</li>
 * <li>Builder: Builds a configured service, using a specific provider.</li>
 * </ul>
 * 
 * @author Victor de Lima Soares
 * @since Sep 25, 2015
 */
package edu.uw.cirg.truenth.oauth.builder;