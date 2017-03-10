var inlandCrossTable;
function initDatatable() {
	inlandCrossTable = $('#inlandCrossTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "initComplete": function( settings, json ) {
				        	$(this).find('tr').each(function () {//全部 table 自适应高度      
				        	       $(this).children('td').each(function(){
				        	          var liLength = $(this).children('ul').find("li").length;
				        	          if(liLength==1){
				        	            $(this).children('ul').find("li").addClass('eq');
				        	          }else if(liLength==2){
				        	            $(this).children('ul').find("li").eq(1).addClass('eq1');
				        	            $(this).children('ul').find("li").eq(0).addClass('eq0');
				        	          }else if(liLength==2){
				        	            $(this).children('ul').find("li").eq(2).addClass('eq2');
				        	          }
				        	       });
				        	});
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
                    			if(value){
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
                    			result += '<li style="list-style:none;">'+value.leavetdate+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "airnum", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.ailinenum+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "airsag", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.customerinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.leavecity+"-"+value.arrivecity+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "airtime", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.leavetime+"-"+value.arrivetime+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "salesprice", "bSortable": false,
                    	render:function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.price+'</li>';
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
                    		if(row.ordersstatus == 1){
                    			result = '查询';
                    		}else if(row.ordersstatus == 2){
                    			result = '预定';
                    		}else if(row.ordersstatus == 5){
                    			result = '关闭';
                    		}
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
		inlandCrossTable.ajax.reload();
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
		url = '/admin/inland/bookingDetail.html?id='+id;
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