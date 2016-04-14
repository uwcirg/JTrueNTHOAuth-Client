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
package edu.uw.cirg.truenth.ss.roles;

import java.io.Serializable;

/**
 * Basic representation for TrueNTH (SS) roles.
 *
 * <p>
 * Shared services' roles require two fields to be minimally represented: name
 * and description. This class provides a basic bean to hold this information,
 * as provided by SS. For most purposes, this class should be enough, but a
 * local interpretation of such information might be necessary.
 * </p>
 *
 * @author Victor de Lima Soares
 * @since Mar 28, 2016
 *
 */
public class SSRole implements Serializable {

    private static final long serialVersionUID = 1L;
    private String	    description;
    private String	    name;

    public SSRole(final String ssName, final String ssDescription) {

	name = ssName;
	description = ssDescription;
    }

    public String getDescription() {

	return description;
    }

    public String getName() {

	return name;
    }

    /**
     * Sets the description attribute.
     * 
     * @param ssDescription
     */
    public void setDescription(final String ssDescription) {

	this.description = ssDescription;
    }

    /**
     * Sets the name attribute.
     * 
     * @param ssName
     */
    public void setName(final String ssName) {

	this.name = ssName;
    }

}
