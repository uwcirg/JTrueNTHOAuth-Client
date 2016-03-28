package edu.uw.cirg.truenth.ss.roles;

import java.util.List;

/**
 * Generic role information extractor.
 *
 * @author Victor de Lima Soares
 * @since Mar 28, 2016
 *
 * @param <T>
 *            Data type of the information source.
 */
public interface SSRoleExtractor<T> {

    /**
     * Extracts: description.
     *
     * @param data
     *            Data origin.
     * @return Role description.
     */
    String extractDescription(T data);

    /**
     * Extracts: name.
     *
     * @param data
     *            Data origin.
     * @return Role name.
     */
    String extractName(T data);

    /**
     * Extracts: SSRole instance.
     *
     * @param data
     *            Data origin.
     * @return Extracted SS role.
     */
    SSRole extractRole(T data);

    /**
     * Extracts: SSRoles from an array of roles.
     *
     * <p>
     * Example (JSON):
     * </p>
     *
     * <PRE>
     * {
     *  "roles": [
     *     {
     *         description": "Administrator privileges, i.e. carte blanche",
     *         "name": "admin"
     *      }
     *   ]
     * }
     * </PRE>
     *
     *
     * @param data
     *            Data origin.
     * @return Extracted SS roles.
     * @see #extractRole(T)
     */
    List<SSRole> extractRoles(T data);
}
