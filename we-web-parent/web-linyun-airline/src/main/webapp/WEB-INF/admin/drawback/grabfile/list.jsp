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
      <div class="row row-top">
        <div class="col-xs-12">
          <div class="row">
            <div class="col-md-12">
              <!-- Custom Tabs -->
              <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">邮件抓取</a></li>
                      <li><a href="#tab_2" data-toggle="tab">报表</a></li>
                </ul>
                <div class="tab-content">
                  <!--邮件抓取-->
                  <div class="tab-pane pane-content active" id="tab_1">
                   <div class="rebatesBtn">
                     <button type="button" onclick="batchDelete();" class="btn btn-primary btn-sm right noneBtn none">批量删除</button>
                     <button type="button" class="btn btn-primary btn-sm right noneBtn none">移动到</button>
                     <button type="button" class="btn btn-primary btn-sm right batchBtn">批量操作</button>
                     <button id="folderId" name="createFolder" onclick='newFolder();' type="button" class="btn btn-primary btn-sm right carrynews">新建文件夹</button>
                     <button id="grabMailId" name="grabMailName" type="button" class="btn btn-primary btn-sm right">邮件抓取</button>
                     <button id="uploadFile" name="fileID" type="file" class="btn btn-primary btn-sm right">上传</button>
                     <button type="button" class="btn btn-primary btn-sm right returnBtn none">返回上一级</button>
                     <button type="button" class="btn btn-primary btn-sm right indexBtn none">返回首页</button>
                   </div>
                   
                   <input id="currentDirId" type="hidden" value="0"/>
                   <input type="hidden" name="fileName" id="fileName" />
				   <input type="hidden" id="url">
                   <ol class="breadcrumb">
                        <li><a href="${base}/admin/drawback/grabfile/list.html"><i class="fa fa-folder-open"></i> 全部文件</a></li>
                   </ol>
                   <table id="rebatesEamilTable" class="table table-bordered table-hover">
                     <thead>
                      <tr>
                       <th><input  class="checkTh" id="fileCheckbox" name="fileCheckbox" type="checkbox"></th>
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

                  <!--报表-->
                  <div class="tab-pane pane-content" id="tab_2">
                        <table id="rebatesReportTable" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                            <th>备注</th>
                            <th>汇款</th>
                            <th>blance(备用金余额)</th>
                            <th>票价(含行李)</th>
                            <th>刷卡费</th>
                            <th>税金/杂项</th>
                            <th>消费税(GST)</th>
                            <th>代理费</th>
                            <th>税返点</th>
                            <th>退税状态</th>
                            <th>实收单价(含操作费)</th>
                            <th>实收合计(含操作费)</th>
                            <th>代理费</th>
                            <th>入澳时间</th>
                            <th>出澳时间</th>
                            <th>操作</th>
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
        </div>
      </div>
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
//控制复选框
$(".checkTh").click(function () {
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
    
    //文件上传
    $('#uploadFile').click(function(){
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
    			$.ajax({
    				cache : false,
    				type : "POST",
    				data : {
    					url : url,
    					fileName : fileName,
    					id:id
    				},
    				dataType : 'json',
    				url : '${base}/admin/drawback/grabfile/saveUploadFile.html',
    				error : function(request) {
    					layer.msg('上传失败!');
    				},
    				success : function(data) {
    					layer.load(1, {
    						 shade: [0.1,'#fff'] //0.1透明度的白色背景
    					});
    				    window.parent.successCallback('6');
    				    parent.location.reload(); // 父页面刷新
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
    });
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
		var url = '${base}/admin/drawback/grabfile/updateDeleteStatus.html';
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
					layer.msg("操作成功!", "", 3000);
					window.location.reload(true);
				} else {
					layer.msg("操作失败!", "", 3000);
				}
			},
			error : function(xhr) {
				layer.msg("操作失败", "", 3000);
			}
		});
	}, function(){
	    // 取消之后不用处理
	});
}
//批量删除
function batchDelete(){
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
				url: '${base}/admin/drawback/grabfile/updateBetchStatus.html',
				success: function (data) { 
					layer.msg("批量删除成功!", "", 1000);
					$('#checkedboxval').val('');
					rebatesEamilTable.ajax.reload(null,false);
					$('.checkTh').attr('checked',false);
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
	  if(id == '1'){
		  layer.msg("新建文件夹成功!",{time: 1000, icon:1});
	  }else if(id == '2'){
		  layer.msg("修改文件夹名称成功!",{time: 1000, icon:1});
	  }else if(id == '3'){
		  layer.msg("删除成功!",{time: 1000, icon:1});
	  }else if(id == '4'){
		  layer.msg("移动成功!",{time: 1000, icon:1});
	  }else if(id == '5'){
		  layer.msg("抓取成功!",{time: 1000, icon:1});
	  }else if(id == '6'){
		  layer.msg("上传成功!",{time: 1000, icon:1});
	  }
  }
</script>
<!---------------------------------------------------- 邮件抓取分页显示 ----------------------------------------------------->
<script type="text/javascript">
//数据集
var dataSet = [
    [ "2017.10", "System Architect", "Edinburgh", "5421" ]
];
	var rebatesEamilTable;
	var options = {
			"searching" : false,
			"processing" : true,
			"serverSide" : true,//左下角括号中页数的显示
			"bPaginate":false,//左下角分页显示
			"info":false,//右下角分页显示
			"bLengthChange" : false,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bJQueryUI": true,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
	        "ajax": {
	            "url": "${base}/admin/drawback/grabfile/listData.html",
	            "type": "post",
	            "data": function (d) {
	            	return {parentId:0}
	            }
	        },
	        "columns": [
						{"data": "id", "bSortable": false,
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
	                    		//return '<td class="checkchild"><input type="checkbox" value='+row.id+'></td>';
	                    	}
						},
	                    {"data": "filename", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var filename = row.filename;
	                    		if(null==filename || ""==filename){
	                    			return null;
	                    		}
	                    		return '<a href="javascript:createFodler('+row.id+',\''+filename+'\');">'+filename+'</a>';
	                    	}
	                    },
	                    {"data": "createtime", "bSortable": true,
	                    	 render: function(data, type, row, meta) {
	                    		 var createtime = new Date(row.createtime);
	                    		 var createtimestr = createtime.getFullYear() + "-" + createtime.getMonth() + "-" + createtime.getDate() + " "
	                    		 + createtime.getHours() + ":" + createtime.getMinutes() + ":" + createtime.getSeconds();
	                    		return createtimestr;
	                        } 	
	                    },
	                    {"data": "filesize", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var filesize = row.filesize;
	                    		if(null==filesize || ""==filesize){
	                    			return null;
	                    		}
	                    		return filesize+"k";
	                    	}	
	                    }
	            ],
	            "columnDefs": [{
	                //   指定第一列，从0开始，0表示第一列，1表示第二列……
	                targets: 4,
	                render: function(data, type, row, meta) {
	                	var editFolder = '<a href="javascript:editFolder('+row.id+');" style="cursor:pointer;">编辑&nbsp;&nbsp;&nbsp;</a>';
	                	var download = '<a href="${base}/admin/drawback/grabfile/downLoadZipFile.html" style="cursor:pointer;">&nbsp;&nbsp;下载&nbsp;&nbsp;&nbsp;</a>';
	                	var move  = '<a href="javascript:fileMove('+row.id+');" style="cursor:pointer;">&nbsp;&nbsp;移动到&nbsp;&nbsp;</a>';
                   		if(1==row.status){
                   			var judge = '<a href="javascript:physicalDelete('+row.id+',2);" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
                   		}else{
                   			var judge = '<a href="javascript:physicalDelete('+row.id+',1);" class="btn_mini btn_modify">启用</a>';
                   		}
	                    return editFolder+download+move+judge;
	                }
	            }]
		};
	function initDatatable() {
		rebatesEamilTable = $('#rebatesEamilTable').DataTable(options);
	}
	//当点击进入下一级的时候重新加载表格
	function createFodler(pid,filename){
		options.ajax.data.parentId=pid;
		var param = {parentId:pid};
		//$('#rebatesEamilTable').empty(); 
		//rebatesEamilTable.ajax.reload(null,false);
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
			$("ol.breadcrumb").find("li").each(function(){
				$(this).removeClass("active");
			});
			$("ol.breadcrumb").append('<li id=\''+pid+'\' class="active"><a onclick="javascript:createFodler(\''+pid+'\',\''+filename+'\');"  href="#">'+filename+'</a></li>');
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
	
	//新建子文件夹
	function newFolder(){
		var pid = $("input#currentDirId").val();
		//rebatesEamilTable.destroy();
		layer.open({
	  	    type: 2,
	  	    title:false,
	  	    closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    area: ['400px', '200px'],
	  	    content: '${base}/admin/drawback/grabfile/add.html?parentId='+pid,
	  	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	  	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	  			parent.layer.close(index);
	  			//rebatesEamilTable.ajax.url("${base}/admin/drawback/grabfile/listData.html?parntId="+pid).load();
	  			var param = {parentId:pid};
				rebatesEamilTable.settings()[0].ajax.data = param;
				rebatesEamilTable.ajax.reload();
	  	    }
	 	});
	}
	//修改文件夹名称
	function editFolder(id){
		layer.open({
	  	    type: 2,
	  	    title:false,
	  	    closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    area: ['400px', '200px'],
	  	    content: '${base}/admin/drawback/grabfile/update.html?id='+id,
	  	    end: function(){//添加完页面点击返回的时候自动加载表格数据
	  	    	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	  			parent.layer.close(index);
	  	    }
	 	});
	}
	
	//移动到
	function fileMove(id){
		$.ajax({
			cache : false,
			type : "POST",
			url : '${base}/admin/drawback/grabfile/fileMove.html',
			data : {
				id:id
			},
			error : function(request) {
				layer.msg('移动失败!');
			},
			success : function(data) {
				layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
			    window.parent.successCallback('4');
			    parent.location.reload(); // 父页面刷新
			}
		});
	}
	$(function() {
		initDatatable();
	});
	//单行选中
	/* $(document).ready(function() {
	    var table = $('#rebatesEamilTable').DataTable();
	    $('#rebatesEamilTable tbody').on( 'click', 'tr', function () {
	        if ($(this).hasClass('selected') ) {
	        	console.info("this",this);
	            $(this).removeClass('selected');
	        }
	        else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    } );
	    $('#button').click( function () {
	        table.row('.selected').remove().draw( false );
	    });
	}); */
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
	                    {"data": "remark", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var remark = row.remark;
	                    		if(null==remark || ""==remark){
	                    			return null;
	                    		}
	                    		var result = '<span data-toggle="tooltip" data-placement="right" title="'+row.remark+'">'+row.remark+'<span>';
	                    		return result;
	                    	}
	                    },
	                    {"data": "remit", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var remit = row.remit;
	                    		if(null==remit || ""==remit){
	                    			return null;
	                    		}
	                    		return remit;
	                    	}
	                    },
	                    {"data": "depositBalance", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var depositBalance = row.depositBalance;
	                    		if(null==depositBalance || ""==depositBalance){
	                    			return null;
	                    		}
	                    		return depositBalance;
	                    	}	
	                    },
	                    {"data": "ticketPrice", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var ticketPrice = row.ticketPrice;
	                    		if(null==ticketPrice || ""==ticketPrice){
	                    			return null;
	                    		}
	                    		return ticketPrice;
	                    	}
	                    },
	                    {"data": "swipe", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var swipe = row.swipe;
	                    		if(null==swipe || ""==swipe){
	                    			return null;
	                    		}
	                    		return swipe;
	                    	}
	                    },
	                    {"data": "tax", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var tax = row.tax;
	                    		if(null==tax || ""==tax){
	                    			return null;
	                    		}
	                    		return tax;
	                    	}
	                    },
	                    {"data": "exciseTax1", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var exciseTax1 = row.exciseTax1;
	                    		if(null==exciseTax1 || ""==exciseTax1){
	                    			return null;
	                    		}
	                    		return exciseTax1;
	                    	}
	                    },
	                    {"data": "agencyFee", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var agencyFee = row.agencyFee;
	                    		if(null==agencyFee || ""==agencyFee){
	                    			return null;
	                    		}
	                    		return agencyFee;
	                    	}
	                    },
	                    {"data": "taxRebate", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var taxRebate = row.taxRebate;
	                    		if(null==taxRebate || ""==taxRebate){
	                    			return null;
	                    		}
	                    		return taxRebate;
	                    	}
	                    },
	                    {"data": "backStatus", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var backStatus = row.backStatus;
	                    		if(null==backStatus || ""==backStatus){
	                    			return null;
	                    		}
	                    		return backStatus;
	                    	}
	                    },
	                    {"data": "realIncome", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var realIncome = row.realIncome;
	                    		if(null==realIncome || ""==realIncome){
	                    			return null;
	                    		}
	                    		return realIncome;
	                    	}
	                    },
	                    {"data": "realTotal", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var realTotal = row.realTotal;
	                    		if(null==realTotal || ""==realTotal){
	                    			return null;
	                    		}
	                    		return realTotal;
	                    	}
	                    },
	                    {"data": "agencyFee2", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var agencyFee2 = row.agencyFee2;
	                    		if(null==agencyFee2 || ""==agencyFee2){
	                    			return null;
	                    		}
	                    		return agencyFee2;
	                    	}
	                    },
	                    {"data": "inAustralianTime", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var inAustralianTime = row.inAustralianTime;
	                    		if(null==inAustralianTime || ""==inAustralianTime){
	                    			return null;
	                    		}
	                    		return inAustralianTime;
	                    	}
	                    },
	                    {"data": "outAustralianTime", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		var outAustralianTime = row.outAustralianTime;
	                    		if(null==outAustralianTime || ""==outAustralianTime){
	                    			return null;
	                    		}
	                    		return outAustralianTime;
	                    	}
	                    }
	            ],
	            "columnDefs": [{
	                //   指定第一列，从0开始，0表示第一列，1表示第二列……
	                targets: 15,
	                render: function(data, type, row, meta) {
	                	var details = '<a style="cursor:pointer;">详情</a>';
	                    return details;
	                }
	            }]
		});
	}
	$(function() {
		initDatatable2();
	});
</script>
</body>
</html>