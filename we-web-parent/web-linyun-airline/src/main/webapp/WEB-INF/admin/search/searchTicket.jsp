<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
							 <select class="form-control input-sm timSelect">
			                     <option value="0">每15分</option>
			                     <option value="1">每30分</option>
			                     <option value="2">每1小时</option>
			                     <option value="3">每天</option>
			                     <option value="4">每周</option>
			                     <option value="5">每月</option>
			                   </select>
							<input id="datepicker" name="datepicker" type="text" class="form-control input-sm conTimeInput" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="请选择提醒日期"> 
							<input type="button" class="btn btn-primary btn-sm" value="保存"> 
							<input type="button" class="btn btn-primary btn-sm" value="取消">
						</div>
					</div>
					<div class="infofooter">
					<form id="customerCheckForm" method="post">
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
							</tr>
							<tr>
								<td><label>出发城市：</label></td>
								<td colspan="3">
									<!-- <input id="departureCityId" type="text" disabled="disabled" class="form-control input-sm"> -->
									<select id="city" class="form-control input-sm addWidths" multiple="multiple" Disabled = "true" data-placeholder="">
										<option></option>
										<c:forEach var="one" items="${obj.outcitylist }">
											<option value="${one.id }">${one.text}</option>
										</c:forEach>
									</select>
								</td>
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
                      <li class="active"><a href="#tab_1" data-toggle="tab">内陆跨海</a></li>
                      <li><a href="#tab_2" data-toggle="tab">国际</a></li>
                    </ul>
                    <div class="tab-content">
                      <div class="tab-pane active" id="tab_1">
                          <table class="scatteredTable1">
                              <tr>
                                <td><label>航程类型：</label></td>
                                <td>
                                	<input id="singleType" type="radio" name="voyageType" value="1" onclick="radioFunct()"><span>单程</span>
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
	<table id="singletable" class="scatteredTable2">
                           <input id="origin" name="origin" type="hidden"/>
						   <input id="destination" name="destination" type="hidden"/>
						   <input id="departuredate" name="departuredate" type="hidden"/>
                           <input id="returndate" name="returndate" type="hidden"/>
                           <!-- 多程查询 start -->
                           <tr class="setMore">
                              <td><label>出发城市：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="拼音/三字代码"> -->
								<select id="outCity0" name="origin0" class="form-control input-sm" multiple="multiple" data-placeholder="拼音/三字代码"></select>
                              </td>
                              <td class="untilTd"><i class="fa fa-minus"></i></td>
                              <td><label>到达城市：</label></td>
                              <td>
                              	<select id="singleArriveCity0" name="destination0"  class="form-control input-sm" multiple="multiple" data-placeholder="拼音/三字代码"></select>
                              </td>
                              <td class="untilTd1"></td><!--空白处 可以忽略-->
                              <td><label>出发日期：</label></td>
                              <td>
                              	<!-- <input id="" name="" type="text" class="form-control input-sm" placeholder="2016-12-21"> -->
                              	<input id="outDatepicker0" name="departuredate0" type="text" class="form-control input-sm" onFocus="WdatePicker({startDate:'%y', dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}'})" placeholder="2017-01-01">
                              </td>
                              <td class="setoutLabel">
                              <label>返回日期：</label></td>
                              <td class="setoutinput">
                              	<!-- <input type="text" class="form-control input-sm" placeholder="2016-12-25"> -->
                              	<input id="returnDatepicker0" name="returndate0" type="text" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'outDatepicker0\')}'})" placeholder="2017-01-15">
                              </td>
                              <td class="addIconTd addSingleIconTd none"><i class="glyphicon glyphicon-plus addMore"></i></td>
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
                             	<select id="airline" name="includedcarriers" onchange="airlineNameOpt()"  class="form-control input-sm" multiple="multiple" data-placeholder="(选填)中文/二字代码"></select>
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
                                <ul id="travelTypeNum" class="paragraphBtn"> </ul>
                                <ul id="travelDateNum" class="paragraphBtn"> </ul>
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
                                <td><label>航程类型：</label></td>
                                <td><input type="radio" name="voyageType1" value="1" onclick="radioFunct1()"><span>单程</span></td>
                                <td><input type="radio" name="voyageType1" value="2" onclick="radioFunct1()"><span>往返</span></td>
                                 <td><input type="radio" name="voyageType1" value="3" onclick="radioFunct1()"><span>多程</span></td>
                              </tr>
                          </table><!--搜索筛选/航程类型-->
