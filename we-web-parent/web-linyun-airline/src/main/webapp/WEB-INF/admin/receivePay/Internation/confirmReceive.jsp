<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
			<button type="button" class="btn btn-primary right btn-sm">取消</button>
			<input type="submit" id="submit" class="btn btn-primary right btn-sm" value="确定付款" />
			<h4>付款</h4>
		</div>
		<div class="modal-body" style="height: 600px; overflow-y: auto;">
			<table id="receivablesTable" class="table table-bordered table-hover">
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
				<tbody>
					<tr>
						<td>2016121200001</td>
						<td>OEUHBL</td>
						<td>JCH-161210</td>
						<td></td>
						<td>05Aug26</td>
						<td>15</td>
						<td>周勇</td>
						<td>9650.11</td>
					</tr>
					<tr>
						<td>2016121200001</td>
						<td>OEUHBL</td>
						<td>JCH-161210</td>
						<td></td>
						<td>05Aug26</td>
						<td>15</td>
						<td>周勇</td>
						<td>9650.11</td>
					</tr>
					<tr>
						<td>2016121200001</td>
						<td>OEUHBL</td>
						<td>JCH-161210</td>
						<td></td>
						<td>05Aug26</td>
						<td>15</td>
						<td>周勇</td>
						<td>9650.11</td>
					</tr>
				</tbody>
			</table>
			<table class="selectTable">
				<tr>
					<td>银行：</td>
					<td><select class="form-control input-sm">
							<option>工商银行</option>
							<option>招商银行</option>
							<option>中国银行</option>
							<option>华夏银行</option>
					</select></td>
					<td>银行卡名称：</td>
					<td><select class="form-control input-sm">
							<option>国际专用卡</option>
							<option>内陆专用卡</option>
					</select></td>
					<td>卡号：</td>
					<td><select class="form-control input-sm">
							<option>6352 7463 3647 756</option>
					</select></td>
					<td>合计：</td>
					<td>3333.33</td>
				</tr>
			</table>
			<table class="payTable2">
				<tr>
					<td>国内外：</td>
					<td><select class="form-control input-sm">
							<option>国内</option>
							<option>境外</option>
					</select></td>
					<td>用途：</td>
					<td><select class="form-control input-sm">
							<option>预付机票款</option>
							<option>机票款</option>
							<option>ETEM使用费</option>
							<option>反税款</option>
							<option>押金</option>
					</select></td>
					<td>资金种类：</td>
					<td><select class="form-control input-sm">
							<option>对公</option>
							<option>现金</option>
							<option>银行卡</option>
							<option>POS</option>
					</select></td>
					<td>付款时间：</td>
					<td><input type="text" class="form-control input-sm"></td>
				</tr>
				<tr>
					<td>手续费：</td>
					<td><select class="form-control input-sm">
							<option>国内</option>
							<option>境外</option>
					</select></td>
					<td>金额：</td>
					<td><select class="form-control input-sm">
							<option>预付机票款</option>
							<option>机票款</option>
							<option>ETEM使用费</option>
							<option>反税款</option>
							<option>押金</option>
					</select></td>
					<td colspan="2"><input type="text"
						class="form-control input-sm textIpnu" disabled="disabled"></td>
					<td class="bj">币种：</td>
					<td><input type="text" class="form-control input-sm"></td>
				</tr>
				<tr>
					<td>发票：</td>
					<td><select class="form-control input-sm">
							<option>有</option>
							<option>无</option>
					</select></td>
					<td>申请人：</td>
					<td><input type="text" class="form-control input-sm"
						disabled="disabled"></td>
					<td>审批人：</td>
					<td><input type="text" class="form-control input-sm"
						disabled="disabled" value="侯小凌"></td>
					<td>审批结果：</td>
					<td><input type="text" class="form-control input-sm"
						disabled="disabled"></td>
				</tr>
			</table>
			<button type="button" class="btn btn-primary btn-sm bankSlipBtn">上传水单</button>
			<div class="bankSlipImg"></div>
		</div>
	</div>
	<!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	
</body>
</html>