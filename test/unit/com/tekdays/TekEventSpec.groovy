package com.tekdays

import grails.test.mixin.TestFor
import spock.lang.Specification

///**
// * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
// **/
@TestFor(TekEvent)
class TekEventSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test toString"() {
        when: "a tekEvent has a name and city"
        def tekEvent = new TekEvent(name: 'Groovy One',
                city: 'San Francisco',
                organizer: [fullName : "Arpine"] as TekUser)
        then: "the toString method will combine them."
        tekEvent.toString() == 'Groovy One, San Francisco'
    }

    void "test to create"() {
        when:
        def event = new TekEvent(
                name: "Nareko",                  // Пустая строка — проверка blank
                city: "Yerevan",
                organizer: "Anna",
                venue: "Expo Center",
                startDate: new Date(),
                endDate: new Date()
        )
        event.validate()
        println event.errors.allErrors

        then:
        !event.validate()
        event.errors['name'].code == 'blank'
    }
}