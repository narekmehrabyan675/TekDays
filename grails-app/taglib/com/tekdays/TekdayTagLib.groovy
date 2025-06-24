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


    def registrationLink = { attrs, body ->
        if (!(request.getSession(false) && session.user)) {
            out << "<span style='float:right; margin-right: 10px'>"
            out << "<a href='${createLink(controller: 'registration', action: 'step1')}'>${message(code: 'welcome.title10')}</a>"
            out << "</span>"
        }
    }




    def loginToggle = {
        if (actionName == 'login') {
            return
        }
        out << "<div style='margin: 15px 0 40px;'>"
        if (request.getSession(false) && session.user) {
            out << "<span style='float:left; margin-left: 15px'>"
            out << "${message(code: 'welcome.welc')}"
            out << " ${session.user}."
            out << "</span><span style='float:right;margin-right:15px'>"
            out << "<a href='${createLink(controller: 'tekUser', action: 'logout')}'>"
            out << "${message(code: 'welcome.title11')} </a></span>"
        } else {
            out << "<span style='float:right;margin-right:10px'>"
            out << "<a href='${createLink(controller: 'tekUser', action: 'login')}'>"
            out << "${message(code: 'welcome.title12')} </a></span>"
        }
        out << "</div><br/>"
    }

    def organizerEvents = {
        if (request.getSession(false) && session.user){
            def userEvents = TekEvent.findAllByOrganizer(session.user)
            if(userEvents){
                out << "<div style='margin-left:25px; margin-top:25px; width:85%'>"
                out << "<h3>${message(code: 'welcome.title5')}</h3>"
                out << "<ol>"
                userEvents.each{
                    out << "<li><a href = '"
                    out << "${createLink(controller: 'tekEvent' , action: 'show' , id: it.id)}'>"
                    out << "${it}</a></li>"
                }
                out << "</ol>"
                out << "</div>"
            }
        }
    }
  /*  SELECT *
    FROM events e
    JOIN tekdays.events_tek_user etu on e.event_id = etu.tek_event_volunteers_id
            WHERE etu.tek_user_id = sessio.user.id*/
    def volunteerEvents = {
        if (request.getSession(false) && session.user){
            def events = TekEvent.createCriteria().list{
                volunteers{
                    eq('id' , session.user?.id)
                }
            }
            if (events){
                out << "<div style='margin-left:25px; margin-top:25px; width:85%'>"
                out << "<h3>${message(code: 'welcome.title6')}</h3>"
                out << "<ul>"
                events.each{
                    out << "<li><a href='"
                    out << "${createLink(controller:'tekEvent',action:'show', id:it.id)}'>"
                    out << "${it}</a></li>"
                }
                out << "</ul>"
                out << "</div>"
            }
        }
    }
    //Ete chka nor kojaky cuca talis
    def volunteerButton = {attrs ->
        if (request.getSession(false) && session.user){
            def user = session.user.merge()
            def event = TekEvent.get(attrs.eventId)
            if (event && !event.volunteers.contains(user)){
                out << "<span id='volunteerSpan' class='menuButton'>"
                out << "<button id='volunteerButton' type='button'>"
                out << "Volunteer For This Event"
                out << "</button>"
                out << "</span>"
            }
        }
    }
    def activationButton = { attrs, body ->
        def user = attrs.user ?: session.user
        if (user && user.activated == false) {
            out << """
                <form action="${createLink(controller: 'tekUser', action: 'activationPage')}" style="float:right" method="get">
                    <button type="submit" class="btn btn-warning">Activate Account</button>
                </form>
            """
        }
    }

    def chatButton = {
        if (actionName == 'chat') {
            return
        }
        if (request.getSession(false) && session.user) {
            /*out << "<span>"
            out << "</span>"*/
            out << "<a href='${createLink(controller: 'tekUser', action: 'chat')}'>"
            out << "${message(code: 'chat')} </a></span>"

        }
        out << "</div><br/>"
    }



}
