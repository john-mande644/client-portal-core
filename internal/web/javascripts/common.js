var Color = new Array();
Color[1] = "ff";
Color[2] = "ee";
Color[3] = "dd";
Color[4] = "cc";
Color[5] = "bb";
Color[6] = "aa";
Color[7] = "99";

function waittofade() {
    if (document.getElementById('fade')) {
        setTimeout("fadeIn(7)", 1000);
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

function fadeIn(where) {
    if (where >= 1) {
        document.getElementById('fade').style.backgroundColor = "#ffff" + Color[where];
        if (where > 1) {
            where -= 1;
            setTimeout("fadeIn(" + where + ")", 200);
        } else {
            where -= 1;
            setTimeout("fadeIn(" + where + ")", 200);
            document.getElementById('fade').style.backgroundColor = "transparent";
        }
    }
}

function validateField(fieldId, alertMessage) {
    if (document.getElementById(fieldId).value == "") {
        alert(alertMessage);
        document.getElementById(fieldId).focus();
        return false;
    } else {
        return true;
    }
}

function popup(url, width, height) {
    window.open(url, 'popup', 'width=' + width + ',height=' + height + ',scrollbars=auto,resizable=yes,toolbar=no,directories=no,menubar=no,status=no,left=100,top=100');
    return false;
}

/**
 * COMMON DHTML FUNCTIONS
 * These are handy functions I use all the time.
 *
 * By Seth Banks (webmaster at subimage dot com)
 * http://www.subimage.com/
 *
 * Up to date code can be found at http://www.subimage.com/dhtml/
 *
 * This code is free for you to use anywhere, just keep this comment block.
 */

/**
 * X-browser event handler attachment and detachment
 *
 * @argument obj - the object to attach event to
 * @argument evType - name of the event - DONT ADD "on", pass only "mouseover", etc
 * @argument fn - function to call
 */
function addEvent(obj, evType, fn) {
    if (obj.addEventListener) {
        obj.addEventListener(evType, fn, true);
        return true;
    } else if (obj.attachEvent) {
        var r = obj.attachEvent("on" + evType, fn);
        return r;
    } else {
        return false;
    }
}
function removeEvent(obj, evType, fn, useCapture) {
    if (obj.removeEventListener) {
        obj.removeEventListener(evType, fn, useCapture);
        return true;
    } else if (obj.detachEvent) {
        var r = obj.detachEvent("on" + evType, fn);
        return r;
    } else {
        alert("Handler could not be removed");
    }
}

/**
 * Code below taken from - http://www.evolt.org/article/document_body_doctype_switching_and_more/17/30655/
 *
 * Modified 4/22/04 to work with Opera/Moz (by webmaster at subimage dot com)
 *
 * Gets the full width/height because it's different for most browsers.
 */
function getViewportHeight() {
    if (window.innerHeight != window.undefined) return window.innerHeight;
    if (document.compatMode == 'CSS1Compat') return document.documentElement.clientHeight;
    if (document.body) return document.body.clientHeight;
    return window.undefined;
}
function getViewportWidth() {
    if (window.innerWidth != window.undefined) return window.innerWidth;
    if (document.compatMode == 'CSS1Compat') return document.documentElement.clientWidth;
    if (document.body) return document.body.clientWidth;
    return window.undefined;
}