<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="/WEB-INF/common/500.jsp"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE HTML>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<title>移动到</title>
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet" href="${base}/public/dist/css/dict.css">
<link rel="stylesheet" href="${base }/public/dist/css/bootstrapValidator.css"/>
<style type="text/css">
    .moveDiv{width: 100%;min-height: 300px;border: solid 1px #f1f1f1;border-radius: 3px;padding: 10px;}
    .moveCentent{background-color: #ececec;width: 100%;height: 100%;}
</style>
<!-- jQuery 2.2.3 -->
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/bootstrap/js/bootstrap.js"></script>
<script src="${base}/public/dist/js/bootstrapValidator.js"></script>
<script src="${base}/common/js/layer/layer.js"></script>
</head>
<body>
	<div class="modal-top">
    <div class="modal-header boderButt">
            <button type="button" class="btn btn-primary right btn-sm" onclick="closewindow();">取消</button>
            <input type="submit" id="submit" class="btn btn-primary right btn-sm" value="保存"/>
            <h4>移动到</h4>
          </div>
          <div class="modal-body">
            <div class="moveDiv">
                <div class="moveCentent">此处放你想放的内容~</div>
            </div>
          </div>
	</div>
<script type="text/javascript">
//点击取消
function closewindow(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</body>
</html>