var leftTimeMap = {"VERY_LONG":"非常长","LONG":"长","MEDIUM":"中","SHORT":"短"};
var Bnade = {
	connectedRealm : {"奥杜尔":"奥杜尔-普瑞斯托-逐日者","恶魔之翼":"恶魔之翼-通灵学院","伊莫塔尔":"伊莫塔尔-萨尔","大地之怒":"大地之怒-恶魔之魂-希尔瓦娜斯","主宰之剑":"主宰之剑-霍格","雷霆号角":"雷霆号角-风暴之眼","基尔加丹":"基尔加丹-奥拉基尔","图拉扬":"图拉扬-海达希亚-瓦里玛萨斯-塞纳里奥","克洛玛古斯":"克洛玛古斯-金度","利刃之拳":"利刃之拳-黑翼之巢","索瑞森":"索瑞森-试炼之环","无尽之海":"无尽之海-米奈希尔","嚎风峡湾":"嚎风峡湾-闪电之刃","古加尔":"古加尔-洛丹伦","拉贾克斯":"拉贾克斯-荆棘谷","影牙要塞":"影牙要塞-艾苏恩","无底海渊":"无底海渊-阿努巴拉克-刀塔-诺莫瑞根","斯坦索姆":"斯坦索姆-穆戈尔-泰拉尔-格鲁尔","藏宝海湾":"藏宝海湾-阿拉希-塔伦米尔","拉格纳洛斯":"拉格纳洛斯-龙骨平原","安纳塞隆":"安纳塞隆-日落沼泽-风暴之鳞-耐普图隆","提瑞斯法":"提瑞斯法-暗影议会","法拉希姆":"法拉希姆-玛法里奥-麦维影歌","加里索斯":"加里索斯-库德兰","安其拉":"安其拉-弗塞雷迦-盖斯","烈焰峰":"烈焰峰-瓦拉斯塔兹","熔火之心":"熔火之心-黑锋哨站","奥达曼":"奥达曼-甜水绿洲","踏梦者":"踏梦者-阿比迪斯","暮色森林":"暮色森林-杜隆坦-狂风峭壁-玛瑟里顿","丹莫德":"丹莫德-克苏恩","格瑞姆巴托":"格瑞姆巴托-埃霍恩","勇士岛":"勇士岛-达文格尔-索拉丁","范克里夫":"范克里夫-血环","艾维娜":"艾维娜-艾露恩","塞泰克":"塞泰克-罗曼斯-黑暗之矛","洛萨":"洛萨-阿卡玛-萨格拉斯","回音山":"回音山-霜之哀伤-神圣之歌-遗忘海岸","提尔之手":"提尔之手-萨菲隆","凯恩血蹄":"凯恩血蹄-瑟莱德丝-卡德加","月光林地":"月光林地-麦迪文","永夜港":"永夜港-翡翠梦境-黄金之路","伊兰尼库斯":"伊兰尼库斯-阿克蒙德-恐怖图腾","梦境之树":"梦境之树-诺兹多姆-泰兰德","奈萨里奥":"奈萨里奥-红龙女王-菲拉斯","末日祷告祭坛":"末日祷告祭坛-迦罗娜-纳沙塔尔-火羽山","卡德罗斯":"卡德罗斯-符文图腾-黑暗魅影-阿斯塔洛","摩摩尔":"摩摩尔-熵魔-暴风祭坛","激流堡":"激流堡-阿古斯","火喉":"火喉-雷克萨","激流之傲":"激流之傲-红云台地","冰川之拳":"冰川之拳-双子峰-埃苏雷格-凯尔萨斯","冬泉谷":"冬泉谷-寒冰皇冠","燃烧平原":"燃烧平原-风行者","自由之风":"自由之风-达隆米尔-艾欧纳尔-冬寒","元素之力":"元素之力-菲米丝-夏维安","古达克":"古达克-梅尔加尼","埃加洛尔":"埃加洛尔-鲜血熔炉-斩魔者","加尔":"加尔-黑龙军团","外域":"外域-织亡者-阿格拉玛-屠魔山谷","普罗德摩":"普罗德摩-铜龙军团","冬拥湖":"冬拥湖-迪托马斯-达基萨斯","刺骨利刃":"刺骨利刃-千针石林","埃雷达尔":"埃雷达尔-永恒之井","奎尔丹纳斯":"奎尔丹纳斯-艾莫莉丝-布鲁塔卢斯","凤凰之神":"凤凰之神-托塞德林","伊利丹":"伊利丹-尘风峡谷","军团要塞":"军团要塞-生态船","安威玛尔":"安威玛尔-扎拉赞恩","拉文凯斯":"拉文凯斯-迪瑟洛克","万色星辰":"万色星辰-奥蕾莉亚-世界之树-布莱恩","哈卡":"哈卡-诺森德-燃烧军团-死亡熔炉","卡拉赞":"卡拉赞-苏塔恩","山丘之王":"山丘之王-拉文霍德","加基森":"加基森-黑暗虚空","守护之剑":"守护之剑-瑞文戴尔","伊萨里奥斯":"伊萨里奥斯-祖阿曼","圣火神殿":"圣火神殿-桑德兰","塞拉赞恩":"塞拉赞恩-太阳之井","卡扎克":"卡扎克-爱斯特纳-戈古纳斯-巴纳扎尔","石锤":"石锤-范达尔鹿盔","卡珊德拉":"卡珊德拉-暗影之月","大漩涡":"大漩涡-风暴之怒","埃克索图斯":"埃克索图斯-血牙魔王","希雷诺斯":"希雷诺斯-芬里斯-烈焰荆棘","奥妮克希亚":"奥妮克希亚-海加尔-纳克萨玛斯","森金":"森金-沙怒-血羽","毁灭之锤":"毁灭之锤-兰娜瑟尔","迦玛兰":"迦玛兰-霜狼","安加萨":"安加萨-莱索恩","格雷迈恩":"格雷迈恩-黑手军团-瓦丝琪","夺灵者":"夺灵者-战歌-奥斯里安","阿拉索":"阿拉索-阿迦玛甘","布莱克摩":"布莱克摩-灰谷","巴尔古恩":"巴尔古恩-托尔巴拉德","玛里苟斯":"玛里苟斯-艾萨拉","破碎岭":"破碎岭-祖尔金","塞拉摩":"塞拉摩-暗影迷宫-麦姆","伊森德雷":"伊森德雷-达斯雷玛-库尔提拉斯-雷霆之怒","塔纳利斯":"塔纳利斯-巴瑟拉斯-密林游侠","玛多兰":"玛多兰-银月-羽月-耳语海岸","古尔丹":"古尔丹-血顶","古拉巴什":"古拉巴什-安戈洛-深渊之喉-德拉诺","巨龙之吼":"巨龙之吼-黑石尖塔","地狱之石":"地狱之石-火焰之树-耐奥祖","厄祖玛特":"厄祖玛特-奎尔萨拉斯","达尔坎":"达尔坎-鹰巢山","伊瑟拉":"伊瑟拉-艾森娜-月神殿-轻风之语","朵丹尼尔":"朵丹尼尔-蓝龙军团","冰霜之刃":"冰霜之刃-安格博达","石爪峰":"石爪峰-阿扎达斯","火烟之谷":"火烟之谷-玛诺洛斯-达纳斯","血吼":"血吼-黑暗之门","达克萨隆":"达克萨隆-阿纳克洛斯","地狱咆哮":"地狱咆哮-阿曼尼-奈法利安","白骨荒野":"白骨荒野-能源舰","基尔罗格":"基尔罗格-巫妖之王-迦顿","加兹鲁维":"加兹鲁维-奥金顿-哈兰","亚雷戈斯":"亚雷戈斯-银松森林","洛肯":"洛肯-海克泰尔","祖达克":"祖达克-阿尔萨斯","戈提克":"戈提克-雏龙之翼"},
	itemBonusMap : {525:"1阶",526:"2阶",527:"3阶",593:"4阶",617:"5阶",618:"6阶",558:"2阶",559:"3阶",594:"4阶",619:"5阶",620:"6阶",560:"普通 战火",561:"战火",562:"战火",563:"普通 带插槽",564:"带插槽",565:"带插槽",566:"英雄级别",567:"史诗"},
	getConnectedRealms : function(realm) {
		var tmpRealm = this.connectedRealm[realm];
		return tmpRealm === undefined ? realm : tmpRealm;
	}, 
	getBonusDesc : function(bonusList) {
		var desc = "";
		if(bonusList === "") {
			return "普通";
		}
		var bonus = bonusList.split(",");		
		for(var i in bonus) {			
			desc += this.itemBonusMap[bonus[i]]+" ";
		}
		return desc;
	},
	getGold : function (value) {
		var v = toDecimal(value/10000);
		return v > 10 ? parseInt(v) : v;
	},
	getResult : function (data) {
		data.sort(function(a, b) {	
			return a - b;
		});
		var range = 0.8;		
		var rangeStart = data.length * ((1 - range) / 2);
		var rangeEnd = data.length*(1 - (1 - range) /2);
		var min = 0;
		var max = 0;
		var avg = 0;
		var count = 0;
		var sum = 0;
		var rangeData = [];
		for(var i = 0; i < data.length - 1; i++){
			var value = data[i];
			if(min === 0 || min > value) {
				min = value;
			}
			if(i > rangeStart && i < rangeEnd) {				
				if(max === 0 || max < value) {
					max = value;
				}
				rangeData[count++] = value;
				sum += value;
			}
		}
		avg = sum / count;
		var result = {"min" : min, "max" : max, "avg" : avg};
		if(avg * 2 < max) {
			result2 = this.getResult(rangeData);
			result.max = result2.max;
			result.avg = result2.avg;
		}
		return result;
	}
};
var BnadeLocalStorage = {
	isLSSupport : function() {
		if (typeof(Storage) !== "undefined"){
			return true;
		} else {
			alert("你的浏览器太老不支持本地存储,建议升级浏览器");
			return false;
		}		
	},
	lsItems : { 
		realm : {
			key : "queryRealms",
			countKey : "queryRealmCount",
			count : 5,
			elementId : "realmQueryList",
			selectElementId : "realmSelectList",
			title : "服务器",
			inputId : "realm",
			buttonId : "queryBtn" 
		}, item : {
			key : "queryItems",
			countKey : "queryItemCount",
			count : 15,
			elementId : "itemQueryList",
			selectElementId : "itemSelectList",
			title : "物品名",
			inputId : "itemName",
			buttonId : "queryBtn" 
		}, pet : {
			key : "queryPets",
			countKey : "queryPetCount",
			count : 15,
			elementId : "petQueryList",
			selectElementId : "petSelectList",
			title : "宠物名",
			inputId : "petName",
			buttonId : "queryBtn" 
		}
	},
	refresh : function(){
		if (this.isLSSupport()) {
			for(var i in this.lsItems) {
				var item = this.lsItems[i];
				var jsonArr=JSON.parse(localStorage.getItem(item.key));
				if (jsonArr !== null) {
					var element = $("#"+item.elementId);
					if (isDomExist(element)) {						
						element.html("<li class='active'><a href='javascript:void(0)'>" + item.title + "</a></li>");
						for(var j in jsonArr){
							if (jsonArr[j] !== null) {
								var id = item.key + j;
								element.append("<li><a href='javascript:void(0)' id='"+id+"' btnId='"+item.buttonId+"' inputId='"+item.inputId+"'>"+jsonArr[j]+"</a></li>");
								$("#" + id).click(function() {
									$("#" + $(this).attr("inputId")).val($(this).html());																		
									var buttonElement = $("#"+$(this).attr("btnId"));											
									if (isDomExist(buttonElement)) {
										buttonElement.click();
									}										
								});								
							}
						}
					}
					var selectElement = $("#"+item.selectElementId);
					if (isDomExist(selectElement)) {
						selectElement.empty();
						for(var k in jsonArr){
							if (jsonArr[k] !== null) {
								var selectId = item.selectElementId + k;
								selectElement.append("<li><a href='javascript:void(0)' id='"+selectId+"' inputId='"+item.inputId+"'>"+jsonArr[k]+"</a></li>");
								$("#"+selectId).click(function() {
									$("#"+$(this).attr("inputId")).val($(this).html());
								});								
							}
						}
					}
				} else {
					localStorage.setItem(item.countKey, item.count);
					localStorage.setItem(item.key,JSON.stringify(new Array(item.count)));
				}
			}
		}
	},
	addItem : function(key, value) {
		if(this.isLSSupport()){				
			var obj=JSON.parse(localStorage.getItem(key));
			if (obj !== null && obj.length > 0) {
				if(obj[0]!=value){
					var tmpItem=obj[0];
					obj[0]=value;
					for(var i in obj){						
						if (i !== "0") {
							if(obj[i]==value){
								obj[i]=tmpItem;
								break;
							}else{
								var tmpItem2=obj[i];
								obj[i]=tmpItem;
								tmpItem=tmpItem2;
							}
						}
					}
					localStorage.setItem(key,JSON.stringify(obj));
				}
			}
			this.refresh();
		}
	}
};
$(document).ready(function() {
	BnadeLocalStorage.refresh();
	// 注册回车事件
	$("body").keydown(function(evt) {			
        if(evt.which === 13) {  
        	if (isDomExist($("#queryBtn"))) {
        		$("#queryBtn").click();
        	}
		}	
	});	
});



		