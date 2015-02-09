$(function() {
	
	$('.goto-admin').on('click',function(){
		location = "/admin";
	});
	
	// show next part of the post
	$('a.btn-plus2').on('click',function(){
		$('.part2',$(this).closest('.post-preview')).fadeIn().css("display","inline");
		$(this).parent().hide();
	})
	
	// another read more
	$('a.btn-plus').on('click',function(){
		$('.part2',$(this).closest('.alert')).fadeIn();
		$(this).closest('.read-more').remove();
	})
	
	// ===============================
	// ADMIN
	// ===============================
	var d = new Date();
	var day = d.getUTCDate();
	var month = d.getUTCMonth() + 1; //months from 1-12
	var year = d.getUTCFullYear();
	$("form.newpostForm input.pubdate").val(day + " " + month + " " + year);
	
	// ==== NEW POST ====
    $("form.newpostForm").bind('submit',function(event) {
    	event.preventDefault();
    	var form = $(this);
    	var title = $("input[name='title']",form).val();
    	var body = $("textarea[name='body']",form).val();
    	var bodyhtml = markdown.toHTML(body);
    	var pubdate = $("input[name='pubdate']",form).val();
    	var isonline = $("input[name='isonline']",form).is(':checked');
    	
        jsRoutes.controllers.Admin.addPost().ajax({
            context: this,
			data:{
				title:title,
				body:body,
				bodyhtml:bodyhtml,
				pubdate:pubdate,
				isonline:isonline
			},
            success:function(data, textStatus, jqXHR) {
            	alert("done !");
            	location.reload();
            },
            error:function() {
            	alert("echec...");
            }
        });
    });

    $("form.updatepostForm").bind('submit',function(event) {
    	event.preventDefault();
    	var form = $(this);
    	var postId = form.attr('data-postid');
    	var title = $("input[name='title']",form).val();
    	var body = $("textarea[name='body']",form).val();
    	var bodyhtml = markdown.toHTML(body);
    	var pubdate = $("input[name='pubdate']",form).val();
    	var isonline = $("input[name='isonline']",form).is(':checked');
    	
        jsRoutes.controllers.Admin.updatePost(postId).ajax({
            context: this,
			data:{
				title:title,
				body:body,
				bodyhtml:bodyhtml,
				pubdate:pubdate,
				isonline:isonline
			},
            success:function(data, textStatus, jqXHR) {
            	alert("done !");
            },
            error:function() {
            	alert("echec...");
            }
        });
    });
    
    
    $('.btn-inactive').on('click',function(){
    	var active = $(this).hasClass('btn-inactive') ;
    	$(this).toggleClass('btn-inactive');
    	if ( active ){
    		var bodyHTML = markdown.toHTML( $('.newpost-body').val() );
    		$('.newpost-preview').html(bodyHTML);
    		$('.newpost-body').hide();
    		$('.newpost-preview-group').show();
    		$(this).html("Edit");
    	} else {
    		$('.newpost-preview-group').hide();
    		$('.newpost-body').show();
    		$(this).html("Preview");
    	}
    });
    
	
});