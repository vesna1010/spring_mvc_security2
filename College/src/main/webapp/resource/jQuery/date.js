//show date picker
$(document).ready(function(){
		$(".datepicker").datepicker({
			dateFormat:'dd-mm-yy',
			changeMonth:true,
			changeYear:true,
			yearRange: "1900:"+new Date().getFullYear()
		});
	
		$(".datepicker").unbind();	
	$(".showDate").click(function(){
		$(this).prev(".datepicker").datepicker('show');
	});
	
		
});