
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

    This produces a binary that contains all the classes and a little bootstrapper.


*  Starting the bridge

    `target/redis-irc-bridge -n NICKNAME -i irc://ircserver.com -r redis://localhost -m redis-channel=irc-channel`

    The mapping (`-m`) consists of a comma separated list of pairs of the form `redis-channel=irc-channel`. Note: that irc channel
    is without the leading #.

* Send messages

    `redis-cli PUBLISH "redis-channel" "This awesomeness has been pushed via redis"`
