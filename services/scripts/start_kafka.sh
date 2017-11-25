ACTIVEMQ_NETWORK=activemq-network

docker network rm $ACTIVEMQ_NETWORK
 

for i in {1..3}
do
	docker run -itd --name broker$i -h broker$i 
done
