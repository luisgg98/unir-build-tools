FROM jenkins/inbound-agent as agent

FROM alpine:3.20.0

RUN apk -U add openjdk21-jre docker git make

COPY --from=agent /usr/local/bin/jenkins-agent /usr/local/bin/jenkins-agent
COPY --from=agent /usr/share/jenkins/agent.jar /usr/share/jenkins/agent.jar

ENTRYPOINT ["/usr/local/bin/jenkins-agent"]