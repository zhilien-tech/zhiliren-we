<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>管理平台</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome-4.4.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons-2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <!-- login style -->
   <link rel="stylesheet" href="${base}/public/dist/css/login.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${base}/public/plugins/iCheck/square/blue.css">
</head>
<body class="hold-transition login-page bodySll">
<div class="login-box">
  <div class="login-logo">
    <h3 class="loginTitle">航空票务<i>系统</i></h3>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">

    <form action="${base}/admin/login.html" method="post">
      <div class="LoginLabel">
        <label>用户名</label>
      </div>
      <div class="form-group marginBott5 has-feedback cf">
        <input id="userName" name="loginName" value="${obj.loginName}" type="text" class="form-control" placeholder="用户名/电话号码">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="LoginLabel">
        <label>密码</label>
      </div>
      <div class="form-group marginBott5 has-feedback cf">
        <input id="password" name="password" value="${obj.password}" type="password" class="form-control" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="LoginLabel">
        <label>验证码</label>
      </div>
      <div class="form-group marginBott5 has-feedback cf">
        <input id="validateCode" name="validateCode" value="${obj.validateCode}" type="text" class="form-control authText" placeholder="">
        <div class="authImg"><img src="yzm.png"></div>
      </div>
      <div class="row LoginBtn">
        <div class="col-xs-12">
          <button type="submit" class="btn btn-primary btn-block">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    <!-- /.social-auth-links -->
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${base}/public/plugins/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>
</body>
</html>