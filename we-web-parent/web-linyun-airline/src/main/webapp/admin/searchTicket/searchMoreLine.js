//第一次初始化加载
initSelect2();
//select2 选项渲染
function formatRepoSelection(repo){
	var text =  repo.text;
	text = text.substr(0,3);
	return text;
}
function initSelect2(){
	$('.setMore').each(function(i){
		if($('.setMore').length - 1 == i){
			//出发城市下拉列表
			$("#outCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var ids = $('#singleArriveCity'+i).val();
						if(ids){
							ids = ids.join(',');
						}
						return {
							cityname : params.term, 
							ids:ids,
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; 
							obj.text = obj.dictCode +" - "+ obj.englishName +" - "+ obj.countryName; 
							return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				templateSelection: formatRepoSelection,
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", 
				maximumSelectionLength : 1, 
				tags : false
			});

			//抵达城市查询
			$("#singleArriveCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var ids = $('#outCity'+i).val();
						if(ids){
							ids = ids.join(',');
						}
						return {
							cityname : params.term, 
							ids:ids,
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; 
							obj.text = obj.dictCode +" - "+ obj.englishName +" - "+ obj.countryName; 
							return obj;
						});
						return {
							results : selectdata
						};
					},
					cache : false
				},
				templateSelection: formatRepoSelection,
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", 
				maximumSelectionLength : 1, 
				tags : false
			});

		}
	});
}

$(function () {
	//客户信息 显示/隐藏
	$('.UnderIcon').on('click',function(){
		$('.hideTable').toggle();
	});
	//清楚按钮 隐藏
	$('#clearBtn').click(function(){
		$('.hideTable').hide();
	});
	//散客
	document.getElementsByName("voyageType")[1].checked="checked";//radio 默认 选中往返
	//团队
	document.getElementsByName("voyageType1")[1].checked="checked";//radio 默认 选中往返

	//添加 setMore
	$('.addSingleIconTd').click(function(){
		var clonediv = $('.setMore').first();
		var divTest =  $('.setMore').last(); 
		var newDiv = clonediv.clone(false,true);
		divTest.after(newDiv);
		$('.setMore').each(function(i){
			if($('.setMore').length - 1 == i){
				$(this).find('.addSingleIconTd').remove();
				$(this).append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removeMore"></i></td>');
				$(this).find('.removIconId').remove();
				$('.setMore').first().find('.removIconId').hide();
				//设置新的出发城市下拉ID
				var outCity = $(this).find('[name=origin0]');
				outCity.attr("id","outCity"+i);
				$('#outCity'+i).empty();
				$('#outCity'+i).next().remove();
				//设置新的返回城市下拉框ID
				var singleArriveCity = $(this).find('[name=destination0]');
				singleArriveCity.attr("id","singleArriveCity"+i);
				$('#singleArriveCity'+i).empty();
				$('#singleArriveCity'+i).next().remove();
				//设置新的出发日期
				var departuredate = $(this).find('[name=departuredate0]');
				departuredate.attr("id","outDatepicker"+i);
				$("#outDatepicker"+i).val("");
				//设置新的到达日期
				/*var returndate = $(this).find('[name=returndate0]');
				returndate.attr("id","returnDatepicker"+i);
				returndate.attr("onFocus",'WdatePicker({dateFmt:"yyyy-MM-dd",minDate:"#F{$dp.$D(\'outDatepicker'+ i +'\')}"})');
				$("#returnDatepicker"+i).val("");*/
			}
		});
		initSelect2();
	});
});
$(document).on('click', '.removeMore', function(e) {
	if($('.setMore').length > 1){
		if($('.setMore').length == 2){
			$('.setMore').last().find('.removIconId').show();
		}
		$(this).parent().parent().remove();
	}
});

/*回车搜索*/
function onkeyEnter(){
	var e = window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode == 13){
    	selectSingle();
    }
}

