package lf.bnade.dao;

import java.sql.SQLException;

/*
 * 表维护
 */
public class TableMaintainanceDao extends BaseDao {
	/*
	 * 优化指定表
	 */
	public int optimizeTable(String tableName) throws SQLException {
		return run.update("optimize table " + tableName);
	}
}
