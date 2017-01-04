<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/common/tld.jsp"%>
<%@include file="/WEB-INF/public/header.jsp"%>
<%@include file="/WEB-INF/public/aside.jsp"%>

<!DOCTYPE html>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>桌面</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.css">
<!-- 图标 -->
<link rel="stylesheet"
	href="${base}/public/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/ionicons/css/ionicons.min.css">

<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
<link rel="stylesheet"
	href="${base}/public/dist/css/skins/_all-skins.min.css">
<!-- 大日历 -->
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/fullcalendar.css">
<link rel="stylesheet" type="text/css"
	href="${base}/public/plugins/fullcalendar/css/fancybox.css">
<!--小日历 css-->
<link rel="stylesheet" href="${base}/public/build/kalendae.css"
	type="text/css" charset="utf-8">

<link rel="stylesheet" type="text/css"
	href="${base}/public/dist/css/desktop.css">
</head>
<body class="hold-transition skin-blue sidebar-mini bodyOne">
	<div class="wrapper">

		<!--right Content-->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">
				<div class="row">

					<div class="col-md-9">
						<!--任务and大日历-->
						<a href="javascript:;" class="customInterface">自定义界面</a>

						 <div class="nav-tabs-custom" id="taskId" style="display: none">
						 	<ul class="nav nav-tabs">
			                  <li class="active"><a href="#tab_1" data-toggle="tab">询单(6)</a></li>
			                  <li><a href="#tab_2" data-toggle="tab">订单(10)</a></li>
			                  <li><a href="#tab_3" data-toggle="tab">账期(2)</a></li>
			                  <li><a href="#tab_4" data-toggle="tab">我的提醒(<span id="remindMsg"></span>)</a></li>
			                </ul>
			                <div class="tab-content">
				                  <div class="tab-pane active" id="tab_1">
				                    <ul class="taskInfo">
				                      <li><a href=""><span>今天</span><span>07：23</span>聚美优品张三向你发送一个预售订单</a></li>
				                      <li><a href=""><span>昨天</span><span>09：03</span>爱我行&nbsp;&nbsp;&nbsp;王行&nbsp;&nbsp;&nbsp;0494573团需要支付一订</a></li>
				                      <li><a href=""><span>12-22</span><span>07：23</span>聚美优品孙先哲向你发送一个预售订单</a></li>
				                    </ul>
				                  </div>
				                  <div class="tab-pane" id="tab_2">
				                    <ul class="taskInfo">
				                      <li><a href=""><span>今天</span><span>07：23</span>聚美优品孙先哲向你发送一个预售订单</a></li>
				                      <li><a href=""><span>昨天</span><span>07：23</span>爱我行&nbsp;&nbsp;&nbsp;王行&nbsp;&nbsp;&nbsp;0494573团需要支付一订</a></li>
				                      <li><a href=""><span>11-12</span><span>07：23</span>聚美优品孙先哲向你发送一个预售订单</a></li>
				                    </ul>
				                  </div>
				                  <div class="tab-pane" id="tab_3">
				                    <ul class="taskInfo">
				                      <li><a href=""><span>今天</span><span>07：00</span>聚美优品孙先哲向你发送一个预售订单</a></li>
				                      <li><a href=""><span>昨天</span><span>09：23</span>爱我行&nbsp;&nbsp;&nbsp;王行&nbsp;&nbsp;&nbsp;0494573团需要支付一订</a></li>
				                    </ul>
				                  </div>
				                  <div class="tab-pane" id="tab_4">
				                    <ul id="taskListId" class="taskInfo">
				                    </ul>
				                  </div>
				            </div>
			                
						 </div>

						<div id="maxCId" style="display: none"
							class="box box-primary maxCalender">
							<!--大日历-->
							<div class="box-body no-padding">
								<!-- 大日历具体展示 -->
								<div id='calendar'></div>
							</div>
						</div>
						<!--end 大日历-->
					</div>
					<!--end 任务and大日历-->

					<div id="minCId" style="display: none" class="col-md-3 minCelender">
						<!--小日历-->
						<div class="box box-primary" id="box-min">
							<p>
								<input id="checkShow" type="checkbox" class="checkShow" checked="checked">
								显示提醒
							</p>
						</div>
					</div>
					<!--end 小日历-->
					<input id="minCalId" type="hidden" >

				</div>
			</section>
			<!-- /.content -->
		</div>
		<!--end right Content-->

		<!--footer-->
		<footer class="main-footer">
			<div class="pull-right hidden-xs"></div>
			<strong>版权 &copy; 2016 <a href="#">聚优国际旅行社（北京）有限公司</a>.
			</strong> 保留所有权利.
		</footer>
		<!--end footer-->

		<!--自定义界面 弹框-->
		<div class="layer-content none" id="layer-diy">
			<form id="checkboxform" method="post">
				<div class="layer-header">
					<button type="button" class="btn btn-primary right btn-sm"
						onclick="closewindow();">取消</button>
					<button type="submit" id="saveCustom"
						class="btn btn-primary right btn-sm" onclick="checkboxSave()">保存</button>
					<h4>自定义界面</h4>
				</div>
				<div class="modal-body">
					<div class="layer-check">
						<p>
							<input id="taskBoxId" name="checkboxname" type="checkbox"
								value="task" class="checkNum" onchange="checkBoxChange(this)" /> <span>任务</span>
						</p>
						<p>
							<input id="maxCalenderId" name="checkboxname" type="checkbox"
								value="maxC" class="checkNum" onchange="checkBoxChange(this)"/> <span>大日历</span>
						</p>
						<p>
							<input id="minCalenderId" name="checkboxname" type="checkbox"
								value="minC" class="checkNum" onchange="checkBoxChange(this)"/> <span>小日历</span>
						</p>
					</div>
				</div>
			</form>
		</div>
		<!--end 自定义界面 弹框-->
	</div>

	<!-- jQuery 2.2.3 -->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<!-- Slimscroll -->
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<!--大日历 js-->
	<%-- <script src='${base}/public/plugins/fullcalendar/js/jquery-ui.css'></script> --%>
	<script src='${base}/public/plugins/fullcalendar/js/fullcalendar.min.js'></script>
	<!--小日历 js-->
	<script src="${base }/public/build/kalendae.standalone.js" type="text/javascript" charset="utf-8"></script>
	<script src="${base}/common/js/layer/layer.js"></script>
	<script type="text/javascript">
		new Kalendae({//小日历 创建
	        attachTo:document.getElementById("box-min"),
	        months:4,
	        mode:'single',
	        dayAttributeFormat:"YYYY-MM-DD",
	        subscribe: {
	             'date-clicked': function (date) {//点击时，取到当前当天的时间
	                //console.log(date, this.getSelected());
	                return false;
	              }
	        },
	        //dateClassMap:classMap,
	        selected:[Kalendae.moment().subtract({M:1}), Kalendae.moment().add({M:1})]
		});
		//end 小日历 js
	</script>
	<!-- end 小日历js -->
	<script type="text/javascript">
		$(function() {
			/* 大日历 */
			calendarInit();
			/* 任务提醒 */
			taskEventList();
			/*自定义界面选择*/
			customInterfaces();
			/*模块展示页面*/
			checkBoxShow();
			/*小日历*/
			minCalendarInit();
			  
		});
	</script>

	
	<!-- 自定义界面保存 -->
	<script type="text/javascript">
		function checkBoxChange(obj){
			if($(".checkNum:checked").size()==0){
				obj.checked = true;
   			}
		}
	
   		function checkboxSave(){
   			$.ajax({
                type: "POST",
                url:'${base}/admin/operationsArea/setCheckBox.html',
                data:$('#checkboxform').serialize(),
                async: false,
                success: function(data) {
                	window.location='${base}/admin/operationsArea/desktop.html';
                },
				error: function(request) {
                    
                }
            });
   		}
	  </script>
	<!-- 自定义界面展示 -->
	<script type="text/javascript">
	  	function checkBoxShow(){
	  		var taskShow = ${obj.checkBox.taskShow};
	  		var maxCShow = ${obj.checkBox.maxCShow};
	  		var minCShow = ${obj.checkBox.minCShow};
	  		if(taskShow){
	  			$("#taskId").css('display','block');
	  			$("#taskBoxId").attr('checked','checked');
	  			taskEventList();
	  		}
	  		if(maxCShow){
	  			$("#maxCId").css('display','block');
	  			$("#maxCalenderId").attr('checked','checked');
	  			$('#calendar').empty();
	  			calendarInit();
	  		}
	  		if(minCShow){
	  			$("#minCId").css('display','block');
	  			$("#minCalenderId").attr('checked','checked');
	  		}
	  	}
	  </script>

	<!-- 任务事件提醒 -->
	<script type="text/javascript">
		function taskEventList() {
			//获取当前日期
			var d = new Date();
			if(d.getDate() < 10){
				var dateStr = d.getMonth()+1 +"-0"+ d.getDate();
				var yesterdayStr = d.getMonth()+1 +"-0"+ (d.getDate()-1);
				dateStr = "0" + dateStr;
				yesterdayStr = "0" + yesterdayStr;
			}else{
				var dateStr = d.getMonth()+1 +"-"+ d.getDate();
				var yesterdayStr = d.getMonth()+1 +"-"+ (d.getDate()-1);
			}
			//获取当前时间
			var timeStr = d.getHours() +":"+ d.getMinutes();
			
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : '${base}/admin/operationsArea/getTaskEvents.html',
				success : function(data){
					var content = "";
					var num = "";
					$.each(eval(data),function(index, element){
	                	var datetimeStr = element.generatetime;
	                	var dStr = datetimeStr.substr(5, 5);
	                	var tStr = datetimeStr.substr(11, 5);
	                	if(dStr == dateStr){
	                		dStr="今天";
	                	}
	                	if(dStr == yesterdayStr){
	                		dStr="昨天";
	                	}
	                	num = element.num;
	                	var cName = element.comname;
	                	var agent = element.username;
	                	var msgC = element.msgcontent;
	                	content += '<li><a href="javascript:;"><span>'+dStr+'</span><span>'+tStr+'</span>'+cName+'&nbsp;&nbsp;'+agent+'&nbsp;&nbsp;自定义事件：'+msgC+'</a></li>';
		            });
		            $("#remindMsg").html(num);
					$("#taskListId").html(content);
				}
			});
		}
	</script>

	<!-- 大日历 -->
	<script type="text/javascript">
	function calendarInit(){
		  $('#calendar').fullCalendar({
			    header: {
			      left: 'prev, next, today',
			      center: ' ',
			      right: 'title'
			    },
			    events: function(start, end,callback) {
			    	
			    	var fStart = $.fullCalendar.formatDate(start,"yyyy-MM-dd hh:mm:ss"); 
			    	var fEnd = $.fullCalendar.formatDate(end,"yyyy-MM-dd hh:mm:ss"); 
			    	
			        $.ajax({
			            url: '/admin/operationsArea/getCustomEvents.html',
			            dataType: 'json',
			            data: {
			                start: fStart,
			                end: fEnd
			            },
			            success: function(data) {
			                var events = [];
			                $.each(eval(data),function(index, element){
			                	events.push(element) ;
			                });
			                callback(events);
			            }
			        });
			    },
			    //點擊事件
			    dayClick: function(date, allDay, jsEvent, view) {
			     	  /* 自定义事件 弹框日期 */
			      	  var selDate =$.fullCalendar.formatDate(date,'yyyy-MM-dd');
			      	  
			          layer.open({
			              type: 2,
			              title:false,
			              shadeClose:false,
			              shade:0.6,
			              maxmin: false, 
			              area: ['400px', '210px'],
			              closeBtn: false,
			              content: '${base}/admin/operationsArea/customEvent.html?selDate='+selDate,
			              end: function () {
			            	  /* $.fancybox.close();  */
			                  $('#calendar').fullCalendar('refetchEvents');
			              }
			          }); 
			      },
			      eventClick: function(calEvent, jsEvent, view) {
			         /* 自定义事件 弹框日期 */
			      	  var msgId = calEvent.id;
			          layer.open({
			              type: 2,
			              title:false,
			              shadeClose:false,
			              shade:0.6,
			              maxmin: false, 
			              area: ['400px', '275px'],
			              closeBtn: false,
			              content: '${base}/admin/operationsArea/updateCustomEvent.html?msgId='+ msgId
			          }); 
			          
			          $('#calendar').fullCalendar('updateEvent', events);
			      },
			      eventMouseover:function( event, jsEvent, view ) {
			    	  $('.fc-event-title').css('display','block');
					  $('.fc-event-title').attr('title',event.title);
			      }
			  });
		  
	  }
	
	
	</script>
	<script type="text/javascript">
		function reload(){
			$('#calendar').fullCalendar( 'refetchEvents' );
		}
	</script>

	<!-- 自定义界面 -->
	<script type="text/javascript">
		function customInterfaces(){
			 /*-----自定义界面 js-----*/
		    $('.customInterface').on('click',function(){
		    	$.ajax({
	                type: "POST",
	                url:'${base}/admin/operationsArea/getCheckBox.html',
	                success: function(data) {
	                	var taskShow = ${obj.checkBox.taskShow};
	        	  		var maxCShow = ${obj.checkBox.maxCShow};
	        	  		var minCShow = ${obj.checkBox.minCShow};
	        	  		if(taskShow){
	        	  			$("#taskBoxId").prop('checked',true);
	        	  		}else{
	        	  			$("#taskBoxId").prop('checked',false);
	        	  		}
	        	  		if(maxCShow){
	        	  			$("#maxCalenderId").prop('checked',true);
	        	  		}else{
	        	  			$("#maxCalenderId").prop('checked',false);
	        	  		}
	        	  		if(minCShow){
	        	  			$("#minCalenderId").prop('checked',true);
	        	  		}else{
	        	  			$("#minCalenderId").prop('checked',false);
	        	  		}
	                },
					error: function(request) {
	                    
	                }
	            });
		        layer.open({
		            type: 1, //Page层类型
		            area: ['350px', '125px'],
		            title: false,
		            shade: 0.6 ,//遮罩透明度
		            maxmin: false, //允许全屏最小化
		            anim: 0, //0-6的动画形式，-1不开启
		            skin: 'layui-layer-demo', //样式类名
		            closeBtn: 0, //不显示关闭按钮
		            shadeClose: true, //开启遮罩关闭
		            content: $('#layer-diy')
		         }); 
		     });

		}
		
		 /* 关闭自定义界面 */
	    function closewindow(){
	    	layer.closeAll();
	    }
	</script>
	<!-- end 自定义界面 -->

	<!-- 小日历 -->
	<script type="text/javascript">
		function minCalendarInit(){
			$('#box-min .kalendae').attr('id','minCalen');//给小日历添加ID
		    /*---------------------------------小日历 节假日------------------------------------*/
		    var CalendarData=new Array(20);
		    var madd=new Array(12);
		    var TheDate=new Date();
		    init();
		    showHoliday();//页面加载时，显示出阳历的节假日
		    function init(){
		        CalendarData[0]=0x41A95;
		        CalendarData[1]=0xD4A;
		        CalendarData[2]=0xDA5;
		        CalendarData[3]=0x20B55;
		        CalendarData[4]=0x56A;
		        CalendarData[5]=0x7155B;
		        CalendarData[6]=0x25D;
		        CalendarData[7]=0x92D;
		        CalendarData[8]=0x5192B;
		        CalendarData[9]=0xA95;
		        CalendarData[10]=0xB4A;
		        CalendarData[11]=0x416AA;
		        CalendarData[12]=0xAD5;
		        CalendarData[13]=0x90AB5;
		        CalendarData[14]=0x4BA;
		        CalendarData[15]=0xA5B;
		        CalendarData[16]=0x60A57;
		        CalendarData[17]=0x52B;
		        CalendarData[18]=0xA93;
		        CalendarData[19]=0x40E95;
		        madd[0]=0;
		        madd[1]=31;
		        madd[2]=59;
		        madd[3]=90;
		        madd[4]=120;
		        madd[5]=151;
		        madd[6]=181;
		        madd[7]=212;
		        madd[8]=243;
		        madd[9]=273;
		        madd[10]=304;
		        madd[11]=334;
		    }
		    function GetBit(m,n){
		        return (m>>n)&1;
		    }
		    function showHoliday(){
		      var sFtv = new Array(//阳历节日
		                      "01-01 元旦",
		                      "02-14 情人",
		                      "03-08 妇女",
		                      "03-12 植树",
		                      "04-01 愚人",
		                      "05-01 劳动",
		                      "06-01 儿童",
		                      "09-10 教师",
		                      "10-01 国庆",
		                      "12-25 圣诞");
		      var lFtv = new Array(//农历节日
		                      "01-01 春节",
		                      "01-15 元宵",
		                      "05-05 端午",
		                      "07-07 七夕",
		                      "07-15 中元",
		                      "08-15 中秋",
		                      "12-08 腊八",
		                      "12-24 小年");
		      //var solarTerm = new Array("立春","春分","清明","立夏","立秋","立冬","冬至");
		      for(var j=0;j<sFtv.length;j++){//获取阳历的节日--------------------------------------------------------------------
		          var JJRMonth=sFtv[j].substr(0,2);//获取阳历 节假日 月
		          var JJRDay=sFtv[j].substr(3,2);//获取阳历 节假日 日
		          var JJRName=sFtv[j].substr(5);//获取阳历 节假日 name
		          var SpanNumber=document.getElementById('minCalen').getElementsByTagName('span');//获取页面里放有多少天数
		          
		          for(var i=0;i<SpanNumber.length;i++){
		              
		              var spanVal=SpanNumber[i].getAttribute("data-date");//获取span标签的data-date属性值
		              if (spanVal!=null) {//当期日不为null时
		                  //阳历 节假日-----------------------------------------------//
		                  SpanNumber[i].style.color="#000";
		                  var thisYear=spanVal.substr(0,4);//获取页面上的年 时间
		                  var thisMonth=spanVal.substr(5,2);//获取页面上的月 时间
		                  var thisDay=spanVal.substr(8,2);//获取页面上的日 时间
		                  var cYear;
		                  var cMonth;
		                  var cDay;
		                  if(JJRMonth==thisMonth){
		                      if(JJRDay==thisDay){
		                          SpanNumber[i].innerHTML=JJRName;
		                          SpanNumber[i].style.color="#e04174";
		                      }
		                  }
		                  //end 阳历 节假日-------------------------------------------//
		                  //阴历 节假日-----------------------------------------------//
		                  var   isEnd=false;
		                  thisMonth=thisMonth-1;
		                  if   (thisYear<1900){thisYear+=1900;}
		                  total=(thisYear-2001)*365
		                         +Math.floor((thisYear-2001)/4)//页面 年
		                         +madd[thisMonth]//页面 月
		                         +parseInt(thisDay)//页面 日
		                         -23;
		                  if(TheDate.getFullYear()%4==0&&thisMonth>1){total++;}
		                  for(m=0;;m++){
		                      k=(CalendarData[m]<0xfff)?11:12;
		                      for(n=k;n>=0;n--){
		                          if(total<=29+GetBit(CalendarData[m],n)){
		                              isEnd=true;
		                              break;
		                          }
		                          total=total-29-GetBit(CalendarData[m],n);
		                      }
		                      if(isEnd)break;
		                  }
		                  cYear=2001+m;//获取页面的 农历 年
		                  cMonth=k-n+1;//获取页面的 农历 月
		                  cDay=total;//获取页面的 农历 日
		                  if(k==12){
		                      if(cMonth==Math.floor(CalendarData[m]/0x10000)+1){cMonth=1-cMonth;} 
		                      if(cMonth>Math.floor(CalendarData[m]/0x10000)+1)
		                          cMonth--;
		                  }
		                  for(var q=0;q<lFtv.length;q++){
		                      var NjjrMonth=lFtv[q].substr(0,2);//获取农历的节假日 月
		                      var NjjrDay=lFtv[q].substr(3,2);//获取农历的节假日 日
		                      var NjjrName=lFtv[q].substr(5);//获取农历的节假日 name
		                      if(cMonth==NjjrMonth){
		                          if(cDay==NjjrDay){
		                               SpanNumber[i].innerHTML=NjjrName;
		                               SpanNumber[i].style.color="#e04174";
		                          }
		                      }
		                  }
		                  //end 阴历 节假日-------------------------------------------//
		              }
		          } 
		      }
		    }
		    
		    $(document).on('click','.k-btn-next-month',function(){//点击小日历 向右箭头时，加载阳历节假日
		          showHoliday();
		    });

		    $(document).on('click','.k-btn-previous-month',function(){//点击小日历 向左箭头时，加载阳历节假日
		          showHoliday();
		    });
		    /*---------------------------------end 小日历 节假日------------------------------------*/
			  //获取当前3个月事件
			  getTimeStr();
			  
			  $('.checkShow').click(function(){//显示提醒 显示/隐藏
			      if($(this).prop('checked')){
			            $('.dot').css('display','block');
			      }else{
			            $('.dot').css('display','none');
			      }
			  });//end 显示提醒 显示/隐藏
			  
		}
	</script>
	<script type="text/javascript">
		 $('.checkShow').click(function(){//显示提醒 显示/隐藏
			 
		      if($(this).prop('checked')){
		          $('.dot').css('display','block');
		          $("#checkShow").prop('checked',true);
		      }else{
		          $('.dot').css('display','none');
		          $("#checkShow").prop('checked',false);
		      }
		  });//end 显示提醒 显示/隐藏
	</script>
	
	
	<script type="text/javascript">
		function getTimeStr(){
		  /* 获取当前月  格式化为：2016-12的形式 */
		  var dateValue=document.getElementById('box-min').getElementsByClassName('k-caption');//获取 小日历 的/年/月
    	  var d = dateValue[1].innerHTML;
    	  var length = d.length;
    	  var y = d.substring(0,4);
    	  var m = "";
    	  var timeStr = "";
    	  if(length>=11){
    		  m = d.substring(8,10);
    		  timeStr = y +"-"+ m;
    	  }else{
    		  m = d.substring(8,9);
    		  timeStr = y +"-0"+ m;
    	  }
	      $.ajax({
	            url: '/admin/operationsArea/getMinCalList.html',
	            dataType: 'json',
	            type: 'POST',
	            data: {
	            	timeStr: timeStr
	            },
	            success: function(data) {
	            	$.each(eval(data), function (index, element) {
            			//显示小红点
            			if($('span[data-date="'+element.gtime+'"]').children().find('i')){
            				$('span[data-date="'+element.gtime+'"]').find('i').remove();
            			}
		            	$('span[data-date="'+element.gtime+'"]').append('<i class="dot"></i>');
	            		
	            		//小红点点击弹框事件
	            		$(document).on('click','span[data-date="'+ element.gtime +'"]',function(){//如果有红色圆点，点击 显示小div信息
	            			$.ajax({
	        		            url: '/admin/operationsArea/getMinCal.html',
	        		            dataType: 'json',
	        		            type: 'POST',
	        		            async:false,
	        		            data: {
	        		            	gtime: element.gtime
	        		            },
	        		            success: function(data) {
	        		            	$.each(eval(data), function (index, element) {
	        		            		$("#minCalId").val("");
	        		            		$("#minCalId").val(element.msgcontent);
	        		                }); 
	        		            }
	        		        });
	            		
	            			layer.tips(
	            				 $("#minCalId").val(), 
	      			    		 this,
	      			    		 {
	      					        tips: [3, 'rgb(90, 90, 90)'],
	      					        time: 3000
	      					     }
		      			    );
	            		
	      			  	});//end 如果有红色圆点，点击 显示小div信息 
		            	
	      			  	
	                }); 
	            }
	       });
		}
		
	
	</script>

	<script type="text/javascript">
		//小日历上一个按钮
		$(".k-btn-previous-month").click(function(){
			getTimeStr();
		});
		//小日历下一个按钮
		$(".k-btn-next-month").click(function(){
			getTimeStr(); 
		});
	</script>
	
</body>
</html>
