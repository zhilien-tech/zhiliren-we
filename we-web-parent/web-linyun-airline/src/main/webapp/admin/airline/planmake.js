//计划制作表格初始化
var datatable1;
function initDatatable1() {
    datatable1 = $('#example3').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "language": {
            "url": BASE_PATH + "/public/plugins/datatables/cn.json"
        },
        "ajax": {
            "url": BASE_PATH + "/admin/customneeds/listPlanMakeData.html",
            "type": "post",
            "data": function (d) {
            	
            }
        },
        "fnDrawCallback"    : function(){
        	var api = this.api();
        	var startIndex= api.context[0]._iDisplayStart;
   	       　　  api.column(0).nodes().each(function(cell, i) {
   	       　　　　cell.innerHTML = startIndex + i + 1; 
   	       　　});
      	},
        "columns": [
                    {"data": "xuhao", "bSortable": false},
                    {"data": "airlinename", "bSortable": false},
                    {"data": "leavesdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var leavesdate = new Date(row.leavesdate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][leavesdate.getMonth()];
                    		return leavesdate.getDate() + "/" + MM;
                    	}
                    },
                    {"data": "leavescity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		return row.leavescity + "-" + row.backscity;
                    	}
                    },
                    {"data": "backsdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var backsdate = new Date(row.backsdate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][backsdate.getMonth()];
                    		return backsdate.getDate() + "/" + MM;
                    	}
                    },
                    {"data": "backscity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		return row.backscity + "-" + row.leavescity;
                    	}	
                    },
                    {"data": "peoplecount", "bSortable": false},
                    {"data": "dayscount", "bSortable": false},
                    {"data": "travelname", "bSortable": false},
                    {"data": "unioncity", "bSortable": false}
            ],
        columnDefs: [{
    		targets: 0,
            render: function(data, type, row, meta) {
                return null
            }
    	}]
    });
}
//加载计划制作列表
initDatatable1();
//加载所有的select2
initSelect2();
//加载旅行社智能下拉
function initSelect2(){
	$('.addMake').each(function(i){
		if($('.addMake').length - 1 == i){
			$("#travelname"+i).select2({
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
							  obj.id =  obj.dictName; // replace pk with your identifier
							  obj.text =  obj.dictName; // replace pk with your identifier
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
				maximumSelectionLength : 1 //设置最多可以选择多少项
				//tags : false, //设置必须存在的选项 才能选中
			});
			//加载出发航班号下拉
			$("#leaveairline" + i).select2({
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
							  obj.id = obj.dictName; // replace pk with your identifier
							  obj.text = obj.dictName; // replace pk with your identifier
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
				maximumSelectionLength : 1 //设置最多可以选择多少项
				//tags : false, //设置必须存在的选项 才能选中
			});
			//加载返程航班下拉
			$("#backairline" + i).select2({
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
							  obj.id = obj.dictName; // replace pk with your identifier
							  obj.text = obj.dictName; // replace pk with your identifier
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
				maximumSelectionLength : 1 //设置最多可以选择多少项
				//tags : false, //设置必须存在的选项 才能选中
			});
			//加载起飞城市下拉
			$("#leavescity" + i).select2({
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
							  obj.id = obj.dictName; // replace pk with your identifier
							  obj.text = obj.dictName; // replace pk with your identifier
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
				maximumSelectionLength : 1 //设置最多可以选择多少项
				//tags : false, //设置必须存在的选项 才能选中
			});
			//加载降落城市下拉
			$("#backscity" + i).select2({
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
							  obj.id = obj.dictName; // replace pk with your identifier
							  obj.text = obj.dictName; // replace pk with your identifier
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
				maximumSelectionLength : 1 //设置最多可以选择多少项
				//tags : false, //设置必须存在的选项 才能选中
			});
			//加载联运城市下拉
			$("#unioncity" + i).select2({
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
							  obj.id = obj.dictName; // replace pk with your identifier
							  obj.text = obj.dictName; // replace pk with your identifier
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
				maximumSelectionLength : 1 //设置最多可以选择多少项
				//tags : false, //设置必须存在的选项 才能选中
			});
			//初始化起始日期
			var startdatepicker = new Pikaday({
			    field: document.getElementById('startdate' + i),
			    firstDay: 1,
			    minDate: new Date(),
			    maxDate: new Date('2120-12-31'),
			    yearRange: [2000,2020]
			});
			//初始化结束日期
			var enddatepicker = new Pikaday({
			    field: document.getElementById('enddate' + i),
			    firstDay: 1,
			    minDate: new Date('2000-01-01'),
			    maxDate: new Date('2120-12-31'),
			    yearRange: [2000,2020]
			});
			//初始化小日历
			new Kalendae({
			    attachTo:document.getElementById('minCalender' + i),
			    months:3,
			    mode:'multiple',
			    subscribe: {
			         'date-clicked': function (date) {
			             console.log(date, this.getSelected());
			             $('#calenderdate' + i).val(this.getSelected());
			             // return false;
			         }
			     },//点击 获取到当天的时间
			    selected:[Kalendae.moment().subtract({M:1}), Kalendae.moment().add({M:1})]
			});
		}
	});
	
}