<!-- 查询团队机票 start -->                  
<form id="searchTeamTicketsForm" method="post">
      <table id="teamTable" class="scatteredTable2">
                            <input id="teamorigin" name="origin" type="hidden"/>
						    <input id="teamdestination" name="destination" type="hidden"/>
						    <input id="teamdeparturedate" name="departuredate" type="hidden"/>
                            <input id="teamreturndate" name="returndate" type="hidden"/>
                            
                            <!-- 团队多程查询 -->
                            <tr class="setTeamMore">
                              <td><label>出发城市：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="拼音/三字代码"> -->
                              	<select id="teamOutCity0" name="origin1" class="form-control input-sm" multiple="multiple" data-placeholder="拼音/三字代码"></select>
                              </td>
                              <td class="untilTd"><i class="fa fa-minus"></i></td>
                              <td><label>到达城市：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="拼音/三字代码"> -->
                              	<select id="teamArriveCity0" name="destination1" class="form-control input-sm" multiple="multiple" data-placeholder="拼音/三字代码"></select>
                              </td>
                              <td class="untilTd1"></td><!--空白处 可以忽略-->
                              <td><label>出发日期：</label></td>
                              <td>
                              	<!-- <input type="text" class="form-control input-sm" placeholder="2016-12-21"> -->
                              	<input id="teamOutDatepicker0" name="departuredate1" type="text" class="form-control input-sm" onFocus="WdatePicker({startDate:'%y', dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}'})" placeholder="2017-01-01">
                              </td>
                              <td class="setoutLabel"><label>返回日期：</label></td>
                              <td class="setoutinput">
                              	<!-- <input type="text" class="form-control input-sm" placeholder="2016-12-25"> -->
                              	<input id="teamReturnDatepicker0" name="returndate1" type="text" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'teamOutDatepicker0\')}'})" placeholder="2017-01-15">
                              </td>
                              <td class="addIconTd addTeamIconTd none"><i class="glyphicon glyphicon-plus addMore"></i></td>
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
                          	<ul id="travelTeamTypeNum" class="paragraphBtn"></ul>
                            <table class="table table-bordered table-hover">
                              <thead>
                              <tr>
                                <th>序号</th>
	                            <th>日期</th>
	                            <th>航班号</th>
	                            <th>航段</th>
	                            <th>时间</th>
	                            <th>人数</th>
	                            <th>FOC</th>
	                            <th>天数</th>
	                            <th>联运要求</th>
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
		<script src="${base}/admin/searchTicket/searchMoreLine.js"></script>
		<!-- 团队信息  js -->
		<script src="${base}/admin/searchTicket/searchTeamMoreLine.js"></script>
		<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
		<!-- layer -->
		<script src="${base}/common/js/layer/layer.js"></script>
		<script type="text/javascript">
	      $(function(){
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
	     });
	  </script>
	  <script type="text/javascript">
  		/* 段数按钮点击事件 */
		$(document).click(function (e) { 
			var num_id = $(e.target).attr('id'); 
			/* 点击 散客每段提醒事件 */
			var num = num_id.indexOf("num");
			if(num==0){
				var i = num_id.substring(3,num_id.length);
				var index = "";
				if(i%2){
					index = (i-1)/2;
					$("#origin").val($("#outCity"+index).select2("val"));
					$("#destination").val($("#singleArriveCity"+index).select2("val"));
					$("#departuredate").val($("#outDatepicker"+index).val());
					$("#returndate").val($("#returnDatepicker"+index).val());
					/* 获取去程数据 */
					$("#airInfoList").val("1");
					$("#searchSingleTicketsBtn").click();
				}else{
					index = (i-2)/2;
					$("#origin").val($("#singleArriveCity"+index).select2("val"));
					$("#destination").val($("#outCity"+index).select2("val"));
					$("#departuredate").val($("#returnDatepicker"+index).val());
					$("#returndate").val($("#outDatepicker"+index).val());
					/* 获取返程数据 */
					$("#airInfoList").val("2");
					$("#searchSingleTicketsBtn").click();	  					
				}
			}
			/* 点击 团客每段提醒事件 */
			if(num_id != null){
				var teamnum = num_id.indexOf("teamNum");
				if(teamnum == 0){
					var i = num_id.substring(7,num_id.length);
					var index = "";
					if(i%2){
						/* 去程数据 */
						index = (i-1)/2;
						$("#teamorigin").val($("#teamOutCity"+index).select2("val"));
						$("#teamdestination").val($("#teamArriveCity"+index).select2("val"));
						$("#teamdeparturedate").val($("#teamOutDatepicker"+index).val());
						$("#teamreturndate").val($("#teamReturnDatepicker"+index).val());
						$("#searchTeamTicketsBtn").click();
					}else{
						/* 返程数据 */
						index = (i-2)/2;
						$("#teamorigin").val($("#teamArriveCity"+index).select2("val"));
						$("#teamdestination").val($("#teamOutCity"+index).select2("val"));
						$("#teamdeparturedate").val($("#teamReturnDatepicker"+index).val());
						$("#teamreturndate").val($("#teamOutDatepicker"+index).val());
						$("#searchTeamTicketsBtn").click(); 
					}
				}
			}
		});
	 </script>
	 <script type="text/javascript">
	 /* ------------------------散客 航程类型 点击事件-------------------------*/
	 function radioFunct(){
	        var radio = document.getElementsByName("voyageType");  
	        for (i=0; i<radio.length; i++) {  
	             if (radio[i].checked) {  
	                var radioValue=radio[i].value;
	                $("#singletable tr").not(":first").remove();
	                if (radioValue==1) {
	                     $('.setoutLabel').hide();
	                     $('.setoutinput').hide();
	                     $('.addIconTd').hide();
	                     $('.removeIconTd').hide();
	                }else if(radioValue==2){
	                     $('.setoutLabel').show();
	                     $('.setoutinput').show();
	                     $('.addIconTd').hide();
	                     $('.removeIconTd').hide();
	                }else if(radioValue==3){
	                     $('.setoutLabel').hide();
	                     $('.setoutinput').hide();
	                     $('.addIconTd').show();
	                     $('.removeIconTd').show();
	                };
	             }  
	        }
	   }
	   /*团队 航程类型 点击事件*/
	   function radioFunct1(){
	         var radio1 = document.getElementsByName("voyageType1");  
	          for (i=0; i<radio1.length; i++) {  
	               if (radio1[i].checked) {  
	                  var radioValue1=radio1[i].value;
	                  $("#teamTable tr").not(":first").remove();
	                  if (radioValue1==1) {
	                       $('.setoutLabel').hide();
	                       $('.setoutinput').hide();
	                       $('.addIconTd').hide();
	                       $('.removeIconTd').hide();
	                  }else if(radioValue1==2){
	                       $('.setoutLabel').show();
	                       $('.setoutinput').show();
	                       $('.addIconTd').hide();
	                       $('.removeIconTd').hide();
	                  }else if(radioValue1==3){
	                       $('.setoutLabel').hide();
	                       $('.setoutinput').hide();
	                       $('.addIconTd').show();
	                       $('.removeIconTd').show();
	                  };
	               }  
	          }
	   }
	 </script>
</body>
</html>
