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

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * JSON role information extractor.
 *
 * @author Victor de Lima Soares
 * @since Mar 28, 2016
 */
public class SSRoleJsonExtractor implements SSRoleExtractor<JsonObject> {

    @Override
    public String extractDescription(final JsonObject data) {

	return data.getString(SSRolesProtocolProperties.DESCRIPTION.toString());
    }

    @Override
    public String extractName(final JsonObject data) {

	return data.getString(SSRolesProtocolProperties.NAME.toString());
    }

    @Override
    public SSRole extractRole(final JsonObject data) {

	final SSRole role = new SSRole();
	role.setName(extractName(data));
	role.setDescription(extractDescription(data));
	return role;
    }

    @Override
    public List<SSRole> extractRoles(final JsonObject data) {

	final JsonArray rolesData = data.getJsonArray(SSRolesProtocolProperties.ROOT.toString());
	if (rolesData == null) { return null; }

	final ArrayList<SSRole> roleList = new ArrayList<SSRole>(rolesData.size());

	SSRole role;

	for (final JsonObject roleData : rolesData.getValuesAs(JsonObject.class)) {
	    role = extractRole(roleData);
	    roleList.add(role);
	}

	return roleList;
    }
}
