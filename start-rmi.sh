#!/bin/bash

registryPort=1099
host=localhost

# Set the source and build output directories
sourceDir="src"
buildDir="out/production/RMIServerTest"

echo
echo 'Killing existing rmiregistry...'
killall -9 rmiregistry >/dev/null 2>&1
pkill -f rmiregistry >/dev/null 2>&1

echo "Starting new rmiregistry from $(pwd)"
# Set the classpath to include both the source and build directories
export CLASSPATH="${sourceDir}:${buildDir}:${CLASSPATH}"

# Start the RMI registry
rmiregistry $registryPort >/dev/null 2>&1 &

echo
