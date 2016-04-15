package lf.bnade.dao;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.util.DSUtil;

import org.apache.commons.dbutils.QueryRunner;

public class BaseDao {
	protected QueryRunner run;
	
	public BaseDao() {
		run = new QueryRunner(DSUtil.getDataSource());
	}
	
	/*
	 * sql中用in的时候把list转成in中的字符串
	 */
	protected String listToString(List<String> list) {
		StringBuffer sb = new StringBuffer();
		for (String s : list) {
			if (sb.length() == 0) {
				sb.append("'");
				sb.append(s);
				sb.append("'");
			} else {
				sb.append(",'");
				sb.append(s);
				sb.append("'");
			}
		}
		return sb.toString();
	}
	
	/*
	 * 删除表
	 */
	public int dropTable(String tableName) throws SQLException {
		return run.update("drop table " + tableName);
	}
}
