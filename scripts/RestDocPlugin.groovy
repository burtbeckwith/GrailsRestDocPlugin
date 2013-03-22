includeTargets << grailsScript("_GrailsBootstrap")
includeTargets << grailsScript("_GrailsRun")

target(restDocPlugin: "Generates REST documentation from controllers") {
	depends(configureProxy, packageApp, classpath, loadApp, configureApp)
	println " ---- Generating REST documentation from controllers"
	appCtx.restDocumentationService.generateDocs()
}

setDefaultTarget 'restDocPlugin'
