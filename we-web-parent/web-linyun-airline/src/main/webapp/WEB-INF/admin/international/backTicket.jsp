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
		.white_content {display: none;position: absolute;top: 25%;left: 25%;width: 55%;height: 55%;padding: 15px;border: 1px solid #efefef;border-radius: 5px;box-shadow: 0 0 31px #dadada;background-color: white;z-index: 1002;overflow: auto;}
		.white_content img {width: 100%;height: 100%;}
		.fa-times-circle {position: absolute;top: 3px;font-size: 18px;left: 473px;cursor: pointer;color: rgb(59, 134, 177);}
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
                    	<option value="">请选择</option>
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
         	<c:choose>
         		<c:when test="${fn:length(obj.backfile)>0}">
			         <c:forEach items="${obj.backfile }" var="one" varStatus="status">
				         <div class="form-group row cloneTR"><!--上传-->
							<ul class="fileUL">
				               <li>
				                 <a href="javascript:;" class="FileDiv">
				                 	上传<input type="file" class="sc" id="sc" name="sc">
				                 </a> 
				               </li>
				               <li><a href="javascript:;" id="fileName" name="fileName">${one.filename }</a></li>
				               <c:if test="${!empty one.fileurl }">
			                      <li><a href="javascript:;" class="fileDelete deleteInvoice" >删除</a></li>
			                      <c:choose>
			                      	<c:when test="${fn:endsWith(fn:toUpperCase(one.filename), '.JPG') or fn:endsWith(fn:toUpperCase(one.filename), '.JPEG') or fn:endsWith(fn:toUpperCase(one.filename), '.PNG') or fn:endsWith(fn:toUpperCase(one.filename), '.GIF') or fn:endsWith(fn:toUpperCase(one.filename), '.BMP')}">
				                      <li><a href="javascript:;" id="preView" class="fileDelete">预览</a></li>
			                      	</c:when>
			                      	<c:otherwise>
				                      <li><a href="${one.fileurl }" id="preView" class="fileDelete">下载</a></li>
			                      	</c:otherwise>
			                      </c:choose>
		                      </c:if>
				               <c:choose>
		                      	<c:when test="${status.index eq 0 }">
				                      <li><a href="javascript:;" class="glyphicon glyphicon-plus addIcon"></a></li>
		                      	</c:when>
		                      	<c:otherwise>
				                      <li><a href="javascript:;" class="glyphicon glyphicon-minus removIcon removTd"></a></li>
		                      	</c:otherwise>
		                      </c:choose>
				            </ul>
				            <input id="fileurl" name="fileurl" type="hidden" value="${one.fileurl }">         	
				         </div><!--end 上传-->
			         </c:forEach>
         		</c:when>
         		<c:otherwise>
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
				            <input id="fileurl" name="fileurl" type="hidden" value="">         	
				         </div><!--end 上传-->
         		</c:otherwise>
         	</c:choose>
         
         </form>
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
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- My97DatePicker --> 
    <script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<!--layer -->
  	<script src="${base}/common/js/layer/layer.js"></script>
<%--   	<script src="${base }/admin/order/invoiceupload.js"></script>
 --%>	<script type="text/javascript">
	  //关闭窗口
	  function closewindow(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
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
	  $('#submit').click(function(){
		  var formdata = getFormJson('#backTicketForm');
		  var backticketfiles = [];
		  $('.cloneTR').each(function(){
			  var backticketfile = {};
			  var filename = $(this).find('[name=fileName]').html();
			  backticketfile.filename = filename;
			  var fileurl = $(this).find('[name=fileurl]').val();
			  backticketfile.fileurl = fileurl;
			  backticketfiles.push(backticketfile);
		  });
		  formdata.backticketfiles = JSON.stringify(backticketfiles);
		  layer.load(1);
		  $.ajax({ 
				type: 'POST', 
				data: formdata, 
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
	          newDiv.find('[name=fileName]').html('未选择文件');
	          newDiv.find('[name=fileurl]').val('');
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
	          $(this).parents('.cloneTR').remove();
	      });
	      
	      $(document).on('click','#preView',function(){
	   	  	  var fileurl = $(this).parent().parent().parent().find('[name=fileurl]').val();
	          document.getElementById('light').style.display='block';
	          document.getElementById('fapiaoid').src=fileurl; 
	      });
	      $(document).on('click','.deleteInvoice',function(){
	   	  	  var invoicedetaildiv = $(this).parent().parent().parent();
	   	  	  invoicedetaildiv.find('[name=fileurl]').val('');
	   	  	  invoicedetaildiv.find('[name=fileName]').html('未选择文件');
	   	  	  invoicedetaildiv.find('#preView').remove();
	   	  	  invoicedetaildiv.find('#download').remove();
	   	  	  invoicedetaildiv.find('.deleteInvoice').remove();
	          document.getElementById('fapiaoid').src=''; 
	      });
 
 		});
	  
	//把dataUrl类型的数据转为blob
	  function dataURLtoBlob(dataurl) { 
	      var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
	          bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
	      while(n--){
	          u8arr[n] = bstr.charCodeAt(n);
	      }
	      return new Blob([u8arr], {type:mime});
	  }

	  $(document).on('change','.sc', function(){
	  	var thisDiv = $(this).parents('.cloneTR'); 
	  	var file = this.files[0];
	  	var reader = new FileReader();
	  	reader.onload = function(e) {
	  		var dataUrl = e.target.result;
	  		var blob = dataURLtoBlob(dataUrl) ;
	      	var formData = new FormData();
	      	formData.append("image", blob,file.name);
	      	$.ajax({ 
	              type: "POST",//提交类型  
	              dataType: "json",//返回结果格式  
	              url: BASE_PATH + '/admin/inland/uploadInvoice.html',//请求地址  
	              async: true  ,
	              processData: false, //当FormData在jquery中使用的时候需要设置此项
	              contentType: false ,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
	            	//请求数据  
	              data:formData ,
	              success: function (obj) {//请求成功后的函数  
	              	if('200' === obj.status){
	              		thisDiv.find('[name=fileurl]').val(obj.data);
	              		thisDiv.find('[name=fileName]').html(file.name);
	              		thisDiv.find('.fileDelete').each(function(){
	              			$(this).parent.remove();
	              		});
	              		var extStart = file.name.lastIndexOf(".");
	              		var ext = file.name.substring(extStart, file.name.length).toUpperCase();
	              		var deletepreview = '<li><a href="javascript:;" class="fileDelete deleteInvoice" >删除</a></li>';
	              		if (ext != ".JPG" && ext != ".JPEG" && ext != ".PNG" && ext != ".GIF" && ext != ".BMP") {
		              		deletepreview += '<li><a href="'+obj.data+'" id="download" class="fileDelete">下载</a></li>';
	              		}else{
		              		deletepreview += '<li><a href="javascript:;" id="preView" class="fileDelete">预览</a></li>';
	              		}
	              		thisDiv.find('[name=fileName]').parent().after(deletepreview);
	              	}
	              },  
	              error: function (obj) {
	              }
	      	});  // end of ajaxSubmit
	  	};
	  	reader.readAsDataURL(file);
	  });

	</script>
</body>
</html>
