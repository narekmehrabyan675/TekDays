package com.takdays

import com.tekdays.TaskService
import com.tekdays.TekEvent
import com.tekdays.TekUser
import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import grails.test.spock.IntegrationSpec



//@Integration
/*
@TestFor(TaskService)
*/
class TaskServiceIntegrationSpec extends IntegrationSpec {
    TaskService taskService

    def setup() {
        new TekUser(fullName: 'Tammy Tester', userName: 'tester',
        email: 'tester@test.com', website: 'test.com',
        bio: 'A test person').save(flush: true)
    }

    def cleanup() {
    }

    void "test addDefaultTasks"() {
        when: "we pass an event to taskService.addDefaultTasks"
        def volunteers = []
        volunteers.add(TekUser.findAll())
        def e = new TekEvent(name:'Test Event',
                city:'TestCity, USA',
                description:'Test Description',
                organizer:TekUser.findByUserName('tester' ),
                volunteers: volunteers,
                venue:'TestCenter' ,
                startDate:new Date(),
                endDate:new Date() + 1)
        taskService.addDefaultTasks(e)

        then: "the event will have 6 default tasks"
        e.tasks.size() == 6
    }
}
