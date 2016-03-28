package edu.uw.cirg.truenth.ss.roles;

/**
 * Properties for the SS's roles protocol.
 *
 * @author Victor de Lima Soares
 * @since Mar 28, 2016
 */
public enum SSRolesProtocolProperties {

    DESCRIPTION("description"), NAME("name"), ROOT("roles");

    private String propertyName;

    /**
     * Constructor: defines the property's name.
     *
     * @param propName
     *            Property's name.
     */
    private SSRolesProtocolProperties(final String propName) {

	propertyName = propName;
    }

    /**
     * Returns the name of the property represented by the enum constant.
     */
    @Override
    public String toString() {

	return propertyName;
    }
}
