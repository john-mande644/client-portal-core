<%@ page import="java.util.List,
                 java.util.Iterator,
                 com.owd.web.internal.navigation.UIUtilities" %>
<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>
<%
    String currentManager = (String) request.getAttribute("lf_CURRMANAGER");
    if (currentManager == null) {
        currentManager = UIUtilities.kHomeAreaName;
    }


    if(request.getParameter("location")!=null)
    {
       request.getSession(true).setAttribute("owd_current_location",request.getParameter("location"));
    }
    String currentLocation = WarehouseStatus.getCurrentLocation(request);
    List<OwdFacility> facilities = WarehouseStatus.getFacilityList();
    StringBuffer fsb = new StringBuffer();
    String currFacility = "UNKNOWN ERROR";
    for(OwdFacility f: facilities)
    {
        if(currentLocation.equals(f.getFacilityCode()))
        {
            currFacility = f.getFacilityCode()+" - "+f.getCity()+", "+f.getState();
        } else
        {
            fsb.append("<li><a href=\"/internal/clienttools/index.jsp?location="+f.getFacilityCode()+"\" class=\"confirmation\">"+f.getFacilityCode()+" - "+f.getCity()+", "+f.getState()+"</a></li>");
        }
    }


%><link rel="Stylesheet" href="/internal/stylesheets/AdHoc.css" type="text/css"/>
<link rel="Stylesheet" href="/internal/stylesheets/screen.css" type="text/css"/>
<link rel="Stylesheet" href="/internal/stylesheets/locationmenu.css" type="text/css"/>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="/internal/javascripts/common.js"></script>
<script language="JavaScript" type="text/javascript" src="/internal/javascripts/getElementsByClass.js"></script>

</head>
<body id="bod" runat=server>
<div class="Container">
    <script language="JavaScript">
        function toggleP2P() {
            if (document.getElementById('P2P').style.display == 'none') {
                document.getElementById('P2P').style.display = 'block'
                document.getElementById('Statusbar').style.display = 'none'
            } else {
                document.getElementById('P2P').style.display = 'none'
                document.getElementById('Statusbar').style.display = 'block'
            }
        }
    </script>

    <div id="Statusbar">
        <div class="click-nav">
            <ul class="no-js">
                <li>
                    <a href="#" class="clicker"><%= currFacility %></a>
                    <ul><%= fsb.toString() %></ul>
                </li>
            </ul>
        </div>

        <script>
            $(function() {
                // Clickable Dropdown
                $('.click-nav > ul').toggleClass('no-js js');
                $('.click-nav .js ul').hide();
                $('.click-nav .js').click(function(e) {
                    $('.click-nav .js ul').slideToggle(200);
                    $('.clicker').toggleClass('active');
                    e.stopPropagation();
                });
                $(document).click(function() {
                    if ($('.click-nav .js ul').is(':visible')) {
                        $('.click-nav .js ul', this).slideUp();
                        $('.clicker').removeClass('active');
                    }
                });

                        $('.confirmation').on('click', function () {
                            return confirm('Are you sure you want to change Facility?');
                        });

            });
        </script>
        <div id="StatusLeft">
            OWD Internal Services


            <span class="light">(<a href="/internal/clienttools/index.jsp"
                                           title="Go to central access page">Home</a>)
        </span>
        &nbsp;&nbsp;
             <%--<span class="light">
                 <%
                     String newLocation="DC1";
                     if("DC1".equals(currentLocation))
                     {
                         newLocation="DC6";
                     }
                 %>
                 (<a href="/internal/clienttools/index.jsp?location=<%= newLocation %>"
                                           title="Go to central access page">Switch DC to <%= newLocation%></a>)
        </span>--%>


        <%-- <div id="P2P" style="display: none;">
                <strong>Switch to:</strong>

                <select id="projectSelector" onChange="window.location.href=document.getElementById('projectSelector').options[selectedIndex].value">
              <option value="/clients/owd/3/">Active Videos</option>
        <option value="/clients/owd/5/">Vitamin Health</option>
        <option value="/clients/owd/7/" selected="selected">OWD - One World Test</option>
        <option value="/clients/owd/4/">OWD - OWD:Billing</option>
        <option value="/clients/owd/6/">OWD - OWD:Capacity</option>
                </select>

                <span class="light">(<a class="admin" href="#" onClick="toggleP2P(); return false;">Cancel</a>)</span>
            </div>
        --%>


    </div>

    <div id="StatusRight">

        (<a href="http://service.owd.com/adhoc/Logout.aspx" title="Log-out and remove the cookie from your machine">Log-out</a>)
    </div>
