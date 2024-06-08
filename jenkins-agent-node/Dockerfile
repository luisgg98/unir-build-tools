FROM jenkins/inbound-agent as agent

FROM node:lts-alpine3.20

RUN apk -U add openjdk21-jre git make

COPY --from=agent /usr/local/bin/jenkins-agent /usr/local/bin/jenkins-agent
COPY --from=agent /usr/share/jenkins/agent.jar /usr/share/jenkins/agent.jar

ENTRYPOINT ["/usr/local/bin/jenkins-agent"]