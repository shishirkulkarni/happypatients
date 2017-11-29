#!/bin/sh
docker rm cassandra-slave cassandra-seed #cassandra
docker rm redis-slave redis-master #redis
docker rm broker1 broker2 broker3 #activemq
docker rm mongo #mongo
docker rm hadoop-namenode hadoop-datanode #hadoop
