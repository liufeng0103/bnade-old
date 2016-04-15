#!/bin/sh

# 启动Job 用于把就表数据导入到新表

LOG_FILE=job.log


nohup java -cp ./libs/*:./bnade.jar:./config  lf.bnade.task.Job1 job2 >$LOG_FILE 2>>$LOG_FILE &
