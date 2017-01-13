<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>退税</title>
	<link rel="stylesheet" href="${base}/public/dist/css/grabMail.css"><!--本页面样式-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!--内容-->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
      <div class="row row-top">
        <div class="col-xs-12">
          <div class="row">
            <div class="col-md-12">
              <!-- Custom Tabs -->
              <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">邮件抓取</a></li>
                      <li><a href="#tab_2" data-toggle="tab">报表</a></li>
                </ul>
                <div class="tab-content">
                  <!--邮件抓取-->
                  <div class="tab-pane pane-content active" id="tab_1">
                   <div class="rebatesBtn">
                     <button type="button" class="btn btn-primary btn-sm right noneBtn none">删除</button>
                     <button type="button" class="btn btn-primary btn-sm right noneBtn none">移动到</button>
                     <button type="button" class="btn btn-primary btn-sm right batchBtn">批量操作</button>
                     <button type="button" class="btn btn-primary btn-sm right">上传</button>
                     <button type="button" class="btn btn-primary btn-sm right">新建文件夹</button>
                     <button type="button" class="btn btn-primary btn-sm right returnBtn none">返回上一级</button>
                     <button type="button" class="btn btn-primary btn-sm right indexBtn none">返回首页</button>
                   </div>
                   <table id="rebatesEamilTable" class="table table-bordered table-hover">
                     <thead>
                      <tr>
                       <th class="none checkTh"><input type="checkbox"></th>
                       <th>文件名</th>
                       <th>时间</th>
                       <th>大小</th>
                       <th>操作</th>
                      </tr>
                     </thead>
                     <tbody>
                      <tr>
                       <td class="none checkTd"><input type="checkbox"></td>
                       <td class="text-left"><a href="javascript:;">vme</a></td>
                       <td>2016-12-12  00:12</td>
                       <td>20k</td>
                       <td>
                          <a href="javascript:;">下载&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">移动到&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">删除</a>
                       </td>
                      </tr>
                      <tr>
                       <td class="none checkTd"><input type="checkbox"></td>
                       <td class="text-left"><a href="javascript:;">aaa</a></td>
                       <td>2016-12-10  01:12</td>
                       <td>89k</td>
                       <td>
                          <a href="javascript:;">下载&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">移动到&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">删除</a>
                       </td>
                      </tr>
                      <tr>
                       <td class="none checkTd"><input type="checkbox"></td>
                       <td class="text-left"><a href="javascript:;">aaa</a></td>
                       <td>2016-12-10  01:12</td>
                       <td>89k</td>
                       <td>
                          <a href="javascript:;">下载&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">移动到&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">删除</a>
                       </td>
                      </tr>
                      <tr>
                       <td class="none checkTd"><input type="checkbox"></td>
                       <td class="text-left"><a href="javascript:;">aaa</a></td>
                       <td>2016-12-10  01:12</td>
                       <td>89k</td>
                       <td>
                          <a href="javascript:;">下载&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">移动到&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">删除</a>
                       </td>
                      </tr>
                      <tr>
                       <td class="none checkTd"><input type="checkbox"></td>
                       <td class="text-left"><a href="javascript:;">aaa</a></td>
                       <td>2016-12-10  01:12</td>
                       <td>89k</td>
                       <td>
                          <a href="javascript:;">下载&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">移动到&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">删除</a>
                       </td>
                      </tr>
                      <tr>
                       <td class="none checkTd"><input type="checkbox"></td>
                       <td class="text-left"><a href="javascript:;">aaa</a></td>
                       <td>2016-12-10  01:12</td>
                       <td>89k</td>
                       <td>
                          <a href="javascript:;">下载&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">移动到&nbsp;&nbsp;&nbsp;</a>
                          <a href="javascript:;">删除</a>
                       </td>
                      </tr>
                     </tbody>
                   </table>
                  </div><!--end 邮件抓取-->

                  <!--报表-->
                  <div class="tab-pane pane-content" id="tab_2">
                        <table id="rebatesReportTable" class="table table-bordered table-hover">
                          <thead>
                          <tr>
                            <th>备注</th>
                            <th>汇款</th>
                            <th>blance(备用金余额)</th>
                            <th>票价(含行李)</th>
                            <th>刷卡费</th>
                            <th>税金/杂项</th>
                            <th>消费税(GST)</th>
                            <th>代理费</th>
                            <th>税返点</th>
                            <th>退税状态</th>
                            <th>实收单价(含操作费)</th>
                            <th>实收合计(含操作费)</th>
                            <th>代理费(与前一个不同?)</th>
                            <th>入澳时间</th>
                            <th>出澳时间</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td>...</td>
                              <td> </td>
                              <td> </td>
                              <td> </td>
                              <td> </td>
                              <td> </td>
                              <td> </td>
                              <td>公式</td>
                              <td>公式</td>
                              <td>未退税</td>
                              <td>公式</td>
                              <td>公式</td>
                              <td>公式</td>
                              <td> </td>
                              <td> </td>
                              <td><a href="javascript:;">详情</a></td>
                            </tr>
                          </tbody>
                        </table>
                  </div><!--end 报表-->

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
<script type="text/javascript">
$(function () {
	//批量管理 点击操作
    $(".batchBtn").click(function(){
        $('.noneBtn').toggle();
        $('.checkTh').toggle();
        $('.checkTd').toggle();
    });
    //table下checkbox全选 点击操作
    $(".checkTh input").click(function() {
      if (this.checked == true){
        $(".checkTd input").each(function() {
          this.checked = true;
        });
      }else{
        $(".checkTd input").each(function() {
          this.checked = false;
        });
      }
    });
});
</script>
<script type="text/javascript">
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
		var url = '${base}/admin/';
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
</script>
<!-- 分页显示 -->
<!-- <script type="text/javascript">
	var rebatesEamilTable;
	function initDatatable() {
		rebatesEamilTable = $('#rebatesEamilTable').DataTable({
			"searching" : false,
			"processing" : true,
			"serverSide" : true,
			"stripeClasses": [ 'strip1','strip2' ],//斑马线
			"bLengthChange" : false,
			"bJQueryUI": true,
			
			"language" : {
				"url" : "${base}/public/plugins/datatables/cn.json"
			}
	        "ajax": {
	            "url": "${base}/admin/drawback/grabfile/listData.html",
	            "type": "post",
	            "data": function (d) {
	            	
	        },
	        "columns": [
	                    {"data": "filename", "bSortable": false},
	                    {"data": "createtime", "bSortable": true,
	                    	 render: function(data, type, row, meta) {
	                    		 var createtime = new Date(row.createtime);
	                    		 var createtimestr = createtime.getFullYear() + "-" + createtime.getMonth() + "-" + createtime.getDate() + " "
	                    		 + createtime.getHours() + ":" + createtime.getMinutes() + ":" + createtime.getSeconds();
	                    		return createtimestr;
	                        } 	
	                    },
	                    {"data": "filesize", "bSortable": false}
	            ],
	            "columnDefs": [{
	                //   指定第一列，从0开始，0表示第一列，1表示第二列……
	                targets: 3,
	                render: function(data, type, row, meta) {
	                	var modify = '<a style="cursor:pointer;" onclick="edit('+row.id+');">编辑</a>';
                   		if(1==row.status){
                   			var judge = '<a href="javascript:physicalDelete('+row.id+',2)" class="btn_mini btn_modify"><font color="#CCCCCC">删除</font></a>';
                   		}else{
                   			var judge = '<a href="javascript:physicalDelete('+row.id+',1)" class="btn_mini btn_modify">启用</a>';
                   		}
	                    return modify+judge;
	                }
	            }
		});
	}
	$(function() {
		initDatatable();
	});
</script> -->
</body>
</html>