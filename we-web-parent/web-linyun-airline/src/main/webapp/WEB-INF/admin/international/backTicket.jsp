<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>退票</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="${base}/public/ionicons/css/ionicons.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<style type="text/css">
		#backTicketForm{margin-top:5px;margin-right:0px;}
		.sc{position: relative;top: -28px;width: 74px;opacity: 0;cursor: pointer;}
		.FileDiv{border: solid 1px #2f7298;height: 29px;line-height: 28px;width:74px;border-radius: 3px;background: #3c8dbc;color: #fff;text-align: center;display: block;}
		.FileDiv:hover{color: #fff;background-color:#367da7;}
		.fileUL {margin-left:20.6%;}
		.fileUL li{float: left;list-style: none;}
		#fileName,.fileDelete{line-height: 29px;padding-left: 10px;font-size: 12px;color:#656565;}
		.fileDelete{font-size: 14px;color: #3c8dbc;}
		.addIcon {top: 3px;margin-left: 10px;}
		.removIcon { margin-top: 3px;margin-left:5px;}
		.allCentext{height:355px;overflow-y: auto;padding-bottom: 0;}
	</style>
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
            <h4 class="invoiceH4">退票</h4>
    </div>
      <div class="modal-body allCentext">
      	<form id="backTicketForm">
      	<input type="hidden" id="id" name="id" value="${obj.backinfo.id }">
      	<input type="hidden" id="visitorid" name="visitorid" value="${obj.visitorinfo.id }">
         <div class="form-group row"><!--退票人/电话/申请日期-->
                  <label class="col-sm-3 text-right padding">退票人：</label>
                  <div class="col-sm-3 padding"><input id="visitorname" name="visitorname" type="text" class="form-control input-sm" value="${empty obj.backinfo.id?obj.visitorinfo.visitorname:obj.backinfo.visitorname }"></div>
                  <label class="col-sm-1 text-right padding">电话：</label>
                  <div class="col-sm-3 padding"><input id="telephone" name="telephone" type="text" class="form-control input-sm" value="${empty obj.backinfo.id?obj.visitorinfo.phonenum:obj.backinfo.telephone }"></div>
         </div><!--end 退票人/电话/申请日期-->
         <div class="form-group row">
                  <label class="col-sm-3 text-right padding">申请日期：</label>
                  <div class="col-sm-3 padding"><input id="applydatestr" name="applydatestr" type="text" class="form-control input-sm" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${obj.backinfo.applydate }" pattern="yyyy-MM-dd" />"></div>
                  <label class="col-sm-1 text-right padding">金额：</label>
                  <div class="col-sm-3 padding"><input id="price" name="price" type="text" class="form-control input-sm" value="${obj.backinfo.price }"></div>
         </div>
         <div class="form-group row">
                  <label class="col-sm-3 text-right padding">税金：</label>
                  <div class="col-sm-3 padding"><input id="tax" name="tax" type="text" class="form-control input-sm" value="${obj.backinfo.tax }"></div>
                  <label class="col-sm-1 text-right padding">退款金额：</label>
                  <div class="col-sm-3 padding"><input id="backprice" name="backprice" type="text" class="form-control input-sm" value="${obj.backinfo.backprice }"></div>
         </div>
         <div class="form-group row"><!--金额/原因/退票状态-->
                  <label class="col-sm-3 text-right padding">原因：</label>
                  <div class="col-sm-3 padding">
                    <select id="reason" name="reason" class="form-control input-sm">
                      <c:forEach var="map" items="${obj.backreasonenum}" >
                   		<c:choose>
                   			<c:when test="${map.key eq obj.backinfo.reason}">
                 				<option value="${map.key}" selected="selected">${map.value}</option>
                   			</c:when>
                   			<c:otherwise>
                 				<option value="${map.key}">${map.value}</option>
                   			</c:otherwise>
                   		</c:choose>
					 </c:forEach>
                    </select>
                  </div>
                  <label class="col-sm-1 text-right padding">退票状态：</label>
                  <div class="col-sm-3 padding">
                    <select id="backstatus" name="backstatus" class="form-control input-sm">
                      <c:forEach var="map" items="${obj.backticketstatusenum}" >
                   		<c:choose>
                   			<c:when test="${map.key eq obj.backinfo.backstatus}">
                 				<option value="${map.key}" selected="selected">${map.value}</option>
                   			</c:when>
                   			<c:otherwise>
                 				<option value="${map.key}">${map.value}</option>
                   			</c:otherwise>
                   		</c:choose>
					 </c:forEach>
                    </select>
                  </div>
         </div><!--end 金额/原因/退票状态-->
         <div class="form-group row"><!--备注-->
                  <label class="col-sm-3 text-right padding">备注：</label>
                  <div class="col-sm-7 padding"><input id="remark" name="remark" type="text" class="form-control input-sm" value="${empty obj.backinfo.id?obj.visitorinfo.remark:obj.backinfo.remark }"></div>
         </div><!--end 备注-->
         <div class="form-group row cloneTR"><!--上传-->
			<ul class="fileUL">
               <li>
                 <a href="javascript:;" class="FileDiv">
                 	上传<input type="file" class="sc" id="sc" name="sc">
                 </a> 
               </li>
               <li><a href="javascript:;" id="fileName" name="fileName">未选择文件</a></li>
               <li><a href="javascript:;" class="glyphicon glyphicon-plus addIcon"></a></li>
            </ul>
            <input id="invoiceurl" name="invoiceurl" type="hidden" value="">         	
         </div><!--end 上传-->
         
         </form>
      </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- My97DatePicker --> 
    <script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!--layer -->
  	<script src="${base}/common/js/layer/layer.js"></script>
  	<script src="${base }/admin/order/invoiceupload.js"></script>
	<script type="text/javascript">
	  //关闭窗口
	  function closewindow(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
	  }
	  
	  $('#submit').click(function(){
		  layer.load(1);
		  $.ajax({ 
				type: 'POST', 
				data: $("#backTicketForm").serialize(), 
				url: '${base}/admin/international/saveBackTicketInfo.html',
	            success: function (data) { 
	            	layer.closeAll('loading');
	            	closewindow();
	            	window.parent.successCallback('3');
	            },
	            error: function (xhr) {
	            	layer.msg("退票失败","",3000);
	            } 
	        });
	  });
	  
	  $(function(){
	      /*----- + 按钮-----*/
	      $('.addIcon').click(function(){
	          var divTest = $(this).parents('.cloneTR'); 
	          var lastDiv = $('.cloneTR').last();
	          var newDiv = divTest.clone(false,true);
	          newDiv.find('[name=invoicenum]').val('');
	          newDiv.find('[name=invoicebalance]').val(''); 
	          newDiv.find('[name=fileName]').html('未选择文件');
	          newDiv.find('[name=invoiceurl]').val('');
	          lastDiv.after(newDiv);
	          var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
	          newDiv.find("p").html(No); 
	          newDiv.find('#preView').parent().remove();
	          newDiv.find('.deleteInvoice').parent().remove();
	          newDiv.find('.addIcon').parent().remove();
	          newDiv.find('.fileUL').append('<li><a href="javascript:;" class="glyphicon glyphicon-minus removIcon removTd"></a></li>');
	          $('.allCentext').scrollTop( $('.allCentext').height() );//点击圆圈加号 添加的内容默认显示出来 
	      });
	      /*----- - 按钮-----*/
	      $(document).on("click",".removIcon",function(){
	    	  var divTest = $(this).parents('.cloneTR');
	    	  var invoicebalance = divTest.find('[name=invoicebalance]').val(); 
	    	  if(invoicebalance){
	    		  var yubanlance = parseFloat($('#balance').html()) + parseFloat(invoicebalance);
	    		  $('#balance').html(yubanlance.toFixed(2));
	    		  $('#backupbalance').val(yubanlance.toFixed(2));
	    	  }
	    	  $('#thisval').val('');
	          $(this).parents('.cloneTR').remove();
	      });
	      
	      $(document).on('click','#preView',function(){
	   	  	  var invoiceurl = $(this).parent().parent().parent().find('[name=invoiceurl]').val();
	          document.getElementById('light').style.display='block';
	          document.getElementById('fapiaoid').src=invoiceurl; 
	      });
	      $(document).on('click','.deleteInvoice',function(){
	   	  	  var invoicedetaildiv = $(this).parent().parent().parent();
	   	  	  invoicedetaildiv.find('[name=invoiceurl]').val('');
	   	  	  invoicedetaildiv.find('[name=fileName]').html('未选择文件');
	   	  	  invoicedetaildiv.find('#preView').remove();
	   	  	  invoicedetaildiv.find('.deleteInvoice').remove();
	          document.getElementById('fapiaoid').src=''; 
	      });
 
 		});
	</script>
</body>
</html>