package eim.casestudy.twitter;
/**
 * 
 * @author Kailash Joshi
 *
 */
public class RunMe {
    /**
     * Driver method for starting Public Streaming
     * @param status
     */
	public static void getTwitterStream(String searchKeyword) {

		String oathSignature = null;
		oathSignature = OathSignature.generateOauthSignature(searchKeyword);
		String header = OathHeader.header(searchKeyword, oathSignature);
		String body = OathBody.body(searchKeyword);
		OathHttpClient.makeHttpRequest(header, body);
	}

	public static void main(String[] args) {
		getTwitterStream("Big Data,EMC,Greenplum,Data mining,hadoop,distributed system");
	}
}
