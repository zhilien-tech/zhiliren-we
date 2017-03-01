//保存订单
function saveOrderInfo(){
	var customdata = {};
	var customerId = $('#linkManId').val();
	customdata.customerId = customerId;
	var generateOrder = $('#generateOrder').val();
	customdata.generateOrder = $("#generateOrder").is(':checked');
	var orderType = $('#orderType').val();
	customdata.orderType = orderType;
	var row = [];
	$('.DemandDiv').each(function(i){
		var row1 = {};
		var leavecity = $(this).find('[name=cOutcity]').val();
		//出发城市
		if (leavecity) {
			leavecity = leavecity.join(',');
		}
		row1.leavecity = leavecity;
		//抵达城市
		var arrivecity = $(this).find('[name=cArrivalcity]').val();
		if (arrivecity) {
			arrivecity = arrivecity.join(',');
		}
		row1.arrivecity = arrivecity;
		row1.leavedate = $(this).find('[name=cOutDate]').val();
		row1.peoplecount = $(this).find('[name=cPersonAmount]').val();
		row1.tickettype = $(this).find('[name=tickettype]').val();
		var airrows = [];
		$(this).find('[name=airLineInfo]').each(function(i){
			var airrow = {};
			var aircom = $(this).find('[name=cAirlineCompany]').val();
			if (aircom) {
				aircom = aircom.join(',');
			}
			airrow.aircom = aircom;
			var ailinenum = $(this).find('[name=cAirlineNum]').val();
			if (ailinenum) {
				ailinenum = ailinenum.join(',');
			}
			airrow.ailinenum = ailinenum;
			airrow.leavetime = $(this).find('[name=cAirOutDate]').val();
			airrow.arrivetime = $(this).find('[name=cAirArrivalDate]').val();
			airrow.formprice = $(this).find('[name=cAirCost]').val();
			airrow.price = $(this).find('[name=cAirPretium]').val();
			airrows.push(airrow);
		});
		row1.airinfo = airrows;
		row.push(row1);
	});
	customdata.customdata=row;
	console.log(JSON.stringify(customdata));
	//alert(JSON.stringify(customdata));
	$.ajax({
		dataType : 'json',
		type: 'POST', 
		delay : 250,
		data: {
			"data":JSON.stringify(customdata)
		}, 
		url : BASE_PATH  + "/admin/search/saveCustomerNeeds.html",
		success: function (data) { 
			layer.msg("添加成功","",3000);
			
		},
		error: function (xhr) {
			layer.msg("添加失败","",3000);
		} 
	});
}