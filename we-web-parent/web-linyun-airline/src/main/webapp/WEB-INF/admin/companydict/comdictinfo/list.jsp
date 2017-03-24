<%@ page contentType="text/html; charset=UTF-8" language="java"	pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>字典信息</title>
<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row row-top">
					<div class="col-xs-12">
						<div class="box" style="padding:10px;">
								<div class="col-md-2">
									<!--状态名称 搜索框-->
										<select id="statusId" name="status" class="form-control input-sm">
											<option value="1">启用中</option>
                                   			<option value="2">已删除</option>
										</select>
								</div>
								<div class="col-md-3 dictInfoSousuo" style="float:left;">
									<!--字典类别名称 搜索下拉框-->
                                    <label class="col-sm-4 text-right padding">类别名称：</label>
		                            <div class="col-sm-8 padding">
		                            	<select id="comTypeCode" name="comTypeName" class="form-control input-sm inpImpWid">
											<option value=" ">==请选择==</option>
											<c:forEach items="${obj.listTypeName}" var="one">
						                    	<option value="${one.comTypeCode }">
					                            	${one.comTypeName }
					                            </option>
					                        </c:forEach>
		                    			</select>
		                            </div>
								</div>
                                <div class="col-md-3 dictInfoSousuo" style="float:left;">
									<!--字典信息名称 搜索框-->
									<input type="text" id="comDictNameId" name="comDictName" onkeypress="onkeyEnter();" class="form-control"
										placeholder="字典信息">
								</div>
								<div class="col-md-2 col-padding">
									<!--搜索 按钮-->
									<button id="comDictInfoSearchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
								</div>
							<div class="col-md-1 col-md-offset-1">
								<a class="btn btn-primary btn-sm" onclick="add();">添加</a>
							</div>
							<!-- /.box-header -->
							<table id="comcomdatatableInfo" class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>公司名称</th>
										<th>类别名称</th>
										<th>字典代码</th>
										<th>字典信息</th>
										<th>状态</th>
										<th>创建时间</th>
										<th>备注</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
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

		<%@include file="/WEB-INF/public/footer.jsp"%>
	</div>
<script src="${base }/admin/order/ordercommon.js"></script>
<script type="text/javascript">
function add(){
	//添加
    layer.open({
   	    type: 2,
   	    title:false,
   	    closeBtn:false,
   	    fix: false,
   	    maxmin: false,
   	    shadeClose: false,
   	    area: ['900px', '435px'],
   	    content: '${base}/admin/companydict/comdictinfo/add.html',
   	    end: function(){//添加完页面点击返回的时候自动加载表格数据
   	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
   			parent.layer.close(index);
   	    }
   	 });
}

//编辑
function edit(id){
	layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['900px', '435px'],
  	    content: '${base}/admin/companydict/comdictinfo/update.html?id='+id
  	  });
  }
//事件提示
function successCallback(id){
	comcomdatatableInfo.ajax.reload(null,false);
	  if(id == '1'){
		  layer.msg("添加成功",{time:2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time:2000});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time:2000});
	  }
  }
//删除提示
function physicalDelete(did, status) {
	if(2==status){
		var zt = "删除";
	}else if(1==status){
		var zt = "启用";
	}
	layer.confirm("您确认"+zt+"信息吗？", {
	    btn: ["是","否"], //按钮
	    shade: false //不显示遮罩
	}, function(){
		// 点击确定之后
		var url = '${base}/admin/companydict/comdictinfo/updateDeleteStatus.html';
		$.ajax({
			type : 'POST',
			data : {
				id : did,
				status : status
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				if ("200" == data.status) {
					layer.msg("操作成功!",{time:2000});
					window.location.reload(true);
				} else {
					layer.msg("操作失败!",{time:2000});
				}
			},
			error : function(xhr) {
				layer.msg("操作失败",{time:2000});
			}
		});
	}, function(){
	    // 取消之后不用处理
	});
}
//描述提示信息弹出层Tooltip
$(function() {
	$("[data-toggle='tooltip']").tooltip();
});
</script>
<!-- 分页显示 -->
<script type="text/javascript">
//初始化
$(function() {
	initDatatable();
	$('.menu-ul:eq(0)').hide();//隐藏收付款的二级菜单
	$('.menu-ul li a:eq(3)').css("color","rgb(245, 245, 245)");//二级菜单 数据字典 高亮style
});

