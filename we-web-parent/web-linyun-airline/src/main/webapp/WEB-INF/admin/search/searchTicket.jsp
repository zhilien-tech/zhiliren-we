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
						<table>
							<tr>
								<td><label>客户姓名：</label></td>
								<td>
									<!-- <input type="text" class="form-control input-sm" placeholder="请输入客户姓名"> -->
									<select id="linkNameId" name="linkName" class="form-control input-sm" multiple="multiple" data-placeholder="请输入客户姓名"></select>
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
                                	<input type="radio" name="voyageType" value="1" onclick="radioFunct(this)"><span>单程</span>
                                </td>
                                <td>
                                	<input type="radio" name="voyageType" value="2" onclick="radioFunct()"><span>往返</span>
                               	</td>
                                <td>
                                	<input type="radio" name="voyageType" value="3" onclick="radioFunct()"><span>多程</span>
                                </td>
                              </tr>
                          </table>
                          <form id="searchForm">
	                          <table class="scatteredTable2">
		                           <!--出发、抵达城市/出发、抵达日期/人数/舱位等级/航空公司 文本框-->
		                           <tr>
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
		                              	<input id="outDatepicker" name="outDatepicker" type="text" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'arriveDatepicker\')}'})" placeholder="2016-12-21">
		                              </td>
		                              <td class="setoutLabel">
		                              <label>返回日期：</label></td>
		                              <td class="setoutinput">
		                              	<!-- <input type="text" class="form-control input-sm" placeholder="2016-12-25"> -->
		                              	<input id="arriveDatepicker" name="arriveDatepicker" type="text" class="form-control input-sm" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'outDatepicker\')}'})" placeholder="2016-12-25">
		                              </td>
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
	                                  <option>成人 10</option>
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
	                                  <option>儿童 10</option>
	                                </select>
	                              </td>
	                              <td class="paddTd">
	                                <select class="form-control input-sm">
	                                  <option>婴儿 0</option>
	                                  <option>婴儿 1</option>
	                                  <option>婴儿 2</option>
	                                  <option>婴儿 3</option>
	                                  <option>婴儿 4</option>
	                                  <option>婴儿 5</option>
	                                  <option>婴儿 6</option>
	                                  <option>婴儿 7</option>
	                                  <option>婴儿 7</option>
	                                  <option>婴儿 9</option>
	                                  <option>婴儿 10</option>
	                                </select>
	                              </td>
	                              <td><label>舱位等级：</label></td>
	                              <td>
	                                 <select class="form-control input-sm selectWid">
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
	                              </td>
	                              <td>
	                              	<button id="searchBtn" type="button"  class="btn btn-primary btn-sm">搜索机票</button>
	                              </td>
	                            </tr>
	                          </table>
                          </form>
                          <div class="TableListInfo">
                              <h5>北京--悉尼</h5>
                              <div class="paragraphNumber">
                                <ul class="paragraphBtn">
                                  <li class="btnStyle">第一段</li>
                                  <li>第二段</li>
                                  <li>第三段</li>
                                  <li>第四段</li>
                                </ul>
                                <ul class="paragraphBtn">
                                  <li class="btnStyle">12-21 周三</li>
                                  <li>12-22 周四</li>
                                  <li>12-23 周五</li>
                                  <li>12-24 周六</li>
                                  <li>12-25 周日</li>
                                  <li>12-26 周一</li>
                                  <li>12-27 周二</li>
                                </ul>
                                <ul class="paragraphListInfo">
                                  <li>
                                    <div class="imgIconDiv"><img src="logoIcon.png"><p>首航CA1890</p></div>
                                    <div class="distanceTimeDiv">
                                        <span class="chufaCS"><b>06:30</b><p>首都T1</p></span>
                                        <span class="shiDuan">3h23m</span>
                                        <span class="daodaCS"><b>10:20</b><p>悉尼机场</p></span>
                                    </div>
                                    <div class="moneyDiv"><i class="fa fa-cny"></i>4986</div>
                                  </li>
                                  <li>
                                    <div class="imgIconDiv"><img src="logoIcon.png"><p>首航CA1890</p></div>
                                    <div class="distanceTimeDiv">
                                        <span class="chufaCS"><b>06:30</b><p>首都T1</p></span>
                                        <span class="shiDuan">3h23m</span>
                                        <span class="daodaCS"><b>10:20</b><p>悉尼机场</p></span>
                                    </div>
                                    <div class="moneyDiv"><i class="fa fa-cny"></i>4986</div>
                                  </li>
                                </ul>
                              </div>
                          </div>
                      </div>
                      <div class="tab-pane" id="tab_2">
                          <table class="scatteredTable1">
                              <tr>
                                <td><label>搜索筛选：</label></td>
                                <td><input type="radio" name="internat1" value="1"><span>国际内陆</span></td>
                                <td colspan="2"><input type="radio" name="internat1" value="2"><span>国际</span></td>
                              </tr>
                              <tr>
                                <td><label>航程类型：</label></td>
                                <td><input type="radio" name="voyageType1" value="1" onclick="radioFunct1()"><span>单程</span></td>
                                <td><input type="radio" name="voyageType1" value="2" onclick="radioFunct1()"><span>往返</span></td>
                                <td><input type="radio" name="voyageType1" value="3" onclick="radioFunct1()"><span>多程</span></td>
                              </tr>
                          </table><!--搜索筛选/航程类型-->
                          <table class="scatteredTable2">
                            <tr>
                              <td><label>出发城市：</label></td>
                              <td>
                              	<input type="text" class="form-control input-sm" placeholder="拼音/三字代码">
                              </td>
                              <td class="untilTd"><i class="fa fa-minus"></i></td>
                              <td><label>到达城市：</label></td>
                              <td>
                              	<input type="text" class="form-control input-sm" placeholder="拼音/三字代码">
                              </td>
                              <td class="untilTd1"></td><!--空白处 可以忽略-->
                              <td><label>出发日期：</label></td>
                              <td>
                              	<input type="text" class="form-control input-sm" placeholder="2016-12-21">
                              </td>
                              <td class="setoutLabel"><label>返回日期：</label></td>
                              <td class="setoutinput">
                              	<input type="text" class="form-control input-sm" placeholder="2016-12-25">
                              </td>
                              <td class="addIconTd none"><i class="glyphicon glyphicon-plus addMore"></i></td>
                            </tr>
                            <tr class="hideTr none">
                              <td><label>出发城市：</label></td>
                              <td>
                              	<input type="text" class="form-control input-sm" placeholder="拼音/三字代码">
                              </td>
                              <td class="untilTd"><i class="fa fa-minus"></i></td>
                              <td><label>到达城市：</label></td>
                              <td>
                              	<input type="text" class="form-control input-sm" placeholder="拼音/三字代码">
                              </td>
                              <td class="untilTd1"></td><!--空白处 可以忽略-->
                              <td><label>出发日期：</label></td>
                              <td>
                              	<input type="text" class="form-control input-sm" placeholder="2016-12-21">
                              </td>
                              <td class="setoutLabel"><label>返回日期：</label></td>
                              <td class="setoutinput">
                              	<input type="text" class="form-control input-sm" placeholder="2016-12-25">
                              </td>
                              <td class="addIconTd none"><i class="glyphicon glyphicon-plus addMore"></i></td>
                            </tr>
                          </table>
                          <table class="scatteredTable3">
                            <tr>
                              <td><label>舱位等级：</label></td>
                              <td>
                                 <select class="form-control input-sm selectWid1">
                                   <option value="1">经济舱</option>
                                   <option value="2">超级经济舱</option>
                                   <option value="3">商务舱</option>
                                   <option value="4">头等舱</option>
                                 </select>
                              </td>
                              <td><label>航空公司：</label></td>
                              <td>
                              	<input type="text" class="form-control input-sm" placeholder="(选填)中文/代码">
                              </td>
                              <td>
                              	<button class="btn btn-primary btn-sm">搜索机票</button>
                              </td>
                            </tr>
                          </table>

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
                              <tbody>
                                <tr>
                                  <td>120000069</td>
                                  <td>散客</td>
                                  <td>aksdfkjsbdn</td>
                                  <td>西安-意大利</td>
                                  <td>2016-10-22</td>
                                  <td>￥4990</td>
                                  <td>6</td>
                                  <td>2016-10-21</td>
                                  <td>马云</td>
                                </tr>
                                <tr>
                                  <td>120120030</td>
                                  <td>散客</td>
                                  <td>aksdfkjsbdn</td>
                                  <td>西安-澳大利亚</td>
                                  <td>2016-12-22</td>
                                  <td>￥4890</td>
                                  <td>6</td>
                                  <td>2016-10-21</td>
                                  <td>刘强东</td>
                                </tr>
                                <tr>
                                  <td>120120320</td>
                                  <td>团队</td>
                                  <td>aksdfkjsbdn</td>
                                  <td>西安-韩国</td>
                                  <td>2016-12-22</td>
                                  <td>￥9890</td>
                                  <td>6</td>
                                  <td>2016-10-21</td>
                                  <td>王健林</td>
                                </tr>
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
		<!-- 客户信息 js -->
		<script src="${base}/admin/searchTicket/searchCustomerInfo.js"></script>
		<!-- 订单信息 js -->
		<script src="${base}/admin/searchTicket/searchOrderInfo.js"></script>
		<!-- layer -->
		<script src="${base}/common/js/layer/layer.js"></script>
		<script type="text/javascript">
	      $(function(){
            $('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
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
	        	//清除文本框数据
	        	
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
	  </script>
	  
	  <!-- 每次添加旅程时  清除多程 文本框内容 -->
	  <script type="text/javascript">
	  	
	  </script>
	  
	  <!-- select2 隐藏域赋值 -->
	  <script type="text/javascript">
	  	/* 出发城市 */
	 	function outCityNameOpt(){
	 		var cityName = $('#outCity').find("option:selected").text();
			$("#outCityName").val(cityName);
			var selectedCityId = $("#outCity").select2("val");
			$("#outCityCode").val(selectedCityId);
		}
	  	/* 抵达城市 */
	 	function arriveCityNameOpt(){
	 		var cityName = $('#singleArriveCity').find("option:selected").text();
			$("#arriveCityName").val(cityName);
			var selectedCityId = $("#singleArriveCity").select2("val");
			$("#arriveCityCode").val(selectedCityId);
		}
	  	/* 航空公司 */
	  	function airlineNameOpt(){
	 		var airName = $('#airline').find("option:selected").text();
			$("#airlineName").val(airName);
		}
	  </script>
	
</body>
</html>
