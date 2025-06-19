package com.tekdays

class EmailService {

    def mailService

    def sendTestEmail(String email , String subj , String message) {
        mailService.sendMail {
            to email
            from "ppetrosyan866@gmail.com"
            subject subj
            body message
        }
    }
}

