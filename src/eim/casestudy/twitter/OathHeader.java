package eim.casestudy.twitter;
/**
 *  
 * @author Kailash Joshi
 *
 */
public class OathHeader {

	private static String quote = "\"";
	private static String comma = ",";
/**
 * Generate Header for streaming public tweets
 * @param track
 * @param signature
 * @return
 */
	public static String header(String track, String signature) {
		StringBuilder sb = new StringBuilder();
		sb.append("OAuth ");
		sb.append(Helper.encode("oauth_consumer_key"));
		sb.append("=").append(quote)
				.append(Helper.encode(Conf.CONSUMER_KEY));
		sb.append(quote).append(comma).append(Helper.encode("oauth_nonce"));
		sb.append("=").append(quote).append(Helper.encode(Helper.getNononce()));
		sb.append(quote).append(comma).append(Helper.encode("oauth_signature"));
		sb.append("=").append(quote).append(Helper.encode(signature));
		sb.append(quote).append(comma)
				.append(Helper.encode("oauth_signature_method"));
		sb.append("=").append(quote).append(Helper.encode("HMAC-SHA1"));
		sb.append(quote).append(comma).append(Helper.encode("oauth_timestamp"));
		sb.append("=").append(quote)
				.append(Helper.encode(Helper.getTimeStamp()));
		sb.append(quote).append(comma).append(Helper.encode("oauth_token"));
		sb.append("=").append(quote)
				.append(Helper.encode(Conf.ACCESS_TOKEN));
		sb.append(quote).append(comma).append(Helper.encode("oauth_version"));
		sb.append("=").append(quote).append(Helper.encode("1.0")).append(quote);
		return sb.toString();
	}
}
