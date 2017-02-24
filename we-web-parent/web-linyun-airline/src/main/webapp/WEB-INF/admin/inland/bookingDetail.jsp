<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>预定订单详情</title>
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
  <link rel="stylesheet" href="${base }/public/dist/css/queryOrderDetail.css">
  <link rel="stylesheet" href="${base }/public/dist/css/bookingOrderDetail.css"><!--本页面styleFlie-->
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
        <div class="row col-sm-9">
          <div class="customerInfo"><!--客户信息-->
               <div class="infoTop">
                 <p>客户信息</p>
                 <div class="infoTopContent">
                   <span>${obj.orderinfo.ordersnum }</span>
                   <select class="form-control input-sm conSelect cf">
                     <option value="1">查询</option>
                     <option value="2">预定</option>
                     <option value="3">出票</option>
                     <option value="4">开票</option>
                     <option value="5">关闭</option>
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
                     <td><select id="linkName" name="linkName" type="text" class="form-control input-sm" multiple="multiple" placeholder="请输入客户姓名">
                     	<c:forEach items="${obj.customerInfos }" var="one"> 
                   			<c:choose>
	                   			<c:when test="${obj.orderinfo.userid  eq one.id  }">
									<option value="${one.id }" selected="selected">${one.linkMan }</option>
	                   			</c:when>
	                   			<c:otherwise>
		                     		<option value="${one.id }">${one.linkMan }</option>
	                   			</c:otherwise>
                    		</c:choose>
                     	</c:forEach>
                     	</select>
                     	<input id="customerId" name="customerId" type="hidden" value="${obj.custominfo.id }"/>
                     	<input id="id" name="id" type="hidden" value="${obj.orderinfo.id }"></td>
                     <td><label style="position: relative;top: 4px;">结算方式：</label></td>
                     <td colspan="3"><pre class="preTxt">不限 信用额度：0  临时额度：0  历史欠款：0  预存款：0</pre></td>
                     <td><input type="button" value="清空" class="btn btn-primary btn-sm clearBtn"><i class="UnderIcon fa fa-chevron-circle-down"></i></td>
                   </tr>
                 </table>

                 <table class="hideTable none">
                   <tr>
                     <td><label>公司简称：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入公司简称" value="${obj.custominfo.shortName }" readonly="true"></td>
                     <td><label>电话：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入电话" value="${obj.custominfo.telephone }" readonly="true"></td>
                     <td><label>地址：</label></td>
                     <td colspan="3"><input type="text" class="form-control input-sm addressInput" placeholder="请输入地址" value="${obj.custominfo.address }" readonly="true"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>负责人：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入负责人" value="${obj.responsible }" readonly="true"></td>
                     <td><label>网址：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入网址" value="${obj.custominfo.siteUrl }" readonly="true"></td>
                     <td><label>传真：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入传真" value="${obj.custominfo.fax }" readonly="true"></td>
                     <td><label>出发城市：</label></td>
                     <td><input type="text" class="form-control input-sm addressInput" placeholder="请输入出发城市" value="${obj.custominfo.id }" readonly="true"></td>
                     
                   </tr>
                 </table>

               </div>
          </div><!--客户信息-->


          <div class="customerInfo"><!--客户需求-->
               <div class="infoTop">
                 <p>客户需求</p>
               </div>
               <c:forEach var="customneed" items="${obj.customneedinfo }">
               <div id="infofooter" class="infofooter">
                <div class="DemandDiv">
                 <span class="titleNum">1</span>
                 <a href="javascript:;" class="btn btn-primary btn-sm addDemand none">游客模板</a>
                 <a href="javascript:;" class="btn btn-primary btn-sm addDemand none">上传游客</a>
                 <a href="javascript:;" class="btn btn-primary btn-sm addDemand none"><b>+</b>&nbsp;&nbsp;需求</a>
                 <input type="hidden" id="customneedid" name="customneedid" value="${customneed.cusinfo.id }">
                 <table class="cloTable">
                   <tr>
                     <td><label>出发城市：</label></td>
                     <td><select id="leavecity" name="leavecity" class="form-control input-sm select2" multiple="multiple" placeholder="PEK(北京)">
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
                     <td><select id="arrivecity" name="arrivecity" class="form-control input-sm" multiple="multiple" placeholder="SYD(悉尼)">
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
                     <td><input id="leavedate" name="leavedate" type="text" class="form-control input-sm textWid" placeholder="2017-02-22" onFocus="WdatePicker({minDate:'%y-%M-%d'})" value="${customneed.cusinfo.leavetdate }"/></td>
                     <td><label>人数：</label></td>
                     <td><input id="peoplecount" name="peoplecount" type="text" class="form-control input-sm textWid" value="${customneed.cusinfo.peoplecount }"/></td>
                     <td><label class="labelWid">早中晚：</label></td>
                     <td>
                       <select id="tickettype" name="tickettype" class="form-control input-sm textWid" value="${customneed.cusinfo.tickettype }">
	                         <option value="1">早</option>
	                         <option value="2">中</option>
	                         <option value="3">晚</option>
	                       </select>
                     </td>
                   </tr>
                   <c:choose>
	                   		<c:when test="${fn:length(customneed.ailines)>0}">
			                   <c:forEach var="airline" items="${customneed.ailines }" varStatus="status">
			                   
			                   <tr name="airlineinfo">
			                     <td></span><label>航空公司：</label></td>
			                     <td><select id="aircom" name="aircom" class="form-control input-sm"  multiple="multiple" placeholder="">
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
			                     <td><select id="ailinenum" name="ailinenum" class="form-control input-sm"  multiple="multiple" placeholder="SYD(悉尼)">
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
			                     <td><input id="leavetime" name="leavetime" type="text" class="form-control input-sm textWid" placeholder="" value="${airline.leavetime }"/></td>
			                     <td><label>抵达时间：</label></td>
			                     <td><input id="arrivetime" name="arrivetime" type="text" class="form-control input-sm textWid" value="${airline.arrivetime }"/></td>
			                     <td><label class="labelWid">成本价：</label></td>
			                     <td><input id="formprice" name="formprice" type="text" class="form-control input-sm textWid" value="${airline.formprice }"/></td>
			                     <td><label class="labelWid">销售价：</label></td>
			                     <td><input id="price" name="price" type="text" class="form-control input-sm textWid" value="${airline.price }"/></td>
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
			                     <td><input id="leavetime" name="leavetime" type="text" class="form-control input-sm textWid" placeholder=""/></td>
			                     <td><label>抵达时间：</label></td>
			                     <td><input id="arrivetime" name="arrivetime" type="text" class="form-control input-sm textWid" /></td>
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
                     <td colspan="12" class="addPNR">
                        <table class="table table-bordered table-hover">
                         <thead>
                          <tr>
                            <td>PNR</td>
                            <td>成本单价</td>
                            <td>成本总价</td>
                            <td>销售单价</td>
                            <td>销售总价</td>
                            <td>人数</td>
                            <td>登录帐号</td>
                            <td>操作</td>
                          </tr>
                         </thead>
                         <tbody>
                          <tr>
                            <td> </td>
                            <td> </td>
                            <td> </td>
                            <td> </td>
                            <td> </td>
                            <td> </td>
                            <td> </td>
                            <td><a href="javascript:;" class="PNRdetails">详情</a></td>
                          </tr>
                         </tbody>
                        </table>
                     </td>
                     <td class="PNRbtnTD none">
                        <a href="javascript:;" class="btn btn-primary btn-sm PNRbtn"><b>+</b>&nbsp;&nbsp;PNR</a>
                     </td>
                   </tr>
                   <tr>
                     <td></span><label>备注：</label></td>
                     <td colspan="11"><input type="text" id="remark" name="remark" class="form-control input-sm noteText" placeholder=" " value="${customneed.cusinfo.remark }"></td>
                   </tr>
                 </table>
                </div>
               </div>
               </c:forEach>
          </div><!--end 客户需求-->

          <div class="customerInfo"><!--信息-->
            <div class="infoTop">
                 <p>信息</p>
            </div>
            <div class="infofooter">
                 <table>
                   <tr>
                     <td><label>客户团号：</label></td>
                     <td><input id="cusgroupnum" name="cusgroupnum" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label>类型：</label></td>
                     <td>
                        <select id="teamtype" name="teamtype" class="form-control input-sm">
                            <option>请选择</option>
                            <option value="1">散</option>
                            <option value="2">团</option>
                        </select>
                     </td>
                     <td><label>内陆跨海：</label></td>
                     <td>
                        <select class="form-control input-sm">
                            <option>请选择</option>
                            <option>跨海</option>
                            <option>新西兰内陆</option>
                            <option>澳洲内陆</option>
                        </select>
                     </td>
                     <td><label>开票日期：</label></td>
                     <td><input id="billingdate" name="billingdate" type="text" class="form-control input-sm" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>付款币种：</label></td>
                     <td>
                        <select id="paycurrency" name="paycurrency" class="form-control input-sm">
                            <option>请选择</option>
                            <option></option>
                            <option></option>
                        </select>
                     </td>
                     <td><label>付款方式：</label></td>
                     <td>
                        <select id="paymethod" name="paymethod" class="form-control input-sm">
                            <option>请选择</option>
                            <option>第三方支付</option>
                            <option>国际段专用卡</option>
                        </select>
                     </td>
                     <td><label>销售：</label></td>
                     <td><input id="salesperson" name="salesperson" type="text" class="form-control input-sm" disabled="disabled"></td>
                     <td><label>开票人：</label></td>
                     <td><input id="issuer" name="issuer" type="text" class="form-control input-sm" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>人头数：</label></td>
                     <td><input id="personcount" name="personcount" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label>是否结算：</label></td>
                     <td>
                        <select id="billingstatus" name="billingstatus" class="form-control input-sm">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                     </td>
                     <td><label>进澳时间：</label></td>
                     <td><input id="enterausdate" name="enterausdate" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label>出澳时间：</label></td>
                     <td><input id="outausdate" name="outausdate" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>应收：</label></td>
                     <td><input id="receivable" name="receivable" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label><a href="javascript:;" class="jianMian">减免</a>：</label></td>
                     <td><input id="relief" name="relief" type="text" class="form-control input-sm" disabled="disabled"></td>
                     <td><label>实收合计：</label></td>
                     <td><input id="incometotal" name="incometotal" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>成本合计：</label></td>
                     <td><input id="costtotal" name="costtotal" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label>应返：</label></td>
                     <td><input id="returntotal" name="returntotal" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                     <td><label>利润合计：</label></td>
                     <td><input id="profittotal" name="profittotal" type="text" class="form-control input-sm disab" disabled="disabled"></td>
                   </tr>
                 </table>
            </div>
          </div><!--end 信息-->
          
        </div>
        <div class="col-sm-3 rightRemind">
            <div class="infoTop">
              <p>提醒设置</p>
            </div>
            <div class="infofooter">
                 <table class="remindSet">
                   <tr>
                     <td>
                       <select class="form-control input-sm">
                         <option>询单</option>
                         <option>账单</option>
                         <option>账期</option>
                       </select>
                     </td>
                     <td><input type="text" class="form-control input-sm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" placeholder="2017-02-15 09:30" disabled="disabled"></td>
                     <td>
                       <select class="form-control input-sm">
                         <option>不重复</option>
                         <option>重复</option>
                       </select>
                     </td>
                   </tr>
                 </table>
            </div>

            <div class="infoTop">
              <p>日志</p>
            </div>
            <div class="infofooter">
                 
            </div>
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
  <!-- My97DatePicker --> 
  <script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
  <script src="${base }/admin/order/bookingorder.js"></script>
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
          $('.disab').removeAttr("disabled");//信息模块 input 禁止编辑的状态
          $('.PNRbtnTD').removeClass('none');//+PNR 按钮 显示
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
          $('.disab').attr("disabled",'disabled');//信息模块 input 添加 不可编辑属性
          $('.PNRbtnTD').addClass('none');//+PNR 按钮 隐藏
        });

        $('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
          $('.hideTable').toggle('400');
        });

        $('.clearBtn').click(function(){//清楚按钮 隐藏
          $('.hideTable').hide('400');
        });

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

        //点击 +PNR 弹框
        $('.PNRbtn').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['830px', '385px'],
                content: ['bookingOrderDetail-+PNR.html','no']
              });
        });

        //点击 详情 弹框
        $('.PNRdetails').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['830px', '475px'],
                content: ['bookingOrderDetail-PNRdetails.html','no']
              });
        });

        //点击 减免 弹框
        $('.jianMian').click(function(){
            layer.open({
                type: 2,
                title:false,
                skin: false, //加上边框
                closeBtn:false,//默认 右上角关闭按钮 是否显示
                shadeClose:true,
                area: ['840px', '230px'],
                content: ['bookingOrderDetail-mitigate.html','no']
              });
        });
    });
  </script>

  <script type="text/javascript">
