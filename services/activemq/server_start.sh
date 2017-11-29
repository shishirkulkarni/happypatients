#!/bin/bash
#Set zookeeper id
if [ -z "$MYID" ]
then
	echo "Zookeeper id not set. Exiting!!!"
	exit 1
else
	touch /opt/zookeeper-3.4.10/data/myid
	echo $MYID > /opt/zookeeper-3.4.10/data/myid
fi

#set hostname in activemq file
sed -i s/hostname=\"\"/hostname=\"$HOSTNAME\"/g /opt/apache-activemq-5.15.2/conf/activemq.xml

/opt/zookeeper-3.4.10/bin/zkServer.sh start
/opt/apache-activemq-5.15.2/bin/activemq start

bash