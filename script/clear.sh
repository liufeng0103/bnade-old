# 执行一些清理工作
find /home/lf/bnade/log -mtime +7 -type f -name *.log.* -exec rm -f {} \;
find /home/lf/tfade/log -mtime +7 -type f -name *.log.* -exec rm -f {} \;
