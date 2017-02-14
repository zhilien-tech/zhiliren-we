/************************************客户需求加载下拉列表 start ************************************/
function initCustNeedsSelect2(){
	//加载出发城市下拉
	$("#cOutcity").select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCustomerCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var cArrivalcity = $('#cArrivalcity').val();
				if(cArrivalcity){
					cArrivalcity = cArrivalcity.join(',');
				}
				return {
					exname : cArrivalcity,
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
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});

	//加载抵达城市下拉
	$("#cArrivalcity").select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCustomerCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var cOutcity = $('#cOutcity').val();
				if(cOutcity){
					cOutcity = cOutcity.join(',');
				}
				return {
					exname : cOutcity,
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
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});

	//加载航空公司下拉
	$("#cAirlineCompany").select2({
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

	//航班号下拉
	$("#cAirlineNum").select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCAirNumSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					exname : "",
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
}
/************************************客户需求加载下拉列表 end ************************************/

/************************************客户需求的 +需求 按钮 start ************************************/
$('.addDemand').click(function(){
	var divTest = $(this).parent(); 
	var newDiv = divTest.clone(false,true);
	//divTest.after(newDiv);
	$('#infofooter').append(newDiv);
	var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
	newDiv.find("p").html(No); 
	newDiv.find('.addDemand').remove();
	newDiv.prepend('<a href="javascript:;" class="btn btn-primary btn-sm removeDemand"><b>-</b>&nbsp;&nbsp;需求</a>');
	var divId=document.getElementById('infofooter').getElementsByTagName('div');
	newDiv.find('.titleNum').text(divId.length);
	
	/**************************************客户需求 设置新的出发城市**************************************/
	newDiv.find('[name=cOutcity]').next().remove();
	newDiv.find('[name=cOutcity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCustomerCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var cArrivalcity = newDiv.find('[name=cArrivalcity]').select2("val");
				if(cArrivalcity){
					cArrivalcity = cArrivalcity.join(',');
				}
				return {
					exname : cArrivalcity,
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
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});

	/**************************************客户需求 设置新的抵达城市**************************************/
	newDiv.find('[name=cArrivalcity]').next().remove();
	newDiv.find('[name=cArrivalcity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCustomerCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var cOutcity = newDiv.find('[name=cOutcity]').select2("val");
				if(cOutcity){
					cOutcity = cOutcity.join(',');
				}
				return {
					exname : cOutcity,
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
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		minimumInputLength : 1,
		maximumInputLength : 20,
		language : "zh-CN", //设置 提示语言
		maximumSelectionLength : 1, //设置最多可以选择多少项
		tags : false //设置必须存在的选项 才能选中
	});
	
	/**************************************航空段数  设置新的航空公司**************************************/
	newDiv.find('[name=cAirlineCompany]').select2({
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
	
	/**************************************设置新的航班号**************************************/
	newDiv.find('[name=cAirlineNum]').select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCAirNumSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					exname : "",
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
	//航空段数 只显示一条
	newDiv.find('.addCustomerAirline').each(function(j){
		if(j == 0){
			//设置新的 航空公司
			newDiv.find('[name=cAirlineCompany]').next().next().remove();
			//设置新的 航班号
			newDiv.find('[name=cAirlineNum]').next().next().remove();
			//设置新的 航空段数   出发日期、抵达日期、销售价、成本
			$(this).find('[name=cAirOutDate]').val('');
			$(this).find('[name=cAirArrivalDate]').val('');
			$(this).find('[name=cAirPretium]').val('');
			$(this).find('[name=cAirCost]').val('');
		}else{
			$(this).remove();
		}
	});
	//客户需求出发日期、人数、早中晚、备注清除
	newDiv.find('[name=cOutDate]').val('');
	newDiv.find('[name=cPersonAmount]').val('');
	//newDiv.find('[name=morningDay]').val(0);
	newDiv.find('[name=cRemark]').val('');

});

/************************************客户需求的 +需求 按钮 end ************************************/
//客户需求的 -需求 按钮
$(document).on("click",".removeDemand",function(){
	$(this).parent().remove();
});


/************************************客户需求的航空段数 + 按钮 start ************************************/
$(document).on("click",".addIcon",function(){
	var divTest = $(this).parent().parent(); 
	var newDiv = divTest.clone(false,true);
	divTest.after(newDiv);
	var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
	newDiv.find("p").html(No); 
	newDiv.find('.addIcon').parent().remove();
	newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removIcon"></i></td>');
	
	//循环设置每段 出发、抵达日期的id
	/*newDiv.find('.addCustomerAirline').each(function(i){
		if($('.addCustomerAirline').length - 1 == i){
			//出发日期设置id
			$(this).find('[name=cAirOutDate]').attr("id","cAirOutDate"+i);
			//抵达日期设置id
			$(this).find('[name=cAirArrivalDate]').attr("id","cAirArrivalDate"+i);
		}
	});*/
	
	/**************************************设置新的航空公司**************************************/
	newDiv.find('[name=cAirlineCompany]').next().remove();
	newDiv.find('[name=cAirlineCompany]').select2({
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
	
	/**************************************设置新的航班号**************************************/
	newDiv.find('[name=cAirlineNum]').next().remove();
	newDiv.find('[name=cAirlineNum]').select2({
		ajax : {
			url : BASE_PATH + "/admin/search/getCAirNumSelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					exname : "",
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
	//航空段数   出发日期、抵达日期、销售价、成本清除
	newDiv.find('[name=cAirOutDate]').val('');
	newDiv.find('[name=cAirArrivalDate]').val('');
	newDiv.find('[name=cAirPretium]').val('');
	newDiv.find('[name=cAirCost]').val('');
});
/************************************客户需求的航空段数 + 按钮 end ************************************/

//客户需求的航空段数 - 按钮
$(document).on("click",".removIcon",function(){
	$(this).parent().parent().remove();
});


/************************************飞机票 选择项点击事件  start ************************************/
$(document).on("click",".custLineChoose",function(){
	
	/*$('.DemandDiv').each(function(i){
		var custNeedNum = $(this).find('[class=titleNum]').html();
		alert(custNeedNum);
		$(this).find('[class=addButton]').click();
	});*/
});

$(document).on("click",".chooseLineBtn",function(){
	alert();
	var custLines = '';
	$('.DemandDiv').each(function(i){
		var custNeedNum = $(this).find('[class=titleNum]').html();
		var custOutCity = $(this).find('[name=cOutcity]').select2("val");
		var custArrivalCity = $(this).find('[name=cArrivalcity]').select2("val");
		
		var custLine = custNeedNum +'. '+ custOutCity +' - '+ custArrivalCity;
		custLines += '<li><a href="javascript:;" name="custLineChoose" onclick="custLineChoose()">'+ custLine +'</a></li>';
	});
	
	$(".dropdown-menu").append(custLines);
});