<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
	
		<form method="post" action="${base}/admin/dictionary/dirrelation/add.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			<div class="form_item">
				<label class="form_label">字典id：</label>
			  	<div class="form_ctrl">
			    	<input name="sourceId" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">关联字典id：</label>
			  	<div class="form_ctrl">
			    	<input name="targetId" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
            <div class="form_actions">
              	<button type="submit" class="btn btn_add">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
          </form>
	</div>
</div>