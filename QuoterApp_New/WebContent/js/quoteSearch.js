var QUOTE_ID_COL         = 0;
var QUOTE_STATUS_COL     = 1;
var QUOTE_NUM_COL        = 2;
var QUOTE_REV_COL        = 3;
var QUOTE_ACTIVE_REV_COL = 4;
var NUM_REV_COL          = 5;
var CUSTOMER_COL         = 6;
var AUTHOR_COL           = 7;
var CREATED_DATE_COL     = 8;
var PRICING_MODEL_COL    = 9;
var PRODUCT_COL          = 10;
var STATUS_COL           = 11;
var ACTION_COL           = 12;

var STATUS_DRAFT     = 'DRAFT';
var STATUS_SUBMITTED = 'SUBMITTED';
var STATUS_APPROVED  = 'APPROVED';

$(document).ready(function() {
	$( "#from" ).datepicker({ dateFormat: "mm/dd/yy" });
	$( "#to" ).datepicker({ dateFormat: "mm/dd/yy" });
	
	$('#quoter-search-result').dataTable({
		"bFilter"        : true, 
		"bJQueryUI"      : true,
		"iDisplayLength" : 20,
		"aaSorting": [[ CREATED_DATE_COL, "desc" ]],
		"aoColumnDefs": [ 
		                 { "bVisible"  : false,   "aTargets" : [ QUOTE_ID_COL, QUOTE_STATUS_COL, QUOTE_NUM_COL, QUOTE_REV_COL, QUOTE_ACTIVE_REV_COL ] },
		                 { "bSortable" : false,   "aTargets" : [ ACTION_COL ] },
		                 { "sWidth"    : "10%",   "aTargets" : [ CUSTOMER_COL, AUTHOR_COL, PRICING_MODEL_COL] },
		                 { "sWidth"    : "4%",    "aTargets" : [ STATUS_COL] },
		                 { "sWidth"    : "7%",    "aTargets" : [ NUM_REV_COL] },
		                 { "sWidth"    : "8%",    "aTargets" : [ CREATED_DATE_COL] },
		                 { "sWidth"    : "11%",   "aTargets" : [ ACTION_COL] },
		                 { "sWidth"    : "10%",   "aTargets" : [ PRODUCT_COL] },
		                 { "sType"     : "quote", "aTargets" : [ NUM_REV_COL] }
		             ], 
		"aLengthMenu": [[10, 20, 50, -1], [10, 20, 50, "All"]]
	});
	
	$("#quoteQuery").validate({
	    rules: {
	        to: { greaterOrEqual: "#from" }
	    },
		messages: {
			to: "'To' must be greater or equal to 'From'"
		}
	});
	
	setupChangeStatusButton();
	
	$('.newRevision').click(function (e){
		e.preventDefault();
		var oTable = $('#quoter-search-result').dataTable();
		var tr = this.parentElement.parentElement;
		var data = oTable.fnGetData(tr);

		createRevisionAjax(data[QUOTE_ID_COL], tr);
	});
	
	$('.addComment').click(function(){
		var oTable = $('#quoter-search-result').dataTable();
		var tr = this.parentElement.parentElement;
		var data = oTable.fnGetData(tr);
		
		$('#dlg-new-comment').val('');

		$('#dlg-add-comment').dialog({
		      modal: true,
		      buttons: {
		        Ok: function() {
		        	addCommentAjax(data[QUOTE_ID_COL], $('#dlg-new-comment').val());
		        	$( this ).dialog( "close" );
		        },
		        Cancel: function() {
			          $( this ).dialog( "close" );
			        }
	
		      },
			  width : 405,
			  height: 235
		    });
	});
	
	$('#onlyActive1').change(function(){
		$('#quoter-search-result').dataTable().fnDraw();
	});
	
} );

// custom filtering using onlyActive checkbox
$.fn.dataTableExt.afnFiltering.push(
	    function( oSettings, aData, iDataIndex ) {
	        //var active = $('#onlyActive1').is(':checked');
	        //console.log('aData', aData);
	        if ( !$('#onlyActive1').is(':checked') || aData[QUOTE_ACTIVE_REV_COL] == 'true' )
	        {
	            return true;
	        }
	        return false;
	    }
	);


