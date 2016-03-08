/**
 * TrueNTHConnect OAuth implementation.
 * 
 * <p>
 * This package provides core OAuth functionality, following the restrictions imposed by our Central Services (CS).
 * </p>
 * <p>
 * The current implementation includes a Scribe compatible set of components, implementing Scribe interfaces and hierarchy.
 * Mainly, it provides a Service and Provider implementation, following Scribe's terminology.
 * </p>
 * Definitions:
 * </p>
 * <ul>
 * <li>Provider: Defines the core communication configuration, according to a provider's defined protocol and applies provider's protocol when requested for data; for instance, it generates a provider specific authorization URL. </li>
 * <li>Service: Offers client services, such as requesting tokens.</li>
 * <li>Builder: Builds a configured service, using a specific provider.</li>
 * </ul>
 * 
 * <p>
 * Dependences:
 * </p>
 * <ul>
 * <li>Scribe >=1.3;</li>
 * <li>Apache Commons Codec >=1.10;</li>
 * <li>javax.json >=1.0.4.</li>
 * </ul>
 * 
 * Communication model:
 * @msg
 * User, Portal, CS 
 * 
 * Portal note Portal [label=Client]
 * CS note CS [label=authorization server,resource server]
 * 
 * Portal -> User [label=Authorization Request]
 * User -> Portal [label=Authorization Grant]
 * Portal -> CS [label=Authorization Grant]
 * CS -> Portal [label= Access Token]
 * Portal -> CS [label=Access Token]
 * CS -> Portal [label=Protected Resource]
 * @endmsc 
 *
 * @author Victor de Lima Soares
 * @since Sep 11, 2015
 */
package edu.uw.cirg.truenth.oauth;