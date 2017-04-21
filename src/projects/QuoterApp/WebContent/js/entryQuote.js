var ENABLED_COL             = 0;
var SPECIAL_COL             = 1;
var CATEGORY_COL            = 2;
var FUNCTIONALITY_COL       = 3;
var ENABLED_DATA_COL        = 4;
var IMPLEMENTATION_COST_COL = 5;
var MONTHLY_COST_COL        = 6;

var REMOVE_ICON = 'images/remove.png';
var ADD_ICON = 'images/add.png';

$(document).ready(function(){
	
	setupTables();
		
	$("#terms").rules('add', {required       : true,
					        number         : true,
					        positiveNumber : true});
	
});

function setupTables()
{
	var aoColumnsDefs = [/*{ "bSortable": false, "aTargets": [ 0, 1 ] },*/
		                 { "bVisible" : false, "aTargets": [SPECIAL_COL, ENABLED_DATA_COL, IMPLEMENTATION_COST_COL, MONTHLY_COST_COL] },
		                 { "sClass"   : "center", "aTargets" : [ENABLED_COL]}, // SPECIAL_COL
		                 { "sWidth"   : "9%", "aTargets": [ ENABLED_COL ] }, // SPECIAL_COL
		                 { "sWidth"   : "16%", "aTargets": [ CATEGORY_COL ] },
		                 { "sWidth"   : "40%", "aTargets": [ FUNCTIONALITY_COL ] } //33
		                 ];
	
	if (isAdmin)
	{
		aoColumnsDefs.push({ "sWidth"   : "18%", "aTargets": [ MONTHLY_COST_COL + 1, MONTHLY_COST_COL + 2 ] });
		aoColumnsDefs.push({ "sClass"   : "right", "aTargets" : [ MONTHLY_COST_COL + 1, MONTHLY_COST_COL + 2 ]});
	}
	
	// init tables
	$('.product-category-tables').find('table')
	                             .each(function(i, val){
	                     			$('#'+val.id).dataTable({"bFilter"      : false, 
										"bJQueryUI"    : true, 
										"bInfo"        : false, 
										"bPaginate"    : false,
										"bSort"        : false,
										"bAutoWidth"   : false,
										"aoColumnDefs" : aoColumnsDefs,
										"fnFooterCallback": function( nFoot, aData, iStart, iEnd, aiDisplay ) {
											if (isAdmin)
											{
												var totalImp = 0;
												var totalMon = 0;
												for (var i = 0; i < aData.length; i++)
												{
													if (aData[i][ENABLED_DATA_COL] == 'true')
													{
														totalImp = totalImp  + parseFloat(aData[i][IMPLEMENTATION_COST_COL]);
														totalMon = totalMon  + parseFloat(aData[i][MONTHLY_COST_COL]);
													}
														 
												}
	
											    nFoot.getElementsByTagName('th')[1].innerHTML = isNaN(totalImp) ? MISSING_METRICS_ERROR : ((isAdmin ? '' : (implementationLbl + ':')) + $.formatNumber(totalImp, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}));
											    nFoot.getElementsByTagName('th')[2].innerHTML = isNaN(totalMon) ? MISSING_METRICS_ERROR : ((isAdmin ? '' : (monthlyLbl+ ':')) + $.formatNumber(totalMon, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}));
											}
									    }
									});
	                             });
	
	// alternate color first time
	$('.enabled-combo').each(function(){
		if ($(this).val() == 'true')
		{	
			var alternateColor = $(this).attr('alternateColor');
			if (alternateColor != '')
				$(this).parent().parent().find('.functionality').css('color', alternateColor);
		}
	});
	
	// change event
	$('.enabled-combo').change(function(event){
		var tableId = $(this).parent().parent().parent().parent().attr('id');
		var oTable =  $('#'+tableId).dataTable();
		var tr = $(this).parent().parent().get(0);
		var alternateColor = $(this).attr('alternateColor');
		
		var pos = oTable.fnGetPosition(tr);
		var data = oTable.fnGetData(pos);
		
		data[ENABLED_DATA_COL] = $(this).val(); 
		if (data[ENABLED_DATA_COL] == 'true')
		{
			//$.formatNumber(number, {format:NUMBER_FORMAT, locale:NUMBER_LOCALE});
			   
			if (isAdmin)
			{
				oTable.fnUpdate($.isNumeric(data[IMPLEMENTATION_COST_COL]) ? $.formatNumber(data[IMPLEMENTATION_COST_COL], {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) : data[IMPLEMENTATION_COST_COL], tr, IMPLEMENTATION_COST_COL + 2);
				oTable.fnUpdate($.isNumeric(data[MONTHLY_COST_COL])        ? $.formatNumber(data[MONTHLY_COST_COL],        {format:NUMBER_FORMAT, locale:NUMBER_LOCALE}) : data[MONTHLY_COST_COL],        tr, MONTHLY_COST_COL + 2);
			}
			
			if (alternateColor != '')
				$(tr).find('.functionality').css('color', alternateColor);
		}
		else
		{
			oTable.fnUpdate( '', tr, IMPLEMENTATION_COST_COL + 2);
			oTable.fnUpdate( '', tr, MONTHLY_COST_COL + 2);

			if (alternateColor != '')
				$(tr).find('.functionality').css('color', '');
		}
		
	});
	
	$( '.tooltip' ).tooltip({
	      position: {
	        /*my: "center bottom-20",
	        at: "center top",*/
	      }
	    });
}



