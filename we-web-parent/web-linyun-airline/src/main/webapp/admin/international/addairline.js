function initAirSelect2(obj){
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
$('.addHDtable').each(function(i){
	initAirSelect2($(this));
});
$('.addHDIcon').click(function(){
    var divTest = $(this).parent().parent(); 
    var newDiv = divTest.clone(false,true);
    var lastDiv = $('.addHD-tr').last();
    newDiv.find('[name=leavedate]').val('');
    newDiv.find('[name=leavetime]').val('');
    newDiv.find('[name=arrivetime]').val('');
    newDiv.find('[name=leavecity]').next().remove();
    newDiv.find('[name=leavecity]').empty();
    newDiv.find('[name=arrivecity]').next().remove();
    newDiv.find('[name=arrivecity]').empty();
    newDiv.find('[name=ailinenum]').next().remove();
    newDiv.find('[name=ailinenum]').empty();
    initAirSelect2(newDiv);
    lastDiv.after(newDiv);
    var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
    newDiv.find("p").html(No); 
    newDiv.find('.addHDIcon').parent().remove();
    newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removHDIcon"></i></td>');
});
$(document).on("click",".removHDIcon",function(){
	var thisdiv = $(this);
	var parentdiv = $(this).parent().parent();
	var airlength = '';
	var leavecity = parentdiv.find('[name=leavecity]').val();
	if (leavecity) {
		leavecity = leavecity.join(',');
		airlength += leavecity;
	}else{
		airlength += '';
	}
	//抵达城市
	var arrivecity = parentdiv.find('[name=arrivecity]').val();
	if (arrivecity) {
		arrivecity = arrivecity.join(',');
		airlength += arrivecity;
	}else{
		airlength += '';
	}
	var ailinenum = parentdiv.find('[name=ailinenum]').val();
	if (ailinenum) {
		ailinenum = ailinenum.join(',');
		airlength += ailinenum;
	}else{
		airlength += '';
	}
	var leavedate = parentdiv.find('[name=leavedate]').val();
	airlength += leavedate;
	var leavetime = parentdiv.find('[name=leavetime]').val();
	airlength += leavetime;
	var arrivetime = parentdiv.find('[name=arrivetime]').val();
	airlength += arrivetime;
	if(airlength){
		layer.confirm('确定要删除吗?', {icon: 3, title:'提示'}, function(index){
			thisdiv.parent().parent().remove();
			layer.close(index);
		});
	}else{
		thisdiv.parent().parent().remove();
	}
});

//select2 选项渲染
function formatRepoSelection(repo){
	var text =  repo.text;
	text = text.substr(0,3);
	return text;
}