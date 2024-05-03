#!/bin/bash

registryPort=1099

echo
echo 'Killing existing rmiregistry...'
killall -9 rmiregistry >/dev/null 2>&1
pkill -f rmiregistry >/dev/null 2>&1

echo "Starting new rmiregistry from $(pwd)"
export CLASSPATH="${CLASSPATH}"

# Start the RMI registry
rmiregistry $registryPort >/dev/null 2>&1 &

echo

sleep 2
echo "$(pwd)"

java GreeterServer