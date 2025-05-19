import com.tekdays.*

class BootStrap {

    def init = { servletContext ->
        new TekUser(fullName: 'John Doe',
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
    }

    def destroy = {
    }
}
