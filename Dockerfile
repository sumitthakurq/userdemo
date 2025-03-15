#Start with a base image containing Java runtime
From openjdk:17-jdk-slim

# MAINTAINER instruction is deprecated in favor of using label
# MAINTAINER eazybytes.com
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="sumit30"

# Add the application's jar to the image
COPY target/usersb-v1.jar usersb-v1.jar



# execute the application
ENTRYPOINT ["java", "-jar", "usersb-v1.jar"]