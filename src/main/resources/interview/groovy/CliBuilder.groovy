/* 
CliBuilder.groovy → using org.apache.commons.cli.DefaultParser and 
Options to parse CLI args.
*/
#!/usr/bin/env groovy
@Grab('commons-cli:commons-cli:1.6.0')

import org.apache.commons.cli.*

class CliBuilderHelper {

    static CommandLine parseArgs(String[] args) {
        Options options = new Options()

        options.addOption("h", "help", false, "Usage Information")
        options.addOption(Option.builder("c")
                .longOpt("city")
                .hasArg()
                .argName("cityName")
                .required(true)
                .desc("City Name")
                .build())
        options.addOption(Option.builder("s")
                .longOpt("state")
                .hasArg()
                .argName("stateName")
                .required(true)
                .desc("State Name")
                .build())
        options.addOption(Option.builder("z")
                .longOpt("zip")
                .hasArgs()
                .argName("zipCodes")
                .valueSeparator(',')
                .required(true)
                .desc("Zip Codes (comma-separated)")
                .build())

        CommandLineParser parser = new DefaultParser()
        HelpFormatter formatter = new HelpFormatter()

        try {
            return parser.parse(options, args)
        } catch (ParseException e) {
            println "\n❌ ${e.message}\n"
            formatter.printHelp("MyGroovyTest.groovy -c cityName -s stateName -z zipCodes", options)
            System.exit(1)
        }
    }
}