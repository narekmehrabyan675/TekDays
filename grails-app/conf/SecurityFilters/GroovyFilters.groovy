package SecurityFilters

class GroovyFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                if (!request) {
                    return true
                }
                if (!controllerName)
                    return true

                def publicActions = ['login', 'validate', 'index', 'show', 'search',
                                     'step1', 'step2', 'step3', 'complete',
                                     'updateAPI', 'changeLocale' , 'updateAPI' , 'updateAPI1']

                if (!session.user) {
                    if (!publicActions.contains(actionName)) {

                        def fullUrl = request.forwardURI



                        if (request.queryString) {
                            fullUrl += "?" + request.queryString
                        }
                        redirect(controller:'tekUser', action:'login',
                                params: [redirectUrl: fullUrl, lang: session.lang?.language])

                        return false
                    }
                    return true
                }

                def activationAllowed = ['activationPage', 'activate', 'logout' , 'login', 'validate', 'index', 'show', 'search',
                                         'step1', 'step2', 'step3', 'complete',
                                         'updateAPI', 'changeLocale']
                if (!session.user.activated && !activationAllowed.contains(actionName)) {
                    def fullUrl = request.forwardURI
                    if (request.queryString) {
                        fullUrl += "?" + request.queryString
                    }
                    flash.message = "Please activate your site to continue."
                    redirect(controller: 'tekUser', action: 'activationPage',
                            params: [redirectUrl: fullUrl , lang: session.lang?.language ?: 'en'])
                    return false
                }
                return true
            }
        }
    }
}
