$(document).ready(function() {
	!function(){
		$('#auctionQuantityContainer').html("加载数据,请稍等...");
		$.get("wow/auction/quantity", function(data) {
       		if (data.code === 201) {
       			$('#auctionQuantityContainer').html("获取数据失败:" + data.errorMessage);
       		} else {
       			var tblHtml = "<table class='table table-striped'><thead><tr><th>#</th><th>服务器</th><th>拍卖总数量</th><th>拍卖行玩家数</th><th>物品种类</th><th>更新时间</th></tr></thead><tbody>";
       			for (var i in data) {
       				var realmAuction = data[i];
       				var realm = Bnade.getConnectedRealms(realmAuction.realm);    				
       				tblHtml += "<tr><td>"+(parseInt(i)+1)+"</td><td>"+realm+"</td><td>"+realmAuction.quantity+"</td><td>"+realmAuction.ownerQuantity+"</td><td>"+realmAuction.itemQuantity+"</td><td>"+new Date(realmAuction.lastUpdateTime).format("MM-dd hh:mm:ss")+"</td></tr>";
       			}
       			tblHtml += "</tbody></table>";
       			$('#auctionQuantityContainer').html(tblHtml);
       		}
       	}).fail(function() {
			$("#auctionQuantityContainer").html("数据加载出错");
	    }); 
	}();
});