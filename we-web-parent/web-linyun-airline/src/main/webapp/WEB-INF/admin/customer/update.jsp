<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>更新</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
</head>

<body>
	<div class="modal-content">
		<form id="form1">
			<div class="modal-header">
				<button type="button" class="btn btn-primary right btn-sm"
					data-dismiss="modal">返回</button>
				<button type="button" id="submit"
					class="btn btn-primary right btn-sm">保存</button>
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
							<input name="id" type="hidden" value="${obj.customer.id}" /> <label
								class="col-sm-3 text-right padding">公司名称：</label>
							<div class="col-sm-8 padding">
								<input name="name" type="text" class="form-control input-sm"
									value="${obj.customer.name}"  onkeyup="sname() placeholder="聚优国际旅行社（北京）有限公司" />
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
									value="${obj.customer.agent}" placeholder="请输入负责人姓名" />
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
							<div class="col-sm-3 padding">
	<input id="departureCity" name="departureCity" type="text"
									onkeyup="goCity()" class="form-control input-sm"
									placeholder="请输入出发城市" value="${obj.customer.departureCity}"/>
								
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_2">
						<!--路线权限-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">国境内陆：</label>
							<div class="col-sm-3 padding">
								<input id="line" name="line" type="text"
									class="form-control input-sm" onkeyup="isLine()" />
							</div>

							<label class="col-sm-2 text-right padding">国际：</label>
							<div class="col-sm-3 padding">
								<input id="line" name="line" onkeyup="isLine()" type="text"
									class="form-control input-sm" />
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_3">
						<!--附件管理-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">附件列表：</label>
							<div class="col-sm-3 padding">
									<p class="flie_A">
									上传 <input name="appendix" type="file" id="file" />
								</p>
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
								<select id="payWay" name="payWay"
 class="form-control input-sm paySele"
									onchange="paySelect_change(this)">
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
								<input type="text" class="form-control input-sm" placeholder="" />
							</div>

						</div>
					</div>
				</div>
			</div>
</form>
	</div>

	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>

	<script src="${base}/public/plugins/iCheck/icheck.min.js"></script>
	<!-- FastClick 快 点击-->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>

	<script src="${base}/common/js/layer/layer.js"></script>

	<script type="text/javascript">
		$(function() {
			//Initialize Select2 Elements
			$(".select2").select2();

			//Datemask dd/mm/yyyy
			$("#datemask").inputmask("dd/mm/yyyy", {
				"placeholder" : "dd/mm/yyyy"
			});
			//Datemask2 mm/dd/yyyy
			$("#datemask2").inputmask("mm/dd/yyyy", {
				"placeholder" : "mm/dd/yyyy"
			});
			//Money Euro
			$("[data-mask]").inputmask();

			//Date range picker
			$('#reservation').daterangepicker();
			//Date range picker with time picker
			$('#reservationtime').daterangepicker({
				timePicker : true,
				timePickerIncrement : 30,
				format : 'MM/DD/YYYY h:mm A'
			});
			//Date range as a button
			$('#daterange-btn').daterangepicker(
					{
						ranges : {
							'Today' : [ moment(), moment() ],
							'Yesterday' : [ moment().subtract(1, 'days'),
									moment().subtract(1, 'days') ],
							'Last 7 Days' : [ moment().subtract(6, 'days'),
									moment() ],
							'Last 30 Days' : [ moment().subtract(29, 'days'),
									moment() ],
							'This Month' : [ moment().startOf('month'),
									moment().endOf('month') ],
							'Last Month' : [
									moment().subtract(1, 'month').startOf(
											'month'),
									moment().subtract(1, 'month')
											.endOf('month') ]
						},
						startDate : moment().subtract(29, 'days'),
						endDate : moment()
					},
					function(start, end) {
						$('#daterange-btn span').html(
								start.format('MMMM D, YYYY') + ' - '
										+ end.format('MMMM D, YYYY'));
					});

			//Date picker
			$('#datepicker1').datepicker({
				autoclose : true
			});
			$('#datepicker2').datepicker({
				autoclose : true
			});

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

			//Colorpicker
			$(".my-colorpicker1").colorpicker();
			//color picker with addon
			$(".my-colorpicker2").colorpicker();

			//Timepicker
			$(".timepicker").timepicker({
				showInputs : false
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
			$.ajax({
				cache : true,
				type : "POST",
				url : '${base}/admin/customer/update.html',
				data : $('#form1').serialize(),// 你的formid
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
					window.location.reload(true);
				}
			});
			$(".Mymodal-lg").modal('hide');
		});

		function sname() {
		}

		//出发城市
		function goCity() {
			alert($("#departureCity").val());
			$.ajax({
				type : 'POST',
				data : {
					departureCity : $("#departureCity").val()
				},
				dataType : 'json',
				url : '${base}/admin/customer/goCity.html',
				success : function(data) {

				}

			});
		}
	</script>
</body>
</html>

