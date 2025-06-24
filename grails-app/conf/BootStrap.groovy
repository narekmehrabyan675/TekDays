import com.tekdays.*


class BootStrap {

    def init = { servletContext ->
        /*servletContext.addListener(new ChatroomEndpoint())
        println "Listener registered"*/
        /*new TekUser(fullName: 'John Doe',
                userName: 'jdoe',
                password: 't0ps3cr3t',
                email: 'jdoe@johnsgroovyshop.com',
                website: 'blog.johnsgroovyshop.com',
                bio: 'John has been programming for over 40 years. ...').save()

        new TekUser(fullName: 'John Deere',
                userName: 'tractorman',
                password: 't0ps3cr3t',
                email: 'john.deere@porkproducers.org',
                website: 'www.perl.porkproducers.org',
                bio: 'John is a top notch Perl programmer and a ...').save()
        def s1 = new Sponsor(name: 'Contegix' ,
                website:'http://www.contegix.com',
                description:'Beyond Managed Hosting for your Enterprise').save()
        def s2 = new Sponsor(name:'Object Computing Incorporated',
                website:'http://ociweb.com',
                description:'An OO Software Engineering Company').save()



        def event1 = new TekEvent(name:'Getway Code Camp',
                     city: 'Yerevan',
                     organizer: TekUser.findByFullName('John Doe'),
                     venue: 'TBD',
                     startDate: new Date('11/21/2013'),
                     endDate: new Date('11/21/2013'),
                     description: '''This conference will bring
                        coders ...''').save()
        def event2 = ( new TekEvent(name: 'Perl Before Swine',

                city: 'Austin, MN',
                organizer: TekUser.findByFullName('John Deere'),
                venue: 'SPAM Museum',
                startDate: new Date('11/2/2013'),
                endDate: new Date('11/2/2013'),
                description: 'Join the Perl programmers of the ...'))
                event2.addToRespondents('ben@grailsmail.com')
                event2.addToRespondents('zachary@linuxgurus.org')
                event2.addToRespondents('solomon@bootstrapwelding.com')
                event2.save()

        def sp1 = new Sponsorship(event: event1,sponsor: s1 ,
                                contributionType: 'Other' , description: 'Cool T-Shirts').save()
        //event1.addToSponsorships(sp1).save()
        //event2.addToSponsorships(sp1).save()
        def sp2 = new Sponsorship(event: event1 , sponsor: s2 ,
                                contributionType: 'Venue' , description: 'Will be paying for the Moscone').save()
        def sp3 = new Sponsorship(event: event2 , sponsor: s1 ,
                contributionType: 'Venue' , description: '123456').save()
        //event2.addToSponsorships(sp3).save()
*/    }

    def destroy = {
    }
}
