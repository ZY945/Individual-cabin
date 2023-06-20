FROM ibm-semeru-runtimes:open-17-jre
VOLUME /tmp
ADD cabin-IM-0.0.1-SNAPSHOT.jar cabin-IM.jar
RUN sh -c 'touch /cabin-IM.jar'
ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /cabin-IM.jar"]
