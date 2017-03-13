//内陆跨海 收款 弹框
var internationalRecTable;
function initRecDataTable() {
	internationalRecTable = $('#internationalRecTable').DataTable({
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
		            {"data": "leavedate", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value){
		            				var date = value.leavedate;
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
		            {"data": "personcount", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var result = '<ul> ';
		            		$.each(row.orders, function(name, value) {
		            			if(value){
		            				var pCount = value.personcount;
		            				if(pCount == null || pCount == undefined || pCount==""){
		            					pCount = '';
		            				}
		            				result += '<li style="list-style:none;">'+pCount+'</li>';
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
		            			if(value){
		            				result += '<li style="list-style:none;">'+value.incometotal+'</li>';
		            			}else{
		            				result += '<li style="list-style:none;"> </li>';
		            			}
		            		});
		            		result += '</ul>';
		            		return result;
		            	}
		            },
		            {"data": "sum", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		return '<a href="javascript:confirmReceive('+row.recid+');">'+row.sum+'</a>';
		            	}
		            },
		            {"data": "shortname", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		return '<a href="javascript:confirmReceive('+row.recid+');">'+row.shortname+'</a>';
		            	}
		            },
		            {"data": "username", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var username = row.username;
		            		if(null == username || ""== username){
		            			return "";
		            		}
		            		return '<a href="javascript:confirmReceive('+row.recid+');">'+row.username+'</a>';
		            	}
		            },
		            {"data": "orderstatus", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var s = '';
		            		if(data == 0){
		            			s = '收款中';
		            		}
		            		if(data == 4 ){
		            			s = '已收款';
		            		}
		            		return '<a href="javascript:confirmReceive('+row.recid+');">'+s+'</a>';
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

//確認收款
$("#confirmRecClick").click(function(){
	$.ajax({
		type : 'POST',
		data : {
			id:$("#recIds").val()
		},
		async: false,
		url: BASE_PATH + '/admin/receivePay/inter/saveInternationalRec.html',
		success : function(data) {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.layer.msg("收款成功", "", 1000);
			parent.initRecDataTable.ajax.reload();
		},
		error: function () {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.layer.msg("收款失败", "", 1000);
		}
	});
});


//关闭确认付款窗口
$("#closeRecWindow").click(function(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
});


function confirmReceive(id){
	layer.open({
		type: 2,
		title:false,
		skin: false, //加上边框
		closeBtn:false,//默认 右上角关闭按钮 是否显示
		shadeClose:true,
		area: ['850px', '650px'],
		content: ['confirmReceive.html?inlandRecId='+ id,'no'],
	});
}

//付款检索
$("#internationalRecSelect").change(function(){
	$("#internationalRecSearchBtn").click();
});


//收付款  搜索按钮
$("#internationalRecSearchBtn").on('click', function () {
	var interOrderStatus = $(".paymentUl li[class='btnStyle']").attr("id");
	var orderStatus = $("#internationalRecSelect").val();
	var inlandRecBeginDate = $("#internationalRecBeginDate").val();
	var inlandRecEndDate = $("#internationalRecEndDate").val();
	var inlandRecInput = $("#internationalRecInput").val();
	    var param = {
					"interOrderStatus":interOrderStatus,
			        "orderStatus":orderStatus,
			        "leaveBeginDate":internationalRecBeginDate,
			        "leaveEndDate":internationalRecEndDate,
					"name": internationalRecInput
	};
	internationalRecTable.settings()[0].ajax.data = param;
	internationalRecTable.ajax.reload();
});


//国际Tab  一订、二订、三订、全款状态 js-----------------------------------------------
$(".paymentUl li").click(function(){
	$(this).addClass("btnStyle").siblings().removeClass('btnStyle');
	var bookId = $(this).attr("id");
	var orderStatus = $("#internationalRecSelect").val();
	var param = {
			"orderStatus":orderStatus,
			"interOrderStatus":bookId
	};
	internationalRecTable.settings()[0].ajax.data = param;
	internationalRecTable.ajax.reload();
});