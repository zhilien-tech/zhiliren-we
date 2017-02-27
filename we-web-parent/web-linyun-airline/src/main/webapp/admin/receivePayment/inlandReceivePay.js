//会计   付款datatable
var inlandPayTable;
function initPayDataTable(){
	inlandPayTable = $("#inlandPayTable").DataTable({
		"searching":false,
		"lengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": BASE_PATH + "/admin/receivePay/inlandPayList.html",
			"type": "post",
			"data": function (d) {

			}
		},
		"columns": [
		            {"data": "id", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '';
		            		var hiddenValue = $('#checkedboxPayValue').val();
		            		var splits = hiddenValue.split(',');
		            		var flag = false;
		            		for(var i=0;i<splits.length;i++){
		            			if(splits[i] == data){
		            				flag = true;
		            			}
		            		}	
		            		if(flag){
		            			result = '<input type="checkbox"  class="checkBoxPayChild" checked="true" value="' + data + '" />';
		            		}else{
		            			result = '<input type="checkbox"  class="checkBoxPayChild" value="' + data + '" />';
		            		}
		            		return result;
		            	}
		            },
		            {"data": "ordernum", "bSortable": false},
		            {"data": "pnrnum", "bSortable": false},
		            {"data": "leavedate", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var MM = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEPT', 'OCT', 'NOV', 'DEC'];
		            		var week = ['MO','TU','WE','TH','FR','SA','SU'];
		            		var ldate = new Date(data);
		            		var result = week[ldate.getUTCDay()]+ldate.getDate() + MM[ldate.getMonth()];
		            		return result;
		            	}
		            },
		            {"data": "peoplecount", "bSortable": false},
		            {"data": "saleprice", "bSortable": false},
		            {"data": "currency", "bSortable": false},
		            {"data": "currency", "bSortable": false},
		            {"data": "orderstatus", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var s = '';
		            		if(data == '1'){
		            			s = '已付款';
		            		}else{
		            			s = '付款中';
		            		}
		            		return s;
		            	}
		            },
		            {"data": "drawer", "bSortable": false}

		            ],
		            "infoCallback": function (settings, start, end, max, total, pre) {
		            	var length = $(".checkBoxPayChild:checked").length;
		            	if(inlandPayTable.page.len() == length){
		            		$(".checkBoxPayAll").prop("checked", true);
		            	}else{
		            		$(".checkBoxPayAll").prop("checked", false);

		            	}
		            	return '显示第 '+start+' 至 '+end+' 条结果，共'+total+' 条 (每页显示 '+max+' 条)'
		            }

	});
}


//内陆跨海 付款 弹框
$('#inlandPayClick').click(function(){
	var ids = $('#checkedboxPayValue').val();
	var length = $(".checkBoxPayChild:checked").length;
	if(!ids){
		layer.msg("请至少选中一条记录", "", 2000);
	}else{

		layer.open({
			type: 2,
			title:false,
			skin: false, //加上边框
			closeBtn:false,//默认 右上角关闭按钮 是否显示
			shadeClose:true,
			area: ['850px', '650px'],
			content: ['confirmPay.html?inlandPayIds='+ ids,'no'],
		});

	}

});

//内路跨海付款 复选框 全选
$(".checkBoxPayAll").click(function () {
	var check = $(this).prop("checked");
	$(".checkBoxPayChild").prop("checked", check);
	//隐藏域的值
	var hiddenval = $('#checkedboxPayValue').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkBoxPayChild:checked").each(function(){
			var thisvals = $(this).val();
			var flag = false;
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisvals){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(hiddenval){
				if(!flag){
					hiddenval += ',' + thisvals;
				}
			}else{
				hiddenval = thisvals;
			}
		});
	}else{
		$(".checkBoxPayChild").each(function(){
			var thisval = $(this).val();
			var flag = false;
			var splits = hiddenval.split(',');
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisval){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(flag){
				var ids = [];
				for(var i=0;i<splits.length;i++){
					if(splits[i] != thisval){
						ids.push(splits[i]);
					}
				}
				ids = ids.join(',');
				hiddenval = ids;
			}
		});
	}
	$('#checkedboxPayValue').val(hiddenval);
});

//会计内陆跨海付款 复选框 单选
$(document).on('click', '.checkBoxPayChild', function(e) {
	var hiddenval = $('#checkedboxPayValue').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxPayValue').val(thisval);
		}else{
			$('#checkedboxPayValue').val(hiddenval+','+thisval);
		}
	}else{
		var splits = hiddenval.split(',');
		var flag = false;
		for(var i=0;i<splits.length;i++){
			if(splits[i] == thisval){
				flag = true;
			}
		}
		//如果存在则删掉当前值
		if(flag){
			var ids = [];
			for(var i=0;i<splits.length;i++){
				if(splits[i] != thisval){
					ids.push(splits[i]);
				}
			}
			ids = ids.join(',');
			$('#checkedboxPayValue').val(ids);
		}else{
			$('#checkedboxPayValue').val(hiddenval);
		}
	}
	var length = $(".checkBoxPayChild:checked").length;
	if(inlandPayTable.page.len() == length){
		$(".checkBoxPayAll").prop("checked", true);
	}else{
		$(".checkBoxPayAll").prop("checked", false);
	}
});

//内陆跨海付款 搜索按钮
$("#inlandPaySearchBtn").on('click', function () {
	var orderStatus = $("#inlandPaySelect").val();
	var inlandPayBeginDate = $("#inlandPayBeginDate").val();
	var inlandPayEndDate = $("#inlandPayEndDate").val();
	var inlandPayInput = $("#inlandPayInput").val();
	    var param = {
			        "orderStatus":orderStatus,
			        "leaveBeginDate":inlandPayBeginDate,
			        "leaveEndDate":inlandPayEndDate,
			"name": inlandPayInput
			    };
	    inlandPayTable.settings()[0].ajax.data = param;
	inlandPayTable.ajax.reload();
});

//内陆跨海 收款 弹框
/*$('.fuKuanBtn').click(function(){
	layer.open({
		type: 2,
		title:false,
		skin: false, //加上边框
		closeBtn:false,//默认 右上角关闭按钮 是否显示
		shadeClose:true,
		area: ['850px', '650px'],
		content: ['confirmReceive.html','no']
	});
});*/

$(function () {
	initPayDataTable();
});



//会计   收款datatable
/*var inlandRecTable;
function initRecDataTable(){
	inlandRecTable = $("#inlandRecTable").DataTable({
		"searching":false,
		"lengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": BASE_PATH + "/admin/receivePay/inlandRecList.html",
			"type": "post",
			"data": function (data) {

			}
		},
		"columns": [
		            {"data": "ordersnum", "bSortable": false}
		            ]

	});
}
 */

/*清除 内陆跨海 收款的   检索项*/
$('#inlandRecClearBtn').click(function(){
	clearSearchTxt("inlandRecSelect", "inlandRecBeginDate", "inlandRecEndDate", "inlandRecInput");
});

/*清除 内陆跨海 付款的   检索项*/
$('#inlandPayClearBtn').click(function(){
	clearSearchTxt("inlandPaySelect", "inlandPayBeginDate", "inlandPayEndDate", "inlandPayInput");
});
