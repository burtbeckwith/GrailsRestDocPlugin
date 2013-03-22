package org.sistemavotacion.doc

/**
 * @author jgzornoza
 * Licencia: https://github.com/jgzornoza/GrailsRestDocPlugin/blob/master/licencia.txt
 */
class CommentDoc {

	Map<String, String> paramsMap = [:]
	final Map<String, String> eTagsMap = [:]
	final Map<String, String> requestHeaderMap = [:]
	final Map<String, String> responseHeaderMap = [:]
	final Map<String, String> urlSufixMap = [:]
	String description
	String result
	String httpMethod

	void addRequestHeader(String headerName, String headerDescription) {
		requestHeaderMap.put(headerName, headerDescription)
	}

	void addUrlSufix(String sufixName, String sufixDescription) {
		urlSufixMap.put(sufixName, sufixDescription)
	}

	void addResponseHeader(String headerName, String headerDescription) {
		responseHeaderMap.put(headerName, headerDescription)
	}

	void addParam(String paramName, String paramDescription) {
		paramsMap.put(paramName, paramDescription)
	}

	void addETag(String etagName, String etagDescription) {
		eTagsMap.put(etagName, etagDescription)
	}
}
