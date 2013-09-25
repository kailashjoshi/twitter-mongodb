package eim.casestudy.twitter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * 
 * @author Kailash Joshi
 *
 */
public class Helper {
	private static String timeStamp = "";
	private static String nononce = "";

	/**
	 * Generate Timestamp
	 * 
	 * @return the timeStamp
	 */
	public static String getTimeStamp() {
		if (timeStamp.equals("")) {
			timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		}
		return timeStamp;
	}

	/**
	 * Generate Unique id for oauth_nononce
	 * 
	 * @return the nononce
	 */
	public static String getNononce() {
		if (nononce.equals("")) {
			nononce = UUID.randomUUID().toString().replaceAll("-", "");
		}
		return nononce;
	}

	/**
	 * URL encode
	 * 
	 * @return encoded string
	 */
	public static String encode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8").replaceAll("\\+", "%20")
					.replaceAll("\\*", "%2A").replaceAll("\\s", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generate Signature Snippet from
	 * https://github.com/cyrus7580/twitter_api_examples
	 * /blob/master/src/net/adkitech/Twitter.java
	 * 
	 * @param baseString
	 * @param keyString
	 * @return
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	public static String computeSignature(String baseString, String keyString)
			throws GeneralSecurityException, UnsupportedEncodingException {
		SecretKey secretKey = null;
		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(secretKey);
		byte[] text = baseString.getBytes();

		return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
	}
}
