<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>

<c:set var="url" value="${base}/admin/Company" />
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-user-secret"></i> 公司管理</h3>
               <form role="form" class="form-horizontal">
              <div class="form-group row form-right">
             
                
                <div class="col-md-3"><!--公司名称/负责人/电话 搜索框-->
                  <input type="text" name="companyName" class="form-control" placeholder="公司名称/负责人/电话" value="${obj.queryForm.companyName}">
                </div>
                <div class="col-md-2 col-padding"><!--搜索 恢复默认 按钮-->
                  <button type="button" class="btn btn-primary btn-sm">搜索</button>
                </div>
              
                <div class="col-md-1 col-md-offset-6">
                  <!-- <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" >添加</button> -->
                  <%-- <button type="button" class="btn btn-primary btn-sm" onClick="window.open('${url}/add.html', 'newwindow', 'height=500, width=800, top=120, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">添加</button> --%>
                  <%-- <a data-toggle="modal" href="${url }/add.html" data-target="#addTabs">添加</a> --%>
                  <a class="btn btn-primary btn-sm" onclick="add();">添加</a>
                </div>
				
              </div>
              </form>
            </div>
            <h4>全部公司：100 上游公司：80 代理商：20</h4>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>公司名称</th>
                  <th>联系人</th>
                  <th>联系方式</th>
                  <th>员工人数</th>
                  <th>公司类型</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${obj.list}" var="one" >
						<tr>
							<td>${one.comName}</td>
							<td>${one.connect}</td>
							<td>${one.mobile}</td>
							<td>
								<c:if test="${empty one.renshu}">
									0
								</c:if>
								<c:if test="${not empty one.renshu}">
									<a  style="cursor:pointer;" onclick="userlist(${one.id})">${one.renshu }</a>
								</c:if>
							</td>
							<td>${one.comType}</td>
							<td>
								<a class="btn btn-primary btn-sm" onclick="edit(${one.id});">编辑</a>
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

<!--弹框 div-->
<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs" style="width:auto;height:1000px;"> 
      <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
              </div>
                <div class="modal-body">
                </div>
            </div>
        </div>
    </div>

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
   function add(){
      layer.open({
    	    type: 2,
    	    title: '公司信息添加',
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    area: ['900px', '650px'],
    	    content: '${url}/add.html'
    	    
    	  });
  }
  
  function edit(id){
	  layer.open({
  	    type: 2,
  	    title: '公司信息修改',
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['900px', '650px'],
  	    content: '${url}/update.html?id='+id
  	  });
  }
  function userlist(id){
	  layer.open({
  	    type: 2,
  	    title: '公司信息修改',
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['900px', '600px'],
  	    content: '${url}/userList.html?id='+id
  	    
  	  });
  }
</script>

