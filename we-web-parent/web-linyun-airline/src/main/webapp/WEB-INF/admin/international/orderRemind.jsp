<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>提醒设置</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
            <h4>提醒设置</h4>
          </div>
          <div class="modal-body" style="height:281px;overflow-y:auto; ">
              <table class="remindTable">
                <tbody>
                  <tr>
                    <td><label class="remindLabel">预订</label></td>
                    <td><input type="text" class="form-control input-sm remindData" placeholder="2017-03-16"></td>
                    <td>
                      <select class="form-control input-sm">
                          <option>不重复</option>
                          <option>每15分</option>
                          <option>每20分</option>
                          <option>每小时</option>
                          <option>每天</option>
                          <option>每周</option>
                          <option>每月</option>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label class="remindLabel">一订</label></td>
                    <td><input type="text" class="form-control input-sm remindData" placeholder="2017-03-16"></td>
                    <td>
                      <select class="form-control input-sm">
                          <option>不重复</option>
                          <option>每15分</option>
                          <option>每20分</option>
                          <option>每小时</option>
                          <option>每天</option>
                          <option>每周</option>
                          <option>每月</option>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label class="remindLabel">二订</label></td>
                    <td><input type="text" class="form-control input-sm remindData" placeholder="2017-03-16"></td>
                    <td>
                      <select class="form-control input-sm">
                          <option>不重复</option>
                          <option>每15分</option>
                          <option>每20分</option>
                          <option>每小时</option>
                          <option>每天</option>
                          <option>每周</option>
                          <option>每月</option>
                      </select>
                    </td>
                  </tr>
                </tbody>
              </table>
          </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>

	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
</body>
</html>