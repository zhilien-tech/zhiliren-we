<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
 <head>
   <meta charset="UTF-8">
   <title>修改密码</title>
   <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
   <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
   <link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
   <link rel="stylesheet" href="${base}/public/dist/css/personalInfo.css"><!--本页的styleFlie-->	
 </head>
<body>
	<div class="modal-top">
		<form id="passwordForm" method="post">
		     <div class="modal-header">
		     	  <input id="userId" name="id" type="hidden" value="${obj.personalInfo[0].id}"/>
		          <button type="button" class="btn btn-primary right btn-sm returnBtn" onclick="closewindow('${obj.personalInfo[0].id}');">取消</button>
		          <button id="submit" class="btn btn-primary right btn-sm saveBtn">保存</button>
		          <h5>修改密码</h5>
		     </div>
	      	<div class="modal-body pwdBody">
	          <div class="form-group pwd">
	       		<label>用户密码：</label>
	            <input id="oldPass" name="password" type="password" placeholder="请输入旧密码"  class="form-control input-sm">
	            <input id="newPass" name="newPass" type="password" placeholder="请输入新密码" class="form-control input-sm">
	            <input id="repeatPass" name="repeatPass" type="password" placeholder="请再输入新密码" class="form-control input-sm">
	          </div>
	        </div>
        </form>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//修改密码
$("#submit").click(function() {
	var loadLayer = layer.load(0.1, {
		 shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
	$.ajax({
		type: 'POST',
		dataType : 'json',
		async: false,
		data: $("#passwordForm").serialize(),//form表单数据
		url: '${base}/admin/user/updatePassword.html',
        success: function (data) { 
        	if(data.status == '200'){
       			layer.close(loadLayer);
       			alert("修改成功!");
        		window.parent.location.href="${base}/admin/logout.html";//密码修改成功跳到登录页
			}else{
				layer.close(loadLayer) ;
				layer.msg(data.message) ;
			}
        },
        error: function (request) {
        	layer.msg("密码修改失败!","",3000);
        } 
    });
});
//验证原密码是否输入正确
/* function checkOldPass(userId){
	$.ajax({
		cache : true,
		type : "POST",
		url : '${base}/admin/user/checkOldPass.html',
		data : $('#passwordForm').serialize(),//form表单数据
		success : function(data) {
			layer.load(1, {
				 shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
            //layer.msg('密码修改成功!',{time: 5000, icon:6});
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		    parent.layer.close(index);
		    window.location.reload();
		},
		error : function(request) {
			layer.msg('原密码密码输入错误!');
		}
	});
} */
//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>