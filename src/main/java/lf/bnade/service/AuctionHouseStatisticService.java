package lf.bnade.service;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.dao.AuctionHouseStatisticDao;
import lf.bnade.model.AuctionHouseStatistic;

public class AuctionHouseStatisticService {
	
	private AuctionHouseStatisticDao dao;
	
	public AuctionHouseStatisticService() {
		dao = new AuctionHouseStatisticDao();
	}

	/*
	 * 获取服务器拍卖数量和玩家数量统计结果
	 */
	public List<AuctionHouseStatistic> getAuctionHouseStatistics() throws SQLException {
		return dao.getAuctionHouseStatistics();
	}
	
	/*
	 * 保存服务器拍卖数量和玩家数量统计结果到历史表
	 */
	public void add(List<AuctionHouseStatistic> list) throws SQLException {	
		dao.add(list);
	}
}
