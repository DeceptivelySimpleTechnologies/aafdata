# Deceptively Simple Technologies Inc's Adaptív Application Foundation (AAF) Data Layer (AAF Data)


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
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


## TL;DR
_Today, the Adaptív Application Foundation (AAF)  data layer (AAF Data) is **a standalone set of generalized business entity models, database scripts, and scripted lookup/reference data**._


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Best-README-Template</h3>

  <p align="center">
    An awesome README template to jumpstart your projects!
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Report Bug</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Request Feature</a>
  </p>
</div>


## Table of Contents
<!-- TABLE OF CONTENTS -->
<details>
  <summary>Expand for Details</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

There are many great README templates available on GitHub; however, I didn't find one that really suited my needs so I created this enhanced one. I want to create a README template so amazing that it'll be the last one you ever need -- I think this is it.

Here's why:
* Your time should be focused on creating something amazing. A project that solves a problem and helps others
* You shouldn't be doing the same tasks over and over like creating a README from scratch
* You should implement DRY principles to the rest of your life :smile:

Of course, no one template will serve all projects since your needs may be different. So I'll be adding more in the near future. You may also suggest changes by forking this repo and creating a pull request or opening an issue. Thanks to all the people have contributed to expanding this template!

Use the `BLANK_README.md` to get started.

<p align="right">(<a href="#top">back to top</a>)</p>


## History
The Adaptív Application Foundation (AAF) is the result of a number of related, reinforcing, and enabling ideas for software application technical design and implementation I've had and worked to realize and refine starting in 2003.  These ideas have come to illustrate and embody my Metis approach to system design and architecture (my definition [here](https://frickingruvin.medium.com/defining-architecture-edcb334d5cbb)).

In 2006 I founded Deceptively Simple Technologies Inc to take up the further development and promotion of technology product ideas like the AAF.  Cygnus Techology Services is my services company, which focuses on consulting, customization, and commercialization of these ideas and products, guided by my own 25+ years of experience with technology leadership & strategy, people, product & process, and technology & tactics.

The AAF has gone through many successful incarnations over the years, enabling several organizations to quickly and efficiently create and maintain business technology solutions after investing the resources needed to implement the infrastructure that makes up the foundation.  I've learned and refined my ideas in each case, and in a few places I've been fortunate enough to collaborate with other professionals who truly believed in the potential of this approach and helped to improve it and to push it forward.

But most of the time I've encountered ineptitude and laziness and fear and resistance bordering on hostility, misunderstanding of the process and timeline, inability to stay the course

I myself was also fearful of making these ideas public (Hey, I'm old)

Today, I have clarified and resolved most of the inconsistent ideas in the AAF, and it is light, fast, adaptable, scalable, and ready to rock.  In making it available under open source licensing, I intend to remove the "costs too much/takes too long" objections to custom implementations of the foundation as well as to "bake in" my process and attention to detail, which most developers don't take the time to understand or value.


## The AAF Vision
The AAF will be an integrated, standards-based collection of business entity definitions, data structures, and data, executable business process models, and business rule (or "decision") models that together provide basic out-of-the-box business functionality that can be utilized as is, modified, extended, etc.


The foundation recognizes and implements common business entity, process, and decision patterns, leading to efficiency, re-use, and happy stakeholders and end-users.  Business entities and their relationships represent the persons, places, and things that our information systems are intended to manage (i.e. the "nouns").  Business processes (i.e. the "verbs") orchstrate the interactions and operations involving these entities.  And business decisions (i.e. the "rules") inform and constrain these processes.
## What Is AAF Today?
In an effort to deliver significant value in the shortest possible timeframe, the AAF data layer (AAF Data) will be released first.

Today, AAF Data is a standalone set of generalized business entity models, database scripts, and scripted lookup/reference data.  Soon it will also include a business entity modeler for extending or modifying these models and their scripted data, a structure service for conforming the entity data structures to the model, a RESTful business entity microservice supporting create, read, update, and delete (CRUD) operations, CRUD event publishing, and SwaggerDoc OpenAPI application programming interface (API) documentation.


### Data Layer


#### Business Entity Models & Data
Scripted database, role, schema, table, and data

Business entity type definitions, entity attributes, type definition/attribute association

EntityType structure and data

EntitySubtype structure and data


#### Data Structure Service

TODO: Define LegalEntity, Environment, EnvironmentOwner, Domain, DomainOwner, Entity, EntityOwner, Database, DatabaseOwner, DatabaseSchemaOwner, DatabaseSchemaDataReadOnly, DatabaseSchemaDataReadWrite, DatabaseSchemaDataDelete

Structure service logic:
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


### Service Layer
Coming soon

Out-of-the-box, ready-to-run BPMN process and DMN decision definitions


### Presentation Layer
Coming soon

Model-based web user interface (UI) components


### Version History

* 0.2
    * Various bug fixes and optimizations
    * See [commit change]() or See [release history]()
* 0.1
    * Initial Release


### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* [Next.js](https://nextjs.org/)
* [React.js](https://reactjs.org/)
* [Vue.js](https://vuejs.org/)
* [Angular](https://angular.io/)
* [Svelte](https://svelte.dev/)
* [Laravel](https://laravel.com)
* [Bootstrap](https://getbootstrap.com)
* [JQuery](https://jquery.com)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.


### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/your_username_/Project-Name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Add Changelog
- [x] Add back to top links
- [ ] Add Additional Templates w/ Examples
- [ ] Add "components" document to easily copy & paste sections of the readme
- [ ] Multi-language Support
    - [ ] Chinese
    - [ ] Spanish

See the [open issues](https://github.com/othneildrew/Best-README-Template/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the GNU GPLv3. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Your Name - [@your_twitter](https://twitter.com/your_username) - email@example.com

Project Link: [https://github.com/your_username/repo_name](https://github.com/your_username/repo_name)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Use this space to list resources you find helpful and would like to give credit to. I've included a few of my favorites to kick things off!

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
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/DeceptivelySimpleTechnologies/aafdata/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/company/deceptively-simple-technologies/
[product-screenshot]: images/screenshot.png


