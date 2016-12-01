$("#companyID").select2({
	ajax : {
		url : BASE_PATH + "/admin/customer/company.html",
		dataType : 'json',
		delay : 250,
		type : 'post',
		data : function(params) {
			return {
				q : params.term, // search term
				page : params.page,
			};
		},
		processResults : function(data, params) {
			params.page = params.page || 1;

			return {
				results : data
			};
		},
		cache : true
	},
	val: agentId,
	escapeMarkup : function(markup) {
		return markup;
	}, // let our custom formatter work
	minimumInputLength : 1,
	maximumInputLength : 20,
	language : "zh-CN", //设置 提示语言
	maximumSelectionLength : 1, //设置最多可以选择多少项
	tags : false, //设置必须存在的选项 才能选中
});


$("#city").select2({
		ajax : {
			url : BASE_PATH  + "/admin/customer/goCity.html",
			dataType : 'json',
			delay : 250,
			type : 'post',
			data : function(params) {
				return {
					q : params.term, // search term
					page : params.page,
				};
			},
			processResults : function(data, params) {
				params.page = params.page || 1;

				return {
					results : data
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
		maximumSelectionLength : 5, //设置最多可以选择多少项
		tags : true, //设置必须存在的选项 才能选中
	});





//日期
var picker1 = new Pikaday(
	    {
	        field: document.getElementById('datepicker1'),
	        firstDay: 1,
	        minDate: new Date('2000-01-01'),
	        maxDate: new Date('3099-12-31'),
	        yearRange: [2000,3099]
	    });
var picker2 = new Pikaday(
	    {
	        field: document.getElementById('datepicker2'),
	        firstDay: 1,
	        minDate: new Date('2000-01-01'),
	        maxDate: new Date('3099-12-31'),
	        yearRange: [2000,3099]
	    });
