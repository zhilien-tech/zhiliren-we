<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>

<c:set var="url" value="${base}/admin/activity" />

<form id="pagerForm" action="${url}/list.html" method="post">
	<input type="hidden" name="pageNumber" value="${obj.pager.pageNumber}" />
	<input type="hidden" name="pageSize" value="${obj.pager.pageSize}" />
</form>

<div class="panel_search"> 
	<form action="${url}/list.html" method="post" onsubmit="return navTabSearch(this);">
		<input type="hidden" name="pageSize" value="${obj.pager.pageSize}" />
		<input type="hidden" name="pageNumber" value="${obj.pager.pageNumber}" />
		<p><span class="tag">标题：</span><span class="ipts"><input type="text" name="title" value="${obj.title}"/></span></p>
		<p><span class="tag">活动开始时间：</span><span class="ipts"><input type="text" class="date" datefmt="yyyy-MM-dd HH:mm:00" name="beginTime" value="${obj.beginTime}"/></span></p>
		<p><span class="tag">活动结束时间：</span><span class="ipts"><input type="text" class="date" datefmt="yyyy-MM-dd HH:mm:00" name="endTime" value="${obj.endTime}"/></span></p>
		<p><span class="ipts"><input class="btn btn_add" type="submit" value="检索"/></span></p>
		
	</form>
</div>


<div class="panel_box noborder">
	<div class="panel_content nopadding">
	
		<ul class="tabletools">
			<li><span class="ipts"><a href="${url}/add.html" target="dialog" class="btn btn_mini btn_add">新增活动</a></span></li>
		</ul>
		<table class="table" width="100%">
			<thead>
				<tr>
					<th><input type="checkbox" group="ids" class="ipt_checkbox checkboxCtrl"></th>
					<th>标题</th>
					<th>活动开始时间</th>
					<th>活动结束时间</th>
					<th>封面图</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${obj.list}" var="one" >
					<tr>
						<td class="txtc"><input name="ids" value="${one.id}" type="checkbox" class="ipt_checkbox"></td>
						<td><a target="dialog" href="${url}/activDetail.html?id=${one.id}">${one.title}</a></td>
						<td><fmt:formatDate value="${one.beginTime}" pattern="yyyy-MM-dd HH:mm"/></td>
						<td><fmt:formatDate value="${one.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
						<td><img src="${one.cover}" width="150" height="50"></td>
						<%-- <td>${one.isDel}</td> --%>
						<%-- <td><we:enum className="com.xiaoka.game.admin.activity.enums.IsDeleteEnum" key="${one.isDel}"/></td> --%>
						<td>
							<a target="dialog" rel="dlgId1" href="${url}/update.html?id=${one.id}" class="btn btn_mini btn_modify">修改</a>
							<%--
								这里如果有写title，则需要确认才会操作
							 --%>
							<a target="ajaxTodo" rel="dlgId1" href="${url}/delete?id=${one.id}&isDel=0" title='是否要删除' class='btn btn_mini btn_del'>删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<%--
			分页标签需要注意的是：
				如果是divSearch，这里需要填充rel属性
				如果是dialogSearch，需要赋值dialog='true'
				如果是navTabSearch，则不需要再写其他
				其他参数自行参看
		--%>
		<!-- 分页 start-->
		<we:page pager="${obj.pager}"/>
		<!-- 分页 end-->
	</div>
</div>
