package redis2irc;


import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.HashSet;
import java.util.Map;

public class Main {
    private final RedisListeningClient redisClient;
    private final IrcSender ircSender;

    public static void main(String[] args) {
        new Main(new Parser(args));
    }

    public Main(Parser args) {
        registerSignalHandlers();

        RedisListeningClientBuilder clientBuilder = new RedisListeningClientBuilder();

        clientBuilder.withHostname(args.redisUri.getHost());
        if (args.redisUri.getPort() != -1) {
            clientBuilder.withPort(args.redisUri.getPort());
        }

        for (String channel : args.mapping.keySet()) {
            clientBuilder.withChannel(channel);
        }

        redisClient = clientBuilder
                .withListener(new RedisMessageListener(args.mapping))
                .build();

        ircSender = new IrcSender(args.ircUri, args.ircNick, new HashSet<String>(args.mapping.values()));
    }

    private void registerSignalHandlers() {
        SignalHandler signalHandler = new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println("Shutting down...");
                if (redisClient != null) {
                    redisClient.close();
                }
                if (ircSender != null)
                    ircSender.close();
                System.exit(0);
            }
        };

        Signal.handle(new Signal("TERM"), signalHandler);
        Signal.handle(new Signal("INT"), signalHandler);
    }


    private class RedisMessageListener implements RedisListener {
        private final Map<String, String> mapping;

        public RedisMessageListener(Map<String, String> mapping) {
            this.mapping = mapping;
        }

        @Override
        public void message(String channel, String message) {
            if (mapping.containsKey(channel)) {
                ircSender.sendMessage(mapping.get(channel), message);
            }
        }
    }
}