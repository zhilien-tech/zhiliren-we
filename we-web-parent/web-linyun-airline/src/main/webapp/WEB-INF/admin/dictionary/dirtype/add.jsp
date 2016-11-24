<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%> 
<%@include file="/WEB-INF/common/tld.jsp"%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>添加</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
	<script src="${base}/common/js/layer/layer.js"></script>
</head>
<body onresize=hero();>
          <div class="modal-top">
          <form id="form1" method="post"> 
              <div class="modal-header boderButt">
                  <button type="button" class="btn btn-primary right btn-sm" data-dismiss="modal">返回</button>
                  <button type="button" id="submit" class="btn btn-primary right btn-sm">保存</button>
                  <h4>添加</h4>
              </div>
                <div class="modal-body">
                 <div class="tab-content">
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">字典类型编码：</label>
                            <div class="col-sm-8 padding">
                              <input id="typeCode" name="typeCode" class="form-control input-sm" placeholder="请输入字典类型编码" />
                            </div>
                        </div>
                        <div class="form-group row">
                        	<label class="col-sm-3 text-right padding">字典类别名称：</label>
                            <div class="col-sm-8 padding">
                              <input name="typeName" class="form-control input-sm" placeholder="请输入字典类别名称" />
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-3 text-right padding">状态：</label>
                            <div class="col-sm-8 padding">
                              <select id="status" name="status" class="form-control input-sm">
                                <option value="1" selected="selected">启用</option>
								<option value="2">删除</option>
                              </select>
                            </div>
                        </div>
						<div class="form-group row">
                            <label class="col-sm-3 text-right padding">描述：</label>
                            <div class="col-sm-8 padding">
                              <textarea name="description" class="form-control"></textarea>
                            </div>
                        </div>
                      </form>
                    </div>
                </div>
            </div>
</body>
</html>	
<script src="${base}/common/js/layer/layer.js"></script>
<script type="text/javascript">
	//添加成功提示
	$("#submit").click(function(){
		$.ajax({
           cache: true,
           type: "POST",
           url:'${base}/admin/dictionary/dirtype/add.html',
           data:$('#form1').serialize(),// 你的formid
           error: function(request) {
              layer.msg('添加失败!');
           },
            success: function(data) {
			layer.load(1, {
				 shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
              layer.msg('添加成功!',{time: 5000, icon:6});
			  window.location.reload(true);
           }
       });
		//添加完成页面自动关闭
		 $(".Mymodal-lg").modal('hide');
	});
	//验证输入框不为空
	$(function(){
    	$('#form1').validation();//自定义form表单的id
  })
</script>