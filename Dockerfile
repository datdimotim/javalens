FROM ubuntu:18.04
RUN apt update
RUN apt install -y default-jdk
RUN apt install -y maven

COPY ./ /project
WORKDIR /project
CMD mvn clean install