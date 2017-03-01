<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>付款申请</title>
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
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="提交"/>
            <h4 class="invoiceH4">付款申请</h4>
    </div>
      <div class="modal-body">
         <table id="receivablesTable" class="table table-bordered table-hover">
                  <thead>
                    <tr>
                      <th>订单号</th>
                      <th>PNR</th>
                      <th>客户团号</th>
                      <th>支付对象</th>
                      <th>开票日期</th>
                      <th>人数</th>
                      <th>开票人</th>
                      <th>金额</th>
                    </tr>
                  </thead>
                  <tbody>
                  </tbody>
         </table>
         <div class="form-group row"><!--用途/币种-->
                  <label class="col-sm-2 text-right padding">用途：</label>
                  <div class="col-sm-2 padding">
                    <select class="form-control input-sm">
                      <option>预付机票款</option>
                      <option>机票款</option>
                      <option>ETEM使用费</option>
                      <option>反税款</option>
                      <option>押金</option>
                    </select>
                  </div>
                  <label class="col-sm-1 text-right padding">币种：</label>
                  <div class="col-sm-2 padding">
                    <select class="form-control input-sm">
                      <option>人民币</option>
                      <option>澳币</option>
                      <option>欧币</option>
                      <option>美元币</option>
                    </select>
                  </div>
         </div><!--end 用途/币种-->
         <div class="form-group row"><!--申请人/审批人/审批结果-->
                  <label class="col-sm-2 text-right padding">申请人：</label>
                  <div class="col-sm-2 padding"><input type="text" class="form-control input-sm" disabled="disabled"></div>
                  <label class="col-sm-1 text-right padding">审批人：</label>
                  <div class="col-sm-2 padding"><input type="text" class="form-control input-sm" disabled="disabled" value="侯小凌"></div>
                  <label class="col-sm-1 text-right padding">审批结果：</label>
                  <div class="col-sm-2 padding"><input type="text" class="form-control input-sm"></div>
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