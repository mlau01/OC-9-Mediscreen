# Mediscreen
Mediscreen is an application about managing patient.
It offer services to doctors, clinics or other medical compagnies to handle patients file.
Mediscreen is able to warn medical assistant of potencial diabetes risks.


This application use different technologies to achieve his purpose like:
- Java 8
- Spring Boot
- Docker
- Angular

Mediscreen is built on a multi service architecture.

Services are:

- Mediscreen-patient: Manage patient personnal informations

- Mediscreen-note: Manage patient notes

- Mediscreen-diabetesia: Diabetes IA detection

- Mediscreen-ui: Angular Front End

There is also two database system: 

- MySQL

- MongoDB

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

3. For Angular front end you need:

- Node.js : https://nodejs.org/en/download/
- Npm : `npm install -g npm@lasted`
- Angular/cli: `npm install -g @angular/cli`

### Installing App

1.Clone this project using git:

`git clone <url>`

2.Build and run the app in your docker environment:

`gradlew runDocker`

This will build all micro services needed and add them to your docker environment in a fat container named 'mediscreen'

This also build the Angular UI and add it to docker environment.

Two database will be containerized: Mysql and MongoDB

If you want build and run only the back end (without Angular UI), you can use this task:

`gradlew runBackDocker`

### Clean Docker environment

1. If you want to uninstall all microservices and clean your Docker environment, use :

`gradlew stopDocker`

OR

`gradlew stopBackDocker`

### Running Tests

Tests cover all Java micro services.

Run tests:

`gradlew test`

### Jacoco reports

![DiabetesIA](https://user-images.githubusercontent.com/55598818/125615311-e75a26a4-5231-4ef3-a722-fa97eaba51bc.PNG)

![Patient](https://user-images.githubusercontent.com/55598818/125615529-9ed05cc7-b073-4572-8206-d668e3bad84f.png)

![Note](https://user-images.githubusercontent.com/55598818/125615708-e91150d1-d186-4e0d-97fd-d9bea3a22950.PNG)

### URLs

Docker will open sockets for each services:

- Mediscreen UI: http://localhost:4200
- Mediscreen Patient restful API: http://localhost:8081
- Mediscreen Patient Note restful API: http://localhost:8082
- Mediscreen Patient Diabetes IA API: http://localhost:8083
- Mediscreen MongoDB http://localhost:27017
- Mediscreen MySQL http://localhost:3306

### Docs

A swagger doc is available for all Java micro services at http://localhost:[microservice port]>/swagger-ui.html#/
