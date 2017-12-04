#Cassandra cluster setup
echo "Booting up cassandra cluster..."

CASSANDRA_PORT=9042
CLUSTER_NAME='test_cluster'
CASSANDRA_SEED_PATH=$(echo $PWD/cassandra/cassandra-seed)
CASSANDRA_SLAVE_PATH=$(echo $PWD/cassandra/cassandra-slave)

echo "booting up seed node..."

echo $CASSANDRA_SEED_PATH

docker run -itd \
	--name cassandra-seed \
	-p $CASSANDRA_PORT:$CASSANDRA_PORT \
	-e CASSANDRA_CLUSTER_NAME=$CLUSTER_NAME \
	-v $CASSANDRA_SEED_PATH:/var/lib/cassandra \
	cassandra


SEED_IP=`docker inspect -f {{.NetworkSettings.IPAddress}} cassandra-seed`

sleep 5

echo "Booting up slave node..."

docker run -itd \
	--name cassandra-slave \
	-e CASSANDRA_CLUSTER_NAME=$CLUSTER_NAME \
	-e CASSANDRA_SEEDS=$SEED_IP \
	-v $CASSANDRA_SLAVE_PATH:/var/lib/cassandra \
	cassandra
