package redis2irc;

import java.util.ArrayList;
import java.util.List;

public class RedisListeningClientBuilder {

    String hostname = "localhost";
    int port = 6379;
    int timeout = 0;

    List<String> channels = new ArrayList<String>();
    private RedisListener listener;

    RedisListeningClientBuilder withHostname(String hostname){
        this.hostname = hostname;
        return this;
    }

    RedisListeningClientBuilder withPort(int port){
        this.port = port;
        return this;
    }

    RedisListeningClientBuilder withTimeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    RedisListeningClientBuilder withChannel(String channel){
        this.channels.add(channel);
        return this;
    }

    RedisListeningClientBuilder withListener(RedisListener listener){
        this.listener=listener;
        return this;
    }

    public RedisListeningClient build(){
        return new RedisListeningClient(hostname, port, timeout, channels, listener);
    }

}
