//内陆跨海 收款 弹框
var inlandRecTable;
function initRecDataTable() {
	inlandRecTable = $('#inlandRecTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
        	"url": BASE_PATH + "/admin/receivePay/inland/inlandRecList.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "ordersnum", "bSortable": false},
                    {"data": "leavedate", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var MM = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEPT', 'OCT', 'NOV', 'DEC'];
		            		var week = ['MO','TU','WE','TH','FR','SA','SU'];
		            		var ldate = new Date(data);
		            		var result = week[ldate.getUTCDay()]+ldate.getDate() + MM[ldate.getMonth()];
		            		return result;
		            	}
		            },
                    {"data": "personcount", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var personcount = row.personcount;
		            		if(null == personcount || ""== personcount){
		            			return "";
		            		}
		            		return personcount;
		            	}
                    },
                    {"data": "incometotal", "bSortable": false},
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
		            		if(data == '3'){
		            			s = '收款中';
		            		}else{
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


//内陆跨海收款  搜索按钮
$("#inlandRecSearchBtn").on('click', function () {
	var orderStatus = $("#inlandRecSelect").val();
	var inlandRecBeginDate = $("#inlandRecBeginDate").val();
	var inlandRecEndDate = $("#inlandRecEndDate").val();
	var inlandRecInput = $("#inlandRecInput").val();
    var param = {
		        "orderStatus":orderStatus,
		        "leaveBeginDate":inlandRecBeginDate,
		        "leaveEndDate":inlandRecEndDate,
				"name": inlandRecInput
		    };
    inlandRecTable.settings()[0].ajax.data = param;
	inlandRecTable.ajax.reload();
});