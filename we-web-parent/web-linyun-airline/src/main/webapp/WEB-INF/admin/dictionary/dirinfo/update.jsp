<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%> 
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
</head>
<body onresize=hero();>
          <div class="modal-top">
          <form id="updateForm">
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">返回</button>
                  <!-- <button type="button" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitInfo();">保存</button> -->
                  <input type="button" id="submitButton" class="btn btn-primary right btn-sm" onclick="submitInfo();" value="保存"/>
                  <h4>编辑</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                        <div class="form-group row">
                        	<%-- 字典类别id --%>
                        	<input name="id" type="hidden" value="${obj.dirinfo.id}"/>
                            <label class="col-sm-3 text-right padding">字典类型编码：</label>
                            <div class="col-sm-8 padding">
                            	<select id="typeCode" name="typeCode" class="form-control input-sm">
			                    	<option>--请选择--</option>
										<c:forEach var="one" items="${obj.dirtype }">
											<option value='${one.typeCode}' ${one.typeCode==obj.dirinfo.typeCode?'selected':''}>
												${one.typeName}
											</option>
										</c:forEach> 
								</select>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">字典代码：</label>
                            <div class="col-sm-8 padding">
                              <input name="dictCode" id="dictCode" type="text" class="form-control input-sm" maxlength="32" value="${obj.dirinfo.dictCode}"/>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">字典信息：</label>
                            <div class="col-sm-8 padding">
                              <input name="dictName" id="dictName" type="text" class="form-control input-sm" maxlength="32" value="${obj.dirinfo.dictName}"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">状态：</label>
                            <div class="col-sm-8 padding">
                              	<select id="status" name="status" class="form-control input-sm">
									<c:forEach var="map" items="${obj.dataStatusEnum}" >
										<c:choose>
										   <c:when test="${map.key == obj.dirinfo.status}">
										   		<option value="${map.key}" selected="selected">${map.value}</option>
										   </c:when>
										   <c:otherwise>
										   		<option value="${map.key}">${map.value}</option>
										   </c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
                            </div>
                        </div>
						<div class="form-group row">
                            <label class="col-sm-3 text-right padding">描述：</label>
                            <div class="col-sm-8 padding">
                              <textarea name="description" id="description" class="form-control">${obj.dirinfo.description}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
          </form>
     </div>
</body>
</html>	
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<!--layer -->
<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
//验证
$(document).ready(function(){
	$('#updateForm').bootstrapValidator({
		message: '验证不通过!',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	dictCode: {
                validators: {
                    notEmpty: {
                        message: '字典代码不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                         url: '${base}/admin/dictionary/dirinfo/checkTypeCodeExist.html',//验证地址
                         message: '字典代码已存在，请重新输入!',//提示消息
                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                         type: 'POST',//请求方式
                         //自定义提交数据，默认值提交当前input value
                         data: function(validator) {
                            return {
                            	typeCode:$('#typeCode').val(),
                            	id:'${obj.dirinfo.id}'
                            };
                         }
                     },
	                regexp: {
                        regexp: /^[A-Za-z0-9]+$/,
                        message: '字典代码只能为字母或数字'
                    }
                }
            },
            dictName: {
            	validators: {
                    notEmpty: {
                        message: '字典信息不能为空!'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
	                    url: '${base}/admin/dictionary/dirinfo/checkDictNameExist.html',//验证地址
	                         message: '字典信息重复，请重新输入!',//提示消息
	                         delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
	                         type: 'POST',//请求方式
	                         //自定义提交数据，默认值提交当前input value
	                         data: function(validator) {
	                            return {
	                            	dictName:$('input[name="dictName"]').val(),
	                            	id:'${obj.dirinfo.id}'
	                            };
	                         }
	                   }
                }
            }
        }
	});
});
	//关闭当前弹层
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	//更新保存
	$('#submitButton').click(function() {
        $('#updateForm').bootstrapValidator('validate');
    });
	
	function submitInfo(){
		$('#updateForm').bootstrapValidator('validate');
		var bootstrapValidator = $("#updateForm").data('bootstrapValidator');
		if(bootstrapValidator.isValid()){
			$.ajax({
				type: 'POST', 
				data: $("#updateForm").serialize(), 
				url: '${base}/admin/dictionary/dirinfo/update.html',
	            success: function (data) { 
	            	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	            	window.parent.successCallback('2');
	            	parent.layer.close(index);
	            },
	            error: function (xhr) {
	            	layer.msg("编辑失败","",3000);
	            } 
	        });
		}
	}
	//点击返回
	function closewindow(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
</script>
