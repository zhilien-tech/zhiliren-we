<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>查询</title>
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
<!-- 图标 -->
<link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${base}/public/ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/skins/_all-skins.min.css">
<link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${base}/public/dist/css/bootstrapValidator.css" />
<link rel="stylesheet" href="${base}/public/dist/css/query.css"><!--本页面styleFlie-->

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!--right Content-->
		<div class="content-wrapper">
			<section class="content">
				<!--客户信息 start-->
				<div class="customerInfo">
					<div class="infoTop">
						<p>客户信息</p>
						<div class="infoTopContent">
							<input type="checkbox" class="conCheckInput" checked="checked"><label>生成订单</label>
							<select class="form-control input-sm conSelect cf">
								<option value="1" selected="selected">查询</option>
								<option value="2">预定</option>
								<option value="3">一订</option>
								<option value="4">二订</option>
								<option value="5">尾款</option>
								<option value="6">出票</option>
								<option value="7">开票</option>
								<option value="8">关闭</option>
							</select> 
							<label>提醒：</label> 
							<input id="datepicker" name="datepicker" type="text" class="form-control input-sm conTimeInput" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="请选择提醒日期"> 
							<input type="button" class="btn btn-primary btn-sm" value="保存"> 
							<input type="button" class="btn btn-primary btn-sm" value="取消">
						</div>
					</div>
					<div class="infofooter">
					<form id="customerCheckFrom" method="post">
						<table>
							<tr>
								<td><label>客户姓名：</label></td>
								<td>
									<!-- <input type="text" class="form-control input-sm" placeholder="请输入客户姓名"> -->
									<select id="linkNameId" name="linkName" onchange="linkNameOpt()" class="form-control input-sm" multiple="multiple" data-placeholder="请输入客户姓名"></select>
								</td>
								<input type="hidden" id="linkManId"> 
								<input type="hidden" id="phoneId">
								<td><label>电话：</label></td>
								<td>
									<!-- <input type="text" class="form-control input-sm" placeholder="请输入电话"> -->
									<select id="phoneNumId" name="phoneNum" class="form-control input-sm" multiple="multiple" data-placeholder="请输入电话"></select>
								</td>
								<td><label>地址：</label></td>
								<td>
									<input id="addressId" type="text" disabled="disabled" class="form-control input-sm addressInput">
								</td>
								<td>
									<input id="clearBtn" type="button" value="清空" class="btn btn-primary btn-sm">
									<i class="UnderIcon fa fa-chevron-circle-down"></i>
								</td>
							</tr>
						</table>
					</form>
						<table class="hideTable none">
							<tr>
								<td><label>公司简称：</label></td>
								<td>
									<input id="shortNameId" type="text" disabled="disabled" class="form-control input-sm">
								</td>
								<td><label>负责人：</label></td>
								<td>
									<input id="agentId" type="text" disabled="disabled" class="form-control input-sm">
								</td>
								<td><label>网址：</label></td>
								<td>
									<input id="siteUrlId" type="text" disabled="disabled" class="form-control input-sm">
								</td>
								<td><label>传真：</label></td>
								<td>
									<input id="faxId" type="text" disabled="disabled" class="form-control input-sm">
								</td>
								<td><label>出发城市：</label></td>
								<td>
									<!-- <input id="departureCityId" type="text" disabled="disabled" class="form-control input-sm"> -->
									<select id="city" class="form-control input-sm" multiple="multiple" Disabled = "true" data-placeholder="">
										<option></option>
										<c:forEach var="one" items="${obj.outcitylist }">
											<option value="${one.id }">${one.text}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td><label style="position: relative;top: 4px;">结算方式：</label></td>
								<td colspan="9"><pre><span id="payTypeId"></span> 信用额度：0  临时额度：0  历史欠款：0  预存款：0</pre></td>
							</tr>
						</table>
					</div>
				</div>
				<!--客户信息 end-->
				
				<!--购票查询列表 start-->
				<div class="listInfo">
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">散客</a></li>
                      <li><a href="#tab_2" data-toggle="tab">团队</a></li>
                    </ul>
                    <div class="tab-content">
                      <div class="tab-pane active" id="tab_1">
                          <table class="scatteredTable1">
                              <tr>
                                <td><label>搜索筛选：</label></td>
                                <td>
                                	<input id="gjnlRadio" type="radio" name="internat" value="1"><span>国际内陆</span>
                                </td>
                                <td colspan="2">
                                	<input id="gjRadio" type="radio" name="internat" value="2"><span>国际</span>
							    </td>
                              </tr>
                              <tr>
                                <td><label>航程类型：</label></td>
                                <td>
                                	<input id="singleType" type="radio" name="voyageType" value="1" onclick="radioFunct(this)"><span>单程</span>
                                </td>
                                <td>
                                	<input id="returnType" type="radio" name="voyageType" value="2" onclick="radioFunct()"><span>往返</span>
                               	</td>
                                <td>
                                	<input id="moreType" type="radio" name="voyageType" value="3" onclick="radioFunct()"><span>多程</span>
                                </td>
                              </tr>
                          </table>
