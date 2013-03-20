class RestDocPluginUrlMappings {

	static mappings = {
		"/$previousController/restDoc"(controller: "restDoc", action: "showControllerDoc")
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/$controller/restDoc" {
			controller = "restDoc"
			action = "list"
		}
		
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
