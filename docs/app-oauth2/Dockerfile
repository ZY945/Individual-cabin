FROM ibm-semeru-runtimes:open-17-jre
VOLUME /tmp
ADD cabin-oauth2-0.0.1-SNAPSHOT.jar app-oauth2.jar
RUN sh -c 'touch /app-oauth2.jar'
ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app-oauth2.jar"]
