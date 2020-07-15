#!/bin/bash
# ws-util

PID=`ps -ef|grep 'Dws-util '|grep -v grep|awk '{print $2}'`
if [ -z $PID ];then
    echo "The program ws-util has been stoped."
else
    kill $PID
    echo "The program ws-util is being killed, please wait for 10 seconds..."
    sleep 10

    PID=`ps -ef|grep 'Dws-util '|grep -v grep|awk '{print $2}'`
    if [ $PID ];then
        kill -9 $PID
    fi
    echo "Kill the program ws-util successfully."
fi