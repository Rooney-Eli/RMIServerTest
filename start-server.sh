#!/bin/bash

rmi_registry_port=1099
# Directory containing the class files
class_directory="out/production/RMIServerTest"

echo
echo 'Killing existing rmiregistry...'
killall -9 rmiregistry >/dev/null 2>&1
pkill -f rmiregistry >/dev/null 2>&1

# Create the output directory if it doesn't exist
mkdir -p $class_directory

# Compile Java source files from src directory to out directory
javac -d $class_directory -sourcepath src src/*.java

# Set the CLASSPATH environment variable
export CLASSPATH="$(pwd)/$class_directory"

echo "Starting new rmiregistry from $(pwd)"

# Start the RMI registry
rmiregistry $rmi_registry_port >/dev/null 2>&1 &

echo

sleep 2

echo "Files in current directory ($(pwd)):"
ls

# Run the GreeterServer with the specified classpath
java -cp "$(pwd)/$class_directory" GreeterServer
