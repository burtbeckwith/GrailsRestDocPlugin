class RestDocPluginGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Rest Dococumentation Plugin" // Headline display name of the plugin
    def author = "José J. García"
    def authorEmail = "gruposp2p@gmail.com"
    def description = '''\
		Plugin that generate REST based docs from annotated controller actions.'''

    // URL to the plugin's documentation
    def documentation = "https://github.com/jgzornoza/GrailsRestDocPlugin/wiki"

    // Extra (optional) plugin metadata

    def license = "APACHE"

    def organization = [ name: "Jose Javier García", url: "http://www.gruposp2p.org" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "Github", url: "https://github.com/jgzornoza/GrailsRestDocPlugin/issues" ]

    def scm = [ url: "https://github.com/jgzornoza/GrailsRestDocPlugin" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
