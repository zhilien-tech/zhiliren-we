<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>	
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>字典信息</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
  <link rel="stylesheet" href="${base}/public/dist/css/skins/skin-blue.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/skins/_all-skins.min.css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
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
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-user-secret"></i>字典信息</h3>
            </div>
            <form action="${base}/admin/dictionary/dirinfo/list.html" method="post" onsubmit="return navTabSearch(this);">
				 <div class="col-md-3"><!--字典信息名称 搜索框-->
            		  <input type="text" name="dictName" value="${obj.queryForm.dictName}" class="form-control" placeholder="字典信息名称">
          		  </div>
				 <div class="col-md-2 col-padding"><!--搜索 按钮-->
              		<button type="submit" class="btn btn-primary btn-sm">搜索</button>
           		 </div>
			</form>
            <div class="col-md-1 col-md-offset-6">
           		<a href="${base}/admin/dictionary/dirinfo/add.html" data-toggle="modal" 
           	class="btn btn-primary btn-sm" id="addBtn" data-target=".Mymodal-lg">添加</a>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  	<th>字典类别编码</th>
					<th>字典代码</th>
					<th>字典信息</th>
					<th>描述</th>
					<th>状态</th>
					<th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${obj.list}" var="one" >
						<tr>
						    <td>${one.typeCode}</td>
							<td>${one.dictCode}</td>
							<td>${one.dictName }</td>
							<td>${one.description }</td>
							<c:if test="${one.status eq 0}"> 
			     				<td>冻结</td>
							</c:if>
							<c:if test="${one.status eq 1}"> 
			     				<td>启用</td>
							</c:if>
							<c:if test="${one.status eq 2}"> 
			     				<td>删除</td>
							</c:if>
							<td>
								<a href="${base}/admin/dictionary/dirinfo/update.html?id=${one.id}" data-toggle="modal" 
           					 id="addBtn" data-target=".Mymodal-lg">编辑</a>
								<%--
									这里如果有写title，则需要确认才会操作
								 --%>
								<a id="deleteBtn" href="${base}/admin/dictionary/dirinfo/delete?id=${one.id}" title='是否要删除' class='btn btn_mini btn_del'>删除</a>
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
</div>
<!-- ./wrapper -->
<!-- 弹框 -->
	<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs">
      <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
              </div>
                <div class="modal-body">
                </div>
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
<!-- AdminLTE for demo purposes -->
<script src="${base}/public/dist/js/demo.js"></script>
<!-- page script -->
<script>
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
  //添加
  $('#addBtn').click(function(){
      $(".Mymodal-lg").on("hidden", function() {
          $(this).removeData("modal");
      });
 })
</script>
</body>
</html>
