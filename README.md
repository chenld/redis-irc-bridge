# redis-irc-bridge

Subscribes to redis channels and pushes the messages to an irc channel.
This creates a simple command line interface to push messages to irc.

## Getting started:

* Compiling

    ~~~{.bash}
    git clone git@github.com:programmiersportgruppe/redis-irc-bridge.git

    cd redis-irc-bridge

    ./build
    ~~~

    This produces a self contain executable jar.

*  Starting the bridge

    `java -jar target/redis-irc-bridge.jar -n NICKNAME -i irc://ircserver.com -r redis://localhost -m redis-channel=irc-channel`

    The mapping (`-m`) consists of a comma separated list of pairs of the form `redis-channel=irc-channel`. Note: that irc channel
    is without the trailing #.

* Send messages

    `redis-cli PUBLISH "redis-channel" "This awesomeness has been pushed via redis"`
