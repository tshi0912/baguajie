/* web context variable setup */
var web_context = '/baguajie';


/* init for jquery.pnotify */
$(function(){
	if(op != undefined){
		op.get_signin_ts();
	}
	
	if($.pnotify != undefined){
		$.pnotify.defaults.pnotify_width = '200px';
		$.pnotify.defaults.pnotify_delay = 5000;
		$.pnotify.defaults.pnotify_sticker = false;
		$.pnotify.defaults.pnotify_history = false;
		$.pnotify.defaults.pnotify_addclass = 'baguajie';
	}
});
