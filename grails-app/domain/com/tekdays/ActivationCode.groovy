package com.tekdays

import org.hibernate.envers.Audited

@Audited
class ActivationCode {
    String code
    String username
    Date createdAt = new Date()

    static constraints = {
        code blank: false , nullable: false
        username blank: false , nullable: false
    }
}
