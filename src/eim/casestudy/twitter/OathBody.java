package eim.casestudy.twitter;
/**
 * 
 * @author Kailash Joshi
 *
 */
public class OathBody {
	/**
	 * Generate Body for streaming public tweets
	 * @param track
	 * @return
	 */
	public static String body(String track){
		StringBuilder sb = new StringBuilder();
		sb.append("track");
		sb.append("=");
		sb.append(Helper.encode(track));
		return sb.toString();
	}
}
