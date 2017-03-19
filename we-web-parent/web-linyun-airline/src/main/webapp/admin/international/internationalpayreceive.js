var shouFuKuanGatheringTable;
//初始化表格
initshouFuKuanGatheringTable();
function initshouFuKuanGatheringTable() {
	shouFuKuanGatheringTable = $('#shouFuKuanGatheringTable').DataTable({
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
          "url": BASE_PATH + "/admin/international/payreceive/listShouFuKuanData.html",
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
                  	render:function(data, type, row, meta) {
                  		return '';
                  	}
                  },
                  {"data": "personcount", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul>';
                  		$.each(row.orders, function(name, value) {
                  			if(value && value.personcount != undefined){
                  				result += '<li style="list-style:none;">'+value.personcount+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "incometotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul>';
                  		$.each(row.orders, function(name, value) {
                  			if(value && value.incometotal != undefined){
                  				result += '<li style="list-style:none;">'+value.incometotal+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "sum", "bSortable": false},
                  {"data": "customename", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.customename){
                    			result = row.customename;
                    		}
                    		return result;
                    	}
                  },
                  {"data": "username", "bSortable": false},
                  {"data": "status", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.receiveenum, function(name, value) {
                  			if(row.status == name){
                  				result = value;
                  			}
                  		});
                  		return result;
                  	  }
                  },{"data": "remark", "bSortable": false,
                	  render:function(data, type, row, meta) {
                		  return '';
                	  }
                  }
          ],
      columnDefs: [{
    	//   指定第一列，从0开始，0表示第一列，1表示第二列……
          targets: 9,
          render: function(data, type, row, meta) {
              return '<a style="cursor:pointer;" onclick="openInvoice('+row.id+','+row.invoiceid+');">开发票</a>'
          }
      }],
  });
}
//打开开发票页面
function openInvoice(id,invoiceid){
	if(invoiceid){
		layer.open({
	        type: 2,
	        title:false,
	        skin: false, //加上边框
	        closeBtn:false,//默认 右上角关闭按钮 是否显示
	        shadeClose:true,
	        area: ['987px', '620px'],
	        content: BASE_PATH + '/admin/international/payreceive/kaiInvoice.html?id='+invoiceid
	      });
	}else{
		layer.open({
			type: 2,
			title:false,
			skin: false, //加上边框
			closeBtn:false,//默认 右上角关闭按钮 是否显示
			shadeClose:true,
			area: ['987px', '620px'],
			content: BASE_PATH + '/admin/international/payreceive/openInvoice.html?id='+id
		});
	}
}
//付款表格
var shouFuKuanPayTable;
//初始化表格
initshouFuKuanPayTable();
function initshouFuKuanPayTable() {
	shouFuKuanPayTable = $('#shouFuKuanPayTable').DataTable({
	"searching":false,
	"bLengthChange": false,
    "processing": true,
    "serverSide": true,
    "stripeClasses": [ 'strip1','strip2' ],
    "language": {
        "url": BASE_PATH + "/public/plugins/datatables/cn.json"
    },
    "ajax": {
        "url": BASE_PATH + "/admin/international/payreceive/listFuKuanData.html",
        "type": "post",
        "data": function (d) {
        	
        }
    },
    "columns": [
                {"data": "ordersnum", "bSortable": false},
                {"data": "pnr", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.pnr && row.pnr != undefined){
                			result = row.pnr;
                		}
                		return result;
                	}
                },
                {"data": "personcount", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '<ul>';
                		$.each(row.orders, function(name, value) {
                			result += '<li style="list-style:none;">'+value.personcount+'</li>';
                		});
                		result += '</ul>';
                		return result;
                	}
                },
                {"data": "hangduan", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = row.leavecity + '/' + row.leavecity;
                		return result;
                	}
                },
                {"data": "leavetdate", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.leavetdate && row.leavetdate != undefined){
                			result = row.leavetdate;
                		}
                		return result;
                	}
                },
                {"data": "time", "bSortable": false,
                	render:function(data, type, row, meta) {
                		return '';
                	}
                },
                {"data": "customename", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.customename && row.customename != undefined){
                			result = row.customename;
                		}
                		return result;
                	}
                },
                {"data": "salesprice", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.salesprice && row.salesprice != undefined){
                			result = row.salesprice;
                		}
                		return result;
                	}
                },
                {"data": "peoplecount", "bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.peoplecount){
                  			result = row.peoplecount;
                  		}
                  		return result;
                  	}
                },
                {"data": "status", "bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		$.each(row.paystatusenum, function(name, value) {
                  			if(row.orderpnrstatus == name){
                  				result = value;
                  			}
                  		});
                  		return result;
                  	}
                },
                {"data": "username", "bSortable": false}
        ],
    columnDefs: [{
  	//   指定第一列，从0开始，0表示第一列，1表示第二列……
        targets: 11,
        render: function(data, type, row, meta) {
            return '<a style="cursor:pointer;" onclick="receiveInvoice('+row.id+','+row.invoiceid+');">收发票</a>'
        }
    }],
});
}
function shoufukuanPay(){
	shouFuKuanPayTable.ajax.reload();
}
function receiveInvoice(id,invoiceid){
	if(invoiceid){
		layer.open({
	        type: 2,
	        title:false,
	        skin: false, //加上边框
	        closeBtn:false,//默认 右上角关闭按钮 是否显示
	        shadeClose:true,
	        area: ['987px', '620px'],
	        content: BASE_PATH + '/admin/international/payreceive/shouInvoice.html?id='+invoiceid
	    });
	}else {
		layer.open({
			type: 2,
			title:false,
			skin: false, //加上边框
			closeBtn:false,//默认 右上角关闭按钮 是否显示
			shadeClose:true,
			area: ['987px', '620px'],
			content: BASE_PATH + '/admin/international/payreceive/receiveInvoice.html?id='+id
		});
	}
}