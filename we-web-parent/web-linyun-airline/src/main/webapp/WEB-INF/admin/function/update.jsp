<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/function/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			<input name="id" type="hidden" value="${obj.function.id}"/>
			<div class="form_item">
				<label class="form_label">功能名称：</label>
			  	<div class="form_ctrl">
			    	<input name="name" type="text" class="required"  maxlength="10" value="${obj.name}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">访问地止：</label>
			  	<div class="form_ctrl">
			    	<input name="url" type="text" class="required"  maxlength="10" value="${obj.url}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">上级功能：</label>
				<div class="form_ctrl">
					<select name="parentId">
						<option value="-1">--请选择--</option>
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
			
			<div class="form_item">
				<label class="form_label">功能等级</label>
			  	<div class="form_ctrl">
			    	<input name="level" type="text" class="required"  maxlength="10" value="${obj.level}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">创建时间：</label>
			  	<div class="form_ctrl">
			    	<input name="createTime" type="text" class="required"  maxlength="10" value="${obj.createTime}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">更新时间：</label>
			  	<div class="form_ctrl">
			    	<input name="updateTime" type="text" class="required"  maxlength="10" value="${obj.updateTime}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">备注：</label>
			  	<div class="form_ctrl">
			    	<input name="remark" type="text" class="required"  maxlength="10" value="${obj.remark}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">序号：</label>
			  	<div class="form_ctrl">
			    	<input name="sort" type="text" class="required"  maxlength="10" value="${obj.sort}"/>
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