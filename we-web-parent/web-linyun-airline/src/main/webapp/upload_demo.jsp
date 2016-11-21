<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta http-equiv="Access-Control-Allow-Origin" content="*" />
<meta name="alexaVerifyID" content="" />
<title>凌云航票系统</title>
</head>
<body>

<div class="panel_box">
	<div class="panel_content nopadding">
		<div class="form_item">
			<label class="form_label">图片：</label>
			<div class="form_ctrl" >
				<input type="hidden" id="webupload_picture" name="cover" value=""/>
		        <div class="wu-example" id="uploader_00" style="width:300px;height:200px;">
		        	<div id="imgUrlMessage" name="imgUrlMessage"></div> 
		        </div>
			</div>
		</div>
	</div>
</div>

<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<jsp:include page="/WEB-INF/common/webupload_resource.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function(){
		if($("#uploader_00").length>0){
			inituploader("","00",[]);
		}
	});
</script>
</body>
</html>