//保存订单
  $('.btnSave').click(function(){
  	//var data = [];
		var customdata = {};
		var customerId = $('#customerId').val();
		customdata.customerId = customerId;
		var id = $('#id').val();
		customdata.id = id;
		var generateOrder = $('#generateOrder').val();
		customdata.generateOrder = $("#generateOrder").is(':checked');
		var orderType = $('#orderType').val();
		customdata.orderType = orderType;
		var row = [];
		$('.DemandDiv').each(function(i){
			var row1 = {};
			var leavecity = $(this).find('[name=leavecity]').val();
			//出发城市
			if (leavecity) {
				leavecity = leavecity.join(',');
			}
			row1.leavecity = leavecity;
			//抵达城市
			var arrivecity = $(this).find('[name=arrivecity]').val();
			if (arrivecity) {
				arrivecity = arrivecity.join(',');
			}
			row1.arrivecity = arrivecity;
			row1.customneedid = $(this).find('[name=customneedid]').val();
			row1.leavedate = $(this).find('[name=leavedate]').val();
			row1.peoplecount = $(this).find('[name=peoplecount]').val();
			row1.tickettype = $(this).find('[name=tickettype]').val();
			row1.remark = $(this).find('[name=remark]').val();
			var airrows = [];
			$(this).find('[name=airlineinfo]').each(function(i){
				var airrow = {};
				var aircom = $(this).find('[name=aircom]').val();
				if (aircom) {
					aircom = aircom.join(',');
	  			}
				airrow.aircom = aircom;
				var ailinenum = $(this).find('[name=ailinenum]').val();
				if (ailinenum) {
					ailinenum = ailinenum.join(',');
	  			}
				airrow.ailinenum = ailinenum;
				airrow.airlineid = $(this).find('[name=airlineid]').val();
				airrow.leavetime = $(this).find('[name=leavetime]').val();
				airrow.arrivetime = $(this).find('[name=arrivetime]').val();
				airrow.formprice = $(this).find('[name=formprice]').val();
				airrow.price = $(this).find('[name=price]').val();
				airrows.push(airrow);
			});
			row1.airinfo = airrows;
			row.push(row1);
		});
		customdata.customdata=row;
		//data.push(customdata);
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
          },
          error: function (xhr) {
          	layer.msg("保存失败","",3000);
          } 
      });
  }); 
  </script>
</body>
</html>