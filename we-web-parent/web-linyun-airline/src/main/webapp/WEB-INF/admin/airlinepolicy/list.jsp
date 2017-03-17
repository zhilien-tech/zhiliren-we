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
  <title>航空公司政策管理</title>
  <link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="${base}/public/dist/css/policyManage.css"><!--本页面Style-->
</head>
<!-- <body class="hold-transition skin-blue sidebar-mini"> -->
<div class="wrapper">

 

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
               <form role="form" class="form-horizontal">
                <div class="form-group row mar cf">
                  <div class="col-md-1 padRight">
                    <select class="form-control input-sm">
                      <!-- <option>VR</option>
                      <option>JQ</option> -->
                      <option value="">请选择</option>
                      <c:forEach items="${obj.airlineCompanyList }" var="each">
                      	<option value="${each.dictCode }">${each.dictCode }</option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-1 padding">
                     <select class="form-control input-sm">
                      <!-- <option>澳洲</option>
                      <option>亚洲</option> -->
                       <option value="">请选择</option>
                      <c:forEach items="${obj.areaList }" var="each">
                      	<option value="${each.areaName}">${each.areaName }</option>
                      </c:forEach>
                    </select>
                  </div>
                  <div class="col-md-1 padding">
                     <select class="form-control input-sm">
                     
                      <option>团</option>
                      <option>散</option>
                    </select>
                  </div>
                  <div class="col-md-1 padding">
                     <input type="type" class="form-control input-sm" placeholder="有效期">
                  </div>
                  <div class="col-md-1 padding">
                     <input type="type" class="form-control input-sm" placeholder="有效期">
                  </div>
                  <div class="col-md-7 padding">
                   <!--  <a href="javascript:;" class="btn btn-primary btn-sm right">上传</a> -->
                     <button id="file" name="file" type="file" class="btn btn-primary btn-sm right" >上传</button>
                  	<%-- <form action="${base}/admin/airlinepolicy/uploadFile.html" method="post" id="uploadForm" enctype="multipart/form-data">
                  		<input name="file" id="file"  type="file"/>
                  		<input type="submit" onclick="onfileChange();" value="提交">
                  	</form> --%>
                  </div>
                </div>
              </form>
            </div>
            <div class="box-body">
              <table id="poilcyManageTable" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>文件名</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
           
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

 
  <!-- <div class="control-sidebar-bg"></div> -->
</div>
<!-- ./wrapper -->

<%@include file="/WEB-INF/public/footer.jsp"%>
<!-- REQUIRED JS SCRIPTS -->
<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
<!-- jQuery 2.2.3 -->

<script>
 /*  $(function () {
    $('#poilcyManageTable').dataTable({//航空公司政策管理 table
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "columnDefs": [
            {"sWidth": "70%","aTargets": [0] },
            {"sWidth": "30%","aTargets": [1] }],
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
  }); */
</script>
<script type="text/javascript">
	//列表展示
	var empTable;
	function initDatatable() {
		empTable = $('#poilcyManageTable').DataTable({
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
	               "url": "${base}/admin/airlinepolicy/listData.html",
	               "type": "post",
	               "data": function (d) {
	            	   
	            	}
	        },
	        "columns": [
	                    {"data": "filename", "bSortable": false},
	                   
	            ],
	        columnDefs: [{
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 1,
	            render: function(data, type, row, meta) {
	            	
	            	var modify1 = '<a style="cursor:pointer;" href="'+row.pdfurl+'" target="_blank">预览</a>';
	            	var modify2 = '<a style="cursor:pointer;" onclick="previewFile('+row.id+');">编辑</a>';
	            	var modify3 = '<a style="cursor:pointer;" href="'+row.url+'">下载</a>';
	            	var modify4 = '<a style="cursor:pointer;" onclick="deleteFile('+row.id+');">删除</a>';
	                return modify1+"&nbsp; &nbsp; &nbsp;"+modify2+"&nbsp; &nbsp; &nbsp;"+modify3+"&nbsp; &nbsp; &nbsp;"+modify4;
	            }
	        }]
		});
	}
	$(function() {
		initDatatable();
		//selectDeptName();
		uploadFile();
	});
	//文件上传
	 function uploadFile(){
		$.fileupload1 = $('#file').uploadify({
			'auto' : true,//选择文件后自动上传
			'formData' : {
				'fcharset' : 'uft-8',
				'action' : 'uploadimage'
			},
			'buttonText' : '上传',//按钮显示的文字
			'fileSizeLimit' : '3000MB',
			'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
			'fileTypeExts' : '*.doc; *.xls; *.xlsx;',//上传文件的类型
			'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
			'multi' : false,//multi设置为true将允许多文件上传
			'successTimeout' : 1800,
			'queueSizeLimit' : 100,
			'uploader' : '${base}/admin/airlinepolicy/uploadFile.html',//后台处理的页面
			//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
			//下面的例子演示如何获取到vid
			 'onUploadSuccess' : function(file, data, response) {
				 
				var jsonobj = eval('(' + data + ')');
				var url  = jsonobj;//地址
				var fileName = file.name;//文件名称
				/* 解决办法，上传成功后，将文件名字和路径添加到form表单的隐藏域中，点击保存的时候将其一起提交到后台进行保存，
				保存的时候判断文件名字是否存在从而判断需不需要再次进行预览格式的转换*/
				//var id = $("input#currentDirId").val();//文件pid
				 $.ajax({
					cache : false,
					type : "POST",
					data : {
						url : url,
						fileName : fileName
					
					},
					dataType : 'json',
					url : '${base}/admin/airlinepolicy/saveUploadFile.html',
					error : function(request) {
						layer.msg('上传失败!');
					},
					success : function(data) {
					     window.parent.successCallback('6');
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					    parent.layer.close(index); 
					}
				}); 
				
				/* var innerHtml = "";
	            if (response) {
	                innerHtml = "<div><a id='downloadA' href='#' download='"+file.name+"' onclick='downloadFile("
	                        + data
	                        + ");' >"
	                        + file.name
	                        + "</a>&nbsp;&nbsp;<span>上传成功</span>&nbsp;&nbsp;&nbsp;&nbsp;"
	                        + data + "'></div>";
	            } else {
	                innerHtml = "<div>该附件上传失败，请重新上传</div>";
	            } */
	           // $("#completeFileName").html($("#completeFileName").html() + innerHtml);
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
	function successCallback(id){
		 // rebatesEamilTable.ajax.reload(null,false);
		  //rebatesReportTable.ajax.reload(null,false);
		  empTable.ajax.reload(null,false);
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
		  }else if(id == '7'){
			  layer.msg("文件下载成功!",{time: 1000, icon:1});
		  }else if(id == '8'){
			  layer.msg("添加成功!",{time: 1000, icon:1});
		  }else if(id == '9'){
			  layer.msg("编辑成功!",{time: 1000, icon:1});
		  }
	  }
	
	//删除
	function deleteFile(id){
		layer.confirm("您确认删除信息吗？", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			// 点击确定之后
			var url = '${base}/admin/airlinepolicy/delete.html';
			$.ajax({
				type : 'POST',
				data : {
					id : id
				},
				dataType : 'json',
				url : url,
				success : function(data) {
					if ("200" == data.status) {
						layer.msg("删除成功!", "", 3000);
						window.parent.successCallback('3');
						/* window.location.reload();  */
						
					} else {
						layer.msg("删除失败!", "", 3000);
					}
				},
				error : function(xhr) {
					layer.msg("删除失败!", "", 3000);
				}
			});
		}, function(){
		    // 取消之后不用处理
		});
	}
	
	
	
</script>
<!-- </body> -->
</html>
