//把dataUrl类型的数据转为blob
function dataURLtoBlob(dataurl) { 
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}

$(document).on('change','.sc', function(){
	var thisDiv = $(this).parents('.cloneTR'); 
	var file = this.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var dataUrl = e.target.result;
		var blob = dataURLtoBlob(dataUrl) ;
    	var formData = new FormData();
    	formData.append("image", blob,file.name);
    	$.ajax({ 
            type: "POST",//提交类型  
            dataType: "json",//返回结果格式  
            url: BASE_PATH + '/admin/inland/uploadInvoice.html',//请求地址  
            async: true  ,
            processData: false, //当FormData在jquery中使用的时候需要设置此项
            contentType: false ,//如果不加，后台会报表单未封装的错误(enctype='multipart/form-data' )
          	//请求数据  
            data:formData ,
            success: function (obj) {//请求成功后的函数  
            	if('200' === obj.status){
            		thisDiv.find('[name=invoiceurl]').val(obj.data);
            		thisDiv.find('[name=fileName]').html(file.name);
            		thisDiv.find('[name=imagename]').val(file.name);
            	}
            },  
            error: function (obj) {
            }
    	});  // end of ajaxSubmit
	};
	reader.readAsDataURL(file);
});

$(document).on('input','#invoicebalance', function(){
	var banlanceyue = parseFloat($('#sumjine').html());
	$('.cloneTR').each(function(i){
		var invoicebalance = $(this).find('[name=invoicebalance]').val();
		if(invoicebalance){
			banlanceyue = banlanceyue - parseFloat(invoicebalance);
		}
	});
	if(!isNaN(banlanceyue)){
		$('#balance').html(banlanceyue.toFixed(2));
	}
});

