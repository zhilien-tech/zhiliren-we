<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>编辑银行卡</title>
<link rel="stylesheet"
	href="${base }/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base }/public/dist/css/bankcardManage.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<%-- <link rel="stylesheet" href="${base}/public/dist/css/user.css"> --%>
<link rel="stylesheet"
	href="${base }/public/dist/css/bootstrapValidator.css" />
<link rel="stylesheet" href="${base}/public/dist/css/bankcardManage.css"><!-- 本页面style -->
</head>
<body>
	<div class="modal-top">
		<form id="editBankCard" method="post">
			<div class="modal-header boderButt">
				<button type="button" class="btn btn-primary right btn-sm"
					onclick="closewindow();">取消</button>
				<input type="submit" id="submit"
					class="btn btn-primary right btn-sm" value="保存"
					onclick="saveSubmit('${obj.bankCardInfo.id}');" />
				<button type="button" class="btn right btn-sm"
					" onclick="physicalDelete('${obj.bankCardInfo.id}');">删除</button>
				<%-- 	            <button type="button" class="btn right btn-sm" onclick="physicalDelete('${obj.bankCardInfo.id}');">删除</button> --%>
				<h4>编辑银行卡</h4>
			</div>
			<div class="modal-body" style="height: 252px; overflow-y: auto;">
				<div class="tab-content backcard">
					<div class="row">
						<!--银行卡名称/卡号/类型-->
						<div class="form-group">
							<label class="col-sm-2 text-right padding">银行卡名称：</label>
							<div class="col-sm-2 padding">
								<input name="cardName" type="text" class="form-control input-sm"
									value="${obj.bankCardInfo.cardName }" />
								<input type="hidden" name="id" value="${obj.bankCardInfo.id}">
							</div>
						</div>
						<div class="form-group form-group1">
							<label class="col-sm-1 text-right padding">卡号：</label>
							<div class="col-sm-2 padding">
								<input name="cardNum" type="text" class="form-control input-sm"
									value="${obj.bankCardInfo.cardNum }" />
							</div>
						</div>
						<div class="form-group form-group1">
						
							<label class="col-sm-1 text-right padding">类型：</label>
							<div class="col-sm-2 padding">
								<select class="form-control input-sm" name="bankCardType"
									id="findBankCardType">
									<c:forEach items="${obj.bankCardTypeList }" var="each">
		               					<option value="${each.dictName }" <c:if test="${each.dictName eq obj.bankCardInfo.bankCardType}">selected</c:if>>${each.dictName }</option>
		               				</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<!--end 银行卡名称/卡号/类型-->

					<div class="row">
						<!--银行/币种-->
						<div class="form-group">
						
							<label class="col-sm-2 text-right padding">银行：</label>
							<div class="col-sm-2 padding">
								<select id="findBank" class="form-control input-sm"
									name="bankName">
									<c:forEach items="${obj.bankList }" var="each">
		               					<option value="${each.dictName }" <c:if test="${each.dictName eq obj.bankCardInfo.bankName}">selected</c:if>>
		               					${each.dictName }</option>
		               				</c:forEach>
	
								</select>
							</div>
						</div>
						<div class="form-group form-group1">
						
							<label class="col-sm-1 text-right padding">币种：</label>
							<div class="col-sm-2 padding">
								<select class="form-control input-sm" name="currency"
									id="findCurrency">
									 <c:forEach items="${obj.moneyTypeList }" var="each">
		               					<option value="${each.dictCode }" <c:if test="${each.dictCode eq obj.bankCardInfo.currency}">selected</c:if>>${each.dictCode }</option>
		               				</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<!--end 银行/币种-->
					<div class="form-group row">
						<!--备注-->
						<label class="col-sm-2 text-right padding">备注：</label>
						<div class="col-sm-8 padding">
							<textarea class="form-control input-sm textareaHeight"
								name="remark">${obj.bankCardInfo.remark }</textarea>
						</div>
					</div>
					<!--end 备注-->
				</div>
			</div>
	</div>
	</form>
	<!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<script
		src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- SlimScroll -->
	
	
	
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script>
	<!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<!-- layer -->
	<script type="text/javascript">
	//验证输入内容不能为空
	$(document).ready(function(){
		formValidator();
	});
	/* $( function init(){
		selectBankName();
		selectBankCardType();
		selectCurrency();
		$("#findBankCardType").find("option[text='信用卡']").attr("selected",true);
	}); */
	//点击取消
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	

	
	//表单验证
	function formValidator(){
		var options={
			message: '验证不通过!',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	cardName: {
	                validators: {
	                    notEmpty: {
	                        message: '银行卡名不能为空!'
	                    },
	                     stringLength: {/*长度提示*/
	                   	    min: 1,
	                   	    max: 6,
	                   	    message: '银行卡名长度不得超出6个汉字!'
	                   	  }
	                }
	            },
	            cardNum: {
	            	validators: {
	                    notEmpty: {
	                        message: '银行卡号不能为空!'
	                    },
	                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                         url: '${base}/admin/bankcard/checkBankCardNumExist.html',//验证地址
	                         message: '银行卡号已存在，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	cardNum:$('input[name="cardNum"]').val(),
	                            	id:'${obj.bankCardInfo.id}'
	                            };
	                         }
	                     }
	                }
	            },
	            bankCardType: {
	            	validators: {
	            		notEmpty: {
	                        message: '请选择银行卡类型!'
	                    }
	                }
	            },
	            bankName: {
	            	validators: {
	            		notEmpty: {
	                        message: '请选择银行!'
	                    }
	                }
	            },
	            currency: {
	            	validators: {
	                    notEmpty: {
	                        message: '请选择币种!'
	                    }
	                }
	            }
	        }
		};
		
		$("#editBankCard").bootstrapValidator(options);  
		$("#editBankCard").data('bootstrapValidator').validate();
		return $("#editBankCard").data('bootstrapValidator').isValid();
	}
	$(formValidator());
	//修改保存
	function saveSubmit(id){
		
		var valid = formValidator();
		
		if(valid){
			$.ajax({
				type : "POST",
				url : '${base}/admin/bankcard/update.html',
				data : $('#editBankCard').serialize(),//form表单数据 
				
				error : function(request) {
					layer.msg('修改失败!');
				},
				success : function(data) {
					if ("200" == data.status) {
						
						layer.msg("修改成功!", "", 3000);
						 var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
					     parent.layer.close(index);
					     window.parent.successCallback('2');
					     
				 	} else {
						layer.msg("修改失败!", "", 3000);
					} 
				}
			});
		}
	}
	
	//删除提示
	function physicalDelete(bankCardId) {
		layer.confirm("您确认删除信息吗？", {
		    btn: ["是","否"], //按钮
		    shade: false //不显示遮罩
		}, function(){
			// 点击确定之后
			var url = '${base}/admin/bankcard/delete.html';
			$.ajax({
				type : 'POST',
				data : {
					bankCardId : bankCardId
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

	</script>


</body>
</html>










