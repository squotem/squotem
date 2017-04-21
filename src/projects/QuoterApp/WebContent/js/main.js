var MISSING_METRICS_ERROR = '<span class="error">Missing metrics error</span>';

var somethingChanged = false;
var leaveMessage = "You are exiting this page, but there are some unsaved changes. Do you want to continue anyway?";
var cancelMessage = "Do you really want to cancel this quote?";
var canceledQuoteMessage="Successfully canceled quote";

var NUMBER_FORMAT     = "###,###,###";
var CURRENCY_FORMAT   = "$###,###,##0.00";
var PERCENTAGE_FORMAT = "#,##0%"; 
var NUMBER_LOCALE     = "us";
var DECIMAL_FORMAT     = "###,##0.00";



$(document).ready(function(){
	$('.btn-save').button({
	      icons: {
		        primary: "ui-icon-disk"
		      }
		    }).tooltip().click(function(){$('#save').val('S');});
	
	$('.btn-next').button({
	      icons: {
		        primary: "ui-icon-arrowthick-1-e"
		      }
		    }).tooltip().click(function(){$('#save').val('N');});
	
	
	$('.btn-refresh').button({
	      icons: {
		        primary: "ui-icon-refresh"//"ui-icon-arrowthick-1-e"
		      }
		    }).tooltip().click(function(){$('#save').val('R');});
	
	
	$('#btn-new-quote').button({
	      icons: {
		        primary: "ui-icon-plus"
	      }
	});

	$('#btn-search').button({
	      icons: {
		        primary: "ui-icon-search"
	      }
	});

	$('#sidebar-submit').button({
	      icons: {
		        primary: "ui-icon-check"
	      }
	});

	/*$('#sidebar-cancel').button({
	      icons: {
		        primary: "ui-icon-close"
	      }
	}).click(function(){
		cancelQuote(currentQuote);
	});*/

	//---- show content here
	
	$('#wrapper').removeClass('hide');
	
	//----
	
	$('form#command select, form#command input').change(function() { 
        somethingChanged = true; 
    });
	
	$('form#command').submit(function(){
		somethingChanged = false;
	});
	
	// function to sort dates
	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	    "date-pre": function ( a ) {
	        var ukDatea = a.split(' ');
	        var result = null;
	        if (ukDatea.length == 2)
	        {
	        	var date = ukDatea[0].split('/');
	        	var time = ukDatea[1].split(':');
	        	
	        	result = (date[2] + date[0] + date[1] + time[0] + time[1]) * 1;
	        }
	        else
	        {
	        	ukDatea = a.split('/');
	        	result = (ukDatea[2] + ukDatea[0] + ukDatea[1]) * 1;
	        }
	        return result;
	    },
	 
	    "date-asc": function ( a, b ) {
	        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	    },
	 
	    "date-desc": function ( a, b ) {
	        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	    }
	} );
	
	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	    "quote-pre": function ( a ) {
	        var num = parseFloat(a);
	        var arr;
	        
	        if (isNaN(num))
	        	arr = $(a).text().split('.');
	        else
	        	arr = a.split('.');

	        num = arr[0] * 1000 + arr[1] * 1;
	        
	        return num;
	    },
	 
	    "quote-asc": function ( a, b ) {
	        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	    },
	 
	    "quote-desc": function ( a, b ) {
	        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	    }
	} );
	
	// to validate date range
	jQuery.validator.addMethod("greaterOrEqual", 
			function(value, element, params) {

			    if (!/Invalid|NaN/.test(new Date(value))) {
			        if ($(params).val())
			        	return new Date(value) >= new Date($(params).val());
			        else
			        	return true;
			    }

			    return isNaN(value) && isNaN($(params).val()) 
			        || (Number(value) >= Number($(params).val())); 
			},'Must be greater or equal to {0}.');
	
	jQuery.validator.addMethod('positiveNumber',
		    function (value) { 
		        return Number(value) > 0;
		    }, 'Enter a positive number.');
});

function cancelQuote(qId){
	if (confirm(cancelMessage))
	{
		$.ajax({
			  type: "POST",
			  url: "changeStatus.json",
			  data: { q: qId, st: 'CANCELED' }
			})
			  .done(function( msg ) {
				  if (msg.success)
					  window.location= 'success.htm?m='+canceledQuoteMessage;
				  else
					  alert(msg.errorMessage);
			  });
	}
}

// to prevent the browser to leave the page
function leavePage(e) 
{
    if(somethingChanged)
    {
        if(!e) e = window.event;
        //e.cancelBubble is supported by IE - this will kill the bubbling process.
        e.cancelBubble = true;
        e.returnValue = leaveMessage;
        //e.stopPropagation works in Firefox.
        if (e.stopPropagation) 
        {
            e.stopPropagation();
            e.preventDefault();
        }

        //return works for Chrome and Safari
        return leaveMessage;
    }
}

window.onbeforeunload=leavePage;

