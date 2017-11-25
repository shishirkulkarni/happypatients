#!/bin/sh
docker rm zookeeper
docker rm cassandra-slave cassandra-seed
docker rm redis-slave redis-master
