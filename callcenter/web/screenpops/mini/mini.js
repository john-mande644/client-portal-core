var gMonths=["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
var gWeekDay=["S","M","T","W","T","F","S"];	

var gBegin=[1980,1,1];	
var gEnd=[2030,12,31];	
var gsOutOfRange="Sorry, you may not go beyond the designated range!";	
var guOutOfRange=null;	

var giFirstDOW=6;	

var gcCalBG="white";	
var guCalBG=null;	
var gcCalFrame="white";	
var gsInnerTable="border=0 cellpadding=0 cellspacing=0";	
var gsOuterTable="border=0 cellpadding=0 cellspacing=2";	 

var gbHideTop=false;	
var giDCStyle=0;	
var gsCalTitle="gMonths[gCurMonth[1]-1]+' '+gCurMonth[0]";	
var gbDCSeq=true;	
var gsYearInBox="i";	
var gsNavPrev="<A id='navPrev' href='javascript:void(0)' class='MonthNav' onmousedown='showPrevMon();return false;' onmouseup='stopShowMon();if(this.blur)this.blur()' onmouseout='stopShowMon();if(this.blur)this.blur()' onmouseover='return true;'>&laquo;</A>";	
var gsNavNext="<A id='navNext' href='javascript:void(0)' class='MonthNav' onmousedown='showNextMon();return false;' onmouseup='stopShowMon();if(this.blur)this.blur()' onmouseout='stopShowMon();if(this.blur)this.blur()' onmouseover='return true;'>&raquo;</A>";	

var gbHideBottom=false;	// true: hide the bottom section; false: show it with gsBottom.
var gsBottom="<A href='javascript:void(0)' class='BottomAnchor' onclick='if(this.blur)this.blur();if(!fSetDate(gToday[0],gToday[1],gToday[2]))alert(\"You cannot select today!\");return false;' onmouseover='return true;' >Today is "+gMonths[gToday[1]-1].substring(0,3)+" "+gToday[2]+", "+gToday[0]+"</A>";	

var giCellWidth=18;	
var giCellHeight=15;	
var giHeadHeight=15;	
var giWeekWidth=16;	
var giHeadTop=0;	
var giWeekTop=2;	

var gcCellBG="white";	
var gsCellHTML="";	
var guCellBGImg="";	
var gsAction=" ";	
var gsDays="dayNo";	

var giWeekCol=-1;	
var gsWeekHead="#";	
var gsWeeks="weekNo";	
var gcWorkday="#002C63";	
var gcSat="#002C63";	
var gcSatBG="#99ccff";	
var gcSun="#002C63";	
var gcSunBG="#99ccff";	

var gcOtherDay="gainsboro";	
var gcOtherDayBG=gcCellBG;	
var giShowOther=2;	

var gbFocus=false;	
var gcToggle="yellow";	

var gcFGToday="red";	 
var gcBGToday="yellow";	
var guTodayBGImg="";	
var giMarkToday=1+4+8; 
var gsTodayTip="Today";	

var gcFGSelected="white";	
var gcBGSelected="#446fa7";	
var guSelectedBGImg="";	
var giMarkSelected=1+4+8;	
var gsSelectedTip="";	

var gbBoldAgenda=false;	
var gbInvertBold=false;	
var gbShrink2fit=true;	
var gdSelect=[0,0,0];	
var giFreeDiv=1;	
var gAgendaMask=[-1,-1,-1,null,null,false,null];	
var giResizeDelay=KO3?150:50;	
var gbFlatBorder=false;	
var gbInvertBorder=false;	
var gbShareAgenda=false;	
var gsAgShared="gContainer._cxp_agenda";	
var gbCacheAgenda=false;	
var giShowInterval=250;	
