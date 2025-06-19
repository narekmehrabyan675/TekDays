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

        "/tekEvent/event/$id"(controller: "tekEvent") {
            action = [GET: "show", POST: "update", PUT: "updateAPI", DELETE: "delete"]
        }

        "/tekEvent/event"(controller: "tekEvent", action: "index", method: "GET")

        "/tekEvent/user/$id"(controller: "tekUser") {
            action = [GET: "show", POST: "update", PUT: "update", DELETE: "delete"]
        }

        "/tekEvent/user"(controller: "tekUser", action: "index", method: "GET")



        "/"(view:"/index")
        "/tekEvent/update"(controller: "tekEvent", action: "update")
        //"search/$query"(controller: "tekEvent" , action: "search")

        "500"(view:'/error')
    }
}