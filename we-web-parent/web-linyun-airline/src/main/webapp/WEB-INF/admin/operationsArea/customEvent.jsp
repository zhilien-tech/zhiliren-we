<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
	<meta name="alexaVerifyID" content="" />
<title>添加</title>

<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet" type="text/css" href="${base}/public/dist/css/desktop.css">
<link rel="stylesheet" href="${base}/public/dist/css/bootstrapValidator.css" />
</head>

<body class="bodyOne">
	<div class="EventBody">
		<form id="customEventForm">
		 	<div class="col-sm-3 padding">
				<input id="msgContent" name="msgContent" type="text" class="form-control input-sm eventText" placeholder="请输入新提示事件">
			</div>
			<div class="evevtTool cf">
				<label>提醒：</label> 
				<input id="datepicker" name="generateTimeString" type="text" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="2016-12-05 13:20">
			</div>

			<div class="evevtBttton">
				<input id="submitBtn" type="button" class="btn btn-primary" onclick="save();" value="添加">
				<button type="button" class="btn btn-primary right" onclick="closewindow();">取消</button>
			</div>
		</form>
	</div>

	<!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		$(function() {
			$("#datepicker").val('${obj}'+' '+FormatDate());
			//formValidator();
		});
		
		//获得当前时间
		
		//表单校验
		$('#customEventForm').bootstrapValidator({
			message : '验证不通过',
			feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
			fields : {
				msgContent : {
					validators : {
						notEmpty : {
							message : '提醒事件不能为空'
						}
					}
				}/* ,
				generateTimeString{
					validators : {
						notEmpty : {
							message : '日期格式不正确'
						}
					}
				} */
			}
		});

		// Validate the form manually
		$('#submitBtn').click(function() {
			$('#customEventForm').bootstrapValidator('validate');
		});
		
		//保存数据
		function save() {
			$('#customEventForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#customEventForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				$.ajax({
					type : 'POST',
					url : '${base}/admin/operationsArea/add.html',
					data : $("#customEventForm").serialize(),
					success : function(data) {
						//formValidator();
						layer.msg("添加成功", "", 3000);
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.layer.close(index);
						window.parent.taskEventList();
					},
					error : function(xhr) {
						layer.msg("添加失败", "", 3000);
					}
				});
			}
		}

		
		//关闭窗口
		function closewindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
		
		
		function FormatDate () {
		    var date = new Date();
		    return zeroize(date.getHours(),2)+":"+zeroize(date.getMinutes(),2)+":"+zeroize(date.getSeconds(),2);
		}
		function zeroize(value, length) {
		    if (!length) length = 2;
		    value = String(value);
		    for (var i = 0, zeros = ''; i < (length - value.length); i++) {
		        zeros += '0';
		    }
		    return zeros + value;
		}

		
	</script>
</body>
</html>










