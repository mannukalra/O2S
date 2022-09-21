#!/bin/bash

curl -k http://192.168.1.36:8090/config -o telegraf/usr/bin/telegraf.conf

cd telegraf/usr/bin/
if [ -e tg_pid.txt ] ; then
    kill $(cat tg_pid.txt)
fi

sleep 5

nohup ./telegraf --config telegraf.conf > telegraf.log 2>&1 &
echo $! > tg_pid.txt

