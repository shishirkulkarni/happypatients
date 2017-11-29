#!/bin/bash
docker start redis-slave redis-master #redis
docker start broker1 broker2 broker3 #activeMq
docker start hadoop-namenode hadoop-datanode #hadoop-datanode
docker start cassandra-slave cassandra-seed #cassandra
docker start mongo #mongo

