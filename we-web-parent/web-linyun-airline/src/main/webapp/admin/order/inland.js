var inlandCrossTable;
function initDatatable() {
	inlandCrossTable = $('#inlandCrossTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "initComplete": function( settings, json ) {
        	autoHighLoad($(this));
          },
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": BASE_PATH + "/admin/inland/listData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "ordersnum", "bSortable": false},
                    {"data": "pnr", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul id="tableUl"> ';
                    		$.each(row.pnrinfo, function(name, value) {
                    			if(value && value.pNR != undefined){
                    				result += '<li style="list-style:none;">'+value.pNR+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "date", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.customerinfo, function(name, value) {
                    			if(value && value.leavetdate != undefined){
                    				result += '<li style="list-style:none;">'+value.leavetdate+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "airnum", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			if(value && value.ailinenum != undefined){
                    				result += '<li style="list-style:none;">'+value.ailinenum+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "airsag", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.customerinfo, function(name, value) {
                    			if(value){
                    				result += '<li style="list-style:none;">';
                    				if(value.leavecity != undefined){
                    					result += value.leavecity;
                    				}
                    				result += '-';
                    				if(value.arrivecity != undefined){
                    					result += value.arrivecity;
                    				}
                    				result += '</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "airtime", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			if(value){
                    				result += '<li style="list-style:none;">';
                    				if(value.leavetime != undefined){
                    					result += value.leavetime;
                    				}
                    				result += '-';
                    				if(value.arrivetime != undefined){
                    					result += value.arrivetime;
                    				}
                    				result += '</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "salesprice", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			if(value && value.price != undefined){
                    				result += '<li style="list-style:none;">'+value.price+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    		return result; 
                    	}
                    },
                    {"data": "receivable", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.receivable){
                    			result = row.receivable;
                    		}
                    		return result; 
                    	}
                    },
                    {"data": "personcount", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.personcount){
                    			result = row.personcount;
                    		}
                    		return result; 
                    	}
                    },
                    {"data": "ordersstatus", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '';
                    		$.ajax({ 
                    			type: 'POST', 
                    			data: {status:row.ordersstatus}, 
                    			async:false,
                    			url: BASE_PATH + '/admin/inland/formatOrderStatus.html',
                                success: function (data) { 
                                	result = data;
                                },
                                error: function (xhr) {
                                } 
                            });
                    		return result; 
                    	}
                    },
                    {"data": "username", "bSortable": false},
                    {"data": "telephone", "bSortable": false},
                    {"data": "action", "bSortable": false}
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 12,
            render: function(data, type, row, meta) {
                return '<a style="cursor:pointer;" onclick="edit('+row.id+','+row.ordersstatus+');">编辑</a>'
            }
        }]
    });
}

	function loadDataTable(status){
		var param = {
				ordersstatus:status
		};
		inlandCrossTable.settings()[0].ajax.data = param;
		inlandCrossTable.ajax.reload(function(json){
			autoHighLoad($('#inlandCrossTable'));
		});
	}
	$("#searchBtn").on('click', function () {
		var companyName = $("#companyName").val();
		var comType = $('#comType').val();
	    var param = {
	        "companyName": companyName,
			"comType" : comType
	    };
	    inlandCrossTable.settings()[0].ajax.data = param;
		inlandCrossTable.ajax.reload();
	});

$(function () {
    initDatatable();
});
$("tbody",$('#inlandCrossTable')).on("click","tr",function(event) {
	var item = inlandCrossTable.row($(this).closest('tr')).data();
	var url = BASE_PATH;
	if(item.ordersstatus == 1){
		url += '/admin/inland/queryDetail.html?id='+item.id;
	}else{
		url = '/admin/inland/bookingDetail.html?id='+item.id;
	}
	window.open(url);
});
function edit(id,status){ 
	var url = BASE_PATH;
	if(status == 1){
		url += '/admin/inland/queryDetail.html?id='+id;
	}else{
		url = '/admin/inland/bookingDetail.html?id='+id;
	}
	window.open(url);
}
