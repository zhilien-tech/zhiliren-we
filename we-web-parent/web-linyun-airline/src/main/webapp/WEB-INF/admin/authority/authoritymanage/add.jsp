<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>部门职位-添加</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/authoritymanage.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<!-- zTree -->
<link rel="stylesheet" href="${base }/common/js/zTree/css/zTreeStyle/zTreeStyle.css">
</head>
<body class="bodyOne">
	<div class="modal-top">
		<form id="addDeptForm" method="post">
			<div class="modal-header">
				<button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
                <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
				<h4>添加职位部门</h4>
			</div>
			<div class="modal-body">
				<div class="departmentName">
					<!--部门权限 设置-->
					<div class="form-group row">
						<input id="functionIds" name="functionIds" type="hidden" value=""/>
						<label class="col-md-2 text-right padding">部门名称：</label>
						<div class="col-md-6 padding">
							<input id="deptName" name="deptName" type="text" class="form-control input-sm inpImportant"
								placeholder="请输入部门名称"> <span class="prompt">*</span>
						</div>
						<div class="col-md-4 padding">
							<button type="button" class="btn btn-primary btn-sm btnPadding"
								id="addJob">添加职位</button>
						</div>
					</div>
				</div>
				<!--end 部门权限 设置-->
				<div class="jobName none">
					<!--职位权限 设置-->
					<div class="form-group row">
						<label class="col-md-2 text-right padding">职位名称：</label>
						<div class="col-md-6 padding">
							<input id="jobName" name="name" type="text" class="form-control input-sm inpImportant"
								placeholder="请输入职位名称"> <span class="prompt">*</span>
						</div>
						<div class="col-md-4 padding">
							<button type="button" class="btn btn-primary btn-sm btnPadding"
								id="settingsPermis">设置权限</button>
							<button type="button" class="btn btn-primary btn-sm btnPadding"
								id="deleteBtn">删除</button>
						</div>
					</div>
					<div class="check_div checkTwo none">
						<!-- 权限配置 -->
						<div class="col-sm-9 text-right padding">
	                          <ul id="treeDemo" class="ztree"></ul>
	                    </div>
					</div>
				</div>
				<!--end 职位权限 设置-->
			</div>
		</form>
	</div>
<!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- AdminLTE App -->
	<!--zTree -->
	<script src="${base}/common/js/zTree/jquery.ztree.core-3.5.js"></script>
	<script src="${base}/common/js/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script src="${base}/common/js/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	$(function() {
		//部门职位 添加职位
		$('#addJob').click(function() {
			$('.jobName').toggle();
		});
		//部门职位 设置权限
		$('#settingsPermis').click(function() {
			$('.checkTwo').toggle();
		});
		//部门职位 删除
		$("#deleteBtn").click(function() {
			$(".jobName").hide();
		});
	});
</script>
<script type="text/javascript">
//验证
$(document).ready(function(){
	$('#addDeptForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	deptName: {
                validators: {
                    notEmpty: {
                        message: '部门名称不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/authority/authoritymanage/checkDeptNameExist.html',//验证地址
                         message: '部门名称已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	deptName:$('#deptName').val(),
                            	id:$('#id').val()
                            };
                         }
                     }
                }
            },
            name: {
            	validators: {
                    notEmpty: {
                        message: '职位名称不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                    url: '${base}/admin/authority/authoritymanage/checkJobNameExist.html',//验证地址
	                         message: '职位名称重复，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	name:$('input[name="name"]').val(),
	                            };
	                         }
	                   }
                }
            }
        }
	});
});
	//添加
	$("#submit").click(function(){
		setFunc();
		$('#addDeptForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#addDeptForm").data('bootstrapValidator');
		var _deptName = $("input#deptName").val();
		var _jobName = $("input#jobName").val();
		var _functionIds = $("input#functionIds").val() ;
		if(bootstrapValidator.isValid()){
			$.ajax({
	           cache: true,
	           type: "POST",
	           url:'${base}/admin/authority/authoritymanage/add.html',
	           data:{
					deptName:_deptName,
					jobName:_jobName,
					functionIds:_functionIds
			   },
	           error: function(request) {
	              layer.msg('添加失败!');
	           },
	            success: function(data) {
				layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
	              layer.msg('添加成功!',{time: 5000, icon:6});
				  window.location.reload(true);
	           }
	       });
		}
		 $(".Mymodal-lg").modal('hide');
	});
	//提交时开始验证
	$('#submit').click(function() {
	    $('#addDeptForm').bootstrapValidator('validate');
	});
	//点击返回
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
		parent.location.reload();
	}
</script>
<!-- 功能树 -->
<script type="text/javascript">
	var setting = {
			check: {
				enable: true,//显示复选框
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	//初始化功能树
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
	function setFunc(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes =  treeObj.getCheckedNodes(true);
		var funcIds = "" ;
		for(var i = 0 ; i < nodes.length; i ++){
			funcIds += (nodes[i].id + ",") ;
		}
		$("input[name='functionIds']").val(funcIds) ;
	}
	//遍历得到的对象
	var zNodes =[
		 {id:"0", pId:"0", name:"职位权限设置", open:true},
		<c:forEach var="p" items="${obj.list}">
			{ id:"${p.id }", pId:"${p.parentId }", name:"${p.name }", open:true,checked:"${p.checked}"},
		</c:forEach>
	];
</script>
</body>
</html>