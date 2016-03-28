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

    private static final long serialVersionUID = -6463765730468262298L;
    private String	    description;
    private String	    name;

    public SSRole() {

	name = "";
	description = "";
    }

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

    public void setDescription(final String ssDescription) {

	description = ssDescription;
    }

    public void setName(final String ssName) {

	name = ssName;
    }

}
