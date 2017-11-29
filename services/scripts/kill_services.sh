#!/bin/bash
docker stop redis-slave redis-master #redis
docker stop broker1 broker2 broker3 #activeMq
docker stop hadoop-namenode #hadoop-datanode
docker stop cassandra-slave cassandra-seed #cassandra

