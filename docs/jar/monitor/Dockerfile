FROM ibm-semeru-runtimes:open-17-jre
VOLUME /tmp
ADD cabin-monitor-0.0.1-SNAPSHOT.jar monitor.jar
RUN sh -c 'touch /monitor.jar'
ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /monitor.jar"]
