package com.tekdays

class SponsorController {
    def scaffold = Sponsor
    def index() {
        [sponsorInstanceList:Sponsor.list()]
    }
}
