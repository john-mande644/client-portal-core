<%@ page import="com.owd.web.warehouse.asn.ASNHome"%>
<style type="text/css">
			@import url(/webapps/styles.css);
			body {
				background-color:	white;
				color:				black;
			}

		</style>

<script language="JavaScript" type="text/javascript">
		<!--
          // Make getElementByID work on old IE browsers
          // http://www.metalusions.com/backstage/articles/8/

          var bShiftDown = false;
          var bUserTyped = false;

          function Array_push()
          {
          	var A_p = 0
          	for (A_p = 0; A_p < arguments.length; A_p++)
          	{
          		this[this.length] = arguments[A_p]
             	}
          	return this.length
          }

          if (typeof Array.prototype.push == "undefined")
          {
          	Array.prototype.push = Array_push
          }


          if(document.all && !document.getElementById)
          {
          	document.getElementById = function(id)
          		{ return document.all[id]; }
          }

          //This function taken from adam55 at tekTips.com
          function getAbsolutePosition(elem)
          {
            	var r = { x: elem.offsetLeft, y: elem.offsetTop };
            	if (elem.offsetParent)
            	{
              		var tmp = getAbsolutePosition(elem.offsetParent);
              		r.x += tmp.x;
              		r.y += tmp.y;
            	}
            	return r;
          }

          function giveFocus ()
          {
          	if (document.all) // IE only
          		formWithProject.sEvent.focus();
          }

          var nTotalLines = 0;
          var prevLength = 0;

          function adjustRows (textarea)
          {
          	bUserTyped = true;
          	if (document.all) // IE only
          	{
          		var bGrew = false;
          		while ((textarea.scrollHeight > textarea.clientHeight) && (textarea.rows < 30))
          		{
          			textarea.rows += 3;
          			bGrew = true;
          		}
          		if ( bGrew )
          			textarea.scrollTop = 0;

          	}
          	else
          	{
          		var rgLines = textarea.value.split('\n');

          		var nRows = 1;
          		for ( var i=0; i<rgLines.length; i++ )
          		{
          			if ( rgLines[i].length > 80 )
          				nRows += Math.ceil(rgLines[i].length/80);
          		}
          		nRows += rgLines.length;
          		if ( nRows > 8 )
          			textarea.rows = Math.min(nRows, 30);
          	}
          }

          function getKeyCode(keyStroke)
          {
          	if (document.all)
          		return event.keyCode;
          	else
          		return keyStroke.which;
          }

          if(document.all && !document.getElementById)
          {
          	document.getParent  = function(el)
          		{ return el.parentElement; }
          } else {
          	document.getParent  = function(el)
          		{ return el.parentNode; }
          }

          function trim(s) {
          	while (s.substring(0,1) == ' ' || s.substring(0,1) == '\n' || s.substring(0,1) == '\r' || s.substring(0,1) == '\t') {
          		s = s.substring(1,s.length);
          	}
          	while (s.substring(s.length-1,s.length) == ' ') {
          		s = s.substring(0,s.length-1);
          	}
          	return s;
          }

          function bodyLoad()
          {
          	setFocusToWindow();
          	for(var i=0; i < rgHelpTips.length; i++)
          	{
          		if ( rgHelpTips[i] != '' && document.getElementById(rgHelpTips[i]) != null )
          			document.getElementById(rgHelpTips[i]).style.display = 'none';
          	}


          	if (!document.all) { window.document.captureEvents(Event.KEYDOWN); }
          	if (!document.all) { window.document.captureEvents(Event.KEYUP); }

          	window.document.onkeydown = handleDocumentKeydown;
          	window.document.onkeyup = handleDocumentKeyup;
          }



          function handleDocumentKeydown(keyStroke)
          {
          		var keyCode = getKeyCode(keyStroke);
          		if (keyCode == 27) {theMgr.hideAllPopups(); return cancel(keyStroke); }
          		checkboxDown(null, keyCode); // for shift key on list page
          		return true;
          }

          function handleDocumentKeyup(keyStroke)
          {
          		var keyCode = getKeyCode(keyStroke);
          		checkboxUp(null, keyCode); // for shift key on list page
          		return true;
          }

          function cancel(e)
          {
          	if (document.all)
                 		event.returnValue = false;
          	else
                 		e.preventDefault();
                 	return false;
          };



          function showError( sErr )
          {
          	var el = document.getElementById( 'helpbox' );
          	if ( el )
          	{
          		el.innerHTML = "<font color='red'>" + sErr + "</font>";
          		if ( el.style.display = 'none' )
          			toggleVisible( el );
          	}
          }

          function showHelp( id )
          {
          	var el = document.getElementById( id + "_help" );
          	var help = document.getElementById( 'helpbox' );
          	if ( help != null && el != null )
          	{
          		help.innerHTML = el.innerHTML;
          		help.style.display = '';
          	}
          	else if ( help != null )
          		help.style.display = 'none';
          }

          function toggleVisible( el )
          {
          	el.style.display = (el.style.display == 'none' ? '' : 'none');
          }

          function checkboxDown( e, keycode )
          {
          	if (!e)	e = window.event;

          	var nKey;
          	if ( keycode >= 0 )
          	{
          		nKey = keycode;
          	}
          	else if (e.which >= 0)
          	{
          		nKey = e.which
          	}
          	else
          	{
          		nKey = e.keyCode
          	}
          	if (nKey == 16) bShiftDown = true;
          }

          function checkboxUp( e, keycode )
          {
          	if (!e) e = window.event;

          	var nKey;
          	if ( keycode >= 0 )
          	{
          		nKey = keycode;
          	}
          	else if (e.which >= 0)
          	{
          		nKey = e.which
          	}
          	else
          	{
          		nKey = e.keyCode
          	}
          	if (nKey == 16) bShiftDown = false;
          }





          function clickDoesNothing()
          {
          	return false;
          }





          var rgHelpTips = new Array();

          //A set of tabbed pages is like a book. Here, each page has a tab and a block. Each tab has a radio button.
          function Book(){
          	this.ids = new Array();
          	this.addPage = function(id){
          		this.ids.push(id);
          		return true;
          	}
          }

          function showTab(book, id){
          	//for each page, show or hide:
          	for(var i=0; i < book.ids.length; i++){
          		if(book.ids[i]==id){
          			//turn on:
          			document.getElementById("blk_" + book.ids[i]).style.display = '';
          			document.getElementById("tab_" + book.ids[i]).style.backgroundColor = '#f3f6f9';
          			document.getElementById("tab_" + book.ids[i]).style.color = '#000000';
          			document.getElementById("rdo_" + book.ids[i]).checked = true;
          		}
          		else{
          			//turn off:
          			document.getElementById("blk_" + book.ids[i]).style.display = 'none';
          			document.getElementById("tab_" + book.ids[i]).style.backgroundColor = '';
          			document.getElementById("tab_" + book.ids[i]).style.color = '#444444';
          			document.getElementById("rdo_" + book.ids[i]).checked = false;
          		}
          	}
          }


          function HoverTipMgr(nameInit, theDivIdInit) {
          	this.fadeIn = false;
          	this.instantFade = false;
          	this.contents = '';
          	this.theTip = null;
          	this.varName = nameInit;
          	this.divId = theDivIdInit;
          	this.delayFade = 950;
          	this.keepInMode = 3000;
          	this.xOffset = 10;
          	this.yOffset = 15;
          	this.num = 0;
          }

          HoverTipMgr.prototype.initTip = function() {
          	if (this.theTip == null) {
          		this.theTip = document.getElementById(this.divId);
          	}
          }

          HoverTipMgr.prototype.fadeInTip = function(target, theSpanId, event) {
          	target.title = '';
          	this.initTip();
          	if ( this.theTip != null )
          	{
          		++this.num;
          		if (this.num > 10000) this.num = 0;
          		this.contents = document.getElementById(theSpanId).innerHTML;
          		if (document.all) {
          			this.theTip.style.left = (event.clientX + document.documentElement.scrollLeft + document.body.scrollLeft + this.xOffset) + 'px';
          			this.theTip.style.top = (event.clientY + document.documentElement.scrollTop + document.body.scrollTop + this.yOffset) + 'px';
          		} else {
          			this.theTip.style.left = (event.pageX + this.xOffset) + 'px';
          			this.theTip.style.top = (event.pageY + this.yOffset) + 'px';
          		}
          		this.fadeIn = true;
          		if (this.instantFade) {
          			this.doFade(true, this.num)
          		} else {
          			setTimeout(this.varName +'.doFade(true, ' + this.num + ')', this.delayFade);
          		}
          	}
          }

          HoverTipMgr.prototype.fadeOutTip = function(target) {
          	this.initTip();
          	if ( this.theTip != null )
          	{
          		this.fadeIn = false;
          		this.theTip.className = 'hoverBugTipInvisible';
          		this.theTip.innerHTML = this.contents = '';
          		++this.num;
          		if (this.num > 10000) this.num = 0;
          		setTimeout(this.varName + '.doFade(false, ' + this.num + ')', this.keepInMode);
          	}
          }

          HoverTipMgr.prototype.doFade = function(fadeIn, exNum) {
          	if (fadeIn == this.fadeIn && exNum == this.num) {
          		if (this.fadeIn) {
          			this.theTip.className = 'hoverBugTip';
          			this.theTip.innerHTML = this.contents;
          			this.instantFade = true;
          		} else {
          			this.instantFade = false;
          		}
          	}
          }


          function PopupMgr() {
          	this.ids = new Array();
          	this.noHideIds = new Array();
          	this.add = function(id){
          		this.ids.push(id);
          		return true;
          	}
          	this.addNoHide = function(id){
          		this.noHideIds.push(id);
          		return true;
          	}
          }

          PopupMgr.prototype.isNoHideId = function(id)
          {
          	for (var i=0; i < theMgr.noHideIds.length; i++)
          		if (id == theMgr.noHideIds[i]) return true;
          	return false;
          }

          PopupMgr.prototype.showPopup = function(id, elPlacement, xOffset, yOffset)
          {
          	var nMaskWidth = window.innerWidth ? window.innerWidth : document.body.offsetWidth;
          	if (document.getElementById(id).style.display == 'none')
          	{
          		// hide all selects so we don't get bleedthrough
          		var rgEl = document.getElementsByTagName('select');
          		for ( var i = 0; i < rgEl.length; i ++ )
          		{
          			if ( rgEl[i].style.display != 'none' && !this.isNoHideId(rgEl[i].id) )
          			{
          				var pt = getAbsolutePosition(rgEl[i]);
          				var el = document.createElement('INPUT');
          				el.type = 'text';
          				el.id = 'input_' + i;
          				el.style.left = pt.x + 'px';
          				el.style.top = pt.y + 'px';
          				if (rgEl[i].selectedIndex >= 0)
          					el.value = ' ' + trim(rgEl[i].options[rgEl[i].selectedIndex].innerHTML);
          				else if (rgEl[i].options.length > 0)
          					el.value = ' ' + trim(rgEl[i].options[0].innerHTML);
          				el.className = rgEl[i].className + '_pseudo';
          				if ( rgEl[i].style.width.length )
          					el.style.width = rgEl[i].style.width;
          				if ( rgEl[i].style.height.length )
          					el.style.height = rgEl[i].style.height;
          				if ( rgEl[i].style.fontSize.length )
          					el.style.fontSize = rgEl[i].style.fontSize;
          				if ( rgEl[i].style.fontFamily.length )
          					el.style.fontFamily = rgEl[i].style.fontFamily;
          				if (el.style.width != '' )
          					el.style.width = (parseInt(el.style.width) - 6) + 'px';
          				rgEl[i].parentNode.insertBefore(el, rgEl[i]);
          				rgEl[i].style.display ='none';
          			}
          		}

          		if ( elPlacement != null )
          		{
          			var pt = getAbsolutePosition(elPlacement);
          			document.getElementById(id).style.top = (pt.y + yOffset) + 'px';
          			document.getElementById(id).style.left = (pt.x + xOffset) + 'px';
          		}

          		document.getElementById(id).style.display = '';
          		document.getElementById('menuDisappearingMask').style.display = '';
          		document.getElementById('menuDisappearingMask').style.height = "100%";
          		document.getElementById('menuDisappearingMask').width = nMaskWidth; //have to calculate at beg. of func. due to IE bug that makes part of appearing window not show
          	}
          	return false;
          }

          PopupMgr.prototype.hidePopup = function (id)
          {
          	if ( !document.getElementById(id) ) return;

          	if ( document.getElementById(id).style.display == '' )
          	{
          		// show all selects so we don't get bleedthrough
          		var rgEl = document.getElementsByTagName('select');
          		for ( var i = 0; i < rgEl.length; i ++ )
          		{
          			var el = document.getElementById('input_' + i);
          			if ( el )
          			{
          				var tmp = el.parentNode;
          				if ( tmp )
          					tmp.removeChild(el);
          			}
          			rgEl[i].style.display = '';
          		}

          		document.getElementById(id).style.display = 'none';
          		document.getElementById('menuDisappearingMask').style.display = 'none';
          	}
          	return false;
          }

          PopupMgr.prototype.hideAllPopups = function ()
          {
          	for(var i=0; i < theMgr.ids.length; i++)
          	{
          		theMgr.hidePopup(theMgr.ids[i]);
          	}
          	return false;
          }


          /**
           * Sets a Cookie with the given name and value.
           *
           * name       Name of the cookie
           * value      Value of the cookie
           */
          function setCookie(name, value)
          {
              document.cookie = name + "=" + escape(value) + "; expires=Thu, 4 Aug 2011 20:47:11 UTC;";
          }

          /**
           * Gets the value of the specified cookie.
           *
           * name  Name of the desired cookie.
           *
           * Returns a string containing value of specified cookie,
           *   or null if cookie does not exist.
           */
          function getCookie(name)
          {
              var dc = document.cookie;
              var prefix = name + "=";
              var begin = dc.indexOf("; " + prefix);
              if (begin == -1)
              {
                  begin = dc.indexOf(prefix);
                  if (begin != 0) return null;
              }
              else
              {
                  begin += 2;
              }
              var end = document.cookie.indexOf(";", begin);
              if (end == -1)
              {
                  end = dc.length;
              }
              return unescape(dc.substring(begin + prefix.length, end));
          }

          /**
           * Deletes the specified cookie.
           *
           * name      name of the cookie
           * [path]    path of the cookie (must be same as path used to create cookie)
           * [domain]  domain of the cookie (must be same as domain used to create cookie)
           */
          function deleteCookie(name)
          {
              if (getCookie(name))
              {
                  document.cookie = name + "=" +
                      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
              }
          }


          /**
           *
           * CALENDAR
           *
           */
          function Clock()
          {
          	this.txtID = '';
          	this.localeTime = GetLocaleTime();
          	this.dt = new Date();
          }
          Clock.prototype.show = function(id)
          {
          	this.txtID = id;
          	document.getElementById('timeTableSelect').size = 20;
          	theMgr.showPopup('tblTime', document.getElementById(id), -2, document.getElementById(id).offsetHeight - 2);
          }
          Clock.prototype.setTime = function(sTime)
          {
          	this.dt = new Date(sTime);
          }
          Clock.prototype.getPrintableTime = function()
          {
          	var sDate = this.localeTime;
          	var sHours = this.dt.getHours();
          	var sMinutes = this.dt.getMinutes() + '';
          	var ampm = '';

          	if ( sDate.indexOf("AM") != -1 ) // ampm
          	{
          		if ( this.dt.getHours() == 0 )
          			sHours = "12";
          		if ( this.dt.getHours() > 12 )
          			sHours = this.dt.getHours() - 12;

          		ampm = 'AM';
          		if ( this.dt.getHours() > 11 )
          			ampm = 'PM';
          	}

          	sDate = sDate.replace(/hh/, sHours);
          	sDate = sDate.replace(/mm/, sMinutes.length == 1 ? "0" + sMinutes : sMinutes);
          	sDate = sDate.replace(/AM/, ampm);
          	return sDate;
          }
          Clock.prototype.hide = function()
          {
          	document.getElementById(this.txtID).value = this.getPrintableTime();
          	theMgr.hideAllPopups();
          }
          Clock.prototype.guessTime = function( sTime )
          {
          	var dt = new Date();
          	dt.setSeconds(0);
          	dt.setMinutes(0);

          	var re = /^([012]?\d)[:\.]?([012345]\d)[:\.]?([012345]?\d?)$/;
          	var matches = re.exec(sTime);
          	if ( matches )
          	{
          		if ( matches.length > 1 )
          			dt.setHours( matches[1] );

          		if ( matches.length > 2 )
          			dt.setMinutes( matches[2] );

          		if ( matches.length > 3 )
          		{
          			if ( !isNaN(parseInt(matches[3])) )
          				dt.setSeconds( parseInt(matches[3]) );
          		}
          		return dt;
          	}

          	re = /^([12]?\d)[:\.]?(\d\d)?\s?([ap]m)$/i;
          	matches = re.exec(sTime);
          	if ( matches )
          	{
          		if ( matches[3].toLowerCase() == "pm" && parseInt(matches[1]) != 12)
          		{
          			var hrs = matches[1];
          			if ( hrs.substr(0,1) == "0" )
          				hrs = hrs.substr(1);
          			dt.setHours(parseInt(hrs) + 12);
          		}
          		else if ( matches[3].toLowerCase() == "am" && parseInt(matches[1]) == 12)
          			dt.setHours(0);
          		else
          			dt.setHours(matches[1]);
          		if ( !isNaN(parseInt(matches[2])) )
          			dt.setMinutes(matches[2]);
          		return dt;
          	}


          	sTime = trim(sTime.toLowerCase());
          	if ( sTime == "noon" )
          	{
          		dt.setHours(12);
          		return dt;
          	}
          	if ( sTime == "midnight" )
          	{
          		dt.setHours(0);
          		return dt;
          	}
          	if ( sTime == "now" )
          		return new Date();

          	var re = /^(\d+)$/;
          	var matches = re.exec(sTime);

          	var n = 0;
          	if ( matches != null && matches.length > 1 )
          	{
          		n = parseInt(matches[1]);
          		if ( n < 8 )
          			dt.setHours(n+12);
          		else
          			dt.setHours(n);
          		return dt;
          	}

          	dt = new Date();
          	if ( sTime.indexOf("minute") != -1 || sTime.indexOf("hour") != -1 )
          	{
          		re = /(\d+)/;
          		matches = re.exec(sTime);
          		if ( matches != null && matches.length > 1 )
          		{
          			n = parseInt(matches[1]);
          		} else {
          			for ( var i = 0; i < this.rgNumbers.length; i++ )
          			{
          				if ( sTime.indexOf(this.rgNumbers[i]) != -1 )
          					n = i;
          			}
          		}
          		if ( sTime.indexOf("minute") != -1 )
          			dt.setMinutes(dt.getMinutes() + n);
          		else
          			dt.setHours(dt.getHours() + n);
          		return dt;
          	}
          	dt.setHours(12);
          	dt.setMinutes(0);
          	dt.setSeconds(0);
          	return dt;
          }

          function Calendar()
          {
          	this.rgMonths = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
          	this.rgDays = GetWeekdays();
          	this.rgNumbers = ['zero','one','two','three','four','five','six','seven','eight','nine','ten','eleven','twelve','thirteen','fourteen','fifteen','sixteen','seventeen','eighteen','nineteen','twenty','twentyone','twentytwo','twentythree','twentyfour','twentyfive','twentysix','twentyseven','twentyeight','twentynine','thirty'];
          	this.iFirstDayOfWeek = 0;
          	this.dt = new Date();
          	this.dtShowing = new Date(this.dt);
          	this.layout = [[1,2,3,4,5,6,7],[8,9,10,11,12,13,14],[15,16,17,18,19,20,21],[22,23,24,25,26,27,28],[29,30,31,1,2,3,4],[5,6,7,8,9,10,11]];
          	this.bPastOK = false;
          	this.txtID = '';
          	this.txtTimeID = '';
          	this.localeDate = GetLocaleDate();
          }

          Calendar.prototype.drawMe = function() {
          	// Write names of days in week row
          	for(var i=0; i<this.rgDays.length; i++)
          		document.getElementById('weekday' + i.toString()).innerHTML = this.rgDays[(i + this.iFirstDayOfWeek)%(this.rgDays.length - 1)].toString().substr(0,1);

          	// find out which day of week the first of the month is
          	var dtFirst = new Date(this.dtShowing);
          	dtFirst.setDate(1);
          	dtFirst.setHours(0, 0, 1);

          	var iDaysToRollBack = 0;
          	if ( dtFirst.getDay() > this.iFirstDayOfWeek )
          		iDaysToRollBack = dtFirst.getDay() - this.iFirstDayOfWeek
          	else if ( dtFirst.getDay() < this.iFirstDayOfWeek )
          		iDaysToRollBack = dtFirst.getDay() + ( 6 - this.iFirstDayOfWeek ) + 1;
          	else
          		iDaysToRollBack = 7;

          	dtFirst.setDate( dtFirst.getDate() - iDaysToRollBack ); // roll back enough to start filling in table
          	var dtToday = new Date();
          	dtToday.setHours(0, 0, 0);

          	for( var i=0; i<6; i++)
          	{
          		for( var j=0; j<=6; j++ )
          		{
          			this.layout[i][j] = new Date(dtFirst);
          			var el = document.getElementById(i.toString() + ',' + j.toString());
          			el.innerHTML = this.layout[i][j].getDate();
          			if ( dtFirst.getMonth() == this.dtShowing.getMonth() )
          			{
          				if ( !this.bPastOK && dtFirst < dtToday )
          					el.style.color = '#777777';
          				else
          					el.style.color = '#000000';
          			}
          			else
          				el.style.color = '#777777';

          			if ( dtFirst.getYear() == dtToday.getYear() && dtFirst.getMonth() == dtToday.getMonth() && dtFirst.getDate() == dtToday.getDate() )
          				el.style.border = '1px solid black';
          			else
          				el.style.border = '';
          			if ( dtFirst.getYear() == this.dt.getYear() && dtFirst.getMonth() == this.dt.getMonth() && dtFirst.getDate() == this.dt.getDate() )
          				el.style.backgroundColor = '#cccccc';
          			else
          				el.style.backgroundColor = '#ffffff';


          			dtFirst.setDate( dtFirst.getDate() + 1 );
          		}
          	}

          	document.getElementById('MonthTitle').innerHTML = this.rgMonths[this.dtShowing.getMonth()] + "&nbsp;" + (this.dtShowing.getUTCFullYear());
          	return true;
          }
          Calendar.prototype.fwdMonth = function() { this.dtShowing.setMonth( this.dtShowing.getMonth() + 1 ); this.drawMe(); }
          Calendar.prototype.backMonth = function() { this.dtShowing.setMonth( this.dtShowing.getMonth() - 1 ); this.drawMe(); }
          Calendar.prototype.setDay = function(id)
          {
          	document.getElementById(id).blur();

          	var rg = id.split(',',2);
          	var dt = new Date(this.layout[rg[0]][rg[1]]);
          	dt.setHours(24, 59, 59);
          	var dtToday = new Date();
          	if ( !this.bPastOK && dt < dtToday )
          		return;

          	this.dt = new Date(this.layout[rg[0]][rg[1]]);
          	this.dtShowing = new Date(this.dt);
          	this.drawMe();
          	this.hide();
          }
          Calendar.prototype.show = function(id, timeID)
          {
          	this.txtID = id;
          	this.txtTimeID = timeID;

          	this.drawMe();
          	theMgr.showPopup('tblCalendar', document.getElementById(id), -2, document.getElementById(id).offsetHeight - 2);
          }
          Calendar.prototype.getPrintableDate = function()
          {
          	var sDate = this.localeDate;
          	sDate = sDate.replace(/dd/, this.dt.getDate());
          	sDate = sDate.replace(/mm/, this.dt.getMonth() + 1);
          	sDate = sDate.replace(/yyyy/, this.dt.getUTCFullYear());
          	return sDate;
          }
          Calendar.prototype.showTips = function()
          {
          	if (trim(document.getElementById('calTips').innerHTML) == "")
          		document.getElementById('calTips').innerHTML = GetCalendarTip();
          	else
          		document.getElementById('calTips').innerHTML = "";
          }
          Calendar.prototype.hide = function()
          {
          	document.getElementById(this.txtID).value = this.getPrintableDate();
          	document.getElementById('calTips').innerHTML = '';
          	theMgr.hideAllPopups();
          }
          Calendar.prototype.setDate = function( sDate )
          {
          	this.dt = new Date(sDate);
          	this.dtShowing = new Date(this.dt);
          }
          Calendar.prototype.guessDate = function( sDate )
          {

          	var dt = new Date(sDate);
          	var dtToday = new Date();

          	if ( !isNaN(dt.getDate()) )
          	{
          		if ( dt.getUTCFullYear() < 2000 )
          			dt.setUTCFullYear( dt.getUTCFullYear() + 100 );

          		if ( !this.bPastOK && dt < dtToday && !(dt.getYear() == dtToday.getYear() && dt.getMonth() == dtToday.getMonth() && dt.getDate() == dtToday.getDate() ))
          		{
          			dt = new Date(dtToday);
          			alert( 'The due date must in the future.  It has been reset to today.' );
          		}

          		return dt;
          	}

          	if ( sDate.match(/^\d?\d[\:\-\.\/]\d?\d[\:\-\.\/]?\d?\d?\d?\d?$/) )
          	{
          		// need to figure out which is month and which is day
          		var re = /^(\d?\d)[\:\-\.\/](\d?\d)[\:\-\.\/]?((\d\d)?\d\d)?$/;
          		var matches = re.exec(sDate);

          		if ( matches )
          		{
          			var theYear = dtToday.getUTCFullYear();
          			if ( matches.length > 2 )
          			{
          				if ( matches[3].length == 2 )
          					theYear = "20" + matches[3];
          				else
          					theYear = matches[3];
          			}

          			if ( this.localeDate.indexOf("mm") == 0 )
          			{
          				dt = new Date(theYear, parseInt(matches[1]) - 1, matches[2]);
          			}
          			else
          			{
          				dt = new Date(theYear, parseInt(matches[2]) - 1, matches[1]);
          			}

          			if ( dt < dtToday && !(dt.getYear() == dtToday.getYear() && dt.getMonth() == dtToday.getMonth() && dt.getDate() == dtToday.getDate() ))
          				dt.setUTCFullYear( dt.getUTCFullYear() + 1 );
          			if ( !isNaN(dt) )
          				return dt;
          		}
          	}

          	var dtGuess = new Date();
          	sDate = trim(sDate.toLowerCase());
          	if ( sDate == "today" )
          			return new Date();

          	if ( sDate == "tomorrow" )
          	{
          		dtGuess.setDate( dtGuess.getDate() + 1 );
          		return dtGuess;
          	}

          	if ( sDate == "day after tomorrow" || sDate == "the day after tomorrow" )
          	{
          		dtGuess.setDate( dtGuess.getDate() + 2 );
          		return dtGuess;
          	}

          	if ( sDate == "yesterday" )
          	{
          		dtGuess.setDate( dtGuess.getDate() - 1 );
          		return dtGuess;
          	}

          	for( var i=0; i < this.rgDays.length; i++ )
          	{
          		// look for first 3 letters of day name mon, tue, wed (but don't confuse mon with month
          		if ( sDate.indexOf(this.rgDays[i].toLowerCase().substr(0,3)) != -1 &&
          				sDate.indexOf("month") == -1 )
          		{
          			var iDayOfWeekToday = dtGuess.getDay();
          			if( i > iDayOfWeekToday )
          				dtGuess.setDate( dtGuess.getDate() + (i - iDayOfWeekToday) );
          			else if ( i < iDayOfWeekToday )
          				dtGuess.setDate( dtGuess.getDate() + (i + 1 + (6 - iDayOfWeekToday)) );
          			else if ( sDate.indexOf("next") != -1 )
          				dtGuess.setDate( dtGuess.getDate() + 7 );
          			return dtGuess;
          		}
          	}

          	for( var i=0; i < this.rgMonths.length; i++ )
          	{
          		var re = /(\d+)/;
          		var n = 0;
          		var matches = re.exec(sDate);

          		if ( matches && matches.length > 1 )
          			n = parseInt(matches[1]);

          		if ( sDate.indexOf(this.rgMonths[i].toLowerCase().substr(0,3)) != -1 )
          		{
          			if ( n != 0 ) // march 26 (in the future from today)
          			{
          				dtGuess.setMonth(i);
          				dtGuess.setDate(n);
          				if ( dtGuess < dtToday )
          					dtGuess.setUTCFullYear( dtGuess.getUTCFullYear() + 1 );
          			}
          			else
          			{
          				var iMonthToday = dtGuess.getMonth();
          				if( i > iMonthToday )
          					dtGuess.setMonth( dtGuess.getMonth() + (i - iMonthToday) );
          				else if ( i < iMonthToday )
          					dtGuess.setMonth( dtGuess.getMonth() + (i + 1 + (11 - iMonthToday)) );
          				else
          					dtGuess.setMonth( dtGuess.getMonth() + 12 );

          				dtGuess.setDate(1);
          			}
          			return dtGuess;
          		}
          	}

          	if ( sDate.indexOf("day") != -1
          		|| sDate.indexOf("week") != -1
          		|| sDate.indexOf("month") != -1
          		|| sDate.indexOf("year") != -1 )
          	{
          		var re = /(\d+)/;
          		var n = 0;
          		var matches = re.exec(sDate);

          		// no digits, assume "next"
          		if (matches == null)
          			n = 1;
          		else if ( matches.length > 1 )
          			n = parseInt(matches[1]);

          		if ( sDate.indexOf("day") != -1 )
          			dtGuess.setDate( dtGuess.getDate() + n );
          		else if ( sDate.indexOf("week") != -1 )
          			dtGuess.setDate( dtGuess.getDate() + (7 * n) );
          		else if ( sDate.indexOf("month") != -1 )
          			dtGuess.setMonth( dtGuess.getMonth() + n );
          		else if ( sDate.indexOf("year") != -1 )
          			dtGuess.setUTCFullYear( dtGuess.getUTCFullYear() + n );
          		return dtGuess;
          	}

          	dtGuess = new Date(sDate);
          	if ( isNaN(dtGuess.getDate()) )
          		return new Date();
          }


          // Pop the help window, or bring it to the front if it already exists
          function popHelpWin(e, url) {
          	var helpWin = window.open('help/' + url, 'fbHelp', 'width=800, height=600, scrollbars=yes, toolbar=yes, location=no, status=no, menubar=no, copyhistory=no, resizable=yes');
          	helpWin.focus();
          	return cancel(e);
          }


          // Close all popups, make the curson an hourglass, and don't cancel any events
          function doPopupClick() {
          	theMgr.hideAllPopups();
          	document.body.style.cursor='wait';
          	return true;
          }


        	//-->
		</script>

