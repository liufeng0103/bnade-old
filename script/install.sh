#!/bin/sh

# 安装war包到tomcat的ROOT目录下

WAR_FILE=bnade-1.0.war

now=$(date +"%Y-%m-%d-%H.%M.%S")
if [ -f $WAR_FILE ]; then
	echo 开始部署
	
	# 关闭tomcat
	echo 关闭tomcat
	service tomcat stop
	sleep 5s
	
	# -o   不 必先询问用户，unzip执 行后覆盖原有文件
	# -q   执 行时不显示任何信息
	unzip -oq $WAR_FILE -d /var/lib/tomcat/webapps/ROOT

	mv $WAR_FILE $WAR_FILE.$now
	
	# 启动tomcat
	echo 启动tomcat
	service tomcat start
	echo 部署完毕

else
	echo 找不到$WAR_FILE
fi

