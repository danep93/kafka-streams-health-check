# Kafka Streams Health Check
This app is a standalone jar reading the `count` metric. If it is `1.0` it throws an exception hence finishes with a non-zero code. 
When deployed in Kubernetes, the Kafka Stream app needs to enable its MBeans:
```
-Dcom.sun.management.jmxremote.port=5555 \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false
```

The health check jar needs to be included in the Kafka Streams Kubernetes pod and configured as well:
```
livenessProbe:
  exec:
    command:
    - java
    - -jar
    - kafka-streams-health-check-1.0.0.jar
```

More about this concept and other ways to monitor Kafka Streams apps is available in this [dedicated blog post](https://blog.softwaremill.com/whats-the-proper-kubernetes-health-check-for-a-kafka-streams-application-c9c00a112581). 

# Development

Publish the artifact in bintray:
```
$ BINTRAY_USER=kijanowski BINTRAY_KEY=xyz ./gradlew bintrayUpload
```