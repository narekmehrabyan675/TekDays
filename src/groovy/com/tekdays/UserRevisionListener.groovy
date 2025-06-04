package com.tekdays


import org.codehaus.groovy.grails.web.util.WebUtils
import org.hibernate.envers.RevisionListener


class UserRevisionListener implements RevisionListener {
    @Override
    void newRevision(Object entity) {
        UserRevisionEntity revisionEntity = (UserRevisionEntity) entity
        def session = WebUtils.retrieveGrailsWebRequest().session
        TekUser user = session.user
        revisionEntity.currentUser = user
    }
}

