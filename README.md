# Hello Rest
[https://github.zhaw.ch/bacn/ase2-spring-boot-hellorest](https://github.zhaw.ch/bacn/ase2-spring-boot-hellorest)

This repository contains a Spring Boot tutorial. The tutorial consists of several step creating a rest backend. Each step is available under a branch

- [master](readme/master.md): initial project setup with spring boot initializer with model, repository, data-rest, unit-test, h2-console and openapi 3.0
- database-bootstrap: add a record with java to the database
- flyway: data migration with flyway (create schema and add data)
- liquibase: database migration with liquibase (create schema and add data), based on database-bootstrap branch
- profiles: add profiles for dev (development) and specific profiles for h2 or mysql database, add environment variables
- docker: create a container and use docker-compose files for starting the backend with h2 database or mysql database incl. adminer
- rest: create your own rest endpoints




