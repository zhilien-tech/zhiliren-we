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
    <title>权限管理</title>
    <!-- 响应式布局 width显示 -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
  	<link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${base}/public/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="${base}/public/dist/css/skins/_all-skins.min.css">
  </head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <!--内容-->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
      <div class="row row-top">
        <div class="col-xs-12">
             <h4 class="page-header"><i class="fa fa-eye"></i> 权限管理</h4>
              <div class="row">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">部门职位</a></li>
                      <li><a href="#tab_2" data-toggle="tab">区域</a></li>
                    </ul>
                    <div class="tab-content">
                      <!--部门职位-->
                     <form id="deptJobAuthorityForm" action="${base}/admin/authority/authoritymanage/list.html" method="post" onsubmit="return navTabSearch(this);">
	                      <div class="tab-pane pane-content active" id="tab_1">
	                         <!-- <button type="button" class="btn btn-primary btn-sm qxglAddbtn">添加</button>  -->
	                         <!-- <a href="job-add.html" data-toggle="modal" class="btn btn-primary btn-sm qxglAddbtn" id="deparPositAdd" data-target="#departmentPositionAdd">添加</a> -->
	                         <div class="col-md-1 col-md-offset-4">
								<a href="${base}/admin/authority/authoritymanage/add.html"
									data-toggle="modal" class="btn btn-primary btn-sm" id="addBtn"
									data-target="#addTabs">添加</a>
							</div>
	                         <table id="example2" class="table table-bordered table-hover">
		                          <thead>
		                          <tr>
		                            <th>部门名称</th>
		                            <th>模块</th>
		                            <th>职位名称</th>11111
		                            <th>操作</th>
		                          </tr>
		                          </thead>
	                          <tbody>
	                            <c:forEach items="${obj.list}" var="one" >
									<tr>
										<td>${one.deptId}</td>
										<td>${one.moduleName}</td>
										<td>${one.jobId}</td>
										<td>${one.remark}</td>
										<td>
										<a target="dialog" rel="user_update" href="${base}/admin/authority/job/update.html?id=${one.id}" class="btn btn_mini btn_modify">编辑</a>
									</tr>
								</c:forEach>
	                        </table>
	                      </div><!--end 部门职位-->
						</form>
                      <!--区域-->
                      <form id="areaListForm" action="${base}/admin/area/list.html" method="post" onsubmit="return navTabSearch(this);">
	                      <div class="tab-pane pane-content" id="tab_2">
	                        <!-- <button type="button" class="btn btn-primary btn-sm qxglAddbtn">添加</button> -->
							             <a href="area-add.html" data-toggle="modal" class="btn btn-primary btn-sm qxglAddbtn" id="addBtn" data-target="#areaAdd">添加</a>
	                        <table id="example3" class="table table-bordered table-hover">
	                          <thead>
	                          <tr>
	                            <th>区域名称</th>
	                            <th>操作</th>
	                          </tr>
	                          </thead>
	                          <tbody>
	                            <tr>
	                              <td>美国</td>
	                              <td><a href="#addTabs" data-toggle="modal">编辑</a></td>
	                            </tr>
	                          </tbody>
	                        </table>
	                      </div><!--end 区域-->
						</form>
                    </div><!-- end tab-content -->
                  </div><!-- end nav-tabs-custom -->
                </div><!-- end col-md-12 -->
              </div><!-- end row -->
        </div>
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!--end 内容-->

  <!-- Main footer -->
   <%@include file="/WEB-INF/public/footer.jsp"%>
  <!--end footer-->
</div>
<!-- ./wrapper -->