var comcomdatatableInfo;
function initDatatable() {
	comcomdatatableInfo = $('#comcomdatatableInfo').DataTable({
		"searching" : false,
		"processing" : true,
		"serverSide" : true,
		"stripeClasses": [ 'strip1','strip2' ],//斑马线
		"bLengthChange" : false,
		"language" : {
			"url" : "${base}/public/plugins/datatables/cn.json"
		},
        "ajax": {
            "url": "${base}/admin/companydict/comdictinfo/comInfoListData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "comname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comname = row.comname;
                    		if(null==comname || ""==comname){
                    			return "";
                    		}
                    		return comname;
                    	}	
                    },
                    {"data": "comtypename", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comtypename = row.comtypename;
                    		if(null==comtypename || ""==comtypename){
                    			return "";
                    		}
                    		return comtypename;
                    	}		
                    },
                    {"data": "comddictcode", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comddictcode = row.comddictcode;
                    		if(null==comddictcode || ""==comddictcode){
                    			return "";
                    		}
                    		return comddictcode;
                    	}		
                    },
                    {"data": "comdictname", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var comdictname = row.comdictname;
                    		if(null==comdictname || ""==comdictname){
                    			return "";
                    		}
                    		return comdictname;
                    	}		
                    },
                    {"data": "status", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var status = row.status;
                    		if(null==status || ""==status){
                    			return "";
                    		}
                    		if(status===1){
                    			return "已启用";
                    		}else if(status===2){
                    			return "已删除";
                    		}
                    	}	
                    }, 
                    {"data": "createtime", "bSortable": false,
                    	 render: function(data, type, row, meta) {
                    		 var createtime = new Date(row.createtime);
                    		 if(null==createtime || ""==createtime){
                    			 return "";
                    		 }
                    		 var createtimestr = createtime.getFullYear() + "-" + createtime.getMonth() + "-" + createtime.getDate() + " "
                    		 + createtime.getHours() + ":" + createtime.getMinutes() + ":" + createtime.getSeconds();
                    		return createtimestr;
                        } 	
                    },
                    {"data": "remark", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		 var remark = row.remark;
	                   		 if(null==remark || ""==remark){
	                   			 return "";
	                   		 }
                    		 var result = '<span data-toggle="tooltip" data-placement="left" title="'+remark+'">'+remark+'<span>';
                    		 return result;
                    	}	
                    }
            ],
            "columnDefs": [{
                //   指定第一列，从0开始，0表示第一列，1表示第二列……
                targets: 7,
                render: function(data, type, row, meta) {
                	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
                  		if(1==row.status){
                  			var judge = '<a href="javascript:physicalDelete('+row.id+',2)" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
                  		}else{
                  			var judge = '<a href="javascript:physicalDelete('+row.id+',1)" class="btn_mini btn_modify">启用</a>';
                  		}
                    return modify+judge;
                }
            }]
	});
}

//公司字典信息搜索
$("#comDictInfoSearchBtn").on('click', function () {
	var status = $("#statusId").val();
	var comTypeCode = $("#comTypeCode").val();
	var comDictName = $("#comDictNameId").val();
    var param = {
		        "status":status,
		        "comTypeCode":comTypeCode,
		        "comDictName":comDictName
		    };
    if(status==1 ||　status==2){
    	comcomdatatableInfo.settings()[0].ajax.data = param;
    	comcomdatatableInfo.ajax.reload(
    			function(json){
    				autoHighLoad($('#comcomdatatableInfo'));
    			}
    	);
    }
});

//状态选择按钮
$("#statusId").change(function(){
	$('#comDictInfoSearchBtn').click();
});
//公司字典类别名称选择按钮
$("#comTypeCode").change(function(){
	$('#comDictInfoSearchBtn').click();
});
//搜索回车事件
function onkeyEnter(){
	 if(event.keyCode==13){
		 $("#comDictInfoSearchBtn").click();
	 }
}
</script>
</body>
</html>