<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>编辑</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/user.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<style type="text/css">
.select2-container {
	width: 95.5% !important;
	display: inline-block;
}

.seleSpanWid .select2-container {
	width: 98.5% !important;
	display: inline-block;
}

.inpNone .select2 .selection span ul li+li {
	display: none;
}
/**验证时对勾的位置*/
.form-control-feedback {position: absolute;top: -2px;right: -25px;}
</style>

</head>
<body onresize=hero();>
    <div class="modal-header">
        <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
     	<button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
        <button type="button" onclick="passwordInit('${obj.userInfo.id}');" class="btn btn-primary right btn-sm" data-dismiss="modal">初始化密码</button>
        <button type="button" onclick="physicalDelete('${obj.userInfo.id}');" class="btn right btn-sm" data-dismiss="modal">删除</button>
        <h4>编辑</h4>
    </div>
    	<form id="editUserForm" method="post">
           <div class="modal-body">
              <div class="tab-content">
                  <div class="row">
                  	<div class="form-group">
                  	  <input name="id" type="hidden" value='${obj.userInfo.id}'/>
                      <label class="col-sm-3 text-right padding">用户姓名：</label>
                      <div class="col-sm-3 padding">
                        <input id="userName" name="userName" type="text" class="form-control input-sm inputWidth" value="${obj.userInfo.userName}"/>
                        <span class="prompt">*</span>
                      </div>
                    </div>
                    <div class="form-group form-group1">
                      <label class="col-sm-2 text-right padding">用户名/手机号码：</label>
                      <div class="col-sm-3 padding">
                        <input id="telephone" name="telephone" type="text" class="form-control input-sm inputWidth" value="${obj.userInfo.telephone}"/>
                        <span class="prompt">*</span>
                      </div>
                     </div>
                  </div>

                  <div class="row">
                  	<div class="form-group">
                      <label class="col-sm-3 text-right padding">联系QQ：</label>
                      <div class="col-sm-3 padding">
                        <input id="qq" name="qq" type="text" class="form-control input-sm inputWidth" value="${obj.userInfo.qq}"/>
                      </div>
                    </div>
                    <div class="form-group form-group1">
                      <label class="col-sm-2 text-right padding">座机号码：</label>
                      <div class="col-sm-3 padding">
                        <input id="landline" name="landline" type="text" class="form-control input-sm inputWidth" value="${obj.userInfo.landline}"/>
                      </div>
                     </div>
                  </div>

                  <div class="row">
                  	<div class="form-group">
                      <label class="col-sm-3 text-right padding">电子邮箱：</label>
                      <div class="col-sm-3 padding">
                        <input id="email" name="email" type="eamil" class="form-control input-sm inputWidth" value="${obj.userInfo.email}"/>
                      </div>
                    </div>
                    <div class="form-group form-group1">
                     <label class="col-sm-2 text-right padding">所属部门：</label>
                      <div class="col-sm-3 padding">
                        <select id="deptId" name="deptId" onchange="selectDeptName();" class="form-control input-sm inputWidth">
                         	<c:forEach items="${obj.deptInfo}" var="one">
			                   	<option value='${one.id}' ${one.id==obj.userInfo.deptId?'selected':''}>
			                   		${one.deptName }
			                   	</option>
							</c:forEach>
                        </select>
                        <span class="prompt">*</span>
                      </div>
                     </div>
                  </div>

                  <div class="row">
                  	<div class="form-group">
                      <label class="col-sm-3 text-right padding">用户职位：</label>
                      <div class="col-sm-3 padding">
                         <select id="jobId" name="jobId" class="form-control input-sm inputWidth">
              			 </select>
                        <span class="prompt">*</span>
                      </div>
					</div>
					<div class="form-group form-group1">
                      <label class="col-sm-2 text-right padding">用户是否禁用：</label>
                      <div class="col-sm-3 padding">
                        <select id="disableStatus" name="disableStatus" class="form-control input-sm inputWidth">
                          <option value="0"
							<c:if test="${'0' eq obj.userInfo.disableStatus}">selected</c:if>>否</option>
						  <option value="1"
							<c:if test="${'1' eq obj.userInfo.disableStatus}">selected</c:if>>是</option>
                        </select>
                      </div>
                     </div>
                  </div>
                  <div class="row">
                  	<div class="form-group">
                      <label class="col-sm-3 text-right padding">负责区域：</label>
                      <div class="col-sm-3 padding">
                         <select id="areaSelect" onchange="setSelectedAreaIds();"
							class="form-control select2 inpImpWid" multiple="multiple"
							data-placeholder="请输入区域名称">
							<option></option>
							<c:forEach var="one" items="${obj.areaInfo }">
								<option value="${one.id }">
									${one.text}
								</option>
							</c:forEach>
						 </select>
						 <!-- 已选中的区域ID -->
						 <input id="selectedAreaIds" type="hidden" name="selectedAreaIds"/>
                         <span class="prompt">*</span>
                      </div>
                     </div>
                  </div>
                  <!-- 设置已选中的项 -->
					<script type="text/javascript">
						function setSelectedAreaIds() {
							var _selectedAreaIds = $("#areaSelect").select2("val");
							$("#selectedAreaIds").val(_selectedAreaIds);
						}
					</script>
            </div>
        </div>
      </form>
<script type="text/javascript">
	var BASE_PATH = '${base}';
