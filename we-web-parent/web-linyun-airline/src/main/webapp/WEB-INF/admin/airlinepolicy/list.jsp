<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/footer.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
</head>
<body>
	<!--  <button id="uploadFile" name="fileID" type="file" >上传</button> -->
	 
	 <input type="file" id="uploadFile" name="fileID"/>
	 
	 
	 <script type="text/javascript">
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
	    		'uploader' : '${base}/admin/airlinepolicy/uploadFile.html',//后台处理的页面
	    		//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
	    		//下面的例子演示如何获取到vid
	    		'onUploadSuccess' : function(file, data, response) {
	    			var jsonobj = eval('(' + data + ')');
	    			var url  = jsonobj;//地址
	    			var fileName = file.name;//文件名称
	    			/* var id = $("input#currentDirId").val();//文件pid
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
	    			}); */
	    			
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
	                //$("#completeFileName").html($("#completeFileName").html() + innerHtml);
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
	//});
	 
	 </script>
</body>
</html>