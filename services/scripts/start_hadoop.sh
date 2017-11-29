HADOOP_NETWORK=hadoop-network

docker network rm $HADOOP_NETWORK
docker network create $HADOOP_NETWORK

docker run -itd \
	--net=$HADOOP_NETWORK \
	--net-alias=hadoop-datanode \
	-h hadoop-datanode \
	--name hadoop-datanode \
	hadoop

sleep 2

docker run -itd \
	--net=$HADOOP_NETWORK \
	--net-alias=hadoop-namenode \
	--name hadoop-namenode \
	-h hadoop-namenode \
	-p 9000:9000 \
	hadoop
