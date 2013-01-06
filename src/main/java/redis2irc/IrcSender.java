package redis2irc;


import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

public class IrcSender {

    private final MyBot bot;

    // I find it annoying, that I have to subclass to set a property.
    public static class MyBot extends PircBot {
        public MyBot(String name) {
            this.setName(name);
        }
    }

    public IrcSender(URI server, String nickName, Set<String> channels) {
        bot = new MyBot(nickName);

        // Enable debugging output.
        bot.setVerbose(true);

        // Connect to the IRC server.
        try {
            if (server.getPort() == -1) {
                bot.connect(server.getHost());
            } else {
                bot.connect(server.getHost(), server.getPort());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not connect to IRC", e);
        } catch (IrcException e) {
            throw new RuntimeException("Problem connecting to IRC", e);

        }

        for (String channel: channels){
            bot.joinChannel("#" + channel);
        }
    }

    public void sendMessage(String channel, String message) {
        bot.sendMessage("#" + channel, message);
    }

    public void close(){
        bot.quitServer();
    }
}
