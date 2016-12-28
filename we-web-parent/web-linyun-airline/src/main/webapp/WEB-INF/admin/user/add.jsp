<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>添加</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/user.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>
</head>
<body onresize=hero();>

          	<form id="addaddUserForm" method="post">
              <div class="modal-header">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
               	  <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
                  <button type="submit" class="btn btn-primary right btn-sm" data-dismiss="modal">初始化密码</button>
                  <button type="submit" class="btn right btn-sm" data-dismiss="modal">删除</button>
                  <h4>基本资料</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">用户姓名：</label>
                            <div class="col-sm-3 padding">
                              <input type="text" class="form-control input-sm inputWidth" placeholder="请输入用户姓名" />
                              <span class="prompt">*</span>
                            </div>
                          
                            <label class="col-sm-2 text-right padding">用户名/手机号码：</label>
                            <div class="col-sm-3 padding">
                              <input type="text" class="form-control input-sm inputWidth" placeholder="请输入用户名/手机号码" />
                              <span class="prompt">*</span>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">联系QQ：</label>
                            <div class="col-sm-3 padding">
                              <input type="text" class="form-control input-sm inputWidth" placeholder="请输入联系QQ" />
                            </div>
                          
                            <label class="col-sm-2 text-right padding">座机号码：</label>
                            <div class="col-sm-3 padding">
                              <input type="text" class="form-control input-sm inputWidth" placeholder="请输入座机号码" />
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">电子邮箱：</label>
                            <div class="col-sm-3 padding">
                              <input type="eamil" class="form-control input-sm inputWidth" placeholder="请输入邮箱" />
                            </div>
                          
                           <label class="col-sm-2 text-right padding">所属部门：</label>
                            <div class="col-sm-3 padding">
                              <select class="form-control input-sm inputWidth">
                                <option>请选择</option>
                                <option>市场部</option>
                                <option>后勤部</option>
                              </select>
                              <span class="prompt">*</span>
                            </div>
                            
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">用户职位：</label>
                            <div class="col-sm-3 padding">
                              <select class="form-control input-sm inputWidth">
                                <option>请选择</option>
                                <option>经理</option>
                                <option>职员</option>
                              </select>
                              <span class="prompt">*</span>
                            </div>

                            <label class="col-sm-2 text-right padding">用户是否禁用：</label>
                            <div class="col-sm-3 padding">
                              <select class="form-control input-sm inputWidth">
                                <option>否</option>
                                <option>是</option>
                              </select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">负责区域：</label>
                            <div class="col-sm-3 padding">
                              <input type="text" class="form-control input-sm inputWidth" placeholder=" " />
                              <span class="prompt">*</span>
                            </div>
                        </div>
                      </form>
                
            </div>
        </div>
	<script type="text/javascript">
	//验证输入内容不能为空
	$(document).ready(function(){
		$('#addUserForm').bootstrapValidator({
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
	                        message: '字典类别编码不能为空!'
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                         url: '${base}/admin/user/checkTypeCodeExist.html',//验证地址
	                         message: '字典类别编码已存在，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	typeCode:$('input[name="typeCode"]').val()
	                            };
	                         }
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
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                    url: '${base}/admin/user/checkTypeNameExist.html',//验证地址
	                         message: '字典类别名称重复，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	typeName:$('input[name="typeName"]').val()
	                            };
	                         }
	                     }
	                }
	            }
	        }
		});
	});
	
		//添加成功提示
		$("#submit").click(function() {
			$('#addUserForm').bootstrapValidator('validate');
			var bootstrapValidator = $("#addUserForm").data('bootstrapValidator');
			if(bootstrapValidator.isValid()){
				$.ajax({
					cache : true,
					type : "POST",
					url : '${base}/admin/user/add.html',
					data : $('#addUserForm').serialize(),// 你的formid
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
		    $('#addUserForm').bootstrapValidator('validate');
		});
		//点击返回
		function closewindow(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
</body>
</html>