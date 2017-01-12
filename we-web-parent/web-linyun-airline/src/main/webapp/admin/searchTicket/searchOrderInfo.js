/*航空公司查询*/
$("#airline").select2({
	ajax : {
		url : BASE_PATH  + "/admin/search/getAirLineSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				airlinename : params.term,
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.dictCode;
				obj.text = obj.dictCode +"-"+ obj.dictName; 
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : true
	},

	escapeMarkup : function(markup) {
		return markup;

	}, 
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN",
	maximumSelectionLength : 1, 
	tags : false,
});

/*------------------------搜索条件 下拉列表  start----------------------------*/
/*成人下拉列表*/
var _agentSelect = document.getElementById("agentSelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "成人"+i;  
	_agentSelect.appendChild(_option);  
}  
/*儿童下拉列表*/
var _childrenSelect = document.getElementById("childrenSelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "儿童"+i;  
	_childrenSelect.appendChild(_option);  
}  
/*婴儿下拉列表*/
var _babySelect = document.getElementById("babySelect");
for ( i = 1; i <= 10; i++){  
	var _option = document.createElement("option");  
	_option.value = i;  
	_option.text = "婴儿"+i;  
	_babySelect.appendChild(_option);  
}  
/*-----------------------搜索条件 下拉列表  end----------------------------*/

/*-----------------------select2隐藏域赋值  start------------------------*/
/* 客户姓名 */
linkNameOpt = function(){
	$("#linkNameValidator").val($('#linkNameId').find("option:selected").text());
}
/*-----------------------select2隐藏域赋值  end------------------------*/

/*-----------------------单程、往返段数查询  start--------------------------------*/
$(document).on('click','#num1',function(){
	document.getElementById('paragraphListInfo').innerHTML="";
	var index=0;
	$("#airInfoList").val(1);
	$("#origin").val($("#outCity"+index).select2("val"));
	$("#destination").val($("#singleArriveCity"+index).select2("val"));
	$("#departuredate").val($("#outDatepicker"+index).val());
	$("#returndate").val($("#returnDatepicker"+index).val());
	searchInlandOrder();
});
$(document).on('click','#num2',function(){
	document.getElementById('paragraphListInfo').innerHTML="";
	var index=0;
	$("#airInfoList").val(2);
	$("#origin").val($("#singleArriveCity"+index).select2("val"));
	$("#destination").val($("#outCity"+index).select2("val"));
	$("#departuredate").val($("#returnDatepicker"+index).val());
	$("#returndate").val("");
	searchInlandOrder();
});
/*-----------------------往返段数查询  end--------------------------------*/

//清除跨海内陆 列表项
function clearSearchHtml(){
	document.getElementById('travelArea').innerHTML="";
	document.getElementById('travelTypeNum').innerHTML="";
	document.getElementById('travelDateNum').innerHTML="";
	document.getElementById('paragraphListInfo').innerHTML="";
	$("#airline").val(null).trigger("change");
	$("#agentSelect").val("0");
	$("#childrenSelect").val("0");
	$("#babySelect").val("0");
	$("#airLevel").val("1");
}
//清除国际 列表项
function clearSearchTeamHtml(){
	$("#teamAirline").val(null).trigger("change");
	document.getElementById('travelTeamTypeNum').innerHTML="";
	document.getElementById('datatable2_info').innerHTML="";
	document.getElementById('datatable2_paginate').innerHTML="";
	document.getElementById('datatable2').innerHTML="";
	$("#teamAirLevel").val("1");
}

