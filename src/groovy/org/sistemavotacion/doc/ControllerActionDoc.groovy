package org.sistemavotacion.doc

/**
 * @author jgzornoza
 * Licencia: https://github.com/jgzornoza/GrailsRestDocPlugin/blob/master/licencia.txt
 */
class ControllerActionDoc {

	CommentDoc commentDoc
	String method
	String uri
	int lineNumber

	ControllerActionDoc(String method, String uri) {
		setMethod(method)
		setUri(uri)
	}
}
