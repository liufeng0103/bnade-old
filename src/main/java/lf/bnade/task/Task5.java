package lf.bnade.task;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import lf.bnade.jmodel.wowtoken.JWowToken;
import lf.bnade.jmodel.wowtoken.JWowTokenRegionHistory;
import lf.bnade.model.WowToken;
import lf.bnade.service.WowTokenService;
import lf.bnade.util.BnadeProperties;
import lf.bnade.util.TimeHelper;
import lf.bnade.wow.WowTokenFetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 时光徽章操作
 */
public class Task5 {

	private static Logger logger = LoggerFactory.getLogger(Task5.class);
	
	public static void main(String[] args) throws Exception {
		logger.info("开始运行Task5");
		long startTime = System.currentTimeMillis();
		File f = new File("t5stop");
		Task5 task = new Task5();
		while(true) {
			if (f.exists()) {
				logger.info("发现关闭文件不运行Task5");
				System.exit(0);
			}	
			try {
				task.process();
				logger.info("等待{}", TimeHelper.format(BnadeProperties.getTask5WaitTime()));
				Thread.sleep(BnadeProperties.getTask5WaitTime());
//				Thread.sleep(30*1000);
				if (f.exists()) {
					logger.info("发现关闭文件,不再继续更新");						
					break;
				}	
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				Thread.sleep(60*1000);
				logger.error("发生异常，等待1分钟");
			}
		}				
		logger.info("Task5总运行时间{}", TimeHelper.format(System.currentTimeMillis() - startTime));	
	}
	
	public void process() throws SQLException {
		logger.info("开始更新");
		WowTokenService service = new WowTokenService();
		WowTokenFetcher fetcher = new WowTokenFetcher();
		logger.info("开始下载最新数据");
		JWowToken jWowToken = fetcher.getWowTokenByRegion("CN");
		logger.info("下载完毕");
		WowToken dbToken = service.getWowTokenByUpdated(jWowToken.getUpdated());
		if (dbToken == null) {
			WowToken newToken = new WowToken();
			newToken.setBuy(jWowToken.getBuy());
			newToken.setUpdated(jWowToken.getUpdated());
			logger.info("保存新数据 buy={},updated={}", newToken.getBuy(), new Date(newToken.getUpdated()));
			service.add(newToken);			
		} else {
			logger.info("已存在，不更新 {}", new Date(jWowToken.getUpdated()));
		}
		logger.info("更新完毕");
	}
	
	public void tokenInit() throws Exception {
		WowTokenService service = new WowTokenService();
		WowTokenFetcher fetcher = new WowTokenFetcher();	
		System.out.println("开始下载数据");
		JWowTokenRegionHistory history = fetcher.getWowTokenHistoryByRegion("CN");
		System.out.println("开始保存时光徽章历史记录 " + history.getCN().size());
		for (List<Long> t : history.getCN()) {
			WowToken token = new WowToken();
			token.setBuy(new Long(t.get(1)).intValue());
			token.setUpdated((long)t.get(0)*1000);
			System.out.println(new Date(token.getUpdated()) + " " + token.getBuy());	
			service.add(token);
		}	
		System.out.println("完成");
	}
}
