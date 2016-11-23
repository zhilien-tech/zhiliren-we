<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%> 
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>添加</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
</head>
<body onresize=hero();>
          <div class="modal-top">
          <form id="form1" method="post" action="${base}/admin/dictionary/dirinfo/add.html"> 
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
                  <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
                  <h4>添加</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">字典类型编码：</label>
                            <div class="col-sm-8 padding">
                              <!-- <input name="typeCode" class="form-control input-sm" placeholder="请输入字典类型编码" /> -->
                            	<select id="typeCode" name="typeCode" class="form-control input-sm">
			                    	<option>--请选择--</option>
			                        <c:forEach var="shipList" items="${obj }"> 
										<option value='${shipList.typeCode}' ${shipList.typeCode==typeCode?'selected':''}>
											${shipList.typeName}
										</option>
									</c:forEach>
                    			</select>
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">字典代码：</label>
                            <div class="col-sm-8 padding">
                              <input name="dictCode" class="form-control input-sm" placeholder="请输入字典代码" />
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">字典信息：</label>
                            <div class="col-sm-8 padding">
                              <input name="dictName" class="form-control input-sm" placeholder="请输入字典信息" />
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">状态：</label>
                            <div class="col-sm-8 padding">
                              <select id="status" name="status" class="form-control input-sm">
                                <option value="0">冻结</option>
								<option value="1" selected="selected">启用</option>
                              </select>
                            </div>
                        </div>
						<div class="form-group row">
                            <label class="col-sm-3 text-right padding">描述：</label>
                            <div class="col-sm-8 padding">
                              <textarea name="description" class="form-control"></textarea>
                            </div>
                        </div>
                      </form>
                    </div>
                </div>
            </div>
</body>
</html>	
	
	
	
	
	
	
	
	
	
	
	