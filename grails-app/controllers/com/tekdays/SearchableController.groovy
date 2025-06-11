package com.tekdays

import grails.converters.JSON

class SearchableController {

    def datatablesSourceService
    EventService eventService

    def dtList() {
    }
    def updated() {
    }
    def updatedHQL() {
    }


    def dataTablesRenderer() {
        def propertiesToRender = ['name', 'city', 'description']
        def entityName = 'TekEvent'
        render datatablesSourceService.dataTablesSource(propertiesToRender, entityName, params)
    }

    def dataTablesRendererupdated() {
    def events1 = eventService.search1(params)

        render events1
    }
    def dataTablesRendererupdatedHQL() {
        def events1 = eventService.searchHQL(params)

        render events1
    }

}
