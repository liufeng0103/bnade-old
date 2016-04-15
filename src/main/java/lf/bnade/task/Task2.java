package lf.bnade.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lf.bnade.model.Auction;
import lf.bnade.model.Realm;
import lf.bnade.model.TaskHistory;
import lf.bnade.service.AuctionHistoryService;
import lf.bnade.service.TaskService;
import lf.bnade.task.handler.AuctionHistoryHandler;
import lf.bnade.util.TimeHelper;
import lf.bnade.util.TimeMoniter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分析清理拍卖行历史数据，历史纪录按每天4次总结
 */
public class Task2 {

	private static Logger logger = LoggerFactory.getLogger(Task2.class);

	private TaskService taskService;	
	private AuctionHistoryService historyService;
	private AuctionHistoryHandler historyHandler;
	private TimeMoniter timeMoniter;	

	public Task2(TaskService taskService) {
		this.taskService = taskService;
		historyService = new AuctionHistoryService();
		historyHandler = new AuctionHistoryHandler();
	}

	public void process(String realm) throws Exception {		
		// 对昨天的数据分析
		long todayTime = System.currentTimeMillis();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		String processDate = sdf.format(sdf.parse(sdf.format(todayTime)).getTime() - TimeHelper.DAY); // 昨天
		process(realm, processDate);
	}
	
	public void process(String realm, String date) throws Exception {
		logger.info("服务器[{}]开始运行{}", realm, getName());	
		Realm realmObj = historyService.getRealmMap().get(realm);
		// 指定日期数据分析			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long startTime  = sdf.parse(date).getTime(); 
		long endTime = startTime + TimeHelper.DAY;
		// 不处理大于当天的日期，包括当天
//		if (startTime < sdf.parse(sdf.format(System.currentTimeMillis())).getTime()) {
			try {
				TaskHistory taskHistory = taskService.getTask2Historys(new TaskHistory(getType(), realmObj.getId(), startTime, TaskHistory.STATUS_SUCCESS));
				if (taskHistory == null) {
					timeMoniter = new TimeMoniter();	
					timeMoniter.start();	
					
					logger.info("服务器[{}]处理{}全天的历史数据", realm, sdf.format(startTime));
					// 服务器一天的历史纪录
					List<Auction> auctions = historyService.getHistoryByRangeTime(realmObj.getHistoryTableName(), startTime, endTime);
					logger.info("服务器[{}]从{}表获取从{}到{}总共{}条历史数据{}", realm, realmObj.getHistoryTableName(),sdf.format(startTime), sdf.format(endTime), auctions.size(), timeMoniter.spend());
					if (auctions.size() > 0) {			
						// 计算所有服务器一天4个时段每个物品的平均值
						historyHandler.process(auctions, startTime);
						logger.info("服务器[{}]{}条历史数据分析完毕{}", realm, auctions.size(), timeMoniter.spend());
						// 保存到数据库
						List<Auction> result = historyHandler.getResult();
						logger.info("服务器[{}]开始保存分析过的历史数据{}条到表{}", realm, result.size(), realmObj.getPeriodHistoryTableName());
						historyService.addHistorys(result, realmObj.getPeriodHistoryTableName());
						logger.info("服务器[{}]保存分析过的历史数据{}条到表{}完毕{}", realm, result.size(), realmObj.getPeriodHistoryTableName(), timeMoniter.spend());	
					} else {
						logger.info("服务器[{}]历史数据未找到", realm);
					}	
					// 清理前天数据
					long clearStartTime = startTime - TimeHelper.DAY;
					long clearEndTime =	endTime - TimeHelper.DAY;
					logger.info("服务器[{}]开始清理从{}到{}的历史数据", realm, new Date(clearStartTime), new Date(clearEndTime));			
					logger.info("服务器[{}]添加{}条正在清理的服务器到数据库{}", realm, taskService.addRunningRealm(realm), timeMoniter.spend());
					int deletedCount = historyService.removeHistoryForTableAndTime(realmObj.getHistoryTableName(), clearStartTime, clearEndTime);			
					logger.info("服务器[{}]从数据库{}表删除分析过的历史数据{}条{}", realm, realmObj.getHistoryTableName(), deletedCount, timeMoniter.spend());
					TaskHistory history = new TaskHistory(getType(), realmObj.getId(), startTime, TaskHistory.STATUS_SUCCESS);
					history.setMessage("");				
					logger.info("服务器[{}]添加{}条运行记录到数据库{}", realm, taskService.addTaskHisotry(history), timeMoniter.spend());
				} else {
					logger.info("服务器[{}]数据已处理过", realm);
				}		
			} catch (Exception e) {
				TaskHistory history = new TaskHistory(getType(), realmObj.getId(), startTime, TaskHistory.STATUS_FAILED);
				String error = e.getMessage();
				if (error.length() > 255) {
					error = error.substring(0, 255);
				}
				history.setMessage(error);
				logger.info("服务器[{}]添加{}条失败的运行记录到数据库{}", realm, taskService.addTaskHisotry(history), timeMoniter.spend());
				throw e;
			}
//		} else {
//			logger.info("服务器[{}]处理日期{},大于等于今日, 数据还不全,请稍后再试", realm, new Date(startTime));
//		}
	}

	public static void main(String[] args) throws Exception{
		TaskService taskService = new TaskService();
		Task2 task = new Task2(taskService);
		String realm = "万色星辰";
		task.process(realm, "2016-02-19");
//		task.process(realm);
		taskService.removeRunningRealm(realm);
	}
	
	public int getType() {
		return 2;
	}

	public String getName() {
		return "Task2";
	}

	public String getDescription() {
		return "分析清理拍卖行历史数据，历史纪录按每天4次总结";
	}

	

}
