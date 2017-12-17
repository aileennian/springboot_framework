FROM ubuntu:14.04
# Set timezone as china/shanghai
RUN cp /usr/share/zoneinfo/PRC /etc/localtime
RUN mkdir /usr/java
# Copy jre install file
COPY jre-8u111-linux-x64.tar.gz /usr/java/
WORKDIR /usr/java/
RUN tar zxvf /usr/java/jre-8u111-linux-x64.tar.gz
RUN rm -rf /usr/java/jre-8u111-linux-x64.tar.gz
# Eureka server directory
RUN mkdir /eureka
# Copy app jar file
COPY F-EurekaServer1-0.0.1-SNAPSHOT.jar /eureka/
EXPOSE 8761
CMD ["/usr/java/jre1.8.0_111/bin/java", "-jar", "/eureka/F-EurekaServer1-0.0.1-SNAPSHOT.jar"]