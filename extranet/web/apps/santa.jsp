<html>
<head>
<title>Letter From Santa Call Download</title>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
    <style type="text/css">
        body {
	background-color: #fff;
	font-family: Verdana, Helvetica, Arial, sans-serif;
	margin-left: auto;
	margin-right: auto;
	background-repeat: repeat-y;
	font-size: 13px;
	padding: 15px;
}
table.calls {
	border: 1px solid #666;
	width: 60%;
	margin: 20px 0 20px 0 !important;
    font-size: 11px;
}
table.search{
    border: none;
    width: auto;
    margin: 0 0 0 0;
    font-size:13px;
}
tr.even {
    background-color: #BFFFBF;
}

th,td {
	padding: 2px 4px 2px 4px !important;
	text-align: left;
	vertical-align: top;
}

thead tr {
	background-color: #80FF80;
}

th.sorted {
	background-color: #008F00;
}

th a,th a:visited {
	color: blue;
}

th a:hover {
	text-decoration: underline;
	color: black;
}

th.sorted a,th.sortable a {
	background-position: right;
	display: block;
	width: 100%;
}


a {
    text-decoration: none;
    color: blue;
}
a:hover {
    text-decoration: underline;
}
span.label {
    font-weight: bold;
}
td.right {
    text-align: right;
}
td.left {
    text-align: left;
}
span.error {
            color:red;
        }
span.pagebanner {
	background-color: #eee;
	border: 1px dotted #999;
	padding: 2px 4px 2px 4px;
	width: 79%;
	margin-top: 10px;
	display: block;
	border-bottom: none;
}

span.pagelinks {
	background-color: #eee;
	border: 1px dotted #999;
	padding: 2px 4px 2px 4px;
	width: 50%;
	display: block;
	border-top: none;
	margin-bottom: -5px;
}
#container {
background:#FFFFFF none repeat scroll 0%;
left:50%;
margin-left:-390px;
overflow:auto;
position:relative;
width:781px;
z-index:100;
}
   #data{
       margin-left:15px;
       
   }
    </style>
</head>
<body>
<div id="container">

     <img src="<html:rewrite page="/images/apps/santa.png" module=""/>" alt="Letters for Santa" border=0/>
Phone Number:
<html:form action="santa" focus="phoneNumber">
    <html:text property="phoneNumber"/>

<html:submit property="submit">
    <bean:message key="button.search"/>
</html:submit>
</html:form>
  <c:if test="${santaCallForm.searchResults!=null}">
     <div id="data">
        <span class="error">${error}</span>
       <display:table id="table1"  name="sessionScope.santaCallForm.searchResults.rows" sort="list"
                       export="false" cellspacing="0" requestURI="" class="calls">

            <c:forEach var="item" items="${sessionScope.santaCallForm.columns}">
                <c:choose>

                    <c:when test='${item.action == "call_id"}'>
                        <display:column href="santa.do?submit=Download" paramId="callId" property="${item.action}"
                                        title="Call Id" sortable="false" >
                        </display:column>
                    </c:when>

                    <c:when test='${item.action=="phone_number"}'>
                        <display:column property="${item.action}" title="Phone Number Called" sortable="false" />
                    </c:when>
                    <c:otherwise>
                        <display:column property="${item.action}" title="${item.action}" sortable="true" headerClass="sortable"/>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
            <display:setProperty name="basic.empty.showtable" value="true"/>
            <display:setProperty name="paging.banner.placement" value="bottom"/>
            <display:setProperty name="paging.banner.page.selected" value=""/>
            <display:setProperty name="paging.banner.all_items_found" value=""/>
            <display:setProperty name="paging.banner.no_items_found" value=""/>
            <display:setProperty name="paging.banner.one_item_found" value=""/>
        </display:table>
      
</div>
  </c:if>
    </div>
</body>
</html>
