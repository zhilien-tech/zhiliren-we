<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>添加</title>
<link rel="stylesheet"
	href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/user.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
	<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>
</head>
<body onresize=hero();>
	<div class="modal-dialog modal-lg">
          <div class="modal-content">
          	<form id="editForm" method="post">
              <div class="modal-header">
                  <button id="backBtn" type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
                  <button type="submit" class="btn btn-primary right btn-sm" data-dismiss="modal">保存</button>
                  <button type="submit" class="btn btn-primary right btn-sm" data-dismiss="modal">初始化密码</button>
                  <button type="submit" class="btn right btn-sm" data-dismiss="modal">删除</button>
                  <h4>编辑</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">用户姓名：</label>
                            <div class="col-sm-3 padding">
                              <input id="userName" name="userName" type="text" class="form-control input-sm inputWidth" value="${obj.user.userName}"/>
                              <span class="prompt">*</span>
                            </div>
                          
                            <label class="col-sm-2 text-right padding">用户名/手机号码：</label>
                            <div class="col-sm-3 padding">
                              <input id="telephone" name="telephone" type="text" class="form-control input-sm inputWidth" value="${obj.user.telephone}"/>
                              <span class="prompt">*</span>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">联系QQ：</label>
                            <div class="col-sm-3 padding">
                              <input id="qq" name="qq" type="text" class="form-control input-sm inputWidth" value="${obj.user.qq}"/>
                            </div>
                          
                            <label class="col-sm-2 text-right padding">座机号码：</label>
                            <div class="col-sm-3 padding">
                              <input id="landline" name="landline" type="text" class="form-control input-sm inputWidth" value="${obj.user.landline}"/>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">电子邮箱：</label>
                            <div class="col-sm-3 padding">
                              <input id="email" name="email" type="text" class="form-control input-sm inputWidth" value="${obj.user.email}"/>
                            </div>
                          
                           <label class="col-sm-2 text-right padding">所属部门：</label>
                            <div class="col-sm-3 padding">
                              <select class="form-control input-sm inputWidth">
                                <option>请选择</option>
                                <option>市场部</option>
                                <option>后勤部</option>
                              </select>
                              <span class="prompt">*</span>
                            </div>
                            
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">用户职位：</label>
                            <div class="col-sm-3 padding">
                              <select id="jobName" name="jobName" class="form-control input-sm inputWidth">
                                <option>请选择</option>
                                <option>经理</option>
                                <option>职员</option>
                              </select>
                              <span class="prompt">*</span>
                            </div>

                            <label class="col-sm-2 text-right padding">用户是否禁用：</label>
                            <div class="col-sm-3 padding">
                              <select id="disableStatus" name="disableStatus" class="form-control input-sm inputWidth">
                                <option>否</option>
                                <option>是</option>
                              </select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">负责区域：</label>
                            <div class="col-sm-3 padding">
                              <input id="areaName" name="areaName" type="text" class="form-control input-sm inputWidth" placeholder="请输入区域名称" />
                              <span class="prompt">*</span>
                            </div>
                        </div>
                      </form>
                 </div>
                </div>
            </div>
        </div>
	<script type="text/javascript">
		
	</script>
</body>
</html>