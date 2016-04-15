#!/bin/sh

# 启动Task11，把各拍卖行拍卖物品数，玩家数保存起来

LOG_FILE=task11.log

# 切换目录，不然使用crontab定时任务无法找到相对路径文件
cd /home/lf/bnade

now=$(date +"%Y-%m-%d-%H.%M.%S")

# 保存log文件
if [ -f $LOG_FILE ]; then
  mv $LOG_FILE ./log/$LOG_FILE.$now
  echo 保存log文件到/log/$LOG_FILE.$now>>$LOG_FILE
fi

nohup java -cp ./libs/*:./bnade.jar:./config  lf.bnade.task.AuctionHouseStatisticTask  >>$LOG_FILE 2>>$LOG_FILE &
