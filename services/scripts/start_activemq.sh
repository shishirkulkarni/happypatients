#!/bin/bash

ACTIVEMQ_NETWORK=activemq-network

docker network rm $ACTIVEMQ_NETWORK
docker network create $ACTIVEMQ_NETWORK


for i in {3..1}
do
	docker run -itd \
		--name broker$i \
		-h broker$i \
		--net=$ACTIVEMQ_NETWORK \
		--net-alias=broker$i \
		-e MYID=$i \
		activemq

done
