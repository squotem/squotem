var DECIMAL_DIGITS = 2;
$(document).ready(function(){

	$('#team-composition').dataTable({"bFilter"      : false, 
		"bJQueryUI"    : true, 
		"bInfo"        : false, 
		"bPaginate"    : false,
		"bSort"        : false,
		"bAutoWidth"   : false,
		"aoColumnDefs" : [{ "sClass"   : "right", "aTargets" : [1, 3]}, 
		                  { "sClass"   : "center", "aTargets" : [ 2 ]},
			              /*{ "sWidth"   : "50%", "aTargets": [ 0 ] },*/
			              { "sWidth"   : "17%", "aTargets": [ 1, 2, 3 ] }
			]
		});
	
	$("#command").validate({
		rules: {
			annualLoadCount: {
                required       : true,
                number         : true,
                positiveNumber : true
            }
        },
        submitHandler: function(form) {
		    form.submit();
		  }
	});
	
	$('select, input').change(function(){
		getTeamCompositionTableInfo();
	});

});

function getTeamCompositionTableInfo()
{
	var serializedData = $('#command').serialize();
	$.ajax({
        type     : "POST",
        url      : '/QuoterApp/whatIfForMTS.json',
        dataType : 'json',
        data     : serializedData
	}).done(function(response) {
        		console.log('success response:', response);
        		updateInfo(response);
            }
	).fail(function(response) {
		console.log('fail response', response);
	});	
}

function updateInfo(data)
{
	// Team Composition
	var oTable = $('#team-composition').dataTable();
	
	for (var i = 0; i < data.roleCosts.length; i++)
	{
		oTable.fnUpdate(data.roleCosts[i].roleCountCalculated.toFixed(DECIMAL_DIGITS), i, 1);
		oTable.fnUpdate((data.roleCosts[i].roleCountCalculated + data.roleCosts[i].roleCountAdditional).toFixed(DECIMAL_DIGITS), i, 3);
		$('#roleCountAdditional'+i).val(data.roleCosts[i].roleCountAdditional.toFixed(DECIMAL_DIGITS));
	}
	
	// MTS Summary
	$('#weekly-load').text($.formatNumber(data.baselineWeeklyLoadCount, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}));
	$('#monthly-price').text($.formatNumber(data.monthlyTotal, {format:CURRENCY_FORMAT, locale:NUMBER_LOCALE})); // $.formatNumber(data[IMPLEMENTATION_COST_COL], {format:NUMBER_FORMAT, locale:NUMBER_LOCALE})
	$('#annual-price').text($.formatNumber(data.anualTotal, {format:CURRENCY_FORMAT, locale:NUMBER_LOCALE})); //.toFixed(DECIMAL_DIGITS));
	
}