<!--部门职位 添加弹框 -->
  <div class="modal fade Mymodal-lg" role="dialog" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="departmentPositionAdd">
    <div class="modal-dialog modal-lg positiTop">
      <div class="modal-content">
        <!-- <div class="modal-header">
            <button type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">取消</button>
            <button type="submit" class="btn btn-primary right btn-sm" data-dismiss="modal">保存</button>
            <h4>添加职位部门</h4>
       </div> -->
        <!-- <div class="modal-body">
          <div class="departmentName"><!--部门权限 设置-->
            <!--<div class="form-group row">
               <label class="col-md-2 text-right padding">部门名称：</label>
               <div class="col-md-6 padding">
                 <input type="text" class="form-control input-sm inpImportant" placeholder="请输入部门名称">
                 <span class="prompt">*</span>
               </div>
               <div class="col-md-4 padding">
                 <button type="submit" class="btn btn-primary btn-sm btnPadding" id="settingsModule">设置模块</button>
                 <button type="button" class="btn btn-primary btn-sm btnPadding" id="addJob">添加职位</button>
               </div>
            </div>
            <div class="check_div checkOne none">
              <div class="row">
                <div class="col-md-12"><input type="checkbox"><span>部门模块设置</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>个人信息</span></div>
                <div class="col-md-3"><input type="checkbox"><span>桌面</span></div>
                <div class="col-md-3"><input type="checkbox"><span>消息通知</span></div>
                <div class="col-md-3"><input type="checkbox"><span>权限管理</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>员工管理</span></div>
                <div class="col-md-3"><input type="checkbox"><span>统计</span></div>
                <div class="col-md-3"><input type="checkbox"><span>客户管理</span></div>
                <div class="col-md-3"><input type="checkbox"><span>航空公司</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>卖票管理</span></div>
                <div class="col-md-3"></div>
                <div class="col-md-3"></div>
                <div class="col-md-3"></div>
              </div>
            </div>
          </div><!--end 部门权限 设置-->
          <!--<div class="jobName none"><!--职位权限 设置-->
            <!--<div class="form-group row">
               <label class="col-md-2 text-right padding">职位名称：</label>
               <div class="col-md-6 padding">
                 <input type="text" class="form-control input-sm inpImportant" placeholder="请输入职位名称">
               </div>
               <div class="col-md-4 padding">
                 <button type="submit" class="btn btn-primary btn-sm btnPadding" id="settingsPermis">设置权限</button>
                 <button type="button" class="btn btn-primary btn-sm btnPadding" id="deleteBtn">删除</button>
               </div>
            </div>
            <div class="check_div checkTwo none">
              <div class="row">
                <div class="col-md-12"><input type="checkbox"><span>部门模块设置</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>个人信息</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>桌面</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>消息通知</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>权限管理：</span></div>
              </div>
              <div class="row check_row_One">
                <div class="col-md-3"><input type="checkbox"><span>查看</span></div>
                <div class="col-md-3"><input type="checkbox"><span>新增</span></div>
                <div class="col-md-3"><input type="checkbox"><span>修改</span></div>
                <div class="col-md-3"><input type="checkbox"><span>删除</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>员工管理：</span></div>
              </div>
              <div class="row check_row_One">
                <div class="col-md-3"><input type="checkbox"><span>查看</span></div>
                <div class="col-md-3"><input type="checkbox"><span>新增</span></div>
                <div class="col-md-3"><input type="checkbox"><span>修改</span></div>
                <div class="col-md-3"><input type="checkbox"><span>删除</span></div>
              </div>
               <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>统计</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>客户管理：</span></div>
              </div>
              <div class="row check_row_One">
                <div class="col-md-3"><input type="checkbox"><span>查看</span></div>
                <div class="col-md-3"><input type="checkbox"><span>新增</span></div>
                <div class="col-md-3"><input type="checkbox"><span>修改</span></div>
                <div class="col-md-3"><input type="checkbox"><span>删除</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-12"><input type="checkbox"><span>航空公司模块：</span></div>
              </div>
              <div class="row check_row_One">
                <div class="col-md-3"><input type="checkbox"><span>客户需求</span></div>
                <div class="col-md-3"><input type="checkbox"><span>计划制作</span></div>
                <div class="col-md-3"><input type="checkbox"><span>excel对比</span></div>
              </div>
              <div class="row check_row">
                <div class="col-md-3"><input type="checkbox"><span>卖票管理(待定)</span></div>
              </div>
            </div>
          </div><!--end 职位权限 设置-->
        <!--</div> -->
      </div>
    </div>
  </div>

<!--区域 添加弹框 -->
  <div class="modal fade Mymodal-lg" role="dialog" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="areaAdd">
    <div class="modal-dialog modal-lg modal-height">
      <div class="modal-content modal-height">
        <div class="modal-header"></div>
        <div class="modal-body"></div>
      </div>
    </div>
  </div>

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
<script>
$(function () {
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
	//初始化
	$(function() {
		initDatatable();
	});
    $('#example3').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false
    });

    //部门职位 添加 弹框
    $('#deparPositAdd').click(function() {
      $("#departmentPositionAdd").on("hidden", function() {
        $(this).removeData("modal");
      });
    });

    //部门职位 设置模块
    // $('#settingsModule').click(function(){
    //   $('.checkOne').slideToggle("slow");
    //   $('.checkTwo').hide();
    // })

    //部门职位 添加职位
    // $('#addJob').click(function(){
    //   $('.jobName').slideDown("slow");
    // })

    //部门职位 设置权限
    // $('#settingsPermis').click(function(){
    //   $('.checkTwo').slideToggle("slow");
    //   $('.checkOne').hide();
    // })

    //部门职位 删除
     // $("#deleteBtn").click(function(){
     //    $(".jobName").hide();
     //  });
   //区域 添加 弹框
    $('#areaAdd').click(function() {
      $(".Mymodal-lg").on("hidden", function() {
        $(this).removeData("modal");
      });
    });

  });
</script>
</body>
</html>