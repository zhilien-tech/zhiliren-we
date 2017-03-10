//只能输入数字
$(document).on("input",".mustNumber",function(){
	$(this).val($(this).val().replace(/[^\d]/g,''));
});
//只能输入数字（带小数点）
$(document).on("input",".mustNumberPoint",function(){
	$(this).val($(this).val().replace(/[^.\d]/g,''));
});
//只能输入时间 （例如12:00）
$(document).on("input",".mustTimes",function(){
	$(this).val($(this).val().replace(/[^:\d]/g,''));
	if($(this).val().length == 2){
		$(this).val($(this).val()+':');
	}else if($(this).val().length > 5){
		$(this).val($(this).val().substr(0,5));
	}
});


//加载日志
function loadOrderLog(orderid){
	$.ajax({ 
		type: 'POST', 
		data: {orderid:orderid}, 
		url: BASE_PATH + '/admin/inland/loadOrderLog.html',
        success: function (data) { 
        	var loghtml = '<ul>';
        	$.each(data.logs, function(name, value) {
        		loghtml += '<li style="list-style:none;">'+value.optime+'　'+value.content+'</li>';
      		});
        	loghtml += '</ul>';
        	$('#orderlog').html(loghtml);
        },
        error: function (xhr) {
        	layer.msg("保存失败","",3000);
        } 
    });
}