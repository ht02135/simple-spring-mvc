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

class CliBuilder {
    private Options options = new Options()
    private Map<String, Option> shortOpts = [:]

    def methodMissing(String name, args) {
        // args = [Map of attributes, String description]
        def map = args[0] as Map
        def desc = args[1] as String

        def builder = Option.builder(name)
        if (map.longOpt) builder.longOpt(map.longOpt)
        if (map.required) builder.required(true)
        if (map.args != null) {
            if (map.args == Option.UNLIMITED_VALUES) {
                builder.hasArgs()
            } else if (map.args instanceof Integer) {
                builder.numberOfArgs(map.args as int)
            }
        }
        if (map.valueSeparator) builder.valueSeparator(map.valueSeparator as char)
        builder.desc(desc)

        Option opt = builder.build()
        options.addOption(opt)
        shortOpts[name] = opt
    }

    def parse(String[] args) {
        def parser = new DefaultParser()
        def cmd = parser.parse(options, args)

        // Wrap CommandLine to allow property-style access
        return new GroovyCommandLine(cmd, shortOpts)
    }

    void usage(String usage = "Usage:", String header = "", String footer = "") {
        new HelpFormatter().printHelp(usage, header, options, footer, true)
    }
}

class GroovyCommandLine {
    private final CommandLine cmd
    private final Map<String, Option> shortOpts

    GroovyCommandLine(CommandLine cmd, Map<String, Option> shortOpts) {
        this.cmd = cmd
        this.shortOpts = shortOpts
    }

    def propertyMissing(String name) {
        if (name.endsWith("s")) {
            def key = name[0..-2]  // plural → original short option
            return cmd.getOptionValues(key)
        }
        return cmd.getOptionValue(name)
    }

    boolean hasOption(String name) {
        return cmd.hasOption(name)
    }

    def each(Closure c) {
        shortOpts.keySet().each { c(it) }
    }
}