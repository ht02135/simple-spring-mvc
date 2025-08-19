/*
you want a generic CliBuilder.groovy that wraps Apache Commons CLI but 
lets you define options in a Groovy-DSL style, just like the built-in 
groovy.cli.picocli.CliBuilder.

So instead of hard-coding the options in the helper class, you want 
something like:
def cli = new CliBuilder()
cli.with {
    h(longOpt: 'help',  required: false, 'Usage Information')
    c(longOpt: 'city',  required: true,  args: 1, 'City Name')
    s(longOpt: 'state', required: true,  args: 1, 'State Name')
    z(longOpt: 'zip',   required: true,  args: Option.UNLIMITED_VALUES, valueSeparator: ',', 'Zip Codes')
}
def opt = cli.parse(args)
and later be able to do:
def help = opt.h
def cityName = opt.c
def stateName = opt.s
def zipCodes = opt.zs
*/
#!/usr/bin/env groovy
@Grab('commons-cli:commons-cli:1.6.0')

import org.apache.commons.cli.*
import CliBuilder2

def cli = new CliBuilder2(
    usage: 'MyGroovyTest.groovy -c cityName -s stateName -z zipCodes',
    header: '\nAvailable options (use -h for help):\n',
    footer: '\nInformation provided via above options is used to generate printed string.\n'
)

cli.with {
    h(longOpt: 'help',  required: false, 'Usage Information')
    c(longOpt: 'city',  required: true,  args: 1, 'City Name')
    s(longOpt: 'state', required: true,  args: 1, 'State Name')
    z(longOpt: 'zip',   required: true,  args: Option.UNLIMITED_VALUES, valueSeparator: ',', 'Zip Codes (separated by comma)')
}

def opt = cli.parse(args)

if (!opt) return

def help = opt.h
def cityName = opt.c
def stateName = opt.s
def zipCodes = opt.zs

if (help) {
    cli.usage()
    System.exit(0)
}

println "cityName : ${cityName}"
println "stateName : ${stateName}"
zipCodes?.each { println "zipCodes : ${it}" }