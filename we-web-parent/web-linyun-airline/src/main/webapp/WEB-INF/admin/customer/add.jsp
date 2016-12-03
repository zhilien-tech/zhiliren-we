<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1">
<link rel="stylesheet" href="${base}/public/css/pikaday.css">
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base }/public/dist/css/bootstrapValidator.css" />
<style type="text/css">
.select2-container {
	width: 95.5% !important;
	display: inline-block;
}

.seleSpanWid .select2-container {
	width: 98.5% !important;
	display: inline-block;
}
</style>
</head>

<body>

	<div class="modal-content">
		<form id="customerAddForm">
			<div class="modal-header">
				<button id="backBtn" type="button" onclick="reload()"
					class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
				<input type="button" id="addBtn" class="btn btn-primary right btn-sm" value="保存" onclick="save()" />
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
						<input name="comId" type="hidden" value="" />
						<!--基本信息-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">公司名称：</label>
							<div class="col-sm-8 padding seleSpanWid">
								<select id="companyId" onchange="editInput()" class="form-control select2 inpImpWid" multiple="multiple"  data-placeholder="请输入公司名称">
									
								</select><span class="prompt">*</span>
								<!-- 公司ID -->
								<input id="agentId" type="hidden" name="agentId" />
								<!-- 公司名称 -->
								<input id="comName" type="hidden" name="name" />
							</div>
						</div>
						<!-- 选中公司 隐藏或显示光标 -->
						<script type="text/javascript">
							function editInput(){
								//var o = $("#companyId").html();
								
								
							}
						</script>
						
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">公司简称：</label>
							<div class="col-sm-3 padding">
								<input name="shortName" type="text"
									class="form-control input-sm inpImportant"
									placeholder="请输入公司简称" />
							</div>

							<label class="col-sm-2 text-right padding">负责人：</label>
							<div class="col-sm-3 padding">
								<!-- 负责人下拉列表 -->
								<select id="agent" name="agent"
									class="form-control input-sm inpImportant">
									<c:forEach var="one" items="${obj.userlist }">
										<option value="${one.id }">${one.userName}</option>
									</c:forEach>
								</select><span class="prompt">*</span>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">联系人：</label>
							<div class="col-sm-3 padding">
								<input name="linkMan" type="text"
									class="form-control input-sm inpImportant" placeholder="请输入联系人" /><span
									class="prompt">*</span>
							</div>

							<label class="col-sm-2 text-right padding">联系电话：</label>
							<div class="col-sm-3 padding">
								<input name="telephone" type="text"
									class="form-control input-sm inpImportant"
									placeholder="请输入联系电话" /><span class="prompt">*</span>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">网址：</label>
							<div class="col-sm-3 padding">
								<input name="siteUrl" type="text"
									class="form-control input-sm inpImportant" placeholder="请输入网址" />
							</div>

							<label class="col-sm-2 text-right padding">传真：</label>
							<div class="col-sm-3 padding">
								<input name="fax" type="text"
									class="form-control input-sm inpImportant" placeholder="请输入传真" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">地址：</label>
							<div class="col-sm-8 padding">
								<input name="address" type="text"
									class="form-control input-sm inpImpWid" placeholder="请输入详细地址" /><span
									class="prompt">*</span>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">旅行社类型：</label>
							<div class="col-sm-3 padding">
								<select id="travelTypeID" name="travelType"
									class="form-control input-sm inpImportant">
									<option value="1" selected="selected">出境社</option>
									<option value="2">国内社</option>
									<option value="3">综合</option>
								</select>
							</div>

							<label class="col-sm-2 text-right padding">是否禁用：</label>
							<div class="col-sm-3 padding">
								<select id="forbidID" name="forbid"
									class="form-control input-sm inpImportant">
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</select>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-3 text-right padding">出发城市：</label>
							<div class="col-sm-8 padding seleSpanWid">
								<select id="city" class="form-control select2 inpImpWid"
									multiple="multiple" data-placeholder="请输入出发城市">
								</select><span class="prompt">*</span>
								<!-- 出发城市ID -->
								<input id="outcity" type="hidden" name="outcityname" />
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_2">
						<!--路线权限-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">国境内陆：</label>
							<div class="col-sm-3 padding">
								<select id="isLine" class="form-control select2 inpImportant"
									multiple="multiple" data-placeholder="请输入国境内陆">
								</select><span class="prompt">*</span>
								<!-- 国境内陆ID -->
								<input id="sLine1ID" type="hidden" name="sLine1" />
							</div>

							<label class="col-sm-2 text-right padding">国际：</label>
							<div class="col-sm-3 padding">
								<select id="sLine2ID" class="form-control select2 inpImportant"
									multiple="multiple" data-placeholder="请输入国际线路">
								</select><span class="prompt">*</span>
								<!-- 国际线路ID -->
								<input id="line2ID" type="hidden" name="internationLine" />
							</div>
						</div>

					</div>
					<div class="tab-pane" id="tabs_3">
						<!--附件管理-->
						<div class="form-group row">
							<label class="col-sm-3 text-right padding">附件列表：</label>
							<div class="col-sm-3 padding">
								<input type="file" name="fileID" id="uploadify" /> <input
									type="hidden" name="appendix" id="appendix" />
								<p class="flie_A">
									上传<input type="button" onclick="fileupload();" />
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
								<input id="datepicker1" name="contractTimeString" type="text"
									class="form-control input-sm input-wid"
									placeholder="2015-08-08" /> 
								至 <input id="datepicker2"
									name="contractDueTimeString" type="text"
									class="form-control input-sm input-wid"
									placeholder="2088-09-09" />
							</div>
						</div>

						<div class="form-group row">
							<label class="col-sm-2 text-right padding">付款方式：</label>
							<div class="col-sm-2 padding">

								<select id="payWayID" name="payWay"
									class="form-control input-sm paySele"
									onchange="paywaySelect_change(this)">
									<option value="1" selected="selected">现金</option>
									<option value="2">支票</option>
									<option value="3">银行汇款</option>
									<option value="4">第三方</option>
									<option value="5">其他</option>
								</select>
							</div>

							<div class="col-sm-8" style="display: none;" id="paywayDivId">
								<div class="col-sm-12 padding payInp">
									<input type="text" id="paywayId" name="paywayName"
										class="paytext form-control input-sm" placeholder="请输入付款方式">
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-2 text-right padding">结算方式：</label>
							<div class="col-sm-2 padding">
								<select id="payTypeID" name="payType"
									class="form-control input-sm sele"
									onchange="paytypeSelect_change(this)">

									<option value="1" selected="selected">月结</option>
									<option value="2">周结</option>
									<option value="3">单结</option>
									<option value="4">其他</option>
								</select>
							</div>
							<div class="col-sm-8" style="display: none;" id="paytypeDivId">
								<div class="col-sm-12 padding inpAdd">
									<input type="text" name="paytypeName"
										class="paytext form-control input-sm" placeholder="请输入结算方式">
								</div>
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
							<!-- 发票项  -->
							<div class="col-sm-8" style="display: none;" id="invioceType">
								<div class="col-sm-12 padding">
									<select id="sInvID" class="form-control select2"
										multiple="multiple" data-placeholder="请输入发票项">
									</select>
									<!-- 发票项ID -->
									<input id="sInvName" type="hidden" name="sInvName" />
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
	</div>
	</form>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
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

	<link href="${base }/public/plugins/uploadify/uploadify.css"
		rel="stylesheet" type="text/css" />
	<script type="text/javascript"
		src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	<!-- 页面js -->
	<script src="${base}/admin/customer/baseinfo.js"></script>
	<script src="${base}/admin/customer/line.js"></script>
	<script src="${base}/admin/customer/upload.js"></script>
	<script src="${base}/admin/customer/caiwu.js"></script>
	<script type="text/javascript">
		var base = "${base}";
		$(function() {

			$.fileupload1 = $('#uploadify').uploadify({
				'auto' : true,
				'formData' : {
					'fcharset' : 'uft-8',
					'action' : 'uploadimage'
				},
				'buttonText' : '选择上传文件',
				'fileSizeLimit' : '3000MB',
				'fileTypeDesc' : '文件',
				'fileTypeExts' : '*.png; *.txt',//文件类型过滤
				'swf' : '${base}/public/plugins/uploadify/uploadify.swf',
				'multi' : false,
				'successTimeout' : 1800,
				'queueSizeLimit' : 100,
				'uploader' : '${base}/admin/customer/uploadFile.html',
				//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
				//下面的例子演示如何获取到vid
				'onUploadSuccess' : function(file, data, response) {
					var jsonobj = eval('(' + data + ')');
					$('#appendix').val(data);
					alert(data);
				}
			});

			//页面加载时 执行
			angentList();

			//公司名称文本框
			var comInput = $("input[placeholder=请输入公司名称]");
			
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
							notEmpty : {
								message : '公司简称不能为空'
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

		});
		/* 页面初始化加载完毕 */
	</script>

	<!-- 显示隐藏问题 -->
	<script type="text/javascript">
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

		//结算方式 add input
		function paytypeSelect_change(obj) {

			var seleValue = $(".sele").find("option:selected").attr("value");
			if (seleValue == 4) {
				document.getElementById("paytypeDivId").style.display = "block";
			} else {
				document.getElementById("paytypeDivId").style.display = "none";
			}

		}

		//付款方式 add input
		function paywaySelect_change(obj) {

			var payValue = $(".paySele").find("option:selected").attr("value");
			if (payValue == 5) {
				document.getElementById("paywayDivId").style.display = "block";
			} else {
				document.getElementById("paywayDivId").style.display = "none";
			}

		}
	</script>

	<!-- 保存页面 -->
	<script type="text/javascript">
		function save(){
			//出发城市ID
			var selectedCityId = $("#city").select2("val");
			$("#outcity").val(selectedCityId);

			//代理商公司ID
			var selectedcompanyId = $("#companyId").select2("val");
			$("#agentId").val(selectedcompanyId);
			//公司名称
			var selectedcompanyName = $('#companyId').find("option:selected").text();
			$("#comName").val(selectedcompanyName);
			//国境线路ID
			var selectedisLine = $("#isLine").select2("val");
			$("#sLine1ID").val(selectedisLine);
			//国际线路ID
			var selectedsLine2ID = $("#sLine2ID").select2("val");
			$("#line2ID").val(selectedsLine2ID);
			//发票项ID
			var selectedsInvID = $("#sInvID").select2("val");
			$("#sInvName").val(selectedsInvID);

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
							layer.msg("添加失败!", "", 3000);
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
		$('#addBtn').click(function() {
			$('#customerAddForm').bootstrapValidator('validate');
		});

		//显示或隐藏发票项
		function gaveInvioce() {
			var s = document.getElementById("invoiceID").value;
			if (s == 1) {
				document.getElementById("invioceType").style.display = "";
			}
			if (s == 0) {
				document.getElementById("invioceType").style.display = "none";
			}
		}

		//返回刷新页面 
		function reload() {
			window.location.reload();
		}
	</script>


</body>
</html>