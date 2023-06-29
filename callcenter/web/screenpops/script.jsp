<html>
<head>
    <title>Report List</title>
    <%

        String announce_script = request.getParameter("announce_script");
        String do_orderref_entry = request.getParameter("do_orderref_entry");
        String client_fkey = request.getParameter("client_fkey");
        String handle = request.getParameter("handle");

    %>
    <STYLE TYPE="text/css">
        .header {
            font-size: 12px;
            font-family: arial;
            letter-spacing: 0pt;
            font-weight: bold;
            color: #ffffff;
        }

        .text {
            font-size: 12px;
            font-family: arial;
            letter-spacing: 0pt;
            font-weight: normal;
            color: #000000;
        }

        .menuhide {
            display: none;
            border-left: 2 solid white;
            border-top: 2 solid white;
            border-right: 2 solid black;
            border-bottom: 2 solid black;
            background-color: #d0d0c9;
            position: absolute;
        }

        .menu {
            display: '';
            border-left: 2 solid white;
            border-top: 2 solid white;
            border-right: 2 solid black;
            border-bottom: 2 solid black;
            background-color: #d0d0c9;
            position: absolute;
        }

        .org {
            color: #000000;
            text-decoration: none;
        }

        .dec {
            color: #0000ff;
            text-decoration: underline;
        }

        .cont {
            text-decoration: none;
            font-size: 12px;
            color: #000000;
        }

        .contdec {
            text-decoration: none;
            font-size: 12px;
            color: #0000ff;
        }
    </STYLE>

</head>

<BODY BGCOLOR="#FFFFFF" TOPMARGIN=8 LEFTMARGIN=6 MARGINWIDTH=11 MARGINHEIGHT=8>
<div id=fsdfd class=text>
    <P><BR>

    <P>
    <CENTER><FONT SIZE=+2>
        <%= announce_script %>
    </FONT></CENTER>

</div>
<% if (do_orderref_entry.equals("1")) {%>
<br>
<hr>
<center>
    <%--<FORM action="http://service.owd.com/forms/do/recordCallcenterOrders" method=post>
        <INPUT TYPE=HIDDEN name=uqhandle value=<%=handle%>>
        <INPUT TYPE=HIDDEN name=client value=<%=client_fkey%>>
        Order Number:<INPUT TYPE=TEXT WIDTH=15 NAME="order_refnum" value="">

        <p>
            Order Sub-Total: &nbsp &nbsp<INPUT TYPE=TEXT WIDTH=15 NAME="total" value="">

        <p>
            <INPUT name=actionType type=submit value="Submit">
    </form>--%>
</center>
<%}%>
</body>
</html>