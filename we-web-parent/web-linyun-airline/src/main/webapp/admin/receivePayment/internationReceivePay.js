/*清除 内陆跨海 收款的   检索项*/
$('#internationRecClearBtn').click(function(){
	clearSearchTxt("internationRecSelect", "internationRecBeginDate", "internationRecEndDate", "internationRecInput");
});

/*清除 内陆跨海 付款的   检索项*/
$('#internationPayClearBtn').click(function(){
	clearSearchTxt("internationPaySelect", "internationPayBeginDate", "internationPayEndDate", "internationPayInput");
});