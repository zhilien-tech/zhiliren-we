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

						<!--任务-->
						<div id="taskId" style="display: none" class="taskDiv">
							<div class="box-header with-border box-head">
								<i class="fa fa-folder-open-o"></i>
								<h4 class="box-title">任务</h4>
							</div>
							<div class="box-body box-sha">
								<!-- 显示任务信息 -->
								<ul id="taskListId" class="taskInfo">

								</ul>
							</div>
						</div>
						<!--end 任务-->

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
								<input type="checkbox" class="checkShow" checked="checked">
								显示提醒
							</p>
						</div>
					</div>
					<!--end 小日历-->

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
			<form id="checkboxform">
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
								value="task" /> <span>任务</span>
						</p>
						<p>
							<input id="maxCalenderId" name="checkboxname" type="checkbox"
								value="maxC" /> <span>大日历</span>
						</p>
						<p>
							<input id="minCalenderId" name="checkboxname" type="checkbox"
								value="minC" /> <span>小日历</span>
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
	<script
		src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${base}/public/dist/js/app.min.js"></script>
	<!--大日历 js-->
	<%-- <script src='${base}/public/plugins/fullcalendar/js/jquery-ui.css'></script> --%>
	<script src='${base}/public/plugins/fullcalendar/js/fullcalendar.min.js'></script>
	<!--小日历 js-->
	<script src="${base }/public/build/kalendae.standalone.js"
		type="text/javascript" charset="utf-8"></script>
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
			/*自定义事件*/
			customInterfaces();
			/*自定义界面*/
			checkBoxShow();
			/*小日历*/
			minCalendarInit();
		});
	</script>

	<!-- 自定义界面保存 -->
	<script type="text/javascript">
   		function checkboxSave(){
   			$.ajax({
                cache: true,
                type: "POST",
                url:'${base}/admin/operationsArea/setCheckBox.html',
                data:$('#checkboxform').serialize(),
                async: false,
                success: function(data) {
                	
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
	                	
	                	var cName = element.comname;
	                	var agent = element.username;
	                	var msgC = element.msgcontent;
	                	content += '<li><a href="javascript:;"><span>'+dStr+'</span><span>'+tStr+'</span>'+cName+'&nbsp;&nbsp;'+agent+'&nbsp;&nbsp;自定义事件：'+msgC+'</a></li>';
		            });
		           
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
			      center: 'title',
			      right: 'month'
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
				
			  //日历中添加红色圆点
			 // $('.back').append('<i class="dot"></i>');
			  //获取当前3个月事件
			  getTimeStr();
			  /* $('span[data-date="2016-12-06"]').append('<i class="dot"></i>');
			  $('span[data-date="2016-12-09"]').append('<i class="dot"></i>');
			  $('span[data-date="2016-12-10"]').append('<i class="dot"></i>'); */
			  //end 日历中添加红色圆点
			  
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
		            	$('span[data-date="'+element.gtime+'"]').append('<i class="dot"></i>');
	            		
	            		//小红点点击弹框事件
	            		$(document).on('click','span[data-date="'+ element.gtime +'"]',function(){//如果有红色圆点，点击 显示小div信息
		      			    layer.tips(
		      			    	 element.msgcontent, 
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
