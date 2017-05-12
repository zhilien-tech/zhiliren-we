<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>游客信息</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">关闭</button>
            <!-- <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/> -->
            <h4>游客信息</h4>
          </div>
          <div class="modal-body" style="height:481px;overflow-y:auto; ">
              <table id="logTable" class="table table-bordered table-hover">
                     <thead>
                      <tr>
                       <th>序号</th>
                       <th>姓名</th>
                       <th>性别</th>
                       <th>游客类型</th>
                       <th>证件类型</th>
                       <th>证件号码</th>
                       <th>状态</th>
                       <th>备注</th>
                       <th>操作</th>
                      </tr>
                     </thead>
                     <tbody id="visitortabledata">
                      <%-- <c:forEach items="${obj.visitors }" var="visitor" varStatus="status">
	                      <tr>
	                       <td>${status.index + 1 }</td>
	                       <td>${visitor.visitorname }</td>
	                       <td>${visitor.gender }</td>
	                       <td>${visitor.visitortype }</td>
	                       <td>${visitor.cardtype }</td>
	                       <td>${visitor.cardnum }</td>
	                       <td><c:forEach items="${obj.backticketstatusenum }" var="map">
		                       		<c:if test="${visitor.backstatus eq map.key }">
		                       			${map.value }
		                       		</c:if>
	                       	</c:forEach>
	                       </td>
	                       <td>${visitor.remark }</td>
	                       <td>
	                          <a href="javascript:editVisitor(${visitor.id });">编辑</a>&nbsp;&nbsp;&nbsp;
	                          <a href="javascript:backTicket(${visitor.id });" class="refund">退票</a>
	                       </td>
	                      </tr>
                      </c:forEach> --%>
                     </tbody>
                   </table>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
    <!--layer -->
  <script src="${base}/common/js/layer/layer.js"></script>
  <script type="text/javascript">
  $(function(){
  });
   //点击 退票 弹框
   function editVisitor(id){
       layer.open({
           type: 2,
           title:false,
           skin: false, //加上边框
           closeBtn:false,//默认 右上角关闭按钮 是否显示
           shadeClose:true,
           area: ['860px', '384px'],
           content: '${base}/admin/international/editVisitorInfo.html?visitorid='+id
         });
   }
   //退票
   function backTicket(id){
	   layer.open({
           type: 2,
           title:false,
           skin: false, //加上边框
           closeBtn:false,//默认 右上角关闭按钮 是否显示
           shadeClose:true,
           area: ['900px', '424px'],
           content: '${base}/admin/international/backTicket.html?visitorid='+id
         });
   }
//关闭窗口
  function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
  }
  
  function successCallback(id){
	  loadTableData();
	  if(id == '1'){
		  layer.msg("添加成功",{time: 2000});
	  }else if(id == '2'){
		  layer.msg("修改成功",{time: 2000});
	  }else if(id == '3'){
		  layer.msg("退票成功",{time: 2000});
	  }
	}
  loadTableData();
  function loadTableData(){
	  $.ajax({
			type: 'POST', 
			data: {pnrid:'${obj.pnrid}'}, 
			url: '${base}/admin/international/loadVisitorData.html',
			async:false,
			dataType:'json',
          success: function (data) { 
        	 var visitorhtml = '';
          	$.each(data.visitors, function(name, value) {
       			visitorhtml += '<tr>';
        		visitorhtml += '<td>'+name+'</td>';
       			if(value.visitorname != undefined){
                    visitorhtml += '<td>'+value.visitorname+'</td>';
       			}else{
       				visitorhtml += '<td></td>';
       			}
       			if(value.gender != undefined){
                      visitorhtml += '<td>'+value.gender+'</td>';
       			}else{
       				visitorhtml += '<td></td>';
       			} 
       			if(value.visitortype != undefined){
                      visitorhtml += '<td>'+value.visitortype+'</td>';
       			}else{
       				visitorhtml += '<td></td>';
       			} 
       			if(value.cardtype != undefined){
                      visitorhtml += '<td>'+value.cardtype+'</td>';
       			}else{
       				visitorhtml += '<td></td>';
       			}
       			if(value.cardnum != undefined){
                      visitorhtml += '<td>'+value.cardnum+'</td>';
       			}else{
       				visitorhtml += '<td></td>';
       			} 
       			if(value.backstatus != undefined){
       				$.each(data.backticketstatusenum, function(name1, value1) {
              			if(value.backstatus == name1){
              				visitorhtml += '<td>'+value1+'</td>';
              			}
              		});
       			}else{
       				visitorhtml += '<td></td>';
       			} 
       			if(value.remark != undefined){
                    visitorhtml += '<td>'+value.remark+'</td>';
     			}else{
     				visitorhtml += '<td></td>';
     			} 
                visitorhtml += '<td><a href="javascript:editVisitor('+value.id+');">编辑</a>';
                visitorhtml += '&nbsp;&nbsp;&nbsp;<a href="javascript:backTicket('+value.id+');">退票</a></td>';
      		});
          	$('#visitortabledata').html(visitorhtml);
          },
          error: function (xhr) {
          	
          } 
    });
  }
  </script>
</body>
</html>	