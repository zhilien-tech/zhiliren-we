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
  <title>员工管理</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="${base}/public/font-awesome-4.4.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base}/public/ionicons-2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/dist/css/skins/skin-blue.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/skins/_all-skins.min.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-users"></i> 员工管理</h3>
               <form role="form" class="form-horizontal">
              <div class="form-group row marginBott cf">
                <div class="col-md-2"><!--部门 下拉框-->
                  <select class="form-control selePadd5">
                    <option>部门</option>
                    <option>客户部</option>
                    <option>销售部</option>
                  </select>
                </div>
                <div class="col-md-3"><!--姓名/联系电话 搜索框-->
                  <input type="text" class="form-control" placeholder="姓名/联系电话">
                </div>
                <div class="col-md-1 col-padding"><!--搜索 按钮-->
                  <button type="button" class="btn btn-primary btn-sm">搜索</button>
                </div>
              
                <div class="col-md-1 col-md-offset-5">
                  <a href="${base}/admin/user/add.html"
					data-toggle="modal" class="btn btn-primary btn-sm" id="addBtn"
					data-target="#addTabs">添加</a>
                </div>

              </div>
              </form>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>姓名</th>
                  <th>联系电话</th>
                  <th>部门</th>
                  <th>职位</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                  <c:forEach items="${obj.list}" var="one" >
						<tr>
							<td>${one.userName}</td>
							<td>${one.telephone}</td>
							<td>${one.qq}</td>
							<td>${one.email}</td>
							<td>
								<a href="${base}/admin/user/update.html?id=${one.id}"
									data-toggle="modal" id="editBtn"
									class="btn btn_mini btn_modify" data-target="#editTabs">编辑</a>
							</td>
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
<script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${base}/public/dist/js/app.min.js"></script>
<!-- page script -->
<script type="text/javascript">
		var datatable;
		function initDatatable() {
			datatable = $('#example2').DataTable({
				"searching" : false,
				"processing" : true,
				"serverSide" : false,
				"bLengthChange" : false,
				"bSort": true, //排序功能 
				"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
				}
			});
		}
		$(function() {
			initDatatable();
		});
	</script>
</body>
</html>