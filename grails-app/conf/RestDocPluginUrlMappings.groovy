class RestDocPluginUrlMappings {

	static mappings = {
		"/$previousController/restDoc"(controller: "restDoc", action: "showControllerDoc")

		"/$controller/restDoc" {
			controller = "restDoc"
			action = "list"
		}
	}
}
