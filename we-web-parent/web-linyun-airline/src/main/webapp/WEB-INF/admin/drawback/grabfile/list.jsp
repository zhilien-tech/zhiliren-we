<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>退税</title>
	<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${base}/public/dist/css/grabMail.css"><!--本页面样式-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!--内容-->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
          <div class="row">
            <div class="col-md-12">
              <!-- Custom Tabs -->
              <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                      <li class="active"><a id="fitTab" href="#tab_1" onclick="reloadTable(0);" data-toggle="tab">散客</a></li>
                      <input id="pagerStatus" type="hidden" value="0"/>
                      <li><a id="teamTab" href="#tab_2" onclick="reloadTable(1);" data-toggle="tab">团队</a></li>
                      <li><a href="#tab_3" data-toggle="tab">报表</a></li>
                </ul>
                <div class="tab-content">
                  <!--------------------------------------------邮件抓取散客-------------------------------------->
                  <div class="tab-pane pane-content active" id="tab_1">
                   <div class="rebatesBtn">
                     <button type="button" onclick="batchDeleteFit();" class="btn btn-primary btn-sm right noneBtn none">批量删除</button>
                     <!-- <button type="button" class="btn btn-primary btn-sm right noneBtn none">移动到</button> -->
                     <button type="button" class="btn btn-primary btn-sm right batchBtn">批量操作</button>
                     <button id="folderId" name="createFolder" onclick='newFolder();' type="button" class="btn btn-primary btn-sm right carrynews">新建文件夹</button>
                     <button id="grabMailId" name="grabMailName" type="button" class="btn btn-primary btn-sm right">邮件抓取</button>
                     <button id="uploadFile" onclick="uploadFile();" name="fileID" type="file" class="btn btn-primary btn-sm right">上传</button>
                     <button type="button" class="btn btn-primary btn-sm right returnBtn none">返回上一级</button>
                     <button type="button" class="btn btn-primary btn-sm right indexBtn none">返回首页</button>
                   </div>
                   <!-- 检索开始 -->
                   <div class="col-md-2 col-padding marBottom10">
                   	 	<input id="fileNameId" name="fileName" type="text" onkeypress="onkeyEnter();" class="form-control" placeholder="请输入航空公司二字代码"/>
                   </div>
                   <div class="col-md-2 col-padding">
                   		<input id="sendTimeId" name="sendTime" type="text" onkeypress="onkeyEnter();" class="form-control" placeholder="请输入时间:例如2017.06"/>
                   </div>
                   <div class="col-md-3 col-padding">		
                   		<input id="searchBtnId" name="searchBtn" type="button" class="btn btn-primary btn-sm" value="搜索"/>
                   </div>
                   <!-- 检索结束 -->
                   <input id="currentDirId" type="hidden" value="0"/>
                   <input type="hidden" name="fileName" id="fileName" />
				   <input type="hidden" id="url">
                   <ol class="breadcrumb" id="fitBreadcrumb">
                        <li><a href="javascript:void(0);" onclick="turnToAll(0);"><i class="fa fa-folder-open"></i> 全部文件</a></li>
                   </ol>
                   <table id="rebatesEamilTable" class="table table-bordered table-hover">
                     <thead>
                      <tr>
                       <th><input  class="checkThFit" id="fileCheckboxFit" name="fileCheckbox" type="checkbox"></th>
	                       <th>文件名</th>
	                       <th>时间</th>
	                       <th>大小</th>
	                       <th>操作</th>
                      </tr>
                     </thead>
                     <form id="folderForm" method="post">
                     	<tbody>
                     		<div  class="alldom">
								<ul id="divall">
								</ul>
							</div>
                     	</tbody>
                     </form>
                   </table>
                   <input id="checkedboxval" name="checkedboxval" type="hidden">
                  </div><!--end 邮件抓取-->
				  <!-----------------------------------------------团队------------------------------------------------------------------->
                  <div class="tab-pane pane-content" id="tab_2">
                   <div class="rebatesBtn">
                     <button type="button" onclick="batchDeleteTeam();" class="btn btn-primary btn-sm right noneBtn none">批量删除</button>
                     <!-- <button type="button" class="btn btn-primary btn-sm right noneBtn none">移动到</button> -->
                     <button type="button" class="btn btn-primary btn-sm right batchBtn">批量操作</button>
                     <button id="folderId" name="createFolder" onclick='newFolder();' type="button" class="btn btn-primary btn-sm right carrynews">新建文件夹</button>
                     <button id="grabMailId" name="grabMailName" type="button" class="btn btn-primary btn-sm right">邮件抓取</button>
                     <button id="uploadFileTeam" onclick="uploadFileTeam();" name="fileID" type="file" class="btn btn-primary btn-sm right">上传</button>
                     <button type="button" class="btn btn-primary btn-sm right returnBtn none">返回上一级</button>
                     <button type="button" class="btn btn-primary btn-sm right indexBtn none">返回首页</button>
                   </div>
                   <!-- 检索开始 -->
                   <div class="col-md-2 col-padding marBottom10">
                   	 	<input id="fileNameId" name="fileName" type="text" onkeypress="onkeyEnter();" class="form-control" placeholder="请输入航空公司二字代码"/>
                   </div>
                   <div class="col-md-2 col-padding">
                   		<input id="sendTimeId" name="sendTime" type="text" onkeypress="onkeyEnter();" class="form-control" placeholder="请输入时间:例如2017.06"/>
                   </div>
                   <div class="col-md-3 col-padding">		
                   		<input id="searchBtnId" name="searchBtn" type="button" class="btn btn-primary btn-sm" value="搜索"/>
                   </div>
                   <!-- 检索结束 -->
                   <!-- 区分当前是哪个切换卡下面的 -->
                   <input type="hidden" name="flagType" value="0" id="flagType">
                   <input id="currentDirId" type="hidden" value="0"/>
                   <input type="hidden" name="fileName" id="fileName" />
				   <input type="hidden" id="url">
                   <ol class="breadcrumb" id="teamBreadcrumb">
                        <li ><a href="javascript:void(0);" onclick="turnToAll(1);"><i class="fa fa-folder-open"></i> 全部文件</a></li>
                   </ol>
                   <table id="rebatesEamilTeamTable" class="table table-bordered table-hover">
                     <thead>
                      <tr>
                       <th><input  class="checkThTeam" id="fileCheckboxTeam" name="fileCheckbox" type="checkbox"></th>
	                       <th>文件名</th>
	                       <th>时间</th>
	                       <th>大小</th>
	                       <th>操作</th>
                      </tr>
                     </thead>
                     <form id="folderForm" method="post">
                     	<tbody>
                     		<div  class="alldom">
								<ul id="divall">
								</ul>
							</div>
                     	</tbody>
                     </form>
                   </table>
                   <input id="checkedboxvalTeam" name="checkedboxvalTeam" type="hidden">
                  </div><!--end 团队-->
                  <!------------------------------------------报表开始---------------------------------------------->
                  <div class="tab-pane pane-content" id="tab_3">
                        <table id="rebatesReportTable" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                          	<th>PNR</th>
                            <th>汇款</th>
                            <th>备用金余额</th>
                            <th>票价</th>
                            <th>刷卡费</th>
                            <th>税金/杂项</th>
                            <th>消费税</th>
                            <th>代理费</th>
                            <th>税返点</th>
                            <th>退税状态</th>
                            <th>实收单价</th>
                            <th>实收合计</th>
                            <th>代理费</th>
                            <th>入澳日期</th>
                            <th>出澳日期</th>
                            <th>关联状态</th>
                            <th>备注</th>
                            <!-- <th>操作</th> -->
                          </tr>
                          </thead>
                          <tbody>
                          </tbody>
                        </table>
                  </div><!--end 报表-->

                </div><!-- end tab-content -->
              </div><!-- end nav-tabs-custom -->
            </div><!-- end col-md-12 -->
          </div><!-- end row -->
   </section>
    <!-- /.content -->
  </div>
  <!--end 内容-->
  <!-- Main footer -->
  <%@include file="/WEB-INF/public/footer.jsp"%>
  <!--end footer-->