/* -------------------------日期小卡片 start------------------------------- */
cardDate = function(obj){
	var d = new Date(obj.getAttribute("value"));
	var seperator1 = "-";
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var strDate = d.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var cardStr = year +'-'+ month +'-'+ strDate;
	$("#departuredate").val(cardStr);
	var id = obj.getAttribute("id");
	$("#addbtnStyle").val(id);
	document.getElementById('paragraphListInfo').innerHTML="";
	searchInlandOrder();
}
getDateCard =function(){
	var dateNumHtml = "";
	var outStr = $("#outDatepicker0").val();
	var outDate = new Date(outStr.replace(/-/g,"/"));
	var returnStr = $("#returnDatepicker0").val();
	var returnDate = new Date(returnStr.replace(/-/g,"/"));
	/* 出发前的日期 */
	var time = outDate.getTime() - new Date().getTime() ; //日期的long型值之差
	var days = Math.floor(time/(24*60*60*1000))+1;
	if(days <=3){
		var dataCardHtml = "";
		for(var i=days; i>=1; i--){
			var beforeDate= outDate.getTime()- 1000*60*60*24*i;
			var changeDate=new Date(beforeDate);
			var formatDate = getNowFormatDate(changeDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="'+formatDate+'">'+formatDate+'</li>';
		}
	}else{
		var dataCardHtml = "";
		for(var i=3; i>=1; i--){
			var beforeDate= outDate.getTime()- 1000*60*60*24*i;
			var changeDate=new Date(beforeDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="'+getNowFormatDate(changeDate)+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}
	/* 出发日期 */
	dataCardHtml += '<li onclick="cardDate(this);" value="'+outDate+'" id="'+getNowFormatDate(outDate)+'" class="btnStyle">'+getNowFormatDate(outDate)+'</li>';
	/* 出发后的日期 */
	var travelTime = returnDate.getTime() - outDate.getTime();
	var travelDays = Math.floor(travelTime/(24*60*60*1000))+1;
	if(travelDays>3){
		var afterDate = outDate.getTime();
		for(var i=1; i<=3; i++){
			afterDate+=1000*60*60*24;
			var changeDate=new Date(afterDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="'+getNowFormatDate(changeDate)+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}else{
		var afterDate = outDate.getTime();
		for(var i=1; i<travelDays; i++){
			afterDate+=1000*60*60*24;
			var changeDate=new Date(afterDate);
			dataCardHtml += '<li onclick="cardDate(this);" value="'+changeDate+'" id="'+getNowFormatDate(changeDate)+'">'+getNowFormatDate(changeDate)+'</li>';
		}
	}
	document.getElementById('travelDateNum').innerHTML=dataCardHtml;
}
/* -------------------------日期小卡片 end------------------------------- */


/* -------------------------日期格式转换 start---------------------------- */
//将分钟数量转换为小时和分钟字符串
toHourMinute = function(minutes){
	var m = parseInt(minutes);
	return (Math.floor(m/60) + "h" + (m%60) + "m" );
}
/*获取某一天是周几*/
getWeekByDay = function(i){ 
	var today = new Array("周日","周一","周二","周三","周四","周五","周六"); //创建星期数组
	return today[i];  //返一个星期中的某一天，其中0为星期日 
}
/*格式化日期为年月*/
getNowFormatDate = function (changeDate){
	var seperator1 = "-";
	var seperator2 = " ";
	var year = changeDate.getFullYear();
	var month = changeDate.getMonth() + 1;
	var strDate = changeDate.getDate();
	var weekday = changeDate.getDay();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var outdate = month +seperator1+ strDate +seperator2+ getWeekByDay(weekday);
	return outdate;
}
/*------------------------日期加减天数   默认返程日期是出发日期的15天---------------------*/
function addDate(dd,count){
	var a = new Date(dd)
	a = a.valueOf()
	a = a + count * 24 * 60 * 60 * 1000
	a = new Date(a)
	return a;
}
defaultDate = function(){
	defaultReturnDate = addDate($("#outDatepicker").val(),15);
	years = defaultReturnDate.getFullYear();
	months = defaultReturnDate.getMonth()+1;
	days = defaultReturnDate.getDate();
	if (months >= 1 && months <= 9) {
		months = "0" + months;
	}
	if (days >= 0 && days <= 9) {
		days = "0" + days;
	}
	var returnDateStr = years +'-'+ months +'-'+days;
	return returnDateStr;
}
function selectSingle(){
	$("#searchSingleTicketsBtn").click();
}
/* -------------------------日期格式转换 end---------------------------- */


/*跨海内陆*/
document.getElementsByName("voyageType")[1].checked="checked";//radio 默认 选中往返
/*国际*/
document.getElementsByName("voyageType1")[1].checked="checked";//radio 默认 选中往返



//全局回车事件
/*document.onkeydown=function(event){
	var tab1Aira = $("#tab_1Id").attr("aria-expanded");
	var tab2Aira = $("#tab_2Id").attr("aria-expanded");
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode==13){ // enter 键
		alert(tab1Aira +"-"+tab2Aira);
		if(tab1Aira){
			$("#searchSingleTicketsBtn").click();
		}
		if(tab2Aira){
			$("#searchTeamTicketsBtn").click();
		}
	}
}; */