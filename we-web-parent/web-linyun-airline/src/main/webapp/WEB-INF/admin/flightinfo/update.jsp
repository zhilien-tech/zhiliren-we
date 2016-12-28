<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/flightinfo/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">出发城市：</label>
			  	<div class="form_ctrl">
			    	<input name="leavecity" type="text" class="required"  maxlength="10" value="${obj.leavecity}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">返回城市：</label>
			  	<div class="form_ctrl">
			    	<input name="backcity" type="text" class="required"  maxlength="10" value="${obj.backcity}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">航班号：</label>
			  	<div class="form_ctrl">
			    	<input name="airlinenum" type="text" class="required"  maxlength="10" value="${obj.airlinenum}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">起飞时间：</label>
			  	<div class="form_ctrl">
			    	<input name="leavetime" type="text" class="required"  maxlength="10" value="${obj.leavetime}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">到达时间：</label>
			  	<div class="form_ctrl">
			    	<input name="backtime" type="text" class="required"  maxlength="10" value="${obj.backtime}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">班期：</label>
			  	<div class="form_ctrl">
			    	<input name="banqi" type="text" class="required"  maxlength="10" value="${obj.banqi}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">机型：</label>
			  	<div class="form_ctrl">
			    	<input name="airtype" type="text" class="required"  maxlength="10" value="${obj.airtype}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">航空公司：</label>
			  	<div class="form_ctrl">
			    	<input name="aircomid" type="text" class="required"  maxlength="10" value="${obj.aircomid}"/>
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