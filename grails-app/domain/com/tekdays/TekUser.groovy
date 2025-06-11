package com.tekdays
import org.hibernate.envers.Audited

@Audited
class TekUser {
    String fullName
    String userName
    String password
    String email
    String website
    String bio
    //TekEvent event


    String toString(){fullName}

   /* int compareTo(Object other) {
        if (!(other instanceof TekUser)) return 0
        return this.id <=> other.id
    }*/

    static constraints = {
        fullName()
        userName()
        email()
        website()
        bio maxSize: 5000
    }
    //Gna tes um es kcvac
    //static belongsTo = [event : TekEvent]
}