/* 跨海内陆多程查询 */
var clickfirst=1;
$("#searchSingleTicketsBtn").click(function() {
	$("#paragraphListInfo").html("");
	var linkName = $("#linkNameId").select2("val");
	var phoneNum = $("#phoneNumId").select2("val");
	var outCity = $('#outCity0').find("option:selected").text();
	var arriveCity = $('#singleArriveCity0').find("option:selected").text();
	var outDatepicker = $("#outDatepicker0").val();
	var returnDatepicker = $("#returnDatepicker0").val();
	var airType = $("input[name='voyageType']:checked").val();
	var agentNum = $("#agentSelect").val();
	var childNum = $("#childrenSelect").val();
	var babyNum = $("#babySelect").val();

	var ariaStr = $('#tab_3Id').attr("aria-expanded");
	//国际不需要验证 客户名称
	if(ariaStr != "true"){
		if(!(linkName || phoneNum)){
			layer.msg("客户姓名不能为空", "", 2000);
			return;
		}
	}
	
	var oCityBoolean = true;
	var aCityBoolean = true;
	var oDateBoolean = true;
	var j = $("tr.setMore").length;
	if(j-1>0){
		for(var i=1; i<j; i++){
			var setMoreTrTd = $("tr.setMore").eq(i).children('td');
			
			//出发城市
			var outSelect = setMoreTrTd.eq(1).children("select");
			var outCity =  outSelect.find("option:selected").text();
			if(outCity==""){
				oCityBoolean = false;
			}
			
			//抵达城市
			var arrSelect = setMoreTrTd.eq(4).children("select");
			var arriveCity = arrSelect.find("option:selected").text();
			if(arriveCity==""){
				aCityBoolean = false;
			}
			
			//出发日期
			var outDatepicker = setMoreTrTd.eq(6).children().val();
			if(outDatepicker==""){
				oDateBoolean = false;
			}
		}
	}
	
	if(outCity==""){
		layer.msg('出发城市不能为空');
		return;
	}else{
		if(oCityBoolean == false){
			layer.msg('出发城市不能为空');
			return;
		}
	}
	if(arriveCity==""){
		layer.msg('到达城市不能为空');
		return;
	}else{
		if(aCityBoolean == false){
			layer.msg('到达城市不能为空');
			return;
		}
	}
	if(outDatepicker==""){
		layer.msg('出发日期不能为空');
		return;
	}else{
		if(oDateBoolean == false){
			layer.msg('出发日期不能为空');
			return;
		}
	}
	if(airType == 2){
		if(returnDatepicker==""){
			layer.msg('返回日期不能为空');
			return;
		}
	}
	if((agentNum=="0") && (childNum=="0") && (babyNum=="0")){
		layer.msg('乘客类型至少为一人');
		return;
	}
	
	/*var msgIndex = layer.msg('查询中...',{time:0});*/
	var layerIndex =  layer.load(1, {shade: "#000"});
	
	$("#origin").val($("#outCity0").select2("val"));
	$("#destination").val($("#singleArriveCity0").select2("val"));
	$("#departuredate").val($("#outDatepicker0").val());
	$("#returndate").val($("#returnDatepicker0").val());
	$("#departureCardDate").val($("#outDatepicker0").val());
	$("#returnCardDate").val($("#returnDatepicker0").val());
	
	//显示区间
	/*var area = $("#origin").val()+' --- '+$("#destination").val();
	document.getElementById('travelArea').innerHTML=area;*/
	//段数
	var airType = $("input[name='voyageType']:checked").val();
	var html = "";
	var oCity = $("#outCity0").select2("val");
	var aCity = $("#singleArriveCity0").select2("val");
	var oDate = $("#outDatepicker0").val();
	var moreLines = "";
	var outArrivalCity0 = $("#outCity0").select2("val") +'-'+ $("#singleArriveCity0").select2("val");
	var arrivalOutCity0 = $("#singleArriveCity0").select2("val") +'-'+ $("#outCity0").select2("val");
	if(airType == 1){
		html = '<li id="num1" class="btnStyle">第1段<p>'+ outArrivalCity0 +'</p></li>';
		document.getElementById('travelTypeNum').innerHTML=html;
	}
	if(airType == 2){
		html = '<li id="num1" class="btnStyle dClas">第1段<p>'+ outArrivalCity0 +'</p></li><li id="num2" class="dClas">第2段<p>'+ arrivalOutCity0 +'</p></li>';
		document.getElementById('travelTypeNum').innerHTML=html;
	}
	/* 多程 显示多段 */
	if(airType == 3){
		
		moreLines = "MORELINES" +" "+ oCity+aCity+oDate;
		/*
		//方案一 显示往返段
		for(var i=1; i<$('.setMore').length*2; i=i+2){
			var j = i+1;
			html +='<li id="num'+i+'">第'+i+'段</li><li id="num'+j+'">第'+j+'段</li>';
		}*/
		//方案二 显示去程段
		html ='<li id="moreNum1" class="btnStyle dClas">第1段<p>'+ outArrivalCity0 +'</p></li>';
		for(var i=2; i<=$('.setMore').length; i++){
			var setMoreTrTd = $("tr.setMore").eq(i-1).children('td');
			//出发城市
			var outSelect = setMoreTrTd.eq(1).children("select");
			var oCity = outSelect.find("option:selected").text();
			//抵达城市
			var arrSelect = setMoreTrTd.eq(4).children("select");
			var aCity = arrSelect.find("option:selected").text();
			//出发日期
			var outDatepicker = setMoreTrTd.eq(6).children().val();
			html +='<li id="moreNum'+i+'">第'+i+'段<p>'+  oCity +'-'+ aCity +'</p></li>';
			moreLines += " " + oCity + aCity + outDatepicker;
		}
		$("#moreLines").val(moreLines);
		document.getElementById('travelTypeNum').innerHTML=html;
	}
	$.ajax({
		type : 'POST',
		data : $("#searchSingleTicketsForm").serialize(),
		url : BASE_PATH  + '/admin/search/searchSingleTickets.html',
		success : function(resp) {
			var airInfo = "";
			/*layer.close(msgIndex);*/
			layer.close(layerIndex);
			if ("200" == resp.statusCode) {
				/* 日期小卡片  */
				getDateCard();
				var outCodeStr = $("#outCity0").select2("val");
				var arriveCodeStr = $("#singleArriveCity0").select2("val");
				
				for (var i=0; i<resp.data.length; i++){
				
					var bfmAirInfo = resp.data[i];
					var airlineCode = bfmAirInfo.list[0].opAirlineCode;
					var FlightNumber = bfmAirInfo.list[0].opFlightNumber;
					var DepartureAirport = bfmAirInfo.list[0].DepartureAirport;
					var ArrivalAirport = bfmAirInfo.list[0].ArrivalAirport;
					var DepartureDateTime = bfmAirInfo.list[0].DepartureDateTime;
					var ArrivalDateTime = bfmAirInfo.list[0].ArrivalDateTime;
					var ElapsedTime = bfmAirInfo.list[0].ElapsedTime;
					var totalAmount = bfmAirInfo.priceInfos[0].totalAmount;
					var currencyCode = bfmAirInfo.priceInfos[0].currencyCode;
					
					
					airInfo += '<li class="ticketsLi">'+
					'<p class="lineCode" hidden>'+airlineCode+'</p>'+
					'<p class="p">'+airlineCode+FlightNumber+'</p>'+
					'<div class="distanceTimeDiv"><span class="chufaCS"><b class="DepartureDateTime">'+DepartureDateTime+'</b><p>'+DepartureAirport+'</p>'+
					'</span><span class="shiDuan">'+toHourMinute(ElapsedTime)+'</span><span class="daodaCS"><b class="ArrivalDateTime">'+ArrivalDateTime+'</b><p>'+ArrivalAirport+'</p></span></div>'+
					'<div class="moneyDiv"><span class="airTotalMoney">'+currencyCode+totalAmount+'</span></div>'+
					'<div class="btn-group xuanzeBtn dropdown">'+
						'<button class="btn chooseAirLineBtn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">选择<span class="caret"></span></button>'+
						'<ul class="dropdown-menu airLineCity">'+
						
						'</ul>'+
					'</div>'+
					'</li>';
					
				}
				if($("#airInfoList").val() == 1){
					document.getElementById('paragraphListInfo').innerHTML=airInfo;
				}else{
					document.getElementById('paragraphListInfo').innerHTML=airInfo;
				}
				
			} else {
				layer.msg("未查询到结果", "", 2000);
			}
		},
		error : function(xhr) {
		}
	});
	
});


