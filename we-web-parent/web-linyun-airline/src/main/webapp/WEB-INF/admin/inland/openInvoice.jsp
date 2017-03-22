<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>开发票</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${base }/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base }/public/ionicons/css/ionicons.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
	<link href="${base }/public/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="${base }/public/dist/css/inlandCross.css"><!--本页style-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" onclick="saveInvoiceInfo();" value="提交"/>
            <h4 class="invoiceH4">收款信息</h4>
    </div>
    <div style="height:550px; overflow-y:auto; ">
      <div class="modal-body">
      	 <input type="hidden" id="id" name="id" value="${obj.id }" >
         <table id="receivablesTable" class="table table-bordered table-hover">
                  <thead>
                    <tr>
                      <th>订单号</th>
                      <th>开票日期</th>
                      <th>客户团号</th>
                      <th>客户公司名称</th>
                      <th>联系人</th>
                      <th>开票人</th>
                      <th>金额</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach var="one" items="${obj.orders }">
                		<tr>
                			<td>${one.ordersnum }</td>
                			<td>${one.billingdate }</td>
                			<td>${one.cusgroupnum }</td>
                			<td>${one.shortName }</td>
                			<td>${one.linkMan }</td>
                			<td>${one.issuer }</td>
                			<td>${one.incometotal }</td>
                		</tr>
                	</c:forEach>
                  </tbody>
         </table>
         <table border="0" class="selectTable">
                  <tr>
                    <td>银行：</td>
                    <td>
                      <select class="form-control input-sm">
                           <c:forEach var="one" items="${obj.yhkSelect }">
                             <c:choose>
                             	<c:when test="${one.id eq obj.receive.bankcardid }">
                             		<option value="${one.id }" selected="selected">${one.dictName }</option>
                             	</c:when>
                             	<c:otherwise>
		                        	<option value="${one.id }">${one.dictName }</option>
                             	</c:otherwise>
                             </c:choose>
                           </c:forEach>
                      </select>
                    </td>
                    <td>银行卡名称：</td>
                    <td>
                      <select class="form-control input-sm">
                          <option>${obj.receive.bankcardname }</option>
                      </select>
                    </td>
                    <td>卡号：</td>
                    <td>
                       <select class="form-control input-sm">
                          <option>${obj.receive.bankcardnum }</option>
                       </select>
                    </td>
                    <td>合计：</td>
                    <td id="sumjine">${obj.receive.sum }</td>
                  </tr>
         </table>
         <div class="bankSlipImg" align="center"><img id="shuidanimg" width="100%" height="305" alt="" src="${obj.bill.receiptUrl }"></div>
      </div>
      <span class="invoiceInfo-header">发票信息</span>
      <div class="invoiceInfo-body">
        <table class="payTable2">
          <tr>
                  <td>发票项目：</td>
                  <td>
                    <select id="invoiceitem" name="invoiceitem" class="form-control input-sm">
                    	<c:forEach items="${obj.ytselect }" var="one">
                    		<option value="${one.id }">${one.comDictName }</option>
                    	</c:forEach>
                    </select>
                  </td>
                  <td>发票日期：</td>
                  <td><input id="invoicedate" name="invoicedate" type="text" onFocus="WdatePicker()" class="form-control input-sm"></td>
                  <!-- <td>开票人：</td>
                  <td>
                     <select id="billuserid" name="billuserid" class="form-control input-sm">
                        <option value="1">林俊杰</option>
                        <option value="2">王力宏</option>
                     </select>
                  </td>
                  <td>部门：</td>
                  <td>
                     <select id="deptid" name="deptid" class="form-control input-sm">
                        <option value="1">国际部</option>
                        <option value="2">内陆部</option>
                     </select>
                  </td> -->
          </tr>
          <tr>
                  <td>付款单位：</td>
                  <td colspan="3"><input id="paymentunit" name="paymentunit" type="text" class="form-control input-sm" value="${obj.customename }" disabled="disabled"></td>
          </tr>
          <tr>
                  <td>备注：</td>
                  <td colspan="3"><input id="remark" name="remark" type="text" class="form-control input-sm"></td>
          </tr>
          <tr>
                  <td>差额：</td>
                  <td><input id="difference" name="difference" type="text" class="form-control input-sm mustNumberPoint"></td>
                  <td>余额：</td>
                  <td><label id="balance" name="balance">${obj.receive.sum }</label>
                  </td>
          </tr>
          <tr class="cloneTR">
                  <td>发票号：</td>
                  <td><input id="invoicenum" name="invoicenum" type="text" class="form-control input-sm"></td>
                  <td>金额：</td>
                  <td><input id="invoicebalance" name="invoicebalance" type="text" class="form-control input-sm mustNumberPoint"></td>
                  <td colspan="4">
                    <ul class="fileUL">
                      <li>
                      	<a href="javascript:;" class="FileDiv">
                      		上传
                          <input type="file" class="sc" id="sc" name="sc">
                        </a> 
                      </li>
                      <li><a href="javascript:;" id="fileName" name="fileName">未选择文件</a></li>
                      <li><a href="javascript:;" class="fileDelete deleteInvoice" >删除</a></li>
                      <li><a href="javascript:;" id="preView" class="fileDelete">预览</a></li>
                      <li><a href="javascript:;" class="glyphicon glyphicon-plus addIcon"></a></li>
                    </ul>
                    <input id="invoiceurl" name="invoiceurl" type="hidden" value="">
                  </td>
          </tr>
        </table>
      </div>
    </div>
	</div>

  <div id="light" class="white_content">
        <i class="fa fa-times-circle" onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'"></i>
        <img id="fapiaoid" src="">
  </div> 
  	<script type="text/javascript">
  		var BASE_PATH = '${base}';
  	</script>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
	<script src="${base }/admin/order/invoiceupload.js"></script>
	<script src="${base }/admin/order/ordercommon.js"></script>
  <script type="text/javascript">
     /*-----收付款>收款>开发票-----*/
