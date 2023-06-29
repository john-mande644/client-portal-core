<!--
/*
rollover.js
*/
//-------------------------------------------------------------

//Modified by CoffeeCup Software 
//This code is Copyright (c) 1998 CoffeeCup Software 
//All rights reserved. License is granted to a single user to 
//reuse this code on a personal or business Web Site. 


<!--
        if(document.images) {
                ccover = new Array(20);
                ccout = new Array(20);
                ccover[1]=new Image;
                ccout[1]=new Image;
                for(var n=2;n<=20;n++) {
                        ccover[n]=new Image;
                        ccout[n]=new Image;
                }
                for(var n=1;n<=20;n++) {
                        ccover[n].src="include/"+n+"b.gif";
                        ccout[n].src="include/"+n+".gif";
                }
        }
        function ccOn(i) {
                if(document.images) document.images["cc" + i].src=ccover[i].src;
        }
        function ccOff(i) {
                if(document.images) document.images["cc" + i].src=ccout[i].src;
        }


//-------------------------------------------------------------
//-->

