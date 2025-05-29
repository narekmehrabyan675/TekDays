package com.tekdays



import static org.springframework.http.HttpStatus.*
import javax.servlet.http.HttpSession
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TekEventController {

    TaskService taskService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TekEvent.list(params), model:[tekEventInstanceCount: TekEvent.count()]
    }
//We can give as parameter id of object on database
    def show(TekEvent tekEventInstance) {
        respond tekEventInstance
    }

    def create() {
        respond new TekEvent(params)
    }
    @Transactional
    def save(TekEvent tekEventInstance) {
        if (tekEventInstance == null) {
            notFound()
            return
        }

        if (tekEventInstance.hasErrors()) {
            respond tekEventInstance.errors, view:'create'
            return
        }

        // Creaeting Sponsorship
        // Is form we have SpnsorID creating via that
        if (params.sponsorId) {
            def sponsor = Sponsor.get(params.sponsorId as Long)
            def sponsorship = new Sponsorship(
                    sponsor: sponsor,
                    event: tekEventInstance,
                    contributionType: "Cash",
                    description: "Auto-added",
                    notes: ""
            )
            tekEventInstance.addToSponsorships(sponsorship)
        }
        taskService.addDefaultTasks(tekEventInstance)
        // Save event
        tekEventInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'tekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                redirect tekEventInstance
            }
            '*' { respond tekEventInstance, [status: CREATED] }
        }
    }




    /*def edit(TekEvent tekEventInstance) {
        respond tekEventInstance
    }

    @Transactional
    def update(TekEvent tekEventInstance) {
        if (tekEventInstance == null) {
            notFound()
            return
        }

        if (tekEventInstance.hasErrors()) {
            respond tekEventInstance.errors, view:'edit'
            return
        }

        if(params.volunteers==null ){
            tekEventInstance.volunteers.clear()
        }

        tekEventInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                redirect tekEventInstance
            }
            '*'{ respond tekEventInstance, [status: OK] }
        }
    }*/

    def edit(TekEvent tekEventInstance) {
        if (!tekEventInstance) {
            notFound()
            return
        }

        // Generate CSRF token and add in session
        String csrfToken = UUID.randomUUID().toString()
        session.csrfToken = csrfToken

        // Giving token on model
        respond tekEventInstance, model: [csrfToken: csrfToken]
    }

    @Transactional
    def update(TekEvent tekEventInstance) {
        if (tekEventInstance == null) {
            notFound()
            return
        }

        // Giving token from Request and Session
        String tokenFromRequest = params.csrfToken
        String tokenFromSession = session.csrfToken

        if (!tokenFromRequest || tokenFromRequest != tokenFromSession) {
            render status: 403, text: 'CSRF token validation failed'
            return
        }


        session.csrfToken = null

        if (tekEventInstance.hasErrors()) {
            respond tekEventInstance.errors, view:'edit'
            return
        }

        if(params.volunteers == null){
            tekEventInstance.volunteers.clear()
        }

        tekEventInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                redirect tekEventInstance
            }
            '*'{ respond tekEventInstance, [status: OK] }
        }
    }


    @Transactional
    def delete(TekEvent tekEventInstance) {

        if (tekEventInstance == null) {
            notFound()
            return
        }

        tekEventInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tekEvent.label', default: 'TekEvent'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
