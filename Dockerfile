FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY ./build/libs/SprintStatsViewer.jar /opt/sprint-stats-viewer/lib/

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/opt/sprint-stats-viewer/lib/SprintStatsViewer.jar"]

EXPOSE 5000

