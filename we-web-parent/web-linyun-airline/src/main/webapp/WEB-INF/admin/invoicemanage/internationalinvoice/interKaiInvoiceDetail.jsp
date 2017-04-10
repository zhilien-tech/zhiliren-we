<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>国际订单详情</title>
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.css">
  <!-- 图标 -->
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base }/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
  <link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
  <link rel="stylesheet" href="${base }/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base }/public/dist/css/query.css">
  <link rel="stylesheet" href="${base }/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <!--Header -->
  <header class="main-header">
    <!-- Logo -->
    <a href="index2.html" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini">航空</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg">航空票务系统</span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <!-- <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a> -->
      <!-- Navbar Right Menu -->
      
    </nav>
  </header>
  <!--end Header -->

  <!--right Content-->
  <div class="content-wrapper">
    <section class="content">
        <div class="row col-sm-12 padding0">
          <div class="customerInfo"><!--客户信息-->
               <div class="infoTop">
                 <p>客户信息</p>
                 <div class="infoTopContent">
                   <span><%-- ${obj.orderinfo.ordersnum } --%></span>
                   <select id="orderType" name="orderType" disabled="disabled" class="form-control input-sm conSelect cf">
                   	 <%-- <c:forEach var="map" items="${obj.internationalstatusenum}" >
                     	<c:if test="${obj.orderinfo.ordersstatus <= map.key }">
                     		<c:choose>
                     			<c:when test="${obj.orderinfo.ordersstatus eq map.key }">
                     				<option value="${map.key}" selected="selected">${map.value}</option>
                     			</c:when>
                     			<c:otherwise>
							   		<option value="${map.key}">${map.value}</option>
                     			</c:otherwise>
                     		</c:choose>
                     	</c:if>
					</c:forEach> --%>
                   </select>
                   <button type="button" class="btn btn-primary input-sm btnRemind none">提醒设置</button>
                   <button type="button" class="btn btn-primary input-sm btnLog none">日志</button>
                   <button type="button" class="btn btn-primary input-sm btnSave none" onclick="saveInternationalDetail()">保存</button> 
                   <button type="button" class="btn btn-primary input-sm btnCancel none">取消</button>
                   <button type="button" class="btn btn-primary input-sm editBtn right">编辑</button>
                   
                 </div>
               </div>
               <div class="infofooter">
                 <table>
                   <tr>
                     <td><label>
                     	<font id="customeidcolor"> 客户姓名：</font>
                     </label></td>
                     <td><input id="linkName" name="linkName" disabled="disabled" type="text" class="form-control input-sm" value="${obj.custominfo.linkMan }">
                     	<input id="customerId" name="customerId" type="hidden" <%-- value="${obj.custominfo.id }" --%>/>
                     	<!-- 订单id -->
                     	<input id="orderedid" name="orderedid" type="hidden" <%-- value="${obj.orderinfo.id }" --%>></td>
	                     <td><label style="position: relative;top: 4px;">结算方式：</label></td>
                     	<td colspan="3"><pre class="preTxt">
					 <%-- <c:choose>
                     	<c:when test="${obj.custominfo.payType eq 1 }">
                     		现金
                     	</c:when>
                     	<c:when test="${obj.custominfo.payType eq 2 }">3
                     		支票
                     	</c:when>
                     	<c:when test="${obj.custominfo.payType eq 3 }">
                     		银行汇款
                     	</c:when>
                     	<c:when test="${obj.custominfo.payType eq 4 }">
                     		第三方
                     	</c:when>
                     	<c:when test="${obj.custominfo.payType eq 5 }">
                     		其他
                     	</c:when>
                     </c:choose>　 --%>
						 信用额度：<fmt:formatNumber type="number" <%-- value="${empty obj.custominfo.creditLine?0:obj.custominfo.creditLine}" pattern="0.00" maxFractionDigits="2" --%>/>  
                     		<font id="historyqiancolor"> 历史欠款：<fmt:formatNumber type="number" <%-- value="${empty obj.custominfo.arrears? 0.00:obj.custominfo.arrears}" pattern="0.00" maxFractionDigits="2" --%>/></font>　
                   		 预存款：<fmt:formatNumber type="number" <%-- value="${empty obj.custominfo.preDeposit?0:obj.custominfo.preDeposit}" pattern="0.00" maxFractionDigits="2" --%>/></pre></td>
                     <td><i class="UnderIcon fa fa-chevron-circle-down"></i></td>
                   </tr>
                 </table>

                 <table class="hideTable none">
                   <tr>
                     <td><label>公司简称：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="" readonly="true"></td>
                     <td><label>电话：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="" readonly="true"></td>
                     <td><label>地址：</label></td>
                     <td colspan="3"><input type="text" class="form-control input-sm addressInput" value="" readonly="true"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>负责人：</label></td>
                     <td><input type="text" class="form-control input-sm" value="" readonly="true"></td>
                     <td><label>网址：</label></td>
                     <td><input type="text" class="form-control input-sm" value="" readonly="true"></td>
                     <td><label>传真：</label></td>
                     <td><input type="text" class="form-control input-sm" value="" readonly="true"></td>
                     <td><label>出发城市：</label></td>
                     <td><input type="text" class="form-control input-sm addressInput" value="" readonly="true"></td>
                   </tr>
                 </table>
               </div>
          </div><!--客户信息-->


          <div class="customerInfo"><!--航程信息-->
               <div class="infoTop">
                 <p>航程信息</p>
               </div>
               <div class="infofooter">
                  <button type="button" class="btn btn-primary right addHD">添加航段</button>
                  <table class="HCinfoInp">
                   <tr>
                     <td><label>航空公司：</label></td>
                     <td class="hkgsTd"><select id="airlinecom" name="airlinecom" disabled="disabled" class="form-control input-sm disab" multiple="multiple">
                     		<%-- <c:forEach items="${obj.aircomselect }" var="aircom">
                     			<c:choose>
                     				<c:when test="${obj.orderinfo.airlinecom eq aircom.dictCode}">
			                     		<option value="${aircom.dictCode }" selected="selected">${aircom.dictCode }-${aircom.dictName }</option>
                     				</c:when>
                     				<c:otherwise>
			                     		<option value="${aircom.dictCode }">${aircom.dictCode }-${aircom.dictName }</option>
                     				</c:otherwise>
                     			</c:choose>
                     		</c:forEach> --%>
                     </select></td>
                     <td class="renshuTd"><label>人数：</label></td>
                     <td><input id="peoplecount" name="peoplecount" disabled="disabled" type="text" class="form-control input-sm disab mustNumber" value=""></td>
                     <td><label>成本单价：</label></td>
                     <td><input id="costsingleprice" name="costsingleprice" disabled="disabled" type="text" class="form-control input-sm disab mustNumberPoint" value=""/></td>
                   </tr>
                 </table>
                 <div class="tableDuan"><!--主段-->
                  <span>主段</span>
                  <table class="table table-bordered table-hover main">
                    <thead>
                      <tr>
                        <th>记录编号</th>
                        <th>航段</th>
                        <th>航班号</th>
                        <th>出发日期</th>
                        <th>时间</th>
                        <th>人数</th>
                        <th colspan="2">操作</th>
                      </tr>
                    </thead>
                    <tbody id="mainsection">
                    </tbody>
                  </table>
                 </div><!--end 主段-->
                 <div class="tableDuan"><!--子段-->
                  <span>子段</span>
                  <table class="table table-bordered table-hover main">
                    <thead>
                      <tr>
                        <th>记录编号</th>
                        <th>航段</th>
                        <th>航班号</th>
                        <th>出发日期</th>
                        <th>时间</th>
                        <th>人数</th>
                        <th colspan="2">操作</th>
                      </tr>
                    </thead>
                    <tbody id="zisection">
                    </tbody>
                  </table>
                 </div><!--end 子段-->
               </div>
          </div><!--end 航程信息-->
          
    	  <div class="listInfo"><!-- 预收款记录/预付款记录 -->
			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs query-style">
					<li class="active"><a href="#tab_1" data-toggle="tab">预收款记录</a></li>
					<li><a href="#tab_2" data-toggle="tab">预付款记录</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab_1">
						<button type="button" class="btn btn-primary right recordBtn addYSK">添加记录</button>
		                <table class="table table-bordered table-hover main">
		                    <thead>
		                      <tr>
		                        <th></th>
		                        <th>预收款比例</th>
		                        <th>免罚金可减人数</th>
		                        <th>实际减少人数</th>
		                        <th>本期罚金</th>
		                        <th>本期应付</th>
		                        <th>本期税金</th>
		                        <th>本期实付</th>
		                        <th>操作</th>
		                      </tr>
		                    </thead>
		                    <tbody id="receiverecord">
		
		                    </tbody>
		                </table>
					</div>
					<div class="tab-pane" id="tab_2">
						<button type="button" class="btn btn-primary right recordBtn addYFK">添加记录</button>
		                <table class="table table-bordered table-hover main">
		                    <thead>
		                      <tr>
		                        <th></th>
		                        <th>预付款比例</th>
		                        <th>免罚金可减人数</th>
		                        <th>实际减少人数</th>
		                        <th>本期罚金</th>
		                        <th>本期应付</th>
		                        <th>本期税金</th>
		                        <th>本期实付</th>
		                        <th>操作</th>
		                      </tr>
		                    </thead>
		                    <tbody id="payrecord">
		                    </tbody>
		                 </table>		
					</div>
				</div>
			</div>
		 </div><!--end 预收款记录/预付款记录 -->

          <div class="customerInfo"><!--信息-->
            <div class="infoTop">
                 <p>信息</p>
            </div>
            <div class="infofooter">
            	<form id="financeForm">
            		<%-- <input id="id" name="id" type="hidden" value="${obj.finance.id }" >
            		<input id="orderid" name="orderid" type="hidden" value="${obj.orderinfo.id }"/> --%>
                 <table class="">
                   <tr>
                     <td><label>客户团号：</label></td>
                     <td><input id="cusgroupnum" name="cusgroupnum" type="text" class="form-control input-sm disab" disabled="disabled" value=""></td>
                     <td><label>类型：</label></td>
                     <td>
                        <select id="teamtype" name="teamtype" class="form-control input-sm disab" disabled="disabled">
                            <option value="">请选择</option>
                            <%-- <c:forEach var="map" items="${obj.passengertypeenum }" >
                            	<c:choose>
	                            	<c:when test="${obj.finance.teamtype eq map.key}">
			                            <option value="${map.key}" selected="selected">${map.value}</option>
	                            	</c:when>
	                            	<c:otherwise>
								   		<option value="${map.key}">${map.value}</option>
	                            	</c:otherwise>
	                            </c:choose>
							 </c:forEach> --%>
                        </select>
                     </td>
                     <td><label>内陆跨海：</label></td>
                     <td>
                        <select id="neilu" name="neilu" class="form-control input-sm disab" disabled="disabled">
                            <option value="">请选择</option>
                            <%-- <c:forEach items="${obj.nlkhcode }" var="one"> 
	                        	<c:choose>
	                        		<c:when test="${obj.finance.neilu eq one.id }">
					                     <option value="${one.id }" selected="selected">${one.dictName }</option>
	                        		</c:when>
	                        		<c:otherwise>
					                     <option value="${one.id }">${one.dictName }</option>
	                        		</c:otherwise>
	                        	</c:choose>
	                     	</c:forEach> --%>
                        </select>
                     </td>
                     <td><label>开票日期：</label></td>
                     <td><input id="billingdate" name="billingdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" class="form-control input-sm" value="" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>付款币种：</label></td>
                     <td>
                        <select id="paycurrency" name="paycurrency" class="form-control input-sm">
                            <option>请选择</option>
                            <%-- <c:forEach items="${obj.bzcode }" var="one"> 
                            	<c:choose>
                            		<c:when test="${obj.finance.paycurrency eq one.id }">
					                     <option value="${one.id }" selected="selected">${one.dictCode }</option>
                            		</c:when>
                            		<c:otherwise>
					                     <option value="${one.id }">${one.dictCode }</option>
                            		</c:otherwise>
                            	</c:choose>
	                     	</c:forEach> --%>
                        </select>
                     </td>
                     <td><label>付款方式：</label></td>
                     <td>
                        <select id="paymethod" name="paymethod" class="form-control input-sm">
                            <option>请选择</option>
                            <%-- <c:forEach var="map" items="${obj.paymethod}" >
                            	<c:choose>
                            		<c:when test="${obj.finance.paymethod eq map.id }">
                            			<option value="${map.id}" selected="selected">${map.bankName}</option>
                            		</c:when>
                            		<c:otherwise>
								   		<option value="${map.id}">${map.bankName}</option>
                            		</c:otherwise>
                            	</c:choose>
							 </c:forEach> --%>
                        </select>
                     </td>
                     <td><label>销售：</label></td>
                     <td><input id="salesperson" name="salesperson" value="候小凌" type="text" class="form-control input-sm" disabled="disabled"></td>
                     <td><label>开票人：</label></td>
                     <td><input id="issuer" name="issuer" type="text" value="" class="form-control input-sm" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>人头数：</label></td>
                     <td><input id="personcount" name="personcount" value="" type="text" class="form-control input-sm disab mustNumber" disabled="disabled"></td>
                     <td><label>是否结算：</label></td>
                     <td>
                        <select id="billingstatus" name="billingstatus" class="form-control input-sm disab" disabled="disabled">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                     </td>
                     <td><label>进澳时间：</label></td>
                     <td><input id="enterausdate" name="enterausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label>出澳时间：</label></td>
                     <td><input id="outausdate" name="outausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.outausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>应收：</label></td>
                     <td><input id="receivable" name="receivable" type="text" class="form-control input-sm disab mustNumberPoint" value="${obj.finance.receivable }" disabled="disabled"></td>
                     <td><label><a href="javascript:;" class="jianMian">减免</a>：</label></td>
                     <td><input id="relief" name="relief" type="text" class="form-control input-sm" disabled="disabled" value="${obj.finance.relief }"></td>
                     <td><label>实收合计：</label></td>
                     <td><input id="incometotal" name="incometotal" type="text" class="form-control input-sm disab loadprofit mustNumberPoint" disabled="disabled" value="${obj.finance.incometotal }"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>成本合计：</label></td>
                     <td><input id="costtotal" name="costtotal" type="text" class="form-control input-sm disab loadprofit mustNumberPoint" disabled="disabled" value="${obj.finance.costtotal }"></td>
                     <td><label>应返：</label></td>
                     <td><input id="returntotal" name="returntotal" type="text" class="form-control input-sm disab loadprofit mustNumberPoint" disabled="disabled" value="${obj.finance.returntotal }"></td>
                     <td><label>利润合计：</label></td>
                     <td><input id="profittotal" name="profittotal" type="text" class="form-control input-sm disab mustNumberPoint" disabled="disabled" value="${obj.finance.profittotal }"></td>
                   </tr>
                 </table>
            </div>
            </form>
          </div><!--end 信息-->
          
        </div>
    </section>
  </div>
  <!--end right Content-->

  <!--footer-->
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
    </div>
    <strong>版权 &copy; 2016 <a href="#">聚优国际旅行社（北京）有限公司</a>.</strong> 保留所有权利.
  </footer>
  <!--end footer-->
  <script type="text/javascript">
  	var BASE_PATH = '${base}';
  	var creditLine = '${obj.custominfo.creditLine}';
	var arrears = '${obj.custominfo.arrears}';
  </script>
  <!--Javascript Flie-->
  <script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
  <script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
  <script src="${base }/public/plugins/fastclick/fastclick.js"></script>
  <!--layer -->
  <script src="${base}/common/js/layer/layer.js"></script>
  	<!-- select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
  <!-- DataTables -->
  <script src="${base }/public/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="${base }/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
  <script src="${base }/admin/order/ordercommon.js"></script>
  <script src="${base }/admin/international/internationaldetail.js"></script>
</body>
</html>
