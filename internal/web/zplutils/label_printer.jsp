<%@ page import="com.owd.web.internal.zplutils.ShipLocation,
                com.owd.web.internal.zplutils.Vendor,
                java.util.List" %>
<html>

<head>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>

    <link REL="stylesheet" HREF="../owd.css" TYPE="text/css">
    <script>
        function Download(filename, inputElement) {
            var text = document.getElementById(inputElement).textContent;

            var element = document.createElement('a');
            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
            element.setAttribute('download', filename);

            element.style.display = 'none';
            document.body.appendChild(element);

            element.click();

            document.body.removeChild(element);
        }

        function showHideAdvanced() {
            if (!document.getElementById("advanced").checked) {
                var container = document.getElementById("advanced_container");
                // Remove every children it had before
                while (container.hasChildNodes()) {
                    container.removeChild(container.lastChild);
                }
            } else {
                // Generate a dynamic number of inputs
                var number = document.getElementById("num_cartons").value;
                // Get the element where the inputs will be added to
                var container = document.getElementById("advanced_container");

                for (i = 1; i <= number; i++) {
                    // Append a node with a random text
                    var header = document.createElement("h6");
                    header.textContent = "Carton " + (i);
                    container.appendChild(header);
                    container.appendChild(document.createElement("br"));

                    // Create an <input> element, set its type and name attributes
                    var itemNum = document.createElement("input");
                    itemNum.type = "text";
                    itemNum.name = "adv_item_num" + i;
                    itemNum.id = itemNum.name;

                    var qty = document.createElement("input");
                    qty.type = "number";
                    qty.name = "adv_qty" + i;
                    qty.id = qty.name;

                    var partial = document.createElement("input");
                    partial.type = "checkbox";
                    partial.value = "true";
                    partial.name = "adv_partial" + i;
                    partial.id = partial.name;

                    var duplicate = document.createElement("button");
                    duplicate.textContent = "Duplicate";
                    duplicate.setAttribute("onclick", "duplicateEntry(" + i + ");");

                    container.appendChild(document.createTextNode("Item number"));
                    container.appendChild(itemNum);
                    container.appendChild(document.createElement("br"));
                    container.appendChild(document.createTextNode("Carton quantity"));
                    container.appendChild(qty);
                    container.appendChild(document.createElement("br"));
                    container.appendChild(partial);
                    container.appendChild(document.createTextNode("Is partial"));
                    if (i < number) {
                        container.appendChild(document.createElement("br"));
                        container.appendChild(duplicate);
                    }

                    // Append a line break
                    container.appendChild(document.createElement("br"));
                }
            }
        }

        function duplicateEntry(i) {
            var itemNum = document.getElementById("adv_item_num" + i).value;
            var qty = document.getElementById("adv_qty" + i).value;
            var partial = document.getElementById("adv_partial" + i).checked;

            document.getElementById("adv_item_num" + (i + 1)).value = itemNum;
            document.getElementById("adv_qty" + (i + 1)).value = qty;
            document.getElementById("adv_partial" + (i + 1)).checked = partial;
        }
    </script>
</head>

<body>
<h4>Use this form to generate ZPL files for QVC vendor compliance labels. If the order has multiple SKU/Kits use
    the Advanced carton options to set the contents and quantity of each carton.</h4>
<br />
<div class="container">
    <form action="label_printer" method="post" role="form" data-toggle="validator" novalidate>
        <div class="form-group">
            <label for="num_cartons">Number of cartons</label>
            <input type="number" class="form-control" id="num_cartons" name="num_cartons"
                   placeholder="Total number of cartons" required />
        </div>
        <div class="form-group">
            <label for="order_num">Order number</label>
            <input type="text" class="form-control" name="order_num" id="order_num"
                   placeholder="OWD order number" required />
        </div>
        <div class="form-group">
            <label for="PO_num">PO number</label>
            <input type="text" class="form-control" name="PO_num" id="PO_num" placeholder="PO Number"
                   required />
        </div>
        <div class="form-group">
            <label for="full_carton_qty">Master carton quantity</label>
            <input type="number" class="form-control" name="full_carton_qty" id="full_carton_qty"
                   placeholder="Quantity of master carton" required />
        </div>
        <div clas="form-group">
            <label for="partial_carton_qty">Partial carton quantity</label>
            <input type="number" class="form-control" name="partial_carton_qty" id="partial_carton_qty"
                   placeholder="Quantity of partial carton" required />
        </div>
        <div class="form-group">
            <label for="item_num">Item number</label>
            <input type="text" class="form-control" name="item_num" id="item_num" minlength="6" />
        </div>

        <div class="form-group">
            <label for="ship_location">Ship location</label>
            <select id="ship_location" class="form-control" name="ship_location" required>
                <% for(ShipLocation loc : (List<ShipLocation>)request.getAttribute("locations")) { %>
                <option value="<%= loc.index %>">
                    <%= loc.display%>
                </option>
                <% } %>
            </select>
            <small id="addressCreate" class="form-text text-muted"><a href="address_form">Add ship
                location</a></small>
        </div>
        <div class="form-group">
            <label for="vendor">Vendor</label>
            <select id="vendor" class="form-control" name="vendor" required>
                <% for(Vendor ven : (List<Vendor>)request.getAttribute("vendors")) { %>
                <option value="<%= ven.getId() %>">
                    <%= ven.getName()%>
                </option>
                <% } %>
            </select>
        </div>
        <div clas="form-group">
            <input class="form-check-input" type="checkbox" class="form-control" id="advanced" name="advanced"
                   value="true" onchange="showHideAdvanced()">
            <label class="form-check-label" for="advanced">Advanced carton options</label><br />
        </div>

        <div id="advanced_container"></div>

        <input type="submit" value="Submit" onclick="this.form.submit();this.disabled=true; this.value='Processing...'" />
        <% if(request.getAttribute("zpl") != null) {%>
        <button id="download_button" onclick="Download('labels.prn', 'zpl_data');return false;">Download</button>

        <p class="hidden" id="zpl_data"><%= request.getAttribute("zpl") %></p>
        <%}%>
    </form>
</div>

<br />


<% if(request.getAttribute("error") != null) {%>
<h4>Exception occurred</h4>
<p><%= request.getAttribute("error")%></p>
<%}%>
</body>
</html>