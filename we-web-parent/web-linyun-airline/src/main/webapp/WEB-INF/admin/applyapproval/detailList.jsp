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
		<li><a href="${base}/admin/applyapproval/dataList.html?operation=${obj.operation}"><i class="fa fa-angle-left"></i>返回</a></li>
		<li><h3>审批详情</h3></li>
		<li> </li>
	</ul>
  </header>
  <content class="content-list">
		<ul class="content-ul">
			<li>
				<span>订单号</span>
				<span>${obj.detaillist.ordersnum }</span>
			</li>
			<li>
				<span>支付对象</span>
				<span>${obj.detaillist.name }</span>
			</li>
			<li>
				<span>用途</span>
				<span>${obj.detaillist.purpose }</span>
			</li>
			<li>
				<span>资金种类</span>
				<span>${obj.detaillist.fundType }</span>
			</li>
			<li>
				<span>金额</span>
				<span>${obj.detaillist.payMoney }</span>
			</li>
			<li>
				<span>手续费</span>
				<span>${obj.detaillist.payFees }</span>
			</li>
			<li>
				<span>币种</span>
				<span>${obj.detaillist.payCurrency }</span>
			</li>
			<li>
				<span>发票</span>
				<span>${obj.detaillist.isInvioce }</span>
			</li>
			<li>
				<span>签收时间</span>
				<span>${obj.detaillist.approveTime }</span>
			</li>
			<li>
				<span>申请人</span>
				<span>${obj.detaillist.proposer }</span>
			</li>
		</ul>
		
  </content>
  <input name="id" id="id" value="${obj.detaillist.id }" type="hidden">
  <input name="usingId" id="usingId" value="${obj.detaillist.usingId }" type="hidden">
  <input name="status" id="status" value="${obj.detaillist.orderPnrStatus }" type="hidden">
  <footer>
  	
	<button type="button" onclick="agree();">同意</button>
	<button type="button">拒绝</button>
  </footer>
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!--layer -->
<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">

//删除提示
function agree() {
	layer.confirm("您确认同意吗？", {
	    btn: ["是","否"], //按钮
	    shade: false //不显示遮罩
	}, function(){
		// 点击确定之后
		var url = "${base}/admin/applyapproval/agree.html";
		$.ajax({
			type : 'POST',
			data : {
				id : $('#id').val(),
				usingId : $('#usingId').val(),
				status:$('#status').val()
				
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				alert(data.status);
				if ("200" == data.status) {
					layer.msg("审核成功!", "", 3000);
					 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				     parent.layer.close(index);
				     window.parent.successCallback('3');
				} else {
					layer.msg("审核失败!", "", 3000);
				}
			},
			error : function(xhr) {
				layer.msg("审核失败", "", 3000);
			}
		});
	}, function(){
	    // 取消之后不用处理
	});
}
</script>
</body>
</html>
