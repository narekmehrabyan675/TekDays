package com.tekdays



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TekUserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TekUser.list(params), model:[tekUserInstanceCount: TekUser.count()]
    }

    def show(TekUser tekUserInstance) {
        respond tekUserInstance
    }

    def create() {
        respond new TekUser(params)
    }

    @Transactional
    def save(TekUser tekUserInstance) {
        if (tekUserInstance == null) {
            notFound()
            return
        }

        if (tekUserInstance.hasErrors()) {
            respond tekUserInstance.errors, view:'create'
            return
        }

        tekUserInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tekUser.label', default: 'TekUser'), tekUserInstance.id])
                redirect tekUserInstance
            }
            '*' { respond tekUserInstance, [status: CREATED] }
        }
    }

    def edit(TekUser tekUserInstance) {
        respond tekUserInstance
    }

    @Transactional
    def update(TekUser tekUserInstance) {
        if (tekUserInstance == null) {
            notFound()
            return
        }

        if (tekUserInstance.hasErrors()) {
            respond tekUserInstance.errors, view:'edit'
            return
        }

        tekUserInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TekUser.label', default: 'TekUser'), tekUserInstance.id])
                redirect tekUserInstance
            }
            '*'{ respond tekUserInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(TekUser tekUserInstance) {

        if (tekUserInstance == null) {
            notFound()
            return
        }

        tekUserInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TekUser.label', default: 'TekUser'), tekUserInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tekUser.label', default: 'TekUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def login() {
        if(params.redirectUrl){
            return [redirectUrl: params.redirectUrl]
        }


        /*if (params.cName)
            return [cName:params.cName, aName:params.aName]*/
    }

    def validate(){
        def user = TekUser.findByUserName(params.username)
        if(user && user.password == params.password){
            session.user = user
            if(params.redirectUrl || params.cName) {
                def url = params.redirectUrl?.replace(request.contextPath, '')
                redirect(uri: url)

//                redirect controller: params.cName, action: params.aName
                return
            }

            redirect(uri:'/')
        }else{
            flash.message = "Invalid usarname and password."
            render view: 'login'
        }
    }

    def registrationFlow = {
        enterDetails {
            on("next") {
                flow.userInstance = new TekUser(params)
                if (flow.userInstance.validate()) {
                    return success()
                } else {
                    return error()
                }
            }.to "confirmDetails"
            on("cancel").to "cancelled"
        }

        confirmDetails {
            on("submit") {
                flow.userInstance.save(flush:true)
                return success()
            }.to "finished"
            on("back").to "enterDetails"
        }

        finished {
            redirect(action: "login")  // или куда надо
        }

        cancelled {
            redirect(action: "index")
        }
    }



    def logout = {
        session.user = null
        redirect(uri:'/')
    }

}
