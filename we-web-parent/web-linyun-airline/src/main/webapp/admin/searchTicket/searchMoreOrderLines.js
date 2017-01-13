/*跨海内陆多条件查询*/
function searchInlandOrder(){
	var linkName = $("#linkNameId").select2("val");
	var phoneNum = $("#phoneNumId").select2("val");
	var outCity = $('#outCity0').find("option:selected").text();
	var arriveCity = $('#singleArriveCity0').find("option:selected").text();
	var outDatepicker = $("#outDatepicker0").val();
	if(!(linkName || phoneNum)){
		layer.msg("客户名称不能为空", "", 2000);
		return;
	}
	if(outCity==""){
		layer.msg('出发城市不能为空');
		return;
	}
	if(arriveCity==""){
		layer.msg('到达城市不能为空');
		return;
	}
	/*if(outDatepicker==""){
		layer.msg('出发日期不能为空');
		return;
	}*/
	var msgIndex = layer.msg('查询中...',{time:0});
	//显示区间
	var area = $("#origin").val()+' --- '+$("#destination").val();
	document.getElementById('travelArea').innerHTML=area;
	//段数
	var airType = $("input[name='voyageType']:checked").val();
	var html = "";
	if(airType == 1){
		html = '<li id="num1" class="btnStyle">第1段</li>';
		document.getElementById('travelTypeNum').innerHTML=html;
	}
	if(airType == 2){
		html = '<li id="num1" class="btnStyle">第1段</li><li id="num2">第2段</li>';
		document.getElementById('travelTypeNum').innerHTML=html;
	}
	/* 多程 显示多段 */
	if(airType == 3){
		html ='<li id="moreNum1" class="btnStyle">第1段</li>';
		for(var i=2; i<=$('.setMore').length; i++){
			html +='<li id="moreNum'+i+'">第'+i+'段</li>';
		}
		document.getElementById('travelTypeNum').innerHTML=html;
	}
	
	$.ajax({
		type : 'POST',
		data : $("#searchSingleTicketsForm").serialize(),
		url : BASE_PATH  + '/admin/search/searchSingleTickets.html',
		success : function(resp) {
			var outLiList = "";
			var returnLiList = "";
			layer.close(msgIndex);
			if ("200" == resp.statusCode) {
				/* 日期小卡片  */
				getDateCard();
				
				/*清除按钮样式*/
				clearBtnClass();
				
				var duanshu = $("#duanshuId").val();
				if(duanshu != ""){
					var outCodeStr = $("#outCity"+duanshu).select2("val");
					var arriveCodeStr = $("#singleArriveCity"+duanshu).select2("val");
				}else{
					var outCodeStr = $("#outCity0").select2("val");
					var arriveCodeStr = $("#singleArriveCity0").select2("val");
				}
				
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
						var airlineCode = resp.data[i].airlineCode;
						var FlightNumber = outList[foot].FlightNumber;
						var ArrivalAirport = outList[foot].ArrivalAirport;
						var DepartureAirport = outList[foot].DepartureAirport;
						var DepartureDateTime = outList[foot].DepartureDateTime;
						var ArrivalDateTime = outList[foot].ArrivalDateTime;
						var ElapsedTime = outList[foot].ElapsedTime;
						var totalAmount = resp.data[i].priceInfo.totalAmount;
						outLiList += '<li>'+
						'<p class="p">'+airlineCode+FlightNumber+'</p></div>'+
						'<div class="distanceTimeDiv"><span class="chufaCS"><b>'+DepartureDateTime+'</b><p>'+DepartureAirport+'</p>'+
						'</span><span class="shiDuan">'+toHourMinute(ElapsedTime)+'</span><span class="daodaCS"><b>'+ArrivalDateTime+'</b><p>'+ArrivalAirport+'</p></span></div>'+
						'<div class="moneyDiv"><i class="fa fa-cny"></i>'+totalAmount+'</div>'+
						'</li>';
					}
					/* 返程列表 */
					for(var foot = 0; foot < returnList.length;foot++){
						var airlineCode = resp.data[i].airlineCode;
						var FlightNumber = returnList[foot].FlightNumber;
						var ArrivalAirport = returnList[foot].ArrivalAirport;
						var DepartureAirport = returnList[foot].DepartureAirport;
						var DepartureDateTime = returnList[foot].DepartureDateTime;
						var ArrivalDateTime = returnList[foot].ArrivalDateTime;
						var ElapsedTime = returnList[foot].ElapsedTime;
						var totalAmount = resp.data[i].priceInfo.totalAmount;
						returnLiList += '<li>'+
						'<p class="p">'+airlineCode+FlightNumber+'</p></div>'+
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
		error : function() {
		}
	});
}


/*国际多条件查询*/
function searchInternetOrders(){
	var linkName = $("#linkNameId").select2("val");
	var phoneNum = $("#phoneNumId").select2("val");
	if(!(linkName || phoneNum)){
		layer.msg("客户名称不能为空", "", 2000);
		return;
	}
	makePart();
	var param = getEditPlanParam();
	datatable2.settings()[0].ajax.data = param;
	datatable2.ajax.url(BASE_PATH + '/admin/search/searchTeamTickets.html').load();
}