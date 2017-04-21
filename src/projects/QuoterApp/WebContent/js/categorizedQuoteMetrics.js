
var MISSING_METRICS_ERROR = '<span class="error">Missing metrics error</span>';

$(document).ready(function(){
	
	var rules = {};
	// get all the text inputs
	$("form#command input[type=text]").each(function(){
		if ($(this).hasClass('numeric'))
		{
			rules[$(this).attr('name')] = {number:true};
		
			$(this).change(function(){
				onChangeNumeric(this);
			});
		}
		
	});
	
	$("form#command input.required").each(function(){

		if (typeof rules[$(this).attr('name')] == 'undefined')
			rules[$(this).attr('name')] = {required:true};
		else
			rules[$(this).attr('name')].required = true;
	});


	// make the validation
	$("#command").validate({
		rules: rules,
        submitHandler: function(form) {
			  
		    form.submit();
		  }
	});
	
	$('.boolPlus').change(function(){
		var div = $(this).next();
		var input = div.find('input');
		if($(this).val() == 'TRUE')
		{
			div.removeClass('hide');
			input.rules('add', {required:true});
			input.on('change', function(){
				somethingChanged = true;
				onChangeNumeric(this);
				});
			
		}
		else
		{
			if (input.hasClass('numeric'))
			{
				input.val(0);
				input.trigger('change');
			}
			div.addClass('hide');
			input.rules('remove', 'required');
			input.off('change');
		}
		
	});

});

function onChangeNumeric(ths)
{
	//calculate new total and %
	var total = 0;
	var inputs = $(ths).parent().parent().parent().parent() // get table or tbody level
	        .find("input[name$='metricValue']");
	
	var inputTotal = null;
	
	$.each(inputs, function(i, val){
	   var value;
	   
	    if ($(inputs[i]).attr('isTotal') == '1')
	    {
	    	inputTotal = $(inputs[i]);
	    }
	    else
	    {
    	    value = parseFloat($(val).val());
    	    total = total + (isNaN(value) ? 0 : value);
	    }
	});
	if (inputTotal != null) //&& inputTotal.length)
	{
		inputTotal.val(total);

		$.each(inputs, function(i, val){
    	   var value = parseFloat($(val).val());
    	   var name = $(val).attr('name').replace('metricValue', 'metricPct');
    	   if ($('input[name="'+name+'"]').length)
    	   {
	    	   var percentage;
	    	   if (value == total)
	    		   percentage = 100;
	    	   else
	    		   percentage = (isNaN(value/total) ? 0 : value/total)*100;
	    	   $('input[name="'+name+'"]').val(percentage).parent().children().find('label').text((percentage > 0) ? (Math.round(percentage) + '%') : '');
    	   }
    	});
		
		// again but with format
		inputTotal.val($.formatNumber(total, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}));
	}			
}

