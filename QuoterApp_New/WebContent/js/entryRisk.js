$(document).ready(function(){
	
	
	$('.risk-slider').each(function(){
		$(this).slider({
			min: 1,
			max: 10,
			range: "min",
			value: $(this).parent().find('input').val(),
			slide: function( event, ui ) {
		        $(this).parent().find('input').val(ui.value);
		        $(this).parent().find('label').text('('+ui.value+')');
		      }
		});
	});
	
	$('.riskCollapse').click(function(e){
		e.preventDefault();
		
		var button = $(this).find('.ui-icon');
		if (button.hasClass('ui-icon-plus'))
			button.removeClass('ui-icon-plus').addClass('ui-icon-minus');
		else
			button.removeClass('ui-icon-minus').addClass('ui-icon-plus');

		$(this).parent().parent().parent().find('textarea').toggle('slow');
	});
	
	$('.riskComment').change(function(){
		if ($.trim($(this).val()) == '')
			$(this).parent().parent().find('.icon-script').addClass('hide');
		else
			$(this).parent().parent().find('.icon-script').removeClass('hide');
	});
});