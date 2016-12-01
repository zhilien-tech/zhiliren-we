<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>更新</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/public/css/pikaday.css">
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css" />

</head>

<body>
	<div class="modal-content">
		<form id="updateForm">
			<div class="modal-header">
				<button type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
				<button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
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
						<input name="comId" type="hidden" value="1" /> 
						<!--基本信息-->
						<div class="form-group row">
							<input name="id" type="hidden" value="${obj.customer.id }">
							<label class="col-sm-3 text-right padding">公司名称：</label>
							<div class="col-sm-8 padding">
								<select id="companyId" class="form-control select2" multiple="multiple"  data-placeholder="请输入公司名称">
									<option></option>
									<c:forEach var="one" items="${obj.comEntity }">
										<option value="${one.id }">${one.text}</option>
									</c:forEach>
								</select>
								<!-- 公司ID -->
								<input id="agentId" type="hidden" name="agentId" value="${obj.customer.agentId}"/>
								<!-- 公司名称 -->
								<input id="comName" type="hidden" name="name"/>
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
								<!-- 负责人下拉列表 -->
								<select id="agent" name="agent" class="form-control input-sm">
									<c:forEach var="one" items="${obj.userlist }">
										<option value="${one.id }" <c:if test="${one.id eq obj.customer.agent}">selected</c:if>>${one.userName}</option>
									</c:forEach>
								</select>
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
									value="${obj.customer.telephone}" placeholder="请输入联系电话" />
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
									value="${obj.customer.address}" placeholder="请输入详细地址" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">旅行社类型：</label>
							<div class="col-sm-3 padding">
								<select id="travelType" name="travelType"
									class="form-control input-sm">
									<option value="1"
										<c:if test="${'1' eq obj.customer.travelType}">selected</c:if>>出境社</option>
									<option value="2"
										<c:if test="${'2' eq obj.customer.travelType}">selected</c:if>>国内社</option>
									<option value="3"
										<c:if test="${'3' eq obj.customer.travelType}">selected</c:if>>综合</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">是否禁用：</label>
							<div class="col-sm-3 padding">
								<select id="forbid" name="forbid" class="form-control input-sm">
									<option value="0"
										<c:if test="${'0' eq obj.customer.forbid}">selected</c:if>>否</option>
									<option value="1"
										<c:if test="${'1' eq obj.customer.forbid}">selected</c:if>>是</option>
								</select>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">出发城市：</label>
							
							<div class="col-sm-8 padding">
								<select id="city" class="form-control select2"  multiple="multiple"  data-placeholder="请输入出发城市">
									<option></option>
									<c:forEach var="one" items="${obj.outcitylist }">
										<option value="${one.id }">${one.text}</option>
									</c:forEach>
								</select>
								<!-- 出发城市ID -->
								<input id="outcity" type="hidden" name="outcityname" value = selectedCityId/>
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_2">
						<!--路线权限-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">国境内陆：</label>
							<div class="col-sm-3 padding">
								<select id="isLine" class="form-control select2"  multiple="multiple"  data-placeholder="请输入国境内陆">
									<option></option>
									<c:forEach var="one" items="${obj.innerlinelist }">
										<option value="${one.id }">${one.text}</option>
									</c:forEach>
								</select>
								<!-- 国境内陆ID -->
								<input id="sLine1ID" type="hidden" name="sLine1" value = selectedCityId/>
							</div>

							<label class="col-sm-2 text-right padding">国际：</label>
							<div class="col-sm-3 padding">
								<select id="sLine2ID" class="form-control select2"  multiple="multiple"  data-placeholder="请输入国际线路">
									<option></option>
									<c:forEach var="one" items="${obj.interlinelist }">
										<option value="${one.id }">${one.text}</option>
									</c:forEach>
								</select>
								<!-- 国际线路ID -->
								<input id="line2ID" type="hidden" name="internationLine" value = selectedCityId/>
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_3">
						<!--附件管理-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">附件列表：</label>
							<div class="col-sm-3 padding">
								<input type="file" name="fileID" id="uploadify" />
								<input type="hidden" name="appendix" id="appendix" />
								<p class="flie_A" onclick="fileupload();">
									上传
								</p>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tabs_4">
						<!--业务范围-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">业务范围：</label>
							<div class="col-sm-8 padding">
								<textarea name="business" class="form-control textar-hei" >${obj.customer.business }</textarea>
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
									<option value="0"
										<c:if test="${'0' eq obj.customer.contract}">selected</c:if>>未签约</option>
									<option value="1"
										<c:if test="${'1' eq obj.customer.contract}">selected</c:if>>已签约</option>
									<option value="2"
										<c:if test="${'2' eq obj.customer.contract}">selected</c:if>>禁止合作</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">合作时间：</label>
							<div class="col-sm-5 padding">
								<input id="datepicker1" name="cooperateTime" type="date"
									class="form-control input-sm input-wid"
									placeholder="2015-08-08" /> 至 <input id="datepicker2"
									name="cooperateDueTime" type="date"
									class="form-control input-sm input-wid"
									placeholder="2088-09-09" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 text-right padding">付款方式：</label>
							<div class="col-sm-2 padding">
								<select id="payWay" name="payWay" class="form-control input-sm paySele" onchange="paySelect_change(this)">
									<option value="1"
										<c:if test="${'1' eq obj.customer.payWay}">selected</c:if>>现金</option>
									<option value="2"
										<c:if test="${'2' eq obj.customer.payWay}">selected</c:if>>支票</option>
									<option value="3"
										<c:if test="${'3' eq obj.customer.payWay}">selected</c:if>>银行汇款</option>
									<option value="4"
										<c:if test="${'4' eq obj.customer.payWay}">selected</c:if>>第三方</option>
									<option value="5"
										<c:if test="${'5' eq obj.customer.payWay}">selected</c:if>>其他</option>
								</select>
							</div>

							<div class="col-sm-8">
								<div class="col-sm-12 padding payInp"></div>
							</div>
						</div>


						<div class="form-group row">

							<label class="col-sm-2 text-right padding">结算方式：</label>
							<div class="col-sm-2 padding">
								<select id="payType" name="payType"
								class="form-control input-sm sele"
									onchange="select_change(this)">
									<option value="1"
										<c:if test="${'1' eq obj.customer.payType}">selected</c:if>>月结</option>
									<option value="2"
										<c:if test="${'2' eq obj.customer.payType}">selected</c:if>>周结</option>
									<option value="3"
										<c:if test="${'3' eq obj.customer.payType}">selected</c:if>>单结</option>
									<option value="4"
										<c:if test="${'4' eq obj.customer.payType}">selected</c:if>>其他</option>
								</select>
							</div>

							<div class="col-sm-8">
								<div class="col-sm-12 padding inpAdd"></div>
							</div>
						</div>

						<div class="form-group row">

							<label class="col-sm-2 text-right padding">提供发票：</label>
							<div class="col-sm-2 padding">
								<select id="invoice" name="invoice"
									class="form-control input-sm" onchange="gaveInvioce()">
									<option value="0"
										<c:if test="${'0' eq obj.customer.invoice}">selected</c:if>>否</option>
									<option value="1"
										<c:if test="${'1' eq obj.customer.invoice}">selected</c:if>>是</option>
								</select>
							</div>
						</div>

					<div class="col-sm-8" style="display: none;" id="invioceType">
							<label class="col-sm-2 text-right padding">发票项目：</label>
							<div class="col-sm-8 padding">
								<select id="sInvID" class="form-control select2"  multiple="multiple"  data-placeholder="请输入国际线路">
									<option></option>
									<c:forEach var="one" items="${obj.invoicelist }">
										<option value="${one.id }">${one.text}</option>
									</c:forEach>
								</select>
								<!-- 发票项ID -->
								<input id="sInvName" type="hidden" name="sInvName" value = selectedCityId/>
							</div>

						</div>
					</div>
				</div>
			</div>
