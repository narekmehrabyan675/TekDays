package com.tekdays

import com.sun.org.apache.xpath.internal.operations.Bool
import org.apache.tools.ant.types.resources.Sort

import javax.persistence.Transient
import java.util.Date
import org.hibernate.envers.Audited



@Audited
class TekEvent {
    String city
    String name
    TekUser organizer
    String venue
    Date startDate
    Date endDate
    String description
    SortedSet tasks
    String lockedBy
    Boolean locked = false
    Date lockedAt = new Date()
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

        name blank: false, nullable: false
        city(blank:false)
        description(blank: true, nullable: true , maxSize: 500)
        respondents nullable: true , blank:true
        volunteers nullable: true , blank:true
        sponsorships nullable: true , blank:true
        tasks nullable: true
        messages nullable: true
    }
}
