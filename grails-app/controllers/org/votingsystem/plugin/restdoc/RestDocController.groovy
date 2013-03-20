package org.votingsystem.plugin.restdoc

/**
 * @infoController Documentación REST de los controladores
 * @descController Generación de documentación de ayuda para acceder a servicios REST.
 *
 * @author jgzornoza
 * Licencia: https://github.com/jgzornoza/GrailsRestDocPlugin/blob/master/licencia.txt
 */
class RestDocController {

	/**
	 * @httpMethod GET
	 * @return Información sobre los servicios que tienen como url base '/restDoc'.
	 */
    def index() { }
	
	def showControllerDoc() {
		render(view:params.previousController)
		return false
	}
}
