package com.tekdays

import org.codehaus.groovy.transform.trait.Traits
import org.hibernate.StaleObjectStateException
import org.hibernate.envers.query.AuditQuery
import org.hibernate.envers.AuditReaderFactory

import static org.springframework.http.HttpStatus.*
import javax.servlet.http.HttpSession
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TekEventController {

    TaskService taskService
    EventService eventService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TekEvent.list(params), model:[tekEventInstanceCount: TekEvent.count()]
    }
//We can give as parameter id of object on database
    @Transactional
    def show(TekEvent tekEventInstance) {

        TekEvent.withNewTransaction {status ->
            def albums = TekEvent.list()
            albums.each {it.name = "MIlen"
            it.save(flush: true)
            }
            if (true) {
                status.setRollbackOnly()
            }
        }

       /* def event = TekEvent.get(params.id)
        event.setName("Changed")
        event.save(flush: true)
        TekEvent.findByName("Test123456")*/
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
                    description: "Without Save",
                    notes: "Without Save"
            )
            tekEventInstance.addToSponsorships(sponsorship)
        }
        taskService.addDefaultTasks(tekEventInstance)
        // Save event
        tekEventInstance.save flush: true

        /*request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'tekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                redirect tekEventInstance
            }
            '*' { respond tekEventInstance, [status: CREATED] }
        }*/
        if (tekEventInstance.save(flush: true)) {
            // If save was successful, proceed with redirection/response.
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message',
                            args: [message(code: 'tekEvent.label', default: 'TekEvent'), tekEventInstance.id])
                    // Now tekEventInstance.id is guaranteed to be set, so redirection works.
                    redirect tekEventInstance
                }
                '*' { respond tekEventInstance, [status: CREATED] }
            }
        } else {
            // If save() failed (e.g., more validation errors surfaced after initial check, or database constraint violations),
            // we can't redirect with an ID. Respond with errors and show the create view again.
            respond tekEventInstance.errors, view:'create'
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

    def edit(Long id ){
        def tekEventInstance = TekEvent.get(id)
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
    def update(Long id ,Long version){
        TekEvent tekEventInstance = TekEvent.get(id)
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

        if (version != null && tekEventInstance.version > version) {
            tekEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    ["TekEvent"] as Object[], "Another user has updated this TekEvent while you were editing.")
            flash.tekEventInstance = tekEventInstance
            redirect(action: "edit", id: tekEventInstance.id)
            return
        }

        tekEventInstance.properties = params


        if (tekEventInstance.hasErrors()) {
            respond tekEventInstance.errors, view:'edit'
            return
        }

        if(params.volunteers == null){
            tekEventInstance.volunteers.clear()
        }

        try {
            tekEventInstance.save flush:true
        }catch (StaleObjectStateException e){
            tekEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    ["TekEvent"] as Object[], "Another user has updated this TekEvent while you were editing.")
            render(view: "edit", model: [tekEventInstance: tekEventInstance ,csrfToken: csrfToken])
            return
        }


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

    def search(){
        if(params.query){
            def events = eventService.search1(params.query)
            [events: events ]
        }
    }

    def volunteer(){
        def event = TekEvent.get(params.id)
        event.addToVolunteers(session.user)
        event.save flush:true
        event.volunteers
        render"Thank you for Volunteering!"
    }


    def sessionFactory // get current session

    def revisions() {
        def auditQueryCreator = AuditReaderFactory.get(sessionFactory.currentSession).createQuery()

        def revisionList = []
        AuditQuery query = auditQueryCreator.forRevisionsOfEntity(TekEvent.class, false, true)
        query.resultList.each {
            if(it[0].id==params.getLong('id')) {
                revisionList.add(it)
            }
        }
        [revisionList: revisionList]
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
