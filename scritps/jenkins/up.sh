#!/bin/sh
make build-agents
make start-jenkins
make jenkins-password
echo "Accede a http://localhost:8080 y usa la password anterior"