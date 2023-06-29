limit_multiple_users = true
sequence = "9x88_4"



//  QuickMenu Pro, Copyright (c) 1998 - 2003, OpenCube Inc. - http://www.opencube.com


/*-------------------------------------------
Colors, Borders, Dividers, and more...
--------------------------------------------*/


	dqm__sub_menu_width = 200      		//default sub menu widths
	dqm__sub_xy = "0,0"            		//default sub x,y coordinates - defined relative
										//to the top-left corner of parent image or sub menu
   

	dqm__urltarget = "_self"			//default URL target: _self, _parent, _new, or "my frame name"

	dqm__border_width = 1
	dqm__divider_height = 0

	dqm__border_color = "transparent"		//Hex color or 'transparent'
	dqm__menu_bgcolor = "#FD3F36"		//Hex color or 'transparent'
	dqm__hl_bgcolor = "#FD3F36"		

	dqm__mouse_off_delay = 100			//defined in milliseconds (activated after mouse stops)
	dqm__nn4_mouse_off_delay = 100		//defined in milliseconds (activated after leaving sub)


/*-------------------------------------------
Font settings and margins
--------------------------------------------*/
   

    //Font settings

	dqm__textcolor = "#ffffff"
	dqm__fontfamily = "Verdana"		//Any available system font     
	dqm__fontsize = 10				//Defined with pixel sizing  	
	dqm__fontsize_ie4 = 9			//Defined with point sizing
	dqm__textdecoration = "normal"	//set to: 'normal', or 'underline'
	dqm__fontweight = "bold"		//set to: 'normal', or 'bold'
	dqm__fontstyle = "normal"		//set to: 'normal', or 'italic' 	


    //Rollover font settings

	dqm__hl_textcolor = "#ffffff"
	dqm__hl_textdecoration = "underline"	//set to: 'normal', or 'underline'



    //Margins and text alignment

	dqm__text_alignment = "left"		//set to: 'left', 'center' or 'right'
	dqm__margin_top = 4
	dqm__margin_bottom = 4
	dqm__margin_left = 4
	dqm__margin_right = 2

   


/*-------------------------------------------
Bullet and Icon image library - Unlimited bullet
or icon images may be defined below and then associated
with any sub menu items within the 'Sub Menu Structure 
and Text' section of this data file.
--------------------------------------------*/


    //Relative positioned icon images (flow with sub item text)

	//dqm__icon_image0 = "navigation_main/bullet_1.gif"
	//dqm__icon_rollover0 = "navigation_main/bullet_2.gif"
	//dqm__icon_image_wh0 = "13,8"

	

    //Absolute positioned icon images (coordinate poitioned)

	dqm__2nd_icon_image0 = "images/lifestyles/arrow_1.gif"
	dqm__2nd_icon_rollover0 = "images/lifestyles/arrow_2.gif"
	dqm__2nd_icon_image_wh0 = "11,11"
	dqm__2nd_icon_image_xy0 = "0,3"



/*---------------------------------------------
Optional Status Bar Text
-----------------------------------------------*/

	dqm__show_urls_statusbar = false
   
	//dqm__status_text0 = "Sample text - Main Menu Item 0"
	//dqm__status_text1 = "Sample text - Main Menu Item 1"

	//dqm__status_text1_0 = "Sample text - Main Menu Item 1, Sub Item 0"	
	//dqm__status_text1_0 = "Sample text - Main Menu Item 1, Sub Item 1"	




/*-------------------------------------------
Internet Explorer Transition Effects
--------------------------------------------*/


    //Options include - none | fade | pixelate |iris | slide | gradientwipe | checkerboard | radialwipe | randombars | randomdissolve |stretch

	dqm__sub_menu_effect = "fade"
	dqm__sub_item_effect = "none"


    //Define the effect duration in seconds below.
   
	dqm__sub_menu_effect_duration = .2
	dqm__sub_item_effect_duration = .2


    //Specific settings for various transitions.

	dqm__effect_pixelate_maxsqare = 25
	dqm__effect_iris_irisstyle = "CIRCLE"		//CROSS, CIRCLE, PLUS, SQUARE, or STAR
	dqm__effect_checkerboard_squaresx = 14
	dqm__effect_checkerboard_squaresY = 14
	dqm__effect_checkerboard_direction = "left"	//UP, DOWN, LEFT, RIGHT


    //Opacity and drop shadows.

	dqm__sub_menu_opacity = 100			//1 to 100
	dqm__dropshadow_color = "ffffff"		//Hex color value or 'none'
	dqm__dropshadow_offx = 0			//drop shadow width
	dqm__dropshadow_offy = 0			//drop shadow height



/*-------------------------------------------
Browser Bug fixes and Workarounds
--------------------------------------------*/


    //Mac offset fixes, adjust until sub menus position correctly.
   
	dqm__os9_ie5mac_offset_X = 0
	dqm__os9_ie5mac_offset_Y = 0

	dqm__osx_ie5mac_offset_X = 0
	dqm__osx_ie5mac_offset_Y = -3

	dqm__ie4mac_offset_X = -8
	dqm__ie4mac_offset_Y = -50


    //Netscape 4 resize bug workaround.

	dqm__nn4_reaload_after_resize = true
	dqm__nn4_resize_prompt_user = false
	dqm__nn4_resize_prompt_message = "To reinitialize the navigation_main_main menu please click the 'Reload' button."
   

    //Set to true if the menu is the only item on the HTML page.

	dqm__use_opera_div_detect_fix = false


    //Pre-defined sub menu item heights for the Espial Escape browser.

	dqm__escape_item_height = 20
	dqm__escape_item_height0_0 = 70
	dqm__escape_item_height0_1 = 70


