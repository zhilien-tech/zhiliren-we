<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
  <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${base}/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/applyApproval.css">
  <title>审批手机-审批详情</title>
</head>
<body>
  <header class="header">
	<ul class="list-ul">
		<li><a href="${base}/admin/applyapproval/dataList.html"><i class="fa fa-angle-left"></i>返回</a></li>
		<li><h3>审批详情</h3></li>
		<li> </li>
	</ul>
  </header>
  <content class="content-list">
		<ul class="content-ul">
			<li>
				<span>订单号</span>
				<span>2016121377643</span>
			</li>
			<li>
				<span>支付对象</span>
				<span>北京神舟国际旅行社集团有限公司</span>
			</li>
			<li>
				<span>用途</span>
				<span>代订机票款</span>
			</li>
			<li>
				<span>资金种类</span>
				<span>对公</span>
			</li>
			<li>
				<span>金额</span>
				<span>9000</span>
			</li>
			<li>
				<span>手续费</span>
				<span>12</span>
			</li>
			<li>
				<span>币种</span>
				<span>人民币</span>
			</li>
			<li>
				<span>发票</span>
				<span>有</span>
			</li>
			<li>
				<span>签收时间</span>
				<span>2017-03-10</span>
			</li>
			<li>
				<span>申请人</span>
				<span>宋淑美</span>
			</li>
		</ul>
  </content>
  <footer>
	<button type="button">同意</button>
	<button type="button">拒绝</button>
  </footer>
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
</body>
</html>
