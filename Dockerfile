FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=9080"]

EXPOSE 9080

ENTRYPOINT ["java","-jar","/app.jar"]

