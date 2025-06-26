grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()

    }

    dependencies {

        runtime 'org.mariadb.jdbc:mariadb-java-client:2.7.2'
        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"


        compile('org.hibernate:hibernate-envers:4.3.11.Final') {
            transitive = false
        }

        bundle('javax.websocket:javax.websocket-api:1.1') {
            // This line is necessary for deployment to Tomcat, since
            // Tomcat comes with its own version of javax.websocket-api.
            export = false
        }
        compile 'javax.websocket:javax.websocket-api:1.1'




        compile('commons-collections:commons-collections:3.2.1')

        runtime('commons-beanutils:commons-beanutils:1.9.4')
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.70" // or ":tomcat:8.0.22"
        // plugins for the compile step
        compile ":scaffolding:2.1.2"
        compile ':cache:1.1.8'
        // asset-pipeline 2.0+ requires Java 7, use version 1.9.x with Java 6
        compile ":asset-pipeline:2.5.7"
        // plugins needed at runtime but not for compilation
        runtime ":hibernate4:4.3.10" // or ":hibernate:3.6.10.18"
        runtime ":database-migration:1.4.0"
        runtime ":jquery:1.11.1"
        compile ":resources:1.2.14"
        compile 'org.grails.plugins:mail:1.0.7'
        compile ":jasper:1.10.0"

    }
}
