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
          <form method="post" action="${base}/admin/dictionary/dirtype/update.html">
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
                  <button type="submit" class="btn btn-primary right btn-sm" data-dismiss="modal">保存</button>
                  <h4>编辑</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                        <div class="form-group row">
                        	<input name="id" type="hidden" value="${obj.dirtype.id}"/>
                            <label class="col-sm-3 text-right padding">字典类型编码：</label>
                            <div class="col-sm-8 padding">
			    				<input name="typeCode" id="typeCode" type="text" class="form-control input-sm"  maxlength="10" value="${obj.dirtype.typeCode}"/>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">字典类别名称：</label>
                            <div class="col-sm-8 padding">
                              <input name="typeName" id="typeName" type="text" class="form-control input-sm" maxlength="32" value="${obj.dirtype.typeName}"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">状态：</label>
                            <div class="col-sm-8 padding">
                              	<select id="status" name="status" class="form-control input-sm">
									<c:forEach items="${obj }">
				     					<option value="0" <c:if test="${'0' eq obj.dirtype.status}">selected</c:if>>冻结</option>
										<option value="1" <c:if test="${'1' eq obj.dirtype.status}">selected</c:if>>启用</option>
									</c:forEach> 
								</select>
                            </div>
                        </div>
						<div class="form-group row">
                            <label class="col-sm-3 text-right padding">描述：</label>
                            <div class="col-sm-8 padding">
                              <textarea name="description" id="description" class="form-control">${obj.dirtype.description}</textarea>
                            </div>
                        </div>
                      </form>
                    </div>
                </div>
            </div>
</body>
</html>	
<script type="text/javascript">
<!-- 状态默认选择 -->
/**状态默认选中*/
	//var  ss = document.getElementById('status');
	//var status= "${obj.dirtype.status}";
	//ss[status].selected=true;
</script>
	
	
	
	
	
	
	
	
	
	