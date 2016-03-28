package edu.uw.cirg.truenth.ss.roles;

/**
 * Basic representation for TrueNTH (SS) roles.
 * 
 * <p>
 * Shared service's roles require two fields to be minimally represented: name
 * and description. This class provides a basic bean to hold this information,
 * as provided by SS. For most purposes, this class should be enough, but a
 * local interpretation of such information might be necessary.
 * </p>
 * 
 * @author Victor de Lima Soares
 * @since Mar 28, 2016
 *
 */
public class SSRole {

    private String name;
    private String description;

    public SSRole() {

	name = "";
	description = "";
    }

    public SSRole(String ssName, String ssDescription) {

	this.name = ssName;
	this.description = ssDescription;
    }

    public String getName() {

	return name;
    }

    public void setName(String ssName) {

	this.name = ssName;
    }

    public String getDescription() {

	return description;
    }

    public void setDescription(String ssDescription) {

	this.description = ssDescription;
    }

}
