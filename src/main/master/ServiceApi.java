package main.master;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

/**
 * This Class acts as an Generic API Page class (Abstration for APIs)
 * @author Khagesh Kapil
 */
public class ServiceApi {
	
	private Object responseModel;
	
	private Map<String,String> requestHeaders = new HashMap<String,String>();
	
	private String requestType;
	
	private String url;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void addRequestHeader(String key, String value){
		requestHeaders.put(key, value);
	}
	
	public ServiceApi(String api) throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Constants constants = new Constants();
		FileFilter fileFilter = new FileFilter();
		Properties apiProperties = new Properties();
		apiProperties.load(new FileInputStream(
				new File(constants.getPath()
						+ Constants.API_RELATIVE_PATH
						+ File.separator
						+ api+".properties")));
		Properties globalProperties = new Properties();
		globalProperties.load(new FileInputStream(
				new File(constants.getPath() 
						+ Constants.API_RELATIVE_PATH 
						+ File.separator
						+ Constants.GLOBAL_CONFIG)));
		setUrl(apiProperties.getProperty("url"));
		setApiEnvironment(globalProperties.getProperty("Test.env"));
		setRequestType(apiProperties.getProperty("request.method"));
		String[] listOfModels = new File( constants.getPath()
				+ Constants.PATH_TO_MODELS ).list(fileFilter);
		
		Iterator<?> iterator = apiProperties.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next().toString(); 
			if(key.startsWith("request.header")){
				addRequestHeader(key.split("request.header.")[1],
						apiProperties.getProperty(key));
			}
		}
		
		for(String modelClass : fileFilter.getUpdateArray(Constants.PATH_TO_MODELS, listOfModels)) {
			modelClass = modelClass.replace(".class","");
			modelClass = modelClass.replaceAll(File.separator, ".");
//			System.out.println(modelClass);
			if(modelClass.endsWith(apiProperties.getProperty("response.schema"))) {
				Class<?> clz= Class.forName(modelClass);
				responseModel = clz.newInstance();
			}
		}
	}
	
	public void setRequestType(String type) {
		this.requestType = type;
	}

	public String getRequestType() {
		return requestType;
	}
	
	public String getRequestURL() {
		return url;
	}
	
	public void setApiEnvironment(String environment) {
		url = environment + getRequestURL();
	}
	
	public boolean hit(Object requestModel) { 
		boolean status = false;
		try {
			responseModel = ApiResponse.requestJson(url, requestModel, requestType, 
					requestHeaders, responseModel);
			status = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public Object getResponseModel() {
		return this.responseModel;
	}
}

class FileFilter implements FilenameFilter {

	private List<String> fileList = new ArrayList<String>();
	
	@Override
	public boolean accept(File dir, String name) {
		String path = dir.getPath();
		File file = new File(path+"/"+name);
		if(file.isDirectory())
			list(file);
		return name.toLowerCase().endsWith(".class");
	}
	
	public List<String> getUpdateArray(String basePath,String[] s) {
        if(s!=null) {
            for(String itrFileName : s) {
                fileList.add(basePath + File.separator + itrFileName);
            }
        }
        return fileList;
    }

	private void list(File file) {
		File[] files = file.listFiles();
		for(File f : files) {
			if(accept(file,f.getName()))
				fileList.add(file.getPath()+"/"+f.getName());
		}
	}
}
