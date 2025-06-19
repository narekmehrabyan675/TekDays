package com.tekdays

import grails.converters.JSON

import java.time.Duration
import java.time.Instant

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TekUserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    EmailService emailService


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
            return [redirectUrl: params.redirectUrl ]
        }
    }

    def validate(){
        def user = TekUser.findByUserName(params.username)
        if(user && user.password == params.password){
            session.user = user
            if(params.redirectUrl || params.cName) {
                def url = params.redirectUrl?.replace(request.contextPath, '')
                redirect(uri: url)
                return
            }

            redirect(uri: '/', params: [lang: session.lang?.language])
        }else{
            flash.message = "Invalid usarname and password."
            render view: 'login'
        }
    }

    def logout = {
        session.user = null
        redirect(uri: '/', params: [lang: session.lang?.language ?:'en'])
    }

    def activationPage(){
        if (!session.user) {
            respond(status: 401, text: [message: "Could not find user on session!"])
            return
        }
        if(session.user.activated == true){
            render(status: 200, text: [message: "Your site is activated!"])
            return
        }
        def activationCode
        if(ActivationCode.findByUsername(session.user.userName)){
            activationCode = ActivationCode.findByUsername(session.user.userName)
            activationCode.createdAt = new Date()
        }else {
            activationCode = new ActivationCode()
        }
        def tekUser = session.user
        activationCode.username = tekUser.userName
        activationCode.code = (100000 + new Random().nextInt(900000)).toString()
        activationCode.merge(flush: true)
        String mailOfUser = "mehrabyannarek19@gmail.com"/*(session.user.email).toString()*/
        String subj = "Activation code"
        String massage = "Dear ${session.user.userName} your activation code is ${activationCode.code.toString()}."

        //emailService.sendTestEmail(mailOfUser , subj , massage)
        flash.message = "Code sent to email ${tekUser.email}"

        if(params.redirectUrl){
            return [redirectUrl: params.redirectUrl ]
        }

        return true
    }
    @Transactional
    def activate(){
        def tekUser = session.user
        def inputCode = params.code?.toString()?.trim()
        def codeEntry = ActivationCode.findByUsernameAndCode(tekUser.userName , inputCode)

        Instant createdAt = codeEntry.createdAt.toInstant()
        Instant now = Instant.now()
        long secondsPassed = Duration.between(createdAt, now).seconds

        if (secondsPassed > 60) {
            flash.errorMessage = "Duration of code validity expeired!"
            redirect(action: 'activationPage', params: [lang: session.lang?.language ?: 'en'])
            return
        }
        def updateduser = TekUser.get(tekUser.id as Long)
        updateduser.activated = true
        updateduser.save(flush: true)
        codeEntry.delete(flush: true)

        session.user = updateduser

        flash.message = "Activation successed!"
        if(params.redirectUrl || params.cName) {
            def url = params.redirectUrl?.replace(request.contextPath, '')
            redirect(uri: url)
            return
        }

        redirect(uri: '/', params: [lang: session.lang?.language ?:'en'])
    }

}
