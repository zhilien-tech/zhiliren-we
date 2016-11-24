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
  <title>字典类型</title>
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
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-user-secret"></i>字典类型</h3>
            </div>
            <form action="${base}/admin/dictionary/dirtype/list.html" method="post" onsubmit="return navTabSearch(this);">
				  <div class="col-md-2"><!--状态名称 搜索框-->
          			<div class="col-sm-12 padding">
                      <select id="status" name="status" class="form-control input-sm">
     						<option value="">--不限--</option>
								<c:forEach var="map" items="${obj.dataStatusEnum}" >
									<c:choose>
									   <c:when test="${map.key == obj.queryForm.status}">
									   		<option value="${map.key}" selected="selected">${map.value}</option>
									   </c:when>
									   <c:otherwise>
									   		<option value="${map.key}">${map.value}</option>
									   </c:otherwise>
								</c:choose>
							</c:forEach>
					  </select>
                    </div>
          		 </div>
				 
				 <div class="col-md-3 dictInfoSousuo"><!--字典类别名称 搜索框-->
            		  <input type="text" name="typeName" value="${obj.queryForm.typeName}" class="form-control" placeholder="字典类别名称">
          		 </div>
				 <div class="col-md-2 col-padding"><!--搜索 按钮-->
              		<button type="submit" class="btn btn-primary btn-sm">搜索</button>
           		 </div>
			</form>
            <div class="col-md-1 col-md-offset-4">
           		<a href="${base}/admin/dictionary/dirtype/add.html" data-toggle="modal" 
           	class="btn btn-primary btn-sm" id="addBtn" data-target=".Mymodal-lg">添加</a>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  	<th>字典类别编码</th>
					<th>字典类别名称</th>
					<th>描述</th>
					<th>状态</th>
					<th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${obj.list}" var="one" >
					<tr>
						<td>${one.typeCode}</td>
						<td>${one.typeName }</td>
						<td><span data-toggle="tooltip" data-placement="right" title="${one.description }">${one.description }<span></td>
						<td><we:enum key="${one.status }" className="com.linyun.airline.common.enums.DataStatusEnum"/></td>
						<td>
							<a href="${base}/admin/dictionary/dirtype/update.html?id=${one.id}" data-toggle="modal" 
           					 id="addBtn" class="btn btn-primary btn-sm" data-target=".Mymodal-lg">编辑</a>
							<%--这里如果有写title，则需要确认才会操作--%>
							<c:choose>
							   <c:when test="${1 == one.status}">
							   		<a href='javascript:physicalDelete(${one.id},2);' class='btn btn-danger btn-sm'>删除</a>
							   </c:when>
							   <c:otherwise>
							   		<a href='javascript:physicalDelete(${one.id},1);' class='btn btn-success btn-sm'>启用</a>
							   </c:otherwise>
							</c:choose>
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
<script src="${base}/common/js/layer/layer.js"></script>
<!-- page script -->
<script type="text/javascript">
  //添加
  $('#addBtn').click(function(){
      $(".Mymodal-lg").on("hidden", function() {
          $(this).removeData("modal");
      });
 });
 //删除提示
function physicalDelete(did,status){
		$.ajax({ 
			type: 'POST', 
			data: {id:did,status:status}, 
			dataType:'json',   
			url: '${base}/admin/dictionary/dirtype/updateDeleteStatus.html',
	           success: function (data) { 
	           	if("200" == data.status){
	           		layer.msg("操作成功!","",3000);
	           		window.location.reload(true);
	           	}else{
	           		layer.msg("操作失败!","",3000);
	           	}
	           },
	           error: function (xhr) {
	           	layer.msg("操作失败","",3000);
	           } 
       });
	}
  //描述提示信息弹出层Tooltip 
  $(function () { 
	  $("[data-toggle='tooltip']").tooltip();
	});

</script>
<script type="text/javascript">
var datatable;
function initDatatable() {
    datatable = $('#example2').DataTable({
    	"searching":false,
        "processing": true,
        "serverSide": false,
        "bLengthChange": false,
        "language": {
            "url": "${base}/public/plugins/datatables/cn.json"
        }
    });
}
$(function () {
    initDatatable();
});
</script>
</body>
</html>
