var inlandCrossTable;
function initDatatable() {
	inlandCrossTable = $('#inlandCrossTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
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
                    		var result = '<ul>';
                    		$.each(row.pnrinfo, function(name, value) {
                    			if(value){
                    				result += '<li style="list-style:none;">'+value.pnr+'</li>';
                    			}
                    		});
                    		result += '</ul>';
                    	}
                    },
                    {"data": "date", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.leavedate+'</li>';
                    		});
                    		result += '</ul>';
                    	}
                    },
                    {"data": "airnum", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.ailinenum+'</li>';
                    		});
                    		result += '</ul>';
                    	}
                    },
                    {"data": "airsag", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.leavecity+"-"+value.arrvicity+'</li>';
                    		});
                    		result += '</ul>';
                    	}
                    },
                    {"data": "airtime", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.leavetime+"-"+value.arrivetime+'</li>';
                    		});
                    		result += '</ul>';
                    	}
                    },
                    {"data": "salesprice", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.price+'</li>';
                    		});
                    		result += '</ul>';
                    	}
                    },
                    {"data": "receivable", "bSortable": false},
                    {"data": "personcount", "bSortable": false},
                    {"data": "ordersstatus", "bSortable": false},
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

function edit(id,status){ 
	var url = BASE_PATH;
	if(status == 1){
		url += '/admin/inland/queryDetail.html?id='+id;
	}else{
		url = '';
	}
	window.open(url);
	/*layer.open({
        type: 2,
        title:false,
        skin: false, //加上边框
        closeBtn:true,//默认 右上角关闭按钮 是否显示
        shadeClose:true,
        area: ['100%', '100%'],
        content: url
      });*/
}