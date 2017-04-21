
$(document).ready(function(){
	
	calculateTotal('monthly');
	
	calculateTotal('upfront');
	
	$("#monthly-table input[name$='customerQuote']").change(function(){
		calculateTotal('monthly');
	});
	
	$("#upfront-table input[name$='customerQuote']").change(function(){
		calculateTotal('upfront');
	});
	
	
	$.validator.addMethod('positiveNumber',
		    function (value) { 
		        return Number(value) >= 0;
		    }, 'Enter a positive number.');
	
	var rules = {};
	$("form#command input[name$='customerQuote']").each(function(){
		rules[$(this).attr('name')] = {required:false, number:true, positiveNumber:true};
	});
	
	$("form#command .numeric").each(function(){
		if (typeof rules[$(this).attr('name')] == 'undefined')
			rules[$(this).attr('name')] = {number:true};
		else
			rules[$(this).attr('name')].number = true;

	});
	
	$("form#command .required").each(function(){
		if (typeof rules[$(this).attr('name')] == 'undefined')
			rules[$(this).attr('name')] = {required:true};
		else
			rules[$(this).attr('name')].required = true;

	});

	// make the validation
	$("#command").validate({
		rules : rules,
        submitHandler: function(form) {
			  
		    form.submit();
		  }
	});

	onInputChange();
	
});

function onInputChange()
{
	$('#quoteImplementation').change(function(){

		var round = Math.round($(this).val() *1);
		var value = $.formatNumber(parseInt(round), {format:NUMBER_FORMAT, locale:NUMBER_LOCALE});
		$(this).val(round);
		$('#firstQuoteImpl').text( value );
		$('#firstQuoteImplTot').text( value );
		 
		$('#contractQuoteImpl').text( value );
		$('#contractQuoteImplTot').text( value );

		recalculateQuote();
	});

	$('#quoteMonthlyFee').change(function(){

		var round = Math.round($(this).val() *1);
		var value = $.formatNumber(parseInt(round), {format:NUMBER_FORMAT, locale:NUMBER_LOCALE});
		$(this).val(round);

		$('#firstQuoteMon').text( value );
		$('#firstQuoteMonTot').text( $.formatNumber(parseInt($(this).val()) * firstYearFeeMonths, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) );

		$('#contractQuoteMon').text( value );
		$('#contractQuoteMonTot').text( $.formatNumber(parseInt($(this).val()) * contractLength, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) );
		
		recalculateQuote();
	});
	
	/*$('input[name="quoteProcurement"').change(function(){
		 var value = $.formatNumber(parseInt($(this).val()), {format:NUMBER_FORMAT, locale:NUMBER_LOCALE});
		 $('#firstQuoteProc').text( value );
		 $('#firstQuoteProcTot').text( value );
		 
		 $('#contractQuoteMon').text( value );
		 $('#contractQuoteMonTot').text( value * firstYearFeeMonths );

		 recalculateQuote();
	});*/
}

function recalculateQuote()
{
	var market = $('#firstMarketSubTotal').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
	var subTotal =  $('#firstQuoteImplTot').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false) +
				    $('#firstQuoteMonTot').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
	var variance = (market - subTotal) / market;
	var savings = 0;
	
	$('#firstQuoteSubTotal').text( $.formatNumber(subTotal, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) );
	
	$('#customerFirstYear').text($('#firstQuoteSubTotal').text());
	$('#llFirstYear').text($('#firstQuoteSubTotal').text());

	savings = $('#customerSavings').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
	$('#customerRatio').text( $.formatNumber(savings/subTotal, {format:DECIMAL_FORMAT, locale:NUMBER_LOCALE}) );

	savings = $('#llSavings').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
	$('#llRatio').text( $.formatNumber(savings/subTotal, {format:DECIMAL_FORMAT, locale:NUMBER_LOCALE}) );
	
	if (variance == -Infinity)
		$('#firstMarketVariance').text(errorMissingMetric).addClass('red');
	else if (variance < 0)
		$('#firstMarketVariance').text( $.formatNumber( variance * -1, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE} ) ).addClass('green').removeClass('red');
	else if (variance > 0)
		$('#firstMarketVariance').text( $.formatNumber( variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE} ) ).addClass('red').removeClass('green');
	else
		$('#firstMarketVariance').text( $.formatNumber( variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE} ) ).removeClass('red').removeClass('green');

	market = $('#contractMarketSubTotal').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
	subTotal =  $('#contractQuoteImplTot').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false) +
	            $('#contractQuoteMonTot').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
	variance = (market - subTotal) / market;
    $('#contractQuoteSubTotal').text( $.formatNumber(subTotal, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) );
    
	if (variance == -Infinity)
		$('#contractMarketVariance').text(errorMissingMetric).addClass('red');
	else if (variance < 0)
    	$('#contractMarketVariance').text( $.formatNumber(variance * -1, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE} ) ).addClass('green').removeClass('red');
    else if (variance > 0)
    	$('#contractMarketVariance').text( $.formatNumber(variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE} ) ).addClass('red').removeClass('green');
    else
    	$('#contractMarketVariance').text( $.formatNumber(variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE} ) ).removeClass('red').removeClass('green');

}

