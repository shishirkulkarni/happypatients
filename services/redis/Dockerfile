FROM ubuntu
RUN apt-get update
RUN apt-get install -y redis-server iputils-ping net-tools
COPY ./redis.conf /etc/redis/redis.conf
COPY ./slot_allocator.sh /tmp/slot_allocator.sh
RUN chmod +x /tmp/slot_allocator.sh
EXPOSE 6379
EXPOSE 16379
ENTRYPOINT ["/usr/bin/redis-server", "/etc/redis/redis.conf"]
