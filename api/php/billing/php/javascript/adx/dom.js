ADX.Event = {

    __eventListeners: [],

    /*
    addListener: function(instance, eventName, listener) {
        var listenerFn = listener;

        if (instance.addEventListener) {
            instance.addEventListener(eventName, listenerFn, false);
        } else if (instance.attachEvent) {
            listenerFn = function() { listener(window.event); }
            instance.attachEvent('on' + eventName, listenerFn);
        } else {
            throw new Error('Event registration is not supported.');
        }

        var event = {
            instance: instance,
            name: eventName,
            listener: listenerFn
        };

        this.__eventListeners.push(event);

        return event;
    },
    */

    addListener: function(instance, eventName, listener) {
        if (instance.addEventListener) {
            instance.addEventListener(eventName, listener, false);
        } else if (instance.attachEvent) {
            instance.attachEvent('on' + eventName, listener);
        } else {
            throw new Error('Event registration is not supported.');
        }

        var event = {
            instance: instance,
            name: eventName,
            listener: listener
        };

        this.__eventListeners.push(event);

        return event;
    },

    removeListener: function(event) {
        var instance = event.instance;
        if (instance.removeEventListener) {
            instance.removeEventListener(event.name, event.listener, false);
        } else if (instance.detachEvent) {
            instance.detachEvent('on' + event.name, event.listener);
        }

        this.__eventListeners.remove(event);
    },

    unregisterAllEvents: function() {
        while(this.__eventListeners.length > 0) {
            removeListener(this.__eventListeners[0]);
        }
    },

    getSource: function(evt) {
        return evt.target ? evt.target : (evt.srcElement ? evt.srcElement : null);
    },

    getKeyCode: function(evt) {
        if (evt.keyCode) { return evt.keyCode; }
        else if (evt.charCode) { return evt.charCode; }
        else { return null; }
    },

    kill: function(evt) {
        this.cancelPropagation(evt);
        this.preventDefault(evt);
    },

    cancelPropagation: function(evt) {
        if (window.event) {
            window.event.cancelBubble = true;
        } else if (evt && evt.stopPropagation) {
            evt.stopPropagation();
        }
    },

    preventDefault: function(evt) {
        if (window.event) {
            window.event.returnValue = false;
        } else if (evt && evt.preventDefault) {
            evt.preventDefault();
        }
    }
};

ADX.Event.onKeyDown = {
    addListener: function(listener) {
        if (!this.listeners) {
            this.listeners = [];
            document.onkeydown = this.dispatch.bindAsEventListener(this);

            // add the base focus group
            this.focusGroup = [];
            this.listeners.push(this.focusGroup);
        }
        this.focusGroup.push(listener);
    },
    removeListener: function(listener) {
        this.focusGroup.remove(listener);
    },
    pushFocusGroup: function() {
        this.focusGroup = [];
        this.listeners.push(this.focusGroup);
    },
    popFocusGroup: function() {
        if (this.listeners.length > 1) {
            this.listeners.pop();
            this.focusGroup = this.listeners[this.listeners.length-1];
        }
    },
    dispatch: function(evt) {
        var result = true;
        for(var i = 0; i < this.focusGroup.length; i++) {
            var listener = this.focusGroup[i];
            if (listener(evt) == false) {
                result = false;
            }
        }
        return result;
    }
};

ADX.KeySink = {
    getInstance: function() {
        if (!this.instance) {
            this.instance = document.createElement('textarea');
            this.instance.style.position = 'absolute';
            this.instance.style.left = '-1000px';
            this.instance.style.top = '-1000px';
            document.body.appendChild(this.instance);
        }

        return this.instance;
    }
};

