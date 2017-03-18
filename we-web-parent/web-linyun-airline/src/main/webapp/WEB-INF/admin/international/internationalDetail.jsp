<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
                   <span>${obj.orderinfo.ordersnum }</span>
                   <select id="orderType" name="orderType" class="form-control input-sm conSelect cf">
                   	 <c:forEach var="map" items="${obj.internationalstatusenum}" >
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
					</c:forEach>
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
                     <td><label>客户姓名：</label></td>
                     <td><input id="linkName" name="linkName" disabled="disabled" type="text" class="form-control input-sm" value="${obj.custominfo.linkMan }">
                     	<input id="customerId" name="customerId" type="hidden" value="${obj.custominfo.id }"/>
                     	<!-- 订单id -->
                     	<input id="orderedid" name="orderedid" type="hidden" value="${obj.orderinfo.id }"></td>
                     <td><label style="position: relative;top: 4px;">结算方式：</label></td>
                     <td colspan="3"><pre class="preTxt">不限 信用额度：0  临时额度：0  历史欠款：0  预存款：0</pre></td>
                     <td><i class="UnderIcon fa fa-chevron-circle-down"></i></td>
                   </tr>
                 </table>

                 <table class="hideTable none">
                   <tr>
                     <td><label>公司简称：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.custominfo.shortName }" readonly="true"></td>
                     <td><label>电话：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.custominfo.telephone }" readonly="true"></td>
                     <td><label>地址：</label></td>
                     <td colspan="3"><input type="text" class="form-control input-sm addressInput" placeholder="" value="${obj.custominfo.address }" readonly="true"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>负责人：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.responsible }" readonly="true"></td>
                     <td><label>网址：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.custominfo.siteUrl }" readonly="true"></td>
                     <td><label>传真：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="" value="${obj.custominfo.fax }" readonly="true"></td>
                     <td><label>出发城市：</label></td>
                     <td><input type="text" class="form-control input-sm addressInput" placeholder="" value="${obj.custominfo.id }" readonly="true"></td>
                     
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
                     <td><select id="aircom" name="aircom" class="form-control input-sm disab" multiple="multiple"></select></td>
                     <td><label>人数：</label></td>
                     <td><input id="peoplecount" name="peoplecount" type="text" class="form-control input-sm disab mustNumber"></td>
                     <td><label>成本单价：</label></td>
                     <td><input id="costprice" name="costprice" type="text" class="form-control input-sm disab mustNumberPoint"></td>
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
            		<input id="id" name="id" type="hidden" value="${obj.finance.id }" >
            		<input id="orderid" name="orderid" type="hidden" value="${obj.orderinfo.id }"/>
                 <table class="">
                   <tr>
                     <td><label>客户团号：</label></td>
                     <td><input id="cusgroupnum" name="cusgroupnum" type="text" class="form-control input-sm disab" disabled="disabled" value="${obj.finance.cusgroupnum }"></td>
                     <td><label>类型：</label></td>
                     <td>
                        <select id="teamtype" name="teamtype" class="form-control input-sm disab" disabled="disabled">
                            <option value="">请选择</option>
                            <c:forEach var="map" items="${obj.passengertypeenum }" >
                            	<c:choose>
	                            	<c:when test="${obj.finance.teamtype eq map.key}">
			                            <option value="${map.key}" selected="selected">${map.value}</option>
	                            	</c:when>
	                            	<c:otherwise>
								   		<option value="${map.key}">${map.value}</option>
	                            	</c:otherwise>
	                            </c:choose>
							 </c:forEach>
                        </select>
                     </td>
                     <td><label>内陆跨海：</label></td>
                     <td>
                        <select id="neilu" name="neilu" class="form-control input-sm disab" disabled="disabled">
                            <option value="">请选择</option>
                            <c:forEach items="${obj.nlkhcode }" var="one"> 
	                        	<c:choose>
	                        		<c:when test="${obj.finance.neilu eq one.id }">
					                     <option value="${one.id }" selected="selected">${one.dictName }</option>
	                        		</c:when>
	                        		<c:otherwise>
					                     <option value="${one.id }">${one.dictName }</option>
	                        		</c:otherwise>
	                        	</c:choose>
	                     	</c:forEach>
                        </select>
                     </td>
                     <td><label>开票日期：</label></td>
                     <td><input id="billingdate" name="billingdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" class="form-control input-sm" value="<fmt:formatDate value="${obj.finance.billingdate }" pattern="yyyy-MM-dd" />" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>付款币种：</label></td>
                     <td>
                        <select id="paycurrency" name="paycurrency" class="form-control input-sm">
                            <option>请选择</option>
                            <c:forEach items="${obj.bzcode }" var="one"> 
			                     <option value="${one.dictCode }">${one.dictCode }</option>
	                     	</c:forEach>
                        </select>
                     </td>
                     <td><label>付款方式：</label></td>
                     <td>
                        <select class="form-control input-sm">
                            <option>请选择</option>
                            <c:forEach var="map" items="${obj.paymethodenum}" >
						   		<option value="${map.key}">${map.value}</option>
							 </c:forEach>
                        </select>
                     </td>
                     <td><label>销售：</label></td>
                     <td><input id="salesperson" name="salesperson" value="候小凌" type="text" class="form-control input-sm" disabled="disabled"></td>
                     <td><label>开票人：</label></td>
                     <td><input id="issuer" name="issuer" type="text" value="${empty obj.finance.issuer?obj.user.userName:obj.finance.issuer }" class="form-control input-sm" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>人头数：</label></td>
                     <td><input id="personcount" name="personcount" value="${obj.finance.personcount }" type="text" class="form-control input-sm disab mustNumber" disabled="disabled"></td>
                     <td><label>是否结算：</label></td>
                     <td>
                        <select id="billingstatus" name="billingstatus" class="form-control input-sm disab" disabled="disabled">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                     </td>
                     <td><label>进澳时间：</label></td>
                     <td><input id="enterausdate" name="enterausdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.finance.enterausdate }" pattern="yyyy-MM-dd" />" type="text" class="form-control input-sm disab" disabled="disabled"></td>
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
  <script type="text/javascript">
    $(function(){
        //编辑按钮 click事件
        $('.editBtn').click(function(){
          $(this).addClass('none');
          $('.btnSave').toggle();//保存按钮 显示
          $('.btnCancel').toggle();//取消按钮 显示
          $(".btnRemind").toggle();//提醒设置按钮 显示
          $(".btnLog").toggle();//日志按钮 显示
          $(".listInfo").toggle();//选项卡 显示
          $('.disab').removeAttr("disabled");//信息模块 input 禁止编辑的状态
        });
        //取消按钮 click事件
        $('.btnCancel').click(function(){
          $('.editBtn').removeClass('none');//显示 编辑 按钮
          $('.btnSave').toggle();//保存 按钮 隐藏
          $('.btnCancel').toggle();//取消 按钮 隐藏
          $('.btnRemind').toggle();//提醒设置 按钮 隐藏
          $('.btnLog').toggle();//日志 按钮 隐藏
          $(".listInfo").toggle();//选项卡 隐藏
          $('.disab').attr("disabled",'disabled');//信息模块 input 添加 不可编辑属性
        });

        $('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
          $('.hideTable').toggle(); 
        });

        //点击 提醒设置 弹框
        $('.btnRemind').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['400px', '350px'],
                content: '${base}/admin/international/orderRemind.html?orderid=${obj.orderinfo.id }'
              });
        });

        //点击 日志 弹框
        $('.btnLog').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['800px', '550px'],
                content: '${base}/admin/international/orderLog.html?orderid=${obj.orderinfo.id }'
              });
        });

        //点击 添加航段 弹框
        $('.addHD').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['1100px', '550px'],
                content: '${base}/admin/international/addAirinfo.html?orderid=${obj.orderinfo.id }'
              });
        });
        //点击 添加记录-预收款 弹框
        $('.addYSK').click(function(){
        	var orderType = $('#orderType').val();
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['1000px', '450px'],
                content: '${base}/admin/international/addReceiveRecord.html?orderid=${obj.orderinfo.id }&payreceivestatus=${obj.receivestatus}&ordersstatus='+orderType
              });
        });

        //点击 添加记录-预付款 弹框
        $('.addYFK').click(function(){
        	var orderType = $('#orderType').val();
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['1000px', '450px'],
                content: '${base}/admin/international/addPayRecord.html?orderid=${obj.orderinfo.id }&payreceivestatus=${obj.paystatus}&ordersstatus='+orderType
              });
        });
        $('.recordParent p:eq(0)').click(function(){//预收款记录 切换tab
          $(this).addClass('recStyle').siblings().removeClass('recStyle');
          $('.recordDiu:eq(0)').show();
          $('.recordDiu:eq(1)').hide();
        });

        $('.recordParent p:eq(1)').click(function(){//预付款记录 切换tab
          $(this).addClass('recStyle').siblings().removeClass('recStyle');
          $('.recordDiu:eq(1)').show();
          $('.recordDiu:eq(0)').hide();
        });
    });
    //加载航段信息
    loadAirlineInfo();
    function loadAirlineInfo(){
    	$.ajax({
			type: 'POST', 
			data: {orderid:'${obj.orderinfo.id}'}, 
			url: '${base}/admin/international/loadAirlineInfo.html',
			async:false,
			dataType:'json',
            success: function (data) { 
            	var mainhtml = '';
            	var zihtml = '';
            	for(var i = 0 ;i < data.length ; i++){
            		if(data[i].pnrinfo.mainsection === 1){
            			mainhtml += '<tr><td>'+data[i].pnrinfo.pNR+'</td>';
            			mainhtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				mainhtml += '<li>'+value.leavecity+'/'+value.arrvicity+'</li>';
                		});
            			mainhtml += '</ul></td>'; 
            			mainhtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				mainhtml += '<li>'+value.ailinenum+'</li>';
                		});
            			mainhtml += '</ul></td>'; 
            			mainhtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				mainhtml += '<li>'+value.leavedate+'</li>';
                		});
            			mainhtml += '</ul></td>'; 
            			mainhtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				mainhtml += '<li>'+value.leavetime+'/'+value.arrivetime+'</li>';
                		});
            			mainhtml += '</ul></td>'; 
            			mainhtml += '<td>'+data[i].pnrinfo.peoplecount+'</td>';
            			mainhtml += '<td><a href="javascript:editAirlineInfo('+data[i].pnrinfo.id+');">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;';
            			mainhtml += '<a href="javascript:visitorInfo('+data[i].pnrinfo.id+');" class="YKinfo">游客信息</a>';
            			mainhtml += '<a href="javascript:;" class="FileDiv">上传游客<input type="file" class="uploadVisitors"><input type="hidden" id="pnrid" name="pnrid" value="'+data[i].pnrinfo.id+'"></a></td>';
            		}else{
            			zihtml += '<tr><td>'+data[i].pnrinfo.pNR+'</td>';
            			zihtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				zihtml += '<li>'+value.leavecity+'/'+value.arrvicity+'</li>';
                		});
            			zihtml += '</ul></td>'; 
            			zihtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				zihtml += '<li>'+value.ailinenum+'</li>';
                		});
            			zihtml += '</ul></td>'; 
            			zihtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				zihtml += '<li>'+value.leavedate+'</li>';
                		});
            			zihtml += '</ul></td>'; 
            			zihtml += '<td><ul>';
            			$.each(data[i].airinfo, function(name, value) {
               				zihtml += '<li>'+value.leavetime+'/'+value.arrivetime+'</li>';
                		});
            			zihtml += '</ul></td>'; 
            			zihtml += '<td>'+data[i].pnrinfo.peoplecount+'</td>';
            			zihtml += '<td><a href="javascript:editAirlineInfo('+data[i].pnrinfo.id+');">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;';
            			zihtml += '<a href="javascript:visitorInfo('+data[i].pnrinfo.id+');" class="YKinfo">游客信息</a>';
            			zihtml += '<a href="javascript:;" class="FileDiv">上传游客<input type="file" class="uploadVisitors"><input type="hidden" id="pnrid" name="pnrid" value="'+data[i].pnrinfo.id+'"></a></td>';
            		}
            	}
            	$('#mainsection').html(mainhtml);
            	$('#zisection').html(zihtml);
            },
            error: function (xhr) {
            } 
      });
    }
    //加载收付款记录信息
    loadpayAndReceiveRecord();
    function loadpayAndReceiveRecord(){
    	$.ajax({
			type: 'POST', 
			data: {orderid:'${obj.orderinfo.id}'}, 
			url: '${base}/admin/international/loadPayReceiveRecord.html',
			async:false,
			dataType:'json',
            success: function (data) { 
            	var receivestatus = data.receivestatus;
            	var paystatus = data.paystatus;
            	var receivehtml = '';
            	var payhtml = '';
            	$.each(data.record, function(name, value) {
            		if(value.recordtype === receivestatus){
            			receivehtml += '<tr>';
            			receivehtml +='<td>'+value.orderstatus+'</td>';
                        receivehtml += '<td>'+value.prepayratio+'</td>';
                        receivehtml += '<td>'+value.freenumber+'</td>';
                        receivehtml += '<td>'+value.actualnumber+'</td>';
                        receivehtml += '<td>'+value.currentfine+'</td>';
                        receivehtml += '<td>'+value.currentdue+'</td>';
                        receivehtml += '<td>'+value.ataxprice+'</td>';
                        receivehtml += '<td>'+value.currentpay+'</td>';
                        receivehtml += '<td>';
                        receivehtml += '<a href="javascript:editRecord('+value.id+','+value.recordtype+');">编辑</a>';
                        receivehtml += '</td>';
                        receivehtml += '</tr>';
            		}else{
            			payhtml += '<tr>';
            			payhtml +='<td>'+value.orderstatus+'</td>';
                        payhtml += '<td>'+value.prepayratio+'</td>';
                        payhtml += '<td>'+value.freenumber+'</td>';
                        payhtml += '<td>'+value.actualnumber+'</td>';
                        payhtml += '<td>'+value.currentfine+'</td>';
                        payhtml += '<td>'+value.currentdue+'</td>';
                        payhtml += '<td>'+value.ataxprice+'</td>';
                        payhtml += '<td>'+value.currentpay+'</td>';
                        payhtml += '<td>';
                        payhtml += '<a href="javascript:editRecord('+value.id+','+value.recordtype+');">编辑</a>';
                        payhtml += '</td>';
                        payhtml += '</tr>';
            		}
        		});
            	$('#receiverecord').html(receivehtml);
            	$('#payrecord').html(payhtml);
            },
            error: function (xhr) {
            } 
      });
    }
    function successCallback(id){
	  loadAirlineInfo();
	  loadpayAndReceiveRecord();
	  if(id == '1'){
		  layer.alert("添加成功",{time: 2000, icon:1});
	  }else if(id == '2'){
		  layer.alert("修改成功",{time: 2000, icon:1});
	  }
	}
    //编辑航段
    function editAirlineInfo(pnrid){
    	layer.open({
            type: 2,
            title:false,
            skin: false, //加上边框
            closeBtn:false,//默认 右上角关闭按钮 是否显示
            shadeClose:true,
            area: ['1100px', '550px'],
            content: '${base}/admin/international/editAirinfo.html?pnrid='+pnrid
          });
    }
    //点击 游客信息 弹框
    function visitorInfo(pnrid){
        layer.open({
            type: 2,
            title:false,
            skin: false, //加上边框
            closeBtn:false,//默认 右上角关闭按钮 是否显示
            shadeClose:true,
            area: ['900px', '550px'],
            content: '${base}/admin/international/visitorInfo.html?pnrid='+pnrid
          });
    }
    //编辑预收款、付款
    function editRecord(id,type){
    	var url = '';
    	if(type == '${obj.receivestatus}'){
    		url = '${base}/admin/international/editReceiveRecord.html?recordid='+id
    	}else{
    		url = '${base}/admin/international/editPayRecord.html?recordid='+id
    	}
       layer.open({
           type: 2,
           title:false,
           skin: false, //加上边框
           closeBtn:false,//默认 右上角关闭按钮 是否显示
           shadeClose:true,
           area: ['900px', '200px'],
           content: url
         });
    }
  </script>
</body>
</html>