//国际 收款 弹框
var interRecTable;
function initRecDataTable() {
	interRecTable = $('#interRecTable').DataTable({
		"searching":false,
		"bLengthChange": false,
		"processing": true,
		"serverSide": true,
		"stripeClasses": [ 'strip1','strip2' ],
		"language": {
			"url": BASE_PATH + "/public/plugins/datatables/cn.json"
		},
		"ajax": {
			"url": BASE_PATH + "/admin/receivePay/inter/interRecList.html",
			"type": "post",
			"data": function (d) {

			}
		},
		"columns": [
		            {"data": "ordersnum", "bSortable": false,
		            	render:function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value){
		            				result += '<li style="list-style:none;">'+value.ordersnum+'</li>';
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "leavesdate,", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.leavesdate != undefined ){
		            				var date = value.leavesdate;
		            				var MM = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEPT', 'OCT', 'NOV', 'DEC'];
		            				var week = ['MO','TU','WE','TH','FR','SA','SU'];
		            				var ldate = new Date(date);
		            				var dateFormat = week[ldate.getUTCDay()]+ldate.getDate() + MM[ldate.getMonth()];
		            				result += '<li style="list-style:none;">'+dateFormat+'</li>';
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "peoplecount", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value){
		            				var pCount = value.peoplecount;
		            				if(pCount == null || pCount == undefined || pCount==""){
		            					pCount = '';
		            				}else{
		            					result += '<li style="list-style:none;">'+pCount+'</li>';
		            				}
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "incometotal", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.incometotal!=undefined){
		            				result += '<li style="list-style:none;">'+value.incometotal+'</li>';
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "sum", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var sum = row.sum;
		            		if(null == sum || ""== sum){
		            			return "";
		            		}
		            		return sum;
		            	}
		            },
		            {"data": "shortname", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '';
		            		$.each(row.orders, function(name, value) {
		            			if(value && value.shortname!=undefined){
		            				result = value.shortname;
		            			}
		            		});
		            		return result;
		            	}
		            },
		            {"data": "username", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var username = row.username;
		            		if(null == username || ""== username){
		            			return "";
		            		}
		            		return username;
		            	}
		            },
		            {"data": "orderstatus", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var s = row.status;
		            		if(s == 1){
		            			s = '收款中';
		            		}
		            		if(s == 2 ){
		            			s = '已收款';
		            		}
		            		return s;
		            	}
		            },
		            {"data": "notes", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var notes = row.notes;
		            		if(null == notes || ""== notes){
		            			return "";
		            		}
		            		return notes;
		            	}
		            }

		            ],
		            columnDefs: [{
		            	//   指定第一列，从0开始，0表示第一列，1表示第二列……
		            	targets: 4,
		            	render: function(data, type, row, meta) {
		            		/*var modify = '<a style="cursor:pointer;" onclick="editUser('+row.userid+');">编辑</a>';
                    return modify;*/
		            		return "";
		            	}
		            }]
	});
}

//datatable行点击事件
$("tbody",$('#interRecTable')).on("click","tr",function(event){
	//获取当前行的数据
	var row = interRecTable.row($(this).closest('tr')).data();
	confirmReceive(row.id);
});

//確認收款
$("#confirmRecClick").click(function(){
	var recStatus = $("#interRecSelect option:selected",window.parent.document).val();
	if(recStatus==1){
		$.ajax({
			type : 'POST',
			data : {
				id:$("#recIds").val()
			},
			async: false,
			url: BASE_PATH + '/admin/receivePay/inter/saveInterRec.html',
			success : function(data) {
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				parent.layer.msg("收款成功", "", 2000);
				parent.interRecTable.ajax.reload(
						function(json){
							autoHighLoad($('#interRecTable'));
						}
				);
				$("#recIds").val("");
			},
			error: function () {
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				parent.layer.msg("收款失败", "", 2000);
				$("#recIds").val("");
			}
		});
	}else{
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
		parent.layer.msg("该订单已收款，请勿重复收款", "", 2000);
	}
	
});

//关闭确认付款窗口
$("#closeRecWindow").click(function(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
});

//打开确认付款页面
function confirmReceive(id){
	layer.open({
		type: 2,
		title:false,
		skin: false, //加上边框
		closeBtn:false,//默认 右上角关闭按钮 是否显示
		shadeClose:true,
		area: ['850px', '650px'],
		content: ['confirmReceive.html?interRecId='+ id,'no'],
	});
}

//付款检索
$("#interRecSelect").change(function(){
	$("#interRecSearchBtn").click();
});


//收款  搜索按钮
$("#interRecSearchBtn").on('click', function () {
	var orderStatus = $("#interRecSelect").val();
	var inlandRecBeginDate = $("#interRecBeginDate").val();
	var inlandRecEndDate = $("#interRecEndDate").val();
	var inlandRecInput = $("#interRecInput").val();
    var param = {
		"orderStatus":orderStatus,
		"leaveBeginDate":inlandRecBeginDate,
		"leaveEndDate":inlandRecEndDate,
		"name": inlandRecInput
	};
	interRecTable.settings()[0].ajax.data = param;
	interRecTable.ajax.reload(
			function(json){
				autoHighLoad($('#interRecTable'));
			}
	);
});

//国际Tab  一订、二订、三订、全款状态 js-----------------------------------------------
$(".paymentUl li").click(function(){
	$(this).addClass("btnStyle").siblings().removeClass('btnStyle');
	var bookId = $(this).attr("id");
	alert(bookId);
	var orderStatus = $("#interRecSelect").val();
	var param = {
			"orderStatus":orderStatus,
			"interOrderStatus":bookId
	};
	interRecTable.settings()[0].ajax.data = param;
	interRecTable.ajax.reload(
			function(json){
				autoHighLoad($('#interRecTable'));
			}		
	);
});

//回车搜索
function recOnkeyEnter(){
	 if(event.keyCode==13){
		 $("#interRecSearchBtn").click();
	 }
}