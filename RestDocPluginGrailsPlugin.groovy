class RestDocPluginGrailsPlugin {
    def version = "0.2"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]
    def title = "Rest Documentation Plugin"
    def author = "Jose J. García Zornoza"
    def authorEmail = "gruposp2p@gmail.com"
    def description = '''\
		Plugin that generate REST based docs from annotated controller actions.'''
    def documentation = "https://github.com/jgzornoza/GrailsRestDocPlugin"
    def license = "APACHE"
    def organization = [ name: "Jose Javier García Zornoza", url: "http://www.gruposp2p.org" ]
    def issueManagement = [ system: "Github", url: "https://github.com/jgzornoza/GrailsRestDocPlugin/issues" ]
    def scm = [ url: "https://github.com/jgzornoza/GrailsRestDocPlugin" ]

}
