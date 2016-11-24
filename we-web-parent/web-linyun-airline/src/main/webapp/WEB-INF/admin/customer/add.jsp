<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">

</head>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
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
								moment().subtract(1, 'month').startOf('month'),
								moment().subtract(1, 'month').endOf('month') ]
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
		$('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red')
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

	//保存页面
	function save() {
		$.ajax({
			type : 'POST',
			data : $("#customerAdd").serialize(),
			url : '${base}/admin/customer/add.html',
			success : function(data) {
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				if ("200" == data.status) {
					layer.msg("修改成功", "", 3000);
				} else {
					layer.msg("修改失败", "", 3000);
				}
				layer.close(index);
				parent.location.reload();

			},
			error : function(xhr) {
				layer.msg("修改失败", "", 3000);
			}
		});
	}

	$("#submit").click(function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : '${base}/admin/customer/add.html',
			data : $('#form1').serialize(),
			error : function(request) {
				layer.msg('添加失败!');
			},
			success : function(data) {
				layer.load(1, {
					shade : [ 0.1, '#fff' ]
				//0.1透明度的白色背景
				});
				layer.msg('添加成功!', {
					time : 5000,
					icon : 6
				});
				window.location.reload(true);
			}
		});
		$(".Mymodal-lg").modal('hide');
	});

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
	
	
	function isLine(){
		alert($("#line").val());
		$.ajax({
			type : 'POST',
			data : {
				departureCity : $("#line").val()
			},
			dataType : 'json',
			url : '${base}/admin/customer/isLine.html',
			success : function(data) {

			}

		});
	}
</script>

<body>

	<div class="modal-content">
		<form id="form1" method="post"
			action="${base}/admin/customer/add.html">
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
						<!-- 上游公司ID  以后会从当前登陆记录-->
						<input name="comId" type="hidden" value="1" />
						<!-- 客户 代理商ID -->
						<input name="agentId" type="hidden" value="1" />
						<!--基本信息-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">公司名称：</label>
							<div class="col-sm-8 padding">
								<input id="name" name="name" type="text"
									class="form-control input-sm" onkeyup="sname()"
									placeholder="聚优国际旅行社（北京）有限公司" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">公司简称：</label>
							<div class="col-sm-3 padding">
								<input name="shortName" type="tel" class="form-control input-sm"
									placeholder="请输入公司简称" />
							</div>

							<label class="col-sm-2 text-right padding">负责人：</label>
							<div class="col-sm-3 padding">
								<input name="agent" type="tel" class="form-control input-sm"
									placeholder="请输入负责人姓名" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">联系人：</label>
							<div class="col-sm-3 padding">
								<input name="linkMan" type="tel" class="form-control input-sm"
									placeholder="请输入联系人" />
							</div>

							<label class="col-sm-2 text-right padding">联系电话：</label>
							<div class="col-sm-3 padding">
								<input name="telephone" type="tel" class="form-control input-sm"
									placeholder="请输入联系电话" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">网址：</label>
							<div class="col-sm-3 padding">
								<input name="siteUrl" type="tel" class="form-control input-sm"
									placeholder="请输入网址" />
							</div>

							<label class="col-sm-2 text-right padding">传真：</label>
							<div class="col-sm-3 padding">
								<input name="fax" type="tel" class="form-control input-sm"
									placeholder="请输入传真" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">地址：</label>
							<div class="col-sm-8 padding">
								<input name="address" type="tel" class="form-control input-sm"
									placeholder="请输入详细地址" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">旅行社类型：</label>
							<div class="col-sm-3 padding">
								<select id="travelType" name="travelType"
									class="form-control input-sm">
									<option value="1" selected="selected">出境社</option>
									<option value="2">国内社</option>
									<option value="3">综合</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">是否禁用：</label>
							<div class="col-sm-3 padding">
								<select id="forbid" name="forbid" class="form-control input-sm">
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</select>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">出发城市：</label>
							<div class="col-sm-3 padding">
								<input id="departureCity" name="departureCity" type="text"
									onkeyup="goCity()" class="form-control input-sm"
									placeholder="请输入出发城市" />
									
								<select class="form-control select2" multiple="multiple"
									data-placeholder="请输入出发城市" >
									<option>上海</option>
									<option>北京</option>
									<option>大连</option>
									<option>江苏</option>
									<option>苏州</option>
								</select>
								
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_2">
						<!--路线权限-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">国境内陆：</label>
							<div class="col-sm-3 padding">
								<input id="line" name="line" type="text" class="form-control input-sm"
									onkeyup="isLine()" />
							</div>

							<label class="col-sm-2 text-right padding">国际：</label>
							<div class="col-sm-3 padding">
								<input id="line" name="line" onkeyup="isLine()" type="text" class="form-control input-sm"/>
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
									<option value="0" selected="selected">未签约</option>
									<option value="1">已签约</option>
									<option value="2">禁止合作</option>
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
								<select id="payWay" name="payWay" class="form-control input-sm">
									<option value="1" selected="selected">现金</option>
									<option value="2">支票</option>
									<option value="3">银行汇款</option>
									<option value="4">第三方</option>
									<option value="5">其他</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">结算方式：</label>
							<div class="col-sm-2 padding">
								<select id="payType" name="payType"
									class="form-control input-sm">
									<option value="1" selected="selected">月结</option>
									<option value="2">周结</option>
									<option value="3">单结</option>
									<option value="4">其他</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">提供发票：</label>
							<div class="col-sm-2 padding">
								<select id="invoice" name="invoice"
									class="form-control input-sm" onchange="gaveInvioce()">
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</select>
							</div>
						</div>

						<div style="display: none;" id="invioceType"
							class="form-group row">
							<label class="col-sm-2 text-right padding">发票项目：</label>
							<div class="col-sm-4 padding">
								<input type="text" class="form-control input-sm" placeholder="" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

