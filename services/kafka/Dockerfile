from openjdk:8
RUN cd /opt && wget http://www.namesdir.com/mirrors/apache/kafka/1.0.0/kafka_2.11-1.0.0.tgz -O kafka.tgz && tar -xvzf kafka.tgz
ADD server.properties /opt/kafka_2.11-1.0.0/config/server.properties
EXPOSE 9092
CMD ["/opt/kafka_2.11-1.0.0/bin/kafka-server-start.sh", "/opt/kafka_2.11-1.0.0/config/server.properties"]