</script>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<!-- Select2 -->
<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
<!-- 页面select2下拉框js -->
<script src="${base}/common/js/layer/layer.js"></script>

<script type="text/javascript">
selectDeptName();
//验证输入内容不能为空
$(document).ready(function(){
	formValidator();
	var _areaSelect = $("#areaSelect").select2({
		ajax : {
			url : BASE_PATH  + "/admin/user/areaSelect2.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					area : params.term, // search term
					selectedAreaIds:$("#selectedAreaIds").val(),
					page : params.page
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
		tags : false, //设置必须存在的选项 才能选中
	});
	_areaSelect.val([${obj.areaIds}]).trigger("change");
});
//校验
function formValidator(){
	$('#editUserForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	userName: {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空!'
                    },
                    remote: {  
                         url: '${base}/admin/user/checkUserNameExist.html',//验证地址
                         message: '用户名称已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	userName:$('input[name="userName"]').val(),
                            	id:'${obj.userInfo.id}'
                            };
                         }
                     },
                     stringLength: {/*长度提示*/
                   	    min: 1,
                   	    max: 6,
                   	    message: '用户名长度不得超出6个汉字!'
                   	  }
                }
            },
            telephone: {
            	validators: {
                    notEmpty: {
                        message: '电话号码不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                    url: '${base}/admin/user/checkTelephoneExist.html',//验证地址
                         message: '电话号码重复，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	telephone:$('input[name="telephone"]').val(),
                            	id:'${obj.userInfo.id}'
                            };
                         }
                     },
                     regexp: {
                    	 	regexp: /^[1][34578][0-9]{9}$/,
	                        message: '电话号码号格式错误!'
	                    }
                }
            },
            qq: {
            	validators: {
            		regexp: {
                        regexp: /^[0-9]*$/,
                        message: 'qq只能输入数字!'
                    }
                }
            },
            email: {
            	validators: {
            		regexp: {
                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                        message: '邮箱格式错误!'
                    }
                }
            },
            deptId: {
            	validators: {
                    notEmpty: {
                        message: '部门不能为空!'
                    }
                }
            },
            jobId: {
            	validators: {
                    notEmpty: {
                        message: '职位不能为空!'
                    }
                }
            },
            dictAreaName: {
            	validators: {
                    notEmpty: {
                        message: '区域不能为空!'
                    }
                }
            }
        }
	});
}
//部门职位联动查询
function selectDeptName(){
	$.ajax({
		cache : true,
		type : "POST",
		url : '${base}/admin/user/selectDeptName.html',
		data : {
			deptId:$('#deptId').val()
		},
		success : function(data) {
			var str = '';
			for(var i=0;i< data.length;i++){
				str += '<option value="'+data[i].id+'">'+data[i].name+'</option>';
			}
			document.getElementById("jobId").innerHTML=str;
		},
		error : function(request) {
			
		}
	});
}
//修改成功提示
$("#submit").click(function() {
	$('#editUserForm').bootstrapValidator('validate');
	var bootstrapValidator = $("#editUserForm").data('bootstrapValidator');
	if(bootstrapValidator.isValid()){
		$.ajax({
			cache : true,
			type : "POST",
			url : '${base}/admin/user/update.html',
			data : $('#editUserForm').serialize(),//form表单数据
			error : function(request) {
				layer.msg('修改失败!');
			},
			success : function(data) {
				layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
              	layer.msg('修改成功!',{time: 5000, icon:6});
				formValidator();
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			    parent.layer.close(index);
			    window.parent.successCallback('2');
			}
		});
	}
});
//密码初始化
function passwordInit(userId){
	layer.confirm("您确认初始化密码吗？", {
	    btn: ["是","否"], //按钮
	    shade: false //不显示遮罩
	}, function(){
		// 点击确定之后
		var url = '${base}/admin/user/passwordInit.html';
		$.ajax({
			cache : true,
			type : "POST",
			url : url,
			data : $('#editUserForm').serialize(),
			success : function(data) {
				layer.load(1, {
					 shade: [0.1,'#fff'] //0.1透明度的白色背景
				});
	            layer.msg('初始化成功!',{time: 5000, icon:6});
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			    parent.layer.close(index);
			    window.parent.successCallback('4');
			},
			error : function(request) {
				layer.msg('初始化失败!');
			}
		});
	}, function(){
	    // 取消之后不用处理
	});
}
//删除提示
function physicalDelete(userId) {
	layer.confirm("您确认删除信息吗？", {
	    btn: ["是","否"], //按钮
	    shade: false //不显示遮罩
	}, function(){
		// 点击确定之后
		var url = '${base}/admin/user/delete.html';
		$.ajax({
			type : 'POST',
			data : {
				userId : userId
			},
			dataType : 'json',
			url : url,
			success : function(data) {
				if ("200" == data.status) {
					layer.msg("删除成功!", "", 3000);
					 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				     parent.layer.close(index);
				     window.parent.successCallback('3');
				} else {
					layer.msg("删除失败!", "", 3000);
				}
			},
			error : function(xhr) {
				layer.msg("操作失败", "", 3000);
			}
		});
	}, function(){
	    // 取消之后不用处理
	});
}
//提交时开始验证
$('#submit').click(function() {
    $('#editUserForm').bootstrapValidator('validate');
});
//点击返回
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>