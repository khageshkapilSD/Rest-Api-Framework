package api.model;

import java.util.HashMap;
import java.util.Map;

public class Get {
	Map<String,String> requestParameters;

	public Get(){
		requestParameters = new HashMap<String, String>();
	}
	public Map<String, String> getRequestParameters() {
		return requestParameters;
	}

	public void addRequestParameter(String key, String value) {
		this.requestParameters.put(key, value);
	}
}
