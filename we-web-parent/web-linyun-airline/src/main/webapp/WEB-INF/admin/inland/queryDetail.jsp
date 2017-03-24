<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>查询订单</title>
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
  <link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <!-- 图标 -->
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base }/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base }/public/dist/css/skins/_all-skins.min.css">
  <link rel="stylesheet" href="${base }/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base }/public/dist/css/query.css">
  <link rel="stylesheet" href="${base }/public/dist/css/queryOrderDetail.css"><!--本页面styleFlie-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!--Header -->
  <header class="main-header">

    <!-- Logo -->
    <a href="#" class="logo">
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
        <div class="row col-sm-10">
          <div class="customerInfo"><!--客户信息-->
               <div class="infoTop">
                 <p>客户信息</p>
                 <div class="infoTopContent">
                   <span>${obj.orderinfo.ordersnum }</span>
                   <select id="orderType" name="orderType" class="form-control input-sm conSelect cf" disabled="disabled">
                     <c:forEach var="map" items="${obj.orderstatusenum}" >
				   		<c:choose>
                   			<c:when test="${obj.orderinfo.ordersstatus eq map.key }">
                   				<option value="${map.key}" selected="selected">${map.value}</option>
                   			</c:when>
                   			<c:otherwise>
					   			<option value="${map.key}">${map.value}</option>
                   			</c:otherwise>
                   		</c:choose>
					 </c:forEach>
                   </select>
                   <button type="button" class="btn btn-primary input-sm btnSave none">保存</button>
                   <button type="button" class="btn btn-primary input-sm btnCancel none">取消</button>
                   <button type="button" class="btn btn-primary input-sm editBtn">编辑</button>
                 </div>
               </div>
               <div class="infofooter">
                 <table>
                   <tr>
                     <td><label>客户姓名：</label></td>
                     <td><input type="text" id="linkName" name="linkName" class="form-control input-sm" value="${obj.custominfo.linkMan }" disabled="disabled">
                     	<input id="customerId" name="customerId" type="hidden" value="${obj.custominfo.id }"/>
                     	<input id="id" name="id" type="hidden" value="${obj.orderinfo.id }">
                     </td>
                     <td><label style="position: relative;top: 4px;">结算方式：</label></td>
                     <td colspan="3"><pre class="preTxt">不限 信用额度：0  临时额度：0  历史欠款：0  预存款：0</pre></td>
                     <td><i class="UnderIcon fa fa-chevron-circle-down"></i></td>
                   </tr>
                 </table>

                 <table class="hideTable none">
                   <tr>
                     <td><label>公司简称：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入公司简称" value="${obj.custominfo.shortName }" readonly="true"/></td>
                     <td><label>电话：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入电话" value="${obj.custominfo.telephone }" readonly="true"/></td>
                     <td><label>地址：</label></td>
                     <td colspan="3"><input type="text" class="form-control input-sm addressInput" placeholder="请输入地址" value="${obj.custominfo.address }" readonly="true"/></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>负责人：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入负责人" value="${obj.responsible }" readonly="true"/></td>
                     <td><label>网址：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入网址" value="${obj.custominfo.siteUrl }" readonly="true"/></td>
                     <td><label>传真：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入传真" value="${obj.custominfo.fax }" readonly="true"/></td>
                     <td><label>出发城市：</label></td>
                     <td><input type="text" class="form-control input-sm addressInput" placeholder="请输入出发城市" value="${obj.custominfo.id }" readonly="true"/></td>
                     
                   </tr>
                 </table>

               </div>
          </div><!--客户信息-->


          <div class="customerInfo"><!--客户需求-->
               <div class="infoTop">
                 <p>客户需求</p>
               </div>
               <c:choose>
               		<c:when test="${fn:length(obj.customneedinfo)>0}">
		               <c:forEach var="customneed" items="${obj.customneedinfo }">
			               <div id="infofooter" name="infofooter" class="infofooter">
			                <div class="DemandDiv">
			                 <span class="titleNum">1</span>
			                 <a href="javascript:;" class="btn btn-primary btn-sm addDemand none"><b>+</b>&nbsp;&nbsp;需求</a>
			                 <!-- 客户需求隐藏域 -->
			                 <input type="hidden" id="customneedid" name="customneedid" value="${customneed.cusinfo.id }">
			                 <table class="cloTable">
			                   <tr>
			                     <td><label>出发城市：</label></td>
			                     <td colspan="2"><select id="leavecity" name="leavecity" disabled="false" class="form-control input-sm select2" multiple="multiple" placeholder="PEK(北京)">
			                     	<c:forEach var="one" items="${obj.city }">
			                    		<c:choose>
			                    			<c:when test="${customneed.cusinfo.leavecity eq one.dictCode }">
												<option value="${one.dictCode }" selected="selected">${one.dictCode}-${one.englishName }-${one.countryName }</option>
			                    			</c:when>
			                    			<c:otherwise>
												<option value="${one.dictCode }">${one.dictCode}-${one.englishName }-${one.countryName }</option>
			                    			</c:otherwise>
			                    		</c:choose>
									</c:forEach>
			                     	</select>
			                     </td>
			                     <td><label>抵达城市：</label></td>
			                     <td colspan="2"><select id="arrivecity" name="arrivecity" disabled="disabled" class="form-control input-sm" multiple="multiple" placeholder="SYD(悉尼)">
			                     	<c:forEach var="one" items="${obj.city }">
			                    		<c:choose>
			                    			<c:when test="${customneed.cusinfo.arrivecity eq one.dictCode }">
												<option value="${one.dictCode }" selected="selected">${one.dictCode}-${one.englishName }-${one.countryName }</option>
			                    			</c:when>
			                    			<c:otherwise>
												<option value="${one.dictCode }">${one.dictCode}-${one.englishName }-${one.countryName }</option>
			                    			</c:otherwise>
			                    		</c:choose>
									</c:forEach>
			                     </select></td>
			                     <td><label>出发日期：</label></td>
			                     <td><input id="leavedate" name="leavedate" disabled="disabled" type="text" class="form-control input-sm textWid" placeholder="2017-02-22" onFocus="WdatePicker({minDate:'${customneed.cusinfo.leavetdate }'})" value="<fmt:formatDate value="${customneed.cusinfo.leavetdate }" pattern="yyyy-MM-dd" />"/></td>
			                     <td><label>人数：</label></td>
			                     <td><input id="peoplecount" name="peoplecount" disabled="disabled" type="text" class="form-control input-sm textWid mustNumber" value="${customneed.cusinfo.peoplecount }"/></td>
			                     <td><label class="labelWid">早中晚：</label></td>
			                     <td>
			                       <select id="tickettype" name="tickettype" disabled="disabled" class="form-control input-sm textWid" value="${customneed.cusinfo.tickettype }">
			                       	 <c:choose>
			                       	 	<c:when test="${customneed.cusinfo.tickettype eq 1 }">
					                         <option value="1" selected="selected">早</option>
			                       	 	</c:when>
			                       	 	<c:otherwise>
				                         <option value="1">早</option>
			                       	 	</c:otherwise>
			                       	 </c:choose>
			                       	 <c:choose>
			                       	 	<c:when test="${customneed.cusinfo.tickettype eq 2 }">
					                         <option value="2" selected="selected">中</option>
			                       	 	</c:when>
			                       	 	<c:otherwise>
				                         	<option value="2">中</option>
			                       	 	</c:otherwise>
			                       	 </c:choose>
			                       	 <c:choose>
			                       	 	<c:when test="${customneed.cusinfo.tickettype eq 3 }">
					                         <option value="3" selected="selected">晚</option>
			                       	 	</c:when>
			                       	 	<c:otherwise>
				                         	<option value="3">晚</option>
			                       	 	</c:otherwise>
			                       	 </c:choose>
			                       </select>
			                     </td>
			                   </tr>
			                   <c:choose>
			                   		<c:when test="${fn:length(customneed.ailines)>0}">
					                   <c:forEach var="airline" items="${customneed.ailines }" varStatus="status">
					                   
					                   <tr name="airlineinfo">
					                     <td></span><label>航空公司：</label></td>
					                     <td><select id="aircom" name="aircom" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="">
					                     	   <c:forEach items="${obj.aircom }" var="one"> 
						                   			<c:choose>
							                   			<c:when test="${airline.aircom  eq one.dictCode  }">
															<option value="${one.dictCode }" selected="selected">${one.dictCode }-${one.dictName }</option>
							                   			</c:when>
							                   			<c:otherwise>
								                     		<option value="${one.dictCode }">${one.dictCode }-${one.dictName }</option>
							                   			</c:otherwise>
						                    		</c:choose>
						                     	</c:forEach>
					                     	</select>
					                     	<!-- 航班信息隐藏域 -->
					                     	<input type="hidden"  id="airlineid" name="airlineid" value="${airline.id }">
					                     	</td>
					                     <td><label>航班号：</label></td>
					                     <td><select id="ailinenum" name="ailinenum" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="SYD(悉尼)">
					                     	<c:forEach items="${obj.airline }" var="one"> 
						                   			<c:choose>
							                   			<c:when test="${airline.ailinenum  eq one.airlinenum  }">
															<option value="${one.airlinenum }" selected="selected">${one.airlinenum }</option>
							                   			</c:when>
							                   			<c:otherwise>
								                     		<option value="${one.airlinenum }">${one.airlinenum }</option>
							                   			</c:otherwise>
						                    		</c:choose>
						                     	</c:forEach>
					                     	</select></td>
					                     <td><label>出发时间：</label></td>
					                     <td><input id="leavetime" name="leavetime" disabled="disabled" type="text" class="form-control input-sm textWid mustTimes" placeholder="" value="${airline.leavetime }"/></td>
					                     <td><label>抵达时间：</label></td>
					                     <td><input id="arrivetime" name="arrivetime" disabled="disabled" type="text" class="form-control input-sm textWid mustArriveTimes" value="${airline.arrivetime }"/></td>
					                     <td><label class="labelWid">成本价：</label></td>
					                     <td><input id="formprice" name="formprice" disabled="disabled" type="text" class="form-control input-sm textWid costPrice" value="${airline.formprice }"/></td>
					                     <td><label class="labelWid">销售价：</label></td>
					                     <td><input id="price" name="price" type="text" disabled="disabled" class="form-control input-sm textWid" value="${airline.price }"/></td>
					                     <c:choose>
					                     	<c:when test="${status.index eq 0 }">
							                     <td class="tdBtn">
							                      <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addIcon removAddMake none"></a>
							                     </td>
					                     	</c:when>
					                     	<c:otherwise>
												<td class="removeIconTd"><i class="glyphicon glyphicon-minus removIcon none"></i></td>			                     	
					                     	</c:otherwise>
					                     </c:choose>
					                   </tr>
					                   </c:forEach>
			                   		</c:when>
			                   		<c:otherwise>
					                   <tr name="airlineinfo">
					                     <td></span><label>航空公司：</label></td>
					                     <td><select id="aircom" name="aircom" class="form-control input-sm"  multiple="multiple" placeholder="" ></select></td>
					                     <td><label>航班号：</label></td>
					                     <td><select id="ailinenum" name="ailinenum" class="form-control input-sm"  multiple="multiple" placeholder="SYD(悉尼)"></select></td>
					                     <td><label>出发时间：</label></td>
					                     <td><input id="leavetime" name="leavetime" type="text" class="form-control input-sm textWid mustTimes" placeholder=""/></td>
					                     <td><label>抵达时间：</label></td>
					                     <td><input id="arrivetime" name="arrivetime" type="text" class="form-control input-sm textWid mustArriveTimes" /></td>
					                     <td><label class="labelWid">销售价：</label></td>
					                     <td><input id="formprice" name="formprice" type="text" class="form-control input-sm textWid" /></td>
					                     <td><label class="labelWid">成本价：</label></td>
					                     <td><input id="price" name="price" type="text" class="form-control input-sm textWid"/></td>
					                     <td class="tdBtn">
					                      <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addIcon removAddMake none"></a>
					                     </td>
					                   </tr>
			                   		</c:otherwise>
			                   </c:choose>
			                   <tr>
			                     <td></span><label>备注：</label></td>
			                     <td colspan="11"><input id="remark" name="remark" disabled="disabled" type="text" class="form-control input-sm noteText" placeholder="" value="${customneed.cusinfo.remark }"/></td>
			                   </tr>
			                 </table>
			                </div>
			               </div>
		               </c:forEach>
		             </c:when>
		             <c:otherwise>
		             	<div id="infofooter" class="infofooter">
		                <div class="DemandDiv">
		                 <span class="titleNum">1</span>
		                 <a href="javascript:;" class="btn btn-primary btn-sm addDemand none addXuQiu"><b>+</b>&nbsp;&nbsp;需求</a>
		                 <input type="hidden" id="customneedid" name="customneedid">
		                 <table class="cloTable">
		                   <tr>
		                     <td><label>出发城市：</label></td>
		                     <td colspan="2"><select id="leavecity" name="leavecity" disabled="disabled" class="form-control input-sm select2" multiple="multiple" placeholder="PEK(北京)">
			                     </select>
			                 </td>
		                     <td><label>抵达城市：</label></td>
		                     <td colspan="2"><select id="arrivecity" name="arrivecity" disabled="disabled" class="form-control input-sm" multiple="multiple" placeholder="SYD(悉尼)">
			                     </select></td>
		                     <td><label>出发日期：</label></td>
		                     <td><input id="leavedate" name="leavedate" disabled="disabled" type="text" class="form-control input-sm textWid" placeholder="2017-02-22" onFocus="WdatePicker({minDate:''})"/></td>
		                     <td><label>人数：</label></td>
		                     <td><input id="peoplecount" name="peoplecount" disabled="disabled" type="text" class="form-control input-sm textWid mustNumber"/></td>
		                     <td><label class="labelWid">早中晚：</label></td>
		                     <td>
		                       <select id="tickettype" name="tickettype" disabled="disabled" class="form-control input-sm textWid">
			                         <option value="1">早</option>
			                         <option value="2">中</option>
			                         <option value="3">晚</option>
			                       </select>
		                     </td>
		                   </tr>
		                   <tr name="airlineinfo">
		                     <td></span><label>航空公司：</label></td>
		                     <td><select id="aircom" name="aircom" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="" ></select></td>
		                     <td><label>航班号：</label></td>
		                     <td><select id="ailinenum" name="ailinenum" disabled="disabled" class="form-control input-sm"  multiple="multiple" placeholder="SYD(悉尼)"></select></td>
		                     <td><label>出发时间：</label></td>
		                     <td><input id="leavetime" name="leavetime" disabled="disabled" type="text" class="form-control input-sm textWid mustTimes" placeholder=""/></td>
		                     <td><label>抵达时间：</label></td>
		                     <td><input id="arrivetime" name="arrivetime" disabled="disabled" type="text" class="form-control input-sm textWid mustArriveTimes" /></td>
		                     <td><label class="labelWid">成本价：</label></td>
		                     <td><input id="formprice" name="formprice" disabled="disabled" type="text" class="form-control input-sm textWid costPrice" /></td>
		                     <td><label class="labelWid">销售价：</label></td>
		                     <td><input id="price" name="price" type="text" disabled="disabled" class="form-control input-sm textWid xiaoShouCls"/></td>
		                     <td class="tdBtn">
		                      <a href="javascript:;" name="addButton" class="glyphicon glyphicon-plus addIcon removAddMake none"></a>
		                     </td>
		                   </tr>
		                   <tr>
		                     <td></span><label>备注：</label></td>
		                     <td colspan="11"><input type="text" id="remark" name="remark" class="form-control input-sm noteText" placeholder=" " value="${customneed.cusinfo.remark }"></td>
		                   </tr>
		                 </table>
		                </div>
		               </div>
		             </c:otherwise>
		          </c:choose>
          </div><!--end 客户需求-->


          <div class="listInfo none">
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">内陆跨海</a></li>
                      <!-- <li><a href="#tab_2" data-toggle="tab">国际</a></li>
                      <li><a href="#tab_3" data-toggle="tab">机票库</a></li> -->
                      <li><a href="#tab_4" data-toggle="tab">sabre</a></li>
                      <li><a href="#tab_5" data-toggle="tab">etem</a></li>
                      <li><a href="#tab_6" data-toggle="tab">CA</a></li>
                    </ul>
                    <div class="tab-content">
                      <div class="tab-pane active" id="tab_1">
                          <table class="scatteredTable1">
                              <tr>
                                <td><label>航程类型：</label></td>
                                <td><input type="radio" name="voyageType" value="1" onclick="radioFunct(this)"/><span>单程</span></td>
                                <td><input type="radio" name="voyageType" value="2" onclick="radioFunct()"/><span>往返</span></td>
                                <td><input type="radio" name="voyageType" value="3" onclick="radioFunct()"/><span>多程</span></td>
                                <td><input type="checkbox" class="checkClass"><span class="checkSpan"/>直飞</span></td>
                              </tr>
                          </table>
                          <table class="scatteredTable2">
                            <tr>
                              <td><label>出发城市：</label></td>
                              <td><input type="text" class="form-control input-sm" placeholder="拼音/三字代码"/></td>
                              <td class="untilTd"><i class="fa fa-minus"></i></td>
                              <td><label>到达城市：</label></td>
                              <td><input type="text" class="form-control input-sm" placeholder="拼音/三字代码"/></td>
                              <td class="untilTd1"></td><!--空白处 可以忽略-->
                              <td><label>出发日期：</label></td>
                              <td><input type="text" class="form-control input-sm" placeholder="2016-12-21"/></td>
                              <td class="setoutLabel"><label>返回日期：</label></td>
                              <td class="setoutinput"><input type="text" class="form-control input-sm" placeholder="2016-12-25"/></td>
                              <td class="addIconTd none"><i class="glyphicon glyphicon-plus addMore"></i></td>
                            </tr>
                            <tr class="hideTr none">
                              <td><label>出发城市：</label></td>
                              <td><input type="text" class="form-control input-sm" placeholder="拼音/三字代码"/></td>
                              <td class="untilTd"><i class="fa fa-minus"></i></td>
                              <td><label>到达城市：</label></td>
                              <td><input type="text" class="form-control input-sm" placeholder="拼音/三字代码"/></td>
                              <td class="untilTd1"></td><!--空白处 可以忽略-->
                              <td><label>出发日期：</label></td>
                              <td><input type="text" class="form-control input-sm" placeholder="2016-12-21"/></td>
                              <td class="setoutLabel"><label>返回日期：</label></td>
                              <td class="setoutinput"><input type="text" class="form-control input-sm" placeholder="2016-12-25"/></td>
                              <td class="addIconTd none"><i class="glyphicon glyphicon-plus addMore"></i></td>
                            </tr>
                          </table>
                          <table class="scatteredTable3">
                            <tr>
                              <td><label>乘客类型：</label></td>
                              <td>
                                <select class="form-control input-sm">
                                  <option>成人 0</option>
                                  <option>成人 1</option>
                                  <option>成人 2</option>
                                  <option>成人 3</option>
                                  <option>成人 4</option>
                                  <option>成人 5</option>
                                  <option>成人 6</option>
                                  <option>成人 7</option>
                                  <option>成人 8</option>
                                  <option>成人 9</option>
                                </select>
                              </td>
                              <td class="paddTd">
                                <select class="form-control input-sm">
                                  <option>儿童 0</option>
                                  <option>儿童 1</option>
                                  <option>儿童 2</option>
                                  <option>儿童 3</option>
                                  <option>儿童 4</option>
                                  <option>儿童 5</option>
                                  <option>儿童 6</option>
                                  <option>儿童 7</option>
                                  <option>儿童 8</option>
                                  <option>儿童 9</option>
                                </select>
                              </td>
                              <td class="paddTd">
                                <select class="form-control input-sm">
                                  <option>婴儿 0</option>
                                  <option>婴儿 1</option>
                                  <option>婴儿 2</option>
                                </select>
                              </td>
                              <td><label>舱位等级：</label></td>
                              <td>
                                 <select class="form-control input-sm selectWid">
                                   <option>经济舱</option>
                                   <option>超级经济舱</option>
                                   <option>商务舱</option>
                                   <option>头等舱</option>
                                 </select>
                              </td>
                              <td><label>航空公司：</label></td>
                              <td><input type="text" class="form-control input-sm" placeholder="(选填)中文/代码"/></td>
                              <td><button class="btn btn-primary btn-sm">搜索机票</button></td>
                            </tr>
                          </table>
                          <div class="TableListInfo">
                              <div class="paragraphNumber">
                                <ul class="paragraphBtn">
                                  <li class="btnStyle">第一段<p>PEK-SYD</p></li>
                                  <li>第二段<p>PEK-SYD</p></li>
                                  <li>第三段<p>PEK-SYD</p></li>
                                  <li>第四段<p>PEK-SYD</p></li>
                                </ul>
                                <ul class="paragraphBtn moneyP">
                                  <li class="btnStyle">12-21 周三<p>￥4999</p></li>
                                  <li>12-22 周四<p>￥4989</p></li>
                                  <li>12-23 周五<p>￥8999</p></li>
                                  <li>12-24 周六<p>￥6999</p></li>
                                  <li>12-25 周日<p>￥9999</p></li>
                                  <li>12-26 周一<p>￥5999</p></li>
                                  <li>12-27 周二<p>￥1999</p></li>
                                </ul>
                                <ul class="paragraphListInfo">
                                  <li>
                                    <div class="imgIconDiv"><p>首航CA1890</p></div>
                                    <div class="distanceTimeDiv">
                                        <span class="chufaCS"><b>06:30</b><p>首都T1</p></span>
                                        <span class="shiDuan">3h23m</span>
                                        <span class="daodaCS"><b>10:20</b><p>悉尼机场</p></span>
                                    </div>
                                    <div class="moneyDiv"><i class="fa fa-cny"></i>4986</div>
                                    <div class="btn-group xuanzeBtn">
                                        <button class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                                          选择
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                          <li><a href="javascript:;">1.北京 - 悉尼</a></li>
                                          <li><a href="javascript:;">2.北京 - 墨尔本</a></li>
                                          <li><a href="javascript:;">3.北京 - 布利斯</a></li>
                                        </ul>
                                    </div>
                                  </li>
                                  <li>
                                    <div class="imgIconDiv"><p>首航CA1890</p></div>
                                    <div class="distanceTimeDiv">
                                        <span class="chufaCS"><b>06:30</b><p>首都T1</p></span>
                                        <span class="shiDuan">3h23m</span>
                                        <span class="daodaCS"><b>10:20</b><p>悉尼机场</p></span>
                                    </div>
                                    <div class="moneyDiv"><i class="fa fa-cny"></i>4986</div>
                                    <div class="btn-group xuanzeBtn">
                                        <button class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                                          选择
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                          <li><a href="javascript:;">1.北京 - 悉尼</a></li>
                                          <li><a href="javascript:;">2.北京 - 墨尔本</a></li>
                                          <li><a href="javascript:;">3.北京 - 布利斯</a></li>
                                        </ul>
                                    </div>
                                  </li>
                                  <li>
                                    <div class="imgIconDiv"><p>首航CA1890</p></div>
                                    <div class="distanceTimeDiv">
                                        <span class="chufaCS"><b>06:30</b><p>首都T1</p></span>
                                        <span class="shiDuan">3h23m</span>
                                        <span class="daodaCS"><b>10:20</b><p>悉尼机场</p></span>
                                    </div>
                                    <div class="moneyDiv"><i class="fa fa-cny"></i>4986</div>
                                    <div class="btn-group xuanzeBtn">
                                        <button class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                                          选择
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                          <li><a href="javascript:;">1.北京 - 悉尼</a></li>
                                          <li><a href="javascript:;">2.北京 - 墨尔本</a></li>
                                          <li><a href="javascript:;">3.北京 - 布利斯</a></li>
                                        </ul>
                                    </div>
                                  </li>
                                  <li>
                                    <div class="imgIconDiv"><p>首航CA1890</p></div>
                                    <div class="distanceTimeDiv">
                                        <span class="chufaCS"><b>06:30</b><p>首都T1</p></span>
                                        <span class="shiDuan">3h23m</span>
                                        <span class="daodaCS"><b>10:20</b><p>悉尼机场</p></span>
                                    </div>
                                    <div class="moneyDiv"><i class="fa fa-cny"></i>4986</div>
                                    <div class="btn-group xuanzeBtn">
                                        <button class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                                          选择
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                          <li><a href="javascript:;">1.北京 - 悉尼</a></li>
                                          <li><a href="javascript:;">2.北京 - 墨尔本</a></li>
                                          <li><a href="javascript:;">3.北京 - 布利斯</a></li>
                                        </ul>
                                    </div>
                                  </li>
                                  <li>
                                    <div class="imgIconDiv"><p>首航CA1890</p></div>
                                    <div class="distanceTimeDiv">
                                        <span class="chufaCS"><b>06:30</b><p>首都T1</p></span>
                                        <span class="shiDuan">3h23m</span>
                                        <span class="daodaCS"><b>10:20</b><p>悉尼机场</p></span>
                                    </div>
                                    <div class="moneyDiv"><i class="fa fa-cny"></i>4986</div>
                                    <div class="btn-group xuanzeBtn">
                                        <button class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                                          选择
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                          <li><a href="javascript:;">1.北京 - 悉尼</a></li>
                                          <li><a href="javascript:;">2.北京 - 墨尔本</a></li>
                                          <li><a href="javascript:;">3.北京 - 布利斯</a></li>
                                        </ul>
                                    </div>
                                  </li>
                                  <li>
                                    <div class="imgIconDiv"><p>首航CA1890</p></div>
                                    <div class="distanceTimeDiv">
                                        <span class="chufaCS"><b>06:30</b><p>首都T1</p></span>
                                        <span class="shiDuan">3h23m</span>
                                        <span class="daodaCS"><b>10:20</b><p>悉尼机场</p></span>
                                    </div>
                                    <div class="moneyDiv"><i class="fa fa-cny"></i>4986</div>
                                    <div class="btn-group xuanzeBtn">
                                        <button class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">
                                          选择
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                          <li><a href="javascript:;">1.北京 - 悉尼</a></li>
                                          <li><a href="javascript:;">2.北京 - 墨尔本</a></li>
                                          <li><a href="javascript:;">3.北京 - 布利斯</a></li>
                                        </ul>
                                    </div>
                                  </li>
                                </ul>
                              </div>
                          </div>
                      </div>
                      <div class="tab-pane sabre" id="tab_4">
                         <textarea class="form-control sabreTextatea"></textarea>
                         <button type="button" class="btn btn-primary input-sm parsingBtn">解析</button>
                         <table id="sabreTable" class="table table-bordered table-hover">
                            <thead>
                              <tr>
                                <th>序号</th>
                                <th>航空公司</th>
                                <th>航班号</th>
                                <th>航段</th>
                                <th>起飞时间</th>
                                <th>航程时间</th>
                                <th>预订时间</th>
                                <th>座位数</th>
                                <th>价格</th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr>
                                <td>1</td>
                                <td>凌云</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>￥6999</td>
                              </tr>
                            </tbody>
                            </table>
                      </div>
                      <div class="tab-pane" id="tab_5">
                          etem
                      </div>
                      <div class="tab-pane" id="tab_6">
                          <iframe class="ifea" src="http://www.jdair.net/" frameborder="0" scrolling="no" ></iframe>
                      </div>
                    </div>
                  </div>
          </div><!--购票查询 列表-->
        </div>
        <div class="col-sm-2 rightRemind">
            <div class="infoTop">
              <p>提醒设置</p>
            </div>
            <div class="infofooter">
                 <table class="remindSet">
                   <tr>
                     <td><input id="remindTime" type="text" class="form-control input-sm" disabled="disabled" placeholder="2020-01-01 00:00:00" onfocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
                     <td>
                       <select id="remindType" disabled="disabled" class="form-control input-sm">
                         <c:forEach var="map" items="${obj.orderRemindEnum}" >
					   		<option value="${map.key}">${map.value}</option>
						 </c:forEach>
                       </select>
                     </td>
                   </tr>
                 </table>
            </div>

            <div class="infoTop">
              <p>日志</p>
            </div>
            <div class="infofooter" id="orderlog">
                 
            </div>
        </div>
    </section>
  </div>
  <!--end right Content-->
