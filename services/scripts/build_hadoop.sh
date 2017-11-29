HADOOP_KEY_NAME="hadoop_rsa"
HADOOP_NAMENODE_HOSTNAME="hadoop-namenode"

if [ -f "hadoop/$HADOOP_KEY_NAME" ]
then
	rm hadoop/$HADOOP_KEY_NAME
fi

if [ -f "hadoop/$HADOOP_KEY_NAME.pub" ]
then
	rm hadoop/$HADOOP_KEY_NAME.pub
fi

if [ -f "hadoop/authorized_keys" ]
then
	rm hadoop/authorized_keys
fi

echo "Generating hadoop ssh keys..."
ssh-keygen -N "" -t rsa -b 4096 -C "hadoop_rsa" -f ./hadoop/$HADOOP_KEY_NAME
cat hadoop/hadoop_rsa.pub >> hadoop/authorized_keys

docker-compose build --no-cache hadoop

#cleanup
rm hadoop/$HADOOP_KEY_NAME hadoop/$HADOOP_KEY_NAME.pub hadoop/authorized_keys

