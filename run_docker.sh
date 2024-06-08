#!/bin/bash
JENKINS_DOCKER_AGENT_SECRET=0a8a9bffca32b7fe8ef05590198be87b46d74f98a441f90a8d27c5ab4ba2565b
docker run -d --rm --network jenkins --name jenkins-agent-docker --init --env DOCKER_HOST=tcp://docker:2376 --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 --volume jenkins-docker-certs:/certs/client:ro --env JENKINS_URL=http://jenkins-server:8080 --env JENKINS_AGENT_NAME=agent01 --env JENKINS_SECRET=${JENKINS_DOCKER_AGENT_SECRET} --env JENKINS_AGENT_WORKDIR=/home/jenkins/agent jenkins-agent-docker
