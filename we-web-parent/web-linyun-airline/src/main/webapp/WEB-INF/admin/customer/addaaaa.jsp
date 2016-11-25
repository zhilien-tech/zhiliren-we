<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="plugins/select2/select2.css">
<link rel="stylesheet" href="dist/css/AdminLTE.css">

</head>


<body>

	<div class="modal-content">
		<form id="form1" method="post"
			action="${base}/admin/customer/add.html">
			<div class="modal-header">
				<button type="button" class="btn btn-primary right btn-sm"
					data-dismiss="modal">返回</button>
				<input type="submit" class="btn btn-primary right btn-sm" value="保存"
					onclick="save()" />
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
				                <select class="form-control select2" style="width: 100%;">
				                  <option selected="selected">北京直立人科技有限公司</option>
				                  <option>聚优国际旅行社（北京）有限公司</option>
				                  <option>阿里巴巴(中国)网络技术有限公司</option>
				                  <option>北京爱奇艺科技有限公司</option>
				                </select>
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
								<select id="travelType" name="travelType"
									class="form-control input-sm">
									<option value="1" selected="selected">张三</option>
									<option value="2">李四</option>
									<option value="3">王五</option>
								</select>
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
							<div class="col-sm-8 padding">
								<select class="form-control select2" multiple="multiple" data-placeholder="请输入出发城市">
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
				                <select class="form-control select2" style="width: 100%;">
				                  <option selected="selected">国境内陆1</option>
				                  <option>国境内陆2</option>
				                  <option>国境内陆3</option>
				                  <option>国境内陆4</option>
				                  <option>国境内陆5</option>
				                </select>
							</div>



							<label class="col-sm-2 text-right padding">国际：</label>
							<div class="col-sm-3 padding">
				                <select class="form-control select2" style="width: 100%;">
				                  <option selected="selected">国际</option>
				                  <option>国际1</option>
				                  <option>国际2</option>
				                  <option>国际3</option>
				                  <option>国际4</option>
				                </select>
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_3">
						<!--附件管理-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">附件列表：</label>
							<div class="col-sm-3 padding">
								<p class="flie_A">
									上传
									<input name="appendix" type="file" id="file" />
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
									<option value="0" selected="selected">未签约</option>
									<option value="1">已签约</option>
									<option value="2">禁止合作</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">合作时间：</label>
							<div class="col-sm-5 padding">
								<input id="datepicker1" name="cooperateTime" type="datetime"
									class="form-control input-sm input-wid"
									placeholder="2015-08-08" /> 
							至 <input id="datepicker2" name="cooperateDueTime"
									type="datetime" class="form-control input-sm input-wid"
									placeholder="2088-09-09" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 text-right padding">付款方式：</label>
							<div class="col-sm-2 padding">
								<select class="form-control input-sm paySele" onchange="paySelect_change(this)">
									<option value="1" selected="selected">现金</option>
									<option value="2">支票</option>
									<option value="3">银行汇款</option>
									<option value="4">第三方</option>
									<option value="5">其他</option>
								</select>
							</div>

							<div class="col-sm-8">
								<div class="col-sm-12 padding payInp">
									
								</div>
							</div>
						</div>



						
						<div class="form-group row">
							<label class="col-sm-2 text-right padding">结算方式：</label>
							<div class="col-sm-2 padding">
								<select class="form-control input-sm sele" onchange="select_change(this)">
									<option value="1" selected="selected">月结</option>
									<option value="2">周结</option>
									<option value="3">单结</option>
									<option value="4">其他</option>
								</select>
							</div>

							<div class="col-sm-8">
								<div class="col-sm-12 padding inpAdd">
									
								</div>
							</div>
						</div>



						<div class="form-group row">
							<label class="col-sm-2 text-right padding">提供发票：</label>
							<div class="col-sm-2 padding">
								<select id="invoice" name="invoice"
									class="form-control input-sm" onchange="gaveInvioce()">
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</select>
							</div>

							<div class="col-sm-8" style="display: none;" id="invioceType">
								<!-- <label class="col-sm-2 text-right padding">发票项目：</label> -->
								<div class="col-sm-12 padding">
									<select class="form-control select2" multiple="multiple" data-placeholder="发票项目">
						                <option>发票项目</option>
						                <option>发票项目1</option>
						                <option>发票项目2</option>
						                <option>发票项目3</option>
						                <option>发票项目4</option>
					            	</select>
								</div>
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
<!-- Select2 -->
<script src="plugins/select2/select2.full.min.js"></script>

<script src="plugins/iCheck/icheck.min.js"></script>
<!-- FastClick 快 点击-->
<script src="plugins/fastclick/fastclick.js"></script>
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

	//保存刷新页面
	function save() {
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

	function sname() {
		$.ajax({
			type : "GET",
			url : "test.json", //访问后台服务器 
			data : {
				name : $("#name").val()
			},
			dataType : "json",
			success : function(data) {
				$('#resText').empty(); //清空resText里面的所有内容
				var html = '';
				$.each(data, function(commentIndex, comment) {
					html += '<div class="comment"><h6>' + comment['username']
							+ ':</h6><p class="para"' + comment['content']
							+ '</p></div>';
				});
				$('#resText').html(html);
			}
		});
	}

	//出发城市
	function goCity() {
		alert();
	}


	//结算方式 add input
	 function select_change(obj){

		   var seleValue=$(".sele").find("option:selected").attr("value");
		   if (seleValue==4) {
		   	$('.inpAdd').append('<input type="text" class="inp form-control input-sm" placeholder="请输入结算方式">');
		   }else{
		   		$('.inp').remove();
		   };
		}

	//付款方式 add input
	 function paySelect_change(obj){

		   var payValue=$(".paySele").find("option:selected").attr("value");
		   if (payValue==5) {
		   	$('.payInp').append('<input type="text" class="paytext form-control input-sm" placeholder="请输入付款方式">');
		   }else{
		   		$('.paytext').remove();
		   };
		}
</script>
</body>
</html>

