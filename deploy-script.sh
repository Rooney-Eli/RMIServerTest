#!/bin/bash


rmi_registry_port=1099

num_containers=1
network_name="awesome-network"

docker network rm $network_name >/dev/null 2>&1

docker network create $network_name

docker build -t --no-cache rmi-node .

for ((i=0; i<num_containers; i++)); do
    docker stop rmi-server-node-$i >/dev/null 2>&1

    docker rm rmi-server-node-$i >/dev/null 2>&1

    docker run -d -p $rmi_registry_port:$rmi_registry_port --name rmi-server-node-$i --network $network_name rmi-node

    echo "rmi-server-node-$i running!"
done

