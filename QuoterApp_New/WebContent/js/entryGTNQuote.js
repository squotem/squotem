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
	
	$('.pricing-checkbox').change(function(){
		var checkedId = this.id;
		//alert("checkedId"+checkedId);
		if (this.checked) {
			var determiningValue = 0;
			var matchIndex=0;
			$('.minLicenseTier'+checkedId).each(function(i) {
				//alert("inside");
				if(checkedId==61 || checkedId==64 || checkedId==66)
				{
					determiningValue = parseInt($('#totalFreightSpend').val());
				}
				else if(checkedId == 62 || checkedId == 63)
					{
					  determiningValue = parseInt($('#totalFreightUnderManagement').val());
					}
				else if(checkedId == 65 || checkedId == 67)
					{
					  determiningValue = parseInt($('#totalParcelCarriers').val());
					}
				var minLicenseValue = parseInt($(this).val());
				if (determiningValue != 0 && determiningValue > minLicenseValue)
						{
								var matchedId = this.id;
								var maxLicenseValue = parseInt($('#maxLicense'+matchedId).val());
								if (determiningValue <= maxLicenseValue || maxLicenseValue ==0)
				        		{
					        		matchIndex = this.id;
					        		return false;
				        		}
						}
			});
			var licenseFee = $('#licenseFee'+matchIndex).val();
			if(checkedId ==65 || checkedId == 67)
				{
				$('#licensePrice'+checkedId).html(licenseFee*determiningValue);
				}
			else {
			$('#licensePrice'+checkedId).html(licenseFee);
			}
			
			var determiningSubValue = 0;
			var matchSubscriptionIndex=0;
			$('.minTier'+checkedId).each(function(i) {
				//alert("inside again");
				if(checkedId==61 || checkedId==64 || checkedId==65 || checkedId ==66 || checkedId ==67)
				{
					determiningSubValue = parseInt($('#totalFreightSpend').val());
				}
				else if(checkedId == 62 || checkedId == 63)
					{
					determiningSubValue = parseInt($('#totalFreightUnderManagement').val());
					}
				else if(checkedId == 68)
				{
				determiningSubValue = parseInt($('#totalFleetSize').val());
				}
				else if(checkedId == 71)
				{
				determiningSubValue = parseInt($('#procurementBidValue').val());
				}
				//alert("determiningSubValue"+determiningSubValue);
				var minSubscriptionValue = parseInt($(this).val());
				//alert("minSubscriptionValue"+minSubscriptionValue);
				if (determiningSubValue != 0 && determiningSubValue > minSubscriptionValue)
						{
								var matchedSubId = this.id;
								var maxSubscriptionValue = parseInt($('#maxTier'+matchedSubId).val());
								//alert("maxSubscriptionValue"+maxSubscriptionValue);
								if (determiningSubValue <= maxSubscriptionValue || maxSubscriptionValue ==0)
				        		{
									//alert("matched");
									matchSubscriptionIndex = this.id;
					        		return false;
				        		}
						}
			});
			
			var annualSubscriptionFee = $('#annualFee'+matchSubscriptionIndex).val();
			 if(checkedId == 65 || checkedId ==67)
			{
			  var totalParcelCarriers = parseInt($('#totalParcelCarriers').val());
			  if(isNaN(totalParcelCarriers) || (totalParcelCarriers == 0))
				  {
				  totalParcelCarriers = 1;
				  }
			  $('#subscriptionPrice'+checkedId).html(annualSubscriptionFee*totalParcelCarriers);
			}
			 else {
				 $('#subscriptionPrice'+checkedId).html(annualSubscriptionFee);
			 }
			
			
			var determiningTierValue = 0;
			var matchTierIndex=0;
			$('.minTierPrice'+checkedId).each(function(i) {
			//alert("inside min tier");
				
				if(checkedId==62)
				{
					determiningTierValue = parseInt($('#totalTMS4FTransactions').val());
				}
				else if(checkedId == 63)
					{
					//alert("63");
					determiningTierValue = parseInt($('#totalTMS4LSPTransactions').val());
					}
				else if(checkedId == 64 || checkedId == 65 || checkedId == 66 || checkedId == 67)
				{
					determiningTierValue = parseInt($('#totalAnnualParcelShipments').val());
				}
				
				
				var minTierPrice = parseInt($(this).val());	
				//alert("inside min tier"+minTierPrice);
				//alert("pricing value"+determiningTierValue);
				if (determiningTierValue > minTierPrice)
						{
					//alert("match");
								var matchTierId = this.id;
								var maxTierPrice = parseInt($('#maxTierPrice'+matchTierId).val());
								//alert("max tier price"+maxTierPrice);
								if (determiningTierValue <= maxTierPrice || maxTierPrice ==0)
				        		{
									//alert("matched")
									matchTierIndex = this.id;
					        		return false;
				        		}
						}
			});
			var feePerTransaction = $('#feePerTransaction'+matchTierIndex).val();
			
			if(checkedId==62 || checkedId==63 || checkedId==64 || checkedId==66)
				{
					var tierValue = $('#tier'+matchTierIndex).val();
					if(tierValue == 1)
						{
						   var maxTierPrice = parseInt($('#maxTierPrice'+matchTierIndex).val());
							$('#tieredPrice'+checkedId).html(feePerTransaction*maxTierPrice);
						}
					else{
						$('#tieredPrice'+checkedId).html(feePerTransaction*determiningTierValue);
					}
				}
			else if(checkedId==65 || checkedId==67)
				{
				 var totalParcelCarriers = parseInt($('#totalParcelCarriers').val());
				  if(isNaN(totalParcelCarriers) || (totalParcelCarriers == 0))
					  {
					  totalParcelCarriers = 1;
					  }
					var annualPricingFee = parseInt($('#annualPricingFee'+matchTierIndex).val());
				  
				  $('#tieredPrice'+checkedId).html(annualPricingFee*totalParcelCarriers);
		
				}
			//alert("feePerTransaction"+feePerTransaction);
			
			
			var determiningFreightValue = 0;
			var matchTransPricingIndex=0;
			$('.minFreight'+checkedId).each(function(i) {
				
				if( checkedId==64 || checkedId==65 || checkedId==66 || checkedId==67 )
				{
					determiningFreightValue = parseInt($('#totalFreightSpend').val());
				}
				else if(checkedId == 69 )
				{
					determiningFreightValue = $('#bluedexInput').val();
				}
				else if(checkedId == 62 || checkedId == 63 )
					{
					determiningFreightValue = parseInt($('#totalFreightUnderManagement').val());
					}
				else if(checkedId == 73)
					{
					determiningFreightValue = parseInt($('#totalDPSTransactions').val());
					}
				else if(checkedId == 74)
				{
				determiningFreightValue = parseInt($('#totalTradingPartners').val());
				}
				else if(checkedId == 75)
				{
				determiningFreightValue = parseInt($('#totalDocDeterTransactions').val());
				}
				else if(checkedId == 76)
				{
				determiningFreightValue = parseInt($('#totalLicDeterTransactions').val());
				}
				else if(checkedId == 77 || checkedId == 78)
				{
				determiningFreightValue = parseInt($('#totalISFTransactions').val());
				}
				else if(checkedId == 79 || checkedId == 80)
				{
				determiningFreightValue = parseInt($('#totalAMSTransactions').val());
				}
				else if(checkedId == 81 || checkedId == 82)
				{
				determiningFreightValue = parseInt($('#totalAESTransactions').val());
				}
				
				
				
				//alert("inside min tran");
				var minFreightValue = parseInt($(this).val());
				//alert("minFrieghtValue"+minFreightValue);
				//alert("pricing value"+pricingValue);
				
				if (determiningFreightValue > minFreightValue)
						{
					//alert("match");
								var matchedFreightId = this.id;
								if(checkedId==69)
										{
										  var tierTrans =parseInt($('#tierTrans'+matchedFreightId).val());
										  if(determiningFreightValue==tierTrans)
											  {
											    matchTransPricingIndex = this.id;
											    return false;
											  }
										}
								else{
									var maxFreightValue = parseInt($('#maxFreight'+matchedFreightId).val());
									//alert("maxFrieghtValue"+maxFreightValue);
									if (determiningFreightValue <= maxFreightValue || maxFreightValue ==0)
					        		{
										//alert("matched");
										matchTransPricingIndex = this.id;
						        		return false;
					        		}
								}
						}
			});
			var annualPrice = $('#annualPrice'+matchTransPricingIndex).val(); 
			if((checkedId == 73 || checkedId ==74 || checkedId ==75 || checkedId ==76 || checkedId ==77 || checkedId ==78 || checkedId ==79 || checkedId ==80 || checkedId ==81 || checkedId ==82) && (matchTransPricingIndex != 0) && annualPrice == 0)
				{
				var pricePerTrans = $('#pricePerTrans'+matchTransPricingIndex).val(); 
				
				$('#perTransactionMinimumPrice'+checkedId).html((determiningFreightValue*pricePerTrans).toFixed(3));

				}
			else if(checkedId == 65 || checkedId ==67)
				{
				  var totalParcelCarriers = parseInt($('#totalParcelCarriers').val());
				  if(isNaN(totalParcelCarriers) || (totalParcelCarriers == 0))
					  {
					  totalParcelCarriers = 1;
					  }
				  $('#perTransactionMinimumPrice'+checkedId).html(annualPrice*totalParcelCarriers);
				}
			else{
			$('#perTransactionMinimumPrice'+checkedId).html(annualPrice);
			}
			
		}
		else{
			$('#subscriptionPrice'+checkedId).html('');
			$('#licensePrice'+checkedId).html('');
			$('#tieredPrice'+checkedId).html('');
			$('#perTransactionMinimumPrice'+checkedId).html('');
		}
	});
	
	
	
});

function setupTables()
{
	var aoColumnsDefs = [/*{ "bSortable": false, "aTargets": [ 0, 1 ] },*/
		                 { "bVisible" : false, "aTargets": [FUNCTIONALITY_COL, ENABLED_DATA_COL]},
		                 { "sClass"   : "center", "aTargets" : [ENABLED_COL]}, // SPECIAL_COL
		                { "sWidth"   : "10%", "aTargets": [ ENABLED_COL ] }, // SPECIAL_COL
		                 { "sWidth"   : "18%", "aTargets": [ CATEGORY_COL ] },
		                 { "sWidth"   : "18%", "aTargets": [ IMPLEMENTATION_COST_COL ] },
		                 { "sWidth"   : "18%", "aTargets": [ MONTHLY_COST_COL ] }//33
		                 ];
	
	if (isAdmin)
	{
		aoColumnsDefs.push({ "sWidth"   : "0%", "aTargets": [ MONTHLY_COST_COL + 1, MONTHLY_COST_COL + 2 ] });
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



