/*--------------------------------加载下拉列表---------------------------------*/

//第一次初始化加载
initSelect2();

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
						return {
							cityname : params.term, 
							ids:$('#singleArriveCity'+i).val(),
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; 
							obj.text = obj.dictCode +"("+ obj.dictName +")"; 
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
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", 
				maximumSelectionLength : 1, 
				tags : false, 
			});

			//抵达城市查询
			$("#singleArriveCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							cityname : params.term, 
							ids:$('#outCity'+i).val(),
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; 
							obj.text = obj.dictCode +"("+ obj.dictName +")"; 
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
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", 
				maximumSelectionLength : 1, 
				tags : false,
			});

		}
	});
}

$(function () {
	//客户信息 显示/隐藏
	$('.UnderIcon').on('click',function(){
		$('.hideTable').toggle('400');
	});
	//清楚按钮 隐藏
	$('#clearBtn').click(function(){
		$('.hideTable').hide('400');
	});
	//散客
	document.getElementsByName("internat")[0].checked="checked";//radio 默认 国际内陆
	document.getElementsByName("voyageType")[1].checked="checked";//radio 默认 选中往返
	//团队
	document.getElementsByName("internat1")[0].checked="checked";//radio 默认 国际内陆
	document.getElementsByName("voyageType1")[1].checked="checked";//radio 默认 选中往返
	//段数 样式切换
	$('.paragraphBtn li').click(function(){
		$(this).addClass('btnStyle').siblings().removeClass('btnStyle');
	});

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
				$('#outCity'+i).next().remove();
				//设置新的返回城市下拉框ID
				var singleArriveCity = $(this).find('[name=destination0]');
				singleArriveCity.attr("id","singleArriveCity"+i);
				$('#singleArriveCity'+i).next().remove();
				//设置新的出发日期
				var departuredate = $(this).find('[name=departuredate0]');
				departuredate.attr("id","outDatepicker"+i);
				$("#outDatepicker"+i).val("");
				//设置新的到达日期
				var returndate = $(this).find('[name=returndate0]');
				returndate.attr("id","returnDatepicker"+i);
				returndate.attr("onFocus",'WdatePicker({dateFmt:"yyyy-MM-dd",minDate:"#F{$dp.$D(\'outDatepicker'+ i +'\')}"})');
				$("#returnDatepicker"+i).val("");
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

/* 散客多程查询 */
var clickfirst=1;
$("#searchSingleTicketsBtn").click(function() {
	var link = $("#linkNameId").val();
	if(link==null){
		layer.msg("客户名称不能为空", "", 2000);
		return;
	}
	if(clickfirst){
		var index = 0;
		$("#origin").val($("#outCity"+index).select2("val"));
		$("#destination").val($("#singleArriveCity"+index).select2("val"));
		$("#departuredate").val($("#outDatepicker"+index).val());
		$("#returndate").val($("#returnDatepicker"+index).val());
	}
	/* 获取去程数据 */
	$("#airInfoList").val("1");
	//loading层
	var index = layer.load(1, {
	  shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
	$.ajax({
		type : 'POST',
		data : $("#searchSingleTicketsForm").serialize(),
		url : BASE_PATH  + '/admin/search/searchSingleTickets.html',
		success : function(resp) {
			var outLiList = "";
			var returnLiList = "";
			if ("200" == resp.statusCode) {
				//如果成功返回数据才显示段数
				//区间
				var area = $("#outCityName").val()+' -- '+$("#arriveCityName").val();
				document.getElementById('travelArea').innerHTML=area;
				//段数
				var airType = $("input[name='voyageType']:checked").val();
				var html = "";
				if(airType == 1){
					html = '<li id="num1" class="btnStyle">第1段</li>';
					document.getElementById('travelTeamTypeNum').innerHTML=html;
				}
				if(airType == 2){
					html = '<li id="num1" class="btnStyle">第1段</li><li id="num2">第2段</li>';
					document.getElementById('travelTeamTypeNum').innerHTML=html;
				}
				/* 多程 显示多段 */
				if(airType == 3){
					for(var i=1; i<$('.setMore').length*2; i=i+2){
						var j = i+1;
						html +='<li id="num'+i+'">第'+i+'段</li><li id="num'+j+'">第'+j+'段</li>';
					}
					document.getElementById('travelTypeNum').innerHTML=html;
				}
				/* 日期小卡片  */
				getDateCard();
				var outCodeStr = $("#outCity0").select2("val");
				var arriveCodeStr = $("#singleArriveCity0").select2("val");
				var outList = new Array();
				var returnList = new Array();
				for (var i=0; i<resp.data.length; i++){
					var list = resp.data[i].list;
					var returnIdx = 0 ;
					for(var j=0; j<list.length; j++){
						var DepartureAirport = resp.data[i].list[j].DepartureAirport;
						/*返程*/
						if(DepartureAirport == arriveCodeStr ){
							returnIdx = j;
							break;
						}
					}
					for(var j=0; j<list.length; j++){
						if(j < returnIdx){
							outList.push(list[j]);
						}else{
							returnList.push(list[j]);
						}
					}
					/* 去程列表 */
					for(var foot = 0; foot < outList.length;foot++){
						var AirlineName = resp.data[i].airlineName;
						var airlineCode = resp.data[i].airlineCode;
						var FlightNumber = outList[foot].FlightNumber;
						var ArrivalAirport = outList[foot].ArrivalAirport;
						var DepartureAirport = outList[foot].DepartureAirport;
						var DepartureDateTime = outList[foot].DepartureDateTime;
						var ArrivalDateTime = outList[foot].ArrivalDateTime;
						var ElapsedTime = outList[foot].ElapsedTime;
						var totalAmount = resp.data[i].priceInfo.totalAmount;
						outLiList += '<li>'+
						'<div class="imgIconDiv"><img src="logoIcon.png"><p>'+AirlineName+airlineCode+FlightNumber+'</p></div>'+
						'<div class="distanceTimeDiv"><span class="chufaCS"><b>'+DepartureDateTime+'</b><p>'+DepartureAirport+'</p>'+
						'</span><span class="shiDuan">'+toHourMinute(ElapsedTime)+'</span><span class="daodaCS"><b>'+ArrivalDateTime+'</b><p>'+ArrivalAirport+'</p></span></div>'+
						'<div class="moneyDiv"><i class="fa fa-cny"></i>'+totalAmount+'</div>'+
						'</li>';
					}
					/* 返程列表 */
					for(var foot = 0; foot < returnList.length;foot++){
						var AirlineName = resp.data[i].airlineName;
						var airlineCode = resp.data[i].airlineCode;
						var FlightNumber = returnList[foot].FlightNumber;
						var ArrivalAirport = returnList[foot].ArrivalAirport;
						var DepartureAirport = returnList[foot].DepartureAirport;
						var DepartureDateTime = returnList[foot].DepartureDateTime;
						var ArrivalDateTime = returnList[foot].ArrivalDateTime;
						var ElapsedTime = returnList[foot].ElapsedTime;
						var totalAmount = resp.data[i].priceInfo.totalAmount;
						returnLiList += '<li>'+
						'<div class="imgIconDiv"><img src="logoIcon.png"><p>'+AirlineName+airlineCode+FlightNumber+'</p></div>'+
						'<div class="distanceTimeDiv"><span class="chufaCS"><b>'+DepartureDateTime+'</b><p>'+DepartureAirport+'</p>'+
						'</span><span class="shiDuan">'+toHourMinute(ElapsedTime)+'</span><span class="daodaCS"><b>'+ArrivalDateTime+'</b><p>'+ArrivalAirport+'</p></span></div>'+
						'<div class="moneyDiv"><i class="fa fa-cny"></i>'+totalAmount+'</div>'+
						'</li>';
					}
				}
				if($("#airInfoList").val() == 1){
					document.getElementById('paragraphListInfo').innerHTML=outLiList;
				}else{
					document.getElementById('paragraphListInfo').innerHTML=returnLiList;
				}
			} else {
				layer.msg(resp.data.message, "", 2000);
			}
		},
		error : function(xhr) {
		}
	});
});


