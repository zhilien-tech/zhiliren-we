<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/dictionary/dirinfo/add.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			<div class="form_item">
				<label class="form_label">字典类别编码：</label>
			  	<div class="form_ctrl">
                    <select id="typeCode" name="typeCode">
                    	<option>--请选择--</option>
                        <c:forEach var="shipList" items="${obj }"> 
							<option value='${shipList.typeCode}' ${shipList.typeCode==typeCode?'selected':''}>
								${shipList.typeName}
							</option>
						</c:forEach>
                    </select>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">字典代码：</label>
			  	<div class="form_ctrl">
			    	<input name="dictCode" type="text" class="required:true"  maxlength="10" placeholder="请输入字典代码..."/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">字典信息：</label>
			  	<div class="form_ctrl">
			    	<input name="dictName" type="text" class="required"  maxlength="10" placeholder="请输入字典信息..."/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">描述：</label>
			  	<div class="form_ctrl">
			    	<input name="description" type="text" class="required"  maxlength="10" placeholder="描述..."/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">状态：</label>
				<div class="form_ctrl"  style="width:222px;">
					<select id="status" name="status">
						<option value="0">冻结</option>
						<option value="1" selected="selected">启用</option>
						<option value="2">删除</option>
					</select>
				</div>
			</div>
            <div class="form_actions">
              	<button type="submit" class="btn btn_add">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
          </form>
	</div>
</div>