/*      var sc = document.getElementById("sc");
     var fileName = document.getElementById("fileName");
     function handleFile(){
            var reg = /[^\\\/]*[\\\/]+/g;
            var nameValue=sc.value.replace(reg, '');
            fileName.innerHTML=nameValue;
     } 
 */     $(function(){
		      /*-----收付款>收款>开发票 + 按钮-----*/
		      $('.addIcon').click(function(){
		          var divTest = $(this).parents('.cloneTR'); 
		          var lastDiv = $('.cloneTR').last();
		          var newDiv = divTest.clone(false,true);
		          //newDiv.find('.FileDiv').html('<input type="file" class="sc" id="sc" name="sc" onchange="handleFile()">');
		          newDiv.find('[name=invoicenum]').val('');
		          newDiv.find('[name=invoicebalance]').val(''); 
		          newDiv.find('[name=fileName]').html('未选择文件');
		          newDiv.find('[name=invoiceurl]').val('');
		          lastDiv.after(newDiv);
		          var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
		          newDiv.find("p").html(No); 
		          newDiv.find('.addIcon').parent().remove();
		          newDiv.find('.fileUL').append('<li><a href="javascript:;" class="glyphicon glyphicon-minus removIcon removTd"></a></li>');
		      });
		      /*-----收付款>收款>开发票 - 按钮-----*/
		      $(document).on("click",".removIcon",function(){
		          $(this).parents('.cloneTR').remove();
		      });
		      
		      $(document).on('click','#preView',function(){
		   	  	  var invoiceurl = $(this).parent().parent().parent().find('[name=invoiceurl]').val();
		   	  	  //alert(invoiceurl);
		          document.getElementById('light').style.display='block';
		          //document.getElementById('fade').style.display='block';
		          document.getElementById('fapiaoid').src=invoiceurl; 
		      });
		      $(document).on('click','.deleteInvoice',function(){
		   	  	  var invoicedetaildiv = $(this).parent().parent().parent();
		   	  	  invoicedetaildiv.find('[name=invoiceurl]').val('');
		   	  	  invoicedetaildiv.find('[name=fileName]').html('未选择文件');
		   	  	  //alert(invoiceurl);
		          //document.getElementById('light').style.display='block';
		          //document.getElementById('fade').style.display='block';
		          document.getElementById('fapiaoid').src=''; 
		      });
     
     });
   //关闭窗口
     function closewindow(){
 		var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
 		parent.layer.close(index);
 	}
   $('#fileindex').val($('.cloneTR').length + 1);
   function saveInvoiceInfo(){
	   var formdata = {};
	   var id = $('#id').val();
	   formdata.pnrid = id;
	   var invoiceitem = $('#invoiceitem').val();
	   formdata.invoiceitem = invoiceitem;
	   var invoicedate = $('#invoicedate').val();
	   formdata.invoicedate = invoicedate;
	   var billuserid = $('#billuserid').val();
	   formdata.billuserid = billuserid;
	   var deptid = $('#deptid').val();
	   formdata.deptid = deptid;
	   var paymentunit = $('#paymentunit').val();
	   formdata.paymentunit = paymentunit;
	   var remark = $('#remark').val();
	   formdata.remark = remark;
	   var difference = $('#difference').val();
	   formdata.difference = difference;
	   var balance = $('#balance').val();
	   formdata.balance = balance;
	   var invoicedetails = [];
	   $('.cloneTR').each(function(i){
		   var detail = {};
		   var invoicenum = $(this).find('[name=invoicenum]').val();
		   detail.invoicenum = invoicenum;
		   var invoicebalance = $(this).find('[name=invoicebalance]').val();
		   detail.invoicebalance = invoicebalance;
		   var fileName = $(this).find('[name=fileName]').html();
		   detail.filename = fileName;
		   var invoiceurl = $(this).find('[name=invoiceurl]').val();
		   detail.invoiceurl = invoiceurl;
		   invoicedetails.push(detail);
	   });
	   formdata.invoicedetails = invoicedetails;
	   $.ajax({ 
			type: 'POST', 
			data: {data:JSON.stringify(formdata)}, 
			url: '${base}/admin/inland/saveReceiveInvoiceInfo.html',
           success: function (data) { 
           	closewindow();
           	window.parent.successCallback('5');
           },
           error: function (xhr) {
           	layer.msg("提交失败","",3000);
           } 
       });
   }
  </script>
</body>
</html>	
