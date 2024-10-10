$('document').ready(function() {
	
	$('.table #editButton').on('click',function(event){		
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(employee){
			$('#txtAddressEdit').val(employee.address);
			$('#txtCityEdit').val(employee.city);
			$('#ddlNationalityEdit').val(employee.countryid);	
					
			var dob = employee.dateOfBirth.substr(0,10);
			$('#txtDateOfBirthEdit').val(dob);
			$('#txtEmailEdit').val(employee.email);
			$('#txtFirstnameEdit').val(employee.firstname);
			$('#txtIdEdit').val(employee.id);
			$('#txtInitialsEdit').val(employee.initials);
			$('#txtLastnameEdit').val(employee.lastname);
			$('#txtUsernameEdit').val(employee.username);
			$('#txtMobileEdit').val(employee.mobile);
			$('#txtOthernameEdit').val(employee.othername);		
			$('#txtPhoneEdit').val(employee.phone);			
			$('#txtSSNEdit').val(employee.socialSecurityNumber);
			$('#ddlGenderEdit').val(employee.gender);			
			$('#ddlStateEdit').val(employee.stateid);	
			$('#ddlTitleEdit').val(employee.title);		
			$('#ddlMaritalStatusEdit').val(employee.maritalStatus);	
			$('#ddlEmployeeTypeEdit').val(employee.employeetypeid);	
						
			var hireDate = employee.hireDate.substr(0,10);
			$('#txtHireDateEdit').val(hireDate);
			$('#ddlJobTitleEdit').val(employee.jobtitleid);			
		});			
		$('#editModal').modal();		
	});
	
	$('.table #detailsButton').on('click',function(event) {
		event.preventDefault();		
		var href= $(this).attr('href');		
		$.get(href, function(employee){
			$('#idDetails').val(employee.id);
			$('#descriptionDetails').val(employee.description);
			$('#detailsDetails').val(employee.details);
			$('#lastModifiedByDetails').val(employee.lastModifiedBy);
			$('#lastModifiedDateDetails').val(employee.lastModifiedDate.substr(0,19).replace("T", " "));
		});			
		$('#detailsModal').modal();		
	});	
	
	$('.table #deleteButton').on('click',function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('#deleteModal #delRef').attr('href', href);
		$('#deleteModal').modal();		
	});	
	
	$('.table #photoButton').on('click',function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('#photoModal #employeePhoto').attr('src', href);	
		$.get(href, function(employee){
			$('#txtId').val(employee.id);
		});	
		$('#photoModal').modal();		
	});	
	
	$('#uploadButton').on('click',function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		
		$.post(href, function(data){
			console.log(data);
		});
	});	
});




//function fcnUpload(url){
//	console.log("Upload button was clicked");
//	//var href = $(this).attr('href');
//	var href = url;
//	console.log("End point of upload: " + href);
//	$.post(href, function(data, status){
//		console.log(data);
//	});
//}