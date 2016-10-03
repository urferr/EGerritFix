#!/bin/bash

#Generate ssh key that will be used by the admin user
ssh-keygen -f $GERRIT_HOME/.ssh/id_rsa -t rsa -N ''

#Initialize the server and keep it running for the time being
java -jar $GERRIT_WAR init --batch --dev -d $GERRIT_HOME/gerrit

echo "Generating project"
ssh -oStrictHostKeyChecking=no -p 29418 -l admin localhost gerrit create-project 'egerrit/test'  --empty-commit

echo "Generating project"
ssh -oStrictHostKeyChecking=no -p 29418 -l admin localhost gerrit create-project 'egerrit/RCPTTtest'  --empty-commit

#Need to flush the caches, otherwise the creation of the first account is failing
ssh -oStrictHostKeyChecking=no -p 29418 -l admin localhost gerrit flush-caches

echo "Creating first user"
ssh -oStrictHostKeyChecking=no -p 29418 -l admin localhost gerrit create-account --full-name test1 --email test1@localhost.com --http-password test1 test1

echo "Creating second user"
ssh -oStrictHostKeyChecking=no -p 29418 -l admin localhost gerrit create-account --full-name test2 --email test2@localhost.com --http-password test2 test2

/home/gerrit2/gerrit/bin/gerrit.sh stop

