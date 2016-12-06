$(".chzn-select").chosen(); 

function loadTranvel(){
	$.ajax({ 
		type: 'POST', 
		data: {id:id}, 
		url: BASE_PATH + '/admin/customneeds/closeCustomNeeds.html',
        success: function (data) { 
        },
        error: function (xhr) {
        } 
    });
}