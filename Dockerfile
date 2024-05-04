FROM ubuntu:20.04

RUN apt-get update && \
    apt-get install -y \
    default-jdk \
    git \
    net-tools \
    vim \
    && rm -rf /var/lib/apt/lists/*

# uncomment this and remove the volume command from docker run when ready
 RUN git clone https://github.com/Rooney-Eli/RMIServerTest.git /tmp/repo

RUN mkdir -p /app/src

 RUN cp /tmp/repo/src/*.java /app/src/

WORKDIR /app

COPY  /tmp/repo/start-server.sh /app/start-server.sh
RUN chmod +x /app/start-server.sh

EXPOSE 1099

CMD ["/app/start-server.sh"]