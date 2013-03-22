package org.sistemavotacion.doc

import org.apache.commons.lang.WordUtils

/**
 * @author jgzornoza
 * Licencia: https://github.com/jgzornoza/GrailsRestDocPlugin/blob/master/licencia.txt
 */
class ControllerDoc {

	Map<String, ControllerActionDoc> methodControllerActionDocMap = [:]

	String infoController
	String descController
	private String controllerClassName
	private String logicalPropertyName

	ControllerDoc(String className) {
		controllerClassName = className
		if(className?.indexOf("Controller") >=0) {
			logicalPropertyName = WordUtils.uncapitalize(
				className.substring(0, className.indexOf("Controller")))
		}
	}

	Collection<ControllerActionDoc> getControllerActions() {
		return methodControllerActionDocMap.values()
	}

	void addControllerAction(String method,
		ControllerActionDoc controllerActionDoc) {
		methodControllerActionDocMap.put(method, controllerActionDoc)
	}

	String getInfoController() {
		if(infoController == null) return logicalPropertyName
		return infoController
	}

	String getLogicalPropertyName() {
		return logicalPropertyName
	}
}
