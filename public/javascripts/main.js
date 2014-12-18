$(function() {
	
	// show next part of the post
	$('.post-teaser .read-more a').on('click',function(){
		$('.part2',$(this).closest('p')).fadeIn();
		$(this).parent().hide();
	})
	
});