<!-- 查询 start -->                  
<form id="searchSingleTicketsForm" method="post">
	<table class="scatteredTable2">
                           
                           <!-- 多程查询 start -->
                           <tr class="setMore">
                              <td><label>出发城市：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="拼音/三字代码"> -->
								<select id="outCity" name="outCity" onchange="outCityNameOpt()" class="form-control input-sm" multiple="multiple" data-placeholder="拼音/三字代码"></select>
								<input id="outCityName" name="outCityName" type="hidden"/>
								<input id="outCityCode" name="outCityCode" type="hidden"/>
                              </td>
                              <td class="untilTd"><i class="fa fa-minus"></i></td>
                              <td><label>到达城市：</label></td>
                              <td>
                              	<select id="singleArriveCity" name="singleArriveCity" onchange="arriveCityNameOpt()"  class="form-control input-sm" multiple="multiple" data-placeholder="拼音/三字代码"></select>
                              	<input id="arriveCityName" name="arriveCityName" type="hidden"/>
                              	<input id="arriveCityCode" name="arriveCityCode" type="hidden"/>
                              </td>
                              <td class="untilTd1"></td><!--空白处 可以忽略-->
                              <td><label>出发日期：</label></td>
                              <td>
                              	<!-- <input id="" name="" type="text" class="form-control input-sm" placeholder="2016-12-21"> -->
                              	<input id="outDatepicker" name="outDatepicker" type="text" class="form-control input-sm" onFocus="WdatePicker({startDate:'%y', dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}'})" placeholder="2016-12-21">
                              </td>
                              <td class="setoutLabel">
                              <label>返回日期：</label></td>
                              <td class="setoutinput">
                              	<!-- <input type="text" class="form-control input-sm" placeholder="2016-12-25"> -->
                              	<input id="returnDatepicker" name="returnDatepicker" type="text" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'outDatepicker\')}'})" placeholder="2016-12-25">
                              </td>
                              <td class="addIconTd none"><i class="glyphicon glyphicon-plus addMore"></i></td>
                           </tr>
                           <!-- 多程查询 end -->
                            
   						 </table>
                         <table class="scatteredTable3">
                           <tr>
                             <td><label>乘客类型：</label></td>
                             <td>
                               <select id="agentSelect" name="agentSelect" class="form-control input-sm">
                               	 	<option value="0" selected="selected">成人 0</option>
                               </select>
                             </td>
                             <td class="paddTd">
                               <select id="childrenSelect" name="childrenSelect" class="form-control input-sm">
                                	<option value="0" selected="selected">儿童 0</option>
                               </select>
                             </td>
                             <td class="paddTd">
                               <select id="babySelect" name="babySelect" class="form-control input-sm">
                               		 <option value="0" selected="selected">婴儿 0</option>
                               </select>
                             </td>
                             <td><label>舱位等级：</label></td>
                             <td>
                                <select id="airLevel" name="airLevel" class="form-control input-sm selectWid">
                                  <option value="1">经济舱</option>
                                  <option value="2">超级经济舱</option>
                                  <option value="3">商务舱</option>
                                  <option value="4">头等舱</option>
                                </select>
                             </td>
                             <td><label>航空公司：</label></td>
                             <td>
                             	<!-- <input type="text" class="form-control input-sm" placeholder="(选填)中文/二字代码"> -->
                             	<select id="airline" name="airline" onchange="airlineNameOpt()"  class="form-control input-sm" multiple="multiple" data-placeholder="(选填)中文/二字代码"></select>
                             	<input id="airlineName" name="airlineName" type="hidden"/>
                             	<input id="airlineCode" name="airlineCode" type="hidden"/>
                             </td>
                             <td>
                             	<button id="searchSingleTicketsBtn" type="button"  class="btn btn-primary btn-sm">搜索机票</button>
                             </td>
                           </tr>
    </table>
