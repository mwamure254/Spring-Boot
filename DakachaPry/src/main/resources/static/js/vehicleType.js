$('document').ready(function() {
	
	$('.table #editButton').on('click',function(event){		
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(vehicleType){
			$('#idEdit').val(vehicleType.id);
			$('#descriptionEdit').val(vehicleType.description);
			$('#detailsEdit').val(vehicleType.details);
		});			
		$('#editModal').modal();		
	});
	
	$('.table #detailsButton').on('click',function(event) {
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(vehicleType){
			$('#idDetails').val(vehicleType.id);
			$('#descriptionDetails').val(vehicleType.description);
			$('#detailsDetails').val(vehicleType.details);
			$('#lastModifiedByDetails').val(vehicleType.lastModifiedBy);
			$('#lastModifiedDateDetails').val(vehicleType.lastModifiedDate.substr(0,19).replace("T", " "));
		});			
		$('#detailsModal').modal();		
	});	
	
	$('.table #deleteButton').on('click',function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteModal #delRef').attr('href', href);
		$('#deleteModal').modal();		
	});	
});