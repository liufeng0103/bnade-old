package lf.bnade;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class InitListener implements ServletContextListener {
	
	private static Logger logger = LoggerFactory.getLogger(InitListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
		initMItemTable();
	}
	
	/*
	 * 如果mt_item表为空，初始化表数据
	 */
	private void initMItemTable() {
//		ItemService itemService = new ItemService();
//		try {
//			logger.info("清空mt_item表");
//			itemService.deleteMItem();
//			logger.info("开始从t_item表导入数");
//			itemService.initMItem();
//			logger.info("mt_item数据导入完成");			
//		} catch (SQLException e) {
//			logger.error(e.getMessage());
//			e.printStackTrace();
//		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

}
