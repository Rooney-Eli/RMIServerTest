#!/bin/bash

# Define the base port
base_port=8080

# Number of containers to run
num_containers=1

docker build -t rmi-node .

# Run containers
for ((i=0; i<num_containers; i++)); do
    port=$((base_port + i))
    docker run -d -p $port:1099 --name rmi-node-$i rmi-node
    echo "Container $i running on port $port"
done