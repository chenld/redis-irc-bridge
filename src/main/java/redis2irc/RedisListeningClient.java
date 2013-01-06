package redis2irc;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.List;


public class RedisListeningClient {

    private final Jedis jedis;

    private Thread listenerThread;
    private JedisPubSubListener jedisPubSub;

    public RedisListeningClient(String hostname, int port, int timeout, final List<String> channels, final RedisListener listener) {
        jedis = new Jedis(hostname, port, timeout);

        listenerThread = new Thread( new Runnable() {
            @Override
            public void run() {
                jedisPubSub = new JedisPubSubListener(listener);
                jedis.subscribe(jedisPubSub, channels.toArray(new String[channels.size()]));
            }
        });

        listenerThread.start();
    }

    public void close() {
        jedisPubSub.unsubscribe();

        try {
            listenerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("We had a problem!", e);

        }
    }

    private static class JedisPubSubListener extends JedisPubSub {

        private final RedisListener listener;

        public JedisPubSubListener(RedisListener listener) {
            this.listener = listener;
        }

        @Override
        public void onMessage(String s, String s2) {
            listener.message(s, s2);
        }

        @Override
        public void onPMessage(String s, String s2, String s3) {}

        @Override
        public void onSubscribe(String s, int i) {}

        @Override
        public void onUnsubscribe(String s, int i) {}

        @Override
        public void onPUnsubscribe(String s, int i) {}

        @Override
        public void onPSubscribe(String s, int i) {}
    }
}
