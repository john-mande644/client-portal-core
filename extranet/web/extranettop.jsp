<%



%>
<HTML>
<HEAD>
    <TITLE>
        One World Extranet
    </TITLE>
    <link rel="stylesheet" href="<%= request.getContextPath()+"/owd.css" %>" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script type="text/javascript" src="https://use.typekit.com/jgj8cre.js"></script>
    <script type="text/javascript">try{Typekit.load();}catch(e){}</script>
    <%= request.getAttribute("headinserts")==null?"":request.getAttribute("headinserts")%>
</head>
<body>
<div id="headerblock">
    <table class="headertable" bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="0" width="100%">
        <tbody><tr>
            <td style="width: 438px;" align="left" bgcolor="#ffffff" valign="TOP"><img src="<%= request.getContextPath()+"/images/extranet.jpg" %>" alt=""></td>
            <td width=100% align="center" bgcolor="#ffffff" valign="bottom">
                <div id="sectionname"><%= request.getAttribute("areatitle") %></div>
            </td>
            <td style="width: 45px;" align="right" bgcolor="#ffffff" valign="TOP">
                <a href="<%= request.getContextPath()+"/" %>"><img src="<%= request.getContextPath()+"/images/home.gif" %>" alt=""></a></td>
            <td style="width: 45px;" align="right" bgcolor="#ffffff" valign="TOP">
                <a href="https://service.owd.com/adhoc/Logout.aspx"><img src="<%= request.getContextPath()+"/images/logout.gif" %>" alt=""></a></td>
        </tr>
        </tbody></table>
    <table class="headertable" bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr><td align="left" bgcolor="#ffffff" valign="TOP" width=100%><div id="mainselection">
        <%= request.getAttribute("clientselector") %>
        </div></td></td>
            <td  nowrap="true" align="right" bgcolor="#ffffff" valign="bottom">
                &nbsp;<br/><b>User: <%= request.getUserPrincipal().getName() %></b></td>
        </tr>
        </tbody></table>
</div>
<hr>
<!-- End Header -->