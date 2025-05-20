package com.tekdays

import java.util.Date

class TekEvent {
    String city
    String name
    //String organizer//will be TekUser
    TekUser organizer
    String venue
    Date startDate
    Date endDate
    String description
    static hasMany = [volunteers : TekUser , respondents : String , sponsorships: Sponsorship , tasks: Task , messages: TekMessage]

    String toString(){
        "$name, $city"
    }
    static mapping ={
        table 'events'
        id column:'event_id'
        name column: 'persons_name'
        city column: 'pleace'
        description sqlType: 'TEXT'
    }
    static constraints = {
        name(blank: false)
        name validator: {val , obj ->
            if(val.contains("Narek")){
                return 'username.no.Narek'
            }}
        city(blank:false)
        description(blank: true, nullable: true , maxSize: 500)
        respondents nullable: true , blank:true
        volunteers nullable: true , blank:true
        sponsorships nullable: true , blank:true
        tasks nullable: true
        messages nullable: true
    }
}
