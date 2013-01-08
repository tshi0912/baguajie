/* ==========================================================
 * bootstrapx-popoverx.js
 * https://github.com/tshi0912/bootstrapx-popoverx
 * version: 1.0
 * ==========================================================
 *
 * Based on work from Twitter Bootstrap and 
 * from Popover library https://github.com/tshi0912/bootstrapx-popoverx
 * from the great guys at Twitter.
 *
 * Untested with 2.1.0 but should worked with 2.0.x
 *
 * ========================================================== */
!function($) {
  "use strict"

  /* class definition */
  var Popoverx = function ( element, options ) {
    // local init
    this.cinit('popoverx', element, options );
  }

  Popoverx.prototype = $.extend({}, $.fn.popover.Constructor.prototype, {

    constructor: Popoverx

    , cinit: function( type, element, options ) {
      this.attr = {};
      this.attr.fireOptions = ['click', 'hover'];
      this.attr.fireEvents = ['click', 'mouseenter'];

      if (!options) options = {};
      
      options.trigger = 'manual';
      this.attr.fireIdx = $.inArray(options.fire_on, this.attr.fireOptions);
      // invalid fire_on option, set it as 'click'
      if(this.attr.fireIdx==-1)
      {
    	  options.fire_on = 'click';
    	  this.attr.fireIdx = 0;
      }
      
      // choose random attrs instead of timestamp ones
      this.attr.me = ((Math.random() * 10) + "").replace(/\D/g, '');
      this.attr.click_event_ns = "click." + this.attr.me + " touchstart." + this.attr.me;

      // call parent
      this.init( type, element, options );

      // setup our own handlers
      this.$element.on( this.attr.fireEvents[this.attr.fireIdx], this.options.selector, $.proxy(this.clickery, this) );

      // soon add click hanlder to body to close this element
      // will need custom handler inside here
    }
    , clickery: function(e) {
      // clickery isn't only run by event handlers can be called by timeout or manually
      // only run our click handler and  
      // need to stop progration or body click handler would fire right away
      if (e) {
        e.preventDefault();
        e.stopPropagation();
      }
      
      // set popover's dim's
      this.options.width  && this.tip().find('.popover-inner').width(  this.options.width  );
      this.options.height && this.tip().find('.popover-inner').height( this.options.height );

      // set popover's tip 'id' for greater control of rendering or css rules
      this.options.tip_id     && this.tip().attr('id', this.options.tip_id );

      // add a custom class
      this.options.class_name && this.tip().addClass(this.options.class_name);

      // we could override this to provide show and hide hooks 
      this[ this.isShown() ? 'hide' : 'show' ]();

      // if shown add global click closer
      if ( this.isShown() ) {
        var that = this;

        // add closer
        switch(this.options.fire_on){
          // close on mouseleave from the target element.
          case 'hover':
            if ( typeof this.attr.hdc == "number" ) {
              clearTimeout(this.attr.hdc);
              delete this.attr.hdc;
            }
        	this.$element.on('mouseleave', function(e){
        	    if(that.options.hover_delay_close && that.options.hover_delay_close > 0){
        	      that.attr.hdc = setTimeout( function(){that.clickery();}, that.options.hover_delay_close );
        	    }else{
        	      that.clickery();
        	    }
            });
        	if(that.options.hover_delay_close && that.options.hover_delay_close > 0){
	        	// if mouse enter popover, prevent closing the popoverx
	        	this.$tip.on('mouseenter', function(e){
	        		if ( typeof that.attr.hdc == "number" ) {
	        	       clearTimeout(that.attr.hdc);
	        	       delete that.attr.hdc;
	        	    }
	        	});
	        	// if mouse leave popover, close popoverx
	        	this.$tip.on('mouseleave', function(e){
	        		that.clickery();
	        	});
        	}
        	break;
        }
        
        // close on global request, exclude clicks inside popoverx
        this.options.global_close &&
          $('body').on( this.attr.click_event_ns, function(e) {
            if ( !that.tip().has(e.target).length ) { that.clickery(); }
          });
        
        this.options.esc_close && $(document).bind('keyup.clickery', function(e) {
            if (e.keyCode == 27) { that.clickery(); }
            return;
        });

        // first check for others that might be open
        // wanted to use 'click' but might accidently trigger other custom click handlers
        // on popoverx elements 
        !this.options.allow_multiple &&
            $('[data-popoverx-open=1]').each( function() { 
                $(this).data('popoverx') && $(this).data('popoverx').clickery(); });

        // help us track elements w/ open popoverxs using html5
        this.$element.attr('data-popoverx-open', 1);

        // if element has close button then make that work, like to
        // add option close_selector
        this.tip().on('click', '[data-dismiss="popoverx"]', $.proxy(this.clickery, this));

        // trigger timeout hide
        if ( typeof this.attr.tid == "number" ) {
          clearTimeout(this.attr.tid);
          delete this.attr.tid;
        }
        if ( this.options.auto_close && this.options.auto_close > 0 ) {
          this.attr.tid = 
            setTimeout( $.proxy(this.clickery, this), this.options.auto_close );  
        }

        // provide callback hooks for post shown event
        typeof this.options.onShown == 'function' && this.options.onShown.call(this);
        this.$element.trigger('shown');
      }
      else {
        this.$element.removeAttr('data-popoverx-open');

        this.options.esc_close && $(document).unbind('keyup.clickery');
        
        if (this.options.fire_on == 'hover'){
        	this.$element.off('mouseleave');
        }

        $('body').off( this.attr.click_event_ns ); 

        if ( typeof this.attr.tid == "number" ) {
          clearTimeout(this.attr.tid);
          delete this.attr.tid;
        }
        
        if ( typeof this.attr.hdc == "number" ) {
          clearTimeout(this.attr.hdc);
          delete this.attr.hdc;
        }
        

		// provide some callback hooks
        typeof this.options.onHidden == 'function' && this.options.onHidden.call(this);
        this.$element.trigger('hidden');
      }
    }
    , show: function () {
      var $tip
          , inside
          , pos
          , actualWidth
          , actualHeight
          , placement
          , tp

      if (this.hasContent() && this.enabled) {
        $tip = this.tip()
        this.setContent()

        if (this.options.animation) {
            $tip.addClass('fade')
        }

        placement = typeof this.options.placement == 'function' ?
          this.options.placement.call(this, $tip[0], this.$element[0]) :
          this.options.placement

        inside = /in/.test(placement)

        $tip
          .remove()
          .css({ top: 0, left: 0, display: 'block' })
          .appendTo(inside ? this.$element : document.body)

        pos = this.getPosition(inside)

        actualWidth = $tip[0].offsetWidth
        actualHeight = $tip[0].offsetHeight

        switch (inside ? placement.split(' ')[1] : placement) {
          case 'bottom':
            tp = {top: pos.top + pos.height, left: pos.left + pos.width / 2 - actualWidth / 2}
            break
          case 'top':
            tp = {top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2}
            break
          case 'left':
            tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth}
            break
          case 'right':
            tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width}
            break
        }
        if(this.options.ensure_visiable){
          var bp = { w: $('body').outerWidth(), h:$('body').outerHeight()};
	      switch (inside ? placement.split(' ')[1] : placement) {
	        case 'bottom':
	        	if(tp.top+actualHeight > bp.h){
	          	  tp.top = pos.top - actualHeight;
	          	  placement = 'top';
	            }
	          break;
	        case 'top':
	        	if(tp.top-actualHeight < 0){
	        	  tp.top = pos.top + pos.height;
	        	  placement = 'bottom';
	        	}
	          break;
	        case 'left':
		        if(tp.left-actualWidth < 0){
		          tp.left = pos.left + pos.width;
		          placement = 'right';
		        }
	          break;
	        case 'right':
		        if(tp.left+actualWidth > bp.w){
		          tp.left = pos.left - actualWidth;
		          placement = 'left';
		        }
	          break;
	      }
        }
        $tip
          .css(tp)
          .addClass(placement)
          .addClass('in')
        }
    }
    , isShown: function() {
      return this.tip().hasClass('in');
    }
    , resetPosition: function() {
        var $tip
        , inside
        , pos
        , actualWidth
        , actualHeight
        , placement
        , tp

      if (this.hasContent() && this.enabled) {
        $tip = this.tip()

        placement = typeof this.options.placement == 'function' ?
          this.options.placement.call(this, $tip[0], this.$element[0]) :
          this.options.placement

        inside = /in/.test(placement)

        pos = this.getPosition(inside)

        actualWidth = $tip[0].offsetWidth
        actualHeight = $tip[0].offsetHeight

        switch (inside ? placement.split(' ')[1] : placement) {
          case 'bottom':
            tp = {top: pos.top + pos.height, left: pos.left + pos.width / 2 - actualWidth / 2}
            break
          case 'top':
            tp = {top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2}
            break
          case 'left':
            tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth}
            break
          case 'right':
            tp = {top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width}
            break
        }

        $tip.css(tp)
      }
    }
    , debughide: function() {
      var dt = new Date().toString();

      console.log(dt + ": popoverx hide");
      this.hide();
    }
  })

  /* plugin definition */
  /* stolen from bootstrap tooltip.js */
  $.fn.popoverx = function( option ) {
    return this.each(function() {
      var $this = $(this)
        , data = $this.data('popoverx')
        , options = typeof option == 'object' && option

      if (!data) $this.data('popoverx', (data = new Popoverx(this, options)))
      if (typeof option == 'string') data[option]()
    })
  }

  $.fn.popoverx.Constructor = Popoverx

  // these defaults are passed directly to parent classes
  $.fn.popoverx.defaults = $.extend({}, $.fn.popover.defaults, {
    trigger: 'manual',
    hover_delay_close: 0, /* it is used only when fired on 'hover', which will delay the popover close, 0 means no delay */
    fire_on: 'click', /* it's used to replace the function of trigger, currently the allowed option is click and hover */
    auto_close:   0, /* ms to auto close popoverx, 0 means none */
    global_close: 1, /* allow close when clicked away from popoverx */
    esc_close:    1, /* allow popoverx to close when esc key is pressed */
    onShown:  null,  /* function to be run once popoverx has been shown */
    onHidden: null,  /* function to be run once popoverx has been hidden */
    width:  null, /* number is px (don't add px), null or 0 - don't set anything */
    height: null, /* number is px (don't add px), null or 0 - don't set anything */
    tip_id: null,  /* id of popover container */
    class_name: 'popoverx', /* default class name in addition to other classes */
    allow_multiple: 0, /* enable to allow for multiple popoverxs to be open at the same time */
    ensure_visiable: 1 /* enable to make sure the popover inside the boundary of browser */
  })

}( window.jQuery );