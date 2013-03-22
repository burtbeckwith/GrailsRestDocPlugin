package org.votingsystem.plugin

import grails.gsp.PageRenderer
import groovy.io.FileType

import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass
import org.codehaus.groovy.tools.groovydoc.FileOutputTool
import org.codehaus.groovy.tools.groovydoc.GroovyDocTool
import org.codehaus.groovy.tools.groovydoc.SimpleGroovyClassDoc
import org.codehaus.groovy.tools.groovydoc.SimpleGroovyMethodDoc
import org.codehaus.groovy.tools.groovydoc.SimpleGroovyRootDoc
import org.sistemavotacion.doc.CommentDoc
import org.sistemavotacion.doc.ControllerActionDoc
import org.sistemavotacion.doc.ControllerDoc

/**
 * @author jgzornoza
 * Licencia: https://github.com/jgzornoza/GrailsRestDocPlugin/blob/master/licencia.txt
 */
class RestDocumentationService {
	
	public static final List classTokens = ["@infoController", "@descController", "@author"]
	public static final List methodTokens = ["@httpMethod", "@param", "@return", "@ETag", 
		"@requestHeader", "@responseHeader", "@urlSufix"]

	static transactional = false

	def grailsApplication
	PageRenderer groovyPageRenderer
	
	File controllersBaseDir = new File("./grails-app/controllers")
	File controllerDocBaseDir = new File("./grails-app/views/restDoc")
	String controllerDocPath = "${controllerDocBaseDir.absolutePath}"
	
	void generateDocs() {
		log.debug(" --- generateDocs")
		Map<String, Set> controllerToActionsMap = [:]
		grailsApplication.controllerClasses.each { DefaultGrailsControllerClass controller ->
			Set<String> methodSet = []
			controller.uris.each { actionURI ->
				if(actionURI.indexOf("/**") == -1) {
					String actionMethodName= controller.getMethodActionName(actionURI)
					methodSet.add(actionMethodName)
				}
			}
			log.debug("controller: ${controller.getLogicalPropertyName()}" + 
				" - Number of actions: ${methodSet?.size()}")
			controllerToActionsMap.put(controller.getLogicalPropertyName(), methodSet)
		}
		List fileNames = []
		GroovyDocTool docTool = new GroovyDocTool("")
		SimpleGroovyRootDoc rootDoc = docTool.getRootDoc()
		controllersBaseDir.eachFileRecurse (FileType.FILES) { file ->
			log.debug("--- Controller file.path: ${file.path}")
			if(file?.name.endsWith("Controller.groovy")) {
				fileNames << file.path
			}	
		}
		docTool.add(fileNames)
		List<ControllerDoc> controllerDocs = []
		rootDoc.classes().each { SimpleGroovyClassDoc it ->
			ControllerDoc controllerDoc = new ControllerDoc(it.name());
			if(!it.methods()) return
			Set<String> actionSet = controllerToActionsMap.get(controllerDoc.logicalPropertyName)
			String controllerBaseURI = "/${controllerDoc.logicalPropertyName}"
			parseControllerComment(controllerDoc, it.getRawCommentText())
			log.debug("----Controller: ${it.name()} - Number of methods: ${it.methods().length}" + 
				" - Number of actions: ${actionSet?.size()}")
			it.methods().each { SimpleGroovyMethodDoc method ->
				if(!actionSet.contains(method.name())) {
					log.debug("--- method ${method.name()} is not a controller Action")
					return
				}
				log.debug("--- Adding Action: ${method.name()}")
				ControllerActionDoc controllerActionDoc =
					new ControllerActionDoc(method.name(), "$controllerBaseURI/${method.name()}");
				controllerDoc.addControllerAction(method.name(), controllerActionDoc)
				controllerActionDoc.commentDoc = parseActionComment(method.getRawCommentText())
			}
			if(actionSet.size() != controllerDoc.getControllerActions()?.size()) {
				log.error("ERROR - Controller: ${it.name()} - actionSet.size(): ${actionSet.size()} - " +
					" - controllerDoc.getControllerActions().size(): ${controllerDoc.getControllerActions().size()}")
			}
			generateControllerDoc(controllerDoc)
			controllerDocs.add(controllerDoc)
		}
		generateAppDoc(controllerDocs)
	}
	
	private void generateControllerDoc(ControllerDoc controllerDoc) {
		String renderedSrc = groovyPageRenderer.render (
			view:"/restDocTemplates/controllerDoc", model: [controllerDoc: controllerDoc])
		FileOutputTool output = new FileOutputTool()
		String outputPath = "${controllerDocPath}/${controllerDoc.logicalPropertyName}.gsp"
		log.debug(" --- generateControllerDoc - outputPath: ${outputPath}")
		output.writeToOutput(outputPath, renderedSrc)
	}
	
	private void generateAppDoc(List<ControllerDoc> controllerDocs) {
		String renderedSrc = groovyPageRenderer.render (
			view:"/restDocTemplates/appDoc", model: [controllerDocs: controllerDocs])
		FileOutputTool output = new FileOutputTool()
		String outputPath = "${controllerDocPath}/index.gsp"
		log.debug(" --- generateAppDoc - outputPath: ${outputPath}")
		output.writeToOutput(outputPath, renderedSrc)
	}
	
	private String cleanComment(String comment) {
		if(!comment) return comment
		String[] commentLines = comment.split(System.getProperty("line.separator"))
		String finalComment = ""
		commentLines.each {
			it = it.replace('*', '').replace("\n", "<br/>").trim()
			finalComment = finalComment.concat(it)
		}
		return finalComment
	}
	
