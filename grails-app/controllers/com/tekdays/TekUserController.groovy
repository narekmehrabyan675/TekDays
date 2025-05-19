package com.tekdays

class TekUserController {
    def scaffold = TekUser
    def index() {
        [tekUserInstanceList:TekUser.list()]
    }
}
