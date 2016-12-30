/*单选按钮
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


出发城市下拉列表
$("#teamOutCity").select2({
	ajax : {
		url : BASE_PATH  + "/admin/search/getCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				cityname : params.term, 
				ids:$('#teamArriveCityCode').val(),
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
抵达城市查询
$("#teamArriveCity").select2({
	ajax : {
		url : BASE_PATH  + "/admin/search/getCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				cityname : params.term, 
				ids:$('#teamOutCityCode').val(),
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




航空公司查询
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
	tags : false,
});

-----------------------select2隐藏域赋值  start------------------------
 出发城市 
outCityNameOpt = function (){
	var cityName = $('#teamOutCity').find("option:selected").text();
	$("#teamOutCityName").val(cityName);
	var selectedCityId = $("#teamOutCity").select2("val");
	$("#teamOutCityCode").val(selectedCityId);
}
 抵达城市 
arriveCityNameOpt = function(){
	var cityName = $('#teamArriveCity').find("option:selected").text();
	$("#teamArriveCityName").val(cityName);
	var selectedCityId = $("#teamArriveCity").select2("val");
	$("#teamArriveCityCode").val(selectedCityId);
}
 航空公司 
airlineNameOpt = function(){
	var airName = $('#teamAirline').find("option:selected").text();
	$("#teamAirlineName").val(airName);
	var selectedAirId = $("#teamAirline").select2("val");
	$("#teamAirlineCode").val(selectedAirId);
}
-----------------------select2隐藏域赋值  end----------------------------*/