package lf.bnade.task;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lf.bnade.util.BnadeProperties;
import lf.bnade.util.BnadeUtils;
import lf.bnade.util.TimeHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskRunner implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(TaskRunner.class);
	
	private boolean isComplete;
	
	private String realm;  TaskRunner() {}
	
	public TaskRunner(String realm) {
		this.realm = realm;
	}

	@Override
	public void run() {
		try {
			isComplete = false;
			new Task1Tmp().process(realm);
		} catch (SQLException e) {
			String error = e.getMessage();
			if(error.length() > 255){
				error = error.substring(0, 255);
			}
			logger.error(error);
		} finally {
			isComplete = true;
		}
	}
	
	public boolean isComplete() {
		return isComplete;
	}

	public static void main(String[] args) throws Exception {
		int threadCount = BnadeProperties.getTask1ThreadCount();
		File f = new File("shutdown");
		File f2 = new File("running");		
		if (!f.exists()) {
			while(true) {
				long startTime = System.currentTimeMillis();
				ExecutorService pool = Executors.newFixedThreadPool(threadCount); 
				// 获取所有服务器列表		
				List<String> realmList = BnadeUtils.fileToStringList("realmlist.txt");
				List<TaskRunner> tasks = new ArrayList<TaskRunner>();
				for (int i = 0; i < realmList.size(); i++) {
					if (f.exists()) {
						logger.info("TaskRunner准备关闭,等待未完成的Task运行完毕,停止剩下的服务器运行Task,总共运行{}个,当前服务器[{}]", i + 1, realmList.get(i));
						break;
					}
					TaskRunner taskRunner = new TaskRunner(realmList.get(i));
					while(true) {
						boolean isTaskAdded = false;
						if (i >= threadCount) {
							for (TaskRunner runing : tasks) {
								if (runing.isComplete()) {
									tasks.remove(runing);
									tasks.add(taskRunner);
									pool.execute(taskRunner);
									isTaskAdded = true;
									logger.info("发现有Task运行完成, 服务器[{}]添加到TaskRunner准备运行", realmList.get(i));
									break;
								}
							}
							if (isTaskAdded) {
								break;
							} else {
								Thread.sleep(1000);
							}
						} else {
							tasks.add(taskRunner);
							pool.execute(taskRunner);
							logger.info("服务器[{}]添加到TaskRunner准备运行", realmList.get(i) );
							break;
						}
					}
				}
				pool.shutdown();
				while(true) {
					if (!pool.isTerminated()) {
						logger.info("线程没有结束，等待3分钟");
						Thread.sleep(60000 * 3);
					} else {
						logger.info("服务器数据更新完毕，用时{}", TimeHelper.format(System.currentTimeMillis() - startTime));
						if (f.exists()) {
							logger.info("关闭TaskRunner");
							if(f2.exists()) {
								f2.delete();
							}
							System.exit(0);
						} else {
							logger.info("线程结束，重新启动task runner");
							break;
						}
					}
				}
			}
		} else {
			logger.info("发现shutdown文件, TaskRunner不运行");
		}
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}
	
	
}
