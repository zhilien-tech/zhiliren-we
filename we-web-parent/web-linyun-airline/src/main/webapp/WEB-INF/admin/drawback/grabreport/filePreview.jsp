<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>附件预览</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	 <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
    <link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css"><!--本页面style-->
    <link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
    <link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
    <style type="text/css">
    	.form-control-feedback {position: absolute;top: 1px;right: -10px;}
    	/* .bankSlipImg{background-color: #525659;} */
    	[cellpadding="0"]{background-color: #fff;padding: 25px;}
    </style>
</head>
<body>
	<div class="modal-top">
    		<div class="modal-header boderButt">
	            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
	            <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
	            <h4>附件预览</h4>
          	</div>
         <form id="addForm" method="post">
          	<div class="modal-body modal-bod" style="height:632px;overflow-y:auto; ">
          	  <input type="hidden" name="flagType" value="${obj.flagType }" id="flagType">
          	  <div class="row"><!--文件名称/PNR/退税状态-->
          	    <div class="form-group inline">
                  <label class="col-sm-2 text-right padding">文件名称：</label>
                  <input id="pid" name="id" type="hidden" value="${obj.fileurl.id }"  />
                  <div class="col-sm-2 padding">
                  		<input id="fileNameId" name="fileName" type="text" class="form-control input-sm inputWidth" value="${obj.fileurl.fileName }" />
                  </div>
                </div>  
              	<div class="form-group inline">
                   <label class="col-sm-1 text-right padding">PNR：</label>
                  <div class="col-sm-2 padding">
                    <%-- <select id="findBank" class="form-control input-sm" onchange="selectBankName();" name="bankName">
               			<option value="">请选择</option>

               			<c:forEach items="${obj.bankList }" var="each">
               				<option value="${each.dictName }">${each.dictName }</option>
               			</c:forEach>
                    </select> --%>
                  	<select id="pnrInfoSelect" name="pnrInfoSelect" onchange="setPNRINfo();" class="form-control select2 inpImpWid" multiple="multiple"></select>
                  	<input id="pnrInfoId"  name="PNR" type="hidden" class="form-control input-sm inputWidth" placeholder="请输入PNR" />
                  </div>
                </div> 
                
                <!-- 设置已选中的项 -->
				<script type="text/javascript">
					function setPNRINfo() {
						var _selectedAreaIds = $("#pnrInfoSelect").select2("val");
						
						$("#pnrInfoId").val(_selectedAreaIds);
					}
				</script>
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">退税状态：</label>
                  <div class="col-sm-2 padding">
                  		<select id="backStatusId" name="backStatus" class="form-control input-sm inputWidth">
                           <option value="0">已退</option>
                           <option value="1" selected="selected">未退</option>
                        </select>
                  </div>
                </div>
         	  </div><!--end 文件名称/PNR/退税状态-->
          	  <div class="row"><!--人数/成本单价/实收单价-->
              	<div class="form-group inline">
                  <label class="col-sm-2 text-right padding">人数：</label>
                  <div class="col-sm-2 padding">
                  	  	<input id="peopleNumId" name="peopleNum" type="text" class="form-control input-sm inputWidth" placeholder="请输入人数" />
                  </div>
                </div> 
                
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">成本单价：</label>
                  <div class="col-sm-2 padding">
                  		<input id="costUnitPriceId" name="costUnitPrice" type="text" class="form-control input-sm inputWidth" placeholder="请输入成本单价"/>
                  </div>
                </div>  
                <div class="form-group inline"> 
                  <label class="col-sm-1 text-right padding">实收单价：</label>
                  <div class="col-sm-2 padding">
                  		<input id="paidUnitPriceId" name="paidUnitPrice" type="text" class="form-control input-sm inputWidth" placeholder="请输入实收单价" />
                  </div>
         	 	</div>
         	  </div><!--end 人数/成本单价/实收单价-->
          	  <div class="row"><!--刷卡费/汇款金额/代理返点-->
              	<div class="form-group inline">
                  <label class="col-sm-2 text-right padding">刷卡费：</label>
                  <div class="col-sm-2 padding">
                  		<input id="swipeId" name="swipe" type="text" class="form-control input-sm inputWidth" placeholder="请输入刷卡费" />
                  </div>
                </div> 
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">汇款金额：</label>
                  <div class="col-sm-2 padding">
                  		<input id="remitId" name="remit" type="text" class="form-control input-sm inputWidth" placeholder="请输入汇款金额" />
                  </div>
                </div>  
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">代理返点：</label>
                  <div class="col-sm-2 padding">
                  		<input id="agentRebateId" name="agentRebate" type="text" class="form-control input-sm inputWidth" placeholder="请输入代理返点"/>
                  </div>
                </div>  
         	  </div><!--end 刷卡费/汇款金额/代理返点-->
          	  <div class="row"><!--税金/杂项/票价/消费税-->
              	<div class="form-group inline">
                  <label class="col-sm-2 text-right padding">税金/杂项：</label>
                  <div class="col-sm-2 padding">
                  		<input id="taxId" name="tax" type="text" class="form-control input-sm inputWidth" placeholder="请输入税金/杂项" />
                  </div>
                </div> 
                <div class="form-group inline">
                  <label class="col-sm-1 text-right padding">票价：</label>
                  <div class="col-sm-2 padding">
                  		<input id="ticketPriceId" name="ticketPrice" type="text" class="form-control input-sm inputWidth" placeholder="请输入票价" />
                  </div>
                </div>  
                <div class="form-group inline"> 
                  <label class="col-sm-1 text-right padding">消费税：</label>
                  <div class="col-sm-2 padding">
                  		<input id="exciseTax1Id" name="exciseTax1" type="text" class="form-control input-sm inputWidth" placeholder="请输入消费税(GST)" />
                  </div>
         	 	</div>
         	  </div><!--end 税金/杂项/票价/消费税-->
          	  <div class="row"><!--入澳时间/出澳时间/备注-->
                <div class="form-group inline">
                  <label class="col-sm-2 text-right padding">入澳日期：</label>
                  <div class="col-sm-2 padding">
                  		<input id="inAustralianTimeId" name="inAustralianTime" type="text" class="form-control input-sm inputWidth" placeholder="请输入入澳时间" />
                  </div>
                </div>  
                <div class="form-group inline"> 
                  <label class="col-sm-1 text-right padding">出澳日期：</label>
                  <div class="col-sm-2 padding">
                  		<input id="outAustralianTimeId" name="outAustralianTime" type="text" class="form-control input-sm inputWidth" placeholder="请输入出澳时间" />
                  </div>
         	 	</div>
         	 	<div class="form-group inline">
                  <label class="col-sm-1 text-right padding">备注：</label>
                  <div class="col-sm-2 padding">
                  		<input id="remarkId" name="remark" type="text" class="form-control input-sm inputWidth" placeholder="请输入备注" />
                  </div>
                </div> 
         	  </div><!--end 入澳时间/出澳时间/备注 -->
         	  <input type="hidden" name="pid" value="${obj.pid }" id="pid">
         	  <table id="PnrShowTable" class="table table-bordered table-hover">
                <thead>
                  <tr>
                    <th>订单号</th>
                    <th>客户团号</th>
                    <th>PNR</th>
                    <th>航空公司</th>
                    <th>人数</th>
                    <th>成本单价</th>
                    <th>入澳日期</th>
                    <th>出澳日期</th>
                    <th>订单状态</th>
                    <th>关联状态</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                <!-- 	<tr>
                		<td>2017032600001</td>
                		<td>DGD938</td>
                		<td>QYGY2G</td>
                		<td>CA</td>
                		<td>6</td>
                		<td>1690.00</td>
                		<td>3U601 CTUMEL 1DEC   0155 1520</td>
                		<td>3U602 MELCTU  10DEC  2210 0550+1</td>
                		<td>一订</td>
                		<td>已关联</td>
                		<td><a href="javscript:;">取消</a></td>
                	</tr> -->
                </tbody>
              </table>
              <div class="bankSlipImg" style="min-height:445px; width:100%;">
              	  <iframe id="zhuce" style="min-height:445px; width:100%;" name="main" src="${obj.fileurl.url}" frameBorder="0" scrolling="no" ></iframe>
              </div>
          </div>
        </form>  
	</div>
	
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
<script src="${base}/common/js/layer/layer.js"></script>
<!-- Select2 -->
<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>

<!-- DataTables -->
<script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>



<script type="text/javascript">
	var BASE_PATH = '${base}';
</script>
<script type="text/javascript">
//验证
$(document).ready(function(){
	$('#addForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	PNR: {
                validators: {
                    notEmpty: {
                        message: 'PNR不能为空!'
                    },
                    stringLength: {/*长度提示*/
                   	    min: 1,
                   	    max: 13,
                   	    message: 'PNR长度不得超出13位!'
                   	  }
                }
            },
            peopleNum: {
                validators: {
                    notEmpty: {
                        message: '人数不能为空!'
                    },
                }
            },
            agentRebate: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '代理返点只能输入整数或者小数!'
                    }
                }
            },
            remit: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '汇款金额只能输入整数或者小数!'
                    }
                }
            },
            swipe: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '刷卡费只能输入整数或者小数!'
                    }
                }
            },
            ticketPrice: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '票价只能输入整数或者小数!'
                    }
                }
            },
            exciseTax1: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '消费税只能输入整数或者小数!'
                    }
                }
            },
            tax: {
                validators: {
                	regexp: {
                        regexp: /^(\d+(\.\d{1,20})?)?$/,
                        message: '税金/杂项只能输入整数或者小数!'
                    }
                }
            }
        }
	});
});
</script>
<script type="text/javascript">
//提交保存
$("#submit").click(function() {
	$('#addForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#addForm").data('bootstrapValidator');
	if(bootstrapValidator.isValid()){
		$.ajax({
			type : "POST",
			url : '${base}/admin/drawback/grabreport/add.html',
			data : $('#addForm').serialize(),
			error : function(request) {
				layer.msg('添加失败!');
			},
			success : function(data) {
				layer.load(1, {
					shade : [ 0.1, '#fff' ]
				//0.1透明度的白色背景
				});
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				parent.layer.close(index);
				window.parent.successCallback('8');
			}
		});
	}
});
//提交时开始验证
$('#submit').click(function() {
    $('#addForm').bootstrapValidator('validate');
});

/* pnr展示 */
$(function (){
	   initDatatable();
});
var empTable;
	function initDatatable() {
		empTable = $('#PnrShowTable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bLengthChange" : false,
			"bSort": true, //排序功能 
			"autoWidth": false,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	       	"ajax": {
	               "url": "${base}/admin/drawback/grabreport/listPnrSystem.html",
	               "type": "post",
	               "data": function (d) {
	            	   
	            	}
	        },
	        "columns": [
	                    {"data": "ordersnum", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.ordersnum;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}		
	                    },//订单号
	                    {"data": "cusgroupnum", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.cusgroupnum;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}			
	                    },//客户团号
	                    {"data": "pnr", "bSortable": false,//PNR
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.pnr;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}		
	                    },
	                    {"data": "filename", "bSortable": false,//航空公司
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.filename;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "personcount", "bSortable": false,//人数
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.personcount;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "costprice", "bSortable": false,//成本单价
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.costprice;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return 0.00;
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "enterausdate", "bSortable": false,//入澳时间
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.enterausdate;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "outausdate", "bSortable": false,//出澳时间
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.outausdate;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "orderstatus", "bSortable": false,//订单状态
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.orderstatus;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "relationstatus", "bSortable": false,//关联状态
	                    	render: function(data, type, row, meta) {
	                    		var relationstatus = row.relationstatus;
	                    		if(1===relationstatus){
	                    			return "关联";
	                    		}else if(0===relationstatus){
	                    			return "未关联";
	                    		}
	                    		return relationstatus;
	                    	}	
	                    },
	                    {"data": "type", "bSortable": false}
	                    ],
	        "columnDefs": [{ "sWidth": "34.66%",  "targets": [0] },
	     				{ "sWidth": "8.66%",  "targets": [1] },
	    				{ "sWidth": "16.66%",  "targets": [2] },
	    				{ "sWidth": "11.66%",  "targets": [3] },
	    				{ "sWidth": "7.66%",  "targets": [4] },
	    				{ "sWidth": "26.66%",  "targets": [5] },
	                    {
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 10,
	            render: function(data, type, row, meta) {
	            	
	            	var modify1 = '<a style="cursor:pointer;" href="'+row.pdfurl+'" target="_blank">预览</a>';
	            	var modify2 = '<a style="cursor:pointer;" onclick="update('+row.id+');">编辑</a>';
	            	if(row.filename ==null || row.filename == ""){
	            		
	            		var modify3 = '<a style="cursor:pointer;" href="#">下载</a>';
	            	}else{
	            		var modify3 = '<a style="cursor:pointer;" href="${base}/admin/airlinepolicy/download.html?id='+row.id+'">下载</a>';
	            		
	            	}
	            	var modify4 = '<a style="cursor:pointer;" onclick="deleteFile('+row.id+');">删除</a>';
	                return modify1+"&nbsp; &nbsp; &nbsp;"+modify2+"&nbsp; &nbsp; &nbsp;"+modify3+"&nbsp; &nbsp; &nbsp;"+modify4;
	            }
	        }]
		});
	}




//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
//pnr的select2

$("#pnrInfoSelect").select2({
	ajax : {
		url : BASE_PATH + "/admin/drawback/grabreport/selectPNRNames.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				p : params.term, // search term
				PNRName:$("#pnrInfoId").val(),
				page : params.page,
				flagType:$("#flagType").val()
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;

			return {
				results : data
			};
		},
		cache : false
	},
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	templateSelection:function  formatRepoSelection(repo){
		var pid=$("#pid").val();
		var flagType=("#flagType").val();
		var text=repo.text;
		$.ajax({
			type : "POST",
			url : '${base}/admin/drawback/grabreport/findAndShowPNR.html',
			data : {
				id : pid,
				pnr:text,
				flagType:flagType
			},
			error : function(request) {
				//layer.msg('添加失败!');
			},
			success : function(data) {
				var param = {PNR:text};
				empTable.settings()[0].ajax.data = param;
				empTable.ajax.reload();
			}
		});
		return repo.text;
		},
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : true, //设置必须存在的选项 才能选中
});
</script>
</body>
</html>
