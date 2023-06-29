<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%
      System.out.println("finderorder.jsp\n");
    com.owd.extranet.servlet.OrderFinder finder = (com.owd.extranet.servlet.OrderFinder)request.getAttribute(com.owd.extranet.servlet.OrdersManager.kCurrentFinder);
      System.out.println("finder retrieveds\n");

    String clientIDStr = ExtranetServlet.getSessionString(request,ExtranetServlet.kExtranetClientID);
    int clientId=1;
    try{
        clientId = Integer.parseInt(clientIDStr);
    }   catch(Exception ex)
    {

    }
%>   
<FORM METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath %>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderExploreAction%>">
<INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
<INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kOrdersMgrName %>" />


Check the boxes for the fields you want to search on and enter the values to use. When you are done, click on the Find button below. The results displayed will be order records that match all the criteria you select. To show all orders, click the "Show All Orders" button.<P>
<HR>
 <TABLE width=100% border=0>
 <TR><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kRefSearchFlag %> VALUE=1 <%= (finder.isRefSearch()?"CHECKED":"") %>>&nbsp;Your&nbsp;Order&nbsp;Reference&nbsp;<SELECT NAME=<%= finder.kRefSearchType %>>
<OPTION VALUE=1 <%= (finder.getRefType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getRefType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kRefSearchValue %> VALUE="<%= (finder.isRefSearch()?finder.getRef():"") %>">
</TD><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kOWDRefSearchFlag %> VALUE=1 <%= (finder.isOWDRefSearch()?"CHECKED":"") %>>&nbsp;OWD&nbsp;Order&nbsp;Reference&nbsp;<SELECT NAME=<%= finder.kOWDRefSearchType %>>
<OPTION VALUE=1 <%= (finder.getOWDRefType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getOWDRefType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT SIZE="15" NAME=<%= finder.kOWDRefSearchValue %> VALUE="<%= (finder.isOWDRefSearch()?finder.getOWDRef():"") %>">
</TD></TR>
<TR><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kHoldSearchFlag %> VALUE=1 <%= (finder.isHoldSearch()?"CHECKED":"") %>>&nbsp;On Hold Status&nbsp;<SELECT NAME=<%= finder.kHoldSearchType %>>
<OPTION VALUE=1 <%= (finder.getHoldType()==1?"SELECTED":"") %>>On Hold
<OPTION VALUE=2 <%= (finder.getHoldType()==2?"SELECTED":"") %>>Not On Hold
</SELECT> 
</TD><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kTrackSearchFlag %> VALUE=1 <%= (finder.isTrackSearch()?"CHECKED":"") %>>&nbsp;Carrier Tracking Number&nbsp;
<SELECT NAME=<%= finder.kTrackSearchType %>>
<OPTION VALUE=1 <%= (finder.getTrackType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getTrackType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT SIZE="15"  NAME=<%= finder.kTrackSearchValue %> VALUE="<%= (finder.isTrackSearch()?finder.getTrack():"") %>">
</TD></TR>
<TR><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kShippedSearchFlag %> VALUE=1 <%= (finder.isShippedSearch()?"CHECKED":"") %>>&nbsp;Shipped Status&nbsp;<SELECT NAME=<%= finder.kShippedSearchType %>>
<OPTION VALUE=1 <%= (finder.getShippedType()==1?"SELECTED":"") %>>Shipped
<OPTION VALUE=2 <%= (finder.getShippedType()==2?"SELECTED":"") %>>Not Shipped
</SELECT>  
</TD><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kReceivedOnSearchFlag %> VALUE=1 <%= (finder.isReceivedOnSearch()?"CHECKED":"") %>>&nbsp;Created On&nbsp;
<SELECT NAME=<%= finder.kReceivedOnYearValue %>>
<OPTION VALUE=2000 <%= ("2000".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2000
<OPTION VALUE=2001 <%= ("2001".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2001
<OPTION VALUE=2002 <%= ("2002".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2002
<OPTION VALUE=2003 <%= ("2003".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2003
<OPTION VALUE=2004 <%= ("2004".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2004
<OPTION VALUE=2005 <%= ("2005".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2005
<OPTION VALUE=2006 <%= ("2006".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2006
<OPTION VALUE=2007 <%= ("2007".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2007
<OPTION VALUE=2008 <%= ("2008".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2008
<OPTION VALUE=2009 <%= ("2009".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2009
<OPTION VALUE=2010 <%= ("2010".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2010
<OPTION VALUE=2011 <%= ("2011".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2011
<OPTION VALUE=2012 <%= ("2012".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2012
    <OPTION VALUE=2013 <%= ("2013".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2013
    <OPTION VALUE=2014 <%= ("2014".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2014
    <OPTION VALUE=2015 <%= ("2015".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2015
    <OPTION VALUE=2016 <%= ("2016".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2016
    <OPTION VALUE=2017 <%= ("2017".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2017
    <OPTION VALUE=2018 <%= ("2018".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2018
    <OPTION VALUE=2019 <%= ("2019".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2019
    <OPTION VALUE=2020 <%= ("2020".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2020
    <OPTION VALUE=2021 <%= ("2021".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2021
    <OPTION VALUE=2022 <%= ("2022".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2022
    <OPTION VALUE=2023 <%= ("2023".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2023
</SELECT>
<SELECT NAME=<%= finder.kReceivedOnMonthValue %>>
<OPTION VALUE=1 <%= ("1".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>January
<OPTION VALUE=2 <%= ("2".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>February
<OPTION VALUE=3 <%= ("3".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>March
<OPTION VALUE=4 <%= ("4".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>April
<OPTION VALUE=5 <%= ("5".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>May
<OPTION VALUE=6 <%= ("6".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>June
<OPTION VALUE=7 <%= ("7".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>July
<OPTION VALUE=8 <%= ("8".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>August
<OPTION VALUE=9 <%= ("9".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>September
<OPTION VALUE=10 <%= ("10".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>October
<OPTION VALUE=11 <%= ("11".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>November
<OPTION VALUE=12 <%= ("12".equals(finder.getReceivedOnMonth())?"SELECTED":"") %>>December
</SELECT>   
<SELECT NAME=<%= finder.kReceivedOnDayValue %>> 
<OPTION VALUE=1 <%= ("1".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>1
<OPTION VALUE=2 <%= ("2".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>2
<OPTION VALUE=3 <%= ("3".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>3
<OPTION VALUE=4 <%= ("4".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>4
<OPTION VALUE=5 <%= ("5".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>5
<OPTION VALUE=6 <%= ("6".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>6
<OPTION VALUE=7 <%= ("7".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>7
<OPTION VALUE=8 <%= ("8".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>8
<OPTION VALUE=9 <%= ("9".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>9
<OPTION VALUE=10 <%= ("10".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>10
<OPTION VALUE=11 <%= ("11".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>11
<OPTION VALUE=12 <%= ("12".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>12
<OPTION VALUE=13 <%= ("13".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>13
<OPTION VALUE=14 <%= ("14".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>14
<OPTION VALUE=15 <%= ("15".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>15
<OPTION VALUE=16 <%= ("16".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>16
<OPTION VALUE=17 <%= ("17".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>17
<OPTION VALUE=18 <%= ("18".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>18
<OPTION VALUE=19 <%= ("19".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>19
<OPTION VALUE=20 <%= ("20".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>20
<OPTION VALUE=21 <%= ("21".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>21
<OPTION VALUE=22 <%= ("22".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>22
<OPTION VALUE=23 <%= ("23".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>23
<OPTION VALUE=24 <%= ("24".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>24
<OPTION VALUE=25 <%= ("25".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>25
<OPTION VALUE=26 <%= ("26".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>26
<OPTION VALUE=27 <%= ("27".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>27
<OPTION VALUE=28 <%= ("28".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>28
<OPTION VALUE=29 <%= ("29".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>29
<OPTION VALUE=30 <%= ("30".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>30
<OPTION VALUE=31 <%= ("31".equals(finder.getReceivedOnDay())?"SELECTED":"") %>>31
</SELECT>
</TD></TR>
<TR><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kBackorderSearchFlag %> VALUE=1 <%= (finder.isBackorderSearch()?"CHECKED":"") %>>&nbsp;Backorder Status&nbsp;<SELECT NAME=<%= finder.kBackorderSearchType %>>
<OPTION VALUE=1 <%= (finder.getBackorderType()==1?"SELECTED":"") %>>Backordered
<OPTION VALUE=2 <%= (finder.getBackorderType()==2?"SELECTED":"") %>>Not Backordered
</SELECT>
</TD><TD NOWRAP>&nbsp;&nbsp;...through
<SELECT NAME=<%= finder.kReceivedToYearValue %>>
<OPTION VALUE=2000 <%= ("2000".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2000
<OPTION VALUE=2001 <%= ("2001".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2001
<OPTION VALUE=2002 <%= ("2002".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2002
<OPTION VALUE=2003 <%= ("2003".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2003
<OPTION VALUE=2004 <%= ("2004".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2004
<OPTION VALUE=2005 <%= ("2005".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2005
<OPTION VALUE=2006 <%= ("2006".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2006
<OPTION VALUE=2007 <%= ("2007".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2007
<OPTION VALUE=2008 <%= ("2008".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2008
<OPTION VALUE=2009 <%= ("2009".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2009
<OPTION VALUE=2010 <%= ("2010".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2010
<OPTION VALUE=2011 <%= ("2011".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2011
<OPTION VALUE=2012 <%= ("2012".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2012
    <OPTION VALUE=2013 <%= ("2013".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2013

    <OPTION VALUE=2014 <%= ("2014".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2014
    <OPTION VALUE=2015 <%= ("2015".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2015
    <OPTION VALUE=2016 <%= ("2016".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2016
    <OPTION VALUE=2017 <%= ("2017".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2017
    <OPTION VALUE=2018 <%= ("2018".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2018
    <OPTION VALUE=2019 <%= ("2019".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2019
    <OPTION VALUE=2020 <%= ("2020".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2020
    <OPTION VALUE=2021 <%= ("2021".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2021
    <OPTION VALUE=2022 <%= ("2022".equals(finder.getReceivedToYear())?"SELECTED":"") %>>2022
    <OPTION VALUE=2023 <%= ("2023".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2023
</SELECT>
<SELECT NAME=<%= finder.kReceivedToMonthValue %>>
<OPTION VALUE=1 <%= ("1".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>January
<OPTION VALUE=2 <%= ("2".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>February
<OPTION VALUE=3 <%= ("3".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>March
<OPTION VALUE=4 <%= ("4".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>April
<OPTION VALUE=5 <%= ("5".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>May
<OPTION VALUE=6 <%= ("6".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>June
<OPTION VALUE=7 <%= ("7".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>July
<OPTION VALUE=8 <%= ("8".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>August
<OPTION VALUE=9 <%= ("9".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>September
<OPTION VALUE=10 <%= ("10".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>October
<OPTION VALUE=11 <%= ("11".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>November
<OPTION VALUE=12 <%= ("12".equals(finder.getReceivedToMonth())?"SELECTED":"") %>>December
</SELECT>
<SELECT NAME=<%= finder.kReceivedToDayValue %>>
<OPTION VALUE=1 <%= ("1".equals(finder.getReceivedToDay())?"SELECTED":"") %>>1
<OPTION VALUE=2 <%= ("2".equals(finder.getReceivedToDay())?"SELECTED":"") %>>2
<OPTION VALUE=3 <%= ("3".equals(finder.getReceivedToDay())?"SELECTED":"") %>>3
<OPTION VALUE=4 <%= ("4".equals(finder.getReceivedToDay())?"SELECTED":"") %>>4
<OPTION VALUE=5 <%= ("5".equals(finder.getReceivedToDay())?"SELECTED":"") %>>5
<OPTION VALUE=6 <%= ("6".equals(finder.getReceivedToDay())?"SELECTED":"") %>>6
<OPTION VALUE=7 <%= ("7".equals(finder.getReceivedToDay())?"SELECTED":"") %>>7
<OPTION VALUE=8 <%= ("8".equals(finder.getReceivedToDay())?"SELECTED":"") %>>8
<OPTION VALUE=9 <%= ("9".equals(finder.getReceivedToDay())?"SELECTED":"") %>>9
<OPTION VALUE=10 <%= ("10".equals(finder.getReceivedToDay())?"SELECTED":"") %>>10
<OPTION VALUE=11 <%= ("11".equals(finder.getReceivedToDay())?"SELECTED":"") %>>11
<OPTION VALUE=12 <%= ("12".equals(finder.getReceivedToDay())?"SELECTED":"") %>>12
<OPTION VALUE=13 <%= ("13".equals(finder.getReceivedToDay())?"SELECTED":"") %>>13
<OPTION VALUE=14 <%= ("14".equals(finder.getReceivedToDay())?"SELECTED":"") %>>14
<OPTION VALUE=15 <%= ("15".equals(finder.getReceivedToDay())?"SELECTED":"") %>>15
<OPTION VALUE=16 <%= ("16".equals(finder.getReceivedToDay())?"SELECTED":"") %>>16
<OPTION VALUE=17 <%= ("17".equals(finder.getReceivedToDay())?"SELECTED":"") %>>17
<OPTION VALUE=18 <%= ("18".equals(finder.getReceivedToDay())?"SELECTED":"") %>>18
<OPTION VALUE=19 <%= ("19".equals(finder.getReceivedToDay())?"SELECTED":"") %>>19
<OPTION VALUE=20 <%= ("20".equals(finder.getReceivedToDay())?"SELECTED":"") %>>20
<OPTION VALUE=21 <%= ("21".equals(finder.getReceivedToDay())?"SELECTED":"") %>>21
<OPTION VALUE=22 <%= ("22".equals(finder.getReceivedToDay())?"SELECTED":"") %>>22
<OPTION VALUE=23 <%= ("23".equals(finder.getReceivedToDay())?"SELECTED":"") %>>23
<OPTION VALUE=24 <%= ("24".equals(finder.getReceivedToDay())?"SELECTED":"") %>>24
<OPTION VALUE=25 <%= ("25".equals(finder.getReceivedToDay())?"SELECTED":"") %>>25
<OPTION VALUE=26 <%= ("26".equals(finder.getReceivedToDay())?"SELECTED":"") %>>26
<OPTION VALUE=27 <%= ("27".equals(finder.getReceivedToDay())?"SELECTED":"") %>>27
<OPTION VALUE=28 <%= ("28".equals(finder.getReceivedToDay())?"SELECTED":"") %>>28
<OPTION VALUE=29 <%= ("29".equals(finder.getReceivedToDay())?"SELECTED":"") %>>29
<OPTION VALUE=30 <%= ("30".equals(finder.getReceivedToDay())?"SELECTED":"") %>>30
<OPTION VALUE=31 <%= ("31".equals(finder.getReceivedToDay())?"SELECTED":"") %>>31
</SELECT>
</TD></TR>  


<TR>
    <TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kCustomerSearchFlag %> VALUE=1 <%= (finder.isCustomerSearch()?"CHECKED":"") %>>&nbsp;
<SELECT NAME="<%= finder.kNameSearchCriteria %>" >
<OPTION VALUE="<%= finder.kNameSearchBillName %>" <%= (finder.getNameSearchCriteria().equals(finder.kNameSearchBillLast)?"SELECTED":"") %>>Customer Name
<OPTION VALUE="<%= finder.kNameSearchBillLast %>" <%= (finder.getNameSearchCriteria().equals(finder.kNameSearchBillLast)?"SELECTED":"") %>>Customer Last Name
<OPTION VALUE="<%= finder.kNameSearchBillFirst %>" <%= (finder.getNameSearchCriteria().equals(finder.kNameSearchBillFirst)?"SELECTED":"") %>>Customer First Name
<OPTION VALUE="<%= finder.kNameSearchCompany %>" <%= (finder.getNameSearchCriteria().equals(finder.kNameSearchCompany)?"SELECTED":"") %>>Company Name
<OPTION VALUE="<%= finder.kNameSearchEmail %>" <%= (finder.getNameSearchCriteria().equals(finder.kNameSearchEmail)?"SELECTED":"") %>>Customer Email
<OPTION VALUE="<%= finder.kNameSearchState %>" <%= (finder.getNameSearchCriteria().equals(finder.kNameSearchState)?"SELECTED":"") %>>Customer State</SELECT>
&nbsp;<SELECT NAME=<%= finder.kCustomerSearchType %>>
<OPTION VALUE=1 <%= (finder.getCustomerType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getCustomerType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT SIZE="15"  NAME=<%= finder.kCustomerSearchValue %> VALUE="<%= (finder.isCustomerSearch()?finder.getCustomerName():"") %>">

            </TD>
    <TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kShippedOnSearchFlag %> VALUE=1 <%= (finder.isShippedOnSearch()?"CHECKED":"") %>>&nbsp;Shipped On&nbsp;
<SELECT NAME=<%= finder.kShippedOnYearValue %>>
<OPTION VALUE=2000 <%= ("2000".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2000
<OPTION VALUE=2001 <%= ("2001".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2001
<OPTION VALUE=2002 <%= ("2002".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2002
<OPTION VALUE=2003 <%= ("2003".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2003
<OPTION VALUE=2004 <%= ("2004".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2004
<OPTION VALUE=2005 <%= ("2005".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2005
<OPTION VALUE=2006 <%= ("2006".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2006
<OPTION VALUE=2007 <%= ("2007".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2007
<OPTION VALUE=2008 <%= ("2008".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2008
<OPTION VALUE=2009 <%= ("2009".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2009
<OPTION VALUE=2010 <%= ("2010".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2010
<OPTION VALUE=2011 <%= ("2011".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2011
<OPTION VALUE=2012 <%= ("2012".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2012
    <OPTION VALUE=2013 <%= ("2013".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2013
    <OPTION VALUE=2014 <%= ("2014".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2014
    <OPTION VALUE=2015 <%= ("2015".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2015
    <OPTION VALUE=2016 <%= ("2016".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2016
    <OPTION VALUE=2017 <%= ("2017".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2017
    <OPTION VALUE=2018 <%= ("2018".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2018
    <OPTION VALUE=2019 <%= ("2019".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2019
    <OPTION VALUE=2020 <%= ("2020".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2020
    <OPTION VALUE=2021 <%= ("2021".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2021
    <OPTION VALUE=2022 <%= ("2022".equals(finder.getShippedOnYear())?"SELECTED":"") %>>2022
    <OPTION VALUE=2023 <%= ("2023".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2023
</SELECT>
<SELECT NAME=<%= finder.kShippedOnMonthValue %>>
<OPTION VALUE=1 <%= ("1".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>January
<OPTION VALUE=2 <%= ("2".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>February
<OPTION VALUE=3 <%= ("3".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>March
<OPTION VALUE=4 <%= ("4".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>April
<OPTION VALUE=5 <%= ("5".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>May
<OPTION VALUE=6 <%= ("6".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>June
<OPTION VALUE=7 <%= ("7".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>July
<OPTION VALUE=8 <%= ("8".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>August
<OPTION VALUE=9 <%= ("9".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>September
<OPTION VALUE=10 <%= ("10".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>October
<OPTION VALUE=11 <%= ("11".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>November
<OPTION VALUE=12 <%= ("12".equals(finder.getShippedOnMonth())?"SELECTED":"") %>>December
</SELECT>
<SELECT NAME=<%= finder.kShippedOnDayValue %>>
<OPTION VALUE=1 <%= ("1".equals(finder.getShippedOnDay())?"SELECTED":"") %>>1
<OPTION VALUE=2 <%= ("2".equals(finder.getShippedOnDay())?"SELECTED":"") %>>2
<OPTION VALUE=3 <%= ("3".equals(finder.getShippedOnDay())?"SELECTED":"") %>>3
<OPTION VALUE=4 <%= ("4".equals(finder.getShippedOnDay())?"SELECTED":"") %>>4
<OPTION VALUE=5 <%= ("5".equals(finder.getShippedOnDay())?"SELECTED":"") %>>5
<OPTION VALUE=6 <%= ("6".equals(finder.getShippedOnDay())?"SELECTED":"") %>>6
<OPTION VALUE=7 <%= ("7".equals(finder.getShippedOnDay())?"SELECTED":"") %>>7
<OPTION VALUE=8 <%= ("8".equals(finder.getShippedOnDay())?"SELECTED":"") %>>8
<OPTION VALUE=9 <%= ("9".equals(finder.getShippedOnDay())?"SELECTED":"") %>>9
<OPTION VALUE=10 <%= ("10".equals(finder.getShippedOnDay())?"SELECTED":"") %>>10
<OPTION VALUE=11 <%= ("11".equals(finder.getShippedOnDay())?"SELECTED":"") %>>11
<OPTION VALUE=12 <%= ("12".equals(finder.getShippedOnDay())?"SELECTED":"") %>>12
<OPTION VALUE=13 <%= ("13".equals(finder.getShippedOnDay())?"SELECTED":"") %>>13
<OPTION VALUE=14 <%= ("14".equals(finder.getShippedOnDay())?"SELECTED":"") %>>14
<OPTION VALUE=15 <%= ("15".equals(finder.getShippedOnDay())?"SELECTED":"") %>>15
<OPTION VALUE=16 <%= ("16".equals(finder.getShippedOnDay())?"SELECTED":"") %>>16
<OPTION VALUE=17 <%= ("17".equals(finder.getShippedOnDay())?"SELECTED":"") %>>17
<OPTION VALUE=18 <%= ("18".equals(finder.getShippedOnDay())?"SELECTED":"") %>>18
<OPTION VALUE=19 <%= ("19".equals(finder.getShippedOnDay())?"SELECTED":"") %>>19
<OPTION VALUE=20 <%= ("20".equals(finder.getShippedOnDay())?"SELECTED":"") %>>20
<OPTION VALUE=21 <%= ("21".equals(finder.getShippedOnDay())?"SELECTED":"") %>>21
<OPTION VALUE=22 <%= ("22".equals(finder.getShippedOnDay())?"SELECTED":"") %>>22
<OPTION VALUE=23 <%= ("23".equals(finder.getShippedOnDay())?"SELECTED":"") %>>23
<OPTION VALUE=24 <%= ("24".equals(finder.getShippedOnDay())?"SELECTED":"") %>>24
<OPTION VALUE=25 <%= ("25".equals(finder.getShippedOnDay())?"SELECTED":"") %>>25
<OPTION VALUE=26 <%= ("26".equals(finder.getShippedOnDay())?"SELECTED":"") %>>26
<OPTION VALUE=27 <%= ("27".equals(finder.getShippedOnDay())?"SELECTED":"") %>>27
<OPTION VALUE=28 <%= ("28".equals(finder.getShippedOnDay())?"SELECTED":"") %>>28
<OPTION VALUE=29 <%= ("29".equals(finder.getShippedOnDay())?"SELECTED":"") %>>29
<OPTION VALUE=30 <%= ("30".equals(finder.getShippedOnDay())?"SELECTED":"") %>>30
<OPTION VALUE=31 <%= ("31".equals(finder.getShippedOnDay())?"SELECTED":"") %>>31
</SELECT>
</TD>

</TR>


<TR><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kCouponSearchFlag %> VALUE=1 <%= (finder.isCouponSearch()?"CHECKED":"") %>>&nbsp;Coupon/Discount&nbsp;Code&nbsp;<SELECT NAME=<%= finder.kCouponSearchType %>>
<OPTION VALUE=1 <%= (finder.getCouponType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getCouponType()==2?"SELECTED":"") %>>contains
<OPTION VALUE=3 <%= (finder.getCouponType()==3?"SELECTED":"") %>>begins with
<OPTION VALUE=4 <%= (finder.getCouponType()==4?"SELECTED":"") %>>ends with
</SELECT>&nbsp;<INPUT size="15" NAME=<%= finder.kCouponSearchValue %> VALUE="<%= (finder.isCouponSearch()?finder.getCoupon():"") %>">
</TD><TD NOWRAP>&nbsp;&nbsp;...through
<SELECT NAME=<%= finder.kShippedToYearValue %>>
<OPTION VALUE=2000 <%= ("2000".equals(finder.getShippedToYear())?"SELECTED":"") %>>2000
<OPTION VALUE=2001 <%= ("2001".equals(finder.getShippedToYear())?"SELECTED":"") %>>2001
<OPTION VALUE=2002 <%= ("2002".equals(finder.getShippedToYear())?"SELECTED":"") %>>2002
<OPTION VALUE=2003 <%= ("2003".equals(finder.getShippedToYear())?"SELECTED":"") %>>2003
<OPTION VALUE=2004 <%= ("2004".equals(finder.getShippedToYear())?"SELECTED":"") %>>2004
<OPTION VALUE=2005 <%= ("2005".equals(finder.getShippedToYear())?"SELECTED":"") %>>2005
<OPTION VALUE=2006 <%= ("2006".equals(finder.getShippedToYear())?"SELECTED":"") %>>2006
<OPTION VALUE=2007 <%= ("2007".equals(finder.getShippedToYear())?"SELECTED":"") %>>2007
<OPTION VALUE=2008 <%= ("2008".equals(finder.getShippedToYear())?"SELECTED":"") %>>2008
<OPTION VALUE=2009 <%= ("2009".equals(finder.getShippedToYear())?"SELECTED":"") %>>2009
<OPTION VALUE=2010 <%= ("2010".equals(finder.getShippedToYear())?"SELECTED":"") %>>2010
<OPTION VALUE=2011 <%= ("2011".equals(finder.getShippedToYear())?"SELECTED":"") %>>2011
<OPTION VALUE=2012 <%= ("2012".equals(finder.getShippedToYear())?"SELECTED":"") %>>2012
    <OPTION VALUE=2013 <%= ("2013".equals(finder.getShippedToYear())?"SELECTED":"") %>>2013

    <OPTION VALUE=2014 <%= ("2014".equals(finder.getShippedToYear())?"SELECTED":"") %>>2014
    <OPTION VALUE=2015 <%= ("2015".equals(finder.getShippedToYear())?"SELECTED":"") %>>2015
    <OPTION VALUE=2016 <%= ("2016".equals(finder.getShippedToYear())?"SELECTED":"") %>>2016
    <OPTION VALUE=2017 <%= ("2017".equals(finder.getShippedToYear())?"SELECTED":"") %>>2017
    <OPTION VALUE=2018 <%= ("2018".equals(finder.getShippedToYear())?"SELECTED":"") %>>2018
    <OPTION VALUE=2019 <%= ("2019".equals(finder.getShippedToYear())?"SELECTED":"") %>>2019
    <OPTION VALUE=2020 <%= ("2020".equals(finder.getShippedToYear())?"SELECTED":"") %>>2020
    <OPTION VALUE=2021 <%= ("2021".equals(finder.getShippedToYear())?"SELECTED":"") %>>2021
    <OPTION VALUE=2022 <%= ("2022".equals(finder.getShippedToYear())?"SELECTED":"") %>>2022
    <OPTION VALUE=2023 <%= ("2023".equals(finder.getReceivedOnYear())?"SELECTED":"") %>>2023
</SELECT>
<SELECT NAME=<%= finder.kShippedToMonthValue %>>
<OPTION VALUE=1 <%= ("1".equals(finder.getShippedToMonth())?"SELECTED":"") %>>January
<OPTION VALUE=2 <%= ("2".equals(finder.getShippedToMonth())?"SELECTED":"") %>>February
<OPTION VALUE=3 <%= ("3".equals(finder.getShippedToMonth())?"SELECTED":"") %>>March
<OPTION VALUE=4 <%= ("4".equals(finder.getShippedToMonth())?"SELECTED":"") %>>April
<OPTION VALUE=5 <%= ("5".equals(finder.getShippedToMonth())?"SELECTED":"") %>>May
<OPTION VALUE=6 <%= ("6".equals(finder.getShippedToMonth())?"SELECTED":"") %>>June
<OPTION VALUE=7 <%= ("7".equals(finder.getShippedToMonth())?"SELECTED":"") %>>July
<OPTION VALUE=8 <%= ("8".equals(finder.getShippedToMonth())?"SELECTED":"") %>>August
<OPTION VALUE=9 <%= ("9".equals(finder.getShippedToMonth())?"SELECTED":"") %>>September
<OPTION VALUE=10 <%= ("10".equals(finder.getShippedToMonth())?"SELECTED":"") %>>October
<OPTION VALUE=11 <%= ("11".equals(finder.getShippedToMonth())?"SELECTED":"") %>>November
<OPTION VALUE=12 <%= ("12".equals(finder.getShippedToMonth())?"SELECTED":"") %>>December
</SELECT>
<SELECT NAME=<%= finder.kShippedToDayValue %>>
<OPTION VALUE=1 <%= ("1".equals(finder.getShippedToDay())?"SELECTED":"") %>>1
<OPTION VALUE=2 <%= ("2".equals(finder.getShippedToDay())?"SELECTED":"") %>>2
<OPTION VALUE=3 <%= ("3".equals(finder.getShippedToDay())?"SELECTED":"") %>>3
<OPTION VALUE=4 <%= ("4".equals(finder.getShippedToDay())?"SELECTED":"") %>>4
<OPTION VALUE=5 <%= ("5".equals(finder.getShippedToDay())?"SELECTED":"") %>>5
<OPTION VALUE=6 <%= ("6".equals(finder.getShippedToDay())?"SELECTED":"") %>>6
<OPTION VALUE=7 <%= ("7".equals(finder.getShippedToDay())?"SELECTED":"") %>>7
<OPTION VALUE=8 <%= ("8".equals(finder.getShippedToDay())?"SELECTED":"") %>>8
<OPTION VALUE=9 <%= ("9".equals(finder.getShippedToDay())?"SELECTED":"") %>>9
<OPTION VALUE=10 <%= ("10".equals(finder.getShippedToDay())?"SELECTED":"") %>>10
<OPTION VALUE=11 <%= ("11".equals(finder.getShippedToDay())?"SELECTED":"") %>>11
<OPTION VALUE=12 <%= ("12".equals(finder.getShippedToDay())?"SELECTED":"") %>>12
<OPTION VALUE=13 <%= ("13".equals(finder.getShippedToDay())?"SELECTED":"") %>>13
<OPTION VALUE=14 <%= ("14".equals(finder.getShippedToDay())?"SELECTED":"") %>>14
<OPTION VALUE=15 <%= ("15".equals(finder.getShippedToDay())?"SELECTED":"") %>>15
<OPTION VALUE=16 <%= ("16".equals(finder.getShippedToDay())?"SELECTED":"") %>>16
<OPTION VALUE=17 <%= ("17".equals(finder.getShippedToDay())?"SELECTED":"") %>>17
<OPTION VALUE=18 <%= ("18".equals(finder.getShippedToDay())?"SELECTED":"") %>>18
<OPTION VALUE=19 <%= ("19".equals(finder.getShippedToDay())?"SELECTED":"") %>>19
<OPTION VALUE=20 <%= ("20".equals(finder.getShippedToDay())?"SELECTED":"") %>>20
<OPTION VALUE=21 <%= ("21".equals(finder.getShippedToDay())?"SELECTED":"") %>>21
<OPTION VALUE=22 <%= ("22".equals(finder.getShippedToDay())?"SELECTED":"") %>>22
<OPTION VALUE=23 <%= ("23".equals(finder.getShippedToDay())?"SELECTED":"") %>>23
<OPTION VALUE=24 <%= ("24".equals(finder.getShippedToDay())?"SELECTED":"") %>>24
<OPTION VALUE=25 <%= ("25".equals(finder.getShippedToDay())?"SELECTED":"") %>>25
<OPTION VALUE=26 <%= ("26".equals(finder.getShippedToDay())?"SELECTED":"") %>>26
<OPTION VALUE=27 <%= ("27".equals(finder.getShippedToDay())?"SELECTED":"") %>>27
<OPTION VALUE=28 <%= ("28".equals(finder.getShippedToDay())?"SELECTED":"") %>>28
<OPTION VALUE=29 <%= ("29".equals(finder.getShippedToDay())?"SELECTED":"") %>>29
<OPTION VALUE=30 <%= ("30".equals(finder.getShippedToDay())?"SELECTED":"") %>>30
<OPTION VALUE=31 <%= ("31".equals(finder.getShippedToDay())?"SELECTED":"") %>>31
</SELECT>
</TD></tr>

 <TR><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kSKUSearchFlag %> VALUE=1 <%= (finder.isSKUSearch()?"CHECKED":"") %>>&nbsp;SKU&nbsp;<SELECT NAME=<%= finder.kSKUSearchType %>>
<OPTION VALUE=1 <%= (finder.getSKUType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getSKUType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kSKUSearchValue %> VALUE="<%= (finder.isSKUSearch()?finder.getSKU():"") %>">

</TD><TD NOWRAP>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kVoidSearchFlag %> VALUE=1 <%= (finder.isVoidsIncluded()?"CHECKED":"") %>>&nbsp;Include Voided Orders
</TD></TR>


<TR><TD NOWRAP valign=top align=left>
<INPUT TYPE=CHECKBOX NAME=<%= finder.kPOValueSearchFlag %> VALUE=1 <%= (finder.isPOValueSearch()?"CHECKED":"") %>>&nbsp;PO Number&nbsp;<SELECT NAME=<%= finder.kPOValueSearchType %>>
<OPTION VALUE=1 <%= (finder.getPOValueType()==1?"SELECTED":"") %>>is
<OPTION VALUE=2 <%= (finder.getPOValueType()==2?"SELECTED":"") %>>contains
</SELECT>&nbsp;<INPUT NAME=<%= finder.kPOValueSearchValue %> VALUE="<%= (finder.isPOValueSearch()?finder.getPOValue():"") %>">
</TD>
<TD NOWRAP>
    <% if(FacilitiesManager.isClientMultiFacility(clientId))
    {
        %>
    <INPUT TYPE=CHECKBOX NAME=<%= finder.kLocationSearchFlag %> VALUE=1 <%= (finder.isLocationSearch()?"CHECKED":"") %>>&nbsp;Facility&nbsp;<SELECT NAME=<%= finder.kLocationSearchValue %>>
    <%= FacilitiesManager.getHTMLSelectOptions(finder.getLocation())%>
</SELECT>
    <% } else { %>
    &nbsp;<B>Facility:&nbsp;</B><%= FacilitiesManager.getFacilityDisplayLabelForCode(FacilitiesManager.getLocationCodeForClient(clientId))%>
    <%
    } %>

</TD></TR>
     <tr>
         <TD NOWRAP valign=top align=left>
             <INPUT TYPE=CHECKBOX NAME=<%= finder.kGroupSearchFlag %> VALUE=1 <%= (finder.isGroupSearch()?"CHECKED":"") %>>&nbsp;Group Name&nbsp;<SELECT NAME=<%= finder.kGroupSearchType %>>
             <OPTION VALUE=1 <%= (finder.getGroupType()==1?"SELECTED":"") %>>is
             <OPTION VALUE=2 <%= (finder.getGroupType()==2?"SELECTED":"") %>>contains
         </SELECT>&nbsp;<INPUT NAME=<%= finder.kGroupSearchValue %> VALUE="<%= (finder.isGroupSearch()?finder.getGroup():"") %>">
         </TD>
     </tr>

 <TR><TD NOWRAP valign=top align=left>&nbsp;
 </TD>
     <TD align=right>&nbsp;<P>&nbsp;
         <INPUT TYPE=SUBMIT NAME=<%= finder.kSubmitType %> VALUE="Find Orders">&nbsp;
         <INPUT TYPE=SUBMIT NAME=<%= finder.kSubmitType %> VALUE="Show All Orders">
     </TD></TR>
</FORM>   
</TABLE>
<%= finder.showResults(request,response)  %>
 