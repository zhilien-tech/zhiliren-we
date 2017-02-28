<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>确认付款</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css">
<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<!--本页面style-->
</head>
<body>
<form id="confirmInlandPayForm">
	<div class="modal-top">
		<div class="modal-header boderButt">
			<button type="button" id="closePayWindow" class="btn btn-primary right btn-sm">取消</button>
			<input type="button" id="submit" onclick="confirmPayClick();" class="btn btn-primary right btn-sm" value="确定付款" />
			<h4>付款</h4>
		</div>
		<div class="modal-body" style="height: 600px; overflow-y: auto;">
			
			<table id="receivablesTable" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th>订单号</th>
						<th>PNR</th>
						<th>客户团号</th>
						<th>收款单位</th>
						<th>开票日期</th>
						<th>人数</th>
						<th>开票人</th>
						<th>金额</th>
					</tr>
				</thead>
				<tbody id="inlandConfirmPayTbody">
					<input id="payIds" name="payIds" type="hidden" ><!-- 水单url -->
				</tbody>
			</table>
			<table class="selectTable">
				<tr>
					<td>银行：</td>
					<td><select id="bankComp" name="bankComp" class="form-control input-sm">
							<option value="1">工商银行</option>
							<option value="2">招商银行</option>
							<option value="3">中国银行</option>
							<option value="4">华夏银行</option>
					</select></td>
					<td>银行卡名称：</td>
					<td><select id="cardName" name="cardName" class="form-control input-sm">
							<option value="1">国际专用卡</option>
							<option value="2">内陆专用卡</option>
					</select></td>
					<td>卡号：</td>
					<td><select id="cardNum" name="cardNum" class="form-control input-sm">
							<option value="1">6352 7463 3647 756</option>
					</select></td>
					<td>合计：</td>
					<td id="totalMoney">0.00</td>
				</tr>
			</table>
			<table class="payTable2">
				<tr>
					<td>国内外：</td>
					<td><select id="payAddress" name="payAddress" class="form-control input-sm">
							<option value=1 selected="selected">国内</option>
							<option value=2>境外</option>
					</select></td>
					<td>用途：</td>
					<td><select id="purpose" name="purpose" class="form-control input-sm">
							<option value=1>预付机票款</option>
							<option value=2>机票款</option>
							<option value=3>ETEM使用费</option>
							<option value=4>反税款</option>
							<option value=5>押金</option>
					</select></td>
					<td>资金种类：</td>
					<td><select id="fundType" name="fundType" class="form-control input-sm">
							<option value=1>对公</option>
							<option value=2>现金</option>
							<option value=3>银行卡</option>
							<option value=4>POS</option>
					</select></td>
					<td>付款时间：</td>
					<td><input id="payDate" name="payDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="2017-02-20" class="form-control input-sm"></td>
				</tr>
				<tr>
					<td>手续费：</td>
					<td><input id="payFees" name="payFees" type="text" class="form-control input-sm"></td>
					<td>金额：</td>
					<td><input id="payMoney" name="payMoney" type="text" class="form-control input-sm"></td>
					<td colspan="2">
						<input id="chineseMoney" type="text" class="form-control input-sm textIpnu" disabled="disabled"></td>
					<td class="bj">币种：</td>
					<td><select id="payCurrency" name="payCurrency" class="form-control input-sm">
							<option value=1>CNY</option>
							<option value=2>AOB</option>
					</select></td>
				</tr>
				<tr>
					<td>发票：</td>
					<td><select id="isInvioce" name="isInvioce" class="form-control input-sm">
							<option value=1>有</option>
							<option value=0>无</option>
					</select></td>
					<td>申请人：</td>
					<td><input id="proposer" name="proposer" type="text" class="form-control input-sm"
						disabled="disabled"></td>
					<td>审批人：</td>
					<td><input id="approver" name="approver" type="text" class="form-control input-sm" disabled="disabled" value="侯小凌"></td>
					<td>审批结果：</td>
					<td><input id="approveResult" name="approveResult" type="text" class="form-control input-sm" disabled="disabled"></td>
				</tr>
			</table>
			<button id="payIds" name="payIds" type="file" class="btn btn-primary btn-sm bankSlipBtn">上传水单</button>
			<input id="receiptUrl" name="receiptUrl" type="hidden" ><!-- 水单url -->
			<div class="bankSlipImg"></div>
		</div>
	</div>
</form>
	<!--JS 文件-->
	<!-- ./wrapper -->
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/admin/receivePayment/receivePayment.js"></script>
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	
	<!-- 确认付款列表 -->
	<script type="text/javascript">
		var tbodyHtml = "";
		var ids = "";
		$.each(${obj}, function (index, obj) {
			var trs = "";
			trs = '<tr><td>'+ obj.ordernum +'</td><td>'+ obj.pnrnum +'</td><td>'+ obj.custgroupnum +'</td><td></td><td>'+ obj.billdate +'</td><td>'+ obj.peoplecount +'</td><td>'+ obj.drawer +'</td><td>'+ obj.saleprice +'</td></tr>';
			tbodyHtml += trs;
			ids += obj.id +",";
		});
		$("#payIds").val(ids);
		$("#inlandConfirmPayTbody").append(tbodyHtml);
	</script>
	
	<!-- 確認付款js -->
	<script src="${base}/admin/receivePayment/confirmPay.js"></script>
</body>
</html>
