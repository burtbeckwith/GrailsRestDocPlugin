package org.sistemavotacion.doc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jgzornoza
 * Licencia: https://github.com/jgzornoza/GrailsRestDocPlugin/blob/master/licencia.txt
 */
public class CommentDoc {
	
	private Map<String, String> paramsMap = new HashMap<String, String>();
	private Map<String, String> eTagsMap = new HashMap<String, String>();
	private Map<String, String> requestHeaderMap = new HashMap<String, String>();
	private Map<String, String> responseHeaderMap = new HashMap<String, String>();
	private Map<String, String> urlSufixMap = new HashMap<String, String>();
	private String description;
	private String result;
	private String httpMethod;
	
	public CommentDoc () {
		
	}

	public Map<String, String> getParamsMap() {
		return paramsMap;
	}
	
	public Map<String, String> getRequestHeaderMap() {
		return requestHeaderMap;
	}
	
	public void addRequestHeader(String headerName, String headerDescription) {
		requestHeaderMap.put(headerName, headerDescription);
	}
	
	public Map<String, String> getUrlSufixMap() {
		return urlSufixMap;
	}
	
	public void addUrlSufix(String sufixName, String sufixDescription) {
		urlSufixMap.put(sufixName, sufixDescription);
	}
	
	public void addResponseHeader(String headerName, String headerDescription) {
		responseHeaderMap.put(headerName, headerDescription);
	}
	
	public Map<String, String> getResponseHeaderMap() {
		return responseHeaderMap;
	}
	
	public void addParam(String paramName, String paramDescription) {
		paramsMap.put(paramName, paramDescription);
	}

	public Map<String, String> getETagsMap() {
		return eTagsMap;
	}
	
	public void addETag(String etagName, String etagDescription) {
		eTagsMap.put(etagName, etagDescription);
	}
	
	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

}