</div>


<div id="Header">

    <h3>

        <a class="" target="___" href="">Help</a>
    </h3>


    <h1>One World Direct</h1>
    <!-- TABS -->
    <ul id="Tabs">

        <li>
            <a <%= UIUtilities.kHomeAreaName.equals((String) request.getAttribute("lf_CURRMANAGER")) ? "class=\"current\"" : ""%>
                    href="/internal/clienttools/"><%= UIUtilities.kHomeAreaName %></a></li>
        <!--	<li><a <%= UIUtilities.kReportAreaName.equals(request.getAttribute("lf_CURRMANAGER"))?"class=\"current\"":""%> href="https://service.owd.com/adhoc/"><%= UIUtilities.kReportAreaName %></a></li> -->
              <li>
            <a <%= UIUtilities.kClientAreaName.equals((String) request.getAttribute("lf_CURRMANAGER")) ? "class=\"current\"" : ""%>
                    href="/internal/client/edit"><%= UIUtilities.kClientAreaName %></a></li>
        <li><a <%= UIUtilities.kUserAreaName.equals(request.getAttribute("lf_CURRMANAGER")) ? "class=\"current\"" : ""%>
                href="/internal/user/edit"><%= UIUtilities.kUserAreaName %></a></li>
        <li><a <%= UIUtilities.kUPSAreaName.equals(request.getAttribute("lf_CURRMANAGER")) ? "class=\"current\"" : ""%>
                href="/internal/intravexbills"><%= UIUtilities.kUPSAreaName %></a></li>
        <li>
            <a <%= UIUtilities.kWarehouseAreaName.equals(request.getAttribute("lf_CURRMANAGER")) ? "class=\"current\"" : ""%>
                    href="/internal/warehouse/admin"><%= UIUtilities.kWarehouseAreaName %></a></li>
        <%--<li>
                  <a <%= UIUtilities.kITAreaName.equals(request.getAttribute("lf_CURRMANAGER")) ? "class=\"current\"" : ""%>
                          href="/internal/it/status.action"><%= UIUtilities.kITAreaName %></a></li>
--%>
    </ul>

</div>


<div id="ContentFrame">
    <div class="col">


        <div class="SectionHeader">
            <%
                List commandList = (List) request.getAttribute("lf_commandList");
                if (commandList != null) {            %><h3><%
            Iterator it = commandList.iterator();
            boolean firstCommand = true;
            while (it.hasNext()) {
                String command = (String) it.next();
                String url = "";
                if (command.indexOf(";") > 0) {
                    url = command.substring(command.indexOf(";") + 1);
                    command = command.substring(0, command.indexOf(";"));
                    if (!firstCommand) {
        %> | <%
            }
        %><A HREF="<%= url %>"><%= command %></A><%
                }

                firstCommand = false;
            }
        %></h3><%
            }
        %>
            <div id="fade"><script>waittofade();</script>

                <H1><%= request.getAttribute("lf_CURRMANAGER") %>
                    &nbsp;-&nbsp;<%= request.getAttribute("lf_navLocation") %></H1></div>


            <!--
   <div id="fade">
  <script>waittofade();</script><h2 class="subheader"><%= request.getAttribute("lf_navLocation") %></h2>  </div>
 </div>-->
        </div>