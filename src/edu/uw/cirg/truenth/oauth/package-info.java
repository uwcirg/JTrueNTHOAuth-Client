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
/**
 * TrueNTHConnect OAuth implementation.
 *
 * <p>
 * This package provides core OAuth functionality, following the restrictions imposed by our Shared Services (CS).
 * </p>
 * <p>
 * The current implementation includes a Scribe compatible set of components, implementing Scribe interfaces and hierarchy.
 * Mainly, it provides a Service and Provider implementation, following Scribe's terminology.
 * </p>
 * <p>
 * Definitions:
 * </p>
 * <ul>
 * <li>Provider: Defines the core communication configuration, according to a provider's defined protocol and applies the provider's protocol when requesting data; for instance, it generates a provider specific authorization URL. </li>
 * <li>Service: Offers client services, such as requesting tokens.</li>
 * <li>Builder: Builds a configured service, using a specific provider.</li>
 * </ul>
 *
 * <p>
 * Dependences:
 * </p>
 * <ul>
 * <li>Scribe 1.3;</li>
 * <li>Apache Commons Codec 1.10;</li>
 * <li>javax.json 1.0.4.</li>
 * </ul>
 *
 * @author Victor de Lima Soares
 * @since Sep 11, 2015
 */
package edu.uw.cirg.truenth.oauth;