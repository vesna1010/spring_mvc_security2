$(document).ready(function() {
	
	var active = $("[name='page']").val();
	
	$("ul.navbar-nav li").each(function() {
		if ($(this).attr("id") == active) {
			$(this).addClass("active");
		}
	});
	
});
