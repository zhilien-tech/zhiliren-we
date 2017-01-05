//计划制作表格初始化
var datatable1;
function initDatatable1() {
    datatable1 = $('#example3').DataTable({
    	"searching":false,
    	"bLengthChange": false,
        "processing": true,
        "serverSide": true,
        "stripeClasses": [ 'strip1','strip2' ],
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
                    {"data": "leavesdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var leavesdate = new Date(row.leavesdate);
                    		var backsdate = new Date(row.backsdate);
                    		var MM = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'][leavesdate.getMonth()];
                    		var week = ['MO','TU','WE','TH','FR','SA','SU'][leavesdate.getUTCDay()]
                    		var MM2 = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'][backsdate.getMonth()];
                    		var week2 = ['MO','TU','WE','TH','FR','SA','SU'][backsdate.getUTCDay()]
                    		var result = '<ul><li style="list-style:none;">'+(week+leavesdate.getDate() + MM)+'</li>'
                    		+'<li style="list-style:none;">'+(week2+backsdate.getDate() + MM2)+'</li>'
                    		+'</ul>';
                    		return result;
                    	}
                    },
                    {"data": "leavescity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>'
                        		+'<li style="list-style:none;">'+row.leaveairline+'</li>'
                        		+'<li style="list-style:none;">'+row.backairline+'</li>'
                        		+'</ul>';
                    		return result;
                    	}
                    },
                    {"data": "backsdate", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>' 
                        		+'<li style="list-style:none;">'+(row.leavescity +'/'+ row.backscity)+'</li>'
                        		+'<li style="list-style:none;">'+(row.backleavecity +'/'+ row.backbackcity)+'</li>'
                        		+'</ul>';
                    		return result;
                    	}
                    },
                    {"data": "backscity", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '<ul>' 
                        		+'<li style="list-style:none;">'+(row.lleavetime +'/'+ row.lbacktime)+'</li>'
                        		+'<li style="list-style:none;">'+(row.bleavetime +'/'+ row.bbacktime)+'</li>'
                        		+'</ul>';
                    		return result;
                    	}	
                    },
                    {"data": "peoplecount", "bSortable": false},
                    {"data": "foc", "bSortable": false,
                    	render: function(data, type, row, meta) {
                    		var result = '否';
                    		if(row.foc == 1){
                    			result = '16/1';
                    		}
                    		return result;
                    	}
                    },
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
							  obj.id =  obj.comname; // replace pk with your identifier
							  obj.text =  obj.comname; // replace pk with your identifier
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
			//加载出发航班号下拉
			$("#leaveairline" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var backairline = $('#backairline'+i).val();
						if(backairline){
							backairline = backairline.join(',');
						}
						return {
							exname : backairline,
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
			//加载返程航班下拉
			$("#backairline" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getAirLineSelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var leaveairline = $('#leaveairline'+i).val();
						if(leaveairline){
							leaveairline = leaveairline.join(',');
						}
						return {
							exname : leaveairline,
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
			//加载起飞城市下拉
			$("#leavescity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var backscity = $('#backscity'+i).val();
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
							  obj.text = obj.dictCode+'('+obj.dictName+')'; // replace pk with your identifier
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
			//加载降落城市下拉
			$("#backscity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var leavescity = $('#leavescity'+i).val();
						if(leavescity){
							leavescity = leavescity.join(',');
						}
						return {
							exname : leavescity,
							cityname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							  obj.id = obj.dictCode; // replace pk with your identifier
							  obj.text = obj.dictCode+'('+obj.dictName+')'; // replace pk with your identifier
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
			//回程出发城市下拉
			$("#backleavecity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var backbackcity = $('#backbackcity'+i).val();
						if(backbackcity){
							backbackcity = backbackcity.join(',');
						}
						return {
							exname : backbackcity,
							cityname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; // replace pk with your identifier
							obj.text = obj.dictCode+'('+obj.dictName+')'; // replace pk with your identifier
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
			//回程抵达城市下拉
			$("#backbackcity" + i).select2({
				ajax : {
					url : BASE_PATH + "/admin/customneeds/getCitySelect.html",
					dataType : 'json',
					delay : 250,
					type : 'post',
					data : function(params) {
						var backleavecity = $('#backleavecity'+i).val();
						if(backleavecity){
							backleavecity = backleavecity.join(',');
						}
						return {
							exname : backleavecity,
							cityname : params.term, // search term
							page : params.page
						};
					},
					processResults : function(data, params) {
						params.page = params.page || 1;
						var selectdata = $.map(data, function (obj) {
							obj.id = obj.dictCode; // replace pk with your identifier
							obj.text = obj.dictCode+'('+obj.dictName+')'; // replace pk with your identifier
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
							  obj.id = obj.dictCode; // replace pk with your identifier
							  var text = obj.dictCode;
							  if(obj.dictName){
								  text = obj.dictCode+'('+obj.dictName+')'
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
				
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				minimumInputLength : 1,
				maximumInputLength : 20,
				language : "zh-CN", //设置 提示语言
				maximumSelectionLength : 1, //设置最多可以选择多少项
				tags : false, //设置必须存在的选项 才能选中
			});
			//初始化起始日期
			/*var startdatepicker = new Pikaday({
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
			});*/
			//初始化小日历
			new Kalendae({
			    attachTo:document.getElementById('minCalender' + i),
			    months:3,
			    direction:'today-future',
			    mode:'multiple',
			    subscribe: {
			         'date-clicked': function (date) {
			             console.log(date, this.getSelected());
			             var selectdate = this.getSelected();
			             if(selectdate){
			            	 if(selectdate.indexOf(FormatDate(date))!=-1){
			            		 selectdate = selectdate.substr(0,selectdate.indexOf(FormatDate(date))-2);
			            	 }else{
			            		 selectdate += ',' + FormatDate(date);
			            	 }
			             }else{
			            	 selectdate += FormatDate(date);
			             }
			             console.log(selectdate);
			             $('#calenderdate' + i).val(selectdate);
			             // return false;
			         }
			     }//点击 获取到当天的时间
			     
			    //selected:[Kalendae.moment().subtract({M:1}), Kalendae.moment().add({M:1})]
			});
			 $('.kalendae').append('<input type="button" class="btn btn-sm btn-primary celenderBtn" value="确定">');
			//计划执着 隐藏日历按钮
		    $('#hidebutton'+i).click(function(){
		      $('#minCalender'+i).slideUp("slow");
		      $(this).hide();
		      $('#showbutton'+i).slideDown("slow");
		    });

		    //计划执着 隐藏日历按钮
		    $('#showbutton'+i).click(function(){
		      $('#minCalender'+i).slideDown("slow");
		      $(this).hide();
		      $('#hidebutton'+i).slideDown("slow");
		    });
		}
	});
	
}

//计划制作的 时间选择  obj当前div对象   status  是否显示过渡效果
function select_change(obj,status){
   var seleValue=$(obj).find("option:selected").attr("value");
   var addMake_aa = $(obj).closest(".aa") ; 
   if (seleValue==2) {
        addMake_aa.find('.checkWeek').slideUp("slow");//checked hide
        addMake_aa.find('.hidnCalendar').slideDown("slow");//div show
        addMake_aa.find('.hidnBtn').slideDown("slow");//show 隐藏日历 按钮
     }else{
    	 if(status){
    		 addMake_aa.find('.checkWeek').show();//checked show
    		 addMake_aa.find('.hidnCalendar').hide();//div hide
    		 addMake_aa.find('.hidnBtn').hide();//hide 隐藏日历 按钮
    		 addMake_aa.find('.showBtn').hide();
    	 }else{
    		 addMake_aa.find('.checkWeek').slideDown("slow");//checked show
    		 addMake_aa.find('.hidnCalendar').slideUp("slow");//div hide
    		 addMake_aa.find('.hidnBtn').slideUp("slow");//hide 隐藏日历 按钮
    		 addMake_aa.find('.showBtn').slideUp("slow");
    	 }
  };
}

$(function () {
	//添加 addMake
	$('.addIcon').click(function(){
	   var clonediv = $('.addMake').first();
	   var divTest =  $('.addMake').last(); 
	   var newDiv = clonediv.clone(false,true);
	   divTest.after(newDiv);
	   $('.addMake').each(function(i){
		   if($('.addMake').length - 1 == i){
			   $(this).find('.addIcon').remove();
			   $(this).find('.removIcon').remove();
			   $('.addMake').first().find('.removIcon').hide();
			   $(this).children().first().before('<a href="javascript:;" name="closeButton" class="removeBtn glyphicon glyphicon-minus removIcon removAddMake"></a><!--删除div按钮->');
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
			   //设置新的去程出发城市下拉ID
			   var leavescity = $(this).find('[name=leavescity]');
			   leavescity.attr("id","leavescity"+i);
			   $('#leavescity'+i).next().remove();
			   //设置新的去程抵达城市下拉框ID
			   var backscity = $(this).find('[name=backscity]');
			   backscity.attr("id","backscity"+i);
			   $('#backscity'+i).next().remove();
			   //设置新的返程出发城市下拉ID
			   var backleavecity = $(this).find('[name=backleavecity]');
			   backleavecity.attr("id","backleavecity"+i);
			   $('#backleavecity'+i).next().remove();
			   //设置新的返程抵达城市下拉框ID
			   var backbackcity = $(this).find('[name=backbackcity]');
			   backbackcity.attr("id","backbackcity"+i);
			   $('#backbackcity'+i).next().remove();
			   //设置新的联运城市下拉框ID
			   var unioncity = $(this).find('[name=unioncity]');
			   unioncity.attr("id","unioncity"+i);
			   $('#unioncity'+i).next().remove();
			   
			   var startenddate = $(this).find('[name=startenddate]');
			   startenddate.empty();
			   var startenddatestr = '<input id="startdate'+i+'" name="startdate" type="text" onFocus='+"WdatePicker({maxDate:'#F{$dp.$D(\\'enddate"+i+"\\')}'})"+' class="form-control input-sm timeWid inputdatestr startdatestr" placeholder="2016-11-05">'+ 
                   '- <input id="enddate'+i+'" name="enddate" type="text" onFocus='+"WdatePicker({minDate:'#F{$dp.$D(\\'startdate"+i+"\\')}'})" +' class="form-control input-sm timeWid inputdatestr enddatestr" placeholder="2016-12-01">';
			   startenddate.html(startenddatestr);
			   //设置新的开始日期控件ID
			   /*var startdate = $(this).find('[name=startdate]');
			   startdate.attr("id","startdate"+i);
			   $('#startdate'+i).val('');
			   //设置新的结束日期控件ID
			   var enddate = $(this).find('[name=enddate]');
			   enddate.attr("id","enddate"+i);*/
			   //startdate.attr("onFocus","WdatePicker({maxDate:'#F{$dp.$D(\\'enddate"+i+"\\')}'})");
			   //enddate.attr("onFocus","WdatePicker({minDate:'#F{$dp.$D(\\'startdate"+i+"\\')}'})");
			   //显示按钮 
			   var showbutton = $(this).find('[name=showbutton]');
			   showbutton.attr("id","showbutton"+i);
			   //隐藏按钮
			   var hidebutton = $(this).find('[name=hidebutton]');
			   hidebutton.attr("id","hidebutton"+i);
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
	   select_change(newDiv.find('[id=weekSelect]'),1);
	   var No = parseInt(divTest.find("p").html())+1;   //假设你用p标签显示序号
	   newDiv.find("p").html(No);  
	})
	//删除 addMake
	$(".removIcon").click(function() {
		if($('.addMake').length > 1){
			$(this).parent().remove();
		}else{
			layer.alert("最后一个不能删",{time: 2000, icon:1});
		}
	});
});

$(document).on('click', '.removeBtn', function(e) {
	if($('.addMake').length > 1){
		if($('.addMake').length == 2){
			$('.addMake').first().find('.removIcon').show();
		}
		$(this).parent().remove();
	}
});
/*$(document).on('focus', '.startdatestr', function(e) {
	var enddateid = $(this).next(".inputdatestr").attr("id");
	return "WdatePicker({maxDate:'#F{$dp.$D(\'"+enddateid+"\')}'})";
});
$(document).on('focus', '.enddatestr', function(e) {
	var startdateid = $(this).prev(".inputdatestr").attr("id");
	return "WdatePicker({minDate:'#F{$dp.$D(\'"+startdateid+"\')}'})";
});*/
//制作计划 
function makePlan(){
	var divlength = $('.addMake').length;
	var teamtype = $('#teamtype').val();
	if(checkIsNull()){
		layer.load(2);
		//删除已制作的临时数据
		$.ajax({ 
			type: 'POST', 
			data: {}, 
			async:false,
			url: BASE_PATH + '/admin/customneeds/deleteMakePlanData.html',
	        success: function (data) { 
	        },
	        error: function (xhr) {
	        } 
	    });
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
			var backleavecity = $(this).find('[name=backleavecity]').val();
			if (backleavecity) {
				backleavecity = backleavecity.join(',');
			}
			var backbackcity = $(this).find('[name=backbackcity]').val();
			if (backbackcity) {
				backbackcity = backbackcity.join(',');
			}
			var unioncity = $(this).find('[name=unioncity]').val();
			if (unioncity) {
				unioncity = unioncity.join(',');
			}
			var foc = $(this).find('[name=foc]').val();
			//时间类型
			var weekSelect = $(this).find('[id=weekSelect]').val();
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
			if(weekSelect == 1){
				calenderdate = '';
			}else if(weekSelect == 2){
				weekday = '';
			}
			var param = {teamtype:teamtype,
						 travelname:travelname,
						 peoplecount:peoplecount,
						 dayscount:dayscount,
						 leaveairline:leaveairline,
						 backairline:backairline,
						 leavescity:leavescity,
						 backscity:backscity,
						 backleavecity:backleavecity,
						 backbackcity:backbackcity,
						 foc:foc,
						 unioncity:unioncity,
						 startdate:startdate,
						 enddate:enddate,
						 timetype:weekSelect,
						 calenderdate:calenderdate,
						 weekday:weekday};
			$.ajax({ 
				type: 'POST', 
				data: param, 
				url: BASE_PATH + '/admin/customneeds/airlineMakePlan.html',
	            success: function (data) {
	            	datatable1.ajax.reload();
	            	if(divlength-1 == i){
	            		layer.closeAll('loading');
	            		layer.alert("制作成功",{time: 2000, icon:1});
	            	}
	            },
	            error: function (xhr) {
	            	layer.alert("制作失败",{time: 2000, icon:1});
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
			layer.alert("请填写第"+(i+1)+"个旅行社",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var peoplecount = $(this).find('[name=peoplecount]').val();
		if(!peoplecount){
			layer.alert("请填写第"+(i+1)+"个人数",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var dayscount = $(this).find('[name=dayscount]').val();
		if(!dayscount){
			layer.alert("请填写第"+(i+1)+"个天数",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var leaveairline = $(this).find('[name=leaveairline]').val();
		if (leaveairline) {
			leaveairline = leaveairline.join(',');
		}else{
			layer.alert("请填写第"+(i+1)+"个去程航班",{time: 2000, icon:1});
			result = false;
			return false;
		}
		/*var backairline = $(this).find('[name=backairline]').val();
		if (backairline) {
			backairline = backairline.join(',');
		}else{
			layer.alert("请填写第"+(i+1)+"个回程航班",{time: 2000, icon:1});
			result = false;
			return false;
		}*/
		var leavescity = $(this).find('[name=leavescity]').val();
		if (leavescity) {
			leavescity = leavescity.join(',');
		}else{
			layer.alert("请填写第"+(i+1)+"个去程出发城市",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var backscity = $(this).find('[name=backscity]').val();
		if (backscity) {
			backscity = backscity.join(',');
		}else{
			layer.alert("请填写第"+(i+1)+"个去程抵达城市",{time: 2000, icon:1});
			result = false;
			return false;
		}
		/*var backleavecity = $(this).find('[name=backleavecity]').val();
		if (backleavecity) {
			backleavecity = backleavecity.join(',');
		}else{
			layer.alert("请填写第"+(i+1)+"个返程出发城市",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var backbackcity = $(this).find('[name=backbackcity]').val();
		if (backbackcity) {
			backbackcity = backbackcity.join(',');
		}else{
			layer.alert("请填写第"+(i+1)+"个返程抵达城市",{time: 2000, icon:1});
			result = false;
			return false;
		}*/
		var unioncity = $(this).find('[name=unioncity]').val();
		if (unioncity) {
			unioncity = unioncity.join(',');
		}else{
			layer.alert("请填写第"+(i+1)+"个联运城市",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var startdate = $(this).find('[name=startdate]').val();
		if(!startdate){
			layer.alert("请填写第"+(i+1)+"个起始日期",{time: 2000, icon:1});
			result = false;
			return false;
		}
		var enddate = $(this).find('[name=enddate]').val();
		if(!enddate){
			layer.alert("请填写第"+(i+1)+"个截止日期",{time: 2000, icon:1});
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
			layer.alert("请选择第"+(i+1)+"个每周或自由中的一种",{time: 2000, icon:1});
			result = false;
			return false;
		}
	});
	return result;
}
//保存计划
function savePlan(){
	var isplan = false;
	$.ajax({ 
		type: 'POST', 
		data: {}, 
		async:false,
		url: BASE_PATH + '/admin/customneeds/isHavePlanData.html',
        success: function (data) { 
        	if(data){
        		isplan = true;
        	}
        },
        error: function (xhr) {
        } 
    });
	if(isplan){
		layer.confirm('确定要保存计划吗?', {icon: 3, title:'提示'}, function(){
			$.ajax({ 
				type: 'POST', 
				data: {}, 
				url: BASE_PATH + '/admin/customneeds/savePlanData.html',
				success: function (data) { 
					layer.alert("保存成功",{time: 2000, icon:1});
					datatable1.ajax.reload();
					datatable2.ajax.reload();
				},
				error: function (xhr) {
					layer.alert("保存失败",{time: 2000, icon:1});
				} 
			});
		});
	}else{
		layer.alert("没有需要保存的计划",{time: 2000, icon:1});
	}
}
//提示是否保存已经制作的计划
window.onbeforeunload = function(event) {
	var isplan = false;
	$.ajax({ 
		type: 'POST', 
		data: {}, 
		async:false,
		url: BASE_PATH + '/admin/customneeds/isHavePlanData.html',
        success: function (data) { 
        	if(data){
        		isplan = true;
        	}
        },
        error: function (xhr) {
        } 
    });
	if(isplan){
		return "计划制作内容尚未保存，确定离开此页面吗？";
	}
}
//关闭页面删除临时计划制作数据
window.onunload = function(event){
	$.ajax({ 
		type: 'POST', 
		data: {}, 
		async:false,
		url: BASE_PATH + '/admin/customneeds/deleteMakePlanData.html',
        success: function (data) { 
        },
        error: function (xhr) {
        } 
    });
}
//格式化日期
function FormatDate (strTime) {
    var date = new Date(strTime);
    return date.getFullYear()+"-"+zeroize((date.getMonth()+1),2)+"-"+zeroize(date.getDate(),2);
}
//位数不足  补0
function zeroize(value, length) {
    if (!length) length = 2;
    value = String(value);
    for (var i = 0, zeros = ''; i < (length - value.length); i++) {
        zeros += '0';
    }
    return zeros + value;
}
//导出新航模板
function exportXinHangTemplate(){
	
}