/*---------------------------------------------
Exposed menu events
----------------------------------------------*/


    //Reference additional onload statements here.

	//dqm__onload_code = "alert('custom function - onload')"


    //The 'X' indicates the index number of the sub menu group or item.

	dqm__showmenu_codeX = "status = 'custom show menu function call - menu0'"
	dqm__hidemenu_codeX = "status = 'custom hide menu function call - menu0'"
	dqm__clickitem_codeX_X = "alert('custom Function - Menu Item 0_0')"



/*---------------------------------------------
Specific Sub Menu Settings
----------------------------------------------*/


    //The following settings may be defined for specific sub menu groups.
    //The 'X' represents the index number of the sub menu group.

	dqm__border_widthX = 10;
	dqm__divider_heightX = 5;		
	dqm__border_colorX = "#0000ff";     
	dqm__menu_bgcolorX = "#ff0000"
	dqm__hl_bgcolorX = "#00ff00"
	dqm__hl_textcolorX = "#ff0000"
	dqm__text_alignmentX = "left"


    //The following settings may be defined for specific sub menu items.
    //The 'X' represents the index number of the sub menu item.

	dqm__hl_subdescX = "custom highlight text"
	dqm__urltargetX = "_new"




/**********************************************************************************************
**********************************************************************************************

                           Main Menu Rollover Images and Links  

**********************************************************************************************
**********************************************************************************************/



    //Main Menu Item 0
	dqm__rollover_image0 = "images/lifestyles/products_2.jpg"
	dqm__rollover_wh0 = "86,30"
	dqm__url0 = "";   


    //Main Menu Item 1
	dqm__rollover_image1 = "images/lifestyles/endorsments_2.jpg"
	dqm__rollover_wh1 = "86,30"
	dqm__url1 = "";


    //Main Menu Item 2
	dqm__rollover_image2 = "images/lifestyles/about_us_2.jpg"
	dqm__rollover_wh2 = "86,30"
	dqm__url2 = "";  


    //Main Menu Item 3
	dqm__rollover_image3 = "nimages/lifestyles/news_2.jpg"
	dqm__rollover_wh3 = "86,30"
	dqm__url3 = ""; 


    //Main Menu Item 4
	dqm__rollover_image4 = "images/lifestyles/where_2.jpg"
	dqm__rollover_wh4 = "86,30"
	dqm__url4 = "http://www.getxla.com/where.htm"; 
	
	
	//Main Menu Item 5
	dqm__rollover_image5 = "images/lifestyles/contact_2.jpg"
	dqm__rollover_wh5 = "86,30"
	dqm__url5 = "http://www.getxla.com/contact_us.htm"; 
	
	
	//Main Menu Item 6	
	dqm__rollover_image6 = "images/lifestyles/home_2.jpg"
	dqm__rollover_wh6 = "98,30"
	dqm__url6 = "http://www.getxla.com/index.htm"; 


/**********************************************************************************************
**********************************************************************************************

                              Sub Menu Structure and Text  

**********************************************************************************************
**********************************************************************************************/
   


//Sub Menu 0
	dqm__sub_xy0 = "-85,30"
	dqm__sub_menu_width0 = 115

	dqm__subdesc0_0 = "xLA Caffeine Free"
	dqm__subdesc0_1 = "xLA Sport"

	dqm__url0_0 = "http://www.getxla.com/xla.htm"
	dqm__url0_1 = "http://www.getxla.com/xla_sport.htm"

	
	
//Sub Menu 1
	dqm__sub_xy1 = "-85,30"
	dqm__sub_menu_width1 = 95

	dqm__subdesc1_0 = "Doctor's Letter"
	dqm__subdesc1_1 = "Celebrity Endorsements"
	dqm__subdesc1_2 = "Customer Testimonials"
	
	dqm__url1_0 = "http://www.getxla.com/doctors_letter.htm"
	dqm__url1_1 = "http://www.getxla.com/endorsments.shtml"
	dqm__url1_2 = "http://www.getxla.com/testimonials.shtml"
	
	
//Sub Menu 2
	dqm__sub_xy2 = "-85,30"
	dqm__sub_menu_width2 = 85

	dqm__subdesc2_0 = "About Us"
	dqm__subdesc2_1 = "Radio & Video"
	
	dqm__url2_0 = "http://www.getxla.com/about_us.htm"
	dqm__url2_1 = "http://www.getxla.com/videos.htm"

	
	
//Sub Menu 3
	dqm__sub_xy3 = "-85,30"
	dqm__sub_menu_width3 = 85

	dqm__subdesc3_0 = "xLA News"
	dqm__subdesc3_1 = "Discounts & Promotional Offers"
	
	dqm__url3_0 = "http://www.getxla.com/news.shtml"
	dqm__url3_1 = "http://www.getxla.com/discounts.shtml"
	
	
		


		