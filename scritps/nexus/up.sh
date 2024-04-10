#!/bin/sh
make start-nexus-jenkins
sleep 30s
make nexus-password
echo "Accede a http://localhost:8081 y usa la password anterior para crear el primer usuario."