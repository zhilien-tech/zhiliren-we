<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>确认收款</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
    <link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css">
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" id="closeRecWindow" class="btn btn-primary right btn-sm">取消</button>
            <input type="button" id="confirmRecClick" class="btn btn-primary right btn-sm" value="确认收款"/>
            <h4>收款</h4>
          </div>
          <div class="modal-body" style="height: 580px;overflow-y:auto; ">
              <table id="receivablesTable" class="table table-bordered table-hover">
                <thead>
                  <tr>
                    <th>订单号</th>
                    <th>开票日期</th>
                    <th>客户团号</th>
                    <th>客户</th>
                    <th>联系人</th>
                    <th>票务</th>
                    <th>金额</th>
                  </tr>
                </thead>
                <tbody>
                  <input id="recIds" name="recIds" type="hidden" value="${obj.id }"><!-- 水单url -->
					<c:forEach var="one" items="${obj.orders}">
                		<tr ondblclick="trDetail(${one.id });">
                			<td>${one.ordersnum }</td>
                			<td>${one.billingdate }</td>
                			<td>${one.cusgroupnum }</td>
                			<td>${one.shortname }</td>
                			<td>${one.linkman }</td>
                			<td>${one.issuer }</td>
                			<td>
                				<fmt:formatNumber type="number" value="${one.currentpay }" pattern="0.00" maxFractionDigits="2"/>
                			</td>
                		</tr>
                	</c:forEach>
                </tbody>
              </table>
              <table border="0" class="selectTable">
                <tr>
                  <td>银行：</td>
                  <td>
                    <select disabled="disabled" class="form-control input-sm">
                   		<option selected = "selected">${obj.bankName}</option>
                    </select>
                  </td>
                  <td>银行卡名称：</td>
                  <td>
                    <select disabled="disabled" class="form-control input-sm">
                          <option selected = "selected">${obj.receive.bankcardname}</option>
                    </select>
                  </td>
                  <td>卡号：</td>
                  <td>
                     <select disabled="disabled" class="form-control input-sm">
                         <option selected = "selected">${obj.receive.bankcardnum}</option>
                     </select>
                  </td>
                  <td>合计：</td>
                  <td>
                  	<fmt:formatNumber type="number" value="${obj.sum}" pattern="0.00" maxFractionDigits="2"/>
                  </td>
                </tr>
              </table>
              <label class="labelShuidan">水单</label>
              <div class="bankSlipImg SDdiv"  align="center">
              	<img width="100%" height="305" alt="" src="${obj.receipturl.receiptUrl }">
              </div>
          </div>
	</div>
	<!--JS 文件-->
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<!-- 確認收款js -->
	<script src="${base}/admin/receivePayment/inland/inlandReceive.js"></script>
	<!-- 收付款Common js -->
	<script src="${base}/admin/receivePayment/recPayCommon.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- 防止订单重复提交 -->
	<script type="text/javascript">
		$(function(){
			var recStatus = $("#inlandRecSelect option:selected",window.parent.document).val();
			if(recStatus==2){
				$("#confirmRecClick").attr('disabled',true);
			}
		});
		function trDetail(id){
			var url = '${base}/admin/inland/bookingDetail.html?id='+ id;
			window.open(url);
		}
	</script>
</body>
</html>
