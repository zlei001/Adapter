FROM 172.168.27.239/devops/openjdk:8

RUN mkdir -p /home/znwg/service-zhwg-ywkt-mtosi/logs

COPY target/service-zhwg-ywkt-mtosi-0.0.1-SNAPSHOT.jar /home/znwg/service-zhwg-ywkt-mtosi

WORKDIR /home/znwg/service-zhwg-ywkt-mtosi
VOLUME /home/znwg/service-zhwg-ywkt-mtosi/logs

ENTRYPOINT ["java","-Xms256m","-Xmx1024m","-jar","service-zhwg-ywkt-mtosi-0.0.1-SNAPSHOT.jar"]
EXPOSE 9555