package com.tekdays

class TekEventController {
    def create() {
        [tekEventInstance: new TekEvent()]
    }

    def save() {
        def organizerName = params.organizerName
        def tekEventInstance = new TekEvent(params)
        tekEventInstance.organizer = TekUser.findByFullName(organizerName)
        def volunteerIds = params.list('volunteerIds')*.toLong()
        def volunteers = TekUser.getAll(volunteerIds)
        volunteers.each { tekEventInstance.addToVolunteers(it) }

        if (!tekEventInstance.save(flush: true)) {
            render view: 'create', model: [tekEventInstance: tekEventInstance]
            return
        }
        flash.message = "Created successfully"
        redirect action: "show", id: tekEventInstance.id
    }

    def show(Long id) {
        def tekEventInstance = TekEvent.get(id)
        if (!tekEventInstance) {
            flash.message = "Not found"
            redirect action: "index"
            return
        }
        //tekEventInstance.name = "Pushkin"
        [tekEventInstance: tekEventInstance]
    }

    def edit(Long id) {
        def tekEventInstance = TekEvent.get(id)
        if (!tekEventInstance) {
            flash.message = "Not found"
            redirect action: "index"
            return
        }
        [tekEventInstance: tekEventInstance]
    }

    def update() {
        def tekEventInstance = TekEvent.get(params.id)

        if (!tekEventInstance) {
            flash.message = "TekEvent not found"
            redirect action: "index"
            return
        }
        def organizerName = params.organizerName

        tekEventInstance.properties = params  // updates
        tekEventInstance.organizer = TekUser.findByFullName(organizerName)

        def volunteerIds = params.list('volunteerIds')*.toLong()
        tekEventInstance.volunteers.clear()  // removing all
        volunteerIds.each {
            def volunteer = TekUser.get(it)
            if (volunteer) {
                tekEventInstance.addToVolunteers(volunteer)
            }
        }

        if (!tekEventInstance.save(flush: true)) {
            render view: 'edit', model: [tekEventInstance: tekEventInstance]
            return
        }

        flash.message = "TekEvent updated successfully"
        redirect action: "show", id: tekEventInstance.id
    }

    def delete(Long id){
        TekEvent.get(id).delete(flush: true)
        flash.message = "✅ Event deleted successfully"
        redirect action: "index"
    }


    def index() {
        [tekEventInstanceList: TekEvent.list()]
    }

}
