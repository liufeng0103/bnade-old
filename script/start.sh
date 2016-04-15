#!/bin/sh

# 运行Task1，用于获取所有服务器当前的拍卖数据，保存到auction和auction history表

LOG_FILE=task1.log

# 是否已经运行
if [ -f running ]; then
  echo TaskRunner - 已经运行
  exit
fi

touch running

# 删除关闭文件
if [ -f shutdown ]; then
  rm shutdown
fi

now=$(date +"%Y-%m-%d-%H.%M.%S")

# 保存日志文件
if [ -f $LOG_FILE ]; then
  mv $LOG_FILE ./log/$LOG_FILE.$now
fi

nohup java -cp ./libs/*:bnade.jar:./config  lf.bnade.task.TaskRunner >>$LOG_FILE 2>>$LOG_FILE &
echo TaskRunner - 已启动>>$LOG_FILE