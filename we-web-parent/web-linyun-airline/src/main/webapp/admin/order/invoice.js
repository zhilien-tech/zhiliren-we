var kaiInvoiceTable;
//初始化表格
initkaiInvoiceTable();
function initkaiInvoiceTable() {
	kaiInvoiceTable = $('#kaiInvoiceTable').DataTable({
  	"searching":false,
  	"bLengthChange": false,
      "processing": true,
      "serverSide": true,
      "stripeClasses": [ 'strip1','strip2' ],
      "language": {
          "url": BASE_PATH + "/public/plugins/datatables/cn.json"
      },
      "ajax": {
          "url": BASE_PATH + "/admin/inland/listKaiInvoiceData.html",
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
                  {"data": "invoicenum", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul> ';
                		$.each(row.invoiceDetail, function(name, value) {
                			if(value && value.invoicenum != undefined){
                				result += '<li style="list-style:none;">'+value.invoicenum+'</li>';
                			}
                		});
                		result += '</ul>';
                		return result;
                  	}
                  },
                  {"data": "invoicebalance", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = '<ul>';
                  		$.each(row.invoiceDetail, function(name, value) {
                  			if(value && value.invoicebalance != undefined){
                  				result += '<li style="list-style:none;">'+value.invoicebalance+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "incometotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = 0;
                  		$.each(row.invoiceDetail, function(name, value) {
                  			if(value && value.invoicebalance != undefined){
                  				result = parseFloat(result) + parseFloat(value.invoicebalance);
                  			}
                  		});
                  		return result;
                  	}
                  },
                  {"data": "invoicedate", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.invoicedate){
                  			result = row.invoicedate;
                  		}
                  		return result;
                  	}  
                  },
                  {"data": "invoiceitem", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var result = '';
                    		if(row.invoiceitem){
                    			result = row.invoiceitem;
                    		}
                    		return result;
                    	}
                  },
                  {"data": "paymentunit", "bSortable": false},
                  {"data": "username", "bSortable": false},
                  {"data": "status", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.status && row.status != undefined){
                  			result = row.status;
                  		}
                  		return result;
                  	}
                  },
                  {"data": "remark", "bSortable": false}
          ],
      columnDefs: [{
    	//   指定第一列，从0开始，0表示第一列，1表示第二列……
          targets: 10,
          render: function(data, type, row, meta) {
              return '<a style="cursor:pointer;" onclick="openkaiInvoiceEdit('+row.id+');">编辑</a>'
          }
      }]
  });
}
function kaiInvoiceLoad(){
	kaiInvoiceTable.ajax.reload();
}
//打开开发票页面
function openkaiInvoiceEdit(id){
	layer.open({
        type: 2,
        title:false,
        skin: false, //加上边框
        closeBtn:false,//默认 右上角关闭按钮 是否显示
        shadeClose:true,
        area: ['987px', '620px'],
        content: BASE_PATH + '/admin/inland/kaiInvoice.html?id='+id
      });
}
//付款表格
var shouInvoiceTable;
//初始化表格
initshouInvoiceTable();
function initshouInvoiceTable() {
	shouInvoiceTable = $('#shouInvoiceTable').DataTable({
	"searching":false,
	"bLengthChange": false,
    "processing": true,
    "serverSide": true,
    "stripeClasses": [ 'strip1','strip2' ],
    "language": {
        "url": BASE_PATH + "/public/plugins/datatables/cn.json"
    },
    "ajax": {
        "url": BASE_PATH + "/admin/inland/listShouInvoiceData.html",
        "type": "post",
        "data": function (d) {
        	
        }
    },
    "columns": [
                {"data": "ordersnum", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.ordersnum && row.ordersnum!= undefined){
                			result = row.ordersnum;
                		}
                		return result;
                	}
                },
                {"data": "pNR", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.pnr && row.pnr!= undefined){
                			result = row.pnr;
                		}
                		return result;
                	}
                },
                {"data": "personcount", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.personcount && row.personcount != undefined) {
                			result =row.personcount;
                		}
                		return result;
                	}
                },
                {"data": "invoicecount", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.invoicecount && row.invoicecount != undefined) {
                			result =row.invoicecount;
                		}
                		return result;
                	}
                },
                {"data": "salespricesum", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.salespricesum && row.salespricesum != undefined) {
                			result =row.salespricesum;
                		}
                		return result;
                	}
                },
                {"data": "invoicedate", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.invoicedate && row.invoicedate != undefined) {
                			result =row.invoicedate;
                		}
                		return result;
                	}
                },
                {"data": "invoiceitem", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.invoiceitem && row.invoiceitem != undefined) {
                			result =row.invoiceitem;
                		}
                		return result;
                	}
                },
                {"data": "paymentunit", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var result = '';
                		if(row.paymentunit && row.paymentunit != undefined) {
                			result =row.paymentunit;
                		}
                		return result;
                	}
                },
                {"data": "username", "bSortable": false},
                {"data": "status", "bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.status){
                  			result = row.status;
                  		}
                  		return result;
                  	}
                },
                {"data": "remark", "bSortable": false,
                	render:function(data, type, row, meta) {
                  		var result = '';
                  		if(row.remark){
                  			result = row.remark;
                  		}
                  		return result;
                  	}
                }
        ],
    columnDefs: [{
  	//   指定第一列，从0开始，0表示第一列，1表示第二列……
        targets: 11,
        render: function(data, type, row, meta) {
            return '<a style="cursor:pointer;" onclick="openshouInvoiceEdit('+row.invoiceid+');">收发票</a>'
        }
    }]
});
}
function shouInvoiceLoad(){
	shouInvoiceTable.ajax.reload();
}

function openshouInvoiceEdit(id){
	layer.open({
        type: 2,
        title:false,
        skin: false, //加上边框
        closeBtn:false,//默认 右上角关闭按钮 是否显示
        shadeClose:true,
        area: ['987px', '620px'],
        content: BASE_PATH + '/admin/inland/shouInvoice.html?id='+id
    });
}