ADX.Dom = {
    // Fast lookup for camelized style property names indexed by
    // hyphenated version of name (i.e. padding-left => paddingLeft)
    propertyNames: [],

    disableTextSelection: function(element) {
        if (ADX.Browser.ie) {
            // Prevent IE text selection while dragging!!!
            // Little-known trick!
            element.onselectstart = function() { return false; }
            element.ondrag = function() { return false; }
        } else {
            // Prevent selection of text in FF
            element.style['MozUserSelect'] = 'none';
            //element.onmousedown = function() { return false; }  // FF on the Mac
        }
    },

    removeElement: function(element) {
        if (element) {
            for(var i = (element.childNodes.length - 1); i >= 0; i--)
                ADX.Dom.removeElement(element.childNodes[i]);

            if (element.parentNode) {
                element.parentNode.removeChild(element);
            }
        }
    },

    removeChildNodes: function(element) {
        if (element) {
            for(var i = (element.childNodes.length - 1); i >= 0; i--)
                ADX.Dom.removeElement(element.childNodes[i]);
        }
    },

    camelize: function(propertyName) {
        var words = propertyName.split('-');

        var result = words[0];

        var numWords = words.length;
        for(var i = 1; i < numWords; i++) {
            var word = words[i];
            result += word.charAt(0).toUpperCase() +
                      word.substr(1, word.length - 1);
        }

        return result;
    },

    getStyle: function(element, property) {
        var value;
        if (element.currentStyle) {
            if (this.propertyNames[property]) {
                property = this.propertyNames[property];
            } else {
                property = this.propertyNames[property] = this.camelize(property);
            }
            value = element.currentStyle[property];
        } else if (window.getComputedStyle) {
            value = document.defaultView.getComputedStyle(element, null).getPropertyValue(property);
        }

        return value;
    },

    hasAttribute: function(element, attributeName) {
        var element = $(element);

        if (!element) {
            return false;
        }

        var attribute = element.getAttributeNode('splitType');

        return (attribute ? true : false);
    },

    hasClass: function(elementId, className) {
        var element = $(elementId);

        if (!element) {
            return false;
        }

        var classNames = element.className.split(' ');

        for(var i = 0; i < classNames.length; i++) {
            if (classNames[i] == className) {
                return true;
            }
        }

        return false;
    },

    isClass: function(something, className) {
        var elm = $(something);
        if (!elm) { return false; }

        if (className == '*') { return true; }
        if (className == '') { return false; }

        if (!elm.className) { return false; }

        var arr = elm.className.split(' ');
        var index = arr.indexOf(className);

        return (index != -1);
    },

    setClass: function(something, className) {
        var elm = $(something);

        if (!elm) { return; }
        if (ADX.Dom.isClass(elm, className)) { return; }

        elm.className = className;
    },

    addClass: function(something, className) {
        var elm = $(something);

        if (!elm) { return; }
        if (ADX.Dom.isClass(elm, className)) { return; }

        var arr = elm.className.split(' ');
        arr.push(className);

        if (arr[0] == '') { arr.splice(0, 1); }

        elm.className = arr.join(' ');
    },

    removeClass: function(something, className) {
        var elm = $(something);

        if (!elm) { return; }

        if (!ADX.Dom.isClass(elm, className)) { return; } // cannot remove non-existing class
        if (className == '*') { elm.className = ''; } // should not occur

        var arr = elm.className.split(' ');
        var index = arr.indexOf(className);

        if (index == -1) { return; } // should NOT occur!
        arr.splice(index,1);
        elm.className = arr.join(' ');
    },

    getElementsByClass: function(className) {
        var elements = document.getElementsByTagName('*')  ||  document.all;
        var a = [];
      
        for(var i = 0; i < elements.length; i++) {
            var element = elements[i];
            var classNames = element.className.split(' ');
            for(var j = 0; j < classNames.length; j++) {
                if (classNames[j] == className) {
                    a.push(element);
                    break;
                }
            }
        }
      
        return a;
    },

    // This function allows us to dynamically create an iframe without a frame border
    // This accounts for a bug in IE
    createIFrame: function(src, width, height, scrolling) {
        if (!scrolling) scrolling = 'no';

        if (ADX.Browser.ie) {
            var html = '<iframe ';
            html += 'src="' + src + '" ';
            html += 'frameborder="0" ';
            html += 'scrolling="' + scrolling + '" ';
            html += 'allowtransparency="true"';
            html += 'width="' + width + '" ';
            html += 'height="' + height + '">';
            html += '</iframe>';

            var s = document.createElement('span');
            s.innerHTML = html;
            return s.firstChild;
        } else {
            var iframe = document.createElement('iframe');
            iframe.setAttribute('src', src);
            iframe.setAttribute('frameborder', '0');
            iframe.setAttribute('scrolling', 'no');
            iframe.setAttribute('allowtransparency', 'true');
            iframe.setAttribute('width', width);
            iframe.setAttribute('height', height);
            iframe.style.border = 'none';

            return iframe;
        }
    },

    createTable: function(cellPadding, cellSpacing, border) {
        if (!cellPadding) cellPadding = '0';
        if (!cellSpacing) cellSpacing = '0';
        if (!border) border = '0';

        if (ADX.Browser.ie) {
            var html = '<table';
            html += ' cellpadding="' + cellPadding + '"';
            html += ' cellspacing="' + cellSpacing + '"';
            html += ' border="' + border + '"';
            html += '></table>';

            var s = document.createElement('span');
            s.innerHTML = html;
            return s.firstChild;
        } else {
            var table = document.createElement('table');
            table.setAttribute('cellpadding', cellPadding);
            table.setAttribute('cellspacing', cellSpacing);
            table.setAttribute('border', border);
            return table;
        }
    },

    getMouseX: function(evt) {
        return this.getScrollLeft() + evt.clientX;
    },

    getMouseY: function (evt) {
        return this.getScrollTop() + evt.clientY;
    },

    getLocalMouseX: function(evt) {
        var node = ADX.Event.getSource(evt);
        return this.getMouseX(evt) - this.findLeft(node);
    },

    getLocalMouseY: function(evt) {
        var node = ADX.Event.getSource(evt);
        return this.getMouseY(evt) - this.findTop(node);
    },

    getScrollLeft: function() {
        return (document.documentElement.scrollLeft ?
                document.documentElement.scrollLeft :
                document.body.scrollLeft);
    },

    getScrollTop: function() {
        return (document.documentElement.scrollTop ?
                document.documentElement.scrollTop :
                document.body.scrollTop);
    },

    findLeft: function(elt, container) {
        var left = 0;
        if (elt.offsetParent) {
            while(elt.offsetParent && elt !== container) {
                left += elt.offsetLeft;
                elt = elt.offsetParent;
            }
        } else if (elt.x) {
            left += elt.x;
        }

        return left;
    },

    findTop: function(elt, container) {
        var top = 0;
        if (elt.offsetParent) {
            while (elt.offsetParent && elt !== container) {
                top += elt.offsetTop;
                elt = elt.offsetParent;
            }
        } else if (elt.y) {
            top += elt.y;
        }

        return top;
    },

    getWindowWidth: function() {
        return (window.innerWidth ? window.innerWidth : document.body.clientWidth);
    },

    getWindowHeight: function() {
        return (window.innerHeight ? window.innerHeight : document.body.clientHeight);
    },

    getLeft: function(elt) {
        if (elt === document.body) return 0;
        return (typeof elt.offsetLeft != 'undefined' ? elt.offsetLeft : elt.style.pixelLeft);
    },

    getRight: function(elt) {
        if (elt === document.body) return ADX.Dom.getWindowWidth();
        return ADX.Dom.getLeft(elt) + ADX.Dom.getWidth(elt);
    },

    getTop: function(elt) {
        if (elt === document.body) return 0;
        return (typeof elt.offsetTop != 'undefined' ? elt.offsetTop : elt.style.pixelTop);
    },

    getBottom: function(elt) {
        if (elt === document.body) return ADX.Dom.getWindowHeight();
        return ADX.Dom.getTop(elt) + ADX.Dom.getHeight(elt);
    },

    getWidth: function(elt) {
        if (elt === document.body) return ADX.Dom.getWindowWidth();

        var width = elt.offsetWidth ? elt.offsetWidth : elt.style.pixelWidth;

        if (width <= 0) {
            width = parseInt(elt.style.width);
            if (isNaN(width)) { width = 0; }
        }

        return width;
    },

    getContentWidth: function(elt) {
        return elt.scrollWidth;
    },

    getContentHeight: function(elt) {
        return elt.scrollHeight;
    },

    getHeight: function(elt) {
        if (elt === document.body) return ADX.Dom.getWindowHeight();

        var height = elt.offsetHeight ? elt.offsetHeight : elt.style.pixelHeight;

        if (height <= 0) {
            height = parseInt(elt.style.height);
            if (isNaN(height)) { height = 0; }
        }

        return height;
    },

    removeNode: function(node) {
        if (node) {
            for(var i = (node.childNodes.length - 1); i >= 0; i--) {
                ADX.Dom.removeElement(node.childNodes[i]);
            }
            node.parentNode.removeChild(node);
        }
    },

    getChildNode: function(node, tagName) {
        for(var i = 0; i < node.childNodes.length; i++) {
            var childNode = node.childNodes[i];

            if (childNode.tagName == tagName) {
                return childNode;
            }
        } 
        return null;
    },

    hasChildNode: function(node, tagName) {
        return (ADX.Dom.getChildNode(node, tagName) != null);
    },

    appendInnerText: function(node, text) {
        var textNode = document.createTextNode(text);
        node.appendChild(textNode);
    },

    setInnerText: function(node, text) {
        node.innerHTML = '';
        var textNode = document.createTextNode(text);
        node.appendChild(textNode);
    },

    setOpacity: function(node, opacity) {
        if (ADX.Browser.ie) {
            node.style.filter = 'alpha(opacity=' + Math.round(100 * opacity) + ')';
        } else {
            node.style.opacity = opacity;
        }
    },

    setClipRect: function(node, left, top, width, height) {
        var right = left + width;
        var bottom = top + height;
        node.style.clip = 'rect(' + top + 'px, ' + right + 'px, ' + bottom + 'px, ' + left + 'px)';
    },

    fixNodeSize: function(node) {
        if (ADX.Browser.ie) {
            var borderLeftWidth = 0;
            var borderRightWidth = 0;
            var borderTopWidth = 0;
            var borderBottomWidth = 0;

            if (node.currentStyle.borderLeftWidth) {
                borderLeftWidth = parseInt(node.currentStyle.borderLeftWidth);
                if (isNaN(borderLeftWidth)) { borderLeftWidth = 0; }
            }

            if (node.currentStyle.borderRightWidth) {
                borderRightWidth = parseInt(node.currentStyle.borderRightWidth);
                if (isNaN(borderRightWidth)) { borderRightWidth = 0; }
            }

            if (node.currentStyle.borderTopWidth) {
                borderTopWidth = parseInt(node.currentStyle.borderTopWidth);
                if (isNaN(borderTopWidth)) { borderTopWidth = 0; }
            }

            if (node.currentStyle.borderBottomWidth) {
                borderBottomWidth = parseInt(node.currentStyle.borderBottomWidth);
                if (isNaN(borderBottomWidth)) { borderBottomWidth = 0; }
            }

            var paddingLeft = parseInt(node.currentStyle.paddingLeft);
            var paddingRight = parseInt(node.currentStyle.paddingRight);

            var width = ADX.Dom.getWidth(node);
            width += borderLeftWidth + borderRightWidth + paddingLeft + paddingRight;

            node.style.width = width + 'px';

            var paddingTop = parseInt(node.currentStyle.paddingTop);
            var paddingBottom = parseInt(node.currentStyle.paddingBottom);

            var height = ADX.Dom.getHeight(node);
            height += borderTopWidth + borderBottomWidth + paddingTop + paddingBottom;

            node.style.height = height + 'px';
        }
    }
};

