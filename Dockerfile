FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y \
    default-jdk \
    git \
    net-tools \
    && rm -rf /var/lib/apt/lists/*

RUN git clone https://github.com/Rooney-Eli/RMIServerTest.git /tmp/repo

RUN mkdir -p /app

RUN cp /tmp/repo/src/*.java /app/
# Debug output
WORKDIR /app

RUN javac -d . *.java

COPY start-rmi.sh /app/start-rmi.sh
RUN chmod +x /app/start-rmi.sh

EXPOSE 1099

CMD ["/app/start-rmi.sh"]