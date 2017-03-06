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
<title>收付款-国际</title>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!--本页面 CSS样式-->
<link rel="stylesheet" type="text/css" href="${base}/public/dist/css/receivePayment.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Content-->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row row-top">
					<div class="col-xs-12">
						<div class="box">
					        <ul class="paymentUl">
		                        <li id="firBooking" class="btnStyle">一订</li>
		                        <li id="secBooking" >二订</li>
		                        <li id="thrBooking" >三订</li>
		                        <li id="allBooking" >全款</li>
					        </ul>
							<div class="nav-tabs-custom tabConte">
								<ul class="nav nav-tabs">
									<li class="active">
										<a href="#tab_1"  onclick="toConfirmRecPage();" data-toggle="tab">收款</a>
									</li>
									<li>
										<a href="#tab_2" onclick="toConfirmPayPage();" data-toggle="tab">付款</a>
									</li>
								</ul>
								<div class="tab-content">
									<!-- ---------------------------start 收款------------------------------- -->
									<div class="tab-pane pane-content active" id="tab_1">
										<div class="box-header">
											<form role="form" class="form-horizontal">
												<div class="form-group row marginBott5 cf">
													<div class="col-md-1 textPadding">
														<select id="internationalRecSelect" class="form-control TimeInput">
															<option value=0>收款中</option>
															<option value=1>已收款</option>
														</select>
													</div>
													<div class="col-md-1 textPadding">
														<input id="internationalRecBeginDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'internationalRecEndDate\')}'})" class="form-control TimeInput" placeholder="2017-02-20">
													</div>
													<label class="col-md-1 labelClas">至</label>
													<div class="col-md-1 textPadding">
														<input id="internationalRecEndDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'internationalRecBeginDate\')}'})" class="form-control TimeInput" placeholder="2017-02-22">
													</div>
													<div class="col-md-3 textPadding">
														<!-- 客户名称/订单号/联系人/PNR 搜索框 -->
														<input id="internationalRecInput" type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
													</div>
													<div class="col-md-2">
														<!-- 搜索 按钮 -->
														<button id="internationalRecSearchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
														<button id="internationalRecClearBtn" type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
													</div>
												</div>
											</form>
										</div>
										<div class="box-body">
											<table id="internationalRecTable" class="table table-bordered table-hover">
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
									<!-- ---------------------------end 收款------------------------------- -->
	
									<!-- --------------------------start 付款------------------------------- -->
									<div class="tab-pane pane-content" id="tab_2">
										<div class="box-header">
											<form role="form" class="form-horizontal">
												<div class="form-group row marginBott5 cf">
													<div class="col-md-1 textPadding">
														<select id="internationalPaySelect" class="form-control TimeInput">
															<option value=1>付款中</option>
															<option value=2>已付款</option>
														</select>
													</div>
													<div class="col-md-1 textPadding">
														<input id="internationalPayBeginDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'internationalPayEndDate\')}'})" class="form-control TimeInput" placeholder="2017-02-20">
													</div>
													<label class="col-md-1 labelClas">至</label>
													<div class="col-md-1 textPadding">
														<input id="internationalPayEndDate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'internationalPayBeginDate\')}'})" class="form-control TimeInput" placeholder="2017-02-22">
													</div>
													<div class="col-md-3 textPadding">
														<!-- 客户名称/订单号/联系人/PNR 搜索框 -->
														<input id="internationalPayInput" type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
													</div>
													<div class="col-md-3">
														<!-- 搜索 按钮 -->
														<button id="internationalPaySearchBtn" type="button" class="btn btn-primary btn-sm">搜索</button>
														<button id="internationalPayClearBtn" type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
														<button id="internationalPayCancelBtn" type="button" class="btn btn-primary btn-sm ckBtn">取消所有勾选</button>
													</div>
													<div class="col-md-2">
														<!-- 付款 按钮 -->
														<button id="internationalPayClick" type="button" class="btn btn-primary btn-sm fuKuanBtn1">付款</button>
													</div>
												</div>
											</form>
										</div>
										<div class="box-body">
											<!-- ---------------------------------- 付款中   列表 ------------------------------------- -->
											<table id="internationalPayTable" class="table table-bordered table-hover">
												<input id="checkedboxPayValue" name="checkedboxPayValue" type="hidden">
												<thead id="internationalPayThead">
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
												<tbody id="internationalPayTbody">
	
												</tbody>
											</table>
											<!-- ---------------------------------- 已收款    列表 ------------------------------------ -->
											<table id="internationalPayEdTable" style="display: none" class="table table-bordered table-hover">
												<thead id="internationalPayEdThead">
													<tr>
														<th>订单号</th>
														<th>PNR</th>
														<th>出发日期</th>
														<th>人数</th>
														<th>应付金额</th>
														<th>币种</th>
														<th>总额</th>
														<th>收款单位</th>
														<th>状态</th>
														<th>开票人</th>
														<th>备注</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody id="internationalPayEdTbody">
												</tbody>
											</table>
	
										</div>
									</div>
									<!-- --------------------------end 付款------------------------------- -->
	
								</div>
								<!-- end tab-content -->
							</div>
							<!-- end nav-tabs-custom -->
						</div>
					</div>
				</div>
			</section>
		</div>

		<!-- Footer -->
		<%@include file="/WEB-INF/public/footer.jsp"%>
	</div>

	<!-- REQUIRED JS SCRIPTS -->
	<!-- 引入js文件 -->
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!-- 国际js -->
	<script src="${base}/admin/receivePayment/interPay.js"></script>
	<script src="${base}/admin/receivePayment/interReceive.js"></script>
	<!-- 国际js -->
	<%-- <script src="${base}/admin/receivePayment/interPayment.js"></script> --%>
	<script type="text/javascript">
		$(function(){
			toConfirmRecPage();
		});
	</script>
	
</body>
</html>
