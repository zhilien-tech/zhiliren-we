<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>	
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>客户管理</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
  <link rel="stylesheet" href="${base}/public/dist/css/skins/skin-blue.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/skins/_all-skins.min.css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <%@include file="/WEB-INF/public/header.jsp"%>
  <%@include file="/WEB-INF/public/aside.jsp"%>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">&nbsp;&nbsp;<i class="fa fa-user-secret"></i> 客户管理</h3>
               <form role="form" class="form-horizontal">
              <div class="form-group row form-right">
             
                <div class="col-md-2"><!--是否签约 下拉框-->
                  <select class="form-control select">
                    <option>是否签约</option>
                    <option>已签约</option>
                    <option>未签约</option>
                    <option>禁止合作</option>
                  </select>
                </div>
                <div class="col-md-2"><!--是否禁用 下拉框-->
                  <select class="form-control select">
                    <option>是否禁用</option>
                    <option>是</option>
                    <option>否</option>
                  </select>
                </div>
                <div class="col-md-3"><!--公司名称/负责人/电话 搜索框-->
                  <input type="text" class="form-control" placeholder="公司名称/负责人/电话">
                </div>
                <div class="col-md-2 col-padding"><!--搜索 恢复默认 按钮-->
                  <button type="button" class="btn btn-primary btn-sm">搜索</button>
                  <button type="button" class="btn btn-primary btn-sm btn-left">恢复默认</button>
                </div>
              
                <div class="col-md-1 col-md-offset-2">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target=".Mymodal-lg">添加</button>
                </div>

              </div>
              </form>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>公司名称</th>
                  <th>联系人</th>
                  <th>联系方式</th>
                  <th>员工人数</th>
                  <th>公司类型</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${obj.list}" var="one" >
						<tr>
							<td class="txtc"><input name="ids" value="${one.id}" type="checkbox" class="ipt_checkbox"></td>
							<td>${one.comName}</td>
							<td>${one.connect}</td>
							<td>${one.mobile}</td>
							<td></td>
							<td>${one.comType}</td>
							<td>
								<a href="#addTabs" data-toggle="modal">修改</a>
								<a target="ajaxTodo" rel="dlgId1" href="${url}/delete?id=${one.id}" title='是否要删除' class='btn btn_mini btn_del'>删除</a>
							</td>
						</tr>
					</c:forEach>
                </tbody>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <%@include file="/WEB-INF/public/footer.jsp"%>
</div>
<!-- ./wrapper -->

