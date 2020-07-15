#!/bin/bash
# ws-util

export lib0=./lib

PID=`ps -ef|grep 'Dws-util '|grep -v grep|awk '{print $2}'`
if [ -z $PID ];then
    java -Dws-util -Xms256m -Xmx1024m   -cp $lib0/dom4j-1.6.1.jar:$lib0/junit-4.8.2.jar:$lib0/commons-dbutils-1.5.jar:$lib0/commons-logging-1.1.3.jar:$lib0/slf4j-api-1.7.5.jar:$lib0/xmlbeans-2.5.0.jar:$lib0/commons-compress-1.8.1.jar:$lib0/proxy-vole-20131209.jar:$lib0/httpclient-4.3.5.jar:$lib0/commons-io-2.4.jar:$lib0/commons-beanutils-1.8.3.jar:$lib0/commons-lang3-3.3.2.jar:$lib0/catt-pub-db.jar:$lib0/bcprov-jdk15-144.jar:$lib0/rsyntaxtextarea-2.5.0.jar:$lib0/logback-access-1.0.13.jar:$lib0/logback-core-1.0.13.jar:$lib0/logback-classic-1.0.13.jar:$lib0/commons-cli-1.2.jar:$lib0/catt-pub-conf.jar:$lib0/binding-2.0.1.jar:$lib0/stax-api-1.0.1.jar:$lib0/ws-util.jar:$lib0/proxool-cglib-0.9.1.jar:$lib0/soapui-5.1.2.jar:$lib0/proxool-0.9.1.jar:$lib0/wsdl4j-1.6.3.jar:$lib0/catt-utils.jar:$lib0/log4j-1.2.17.jar:$lib0/httpcore-4.3.2.jar:$lib0/commons-codec-1.6.jar: com.catt.zhwg.ws.parser.util.Main  >/dev/null 2>err.log &
else
    echo "The program ws-util has been running.Please stop it firstly."
fi
