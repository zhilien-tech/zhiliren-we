<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>退票</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base}/public/ionicons/css/ionicons.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
            <h4 class="invoiceH4">退票</h4>
    </div>
      <div class="modal-body" style="height:320px;overflow-y: auto;">
      	<form id="backTicketForm">
      	<input type="hidden" id="id" name="id" value="${obj.backinfo.id }">
      	<input type="hidden" id="visitorid" name="visitorid" value="${obj.visitorinfo.id }">
         <div class="form-group row"><!--退票人/电话/申请日期-->
                  <label class="col-sm-2 text-right padding">退票人：</label>
                  <div class="col-sm-2 padding"><input id="visitorname" name="visitorname" type="text" class="form-control input-sm" value="${empty obj.backinfo.id?obj.visitorinfo.visitorname:obj.backinfo.visitorname }"></div>
                  <label class="col-sm-2 text-right padding">电话：</label>
                  <div class="col-sm-2 padding"><input id="telephone" name="telephone" type="text" class="form-control input-sm" value="${empty obj.backinfo.id?obj.visitorinfo.phonenum:obj.backinfo.telephone }"></div>
         </div><!--end 退票人/电话/申请日期-->
         <div class="form-group row">
                  <label class="col-sm-2 text-right padding">申请日期：</label>
                  <div class="col-sm-2 padding"><input id="applydatestr" name="applydatestr" type="text" class="form-control input-sm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.backinfo.applydate }" pattern="yyyy-MM-dd" />"></div>
                  <label class="col-sm-2 text-right padding">金额：</label>
                  <div class="col-sm-2 padding"><input id="price" name="price" type="text" class="form-control input-sm" value="${obj.backinfo.price }"></div>
         </div>
         <div class="form-group row">
                  <label class="col-sm-2 text-right padding">税金：</label>
                  <div class="col-sm-2 padding"><input id="tax" name="tax" type="text" class="form-control input-sm" value="${obj.backinfo.tax }"></div>
                  <label class="col-sm-2 text-right padding">退款金额：</label>
                  <div class="col-sm-2 padding"><input id="backprice" name="backprice" type="text" class="form-control input-sm" value="${obj.backinfo.backprice }"></div>
         </div>
         <div class="form-group row"><!--金额/原因/退票状态-->
                  <label class="col-sm-2 text-right padding">原因：</label>
                  <div class="col-sm-2 padding">
                    <select id="reason" name="reason" class="form-control input-sm">
                      <c:forEach var="map" items="${obj.backreasonenum}" >
                   		<c:choose>
                   			<c:when test="${map.key eq obj.backinfo.reason}">
                 				<option value="${map.key}" selected="selected">${map.value}</option>
                   			</c:when>
                   			<c:otherwise>
                 				<option value="${map.key}">${map.value}</option>
                   			</c:otherwise>
                   		</c:choose>
					 </c:forEach>
                    </select>
                  </div>
                  <label class="col-sm-2 text-right padding">退票状态：</label>
                  <div class="col-sm-2 padding">
                    <select id="backstatus" name="backstatus" class="form-control input-sm">
                      <c:forEach var="map" items="${obj.backticketstatusenum}" >
                   		<c:choose>
                   			<c:when test="${map.key eq obj.backinfo.backstatus}">
                 				<option value="${map.key}" selected="selected">${map.value}</option>
                   			</c:when>
                   			<c:otherwise>
                 				<option value="${map.key}">${map.value}</option>
                   			</c:otherwise>
                   		</c:choose>
					 </c:forEach>
                    </select>
                  </div>
         </div><!--end 金额/原因/退票状态-->
         <div class="form-group row"><!--备注-->
                  <label class="col-sm-2 text-right padding">备注：</label>
                  <div class="col-sm-8 padding"><input id="remark" name="remark" type="text" class="form-control input-sm" value="${empty obj.backinfo.id?obj.visitorinfo.remark:obj.backinfo.remark }"></div>
         </div><!--end 备注-->
         </form>
      </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- My97DatePicker --> 
  <script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!--layer -->
  	<script src="${base}/common/js/layer/layer.js"></script>
	<script type="text/javascript">
	  //关闭窗口
	  function closewindow(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
	  }
	  
	  $('#submit').click(function(){
		  layer.load(1);
		  $.ajax({ 
				type: 'POST', 
				data: $("#backTicketForm").serialize(), 
				url: '${base}/admin/international/saveBackTicketInfo.html',
	            success: function (data) { 
	            	layer.closeAll('loading');
	            	closewindow();
	            	window.parent.successCallback('3');
	            },
	            error: function (xhr) {
	            	layer.msg("退票失败","",3000);
	            } 
	        });
	  });
	</script>
</body>
</html>