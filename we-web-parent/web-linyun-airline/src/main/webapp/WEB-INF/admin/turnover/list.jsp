<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>流水</title>
 <!--  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"> -->
  <!-- Bootstrap 3.3.6 -->
  <c:set var="url" value="${base}/admin/turnover" />
  <link rel="stylesheet" href="${base}/public/dist/css/swiftNumber.css"><!--本页style-->
</head>
<!-- <body class="hold-transition skin-blue sidebar-mini"> -->
<div class="wrapper">
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header LiuSuiNum">
               <form role="form" class="form-horizontal" id="selectTurnOver">
	                <div class="form-group row marginBott cf">
	                  <div class="col-md-1 paddingRight5">
	                    <select class="form-control input-sm" name="status" id="status" onchange="select();">
	                      <option value="1">启用中</option>
	                      <option value="0">关闭</option>
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                     <select class="form-control input-sm" name="bankCardName" id="bankCardName" onchange="select();">
	                      <!-- <option>银行卡名称</option>
	                      <option>国际段专用</option>-->
	                      <option value="">银行卡名称</option> 
	                      <c:forEach items="${obj.bankNameList }" var="each" >
	                    		<option value="${each.cardName }">${each.cardName }</option>
		                  </c:forEach>	        
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                    <select class="form-control input-sm" name="operation" id="operation" onchange="select();">
	                      <option value="">用途</option>
	                      <option value="支出">支出</option>
	                      <option value="收入">收入</option>
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                     <select class="form-control input-sm" name="projectName" id="projectName" onchange="select();">
	                     <!--  <option>机票款</option>
	                      <option>预付机票款</option>-->
	                      <option value="">项目</option> 
	                       <c:forEach items="${obj.projectList }" var="each">
	                    		<option value="${each.dictName }">${each.dictName }</option>
		                  </c:forEach>	        
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                     <select class="form-control input-sm" name="currency" id="currency" onchange="select();">
	                      <option value="">币种</option>
	                     <!--  <option>人民币</option>
	                      <option>澳币</option> -->
	                      <c:forEach items="${obj.currencyList }" var="each">
	                    		<option value="${each.dictCode }">${each.dictCode }</option>
		                  </c:forEach>
	                    </select>
	                  </div>
	                  <div class="col-md-1 padding">
	                     <input type="text" class="form-control" placeholder="2017-02-22" name="tradeDate" id="tradeDate" value="">
	                  </div>
	                  <div class="col-md-2 padding">
	                    <button type="button" class="btn btn-primary btn-sm suBtn" onclick="select();" >搜索</button>
	                    <button type="button" class="btn btn-primary btn-sm suBtn" onclick="clearSelect();">清空</button>
	                  </div>
	                  <div class="col-md-1col-md-offset-3">
	                    <button type="button" class="btn btn-primary btn-sm right jyb" id="addTurnOver">记一笔</button>
	                  </div>
	                </div>
              </form>
            </div>
            <!-- /.box-header -->
            <div class="box-body" style="margin-top:-5px;">
              <table id="turnOverTable" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>交易日期</th>
                  <th>银行卡名称</th>
                  <th>项目</th> 
                  <th>金额</th>
                  <th>用途</th>
                  <th>账户余额</th>
                  <th>币种</th>
                  <th>平均汇率</th>
                  <th>单位</th>
                  <th>收/开发票</th>
                  <th>备注</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                  <!-- <tr>
                    <td>2017-02-22</td>
                    <td> </td>
                    <td>  </td>
                    <td>9362.32</td>
                    <td> </td>
                    <td> </td>
                    <td> </td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><a href="javascript:;">关闭</a></td>
                  </tr> -->
                </tbody>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  
</div>
<!-- ./wrapper -->
<%@include file="/WEB-INF/public/footer.jsp"%>

<!-- REQUIRED JS SCRIPTS -->


