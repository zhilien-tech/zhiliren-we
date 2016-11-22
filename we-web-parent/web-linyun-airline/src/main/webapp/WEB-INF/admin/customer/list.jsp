<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/customer" />

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>客户管理</title>
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
<!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
<link rel="stylesheet"
	href="${base}/public/dist/css/skins/skin-blue.min.css">
<link rel="stylesheet"
	href="${base}/public/dist/css/skins/_all-skins.min.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
<script
	src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${base}/public/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${base}/public/dist/js/demo.js"></script>
<!-- page script -->
<script type="text/javascript">
  $(function () {
    $("#example1").DataTable();
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false
    });
  });
  
  function makeDefault(){
	  $("#sname").val("");
	  $("#select1").val("-1");
	  $("#select2").val("-1");
  }
 
  $('#addBtn').click(function(){
      $(".Mymodal-lg").on("hidden", function() {
          $(this).removeData("modal");
      });
  });
  
  $('#updateBtn').click(function(){
      $(".Mymodal-lg").on("hidden", function() {
          $(this).removeData("modal");
      });
  });
 
  
</script>
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
									&nbsp;&nbsp;<i class="fa fa-user-secret"></i> 客户管理
								</h3>
								<form role="form" class="form-horizontal"
									action="${base}/admin/customer/list.html" method="post"
									onsubmit="return navTabSearch(this);">
									<div class="form-group row form-right">

										<div class="col-md-2">
											<!--是否签约 下拉框-->
											<select id="select1" class="form-control select"
												name="contract" <!-- onchange="alert($(this).val())" -->>
												<option value="-1">是否签约</option>
												<option value="0">未签约</option>
												<option value="1">已签约</option>
												<option value="2">禁止合作</option>
											</select>
										</div>
										<div class="col-md-2">
											<!--是否禁用 下拉框-->
											<select id="select2" class="form-control select"
												name="forbid">
												<option value="-1">是否禁用</option>
												<option value="1">是</option>
												<option value="0">否</option>
											</select>
										</div>
										<div class="col-md-3">
											<!--公司名称/负责人/电话 搜索框-->
											<input type="text" id="sname" name="name"
												class="form-control" value="" placeholder="公司名称/负责人/电话">
										</div>
										<div class="col-md-2 col-padding">
											<!--搜索 恢复默认 按钮-->
											<button type="submit" class="btn btn-primary btn-sm">搜索</button>
											<button type="button" class="btn btn-primary btn-sm btn-left"
												onclick="makeDefault()">恢复默认</button>
										</div>


										<div class="col-md-1 col-md-offset-2">
											<a href="${base}/admin/customer/add.html" data-toggle="modal"
												class="btn btn-primary btn-sm" id="addBtn"
												data-target=".Mymodal-lg">添加</a>
										</div>

									</div>
								</form>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="example2" class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>序号</th>
											<th>公司名称</th>
											<th>负责人</th>
											<th>联系电话</th>
											<th>签约状态</th>
											<th>签约到期日</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${obj.list}" var="one">
											<tr>
												<td class="txtc"><input name="ids" value="${one.id}"
													type="checkbox" class="ipt_checkbox">&nbsp;&nbsp;&nbsp;${one.id}
												</td>
												<td>${one.name}</td>
												<td>${one.linkMan}</td>
												<td>${one.telephone}</td>
												<td><c:if test="${one.contract==1 }">已签约</c:if> <c:if
														test="${one.contract==0 }">未签约</c:if></td>
												<td><fmt:formatDate value="${one.contractDueTime}"
														pattern="yyyy-MM-dd" /></td>
												<td><a target="dialog" rel="dlgId1"
													href="${base}/admin/customer/update.html?id=${one.id}"
													id="updateBtn" class="btn btn_mini btn_modify"
													data-target=".Mymodal-lg" data-toggle="modal">修改</a> <a
													target="ajaxTodo" rel="dlgId1"
													href="${url}/delete?id=${one.id}" title='是否要删除'
													class='btn btn_mini btn_del'>删除</a></td>
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

		<!-- Main Footer -->
		<footer class="main-footer">
			<!-- To the right -->
			<div class="pull-right hidden-xs">
				<!-- Anything you want -->
			</div>
			<!-- Default to the left -->
			<strong>版权 &copy; 2016 <a href="#">北京XXX科技有限公司</a>.
			</strong> 保留所有权利.
		</footer>
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!--弹框 div-->
	<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1"
		aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs"
		style="width: auto; height: 1000px;">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header"></div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>

</body>
</html>
