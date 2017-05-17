<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>付款申请</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${base }/public/ionicons/css/ionicons.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${base }/public/dist/css/inlandCross.css"><!--本页style-->
    <style type="text/css">
    	.form-group {margin-bottom: 8px;}
    	.labelWid {width: 95px !important;}
    </style>
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" onclick="commitPayApply();" value="提交"/>
            <h4 class="invoiceH4">付款申请</h4>
    </div>
      <div class="modal-body" style="height: 250px;overflow-y: auto;">
      	 <input type="hidden" id="ids" name="ids" value="${obj.ids }">
         <table id="receivablesTable" class="table table-bordered table-hover">
                  <thead>
                    <tr>
                      <th>订单号</th>
                      <th>PNR</th>
                      <th>客户团号</th>
                      <th>支付对象</th>
                      <th>开票日期</th>
                      <th>人数</th>
                      <th>票务</th>
                      <th>金额</th>
                    </tr>
                  </thead>
                  <tbody>
                  	<c:forEach var="one" items="${obj.orders }">
                		<tr ondblclick="toOrderDetail(${one.ordersid})">
                			<td>${one.ordersnum }</td>
                			<td>${one.pnr }</td>
                			<td>${one.cusgroupnum }</td>
                			<td>${one.shortName }</td>
                			<td><fmt:formatDate value="${one.billingdate }" pattern="yyyy-MM-dd" /></td>
                			<td>${one.peoplecount }</td>
                			<td>${one.issuer }</td>
                			<td><fmt:formatNumber type="number" value="${one.costpricesum }" pattern="0.00" maxFractionDigits="2"/></td>
                		</tr>
                	</c:forEach>
                  </tbody>
         </table>
         <div class="form-group row"><!--用途/币种-->
                  <label class="col-sm-2 text-right padding">项目用途：</label>
                  <div class="col-sm-2 padding">
                    <select id="purpose" name="purpose" class="form-control input-sm">
                      <c:forEach var="one" items="${obj.ytSelect }">
                        	<option value="${one.id }">${one.comDictName }</option>
                      </c:forEach>
                    </select>
                  </div>
                  <label class="col-sm-1 text-right padding labelWid">币种：</label>
                  <div class="col-sm-2 padding">
                    <select id="payCurrency" name="payCurrency" class="form-control input-sm">
                      <c:forEach var="one" items="${obj.bzSelect }">
                        	<option value="${one.id }">${one.dictCode }</option>
                        </c:forEach>
                    </select>
                  </div>
         </div><!--end 用途/币种-->
         <div class="form-group row"><!--申请人/审批人/审批结果-->
         <label class="col-sm-2 text-right padding">申请人：</label>
         <div class="col-sm-2 padding"><input type="text" class="form-control input-sm" disabled="disabled" value="${obj.user.fullName }"></div>
         <label class="col-sm-1 text-right padding labelWid">审批人：</label>
         <div class="col-sm-2 padding"><input id="approver" name="approver" type="text" class="form-control input-sm" disabled="disabled" value="侯小凌"></div>
         <label class="col-sm-1 text-right padding" style="width:95px;">审批结果：</label>
         <div class="col-sm-2 padding"><input id="approveResult" name="approveResult" type="text" class="form-control input-sm" disabled="disabled"></div>
      </div>
      <div class="form-group row"><!--申请人/审批人/审批结果-->
         <label class="col-sm-2 text-right padding">开户银行：</label>
         <div class="col-sm-2 padding"><input id="openbank" name="openbank" type="text" class="form-control input-sm"></div>
         <label class="col-sm-1 text-right padding labelWid">开户名称：</label>
         <div class="col-sm-2 padding"><input id="openname" name="openname" type="text" class="form-control input-sm"></div>
         <label class="col-sm-1 text-right padding" style="width:95px;">开户账号：</label>
         <div class="col-sm-2 padding"><input id="opennumber" name="opennumber" type="text" class="form-control input-sm"></div>
      </div>
	</div>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!--layer -->
    <script src="${base}/common/js/layer/layer.js"></script>
	<script type="text/javascript">
	//关闭窗口
    function closewindow(){
		var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	function commitPayApply(){
		var ids = $('#ids').val();
		var purpose = $('#purpose').val();
		var payCurrency = $('#payCurrency').val();
		var approver = $('#approver').val();
		var approveResult = $('#approveResult').val();
		var openbank = $('#openbank').val();
		var openname = $('#openname').val();
		var opennumber = $('#opennumber').val();
		$.ajax({
	        type: "post",
	        url: '${base}/admin/inland/saveSeaPayApply.html',
	        data: {ids:ids,purpose:purpose,payCurrency:payCurrency,approver:approver,approveResult:approveResult,openbank:openbank,openname:openname,opennumber:opennumber},
	        cache: false,
	        async : false,
	        success: function (data ,textStatus, jqXHR){
	        	layer.msg("提交成功！",{time: 2000});
	        	closewindow();
	        	window.parent.successCallback('5');
	        },
	        error:function (XMLHttpRequest, textStatus, errorThrown) {      
	            layer.msg("请求失败！",{time: 2000});
	        }
	     });
	}
	
	function toOrderDetail(id){
		var url = '${base}/admin/inland/bookingDetail.html?id=' + id;
		window.open(url);
	}
	</script>
</body>
</html>