function setupChangeStatusButton()
{
	$('.changeStatus').off('click');
	
	$('.changeStatus').on('click', function(e){
		e.preventDefault();
		var oTable = $('#quoter-search-result').dataTable();
		var tr = this.parentElement.parentElement;
		var data = oTable.fnGetData(tr);
		var preAppendText = 'Changed Quote Status from ';
			
		$('#dlg-quote-num').text(data[QUOTE_NUM_COL]);
		$('#dlg-quote-rev').text(data[QUOTE_REV_COL]);
		$('#dlg-quote-status').text(data[STATUS_COL]);
		
		// set statuses combo
		$('#dlg-quote-new-status').empty();
		
		switch (data[QUOTE_STATUS_COL])
		{
			case STATUS_DRAFT:
				$('#dlg-quote-new-status').val(STATUS_SUBMITTED);
				$('#dlg-quote-new-status-lbl').text(quoteStatuses[STATUS_SUBMITTED]);
				preAppendText = preAppendText + quoteStatuses[STATUS_DRAFT] + ' to ' + quoteStatuses[STATUS_SUBMITTED] + ' | ';
				break;
			case STATUS_SUBMITTED:
				$('#dlg-quote-new-status').val(STATUS_APPROVED);
				$('#dlg-quote-new-status-lbl').text(quoteStatuses[STATUS_APPROVED]);
				preAppendText = preAppendText + quoteStatuses[STATUS_SUBMITTED] + ' to ' + quoteStatuses[STATUS_APPROVED] + ' | ';
				break;
			case STATUS_APPROVED:
				break;
		}
		
		
		$('#dlg-status-comment').val('');
		$('#dlg-change-status').dialog({
		      modal: true,
		      buttons: {
		        Ok: function() {
		        	updateQuoteAjax(data[QUOTE_ID_COL], $('#dlg-quote-new-status').val(), preAppendText + $('#dlg-status-comment').val(), tr, this);
		        },
		        Cancel: function() {
			          $( this ).dialog( "close" );
			        }
	
		      },
			  width : 415,
			  height: 340
		    });
		
	});
	
	$('.submitQuote').off('click');
	
	$('.submitQuote').on('click', function(e){
		e.preventDefault();
		var oTable = $('#quoter-search-result').dataTable();
		var tr = this.parentElement.parentElement;
		var data = oTable.fnGetData(tr);

		submitQuoteAjax(data[QUOTE_ID_COL], tr);
	});
	

}

function addCommentAjax(qId, qComment)
{
	// make ajax call then update the table if apply
	//changeStatus.json?q=NNN&st=BLABLA
			
	$.ajax({
	  type: "POST",
	  url: "addComment.json",
	  data: { q: qId, comment: qComment }
	})
	  .done(function( msg ) {
		  if (msg.success)
			  null;
		  else
			  alert(msg.errorMessage);
	  });
}


function updateQuoteAjax(qId, qStatus, qComment, tr, popup)
{
	// make ajax call then update the table if apply
	//changeStatus.json?q=NNN&st=BLABLA
	
	$('#dlg-change-status').dialog({
		buttons: {},
	});
	
	$('#working').show();
	
	//double check to make sure permissions are ok. Will never actually go? 
	if((approvalLevel == 'BASELINE' && qStatus != STATUS_SUBMITTED)
		|| (approvalLevel == 'BUSINESS_PLAN' && qStatus != STATUS_APPROVED)
		|| (approvalLevel == 'ALL' && (qStatus != STATUS_APPROVED && qStatus != STATUS_SUBMITTED))){
		alert("Error: You do not have permission to perform this action.");
		return;
	}
	
	$.ajax({
	  type: "POST",
	  url: "changeStatus.json",
	  data: { q: qId, st: qStatus, comment: qComment }
	})
	  .done(function( msg ) {
		  if (msg.success) {

			  $( popup ).dialog( "close" );
			  $('#working').hide();
			  updateQuoteTable(qStatus, tr);
		  
		  	  if (approvalLevel === 'BASELINE')
		  		  $( "#changeStatus" ).hide();
		  	  if (qStatus === "APPROVED")
		  		  $( "#changeStatus" ).hide();
		  }
		  else {
			  alert(msg.errorMessage);
		  }
		  
	  });
}

