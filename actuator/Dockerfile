FROM alpine-oraclejdk8:slim
COPY ./build/libs/KLM ASSIGNMENT-0.1.0.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
CMD ["java","-jar","KLM ASSIGNMENT-0.1.0.jar"]