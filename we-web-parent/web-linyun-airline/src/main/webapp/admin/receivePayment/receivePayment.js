$('#payTable').dataTable({//内陆跨海 收款 table
	"paging": true,
	"lengthChange": false,
	"searching": false,
	"ordering": true,
	"info": true,
	"autoWidth": false,
	"columnDefs": [
	               {"sWidth": "11.33%","aTargets": [0] },
	               {"sWidth": "8.33%","aTargets": [1] },
	               {"sWidth": "4.33%","aTargets": [2] },
	               {"sWidth": "6.33%","aTargets": [3] },
	               {"sWidth": "5.33%","aTargets": [4] },
	               {"sWidth": "15.33%","aTargets": [5] },
	               {"sWidth": "5.33%","aTargets": [6] },
	               {"sWidth": "5.33%","aTargets": [7] },
	               {"sWidth": "8.33%","aTargets": [8] }],
	               "oLanguage": {                          //汉化   
	            	   "sSearch": ["one","two"],  
	            	   "oPaginate":{   
	            		   "sFirst": "首页",   
	            		   "sPrevious": "上一页",   
	            		   "sNext": "下一页",   
	            		   "sLast": "尾页"  
	            	   }   
	               }    
});

$('#receiveTable').dataTable({//内陆跨海 付款 table
	"paging": true,
	"lengthChange": false,
	"searching": false,
	"ordering": true,
	"info": true,
	"autoWidth": false,
	"columnDefs": [
	               {"sWidth": "4.33%","aTargets": [0] },
	               {"sWidth": "8.33%","aTargets": [1] },
	               {"sWidth": "5.33%","aTargets": [2] },
	               {"sWidth": "5.33%","aTargets": [3] },
	               {"sWidth": "4.33%","aTargets": [4] },
	               {"sWidth": "6.33%","aTargets": [5] },
	               {"sWidth": "5.33%","aTargets": [6] },
	               {"sWidth": "12.33%","aTargets": [7] },
	               {"sWidth": "5.33%","aTargets": [8] },
	               {"sWidth": "5.33%","aTargets": [9] }],
	               "oLanguage": {                          //汉化   
	            	   "sSearch": ["one","two"],  
	            	   "oPaginate":{   
	            		   "sFirst": "首页",   
	            		   "sPrevious": "上一页",   
	            		   "sNext": "下一页",   
	            		   "sLast": "尾页"  
	            	   }   
	               }    
});

$('#GJpayTable').dataTable({//国际 收款 table
	"paging": true,
	"lengthChange": false,
	"searching": false,
	"ordering": true,
	"info": true,
	"autoWidth": false,
	"columnDefs": [
	               {"sWidth": "11.33%","aTargets": [0] },
	               {"sWidth": "8.33%","aTargets": [1] },
	               {"sWidth": "4.33%","aTargets": [2] },
	               {"sWidth": "6.33%","aTargets": [3] },
	               {"sWidth": "5.33%","aTargets": [4] },
	               {"sWidth": "15.33%","aTargets": [5] },
	               {"sWidth": "5.33%","aTargets": [6] },
	               {"sWidth": "5.33%","aTargets": [7] },
	               {"sWidth": "8.33%","aTargets": [8] }],
	               "oLanguage": {                          //汉化   
	            	   "sSearch": ["one","two"],  
	            	   "oPaginate":{   
	            		   "sFirst": "首页",   
	            		   "sPrevious": "上一页",   
	            		   "sNext": "下一页",   
	            		   "sLast": "尾页"  
	            	   }   
	               }    
});

$('#GJreceiveTable').dataTable({//国际 付款 table
	"paging": true,
	"lengthChange": false,
	"searching": false,
	"ordering": true,
	"info": true,
	"autoWidth": false,
	"columnDefs": [
	               {"sWidth": "4.33%","aTargets": [0] },
	               {"sWidth": "8.33%","aTargets": [1] },
	               {"sWidth": "5.33%","aTargets": [2] },
	               {"sWidth": "5.33%","aTargets": [3] },
	               {"sWidth": "4.33%","aTargets": [4] },
	               {"sWidth": "6.33%","aTargets": [5] },
	               {"sWidth": "5.33%","aTargets": [6] },
	               {"sWidth": "12.33%","aTargets": [7] },
	               {"sWidth": "5.33%","aTargets": [8] },
	               {"sWidth": "5.33%","aTargets": [9] }],
	               "oLanguage": {                          //汉化   
	            	   "sSearch": ["one","two"],  
	            	   "oPaginate":{   
	            		   "sFirst": "首页",   
	            		   "sPrevious": "上一页",   
	            		   "sNext": "下一页",   
	            		   "sLast": "尾页"  
	            	   }   
	               }    
});

$(".TabUL li").click(function(){
	$(this).addClass("tabStyle").siblings().removeClass('tabStyle');
});


//内陆跨海 table下checkbox全选 点击操作
$(".checkTh input").click(function() {
	if (this.checked == true){
		$(".checkTd input").each(function() {
			this.checked = true;
		});
	}else{
		$(".checkTd input").each(function() {
			this.checked = false;
		});
	}
});

//国际 table下checkbox全选 点击操作
$(".checkTh1 input").click(function() {
	if (this.checked == true){
		$(".checkTd1 input").each(function() {
			this.checked = true;
		});
	}else{
		$(".checkTd1 input").each(function() {
			this.checked = false;
		});
	}
});
//国际Tab  一订/二订..状态 js-----------------------------------------------
$(".paymentUl li").click(function(){
	$(this).addClass("btnStyle").siblings().removeClass('btnStyle');
});

//清空搜索项函数
function clearSearchTxt(selectId, beginDateId, endDateId, inputId){
	$("#"+selectId).val(0);
	$("#"+beginDateId).val("");
	$("#"+endDateId).val("");
	$("#"+inputId).val("");
}