function updateQuoteTable(qStatus, tr)
{
	var oTable = $('#quoter-search-result').dataTable();
	var aPos = oTable.fnGetPosition( tr );
	var data = oTable.fnGetData(tr);
	
	// remove options
	switch (data[QUOTE_STATUS_COL])
	{
		case STATUS_DRAFT:  // remove edit option
			//$(tr).find('.editQuote').addClass('hide');
			$(tr).find('.submitQuote').addClass('hide');
			//oTable.fnUpdate( data[QUOTE_NUM_COL] + '.' + data[QUOTE_REV_COL], aPos, NUM_REV_COL );
			break;
	}
	
	oTable.fnUpdate( qStatus, aPos, QUOTE_STATUS_COL );
	oTable.fnUpdate( quoteStatuses[qStatus], aPos, STATUS_COL );
	
}

function submitQuoteAjax(qId, tr)
{
	$.ajax({
		  type: "POST",
		  url: "submitQuote.json",
		  data: { q: qId }
		})
		  .done(function( msg ) {
			  if (msg.success)
				  updateQuoteTable(STATUS_SUBMITTED, tr);
			  else
				  alert(msg.errorMessage);
		  });
	
}

function createRevisionAjax(qId, tr)
{
	//- URL call: AJAX call: createRevision.json?q=XXX
	//		  Response: JSON object, with properties:
	//		  success : true/false
	//		  (if success = false), errorMessage
	//		  (if success = true), newQuoteId , containing id for the new revision
	$.ajax({
		  type: "POST",
		  url: "createRevision.json",
		  data: { q: qId }
		})
		  .done(function( msg ) {
			  if (msg.success)
				  createRevisionTable(msg.newQuoteId, tr);
			  else
				  alert(msg.errorMessage);
		  });
	
}

function createRevisionTable(newQuoteId, tr)
{
	var oTable = $('#quoter-search-result').dataTable();
	var data = oTable.fnGetData(tr);
	var row = data.slice();
	var d = new Date();
	
	// setup new row base on original one
	row[QUOTE_ID_COL]     = newQuoteId;
	row[QUOTE_STATUS_COL] = STATUS_DRAFT;
	row[QUOTE_REV_COL]    = parseInt(row[QUOTE_REV_COL]) + 1;
	row[NUM_REV_COL]      = '<a href="entryCustomer.htm?q='+row[QUOTE_ID_COL] + '" title="Edit quote #'+row[QUOTE_NUM_COL] + '">'+row[QUOTE_NUM_COL] + '.' + row[QUOTE_REV_COL]+'</a>'; // link
	row[CREATED_DATE_COL] = ('0' + (d.getMonth() + 1)).slice(-2) + '/' + ('0' + d.getDate()).slice(-2) + '/' + d.getFullYear();
	row[STATUS_COL]       = quoteStatuses[STATUS_DRAFT];
	
	row[ACTION_COL]       = replaceAll('q='+data[QUOTE_ID_COL], 'q='+newQuoteId, row[ACTION_COL]);
	row[ACTION_COL]       = replaceAll('#'+data[NUM_REV_COL], '#'+row[QUOTE_NUM_COL] + '.' + row[QUOTE_REV_COL], row[ACTION_COL]);
	
	// buttons visibility according to status
	// show edit, submit, change status, hide create new
	row[ACTION_COL]       = replaceAll('hide', '', row[ACTION_COL]);
	row[ACTION_COL]       = replaceAll('newRevision', 'newRevision hide', row[ACTION_COL]);
	
	// hide create new, change status in origin
	$(tr).find('.newRevision, .changeStatus').addClass('hide');

	// add new row
	oTable.fnAddData(row);
	
	// refresh change status button
	setupChangeStatusButton();
	
	//console.log('new qid:', newQuoteId, 'tr:', tr, 'data:', data);
	
}

function escapeRegExp(str) {
	return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
}

function replaceAll(find, replace, str) {
	return str.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}