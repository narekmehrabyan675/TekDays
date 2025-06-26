package com.tekdays

import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.hibernate.criterion.CriteriaSpecification

@Transactional
class EventService implements GrailsApplicationAware{
    GrailsApplication grailsApplication


    /*JSON search1(String query)*/
    JSON search1(Map params){
        String query = params['sSearch']
        if (!query || query == "" || query == null) {
            def results = TekEvent.list()

            def data = results.collect { e ->
                return [
                        e.name,
                        e.city,
                        e.description,
                        e.id
                ]
            }

            def result = [
                    "sEcho": params?.sEcho,
                    //draw: params.draw?.toInteger() ?: 1,
                    "iTotalRecords": results.size(),
                    "iTotalDisplayRecords": results.size(),
                    "aaData": data
            ]
            return result as JSON
        }

        String likeQuery = "%${query}%"


        def results = TekEvent.createCriteria().listDistinct {
            createAlias('organizer', 'o', CriteriaSpecification.LEFT_JOIN)
            createAlias('volunteers', 'v', CriteriaSpecification.LEFT_JOIN)
            createAlias('sponsorships', 's', CriteriaSpecification.LEFT_JOIN)
            createAlias('s.sponsor', 'sp', CriteriaSpecification.LEFT_JOIN)
            createAlias('tasks', 't', CriteriaSpecification.LEFT_JOIN)
            createAlias('t.assignedTo', 'ta', CriteriaSpecification.LEFT_JOIN)
            createAlias('messages', 'm', CriteriaSpecification.LEFT_JOIN)
            createAlias('m.author', 'ma', CriteriaSpecification.LEFT_JOIN)

            or {
                ilike('city', likeQuery)
                ilike('name', likeQuery)
                ilike('venue', likeQuery)
                ilike('description', likeQuery)

                or {
                    ilike('o.fullName', likeQuery)
                    ilike('o.userName', likeQuery)
                    ilike('o.email', likeQuery)
                    ilike('o.website', likeQuery)
                    ilike('o.bio', likeQuery)
                }

                or {
                    ilike('v.fullName', likeQuery)
                    ilike('v.userName', likeQuery)
                    ilike('v.email', likeQuery)
                    ilike('v.website', likeQuery)
                    ilike('v.bio', likeQuery)
                }

                or {
                    ilike('s.contributionType', likeQuery)
                    ilike('s.description', likeQuery)
                    ilike('s.notes', likeQuery)
                    ilike('sp.name', likeQuery)
                    ilike('sp.website', likeQuery)
                    ilike('sp.description', likeQuery)
                }

                or {
                    ilike('t.title', likeQuery)
                    ilike('t.notes', likeQuery)
                    ilike('ta.fullName', likeQuery)
                    ilike('ta.userName', likeQuery)
                    ilike('ta.email', likeQuery)
                    ilike('ta.website', likeQuery)
                    ilike('ta.bio', likeQuery)
                }

                or {
                    ilike('m.subject', likeQuery)
                    ilike('m.content', likeQuery)
                    ilike('ma.fullName', likeQuery)
                    ilike('ma.userName', likeQuery)
                    ilike('ma.email', likeQuery)
                    ilike('ma.website', likeQuery)
                    ilike('ma.bio', likeQuery)
                }
            }
        }


        def data = results.collect { e ->
            return [
                    (e.name).toString(),
                    e.city,
                    e.description,
                    (e.id).toString()
            ]
        }

        def result = [
                "sEcho": params?.sEcho,
                "iTotalRecords": results.size(),
                "iTotalDisplayRecords": results.size(),
                "aaData": data
        ]

        return result as JSON
    }

    JSON searchHQL(Map params){
        String query = params['sSearch']
        String likeQuery = "%${query}%"

        def hql = """
    select distinct e from TekEvent e
    left join e.organizer o
    left join e.volunteers v
    left join e.sponsorships s
    left join s.sponsor sp
    left join e.tasks t
    left join t.assignedTo ta
    left join e.messages m
    left join m.author ma
    where
        lower(e.city) like :q or
        lower(e.name) like :q or
        lower(e.venue) like :q or
        lower(e.description) like :q or
        lower(o.fullName) like :q or
        lower(o.userName) like :q or
        lower(o.email) like :q or
        lower(o.website) like :q or
        lower(o.bio) like :q or
        lower(v.fullName) like :q or
        lower(v.userName) like :q or
        lower(v.email) like :q or
        lower(v.website) like :q or
        lower(v.bio) like :q or
        lower(s.contributionType) like :q or
        lower(s.description) like :q or
        lower(s.notes) like :q or
        lower(sp.name) like :q or
        lower(sp.website) like :q or
        lower(sp.description) like :q or
        lower(t.title) like :q or
        lower(t.notes) like :q or
        lower(ta.fullName) like :q or
        lower(ta.userName) like :q or
        lower(ta.email) like :q or
        lower(ta.website) like :q or
        lower(ta.bio) like :q or
        lower(m.subject) like :q or
        lower(m.content) like :q or
        lower(ma.fullName) like :q or
        lower(ma.userName) like :q or
        lower(ma.email) like :q or
        lower(ma.website) like :q or
        lower(ma.bio) like :q
"""

        def results = TekEvent.executeQuery(hql, [q: likeQuery.toLowerCase()])

        def data = results.collect { e ->
            return [
                    (e.name).toString(),
                    e.city,
                    e.description,
                    (e.id).toString()
            ]
        }

        def result = [
                "sEcho": params?.sEcho,
                "iTotalRecords": results.size(),
                "iTotalDisplayRecords": results.size(),
                "aaData": data
        ]

        return result as JSON

    }

}