<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript">
$(function () {
	//批量管理 点击操作
    $(".batchBtn").click(function(){
        $('.noneBtn').toggle();
        //$('.checkTh').toggle();
        $('.checkTd').toggle();
    });

//复选框选择点击操作
$(".checkThFit").click(function () {
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
	var length = $(".checkchild:checked").length;
	if(rebatesEamilTable.page.len() == length){
		$(".checkThFit").prop("checked", true);
	}else{
		$(".checkThFit").prop("checked", false);
	}
});


//团队批量删除复选框点击操作
$(".checkThTeam").click(function () {
    var check = $(this).prop("checked");
    $(".checkchildTeam").prop("checked", check);
    //隐藏域的值
    var hiddenval = $('#checkedboxvalTeam').val();
	if(check){
		var splits = hiddenval.split(',');
		$(".checkchildTeam:checked").each(function(){
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
		$(".checkchildTeam").each(function(){
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
	$('#checkedboxvalTeam').val(hiddenval);
});
//点击之后给隐藏域赋值
$(document).on('click', '.checkchildTeam', function(e) {
	var hiddenval = $('#checkedboxvalTeam').val();
	var thisval = $(this).val();
	var check = $(this).prop("checked");
	if(check){
		if(!hiddenval){
			$('#checkedboxvalTeam').val(thisval);
		}else{
			$('#checkedboxvalTeam').val(hiddenval+','+thisval);
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
			$('#checkedboxvalTeam').val(ids);
		}else{
			$('#checkedboxvalTeam').val(hiddenval);
		}
	}
	var length = $(".checkchildTeam:checked").length;
	if(rebatesEamilTable.page.len() == length){
		$(".checkThTeam").prop("checked", true);
	}else{
		$(".checkThTeam").prop("checked", false);
	}
});


    //文件上传
    $(uploadFile());
    function uploadFile(){
    	$.fileupload1 = $('#uploadFile').uploadify({
    		'auto' : true,//选择文件后自动上传
    		'formData' : {
    			'fcharset' : 'uft-8',
    			'action' : 'uploadimage'
    		},
    		'buttonText' : '上传',//按钮显示的文字
    		'fileSizeLimit' : '3000MB',
    		'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
    		'fileTypeExts' : '*.png; *.txt; *.doc; *.pdf; *.xls; *.jpg; *.docx; *.xlsx;',//上传文件的类型
    		'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
    		'multi' : false,//multi设置为true将允许多文件上传
    		'successTimeout' : 1800,
    		'queueSizeLimit' : 100,
    		'uploader' : '${base}/admin/drawback/grabfile/uploadFile.html',//后台处理的页面
    		//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
    		//下面的例子演示如何获取到vid
    		'onUploadSuccess' : function(file, data, response) {
    			var jsonobj = eval('(' + data + ')');
    			var url  = jsonobj;//地址
    			var fileName = file.name;//文件名称
    			var id = $("input#currentDirId").val();//文件pid
    			var flagType=$("#flagType").val();
    			$.ajax({
    				type : "POST",
    				data : {
    					url : url,
    					fileName : fileName,
    					id:id,
    					flagType:flagType
    				},
    				dataType : 'json',
    				url : '${base}/admin/drawback/grabfile/saveUploadFile.html',
    				error : function(request) {
    					layer.msg('上传失败!',{time:2000});
    				},
    				success : function(data) {
    				    window.parent.successCallback('6');
    					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    				    parent.layer.close(index);
    				}
    			});
    			
    			var innerHtml = "";
                if (response) {
                    innerHtml = "<div><a id='downloadA' href='#' download='"+file.name+"' onclick='downloadFile("
                            + data
                            + ");' >"
                            + file.name
                            + "</a>&nbsp;&nbsp;<span>上传成功</span>&nbsp;&nbsp;&nbsp;&nbsp;"
                            + data + "'></div>";
                } else {
                    innerHtml = "<div>该附件上传失败，请重新上传</div>";
                }
                $("#completeFileName").html($("#completeFileName").html() + innerHtml);
    		},
            //加上此句会重写onSelectError方法【需要重写的事件】
            'overrideEvents': ['onSelectError', 'onDialogClose'],
            //返回一个错误，选择文件的时候触发
            'onSelectError':function(file, errorCode, errorMsg){
                switch(errorCode) {
                    case -110:
                        alert("文件 ["+file.name+"] 大小超出系统限制！");
                        break;
                    case -120:
                        alert("文件 ["+file.name+"] 大小异常！");
                        break;
                    case -130:
                        alert("文件 ["+file.name+"] 类型不正确！");
                        break;
                }
            }
    	});
    }
    $(uploadFileTeam());
    function uploadFileTeam(){/* 团队上传 */
    	$.fileupload1 = $('#uploadFileTeam').uploadify({
    		'auto' : true,//选择文件后自动上传
    		'formData' : {
    			'fcharset' : 'uft-8',
    			'action' : 'uploadimage'
    		},
    		'buttonText' : '上传',//按钮显示的文字
    		'fileSizeLimit' : '3000MB',
    		'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
    		'fileTypeExts' : '*.png; *.txt; *.doc; *.pdf; *.xls; *.jpg; *.docx; *.xlsx;',//上传文件的类型
    		'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
    		'multi' : false,//multi设置为true将允许多文件上传
    		'successTimeout' : 1800,
    		'queueSizeLimit' : 100,
    		'uploader' : '${base}/admin/drawback/grabfile/uploadFile.html',//后台处理的页面
    		//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
    		//下面的例子演示如何获取到vid
    		'onUploadSuccess' : function(file, data, response) {
    			var jsonobj = eval('(' + data + ')');
    			var url  = jsonobj;//地址
    			var fileName = file.name;//文件名称
    			var id = $("input#currentDirId").val();//文件pid
    			var flagType=$("#flagType").val();
    			$.ajax({
    				type : "POST",
    				data : {
    					url : url,
    					fileName : fileName,
    					id:id,
    					flagType:flagType
    				},
    				dataType : 'json',
    				url : '${base}/admin/drawback/grabfile/saveUploadFile.html',
    				error : function(request) {
    					layer.msg('上传失败!',{time:2000});
    				},
    				success : function(data) {
    				    window.parent.successCallback('6');
    					var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    				    parent.layer.close(index);
    				}
    			});
    			
    			var innerHtml = "";
                if (response) {
                    innerHtml = "<div><a id='downloadA' href='#' download='"+file.name+"' onclick='downloadFile("
                            + data
                            + ");' >"
                            + file.name
                            + "</a>&nbsp;&nbsp;<span>上传成功</span>&nbsp;&nbsp;&nbsp;&nbsp;"
                            + data + "'></div>";
                } else {
                    innerHtml = "<div>该附件上传失败，请重新上传</div>";
                }
                $("#completeFileName").html($("#completeFileName").html() + innerHtml);
    		},
            //加上此句会重写onSelectError方法【需要重写的事件】
            'overrideEvents': ['onSelectError', 'onDialogClose'],
            //返回一个错误，选择文件的时候触发
            'onSelectError':function(file, errorCode, errorMsg){
                switch(errorCode) {
                    case -110:
                        alert("文件 ["+file.name+"] 大小超出系统限制！");
                        break;
                    case -120:
                        alert("文件 ["+file.name+"] 大小异常！");
                        break;
                    case -130:
                        alert("文件 ["+file.name+"] 类型不正确！");
                        break;
                }
            }
    	});
    }
});
</script>
<script type="text/javascript">
//单条删除
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
		var url = '${base}/admin/drawback/grabfile/delete.html';
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
					window.parent.successCallback('3');
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
//散客批量删除
function batchDeleteFit(){
	var flagType=$("#flagType").val();
	var ids = $('#checkedboxval').val();
	var length = $(".checkchild:checked").length;
	if(!ids){
		layer.msg("请至少选中一条记录","", 1000);
	}else{
		layer.confirm("确定要批量删除吗?", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			$.ajax({
				type: 'POST', 
				data: {ids:ids}, 
				url: '${base}/admin/drawback/grabfile/batchDelete.html',
				success: function (data) { 
					window.parent.successCallback('10');
					$('#checkedboxval').val('');
					if(flagType===0){
						rebatesEamilTable.ajax.reload(null,false);
					}else if(flagType===1){
						rebatesEamilTeamTable.ajax.reload(null,false);
					}
					$('.checkThFit').attr('checked',false);
				},
				error: function (xhr) {
					layer.msg("批量删除失败!", "", 1000);
				} 
			});
		});
	}
}
//团队批量删除
function batchDeleteTeam(){

	var ids = $('#checkedboxvalTeam').val();

	var length = $(".checkchildTeam:checked").length;
	if(!ids){
		layer.msg("请至少选中一条记录","", 1000);
	}else{
		layer.confirm("确定要批量删除吗?", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			$.ajax({
				type: 'POST', 
				data: {ids:ids}, 
				url: '${base}/admin/drawback/grabfile/batchDelete.html',
				success: function (data) { 
					window.parent.successCallback('10');
					$('#checkedboxvalTeam').val('');
					rebatesEamilTable.ajax.reload(null,false);
					$('.checkThTeam').attr('checked',false);
				},
				error: function (xhr) {
					layer.msg("批量删除失败!", "", 1000);
				} 
			});
		});
	}
}
//备注提示信息弹出层Tooltip
$(function() {
	$("[data-toggle='tooltip']").tooltip();
});

/* //移动到
function move(id){
     layer.open({
   	    type: 2,
   	    title:false,
   	    closeBtn:false,
   	    fix: false,
   	    maxmin: false,
   	    shadeClose: false,
   	    area: ['800px', '500px'],
   	    content: '${base}/admin/drawback/grabfile/move.html?id='+id,
   	    end: function(){//添加完页面点击返回的时候自动加载表格数据
   	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
   			parent.layer.close(index);
   	    }
  	});
} */
//邮件抓取入口
$('#grabMailId').click(function(){
	$.ajax({
		cache : false,
		type : "POST",
		url : '${base}/admin/drawback/grabfile/grabMail.html',
		error : function(request) {
			layer.msg('抓取失败!');
		},
		success : function(data) {
			layer.load(1, {
				 shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
		    window.parent.successCallback('5');
		    parent.location.reload(); // 父页面刷新
		}
	});
});
//事件提示
function successCallback(id){
	  rebatesEamilTable.ajax.reload(null,false);
	  rebatesReportTable.ajax.reload(null,false);
	  rebatesEamilTeamTable.ajax.reload(null,false);
	  if(id == '1'){
		  layer.msg("新建文件夹成功",{time:2000});
	  }else if(id == '2'){
		  layer.msg("修改文件夹名称成功",{time:2000});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time:2000});
	  }else if(id == '4'){
		  layer.msg("移动成功",{time:2000});
	  }else if(id == '5'){
		  layer.msg("抓取成功",{time:2000});
	  }else if(id == '6'){
		  layer.msg("上传成功",{time:2000});
	  }else if(id == '7'){
		  layer.msg("文件下载成功",{time:2000});
	  }else if(id == '8'){
		  layer.msg("添加成功",{time:2000});
	  }else if(id == '9'){
		  layer.msg("编辑成功",{time:2000});
	  }else if(id == '10'){
		  layer.msg("批量删除成功",{time:2000});
	  }
  }
</script>
<!---------------------------------------------------- 邮件抓取分页显示 ----------------------------------------------------->
<script type="text/javascript">
	//初始化表格
	$(function() {
		initDatatable(0);
		initDatatable(1);
	});
	var rebatesEamilTable;
	var rebatesEamilTeamTable;
	var options0 = {//散客
					"searching" : false,
					"processing" : true,
					"serverSide" : true,//左下角括号中页数的显示
					"bPaginate":true,//左下角分页显示
					"info":true,//右下角分页显示
					"bLengthChange" : false,
					"stripeClasses": [ 'strip1','strip2' ],//斑马线
					"bJQueryUI": true,
					"bAutoWidth":false,
					"language" : {
						"url" : "${base}/public/plugins/datatables/cn.json"
					},
			        "ajax": {
			            "url": "${base}/admin/drawback/grabfile/listData.html?flag=0",
			            "type": "post",
			            "data": function (d) {
			            	return {parentId:0}
			            }
			        },
			        "columns": [
								{"data": "id", "bSortable": false,"sWidth": "5%",
			                    	render: function(data, type, row, meta) {
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
			                    {"data": "filename", "bSortable": false,"sWidth": "50%",
			                    	render: function(data, type, row, meta) {
			                    		var filename = row.filename;
			                    		var fileId  = row.id;
			                    		var filetype = row.type;
			                    		var fileExtension='aaa';
			                    		if(filename!=null&&filename!=''){
				                    		fileExtension = filename.substring(filename.lastIndexOf('.') + 1);
			                    		}
			                    		if(null==filename || ""==filename){
			                    			return null;
			                    		}
			                    		//return filename;
			                    		if(filetype===1){
			                    			/* return '<a id="'+fileId+'" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>'; */
			                    			return '<a id="'+fileId+'" href="javascript:editFolder('+row.id+',\''+filename+'\','+filetype+',0);">'+filename+'</a>';
			                    		}else if(filetype===2 && fileExtension==="pdf"){
			                    			/* return  '<a id="'+fileId+'" class="fa fa-file-pdf-o" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>'; */
			                    			return  '<a id="'+fileId+'" class="fa fa-file-pdf-o" href="javascript:editFolder('+row.id+',\''+filename+'\','+filetype+',0);">'+filename+'</a>';
			                    		}//else if(filetype===2 && fileExtension==="PNG"){
			                    			/* return  '<a id="'+fileId+'" class="fa fa-area-chart" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>'; */
			                    			//return  '<a id="'+fileId+'" class="fa fa-area-chart" href="javascript:editFolder('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>';
			                    		//}
			                    		else{
			                    			/* return  '<a id="'+fileId+'" class="fa fa-file-text" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>'; */
			                    			return  '<a id="'+fileId+'" class="fa fa-file-text" href="javascript:editFolder('+row.id+',\''+filename+'\','+filetype+',0);">'+filename+'</a>';
			                    		}
			                    	}
			                    },
			                    {"data": "updatetime", "bSortable": true,"sWidth": "20%",
			                    	 render: function(data, type, row, meta) {
			                    		 var updatetime = row.updatetime;
			                    		 if(null==updatetime || ""==updatetime){
			                    			 return "";
			                    		 }
			                    		return updatetime;
			                        } 	
			                    },
			                    {"data": "filesize", "bSortable": false,"sWidth": "13%",
			                    	render: function(data, type, row, meta) {
			                    		var filesize = row.filesize;
			                    		var unit = row.unit;
			                    		if(null==filesize || ""==filesize){
			                    			return null;
			                    		}
			                    		return filesize+unit;
			                    	}	
			                    },
			                    {"data": "no", "bSortable": false,"sWidth": "12%",
			                    	render: function(data, type, row, meta) {
					                	//var editFolder = '<a href="javascript:editFolder('+row.id+');" style="cursor:pointer;">编辑&nbsp;&nbsp;</a>';
					                	var download = '<a href="${base}/admin/drawback/grabfile/downLoadZipFile.html?parentId='+row.id+'" style="cursor:pointer;">下载&nbsp;&nbsp;</a>';
					                	//var move  = '<a href="javascript:move('+row.id+');" style="cursor:pointer;">移动到&nbsp;&nbsp;</a>';
				                   		if(1==row.status){
				                   			var judge = '<a href="javascript:physicalDelete('+row.id+',2);" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
				                   		}else{
				                   			var judge = '<a href="javascript:physicalDelete('+row.id+',1);" class="btn_mini btn_modify">启用</a>';
				                   		}
					                    return download+judge;
					                }	
			                    }
			            ]
				};
	var options1 = {//团队
					"searching" : false,
					"processing" : true,
					"serverSide" : true,//左下角括号中页数的显示
					"bPaginate":true,//左下角分页显示
					"info":true,//右下角分页显示
					"bLengthChange" : false,
					"stripeClasses": [ 'strip1','strip2' ],//斑马线
					"bJQueryUI": true,
					"bAutoWidth":false,
					"language" : {
						"url" : "${base}/public/plugins/datatables/cn.json"
					},
			        "ajax": {
			            "url": "${base}/admin/drawback/grabfile/listData.html?flag=1",
			            "type": "post",
			            "data": function (d) {
			            	return {parentId:0}
			            }
			        },
			        "columns": [{"data": "id", "bSortable": false,"sWidth": "5%",
			                    	render: function(data, type, row, meta) {
			                    		var result = '';
			                    		var hiddenval = $('#checkedboxvalTeam').val();
			                    		var splits = hiddenval.split(',');
			                    		var flag = false;
			                    		for(var i=0;i<splits.length;i++){
			                    			if(splits[i] == row.id){
			                    				flag = true;
			                    			}
			                    		}	
			                    		if(flag){
			                    			result = '<input type="checkbox"  class="checkchildTeam" checked="true" value="' + row.id + '" />';
			                    		}else{
			                    			result = '<input type="checkbox"  class="checkchildTeam" value="' + row.id + '" />';
			                    		}
			                            return result;
			                    	}
								},
			                    {"data": "filename", "bSortable": false,"sWidth": "50%",
			                    	render: function(data, type, row, meta) {
			                    		var filename = row.filename;
			                    		var fileId  = row.id;
			                    		var filetype = row.type;
			                    		var fileExtension='aaa';
			                    		if(filename!=null&&filename!=''){
				                    		fileExtension = filename.substring(filename.lastIndexOf('.') + 1);
			                    		}
			                    		if(null==filename || ""==filename){
			                    			return null;
			                    		}
			                    		//return filename;
			                    		if(filetype===1){
			                    			/* return '<a id="'+fileId+'" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>'; */
			                    			return '<a id="'+fileId+'" href="javascript:editFolder('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>';
			                    		}else if(filetype===2 && fileExtension==="pdf"){
			                    			/* return  '<a id="'+fileId+'" class="fa fa-file-pdf-o" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>'; */
			                    			return  '<a id="'+fileId+'" class="fa fa-file-pdf-o" href="#">'+filename+'</a>';
			                    		}//else if(filetype===2 && fileExtension==="PNG"){
			                    			/* return  '<a id="'+fileId+'" class="fa fa-area-chart" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>'; */
			                    			//return  '<a id="'+fileId+'" class="fa fa-area-chart" href="javascript:editFolder('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>';
			                    		//}
			                    		else{
			                    			/* return  '<a id="'+fileId+'" class="fa fa-file-text" href="javascript:createFodler('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>'; */
			                    			return  '<a id="'+fileId+'" class="fa fa-file-text" href="javascript:editFolder('+row.id+',\''+filename+'\','+filetype+',1);">'+filename+'</a>';
			                    		}
			                    	}
			                    },
			                    {"data": "updatetime", "bSortable": true,"sWidth": "20%",
			                    	 render: function(data, type, row, meta) {
			                    		 var updatetime = row.updatetime;
			                    		 if(null==updatetime || ""==updatetime){
			                    			 return "";
			                    		 }
			                    		return updatetime;
			                        } 	
			                    },
			                    {"data": "filesize", "bSortable": false,"sWidth": "13%",
			                    	render: function(data, type, row, meta) {
			                    		var filesize = row.filesize;
			                    		var unit = row.unit;
			                    		if(null==filesize || ""==filesize){
			                    			return null;
			                    		}
			                    		return filesize+unit;
			                    	}	
			                    },
			                    {"data": "no", "bSortable": false,"sWidth": "12%",
			                    	render: function(data, type, row, meta) {
					                	//var editFolder = '<a href="javascript:editFolder('+row.id+');" style="cursor:pointer;">编辑&nbsp;&nbsp;</a>';
					                	var download = '<a href="${base}/admin/drawback/grabfile/downLoadZipFile.html?parentId='+row.id+'" style="cursor:pointer;">下载&nbsp;&nbsp;</a>';
					                	//var move  = '<a href="javascript:move('+row.id+');" style="cursor:pointer;">移动到&nbsp;&nbsp;</a>';
				                   		if(1==row.status){
				                   			var judge = '<a href="javascript:physicalDelete('+row.id+',2);" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
				                   		}else{
				                   			var judge = '<a href="javascript:physicalDelete('+row.id+',1);" class="btn_mini btn_modify">启用</a>';
				                   		}
					                    return download+judge;
					                }	
			                    }
			            ]
				};
	function initDatatable(flag) {
			if(flag==0){
				rebatesEamilTable = $('#rebatesEamilTable').DataTable(options0);
			}
			if(flag==1){
				rebatesEamilTeamTable = $('#rebatesEamilTeamTable').DataTable(options1);
			}
	}
	/*切换标签的时候重新加载table */
	function reloadTable(flag){
		$("#flagType").val(flag);
		$("input#currentDirId").val(0);
		if(flag==0){
			options0.ajax.data.parentId=0;
			var param = {parentId:0};
			rebatesEamilTable.settings()[0].ajax.data = param;
			rebatesEamilTable.ajax.reload();
			$("#fitBreadcrumb").find("li").each(function(index){
				if(index>0){
					$(this).remove(); 
				} 
			});
		}
		if(flag==1){
			options1.ajax.data.parentId=0;
			var param = {parentId:0};
			rebatesEamilTeamTable.settings()[0].ajax.data = param;			
			rebatesEamilTeamTable.ajax.reload();
			$("#teamBreadcrumb").find("li").each(function(index){
				if(index>0){
					$(this).remove(); 
				} 
			});

		}
		
	}
	//当点击进入下一级的时候重新加载表格
	//var clickFlag = 1;
	function createFodler(pid,filename,filetype,clickFlag){
		if(clickFlag===1 && filetype===2){
			$.ajax({
				type : "POST",
				url : '${base}/admin/drawback/grabreport/filePreview.html',
				data : {
					id : pid,
					
				},
				success : function(data) {
					if(filetype == 2){
						layer.open({
					  	    type: 2,
					  	    title:false,
					  	    closeBtn:false,
					  	    fix: false,
					  	    maxmin: false,
					  	    shadeClose: false,
					  	    scrollbar: false,
					  	    area: ['1200px', '700px'],
					  	    content: '${base}/admin/drawback/grabreport/filePreview.html?id='+pid
					 	});
					} 
						
				},
				error : function(request) {
					layer.msg("操作失败", "", 3000);
				}
			});
		}else{
			
			options0.ajax.data.parentId=pid;
			var param = {parentId:pid};
			rebatesEamilTable.settings()[0].ajax.data = param;
			rebatesEamilTable.ajax.reload();
			var exist=false;
			$("#fitBreadcrumb").find("li").each(function(index){
				var currenuId = $(this).attr("id");
				if(currenuId == pid){
					exist = true ;
					return false;
				}
			});
			
			if(!exist){
				if(filetype == 1){
					$("#fitBreadcrumb").find("li").each(function(){
						$(this).removeClass("active");
					});
					$("#fitBreadcrumb").append('<li id=\''+pid+'\' class="active"><a class="fa fa-folder-open" onclick="javascript:createFodler(\''+pid+'\',\''+filename+'\');"  href="#">'+filename+'</a></li>');
				}
			}else{
				//找到指定元素的下标
				var selectIndex = 0;
				$("#fitBreadcrumb").find("li").each(function(index){
					var currenuId = $(this).attr("id");
					if(currenuId == pid){
						selectIndex=index;
						return false;
					}
				});
				
				//删除大于该下标的其他元素
				$("#fitBreadcrumb").find("li").each(function(index){
					if(index > selectIndex){
						$(this).remove(); 
					}
				});
				var length = $("#fitBreadcrumb").find("li").length;
				$("#fitBreadcrumb").find("li").each(function(index){
					if(index != (length-1)){
						$(this).removeClass("active");
					}
				});
			}
			//修改当前所在文件夹id
			$("input#currentDirId").val(pid);
		}
	}
	
	//单击进行文件(文件夹)名称的修改
	/* $('#rebatesEamilTable tbody').on('click','tr td:nth-child(2)',function(event){
		//获取当前行的数据
		var row = rebatesEamilTable.row($(this).closest('tr')).data();
		var clickFlag =2;
		var filetype = row.type;
		if(clickFlag == 2 && filetype==1){
			editFolder(row.id);
		}
	}); */
	//双击进入到下一层散客
	$('#rebatesEamilTable tbody').on("dblclick","tr",function(event){
		//获取当前行的数据
		var row = rebatesEamilTable.row($(this).closest('tr')).data();
		var clickFlag =1;
		var filetype = row.type;
		if(clickFlag ==1 && filetype==2){
			clickFlag = 2;
			var a=row.id;
			editFolder(a,1223,2,0);
			return false;
		}
			createFodler(row.id,row.filename,filetype,clickFlag);
	});
	//双击进入到下一层团队
	$('#rebatesEamilTeamTable tbody').on("dblclick","tr",function(event){
		//获取当前行的数据
		var row = rebatesEamilTeamTable.row($(this).closest('tr')).data();
		var clickFlag =1;
		var filetype = row.type;
		if(clickFlag ==1 && filetype==2){
			clickFlag = 2;
			var a=row.id;
			editFolder(a,1223,2,0);
			return false;
		}
			createFodler1(row.id,row.filename,filetype,clickFlag);
	});
	//================================================================================================

function createFodler1(pid,filename,filetype,clickFlag){//团队
		if(clickFlag===1 && filetype===2){
			$.ajax({
				type : "POST",
				url : '${base}/admin/drawback/grabfile/filePreview.html',
				data : {
					id : pid
				},
				success : function(data) {
					if(filetype == 2){
						layer.open({
					  	    type: 2,
					  	    title:false,
					  	    closeBtn:false,
					  	    fix: false,
					  	    maxmin: false,
					  	    shadeClose: false,
					  	    scrollbar: false,
					  	    area: ['1200px', '700px'],
					  	    content: '${base}/admin/drawback/grabreport/filePreview.html?id='+pid
					 	});
					} 
						
				},
				error : function(request) {
					layer.msg("操作失败", "", 3000);
				}
			});
		}else{
			
			options1.ajax.data.parentId=pid;
			var param = {parentId:pid};
			rebatesEamilTeamTable.settings()[0].ajax.data = param;
			rebatesEamilTeamTable.ajax.reload();
			var exist=false;
			$("#teamBreadcrumb").find("li").each(function(index){
				var currenuId = $(this).attr("id");
				if(currenuId == pid){
					exist = true ;
					return false;
				}
			});
			
			if(!exist){
				if(filetype == 1){
					$("#teamBreadcrumb").find("li").each(function(){
						$(this).removeClass("active");
					});
					$("#teamBreadcrumb").append('<li id=\''+pid+'\' class="active"><a class="fa fa-folder-open" onclick="javascript:createFodler1(\''+pid+'\',\''+filename+'\');"  href="#">'+filename+'</a></li>');
				}
			}else{
				//找到指定元素的下标
				var selectIndex = 0;
				$("#teamBreadcrumb").find("li").each(function(index){
					var currenuId = $(this).attr("id");
					if(currenuId == pid){
						selectIndex=index;
						return false;
					}
				});
				
				//删除大于该下标的其他元素
				$("#teamBreadcrumb").find("li").each(function(index){
					if(index > selectIndex){
						$(this).remove(); 
					}
				});
				var length = $("#teamBreadcrumb").find("li").length;
				$("#teamBreadcrumb").find("li").each(function(index){
					if(index != (length-1)){
						$(this).removeClass("active");
					}
				});
			}
			//修改当前所在文件夹id
			$("input#currentDirId").val(pid);
		}
	}
	//新建子文件夹
	function newFolder(){
		var pid = $("input#currentDirId").val();
		var flagType = $("#flagType").val();
		layer.open({
	  	    type: 2,
	  	    title:false,
	  	    closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    scrollbar: false,
	  	    area: ['400px', '170px'],
	  	    content: '${base}/admin/drawback/grabfile/add.html?parentId='+pid+'&flagType='+flagType,
	  	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	  	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	  			parent.layer.close(index);
	  			var param = {parentId:pid};
				rebatesEamilTable.settings()[0].ajax.data = param;
				rebatesEamilTable.ajax.reload();
	  	    }
	 	});
	}
	
	//修改文件夹名称
	function editFolder(id,filename,filetype,clickFlag){
		if(filetype===1){
			layer.open({
		  	    type: 2,
		  	    title:false,
		  	    closeBtn:false,
		  	    fix: false,
		  	    maxmin: false,
		  	    shadeClose: false,
		  	    scrollbar: false,
		  	    area: ['400px', '170px'],
		  	    content: '${base}/admin/drawback/grabfile/update.html?id='+id,
		  	    end: function(){//添加完页面点击返回的时候自动加载表格数据
		  	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		  			parent.layer.close(index);
		  	    }
		 	});
		}else if(filetype===2){
			/* $.ajax({ */
				/* type : "POST",
				url : '${base}/admin/drawback/grabfile/filePreview.html?flagType='+clickFlag,
				data : {
					id : id
				},
				success : function(data) { */
					//if(filetype == 2){
						layer.open({
					  	    type: 2,
					  	    title:false,
					  	    closeBtn:false,
					  	    fix: false,
					  	    maxmin: false,
					  	    shadeClose: false,
					  	    scrollbar: false,
					  	    area: ['1200px', '700px'],
					  	    content: '${base}/admin/drawback/grabreport/filePreview.html?id='+id+'&flagType='+clickFlag
					 	});
					//} 
						
				/* },
				error : function(request) {
					layer.msg("操作失败", "", 3000);
				}
			}); */
		}else{
			options.ajax.data.parentId=pid;
			var param = {parentId:pid};
			rebatesEamilTable.settings()[0].ajax.data = param;
			rebatesEamilTable.ajax.reload();
			var exist=false;
			$("ol.breadcrumb").find("li").each(function(index){
				var currenuId = $(this).attr("id");
				if(currenuId == pid){
					exist = true ;
					return false;
				}
			});
			
			if(!exist){
				if(filetype == 1){
					$("ol.breadcrumb").find("li").each(function(){
						$(this).removeClass("active");
					});
					$("ol.breadcrumb").append('<li id=\''+pid+'\' class="active"><a class="fa fa-folder-open" onclick="javascript:createFodler(\''+pid+'\',\''+filename+'\');"  href="#">'+filename+'</a></li>');
				}
			}else{
				//找到指定元素的下标
				var selectIndex = 0;
				$("ol.breadcrumb").find("li").each(function(index){
					var currenuId = $(this).attr("id");
					if(currenuId == pid){
						selectIndex=index;
						return false;
					}
				});
				
				//删除大于该下标的其他元素
				$("ol.breadcrumb").find("li").each(function(index){
					if(index > selectIndex){
						$(this).remove(); 
					}
				});
				var length = $("ol.breadcrumb").find("li").length;
				$("ol.breadcrumb").find("li").each(function(index){
					if(index != (length-1)){
						$(this).removeClass("active");
					}
				});
			}
			//修改当前所在文件夹id
			$("input#currentDirId").val(pid);
		}
	}
	//散客条件检索
	$("#searchBtnId").on('click', function () {
		var fileNameCode = $("#fileNameId").val();
		var fileNameTime = $("#sendTimeId").val();
	    var param = {
			        "fileNameCode":fileNameCode,
			        "fileNameTime":fileNameTime
			    };
		rebatesEamilTable.settings()[0].ajax.data = param;
		rebatesEamilTable.ajax.reload();
	});
	//搜索回车事件
	function onkeyEnter(){
		 if(event.keyCode==13){
			 $("#searchBtnId").click();
		 }
	}
</script>

<!------------------------------------------------------------ 报表分页显示 ---------------------------------------------------->
<script type="text/javascript">
	var rebatesReportTable;
	function initDatatable2() {
		rebatesReportTable = $('#rebatesReportTable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"bLengthChange" : false,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bJQueryUI": true,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	        "ajax": {
	            "url": "${base}/admin/drawback/grabreport/listData.html",
	            "type": "post",
	            "data": function (d) {
	            	
	            }
	        },
	        "columns": [
	                    {"data": "pnr", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var pnrdata = row.pnr;
	                    		var pnr = '<span data-toggle="tooltip" data-placement="right" title="'+pnrdata+'">'+pnrdata+'<span>';
	                    		if(null==pnr || ""==pnr){
	                    			return "";
	                    		}
	                    		return pnr;
	                    	}
	                    },
	                    {"data": "remit", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var remitdata = (row.remit);
	                    		if(null==remitdata || ""==remitdata){
	                    			return "0.00";
	                    		}else{
	                    			remitdata = (row.remit).toFixed(2);
	                    			var remit = '<span data-toggle="tooltip" data-placement="right" title="'+remitdata+'">'+remitdata+'<span>';
	                    		}
	                    		return remit;
	                    	}
	                    },
	                    {"data": "depositbalance", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalancedata = (row.depositbalance);
	                    		if(null==depositBalancedata || ""==depositBalancedata){
	                    			return "0.00";
	                    		}else{
	                    			depositBalancedata = (row.depositbalance).toFixed(2);
	                    			var depositBalance = '<span data-toggle="tooltip" data-placement="right" title="'+depositBalancedata+'">'+depositBalancedata+'<span>';
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "ticketprice", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var ticketPrice = (row.ticketprice);
	                    		if(null==ticketPrice || ""==ticketPrice){
	                    			return "0.00";
	                    		}
	                    		return ticketPrice.toFixed(2);
	                    	}
	                    },
	                    {"data": "swipe", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var swipe = (row.swipe);
	                    		if(null==swipe || ""==swipe){
	                    			return "0.00";
	                    		}
	                    		return swipe.toFixed(2);
	                    	}
	                    },
	                    {"data": "tax", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var tax = (row.tax);
	                    		if(null==tax || ""==tax){
	                    			return "0.00";
	                    		}
	                    		return tax.toFixed(2);
	                    	}
	                    },
	                    {"data": "excisetax1", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var exciseTax1 = (row.excisetax1);
	                    		if(null==exciseTax1 || ""==exciseTax1){
	                    			return "0.00";
	                    		}
	                    		return exciseTax1.toFixed(2);
	                    	}
	                    },
	                    {"data": "agencyfee", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var agencyFeeData = (row.agencyfee);
	                    		if(null==agencyFeeData || ""==agencyFeeData){
	                    			return "0.00";
	                    		}else{
	                    			agencyFeeData = (row.agencyfee).toFixed(2);
	                    			var agencyFee = '<span data-toggle="tooltip" data-placement="right" title="'+agencyFeeData+'">'+agencyFeeData+'<span>';
	                    		}
	                    		return agencyFee;
	                    	}
	                    },
	                    {"data": "taxrebate", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var taxRebateData = (row.taxrebate);
	                    		if(null==taxRebateData || ""==taxRebateData){
	                    			return "0.00";
	                    		}else{
	                    			taxRebateData = (row.taxrebate).toFixed(2);
	                    			var taxRebate = '<span data-toggle="tooltip" data-placement="right" title="'+taxRebateData+'">'+taxRebateData+'<span>';
	                    		}
	                    		return taxRebate;
	                    	}
	                    },
	                    {"data": "backstatus", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var backStatus = row.backstatus;
	                    		if(backStatus===0){
	                    			return "不退税";
	                    		}else if(backStatus===1){
	                    			return "未退税";
	                    		}else if(backStatus===2){
		                    		return "已退税";
	                    		}
	                    	}
	                    },
	                    {"data": "realincome", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var realIncomedata = (row.realincome);
	                    		if(null==realIncomedata || ""==realIncomedata){
	                    			return "0.00";
	                    		}else{
	                    			realIncomedata = (row.realincome).toFixed(2);
	                    			var realIncome = '<span data-toggle="tooltip" data-placement="right" title="'+realIncomedata+'">'+realIncomedata+'<span>';
	                    		}
	                    		return realIncome;
	                    	}
	                    },
	                    {"data": "realtotaldata", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var realTotalData = (row.realtotal);
	                    		if(null==realTotalData || ""==realTotalData){
	                    			return "0.00";
	                    		}else{
	                    			realTotalData = (row.realtotal).toFixed(2);
	                    			var realTotal = '<span data-toggle="tooltip" data-placement="right" title="'+realTotalData+'">'+realTotalData+'<span>';
	                    		}
	                    		return realTotal;
	                    	}
	                    },
	                    {"data": "agencyfee2", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var agencyFee2Data = (row.agencyfee2);
	                    		if(null==agencyFee2Data || ""==agencyFee2Data){
	                    			return "0.00";
	                    		}else{
	                    			agencyFee2Data = (row.agencyfee2).toFixed(2);
	                    			var agencyFee2 = '<span data-toggle="tooltip" data-placement="right" title="'+agencyFee2Data+'">'+agencyFee2Data+'<span>';
	                    		}
	                    		return agencyFee2;
	                    	}
	                    },
	                    {"data": "inaustraliantime", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var inAustralianTimeData = row.inaustraliantime;
	                    		if(null==inAustralianTimeData || ""==inAustralianTimeData){
	                    			return "";
	                    		}else{
	                    			var inAustralianTime = '<span data-toggle="tooltip" data-placement="left" title="'+inAustralianTimeData+'">'+inAustralianTimeData+'<span>';
	                    		}
	                    		return inAustralianTime;
	                    	}
	                    },
	                    {"data": "outaustraliantime", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var outAustralianTimeData = row.outaustraliantime;
	                    		if(null==outAustralianTimeData || ""==outAustralianTimeData){
	                    			return "";
	                    		}else{
		                    		var outAustralianTime = '<span data-toggle="tooltip" data-placement="left" title="'+outAustralianTimeData+'">'+outAustralianTimeData+'<span>';
	                    		}
	                    		return outAustralianTime;
	                    	}
	                    },
	                    {"data": "backstatus", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var backStatus = row.backstatus;
	                    		if(backStatus===0){
	                    			return "未关联";
	                    		}else if(backStatus===1){
	                    			return "已关联";
	                    		}
	                    		return "";
	                    	}
	                    },
	                    {"data": "remark", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var remarkData = row.remark;
	                    		if(null==remarkData || ""==remarkData){
	                    			return "";
	                    		}else{
		                    		var remark = '<span data-toggle="tooltip" data-placement="left" title="'+remarkData+'">'+remarkData+'<span>';
	                    		}
	                    		return remark;
	                    	}
	                    }
	            ],
	            "columnDefs": [
							   {"sWidth": "7%","aTargets": [10] },
							   {
					                //   指定第一列，从0开始，0表示第一列，1表示第二列……
					                targets: 16,
					                render: function(data, type, row, meta) {
					                	/* var details = '<a style="cursor:pointer;" onclick="editPreview('+row.id+');">编辑</a>'; */
					                    return null;
					                }
				               }]
		});
	}
	//报表详情
	function editPreview(id){
		layer.open({
	  	    type: 2,
	  	    title: false,
	  	  	closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    scrollbar: false,
	  	  	area: ['1000px', '600px'],
	  	    content: '${base}/admin/drawback/grabreport/update.html?id='+id
	  	  });
	}
	
	$(function() {
		initDatatable2();
	});
	
	
	function turnToAll(flag){
		$("input#currentDirId").val(0);

		if(flag==0){
			options0.ajax.data.parentId=0;
			var param = {parentId:0};
			rebatesEamilTable.settings()[0].ajax.data = param;
			rebatesEamilTable.ajax.reload();
			$("#fitBreadcrumb").find("li").each(function(index){
				if(index>0){
					$(this).remove(); 
				} 
			});
		}else if(flag==1){
			options1.ajax.data.parentId=0;
			var param = {parentId:0};
			rebatesEamilTeamTable.settings()[0].ajax.data = param;
			rebatesEamilTeamTable.ajax.reload();
			$("#teamBreadcrumb").find("li").each(function(index){
				if(index>0){
					$(this).remove(); 
				} 
			});
		}
		
	}
	
	
	
	
</script>
<!-- tab切换卡 -->
<script type="text/javascript">
	function setPageStatus(status){
		$("input#pagerStatus").val(status) ;
	}
	$(function() {
		var pageStatus = $("input#pagerStatus").val();
		if("0" == pageStatus){
			$("li#fitTab").addClass("active");
			$("li#teamTab").removeClass("active");
		}else{
			$("li#fitTab").removeClass("active");
			$("li#teamTab").addClass("active");
		}
	});
</script>
</body>
</html>
