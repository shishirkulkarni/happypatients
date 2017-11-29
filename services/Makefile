

all: build-services start-services

build-activemq:
	scripts/build_activemq.sh

build-redis:
	scripts/build_redis.sh

build-cassandra:
	scripts/build_cassandra.sh

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

start-hadoop:
	scripts/start_hadoop.sh

start-activemq:
	scripts/start_activemq.sh

kill-redis:
	scripts/kill_redis.sh

kill-cassandra:
	scripts/kill_cassandra.sh

kill-hadoop:
	scripts/kill_hadoop.sh

kill-mongo:
	scripts/kill_mongo.sh

kill-activemq:
	scripts/kill_activemq.sh

start-services: start-redis start-cassandra start-hadoop start-mongo start-activemq

build-services: build-redis build-cassandra buil build-hadoop build-mongo build-activemq

kill-services: kill-redis kill-cassandra kill-hadoop kill-mongo kill-activemq

restart-services:
	scripts/restart_services.sh

clean:
	scripts/cleanup.sh
