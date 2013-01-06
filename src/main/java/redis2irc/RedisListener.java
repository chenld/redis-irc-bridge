package redis2irc;


public interface  RedisListener {
    public void message(String channel, String message);
}
