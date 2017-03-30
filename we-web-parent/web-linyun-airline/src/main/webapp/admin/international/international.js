var internationalTable;
function initInternationalTable(){
	internationalTable = $('#internationalTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "stripeClasses": [ 'strip1','strip2' ],
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": BASE_PATH + "/admin/international/internationalListData.html",
            "type": "post",
            "data": function (d) {
            }
        },
        "fnDrawCallback" : function(){
        	var api = this.api();
        	var startIndex= api.context[0]._iDisplayStart;
   	       　　  api.column(0).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML = startIndex + i + 1; 
   	       　　});
      	},
        "columns": [
                    {"data": "xuhao", "bSortable": false},
                    {"data": "ordersnum", "bSortable": false},
                    {"data": "leavesdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		var MM = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEPT', 'OCT', 'NOV', 'DEC'];
                    		var week = ['MO','TU','WE','TH','FR','SA','SU'];
                    		$.each(row.airinfo, function(name, value) {
                    			var leavedate = new Date(value.leavedate);
                    			result += '<li style="list-style:none;">'+(week[leavedate.getUTCDay()]+leavedate.getDate() + MM[leavedate.getMonth()])+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "leavescity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+value.ailinenum+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "backsdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+(value.leavecity+'/'+value.arrvicity)+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}
                    },
                    {"data": "backscity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>';
                    		$.each(row.airinfo, function(name, value) {
                    			result += '<li style="list-style:none;">'+(value.leavetime+'/'+value.arrivetime)+'</li>';
                    		});
                    		result += '</ul>';
                    		return result;
                    	}	
                    },
                    {"data": "peoplecount", "bSortable": false},
                    {"data": "foc", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '否';
                    		if(row.foc == 1){
                    			result = '16/1';
                    		}
                    		return result;
                    	}
                    },
                    {"data": "dayscount", "bSortable": false},
                    {"data": "ordersstatus", "bSortable": false,
                    	render:function(data, type, row, meta) {
                      		var result = '';
                      		$.each(row.orderstatusenum, function(name, value) {
                      			if(row.ordersstatus == name){
                      				result = value;
                      			}
                      		});
                      		return result;
                      	  }
                    },
                    {"data": "travelname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result='';
                    		if(row.travelname){
                    			result = row.travelname;
                    		}
                    		return result;
                    	}
                    },
                    {"data": "unioncity", "bSortable": false}
            ],
        columnDefs: [{
    		targets: 0,
            render: function(data, type, row, meta) {
                return null
            }
    	}]
    });
}
initInternationalTable();
//加载列表数据
function loadDataTable(status){
	var param = {
			ordersstatus:status
	};
	internationalTable.settings()[0].ajax.data = param;
	internationalTable.ajax.reload();
	
}
//点击行跳转到详情页
$("tbody",$('#internationalTable')).on("dblclick","tr",function(event) {
	var item = internationalTable.row($(this).closest('tr')).data();
	var url = BASE_PATH + '/admin/international/internationalDetail.html?orderid='+item.ordernumber;
	window.open(url);
});