<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>+PNR</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base }/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base }/public/dist/css/bookingOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="button" id="submit" class="btn btn-primary right btn-sm" onclick="savePnrInfo();" value="保存"/>
            <h4>+PNR</h4>
          </div>
          <div class="modal-body" style="height:500px;overflow-y: auto;">
            <div class="tab-content backcard">
            	<form id="addPnrForm">
            	<input id="needid" name="needid" type="hidden" value="${obj.needid }" >
                <%-- <table class="PNRtable">
                  <tr>
                    <td>PNR：</td>
                    <td><input id="pnr" name="pnr" type="text" class="form-control input-sm PNRlength"></td>
                    <td>登录帐号：</td>
                    <td><select id="loginid" name="loginid" class="form-control input-sm">
                    		<option value="">请选择</option>
	                    	<c:forEach items="${obj.loginselect }" var="one">
		                    		<option value="${one.id }">${one.loginNumName }</option>
	                    	</c:forEach>
                    	</select>
                    </td>
                    <td>人数：</td>
                    <td><input id="peoplecount" name="peoplecount" type="text" class="form-control input-sm mustNumber"></td></td>
                    <td>币种：</td>
                    <td>
                      <select id="currency" name="currency" class="form-control input-sm">
                        <option value="">请选择</option>
                        <c:forEach items="${obj.bzcode }" var="one"> 
		                     <option value="${one.dictCode }">${one.dictCode }</option>
                     	</c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td>成本单价：</td>
                    <td><input id="costprice" name="costprice" type="text" class="form-control input-sm mustNumberPoint"></td>
                    <td>成本总价：</td>
                    <td><input id="costpricesum" name="costpricesum" type="text" class="form-control input-sm mustNumberPoint"></td></td>
                    <td>销售单价：</td>
                    <td><input id="salesprice" name="salesprice" type="text" class="form-control input-sm mustNumberPoint"></td></td>
                    <td>销售总价：</td>
                    <td><input id="salespricesum" name="salespricesum" type="text" class="form-control input-sm mustNumberPoint"></td></td>
                  </tr>
                </table> --%>
				<table class="PNRtable">
                  <tr>
                    <td>PNR：</td>
                    <td><input id="pNR" name="pNR" type="text" class="form-control input-sm PNRlength"></td>
                    <td>登录帐号：</td>
                    <td><select id="loginid" name="loginid" class="form-control input-sm">
                    		<option value="">请选择</option>
	                    	<c:forEach items="${obj.loginselect }" var="one">
		                    		<option value="${one.id }">${one.loginNumName }</option>
	                    	</c:forEach>
                    	</select>
                    </td>
                    <td>币种：</td>
                    <td>
                      <select id="currency" name="currency" class="form-control input-sm">
                        <option value="">请选择</option>
                        <c:forEach items="${obj.bzcode }" var="one"> 
		                     <option value="${one.dictCode }">${one.dictCode }</option>
                     	</c:forEach>
                      </select>
                    </td>
                    <td>平均汇率：</td>
                    <td><input id="averagerate" name="averagerate" type="text" class="form-control input-sm mustNumberPoint"></td>
                    <td>实时汇率：</td>
                    <td><input id="currentrate" name="currentrate" type="text" class="form-control input-sm mustNumberPoint"></td>
                  </tr>
                  <tr class="priceinfo">
                  	<td>成人数：</td>
                    <td><input id="adultcount" name="adultcount" type="text" class="form-control input-sm mustNumber peoplecount autojisuan"></td>
                    <td>成本单价：</td>
                    <td><input id="adultcostprice" name="adultcostprice" type="text" class="form-control input-sm mustNumberPoint costprice autojisuan"></td>
                    <td>成本总价：</td>
                    <td><input id="adultcostpricesum" name="adultcostpricesum" type="text" class="form-control input-sm mustNumberPoint costpricesum"></td>
                    <td>销售单价：</td>
                    <td><input id="adultsalesprice" name="adultsalesprice" type="text" class="form-control input-sm mustNumberPoint salesprice"></td>
                    <td>销售总价：</td>
                    <td><input id="adultsalespricesum" name="adultsalespricesum" type="text" class="form-control input-sm mustNumberPoint salespricesum"></td>
                  </tr>
                  <tr class="priceinfo">
                  	<td>儿童数：</td>
                    <td><input id="childcount" name="childcount" type="text" class="form-control input-sm mustNumber peoplecount autojisuan"></td>
                    <td>成本单价：</td>
                    <td><input id="childcostprice" name="childcostprice" type="text" class="form-control input-sm mustNumberPoint costprice autojisuan"></td>
                    <td>成本总价：</td>
                    <td><input id="childcostpricesum" name="childcostpricesum" type="text" class="form-control input-sm mustNumberPoint costpricesum"></td>
                    <td>销售单价：</td>
                    <td><input id="childsalesprice" name="childsalesprice" type="text" class="form-control input-sm mustNumberPoint salesprice"></td>
                    <td>销售总价：</td>
                    <td><input id="childsalespricesum" name="childsalespricesum" type="text" class="form-control input-sm mustNumberPoint salespricesum"></td>
                  </tr>
                  <tr class="priceinfo">
                  	<td>婴儿数：</td>
                    <td><input id="babycount" name="babycount" type="text" class="form-control input-sm mustNumber peoplecount autojisuan"></td>
                    <td>成本单价：</td>
                    <td><input id="babycostprice" name="babycostprice" type="text" class="form-control input-sm mustNumberPoint costprice autojisuan"></td>
                    <td>成本总价：</td>
                    <td><input id="babycostpricesum" name="babycostpricesum" type="text" class="form-control input-sm mustNumberPoint costpricesum"></td>
                    <td>销售单价：</td>
                    <td><input id="babysalesprice" name="babysalesprice" type="text" class="form-control input-sm mustNumberPoint salesprice"></td>
                    <td>销售总价：</td>
                    <td><input id="babysalespricesum" name="babysalespricesum" type="text" class="form-control input-sm mustNumberPoint salespricesum"></td>
                  </tr>
                  <tr>
                  	<td> </td>
                    <td> </td>
                    <td> </td>
                    <td> </td>
                    <td>成本合计：</td>
                    <td><input id="costpricesum" name="costpricesum" type="text" class="form-control input-sm mustNumberPoint"></td>
                    <td> </td>
                    <td> </td>
                    <td>销售合计：</td>
                    <td><input id="salespricesum" name="salespricesum" type="text" class="form-control input-sm mustNumberPoint"></td>
                  </tr>
                  <tr>
                  	<td> </td>
                    <td> </td>
                    <td> </td>
                    <td colspan="2">成本RMB合计：</td>
                    <td><input id="costpricesumrmb" name="costpricesumrmb" type="text" class="form-control input-sm mustNumberPoint"></td>
                    <td colspan="3">销售RMB合计：</td>
                    <td><input id="salespricesumrmb" name="salespricesumrmb" type="text" class="form-control input-sm mustNumberPoint"></td>
                  </tr>
                </table>
                <div class="multiselectDiv">
                  <div class="row">
                    <div class="col-sm-5">
                      <p class="multiselectP">请选择</p>
                      <select name="from" id="multiselect" class="form-control" size="8" multiple="multiple">
                      	<c:forEach items="${obj.visitors }" var="one"> 
		                     <option value="${one.id }" data-position="${one.id }">${one.visitorname }</option>
                     	</c:forEach>
                      </select>
                    </div>
                    
                    <div class="col-sm-2">
                      <button type="button" id="multiselect_rightAll" class="btn btn-block"><i class="fa fa-angle-double-right"></i></button>
                      <button type="button" id="multiselect_rightSelected" class="btn btn-block"><i class="fa fa-angle-right"></i></button>
                      <button type="button" id="multiselect_leftSelected" class="btn btn-block"><i class="fa fa-angle-left"></i></button>
                      <button type="button" id="multiselect_leftAll" class="btn btn-block"><i class="fa fa-angle-double-left"></i></button>
                    </div>
                    
                    <div class="col-sm-5">
                      <p class="multiselectP">已选择</p>
                      <select name="to" id="multiselect_to" class="form-control" size="8" multiple="multiple"></select>
                    </div>
                  </div>
                </div>
                </form>
            </div>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
    <script src="${base }/admin/order/ordercommon.js"></script>

	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
    <script src="${base }/public/dist/js/multiselect.min.js"></script>
    <script src="${base }/public/dist/js/prettify.min.js"></script>
    <!--layer -->
  <script src="${base}/common/js/layer/layer.js"></script>
    <script type="text/javascript">
  //票价折扣
 	var discountFare = '${obj.custominfo.discountFare}';
 	//手续费
 	var fees = '${obj.custominfo.fees}'; 
      $(function(){
    	  var orderCount = 0;
          $('#multiselect').multiselect({
        	  keepRenderingSort:true
          });
    	  /* var visitors = eval('${obj.visitors}');
    	  var options = '';
    	  $.each(visitors, function(name, value) {
    		  var option = '<option value='+value.id+' position="'+name+'">'+value.visitorname+'</option>';
    		  options += option;
  		  });
    	  $('#multiselect').html(options); */
      });
      //关闭窗口
      function closewindow(){
  		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
  		parent.layer.close(index);
  	}
     //保存PNR信息
     function savePnrInfo(){
    	 var visitor = '';
    	 $('#multiselect_to option').each(function(i){
    		 visitor += $(this).val() + ',';
    	 });
    	 visitor = visitor.substr(0,visitor.length-1);
    	 var addPnrData = getFormJson('#addPnrForm');
    	 addPnrData.visitor = visitor;
    	 addPnrData.peoplecount = $('#peoplecount').val();
    	 //alert(JSON.stringify(addPnrData));
    	 layer.load(1);
    	 $.ajax({ 
 			type: 'POST', 
 			data: addPnrData, 
 			url: '${base}/admin/inland/addPnrInfo.html',
           success: function (data) { 
           	//alert("添加成功");
           	//location.reload();
           	layer.closeAll('loading');
           	closewindow();
           	window.parent.successCallback('1');
           },
           error: function (xhr) {
           	layer.msg("保存失败","",3000);
           } 
       });
     }
     
     
   //获取form下所有值
     function getFormJson(form) {
   	  var o = {};
   	  var a = $(form).serializeArray();
   	  $.each(a, function (){
   		  if (o[this.name] != undefined) {
   		  	if (!o[this.name].push) {
   	  	  		o[this.name] = [o[this.name]];
   	  		}
   	  		o[this.name].push(this.value || '');
   	  	  } else {
   	  	  	o[this.name] = this.value || '';
   	  	  }
   	  });
   	  return o;
     }
   //给人数赋值
   function assignment(){
	   var count = $('#multiselect_to option').length; 
	   if(count == 0){
		   $('#peoplecount').val('');
	   }else{
		   $('#peoplecount').val(count);
	   }
   }
   
   $('.autojisuan').on('input',function(){
	   var parentdiv = $(this).parent().parent();
	   //成本单价
	   var costprice = parentdiv.find('.costprice').val();
	   //人数
	   var peoplecount = parentdiv.find('.peoplecount').val();
	   //自动填充销售单价
	   var salesprice = parseFloat(costprice) * parseFloat(discountFare)/100 + parseFloat(fees);
	   if(costprice){
	 		if(isNaN(salesprice)){
	 			parentdiv.find('.salesprice').val('');
	 		}else{
	 			parentdiv.find('.salesprice').val(salesprice.toFixed(2));
	 		}
	 	}else{
	 		parentdiv.find('.salesprice').val('');
	 	}
	   if(peoplecount){
		   var costpricesum = parseFloat(costprice) * parseInt(peoplecount);
		   if(!isNaN(costpricesum)){
			   parentdiv.find('.costpricesum').val(costpricesum.toFixed(2));
		   }
		   var salespricesum = parseFloat(salesprice) * parseInt(peoplecount);
		   if(!isNaN(salespricesum)){
			   parentdiv.find('.salespricesum').val(salespricesum.toFixed(2));
		   }
	   }
	   setSumPrice();
   });
   $('.salesprice').on('input',function(){
	   var parentdiv = $(this).parent().parent();
	   //人数
	   var peoplecount = 0;
	   if(parentdiv.find('.peoplecount').val()){
		   peoplecount = parseInt(parentdiv.find('.peoplecount').val());
	   }
	   var salesprice = 0;
	   if($(this).val()){
		   salesprice = $(this).val();
	   }
	   var salespricesum = salesprice * peoplecount;
	   if(salespricesum != 0){
		   parentdiv.find('.salespricesum').val(salespricesum.toFixed(2));
	   }
	   setSumPrice();
   });
   $('#averagerate').on('input',function(){
  	 setSumPrice();
   });
   $('#currentrate').on('input',function(){
  	 setSumPrice();
   });
   function setSumPrice(){
	   var costpricesum = 0;
	   var salespricesum = 0;
	   $('.priceinfo').each(function(i){
		   if($(this).find('.costpricesum').val()){
			   costpricesum += parseFloat($(this).find('.costpricesum').val());
		   }
		   if($(this).find('.salespricesum').val()){
			   salespricesum += parseFloat($(this).find('.salespricesum').val());
		   }
	   });
	   if(costpricesum > 0){
		   $('#costpricesum').val(costpricesum.toFixed(2));
		   var averagerate = 0;
		   if($('#averagerate').val()){
			   averagerate = parseFloat($('#averagerate').val());
			   var costpricesumrmb = costpricesum * averagerate;
			   if(costpricesumrmb > 0){
				   $('#costpricesumrmb').val(costpricesumrmb.toFixed(2));
			   }
		   }
	   }
	   if(salespricesum > 0){
		   $('#salespricesum').val(salespricesum.toFixed(2));
		   var currentrate = 0;
		   if($('#currentrate').val()){
			   currentrate = parseFloat($('#currentrate').val());
			   var salespricesumrmb = salespricesum * currentrate;
			   if(salespricesumrmb > 0){
				   $('#salespricesumrmb').val(salespricesumrmb.toFixed(2));
			   }
		   }
	   }
   }
  </script>
</body>
</html>	
