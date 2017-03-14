<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<style type="text/css">
	.sidebar-mini .wrapper .wrapper footer:nth-child(even){display:none;} 
</style>
<!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar Menu -->
      <ul class="sidebar-menu">
        <c:forEach items="${menus}" var="menu" varStatus="stat">
	  	<c:if test="${menu.level == 1 }">
	  		<li id="${stat.index}">
	  			 <c:choose>
				   <c:when test="${menu.url != null && '' != menu.url}">
				   		<a href="${base}${menu.url}?currentPageIndex=${stat.index}">
				   				<i class="${menu.portrait }"></i>
				   			<span>${menu.name}</span>
				   		</a>
				   </c:when>
				   <c:otherwise>
				   		<a href="javascript:;" class="menu1">
				   			<i class="fa fa-th-large"></i>
				   			<span>${menu.name}</span>
				   			<span class="pull-right-container">
				              <i class="fa fa-angle-right pull-right" style="font-size:12px;margin: 2px 10px;"></i>
				            </span>
				   		</a>
				   		<%--子菜单 --%>
				   		<ul class="none menu-ul" style="padding-left: 22px;">
							<c:forEach var="function" items="${functionMap[menu.id]}">
								<li style="line-height: 30px;">
									<a href="${base}${function.url}?currentPageIndex=${stat.index}" style="margin-left: 15px;"><i class="fa fa-circle-o"></i> ${function.name}</a>
								</li>
							</c:forEach>
						</ul>
						
				   </c:otherwise>
			    </c:choose>
			    
				<!-- <ul class="treeview-menu">
				</ul> -->
			</li>
		  	</c:if>
	  	</c:forEach>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
<script>
	$(function(){
		//二级菜单 显示隐藏
		 $('.menu1').click(function(){
			$(this).next('ul').toggle();
		}); 
		
		if($('.menu1').parent().hasClass('active')){
			$(this).find('.menu-ul').show();
		}else{
			$(this).find('.menu-ul').hide();
		}
	});
</script>