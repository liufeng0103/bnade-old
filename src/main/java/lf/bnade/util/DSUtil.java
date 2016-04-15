package lf.bnade.util;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

/*
 * DataSource
 */
public class DSUtil {
	
	private static BasicDataSource dataSource;
	
	private DSUtil() {}

	public static DataSource getDataSource() {
		if(dataSource == null) {
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(BnadeProperties.getDriverClassName());
			dataSource.setUrl(BnadeProperties.getUrl());
			dataSource.setUsername(BnadeProperties.getUsername());
			dataSource.setPassword(BnadeProperties.getPassword());
			dataSource.setMaxActive(BnadeProperties.getMaxActive());
			dataSource.setInitialSize(1);
			dataSource.setMaxWait(60000);
			dataSource.setMaxIdle(20);
			dataSource.setMinIdle(3);
			dataSource.setRemoveAbandoned(true);
			dataSource.setRemoveAbandonedTimeout(180);
		}
		return dataSource;
	}
	
}
