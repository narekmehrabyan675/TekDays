package com.tekdays

class TekdayTagLib {

    def messageThread = { attrs ->
        def messages = attrs.messages.findAll { msg -> !msg.parent }
        processMessages(messages, 0)
    }

    void processMessages(messages, indent) {
        messages.each { msg ->
            out << "<p style='height:35px; margin-left:${indent * 20}px;'>"


            out << g.remoteLink(controller: 'tekMessage',
                    action: 'showDetail',
                    id: msg.id,
                    update: 'details') {
                "${msg?.author} - ${msg?.subject}"
            }

            out << "</p>\n"

            def children = com.tekdays.TekMessage.findAllByParent(msg)
            if (children) {
                processMessages(children, indent + 1)
            }
        }
    }

    def backwards = { attrs, body ->
        out << body().reverse()
    }


    def loginToggle = {
        if (actionName == 'login') {
            return
        }
        out << "<div style='margin: 15px 0 40px;'>"
        if (request.getSession(false) && session.user) {
            out << "<span style='float:left; margin-left: 15px'>"
            out << "Welcome ${session.user}."
            out << "</span><span style='float:right;margin-right:15px'>"
            out << "<a href='${createLink(controller: 'tekUser', action: 'logout')}'>"
            out << "Logout </a></span>"
        } else {
            out << "<span style='float:right;margin-right:10px'>"
            out << "<a href='${createLink(controller: 'tekUser', action: 'login')}'>"
            out << "Login </a></span>"
        }
        out << "</div><br/>"
    }

}
