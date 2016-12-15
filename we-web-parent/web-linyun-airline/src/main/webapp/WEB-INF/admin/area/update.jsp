<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑区域</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<style type="text/css">
.select2-container {
	width: 95.5% !important;
	display: inline-block;
}

.seleSpanWid .select2-container {
	width: 98.5% !important;
	display: inline-block;
}

.inpNone .select2 .selection span ul li+li {
	display: none;
}
</style>
</head>
<body>
	<div class="modal-top">
		<form id="editAreaForm" method="post">
		     <div class="modal-header">
		          <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
		          <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
		          <button type="submit" class="btn right btn-sm" data-dismiss="modal">删除</button>
		          <h4>编辑区域</h4>
		     </div>
		      <div class="modal-body">
		          <div class="form-group">
		             <div class="col-md-8 col-md-offset-2">
		                <%-- <input id="areaName" name="areaName" value="${obj.areaName }" type="text" placeholder="请输入区域名称" class="form-control input-sm"> --%>
		             	<select id="areaName" name="dictAreaName" onchange="editInput();"
							class="form-control select2 inpImpWid" multiple="multiple"
							value="${obj.areaName }">
						</select><span class="prompt">*</span>
						<!-- 字典信息ID -->
						<input id="infoId" type="hidden" name="infoId" />
						<!-- 字典信息区域名称 -->
						<input id="dictAreaName" type="hidden" name="areaName" />
		             </div>
		          </div>
		          <!-- 字典信息OnChange事件 -->
					<script type="text/javascript">
						function editInput() {
							//代理商公司ID
							var selectedAreaName = $("#areaName").select2("val");
							$("#infoId").val(selectedAreaName);
							//公司名称
							var selectedDictAreaName = $('#areaName').find("option:selected").text();
							$("#dictAreaName").val(selectedDictAreaName);
						}
					</script>
		      </div>
		</form>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<!-- 页面select2下拉框js -->
	<script src="${base}/admin/areaSelect2/area.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//验证
$(document).ready(function(){
	$('#addDeptForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	deptName: {
                validators: {
                    notEmpty: {
                        message: '部门名称不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/area/checkAreaNameExist.html',//验证地址
                         message: '部门名称已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	areaName:$('input#areaName').val(),
                            	id:'${obj.id}'
                            };
                         }
                     }
                }
            }
        }
	});
});
//编辑保存
$("#submit").click(function(){
	$('#editAreaForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#editAreaForm").data('bootstrapValidator');
	var _areaName = $("input#areaName").val();
	if(bootstrapValidator.isValid()){
		$.ajax({
           cache: true,
           type: "POST",
           url:'${base}/admin/area/update.html',
           data:{
				areaName:_areaName,
				id:'${obj.id}'
		   },
           error: function(request) {
              layer.msg('编辑失败!');
           },
            success: function(data) {
			layer.load(1, {
				 shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
              layer.msg('编辑成功!',{time: 5000, icon:6});
			  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			  parent.layer.close(index);
			  window.location.reload(true);
           }
       });
	}
	 $(".Mymodal-lg").modal('hide');
});
//提交时开始验证
$('#submit').click(function() {
    $('#editAreaForm').bootstrapValidator('validate');
});
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>	