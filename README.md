# Hello Rest
[https://github.zhaw.ch/bacn/ase2-spring-boot-hellorest](https://github.zhaw.ch/bacn/ase2-spring-boot-hellorest)

This repository contains a Spring Boot tutorial. The tutorial consists of several steps creating a rest backend. Each step is available under a **branch**.

- [master](readme/master.md): initial project setup with spring boot initializer with model, repository, data-rest, unit-test, h2-console and openapi 3.0.
- [database-bootstrap](readme/database-bootstrap.md): add a record with java to the database.
- [flyway](readme/flyway.md): data migration with flyway (create schema and add data).
- [liquibase](readme/liquibase.md): database migration with liquibase (create schema and add data), based on database-bootstrap branch.
- [profiles](readme/profiles.md): add profiles for dev (development) and specific profiles for h2 or mysql database, add environment variables.
- [docker](readme/docker.md): create a container and use docker-compose files for starting the backend with h2 database or mysql database incl. adminer.
- [rest](readme/rest.md): create your own rest endpoints.


<br/>

## Branches in this tutorial

The following illustration shows how the branches of this tutorial have been created:

![branch-flow-all.png](readme/branch-flow-all.png)





