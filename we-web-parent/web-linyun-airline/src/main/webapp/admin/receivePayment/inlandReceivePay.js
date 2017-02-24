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
			"url": BASE_PATH + "/admin/inland/inlandPayList.html",
			"type": "post",
			"data": function (d) {

			}
		},
		"columns": [
		            {"data": "ordersnum", "bSortable": false}
		            ]

	});
}




//会计   收款datatable
var inlandRecTable;
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
			"url": url,
			"type": "post",
			"data": function (data) {

			}
		},
		"columns": [
		            {"data": "ordersnum", "bSortable": false}
		            ]

	});
}


/*清除 内陆跨海 收款的   检索项*/
$('#inlandRecClearBtn').click(function(){
	clearSearchTxt("inlandRecSelect", "inlandRecBeginDate", "inlandRecEndDate", "inlandRecInput");
});

/*清除 内陆跨海 付款的   检索项*/
$('#inlandPayClearBtn').click(function(){
	clearSearchTxt("inlandPaySelect", "inlandPayBeginDate", "inlandPayEndDate", "inlandPayInput");
});
