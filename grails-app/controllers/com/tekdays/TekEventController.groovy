package com.tekdays

import grails.converters.JSON
import grails.converters.XML
import org.codehaus.groovy.transform.trait.Traits
import org.hibernate.HibernateException
import org.hibernate.StaleObjectStateException
import org.hibernate.envers.query.AuditQuery
import org.hibernate.envers.AuditReaderFactory

import static org.springframework.http.HttpStatus.*
//import grails.converters.deep.*

import grails.transaction.Transactional

@Transactional(readOnly = true)
class TekEventController {


    TaskService taskService
    EventService eventService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def allowedIps = ['195.250.67.234' , '127.0.0.1']




    def beforeInterceptor = {
        def clientIp = request.getRemoteAddr()
        if ((!(clientIp in allowedIps) && actionName == 'updateAPI') || (!request.getHeader("Accept")?.contains("application/json") && actionName == 'updateAPI')) {
            render status: 403, text: "Can access only via  API (JSON)"
            return false
        }
    }

    def index(Integer max) {
        println "Got param lang:"
        params.max = Math.min(max ?: 10, 100)
        respond TekEvent.list(params), model:[tekEventInstanceCount: TekEvent.count()]
    }
//We can give as parameter id of object on database
    @Transactional
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
                    description: "Without Save",
                    notes: "Without Save"
            )
            tekEventInstance.addToSponsorships(sponsorship)
        }
        taskService.addDefaultTasks(tekEventInstance)
        // Save event
        tekEventInstance.save flush: true


        if (!tekEventInstance.save(flush: true)) {
            respond tekEventInstance.errors, view:'create'
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tekEvent.label', default: 'TekEvent'), tekEventInstance.id])
        redirect tekEventInstance
    }



    def edit(Long id ){
        def tekEventInstance = TekEvent.get(id)

        def userName = session.user?.userName
        def lockTimeoutMinutes = 5

        if(tekEventInstance.locked && tekEventInstance.lockedBy != userName){
            if (tekEventInstance.lockedAt?.before(new Date() - lockTimeoutMinutes.minutes)){
                tekEventInstance.lockedBy = null
                tekEventInstance.lockedAt = null
                tekEventInstance.locked = false
            }else{
                flash.message = "This event is being edited by ${tekEventInstance.lockedBy} , please wait!"
                redirect(action: 'show', id: id)
                return
            }
        }
        tekEventInstance.lockedAt = new Date()
        tekEventInstance.locked = true
        tekEventInstance.lockedBy = userName
        tekEventInstance.save(flush: true)


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
            flash.message = message(code: "default.optimistic.locking.failure",
                    args: ["TekEvent"],
                    default: "Another user has updated this TekEvent while you were editing.")

            redirect(action: "edit", id: tekEventInstance.id)
            return
        }

        tekEventInstance.properties = params

        if (tekEventInstance.hasErrors()) {
            respond tekEventInstance.errors, view:'edit'
            return
        }
        tekEventInstance.volunteers.clear()
        for(i in params.volunteers) {
            def v = TekUser.get(i as Long)
            tekEventInstance.addToVolunteers(v)
        }
        tekEventInstance.merge(flush: true)

        try {
            tekEventInstance.locked = false
            tekEventInstance.lockedBy = null
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


    def updateAPI(){
        def tekEvent = TekEvent.get(params.id)

        if (!tekEvent) {
            render status: 404, text: "Event not found"
            return
        }

        def json = request.JSON

        tekEvent.properties = json
        def messages = json.messages
        if(!messages || messages == null){
            tekEvent.messages = messages;
        }


        try {
            if (tekEvent.save(flush: true)) {
                render tekEvent as JSON
            } else {
                render status: 400, text: "Validation error: ${tekEvent.errors}"
            }
        } catch (Exception e) {
            log.error("Update error", e)
            render status: 500, text: "Server error"
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