</form>
<!-- 查询机票  end --> 
                          <div class="TableListInfo">
                              <h5 id="travelArea"></h5>
                              <div class="paragraphNumber">
                                <ul id="travelTypeNum" class="paragraphBtn">
                                	
                                </ul>
                                <ul id="travelDateNum" class="paragraphBtn">
                                
                                </ul>
                                <!-- 机票信息展示 -->
                                <ul id="paragraphListInfo" class="paragraphListInfo"></ul>
                                <input type="hidden" id="airInfoList" name="airInfoList" value="1">
                              </div>
                          </div>
                      </div>
                      
                      
                      <!-- 团队票 检索 -->
                      <div class="tab-pane" id="tab_2">
                          <table class="scatteredTable1">
                              <tr>
                                <td><label>搜索筛选：</label></td>
                                <td><input id="gjnlRadioTeam" type="radio" name="internat1" value="1"><span>国际内陆</span></td>
                                <td colspan="2"><input id="gjRadioTeam" type="radio" name="internat1" value="2"><span>国际</span></td>
                              </tr>
                              <tr>
                                <td><label>航程类型：</label></td>
                                <td><input type="radio" name="voyageType1" value="1" onclick="radioFunct1()"><span>单程</span></td>
                                <td><input type="radio" name="voyageType1" value="2" onclick="radioFunct1()"><span>往返</span></td>
                                 <td><input type="radio" name="voyageType1" value="3" onclick="radioFunct1()"><span>多程</span></td>
                              </tr>
                          </table><!--搜索筛选/航程类型-->
<!-- 查询团队机票 start -->                  
<form id="searchTeamTicketsForm" method="post">
      <table class="scatteredTable2">
                            <tr>
                              <td><label>出发城市：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="拼音/三字代码"> -->
                              	<select id="teamOutCity" name="teamOutCity" onchange="teamOutCityNameOpt()" class="form-control input-sm" multiple="multiple" data-placeholder="拼音/三字代码"></select>
								<input id="teamOutCityName" name="teamOutCityName" type="hidden"/>
								<input id="teamOutCityCode" name="origin" type="hidden"/>
                              </td>
                              <td class="untilTd"><i class="fa fa-minus"></i></td>
                              <td><label>到达城市：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="拼音/三字代码"> -->
                              	<select id="teamArriveCity" name="teamArriveCity" onchange="teamArriveCityNameOpt()"  class="form-control input-sm" multiple="multiple" data-placeholder="拼音/三字代码"></select>
                              	<input id="teamArriveCityName" name="teamArriveCityName" type="hidden"/>
                              	<input id="teamArriveCityCode" name="destination" type="hidden"/>
                              </td>
                              <td class="untilTd1"></td><!--空白处 可以忽略-->
                              <td><label>出发日期：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="2016-12-21"> -->
                              	<input id="teamOutDatepicker" name="departuredate" type="text" class="form-control input-sm" onFocus="WdatePicker({startDate:'%y', dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}'})" placeholder="2016-12-21">
                              </td>
                              <td class="setoutLabel"><label>返回日期：</label></td>
                              <td class="setoutinput">
                              	<!-- <input type="text" class="form-control input-sm" placeholder="2016-12-25"> -->
                              	<input id="teamReturnDatepicker" name="returndate" type="text" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'teamOutDatepicker\')}'})" placeholder="2016-12-25">
                              </td>
                              <td class="addIconTd none"><i class="glyphicon glyphicon-plus addMore"></i></td>
                            </tr>
	 </table>
	 <table class="scatteredTable3">
                            <tr>
                              <td><label>舱位等级：</label></td>
                              <td>
                                 <select id="teamAirLevel" name="teamAirLevel"  class="form-control input-sm selectWid1">
                                   <option value="1">经济舱</option>
                                   <option value="2">超级经济舱</option>
                                   <option value="3">商务舱</option>
                                   <option value="4">头等舱</option>
                                 </select>
                              </td>
                              <td><label>航空公司：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="(选填)中文/代码"> -->
                              	<select id="teamAirline" name="teamAirline" onchange="teamAirlineNameOpt()"  class="form-control input-sm" multiple="multiple" data-placeholder="(选填)中文/二字代码"></select>
                             	<input id="teamAirlineName" name="teamAirlineName" type="hidden"/>
                             	<input id="teamAirlineCode" name="includedcarriers" type="hidden"/>
                              </td>
                              <td>
                              	<button id="searchTeamTicketsBtn" type="button" class="btn btn-primary btn-sm">搜索机票</button>
                              </td>
                            </tr>
     </table>
