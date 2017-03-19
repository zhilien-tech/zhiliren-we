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
            <input type="button" id="confirmRecClick" class="btn btn-primary right btn-sm" value="提交"/>
            <h4>收款</h4>
          </div>
          <div class="modal-body" style="height: 483px;overflow-y:auto; ">
              <table id="interConfirmRecTable" class="table table-bordered table-hover">
                <thead>
                  <tr>
                    <th>订单号</th>
                    <th>开票日期</th>
                    <th>客户团号</th>
                    <th>客户</th>
                    <th>联系人</th>
                    <th>开票人</th>
                    <th>金额</th>
                  </tr>
                </thead>
                <tbody id="interConfirmRecTbody">
                  <input id="recIds" name="recIds" type="hidden" value="${obj.inlandRecId }"><!-- 水单url -->
					<c:forEach var="one" items="${obj.orders}">
                		<tr>
                			<td>${one.ordersnum }</td>
                			<td>${one.billdate }</td>
                			<td>${one.cusgroupnum }</td>
                			<td>${one.shortname }</td>
                			<td>${one.linkman }</td>
                			<td>${one.username }</td>
                			<td>${one.incometotal }</td>
                		</tr>
                	</c:forEach>
                </tbody>
              </table>
              <table border="0" class="selectTable">
                <tr>
                  <td>银行：</td>
                  <td>
                    <select disabled="disabled" class="form-control input-sm">
                        <option selected = "selected">${obj.bankComp}</option>
                    </select>
                  </td>
                  <td>银行卡名称：</td>
                  <td>
                    <select disabled="disabled" class="form-control input-sm">
                         <option selected = "selected">${obj.bankcardname}</option>
                    </select>
                  </td>
                  <td>卡号：</td>
                  <td>
                     <select disabled="disabled" class="form-control input-sm">
                         <option selected = "selected">${obj.bankcardnum}</option>
                     </select>
                  </td>
                  <td>合计：</td>
                  <td>${obj.sum}</td>
                </tr>
              </table>
              <label class="labelShuidan">水单</label>
              <div class="bankSlipImg SDdiv"  align="center">
              	<img width="400" height="300" alt="" src="${obj.receipturl }">
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
	<script src="${base}/admin/receivePayment/inter/interReceive.js"></script>
	
</body>
</html>