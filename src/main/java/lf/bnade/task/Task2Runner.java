package lf.bnade.task;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import lf.bnade.service.TaskService;
import lf.bnade.util.BnadeUtils;
import lf.bnade.util.TimeHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task2Runner {

	private static Logger logger = LoggerFactory.getLogger(Task2Runner.class);
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// int runCount = BnadeProperties.getTask2RunCountPerTime();
		logger.info("每次运行{}个服务器", 1);
		File f = new File("t2stop");
		if (f.exists()) {
			logger.info("发现关闭文件不运行Task2");
			System.exit(0);
		}
		TaskService taskService = new TaskService();
		Task2 task2 = new Task2(taskService);
		List<String> realmList = BnadeUtils.fileToStringList("realmlist.txt");			
		for (String realm : realmList) {
			try {
				if (args != null && args.length > 0) {
					String processDate = args[0];
					logger.info("服务器[{}]运行指定日期{}", realm, processDate);
					task2.process(realm, processDate);
				} else {
					task2.process(realm);
				}				
			} catch (Exception e) {
				String error = e.getMessage();
				if(error.length() > 255){
					error = error.substring(0, 255);
				}
				logger.error(error);
			} finally {
				try {
					logger.info("服务器[{}]从task表中删除{}条记录", realm, taskService.removeRunningRealm(realm));
				} catch (SQLException e) {
					String error = e.getMessage();
					if(error.length() > 255){
						error = error.substring(0, 255);
					}
					logger.error(error);
				}
			}
			if (f.exists()) {
				logger.info("发现关闭文件,不再继续运行剩下的服务器");						
				break;
			}				
		}
		logger.info("Task2总运行时间{}", TimeHelper.format(System.currentTimeMillis() - startTime));
	}

}
