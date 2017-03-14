<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/invoiceinfo/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">发票项目：</label>
			  	<div class="form_ctrl">
			    	<input name="invoiceitem" type="text" class="required"  maxlength="10" value="${obj.invoiceitem}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">发票日期：</label>
			  	<div class="form_ctrl">
			    	<input name="invoicedate" type="text" class="required"  maxlength="10" value="${obj.invoicedate}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">开票人：</label>
			  	<div class="form_ctrl">
			    	<input name="billuserid" type="text" class="required"  maxlength="10" value="${obj.billuserid}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">部门：</label>
			  	<div class="form_ctrl">
			    	<input name="deptid" type="text" class="required"  maxlength="10" value="${obj.deptid}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">付款单位：</label>
			  	<div class="form_ctrl">
			    	<input name="paymentunit" type="text" class="required"  maxlength="10" value="${obj.paymentunit}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">备注：</label>
			  	<div class="form_ctrl">
			    	<input name="remark" type="text" class="required"  maxlength="10" value="${obj.remark}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">差额：</label>
			  	<div class="form_ctrl">
			    	<input name="difference" type="text" class="required"  maxlength="10" value="${obj.difference}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">余额：</label>
			  	<div class="form_ctrl">
			    	<input name="balance" type="text" class="required"  maxlength="10" value="${obj.balance}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">发票类型（收款、付款）：</label>
			  	<div class="form_ctrl">
			    	<input name="invoicetype" type="text" class="required"  maxlength="10" value="${obj.invoicetype}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">收款id：</label>
			  	<div class="form_ctrl">
			    	<input name="receiveid" type="text" class="required"  maxlength="10" value="${obj.receiveid}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">pnrid：</label>
			  	<div class="form_ctrl">
			    	<input name="pnrid" type="text" class="required"  maxlength="10" value="${obj.pnrid}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">opid：</label>
			  	<div class="form_ctrl">
			    	<input name="opid" type="text" class="required"  maxlength="10" value="${obj.opid}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">optime：</label>
			  	<div class="form_ctrl">
			    	<input name="optime" type="text" class="required"  maxlength="10" value="${obj.optime}"/>
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