//航空公司下拉
$('.aircomselect').select2({
	ajax : {
		url : BASE_PATH + '/admin/search/getAirLineSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				airlinename : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				  obj.id = obj.dictCode; // replace pk with your identifier
				  obj.text = obj.dictCode + "-" + obj.dictName; // replace pk with your identifier
				  return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
//城市下拉
$('.cityselect').select2({
	ajax : {
		url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				cityname : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				  obj.id = obj.dictCode; // replace pk with your identifier
				  obj.text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName; // replace pk with your identifier
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
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
//旅行社
$(".travelname").select2({
	ajax : {
		url : BASE_PATH + "/admin/customneeds/getTravelNameSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				travelname : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id =  obj.shortName; // replace pk with your identifier
				obj.text =  obj.shortName; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
//航班下拉
$(".airlineselect").select2({
	ajax : {
		url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				airlinename : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.airlinenum; // replace pk with your identifier
				obj.text = obj.airlinenum; // replace pk with your identifier
				return obj;
			});
			return {
				results : selectdata
			};
		},
		cache : false
	},
	
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
//联运城市下拉
$(".unioncityselect").select2({
	ajax : {
		url : BASE_PATH + "/admin/customneeds/getUnionCitySelect.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				cityname : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.dictCode; // replace pk with your identifier
			    var text = obj.dictCode;
			    if(obj.englishName){
				    text = obj.dictCode+'-'+obj.englishName+'-'+obj.countryName;
			    }
			    obj.text = text; // replace pk with your identifier
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
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false, //设置必须存在的选项 才能选中
});

//select2 选项渲染
function formatRepoSelection(repo){
	var text =  repo.text;
	text = text.substr(0,3);
	return text;
}