/* 
MyGroovyTest.groovy → demonstrating usage (
groovy MyGroovyTest.groovy -c cityName -s stateName -z 02135,11111
).
*/
#!/usr/bin/env groovy
@Grab('commons-cli:commons-cli:1.6.0')

import org.apache.commons.cli.*
import CliBuilderHelper

def opt = CliBuilderHelper.parseArgs(args)

if (opt.hasOption("h")) {
    println "Help requested."
    System.exit(0)
}

def cityName = opt.getOptionValue("c")
def stateName = opt.getOptionValue("s")
def zipCodes = opt.getOptionValues("z")

println "cityName : ${cityName}"
println "stateName : ${stateName}"

if (zipCodes) {
    zipCodes.each { println "zipCodes : ${it}" }
}