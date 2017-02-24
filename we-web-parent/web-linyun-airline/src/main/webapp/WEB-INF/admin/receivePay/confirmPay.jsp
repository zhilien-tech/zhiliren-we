<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>确认付款</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
    <link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css"><!--本页面style-->
</head>
<body>
	<div class="modal-top">
		<div class="modal-header boderButt">
			<button type="button" class="btn btn-primary right btn-sm">取消</button>
			<input type="submit" id="submit" class="btn btn-primary right btn-sm" value="提交" />
			<h4>收款</h4>
		</div>
		<div class="modal-body" style="height: 483px; overflow-y: auto;">
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
						<td>05Aug26</td>
						<td>JCH-161210</td>
						<td>JQ</td>
						<td>吕超容</td>
						<td>周勇</td>
						<td>9650.00</td>
					</tr>
					<tr>
						<td>2016121200001</td>
						<td>05Aug26</td>
						<td>JCH-161210</td>
						<td>JQ</td>
						<td>吕超容</td>
						<td>周勇</td>
						<td>9650.00</td>
					</tr>
					<tr>
						<td>2016121200001</td>
						<td>05Aug26</td>
						<td>JCH-161210</td>
						<td>JQ</td>
						<td>吕超容</td>
						<td>周勇</td>
						<td>9650.00</td>
					</tr>
				</tbody>
			</table>
			<table border="0" class="selectTable">
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
			<label class="labelShuidan">水单</label>
			<div class="bankSlipImg SDdiv"></div>
		</div>
	</div>
	<!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<!-- AdminLTE App -->
</body>
</html>
