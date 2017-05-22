<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>添加航段</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/plugins/select2/select2.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base }/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base }/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" onclick="saveAirInfo();" value="保存"/>
            <c:if test="${obj.pnrinfo.mainsection eq 0 }">
	            <button type="button" class="btn right btn-sm" onclick="deleteairinfo()">删除</button>
            </c:if>
            <h4>编辑航段</h4>
          </div>
          <div class="modal-body" style="height:481px;overflow-y:auto; ">
              <table class="addHDtable">
                <tbody>
                      <tr>
                        <td><label>记录编号：</label></td>
                        <td colspan="3"><input id="pnr" name="pnr" type="text" class="form-control input-sm numTd PNRlength" value="${obj.pnrinfo.PNR }">
                        	<input type="hidden" id="pnrid" name="pnrid" value="${obj.pnrinfo.id }">
                        </td>
                        <td><label style="position: relative;right: 10px;">人数：</label></td>
                        <td colspan="1"><input id="peoplecount" name="peoplecount" type="text" class="form-control input-sm numTd mustNumber" value="${obj.pnrinfo.peoplecount }" style="position: relative;
    right: 10px;">
                        </td>
                      </tr>
                    <c:choose>
                   		<c:when test="${fn:length(obj.airinfo)>0}">
                      <c:forEach  items="${obj.airinfo }" var="airinfo" varStatus="status">
	                      <tr class="addHD-tr">
	                        <td><label>出发城市：</label></td>
	                        <td><select id="leavecity" name="leavecity" type="text" class="form-control input-sm cityselect2" multiple="multiple">
	                        	<c:forEach var="one" items="${obj.city }">
									<c:choose>
		                    			<c:when test="${airinfo.leavecity eq one.dictCode }">
											<option value="${one.dictCode }" selected="selected">${one.dictCode}</option>
		                    			</c:when>
		                    			<c:otherwise>
											<option value="${one.dictCode }">${one.dictCode}</option>
		                    			</c:otherwise>
		                    		</c:choose>
								</c:forEach>
	                        </select></td>
	                        <td><label>抵达城市：</label></td>
	                        <td><select id="arrivecity" name="arrivecity" type="text" class="form-control input-sm cityselect2" multiple="multiple">
	                        		<c:forEach var="one" items="${obj.city }">
										<c:choose>
			                    			<c:when test="${airinfo.arrvicity eq one.dictCode }">
												<option value="${one.dictCode }" selected="selected">${one.dictCode}</option>
			                    			</c:when>
			                    			<c:otherwise>
												<option value="${one.dictCode }">${one.dictCode}</option>
			                    			</c:otherwise>
			                    		</c:choose>
									</c:forEach>
	                        	</select></td>
	                        <td><label>出发日期：</label></td>
	                        <td><input id="leavedate" name="leavedate" type="text" class="form-control input-sm" onFocus="WdatePicker({minDate:'%y-%M-%d'})" value="<fmt:formatDate value="${airinfo.leavedate }" pattern="yyyy-MM-dd" />" placeholder=""></td>
	                        <td><label>航班号：</label></td>
	                        <td><select id="ailinenum" name="ailinenum" type="text" class="form-control input-sm" placeholder="ca309" multiple="multiple">
	                        		<c:forEach var="one" items="${obj.airline }">
			                    		<c:choose>
			                    			<c:when test="${airinfo.ailinenum eq one.dictName }">
												<option value="${one.dictName }" selected="selected">${one.dictName}</option>
			                    			</c:when>
			                    			<c:otherwise>
												<option value="${one.dictName }">${one.dictName}</option>
			                    			</c:otherwise>
			                    		</c:choose>
									</c:forEach>
	                        	</select></td>
	                        <td><label>出发时间：</label></td>
	                        <td><input id="leavetime" name="leavetime" type="text" class="form-control input-sm mustTimes" placeholder="12:00" value="${airinfo.leavetime }"></td>
	                        <td><label>抵达时间：</label></td>
	                        <td><input id="arrivetime" name="arrivetime" type="text" class="form-control input-sm mustArriveTimes" placeholder="13:00" value="${airinfo.arrivetime }"></td>
	                        <c:choose>
	                        	<c:when test="${status.index eq 0 }">
			                        <td><a href="javascript:;" class="glyphicon glyphicon-plus addHDIcon"></a></td>
	                        	</c:when>
	                        	<c:otherwise>
	                        		<td class="removeIconTd"><i class="glyphicon glyphicon-minus removHDIcon"></i></td>
	                        	</c:otherwise>
	                        </c:choose>
	                      </tr>
                      </c:forEach>
                     </c:when>
                     <c:otherwise>
                     	<tr class="addHD-tr">
	                        <td><label>出发城市：</label></td>
	                        <td><select id="leavecity" name="leavecity" class="form-control input-sm cityselect2" multiple="multiple"></select></td>
	                        <td><label>抵达城市：</label></td>
	                        <td><select id="arrivecity" name="arrivecity" class="form-control input-sm cityselect2" multiple="multiple"></select></td>
	                        <td><label>出发日期：</label></td>
	                        <td><input id="leavedate" name="leavedate" type="text" class="form-control input-sm" onFocus="WdatePicker({minDate:'%y-%M-%d'})" placeholder=" "></td>
	                        <td><label>航班号：</label></td>
	                        <td><select id="ailinenum" name="ailinenum" class="form-control input-sm" placeholder="ca309" multiple="multiple"></select></td>
	                        <td><label>出发时间：</label></td>
	                        <td><input id="leavetime" name="leavetime" type="text" class="form-control input-sm mustTimes" placeholder=" "></td>
	                        <td><label>抵达时间：</label></td>
	                        <td><input id="arrivetime" name="arrivetime" type="text" class="form-control input-sm mustArriveTimes" placeholder=" "></td>
	                        <td><a href="javascript:;" class="glyphicon glyphicon-plus addHDIcon"></a></td>
	                      </tr>
                     </c:otherwise>
                  </c:choose>
                </tbody>
              </table>
          </div>
	</div>
	<script type="text/javascript">
		var BASE_PATH = '${base}';
	</script>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>
  <!-- DataTables -->
  <script src="${base }/public/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="${base }/public/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!-- select2 -->
	
	<script src="${base}/public/plugins/select2/select2.full.min.js"></script>
	<script src="${base}/public/plugins/select2/i18n/zh-CN.js"></script>
	<!-- My97DatePicker -->
	<script src="${base}/common/js/My97DatePicker/WdatePicker.js"></script>
	<script src="${base }/admin/order/ordercommon.js"></script>
	<script src="${base }/admin/international/addairline.js"></script>
	<!--layer -->
	<script src="${base}/common/js/layer/layer.js"></script>
  <script type="text/javascript">
  	  //关闭窗口
	  function closewindow(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
	  }
  	  //保存航段信息
  	 function saveAirInfo(){
  		 var data = {};
  		 var pnrid = $('#pnrid').val();
  		 data.pnrid = pnrid;
  		 var pnr = $('#pnr').val();
  		 data.pnr = pnr;
  		 var peoplecount = $('#peoplecount').val();
  		 data.peoplecount = peoplecount;
  		 var airinfos = [];
  		 $('.addHD-tr').each(function(i){
  			 var airlength = '';
  			 var airinfo = {};
  			var leavecity = $(this).find('[name=leavecity]').val();
  			if (leavecity) {
  				leavecity = leavecity.join(',');
  				airlength += leavecity;
  			}else{
	  			airlength += '';
  			}
  			airinfo.leavecity = leavecity;
  			//抵达城市
  			var arrivecity = $(this).find('[name=arrivecity]').val();
  			if (arrivecity) {
  				arrivecity = arrivecity.join(',');
	  			airlength += arrivecity;
  			}else{
	  			airlength += '';
  			}
  			airinfo.arrivecity = arrivecity;
  			var ailinenum = $(this).find('[name=ailinenum]').val();
			if (ailinenum) {
				ailinenum = ailinenum.join(',');
				airlength += ailinenum;
  			}else{
				airlength += '';
  			}
			airinfo.ailinenum = ailinenum;
  			var leavedate = $(this).find('[name=leavedate]').val();
			airinfo.leavedate = leavedate;
			airlength += leavedate;
  			var leavetime = $(this).find('[name=leavetime]').val();
			airinfo.leavetime = leavetime;
			airlength += leavetime;
  			var arrivetime = $(this).find('[name=arrivetime]').val();
			airinfo.arrivetime = arrivetime;
			if(airlength){
				airinfos.push(airinfo);
			}
  		 });
  		 data.airinfos = airinfos;
  		layer.load(1);
  		$.ajax({ 
			type: 'POST', 
			data: {data:JSON.stringify(data)}, 
			url: '${base}/admin/international/saveEditAirinfo.html',
            success: function (data) { 
            	layer.closeAll('loading');
            	closewindow();
            	window.parent.successCallback('2');
            },
            error: function (xhr) {
            	layer.msg("编辑失败","",3000);
            } 
        });
  	 }
  	  
  	 function deleteairinfo(){
  		layer.confirm('确认删除该航段吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {id:'${obj.pnrinfo.id}'}, 
				url: '${base}/admin/international/deleteAirinfo.html',
	            success: function (data) { 
	            	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	            	window.parent.successCallback('5');
	            	parent.layer.close(index);
	            },
	            error: function (xhr) {
	            	layer.msg("删除失败","",3000);
	            } 
	        });
		});
  	 }
  </script>
</body>
</html>	