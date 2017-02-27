<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>收/付款</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!--会计 收付款 CSS-->
<link rel="stylesheet" type="text/css"
	href="${base}/public/dist/css/receivePayment.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Content-->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row row-top">
					<div class="col-xs-12">

						<div class="nav-tabs-custom">
							<ul class="nav nav-tabs">
								<li class="active">
									<a href="#tab_1" data-toggle="tab">内陆跨海</a>
								</li>
								<li>
									<a href="#tab_2" data-toggle="tab">国际</a>
								</li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane pane-content active" id="tab_1">
									<!--内陆跨海-->
									<div class="nav-tabs-custom">
										<!--内陆跨海选项卡 内容-->
										<ul class="nav nav-tabs nlkhUL">
											<li class="active">
												<a href="#tab1_1" data-toggle="tab">收款</a>
											</li>
											<li>
												<a href="#tab1_2" data-toggle="tab">付款</a>
											</li>
										</ul>
										<div class="tab-content padding0">
										
											<!-------------------------------------------------start 收款----------------------------------->
											<div class="tab-pane pane-content active" id="tab1_1">
												<div class="box-header">
													<form role="form" class="form-horizontal">
														<div class="form-group row marginBott5 cf">
															<div class="col-md-1 textPadding">
																<select id="inlandRecSelect" class="form-control TimeInput">
																	<option value="0">收款中</option>
																	<option value="1">已收款</option>
																</select>
															</div>
															<div class="col-md-1 textPadding">
																<input id="inlandRecBeginDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'inlandRecEndDate\')}'})" class="form-control TimeInput" placeholder="2017-02-20">
															</div>
															<label class="col-md-1 labelClas">至</label>
															<div class="col-md-1 textPadding">
																<input id="inlandRecEndDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'inlandRecBeginDate\')}'})" class="form-control TimeInput" placeholder="2017-02-22">
															</div>
															<div class="col-md-3 textPadding">
																<!-- 客户名称/订单号/联系人/PNR 搜索框 -->
																<input id="inlandRecInput" type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
															</div>
															<div class="col-md-2">
																<!-- 搜索 按钮 -->
																<button id="inlandRecSearchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
																<button id="inlandRecClearBtn" type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
															</div>
														</div>
													</form>
												</div>
												<div class="box-body">
													<table id="inlandRecTable" class="table table-bordered table-hover">
														<thead>
															<tr>
																<th>订单号</th>
																<th>出发日期</th>
																<th>人数</th>
																<th>销售金额</th>
																<th>总价</th>
																<th>客户名称</th>
																<th>开票人</th>
																<th>状态</th>
																<th>备注</th>
															</tr>
														</thead>
														<tbody>
														</tbody>
													</table>
												</div>
											</div>
											<!-------------------------------------------------end 收款----------------------------------->
											
											<!-------------------------------------------------start 付款----------------------------------->
											<div class="tab-pane pane-content" id="tab1_2">
												<div class="box-header">
													<form role="form" class="form-horizontal">
														<div class="form-group row marginBott5 cf">
															<div class="col-md-1 textPadding">
																<select id="inlandPaySelect" class="form-control TimeInput">
																	<option value="0">付款中</option>
																	<option value="1">已付款</option>
																</select>
															</div>
															<div class="col-md-1 textPadding">
																<input id="inlandPayBeginDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'inlandPayEndDate\')}'})" class="form-control TimeInput" placeholder="2017-02-20">
															</div>
															<label class="col-md-1 labelClas">至</label>
															<div class="col-md-1 textPadding">
																<input id="inlandPayEndDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'inlandPayBeginDate\')}'})" class="form-control TimeInput" placeholder="2017-02-22">
															</div>
															<div class="col-md-3 textPadding">
																<!-- 客户名称/订单号/联系人/PNR 搜索框 -->
																<input id="inlandPayInput" type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
															</div>
															<div class="col-md-2">
																<!-- 搜索 按钮 -->
																<button id="inlandPaySearchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
																<button id="inlandPayClearBtn" type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
															</div>
															<div class="col-md-3">
																<!-- 付款 按钮 -->
																<button id="inlandPayClick" type="button" class="btn btn-primary btn-sm fuKuanBtn1">付款</button>
															</div>
														</div>
													</form>
												</div>
												<div class="box-body">
													<table id="inlandPayTable" class="table table-bordered table-hover">
														<input id="checkedboxPayValue" name="checkedboxPayValue" type="hidden">
														<thead>
															<tr>
																<td class="checkTh"><input type="checkbox" class="checkBoxPayAll"></td>
																<th>订单号</th>
																<th>PNR</th>
																<th>出发日期</th>
																<th>人数</th>
																<th>应付金额</th>
																<th>币种</th>
																<th>收款单位</th>
																<th>状态</th>
																<th>开票人</th>
															</tr>
														</thead>
														<tbody>
														</tbody>
													</table>
												</div>
											</div>
											<!-------------------------------------------------start 付款----------------------------------->
										</div>
									</div>
									<!--end 内陆跨海选项卡 内容-->
								</div>
								<!--end 内陆跨海-->
								
								
								
								<div class="tab-pane pane-content" id="tab_2">
									<!--国际-->
									<ul class="paymentUl">
										<li class="btnStyle">一订</li>
										<li>二订</li>
										<li>三订</li>
										<li>全款</li>
									</ul>
									<div class="nav-tabs-custom">
										<!--国际选项卡 内容-->
										<ul class="nav nav-tabs nlkhUL">
											<li class="active"><a href="#tab2_1" data-toggle="tab">收款</a></li>
											<li><a href="#tab2_2" data-toggle="tab">付款</a></li>
										</ul>
										<div class="tab-content padding0">
											<div class="tab-pane pane-content active" id="tab2_1">
												<!--收款-->
												<div class="box-header">
													<form role="form" class="form-horizontal">
														<div class="form-group row marginBott5 cf">
															<div class="col-md-1 textPadding">
																<select class="form-control TimeInput">
																	<option>已收</option>
																	<option>未收</option>
																</select>
															</div>
															<div class="col-md-1 textPadding">
																<input type="text" class="form-control TimeInput" placeholder="2017-02-20">
															</div>
															<label class="col-md-1 labelClas">至</label>
															<div class="col-md-1 textPadding">
																<input type="text" class="form-control TimeInput" placeholder="2017-02-22">
															</div>
															<div class="col-md-3 textPadding">
																<!-- 客户名称/订单号/联系人/PNR 搜索框 -->
																<input type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
															</div>
															<div class="col-md-2">
																<!-- 搜索 按钮 -->
																<button type="button" class="btn btn-primary btn-sm">搜索</button>
																<button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
															</div>
														</div>
													</form>
												</div>
												<div class="box-body">
													<table id="GJpayTable"
														class="table table-bordered table-hover">
														<thead>
															<tr>
																<th>订单号</th>
																<th>出发日期</th>
																<th>人数</th>
																<th>销售金额</th>
																<th>总价</th>
																<th>客户名称</th>
																<th>开票人</th>
																<th>状态</th>
																<th>备注</th>
															</tr>
														</thead>
														<tbody>
														</tbody>
													</table>
												</div>
											</div>
											<!--end 收款-->
											<div class="tab-pane pane-content" id="tab2_2">
												<!--付款-->
												<div class="box-header">
													<form role="form" class="form-horizontal">
														<div class="form-group row marginBott5 cf">
															<div class="col-md-1 textPadding">
																<select class="form-control TimeInput">
																	<option>未收</option>
																	<option>已收</option>
																</select>
															</div>
															<div class="col-md-1 textPadding">
																<input type="text" class="form-control TimeInput" placeholder="2017-02-20">
															</div>
															<label class="col-md-1 labelClas">至</label>
															<div class="col-md-1 textPadding">
																<input type="text" class="form-control TimeInput" placeholder="2017-02-22">
															</div>
															<div class="col-md-3 textPadding">
																<!-- 客户名称/订单号/联系人/PNR 搜索框 -->
																<input type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
															</div>
															<div class="col-md-2">
																<!-- 搜索 按钮 -->
																<button type="button" class="btn btn-primary btn-sm">搜索</button>
																<button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
															</div>
															<div class="col-md-3">
																<!-- 付款 -->
																<button type="button" class="btn btn-primary btn-sm fuKuanBtn">付款</button>
															</div>
														</div>
													</form>
												</div>
												<div class="box-body">
													<table id="GJreceiveTable"
														class="table table-bordered table-hover">
														<thead>
															<tr>
																<td class="checkTh1"><input type="checkbox"></td>
																<th>订单号</th>
																<th>PNR</th>
																<th>出发日期</th>
																<th>人数</th>
																<th>应付金额</th>
																<th>币种</th>
																<th>收款单位</th>
																<th>状态</th>
																<th>开票人</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td class="checkTd1"><input type="checkbox"></td>
																<td>20170222001</td>
																<td>AKFIRP</td>
																<td>FC28JAN</td>
																<td>5</td>
																<td>2000</td>
																<td>CNY</td>
																<td>北京神舟国际旅行社集团有限公司</td>
																<td>付款中</td>
																<td>张三四</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<!--end 付款-->
										</div>
									</div>
									<!--end 国际选项卡 内容-->
								</div>
								<!--end 国际-->
							</div>
							<!-- end tab-content -->
						</div>
						<!-- end nav-tabs-custom -->
					</div>
				</div>
			</section>
		</div>

		<!--footer-->
		<%@include file="/WEB-INF/public/footer.jsp"%>
		<!--end footer-->
	</div>

	<!-- 引入js文件 -->
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base}/admin/receivePayment/receivePayment.js"></script>
	<!-- 内陆跨海js -->
	<script src="${base}/admin/receivePayment/inlandReceivePay.js"></script>
	<!-- 国际js -->
	<%-- <script src="${base}/admin/receivePayment/internationPayment.js"></script> --%>
	
	<!-- ./wrapper -->
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
</body>
</html>