//计划制作的 时间选择
function select_change(obj){
   var seleValue=$(obj).find("option:selected").attr("value");
   var addMake_aa = $(obj).closest(".aa") ; 
   if (seleValue==2) {
        addMake_aa.find('.checkWeek').slideUp("slow");//checked hide
        addMake_aa.find('.hidnCalendar').slideDown("slow");//div show
     }else{
       addMake_aa.find('.checkWeek').slideDown("slow");//checked show
       addMake_aa.find('.hidnCalendar').slideUp("slow");//div hide
  };
}

$(function () {
	//添加 addMake
	$('.addIcon').click(function(){
	   var divTest =  $('.addMake').last(); 
	   var newDiv = divTest.clone(false,true);
	   divTest.after(newDiv);
	   $('.addMake').each(function(i){
		   if($('.addMake').length - 1 == i){
			   $(this).find('[name=addButton]').remove();
			   $(this).find('[name=closeButton]').remove();
			   //设置新的旅行社Id
			   var travelname = $(this).find('[name=travelname]');
			   travelname.attr("id","travelname"+i);
			   $('#travelname'+i).next().remove();
			   //人数置空
			   $(this).find('[name=peoplecount]').val('');
			   //天数置空
			   $(this).find('[name=dayscount]').val('');
			   //设置新的出发航班Id
			   var leaveairline = $(this).find('[name=leaveairline]');
			   leaveairline.attr("id","leaveairline"+i);
			   $('#leaveairline'+i).next().remove();
			   //设置新的返回航班下拉框ID
			   var backairline = $(this).find('[name=backairline]');
			   backairline.attr("id","backairline"+i);
			   $('#backairline'+i).next().remove();
			   //设置新的出发城市下拉ID
			   var leavescity = $(this).find('[name=leavescity]');
			   leavescity.attr("id","leavescity"+i);
			   $('#leavescity'+i).next().remove();
			   //设置新的降落城市下拉框ID
			   var backscity = $(this).find('[name=backscity]');
			   backscity.attr("id","backscity"+i);
			   $('#backscity'+i).next().remove();
			   //设置新的联运城市下拉框ID
			   var unioncity = $(this).find('[name=unioncity]');
			   unioncity.attr("id","unioncity"+i);
			   $('#unioncity'+i).next().remove();
			   //设置新的开始日期控件ID
			   var startdate = $(this).find('[name=startdate]');
			   startdate.attr("id","startdate"+i);
			   $('#startdate'+i).val('');
			   //设置新的结束日期控件ID
			   var enddate = $(this).find('[name=enddate]');
			   enddate.attr("id","enddate"+i);
			   $('#enddate'+i).val('');
			   //清空选择每周
			   $(this).find('input[name=weekday]').each(function() { 
				   $(this).attr("checked", false); 
			  }); 
			   //设置新的小日历ID
			   var minCalender = $(this).find('[name=minCalender]');
			   minCalender.attr("id","minCalender"+i);
			   $('#minCalender'+i).empty();
			   //设置新的小日历隐藏域ID
			   var calenderdate = $(this).find('[name=calenderdate]');
			   calenderdate.attr("id","calenderdate"+i);
		   }
	   });
	   initSelect2();
	   var No = parseInt(divTest.find("p").html())+1;   //假设你用p标签显示序号
	   newDiv.find("p").html(No);  
	})
	//删除 addMake
	$(".removIcon").click(function() {
		if($('.addMake').length > 1){
			$('.addMake').last().remove();
		}
	});
});

