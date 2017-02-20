<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/public/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/inlandCross.css">
<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          
          <div class="nav-tabs-custom">
              <ul class="nav nav-tabs">
                <li class="active"><a href="#tab_1" onclick="loadDataTable(0)" data-toggle="tab">全部</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(1)" data-toggle="tab">查询</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(2)" data-toggle="tab">预订</a></li>
                <li><a href="#tab_4" data-toggle="tab">出票</a></li>
                <li><a href="#tab_5" data-toggle="tab">收/付款</a></li>
                <li><a href="#tab_1" onclick="loadDataTable(5)" data-toggle="tab">关闭</a></li>
                <li class="orderLi"><button type="button" id="addOrder" class="btn btn-primary btn-sm right">添加订单</button></li>
              </ul>
              <div class="tab-content"><!--全部-->
                  <div class="tab-pane pane-content active" id="tab_1">
                    <div class="box-header">
                       <form role="form" class="form-horizontal">
                        <div class="form-group row marginBott cf">
                          <div class="col-md-1">
                            <input type="text" class="form-control TimeInput" placeholder="2017-02-20"> 
                          </div>
                          <label class="col-md-1 labelClas">至</label>
                          <div class="col-md-1">
                            <input type="text" class="form-control TimeInput" placeholder="2017-02-22">
                          </div>
                          <div class="col-md-3"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                            <input type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                          </div>
                          <div class="col-md-1"><!-- 搜索 按钮 -->
                            <button type="button" class="btn btn-primary btn-sm">搜索</button>
                          </div>
                          
                        </div>
                      </form>
                    </div>
                    <div class="box-body">
                      <table id="inlandCrossTable" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                          <th>订单</th>
                          <th>PNR</th>
                          <th>日期</th>
                          <th>航班号</th>
                          <th>航段</th>
                          <th>时间</th>
                          <th>销售单价</th>
                          <th>销售总价</th>
                          <th>数量</th>
                          <th>状态</th>
                          <th>联系人</th>
                          <th>电话</th>
                          <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div>
                  </div><!--end 全部-->

                  <!--出票-->
                  <div class="tab-pane pane-content" id="tab_4">
                    <div class="box-header">
                       <form role="form" class="form-horizontal">
                        <div class="form-group row marginBott cf">
                          <div class="col-md-1">
                            <select class="form-control TimeInput">
                                <option>全部</option>
                                <option>已出票</option>
                                <option>收款中</option>
                                <option>付款中</option>
                            </select>
                          </div>
                          <div class="col-md-1">
                            <input type="text" class="form-control TimeInput" placeholder="2017-02-20"> 
                          </div>
                          <label class="col-md-1 labelClas">至</label>
                          <div class="col-md-1">
                            <input type="text" class="form-control TimeInput" placeholder="2017-02-22">
                          </div>
                          <div class="col-md-3"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                            <input type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                          </div>
                          <div class="col-md-1"><!-- 搜索 按钮 -->
                            <button type="button" class="btn btn-primary btn-sm">搜索</button>
                          </div>
                          <div class="receivePay"><!-- 收款/付款 按钮 -->
                            <button type="button" id="paymentBtn" class="btn btn-primary btn-sm right">付款</button>
                            <button type="button" id="collectionBtn" class="btn btn-primary btn-sm right">收款</button>
                          </div>
                        </div>
                      </form>
                    </div>
                    <div class="box-body">
                      <table id="inlandCrossTable1" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                          <td class="checkTh"><input type="checkbox"></td>
                          <th>订单</th>
                          <th>PNR</th>
                          <th>日期</th>
                          <th>航班号</th>
                          <th>航段</th>
                          <th>时间</th>
                          <th>销售单价</th>
                          <th>销售总价</th>
                          <th>数量</th>
                          <th>状态</th>
                          <th>联系人</th>
                          <th>电话</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div>
                  </div><!--end 出票-->

                  <!--收/付款-->
                  <div class="tab-pane pane-content" id="tab_5">
                        <div class="box-header">
                       <form role="form" class="form-horizontal">
                        <div class="form-group row marginBott cf">
                          <div class="col-md-1">
                            <select class="form-control TimeInput">
                                <option>全部</option>
                                <option>已出票</option>
                                <option>收款中</option>
                                <option>付款中</option>
                            </select>
                          </div>
                          <div class="col-md-1">
                            <input type="text" class="form-control TimeInput" placeholder="2017-02-20"> 
                          </div>
                          <label class="col-md-1 labelClas">至</label>
                          <div class="col-md-1">
                            <input type="text" class="form-control TimeInput" placeholder="2017-02-22">
                          </div>
                          <div class="col-md-3"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                            <input type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                          </div>
                          <div class="col-md-1"><!-- 搜索 按钮 -->
                            <button type="button" class="btn btn-primary btn-sm">搜索</button>
                          </div>
                          
                        </div>
                      </form>
                    </div>
                    <div class="box-body">
                      <table id="inlandCrossTable2" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                          <th>订单</th>
                          <th>PNR</th>
                          <th>日期</th>
                          <th>航班号</th>
                          <th>航段</th>
                          <th>时间</th>
                          <th>销售单价</th>
                          <th>销售总价</th>
                          <th>数量</th>
                          <th>状态</th>
                          <th>联系人</th>
                          <th>电话</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div>
                  </div><!--end 收/付款-->

              </div><!-- end tab-content -->
          </div><!-- end nav-tabs-custom -->
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
<script type="text/javascript">
	var BASE_PATH = '${base}';
</script>
<script src="${base}/admin/order/inland.js"></script>
<script type="text/javascript">
//添加订单 弹框
	$('#addOrder').click(function(){
	    layer.open({
	        type: 2,
	        title:false,
	        skin: false, //加上边框
	        closeBtn:false,//默认 右上角关闭按钮 是否显示
	        shadeClose:true,
	        area: ['1087px', '620px'],
	        content: '${base}/admin/inland/addOrder.html'
	      });
	  });
	  
	//他页面回调
	  function successCallback(id){
		  inlandCrossTable.ajax.reload(null,false);
		  if(id == '1'){
			  layer.alert("添加成功",{time: 2000, icon:1});
		  }else if(id == '2'){
			  layer.alert("修改成功",{time: 2000, icon:1});
		  }else if(id == '3'){
			  layer.alert("关闭成功",{time: 2000, icon:1});
		  }else if(id == '4'){
			  layer.alert("启用成功",{time: 2000, icon:1});
		  }
	  }
</script>
