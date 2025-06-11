package com.tekdays

import org.apache.tools.ant.types.resources.Sort

import javax.persistence.Transient
import java.util.Date
import org.hibernate.envers.Audited
import org.hibernate.envers.NotAudited



@Audited
class TekEvent {
    //@Transient
    String city
    String name
    //String organizer//will be TekUser
    TekUser organizer
    String venue
    Date startDate
    Date endDate
    String description
    //Long version
/*
    SortedSet volunteers
*/
    SortedSet tasks
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
        sponsorships cascade: 'all'
        volunteers cascade: 'save-update'
    }
    static constraints = {
        name(blank: false)
        name validator: {val , obj ->
            if(val.contains("Test")){
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
