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
        <!-- Optionally, you can add icons to the links -->
        <%-- <li><a href="${base}/admin/user/list.html"><i class="fa fa-users"></i><span>员工管理</span></a></li> --%>
		<li><a href="${base}/admin/authority/authoritymanage/list.html"><i class="fa fa-eye"></i><span>权限管理</span></a></li>
        <li><a href="${base}/admin/operationsArea/desktop.html"><i class="fa fa-tv"></i><span>桌面</span></a></li>
        <li><a href="${base}/admin/Company/list.html"><i class="fa fa-building"></i><span>公司管理</span></a></li>
        <li><a href="${base}/admin/customer/list.html"><i class="fa fa-user-secret"></i><span>客户管理</span></a></li>
        <li><a href="${base}/admin/customneeds/list.html"><i class="fa fa-plane"></i><span>航空公司模块</span></a></li>
        <%-- <li><a href="${base}/admin/authority/function/list.html"><i class="fa fa-th-large"></i><span>功能管理</span></a></li> --%>
        <li><a href="${base}/admin/authority/companyfunction/companyList.html"><i class="fa fa-gears"></i><span>公司权限配置</span></a></li>
         
        </li>
        <li class="treeview">
          <a href="${base}/admin/dictionary/dirtype/list.html">
            <i class="fa fa-table"></i> <span>数据字典</span>
            <span class="pull-right-container">
              <i class="fa fa-chevron-right pull-right" style="font-size:12px;margin: 2px 10px;"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="${base}/admin/dictionary/dirtype/list.html" style="margin-left: 15px;"><i class="fa fa-circle-o"></i> 字典类型</a></li>
            <li><a href="${base}/admin/dictionary/dirinfo/list.html" style="margin-left: 15px;"><i class="fa fa-circle-o"></i> 字典信息</a></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
