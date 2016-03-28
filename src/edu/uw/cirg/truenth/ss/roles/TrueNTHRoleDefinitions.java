package edu.uw.cirg.truenth.ss.roles;

/**
 * Properties for the SS's roles protocol.
 * 
 * @author Victor de Lima Soares
 * @since Mar 28, 2016
 */
public enum TrueNTHRoleDefinitions {

    ROOT("roles"), DESCRIPTION("description"), NAME("name");

    private String propertyName;

    /**
     * Constructor: defines the property's name.
     * 
     * @param propName
     *            Property's name.
     */
    private TrueNTHRoleDefinitions(String propName) {

	propertyName = propName;
    }

    /**
     * Returns the name of the property represented by the enum constant.
     */
    public String toString() {

	return propertyName;
    }
}