</form>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
		var  agentId  = '${obj.customer.agentId}';
	</script>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<script src="${base}/public/plugins/iCheck/icheck.min.js"></script>
	<!-- FastClick 快 点击-->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<script src="${base}/public/dist/js/pikaday.js"></script>
	<!-- 文件上传 -->
	<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />  
	<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	
	<!-- 页面js -->
	<script src="${base}/admin/customer/baseinfo.js"></script>
	<script src="${base}/admin/customer/line.js"></script>
	<script src="${base}/admin/customer/upload.js"></script>
	<script src="${base}/admin/customer/caiwu.js"></script>
	<script type="text/javascript">
		$(function() {
			
			//公司名称回显companyId
			var _comSelect = $("#companyId").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/company.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							page : params.page,
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						
						return {
							results : data
						};
					},
					cache : true
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : true, //设置必须存在的选项 才能选中
			});
			_comSelect.val([${obj.comIds}]).trigger("change");
			
			//出发城市回显
			var _citySelect = $("#city").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/goCity.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							page : params.page,
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						
						return {
							results : data
						};
					},
					cache : true
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 5, //设置最多可以选择多少项
				tags : true, //设置必须存在的选项 才能选中
			});
			_citySelect.val([${obj.outcityIds}]).trigger("change");
			
			//国境内陆回显
			var _innerLineSelect = $("#isLine").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/isLine.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							page : params.page,
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						
						return {
							results : data
						};
					},
					cache : true
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 5, //设置最多可以选择多少项
				tags : true, //设置必须存在的选项 才能选中
			});
			_innerLineSelect.val([${obj.innerCityIds}]).trigger("change");
			
			//国际线路回显
			var _interLineSelect = $("#sLine2ID").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/international.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							page : params.page,
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						
						return {
							results : data
						};
					},
					cache : true
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 5, //设置最多可以选择多少项
				tags : true, //设置必须存在的选项 才能选中
			});
			_interLineSelect.val([${obj.interLineIds}]).trigger("change");
			
			//发票项回显
			var _invioceSelect = $("#sInvID").select2({
				ajax : {
					url : BASE_PATH  + "/admin/customer/isInvioce.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							q : params.term, // search term
							page : params.page,
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						
						return {
							results : data
						};
					},
					cache : true
				},
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 5, //设置最多可以选择多少项
				tags : true, //设置必须存在的选项 才能选中
			});
			_invioceSelect.val([${obj.invioceIds}]).trigger("change");
			
			
			
			gaveInvioce();
			
			//iCheck for checkbox and radio inputs
			$('input[type="checkbox"].minimal, input[type="radio"].minimal')
					.iCheck({
						checkboxClass : 'icheckbox_minimal-blue',
						radioClass : 'iradio_minimal-blue'
					});
			//Red color scheme for iCheck
			$(
					'input[type="checkbox"].minimal-red, input[type="radio"].minimal-red')
					.iCheck({
						checkboxClass : 'icheckbox_minimal-red',
						radioClass : 'iradio_minimal-red'
					});
			//Flat red color scheme for iCheck
			$('input[type="checkbox"].flat-red, input[type="radio"].flat-red')
					.iCheck({
						checkboxClass : 'icheckbox_flat-green',
						radioClass : 'iradio_flat-green'
					});

		});

		//更新时刷新页面
		function update() {
			window.location.reload();
		}

		//显示或隐藏发票项
		function gaveInvioce() {
			var s = document.getElementById("invoice").value;
			if (s == 1) {
				document.getElementById("invioceType").style.display = "";
			}
			if (s == 0) {
				document.getElementById("invioceType").style.display = "none";
			}
		}

		//更新弹框提示
		$("#submit").click(function() {
			//出发城市ID
			var selectedCityId = $("#city").select2("val") ;
			$("#outcity").val(selectedCityId) ;
			//代理商ID
			var selectedcompanyId = $("#companyID").select2("val") ;
			$("#agentId").val(selectedcompanyId) ;
			//代理商名称
			var selectedcompanyName = $('#companyID').find("option:selected").text();
			$("#comName").val(selectedcompanyName);
			
			//出发城市ID
			var selectedisLine = $("#isLine").select2("val") ;
			$("#sLine1ID").val(selectedisLine) ;
			//出发城市ID
			var selectedsLine2ID = $("#sLine2ID").select2("val") ;
			$("#line2ID").val(selectedsLine2ID) ;
			//出发城市ID
			var selectedsInvID = $("#sInvID").select2("val") ;
			$("#sInvName").val(selectedsInvID) ; 
			
			$.ajax({
				type : "POST",
				url : '${base}/admin/customer/update.html',
				data : $('#updateForm').serialize(),// 你的formid
				error : function(request) {
					layer.msg('修改失败!');
				},
				success : function(data) {
					layer.load(1, {
						shade : [ 0.1, '#fff' ]
					//0.1透明度的白色背景
					});
					layer.msg('修改成功!', {
						time : 5000,
						icon : 6
					});
				}
			});
		});

	</script>
</body>
</html>

