<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>退票-add</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" onclick="saveData()" value="提交"/>
            <h4>退票</h4>
          </div>
          <div class="modal-body" style="padding:0;">
            <div class="tab-content backcard">
            	<form id="mitigateForm">
                <div class="form-group row">
                	<input type="hidden" id="orderid" name="orderid" value="${obj.orderid }">
                  <label class="col-sm-2 text-right padding">减免对象：</label>
                  <div class="col-sm-5 padding">
                  	<input id="customeid" name="customeid" type="hidden" value="${obj.customeinfo.id }">
                    <input name="customname" type="text" class="form-control input-sm" value="${obj.customeinfo.name }"/>
                  </div>
                  <label class="col-sm-2 text-right padding">用途：</label>
                  <div class="col-sm-2 padding">
                    <input id="application" name="application" type="text" class="form-control input-sm" value="${obj.mitigate.application }"/>
                  </div>
                </div>

                <div class="form-group row">
                  <label class="col-sm-2 text-right padding">金额：</label>
                  <div class="col-sm-2 padding">
                    <input id="account" name="account" type="text" class="form-control input-sm account mustNumberPoint" value="${obj.mitigate.account }"/>
                  </div>
                  <div class="col-sm-3 padding">
                      <input id="accountupper" name="accountupper" type="text" class="form-control input-sm" value="${obj.mitigate.accountupper }" disabled="disabled" />
                  </div>
                  <label class="col-sm-2 text-right padding">币种：</label>
                  <div class="col-sm-2 padding">
                    <select id="currency" name="currency" class="form-control input-sm">
                    	<option value="">请选择</option>
                      <c:forEach items="${obj.bzcode }" var="one"> 
                      	 <c:choose>
                      	 	<c:when test="${one.dictCode eq obj.mitigate.currency}">
			                     <option value="${one.dictCode }" selected="selected">${one.dictCode }</option>
                      	 	</c:when>
                      	 	<c:otherwise>
			                     <option value="${one.dictCode }">${one.dictCode }</option>
                      	 	</c:otherwise>
                      	 </c:choose>
	                  </c:forEach>
                    </select>
                  </div>
                </div><!--end 银行/币种-->

                <div class="form-group row">
                  <label class="col-sm-2 text-right padding">申请人：</label>
                  <div class="col-sm-2 padding"><input id="applyid" name="applyid" type="text" class="form-control input-sm" disabled="disabled" value="${empty obj.mitigate.applyid ? obj.user.fullName : obj.applyuser.fullName }"/></div>
                  <label class="col-sm-1 text-right padding">审批人：</label>
                  <div class="col-sm-2 padding"><input id="approvelid" name="approvelid" type="text" class="form-control input-sm" disabled="disabled" value="候小凌"/></div>
                  <label class="col-sm-2 text-right padding">申请结果：</label>
                  <div class="col-sm-2 padding"><input id="applyResult" name="applyResult" type="text" class="form-control input-sm" disabled="disabled" value="${obj.applyresult }"/></div>
                </div><!--end 银行/币种-->
                </form>
            </div>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
</body>
</html>	