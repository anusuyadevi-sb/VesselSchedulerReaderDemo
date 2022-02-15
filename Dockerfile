FROM openjdk:11
EXPOSE 9991
ADD target/vesselschedulerreader.jar vesselschedulerreader.jar
ENTRYPOINT ["java","-jar","/vesselschedulerreader.jar"]
