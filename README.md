[![Application Tests](https://github.com/BranislavBeno/Sprint-Statistics-Viewer/actions/workflows/tests.yml/badge.svg)](https://github.com/BranislavBeno/Sprint-Statistics-Viewer/actions/workflows/tests.yml)
[![Docker Image Deploy](https://github.com/BranislavBeno/Sprint-Statistics-Viewer/actions/workflows/deploy.yml/badge.svg)](https://github.com/BranislavBeno/Sprint-Statistics-Viewer/actions/workflows/deploy.yml)  
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_SprintStatsViewer&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=BranislavBeno_SprintStatsViewer)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_SprintStatsViewer&metric=coverage)](https://sonarcloud.io/summary/new_code?id=BranislavBeno_SprintStatsViewer)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=BranislavBeno_SprintStatsViewer&metric=ncloc)](https://sonarcloud.io/dashboard?id=BranislavBeno_SprintStatsViewer)  
[![](https://img.shields.io/badge/Java-21-blue)](/build.gradle)
[![](https://img.shields.io/badge/Spring%20Boot-3.4.1-blue)](/build.gradle)
[![](https://img.shields.io/badge/Testcontainers-1.20.4-blue)](/build.gradle)
[![](https://img.shields.io/badge/Gradle-8.12-blue)](/gradle/wrapper/gradle-wrapper.properties)
[![](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)  

# Sprint statistics viewer

This web application offers various statistics related to the course and history of scrum sprints in a multi-team project.

Currently, the following indicators can be tracked:

*  Sprint progress overview in story points pro team plus remaining time till end of sprints in days as well.
*  Sprint related KPI's.
*  Summarized refinement for all teams.
*  Sprint goals pro team.
*  Team's scope focus - how was set priority to particular scope of features.
*  Team's velocity - average value of finished story points for the last 12 sprints.
*  Team's work proportion - the ratio between finished stories and bugs.
*  Team's work refinement - how much story points was already refined and assigned to one of next 4 sprints.
