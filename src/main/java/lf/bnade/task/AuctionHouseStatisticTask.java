package lf.bnade.task;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lf.bnade.model.AuctionHouseStatistic;
import lf.bnade.service.AuctionHouseStatisticService;

/*
 * task11 把各拍卖行拍卖物品数，玩家数保存起来
 */
public class AuctionHouseStatisticTask {
	
	private static Logger logger = LoggerFactory.getLogger(AuctionHouseStatisticTask.class);
	
	private AuctionHouseStatisticService service;
	
	public AuctionHouseStatisticTask() {
		service = new AuctionHouseStatisticService();
	}

	public void process() throws SQLException {
		logger.info("开始运行");
		List<AuctionHouseStatistic> ahs = service.getAuctionHouseStatistics();
		if(ahs != null && ahs.size() > 0) {
			logger.info("开始保存{}条记录", ahs.size());
			service.add(ahs);
		}
		logger.info("完毕");
	}
	
	public static void main(String[] args) {
		AuctionHouseStatisticTask task = new AuctionHouseStatisticTask();
		try {
			task.process();
		} catch (SQLException e) {
			logger.error("出错了:{}", e.getMessage().length() > 255 ? e.getMessage().substring(0, 255) : e.getMessage());			
		}
	}
}
