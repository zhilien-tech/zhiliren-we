<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/operationsArea/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">标题：</label>
			  	<div class="form_ctrl">
			    	<input name="msgTitle" type="text" class="required"  maxlength="10" value="${obj.msgTitle}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">内容：</label>
			  	<div class="form_ctrl">
			    	<input name="msgContent" type="text" class="required"  maxlength="10" value="${obj.msgContent}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">消息类型：</label>
			  	<div class="form_ctrl">
			    	<input name="msgType" type="text" class="required"  maxlength="10" value="${obj.msgType}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">生成日期：</label>
			  	<div class="form_ctrl">
			    	<input name="generateTime" type="text" class="required"  maxlength="10" value="${obj.generateTime}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">优先级：</label>
			  	<div class="form_ctrl">
			    	<input name="priorityLevel" type="text" class="required"  maxlength="10" value="${obj.priorityLevel}"/>
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