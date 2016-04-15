#!/bin/sh

# 启动Task5，用于更新时光徽章数据

LOG_FILE=task5.log

# 切换目录，不然使用crontab定时任务无法找到相对路径文件
cd /home/lf/bnade

now=$(date +"%Y-%m-%d-%H.%M.%S")

# 保存log文件
if [ -f $LOG_FILE ]; then
  mv $LOG_FILE ./log/$LOG_FILE.$now
  echo 保存log文件到/log/$LOG_FILE.$now>>$LOG_FILE
fi

if [ -f t5stop ]; then
  rm t5stop
fi

nohup java -cp ./libs/*:./bnade.jar:./config  lf.bnade.task.Task5  >>$LOG_FILE 2>>$LOG_FILE &
