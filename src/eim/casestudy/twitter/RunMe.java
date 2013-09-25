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
	public static void getTwitterStream(String status) {

		String oathSignature = null;
		oathSignature = OathSignature.generateOauthSignature(status);
		String header = OathHeader.header(status, oathSignature);
		String body = OathBody.body(status);
		OathHttpClient.makeHttpRequest(header, body);
	}

	public static void main(String[] args) {
		getTwitterStream("Big Data,EMC,Greenplum,Data mining,hadoop,distributed system");
	}
}
