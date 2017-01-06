/* 加载客户姓名下拉 */
$("#linkNameId").select2({
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
		cache : true
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

/* 加载电话下拉 */
$("#phoneNumId").select2({
	ajax : {
		url : BASE_PATH+'/admin/search/getPhoneNumSelect.html',
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				phonenum : params.term, // search term
				page : params.page
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;
			var selectdata = $.map(data, function (obj) {
				obj.id = obj.id; // replace pk with your identifier
				obj.text = obj.telephone; // replace pk with your identifier
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
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false //设置必须存在的选项 才能选中
});
/* 加载出发城市样式 */
$("#city").select2({});

/* 选中客户名称 */
$("#linkNameId").on('select2:select', function (evt) {
	var customerId = $(this).select2("val");
	$("#linkManId").val(customerId);
	$.ajax({
		type : 'POST',
		data : {
			"id":$("#linkManId").val()
		},
		dataType:'json',
		url : BASE_PATH+'/admin/search/getCustomerById.html',
		success : function(data) {
			var dataJson = jQuery.parseJSON(data); 
			var phoneNum = dataJson.customerInfoEntity.telephone;
			var payType = dataJson.customerInfoEntity.payType;
			$("#addressId").val(dataJson.customerInfoEntity.address);
			$("#shortNameId").val(dataJson.customerInfoEntity.shortName);
			$("#agentId").val(dataJson.customerInfoEntity.agent);
			$("#siteUrlId").val(dataJson.customerInfoEntity.siteUrl);
			$("#faxId").val(dataJson.customerInfoEntity.fax);
			if(payType == 1){
				$("#payTypeId").html("月结");
			}else if(payType == 2){
				$("#payTypeId").html("周结");
			}else if(payType == 3){
				$("#payTypeId").html("单结");
			}else if(payType == 4){
				$("#payTypeId").html(dataJson.customerInfoEntity.paytypeName);
			}
			/* 电话补全 */
			$("#phoneNumId").select2({
				initSelection : function (element, callback) {
					var data = {id: 1, text: phoneNum};
					callback(data);
				}
			});
			/* 出发城市补全 */
			$("#city").select2({
				initSelection : function (element, callback) {
					var data = dataJson.outcitylist;
					callback(data);
				}
			});
		},
		error : function() {
		}
	});

});
/* 选中电话 */
$("#phoneNumId").on('select2:select', function (evt) {
	var _customerId = $(this).select2("val");
	$("#phoneId").val(_customerId);
	$.ajax({
		type : 'POST',
		data : {
			id:$("#phoneId").val()
		},
		dataType:'json',
		url : BASE_PATH+'/admin/search/getCustomerById.html',
		success : function(data) {
			var dataJson = jQuery.parseJSON(data); 
			var dataJson = jQuery.parseJSON(data); 
			var linkName = dataJson.customerInfoEntity.linkMan;
			var payType = dataJson.customerInfoEntity.payType;
			$("#addressId").val(dataJson.customerInfoEntity.address);
			$("#shortNameId").val(dataJson.customerInfoEntity.shortName);
			$("#agentId").val(dataJson.customerInfoEntity.agent);
			$("#siteUrlId").val(dataJson.customerInfoEntity.siteUrl);
			$("#faxId").val(dataJson.customerInfoEntity.fax);
			if(payType == 1){
				$("#payTypeId").html("月结");
			}else if(payType == 2){
				$("#payTypeId").html("周结");
			}else if(payType == 3){
				$("#payTypeId").html("单结");
			}else if(payType == 4){
				$("#payTypeId").html(dataJson.customerInfoEntity.paytypeName);
			}
			/* 客户名称补全 */
			$("#linkNameId").select2({
				initSelection : function (element, callback) {
					var data = {id: 1, text: linkName};
					callback(data);
				}
			});
			/* 出发城市补全 */
			$("#city").select2({
				initSelection : function (element, callback) {
					var data = dataJson.outcitylist;
					callback(data);
				}
			});
		},
		error : function() {
		}
	});
});
/* 取消选中时 */
$("#linkNameId").on('select2:unselect', function (evt) {
	clearText();
}); 
$("#phoneNumId").on('select2:unselect', function (evt) {
	clearText();
}); 

/* 清除按钮 */
$("#clearBtn").click(function(){
	clearText();
});
/*客户信息 清除回显内容*/
function clearText(){
	//客户姓名清空
	$("#linkNameId").val(null).trigger("change");
	//电话清空
	$("#phoneNumId").val(null).trigger("change");
	//出发城市清空
	$("#city").val(null).trigger("change");
	//文本框清空
	$("#addressId").val("");
	$("#shortNameId").val("");
	$("#agentId").val("");
	$("#siteUrlId").val("");
	$("#faxId").val("");
	//付款方式清除
	$("#payTypeId").html("");
}

/*散客 航程类型 点击事件*/
function radioFunct(obj){
	var radio = document.getElementsByName("voyageType");  
	for (i=0; i<radio.length; i++) {  
		if (radio[i].checked) {  
			var radioValue=radio[i].value;
			if (radioValue==1) {
				$('.setoutLabel').hide('300');
				$('.setoutinput').hide('300');
				$('.addIconTd').hide('300');
				$('.removeIconTd').hide('300');
				//清除
				
			}else if(radioValue==2){
				$('.setoutLabel').show('300');
				$('.setoutinput').show('300');
				$('.addIconTd').hide('300');
				$('.removeIconTd').hide('300');
			}else if(radioValue==3){
				$('.setoutLabel').show('300');
				$('.setoutinput').show('300');
				$('.addIconTd').show('300');
				$('.removeIconTd').show('300');
			};
		}  
	}
}
/*团队 航程类型 点击事件*/
function radioFunct1(){
	var radio1 = document.getElementsByName("voyageType1");  
	for (i=0; i<radio1.length; i++) {  
		if (radio1[i].checked) {  
			var radioValue1=radio1[i].value;
			if (radioValue1==1) {
				$('.setoutLabel').hide('300');
				$('.setoutinput').hide('300');
				$('.addIconTd').hide('300');
				$('.removeIconTd').hide('300');
			}else if(radioValue1==2){
				$('.setoutLabel').show('300');
				$('.setoutinput').show('300');
				$('.addIconTd').hide('300');
				$('.removeIconTd').hide('300');
			}else if(radioValue1==3){
				$('.setoutLabel').show('300');
				$('.setoutinput').show('300');
				$('.addIconTd').show('300');
				$('.removeIconTd').show('300');
			};
		}  
	}
}
