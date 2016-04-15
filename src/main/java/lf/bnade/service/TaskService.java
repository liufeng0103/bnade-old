package lf.bnade.service;

import java.sql.SQLException;

import lf.bnade.dao.TaskDao;
import lf.bnade.model.TaskHistory;

/*
 * Task相关操作
 */
public class TaskService {

	private TaskDao taskDao;	
	
	public TaskService() {
		taskDao = new TaskDao();
	}

	/*
	 * 获取task2正在处理的服务器名, task1运行时调用避免同时对一个服务器操作
	 * 没有数据时返回null
	 */
	public String getRunningRealm() throws SQLException {
		return taskDao.getRunningRealm();
	}
	
	/*
	 * 添加task2正在处理的服务器名, task2开始清理时添加正在清理的服务器,避免不同task对统一表同时做增删操作
	 */
	public int addRunningRealm(String realm) throws SQLException {
		return taskDao.addRunningRealm(realm);
	}
	
	/*
	 * 删除task2正在处理的服务器名, 但task2处理完某个服务器后调用
	 */
	public int removeRunningRealm(String realm) throws SQLException {
		return taskDao.removeRunningRealm(realm);
	}
	
	/*
	 * 添加task2处理结果
	 */
	public int addTaskHisotry(TaskHistory history) throws SQLException {
		return taskDao.addTaskHisotry(history);
	}
	
	/*
	 * 获取task2某天的运行数据
	 */
	public TaskHistory getTask2Historys(TaskHistory history) throws SQLException {
		return taskDao.getTask2Historys(history);
	}
}