//制作计划 
function makePlan(){
	var divlength = $('.addMake').length;
	var teamtype = $('#teamtype').val();
	//layer.load(2);
	if(checkIsNull()){
		$('.addMake').each(function(i){
			var travelname = $(this).find('[name=travelname]').val();
			if(travelname){
				travelname = travelname.join(',');
			}
			var peoplecount = $(this).find('[name=peoplecount]').val();
			var dayscount = $(this).find('[name=dayscount]').val();
			var leaveairline = $(this).find('[name=leaveairline]').val();
			if (leaveairline) {
				leaveairline = leaveairline.join(',');
			}
			var backairline = $(this).find('[name=backairline]').val();
			if (backairline) {
				backairline = backairline.join(',');
			}
			var leavescity = $(this).find('[name=leavescity]').val();
			if (leavescity) {
				leavescity = leavescity.join(',');
			}
			var backscity = $(this).find('[name=backscity]').val();
			if (backscity) {
				backscity = backscity.join(',');
			}
			var unioncity = $(this).find('[name=unioncity]').val();
			if (unioncity) {
				unioncity = unioncity.join(',');
			}
			var startdate = $(this).find('[name=startdate]').val();
			var enddate = $(this).find('[name=enddate]').val();
			var calenderdate = $(this).find('[name=calenderdate]').val();
			var weekday =[];    
			$(this).find('input[name="weekday"]:checked').each(function(){    
				weekday.push($(this).val());    
			});   
			if (weekday) {
				weekday = weekday.join(',');
			}
			var param = {teamtype:teamtype,
						 travelname:travelname,
						 peoplecount:peoplecount,
						 dayscount:dayscount,
						 leaveairline:leaveairline,
						 backairline:backairline,
						 leavescity:leavescity,
						 backscity:backscity,
						 unioncity:unioncity,
						 startdate:startdate,
						 enddate:enddate,
						 calenderdate:calenderdate,
						 weekday:weekday};
			$.ajax({ 
				type: 'POST', 
				data: param, 
				url: BASE_PATH + '/admin/customneeds/airlineMakePlan.html',
	            success: function (data) {
	            	$(this).remove();
	            	if(divlength-1 == i){
	            		//layer.closeAll('loading');
	            		layer.msg("制作成功",{time: 2000, icon:1});
	            		datatable1.ajax.reload();
	            	}
	            },
	            error: function (xhr) {
	            	layer.msg("制作失败",{time: 2000, icon:1});
	            } 
	        });
		});
	}
}
//判断填写是否正确
function checkIsNull(){
	var result = true;
	$('.addMake').each(function(i){
		var travelname = $(this).find('[name=travelname]').val();
		if(travelname){
			travelname = travelname.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个旅行社",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var peoplecount = $(this).find('[name=peoplecount]').val();
		if(!peoplecount){
			layer.msg("请填写第"+(i+1)+"个人数",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var dayscount = $(this).find('[name=dayscount]').val();
		if(!dayscount){
			layer.msg("请填写第"+(i+1)+"个天数",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var leaveairline = $(this).find('[name=leaveairline]').val();
		if (leaveairline) {
			leaveairline = leaveairline.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个去程航班",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var backairline = $(this).find('[name=backairline]').val();
		if (backairline) {
			backairline = backairline.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个回程航班",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var leavescity = $(this).find('[name=leavescity]').val();
		if (leavescity) {
			leavescity = leavescity.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个出发城市",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var backscity = $(this).find('[name=backscity]').val();
		if (backscity) {
			backscity = backscity.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个返回城市",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var unioncity = $(this).find('[name=unioncity]').val();
		if (unioncity) {
			unioncity = unioncity.join(',');
		}else{
			layer.msg("请填写第"+(i+1)+"个联运城市",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var startdate = $(this).find('[name=startdate]').val();
		if(!startdate){
			layer.msg("请填写第"+(i+1)+"个起始日期",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var enddate = $(this).find('[name=enddate]').val();
		if(!enddate){
			layer.msg("请填写第"+(i+1)+"个截止日期",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var calenderdate = $(this).find('[name=calenderdate]').val();
		var weekday =[];    
		$(this).find('input[name="weekday"]:checked').each(function(){    
			weekday.push($(this).val());    
		});   
		if (weekday) {
			weekday = weekday.join(',');
		}
		if(!weekday && !calenderdate){
			layer.msg("请选择第"+(i+1)+"个每周或自由中的一种",{time: 2000, icon:1});
			result = false;
			return false;
		}
	});
	return result;
}
//保存计划
function savePlan(){
	layer.confirm('确定要保存计划吗?', {icon: 3, title:'提示'}, function(){
		$.ajax({ 
			type: 'POST', 
			data: {}, 
			url: BASE_PATH + '/admin/customneeds/savePlanData.html',
            success: function (data) { 
            	layer.msg("保存成功",{time: 2000, icon:1});
            	datatable1.ajax.reload();
            },
            error: function (xhr) {
            	layer.msg("保存失败",{time: 2000, icon:1});
            } 
        });
	});
}