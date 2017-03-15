//客户姓名下拉
$("#linkName").select2({
	ajax : {
		url : BASE_PATH+'/admin/search/getLinkNameSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				linkname : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.linkMan; // replace pk with your identifier
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
//选中客户姓名之后，其他信息自动填充
/* 选中客户名称 */
$("#linkName").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	$("#customerId").val(customerId);
	$.ajax({
		type : 'POST',
		data : {
			"id":$("#customerId").val()
		},
		dataType:'json',
		url : BASE_PATH+'/admin/search/getCustomerById.html',
		success : function(data) {
			var dataJson = jQuery.parseJSON(data); 
			$("#shortName").val(dataJson.customerInfoEntity.shortName);
			$("#telephone").val(dataJson.customerInfoEntity.telephone);
			$("#address").val(dataJson.customerInfoEntity.address);
			$("#responsible").val(dataJson.responsibleName);
			$("#siteUrl").val(dataJson.customerInfoEntity.siteUrl);
			$("#fax").val(dataJson.customerInfoEntity.fax);
			/* 出发城市补全 */
			$("#departureCity").val(dataJson.customerInfoEntity.departureCity);
			$("#discountFare").val(dataJson.customerInfoEntity.discountFare);
			$("#fees").val(dataJson.customerInfoEntity.fees);
		},
		error : function() {
		}
	});
});
function initCitySelect2(obj){
	//加载起飞城市下拉
	obj.find('[name=leavecity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var backscity = obj.find('[name=arrivecity]').val();
				if(backscity){
					backscity = backscity.join(',');
				}
				return {
					exname : backscity,
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
	
	//加载起飞城市下拉
	obj.find('[name=arrivecity]').select2({
		ajax : {
			url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				var backscity = obj.find('[name=leavecity]').val();
				if(backscity){
					backscity = backscity.join(',');
				}
				return {
					exname : backscity,
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
}
//初始化航班信息select2
function initAirInfoSelect2(obj){
	obj.find('[name=aircom]').select2({
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
	//加载返程航班下拉
	obj.find('[name=ailinenum]').select2({
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
}
$(function(){
	var firstDemandDiv = $('.DemandDiv').first();
	initCitySelect2(firstDemandDiv);
	initAirInfoSelect2(firstDemandDiv.find('[name=airlineinfo]'));
    $('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
        $('.hideTable').toggle();
      });
    //客户需求的 + 按钮
    $(document).on("click",".addIcon",function(){
        var divTest = $(this).parent().parent().parent().find('[name=airlineinfo]').last(); 
        var newDiv = divTest.clone(false,true);
        divTest.after(newDiv);
        newDiv.find('[name=aircom]').next().remove();
        newDiv.find('[name=ailinenum]').next().remove();
        newDiv.find('[name=leavetime]').val('');
        newDiv.find('[name=arrivetime]').val('');
        newDiv.find('[name=formprice]').val('');
        newDiv.find('[name=price]').val('');
		initAirInfoSelect2(newDiv);
        var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
        newDiv.find("p").html(No); 
        newDiv.find('.addIcon').remove();
        newDiv.find('.removeIconTd').remove();
        newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removIcon"></i></td>');
    });
    //客户需求的 - 按钮
    $(document).on("click",".removIcon",function(){
        $(this).parent().parent().remove();
    });

    //客户需求的 +需求 按钮
    $('.addDemand').click(function(){
        var divTest = $(this).parent(); 
        var newDiv = divTest.clone(false,true);
        newDiv.find('[name=leavecity]').next().remove();
        newDiv.find('[name=arrivecity]').next().remove();
        //清空出发日期
        newDiv.find('[name=leavedate]').val('');
        //清空人数
        newDiv.find('[name=peoplecount]').val('');
        //清空票务类型
        newDiv.find('[name=tickettype]').val('');
        initCitySelect2(newDiv);
        //divTest.after(newDiv);
        $('#infofooter').append(newDiv);
        var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
        newDiv.find("p").html(No); 
        newDiv.find('.addDemand').remove();
        newDiv.prepend('<a href="javascript:;" class="btn btn-primary btn-sm removeDemand"><b>-</b>&nbsp;&nbsp;需求</a>');
        var divId=document.getElementById('infofooter').getElementsByTagName('div');
        newDiv.find('.titleNum').text(divId.length);
        newDiv.find('[name=airlineinfo]').each(function(i){
        	if(i > 0){
        		$(this).remove();
        	}else{
        		$(this).find('[name=aircom]').next().remove();
        		$(this).find('[name=ailinenum]').next().remove();
        		$(this).find('[name=leavetime]').val('');
        		$(this).find('[name=arrivetime]').val('');
        		$(this).find('[name=formprice]').val('');
        		$(this).find('[name=price]').val('');
        		initAirInfoSelect2($(this));
        	}
        });
    });
    //客户需求的 -需求 按钮
    $(document).on("click",".removeDemand",function(){
        $(this).parent().remove();
    });
  });
