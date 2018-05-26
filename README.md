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
sudo apt install nodejs
sudo apt install npm
```

Install angular CLI
```
sudo npm install -g @angular/cli
```


#### Setting the wildfly domain mode

`cd $JBOSS_HOME/domain/configuration`

* Open host.xml and configure setting for servers. For example:
```
<servers>
         <server name="server1" group="serverGroup1">
         </server>
         <server name="server2" group="serverGroup2" auto-start="true">
             <jvm name="default"/>
             <socket-bindings port-offset="100"/>
         </server>
         <server name="server3" group="other-server-group" auto-start="false">
             <jvm name="default"/>
             <socket-bindings port-offset="250"/>
         </server>
     </servers>
```
* Open domain.xml and configure setting for server-groups. For example:
```
<server-groups>
        <server-group name="main-server-group" profile="full">
            <jvm name="default">
                <heap size="64m" max-size="512m"/>
            </jvm>
            <socket-binding-group ref="full-sockets"/>
            <deployments>
                <deployment name="TACOS-rest.war" runtime-name="TACOS-rest.war"/>
            </deployments>
        </server-group>
        <server-group name="other-server-group" profile="full-ha">
            <jvm name="default">
                <heap size="64m" max-size="512m"/>
            </jvm>
            <socket-binding-group ref="full-ha-sockets"/>
            <deployments>
                <deployment name="TACOS-rest.war" runtime-name="TACOS-rest.war"/>
            </deployments>
        </server-group>
        <server-group name="serverGroup1" profile="full">
            <jvm name="default">
                <heap size="64m" max-size="512m"/>
            </jvm>
            <socket-binding-group ref="full-sockets"/>
            <deployments>
                <deployment name="TACOS-rest.war" runtime-name="TACOS-rest.war"/>
            </deployments>
        </server-group>
        <server-group name="serverGroup2" profile="full">
            <jvm name="default">
                <heap size="64m" max-size="512m"/>
            </jvm>
            <socket-binding-group ref="full-sockets"/>
            <deployments>
                <deployment name="TACOS-rest.war" runtime-name="TACOS-rest.war"/>
            </deployments>
        </server-group>
    </server-groups>

```

#### First run - Ubuntu or other system with gnome

`git clone https://github.com/LizzardCorp/pv243project.git`

`cd pv243project/TACOS-rest/src/main/frontend`

`npm install`

`cd ../../../..`

`./run.sh`

#### First run - Other system based on Linux

`git clone https://github.com/LizzardCorp/pv243project.git`

`cd pv243project/TACOS-rest/src/main/frontend`

`npm install`

`cd $JBOSS_HOME/bin`

`./domain.sh`

`./jboss-cli.sh --file=[path to the root of pv243project]/TACOS-rest/deploy.cli`