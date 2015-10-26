package edu.uw.cirg.truenth.oauth.model;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.json.Json;
import javax.json.JsonObject;

import org.apache.commons.codec.binary.Base64;

/**
 * TrueNTH "signed requests" model.
 * 
 * @author Victor de Lima Soares
 * @since 0.5 Oct 21, 2015
 * @version 2.0
 */
public class TrueNTHSignedRequest {

    private String	       signature;
    private JsonObject	   data;

    private final static Charset charset   = StandardCharsets.UTF_8;
    private final static String  algorithm = "HmacSHA256";
    private final static Base64  base64    = new Base64(false);
    
    /**
     * Builds a TrueNTHSignedRequest instance from an existing request in a
     * String format.
     * 
     * <p>
     * To be successfully built, the data must have been signed with
     * validationKey, using a know HMAC algorithm.
     * </p>
     * <p>
     * Supported algorithm: "HmacSHA256".
     * </p>
     * <p>
     * Signed request data example(assume JSon format):
     * {"issued_at":1445489995,"user_id":10015,"event"
     * :"logout","algorithm":"HMAC-SHA256"}
     * </p>
     * 
     * @since 1.5
     * @param signed_request
     *            Raw string containing the "signed_request": signature+data.
     * @param validationKey
     *            Company's App Secret.
     * @throws NoSuchAlgorithmException
     *             If the algorithm declared inside the request is not
     *             supported.
     * @throws InvalidKeyException
     *             If the key is not suitable for the algorithm.
     */
    public TrueNTHSignedRequest(String signed_request, String validationKey) throws InvalidKeyException, NoSuchAlgorithmException{
	if (signed_request == null) { throw new NullPointerException("signed_request cannot be null"); }

	String[] signedRequest = signed_request.split("\\.", 2);

	this.signature = new String(base64.decode(signedRequest[0].getBytes(charset)));

	String rawData = signedRequest[1];
	this.data = Json.createReader(new StringReader(new String(base64.decode(rawData.getBytes(charset))))).readObject();

	this.validate(rawData, validationKey);
    };

    /**
     * Validates the data received, matching it with the expected signature.
     * 
     * @since 0.5
     * @param rawData
     *            Raw data received in the "signed_request".
     * @param validationKey
     *            Company App key.
     * @throws NoSuchAlgorithmException
     *             If the algorithm declared inside the request is not
     *             supported.
     * @throws InvalidKeyException
     *             If the key is not suitable for the algorithm.
     * @throws IllegalStateException
     *             If the key was not the one used to build the signed request.
     */
    private void validate(String rawData, String validationKey) throws NoSuchAlgorithmException, InvalidKeyException {

	if (!data.getString("algorithm").equals("HMAC-SHA256")) { throw new NoSuchAlgorithmException("Unknown signature algorithm."); }

	if (!hmacSHA256(rawData, validationKey).equals(signature)) { throw new IllegalStateException("Signature is not correct."); }
    }

    /**
     * Calculates the expected signature for the received data.
     * 
     * @since 0.5
     * @param data
     *            Raw data received in the "signed_request".
     * @param key
     *            Company App key.
     * @return Expected signature for the received data.
     * @throws NoSuchAlgorithmException
     *             If the algorithm declared inside the request is not
     *             supported.
     * @throws InvalidKeyException
     *             If the key is not suitable for the algorithm.
     */
    private String hmacSHA256(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {

	SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(charset), algorithm);
	Mac mac = Mac.getInstance(algorithm);
	mac.init(secretKey);
	byte[] hmacData = mac.doFinal(data.getBytes(charset));
	return new String(hmacData);
    }

    /**
     * Access the signature used.
     * 
     * @since 0.5
     * @return Signed request's signature.
     */
    public String getSignature() {

	return signature;
    }

    /**
     * Access the extracted JSon data.
     * 
     * @since 0.5
     * @return Signed request's data.
     */
    public JsonObject getData() {

	return data;
    }

}
