#!/usr/bin/env sh

# build new application jar file
sh gradlew clean bootJar

# copy new created jar file into root directory
cp ./build/libs/SprintStatsViewer-R0.5.0.jar ./build/libs/SprintStatsViewer.jar

# create docker image
#docker build --tag=stats-viewer:latest --rm=true .

# run docker image
#docker run --name=stats-viewer --publish=8080:5000 stats-viewer:latest

# run application
#java -jar SprintStatsViewer.jar

