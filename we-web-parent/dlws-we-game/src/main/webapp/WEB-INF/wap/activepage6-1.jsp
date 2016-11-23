<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta content="width=device-width, minimum-scale=1,initial-scale=1, maximum-scale=1, user-scalable=1" id="viewport" name="viewport" />
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<link rel="stylesheet" type="text/css" href="css/mui.min.css" />
		<link rel="stylesheet" href="css/activepage.css" />
		<link rel="stylesheet" type="text/css" href="css/self.css" />
		<link rel="stylesheet" href="css/index.css" />

		<script src="js/mui.min.js"></script>
		<script src="js/mui.pullToRefresh.js"></script>
		<script src="js/mui.pullToRefresh.material.js"></script>
		<script src="http://cdn.bootcss.com/jquery/3.1.0/jquery.js"></script>
		<title>6-1活动页面</title>
	</head>
	<style>
		/*.text{
		background: #eeeeef;	
	}
	.yello{
		background: yellow;
		border-radius: 50%;
	}*/
	</style>

	<body>
		<!-- <div class="header">
			<span></span> 首页
			<span></span>
		</div> -->
		<div class="main clearfix">
			<img style="max-width:100%" src="img/banner1.png" alt="" />
			<div class="personage clearfix">
				<p class="first"></p>
				<p class="two"></p>
			</div>
			<div class="signin">
				<p class="first">
					<span>签到天数</span><span>1天</span>
				</p>
			</div>
			<div class="money">
				<span>奖金池总额</span>
				<span>999元</span>
			</div>
		</div>
		<div class="footer">
			<p class="first">
				首页
			</p>
			<p class="two">排行榜</p>
			<p class="three">发现</p>
			<p class="four">我的</p>
		</div>
		<div class="menu">
			<div class="submenu">
				<p class="one">参与up</p>
				<a href="startPK2-1.html">
					<p class="two">找人up</p>
				</a>
				<p class="three " id="rulebtn">参与规格</p>
				<p class="four">余额</p>
			</div>
		</div>
		<div class="rulePopup">
			<p class="one">参与规则</p>
			<p class="two" id="ruleok">我知道了</p>
		</div>
		<div class="sel_cal">
			<a class="fl prev_month">
				<</a>
					<p class="showdate fl"></p>
					<a class="fr next_month">></a>
		</div>
		<div class="text"></div>
		<script src="js/time.js"></script>
		<script type="text/javascript">
			$(function() {
				document.getElementById("rulebtn").addEventListener("tap", function() {
					$(".rulePopup").show();
				})
				document.getElementById("ruleok").addEventListener("tap", function() {
					$(".rulePopup").hide();
				})
			})
			$(function() {
				var winW = document.documentElement.clientWidth;
				console.log(winW)
				var fontSize = 100;
				var dew = 375;
				var rem = dew / fontSize;
				//    if(winW>dew){
				//        winW = dew;
				//        alert(winW);
				//    }
				document.documentElement.style.fontSize = winW / rem + "px"
			})
		</script>
	</body>

</html>