</form>
<!-- 查询团队机票  end --> 

                          <div class="tableInfoDiv">
                            <table class="table table-bordered table-hover">
                              <thead>
                              <tr>
                                <th>订单号</th>
                                <th>团型</th>
                                <th>团名</th>
                                <th>程航</th>
                                <th>始发日期</th>
                                <th>价格</th>
                                <th>数量</th>
                                <th>申请日期</th>
                                <th>操作人</th>
                              </tr>
                              </thead>
                              <!-- 显示团队票信息 -->
                              <tbody id="teamtbody">
                              	
                              </tbody>
                            </table>
                          </div>
                      </div>
                    </div>
                  </div>
          </div><!--购票查询 列表-->
			</section>
		</div>
		<!--end right Content-->
		
		<!--footer-->
			<%@include file="/WEB-INF/public/footer.jsp"%>
		<!--end footer-->

		<script type="text/javascript">
			var BASE_PATH = '${base}';
		</script>
		<!-- jQuery 2.2.3 -->
		<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<!-- Bootstrap 3.3.6 -->
		<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
		<!-- Slimscroll -->
		<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
		<!-- FastClick -->
		<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
		<!-- select2 -->
		<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
		<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
		<!-- My97DatePicker -->
		<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
		<!-- Validator -->
		<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
		<!-- 客户信息 js -->
		<script src="${base}/admin/searchTicket/searchCustomerInfo.js"></script>
		<!-- 订单信息 js -->
		<script src="${base}/admin/searchTicket/searchOrderInfo.js"></script>
		<!-- 多程信息 js -->
		<%-- <script src="${base}/admin/searchTicket/searchMoreline.js"></script> --%>
		<!-- 团队信息  js -->
		<script src="${base}/admin/searchTicket/searchTeamOrder.js"></script>
		
		<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
		
		<!-- layer -->
		<script src="${base}/common/js/layer/layer.js"></script>
		<script type="text/javascript">
	      $(function(){
	    	//客户信息 显示/隐藏
            $('.UnderIcon').on('click',function(){
              $('.hideTable').toggle('400');
            });
    		
       		$('#clearBtn').click(function(){//清楚按钮 隐藏
              $('.hideTable').hide('400');
            });
	        /*散客*/
	        document.getElementsByName("internat")[0].checked="checked";//radio 默认 国际内陆
	        document.getElementsByName("voyageType")[1].checked="checked";//radio 默认 选中往返
	        /*团队*/
	        document.getElementsByName("internat1")[0].checked="checked";//radio 默认 国际内陆
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
			
	        //校验
	    	$('#customerCheckFrom').bootstrapValidator({
	  			message: '验证不通过!',
	  	        feedbackIcons: {
	  	            valid: 'glyphicon glyphicon-ok',
	  	            invalid: 'glyphicon glyphicon-remove',
	  	            validating: 'glyphicon glyphicon-refresh'
	  	        },
	  	        fields: {
	  	        	linkName: {
	  	                validators: {
	  	                    notEmpty: {
	  	                        message: '客户姓名不能为空!'
	  	                    }
	  	                }
	  	            }
	  	        }
	  		});
	        alert();
	        
	     });
	  </script>
	  
	  <script type="text/javascript">
 	  	/* 散客查询 */
  		$("#searchSingleTicketsBtn").click(function() {
  			var link = $("#linkNameId").val();
  			if(link==null){
  				layer.msg("客户名称不能为空", "", 2000);
  				return;
  			}
			$.ajax({
				type : 'POST',
				data : $("#searchSingleTicketsForm").serialize(),
				url : '${base}/admin/search/searchSingleTickets.html',
				success : function(resp) {
					var outLiList = "";
					var returnLiList = "";
					
					if ("200" == resp.statusCode) {
						//如果成功返回数据才显示段数
						//区间
						var area = $("#outCityName").val()+' -- '+$("#arriveCityName").val();
						document.getElementById('travelArea').innerHTML=area;
						//段数
						var airType = $("input[name='voyageType']:checked").val();
						var html = "";
						if(airType == 1){
							html = '<li id="num01" class="btnStyle">第一段</li>';
							document.getElementById('travelTypeNum').innerHTML=html;
						}
						if(airType == 2){
							html = '<li id="num01" class="btnStyle">第一段</li><li id="num02">第二段</li>';
							document.getElementById('travelTypeNum').innerHTML=html;
						}
						/* 多程 显示多段 */
						if(airType == 3){
							
						}
						/* 日期小卡片  */
						getDateCard();
						
						var outCodeStr = $("#outCity").select2("val");
						var arriveCodeStr = $("#singleArriveCity").select2("val");
						var outList = new Array();
						var returnList = new Array();
						
						for (var i=0; i<resp.data.length; i++){
							var list = resp.data[i].list;
							
							var returnIdx = 0 ;
							for(var j=0; j<list.length; j++){
								var DepartureAirport = resp.data[i].list[j].DepartureAirport;
								/*返程*/
								if(DepartureAirport == arriveCodeStr ){
									returnIdx = j;
									break;
								}
							}
							for(var j=0; j<list.length; j++){
								if(j < returnIdx){
									outList.push(list[j]);
								}else{
									returnList.push(list[j]);
								}
							}
							
							/* 去程列表 */
							for(var foot = 0; foot < outList.length;foot++){
								var AirlineName = resp.data[i].airlineName;
								var airlineCode = resp.data[i].airlineCode;
								var FlightNumber = outList[foot].FlightNumber;
								var ArrivalAirport = outList[foot].ArrivalAirport;
								var DepartureAirport = outList[foot].DepartureAirport;
								var DepartureDateTime = outList[foot].DepartureDateTime;
								var ArrivalDateTime = outList[foot].ArrivalDateTime;
								var ElapsedTime = outList[foot].ElapsedTime;
								var totalAmount = resp.data[i].priceInfo.totalAmount;
								
								
								outLiList += '<li>'+
							    '<div class="imgIconDiv"><img src="logoIcon.png"><p>'+AirlineName+airlineCode+FlightNumber+'</p></div>'+
   								'<div class="distanceTimeDiv"><span class="chufaCS"><b>'+DepartureDateTime+'</b><p>'+DepartureAirport+'</p>'+
   								'</span><span class="shiDuan">'+toHourMinute(ElapsedTime)+'</span><span class="daodaCS"><b>'+ArrivalDateTime+'</b><p>'+ArrivalAirport+'</p></span></div>'+
						   					'<div class="moneyDiv"><i class="fa fa-cny"></i>'+totalAmount+'</div>'+
						   					'</li>';
							}
							
							/* 返程列表 */
							for(var foot = 0; foot < returnList.length;foot++){
								var AirlineName = resp.data[i].airlineName;
								var airlineCode = resp.data[i].airlineCode;
								var FlightNumber = returnList[foot].FlightNumber;
								var ArrivalAirport = returnList[foot].ArrivalAirport;
								var DepartureAirport = returnList[foot].DepartureAirport;
								var DepartureDateTime = returnList[foot].DepartureDateTime;
								var ArrivalDateTime = returnList[foot].ArrivalDateTime;
								var ElapsedTime = returnList[foot].ElapsedTime;
								var totalAmount = resp.data[i].priceInfo.totalAmount;
								
								
								returnLiList += '<li>'+
							    '<div class="imgIconDiv"><img src="logoIcon.png"><p>'+AirlineName+airlineCode+FlightNumber+'</p></div>'+
   								'<div class="distanceTimeDiv"><span class="chufaCS"><b>'+DepartureDateTime+'</b><p>'+DepartureAirport+'</p>'+
   								'</span><span class="shiDuan">'+toHourMinute(ElapsedTime)+'</span><span class="daodaCS"><b>'+ArrivalDateTime+'</b><p>'+ArrivalAirport+'</p></span></div>'+
						   					'<div class="moneyDiv"><i class="fa fa-cny"></i>'+totalAmount+'</div>'+
						   					'</li>';
							}
							
						}
						
						if($("#airInfoList").val() == 1){
							document.getElementById('paragraphListInfo').innerHTML=outLiList;
						}else{
							document.getElementById('paragraphListInfo').innerHTML=returnLiList;
						}
						
					} else {
						layer.msg(resp.data.message, "", 2000);
					}

				},
				error : function(xhr) {
				}
			});
			
		});
  		
	  	/* 团客查询 */
		$("#searchTeamTicketsBtn").click(function() {
			var link = $("#linkNameId").val();
  			if(link==null){
  				layer.msg("客户名称不能为空", "", 2000);
  				return;
  			}
			$.ajax({
				type : 'POST',
				data : $("#searchTeamTicketsForm").serialize(),
				url : '${base}/admin/search/searchTeamTickets.html',
				success : function(data) {
					var teamList = "";
					 $.each(data, function (index, element) {  
			              teamList += '<tr><td>'+ element.ordersnum +'</td><td>'+ "团队" +'</td><td>'+ "爱自由" +'</td><td>'+element.leavescity +'/'+ element.backscity +'</td>'+
			              			'<td>'+element.leavesdate+'</td><td>'+element.price+'</td><td>'+element.amount+'</td><td>'+element.orderstime+'</td><td>'+element.opid+'</td></tr>';
			         });  
					 document.getElementById('teamtbody').innerHTML=teamList;
				},
				error : function() {
				}
			});
		});
	  </script>
</body>
</html>
