#!/bin/bash

curl -k http://192.168.43.40:8090/config -o %BASE_PATH%/telegraf/usr/bin/telegraf.conf

cd %BASE_PATH%/telegraf/usr/bin/
if [ -e tg_pid.txt ] ; then
    kill $(cat tg_pid.txt)
fi

sleep 3

nohup ./telegraf --config telegraf.conf > nphup.out 2>&1 &
echo $! > tg_pid.txt

