package eim.casestudy.twitter;
/**
 * 
 * @author Kailash Joshi
 *
 */

public class OathSignature {

	private static String baseString(String track) {
		StringBuilder tmp = new StringBuilder();
		tmp.append("POST");
		tmp.append("&");
		tmp.append(Helper
				.encode("https://stream.twitter.com/1.1/statuses/filter.json"));
		tmp.append("&");
		tmp.append(Helper.encode("oauth_consumer_key="));
		tmp.append(Helper.encode(Conf.CONSUMER_KEY));
		tmp.append(Helper.encode("&oauth_nonce="));
		tmp.append(Helper.encode(Helper.getNononce()));
		tmp.append(Helper.encode("&oauth_signature_method="));
		tmp.append(Helper.encode("HMAC-SHA1"));
		tmp.append(Helper.encode("&oauth_timestamp="));
		tmp.append(Helper.encode(Helper.getTimeStamp()));
		tmp.append(Helper.encode("&oauth_token="));
		tmp.append(Helper.encode(Conf.ACCESS_TOKEN));
		tmp.append(Helper.encode("&oauth_version="));
		tmp.append(Helper.encode("1.0"));
		tmp.append(Helper.encode("&track="));
		tmp.append(Helper.encode(Helper.encode(track)));
		return tmp.toString();
	}

	private static String keyString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Conf.CONSUMER_SECRET);
		sb.append("&");
		sb.append(Conf.ACCESS_TOKEN_SECRET);
		return sb.toString();
	}
/**
 * Generate oauth signature
 * Example and documentation https://dev.twitter.com/docs/auth/creating-signature 
 * @param track
 * @return
 */
	public static String generateOauthSignature(String track) {
		try {
			return Helper.computeSignature(baseString(track), keyString());
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
