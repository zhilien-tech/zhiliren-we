<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/public/css/pikaday.css">
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base }/public/dist/css/bootstrapValidator.css" />

</head>

<body>

	<div class="modal-content">
		<form id="customerAddForm">
			<div class="modal-header">
				<button id="backBtn" type="button" onclick="reload()"
					class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>

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
						<!-- TODO -->
						<input name="comId" type="hidden" value="1" />

						<!-- 客户 代理商ID -->
						<input name="agentId" type="hidden" value="1" />


						<!--基本信息-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">公司名称：</label>
							<div class="col-sm-8 padding">
								<input id="companyID" name="name" type="text"
									class="form-control input-sm" onkeyup="sname()"
									placeholder="请输入公司名称" onkeyup="" />


							</div>

						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">公司简称：</label>
							<div class="col-sm-3 padding">
								<input name="shortName" type="text"
									class="form-control input-sm" placeholder="请输入公司简称" />
							</div>

							<label class="col-sm-2 text-right padding">负责人：</label>
							<div class="col-sm-3 padding">
								<!-- <input name="agent" type="tel" class="form-control input-sm"
									placeholder="请输入负责人姓名" /> -->
								<!-- 负责人下拉列表 -->
								<select id="agentId" name="agent" class="form-control input-sm">

								</select>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">联系人：</label>
							<div class="col-sm-3 padding">
								<input name="linkMan" type="text" class="form-control input-sm"
									placeholder="请输入联系人" />
							</div>

							<label class="col-sm-2 text-right padding">联系电话：</label>
							<div class="col-sm-3 padding">
								<input name="telephone" type="text"
									class="form-control input-sm" placeholder="请输入联系电话" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">网址：</label>
							<div class="col-sm-3 padding">
								<input name="siteUrl" type="text" class="form-control input-sm"
									placeholder="请输入网址" />
							</div>

							<label class="col-sm-2 text-right padding">传真：</label>
							<div class="col-sm-3 padding">
								<input name="fax" type="text" class="form-control input-sm"
									placeholder="请输入传真" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">地址：</label>
							<div class="col-sm-8 padding">
								<input name="address" type="text" class="form-control input-sm"
									placeholder="请输入详细地址" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">旅行社类型：</label>
							<div class="col-sm-3 padding">
								<select id="travelTypeID" name="travelType"
									class="form-control input-sm">
									<option value="1" selected="selected">出境社</option>
									<option value="2">国内社</option>
									<option value="3">综合</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">是否禁用：</label>
							<div class="col-sm-3 padding">
								<select id="forbidID" name="forbid"
									class="form-control input-sm">
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</select>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">出发城市：</label>
							<div class="col-sm-8 padding">

								<!--<input id="departureCityID" name="departureCity" type="text"
									onkeyup="goCity()" class="form-control input-sm"
									placeholder="请输入出发城市" />  -->

								<select id="city" class="form-control select2"  multiple="multiple" data-placeholder="请输入出发城市">
								</select>


							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_2">
						<!--路线权限-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">国境内陆：</label>
							<div class="col-sm-3 padding">
								<input id="line1ID" name="line1" onkeyup="goLine1()" type="text"
									class="form-control input-sm" /> <select id="sLine1ID"
									name="sLine1" class="form-control select2"
									style="display: none;" multiple="multiple">

								</select>
							</div>

							<label class="col-sm-2 text-right padding">国际：</label>
							<div class="col-sm-3 padding">
								<input id="line2ID" name="line2" onkeyup="goLine2()" type="text"
									class="form-control input-sm" /> <select id="sLine2ID"
									name="sLine2" class="form-control select2"
									style="display: none;" multiple="multiple">
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
									上传 <input name="appendix" type="file" id="fileID" /> 
									<input type="button" name="fileLoad" id="fileLoad" value="上传" onclick="fileupload()" />
								</p>
							</div>
						</div>
					</div>
					<div class="tab-pane" id="tabs_4">
						<!--业务范围-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">业务范围：</label>
							<div class="col-sm-8 padding">
								<textarea id="businessID" name="business"
									class="form-control textar-hei"></textarea>
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
								<input id="datepicker1" name="contractTime" type="text"
									class="form-control input-sm input-wid"
									placeholder="2015-08-08" /> 至 <input id="datepicker2"
									name="contractDueTime" type="text"
									class="form-control input-sm input-wid"
									placeholder="2088-09-09" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 text-right padding">付款方式：</label>
							<div class="col-sm-2 padding">

								<select id="payWayID" name="payWay"
									class="form-control input-sm paySele"
									onchange="paySelect_change(this)">
									<option value="1" selected="selected">现金</option>
									<option value="2">支票</option>
									<option value="3">银行汇款</option>
									<option value="4">第三方</option>
									<option value="5">其他</option>
								</select>
							</div>

							<div class="col-sm-8">
								<div class="col-sm-12 padding payInp"></div>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2 text-right padding">结算方式：</label>
							<div class="col-sm-2 padding">
								<select id="payTypeID" name="payType"
									class="form-control input-sm sele"
									onchange="select_change(this)">

									<option value="1" selected="selected">月结</option>
									<option value="2">周结</option>
									<option value="3">单结</option>
									<option value="4">其他</option>
								</select>
							</div>
							<div class="col-sm-8">
								<div class="col-sm-12 padding inpAdd"></div>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 text-right padding">提供发票：</label>
							<div class="col-sm-2 padding">
								<select id="invoiceID" name="invoice"
									class="form-control input-sm" onchange="gaveInvioce()">
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</select>
							</div>
							<div class="col-sm-8" style="display: none;" id="invioceType">
								<div class="col-sm-12 padding">
									<input type="text" class="form-control input-sm"
										placeholder="提供发票项" />
								</div>
							</div>
						</div>


						<div class="col-sm-8" style="display: block;" id="invioceType">
							<label class="col-sm-2 text-right padding">发票项目：</label>
							<div class="col-sm-8 padding">
								<input id="inInvioceID" name="inInvioce" onkeyup="goToInvioce()"
									type="text" class="form-control input-sm" placeholder="请输入发票项" />
								<select id="sInvID" name="sInv" class="form-control select2"
									style="display: none;" multiple="multiple"></select>

							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	</form>
	</div>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- 文件上传 -->
	<script src="${base}/public/uploadFile/ajaxfileupload.js"></script>

	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<!-- Select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>

	<script src="${base}/public/plugins/iCheck/icheck.min.js"></script>
	<!-- FastClick 快 点击-->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>

	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<script src="${base}/public/dist/js/pikaday.js"></script>
	<script type="text/javascript">
		$(function() {
			
			$("#city").select2({
					ajax : {
						url : "${base}/admin/customer/goCity.html",
						dataType : 'json',
						delay : 250,
						type : 'post',
						data : function(params) {
							return {
								q : params.term, // search term
								page : params.page
							};
						},
						processResults : function(data, params) {
							var content = "";
							
							for (var i = 0; i < data.length; i++) {
								var dictName = data[i].dictName;
								var id = data[i].id;
								content += '<option value="' + id + '" onclick="optCity(this)">' + dictName + '</option>';
							}
							console.log(content);
							document.getElementById("city").innerHTML=content;
							//$("#city").html(content);
							$("#city").css("display", "block");
							$("#city").remove("");
							params.page = params.page || 1;

							return {
								results : data.items
							};
						},
						cache : true
					},
					escapeMarkup : function(markup) {
						return markup;
					}, // let our custom formatter work
					minimumInputLength : 1,
					language : "zh-CN", //设置 提示语言
					maximumSelectionLength : 3, //设置最多可以选择多少项
					placeholder : "请选择",
					tags : false, //设置必须存在的选项 才能选中
					templateResult : function(repo) { //搜索到结果返回后执行，可以控制下拉选项的样式
						/* console.log("====================templateResult开始==================");
						console.log(repo);
						console.log("====================templateResult结束==================");
						if (repo.loading)
							return repo.text;
						var markup = "<div class=''>" + repo.text
								+ "</div>";
						return markup; */
					},
					templateSelection : function(repo) { //选中某一个选项是执行
						/* console.log("------------------templateSelection开始-------------------------------------");
						console.log(repo);
						console.log("------------------templateSelection结束-------------------------------------");
						return repo.full_name || repo.text; */
					}
				});

			var picker1 = new Pikaday(
				    {
				        field: document.getElementById('datepicker1'),
				        firstDay: 1,
				        minDate: new Date('2000-01-01'),
				        maxDate: new Date('3099-12-31'),
				        yearRange: [2000,3099]
				    });
			var picker2 = new Pikaday(
				    {
				        field: document.getElementById('datepicker2'),
				        firstDay: 1,
				        minDate: new Date('2000-01-01'),
				        maxDate: new Date('3099-12-31'),
				        yearRange: [2000,3099]
				    });

			//iCheck for checkbox and radio inputs
			$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
						checkboxClass : 'icheckbox_minimal-blue',
						radioClass : 'iradio_minimal-blue'
					});
			//Red color scheme for iCheck
			$(
					'input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
						checkboxClass : 'icheckbox_minimal-red',
						radioClass : 'iradio_minimal-red'
					});
			//Flat red color scheme for iCheck
			$('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
						checkboxClass : 'icheckbox_flat-green',
						radioClass : 'iradio_flat-green'
					});


			//校验
			$('#customerAddForm').bootstrapValidator({
				message : '验证不通过',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					name : {
						validators : {
							notEmpty : {
								message : '公司名称不能为空'
							}
						}
					},
					shortName : {
						validators : {
							regexp : {
								regexp : /^[\u4e00-\u9fa5A-Za-z]{1,6}$/,
								message : '公司简称长度最多为6'
							}
						}
					},
					agent : {
						validators : {
							notEmpty : {
								message : '负责人不能为空'
							}
						}
					},
					linkMan : {
						validators : {
							notEmpty : {
								message : '联系人不能为空'
							}
						}
					},
					telephone : {
						validators : {
							notEmpty : {
								message : '联系电话不能为空'
							},
							regexp : {
								regexp : /^[1][34578][0-9]{9}$/,
								message : '联系电话格式错误'
							}
						}
					},
					address : {
						validators : {
							notEmpty : {
								message : '公司地址不能为空'
							}
						}
					}
				}
			});

			/* 负责人名称 下拉列表*/
			function angentList() {
				$.ajax({
					type : 'POST',
					dataType : 'json',
					url : '${base}/admin/customer/agent.html',
					success : function(data) {
						var content = "";
						for (var i = 0; i < data.length; i++) {
							var name = data[i].name;
							var id = data[i].id;
							content += "<option value='" + id
									+ "' onclick='optAgent(this)'>" + name
									+ "</option>";
						}
						$("#agentID").html(content);
					}
				});
			}

		});
		/* 页面初始化加载完毕 */
	</script>
	
	<!-- 下拉列表 -->
	<script type="text/javascript">
		/* 公司名称下拉列表输入框 */
		function companyList() {
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${base}/admin/customer/company.html',
				success : function(data) {
					var content = "";
					for (var i = 0; i < data.length; i++) {
						var comName = data[i].comName;
						var id = data[i].id;
						content += "<option value='" + id
								+ "' onclick='optCompany(this)'>" + comName
								+ "</option>";
					}
					$("#company").html(content);
				}
			});
		}
	
		//出发城市
		function goCity() {
			$.ajax({
				type : 'POST',
				data : {
					departureCity : $("#departureCityID").val()
				},
				dataType : 'json',
				url : '${base}/admin/customer/goCity.html',
				success : function(data) {
					var content = "";
					for (var i = 0; i < data.length; i++) {
						var dictName = data[i].dictName;
						var id = data[i].id;
						content += "<option value='" + id
								+ "' onclick='optCity(this)'>" + dictName
								+ "</option>";
					}
					$("#city").html(content);
					$("#city").css("display", "block");
				}
			});
		}
	
		$("input[type='search']").keyup(function() {
			goCity();
		});
	
		//每个城市的点击事件
		function optCity(obj) {
			alert(obj.id);
		}
	
		//国境内陆线路
		function goLine1() {
			$.ajax({
				type : 'POST',
				data : {
					line : $("#line1ID").val()
				},
				dataType : 'json',
				url : '${base}/admin/customer/isLine.html',
				success : function(data) {
					var content = "";
					for (var i = 0; i < data.length; i++) {
						var dictName = data[i].dictName;
						var id = data[i].id;
						content += "<option value='" + id
								+ "' onclick='optCity(this)'>" + dictName
								+ "</option>";
					}
					$("#sLine1ID").html(content);
					$("#sLine1ID").css("display", "block");
				}
			});
		}
	
		//国际线路
		function goLine2() {
			$.ajax({
				type : 'POST',
				data : {
					line : $("#line2ID").val()
				},
				dataType : 'json',
				url : '${base}/admin/customer/isLine.html',
				success : function(data) {
					var content = "";
					for (var i = 0; i < data.length; i++) {
						var dictName = data[i].dictName;
						var id = data[i].id;
						content += "<option value='" + id
								+ "' onclick='optCity(this)'>" + dictName
								+ "</option>";
					}
					$("#sLine2ID").html(content);
					$("#sLine2ID").css("display", "block");
				}
			});
		}
	
		//发票项
		function goToInvioce() {
			$.ajax({
				type : 'POST',
				data : {
					invioce : $("#inInvioceID").val()
				},
				dataType : 'json',
				url : '${base}/admin/customer/isInvioce.html',
				success : function(data) {
					var content = "";
					for (var i = 0; i < data.length; i++) {
						var dictName = data[i].dictName;
						var id = data[i].id;
						content += "<option value='" + id
								+ "' onclick='optInvioce(this)'>" + dictName
								+ "</option>";
					}
					$("#sInvID").html(content);
					$("#sInvID").css("display", "block");
				}
			});
		}
	
		//结算方式 add input
		function select_change(obj) {
	
			var seleValue = $(".sele").find("option:selected").attr("value");
			if (seleValue == 4) {
				$('.inpAdd').append('<input type="text" class="inp form-control input-sm" placeholder="请输入结算方式">');
			} else {
				$('.inp').remove();
			}
	
		}
	
		//付款方式 add input
		function paySelect_change(obj) {
	
			var payValue = $(".paySele").find("option:selected").attr("value");
			if (payValue == 5) {
				$('.payInp').append('<input type="text" class="paytext form-control input-sm" placeholder="请输入付款方式">');
			} else {
				$('.paytext').remove();
			}
	
		}

	</script>
	
	<!-- 保存页面 -->
	<script type="text/javascript">
		function save() {
			$('#customerAddForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#customerAddForm").data(
					'bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				$.ajax({
					type : 'POST',
					data : $("#customerAddForm").serialize(),
					url : '${base}/admin/customer/add.html',
					success : function(data) {
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						if ("200" == data.status) {
							layer.msg("添加成功", "", 3000);
						} else {
							layer.msg("添加失败！", "", 3000);
						}
						layer.close(index);
						parent.location.reload();
	
					},
					error : function(xhr) {
						layer.msg("添加失败", "", 3000);
					}
				});
			}
		}
		//提交时开始验证
		$('#submit').click(function() {
			$('#customerAddForm').bootstrapValidator('validate');
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
	
		//返回刷新页面 
		function reload() { window.location.reload(); }
	</script>

	<!-- 文件上传 -->
	<script type="text/javascript">
		function fileupload() {
			if ($("#fileID").val() == "") {
				alert("上传文件不能为空!");
				return false;
			}
			$.ajaxFileUpload({
				url : "${base}/admin/customer/upload.html",
				secureuri : false,
				fileElementId : 'fileID',
				dataType : 'text/xml',
				success : function(data) {
					alert("data");
				},
				error : function(data, status, e) {
					alert("fail");
				}
			});
		}
	</script>

</body>
</html>