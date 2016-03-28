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
public class TrueNTHRoleJsonExtractor implements TrueNTHRoleExtractor<JsonObject> {
    
    @Override
    public String extractName(JsonObject data) {
	return data.getString(TrueNTHRoleDefinitions.NAME.toString());
    }

    @Override
    public String extractDescription(JsonObject data) {
	return data.getString(TrueNTHRoleDefinitions.DESCRIPTION.toString());
    }

    @Override
    public SSRole extractRole(JsonObject data) {
	SSRole role = new SSRole();
	role.setName(extractName(data));
	role.setDescription(extractDescription(data));
	return role;
    }
    
    @Override
    public List<SSRole> extractRoles(JsonObject data) {
	JsonArray rolesData = data.getJsonArray(TrueNTHRoleDefinitions.ROOT.toString());
	if(rolesData == null) return null;
	
	ArrayList<SSRole> roleList = new ArrayList<SSRole>(rolesData.size());
	
	SSRole role;
	
	for(JsonObject roleData:rolesData.getValuesAs(JsonObject.class)){
	    role = extractRole(roleData);
	    roleList.add(role);
	}
	
	return roleList;
    }
}
