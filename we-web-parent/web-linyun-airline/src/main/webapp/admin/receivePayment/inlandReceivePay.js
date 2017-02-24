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
		            {"data": " ", "bSortable": false},
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
		            {"data": "收款单位", "bSortable": false},
		            {"data": "orderstatus", "bSortable": false,
		            	render: function(data, type, row, meta) {
		            		var s = '';
		            		if(row.orderstatus == '1'){
		            			s = '已付款';
		            		}else{
		            			s = '付款中';
		            		}
		            		return s;
		            	}
		            },
		            {"data": "drawer", "bSortable": false}

		            ]

	});
}

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
