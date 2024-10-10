$('document').ready(function() {
	
	$('.table #editButton').on('click',function(event){		
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(client){
			$('#txtAddressEdit').val(client.address);
			$('#txtCityEdit').val(client.city);
			$('#ddlCountryEdit').val(client.countryid);
			$('#txtDetailsEdit').val(client.details);
			$('#txtEmailEdit').val(client.email);
			$('#txtIdEdit').val(client.id);
			$('#txtMobileEdit').val(client.mobile);
			$('#txtNameEdit1').val(client.firstname);	
			$('#txtNameEdit2').val(client.lastname);	
			$('#txtPhoneEdit').val(client.phone);			
			$('#ddlStateEdit').val(client.stateid);	
			$('#txtWebsiteEdit').val(client.website);
		});			
		$('#editModal').modal();		
	});
	
	$('.table #detailsButton').on('click',function(event){		
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(client){
			$('#txtAddress').val(client.address);
			$('#txtCity').val(client.city);
			$('#ddlCountry').val(client.countryid);
			$('#txtDetails').val(client.details);
			$('#txtEmail').val(client.email);
			$('#txtId').val(client.id);
			$('#txtMobile').val(client.mobile);
			$('#txtName1').val(client.firstname);	
			$('#txtName2').val(client.lastname);	
			$('#txtPhone').val(client.phone);			
			$('#ddlState').val(client.stateid);	
			$('#txtWebsite').val(client.website);
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