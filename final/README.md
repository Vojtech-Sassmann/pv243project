# PV243 team project - TACOS
The amazing creative ordering system

A JavaEE school team project.

Team members: 
* Vojtěch Sassmann
* Peter Balčirák
* Pavel Vyskočil

Wiki: https://github.com/LizzardCorp/pv243project/wiki


# Running the project

Run wildfly in domain mode 
`$JBOSS_HOME/bin/domain.sh`

After startup run configuration script
`$JBOSS_HOME/bin/jboss-cli.sh --file=configure.cli`
If you see message like this `Failed to establish connection in 6022ms` just ignore it.

Deploy application
`$JBOSS_HOME/bin/jboss-cli.sh --file=deploy.cli`

Then navigate to url 
`localhost:8080/TACOS-rest/`

You can sing up or use one of pre-created accounts: 

submitter: 

```
username: submitter@email.com
password: password
```

admin: 

```
username: admin@email.com
password: password
```

practitioner:

```
username: practitioner@email.com
password: password
```


