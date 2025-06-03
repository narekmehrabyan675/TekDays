class UrlMappings {

	static mappings = {
        "/TekDays/tekEvent/search/$query"(controller: "tekEvent", action: "search"){
            constraints {
                query(matches: /.+/)
            }
        }

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(view:"/index")
        "/tekEvent/update"(controller: "tekEvent", action: "update")
        //"search/$query"(controller: "tekEvent" , action: "search")

        "500"(view:'/error')
	}
}
