<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>添加记录-预收款</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/plugins/datatables/dataTables.bootstrap.css">
  <link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
            <h4>预收款</h4>
          </div>
          <div class="modal-body" style="height:481px;overflow-y:auto; ">
              <table class="addYSKtable">
                <tbody>
                      <tr>
                        <td><label>成本单价：</label></td>
                        <td><input type="text" class="form-control input-sm"></td>
                        <td><label>预收款比例 ：</label></td>
                        <td><input type="text" class="form-control input-sm"></td>
                        <td><label>实际人数：</label></td>
                        <td><input type="text" class="form-control input-sm"></td>
                        <td colspan="2"><label>免罚金可减人数：</label></td>
                        <td colspan="2"><input type="text" class="form-control input-sm"></td>
                      </tr>
                      <tr>
                        <td><label>本期罚金：</label></td>
                        <td><input type="text" class="form-control input-sm"></td>
                        <td><label>本期应付：</label></td>
                        <td><input type="text" class="form-control input-sm"></td>
                        <td><label>税金单价：</label></td>
                        <td><input type="text" class="form-control input-sm"></td>
                        <td><label>本期实付：</label></td>
                        <td><input type="text" class="form-control input-sm"></td>
                        <td><label>币种：</label></td>
                        <td>
                          <select class="form-control input-sm">
                            <option>UNY</option>
                            <option>USD</option>
                          </select>
                        </td>
                      </tr>
                </tbody>
              </table>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
</body>
</html>	