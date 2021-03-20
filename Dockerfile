FROM java:8

WORKDIR /app

COPY siji.jar .

CMD ["--server.port=9999"]


EXPOSE 9999

ENTRYPOINT java -jar siji.jar