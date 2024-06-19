# Hermes Analyzer - Backend

## Description

This repository is part of a TFG project related to the evaluation of the capabilities of Large Language Models (LLM).
This tool allows users to follow a (semi-)automated flow that will be upgraded in the coming months, and offers a REST
API with an already developed [Frontend](https://github.com/atenearesearchgroup/llm-evaluator-frontend).

### Abstract

The advancement of Large Language Models (LLMs) and the potential of their applications have led to a growing interest
for its the application to Model Driven Software Engineering. In this field, LLMs have started to be used for the
automatic creation of software models given a natural language description of the domain to be modelled. This has led to
the identification of a research niche focused on the evaluation of the modelling capabilities of LLMs.

This project has two main contributions. Firstly, the capabilities of different LLMs in class diagram generation tasks
are studied and analysed. This first study is done in an exploratory way following the same process that was published
in the following research paper for ChatGPT: ``On the assessment of generative AI in modeling tasks: an experience
report with ChatGPT and UML'' by Javier Cámara, Javier Troya, Lola Burgueño, and Antonio Vallecillo, published in 2023
in the journal Software and Systems Modeling. These exhaustive tests try to observe, analyse and compare the
capabilities, strengths and weaknesses of some of the language models that are most popular today.

Secondly, and based on the results obtained in the previous phase, this TFG provides the definition of a systematic and
reproducible procedure for the future evaluation of the usefulness and applicability of language models.

To facilitate the application of this procedure, defined as a workflow, a web application has been developed that
provides an accessible, intuitive and user-friendly interface. This application is designed to guide users through the
procedure without the need to memorise each step, thus optimising the workflow.

## Usage

### Docker

#### Prerequisites

To be able to run the project you only need to have installed [Docker](https://www.docker.com).

#### Step 1: Build

A [Dockerfile](https://github.com/atenearesearchgroup/llm-evaluator-backend/blob/master/Dockerfile) is included in the
root dir, to generate the needed image you will need to use the command:

```
docker build -t hermesanalyzer/backend .
```

It will have generated a docker image with the tag `hermesanalyzer/backend`.

#### Step 2: Setup env variables and Run the container

Copy ``.env.example`` and rename it to ``.env.docker``, then modify the environment variables. An example of valid
environment file might be:

```
DATASOURCE_URL=jdbc:mysql://host.docker.internal:3307/hermesanalyzer
DATASOURCE_USERNAME=root
DATASOURCE_PASSWORD=verysecret
DATASOURCE_SCHEMA=hermesanalyzer
```

> **Consideration**: If you are going to use the dockerized version without MySQL added, you must be sure ``DATASOURCE_URL``
> environment variable is set to ``host.docker.internal`` if using a local database. If you use the Backend + MySQL
> compose you must set it to ``mysql`` as it is the service name.

#### Run the backend

Now, you can run it by using the command (Use -d to detach):

```
docker run -p 8080:8080 --env-file .env.docker -t hermesanalyzer/backend -it
```

#### Run the backend + MySQL database

Now, you can run it by using the command (Use -d to detach):

```
docker compose up
```

## Technology Stack

This project has been developed with **Java 17** and **Gradle** as the building tool, by using the **[Spring](https://spring.io)** Framework.

## Authors

> See the list of [contributors](https://github.com/atenearesearchgroup/llm-evaluator-backend/graphs/contributors) who
> participated in this project.

## License

This project is licensed under the GPL-3.0 License - see the [LICENSE](./LICENSE) file for details
