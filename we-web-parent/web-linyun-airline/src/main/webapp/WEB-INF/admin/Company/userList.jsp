<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
  <link rel="stylesheet" href="${base}/public/dist/css/skins/skin-blue.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/skins/_all-skins.min.css">
</head>
<body>
<!-- Content Wrapper. Contains page content -->
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-user-secret"></i> 公司员工</h3>
               <form role="form" class="form-horizontal">
              <div class="form-group row form-right">
             
                
                <div class="col-md-3"><!--公司名称/负责人/电话 搜索框-->
                  <input type="text" name="userName" class="form-control" placeholder="员工姓名/电话" >
                </div>
                <div class="col-md-2 col-padding"><!--搜索 恢复默认 按钮-->
                  <button type="button" class="btn btn-primary btn-sm">搜索</button>
                </div>
              
                <div class="col-md-1 col-md-offset-2">
                </div>
				
              </div>
              </form>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>员工姓名</th>
                  <th>部门</th>
                  <th>职位</th>
                  <th>电话</th>
                  <th>是否从本公司移除</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${obj.list}" var="one" >
						<tr>
							<td>${one.userName}</td>
							<td>${one.department}</td>
							<td>${one.zhiwei}</td>
							<td>${one.telephone}</td>
							<td>
								<a href="${base }/admin/userjob/update.html?id=${one.ujid}&status=0" data-toggle="modal">是</a>
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
  <!-- /.content-wrapper -->

</div>
<!-- ./wrapper -->

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
  
</script>

</body>
</html>