</div>
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
	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- select2 -->
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<!-- DataTables -->
	<script src="${base }/public/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="${base }/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!-- Validator -->
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
	<script src="${base }/admin/order/queryorder.js"></script>
	<script src="${base }/admin/order/ordercommon.js"></script>
  <script type="text/javascript">
      $(function(){
        //编辑按钮 click事件
        $('.editBtn').click(function(){
              $(this).addClass('none');
              $('.btnSave').toggle();//保存按钮 显示
              $('.btnCancel').toggle();//取消按钮 显示
              if($(".addDemand").hasClass("sw")){//显示 +需求 按钮
                $('.addDemand').removeClass('sw');
              }else{
                $('.addDemand').addClass('sw');
              }
              $('.removeDemand').removeClass('none');//显示 -需求 按钮
              $('.removAddMake').toggle();//显示 圆圈+ 按钮
              $('.removIcon').toggle();//显示 圆圈- 按钮
              $(".listInfo").toggle();//选项卡 显示
              $('.remindSet tbody tr td input').removeAttr("disabled");//删除 提醒设置 input 禁止编辑的状态
              $('#orderType').removeAttr("disabled");//状态可编辑
              $('#remindType').removeAttr("disabled");//提醒状态可编辑
              $('.DemandDiv').each(function(i){
            	  $(this).find('[name=leavecity]').removeAttr('disabled');
            	  $(this).find('[name=arrivecity]').removeAttr('disabled');
                  $(this).find('[name=leavedate]').removeAttr('disabled');
                  $(this).find('[name=peoplecount]').removeAttr('disabled');
                  $(this).find('[name=tickettype]').removeAttr('disabled');
                  $(this).find('[name=remark]').removeAttr('disabled');
                  $(this).find('[name=airlineinfo]').each(function(i){
                	$(this).find('[name=airlineid]').removeAttr('disabled');
              		$(this).find('[name=aircom]').removeAttr('disabled');
              		$(this).find('[name=ailinenum]').removeAttr('disabled');
              		$(this).find('[name=leavetime]').removeAttr('disabled');
              		$(this).find('[name=arrivetime]').removeAttr('disabled');
              		$(this).find('[name=formprice]').removeAttr('disabled');
              		$(this).find('[name=price]').removeAttr('disabled');
                  });
              });
        }); 
        //取消按钮 click事件
        $('.btnCancel').click(function(){
          $('.editBtn').removeClass('none');//显示 编辑 按钮
          $('.btnSave').toggle();//保存 按钮 隐藏
          $('.btnCancel').toggle();//取消 按钮 隐藏
          $('.addDemand').removeClass('sw');//+需求 隐藏
          $('.removeDemand').addClass('none');//-需求 隐藏
          $('.removAddMake').toggle();//圆圈+ 按钮 隐藏 
          $('.removIcon').toggle();//圆圈- 按钮 隐藏
          $(".listInfo").toggle();//选项卡 隐藏
          $('.remindSet tbody tr td input').attr("disabled",'disabled');//提醒设置 input 添加 不可编辑属性
          $('#orderType').attr("disabled",'disabled');//状态 不可编辑属性
          $('#remindType').attr("disabled",'disabled');//提醒状态 不可编辑属性
          $('.DemandDiv').each(function(i){
        	  var customneedid = $(this).find('[name=customneedid]').val();
        	  if(i>0 && !customneedid){
        		  $(this).remove(); 
        	  }else{
	        	  $(this).find('[name=leavecity]').attr('disabled','disabled');
	        	  $(this).find('[name=arrivecity]').attr('disabled','disabled');
	              $(this).find('[name=leavedate]').attr('disabled','disabled');
	              $(this).find('[name=peoplecount]').attr('disabled','disabled');
	              $(this).find('[name=tickettype]').attr('disabled','disabled');
	              $(this).find('[name=remark]').attr('disabled','disabled');
	              $(this).find('[name=airlineinfo]').each(function(i){
	            	$(this).find('[name=airlineid]').attr('disabled','disabled');
	          		$(this).find('[name=aircom]').attr('disabled','disabled');
	          		$(this).find('[name=ailinenum]').attr('disabled','disabled');
	          		$(this).find('[name=leavetime]').attr('disabled','disabled');
	          		$(this).find('[name=arrivetime]').attr('disabled','disabled');
	          		$(this).find('[name=formprice]').attr('disabled','disabled');
	          		$(this).find('[name=price]').attr('disabled','disabled');
	              });
        	  }
          });
        });


        $('.clearBtn').click(function(){//清楚按钮 隐藏
              $('.hideTable').hide('400');
        });

        /*国际内陆*/
        document.getElementsByName("voyageType")[1].checked="checked";//radio 默认 选中往返
        /*国际*/
        document.getElementsByName("voyageType1")[1].checked="checked";//radio 默认 选中往返

        $('.paragraphBtn li').click(function(){//段数 样式切换
          $(this).addClass('btnStyle').siblings().removeClass('btnStyle');
        });

         //添加 .addMore
        $('.addMore').click(function(){
            var divTest = $(this).parent().parent(); 
            var newDiv = divTest.clone(true);
            divTest.after(newDiv);
            var No = parseInt(divTest.find("p").html())+1;//假设你用p标签显示序号
            newDiv.find("p").html(No); 
            newDiv.find('.addIconTd').remove();
            newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removeMore"></i></td>');
        });
        //删除 .addMore
        $(document).on('click','.removeMore',function(){
            $(this).parent().parent().remove(); 
        });
      }); 

      /*国际内陆 航程类型 点击事件*/
      function radioFunct(obj){
           var radio = document.getElementsByName("voyageType");  
           for (i=0; i<radio.length; i++) {
                if (radio[i].checked) {  
                   var radioValue=radio[i].value;
                   if (radioValue==1) {
                       /*$('.setoutLabel').hide('300');
                        $('.setoutinput').hide('300');
                        $('.addIconTd').hide('300');
                        $('.removeIconTd').hide('300');*/
                        $(this).parent().css("border","solid 1px red");
                        $(this).parents('.scatteredTable1').next('.scatteredTable2').find('.setoutLabel').hide();
                        $(this).parents('.scatteredTable1').next('.scatteredTable2').find('.setoutinput').hide();
                   }else if(radioValue==2){
                        /*$('.setoutLabel').show('300');
                        $('.setoutinput').show('300');
                        $('.addIconTd').hide('300');
                        $('.removeIconTd').hide('300');*/
                   }else if(radioValue==3){
                        /*$('.setoutLabel').hide('300');
                        $('.setoutinput').hide('300');
                        $('.addIconTd').show('300');
                        $('.removeIconTd').show('300');*/
                   }
                }  
           }
      }
      /*国际 航程类型 点击事件*/
      function radioFunct1(){
            var radio1 = document.getElementsByName("voyageType1");  
             for (i=0; i<radio1.length; i++) {  
                  if (radio1[i].checked) {  
                     var radioValue1=radio1[i].value;
                     if (radioValue1==1) {
                          $('.setoutLabel').hide('300');
                          $('.setoutinput').hide('300');
                          $('.addIconTd').hide('300');
                          $('.removeIconTd').hide('300');
                     }else if(radioValue1==2){
                          $('.setoutLabel').show('300');
                          $('.setoutinput').show('300');
                          $('.addIconTd').hide('300');
                          $('.removeIconTd').hide('300');
                     }else if(radioValue1==3){
                          $('.setoutLabel').hide('300');
                          $('.setoutinput').hide('300');
                          $('.addIconTd').show('300');
                          $('.removeIconTd').show('300');
                     }
                  }  
             }
      }
      //保存订单
    $('.btnSave').click(function(){
    	//var data = [];
  		var customdata = {};
  		var customerId = $('#customerId').val();
  		customdata.customerId = customerId;
  		var id = $('#id').val();
  		customdata.id = id;
  		var remindTime = $('#remindTime').val();
  		customdata.remindTime = remindTime;
  		var remindType = $('#remindType').val();
  		customdata.remindType = remindType;
  		var generateOrder = $('#generateOrder').val();
  		customdata.generateOrder = $("#generateOrder").is(':checked');
  		var orderType = $('#orderType').val();
  		customdata.orderType = orderType;
		var row = [];
  		$('.DemandDiv').each(function(i){
  			var lenthcustom = '';
  			var row1 = {};
  			var leavecity = $(this).find('[name=leavecity]').val();
  			//出发城市
  			if (leavecity) {
  				leavecity = leavecity.join(',');
	  			lenthcustom += leavecity;
  			}else{
  				lenthcustom += '';
  			}
  			row1.leavecity = leavecity;
  			//抵达城市
  			var arrivecity = $(this).find('[name=arrivecity]').val();
  			if (arrivecity) {
  				arrivecity = arrivecity.join(',');
	  			lenthcustom += arrivecity;
  			}else{
  				lenthcustom += '';
  			}
  			row1.arrivecity = arrivecity;
  			row1.customneedid = $(this).find('[name=customneedid]').val();
  			row1.leavedate = $(this).find('[name=leavedate]').val();
  			row1.peoplecount = $(this).find('[name=peoplecount]').val();
  			row1.tickettype = $(this).find('[name=tickettype]').val();
  			row1.remark = $(this).find('[name=remark]').val();
  			lenthcustom += $(this).find('[name=customneedid]').val();
  			lenthcustom += $(this).find('[name=leavedate]').val();
  			lenthcustom += $(this).find('[name=peoplecount]').val();
  			lenthcustom += $(this).find('[name=remark]').val();
  			var airrows = [];
  			$(this).find('[name=airlineinfo]').each(function(i){
  				var lengthAir = '';
  				var airrow = {};
  				var aircom = $(this).find('[name=aircom]').val();
  				if (aircom) {
  					aircom = aircom.join(',');
	  				lengthAir += aircom;
  	  			}else{
  	  				lengthAir += '';
  	  			}
  				airrow.aircom = aircom;
  				var ailinenum = $(this).find('[name=ailinenum]').val();
  				if (ailinenum) {
  					ailinenum = ailinenum.join(',');
	  				lengthAir += ailinenum;
  	  			}else{
  	  				lengthAir += '';
  	  			}
  				airrow.ailinenum = ailinenum;
  				airrow.airlineid = $(this).find('[name=airlineid]').val();
  				airrow.leavetime = $(this).find('[name=leavetime]').val();
  				airrow.arrivetime = $(this).find('[name=arrivetime]').val();
  				airrow.formprice = $(this).find('[name=formprice]').val();
  				airrow.price = $(this).find('[name=price]').val();
  				lengthAir += $(this).find('[name=leavetime]').val();
  				lengthAir += $(this).find('[name=arrivetime]').val();
  				lengthAir += $(this).find('[name=formprice]').val();
  				lengthAir += $(this).find('[name=price]').val();
  				if(lengthAir.length > 0){
	  				airrows.push(airrow);
  				}
  				lenthcustom += lengthAir;
  			});
  			row1.airinfo = airrows;
  			if(lenthcustom.length > 0){
	  			row.push(row1);
  			}
  		});
  		customdata.customdata=row;
  		//data.push(customdata);"C:/Users/ui/AppData/Roaming/Tencent/QQ/Temp/{TZ}2SS28JMJX{P}(C[]2ZS.jpg"
  		//alert(JSON.stringify(data));
  		console.log(JSON.stringify(customdata));
  		layer.load(1);
		$.ajax({ 
			type: 'POST', 
			data: {data:JSON.stringify(customdata)}, 
			url: '${base}/admin/inland/saveOrderInfo.html',
            success: function (data) { 
            	//alert("添加成功");
            	//location.reload();
            	layer.closeAll('loading');
            	layer.msg("保存成功",{time: 2000, icon:1});
            	if(data.ordersstatus ===1){
	            	window.location.reload();
            	}else{
            		window.location.href = "${base}/admin/inland/bookingDetail.html?id=" + data.id;
            	}
            },
            error: function (xhr) {
            	layer.msg("保存失败","",3000);
            } 
        });
    });
  	//oninput事件
    $(document).on('input', '.costPrice', function(e) {
    	$(this).val($(this).val().replace(/[^.\d]/g,''));
    	var fromprice = $(this).val();
    	//票价折扣
    	var discountFare = '${obj.custominfo.discountFare}';
    	//手续费
    	var fees = '${obj.custominfo.fees}'; 
    	//alert("值："+fromprice + " 折扣："+discountFare + " 手续费：" + fees);
    	var price = parseFloat(fromprice * discountFare / 100) + parseFloat(fees);
    	if(fromprice){
     		if(isNaN(price)){
     			$(this).parent().parent().find('[name=price]').val('');
     		}else{
    	 		$(this).parent().parent().find('[name=price]').val(price);
     		}
     	}else{
     		$(this).parent().parent().find('[name=price]').val('');
     	}
    });
  	//加载日志
    loadOrderLog('${obj.orderinfo.id }');
  </script>
</body>
</html>
