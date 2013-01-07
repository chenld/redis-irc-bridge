#!/usr/bin/env bash
set -e

mvn assembly:assembly -DdescriptorId=jar-with-dependencies
mv target/redis-irc-bridge-1-jar-with-dependencies.jar target/redis-irc-bridge.jar


cat > target/redis-irc-bridge <<'HERE'
#!/bin/bash

exec java -jar $0 "$@" &> /var/log/redis-irc-bridge/redis-irc-bridge.log &
NEW_PID=$!
echo "PID: $NEW_PID"
echo $NEW_PID > "/var/run/redis-irc-bridge/redis-irc-bridge.pid"

exit 0
HERE

cat target/redis-irc-bridge.jar >> target/redis-irc-bridge

chmod a+x target/redis-irc-bridge

echo "Jar available at target/redis-irc-bridge.jar"
