package main.master;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.*;

import api.model.Get;

public class ApiResponse {

	public static Object requestJson(String endPointUrl,
			Object requestModel, String requestType,
			Map<String, String> headers, Object responseModel) throws JsonParseException, JsonMappingException, IOException {
		HttpResponse response = null;
		String responseData = "";
		try {
			if ((!endPointUrl.startsWith("http")) || (headers == null)
					|| (requestType.isEmpty())) {
				throw new IOException();
			}
			HttpClient client = HttpClientBuilder.create().build();
			if (requestType.equals("get")) {
				HttpGet httpGet = getRequest(endPointUrl,(Get) requestModel, headers);
				response = client.execute(httpGet);

			} else if (requestType.equals("post")) {
				HttpPost httpPost = postRequest(headers, requestModel, endPointUrl);
				response = client.execute(httpPost);

			}
			responseData = EntityUtils.toString(response.getEntity());
			
		} catch (ClientProtocolException e) {
			System.out.println("Bad Request");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Bad Request");
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(responseData, responseModel.getClass());

	}

	private static HttpPost postRequest(Map<String, String> headers,
			Object requestModel, String url)
			throws JsonGenerationException, JsonMappingException, JSONException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonRequest = new JSONObject(mapper.writeValueAsString(requestModel));
		HttpPost httpPost = new HttpPost(url);
		Set<String> keySet = headers.keySet();
		for (String key : keySet) {
			String value = headers.get(key);
			if (TextUtils.isEmpty(value)) {
				value = "";
			}
			httpPost.addHeader(key, value);
		}
		StringEntity se;
		if(keySet.contains("contentType") && headers.get("contentType").equals("application/json"))
			se = new StringEntity(jsonRequest.toString(),ContentType.APPLICATION_JSON);
		else
			se = new StringEntity(jsonRequest.toString());
		httpPost.setEntity(se);
		return httpPost;
	}

	private static HttpGet getRequest(String url, Get requestModel,
			Map<String, String> headers) {
		String uri = null;
		Map<String, String> requestParameters  = requestModel.getRequestParameters();
		try {
			if (requestParameters != null)
				uri = ApiResponse.generateGetUrl(url, requestParameters);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpGet httpGet = new HttpGet(uri);
		Set<String> keySet = headers.keySet();
		for (String key : keySet) {
			String value = headers.get(key);
			if (TextUtils.isEmpty(value)) {
				value = "";
			}
			httpGet.addHeader(key, value);
		}
		return httpGet;
	}

	public static String generateGetUrl(String url, Map<String, String> requestParameters)
			throws URISyntaxException {

		if (requestParameters != null) {
			URIBuilder uriBuilder = new URIBuilder(url);
			Set<String> keySet = requestParameters.keySet();
			for (String key : keySet) {
				String value = (String) requestParameters.get(key);
				if (TextUtils.isEmpty(value)) {
					value = "";
				}
				uriBuilder.addParameter(key, value);
			}
			return uriBuilder.build().toString();
		} else
			return url;

	}
	
}



