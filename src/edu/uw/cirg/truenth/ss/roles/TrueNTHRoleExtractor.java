package edu.uw.cirg.truenth.ss.roles;

import java.util.List;

import javax.json.JsonObject;

/**
 * Generic role information extractor.
 * 
 * @author Victor de Lima Soares
 * @since Mar 28, 2016
 * 
 * @param <T>
 *            Data type of the information source.
 */
public interface TrueNTHRoleExtractor<T> {

    /**
     * Extracts: name.
     * 
     * @param data
     *            Data origin.
     * @return Role name.
     */
    String extractName(T data);

    /**
     * Extracts: description.
     * 
     * @param data
     *            Data origin.
     * @return Role description.
     */
    String extractDescription(T data);

    /**
     * Extracts: TrueNTHRole instance.
     * 
     * @param data
     *            Data origin.
     * @return Extracted TrueNTH role.
     */
    SSRole extractRole(JsonObject data);

    /**
     * Extracts: TrueNTHRoles from an array of roles.
     * 
     * <p>Example:</p>
     * 
     *<PRE>
     *{
     * "roles": [
     *    {
     *        description": "Administrator privileges, i.e. carte blanche",
     *        "name": "admin"
     *     }
     *  ]
     *}
     * </PRE>
     * 
     * 
     * @param data
     *            Data origin.
     * @return Extracted TrueNTH roles.
     * @see #extractRole(JsonObject)
     */
    List<SSRole> extractRoles(JsonObject data);
}
