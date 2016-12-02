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
                  <button id="backBtn" type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
                  <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
                  <h4>编辑</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                       <input name="id" type="hidden" value="${obj.function.id}"/>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">功能名称：</label>
                            <div class="col-sm-8 padding">
                              <input name="name" type="text" class="form-control input-sm"  maxlength="32" value="${obj.name}"/>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">访问地止：</label>
                            <div class="col-sm-8 padding">
                            	<input name="url" type="text" class="form-control input-sm" maxlength="32" value="${obj.url}"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">上级功能：</label>
                            <div class="col-sm-8 padding">
								<select name="parentId" class="form-control input-sm">
									<option value="">--请选择--</option>
									<c:forEach items="${obj.list}" var="pro" >
										<c:choose>
										   <c:when test="${pro.id == obj.function.parentId}">
										   		<option value="${pro.id}" selected="selected">${pro.name}</option>
										   </c:when>
										   <c:otherwise>
										   		<option value="${pro.id}">${pro.name}</option>
										   </c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">功能等级：</label>
                            <div class="col-sm-8 padding">
                            	<input name="level" type="text" class="form-control input-sm" maxlength="32" value="${obj.level}"/>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">序号：</label>
                            <div class="col-sm-8 padding">
                            	<input name="sort" type="text" class="form-control input-sm" maxlength="32" value="${obj.sort}"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">备注：</label>
                            <div class="col-sm-8 padding">
                              <textarea name="remark" id="remark" class="form-control">${obj.remark}</textarea>
                            </div>
                        </div>
                    </div>
                </div>
          </form>
     </div>
</body>
</html>	
<script type="text/javascript">
//更新提交
$("#submit").click(function(){
	$.ajax({
           cache: true,
           type: "POST",
           url:'${base}/admin/authority/function/update.html',
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
//点击返回按钮自动刷新页面
$('#backBtn').click(function(){
	window.location.href="${base}/admin/authority/function/list.html";
});
</script>
