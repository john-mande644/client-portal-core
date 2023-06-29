<%@ page %>

<html>
    <head>
        <link REL="stylesheet" HREF="../owd.css" TYPE="text/css">

    </head>
    <body>
        <a href="label_printer">Return to label utility</a>
        <h4>Enter address</h4>
        <form action="address_form" method="post">
            <label for="ship_to">Ship to</label>
            <input type="text" id="ship_to" name="ship_to" required/>
            <br/>

            <label for="address1">Address line 1</label>
            <input type="text" id="address1" name="address1" required/>
            <br/>

            <label for="address2">Address line 2</label>
            <input type="text" id="address2" name="address2" required/>
            <br/>

            <label for="city">City</label>
            <input type="text" id="city" name="city" required/>
            <br/>

            <label for="state">State (abbreviation)</label>
            <input type="text" id="state" name="state" required/>
            <br/>

            <label for="zip">ZIP code</label>
            <input type="text" id="zip" name="zip" required/>
            <br/>

            <input type = "submit" value = "Submit"/>
        </form>
        <br/>

        <% if(request.getAttribute("result") != null) {%>
            <h4>Result</h4>
            <p><%= request.getAttribute("result")%></p>
        <%}%>

        <% if(request.getAttribute("locations") != null) {%>
            <h4>Current ship locations</h4>
            <ol><%= request.getAttribute("locations")%></ol>
        <%}%>
    </body>
</html>