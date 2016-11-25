<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Left side column. contains the logo and sidebar -->
   
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu">
        <li class="header">菜单栏</li>
        <!-- Optionally, you can add icons to the links -->
        <li><a href="employeeManage.html"><i class="fa fa-users"></i> <span>员工管理</span></a></li>
        <li><a href="${base}/admin/customer/list.html"><i class="fa fa-user-secret"></i><span>客户管理</span></a></li>
         <li class="treeview">
          <a href="${base}/admin/job/list.html">
            <i class="fa fa-edit"></i> <span>权限管理</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="${base}/admin/job/list.html"><i class="fa fa-circle-o"></i> 职位管理</a></li>
            <li><a href="${base}/admin/job/list.html"><i class="fa fa-circle-o"></i> 用户管理</a></li>
            <li><a href="${base}/admin/job/list.html"><i class="fa fa-circle-o"></i> 功能管理</a></li>
          </ul>
        </li>
        <li class="active treeview">
          <a href="${base}/admin/dictionary/dirtype/list.html">
            <i class="fa fa-table"></i> <span>数据字典</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="${base}/admin/dictionary/dirtype/list.html"><i class="fa fa-circle-o"></i> 字典类型</a></li>
            <li><a href="${base}/admin/dictionary/dirinfo/list.html"><i class="fa fa-circle-o"></i> 字典信息</a></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