<script language="JavaScript" type="text/javascript">
		<!--
          	function setFocusToWindow()
			{
				var el = null;

				// don't scroll down if there is an error at the top
				if (el != null && document.getElementById("bugerror") == null && !bUserTyped)
				{
					el.focus();
					if (!document.selection)
					{
						if ( el.setSelectionRange )
							el.setSelectionRange(0,0);
					}
				}
			}

			function toggleReply( el )
			{
				if ( el.nextSibling.style.display == 'none' )
				{
					el.nextSibling.style.display='';
					el.firstChild.innerHTML='- hide quoted text -';
				}
				else
				{
					el.nextSibling.style.display='none';
					el.firstChild.innerHTML='- show quoted text -';
				}
			}

			function GetLocaleDate() { return 'mm/dd/yyyy'; }
			function GetLocaleTime() { return 'hh:mm AM'; }
			function GetWeekdays()   { return new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'); }
			function GetCalendarTip(){ return "Enter a date as mm/dd/yyyy. You can also enter English phrases like 'today', 'tommorow', 'in 3 days', 'in 2 months', 'next Tuesday', etc. "; }

			// globals
			var theMgr = new PopupMgr();
			var theHoverTipMgr = new HoverTipMgr('theHoverTipMgr', 'theHoverTip');
			var cal = new Calendar();
			var clo = new Clock();

			theMgr.add('tblCalendar');
			theMgr.add('tblTime');
        	//-->
		</script>
	<img src="/webapps/images/test/spacer.gif"
			 height="0"
			 width="0"
			 id="menuDisappearingMask"
			 style="display:none;position:absolute;z-index:1"
			 alt=""
			 onclick="javascript:return theMgr.hideAllPopups();" />

		<table width="100%" cellpadding="0" cellspacing="0" border="0">

	<tr>
		<td class="navBar1" align="left">
			<a href="default.asp"
			   class="image"
			   title="FogBugz home page"><img src="/webapps/images/test/BugzLogo.gif"
											  width="186"
											  height="28"
											  alt="FogBugz"
											  border="0"
											  /></a></td>

		<td class="navBar1" align="right" style="padding-right:10px;">



					<!-- <a href="default.asp?pg=pgSearch" title="Enter a search term or case number">Search:</a> -->

					<form action="default.asp" class="noBorder" id="frmSearch">
						<input type="hidden" name="pre" value="preMultiSearch" />
						<input type="hidden" name="pg" value="pgList" />
						<input type="hidden" name="pgBack" value="pgSearch" />

							<input type="hidden" name="search" value="2" />


						<input id="searchFor"
							name="searchFor"
							size="16"
							class="navbarEdit"
							style="width:8em; font-weight:bold; height: 15px; border: solid 1px #1B6B8F; background: #fff; padding-left: 3px; padding-right: 3px;"
							autocomplete="off"
							/>

						<input type="image"
								border="0"
								src="navbar1-search.gif"
								alt="Search"
								height="17"
								width="45"
								align="middle"
								id="imgSearch"
								onmouseover="document.getElementById('imgSearch').src='/webapps/images/test/navbar1-search-hover.gif'"
								onmouseout="document.getElementById('imgSearch').src='/webapps/images/test/navbar1-search.gif'"
								/>

					</form>



			<a href="default.asp?pg=pgHelp"
				onclick="javascript: return theMgr.showPopup('helpPopup',this,-214,this.offsetHeight + 4);"
				title="Help"><img src="/webapps/images/test/navbar1-help.gif"
				                  border="0"
				                  alt="Help"
				                  height="17"
				                  width="17"
				                  align="middle"
				                  id="imgHelp"
				                  onmouseover="imgHelp.src='/webapps/images/test/navbar1-help-hover.gif'"
				                  onmouseout="imgHelp.src='/webapps/images/test/navbar1-help.gif'"
				                  /></a>

		<span id="helpPopup" class="popupMenu helpPopup" style="display:none;width:230px;">

		<div class="highlight">

			<a href="help/index.html" onclick="popHelpWin(event, 'index.html'); return theMgr.hideAllPopups();">FogBugz Help</a>
			<a href="http://www.fogcreek.com">Fog Creek Software on the Web</a>
			<a href="http://support.fogcreek.com/?fogbugz">OWD User Discussion Group</a>

		</div>

		<div>

			<a href="default.asp?pg=pgComposeAdminEmail"
				title="Email the Local FogBugz Administrator">Email Your Account Manager</a>
			<a href="default.asp?pg=pgComposeAdminEmail"
				title="Email OWD Technical Support Desk">Email Technical Support</a>

		</div>

		<div class="highlight">

			<a href="default.asp?pg=pgAbout">About One World</a>

		</div>

	</span> <!-- end helpPopup -->
	<script language="JavaScript" type="text/javascript">
		theMgr.add('helpPopup');
	</script>


		</td>
	</tr>

	<tr>
		<td align="left" valign="middle" class="navBar2">
			<span style="color:#5E8A9D; padding-left:10px;">User:&nbsp;</span>
			<span>Stewart&nbsp;Buskirk</span>
		</td>

		<td class="navBar2" valign="middle" align="right" style="padding-right:10px;">



<A HREF="./edit"><B>Home</B></A>
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=asn-search"><B>Inventory</B></A>


<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=create"><B>Orders</B></A>
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=create"><B>Customers</B></A>
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=create"><B>Contacts</B></A>
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=create"><B>Invoices</B></A>
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=create"><B>Configuration</B></A>
<A HREF="./edit?<%= ASNHome.kParamAdminAction%>=create"><B>Profile</B></A>


							<a href="default.asp?pg=pgFilter"
								onclick="javascript:return theMgr.showPopup('filterPopup', this, 0, this.offsetHeight + 2);" title="Saved Filters">Filters</a><span class="nonCssBrowsers">:</span>&nbsp;


		<span id="filterPopup" class="popupMenu filterPopup" style="display:none;width:180px;">

		<div class="highlight">


			<a href="#" onclick="toggleVisible(document.getElementById('idSaveCurrentFilter'));">Save current filter as...</a>
			<div id="idSaveCurrentFilter" style="display:none;">
			<form method="get" action="default.asp" class="noBorder">
				<input type="hidden" name="pg" value="pgList" />
				<input type="hidden" name="pre" value="preSaveCurrentFilterAs" />
				<input type="text"
					   style="margin-left:20px; width:100px;"
					   maxlength="40"
					   name="sFilterName"
					   class="navbarEdit"
					   value="IT Work Orders" />
					<button class="navbarButton" onclick="this.form.submit(); return doPopupClick();">OK</button>
			</form>
			</div>

			<a href="default.asp?pg=pgManageFilters" onclick="return doPopupClick()">Manage saved filters...</a>

		</div>

		<div>
			<a href="default.asp?pg=pgList&amp;pre=preSaveFilterEZ&amp;ixPersonAssignedTo=2" onclick="return doPopupClick();">My Cases</a>

			</div><div class="highlight">
				<a href="default.asp?pg=pgList&amp;pre=preLoadFilter&amp;ixFilter=18" onclick="return doPopupClick();">IT Work Orders</a>
				</div><div>
				<a href="default.asp?pg=pgList&amp;pre=preLoadFilter&amp;ixFilter=8" onclick="return doPopupClick();">Active Cases</a>

				<a href="default.asp?pg=pgList&amp;pre=preLoadFilter&amp;ixFilter=3" onclick="return doPopupClick();">All Cases</a>

				<a href="default.asp?pg=pgList&amp;pre=preLoadFilter&amp;ixFilter=5" onclick="return doPopupClick();">All Open Cases</a>

				<a href="default.asp?pg=pgList&amp;pre=preLoadFilter&amp;ixFilter=2" onclick="return doPopupClick();">Closed Cases</a>

				<a href="default.asp?pg=pgList&amp;pre=preLoadFilter&amp;ixFilter=19" onclick="return doPopupClick();">Last 10 Cases Opened</a>

				<a href="default.asp?pg=pgList&amp;pre=preLoadFilter&amp;ixFilter=7" onclick="return doPopupClick();">Latest Cases</a>

				<a href="default.asp?pg=pgList&amp;pre=preLoadFilter&amp;ixFilter=6" onclick="return doPopupClick();">Triage</a>

		</div>

		<div class="highlight">

			<a href="default.asp?pg=pgFilter" onclick="return doPopupClick();">Customize...</a>

		</div>

	</span> <!-- end filterPopup -->
	<script language="JavaScript" type="text/javascript">
		theMgr.add('filterPopup');
	</script>



			<span class="nonCssBrowsers"><br /></span>
		</td>
	</tr>


    </table>

<TABLE border=0 cellpadding=5 cellspacing=5 WIDTH=100%><TR><TD width=100%>
<B>	<p class="headline">ASN Management</p></TD><TD ALIGN=RIGHT NOWRAP>
 <%
	 String error = (String) request.getAttribute("errormessage");
 %>

</td></tr></table>
<HR>

<%= (error!= null?"<font color=red><B>"+error+"</b></font><HR>":"")%>
