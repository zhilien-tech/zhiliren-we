<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/customer/add.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			
			<div class="form_item">
				<label class="form_label">上游公司id：</label>
			  	<div class="form_ctrl">
			    	<input name="comId" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">代理商id：</label>
			  	<div class="form_ctrl">
			    	<input name="agentId" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">名称：</label>
			  	<div class="form_ctrl">
			    	<input name="name" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">简称：</label>
			  	<div class="form_ctrl">
			    	<input name="shortName" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">联系人：</label>
			  	<div class="form_ctrl">
			    	<input name="linkMan" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">电话：</label>
			  	<div class="form_ctrl">
			    	<input name="telephone" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">传真：</label>
			  	<div class="form_ctrl">
			    	<input name="fax" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">网址：</label>
			  	<div class="form_ctrl">
			    	<input name="siteUrl" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">地址：</label>
			  	<div class="form_ctrl">
			    	<input name="address" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">负责人：</label>
			  	<div class="form_ctrl">
			    	<input name="agent" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">添加时间：</label>
			  	<div class="form_ctrl">
			    	<input name="createTime" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">出发城市：</label>
			  	<div class="form_ctrl">
			    	<input name="departureCity" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">附件管理：</label>
			  	<div class="form_ctrl">
			    	<input name="appendix" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">旅行社类型：</label>
			  	<div class="form_ctrl">
			    	<input name="travelType" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">付款方式（现金、支票、银行汇款、第三方、其他）：</label>
			  	<div class="form_ctrl">
			    	<input name="payWay" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">是否提供发票（0：否   1：是）：</label>
			  	<div class="form_ctrl">
			    	<input name="invoice" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">销售价：</label>
			  	<div class="form_ctrl">
			    	<input name="sellPrice" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">货币单位：</label>
			  	<div class="form_ctrl">
			    	<input name="moneyUnit" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">结算形式（月结、周结、单结、其他）：</label>
			  	<div class="form_ctrl">
			    	<input name="payType" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">合作时间：</label>
			  	<div class="form_ctrl">
			    	<input name="cooperateTime" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">合作到期时间：</label>
			  	<div class="form_ctrl">
			    	<input name="cooperateDueTime" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">签约时间：</label>
			  	<div class="form_ctrl">
			    	<input name="contractTime" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">签约到期时间：</label>
			  	<div class="form_ctrl">
			    	<input name="contractDueTime" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">是否签约（未签约、已签约、禁止合作）：</label>
			  	<div class="form_ctrl">
			    	<input name="contract" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">是否禁用：</label>
			  	<div class="form_ctrl">
			    	<input name="forbid" type="text" class="required"  maxlength="10"/>
			  	</div>
			</div>
			
            <div class="form_actions">
              	<button type="submit" class="btn btn_add">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
          </form>
	</div>
</div>