<script>
 /*  $(function () {
    $('#turnOverTable').dataTable({//邮件抓取 table
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "columnDefs": [
            {"sWidth": "11.33%","aTargets": [0] },
            {"sWidth": "11.33%","aTargets": [1] },
            {"sWidth": "8.33%","aTargets": [2] },
            {"sWidth": "8.33%","aTargets": [3] },
            {"sWidth": "8.33%","aTargets": [4] },
            {"sWidth": "8.33%","aTargets": [5] },
            {"sWidth": "5.33%","aTargets": [6] },
            {"sWidth": "9.33%","aTargets": [7] },
            {"sWidth": "5.33%","aTargets": [8] },
            {"sWidth": "9.33%","aTargets": [9] },
            {"sWidth": "9.33%","aTargets": [10] },
            {"sWidth": "5.33%","aTargets": [11] }],
        "oLanguage": {                          //汉化   
          "sSearch": ["one","two"],  
          "oPaginate":{   
                "sFirst": "首页",   
                "sPrevious": "上一页",   
                "sNext": "下一页",   
                "sLast": "尾页"  
              }   
            }    
        });

    $(".TabUL li").click(function(){
      $(this).addClass("tabStyle").siblings().removeClass('tabStyle');
    });*/
    //添加订单 弹框
    $('#addTurnOver').click(function(){
        layer.open({
            type: 2,
            title:false,
            skin: false, //加上边框
            closeBtn:false,//默认 右上角关闭按钮 是否显示
            shadeClose:true,
            area: ['770px', '305px'],
            content: ['${url}/add.html','no']
          });
      }); 
  //});
</script>
<script type="text/javascript">
	var empTable;
	function initDatatable() {
		empTable = $('#turnOverTable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bLengthChange" : false,
			"bSort": true, //排序功能 
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	       	"ajax": {
	               "url": "${base}/admin/turnover/listData.html",
	               "type": "post",
	               "data": $('#selectTurnOver').serialize()
	        },
	        "columns": [
	                    {"data": "modifydate", "bSortable": false},
	                    {"data": "bankname", "bSortable": false},
	                    {"data": "projectname", "bSortable": false},//项目
	                    {"data": "money", "bSortable": false},
	                    {"data": "purpose", "bSortable": false},
	                    {"data": "balance", "bSortable": false},
	                    {"data": "currency", "bSortable": false},
	                    {"data": "averagerate", "bSortable": false},
	                    {"data": "companyname", "bSortable": false},//单位
	                    {"data": "averagerate", "bSortable": false},//发票
	                    {"data": "remark", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.remark;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return "";
	                    		}
	                    		return depositBalance;
	                    	}	
	                    }
	            ],
	        columnDefs: [{
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 11,
	            render: function(data, type, row, meta) {
	            	
	            	if(row.status==1){
	            		
		            	var modify = '<a style="cursor:pointer;" onclick="selectClose('+row.id+');">关闭</a>';
	            	}else{
	            		
		            	var modify = '<a style="cursor:pointer;" >已关闭</a>';
	            	}
	                return modify;
	            }
	        }]
		});
	}
	$(function() {
		initDatatable();
		//selectDeptName();
	});
	function successCallback(id){
		empTable.ajax.reload(null,false);
		  if(id == '1'){
			  layer.msg("添加成功",{time: 2000, icon:1});
		  }else if(id == '2'){
			  layer.msg("关闭成功",{time: 2000, icon:1});
		  }else if(id == '3'){
			  layer.msg("删除成功",{time: 2000, icon:1});
		  }
	  }
	//根据不同的条件进行搜索
	function select()
	{
		var tradeDate = $('#tradeDate').val();
		var status = $('#status').val();
		var operation = $('#operation').val();
		var bankCardName = $('#bankCardName').val();
		var projectName = $('#projectName').val();
		var currency = $('#currency').val();
		
	    var param = {
	        "status": status,
	        "operation": operation,
	        "bankCardName": bankCardName,
	        "projectName": projectName,
	        "currency": currency,
			"tradeDate" : tradeDate
	    };
	    empTable.settings()[0].ajax.data = param;
		empTable.ajax.reload();
	}
	function selectClose(id){
		
		$.ajax({
			type : "POST",
			url : '${base}/admin/turnover/update.html',
			data :{
				id : id
			},
			error : function(request) {
				layer.msg('关闭失败!');
			},
			success : function(data) {
				if ("200" == data.status) {
					
					layer.msg("关闭成功!", "", 3000);
					 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				     parent.layer.close(index);
				     window.parent.successCallback('2');
			 	} else {
					layer.msg("关闭失败!", "", 3000);
				} 
			}
		});
		
	}
	function clearSelect(){
		$('#tradeDate').val("");
		$('#status').val(1);
		$('#operation').val("");
		$('#bankCardName').val("");
		$('#projectName').val("");
		$('#currency').val("");
	}
</script>
</html>
