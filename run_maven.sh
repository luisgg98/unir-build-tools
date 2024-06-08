#!/bin/bash
JENKINS_MAVEN_AGENT_SECRET=50c9cf3897e7b79c8a84a299524d0e98ff5dd860f506e9f52ab1e67455dd53f6
docker run -d --rm --network jenkins --name jenkins-agent-maven --init --env JENKINS_URL=http://jenkins-server:8080 --env JENKINS_AGENT_NAME=agent02 --env JENKINS_SECRET=${JENKINS_MAVEN_AGENT_SECRET} --env JENKINS_AGENT_WORKDIR=/home/jenkins/agent jenkins-agent-maven