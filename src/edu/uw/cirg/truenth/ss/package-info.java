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
 * SS packages: artifacts to manipulate SS' data.
 *
 * <p>
 * The SS packages define artifacts that are uniquely used to interpret information coming from SS.
 * As such, client applications can use those transition elements to retrieve data in an organized fashion,
 * which is independent from the transmission format; e.g. JSON.
 * </p>
 *
 * <p>
 * These packages define complementary capabilities to the OAuth functionalities and their use is not required.
 * If the developer decides to define their own implementation,
 * they can choose to use the OAuth classes and ignore the elements defined here.
 * </p>
 *
 * <p>
 * For instance, JSON may be used to receive information,
 * however, the OAuth library can deliver a Java Bean to its clients.
 * Additionally, application developers can decide to use extractors to obtain pieces of data instead.
 * </p>
 *
 * @author Victor de Lima Soares
 * @since Apr 14, 2016
 *
 */
package edu.uw.cirg.truenth.ss;