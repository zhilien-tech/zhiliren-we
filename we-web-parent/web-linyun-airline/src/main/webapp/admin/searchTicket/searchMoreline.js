
initSelect2();

function initSelect2(){
	$('.setMore').each(function(i){
		if($('.setMore').length - 1 == i){
			/*出发城市下拉列表*/
			$("#outCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							cityname : params.term, 
							ids:$('#arriveCityCode').val(),
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

			/*抵达城市查询*/
			$("#singleArriveCity"+i).select2({
				ajax : {
					url : BASE_PATH  + "/admin/search/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						return {
							cityname : params.term, 
							ids:$('#outCityCode').val(),
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
	$('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
		$('.hideTable').toggle('400');
	});
	$('#clearBtn').click(function(){//清楚按钮 隐藏
		$('.hideTable').hide('400');
	});
	/*散客*/
	document.getElementsByName("internat")[0].checked="checked";//radio 默认 国际内陆
	document.getElementsByName("voyageType")[1].checked="checked";//radio 默认 选中往返
	/*团队*/
	document.getElementsByName("internat1")[0].checked="checked";//radio 默认 国际内陆
	document.getElementsByName("voyageType1")[1].checked="checked";//radio 默认 选中往返
	$('.paragraphBtn li').click(function(){//段数 样式切换
		$(this).addClass('btnStyle').siblings().removeClass('btnStyle');
	});

	
	//添加 setMore
	$('.addIconTd').click(function(){
		var clonediv = $('.setMore').first();
		var divTest =  $('.setMore').last(); 
		var newDiv = clonediv.clone(false,true);
		divTest.after(newDiv);
		$('.setMore').each(function(i){
			if($('.setMore').length - 1 == i){
				$(this).find('.addIconTd').remove();
				$(this).find('.removeIconTd').remove();
				$('.setMore').first().find('.removeIconTd').hide();

				//设置新的出发城市
				var outCity = $(this).find('[name=outCity]');
				outCity.attr("id","outCity"+i);
				$('#outCity'+i).next().remove();
				//设置新的降落城市
				var singleArriveCity = $(this).find('[name=singleArriveCity]');
				singleArriveCity.attr("id","singleArriveCity"+i);
				$('#singleArriveCity'+i).next().remove();
				//清除出发返回日期框
				$('#outDatepicker'+i).val('');
				$('#returnDatepicker'+i).val('');
			}
		});
		initSelect2();
	});
	//删除 setMore
	$(".removeIconTd").click(function() {
		if($('.setMore').length > 1){
			$(this).parent().remove();
		}else{
			layer.msg("最后一个不能删",{time: 1000, icon:1});
		}
	});
});


/*-----------------------select2隐藏域赋值  start------------------------*/
/* 客户姓名 */
linkNameOpt = function(){
	$("#linkNameValidator"+i).val($('#linkNameId'+i).find("option:selected").text());
}
/* 出发城市 */
outCityNameOpt = function (){
	var cityName = $('#outCity'+i).find("option:selected").text();
	$("#outCityName"+i).val(cityName);
	var selectedCityId = $("#outCity"+i).select2("val");
	$("#outCityCode"+i).val(selectedCityId);
}
/* 抵达城市 */
arriveCityNameOpt = function(){
	var cityName = $('#singleArriveCity'+i).find("option:selected").text();
	$("#arriveCityName"+i).val(cityName);
	var selectedCityId = $("#singleArriveCity"+i).select2("val");
	$("#arriveCityCode"+i).val(selectedCityId);
}
/* 航空公司 */
airlineNameOpt = function(){
	var airName = $('#airline'+i).find("option:selected"+i).text();
	$("#airlineName"+i).val(airName);
	var selectedAirId = $("#airline"+i).select2("val");
	$("#airlineCode"+i).val(selectedAirId);
}
/*-----------------------select2隐藏域赋值  end----------------------------*/


