<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="dist/css/AdminLTE.css">
</head>
<body>

	<div class="modal-content">
		<form method="post" action="${base}/admin/customer/update.html">
			<div class="modal-header">
				<button type="button" class="btn btn-primary right btn-sm"
					data-dismiss="modal">返回</button>
				<input type="submit" class="btn btn-primary right btn-sm"
					data-dismiss="modal" value="更新" />
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tabs_1" data-toggle="tab">基本信息</a></li>
					<li><a href="#tabs_2" data-toggle="tab">线路权限</a></li>
					<li><a href="#tabs_3" data-toggle="tab">附件管理</a></li>
					<li><a href="#tabs_4" data-toggle="tab">业务范围</a></li>
					<li><a href="#tabs_5" data-toggle="tab">财务信息</a></li>
				</ul>
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="tab-pane active" id="tabs_1">
						<input name="comId" type="hidden" value="1" /> <input
							name="agentId" type="hidden" value="1" />
						<!--基本信息-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">公司名称：</label>
							<div class="col-sm-8 padding">
								<input name="name" type="text" class="form-control input-sm"
									value="${obj.customer.name}"  placeholder="聚优国际旅行社（北京）有限公司" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">公司简称：</label>
							<div class="col-sm-3 padding">
								<input name="shortName" type="tel" class="form-control input-sm"
									value="${obj.customer.shortName}" placeholder="请输入公司简称" />
							</div>

							<label class="col-sm-2 text-right padding">负责人：</label>
							<div class="col-sm-3 padding">
								<input name="agent" type="tel" class="form-control input-sm"
									value="${obj.customer.agent}"  placeholder="请输入负责人姓名" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">联系人：</label>
							<div class="col-sm-3 padding">
								<input name="linkMan" type="tel" class="form-control input-sm"
									value="${obj.customer.linkMan}" placeholder="请输入联系人" />
							</div>

							<label class="col-sm-2 text-right padding">联系电话：</label>
							<div class="col-sm-3 padding">
								<input name="telephone" type="tel" class="form-control input-sm"
									value="${obj.customer.telephone}"  placeholder="请输入联系电话" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">网址：</label>
							<div class="col-sm-3 padding">
								<input name="siteUrl" type="tel" class="form-control input-sm"
									value="${obj.customer.siteUrl}" placeholder="请输入网址" />
							</div>

							<label class="col-sm-2 text-right padding">传真：</label>
							<div class="col-sm-3 padding">
								<input name="fax" type="tel" class="form-control input-sm"
									value="${obj.customer.fax}" placeholder="请输入传真" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">地址：</label>
							<div class="col-sm-8 padding">
								<input name="address" type="tel" class="form-control input-sm"
									value="${obj.customer.address}"  placeholder="请输入详细地址" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">旅行社类型：</label>
							<div class="col-sm-3 padding">
								<select id="travelType" name="travelType"
									class="form-control input-sm">
									<option value="1" <c:if test="${'1' eq obj.customer.travelType}">selected</c:if>>出境社</option>
									<option value="2" <c:if test="${'2' eq obj.customer.travelType}">selected</c:if>>国内社</option>
									<option value="3" <c:if test="${'3' eq obj.customer.travelType}">selected</c:if>>综合</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">是否禁用：</label>
							<div class="col-sm-3 padding">
								<select id="forbid" name="forbid" class="form-control input-sm">
									<option value="0" <c:if test="${'0' eq obj.customer.forbid}">selected</c:if>>否</option>
									<option value="1" <c:if test="${'1' eq obj.customer.forbid}">selected</c:if>>是</option>
								</select>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">出发城市：</label>
							<div class="col-sm-3 padding">
								<input name="departureCity" type="tel"
									class="form-control input-sm" value="${obj.customer.departureCity} placeholder="请输入出发城市" />
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_2">
						<!--路线权限-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">国境内陆：</label>
							<div class="col-sm-3 padding">
								<input type="text" class="form-control input-sm" placeholder="" />
							</div>

							<label class="col-sm-2 text-right padding">国际：</label>
							<div class="col-sm-3 padding">
								<input type="text" class="form-control input-sm" placeholder="" />
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_3">
						<!--附件管理-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">附件列表：</label>
							<div class="col-sm-3 padding">
								<input name="appendix" type="file" id="file" />
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tabs_4">
						<!--业务范围-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">业务范围：</label>
							<div class="col-sm-8 padding">
								<textarea name="business" class="form-control textar-hei"></textarea>
							</div>
						</div>
					</div>
					<div class="tab-pane tab-pane1" id="tabs_5">
						<!--财务信息-->
						<div class="form-group row">
							<label class="col-sm-2 text-right padding">签约状态：</label>
							<div class="col-sm-2 padding">
								<select id="contract" name="contract"
									class="form-control input-sm">
									<option value="0" <c:if test="${'0' eq obj.customer.contract}">selected</c:if>>未签约</option>
									<option value="1" <c:if test="${'1' eq obj.customer.contract}">selected</c:if>>已签约</option>
									<option value="2" <c:if test="${'2' eq obj.customer.contract}">selected</c:if>>禁止合作</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">合作时间：</label>
							<div class="col-sm-5 padding">
								<input name="cooperateTime" type="text"
									class="form-control input-sm input-wid"
									placeholder="2015-08-08" /> 至 <input name="cooperateDueTime"
									type="text" class="form-control input-sm input-wid"
									placeholder="2088-09-09" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 text-right padding">付款方式：</label>
							<div class="col-sm-2 padding">
								<select id="payWay" name="payWay" class="form-control input-sm">	
									<option value="1" <c:if test="${'1' eq obj.customer.payWay}">selected</c:if>>现金</option>
									<option value="2" <c:if test="${'2' eq obj.customer.payWay}">selected</c:if>>支票</option>
									<option value="3" <c:if test="${'3' eq obj.customer.payWay}">selected</c:if>>银行汇款</option>
									<option value="4" <c:if test="${'4' eq obj.customer.payWay}">selected</c:if>>第三方</option>
									<option value="5" <c:if test="${'5' eq obj.customer.payWay}">selected</c:if>>其他</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">结算方式：</label>
							<div class="col-sm-2 padding">
								<select id="payType" name="payType"
									class="form-control input-sm">
									<option value="1" <c:if test="${'1' eq obj.customer.payType}">selected</c:if>>月结</option>
									<option value="2" <c:if test="${'2' eq obj.customer.payType}">selected</c:if>>周结</option>
									<option value="3" <c:if test="${'3' eq obj.customer.payType}">selected</c:if>>单结</option>
									<option value="4" <c:if test="${'4' eq obj.customer.payType}">selected</c:if>>其他</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">提供发票：</label>
							<div class="col-sm-2 padding">
								<select id="invoice" name="invoice"
									class="form-control input-sm">
									<option value="0" <c:if test="${'0' eq obj.customer.invoice}">selected</c:if>>否</option>
									<option value="1" <c:if test="${'1' eq obj.customer.invoice}">selected</c:if>>是</option>
								</select>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 text-right padding">发票项目：</label>
							<div class="col-sm-4 padding">
								<input type="tel" class="form-control input-sm" placeholder="" />
							</div>

						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<!-- jQuery 2.2.3 -->
	<script src="plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

