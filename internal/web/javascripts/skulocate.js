var req;

function loadXMLDoc(url) {

    // Internet Explorer
    try {
        req = new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch(e) {
        try {
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }
        catch(oc) {
            req = null;
        }
    }

    // Mozailla/Safari
    if (!req && typeof XMLHttpRequest != "undefined") {
        req = new XMLHttpRequest();
    }

    // Call the processChange() function when the page has loaded
    if (req != null) {
        req.onreadystatechange = processChange;

        req.open("POST", url, true);
        req.send(null);
    }
}

function processChange() {
    //alert(req.readyState+'');
    // The page has loaded and the HTTP status code is 200 OK
    if (req.readyState == 4 && req.status == 200) {
        //     alert(req.responseText);
        // Write the contents of this URL to the searchResult layer
        getObject("searchResult").innerHTML = req.responseText;
    }
}

function getObject(name) {
    var ns4 = (document.layers) ? true : false;
    var w3c = (document.getElementById) ? true : false;
    var ie4 = (document.all) ? true : false;

    if (ns4) return eval('document.' + name);
    if (w3c) return document.getElementById(name);
    if (ie4) return eval('document.all.' + name);
    return false;
}


window.onload = function() {
    if (getObject("q") != null)
    {
        getObject("q").focus();
    }
}


function getkeyval(field, e)
{
    var key;
    var keychar;


    if (window.event) {
        key = window.event.keyCode;
    }
    else if (e)
    {
        key = e.which;
    }
    else
    {
        //getObject("inKey").innerHTML = ":returning"+field.value+":";
        return field.value;
    }
    //getObject("inKey").innerHTML = ":"+key+":";
    keychar = String.fromCharCode(key);

    //
    //alert(key);
    // control keys
    if ((key == null) || (key < 33))
    {
        return field.value;
    }

    else  if (key == 127 || key == 8) //delete
    {

        return field.value;

    } else
    {
        return field.value;
    }
}
