$(function() {
	
	// show next part of the post
	$('.post-teaser .read-more a').on('click',function(){
		$('.part2',$(this).closest('p')).fadeIn();
		$(this).parent().hide();
	})
	
	// admin
	var d = new Date();
	var day = d.getUTCDate();
	var month = d.getUTCMonth() + 1; //months from 1-12
	var year = d.getUTCFullYear();
	$("form.newpostForm input.pubdate").val(day + " " + month + " " + year);
	
    $("form.newpostForm").bind('submit',function(event) {
    	event.preventDefault();
    	var form = $(this);
    	var title = $("input[name='title']",form).val();
    	var body = $("textarea[name='body']",form).val();
    	var pubdate = $("input[name='pubdate']",form).val();
    	var isonline = $("input[name='isonline']",form).is(':checked');
    	
        jsRoutes.controllers.Admin.addPost().ajax({
            context: this,
			data:{
				title:title,
				body:body,
				pubdate:pubdate,
				isonline:isonline
			},
            success:function(data, textStatus, jqXHR) {
            	alert("ok");
            },
            error:function() {
            	alert("ko");
            }
        });
    });
	
});