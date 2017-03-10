var drawerPayTable;
//初始化表格
initdrawerPayTable();
function initdrawerPayTable() {
	drawerPayTable = $('#drawerPayTable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "initComplete": function( settings, json ) {
        	$(this).find('tr').each(function () {//出票 table 自适应高度      
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
        "columns": [{"data": "id", "bSortable": false,
			        	"render": function (data, type, row, meta) {
			        		var result = '';
			        		var hiddenval = $('#checkedboxval').val();
			        		var splits = hiddenval.split(',');
			        		var flag = false;
			        		for(var i=0;i<splits.length;i++){
			        			if(splits[i] == row.id){
			        				flag = true;
			        			}
			        		}	
			        		if(flag){
			        			result = '<input type="checkbox"  class="checkchild" checked="true" value="' + row.id + '" />';
			        		}else{
			        			result = '<input type="checkbox"  class="checkchild" value="' + row.id + '" />';
			        		}
			                return result;
			            }
			        },
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
                    		var result = '<ul id="tableUl">';
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
                    		var result = '<ul id="tableUl">';
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
                    		var result = '<ul id="tableUl">';
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
                    		var result = '<ul id="tableUl">';
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
                    		var result = '<ul id="tableUl">';
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
                    {"data": "telephone", "bSortable": false}
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
        }],
        "infoCallback": function (settings, start, end, max, total, pre) {
    		var length = $(".checkchild:checked").length;
    		if(drawerPayTable.page.len() == length){
    			$(".checkall").prop("checked", true);
    		}else{
    			$(".checkall").prop("checked", false);
    			
    		}
    		return '显示第 '+start+' 至 '+end+' 条结果，共'+total+' 条 (每页显示 '+max+' 条)'
    	}
    });
}
//控制复选框
$(".checkall").click(function () {
    var check = $(this).prop("checked");
    $(".checkchild").prop("checked", check);
    //隐藏域的值
    var hiddenval = $('#checkedboxval').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkchild:checked").each(function(){
			var thisvals = $(this).val();
			var flag = false;
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisvals){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(hiddenval){
				if(!flag){
					hiddenval += ',' + thisvals;
				}
			}else{
				hiddenval = thisvals;
			}
		});
	}else{
		$(".checkchild").each(function(){
			var thisval = $(this).val();
			var flag = false;
			var splits = hiddenval.split(',');
			for(var i=0;i<splits.length;i++){
				if(splits[i] == thisval){
					flag = true;
				}
			}
			//如果隐藏域值为空
			if(flag){
				var ids = [];
				for(var i=0;i<splits.length;i++){
					if(splits[i] != thisval){
						ids.push(splits[i]);
					}
				}
				ids = ids.join(',');
				hiddenval = ids;
			}
		});
	}
	$('#checkedboxval').val(hiddenval);
});
//点击之后给隐藏域赋值
$(document).on('click', '.checkchild', function(e) {
	var hiddenval = $('#checkedboxval').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxval').val(thisval);
		}else{
			$('#checkedboxval').val(hiddenval+','+thisval);
		}
	}else{
		var splits = hiddenval.split(',');
		var flag = false;
		for(var i=0;i<splits.length;i++){
			if(splits[i] == thisval){
				flag = true;
			}
		}
		//如果存在则删掉当前值
		if(flag){
			var ids = [];
			for(var i=0;i<splits.length;i++){
				if(splits[i] != thisval){
					ids.push(splits[i]);
				}
			}
			ids = ids.join(',');
			$('#checkedboxval').val(ids);
		}else{
			$('#checkedboxval').val(hiddenval);
		}
	}
});
//点击出票加载出票表格
function loadTicking(){
	var param = {
			ordersstatus:3,
			ticketing:1
	};
	drawerPayTable.settings()[0].ajax.data = param;
	drawerPayTable.ajax.reload();
}
$('.fuKuanBtn').click(function(){
	var ids = $('#checkedboxval').val();
	if(!ids){
		layer.msg("请至少选中一条记录",{time: 2000, icon:1});
	}else{
		layer.open({
			type: 2,
			title:false,
			skin: false, //加上边框
			closeBtn:false,//默认 右上角关闭按钮 是否显示
			shadeClose:true,
			area: ['850px', '550px'],
			content: BASE_PATH + '/admin/inland/seaInvoice.html?ids='+ids
		});
	}
});
