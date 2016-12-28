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
	<link rel="stylesheet" href="${base }/public/plugins/select2/select2.css">
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
		.form-control-feedback {position: absolute;top: -2px;right: -25px;}
		html, body {min-height: 0;min-width: 0;overflow-x: auto !important;}
		.modal-body{height: 515px; overflow-y: auto;}
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
                            <label class="col-sm-3 text-right padding">出发城市：</label>
                            <div class="col-sm-3 padding">
	                            <select id="leavecity" name="leavecity" type="text" class="form-control input-sm select2" multiple="multiple" placeholder="" >
	                            </select>
                            </div>
                          
                            <label class="col-sm-2 text-right padding">返回城市：</label>
                            <div class="col-sm-3 padding">
                            	<select id="backcity" name="backcity" type="text" class="form-control input-sm select2" multiple="multiple" placeholder="" >
	                            </select>
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-3 text-right padding">航班号：</label>
                            <div class="col-sm-3 padding">
                              <input name="airlinenum" type="tel" class="form-control input-sm inpImportant" placeholder="请输入航班号" /><span class="prompt">*</span>
                            </div>
                            
                            <label class="col-sm-2 text-right padding">起飞时间：</label>
                            <div class="col-sm-3 padding">
                              <input name="leavetime" type="tel" class="form-control input-sm" placeholder="请输入起飞时间" />
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-3 text-right padding">到达时间：</label>
                            <div class="col-sm-3 padding">
                              <input name="backtime" type="tel" class="form-control input-sm inpImportant" placeholder="请输入到达时间" />
                            </div>
                          
                            <label class="col-sm-2 text-right padding">班期：</label>
                            <div class="col-sm-3 padding">
                            	<input name="banqi" type="tel" class="form-control input-sm inpImportant" placeholder="请输入班期" />
                              <span class="prompt">*</span>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">机型：</label>
                            <div class="col-sm-3 padding">
                              <input name="airtype" type="tel" class="form-control input-sm inpImportant" placeholder="请输入机型" /><span class="prompt">*</span>
                            </div>
                            
                            <label class="col-sm-2 text-right padding">航空公司：</label>
                            <div class="col-sm-3 padding">
                            	<select id="aircomid" name="aircomid" type="text" class="form-control input-sm select2" multiple="multiple" placeholder="" >
	                            </select>
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
<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		if($("#uploader_00").length>0){
			inituploader("","00",[]);
		}
		formValidator();
	});
	//表单校验
	function formValidator(){
		$('#companyaddForm').bootstrapValidator({
			 message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        }
		});
	}
	// Validate the form manually
    $('#submitButton').click(function() {
        $('#companyaddForm').bootstrapValidator('validate');
    });
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
		window.parent.successCallback('4');
	}
	function submitCompany(){
		$('#companyaddForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#companyaddForm").data('bootstrapValidator');
		if(bootstrapValidator.isValid()){
			layer.load(1);
			$.ajax({ 
				type: 'POST', 
				data: $("#companyaddForm").serialize(), 
				url: '${base}/admin/flightinfo/add.html',
	            success: function (data) { 
	            	layer.closeAll('loading');
	            	layer.msg("添加成功",{time: 2000, icon:1});
	            	$('#companyaddForm')[0].reset();
	            	initAddSelect2();
	            	$("#companyaddForm").data('bootstrapValidator').destroy();
	                $('#companyaddForm').data('bootstrapValidator', null);
	                formValidator();	
	            },
	            error: function (xhr) {
	            	layer.msg("添加失败","",3000);
	            } 
	        });
		}
	}
	initAddSelect2();
	//加载select2
	function initAddSelect2(){
		$("#aircomid").select2({
			ajax : {
				url : "${base}/admin/customneeds/getAirComSelect.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					return {
						aircom : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function (obj) {
						obj.id =  obj.id; // replace pk with your identifier
						obj.text =  obj.dictName; // replace pk with your identifier
						return obj;
					});
					return {
						results : selectdata
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
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false //设置必须存在的选项 才能选中
		});
		$("#leavecity").select2({
			ajax : {
				url : "${base}/admin/customneeds/getCitySelect.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					return {
						cityname : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function (obj) {
						obj.id = obj.dictCode; // replace pk with your identifier
						obj.text = obj.dictCode; // replace pk with your identifier
						return obj;
					});
					return {
						results : selectdata
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
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false //设置必须存在的选项 才能选中
		});
		$("#backcity").select2({
			ajax : {
				url : "${base}/admin/customneeds/getCitySelect.html",
				dataType : 'json',
				delay : 250,
				type : 'post',
				data : function(params) {
					return {
						cityname : params.term, // search term
						page : params.page
					};
				},
				processResults : function(data, params) {
					params.page = params.page || 1;
					var selectdata = $.map(data, function (obj) {
						obj.id = obj.dictCode; // replace pk with your identifier
						obj.text = obj.dictCode; // replace pk with your identifier
						return obj;
					});
					return {
						results : selectdata
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
			maximumSelectionLength : 1, //设置最多可以选择多少项
			tags : false //设置必须存在的选项 才能选中
		});
	}
</script>
</body>
</html>	
