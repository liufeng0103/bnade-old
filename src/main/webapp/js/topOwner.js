$(document).ready(function(){function t(){var t=$("#realm").val();""!==t?($("#msg").html("正在查询,请稍等..."),a(t),e(t)):$("#msg").html("服务器名不能为空")}function a(t){var a="topAuc",e="wow/auction/top/aucquantity/realm/"+t;o(t,e,a)}function e(t){var a="topPrice",e="wow/auction/top/totalprice/realm/"+t;o(t,e,a)}function o(t,a,e){$.get(a,function(a){if(201===a.code)$("#msg").html("加载数据失败:"+a.errorMessage);else{BnadeLocalStorage.addItem(BnadeLocalStorage.lsItems.realm.key,t);var o="<table class='table table-striped'><thead><tr><th>#</th><th>玩家</th><th>拍卖数量</th><th>拍卖总价值</th></tr></thead><tbody>";for(var n in a){var r=a[n],l=Bnade.getGold(r.total);o+="<tr><td>"+(parseInt(n)+1)+"</td><td>"+r.owner+"</td><td>"+r.aucQuantity+"</td><td>"+l+"</td></tr>"}o+="</tbody></table>",$("#"+e).html(o)}$("#msg").html("")}).fail(function(){$("#msg").html("查询出错")})}$("#queryBtn").click(function(){t()})});