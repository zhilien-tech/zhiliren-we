<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>功能管理</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet"
	href="${base}/public/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet"
	href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/public/dist/css/skins/skin-blue.min.css">
<link rel="stylesheet"
	href="${base}/public/dist/css/skins/_all-skins.min.css">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<%@include file="/WEB-INF/public/header.jsp"%>
		<%@include file="/WEB-INF/public/aside.jsp"%>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row row-top">
					<div class="col-xs-12">
						<div class="box">
							<div class="box-header">
								<h3 class="box-title">
									&nbsp;&nbsp;<i class="fa fa-user-secret"></i>功能管理
								</h3>
							</div>
							<form id="form1"
								action="${base}/admin/authority/function/list.html"
								method="post" onsubmit="return navTabSearch(this);">
								<div class="col-md-2">
									<!--上级功能 搜索框-->
									<div class="col-sm-12 padding">
										<select name="parentId" class="form-control input-sm">
											<option value="-1">--不限--</option>
											<c:forEach items="${obj.functions}" var="pro">
												<c:choose>
													<c:when test="${pro.id == obj.queryForm.parentId}">
														<option value="${pro.id}" selected="selected">${pro.name}</option>
													</c:when>
													<c:otherwise>
														<option value="${pro.id}">${pro.name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-3 dictInfoSousuo" style="float: left;">
									<!--功能名称 搜索框-->
									<input type="text" name="typeCode"
										value="${obj.queryForm.name}" class="form-control"
										placeholder="功能名称">
								</div>
								<div class="col-md-2 col-padding">
									<!--搜索 按钮-->
									<button type="submit" class="btn btn-primary btn-sm">搜索</button>
								</div>
							</form>
							<div class="col-md-2 col-md-offset-3">
								<a href="${base}/admin/authority/function/add.html"
									data-toggle="modal" class="btn btn-primary btn-sm" id="addBtn"
									data-target="#addTabs">添加功能</a>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="example2"
									class="table table-bordered table-hover table-wid">
									<thead>
										<tr>
											<th>功能名称</th>
											<th>访问地止</th>
											<th>上级功能</th>
											<th>功能等级</th>
											<th>创建时间</th>
											<th>更新时间</th>
											<th>备注</th>
											<th>序号</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${obj.list}" var="one">
											<tr>
												<td>${one.name}</td>
												<td>${one.url}</td>
												<td>${one.parentName }</td>
												<td>${one.level}</td>
												<td><fmt:formatDate value="${one.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td><fmt:formatDate value="${one.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>${one.remark}</td>
												<td>${one.sort}</td>
												<td>
													<a href="${base}/admin/authority/function/update.html?id=${one.id}"
													   data-toggle="modal" id="editBtn" class="btn btn_mini btn_modify"
													   data-target="#editTabs">编辑</a>
												</td>  
												 <%--这里如果有写title，则需要确认才会操作 --%> 
												 <%-- <a
														href="${base}/admin/authority/function/delete?id=${one.id}"
														data-toggle="modal" id="editBtn"
														class="btn btn_mini btn_modify" data-target="#editTabs">编辑</a> --%>
												<%-- <a target="ajaxTodo" rel="dlgId1" href="${base}/admin/authority/function/delete?id=${one.id}"
												title='是否要删除' class='btn btn_mini btn_del'>删除</a></td> --%>
											</tr>
										</c:forEach>
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

		<%@include file="/WEB-INF/public/footer.jsp"%>
	</div>
	<!-- ./wrapper -->
	<!-- 添加弹框 -->
	<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1"
		aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<!-- 编辑弹框 -->
	<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1"
		aria-labelledby="myLargeModalLabel" aria-hidden="true" id="editTabs">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<!-- REQUIRED JS SCRIPTS -->
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- DataTables -->
	<script
		src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script
		src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<!-- SlimScroll -->
	<script
		src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<script>
		//添加
		$('#addBtn').click(function() {
			$(".Mymodal-lg").on("hidden", function() {
				$(this).removeData("modal");
			});
		});
		//删除提示
		function physicalDelete(did, status) {
			layer
					.confirm(
							"您确认删除(启用)信息吗？",
							{
								btn : [ "是", "否" ], //按钮
								shade : false
							//不显示遮罩
							},
							function() {
								// 点击确定之后
								var url = '${base}/admin/authority/function/updateDeleteStatus.html';
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
							}, function() {
								// 取消之后不用处理
							});
		}
		//描述提示信息弹出层Tooltip
		$(function() {
			$("[data-toggle='tooltip']").tooltip();
		});
	</script>
	<!-- 分页显示 -->
	<script type="text/javascript">
		var datatable;
		function initDatatable() {
			datatable = $('#example2').DataTable({
				"searching" : false,
				"processing" : true,
				"serverSide" : false,
				"bLengthChange" : false,
				"language" : {
					"url" : "${base}/public/plugins/datatables/cn.json"
				}
			});
		}
		$(function() {
			initDatatable();
		});
	</script>
	<script type="text/javascript">
		//状态默认选中
		function defaultSelect() {
			document.getElementById("form1").submit();
		}
	</script>
</body>
</html>