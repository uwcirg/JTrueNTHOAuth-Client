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
