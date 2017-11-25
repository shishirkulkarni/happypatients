from openjdk:8

#install and configure ssh first:
RUN apt-get update && apt-get install -y openssh-server
RUN mkdir /var/run/sshd
RUN echo 'root:screencast' | chpasswd
RUN sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# SSH login fix. Otherwise user is kicked off after login
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

ENV NOTVISIBLE "in users profile"
RUN echo "export VISIBLE=now" >> /etc/profile

#Copy ssh configs
COPY ./hadoop_rsa /root/.ssh/id_rsa
COPY ./hadoop_rsa.pub /root/.ssh/id_rsa.pub
COPY ./authorized_keys /root/.ssh/authorized_keys
COPY ./ssh_config /root/.ssh/config
RUN chmod 400 /root/.ssh/*

EXPOSE 9000 8025 8030 8040 22
RUN cd /opt && \
	wget http://mirrors.advancedhosters.com/apache/hadoop/common/hadoop-2.6.5/hadoop-2.6.5.tar.gz

RUN cd /opt && tar -xvzf hadoop-2.6.5.tar.gz &&\
	mv hadoop-2.6.5 hadoop

#Hadoop specific environment vars
ENV HADOOP_PREFIX=/opt/hadoop
ENV PATH=$HADOOP_PREFIX/bin:$PATH
ENV HADOOP_COMMON_HOME=$HADOOP_PREFIX
ENV HADOOP_HDFS_HOME=$HADOOP_PREFIX
ENV HADOOP_YARN_HOME=$HADOOP_PREFIX
ENV HADOOP_CONF_DIR=$HADOOP_PREFIX/etc/hadoop
ENV YARN_CONF_DIR=$HADOOP_CONF_DIR

# config files for hadoop
COPY core-site.xml $HADOOP_CONF_DIR/core-site.xml 
COPY hdfs-site.xml $HADOOP_CONF_DIR/hdfs-site.xml
COPY mapred-site.xml $HADOOP_CONF_DIR/mapred-site.xml
COPY yarn-site.xml $HADOOP_CONF_DIR/yarn-site.xml
COPY slaves $HADOOP_CONF_DIR/slaves

#startup script
COPY ./server_start.sh /usr/bin/server_start.sh
RUN chmod +x /usr/bin/server_start.sh
CMD ["server_start.sh"]
