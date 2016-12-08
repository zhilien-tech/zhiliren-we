<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" type="text/css" href="${base}/public/dist/css/desktop.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css" />
<link rel="stylesheet" href="${base}/public/css/pikaday.css">

</head>
<body class="bodyOne">
	<div class="EventBody">
		<form id="customEventForm">
			<input id="msgContent" name="msgContent" type="text"
				class="form-control input-sm eventText" placeholder="新提示事件">
			<div class="evevtTool cf">
				<label>提醒：</label> 
				<input id="datepicker" name="generateTimeString" type="text" class="form-control input-sm" placeholder="2016-12-05 13:20">
			</div>

			<!-- 隐藏域 -->
			<!-- 发送方id  自定义事件中，发送方接收方都是自身   目前假设为1,以后会设置为当前登陆者 -->
			<input id="sendUserId" name="sendUserId" type="hidden" value="1" />
			<!-- 发送方类型 -->
			<input id="sendUserType" name="sendUserType" type="hidden" />

			<!-- 接收方id  自定义事件中，发送方接收方都是自身 -->
			<input id="recUserId" name="recUserId" type="hidden" value="1" />
			<!-- 接收方类型 -->
			<input id="recUserType" name="recUserType" type="hidden" />

			<div class="evevtBttton">
				<button id="submitBtn" type="button" class="btn btn-primary"
					onclick="save();">添加</button>
				<button type="button" class="btn btn-primary right"
					onclick="closewindow();">取消</button>
			</div>
		</form>
	</div>

	<!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/pikaday.js"></script>
	<!-- pikaDay 日历控件 -->
	<script src="${base}/admin/operationsArea/addCalendar.js"></script>
	<!-- js文件 -->
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		$(function() {
			$("#datepicker").val('${obj}');
			$('#customEventForm').bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					msgContent : {
						validators : {
							notEmpty : {
								message : '提醒事件不能为空!'
							}
						}
					}
				}
			});
		});

		//保存数据
		function save() {
			$('#customEventForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#customEventForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				$.ajax({
					cache : true,
					type : 'POST',
					data : $("#customEventForm").serialize(),
					url : '${base}/admin/operationsArea/add.html',
					success : function(data) {
						layer.msg("添加成功", {
							time : 5000,
							icon : 1
						});
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.layer.close(index);
					},
					error : function(xhr) {
						layer.msg("添加失败", "", 3000);
					}
				});
			}
		}

		// Validate the form manually
		$('#submitBtn').click(function() {
			$('#customEventForm').bootstrapValidator('validate');
		});
		
		//关闭窗口
		function closewindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>










