<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/WEB-INF/common/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
   <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${base}/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" href="${base}/public/dist/css/applyApproval.css">
  <link rel="stylesheet" href="${base}/public/dist/css/iosSelect.css">
  <title>审批手机-审批列表</title>
  <script>
        /*var _hmt = _hmt || [];
        (function() {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?b25bf95dd99f58452db28b1e99a1a46f";
        var s = document.getElementsByTagName("script")[0]; 
        s.parentNode.insertBefore(hm, s);
        })();*/
  </script>
</head>
<body>
  <header class="header">
	<ul class="list-ul">
		<li><a href="${base}/admin/applyapproval/list.html"><i class="fa fa-angle-left"></i>返回</a></li>
		<li><h3>审批列表</h3></li>
		<li><a href="javascript:;" id="selectDate">筛选</a></li>
	</ul>
  </header>
  <content class="content-list">
	<a href="${base}/admin/applyapproval/detailList.html">
		<ul class="content-a-ul">
			<li>
				<span>北京直立人科技有限公司</span>
				<span>￥69880</span>
			</li>
			<li>
				<span>代订机票</span>
				<span>王五</span>
			</li>
			<li>
				<span class="color1">同意</span>
				<span>2017-03-09 12:22</span>
			</li>
		</ul>
	</a>
	<a href="${base}/admin/applyapproval/detailList.html">
		<ul class="content-a-ul">
			<li>
				<span>北京直立人科技有限公司</span>
				<span>￥69880</span>
			</li>
			<li>
				<span>代订机票</span>
				<span>王五</span>
			</li>
			<li>
				<span class="color1">同意</span>
				<span>2017-03-09 12:22</span>
			</li>
		</ul>
	</a>
	<a href="${base}/admin/applyapproval/detailList.html">
		<ul class="content-a-ul">
			<li>
				<span>北京直立人科技有限公司</span>
				<span>￥69880</span>
			</li>
			<li>
				<span>代订机票</span>
				<span>王五</span>
			</li>
			<li>
				<span class="color2">拒绝</span>
				<span>2017-03-09 12:22</span>
			</li>
		</ul>
	</a>
	<a href="${base}/admin/applyapproval/detailList.html">
		<ul class="content-a-ul">
			<li>
				<span>北京直立人科技有限公司</span>
				<span>￥69880</span>
			</li>
			<li>
				<span>代订机票</span>
				<span>王五</span>
			</li>
			<li>
				<span class="color1">同意</span>
				<span>2017-03-09 12:22</span>
			</li>
		</ul>
	</a>
	<a href="${base}/admin/applyapproval/detailList.html">
		<ul class="content-a-ul">
			<li>
				<span>北京直立人科技有限公司</span>
				<span>￥69880</span>
			</li>
			<li>
				<span>代订机票</span>
				<span>王五</span>
			</li>
			<li>
				<span class="color1">同意</span>
				<span>2017-03-09 12:22</span>
			</li>
		</ul>
	</a>
  </content>
<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${base}/public/dist/js/zepto.js"></script>
<script src="${base}/public/dist/js/iscroll.js"></script>
<script src="${base}/public/dist/js/iosSelect.js"></script>
<script type="text/javascript">
    var selectDateDom = $('#selectDate');
    var showDateDom = $('#showDate');
    // 初始化时间
    var now = new Date();
    var nowYear = now.getFullYear();
    var nowMonth = now.getMonth() + 1;
    var nowDate = now.getDate();
    showDateDom.attr('data-year', nowYear);
    showDateDom.attr('data-month', nowMonth);
    showDateDom.attr('data-date', nowDate);
    // 数据初始化
    function formatYear (nowYear) {
        var arr = [];
        for (var i = nowYear - 5; i <= nowYear + 5; i++) {
            arr.push({
                id: i + '',
                value: i + '年'
            });
        }
        return arr;
    }
    function formatMonth () {
        var arr = [];
        for (var i = 1; i <= 12; i++) {
            arr.push({
                id: i + '',
                value: i + '月'
            });
        }
        return arr;
    }
    function formatDate (count) {
        var arr = [];
        for (var i = 1; i <= count; i++) {
            arr.push({
                id: i + '',
                value: i + '日'
            });
        }
        return arr;
    }
    var yearData = function(callback) {
        setTimeout(function() {
            callback(formatYear(nowYear))
        }, 2000)
    }
    var monthData = function (year, callback) {
        setTimeout(function() {
            callback(formatMonth());
        }, 2000);
    };
    var dateData = function (year, month, callback) {
        setTimeout(function() {
            if (/^1|3|5|7|8|10|12$/.test(month)) {
                callback(formatDate(31));
            }
            else if (/^4|6|9|11$/.test(month)) {
                callback(formatDate(30));
            }
            else if (/^2$/.test(month)) {
                if (year % 4 === 0 && year % 100 !==0 || year % 400 === 0) {
                    callback(formatDate(29));
                }
                else {
                    callback(formatDate(28));
                }
            }
            else {
                throw new Error('month is illegal');
            }
        }, 2000);
    };
    selectDateDom.bind('click', function () {
        var oneLevelId = showDateDom.attr('data-year');
        var twoLevelId = showDateDom.attr('data-month');
        var threeLevelId = showDateDom.attr('data-date');
        var iosSelect = new IosSelect(3, 
            [yearData, monthData, dateData],
            {
                title:false,
                itemHeight: 35,
                relation: [1, 1],
                oneLevelId: oneLevelId,
                twoLevelId: twoLevelId,
                threeLevelId: threeLevelId,
                showLoading: true,
                callback: function (selectOneObj, selectTwoObj, selectThreeObj) {
                    showDateDom.attr('data-year', selectOneObj.id);
                    showDateDom.attr('data-month', selectTwoObj.id);
                    showDateDom.attr('data-date', selectThreeObj.id);
                    showDateDom.html(selectOneObj.value + ' ' + selectTwoObj.value + ' ' + selectThreeObj.value);
                }
        });
    });
</script>
</body>
</html>