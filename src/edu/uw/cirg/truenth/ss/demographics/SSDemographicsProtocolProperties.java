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

/**
 * Constants for TrueNTH (SS) demographics API, protocol fields.
 *
 * @author Victor de Lima Soares
 * @since Mar 25, 2016
 */
public enum SSDemographicsProtocolProperties {

    BIRTH_DATE("birthDate"),
    COMMUNICATION("communication"),
    GENDER("gender"),
    GENDER_FEMALE("female"),
    GENDER_MALE("male"),
    IDENTIFIER("identifier"),
    IDENTIFIER_LABEL("label"),
    IDENTIFIER_TRUENTH_ID("Truenth identifier"),
    IDENTIFIER_TRUENTH_USERNAME("Truenth username"),
    IDENTIFIER_VALUE("value"),
    NAME("name"),
    NAME_FAMILY("family"),
    NAME_GIVEN("given"),
    PHOTO("photo"),
    PHOTO_URL("url"),
    TELECOM("telecom"),
    TELECOM_SYSTEM("system"),
    TELECOM_SYSTEM_EMAIL("email"),
    TELECOM_SYSTEM_VALUE("value");

    private String propertyName;

    /**
     * Constructor.
     *
     * @param propName
     *            Property name.
     */
    private SSDemographicsProtocolProperties(final String propName) {

	propertyName = propName;
    }

    /**
     * Returns the property name represented by the enum constant.
     */
    @Override
    public String toString() {

	return propertyName;
    }

}
