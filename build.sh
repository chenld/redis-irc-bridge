#!/usr/bin/env bash
set -e

mvn assembly:assembly -DdescriptorId=jar-with-dependencies
mv target/redis-irc-bridge-1-jar-with-dependencies.jar target/redis-irc-bridge.jar


cat > target/redis-irc-bridge <<'HERE'
#!/bin/sh

exec java -jar $0 "$@"


HERE

cat target/redis-irc-bridge.jar >> target/redis-irc-bridge

chmod a+x target/redis-irc-bridge

echo "Jar available at target/redis-irc-bridge.jar"