ADX.Color = {
    convertDecToHex: function(dec) {
        var digits = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');

        var n1 = Math.floor(dec / 16);
        var n2 = dec - (n1 * 16);
        var hex = digits[n1] + digits[n2];

        return hex;
    },

    convertRGBToHSV: function(r, g, b) {
        var max = Math.max(Math.max(r, g), b);
        var min = Math.min(Math.min(r, g), b);

        var h = 0;
        var s = 0;
        var v = max;
        if (max > 0) {
            s = (max-min) /  max;
        }

        if (max != min) {
            if (r == max) { h = (g-b) / (max-min) / 6;  }
            if (g == max) { h = (2.0 + (b-r) / (max-min)) / 6; }
            if (b == max) { h = (4.0 + (r-g) / (max-min)) / 6; }
        }

        if (h < 0) { h += 1; }

        return [h, s, v];
    },

    convertHSVToRGB: function (h, s, v) {
        var r, g, b;

        if (s == 0) {
           r = v * 255;
           g = v * 255;
           b = v * 255;
        } else {

           // h must be < 1
           var var_h = h * 6;
           if ( var_h == 6 ) {
               var_h = 0;
           }

           //Or ... var_i = floor( var_h )
           var var_i = Math.floor( var_h );
           var var_1 = v * ( 1 - s );
           var var_2 = v * ( 1 - s * ( var_h - var_i ) );
           var var_3 = v * ( 1 - s * ( 1 - ( var_h - var_i ) ) );

           if ( var_i == 0 ) { 
               var_r = v; 
               var_g = var_3; 
               var_b = var_1;
           } else if ( var_i == 1 ) { 
               var_r = var_2;
               var_g = v;
               var_b = var_1;
           } else if ( var_i == 2 ) {
               var_r = var_1;
               var_g = v;
               var_b = var_3
           } else if ( var_i == 3 ) {
               var_r = var_1;
               var_g = var_2;
               var_b = v;
           } else if ( var_i == 4 ) {
               var_r = var_3;
               var_g = var_1;
               var_b = v;
           } else { 
               var_r = v;
               var_g = var_1;
               var_b = var_2
           }

           r = var_r * 255                  //rgb results = 0 ÷ 255
           g = var_g * 255
           b = var_b * 255

        }

        return [Math.round(r), Math.round(g), Math.round(b)];
    },

    // Convert rgb to hsl (all values 0..1)
    convertRGBToHSL: function(r, g, b) {
        var max = Math.max(Math.max(r, g), b);
        var min = Math.min(Math.min(r, g), b);
        var l = (max + min) / 2;
        var h = 0;
        var s = 0;

        if (max != min) {
            if (l < .5) {
                s = (max - min) / (max + min);
            } else {
                s = (max - min) / (2 - max - min);
            }

            if (r == max) { h = (g-b) / (max-min) / 6;  }
            if (g == max) { h = (2.0 + (b-r) / (max-min)) / 6; }
            if (b == max) { h = (4.0 + (r-g) / (max-min)) / 6; }
        }

        if (h < 0) { h += 1; }

        return [h, s, l];
    },

    // Adapted from http://www.easyrgb.com/math.html
    // hsv values = 0 - 1
    // rgb values 0 - 255
    convertHSLToRGB: function(h, s, v) {
        var r, g, b;
        if ( s == 0 ) {
           r = v * 255;
           g = v * 255;
           b = v * 255;
        } else {

           // h must be < 1
           var var_h = h * 6;
           if ( var_h == 6 ) {
               var_h = 0;
           }

           //Or ... var_i = floor( var_h )
           var var_i = Math.floor( var_h );
           var var_1 = v * ( 1 - s );
           var var_2 = v * ( 1 - s * ( var_h - var_i ) );
           var var_3 = v * ( 1 - s * ( 1 - ( var_h - var_i ) ) );

           if ( var_i == 0 ) { 
               var_r = v; 
               var_g = var_3; 
               var_b = var_1;
           } else if ( var_i == 1 ) { 
               var_r = var_2;
               var_g = v;
               var_b = var_1;
           } else if ( var_i == 2 ) {
               var_r = var_1;
               var_g = v;
               var_b = var_3
           } else if ( var_i == 3 ) {
               var_r = var_1;
               var_g = var_2;
               var_b = v;
           } else if ( var_i == 4 ) {
               var_r = var_3;
               var_g = var_1;
               var_b = v;
           } else { 
               var_r = v;
               var_g = var_1;
               var_b = var_2
           }

           r = var_r * 255                  //rgb results = 0 ÷ 255
           g = var_g * 255
           b = var_b * 255

           }
        return [Math.round(r), Math.round(g), Math.round(b)];
    },

    // Convert hsl to rgb (all values 0..255)
    // h is {r,g,b}
    /*
    convertHSLToRGB: function(h, s, l) {
        
        var r = h.r * l / 255;
        var g = h.g * l / 255;
        var b = h.b * l / 255;

        var a = (r + g + b) / 3;


        var max = Math.max(r, g, b);
        var min = Math.min(r, g, b);
        var l = (max + min) / 2;
        var h = 0;
        var s = 0;

        if (max != min) {
            if (l < .5) {
                s = (max - min) / (max + min);
            } else {
               s = (max - min) / (2 - max - min);
            }

            if (r == max) { h = (g-b) / (max-min) / 6;  }
            if (g == max) { h = (2.0 + (b-r) / (max-min)) / 6; }
            if (b == max) { h = (4.0 + (r-g) / (max-min)) / 6; }
        }

        if (h < 0) { h += 1; }

        return [h, s, l];
    },
    */

    convertRGBToLuminosity: function(rgbStr) {
        var p1 = rgbStr.indexOf('(');
        var p2 = rgbStr.indexOf(')');
        var temp = rgbStr.substring(p1 + 1, p2);

        var rgb = temp.split(',');

        r = Math.min(parseInt(rgb[0]), 255);
        g = Math.min(parseInt(rgb[1]), 255);
        b = Math.min(parseInt(rgb[2]), 255);

        var max = Math.max(r, g, b);
        var min = Math.min(r, g, b);
        var luminosity = (max + min) / 2;

        return luminosity;
    },

    convertHexToLuminosity: function(hexStr) {
        var r = parseInt(hexStr.substring(1,3), 16);
        var g = parseInt(hexStr.substring(3,5), 16);
        var b = parseInt(hexStr.substring(5,7), 16);

        var max = Math.max(r, g, b);
        var min = Math.min(r, g, b);
        var luminosity = (max + min) / 2;

        return luminosity;
    },

    // Converts 'rgb(rr, gg, bb)' => '#RRGGBB' in hex
    convertRGBToHexColor: function(rgbStr) {
        var p1 = rgbStr.indexOf('(');
        var p2 = rgbStr.indexOf(')');
        var temp = rgbStr.substring(p1 + 1, p2);

        var rgb = temp.split(',');

        rgb[0] = Math.min(parseInt(rgb[0]), 255);
        rgb[1] = Math.min(parseInt(rgb[1]), 255);
        rgb[2] = Math.min(parseInt(rgb[2]), 255);

        var hexR = convertDecToHex(rgb[0]);
        var hexG = convertDecToHex(rgb[1]);
        var hexB = convertDecToHex(rgb[2]);

        hexR = (hexR.length == 1 ? '0' + hexR : hexR);
        hexG = (hexG.length == 1 ? '0' + hexG : hexG);
        hexB = (hexB.length == 1 ? '0' + hexB : hexB);

        var hexColor = '#' + hexR + hexG + hexB;

        return hexColor;
    },

    // Converts '#RRGGBB' => 'rgb(rr, gg, bb)'
    convertHexToRGBColor: function(hexStr) {
        var r = parseInt(hexStr.substring(1,3), 16);
        var g = parseInt(hexStr.substring(3,5), 16);
        var b = parseInt(hexStr.substring(5,7), 16);

        var rgbColor = 'rgb(' + r + ',' + g + ',' + b + ')';

        return rgbColor;
    }
};