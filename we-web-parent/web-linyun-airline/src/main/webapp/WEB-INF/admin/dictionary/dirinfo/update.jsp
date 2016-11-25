<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%> 
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>编辑</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
</head>
<body onresize=hero();>
          <div class="modal-top">
          <form id="updateForm">
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
                  <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
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
<script type="text/javascript">
$("#submit").click(function(){
	$.ajax({
           cache: true,
           type: "POST",
           url:'${base}/admin/dictionary/dirinfo/update.html',
           data:$('#updateForm').serialize(),// 你的formid
           error: function(request) {
              layer.msg('修改失败!');
           },
             success: function(data) {
		layer.load(1, {
			 shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
              layer.msg('修改成功!',{time: 5000, icon:6});
		  	  window.location.reload(true);
          }
       });
	 $(".Mymodal-lg").modal('hide');
});
</script>
