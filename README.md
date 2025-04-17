# <div align="center"><img src="https://deceptivelysimpletechnologies.com/wp-content/uploads/2020/07/Deceptively-Simple-Technologies-Inc-Logo-aed1783e-abbb-4e54-a080-df93cc1991ed_70x70.png" alt="Logo" width="20" height="20"> Deceptively Simple Technologies Inc's <img src="https://deceptivelysimpletechnologies.com/wp-content/uploads/2020/07/Deceptively-Simple-Technologies-Inc-Logo-aed1783e-abbb-4e54-a080-df93cc1991ed_70x70.png" alt="Logo" width="20" height="20"> Adaptív Application Foundation (AAF) <img src="https://deceptivelysimpletechnologies.com/wp-content/uploads/2020/07/Deceptively-Simple-Technologies-Inc-Logo-aed1783e-abbb-4e54-a080-df93cc1991ed_70x70.png" alt="Logo" width="20" height="20"> Data Layer (AAF Data)</div>
## <div align="center">Locally-Sourced, Artisanal Data™</div>
### <div align="center">Delivering Foundational Business Agility</div>
<br>
<div align="center">Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.</div>

<div id="top"></div>
<!--
*** Thanks to Othneil Drew's Best-README-Template
*** https://github.com/othneildrew/Best-README-Template/blob/master/README.md
-->


<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<!-- [![Contributors][contributors-shield]][contributors-url] -->
<!-- [![Forks][forks-shield]][forks-url] -->
<!-- [![Stargazers][stars-shield]][stars-url] -->
<!-- [![Issues][issues-shield]][issues-url] -->
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


## TL;DR
**Today, AAF Data is**:
- A standalone set of
  - Generalized **business entity models**, e.g. Person, Organization, OrganizationalUnit, Employee, etc,
  - **Database creation scripts** for roles, schemas, tables, constraints, indexes, functions, and
  - **Scripted lookup/reference data**, e.g. EntityType (Person, Organization, OrganizationalUnit, Employee, etc), EntitySubtype (Organization - C Corp, S Corp, LLC, etc, OrganizationalUnit - Region, Division, Department, etc), etc,
- A Dockerized, **REST**ful **entity data microservice** (EDM) providing
  - **C**reate,
  - **R**ead,
  - **U**pdate, and
  - **D**elete (**CRUD**) operations,
- A Dockerized, **REST**ful **system data service** (SDS) providing the capability to
    - Create **N**ew business entity definition,
    - **C**lone Existing business entity definition,
    - Create New business entity **A**ttribute,
    - **A**ssociate business entity attribute with business entity definition(s),
    - **P**ublish entities, attributes, and associations, thereby creating corresponding new database schemas, tables, constraints, indexes, etc, and
    - **U**pdate the published status of entities, attributes, and associations,
- And **SwaggerDoc** **OpenAPI** application programming interface (API) **documentation**.


