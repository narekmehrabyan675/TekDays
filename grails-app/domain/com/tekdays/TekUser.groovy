package com.tekdays
import org.hibernate.envers.Audited

import javax.transaction.Transactional

@Audited
class TekUser {
    String fullName
    String userName
    String password
    String email
    String website
    String bio
    Boolean activated = false
    //TekEvent event


    String toString(){fullName}

   /* int compareTo(Object other) {
        if (!(other instanceof TekUser)) return 0
        return this.id <=> other.id
    }*/

    static constraints = {
        fullName blank: false , nullable: false
        userName blank: false , nullable: false
        email blank: false , nullable: false , email: true
        website url: true , blank: true , nullable: true
        bio maxSize: 5000 , blank: true , nullable: true
        password blank: false , nullable: false , validator: { val, obj ->
            if (!val) return 'password.blank'
            if (val.size() < 8) return 'password.tooShort'
            if (!val.matches(".*[A-Z].*")) return 'password.noUppercase'
            if (!val.matches(".*[a-z].*")) return 'password.noLowercase'
            if (!val.matches(".*\\d.*")) return 'password.noDigit'
            if (!val.matches(".*[!@#\$%^&*()].*")) return 'password.noSymbol'
            return true
        }
    }

    static TekUser lookupByUsername(String username) {
        return TekUser.findByUserName(username)
    }
    //Gna tes um es kcvac
    //static belongsTo = [event : TekEvent]
}
