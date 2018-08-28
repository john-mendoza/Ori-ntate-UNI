/**
	* Template Name: Apex App
	* Version: 1.0
	* Template Scripts
	* Author: MarkUps
	* Author URI: http://www.markups.io/

	Custom JS


**/



(function( $ ){


	/* ----------------------------------------------------------- */
	/*  1. FULL OVERLYAY MENU
	/* ----------------------------------------------------------- */

   $('.mu-menu-btn').on('click', function(event) {
        event.preventDefault();
        $('.mu-menu-full-overlay').addClass('mu-menu-full-overlay-show');
    });

    // when click colose btn
    $('.mu-menu-close-btn').on('click', function(event) {
	    event.preventDefault();
		$('.mu-menu-full-overlay').removeClass('mu-menu-full-overlay-show');
    });

    // when click menu item overlay disappear
    $('.mu-menu a').on('click', function(event) {
        event.preventDefault();
        $('.mu-menu-full-overlay').removeClass('mu-menu-full-overlay-show');
    });

})( jQuery );
