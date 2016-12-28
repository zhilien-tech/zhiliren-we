<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- Left side column. contains the logo and sidebar -->
   
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu">
        <li class="header">菜单栏</li>
        
        <c:forEach items="${menus}" var="menu" >
	  	<c:if test="${menu.level == 1 }">
	  		<li>
	  			 <c:choose>
				   <c:when test="${menu.url != null && '' != menu.url}">
				   		<a href="${base}${menu.url}">
				   			<i class="fa fa-th-large"></i>
				   			<span>${menu.name}</span>
				   		</a>
				   </c:when>
				   <c:otherwise>
				   		<a>
				   			<i class="fa fa-th-large"></i>
				   			<span>${menu.name}</span>
				   			<span class="pull-right-container">
				              <i class="fa fa-chevron-right pull-right" style="font-size:12px;margin: 2px 10px;"></i>
				            </span>
				   		</a>
				   </c:otherwise>
			    </c:choose>
				<c:forEach var="function" items="${functionMap[menu.id]}" varStatus="stat">
					<ul>
					<li>
						<a href="${base}${function.url}" style="margin-left: 15px;"><i class="fa fa-circle-o"></i> ${function.name}</a>
					</li>
					</ul>
				</c:forEach>
			</li>
		  	</c:if>
	  	</c:forEach>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
