<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/user/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			<input name="user.id" type="hidden" value="${obj.user.id}"/>
			<div class="form_item">
				<label class="form_label">用户姓名：</label>
			  	<div class="form_ctrl">
			    	<input name="userName" type="text" class="required"  maxlength="10" value="${obj.user.userName}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">用户名/手机号码：</label>
			  	<div class="form_ctrl">
			    	<input name="telephone" type="text" class="required"  maxlength="10" value="${obj.user.telephone}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">座机号码：</label>
			  	<div class="form_ctrl">
			    	<input name="landline" type="text" class="required"  maxlength="10" value="${obj.user.landline}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">联系QQ：</label>
			  	<div class="form_ctrl">
			    	<input name="qq" type="text" class="required"  maxlength="10" value="${obj.user.qq}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">电子邮箱：</label>
			  	<div class="form_ctrl">
			    	<input name="email" type="text" class="required"  maxlength="10" value="${obj.user.email}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">用户类型：</label>
			  	<div class="form_ctrl">
			    	<select id="select_userType" name="user.userType" class="required">
						<c:forEach var="map" items="${obj.userTypeEnum}" >
							<c:choose>
							   <c:when test="${map.key == obj.user.userType}">
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
			    	<select id="select_status" name="user.status" class="required">
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
				<label class="form_label">职位设置：</label>
				<div class="form_ctrl">
					<c:forEach items="${obj.jobList}" var="each" varStatus="st" >
						<label>
							<c:choose> 
							<c:when test="${each.checked == true}"> 
								<input name="user.map[${st.index}].jobId" type="checkbox" value="${each.id}" checked="checked"/>${each.name}
							</c:when> 
							<c:otherwise> 
								<input name="user.map[${st.index}].jobId" type="checkbox" value="${each.id}"/>${each.name}
							</c:otherwise> 
							</c:choose> 
						</label>
						<c:if test="${st.count % 6 == 0}"> 
							<br>
						</c:if> 
					</c:forEach>
				</div>
			</div>
			<div class="form_item">
				<label class="form_label">用户是否禁用：</label>
			  	<div class="form_ctrl">
			    	<input name="disableStatus" type="text" class="required"  maxlength="10" value="${obj.user.disableStatus}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">备注：</label>
			  	<div class="form_ctrl">
			    	<input name="remark" type="text" class="required"  maxlength="10" value="${obj.remark}"/>
			  	</div>
			</div>
			
            <div class="form_actions">
            	<input name="id" type="hidden" value="${obj.id}"/>
              	<button type="submit" class="btn btn_add">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
          </form>
	</div>
</div>