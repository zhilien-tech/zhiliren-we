<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<c:set var="url" value="${base}/admin/customneeds" />
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-users"></i> 客户需求</h3>
              <div class="form-group row form-right">
                  <!-- <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" >添加</button> -->
                  <%-- <button type="button" class="btn btn-primary btn-sm" onClick="window.open('${url}/add.html', 'newwindow', 'height=500, width=800, top=120, left=500, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">添加</button> --%>
                  <%-- <a data-toggle="modal" href="${url }/add.html" data-target="#addTabs">添加</a> --%>
                  　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
                  <a class="btn btn-primary btn-sm" href="${url }/downloadTemplate.html">模板</a>
                  　		　<!-- <a class="btn btn-primary btn-sm" onclick="add();">导入Excel</a> -->
                  <span>
                  <form id="uploadExcelForm" action="${url }/inportExcelData.html" name="form3" enctype="multipart/form-data" method="post" target="hidden_frame" style="display: inline;">
	                   	<input type="file" name="excelFile" id="excelFile" size="20" onchange="javascript:onfileChange();" style="width:180px;"/>
						<iframe name='hidden_frame' id='hidden_frame' style="display: none"></iframe>
					</form>
					</span>
		                  　<a class="btn btn-primary btn-sm" href="${base}/admin/customneeds/exportCustomNeedsExcel.html" id="exportExcelId">导出Excel</a>
		                  　<a class="btn btn-primary btn-sm" onclick="add();">添加</a>
                  <br/>
                <label class="radio-inline SelectWid">
                   <!--部门 下拉框-->
                     <select class="form-control select" id="isclose" onchange="selectDept();">
                       <option value="0">默认</option>
                    <option value="1">关闭</option>
                     </select>
                 </label>
                 	<form id="searchForm">
			                航空公司<input type="text" name="airline" id="airline"  placeholder="首航-CA" onkeypress="onkeyEnter();">
			                旅行社<input type="text" name="travel" id="travel"  onkeypress="onkeyEnter();">
			                人数<input type="text" name="totalcount" id="totalcount"  onkeypress="onkeyEnter();">
			                天数<input type="text" name="totalday" id="totalday"   onkeypress="onkeyEnter();">
			                去程日期<input type="text" name="leavedate" id="leavedate"  onkeypress="onkeyEnter();">
			                去程航段<input type="text" name="leavecity" id="leavecity"  placeholder="北京-PEK" onkeypress="onkeyEnter();">
			                回程日期<input type="text" name="backdate" id="backdate"  onkeypress="onkeyEnter();">
			                  回程航段<input type="text" name="backcity" id="backcity"  placeholder="悉尼-SYD" onkeypress="onkeyEnter();">
                  </form>
                  <button id="searchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>　
                  <button id="resetBtn" type="button" class="btn btn-primary btn-sm">恢复默认</button>
              
                
				
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="datatable" class="table table-bordered table-hover" style="width: 100%;">
                <thead>
                <tr>
                  	<th>序号</th>
                  	<th>航空公司名称</th>
					<th>去程日期</th>
					<th>去程航段</th>
					<th>回程日期</th>
					<th>回程航段</th>
					<th>人数</th>
					<th>天数</th>
					<th>旅行社名称</th>
					<th>联运要求</th>
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
	<script src="${base}/public/dist/js/pikaday.js"></script>
