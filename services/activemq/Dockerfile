from openjdk:8
ARG id
RUN cd /opt && \
	wget http://mirrors.ocf.berkeley.edu/apache/zookeeper/stable/zookeeper-3.4.10.tar.gz && \
	tar -xvzf zookeeper-3.4.10.tar.gz && \
	rm zookeeper-3.4.10.tar.gz
RUN cd /opt && \
	wget http://mirror.metrocast.net/apache//activemq/5.15.2/apache-activemq-5.15.2-bin.tar.gz && \
	tar -xvzf apache-activemq-5.15.2-bin.tar.gz && \
	rm apache-activemq-5.15.2-bin.tar.gz
RUN mkdir /opt/zookeeper-3.4.10/data
COPY ./zoo.cfg /opt/zookeeper-3.4.10/conf/zoo.cfg
COPY ./activemq.xml /opt/apache-activemq-5.15.2/conf/activemq.xml
COPY ./server_start.sh /usr/bin
RUN chmod +x /usr/bin/server_start.sh
EXPOSE 2181 2888 3888 61616 44797
CMD ["server_start.sh"]
