<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta http-equiv="Access-Control-Allow-Origin" content="*" />
<meta name="alexaVerifyID" content="" />
<title>添加</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/ionicons.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css" />
<!-- style -->
<link rel="stylesheet" href="${base}/public/css/style.css">
<style type="text/css">
.wu-example .statusBar .btns .uploadBtn {
	background: #3c8dbc !important;
	color: #fff;
	border-color: transparent;
	position: relative;
	top: -122px;
	height: 40px;
	border-radius: 5px;
}
</style>
</head>
<body onresize=hero();>
	<div class="modal-top">
		<form id="functionAddForm" method="post">
			<div class="modal-header boderButt">
				<button type="button" class="btn btn-primary right btn-sm"
					onclick="closewindow();">返回</button>
				<input type="button" id="submitButton"
					class="btn btn-primary right btn-sm" onclick="submitFunction()"	value="保存" />
				<h4>功能添加</h4>
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">上级功能：</label>
						<div class="col-sm-8 padding">
							<select name="parentId" class="form-control input-sm inpImpWid">
								<option value=" ">--请选择--</option>
								<c:forEach var="pro" items="${obj}">
									<option value="${pro.id}">${pro.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">访问地止：</label>
						<div class="col-sm-8 padding">
							<input name="url" type="text"
								class="form-control input-sm inpImpWid" placeholder="请输入访问地止" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">功能名称：</label>
						<div class="col-sm-8 padding">
							<input name="name" type="text"
								class="form-control input-sm inpImpWid" placeholder="请输入功能名称" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">功能等级：</label>
						<div class="col-sm-8 padding">
							<input name="level" type="text"
								class="form-control input-sm inpImpWid" placeholder="请输入功能等级" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">序号：</label>
						<div class="col-sm-8 padding">
							<input name="sort" type="text" class="form-control inpImpWid"
								placeholder="请输入序号" />
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">备注：</label>
						<div class="col-sm-8 padding ">
							<textarea name="remark" class="form-control inpImpWid"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<jsp:include page="/WEB-INF/common/webupload_resource.jsp"></jsp:include>
	<script type="text/javascript">
		// Validate the form manually
		$('#submitButton').click(function() {
			$('#functionAddForm').bootstrapValidator('validate');
		});
		function closewindow() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
			parent.location.reload();
		}
		function submitFunction() {
			$('#functionAddForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#functionAddForm").data('bootstrapValidator');
			if (bootstrapValidator.isValid()) {
				$.ajax({
					type : 'POST',
					data : $("#functionAddForm").serialize(),
					url : '${base}/admin/authority/function/add.html',
					success : function(data) {
						layer.msg("添加成功", "", 3000);
						var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
						parent.layer.close(index);
						window.parent.successCallback('1');
					},
					error : function(xhr) {
						layer.msg("添加失败", "", 3000);
					}
				});
			}
		}
	</script>
</body>
</html>