<!--弹框 div-->
<div class="modal fade Mymodal-lg" role="dialog" tabindex="-1" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="addTabs">
      <div class="modal-dialog modal-lg">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
                  <button type="submit" class="btn btn-primary right btn-sm" data-dismiss="modal">保存</button>
                  <ul class="nav nav-tabs">
                    <li class="active"><a href="#tabs_1" data-toggle="tab">基本信息</a></li>
                    <li><a href="#tabs_2" data-toggle="tab">线路权限</a></li>
                    <li><a href="#tabs_3" data-toggle="tab">附件管理</a></li>
                    <li><a href="#tabs_4" data-toggle="tab">业务范围</a></li>
                    <li><a href="#tabs_5" data-toggle="tab">财务信息</a></li>
                  </ul>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                    <div class="tab-pane active" id="tabs_1"><!--基本信息-->
                      <form> 
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">公司名称：</label>
                            <div class="col-sm-8 padding">
                              <input type="tel" class="form-control input-sm" placeholder="聚优国际旅行社（北京）有限公司" />
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">公司简称：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="请输入公司简称" />
                            </div>
                          
                            <label class="col-sm-2 text-right padding">负责人：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="请输入负责人姓名" />
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-3 text-right padding">联系人：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="请输入联系人" />
                            </div>
                          
                            <label class="col-sm-2 text-right padding">联系电话：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="请输入联系电话" />
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-3 text-right padding">网址：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="请输入网址" />
                            </div>
                          
                            <label class="col-sm-2 text-right padding">传真：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="请输入传真" />
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">地址：</label>
                            <div class="col-sm-8 padding">
                              <input type="tel" class="form-control input-sm" placeholder="请输入详细地址" />
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">旅行社类型：</label>
                            <div class="col-sm-3 padding">
                              <select class="form-control input-sm">
                                <option>出境社</option>
                                <option>国内社</option>
                                <option>综合</option>
                              </select>
                            </div>

                            <label class="col-sm-2 text-right padding">是否禁用：</label>
                            <div class="col-sm-3 padding">
                              <select class="form-control input-sm">
                                <option>否</option>
                                <option>是</option>
                              </select>
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-3 text-right padding">出发城市：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="请输入出发城市" />
                            </div>
                        </div>
                      </form>
                      
                    </div>
                    <div class="tab-pane" id="tabs_2"><!--路线权限-->
                       <form> 
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">国境内陆：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="" />
                            </div>

                            <label class="col-sm-2 text-right padding">国际：</label>
                            <div class="col-sm-3 padding">
                              <input type="tel" class="form-control input-sm" placeholder="" />
                            </div>
                        </div>
                      </form>

                    </div>
                    <div class="tab-pane" id="tabs_3"><!--附件管理-->
                      <form> 
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">附件列表：</label>
                            <div class="col-sm-3 padding">
                              <input type="file" id="file" />
                            </div>
                        </div>
                      </form>
                    </div>
                    <div class="tab-pane" id="tabs_4"><!--业务范围-->
                      <form> 
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">业务范围：</label>
                            <div class="col-sm-8 padding">
                              <textarea class="form-control textar-hei"></textarea>
                            </div>
                        </div>
                      </form>
                    </div>
                    <div class="tab-pane tab-pane1" id="tabs_5"><!--财务信息-->
                      <form> 
                        <div class="form-group row">
                            <label class="col-sm-2 text-right padding">签约状态：</label>
                            <div class="col-sm-2 padding">
                              <select class="form-control input-sm">
                                <option>未签约</option>
                                <option>已签约</option>
                                <option>禁止合作</option>
                              </select>
                            </div>
                            
                            <label class="col-sm-2 text-right padding">合作方式：</label>
                            <div class="col-sm-5 padding">
                              <input type="text" class="form-control input-sm input-wid" placeholder="2015-08-08" />
                              至
                              <input type="text" class="form-control input-sm input-wid" placeholder="2088-09-09" />
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-2 text-right padding">付款方式：</label>
                            <div class="col-sm-2 padding">
                              <select class="form-control input-sm">
                                <option>现金</option>
                                <option>支付宝</option>
                                <option>银行汇款</option>
                                <option>第三方</option>
                                <option>其他</option>
                              </select>
                            </div>
                          
                            <label class="col-sm-2 text-right padding">结算方式：</label>
                            <div class="col-sm-2 padding">
                              <select class="form-control input-sm">
                                <option>月结</option>
                                <option>周结</option>
                                <option>单结</option>
                                <option>其他</option>
                              </select>
                            </div>

                             <label class="col-sm-2 text-right padding">提供发票：</label>
                            <div class="col-sm-2 padding">
                              <select class="form-control input-sm">
                                <option>是</option>
                                <option>否</option>
                              </select>
                            </div>
                        </div>

                        <div class="form-group row">
                          <label class="col-sm-2 text-right padding">发票项目：</label>
                            <div class="col-sm-4 padding">
                              <input type="tel" class="form-control input-sm" placeholder="" />
                            </div>
                          
                        </div>
                      </form>
                    </div>
                 </div>
                </div>
            </div>
        </div>
    </div>


<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="${base}/public/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${base}/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${base}/public/dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${base}/public/dist/js/demo.js"></script>
<!-- page script -->
<script>
  $(function () {
    $("#example1").DataTable();
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false
    });
  });
</script>
</body>
</html>
