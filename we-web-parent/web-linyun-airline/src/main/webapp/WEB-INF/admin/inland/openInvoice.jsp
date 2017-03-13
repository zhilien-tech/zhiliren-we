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
                    <td>${obj.receive.sum }</td>
                  </tr>
         </table>
         <div class="bankSlipImg" align="center"><img id="shuidanimg" width="400" height="300" alt="" src="${obj.bill.receiptUrl }"></div>
      </div>
      <span class="invoiceInfo-header">发票信息</span>
      <div class="invoiceInfo-body">
        <table class="payTable2">
          <tr>
                  <td>发票项目：</td>
                  <td>
                    <select id="invoiceitem" name="invoiceitem" class="form-control input-sm">
                        <option value="1">团款</option>
                        <option value="2">代订机票费用</option>
                    </select>
                  </td>
                  <td>发票日期：</td>
                  <td><input id="invoicedate" name="invoicedate" type="text" onFocus="WdatePicker()" class="form-control input-sm"></td>
                  <td>开票人：</td>
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
                  </td>
          </tr>
          <tr>
                  <td>付款单位：</td>
                  <td colspan="3"><input id="paymentunit" name="paymentunit" type="text" class="form-control input-sm" value="JQ" disabled="disabled"></td>
          </tr>
          <tr>
                  <td>备注：</td>
                  <td colspan="3"><input id="remark" name="remark" type="text" class="form-control input-sm"></td>
          </tr>
          <tr>
                  <td>差额：</td>
                  <td><input id="difference" name="difference" type="text" class="form-control input-sm"></td>
                  <td>余额：</td>
                  <td><label>${obj.receive.sum }</label>
                  	<input id="balance" name="balance" type="hidden" value="">
                  </td>
          </tr>
          <tr class="cloneTR">
                  <td>发票号：</td>
                  <td><input id="invoicenum" name="invoicenum" type="text" class="form-control input-sm"></td>
                  <td>金额：</td>
                  <td><input id="invoicebalance" name="invoicebalance" type="text" class="form-control input-sm"></td>
                  <td colspan="4">
                    <ul class="fileUL">
                      <li>
                        <a href="javascript:;" class="FileDiv">
                          上传
                          <input type="file" class="sc" id="sc" name="sc" onchange="handleFile()">
                        </a>
                      </li>
                      <li><a href="javascript:;" id="fileName" name="fileName">未选择文件</a></li>
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
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${base }/public/plugins/uploadify/jquery.uploadify.min.js"></script>
  <script type="text/javascript">
     /*-----收付款>收款>开发票-----*/
     var sc = document.getElementById("sc");
     var fileName = document.getElementById("fileName");
     function handleFile(){
            var reg = /[^\\\/]*[\\\/]+/g;
            var nameValue=sc.value.replace(reg, '');
            fileName.innerHTML=nameValue;
     }
     $(function(){
      /*-----收付款>收款>开发票 + 按钮-----*/
      $('.addIcon').click(function(){
          var divTest = $(this).parents('.cloneTR'); 
          var newDiv = divTest.clone(true);
          divTest.after(newDiv);
          var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
          newDiv.find("p").html(No); 
          newDiv.find('.addIcon').parent().remove();
          newDiv.find('.fileUL').append('<li><a href="javascript:;" class="glyphicon glyphicon-minus removIcon removTd"></a></li>');
      });
      /*-----收付款>收款>开发票 - 按钮-----*/
      $(document).on("click",".removIcon",function(){
          $(this).parents('.cloneTR').remove();
      });
      $('.cloneTR').each(function(i){
	      $(this).find('[name=fileName]').click(function(){
    	  	  var invoiceurl = $(this).parent().parent().parent().find('[name=invoiceurl]').val();
    	  	  //alert(invoiceurl);
	          document.getElementById('light').style.display='block';
	          //document.getElementById('fade').style.display='block';
	          document.getElementById('fapiaoid').src=invoiceurl; 
	      });
      });
     

     });
   //关闭窗口
     function closewindow(){
 		var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
 		parent.layer.close(index);
 	}
   
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
   loadUpload();
   function loadUpload(){
	   $('.cloneTR').each(function(i){
		   $(this).find('[name=sc]').click(function(){
		    	$.fileupload1 = $(this).find('[name=sc]').uploadify({
		    		'auto' : true,//选择文件后自动上传
		    		'formData' : {
		    			'fcharset' : 'uft-8',
		    			'action' : 'uploadimage'
		    		},
		    		'buttonText' : '上传',//按钮显示的文字
		    		'fileSizeLimit' : '3000MB',
		    		'fileTypeDesc' : '文件',//在浏览窗口底部的文件类型下拉菜单中显示的文本
		    		'fileTypeExts' : '*.png; *.jpg; *.bmp; *.gif; *.jpeg;',//上传文件的类型
		    		'swf' : '${base}/public/plugins/uploadify/uploadify.swf',//指定swf文件
		    		'multi' : false,//multi设置为true将允许多文件上传
		    		'successTimeout' : 1800,
		    		'queueSizeLimit' : 100,
		    		'uploader' : '${base}/admin/drawback/grabfile/uploadFile.html',//后台处理的页面
		    		//onUploadSuccess为上传完视频之后回调的方法，视频json数据data返回，
		    		//下面的例子演示如何获取到vid
		    		'onUploadSuccess' : function(file, data, response) {
		    			var jsonobj = eval('(' + data + ')');
		    			var url  = jsonobj;//地址
		    			var fileName = file.name;//文件名称
		    			var parentdom = $(this).parent().parent().parent().parent();
		    			consolt.log(JSON.stringify(parentdom));
		    			parentdom.find('[name=fileName]').html(fileName);
		    			parentdom.find('[name=invoiceurl]').val(url);
		    		},
		            //加上此句会重写onSelectError方法【需要重写的事件】
		            'overrideEvents': ['onSelectError', 'onDialogClose'],
		            //返回一个错误，选择文件的时候触发
		            'onSelectError':function(file, errorCode, errorMsg){
		                switch(errorCode) {
		                    case -110:
		                        alert("文件 ["+file.name+"] 大小超出系统限制！");
		                        break;
		                    case -120:
		                        alert("文件 ["+file.name+"] 大小异常！");
		                        break;
		                    case -130:
		                        alert("文件 ["+file.name+"] 类型不正确！");
		                        break;
		                }
		            }
		    	});
		    });
		   
	   });
   }
  </script>
</body>
</html>	