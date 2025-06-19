package com.tekdays

import grails.transaction.Transactional
import org.apache.catalina.User



@Transactional(readOnly = true)
class RegistrationController {


    def step1() {
        session.registrationData = session.registrationData ?: [:]
        session.registrationStarted = true
        render(view: 'step1')
    }

    def step2() {
        def requiredFields = ['fullName', 'userName', 'password', 'website', 'bio']
        boolean missing = requiredFields.any { !params[it] && !session.registrationData[it] }
        if (missing) {
            flash.message = "Please fill all required fields"
            redirect(action: 'step1')
            return
        }
        requiredFields.each { field ->
            if (params[field]) {
                session.registrationData[field] = params[field]
            }
        }
        requiredFields.each { field ->
            if (params[field]=="") {
                session.registrationData[field] = ""
            }
        }

        render(view: 'step2')
    }

    def step3() {
        if (params.email) {
            if (!params.email.contains('@')) {
                flash.message = "Invalid email format"
                redirect(action: 'step2')
                return
            }
            session.registrationData.email = params.email
        }

        if (!session.registrationData?.email) {
            flash.message = "Email is required"
            redirect(action: 'step2')
            return
        }

        render(view: 'step3', model: [data: session.registrationData])
    }
      @Transactional
    def complete() {
        def user = new TekUser(session.registrationData)
        if (!user.save(flush: true)) {
            flash.message = "Something went wrong. Please correct your data."
            render(view: 'step1', model: [userInstance: user])
            return
        }

        session.registrationData = null
        flash.message = "Registration successful!"
        redirect(controller: "tekUser", action: "login")
    }
}

