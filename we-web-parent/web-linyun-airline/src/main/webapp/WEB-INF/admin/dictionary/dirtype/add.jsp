<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<link rel="stylesheet"
	href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>
</head>
<body onresize=hero();>
	<div class="modal-top">
		<form id="addForm">
			<div class="modal-header boderButt">
				<button type="button" class="btn btn-primary right btn-sm"
					data-dismiss="modal">返回</button>
				<button type="button" id="submit"
					class="btn btn-primary right btn-sm">保存</button>
				<h4>添加</h4>
			</div>
			<div class="modal-body">
				<div class="tab-content">
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">字典类型编码：</label>
						<div class="col-sm-8 padding">
							<input name="typeCode" class="form-control input-sm inpImpWid"
								placeholder="请输入字典类型编码" /> <span class="prompt">*</span>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">字典类别名称：</label>
						<div class="col-sm-8 padding">
							<input name="typeName" class="form-control input-sm inpImpWid"
								placeholder="请输入字典类别名称" /> <span class="prompt">*</span>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">状态：</label>
						<div class="col-sm-8 padding">
							<select name="status" class="form-control input-sm inpImpWid">
								<option value="1" selected="selected">启用</option>
								<option value="2">删除</option>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-3 text-right padding">描述：</label>
						<div class="col-sm-8 padding">
							<textarea name="description" class="form-control inpImpWid"></textarea>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	//验证
	$(document).ready(function(){
		$('#addForm').bootstrapValidator({
			message: '验证不通过!',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	typeCode: {
	                validators: {
	                    notEmpty: {
	                        message: '字典代码类别编码不能为空!'
	                    },
		                regexp: {
	                        regexp: /^[A-Za-z0-9]+$/,
	                        message: '字典代码只能为字母或数字'
	                    }
	                }
	            },
	            typeName: {
	            	validators: {
	                    notEmpty: {
	                        message: '字典类别名称不能为空!'
	                    }
	                }
	            }
	        }
		});
	});
		//添加成功提示
		$("#submit").click(function() {
			$('#addForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#addForm").data('bootstrapValidator');
			if(bootstrapValidator.isValid()){
				$.ajax({
					cache : true,
					type : "POST",
					url : '${base}/admin/dictionary/dirtype/add.html',
					data : $('#addForm').serialize(),// 你的formid
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
			}
			//添加完成页面自动关闭
			$(".Mymodal-lg").modal('hide');
		});
		//提交时开始验证
		$('#submit').click(function() {
	        $('#addForm').bootstrapValidator('validate');
	    });
	</script>
</body>
</html>