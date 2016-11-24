<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
	<meta name="alexaVerifyID" content="" />
    <title>添加</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base }/public/dist/css/font-awesome.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/ionicons.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
	<!-- style -->
  <link rel="stylesheet" href="${base }/public/css/style.css">
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
                <form id="companyaddForm"> 
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                  <input type="button" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitCompany()" value="保存"/>
                  <h4>&nbsp;&nbsp;&nbsp;<i class="fa fa-user"></i> 基本资料</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">公司名称：</label>
                            <div class="col-sm-8 padding">
                              <input name="comName" type="tel" class="form-control input-sm inpImpWid" placeholder="请输入公司名称" /><span class="prompt">*</span>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">用户名：</label>
                            <div class="col-sm-3 padding">
                              <input name="telephone" type="tel" class="form-control input-sm inpImportant" placeholder="请输入用户名" /><span class="prompt">*</span>
                            </div>
                          
                            <label class="col-sm-2 text-right padding">联系人：</label>
                            <div class="col-sm-3 padding">
                              <input name="connect" type="tel" class="form-control input-sm inpImportant" placeholder="请输入联系人姓名" /><span class="prompt">*</span>
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-3 text-right padding">联系电话：</label>
                            <div class="col-sm-3 padding">
                              <input name="mobile" type="tel" class="form-control input-sm inpImportant" placeholder="请输入联系人手机号" /><span class="prompt">*</span>
                            </div>
                            
                            <label class="col-sm-2 text-right padding">联系邮箱：</label>
                            <div class="col-sm-3 padding">
                              <input name="email" type="tel" class="form-control input-sm" placeholder="请输入联系邮箱" />
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-3 text-right padding">座机电话：</label>
                            <div class="col-sm-3 padding">
                              <input name="phonenumber" type="tel" class="form-control input-sm inpImportant" placeholder="请输入公司座机号" />
                            </div>
                          
                            <label class="col-sm-2 text-right padding">公司类型：</label>
                            <div class="col-sm-3 padding">
                              <select class="form-control input-sm inpImportant" name="comType">
                                <option value="">==请选择==</option>
                              	<c:forEach var="map" items="${obj.companyTypeEnum}" >
							   		<option value="${map.key}">${map.value}</option>
								</c:forEach>
                              </select><span class="prompt">*</span>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">地址：</label>
                            <div class="col-sm-8 padding">
                              <input name="address" type="tel" class="form-control input-sm inpImpWid" placeholder="请输入详细地址" /><span class="prompt">*</span>
                            </div>
                        </div>


						 <div class="form-group row">
                            <label class="col-sm-3 text-right padding">上传营业执照：</label>
                            <div class="col-sm-8 padding">
                              <input type="hidden" id="webupload_picture" name="license" value=""/>
								        <div class="wu-example" id="uploader_00" style="width:300px;height:200px;">
								        	<div id="imgUrlMessage" name="imgUrlMessage"></div> 
								        </div>
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
	$(document).ready(function(){
		if($("#uploader_00").length>0){
			inituploader("","00",[]);
		}
		
		$('#companyaddForm').bootstrapValidator({
			 message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	comName: {
	                validators: {
	                    notEmpty: {
	                        message: '公司名称不能为空'
	                    }
	                }
	            },
	            telephone: {
	            	validators: {
	                    notEmpty: {
	                        message: '用户名不能为空'
	                    },
		                regexp: {
	                        regexp: /^[A-Za-z0-9]+$/,
	                        message: '用户名只能为字母或数字'
	                    }
	                }
	            },
	            connect: {
	            	validators: {
	                    notEmpty: {
	                        message: '联系人不能为空'
	                    }
	                }
	            },
	            mobile: {
	            	validators: {
	            		notEmpty: {
	                        message: '联系人手机号不能为空'
	                    },
	            		regexp: {
	                        regexp: /^[1][34578][0-9]{9}$/,
	                        message: '联系人手机号格式错误'
	                    }
	                }
	            },
	            email: {
	            	validators: {
	            		regexp: {
	                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	                        message: '邮箱格式错误'
	                    }
	                }
	            },
	            phonenumber: {
	            	validators: {
	            		regexp: {
	                        regexp: /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/,
	                        message: '座机格式:区号-座机号'
	                    }
	                }
	            },
	            comType: {
	            	validators: {
	            		notEmpty: {
	                        message: '请选择公司类型'
	                    }
	                }
	            },
	            address: {
	            	validators: {
	            		notEmpty: {
	                        message: '公司地址不能为空'
	                    }
	                }
	            }
	        }
		});
	});
	// Validate the form manually
    $('#submitButton').click(function() {
        $('#companyaddForm').bootstrapValidator('validate');
    });
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
		parent.location.reload();
	}
	function submitCompany(){
		$('#companyaddForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#companyaddForm").data('bootstrapValidator');
		if(bootstrapValidator.isValid()){
			$.ajax({ 
				type: 'POST', 
				data: $("#companyaddForm").serialize(), 
				url: '${base}/admin/Company/add.html',
	            success: function (data) { 
	            	alert("添加成功");
	            	location.reload();
	            	$('#companyaddForm')[0].reset();
	            	if($("#uploader_00").length>0){
	        			inituploader("","00",[]);
	        		}
	            },
	            error: function (xhr) {
	            	layer.msg("添加失败","",3000);
	            } 
	        });
		}
	}
</script>
	
</body>
</html>	
	
	
	
	
	
	
	
	
	
	
	