function calculateTotal(tablePrefix)
{
	var totalMarket = 0;
	var totalCustomer = 0;
	var variance = 0;
	var error = null;
	$('#'+tablePrefix+'-table tr').not(':first').not(':last').each(function(){
		var row = $(this.cells);
		var market = $(row[1]).parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
		var customer = parseFloat($('input', row[2]).val());
		if (error == null && $('span', row[1]).length > 0)
			error = $('span', row[1]);
		totalMarket = totalMarket + market;
		totalCustomer = totalCustomer + customer;
		
		if (market > customer)
		{
			variance = (customer / market - 1)*-1;
			$(row[3]).removeClass('green')
			         .addClass('red')
			         .text( parseFloat(variance.toFixed(2)) > 0 ? $.formatNumber(variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE}) : '' );
		}
		else 
			if (market < customer)
			{
				variance =  market > 0 ? (customer / market - 1) : 1;
				$(row[3]).removeClass('red')
						 .addClass('green')
						 .text( parseFloat(variance.toFixed(2)) > 0 ? $.formatNumber( variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE}) : '');
			}
			else
				$(row[3]).removeClass('red').removeClass('green').text('');
			
		//console.log('market', market, 'customer', customer);
	});
	
	if (error != null)
	{
		$('#'+tablePrefix+'-market-total').text('');
		$('#'+tablePrefix+'-market-total').html(MISSING_METRICS_ERROR);
	}
	else
		if (totalMarket > 0)
			$('#'+tablePrefix+'-market-total').text( $.formatNumber(totalMarket, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) );
		else
			$('#'+tablePrefix+'-market-total').text('');
	
	if (totalCustomer > 0)
		$('#'+tablePrefix+'-customer-total').text( $.formatNumber(totalCustomer, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) );
	else
		$('#'+tablePrefix+'-customer-total').text('');
	
	// variance
	if (totalMarket > totalCustomer)
	{
		variance =((totalCustomer / totalMarket - 1)*-1);
		$('#'+tablePrefix+'-variance-total').removeClass('green').addClass('red').text(parseFloat(variance.toFixed(2)) > 0 ? $.formatNumber(variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE}) : '' );
	}
	else 
		if (totalMarket < totalCustomer)
		{
			variance = totalMarket > 0 ? (totalCustomer / totalMarket - 1) : 1; 
			$('#'+tablePrefix+'-variance-total').removeClass('red').addClass('green').text( parseFloat(variance.toFixed(2)) > 0 ? $.formatNumber(variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE}) : '' );
		}
		else
			$('#'+tablePrefix+'-variance-total').removeClass('red').removeClass('green').text('');
	
	calculateDealPrice();
}

// to calculate Deal Price
function calculateDealPrice()
{
	var terms = parseInt($('#terms').text());
	var totalMarket = 0;
	var totalCustomer = 0;
	var calcPercentage = true; 

	if ($('span', '#monthly-market-total').length > 0 || $('span', '#upfront-market-total').length > 0)
	{
		$('#deal-market').text('');
		$('#deal-market').html(MISSING_METRICS_ERROR);
		calcPercentage = false;
	}
	else
	{
		totalMarket = $('#monthly-market-total').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false) * terms
		            + $('#upfront-market-total').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
		
		$('#deal-market').text( $.formatNumber(totalMarket, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) );
	}
	
	if ($('span', '#monthly-customer-total').length > 0 || $('span', '#upfront-customer-total').length > 0)
	{
		$('#deal-customer').text('');
		$('#deal-customer').html(MISSING_METRICS_ERROR);
		calcPercentage = false;
	}
	else
	{
		totalCustomer = $('#monthly-customer-total').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false) * terms
    	  			  + $('#upfront-customer-total').parseNumber({format:NUMBER_FORMAT, locale:NUMBER_LOCALE}, false);
		
		$('#deal-customer').text( $.formatNumber(totalCustomer, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) );
	}
	
	if (calcPercentage)
	{
		var variance = 0;
		if (totalMarket > totalCustomer)
		{
			variance = ((totalCustomer / totalMarket - 1)*-1);
			$('#deal-variance').removeClass('green').addClass('red').text( parseFloat(variance.toFixed(2)) > 0 ? $.formatNumber(variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE}) : '' );
		}
		else 
			if (totalMarket < totalCustomer)
			{
				variance = ((totalCustomer / totalMarket - 1));
				$('#deal-variance').removeClass('red').addClass('green').text( parseFloat(variance.toFixed(2)) > 0 ? $.formatNumber(variance, {format:PERCENTAGE_FORMAT, locale:NUMBER_LOCALE}) : '' );
			}
			else
				$('#deal-variance').removeClass('red').removeClass('green').text('');
	}
	else
	{
		$('#deal-variance').removeClass('red').removeClass('green').text('');
		$('#deal-variance').html(MISSING_METRICS_ERROR);
	}


}