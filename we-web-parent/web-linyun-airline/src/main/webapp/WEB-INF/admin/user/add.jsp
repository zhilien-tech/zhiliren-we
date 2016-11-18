<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/user/add.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">用户姓名：</label>
			  	<div class="form_ctrl">
			    	<input name="userName" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">用户名/手机号码：</label>
			  	<div class="form_ctrl">
			    	<input name="telephone" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">座机号码：</label>
			  	<div class="form_ctrl">
			    	<input name="landline" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">联系QQ：</label>
			  	<div class="form_ctrl">
			    	<input name="qq" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">电子邮箱：</label>
			  	<div class="form_ctrl">
			    	<input name="email" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">用户类型：</label>
			  	<div class="form_ctrl">
			    	<select id="select_userType" name="userType" class="required">
						<c:forEach var="map" items="${obj.userTypeEnum}" >
							<c:choose>
							   <c:when test="${map.key == 1}">
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
			<div class="form_item">
				<label class="form_label">状态：</label>
			  	<div class="form_ctrl">
			    	<select id="select_status" name="status" class="required">
						<c:forEach var="map" items="${obj.userStatusEnum}" >
							<c:choose>
							   <c:when test="${map.key == 1}">
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
			<div class="form_item">
				<label class="form_label">创建时间：</label>
			  	<div class="form_ctrl">
			    	<input name="createTime" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">更新时间：</label>
			  	<div class="form_ctrl">
			    	<input name="updateTime" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">用户是否禁用：</label>
			  	<div class="form_ctrl">
			    	<input name="disableStatus" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">备注：</label>
			  	<div class="form_ctrl">
			    	<input name="remark" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			
            <div class="form_actions">
              	<button type="submit" class="btn btn_add">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
          </form>
	</div>
</div>