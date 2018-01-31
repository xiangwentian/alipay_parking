$(function() {
	// 初始化分页插件，并指定初始值
	var page = new Paging();
	var pageSizeInit = 10;
	var pageNum = 1;
	//总数据条数
	var itemNumber = 0;
	
	$(document).ready(function(){
		//queryParking(1)
		itemNumber = 0;
		pageNum = 1;
		pageToolbar();
	});
	
	function reset(){
		$("#carNumber").val("");
		$("#inb").val("");
		$("#ine").val("");
		$("#outb").val("");
		$("#oute").val("");
	}
	
	function queryOrder(){
		
	}
	
	//分页控件控制
	function pageToolbar() {
		$('#pageToolbar').empty();
		$('#pageToolbar').Paging({
			pagesize: pageSizeInit,
			count: itemNumber == 0 ? 1 : itemNumber,
			toolbar: true,
			hash: true,
			changePagesize: function(ps) {
				pageSizeInit = ps;
				pageNum = 1;
				if(parseInt(itemNumber) > 0) {
					//TODO 调用请求数据的方法
					queryOrder();
				}
			},
			callback: function(a) {
				pageNum = a;
				if(parseInt(itemNumber) > 0) {
					//TODO 调用请求数据的方法
					queryOrder();
				}
			}
		});
	}

})