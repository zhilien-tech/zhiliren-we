<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>收款</title>
	<link rel="stylesheet" href="${base }/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base }/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base }/public/dist/css/inlandCross.css"><!--本页style-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow()">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="提交"/>
            <h4>收款</h4>
          </div>
          <div class="modal-body" style="height: 483px;overflow-y:auto; ">
              <table id="receivablesTable" class="table table-bordered table-hover">
                <thead>
                  <tr>
                    <th>订单号</th>
                    <th>开票日期</th>
                    <th>客户团号</th>
                    <th>客户</th>
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
                        	<option value="${one.dictCode }">${one.dictName }</option>
                        </c:forEach>
                    </select>
                  </td>
                  <td>银行卡名称：</td>
                  <td>
                    <select class="form-control input-sm">
                        <option>国际专用卡</option>
                        <option>内陆专用卡</option>
                    </select>
                  </td>
                  <td>卡号：</td>
                  <td>
                     <select class="form-control input-sm">
                        <option>6352 7463 3647 756</option>
                     </select>
                  </td>
                  <td>合计：</td>
                  <td>3333.33</td>
                </tr>
              </table>
              <button type="button" class="btn btn-primary btn-sm bankSlipBtn">上传水单</button>
              <div class="bankSlipImg"></div>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base }/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base }/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base }/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base }/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base }/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
	<!--layer -->
    <script src="${base}/common/js/layer/layer.js"></script>
	<script type="text/javascript">
	//关闭窗口
    function closewindow(){
		var index = parent.parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	</script>
</body>
</html>	