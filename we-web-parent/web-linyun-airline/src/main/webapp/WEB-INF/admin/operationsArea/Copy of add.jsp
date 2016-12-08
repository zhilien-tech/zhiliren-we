<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/operationsArea/add.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">标题：</label>
			  	<div class="form_ctrl">
			    	<input name="msgTitle" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">内容：</label>
			  	<div class="form_ctrl">
			    	<input name="msgContent" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">消息类型：</label>
			  	<div class="form_ctrl">
			    	<input name="msgType" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">生成日期：</label>
			  	<div class="form_ctrl">
			    	<input name="generateTime" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">优先级：</label>
			  	<div class="form_ctrl">
			    	<input name="priorityLevel" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			
            <div class="form_actions">
              	<button type="submit" class="btn btn_add" onclick="save()">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
            <script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
            <script src="${base}/public/plugins/fastclick/fastclick.js"></script>
            <script src="${base}/common/js/layer/layer.js"></script>
          </form>
	</div>
</div>