FROM ibm-semeru-runtimes:open-17-jre
VOLUME /tmp
ADD admin-0.0.1-SNAPSHOT.jar admin.jar
RUN sh -c 'touch /admin.jar'
ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /admin.jar"]
