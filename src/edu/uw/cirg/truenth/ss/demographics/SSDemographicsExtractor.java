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
package edu.uw.cirg.truenth.ss.demographics;

import java.net.URL;
import java.util.Calendar;

/**
 * Generic demographic information extractor.
 *
 * @author Victor de Lima Soares
 * @since Mar 25, 2016
 * @param <T>
 *            Data source object type.
 */
public interface SSDemographicsExtractor<T> {

    /**
     * Extracts: birthday.
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>Birthday, if it can be extracted;</li>
     *         <li>new date instance (January 1, 1970), otherwise.</li>
     *         </ul>
     */
    Calendar extractBirthday(T data);

    /**
     * Extracts: email address.
     *
     * @param data
     *            Data origin.
     * @return email address.
     */
    String extractEmail(T data);

    /**
     * Extracts: first name.
     *
     * @param data
     *            Data origin.
     * @return first name.
     */
    String extractFirstName(T data);

    /**
     * Extracts: gender.
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>Gender, listed under the first GENDER_CODING_CODE;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     */
    String extractGender(T data);

    /**
     * Extracts: last name.
     *
     * @param data
     *            Data origin.
     * @return last name.
     */
    String extractLastName(T data);

    /**
     * Extracts: TrueNTH profile picture URL.
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>Image URL, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     */
    URL extractPhotoUrl(T data);

    /**
     * Extracts: TrueNTH ID.
     *
     * @param data
     *            Data origin.
     * @return TrueNTH ID.
     */
    long extractTrueNTHID(T data);

    /**
     * Extracts: TrueNTH username.
     *
     * @param data
     *            Data origin.
     * @return TrueNTH username.
     */
    String extractTrueNTHUsername(T data);
}
