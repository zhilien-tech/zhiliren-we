//确认付款
function confirmPayClick(){
	$.ajax({
		type : 'POST',
		data : $("#confirmInlandPayForm").serialize(),
		async: false,
		url: BASE_PATH + '/admin/receivePay/saveInlandPay.html',
		success : function(data) {
			layer.msg("付款成功", "", 2000);
		}
	});
}