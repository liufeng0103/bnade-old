package lf.bnade.dao;

import java.sql.SQLException;

import lf.bnade.model.TaskHistory;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/*
 * Task相关的db操作
 */
public class TaskDao extends BaseDao {

	/*
	 * 获取task2正在处理的服务器名
	 */
	public String getRunningRealm() throws SQLException {
		return run.query("select realm from t_t2realm limit 1", new ScalarHandler<String>());
	}
	
	/*
	 * 添加task2正在处理的服务器名
	 */
	public int addRunningRealm(String realm) throws SQLException {
		return run.update("insert into t_t2realm values(?)", realm);
	}
	
	/*
	 * 删除task2正在处理的服务器名
	 */
	public int removeRunningRealm(String realm) throws SQLException {
		return run.update("delete from t_t2realm where realm=?", realm);
	}
	
	/*
	 * 添加task2处理结果
	 */
	public int addTaskHisotry(TaskHistory history) throws SQLException {
		return run.update("insert into t_task_history (type,realmId,processTime,status,message) values(?,?,?,?,?)", 
				history.getType(), history.getRealmId(), history.getProcessTime(), history.getStatus(), history.getMessage());
	}
	
	/*
	 * 获取task2某天的运行数据
	 */
	public TaskHistory getTask2Historys(TaskHistory history) throws SQLException {
		return run.query("select * from t_task_history where type=? and realmId=? and processTime=? and status=? limit 1", 
				new BeanHandler<TaskHistory>(TaskHistory.class), history.getType(), history.getRealmId(), history.getProcessTime(), history.getStatus());
	}
	
}
