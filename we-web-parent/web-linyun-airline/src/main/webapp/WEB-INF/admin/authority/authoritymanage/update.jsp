<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>部门职位-添加</title>
<link rel="stylesheet"
	href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/public/dist/css/authoritymanage.css">
<!-- zTree -->
<link rel="stylesheet"
	href="${base }/common/js/zTree/css/zTreeStyle/zTreeStyle.css">
</head>
<body class="bodyOne">
	<div class="modal-top">
		<div class="modal-header">
			<button type="button" class="btn btn-primary right btn-sm"
				data-dismiss="modal">取消</button>
			<button type="submit" class="btn btn-primary right btn-sm"
				data-dismiss="modal">保存</button>
			<h4>添加职位部门</h4>
		</div>
		<div class="modal-body">
			<div class="departmentName">
				<!--部门权限 设置-->
				<div class="form-group row">
					<label class="col-md-2 text-right padding">部门名称：</label>
					<div class="col-md-6 padding">
						<input type="text" class="form-control input-sm inpImportant"
							placeholder="请输入部门名称"> <span class="prompt">*</span>
					</div>
					<div class="col-md-4 padding">
						<button type="button" class="btn btn-primary btn-sm btnPadding"
							id="addJob">添加职位</button>
					</div>
				</div>
			</div>
			<!--end 部门权限 设置-->
			<div class="jobName none">
				<!--职位权限 设置-->
				<div class="form-group row">
					<label class="col-md-2 text-right padding">职位名称：</label>
					<div class="col-md-6 padding">
						<input type="text" class="form-control input-sm inpImportant"
							placeholder="请输入职位名称"> <span class="prompt">*</span>
					</div>
					<div class="col-md-4 padding">
						<button type="submit" class="btn btn-primary btn-sm btnPadding"
							id="settingsPermis">设置权限</button>
						<button type="button" class="btn btn-primary btn-sm btnPadding"
							id="deleteBtn">删除</button>
					</div>
				</div>
				<div class="check_div checkTwo none">
					<div class="row">
						<div class="col-md-12">
							<input type="checkbox"><span>部门模块设置</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-3">
							<input type="checkbox"><span>个人信息</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-3">
							<input type="checkbox"><span>桌面</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-3">
							<input type="checkbox"><span>消息通知</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-3">
							<input type="checkbox"><span>权限管理：</span>
						</div>
					</div>
					<div class="row check_row_One">
						<div class="col-md-3">
							<input type="checkbox"><span>查看</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>新增</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>修改</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>删除</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-3">
							<input type="checkbox"><span>员工管理：</span>
						</div>
					</div>
					<div class="row check_row_One">
						<div class="col-md-3">
							<input type="checkbox"><span>查看</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>新增</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>修改</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>删除</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-3">
							<input type="checkbox"><span>统计</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-3">
							<input type="checkbox"><span>客户管理：</span>
						</div>
					</div>
					<div class="row check_row_One">
						<div class="col-md-3">
							<input type="checkbox"><span>查看</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>新增</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>修改</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>删除</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-12">
							<input type="checkbox"><span>航空公司模块：</span>
						</div>
					</div>
					<div class="row check_row_One">
						<div class="col-md-3">
							<input type="checkbox"><span>客户需求</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>计划制作</span>
						</div>
						<div class="col-md-3">
							<input type="checkbox"><span>excel对比</span>
						</div>
					</div>
					<div class="row check_row">
						<div class="col-md-3">
							<input type="checkbox"><span>卖票管理(待定)</span>
						</div>
					</div>
				</div>
			</div>
			<!--end 职位权限 设置-->
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			//部门职位 添加职位
			$('#addJob').click(function() {
				$('.jobName').toggle();
			});
			//部门职位 设置权限
			$('#settingsPermis').click(function() {
				$('.checkTwo').toggle();
			});
			//部门职位 删除
			$("#deleteBtn").click(function() {
				$(".jobName").hide();
			});
		});
	</script>
	<!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<!-- AdminLTE App -->
	<!--zTree -->
	<script src="${base}/common/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script src="${base}/common/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script src="${base}/common/js/zTree/jquery.ztree.exedit-3.5.js"></script>
	<!-- 功能树 -->
	<script type="text/javascript">
		
	</script>
</body>
</html>