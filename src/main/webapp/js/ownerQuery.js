$(document).ready(function(){function t(){$("#ownerItemsContainer").html("正在查询,请稍等...");var t=$("#realm").val(),e=$("#owner").val();""===t||""===e?$("#ownerItemsContainer").html("服务器或玩家名不能为空"):$.get("wow/auction/realm/"+t+"/owner/"+e,function(t){if(201===t.code)alert("获取数据失败:"+t.errorMessage);else if(0===t.length)$("#ownerItemsContainer").html("找不到玩家拍卖的物品");else{var e="<table class='table table-striped'><thead><tr><th>#</th><th>ID</th><th>物品</th><th>竞价</th><th>一口价</th><th>数量</th><th>剩余时间</th></tr></thead><tbody>";for(var n in t){var r=t[n],a=Bnade.getGold(r.bid),l=Bnade.getGold(r.buyout);e+="<tr><td>"+(parseInt(n)+1)+"</td><td>"+r.item+"</td><td>"+r.name+"</td><td>"+a+"</td><td>"+l+"</td><td>"+r.quantity+"</td><td>"+leftTimeMap[r.timeLeft]+"</td></tr>"}e+="</tbody></table>",$("#ownerItemsContainer").html(e)}}).fail(function(){$("#ownerItemsContainer").html("数据加载出错")})}function e(){var t=getUrlParam("realm"),e=getUrlParam("owner");null!==e&&""!==e&&($("#realm").val(t),$("#owner").val(e),$("#queryBtn").click())}$("#queryBtn").click(function(){t()}),!function(){e()}()});