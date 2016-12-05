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
	<link rel="stylesheet" href="${base}/public/css/pikaday.css">
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
                <form id="customNeedsAddForm"> 
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                  <input type="button" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitCustomNeeds()" value="保存"/>
                  <h4>&nbsp;&nbsp;&nbsp;<i class="fa fa-user"></i> 添加客户需求</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">航空公司：</label>
                            <div class="col-sm-3 padding">
                              <input name="airline" type="tel" class="form-control input-sm inpImportant" placeholder="请输入公司名称" /><span class="prompt">*</span>
                            </div>
                            
                            <label class="col-sm-3 text-right padding">旅行社：</label>
                            <div class="col-sm-3 padding">
                              <input name="travel" type="tel" class="form-control input-sm inpImportant" placeholder="请输入旅行社" /><span class="prompt">*</span>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-2 text-right padding">人数：</label>
                            <div class="col-sm-2 padding">
                              <input name="totalcount" type="tel" class="form-control input-sm inpImportant" />
                            </div>
                          
                            <label class="col-sm-2 text-right padding">天数：</label>
                            <div class="col-sm-2 padding">
                              <input name="totalday" type="tel" class="form-control input-sm " />
                            </div>
                            
                            <label class="col-sm-2 text-right padding">联运要求：</label>
                            <div class="col-sm-2 padding">
                              <input name="uniontransport" type="tel" class="form-control input-sm inpImportant" />
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-2 text-right padding">去程日期：</label>
                            <div class="col-sm-2 padding">
                              <input id="leavedateString" name="leavedateString" type="tel" class="form-control input-sm inpImportant"/>
                            </div>
                            
                            <label class="col-sm-2 text-right padding">出发城市：</label>
                            <div class="col-sm-2 padding">
                              <input name="leavecity" type="tel" class="form-control input-sm"/>
                            </div>
                            
                            <label class="col-sm-2 text-right padding">出发航班：</label>
                            <div class="col-sm-2 padding">
                              <input name="leaveflight" type="tel" class="form-control input-sm"/>
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-2 text-right padding">回程日期：</label>
                            <div class="col-sm-2 padding">
                              <input id="backdateString" name="backdateString" type="tel" class="form-control input-sm inpImportant"/>
                            </div>
                          
                            <label class="col-sm-2 text-right padding">返回城市：</label>
                            <div class="col-sm-2 padding">
                              <input name="backcity" type="tel" class="form-control input-sm inpImportant"/>
                            </div>
                            <label class="col-sm-2 text-right padding">回程航班：</label>
                            <div class="col-sm-2 padding">
                              <input name="backflight" type="tel" class="form-control input-sm inpImportant" />
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
	<!--pikaday -->
	<script src="${base}/public/dist/js/pikaday.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//页面校验
		formValidator();
		//选择日期控件
		var picker = new Pikaday({
	        field: document.getElementById('leavedateString'),
	        firstDay: 1,
	        minDate: new Date('2000-01-01'),
	        maxDate: new Date('2120-12-31'),
	        yearRange: [2000,2020]
	    });
	    var picker = new Pikaday({
	        field: document.getElementById('backdateString'),
	        firstDay: 1,
	        minDate: new Date('2000-01-01'),
	        maxDate: new Date('2120-12-31'),
	        yearRange: [2000,2020]
	    });
	});
	// Validate the form manually
    $('#submitButton').click(function() {
        $('#customNeedsAddForm').bootstrapValidator('validate');
    });
	//关闭弹框
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
		parent.location.reload();
	}
	//提交表单数据
	function submitCustomNeeds(){
		$('#customNeedsAddForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#customNeedsAddForm").data('bootstrapValidator');
		if(bootstrapValidator.isValid()){
			$.ajax({ 
				type: 'POST', 
				data: $("#customNeedsAddForm").serialize(), 
				url: '${base}/admin/customneeds/add.html',
	            success: function (data) { 
	            	//alert("添加成功");
	            	layer.msg("添加成功",{time: 2000, icon:1});
	            	$("#customNeedsAddForm")[0].reset();
	            	$("#customNeedsAddForm").data('bootstrapValidator').destroy();
	                $('#customNeedsAddForm').data('bootstrapValidator', null);
	                formValidator();
	            	//location.reload();
	            },
	            error: function (xhr) {
	            	layer.msg("添加失败","",3000);
	            } 
	        });
		}
	}
	//页面校验方法
	function formValidator(){
		$('#customNeedsAddForm').bootstrapValidator({
			 message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	airline: {
	                validators: {
	                    notEmpty: {
	                        message: '航空公司不能为空'
	                    }
	                }
	            },
	            travel: {
	            	validators: {
	                    notEmpty: {
	                        message: '旅行社不能为空'
	                    }
	                }
	            },
	            totalcount: {
	            	validators: {
	            		regexp: {
	                        regexp: /^[0-9]+$/,
	                        message: '人数只能为数字'
	                    }
	                }
	            },
	            totalday: {
	            	validators: {
	            		regexp: {
	                        regexp: /^[0-9]+$/,
	                        message: '天数只能为数字'
	                    }
	                }
	            }
	        }
		});
	}
</script>
	
</body>
</html>	
	
	
	
	
	
	
	
	
	
	
	
