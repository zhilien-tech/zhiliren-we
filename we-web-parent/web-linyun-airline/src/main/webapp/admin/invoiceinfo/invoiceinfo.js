var KaiInvoiceTable1;
//初始化表格
initKaiInvoiceTable1();
aaa();
function initKaiInvoiceTable1() {
	KaiInvoiceTable1 = $('#KaiInvoiceTable1').DataTable({
  	"searching":false,
  	"bLengthChange": false,
      "processing": true,
      "serverSide": true,
      "stripeClasses": [ 'strip1','strip2' ],
      "language": {
          "url": BASE_PATH + "/public/plugins/datatables/cn.json"
      },
      "ajax": {
          "url": BASE_PATH + "/admin/invoicemanage/invoiceinfo/listData.html",
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
                		$.each(row.invoicedetail, function(name, value) {
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
                  		$.each(row.invoicedetail, function(name, value) {
                  			if(value && value.invoicebalance != undefined){
                  				result += '<li style="list-style:none;">'+value.invoicebalance+'</li>';
                  			}
                  		});
                  		result += '</ul>';
                  		return result;
                  	}
                  },
                  {"data": "invoicetotal", "bSortable": false,
                  	render:function(data, type, row, meta) {
                  		var result = 0;
                  		$.each(row.invoicedetail, function(name, value) {
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
                  {"data": "paymentunit", "bSortable": false,
                	  render:function(data, type, row, meta) {
                    		var paymentunit = row.paymentunit;
                    		if(paymentunit == "" || paymentunit == null){
                    			return "";
                    		}
                    		return paymentunit;
                    	}
                  },
                  {"data": "username", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var username = row.username;
                  		if(username == "" || username == null){
                  			return "";
                  		}
                  		return username;
                  	}
                  },
                  {"data": "status", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var status = row.status;
                  		if(status ==null || status==""){
                  			return "";
                  		}else{
                  			if(status===1){
                  				return "开发票中";
                  			}else if(status===2){
                  				return "已开发票";
                  			}
                  		}
                  	}
                  },
                  {"data": "remark", "bSortable": false,
                	  render:function(data, type, row, meta) {
                  		var remark = row.remark;
                  		if(remark == "" || remark == null){
                  			return "";
                  		}
                  		return remark;
                  	}
                  }
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
	KaiInvoiceTable1.ajax.reload();
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
        content: BASE_PATH + '/admin/invoicemanage/invoiceinfo/kaiOpenInvoice.html?id='+id
      });
}
//开发票 搜索按钮
/*$("#kaiSearchInvoiceBtn").on('click', function () {
	var status = $("#kaiInvoiceSelect").val();
	var billuserid = $("#kaibilluserid").val();
	var kaiInvoiceBeginDate = $("#kaiInvoiceBeginDate").val();
	var kaiInvoiceEndDate = $("#kaiInvoiceEndDate").val();
	var invoicenum = $("#invoicenumId").val();
	var paymentunit = $("#invoicenumId").val();
	
    var param = {
		        "status":status,
		        "billuserid":billuserid,
		        "kaiInvoiceBeginDate":kaiInvoiceBeginDate,
		        "kaiInvoiceEndDate":kaiInvoiceEndDate,
				"invoicenum": invoicenum,
				"paymentunit": paymentunit
		    };
    	KaiInvoiceTable1.settings()[0].ajax.data = param;
    	KaiInvoiceTable1.ajax.reload(
			function(json){
				alert($('#KaiInvoiceTable1'));
				autoHighLoad($('#KaiInvoiceTable1'));
				
			}
    	);
}
);*/




$("#kaiSearchInvoiceBtn").on('click',aaa());



function aaa() {
	var status = $("#kaiInvoiceSelect").val();
	var billuserid = $("#kaibilluserid").val();
	var kaiInvoiceBeginDate = $("#kaiInvoiceBeginDate").val();
	var kaiInvoiceEndDate = $("#kaiInvoiceEndDate").val();
	var invoicenum = $("#invoicenumId").val();
	var paymentunit = $("#invoicenumId").val();
	
    var param = {
		        "status":status,
		        "billuserid":billuserid,
		        "kaiInvoiceBeginDate":kaiInvoiceBeginDate,
		        "kaiInvoiceEndDate":kaiInvoiceEndDate,
				"invoicenum": invoicenum,
				"paymentunit": paymentunit
		    };
    	KaiInvoiceTable1.settings()[0].ajax.data = param;
    	KaiInvoiceTable1.ajax.reload(
			function(json){
				autoHighLoad($('#KaiInvoiceTable1'));
			}
    	);
}










//开发票状态选择按钮
$("#kaiInvoiceSelect").change(function(){
	$('#kaiSearchInvoiceBtn').click();
});
//开发票开票人选择按钮
$("#kaibilluserid").change(function(){
	$('#kaiSearchInvoiceBtn').click();
});
/*清除 开发票   检索项*/
$('#kaiEmptyBtn').click(function(){
	clearSearchTxt("kaiInvoiceSelect","kaibilluserid", "kaiInvoiceBeginDate", "kaiInvoiceEndDate", "invoicenumId");
});

/*******************************************************收发票表格*****************************************************************/
//收发票表格
var shouInvoiceTable1;
//初始化表格
initshouInvoiceTable1();
function initshouInvoiceTable1() {
	shouInvoiceTable1 = $('#shouInvoiceTable1').DataTable({
	"searching":false,
	"bLengthChange": false,
    "processing": true,
    "serverSide": true,
    "stripeClasses": [ 'strip1','strip2' ],
    "language": {
        "url": BASE_PATH + "/public/plugins/datatables/cn.json"
    },
    "ajax": {
        "url": BASE_PATH + "/admin/invoicemanage/invoiceinfo/listShouInvoiceData.html",
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
                {"data": "username", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var username = row.username;
                		if(username===null ||  username===""){
                			return "";
                		}
                		return username;
                	}
                },
                {"data": "status", "bSortable": false,
                	render:function(data, type, row, meta) {
                		var status = row.status;
                  		if(status ==null || status==""){
                  			return "";
                  		}else{
                  			if(status===3){
                  				return "收发票中";
                  			}else if(status===4){
                  				return "已收发票";
                  			}
                  		}
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
	shouInvoiceTable1.ajax.reload(
			function(json){
				autoHighLoad($('#shouInvoiceTable1'));
			}
	);
}

function openshouInvoiceEdit(id){
	layer.open({
        type: 2,
        title:false,
        skin: false, //加上边框
        closeBtn:false,//默认 右上角关闭按钮 是否显示
        shadeClose:true,
        area: ['987px', '620px'],
        content: BASE_PATH + '/admin/invoicemanage/invoiceinfo/shouOpenInvoice.html?id='+id
    });
}



//收发票 搜索按钮
$("#shouSearchInvoiceBtn").on('click', function () {
	var status = $("#shouInvoiceSelect").val();
	var billuserid = $("#shoubilluserid").val();
	var shouInvoiceBeginDate = $("#shouInvoiceBeginDate").val();
	var shouInvoiceEndDate = $("#shouInvoiceEndDate").val();
	var PNR = $("#paymentunitId").val();
	var paymentunit = $("#paymentunitId").val();
    var param = {
		        "status":status,
		        "billuserid":billuserid,
		        "shouInvoiceBeginDate":shouInvoiceBeginDate,
		        "shouInvoiceEndDate":shouInvoiceEndDate,
				"PNR": PNR,
				"paymentunit": paymentunit
		    };
    	shouInvoiceTable1.settings()[0].ajax.data = param;
    	shouInvoiceTable1.ajax.reload(
    			function(json){
    				autoHighLoad($('#shouInvoiceTable1'));
    			}
    	);
});

/*清除 收发票   检索项*/
$('#shouEmptyBtn').click(function(){
	clearSearchTxt("shouInvoiceSelect","shoubilluserid", "shouInvoiceBeginDate", "shouInvoiceEndDate", "paymentunitId");
});

//清空搜索项函数
function clearSearchTxt(selectId,selectUsername ,beginDateId, endDateId, inputId){
	$("#"+selectId+" option:first").prop("selected", 'selected');  
	$("#"+selectUsername+" option:first").prop("selected", 'selected');  
	$("#"+beginDateId).val("");
	$("#"+endDateId).val("");
	$("#"+inputId).val("");
}
//收发票状态选择按钮
$("#shouInvoiceSelect").change(function(){
	$('#shouSearchInvoiceBtn').click();
});
//根据开票人进行筛选
$("#shoubilluserid").change(function(){
	$('#shouSearchInvoiceBtn').click();
});