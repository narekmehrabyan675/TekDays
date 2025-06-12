package SecurityFilters

class GroovyFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                if (!controllerName)
                    return true

                def allowedActions = ['show', 'index', 'login', 'validate' , 'search' , 'step1' , 'step2','step3' ,'complete']

                if (!session.user && !allowedActions.contains(actionName)){
                    def fullUrl = request.forwardURI
                    if (request.queryString) {
                        fullUrl += "?" + request.queryString
                    }
                    redirect(controller:'tekUser', action:'login',
                            params: [redirectUrl: fullUrl])

                    return false
                }
            }
        }
            /*after = { Map model ->

            }
            afterView = { Exception e ->

            }*/
        }
    }

