<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<c:set var="url" value="${base}/admin/flightinfo" />
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-users"></i> 航班管理</h3>
              <div class="form-group row form-right">
             
                
                <div class="col-md-3"><!--公司名称/负责人/电话 搜索框-->
                  <input type="text" name="companyName" id="companyName" class="form-control" placeholder="公司名称/负责人/电话" onkeypress="onkeyEnter();">
                </div>
                <div class="col-md-2 col-padding"><!--搜索 恢复默认 按钮-->
                  <button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
                </div>
              
                <div class="col-md-1 col-md-offset-6 paddiLeAdd">
                  <a class="btn btn-primary btn-sm" onclick="add();">添加</a>
                </div>
				
              </div>
            </div>
            <h4 class="padLeft" id="companyCount"></h4>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="datatables" class="table table-bordered table-hover" style="width: 100%;">
                <thead>
                <tr>
					<th>出发城市</th>
					<th>返回城市</th>
					<th>航班号</th>
					<th>起飞时间</th>
					<th>到达时间</th>
					<th>班期</th>
					<th>机型</th>
					<th>航空公司</th>
					<th>操作</th>
                </tr>
                </thead>
                <tbody>
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
<!-- page script -->
<script>
var datatable;
function initDatatable() {
    datatable = $('#datatables').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "language": {
            "url": "${base}/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": "${url}/listData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "columns": [
                    {"data": "leavecity", "bSortable": false},
                    {"data": "backcity", "bSortable": false},
                    {"data": "airlinenum", "bSortable": false},
                    {"data": "leavetime", "bSortable": false},
                    {"data": "backtime", "bSortable": false},
                    {"data": "banqi", "bSortable": false},
                    {"data": "airtype", "bSortable": false},
                    {"data": "aircomid", "bSortable": false}
            ],
        columnDefs: [{
            //   指定第一列，从0开始，0表示第一列，1表示第二列……
            targets: 8,
            render: function(data, type, row, meta) {
                return '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>'
            }
        }]
    });
}

	$("#searchBtn").on('click', function () {
		var companyName = $("#companyName").val();
	    var param = {
	        "companyName": companyName
	    };
	    datatable.settings()[0].ajax.data = param;
	    datatable.ajax.reload();
	});

$(function () {
    initDatatable();
});
   function add(){
      layer.open({
    	    type: 2,
    	    title: false,
    	    closeBtn:true,
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    area: ['900px', '490px'],
    	    content: '${url}/add.html'
    	    
    	  });
  }
  
  function edit(id){
	  layer.open({
  	    type: 2,
  	    title: false,
  	  	closeBtn:false,
  	    fix: false,
  	    maxmin: false,
  	    shadeClose: false,
  	    area: ['900px', '700px'],
  	    content: '${url}/update.html?id='+id
  	  });
  }
  function onkeyEnter(){
		 if(event.keyCode==13){
			 $("#searchBtn").click();
		 }
	}
  function successCallback(id){
	  datatable.ajax.reload();
	  loadCompanyCount();
	  if(id == '1'){
		  layer.msg("添加成功",{time: 2000, icon:1});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time: 2000, icon:1});
	  }else if(id == '3'){
		  layer.msg("删除成功",{time: 2000, icon:1});
	  }
  }
</script>
