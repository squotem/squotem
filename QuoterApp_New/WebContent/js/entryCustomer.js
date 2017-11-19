$(document).ready(function(){
	
	$('select[name="customer.id"]').change(function(){
		var val = $(this).val();
		setupCustomerFields(val);
	});
		
	$('select[name="customer.country.countryCode"]').change(function(){
		var stateCombo = $('select[name="customer.state.stateShortName"]');
		var country = $(this).val();
		stateCombo.empty();
		
		for (var i in states[country])
		{
		     stateCombo
	         .append($("<option></option>")
	         .attr("value", states[country][i].shortName)
	         .text(states[country][i].name)); 
		}
	});
	
	$('select[name="matrix.id"]').change(function(){
		if (matrices[$(this).val()].hasMts)
			$('#hasMts1').prop('disabled', false);
		else
			$('#hasMts1').prop('disabled', true).prop('checked', false);
	});
	
	$('#hasMts1').prop('disabled', !matrices[$('select[name="matrix.id"]').val()].hasMts);
	
	setupCustomerFields($('select[name="customer.id"]').val());

	$("#command").validate({
		rules: {
			'customer.name': {
            	required : true
            },
			'customer.sponsorPhone': {
                required : false,
                phoneUS  : true
            },
            /*productCategoryIds : {
            	required : true
            },*/
            'matrix.id' : {
            	required : true
            }
        },
        messages: {
        	'customer.sponsorPhone': {
                required: "Please enter your phone number",
                phoneUS: "Please enter a valid phone number"
            },
            /*productCategoryIds:"Please select TMS and/or Procurement",*/
            'matrix.id':"Please select one"
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") == "productCategoryIds") {
              error.insertAfter("#quote-type");
            } else {
              error.insertAfter(element);
            }
          },
        submitHandler: function(form) {
			  
		    form.submit();
		  }
	});
	
	// dates
	$('#effectiveDate').datepicker();;
	$('#expireDate').datepicker();;

});

function setupCustomerFields(selectedCustomerId) {
	if (selectedCustomerId < 0)
	{
		$('input[name="customer.name"]').removeClass('hide').val('');
		$('#name-lbl').addClass('hide');
		
		$('#customerTypeStr').removeClass('hide').val($('#customerTypeStr option:first').val());
		$('#customerType-lbl').addClass('hide');
		
		$('select[name="customer.businessSector.id"]').removeClass('hide').val($('select[name="customer.businessSector.id"] option:first').val());
		$('#businessSector-lbl').addClass('hide');
		
		$('input[name="customer.city"]').removeClass('hide').val('');
		$('#city-lbl').addClass('hide');
		
		$('select[name="customer.state.stateShortName"]').removeClass('hide').val('');
		$('#state-lbl').addClass('hide');
		
		$('select[name="customer.country.countryCode"]').removeClass('hide').val('US').trigger('change'); // default value
		$('#country-lbl').addClass('hide');
		
		$('input[name="customer.projectSponsor"]').removeClass('hide').val('');
		$('#projectSponsor-lbl').addClass('hide');
		
		$('input[name="customer.sponsorPhone"]').removeClass('hide').val('');
		$('#sponsorPhone-lbl').addClass('hide');
	}
	else
	{
		$('input[name="customer.name"]').addClass('hide');
		$('#name-lbl').removeClass('hide').text(customers[selectedCustomerId].name);
		
		$('#customerTypeStr').addClass('hide');
		$('#customerType-lbl').removeClass('hide').text(customers[selectedCustomerId].customerType);
		
		$('select[name="customer.businessSector.id"]').addClass('hide');
		$('#businessSector-lbl').removeClass('hide').text(customers[selectedCustomerId].businessSector);
		
		$('input[name="customer.city"]').addClass('hide');
		$('#city-lbl').removeClass('hide').text(customers[selectedCustomerId].city);
		
		$('select[name="customer.state.stateShortName"]').addClass('hide');
		$('#state-lbl').removeClass('hide').text(customers[selectedCustomerId].state);
		
		$('select[name="customer.country.countryCode"]').addClass('hide');
		$('#country-lbl').removeClass('hide').text(customers[selectedCustomerId].country);
		
		$('input[name="customer.projectSponsor"]').addClass('hide');
		$('#projectSponsor-lbl').removeClass('hide').text(customers[selectedCustomerId].projectSponsor);
		
		$('input[name="customer.sponsorPhone"]').addClass('hide');
		$('#sponsorPhone-lbl').removeClass('hide').text(customers[selectedCustomerId].sponsorPhone);
	}		
}


