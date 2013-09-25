package eim.casestudy.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.json.JSONObject;
/**
 * 
 * @author Kailash Joshi
 *
 */
public class OathHttpClient {
	private static HttpParams params = null;
	private static HttpProcessor processor = null;
/**
 * Make HTTP Request using apache Httpcore
 * reference https://github.com/cyrus7580/twitter_api_examples/blob/master/src/net/adkitech/Twitter.java 
 * @param authHeader
 * @param requestBody
 */
	public static void makeHttpRequest(String authHeader, String requestBody) {
		setHttpParam();
		setProcessor();
		HttpContext context = new BasicHttpContext(null);
		DefaultHttpClientConnection connection = new DefaultHttpClientConnection();
		context.setAttribute(ExecutionContext.HTTP_CONNECTION, connection);
		context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, new HttpHost(
				"stream.twitter.com", 443));
		try {

			SSLContext ssl = SSLContext.getInstance("TLS");
			ssl.init(null, null, null);
			SSLSocketFactory sslFactory = ssl.getSocketFactory();
			Socket sock = sslFactory.createSocket();
			sock.connect(new InetSocketAddress(new HttpHost(
					"stream.twitter.com", 443).getHostName(), new HttpHost(
					"stream.twitter.com", 443).getPort()), 0);
			connection.bind(sock, getHttpParam());
			BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest(
					"POST", "/1.1/statuses/filter.json");
			request.setEntity(new StringEntity(requestBody,
					ContentType.APPLICATION_FORM_URLENCODED));
			request.setParams(getHttpParam());
			request.addHeader("Authorization", authHeader);

			new HttpRequestExecutor().preProcess(request, getProcessor(),
					context);
			HttpResponse response = new HttpRequestExecutor().execute(request,
					connection, context);

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println("HTTP error");
			}
			response.setParams(getHttpParam());
			new HttpRequestExecutor().postProcess(response, getProcessor(),
					context);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));

			for (String line = null; (line = reader.readLine()) != null;) {
				System.out.println(StringEscapeUtils.unescapeJava(line));
				try{
				JSONObject allCDs = new JSONObject(line.trim());
				MongoInsert.insertData(allCDs.toString());}
				catch(Exception e){
					continue;
				}				
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected static void setHttpParam() {
		params = new SyncBasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setUserAgent(params, "HttpCore/1.1");
		HttpProtocolParams.setUseExpectContinue(params, false);
	}

	protected static HttpParams getHttpParam() {
		return params;
	}

	/**
	 * @return the processor
	 */
	protected static HttpProcessor getProcessor() {
		return processor;
	}

	/**
	 * @param processor
	 *            the processor to set
	 */
	protected static void setProcessor() {
		OathHttpClient.processor = new ImmutableHttpProcessor(
				new HttpRequestInterceptor[] { new RequestContent(),
						new RequestTargetHost(), new RequestConnControl(),
						new RequestUserAgent(), new RequestExpectContinue() });
	}

}
