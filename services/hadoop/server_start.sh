#!/bin/bash

chmod 0400 ~/.ssh
service ssh start

#config JAVA_HOME in hadoop env
echo "export JAVA_HOME=$JAVA_HOME" >> $HADOOP_CONF_DIR/hadoop-env.sh

if [ "$?" -ne "0" ]
then
	echo "Error starting ssh daemon. Exiting"
	exit 1
fi

echo "export JAVA_HOME=$JAVA_HOME" >> $HADOOP_CONF_DIR/hadoop-env.sh

if [ "hadoop-namenode" = `hostname` ]
then
	hadoop namenode -format
	$HADOOP_PREFIX/sbin/start-all.sh
	jps
fi
#trick to prevent container from exiting
tail