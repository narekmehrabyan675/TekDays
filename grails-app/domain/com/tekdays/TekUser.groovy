package com.tekdays

class TekUser {
    String fullName
    String userName
    String password
    String email
    String website
    String bio
    //TekEvent event


    String toString(){fullName}

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
