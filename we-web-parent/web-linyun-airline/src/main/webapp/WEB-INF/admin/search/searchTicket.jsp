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
<title>查询</title>
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<!-- 图标 -->
<link rel="stylesheet"
	href="${base}/public/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/public/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/query.css">
<!--本页面styleFlie-->

<style type="text/css">
.select2-container {
	width: 95.5% !important;
	display: inline-block;
}

.seleSpanWid .select2-container {
	width: 98.5% !important;
	display: inline-block;
}

.inpNone .select2 .selection span ul li+li {
	display: none;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!--right Content-->
		<div class="content-wrapper">
			<section class="content">
				<div class="customerInfo">
					<div class="infoTop">
						<p>客户信息</p>
						<div class="infoTopContent">
							<input type="checkbox" class="conCheckInput" checked="checked"><label>生成订单</label>
							<select class="form-control input-sm conSelect cf">
								<option value="1" selected="selected">查询</option>
								<option value="2">预定</option>
								<option value="3">一订</option>
								<option value="4">二订</option>
								<option value="5">尾款</option>
								<option value="6">出票</option>
								<option value="7">开票</option>
								<option value="8">关闭</option>
							</select> 
							<label>提醒：</label> 
							<input id="datepicker" name="datepicker" type="text" class="form-control input-sm conTimeInput" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="请选择提醒日期"> 
							<input type="button" class="btn btn-primary btn-sm" value="保存"> 
							<input type="button" class="btn btn-primary btn-sm" value="取消">
						</div>
					</div>
					<div class="infofooter">
						<table>
							<tr>
								<td><label>客户姓名：</label></td>
								<td>
									<!-- <input type="text" class="form-control input-sm" placeholder="请输入客户姓名"> -->
									<select id="linkNameId" name="linkName" class="form-control input-sm" multiple="multiple" data-placeholder="请输入客户姓名"></select>
								</td>
								<input type="hidden" id="linkManId"> 
								<input type="hidden" id="phoneId">
								<td><label>电话：</label></td>
								<td>
									<!-- <input type="text" class="form-control input-sm" placeholder="请输入电话"> -->
									<select id="phoneNumId" name="phoneNum" class="form-control input-sm" multiple="multiple" data-placeholder="请输入电话"></select>
								</td>
								<td><label>地址：</label></td>
								<td>
									<input id="addressId" type="text" disabled="disabled" class="form-control input-sm addressInput">
								</td>
								<td>
									<input id="clearBtn" type="button" value="清空" class="btn btn-primary btn-sm">
									<i class="UnderIcon fa fa-chevron-circle-down"></i>
								</td>
							</tr>
						</table>
						

						<table class="hideTable none">
							<tr>
								<td><label>公司简称：</label></td>
								<td>
									<input id="shortNameId" type="text" disabled="disabled" class="form-control input-sm">
								</td>
								<td><label>负责人：</label></td>
								<td>
									<input id="agentId" type="text" disabled="disabled" class="form-control input-sm">
								</td>
								<td><label>网址：</label></td>
								<td>
									<input id="siteUrlId" type="text" disabled="disabled" class="form-control input-sm">
								</td>
								<td><label>传真：</label></td>
								<td>
									<input id="faxId" type="text" disabled="disabled" class="form-control input-sm">
								</td>
								<td><label>出发城市：</label></td>
								<td>
									<!-- <input id="departureCityId" type="text" disabled="disabled" class="form-control input-sm"> -->
									<select id="city" class="form-control input-sm" multiple="multiple" Disabled = "true" data-placeholder="">
										<option></option>
										<c:forEach var="one" items="${obj.outcitylist }">
											<option value="${one.id }">${one.text}</option>
										</c:forEach>
									</select>
								</td>

							</tr>
							<tr>
								<td><label>结算方式：</label></td>
								<td colspan="9"><pre><span id="payTypeId"></span> 信用额度：0  临时额度：0  历史欠款：0  预存款：0</pre></td>
							</tr>
						</table>

					</div>
				</div>
				<!--客户信息-->
				<div class="listInfo"></div>
				<!--购票查询 列表-->
			</section>
		</div>
		<!--end right Content-->
		<!--footer-->
			<%@include file="/WEB-INF/public/footer.jsp"%>
		<!--end footer-->

		<script type="text/javascript">
			var BASE_PATH = '${base}';
		</script>
		<!-- jQuery 2.2.3 -->
		<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<!-- Bootstrap 3.3.6 -->
		<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
		<!-- Slimscroll -->
		<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
		<!-- FastClick -->
		<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
		<!-- select2 -->
		<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
		<!-- My97DatePicker -->
		<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
		<!-- layer -->
		<script src="${base}/common/js/layer/layer.js"></script>
		<script type="text/javascript">
	      $(function(){
	        $('.UnderIcon').on('click',function(){
	          $('.hideTable').toggle('400');
	        });
	      });
	    </script>
	    <script src="${base}/admin/searchTicket/searchCustomerInfo.js"></script>
	
</body>
</html>
