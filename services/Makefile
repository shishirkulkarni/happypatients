

all: build-services start-services

build-redis:
	scripts/build_redis.sh

build-cassandra:
	scripts/build_cassandra.sh

build-zookeeper:
	scripts/build_zookeeper.sh

build-hadoop:
	scripts/build_hadoop.sh

build-mongo:
	scripts/build_mongo.sh

start-redis:
	scripts/start_redis.sh

start-mongo:
	scripts/start_mongo.sh

start-cassandra:
	scripts/start_cassandra.sh

start-kafka:
	scripts/start_kafka.sh

start-hadoop:
	scripts/start_hadoop.sh

start-activemq:
	scripts/start_activemq.sh

kill-redis:
	scripts/kill_redis.sh

kill-cassandra:
	scripts/kill_cassandra.sh

kill-kafka:
	scripts/kill_kafka.sh

kill-zookeeper:
	scripts/kill_zookeeper.sh

kill-hadoop:
	scripts/kill_hadoop.sh

kill-mongo:
	scripts/kill_mongo.sh

start-services: start-redis start-cassandra start-hadoop start-mongo start-activemq

build-services: build-redis build-cassandra build-zookeeper build-hadoop build-mongo

kill-services: kill-redis kill-cassandra kill-kafka kill-zookeeper kill-hadoop kill-mongo kill-activemq

clean:
	scripts/cleanup.sh
