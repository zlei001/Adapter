FROM 10.15.2.154/project1/openjdk:8
MAINTAINER service-security
#设置时区（如果不设置，容器内的时间会比实际时间慢8个小时）
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezon 

RUN mkdir -p /home/znwg/service-zhwg-topo/logs

COPY target/service-zhwg-topo-0.0.1-SNAPSHOT.jar /home/znwg/service-zhwg-topo

WORKDIR /home/znwg/service-zhwg-topo
VOLUME /home/znwg/service-zhwg-topo/logs

ENTRYPOINT ["java","-Xms512m","-Xmx2048m","-jar","service-zhwg-topo-0.0.1-SNAPSHOT.jar"]
EXPOSE 8758

