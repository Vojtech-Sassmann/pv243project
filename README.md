# PV243 team project - TACOS
The amazing creative ordering system

[![Build Status](https://travis-ci.org/LizzardCorp/pv243project.svg?branch=master)](https://travis-ci.org/LizzardCorp/pv243project)

A Java school team project.

Team members: 
* Vojtěch Sassmann
* Peter Balčirák
* Pavel Vyskočil

Wiki: https://github.com/LizzardCorp/pv243project/wiki


# Running the project

#### Prerequisites
- JDK 8
- Wildfly 11 and later
- Setted $JBOSS_HOME
- angular cli

#### Install Angular CLI

Install nodejs and npm
```
curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash -
sudo apt-get install -y nodejs
```

Install angular CLI
```
sudo npm install -g @angular/cli
```

#### Running server

Clone the project
`git clone https://github.com/LizzardCorp/pv243project.git`

Build the project
`mvn clean install -DskipTests`

Go to the app directory
`cd TACOS-rest`

Run wildfly in domain mode 
`$JBOSS_HOME/bin/domain.sh`

After startup run configuration script
`$JBOSS_HOME/bin/jboss-cli.sh --file=configure.cli`

Deploy application
`$JBOSS_HOME/bin/jboss-cli.sh --file=deploy.cli`

#### Running frontend

Cd to frontend folder
`cd src/main/frontend`

Install dependencies
`npm install`

Run the angular project
`ng serve`

Then navigate to url 
`localhost:4200/marketplace`
