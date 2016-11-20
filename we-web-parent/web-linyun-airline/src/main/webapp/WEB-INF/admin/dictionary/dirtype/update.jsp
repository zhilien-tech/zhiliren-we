<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<div class="panel_box">
	<div class="panel_content nopadding">
		<form method="post" action="${base}/admin/dictionary/dirtype/update.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
			<div class="form_item">
				<label class="form_label">字典类别编码：</label>
			  	<div class="form_ctrl">
			  		<%-- 字典类别id --%>
			  		<input name="id" type="hidden" value="${obj.dirtype.id}"/>
			    	<input name="typeCode" type="text" class="required"  maxlength="10" value="${obj.dirtype.typeCode}"/>
			  	</div>
			</div>
			<div class="form_item">
				<label class="form_label">字典类别名称：</label>
				<div class="form_ctrl">
					<input name="typeName" type="text" class="required" maxlength="32" value="${obj.dirtype.typeName}"/>
				</div>
			</div>
			<div class="form_item">
				<label class="form_label">描述：</label>
				<div class="form_ctrl">
					<input name="description" type="text" class="required" maxlength="32" value="${obj.dirtype.description}"/>
				</div>
			</div>
			<div class="form_item">
				<label class="form_label">状态：</label>
				<div class="form_ctrl">
					<select id="status" name="status">
						<c:forEach items="${obj }">
	     					<option value="0">冻结</option>
							<option value="1">启用</option>
							<option value="2">删除</option>
						</c:forEach> 
					</select>
				</div>
			</div>
			
			<%-- <div class="form_item">
				<label class="form_label"><button type="button" class="btn btn_modify" onclick="initTree()">功能</button></label>
				<div class="form_ctrl">
						<div id="ztreeDiv">
							<c:forEach var="p" items="${obj.list}">
								<li type="hidden" id="${p.id }" name="${p.typeName }"/>
							</c:forEach>
						</div>
						<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div> --%>
			
            <div class="form_actions">
              	<button type="submit" class="btn btn_add" onclick="setFunc()">保存</button>
				<button type="button" class="btn btn_del closeDialog">取消</button>
            </div>
          </form>
	</div>
</div>
<script>
seajs.use('${static_path}/xiaoka/dlws-xiaoka-game/dictionary/dirtype/update');

//状态默认选中
var  ss = document.getElementById('status');
var status= "${obj.dirtype.status}";
ss[status].selected=true;

var setting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
		
	function initTree(){
		if(!we.utils.isEmpty($("#treeDemo").html())){
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			treeObj.expandAll(false);
			return ;
		}
		
		//获取zNodes
		var zNodes = new Array();
		$('#ztreeDiv li').each(function(){
			var $this = $(this);
			var node =  { id:$this.attr('id'),name:$this.attr('typeName'),open:"true"};
			//每个节点
			we.utils.pushOnly(node,zNodes);
		});
		
		//调用初始方法
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	}

	function setFunc(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes =  treeObj.getCheckedNodes(true);
		
		var funcIds = "" ;
		for(var i = 0 ; i < nodes.length; i ++){
			funcIds += (nodes[i].id + ",") ;
		}
		$("input[name='functionIds']").val(funcIds) ;
	}
</script>