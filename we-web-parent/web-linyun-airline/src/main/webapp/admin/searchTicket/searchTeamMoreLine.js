/*单选按钮*/
$("input[name=internat1]").click(function(){
	var typeCodeStr = "";
	switch($("input[name=internat1]:checked").attr("id")){
	case "gjnlRadioTeam":
		typeCodeStr = "GJNL";
		break;
	case "gjRadioTeam":
		typeCodeStr = "GJ";
		break;
	}
	$.ajax({
		type : 'POST',
		data : {
			typeCode:typeCodeStr
		},
		dataType:'json',
		url : BASE_PATH+'/admin/search/initCityTypeCode.html',
		success : function(data) {

		},
		error : function() {
		}
	});
});


//第一次初始化加载
initTeamSelect2();

function initTeamSelect2(){
	$('.setTeamMore').each(function(i){
		if($('.setTeamMore').length - 1 == i){
			//出发城市下拉列表
			$("#teamOutCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							cityname : params.term, 
							ids:$('#teamArriveCity'+i).val(),
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
				tags : false
			});

			//抵达城市查询
			$("#teamArriveCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							cityname : params.term, 
							ids:$('#teamOutCity'+i).val(),
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
				tags : false
			});
		}
	});
}

$(function () {
	//段数 样式切换
	$('.paragraphBtn li').click(function(){
		$(this).addClass('btnStyle').siblings().removeClass('btnStyle');
	});

	//添加 setTeamMore
	$('.addTeamIconTd').click(function(){
		var clonediv = $('.setTeamMore').first();
		var divTest =  $('.setTeamMore').last(); 
		var newDiv = clonediv.clone(false,true);
		divTest.after(newDiv);
		$('.setTeamMore').each(function(i){
			if($('.setTeamMore').length - 1 == i){
				$(this).find('.addTeamIconTd').remove();
				$(this).append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removeMore"></i></td>');
				$(this).find('.removIconId').remove();
				$('.setTeamMore').first().find('.removIconId').hide();
				//设置新的出发城市下拉ID
				var teamOutCity = $(this).find('[name=origin1]');
				teamOutCity.attr("id","teamOutCity"+i);
				$('#teamOutCity'+i).next().remove();
				//设置新的返回城市下拉框ID
				var teamArriveCity = $(this).find('[name=destination1]');
				teamArriveCity.attr("id","teamArriveCity"+i);
				$('#teamArriveCity'+i).next().remove();
				//设置新的出发日期
				var teamDeparturedate = $(this).find('[name=departuredate1]');
				teamDeparturedate.attr("id","teamOutDatepicker"+i);
				$("#teamOutDatepicker"+i).val("");
				//设置新的到达日期
				var teamReturndate = $(this).find('[name=returndate1]');
				teamReturndate.attr("id","teamReturnDatepicker"+i);
				teamReturndate.attr("onFocus",'WdatePicker({dateFmt:"yyyy-MM-dd",minDate:"#F{$dp.$D(\'teamOutDatepicker'+ i +'\')}"})');
				$("#teamReturnDatepicker"+i).val("");
			}
		});
		initTeamSelect2();
	});
});
$(document).on('click', '.removeMore', function(e) {
	if($('.setTeamMore').length > 1){
		if($('.setTeamMore').length == 2){
			$('.setTeamMore').last().find('.removIconId').show();
		}
		$(this).parent().parent().remove();
	}
});


/*航空公司查询*/
$("#teamAirline").select2({
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
	tags : false
});


/* 团客多程查询 */
var clickone=1;
$("#searchTeamTicketsBtn").click(function() {
	var link = $("#linkNameId").val();
	if(link==null){
		layer.msg("客户名称不能为空", "", 2000);
		return;
	}
	//loading层
	var index = layer.load(1, {
	  shade: [0.1,'#fff'] //0.1透明度的白色背景
	});
	if(clickone){
		var index=0;
		$("#teamorigin").val($("#teamOutCity"+index).select2("val"));
		$("#teamdestination").val($("#teamArriveCity"+index).select2("val"));
		$("#teamdeparturedate").val($("#teamOutDatepicker"+index).val());
		$("#teamreturndate").val($("#teamReturnDatepicker"+index).val());
		clickone=0;
	}
	$.ajax({
		type : 'POST',
		data : $("#searchTeamTicketsForm").serialize(),
		url : BASE_PATH  + '/admin/search/searchTeamTickets.html',
		success : function(data) {
			//段数
			var airTeamType = $("input[name='voyageType1']:checked").val();
			var html = "";
			if(airTeamType == 1){
				html = '<li id="teamNum1" class="btnStyle">第1段</li>';
				document.getElementById('travelTeamTypeNum').innerHTML=html;
			}
			if(airTeamType == 2){
				html = '<li id="teamNum1" class="btnStyle">第1段</li><li id="teamNum2">第2段</li>';
				document.getElementById('travelTeamTypeNum').innerHTML=html;
			}
			/* 多程 显示多段 */
			if(airTeamType == 3){
				for(var i=1; i<$('.setTeamMore').length*2; i=i+2){
					var j = i+1;
					html +='<li id="teamNum'+i+'">第'+i+'段</li><li id="teamNum'+j+'">第'+j+'段</li>';
				}
				document.getElementById('travelTeamTypeNum').innerHTML=html;
			}
			var teamList = "";
			$.each(data, function (index, element) {  
				teamList += '<tr><td>'+ element.ordersnum +'</td><td>'+ " " +'</td><td>'+ " " +'</td><td>'+element.leavescity +'/'+ element.backscity +'</td>'+
				'<td>'+element.leavesdate+'</td><td>'+element.price+'</td><td>'+element.amount+'</td><td>'+element.orderstime+'</td><td>'+element.opid+'</td></tr>';
			});  
			document.getElementById('teamtbody').innerHTML=teamList;
		},
		error : function(){
		}
	});
});


/*-----------------------select2隐藏域赋值  start------------------------*/
/* 航空公司 */
airlineNameOpt = function(){
	var airName = $('#teamAirline').find("option:selected").text();
	$("#teamAirlineName").val(airName);
	var selectedAirId = $("#teamAirline").select2("val");
	$("#teamAirlineCode").val(selectedAirId);
}
/*-----------------------select2隐藏域赋值  end----------------------------*/
$(document).on('click','#teamNum1',function(){
	var index=0;
	$("#teamorigin").val($("#teamOutCity"+index).select2("val"));
	$("#teamdestination").val($("#teamArriveCity"+index).select2("val"));
	$("#teamdeparturedate").val($("#teamOutDatepicker"+index).val());
	$("#teamreturndate").val($("#teamReturnDatepicker"+index).val());
	$("#searchTeamTicketsBtn").click();
});
$(document).on('click','#teamNum2',function(){
	var index=0;
	$("#teamorigin").val($("#teamArriveCity"+index).select2("val"));
	$("#teamdestination").val($("#teamOutCity"+index).select2("val"));
	$("#teamdeparturedate").val($("#teamReturnDatepicker"+index).val());
	$("#teamreturndate").val($("#teamOutDatepicker"+index).val());
	$("#searchTeamTicketsBtn").click();
});