**Project Website**: [Deceptively Simple Technologies Inc](https://deceptivelysimpletechnologies.com/tools/adaptiv-application-foundation-aaf/aaf-data/)


**To Provide Product Feedback & Be Notified of Updates**: [2-Minute Feedback Form](https://docs.google.com/forms/d/1hSlFRKYAgQf97kp533ccgLwm2UuhjXsaQFCR7On5oIg/edit)

**Note**: Cygnus Technology Services, who is collecting product feedback, is a Deceptively Simple Technologies (DST) **service partner**, providing AAF Data  **implementation**, **customization**, **training**, and **support** services.  If you are interested in becoming a DST partner, please email partners@deceptivelysimpletechnologies.com.


**Features**
  - Containerized. Services run in Docker containers, available from DockerHub.
<!-- -->
  - The entity data microservice (**EDM**):
    - **Will validate each HTTP request** (near future) using a JSON Web Token (**JWT**) mechanism with optional cryptographic signing and encryption capabilities,
    - **Persists and retrieves business entity data** via its **API**, which calls low-level `POST`, `GET`, `PATCH`, and “soft” DELETE database functions to ensure data integrity and performance -- **NO direct table access**,
    - **Consistently follows RESTful style and best practices**,
    - **Returns standard and appropriate HTTP response codes**, e.g. `200 OK`, `201 Created`, `400 Bad Request`, `401 Unauthorized`, `404 Not Found`, `422 Unprocessable Entity`, `500 Internal Server Error`, etc
    - **Accepts and returns structured JSON resource/entity data** in HTTP request/response bodies as specified for each business entity, and 
<!--     - **Includes** `entities` **in the request URL**, e.g. `/entities/organization/1234`, to partition low-level resource/entity operations from future high-level process invocation, e.g. `/processes/InformationSystem/RegisterInformationSystemUser/` -->
- The system data service (**SDS**):
    - **Will validate each HTTP request** (near future) using a JSON Web Token (**JWT**) mechanism with optional cryptographic signing and encryption capabilities,
    - **Enables authorized entity Modelers and Publishers** to define new EntityTypeDefinitions, EntityTypeAttribtues, and EntityTypeDefinitionEntityTypeAttributeAssociations via its **API**, which calls high-level process `POST` and low-level entity `POST`, `PATCH`, and “soft” DELETE database functions to add (or "publish") these new entities, attributes, and associations to the database, resulting in new model-driven data structures, e.g. SCHEMAs, TABLEs, COLUMNs, CONSTRAINTs, INDEXs, etc,
    - **Consistently follows RESTful style and best practices**,
    - **Returns standard and appropriate HTTP response codes**, e.g. `200 OK`, `201 Created`, `400 Bad Request`, `401 Unauthenticated`, `404 Not Found`, `422 Unprocessable Entity`, 500 Internal Server Error`, etc
    - **Accepts and returns structured JSON resource/entity data** in HTTP request/response bodies as specified for each business entity, and
<!--     - **Includes** `processes` **in the request URL**, e.g. `/processes/organization/1234`, to partition low-level resource/entity operations from future high-level process invocation, e.g. `/processes/InformationSystem/RegisterInformationSystemUser/` -->


**Rules**
- **The custom database roles**, e.g. AafCoreOwner, AafCoreDataReadWrite, etc, **are to be used appropriately** (as designed and demonstrated in this project) **to improve data security**.
- **The current set of business entities and their descriptions can be retrieved** using the `GetEntityTypeDefinitions` database function and includes:
  - *`EntityTypeDefinition`
  - *`EntityTypeAttribute`
  - *`EntityTypeDefinitionEntityTypeAttributeAssociation`
  - `EntitySubtype`
  - `EntitySubtypeHierarchy`
  - `EntityType`
  - `Language`
  - `GeographicUnit`
  - `GeographicUnitHierarchy`
  - `Locale`
  - `Currency`
  - `Periodicity`
  - `PostalAddress`
  - `Organization`
  - `OrganizationalUnit`
  - `OrganizationalUnitHierarchy`
  - `Person`
  - `Employee`
  - `LegalEntity`
- **The set of "origin" (*) business entities can produce other business entities and their corresponding database structures**
- **The current set of business entity attributes and their descriptions can be retrieved** using the `GetEntityTypeAttributes` database function and includes:
  - *`Id` `bigint`
  - *`Uuid` `uuid`
  - *`EntitySubtypeId` `bigint`
  - *`TextKey` `varchar`
  - `LocalizedName` `varchar`
  - `LocalizedDescription` `varchar`
  - `LocalizedAbbreviation` `varchar`
  - *`ResourceName` `varchar`
  - *`Ordinal` `bigint`
  - *`IsActive` `boolean`
  - *`CorrelationUuid` `uuid`
  - *`Digest` `varchar`
  - *`CreatedAtDateTimeUtc` `timestamp`
  - *`CreatedByInformationSystemUserId` `bigint`
  - *`UpdatedAtDateTimeUtc` `timestamp`
  - *`UpdatedByInformationSystemUserId` `bigint`
  - *`DeletedAtDateTimeUtc` `timestamp`
  - *`DeletedByInformationSystemUserId` `bigint`
- **The set of common (*) business entity attributes are to be included in each business entity definition** and corresponding database table, etc.
- **These business entity attribute names, data types, Pascal case, etc are intentional, carefully chosen, and NOT to be altered**.
- When naming entities and attributes, we **avoid acronyms and abbreviations**, and we strive to think carefully about, to "**call things what they really are**", and to **be specific**, e.g. AtDateTimeUtc.
- **Each entity’s database table is partitioned** using a corresponding Postgres schema, e.g. "Organization"."Organization".
- EntitySubtype, GeographicUnit, GeographicUnitHierarchy, etc represent scripted (canonical) lists of options like an entity’s subtypes, etc.
- `Unknown` (Id = -1) and `None` (Id = 0) are standard `EntitySubtype` and `EntitySubtype` values indicating a currently unknown/to be determined or an inapplicable entity or entity subtype value, respectively.


Soon **AAF Data will also include**:
- **Terraform scripts** for creating AWS infrastruture and deploying the AAF Data services to the cloud,
- **JWT helper functions** (encode/decode),
- A web-based **business entity modeler** for extending or modifying these models and their scripted data,
- A **structure service** for conforming the entity data structures to the model, and
- **CRUD** operation **event publishing**.


<!-- GETTING STARTED -->
## Getting Started

This is the second AAF Data release, and it supports very simple local (LOC) and minimal (MIN) environments, primarily to demonstrate its purpose and basic capabilities.

Please bear in mind that this is a work in progress.  There are many more features and capabilities to come, and features are often "layered", 
meaning that they have dependencies on other features that must be implemented first.  A good example of this is data validation, which is implemented 
with database column constraints in the data layer but which is enhanced with code-based validation in the servic layer and which will be further enhanced in the presentation layer. 


### Prerequisites

You will need:

1. Root (default `postgres` role) access a PostgreSQL database server
2. Either a local copy of the aafdata/EntityDataMicroservice code (and an integrated development environment like JetBrains IntelliJ IDEA), by cloning the code repository, or a DockerHub account to pull and run the Docker image

### Installation

Before running the EntityDataMicroservice, you must:

1. Change directory (`cd`) to `aafdata/src/main/data/`, make executable (`chmod +x <fileName>`), and run the eight (8) BASH command line scripts in `aafdata/src/main/data/` in your PostgreSQL database server in the specified order and with the specified roles (changing the `postgres` role's password if necessary) to create the necessary roles, schemas, tables, functions, and data:
   1. `./001-CreateDatabaseServer.sh PostgreSQL 14 localhost 5432 postgres postgres postgres`
   1. `./002-CreateDatabaseRoleAafCoreOwnerAsPostgresRole.sh PostgreSQL 14 localhost 5432 postgres postgres postgres`
   1. `./003-CreateDatabaseRolesAndDatabaseAsAafCoreOwnerRole.sh PostgreSQL 14 localhost 5432 AafCoreOwner postgres 0wn3rCl13nt!`
   1. `./004-CreateDatabaseExtensionsAsAafCoreOwnerRole.sh PostgreSQL 14 localhost 5432 AafCoreOwner AafCore 0wn3rCl13nt!`
   1. `./005-CreateDatabaseSchemasAsAafCoreModeler.sh PostgreSQL 14 localhost 5432 AafCoreModeler AafCore M0d3l3rCl13nt!`
   1. `./006-CreateDatabaseTablesAsAafCoreModeler.sh PostgreSQL 14 localhost 5432 AafCoreModeler AafCore M0d3l3rCl13nt!`
   1. `./007-CreateDatabaseFunctionsAsAafCoreModeler.sh PostgreSQL 14 localhost 5432 AafCoreModeler AafCore M0d3l3rCl13nt!`
   1. `./008-CreateDatabaseDataAsAafCoreModeler.sh PostgreSQL 14 localhost 5432 AafCoreModeler AafCore M0d3l3rCl13nt!`

Please **note** that **this second AAF Data release is primarily for demonstration and evaluation purposes**.  It supports very simple local (LOC) and minimal (MIN) remote/cloud environments and **is not intended to be used in a production environment**.  **Later releases** will support shared, remote/cloud development (DEV), staging (STG), and production (PRD) environments and **will utilize secrets management and other security best practices**.  Crawl, walk, run.

2. **Run the Java/Springboot-based EntityDataMicroservice**:
   1. **Locally** in IDE:
      1. **Open** the `aafdata` project in **IntelliJ IDEA**.
      1. **Run** the `EntityDataMicroservice` class.
   1. **Locally** from the command line:
      1. Set the profile environment variable with `export spring_profiles_active=loc` (NOTE: Remove it with `unset spring_profiles_active`)
      1. **Change directory** (`cd`) to `aafdata/EntityDataMicroservice/`.
      1. **Build** the project:
         ```sh
         mvn clean package
         ```
      1. **Run** the project:
         ```sh
         java -jar target/EntityDataMicroservice-0.0.1-SNAPSHOT.jar
         ```
   1. **In Docker**:
      1. Set the profile environment variable with `export spring_profiles_active=min` (NOTE: Remove it with `unset spring_profiles_active`)
      1. **Build** a Docker image with `docker build -t deceptivelysimpletechnologies/aafdata-entitydatamicroservice:$(date +%Y%m%d_%H%M%S) .`
      1. -OR-
      1. **Pull** the Docker image from DockerHub:
         ```sh
         docker pull deceptivelysimpletechnologies/aafdata-entitydatamicroservice:20241106_143503
         ```
      1. **Run** the Docker container:
         ```sh
         docker run -d --name aafdata-edm-min -e spring_profiles_active=min -p 8080:8080 -t deceptivelysimpletechnologies/aafdata-entitydatamicroservice:latest
         ```

3. **Run the Java/Springboot-based SystemDataService**:
    1. **Locally** in IDE:
        1. **Open** the `aafdata` project in **IntelliJ IDEA**.
        1. **Run** the `SystemDataService` class.
    1. **Locally** from the command line:
        1. Set the profile environment variable with `export spring_profiles_active=loc` (NOTE: Remove it with `unset spring_profiles_active`)
        1. **Change directory** (`cd`) to `aafdata/SystemDataService/`.
        1. **Build** the project:
           ```sh
           mvn clean package
           ```
        1. **Run** the project:
           ```sh
           java -jar target/SystemDataService-0.0.1-SNAPSHOT.jar
           ```
    1. **In Docker**:
        1. Set the profile environment variable with `export spring_profiles_active=min` (NOTE: Remove it with `unset spring_profiles_active`)
        1. **Build** a Docker image with `docker build -t deceptivelysimpletechnologies/aafdata-systemdataservice:$(date +%Y%m%d_%H%M%S) .`
        1. -OR-
        1. **Pull** the Docker image from DockerHub:
           ```sh
           docker pull deceptivelysimpletechnologies/aafdata-systemdataservice:20250130_154029
           ```
        1. **Run** the Docker container:
           ```sh
           docker run -d --name aafdata-sds-min -e spring_profiles_active=min -p 8081:8081 -t deceptivelysimpletechnologies/aafdata-systemdataservice:latest
           ```

4. **Run the Progessive Web Application (PWA) EntityModelingService**:
    1. **In Docker**:
        1. **Change directory** (`cd`) to `aafdata/EntityModelingService/`.
        1. **Build** a Docker image with `docker build -t deceptivelysimpletechnologies/aafdata-entitymodelingservice:$(date +%Y%m%d_%H%M%S) .`
        1. **Run** the Docker container:
           ```sh
           docker run -d --name EntityModelingService -e spring_profiles_active=min -p 8082:80 -v ~/Documents/Project/dst/aafdata/EntityModelingService:/usr/share/nginx/html:ro deceptivelysimpletechnologies/aafdata-entitymodelingservice:20250203_114613
           ```
        1. **Open a web browser** and navigate to `http://localhost:8082/` to view the EntityModelingService.
<!--         1. Set the profile environment variable with `export spring_profiles_active=min` (NOTE: Remove it with `unset spring_profiles_active`) -->


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

A complete set of Postman requests for both EDM and SDS services is included with the source code at `aafdata/client/DST AAF Data.postman_collection.json`.

**Note**: Each AAF Data API request requires a JWT-encoded cookie, which must be added to the Postman client manually since it will not export with the request collection 
for security reasons.  The cookie must be name "Authentication" and is for the moment hard-coded with the following structure and content: 

```jwt
Authentication=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImxvYyJ9.eyJpc3MiOiJBQUZEYXRhLUNsaWVudCIsInN1YiI6IkF1dGhlbnRpY2F0aW9uIiwiYXVkIjoiQUFGRGF0YS1FbnRpdHlEYXRhTWljcm9zZXJ2aWNlIiwiZXhwIjoxNzIzODE2OTIwLCJpYXQiOjE3MjM4MTY4MDAsIm5iZiI6MTcyMzgxNjc4OSwianRpIjoiZWY0YWY0ZTMtZTczNi00MjVhLWFhZmYtZWNhMDNiN2I5YjI4IiwiYm9keSI6eyJFbWFpbEFkZHJlc3MiOiJhbXkuYW5kZXJzb25AYW15c2FjY291bnRpbmcuY29tIn19.a5UdLPgo4CzMyK1_JuWbQQQEMYz-rBcLu5uH0sZElqw; Path=/; Domain=localhost; HttpOnly; Expires=Mon, 18 Aug 2025 12:07:49 GMT;
```

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- PROJECT LOGO -->
<br />
<div align="center">
    <img src="https://deceptivelysimpletechnologies.com/wp-content/uploads/2020/08/pexels-daniel-kux-932320-1568x912.jpg" alt="Logo" width="128" height="64">
<!-- <a href="https://github.com/othneildrew/Best-README-Template">Explore the docs »</a> -->
<!-- <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a> -->
<!-- · -->
<!-- <a href="https://github.com/othneildrew/Best-README-Template/issues">Report Bug</a> -->
<!-- · -->
<!-- <a href="https://github.com/othneildrew/Best-README-Template/issues">Request Feature</a> -->
</div>


## Table of Contents
<!-- TABLE OF CONTENTS -->
<details>
  <summary>Expand for Details</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#history">History</a>
    </li>
    <li>
      <a href="#the-aaf-vision">The AAF Vision</a>
    </li>
    <li>
      <a href="#what-is-aaf-today">What Is AAF Today?</a>
      <ul>
        <li>
          <a href="#how-are-the-aaf-models-represented-in-the-data-layer">How Are the AAF Models Represented in the Data Layer?</a>
          <ul>
            <li><a href="#business-entity-microservice">Business Entity Microservice</a></li>
            <li><a href="#system-data-service">Data Structure Service</a></li> 
          </ul>
        </li>
      </ul>
      <ul>
        <li>
          <a href="#how-will-the-aaf-models-be-represented-in-the-service-layer">How Will the AAF Models Be Represented in the Service Layer?</a>
        </li>
      </ul>
      <ul>
        <li>
          <a href="#how-will-the-aaf-models-be-represented-in-the-presentation-layer">How Will the AAF Models Be Represented in the Presentation Layer?</a>
        </li>
      </ul>
    </li>
    <li>
      <a href="#version-history">Version History</a>
    </li>
    <li>
      <a href="#built-with">Built With</a>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About the Project
AAF Data is the crucial first layer -- business entity data modeling and persistence -- of the Adaptív Application Foundation (AAF).
<p align="right">(<a href="#top">back to top</a>)</p>


## History
In the 30 years or so that I've been designing, developing, and delivering software, I've seen anti-patterns (things that one should never do)
like object-relational mappers (ORMs) and data transfer objects (DTOs) become not only acceptable but standard operating procedure.

Code size, dependencies, and complexity have skyrocketed while fundamentals, performance, and maintainability have plummeted to the point where
many developers no longer know the basics of their craft or use the right tool for the job.

I've written about this in my [Look, Fuckers](https://medium.com/look-fuckers) publication on Medium, and I've led several teams
to implement my ideas -- the best practice ideas now "baked into" this AAF Data layer product -- in custom, commercial applications,
but this process is time-consuming and expensive.

So, I have worked slowly but steadily over the years, creating this free, Open Source implementation in order to to remove the "costs too much/takes too long"
risks and objections that have complicated custom implementations, and I am very excited to offer these assets and capabilities for your consideration and use.

The Adaptív Application Foundation (AAF) is the result of a number of related, reinforcing, and enabling ideas for software application technical design and implementation I've had and worked to realize and refine starting in 2003.  These ideas have come to illustrate and embody my [Metis](https://cygnustechnologyservices.com/metis-methodology/) approach to system design and architecture (my definition [here](https://frickingruvin.medium.com/defining-architecture-edcb334d5cbb)).

In 2006 I founded Deceptively Simple Technologies Inc to take up the further development and promotion of technology product ideas like the AAF.  Cygnus Techology Services is my services company, which focuses on consulting, customization, and commercialization of these ideas and products, guided by my own 25+ years of experience with technology leadership & strategy, people, product & process, and technology & tactics.

The AAF has gone through many successful incarnations over the years, enabling several organizations to quickly and efficiently create and maintain business technology solutions after investing the resources needed to implement the infrastructure that makes up the foundation.  I've learned and refined my ideas in each case, and in a few places I've been fortunate enough to collaborate with other professionals who truly believed in the potential of this approach and helped to improve it and to push it forward.

But most of the time I've encountered ineptitude and laziness and fear and resistance bordering on hostility, misunderstanding of the process and timeline, inability to stay the course

I myself was also fearful of making these ideas public (hey, I'm old).

Today, I believe that I have clarified and resolved most of the inconsistent ideas in the AAF, and it is light, fast, adaptable, scalable, and ready to rock.  In making it available under open source licensing, I intend to remove the "costs too much/takes too long" objections to custom implementations of the foundation as well as to "bake in" my process and attention to detail, which most developers don't take the time to understand or value.


## The AAF Vision
The AAF will be an integrated, standards-based collection of business entity definitions and relationships, data structures, and data, executable business process models, and business rule (or "decision") models that together provide basic out-of-the-box business functionality that can be utilized as is, modified, extended, etc.


The foundation recognizes and implements common business entity, process, and decision patterns, leading to efficiency, re-use, and happy stakeholders and end-users.  Business entities and their relationships represent the persons, places, and things that our information systems are intended to manage (i.e. the "nouns").  Business processes (i.e. the "verbs") orchestrate the interactions and operations involving these entities.  And business decisions (i.e. the "rules") inform and constrain these processes.
## What Is AAF Today?
In an effort to deliver significant value in the shortest possible timeframe, the AAF data layer (AAF Data) will be released first.

Today, AAF Data is:
- A standalone set of
    - Generalized **business entity models**,
    - **Database creation scripts** for roles, schemas, tables, functions, and
    - **Scripted lookup/reference data**,
- A Dockerized, **REST**ful **entity data microservice** (EDM) providing
    - **C**reate,
    - **R**ead,
    - **U**pdate, and
    - **D**elete (**CRUD**) operations,
- A Dockerized, **REST**ful **system data service** (SDS) providing
    - Create **N**ew business entity definition,
    - **C**lone Existing business entity definition,
    - Create New business entity **A**ttribute,
    - **A**ssociate business entity attribute with business entity definition(s),
    - **P**ublish entities, attributes, and associations, thereby creating corresponding new database schemas, tables, constraints, indexes, etc, and
    - **U**pdate the published status of entities, attributes, and associations,
- And **SwaggerDoc** **OpenAPI** application programming interface (API) **documentation**.


### How Are the AAF Models Represented in the Data Layer?


#### Business Entity Microservice
Scripted database, role, schema, table, and data

Business entity type definitions, entity attributes, type definition/attribute association

EntityType structure and data

EntitySubtype structure and data


#### System Data Service

TODO: Define LegalEntity, Environment, EnvironmentOwner, Domain, DomainOwner, Entity, EntityOwner, Database, DatabaseOwner, DatabaseSchemaOwner, DatabaseSchemaDataReadOnly, DatabaseSchemaDataReadWrite, DatabaseSchemaDataDelete

System data service logic:
- Does the given LegalEntity's local copy of the AAF Core database exist as its AAF Company database as expected in the given environment?
- Create the given LegalEntity's AAF Company database with its own UUID-based name in the given environment.
- Is the given LegalEntity's AAF Company database valid in the given environment?
- Is the given LegalEntity's AAF Company database up to date in the given environment?
- Do the given business entity's data structures (e.g. schema, tables, constraints, functions, etc) exist as expected in the given environment?
- Create the given business entity's data structures in the given environment.
- Are the given business entity's data structures valid in the given environment?
- Are the given business entity's data structures up to date in the given environment?
- Does the standard, scripted business entity data exist as expected in the given environment?
- Create the standard, scripted business entity data in the given environment.


### How Will the AAF Models Be Represented in the Service Layer?
Coming soon

Out-of-the-box, ready-to-run BPMN process and DMN decision definitions


### How Will the AAF Models Be Represented in the Presentation Layer?
Coming soon

Model-based web user interface (UI) components


## Version History

* 0.0
    * Various bug fixes and optimizations
    * Initial Release of EDM and basic business entities
    * See [commit change](https://github.com/DeceptivelySimpleTechnologies/aafdata/graphs/commit-activity) or See [release history]()
* 1.0
    * Initial Release of SDS and related EDM changes
    * Various bug fixes and optimizations
    * See [commit change](https://github.com/DeceptivelySimpleTechnologies/aafdata/graphs/commit-activity) or See [release history]()


## Built With

* [OpenJDK](https://openjdk.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Webflux](https://www.postgresql.org/)
* [PostgreSQL](https://www.postgresql.org/)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Postman](https://www.postman.com/)
* [FasterXML/jackson](https://github.com/FasterXML/jackson)
* [springdoc-openapi](https://springdoc.org/)
* [SLF4J](https://www.slf4j.org/)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- ROADMAP -->
## Roadmap

- [x] Set up JWT infrastructure with request body in token
- [ ] Add JWT helper functions (encode/decode)
- [x] Finish README.md
- [x] LOC environment setup (with profiles, etc)
- [ ] Dockerfile with AWS CloudFront logging driver, etc
- [x] Add EDM image to DockerHub
- [.] Unit testing
- [ ] Security testing (database table direct access, etc)
- [ ] Integration testingSet up Terraform Remote Backend With AWS Using A Bash Script
- [ ] Enhance Swagger/OpenAPI documentation with cached EntityType, etc data
- [ ] Implement a global error handler for all exceptions with JSON return
- [ ] Implement localization support
    - [x] U.S. English (system default)
    - [ ] Spanish

See the [open issues](https://github.com/DeceptivelySimpleTechnologies/aafdata/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please check the roadmap (above) for planned enhancements, etc, clone the repo, create a new feature branch, implement your idea there, and create a pull request.
Don't forget to give the project a star! Thanks again!

1. Check the Roadmap (above)
2. Clone the Project
3. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
4. Commit your Changes (`git commit -m 'Add the AmazingFeature'`)
5. Push to the Branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the GNU GPLv3. Please see the aafdata/LICENSE.txt file for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Doug Wilson, President & CEO, Deceptively Simple Technologies Inc
Project Link: [DeceptivelySimpleTechnologies/aafdata](https://github.com/DeceptivelySimpleTechnologies/aafdata)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Here are some of the resources I've found helpful during the development of this project and would like thank.

* [Choose an Open Source License](https://choosealicense.com)
* [Img Shields](https://shields.io)
* [GitHub Pages](https://pages.github.com)
* [Font Awesome](https://fontawesome.com)
* [Toptal gitignore.io](https://www.toptal.com/developers/gitignore)
* [jwt.io](https://jwt.io/)
* [Online UUID Generator](https://www.uuidgenerator.net/version4)
* [SHA-256 Hash Calculator](https://xorbin.com/tools/sha256-hash-calculator)
* [JSON Formatter](https://jsonformatter.org/e8391c)
* [Browserling All Hash Generator](https://www.browserling.com/tools/all-hashes)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/DeceptivelySimpleTechnologies/aafdata/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/DeceptivelySimpleTechnologies/aafdata/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/DeceptivelySimpleTechnologies/aafdata/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/DeceptivelySimpleTechnologies/aafdata/issues
[license-shield]: https://img.shields.io/badge/license-gpl-f39f37?style=for-the-badge
[license-url]: https://github.com/DeceptivelySimpleTechnologies/aafdata/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/company/deceptively-simple-technologies/
[product-screenshot]: /Postman_JSON_Response_Data.png


