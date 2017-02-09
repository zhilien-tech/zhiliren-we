//客户需求的 + 按钮
$('.addIcon').click(function(){
	var divTest = $(this).parent().parent(); 
	var newDiv = divTest.clone(true);
	divTest.after(newDiv);
	var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
	newDiv.find("p").html(No); 
	newDiv.find('.addIcon').parent().remove();
	newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removIcon"></i></td>');
});
//客户需求的 - 按钮
$(document).on("click",".removIcon",function(){
	$(this).parent().parent().remove();
});

//客户需求的 +需求 按钮
$('.addDemand').click(function(){
	var divTest = $(this).parent(); 
	var newDiv = divTest.clone(true);
	//divTest.after(newDiv);
	$('#infofooter').append(newDiv);
	var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
	newDiv.find("p").html(No); 
	newDiv.find('.addDemand').remove();
	newDiv.prepend('<a href="javascript:;" class="btn btn-primary btn-sm removeDemand"><b>-</b>&nbsp;&nbsp;需求</a>');
	var divId=document.getElementById('infofooter').getElementsByTagName('div');
	newDiv.find('.titleNum').text(divId.length);
});
//客户需求的 -需求 按钮
$(document).on("click",".removeDemand",function(){
	$(this).parent().remove();
});