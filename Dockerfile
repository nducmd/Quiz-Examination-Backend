# base image
FROM openjdk:17-oracle
# copy jar from target to image (root folder)
COPY target/exam-system-0.0.1-SNAPSHOT.jar exam-system-0.0.1-SNAPSHOT.jar
LABEL authors="MinhDuc"
# run jar
ENTRYPOINT ["java", "-jar", "/exam-system-0.0.1-SNAPSHOT.jar"]