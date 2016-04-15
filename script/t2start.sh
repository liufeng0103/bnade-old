#!/bin/sh

# 启动Task2，用于统计和清理auction history数据

LOG_FILE=task2.log

# 切换目录，不然使用crontab定时任务无法找到相对路径文件
cd /home/lf/bnade

now=$(date +"%Y-%m-%d-%H.%M.%S")

# 保存log文件
if [ -f $LOG_FILE ]; then
  mv $LOG_FILE ./log/$LOG_FILE.$now
  echo 保存log文件到/log/$LOG_FILE.$now>>$LOG_FILE
fi

if [ -f t2stop ]; then
  rm t2stop
fi

echo 开始运行task2>>$LOG_FILE
nohup java -cp ./libs/*:./bnade.jar:./config  lf.bnade.task.Task2Runner  >>$LOG_FILE 2>>$LOG_FILE &
