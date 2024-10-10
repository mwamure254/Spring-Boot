$('document').ready(function() {
	
	$('.table #editButton').on('click',function(event){		
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(contact){
			$('#txtEmailEdit').val(contact.email);
			$('#txtFirstnameEdit').val(contact.firstname);
			$('#txtIdEdit').val(contact.id);
			$('#txtLastnameEdit').val(contact.lastname);	
			$('#txtMobileEdit').val(contact.mobile);
			$('#txtPhoneEdit').val(contact.phone);			
			$('#txtRemarksEdit').val(contact.remarks);
		});			
		$('#editModal').modal();		
	});
	
	$('.table #detailsButton').on('click',function(event) {
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(contact){
			$('#txtEmail').val(contact.email);
			$('#txtFirstname').val(contact.firstname);
			$('#txtId').val(contact.id);
			$('#txtLastname').val(contact.lastname);	
			$('#txtMobile').val(contact.mobile);
			$('#txtPhone').val(contact.phone);			
			$('#txtRemarks').val(contact.remarks);
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