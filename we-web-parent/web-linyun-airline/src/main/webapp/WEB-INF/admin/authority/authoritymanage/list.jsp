<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%> 
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>权限管理</title>
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
                      <div class="tab-pane pane-content active" id="tab_1">
                         <div class="col-md-1 col-md-offset-11">
								<%-- <a href="${base}/admin/authority/authoritymanage/add.html"
									data-toggle="modal" class="btn btn-primary btn-sm" id="addDeptBtn"
									data-target="#addDeptTabs">添加</a> --%>
								<a id="addDeptBtn" class="btn btn-primary btn-sm" onclick="addDept();">添加</a>
						 </div>
                         <table id="deptDatatable" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                            <th>部门名称</th>
                            <th>模块</th>
                            <th>职位名称</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          </tbody>
                        </table>
                      </div><!--end 部门职位-->
                      <!--区域-->
                      <div class="tab-pane pane-content" id="tab_2">
	                    <div class="col-md-1 col-md-offset-11">
	                        <a href="${base}/admin/area/add.html"
										data-toggle="modal" class="btn btn-primary btn-sm" id="addAreaBtn"
										data-target="#addAreaTabs">添加</a>
						</div>
                        <table id="areaDatatable" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                            <th>区域名称</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td>美国</td>
                              <td>
                              	<a href="${base}/admin/area/update.html?id=${one.id}"
										data-toggle="modal" id="editAreaBtn"
										class="btn btn_mini btn_modify" data-target="#editDeptTabs">编辑</a>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div><!--end 区域-->

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
<script type="text/javascript">
	//添加部门职位
	function addDept(){
      layer.open({
    	    type: 2,
    	    title:false,
    	    closeBtn:false,
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    area: ['900px', '500px'],
    	    content: '${base}/admin/authority/authoritymanage/add.html',
    	    end: function(){//添加完页面点击返回的时候自动加载表格数据
    	    	parent.location.reload();
    	    }
   	 	 });
		 }
	//编辑
	function edit(id){
		  layer.open({
	  	    type: 2,
	  	    title: false,
	  	  	closeBtn:false,
	  	    fix: false,
	  	    maxmin: false,
	  	    shadeClose: false,
	  	    area: ['900px', '400px'],
	  	    content: '${base}/admin/dictionary/dirinfo/update.html?id='+id
	  	  });
	  }
	//事件提示
	function successCallback(id){
		deptDatatable.ajax.reload();
		  if(id == '1'){
			  layer.msg("添加成功",{time: 2000, icon:1});
		  }else if(id == '2'){
			  layer.msg("修改成功",{time: 2000, icon:1});
		  }else if(id == '3'){
			  layer.msg("删除成功",{time: 2000, icon:1});
		  }
	  }
	//删除提示
	function physicalDelete(did, status) {
		if(2==status){
			var zt = "删除";
		}else if(1==status){
			var zt = "启用";
		}
		layer.confirm("您确认"+zt+"信息吗？", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			// 点击确定之后
			var url = '${base}/admin/dictionary/dirinfo/updateDeleteStatus.html';
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
		}, function(){
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
	var deptDatatable;
	function initDatatable() {
		deptDatatable = $('#deptDatatable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"bLengthChange" : false,
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			},
           	"ajax": {
                   "url": "${base}/admin/authority/authoritymanage/listData.html",
                   "type": "post",
                   "data": function (d) {
                	   
                	}
	        },
	        "columns": [
	                    {"data": "deptName", "bSortable": false},
	                    {"data": "moduleName", "bSortable": false,
	                    	render: function(data, type, row, meta) {
	                    		if(row.moduleName==null){
	                    			return "";
	                    		}else{
	                    			return row.moduleName;
	                    		}
	                    	}
	                    },
	                    {"data": "name", "bSortable": false}
	            ],
            columnDefs: [{
                //   指定第一列，从0开始，0表示第一列，1表示第二列……
                targets: 3,
                render: function(data, type, row, meta) {
                	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
                    return modify;
                }
            }]
		});
	}
	$(function() {
		initDatatable();
	});
</script>
</body>
</html>