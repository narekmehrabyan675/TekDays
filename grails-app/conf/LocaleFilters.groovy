import org.springframework.web.servlet.i18n.SessionLocaleResolver

class LocaleFilters {

    def filters = {
        all(uri: '/**') {
            before = {
                println "LocaleFilters: runing!"

                if (params.lang) {
                    println "Got param lang: ${params.lang}"
                    def locale = new Locale(params.lang)
                    session.lang = locale
                    println "Saved to session: ${session.lang}"
                    request.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale)
                } else if (session.lang) {
                    println "Using session.lang: ${session.lang}"
                    request.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, session.lang)
                }

                return true
            }
        }
    }
}
