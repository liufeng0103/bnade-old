USE wow;

-- truncate table;
-- 服务器信息
CREATE TABLE IF NOT EXISTS t_realm (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	
	name VARCHAR(8) NOT NULL,						-- 服务器名
	historyTableName VARCHAR(24) NOT NULL,			-- 历史纪录表名	t_auction_history_xxx
	periodHistoryTableName VARCHAR(30) NOT NULL,	-- 时段记录表名	t_auction_history_period_xxx
	dailyHistoryTableName VARCHAR(30) NOT NULL,		-- 每日记录表名	t_auction_history_daily_xxx
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_realm ADD INDEX(name);
-- 各服务器history表，有程序产生不用自己创建
CREATE TABLE IF NOT EXISTS t_auction_history_xxx (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	-- 主键ID
	itemId INT UNSIGNED NOT NULL,				-- 物品ID
	realmId INT UNSIGNED NOT NULL,				-- 服务器ID
	minBid BIGINT NOT NULL,						-- 最低竞价
	minBidOwner VARCHAR(12) NOT NULL,			-- 最低竞价卖家
	minBuyout BIGINT NOT NULL,					-- 最低一口价
	minBuyoutOwner VARCHAR(12) NOT NULL,		-- 最低一口价卖家
	totalQuantity INT NOT NULL,					-- 总数量
	lastModified BIGINT UNSIGNED NOT NULL,		-- 更新时间	
	petSpeciesId INT NOT NULL,					-- 宠物ID
	petLevel INT NOT NULL,						-- 宠物等级
	context INT NOT NULL, 						-- 物品来源(制造业，各种难度FB)
	bonusList VARCHAR(20) NOT NULL, 			-- 装备奖励
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_auction_history_xxx ADD INDEX(lastModified); -- task2获取服务器指定时间内数据时使用，task2清理服务器指定时间内数据时使用
ALTER TABLE t_auction_history_xxx ADD INDEX(itemId,lastModified); -- 获取24小时或一周内数据时用
ALTER TABLE t_auction_history_xxx ADD INDEX(petSpeciesId,lastModified);

-- 最近一次拍卖数据信息
DROP TABLE IF EXISTS t_latest_auction;
CREATE TABLE t_latest_auction (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	-- 主键ID	
	itemId INT UNSIGNED NOT NULL,				-- 物品ID
	realmId INT UNSIGNED NOT NULL,				-- 服务器ID
	minBid BIGINT NOT NULL,						-- 最低竞价
	minBidOwner VARCHAR(12) NOT NULL,			-- 最低竞价卖家
	minBuyout BIGINT NOT NULL,					-- 最低一口价
	minBuyoutOwner VARCHAR(12) NOT NULL,		-- 最低一口价卖家
	totalQuantity INT NOT NULL,					-- 总数量
	timeLeft VARCHAR(12) NOT NULL,
	lastModified BIGINT NOT NULL,				-- 更新时间
	petSpeciesId INT NOT NULL,					-- 宠物ID
	petLevel INT NOT NULL,						-- 宠物等级
	petBreedId INT NOT NULL,					-- 宠物成长类型
	context INT NOT NULL, 						-- 物品来源(制造业，各种难度FB)
	bonusList VARCHAR(20) NOT NULL, 			-- 装备奖励
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	
ALTER TABLE t_latest_auction ADD INDEX(itemId); 	-- 获取某个物品在所有服务器的数据用
ALTER TABLE t_latest_auction ADD INDEX(realmId); 	-- task1清理某个服务器数据和拷贝某个服务器数据到history表时用
ALTER TABLE t_latest_auction ADD INDEX(petSpeciesId); -- 查询所有服务器宠物数据时用

ALTER TABLE t_latest_auction ADD petBreedId INT NOT NULL after petLevel;
ALTER TABLE t_latest_auction ADD timeLeft VARCHAR(12) NOT NULL after totalQuantity;
ALTER TABLE t_latest_auction ADD context INT NOT NULL, ADD bonusList VARCHAR(20) NOT NULL;  -- 旧表添加新列
ALTER TABLE t_latest_auction  drop column context,bonusList;  -- 旧表添加新列
truncate t_latest_auction;

-- 拍卖行所有拍卖的物品
DROP TABLE IF EXISTS t_auction_house_x;
CREATE TABLE t_auction_house_x (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,	-- 主键ID
	realmId INT UNSIGNED NOT NULL,				-- 服务器ID
	auc INT UNSIGNED NOT NULL,					-- 拍卖ID
	item INT UNSIGNED NOT NULL,					-- 物品ID	
	owner VARCHAR(12) NOT NULL,					-- 卖家
	ownerRealm VARCHAR(8) NOT NULL,				-- 卖家服务器
	bid	BIGINT NOT NULL,						-- 竞价
	buyout BIGINT NOT NULL,						-- 一口价	
	quantity INT NOT NULL,						-- 数量
	timeLeft VARCHAR(12) NOT NULL,				-- 剩余时间
	petSpeciesId INT NOT NULL,					-- 宠物ID
	petLevel INT NOT NULL,						-- 宠物等级
	petBreedId INT NOT NULL,					-- 宠物成长类型
	context INT NOT NULL, 						-- 物品来源(制造业，各种难度FB)
	bonusList VARCHAR(20) NOT NULL, 			-- 装备奖励
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	
ALTER TABLE t_auction_house ADD INDEX(realmId,owner); 	-- 使用: 1. 删除某个服务器所有数据 2. 某个服务器某个玩家拍卖的所有物品

-- 物品信息
DROP TABLE IF EXISTS t_item;
CREATE TABLE t_item (
	id	INT UNSIGNED NOT NULL,			-- ID
	description VARCHAR(255) NOT NULL,	-- 描述
	name VARCHAR(80) NOT NULL,			-- 物品名
	icon VARCHAR(64) NOT NULL,			-- 图标名
	itemClass INT NOT NULL,
	itemSubClass INT NOT NULL,
	inventoryType INT NOT NULL,
	itemLevel INT NOT NULL,				-- 物品等级
	json text NOT NULL,					-- api返回物品的json,暂时用不上
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `t_item` ADD INDEX(`name`);
ALTER TABLE t_item ADD itemClass INT NOT NULL, ADD itemSubClass INT NOT NULL, ADD inventoryType INT NOT NULL after icon;

ALTER TABLE t_item ADD itemLevel INT NOT NULL; -- 旧表添加新列
ALTER TABLE t_item MODIFY json text NOT NULL;
-- 官网api没有下面这些物品信息手动插入
insert into t_item (id,name,itemLevel) values(5108,'黑铁皮衣',32);
insert into t_item (id,name,itemLevel) values(5487,'食谱：掘地鼠炖肉',20);
insert into t_item (id,name,itemLevel) values(11151,'公式：附魔手套 - 采药',29);
insert into t_item (id,name,itemLevel) values(11206,'公式：附魔披风 - 次级敏捷',45);
insert into t_item (id,name,itemLevel) values(11818,'格里塞特厕所钥匙',43);
insert into t_item (id,name,itemLevel) values(14469,'图样：符文布袍',52);
insert into t_item (id,name,itemLevel) values(14478,'图样：亮布长袍',54);
insert into t_item (id,name,itemLevel) values(14484,'图样：亮布披风',55);
insert into t_item (id,name,itemLevel) values(14491,'图样：符文布短裤',57);
insert into t_item (id,name,itemLevel) values(14498,'图样：符文布头带',59);
insert into t_item (id,name,itemLevel) values(15733,'图样：绿龙鳞片护腿',54);
insert into t_item (id,name,itemLevel) values(18401,'弗洛尔的屠龙技术纲要',60);
insert into t_item (id,name,itemLevel) values(18782,'《高级铸甲技术：第二卷》的上半部',35);
insert into t_item (id,name,itemLevel) values(19709,'黄色哈卡莱宝石',61);
insert into t_item (id,name,itemLevel) values(19712,'紫色哈卡莱宝石',61);
insert into t_item (id,name,itemLevel) values(24507,'元素碎块',1);
-- 物品 内存表
DROP TABLE IF EXISTS mt_item;
CREATE TABLE mt_item (
	id	INT UNSIGNED NOT NULL,	
	name VARCHAR(80) NOT NULL,
	icon VARCHAR(64) NOT NULL,	
	itemLevel INT NOT NULL,
	PRIMARY KEY(id)
) ENGINE=Memory DEFAULT CHARSET=utf8;
ALTER TABLE `mt_item` ADD INDEX(`name`);
truncate mt_item
insert into mt_item (id,name,icon,itemLevel) select id,name,icon,itemLevel from t_item
SELECT id,name,icon FROM `mt_item` WHERE name like '%龙%'

DROP TABLE IF EXISTS t_item_bonus;
CREATE TABLE t_item_bonus (
	itemId	INT UNSIGNED NOT NULL,		-- 物品ID
	context	INT NOT NULL,
	bonusList VARCHAR(20) NOT NULL, 	-- 装备奖励
	PRIMARY KEY(itemId,context,bonusList)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 宠物信息表
CREATE TABLE IF NOT EXISTS t_pet (	
	id INT UNSIGNED NOT NULL,		-- 宠物id				
	name VARCHAR(16) NOT NULL,		-- 宠物名
	icon VARCHAR(64) NOT NULL,		-- 宠物图标
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_pet ADD INDEX(name);

DROP TABLE IF EXISTS t_pet_breed;
CREATE TABLE t_pet_breed (
	petSpeciesId INT UNSIGNED NOT NULL,
	petBreedId	INT UNSIGNED NOT NULL,	
	PRIMARY KEY(petSpeciesId,petBreedId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_pet_stats;
CREATE TABLE t_pet_stats (
	speciesId INT UNSIGNED NOT NULL,
	breedId	INT UNSIGNED NOT NULL,
	petQualityId INT UNSIGNED NOT NULL default 3,
	level	INT UNSIGNED NOT NULL default 25,
	health INT UNSIGNED NOT NULL,
	power	INT UNSIGNED NOT NULL,	
	speed INT NOT NULL,
	PRIMARY KEY(speciesId,breedId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 

-- 拍卖行文件信息，主要是json数据的url和数据的更新日期比较重要
DROP TABLE IF EXISTS t_auction_file;
CREATE TABLE t_auction_file (
	id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	url VARCHAR(128) NOT NULL,
	lastModified BIGINT NOT NULL,
	realm VARCHAR(8) NOT NULL,
	maxAucId INT NOT NULL,
	quantity INT NOT NULL,		-- 最新一次总的拍卖数量
	ownerQuantity INT NOT NULL,	-- 拍卖行玩家数量
	itemQuantity INT NOT NULL,	-- 物品种类数量
	createTime BIGINT NOT NULL,
	lastUpdateTime BIGINT NOT NULL,
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_auction_file ADD quantity INT NOT NULL;
ALTER TABLE t_auction_file ADD ownerQuantity INT NOT NULL, itemQuantity INT NOT NULL;

-- 保存task2当前正在运行的realm
DROP TABLE IF EXISTS t_t2realm;
CREATE TABLE t_t2realm (	
	realm VARCHAR(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 保存Job运行过的realm
DROP TABLE IF EXISTS t_job_control;
CREATE TABLE t_job_control (	
	realm VARCHAR(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 时光徽章
DROP TABLE IF EXISTS t_wowtoken;
CREATE TABLE t_wowtoken (	
	buy INT UNSIGNED NOT NULL,
	updated BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY(updated)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
-- 服务器玩家
DROP TABLE IF EXISTS t_player;
CREATE TABLE t_player (	
	id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(12) NOT NULL,
	realm VARCHAR(8) NOT NULL,	
	realmId INT UNSIGNED NOT NULL,
	classV INT UNSIGNED NOT NULL,
	race INT UNSIGNED NOT NULL,
	gender TINYINT UNSIGNED NOT NULL,
	level INT UNSIGNED NOT NULL,
	thumbnail VARCHAR(64) NOT NULL,
	lastModified BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 服务器最新玩家
DROP TABLE IF EXISTS t_current_player;
CREATE TABLE t_current_player (	
	id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	realm VARCHAR(8) NOT NULL,	
	name VARCHAR(12) NOT NULL,
	realmId INT UNSIGNED NOT NULL,
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE t_current_player ADD INDEX(realmId);

-- 小于10级的玩家，api找不到
DROP TABLE IF EXISTS t_lowlevel_player;
CREATE TABLE t_lowlevel_player (	
	id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	realm VARCHAR(8) NOT NULL,	
	name VARCHAR(12) NOT NULL,
	realmId INT UNSIGNED NOT NULL,
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 每天拍卖数量，玩家数统计
DROP TABLE IF EXISTS t_auction_house_statistic;
CREATE TABLE t_auction_house_statistic (	
	id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	realmId INT UNSIGNED NOT NULL,			-- 服务器id， 0标识所有
	realmCount INT UNSIGNED NOT NULL,		-- 服务器数量
	auctionQuantity INT UNSIGNED NOT NULL,	-- 拍卖物品数
	ownerQuantity INT UNSIGNED NOT NULL,	-- 拍卖行玩家数
	updated BIGINT UNSIGNED NOT NULL,	-- 更新时间
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 保存task运行信息
DROP TABLE IF EXISTS t_task_history;
CREATE TABLE t_task_history (
	id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	type INT UNSIGNED NOT NULL,				-- task类型，现在有task1，task2
	realmId INT UNSIGNED NOT NULL,			-- 处理的服务器ID
	processTime BIGINT UNSIGNED NOT NULL,	-- task2用于查看处理的那天的记录 
	status INT UNSIGNED NOT NULL,			-- 运行结果，成功还是失败
	message VARCHAR(255) NOT NULL,			-- 运行失败时的异常信息
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;