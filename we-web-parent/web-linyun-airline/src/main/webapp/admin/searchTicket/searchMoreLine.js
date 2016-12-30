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


