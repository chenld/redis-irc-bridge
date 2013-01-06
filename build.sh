#!/usr/bin/env bash
set -e

mvn assembly:assembly -DdescriptorId=jar-with-dependencies
mv target/redis-irc-bridge-1-jar-with-dependencies.jar target/redis-irc-bridge.jar

echo "Jar available at target/redis-irc-bridge.jar"