<!-- page script -->
<script>
var datatable;
function initDatatable() {
    datatable = $('#datatable').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "language": {
            "url": "${base}/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": "${base}/admin/customneeds/listData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "fnDrawCallback"    : function(){
   	       　　this.api().column(0).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML =  i + 1;
   	       　　});
      	},
        "columns": [
                    {"data": "xuhao", "bSortable": false},
                    {"data": "airline", "bSortable": false},
                    {"data": "leavedate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var leavedate = new Date(row.leavedate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][leavedate.getMonth()];
                    		return leavedate.getDate() + "/" + MM;
                    	}
                    },
                    {"data": "leavecity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		return row.leavecity + "-" + row.backcity;
                    	}
                    },
                    {"data": "backdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var backdate = new Date(row.backdate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][backdate.getMonth()];
                    		return backdate.getDate() + "/" + MM;
                    	}
                    },
                    {"data": "backcity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		return row.backcity + "-" + row.leavecity;
                    	}	
                    },
                    {"data": "totalcount", "bSortable": false},
                    {"data": "totalday", "bSortable": false},
                    {"data": "travel", "bSortable": false},
                    {"data": "uniontransport", "bSortable": false}
            ],
        columnDefs: [{
	            //   指定第一列，从0开始，0表示第一列，1表示第二列……
	            targets: 10,
	            render: function(data, type, row, meta) {
	                return '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>'
	                		+ '&nbsp;&nbsp;&nbsp;<a style="cursor:pointer;" onclick="closeCustomNeeds('+row.id+');">关闭</a>'
	            }
        	},{
        		targets: 0,
	            render: function(data, type, row, meta) {
	                return null
	            }
        	}]
    });
}
	//按钮点击搜索
	$("#searchBtn").on('click', function () {
		//为Excel导出提供方法
		document.getElementById('exportExcelId').href= "${base}/admin/customneeds/exportCustomNeedsExcel.html?" + $("#searchForm").serialize();
	    var param = getSearchInfo();
	    datatable.settings()[0].ajax.data = param;
	    datatable.ajax.reload();
	});
	//获取搜索数据
	function getSearchInfo(){
		var isclose = $("#isclose").val();
		var airline = $("#airline").val();
		var travel = $("#travel").val();
		var totalcount = $("#totalcount").val();
		var totalday = $("#totalday").val();
		var leavedate = $("#leavedate").val();
		var leavecity = $("#leavecity").val();
		var backdate = $("#backdate").val();
		var backcity = $("#backcity").val();
		if(leavedate){
			leavedate = new Date(leavedate);
		}
		if(backdate){
			backdate = new Date(backdate);
		}
	    var param = {
	        "isclose": isclose,
	        "airline": airline,
	        "travel": travel,
	        "totalcount": totalcount,
	        "totalday": totalday,
	        "leavedate": leavedate,
	        "leavecity": leavecity,
	        "backdate": backdate,
	        "backcity": backcity
	    };
		return param;
	}
//恢复默认
$('#resetBtn').on('click', function () {
	$("#isclose").val('');
	$("#airline").val('');
	$("#travel").val('');
	$("#totalcount").val('');
	$("#totalday").val('');
	$("#leavedate").val('');
	$("#leavecity").val('');
	$("#backdate").val('');
	$("#backcity").val('');
	datatable.ajax.reload();
});
$(function () {
    initDatatable();
   	//初始化日期控件
    var picker = new Pikaday({
        field: document.getElementById('leavedate'),
        firstDay: 1,
        minDate: new Date('2000-01-01'),
        maxDate: new Date('2120-12-31'),
        yearRange: [2000,2020]
    });
    var picker = new Pikaday({
        field: document.getElementById('backdate'),
        firstDay: 1,
        minDate: new Date('2000-01-01'),
        maxDate: new Date('2120-12-31'),
        yearRange: [2000,2020]
    });

});
   function add(){
      layer.open({
    	    type: 2,
    	    title: false,
    	    closeBtn:false,
    	    fix: false,
    	    maxmin: false,
    	    shadeClose: false,
    	    area: ['900px', '500px'],
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
  	    area: ['900px', '500px'],
  	    content: '${url}/update.html?id='+id
  	  });
  }
 
  function closeCustomNeeds(id){
	  layer.confirm('确定要关闭该需求吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:id}, 
				url: '${base}/admin/customneeds/closeCustomNeeds.html',
	            success: function (data) { 
	            	layer.msg("关闭成功",{time: 2000, icon:1});
	            	datatable.ajax.reload();
	            },
	            error: function (xhr) {
	            	layer.msg("关闭失败",{time: 2000, icon:1});
	            } 
	        });
		});
  }
    function onkeyEnter(){
       var e = window.event || arguments.callee.caller.arguments[0];
	   if(e || e.keyCode == 13){
		 $("#searchBtn").click();
	   }
	}
//选中后开始导入
  function onfileChange() {
	   uploadfile();
  }
  //导入Excel
  function uploadfile() {
  		var filepath = document.getElementById("excelFile").value;
  		var extStart = filepath.lastIndexOf(".");
  		var ext = filepath.substring(extStart, filepath.length).toUpperCase();
  		if (ext != ".XLS" && ext != ".XLSX") {
  			layer.alert("请选择正确的Excel文件");
  			return;
  		}
  		document.getElementById("uploadExcelForm").submit();
  		//layer.load(1, {shade: [0.8, '#393D49']});
  	}
  //其他页面回调
  function successCallback(id){
	  datatable.ajax.reload();
	  if(id == '1'){
		  layer.msg("添加成功",{time: 2000, icon:1});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time: 2000, icon:1});
	  }else if(id == '3'){
		  layer.msg("关闭成功",{time: 2000, icon:1});
	  }
  }
  
  function callback(){
	  layer.msg("导入成功",{time: 2000, icon:1});
	  datatable.ajax.reload();
  }
  
</script>

