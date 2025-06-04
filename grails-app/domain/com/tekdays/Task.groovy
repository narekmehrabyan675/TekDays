package com.tekdays

import com.sun.org.apache.xpath.internal.operations.Bool
import org.hibernate.envers.Audited

@Audited
class Task {
    String title
    String notes
    TekUser assignedTo
    Date dueDate
    TekEvent event
    Boolean completed
    static constraints = {
        title blank: false
        notes blank: true, nullable: true, maxSize: 5000
        assignedTo nullable: true
        dueDate nullable: true
        completed nullable: true
    }
    static belongsTo = TekEvent
}
