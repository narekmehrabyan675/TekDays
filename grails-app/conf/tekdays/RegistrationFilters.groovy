package tekdays

class RegistrationFilters {

    def filters = {
        clearRegistration(controllerExclude: 'registration', action: '*') {
            before = {
                if (session.registrationData || session.registrationStarted) {
                    session.registrationData = null
                    session.registrationStarted = null
                }
                return true
            }
        }
    }
}

