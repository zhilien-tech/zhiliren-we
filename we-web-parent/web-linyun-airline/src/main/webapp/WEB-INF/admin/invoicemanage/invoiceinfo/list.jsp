<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<link rel="stylesheet" type="text/css" href="${base }/public/dist/css/invoiceManage.css">
 <!-- Content-->
  <div class="content-wrapper">
    <!-- Main content -->
    <section class="content">
    <div class="row row-top">
        <div class="col-xs-12">
          
          <div class="nav-tabs-custom">
              <ul class="nav nav-tabs">
                <li class="active"><a href="#tab_1" data-toggle="tab">开发票</a></li>
                <li><a href="#tab_2" data-toggle="tab">收发票</a></li>
              </ul>
              <div class="tab-content">
                  <div class="tab-pane pane-content active" id="tab_1"><!--开发票-->
                    <div class="box-header">
                          <form role="form" class="form-horizontal">
                           <div class="form-group row marginBott5 cf">
                             <div class="col-md-1 textPadding">
                               <select class="form-control TimeInput">
                                   <option>全部</option>
                                   <option>收发票中</option>
                                   <option>已开发票</option>
                               </select>
                             </div>
                             <div class="col-md-1 textPadding">
                               <select class="form-control TimeInput">
                                   <option>开票人</option>
                                   <option>张三</option>
                               </select>
                             </div>
                             <div class="col-md-1 textPadding">
                               <input type="text" class="form-control TimeInput" placeholder="2017-02-20"> 
                             </div>
                             <label class="col-md-1 labelClas">至</label>
                             <div class="col-md-1 textPadding">
                               <input type="text" class="form-control TimeInput" placeholder="2017-02-22">
                             </div>
                             <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                               <input type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                             </div>
                             <div class="col-md-2"><!-- 搜索 按钮 -->
                               <button type="button" class="btn btn-primary btn-sm">搜索</button>
                               <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                             </div>
                           </div>
                         </form>
                    </div>
                    <div class="box-body">
                         <table id="KaiInvoiceTable1" class="table table-bordered table-hover">
                           <thead>
                           <tr>
                             <th>订单号</th>
                             <th>发票号</th>
                             <th>发票金额</th>
                             <th>总额</th>
                             <th>开发票日期</th>
                             <th>发票开具项目</th>
                             <th>付款单位</th>
                             <th>开票人</th>
                             <th>状态</th>
                             <th>备注</th>
                             <th>操作</th>
                           </tr>
                           </thead>
                           <tbody>
                           </tbody>
                         </table>
                    </div>
                  </div><!--end 开发票-->
                  <div class="tab-pane pane-content" id="tab_2"><!--收发票-->
                    <div class="box-header">
                        <form role="form" class="form-horizontal">
                          <div class="form-group row marginBott5 cf">
                            <div class="col-md-1 textPadding">
                              <select class="form-control TimeInput">
                                  <option>全部</option>
                                  <option>收发票中</option>
                                  <option>已开发票</option>
                              </select>
                            </div>
                            <div class="col-md-1 textPadding">
                              <select class="form-control TimeInput">
                                  <option>开票人</option>
                                  <option>张三</option>
                              </select>
                            </div>
                            <div class="col-md-1 textPadding">
                              <input type="text" class="form-control TimeInput" placeholder="2017-02-20"> 
                            </div>
                            <label class="col-md-1 labelClas">至</label>
                            <div class="col-md-1 textPadding">
                              <input type="text" class="form-control TimeInput" placeholder="2017-02-22">
                            </div>
                            <div class="col-md-3 textPadding"><!-- 客户名称/订单号/联系人/PNR 搜索框 -->
                              <input type="text" class="form-control" placeholder="客户名称/订单号/联系人/PNR">
                            </div>
                            <div class="col-md-2"><!-- 搜索 按钮 -->
                              <button type="button" class="btn btn-primary btn-sm">搜索</button>
                              <button type="button" class="btn btn-primary btn-sm ckBtn">清空</button>
                            </div>
                          </div>
                        </form>
                    </div>
                    <div class="box-body">
                         <table id="shouInvoiceTable" class="table table-bordered table-hover">
                           <thead>
                           <tr>
                             <th>订单号</th>
                             <th>PNR</th>
                             <th>人数</th>
                             <th>发票数</th>
                             <th>总金额</th>
                             <th>收发票日期</th>
                             <th>发票开具项目</th>
                             <th>收款单位</th>
                             <th>开票人</th>
                             <th>状态</th>
                             <th>备注</th>
                             <th>操作</th>
                           </tr>
                           </thead>
                           <tbody>
                           </tbody>
                         </table>
                    </div>
                  </div><!--end 收发票-->
              </div><!-- end tab-content -->
          </div><!-- end nav-tabs-custom -->
        </div>
      </div>
    </section>
  </div>
<!-- Footer -->
<%@include file="/WEB-INF/public/footer.jsp"%>
<!--end footer-->
<script type="text/javascript">
	var BASE_PATH = '${base}';
</script>
<script src="${base}/admin/order/invoice.js"></script>
<script src="${base}/admin/invoiceinfo/invoiceinfo.js"></script>