package lf.bnade.task;

import java.sql.SQLException;

import lf.bnade.dao.BaseDao;

public class DBTableManager extends BaseDao {

	// ----------------------------t_auction_house_x-------------------------------------
	/*
	 * 创建表
	 */
	public void createAuctionHouseTable() throws SQLException {
		for (int i = 1; i <= 170; i++) {
			String sql = "CREATE TABLE t_auction_house_"+i+" (			"+
					"	id INT UNSIGNED NOT NULL AUTO_INCREMENT,"+
					"	realmId INT UNSIGNED NOT NULL,			"+
					"	auc INT UNSIGNED NOT NULL,				"+
					"	item INT UNSIGNED NOT NULL,				"+
					"	owner VARCHAR(12) NOT NULL,				"+
					"	ownerRealm VARCHAR(8) NOT NULL,			"+
					"	bid	BIGINT NOT NULL,					"+
					"	buyout BIGINT NOT NULL,					"+
					"	quantity INT NOT NULL,					"+
					"	timeLeft VARCHAR(12) NOT NULL,			"+
					"	petSpeciesId INT NOT NULL,				"+
					"	petLevel INT NOT NULL,					"+
					"	petBreedId INT NOT NULL,				"+
					"	context INT NOT NULL, 					"+
					"	bonusList VARCHAR(20) NOT NULL, 		"+
					"	PRIMARY KEY(id)                         "+
					") ENGINE=InnoDB DEFAULT CHARSET=utf8      ";
			run.update(sql);
			System.out.println("已创建t_auction_house表：" + i);
		}
		
	}
	/*
	 * 创建索引
	 */
	public void createAuctionHouseTableIndex() throws SQLException {
		for (int i = 1; i <= 170; i++) {
			String sql = "ALTER TABLE t_auction_house_" + i + " ADD INDEX(realmId,owner)";
			run.update(sql);
			System.out.println("已为表t_auction_house_"+i+"创建索引");
		}
		
	}
	
	public static void main(String[] args) throws SQLException {
		DBTableManager m = new DBTableManager();
		m.createAuctionHouseTable();
		m.createAuctionHouseTableIndex();
	}
}
