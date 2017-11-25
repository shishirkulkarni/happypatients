#Redis cluster setup
REDIS_PORT=6379
docker run -itd --name redis-master -p 6379:6379 redis
docker run -itd --name redis-slave redis
sleep 2;
REDIS_MASTER=`docker inspect -f {{.NetworkSettings.IPAddress}} redis-master`
REDIS_SLAVE=`docker inspect -f {{.NetworkSettings.IPAddress}} redis-slave`
echo "Establishing Clusters..."
docker exec -it redis-master redis-cli -h $REDIS_MASTER CLUSTER MEET $REDIS_MASTER $REDIS_PORT
docker exec -it redis-master redis-cli -h $REDIS_MASTER CLUSTER MEET $REDIS_SLAVE $REDIS_PORT
docker exec -it redis-slave redis-cli -h $REDIS_MASTER CLUSTER MEET $REDIS_MASTER $REDIS_PORT
docker exec -it redis-slave redis-cli -h $REDIS_MASTER CLUSTER MEET $REDIS_SLAVE $REDIS_PORT
MASTER_ID=`docker exec -it redis-slave redis-cli CLUSTER NODES | grep $REDIS_MASTER | awk '{print $1}'`
docker exec -it redis-slave redis-cli CLUSTER REPLICATE $MASTER_ID
echo "Adding slots to redis master. This may take some time..."
docker exec -it redis-master "/tmp/slot_allocator.sh" > /dev/null

