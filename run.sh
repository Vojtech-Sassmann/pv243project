#bin/bash
CWD="$(pwd)"

mvn clean install -DskipTests
cp TACOS-rest/target/TACOS-rest.war $JBOSS_HOME/

cd TACOS-rest/src/main/frontend
gnome-terminal -e "ng serve"

gnome-terminal -e "$JBOSS_HOME/bin/domain.sh"
sleep 15
cd $JBOSS_HOME/
bin/jboss-cli.sh --file=$CWD/TACOS-rest/configure-jms.cli
bin/jboss-cli.sh --file=$CWD/TACOS-rest/configure-mail-session.cli
bin/jboss-cli.sh --file=$CWD/TACOS-rest/deploy.cli
echo "TACOS app was deployed"
