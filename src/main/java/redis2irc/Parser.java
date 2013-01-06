package redis2irc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/** Parses command line arguments, I am so disappointed with the commons-cli parser. */
public class Parser {
    public final URI ircUri;
    public final URI redisUri;

    public final Map<String, String> mapping;

    public Parser(String[] args) {
        Options options = new Options();
        options.addOption("i", "irc-url", true, "IRC url");
        options.addOption("r", "redis-url", true, "Redis url");
        options.addOption("m", "channel mapping", true,
                "Redis to IRC channel mapping comma separated list of '=' separated pairs");


        GnuParser parser = new GnuParser();
        try {
            CommandLine commandline = parser.parse(options, args);

            if (commandline.getOptionValue("i") == null)
                throw new RuntimeException("Missing irc uri");

            if (commandline.getOptionValue("r") == null)
                throw new RuntimeException("Missing redis uri");

            if (commandline.getOptionValue("m") == null)
                throw new RuntimeException("Missing channel mapping");

            this.ircUri = URI.create(commandline.getOptionValue("i"));

            this.redisUri = URI.create(commandline.getOptionValue("r"));

            String mappingText = commandline.getOptionValue("m");

            this.mapping = new HashMap<String, String>();
            for (String pair: mappingText.split(",")) {
                String[] split = pair.split("=");
                mapping.put(split[0], split[1]);
            }

        } catch (ParseException e) {
            throw new RuntimeException("Could not parse command line args", e);
        }
    }
}
