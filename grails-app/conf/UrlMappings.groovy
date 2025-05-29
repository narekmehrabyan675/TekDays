class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "/tekEvent/update"(controller: "tekEvent", action: "update")

        "500"(view:'/error')
	}
}
