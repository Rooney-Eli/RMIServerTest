FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y \
    default-jdk \
    git \
    net-tools \
    && rm -rf /var/lib/apt/lists/*

RUN git clone https://github.com/Rooney-Eli/RMIServerTest.git /tmp/repo

RUN mkdir -p /app/src

RUN cp /tmp/repo/src/*.java /app/src/

WORKDIR /app

COPY  /tmp/repo/start-rmi.sh /app/start-rmi.sh
RUN chmod +x /app/start-server.sh

EXPOSE 1099

CMD ["/app/start-server.sh"]