	private Map<String, Integer> getMethodsLineNumberMap(File controlleFile) {
		log.debug("getMethodsLineNumberMap")
		String fileString = controlleFile.text
		def ast = new AstBuilder().buildFromString(CompilePhase.INSTRUCTION_SELECTION, false, fileString)
		Map<String, Integer> result = [:]
		ast[1].methods.each { MethodNode it ->
			log.debug("--- methods: ${it.name} - lineNumber: ${it.lineNumber}")
			result.put(it.name, it.lineNumber)
			it.getAnnotations().each {annotation ->
				log.debug("annotation: ${annotation}")
			}
		}
		return result
	}
	
	private void parseControllerComment(
		ControllerDoc controllerDoc, String comment) {
		log.debug(" --- parseControllerComment")
		if(!comment) return
		comment = comment.replace("*", "")
		String nextToken = getNextToken(comment, classTokens)
		while(nextToken) {
			int tokenIdx = comment.indexOf(nextToken)
			String lastToken = nextToken
			nextToken = getNextToken(comment.substring(tokenIdx + lastToken.length()), classTokens)
			//log.debug(" --- lastToken:${lastToken} - nextToken: ${nextToken} - comment:'${comment}'")
			int nexTokenIdx
			if("@infoController".equals(lastToken)) {
				if(!nextToken) {
					controllerDoc.infoController =
						comment.substring(tokenIdx + "@infoController".length()).trim()
				} else {
					nexTokenIdx = comment.indexOf(nextToken)
					controllerDoc.infoController =	comment.substring(
						tokenIdx + "@infoController".length(), nexTokenIdx).trim()
					comment = comment.substring(nexTokenIdx)
				}
			} else if("@descController".equals(lastToken)) {
				if(!nextToken) {
					controllerDoc.descController =
						comment.substring(tokenIdx + "@descController".length()).trim()
				} else {
					nexTokenIdx = comment.indexOf(nextToken)
					controllerDoc.descController =	comment.substring(
						tokenIdx + "@descController".length(), nexTokenIdx)
					comment = comment.substring(nexTokenIdx)
				}
			} else {
				if(nextToken) {
					nexTokenIdx = comment.indexOf(nextToken)
					comment = comment.substring(nexTokenIdx)
				}
			}
		}
	}
	
	private CommentDoc parseActionComment(String comment) {
		log.debug(" --- parseActionComment --- ")
		CommentDoc commentDoc = new CommentDoc()
		if(!comment) return commentDoc
		comment = comment.replace("*", "")
		String nextToken = getNextToken(comment, methodTokens)
		int tokenIdx
		if(!nextToken) {
			commentDoc.description = comment
			return commentDoc
		} else {
			tokenIdx = comment.indexOf(nextToken)
			commentDoc.description = comment.substring(0, tokenIdx)
			comment = comment.substring(tokenIdx + nextToken.length())
		}
		String tokenText
		while(nextToken) {
			String lastToken = nextToken
			nextToken = getNextToken(comment, methodTokens)
			//log.debug(" *** lastToken: '${lastToken}' - nextToken: ${nextToken} - comment: '${comment}'")
			if(!nextToken) {
				tokenText = comment.trim()
				comment = ""
			} else {
				tokenIdx = comment.indexOf(nextToken)
				tokenText = comment.substring(0, tokenIdx).trim()
				comment = comment.substring(tokenIdx + nextToken?.length())
			}
			if("@param".equals(lastToken)) {
				String[] paramAndDescription = getParamAndDescription(tokenText)
				commentDoc.addParam(paramAndDescription[0],paramAndDescription[1])
			} else if("@ETag".equals(lastToken)){
				String[] paramAndDescription = getParamAndDescription(tokenText)
				commentDoc.addETag(paramAndDescription[0],paramAndDescription[1])
			} else if("@requestHeader".equals(lastToken)){
				String[] paramAndDescription = getParamAndDescription(tokenText)
				commentDoc.addRequestHeader(paramAndDescription[0],paramAndDescription[1])
			} else if("@responseHeader".equals(lastToken)){
				String[] paramAndDescription = getParamAndDescription(tokenText)
				commentDoc.addResponseHeader(paramAndDescription[0],paramAndDescription[1])
			} else if("@urlSufix".equals(lastToken)){
				String[] paramAndDescription = getParamAndDescription(tokenText)
				commentDoc.addUrlSufix(paramAndDescription[0],paramAndDescription[1])
			}  else if("@httpMethod".equals(lastToken)) {
				commentDoc.httpMethod = tokenText.trim()
			} else if("@return".equals(lastToken)) {
				commentDoc.result = tokenText.trim()
			}
		}
		return commentDoc
	}
	
	private String[] getParamAndDescription(String tokenText) {
		if(!tokenText) return ["", ""] as String[]
		List result
		String[] tokenTextSplitted = tokenText.split("\\s+")//this groups all whitespaces as a delimiter.
		if(tokenTextSplitted) {
			result = [tokenTextSplitted[0].trim(),
				tokenText.substring(tokenTextSplitted[0].length()).trim()]
		} else result = [tokenTextSplitted[0].trim(),""]
		return result.toArray()
	}
	
	private String getNextToken(String comment, List tokenList) {
		Integer lowestIdx
		String token
		tokenList.each {
			int idx = comment.indexOf(it)
			if(idx >= 0) {
				if(lowestIdx == null) {
					lowestIdx = idx
					token = it
				} else {
					if(idx < lowestIdx) {
						lowestIdx = idx
						token = it
					}
				}
			}
		}
		return token
	}
}
