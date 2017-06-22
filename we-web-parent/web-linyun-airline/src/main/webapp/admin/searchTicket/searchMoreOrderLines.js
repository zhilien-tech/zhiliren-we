/*跨海内陆多条件查询*/
function searchInlandOrder(){
	var linkName = $("#linkNameId").select2("val");
	var phoneNum = $("#phoneNumId").select2("val");
	var outCity = $('#outCity0').find("option:selected").text();
	var arriveCity = $('#singleArriveCity0').find("option:selected").text();
	var outDatepicker = $("#outDatepicker0").val();
	var ariaStr = $('#tab_3Id').attr("aria-expanded");
	//国际不需要验证 客户名称
	if(ariaStr != "true"){
		if(!(linkName || phoneNum)){
			layer.msg("客户姓名不能为空", "", 2000);
			return;
		}
	}
	if(outCity==""){
		layer.msg('出发城市不能为空');
		return;
	}
	if(arriveCity==""){
		layer.msg('到达城市不能为空');
		return;
	}
	if(outDatepicker == ""){
		layer.msg('出发日期不能为空');
		return;
	}
	/*var msgIndex = layer.msg('查询中...',{time:0});*/
	var layerIndex =  layer.load(1, {shade: "#000"});
	//显示区间
	/*var area = $("#origin").val()+' --- '+$("#destination").val();
	document.getElementById('travelArea').innerHTML=area;*/
	
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
}


/*国际多条件查询*/
function searchInternetOrders(){
	var linkName = $("#linkNameId").select2("val");
	var phoneNum = $("#phoneNumId").select2("val");
	if(!(linkName || phoneNum)){
		layer.msg("客户姓名不能为空", "", 2000);
		return;
	}
	makePart();
	var param = getEditPlanParam();
	datatable2.settings()[0].ajax.data = param;
	datatable2.ajax.url(BASE_PATH + '/admin/search/searchTeamTickets.html').load();
}