package edu.uw.cirg.truenth.ss.demographics;

import java.net.URL;
import java.util.Calendar;

/**
 * Generic demographic information extractor.
 *
 * @author Victor de Lima Soares
 * @since Mar 25, 2016
 * @param <T>
 *            Data source object type.
 */
public interface SSDemographicsExtractor<T> {

    /**
     * Extracts: birthday.
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>Birthday, if it can be extracted;</li>
     *         <li>new date instance (January 1, 1970), otherwise.</li>
     *         </ul>
     */
    public Calendar extractBirthday(T data);

    /**
     * Extracts: email address.
     *
     * @param data
     *            Data origin.
     * @return email address.
     */
    public String extractEmail(T data);

    /**
     * Extracts: first name.
     *
     * @param data
     *            Data origin.
     * @return first name.
     */
    public String extractFirstName(T data);

    /**
     * Extracts: gender.
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>Gender, listed under the first GENDER_CODING_CODE;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     */
    public String extractGender(T data);

    /**
     * Extracts: last name.
     *
     * @param data
     *            Data origin.
     * @return last name.
     */
    public String extractLastName(T data);

    /**
     * Extracts: TrueNTH profile picture URL.
     *
     * @param data
     *            Data origin.
     *
     * @return <ul>
     *         <li>Image URL, if it can be extracted;</li>
     *         <li>null, otherwise.</li>
     *         </ul>
     */
    public URL extractPhotoUrl(T data);

    /**
     * Extracts: TrueNTH ID.
     *
     * @param data
     *            Data origin.
     * @return TrueNTH ID.
     */
    public long extractTrueNTHID(T data);

    /**
     * Extracts: TrueNTH username.
     *
     * @param data
     *            Data origin.
     * @return TrueNTH username.
     */
    public String extractTrueNTHUsername(T data);
}
