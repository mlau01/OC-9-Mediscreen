# Mediscreen
Mediscreen is an application about managing patient.
It offer services to doctors, clinics or other medical compagnies to handle patients file.
In future releases Mediscreen will be able to warn medical assistant of potencial deseases risks.


This application use different technologies to achieve his purpose like:
- Java 8
- Spring Boot
- Docker
- Angular

Mediscreen is built on a multi service architecture.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

- Java >= 1.8
- Docker >= 20

### Installing environment

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Docker:

https://docs.docker.com/engine/install/

### Installing App

1.Clone this project using git:

`git clone <url>`

2.Build and run the app in your docker environment:

`gradlew runDocker`

this will build all micro services needed and add them to your docker environment.

### Running Tests

Tests cover all Java micro services.

Run tests:

`gradlew test`

### URLs

Docker will open sockets for each micro services:

- Mediscreen UI: http://localhost:4200
- Mediscreen Patient restful API: http://localhost:8081
- Mediscreen Patient Note restful API: NOT YET AVAILABLE
- Mediscreen Patient Disease IA restful API: NOT YET AVAILABLE
- Mediscreen MongoDB http://localhost:27000
- Mediscreen MySQL http://localhost:3600

### Docs

A swagger doc is available for all Java micro services at http://localhost:<microservice port>/swagger-ui.html#/
