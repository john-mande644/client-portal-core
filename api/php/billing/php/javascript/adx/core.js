function isObject(obj) {
    return (typeof(src) == 'object');
}

function equivalent(src1, src2) {
    if (src1 == src2) return true;
    if (src1 == null || src2 == null) return false;
    if (typeof(src1) != typeof(src2)) return false;

    if (typeof(src1) != 'object') {
        return (src1 == src2);
    } else {
        for(i in src1) {
            if (!equivalent(src1[i], src2[i])) {
                return false;
            }
        }
        for(i in src2) {
            if (src1[i] == undefined) {
                return false;
            }
        }
    }

    return true;
}

function copy(src) {
    if (src == null || typeof(src) != 'object') {
        return src;
    }

    var c = {};
    for(var i in src) {
        c[i] = copy(src[i]);
    }

    return c;
}

function trim(str) {
    if (str) {
        return str.replace(/^\s*|\s*$/g, '');
    } else {
        return '';
    }
}

// These min/max values won't fail on Date objects like Math.min/max
function minValue(val1, val2) {
    return (val1 < val2 ? val1 : val2);
}

function maxValue(val1, val2) {
    return (val1 > val2 ? val1 : val2);
}

function stripTags(str) {
    var regExp = /<\/?[^>]+>/gi;
    str = str.replace(regExp, '');
    return str;
}

function $(something) {
    if (typeof(something) == 'string') {
        var elm = document.getElementById(something);
    } else {
        var elm = something;
    }

    if (!elm) return false;

    return elm;
}

var ADX = {};

ADX.Browser = {
    init: function() {
        var agent = navigator.userAgent.toLowerCase();

        this.mac = (agent.indexOf("mac") != -1);
        this.ie = (agent.indexOf("msie") != -1);
        this.safari = (agent.indexOf('safari') != -1);
        this.opera = (agent.indexOf('opera') != -1);
        this.firefox = (agent.indexOf('firefox') != -1);
    }
};

ADX.Browser.init();


if (ADX.Browser.ie) {
    console = {
        info: function(text) {
            Debug.log(text)
        }
    }
} else {
    console2 = {
        info: function(text) {
            Debug.log(text)
        }
    }
}

ADX.Class = {
    // Root class to all ADX classes
    Obj: function() {},
    
    // Create a new class
    create: function(definition) {
        // Just for convenience
        var name = definition.name;
        var base = definition.base;
        var statics = definition.statics;
        var members = definition.members;
      
        if (!name) {
            throw "ADX class must have a name";
        }
      
        // Create/assign constructor function from members
        var c;
        if (members && members.constructor) {
            c = members.constructor;
        } else {
            // Whoops! No constructor defined in members, so let's create one
            c = function() {};
        }

        // Assign prototype for inheritance
        if (!base) { base = ADX.Class.Obj; }
        var f = function() {}
        f.prototype = base.prototype;
        f.prototype.constructor = base;
        c.prototype = new f();
        c.prototype.baseClass = base;
        c.prototype.constructor = c;

        // Copy statics to class
        if (statics) {
            for(var i in statics) {
                c[i] = statics[i];
            }
        }

        // Copy members to prototype
        if (members) {
            for(var i in members) {
                c.prototype[i] = members[i];
            }
        }

        // Assign 'class' to name
        c.prototype[name] = c;

        // Assign class name
        //c.prototype.className = name;

        return c;
    }
};

ADX.KeyCode = {
    BACKSPACE: 8,
    TAB: 9,
    ENTER: 13,
    SHIFT: 16,
    CONTROL: 17,
    ALT: 18,
    ESCAPE: 27,
    SPACE_BAR: 32,
    PAGE_UP: 33,
    PAGE_DOWN: 34,
    END: 35,
    HOME: 36,
    LEFT_ARROW: 37,
    UP_ARROW: 38,
    RIGHT_ARROW: 39,
    DOWN_ARROW: 40,
    INSERT: 45,
    DELETE: 46,
    INS: 48,
    F1: 112,
    F2: 113,
    F3: 114,
    F4: 115,
    F5: 116,
    F6: 117,
    F7: 118,
    F8: 119,
    F9: 120,
    F10: 121,
    F11: 122,
    F12: 123,
    FORWARD_SLASH: 191,
    APOSTROPHE: 222
}

//////////////////////////////////////////////////////////////////////////////////////////
// Function extensions
Function.prototype.bind = function(object) {
    var self = this;
    return function() {
        return self.apply(object, arguments);
    }
}

Function.prototype.bindAsEventListener = function(object) {
    var self = this;

    return function(event) {
        return self.call(object, event || window.event);
    }
}

Function.prototype.bindEx = function(object) {
    var self = this;
    var args = new Array();
    args.pushArray(arguments, 1);
    return function() {
        var args2 = new Array();
        args2.pushArray(args, 0);
        args2.pushArray(arguments, 0);
        return self.apply(object, args2);
    }
}

Function.prototype.bindAsEventListenerEx = function(object) {
    var self = this;
    var args = new Array();
    args.pushArray(arguments, 1);

    return function(event) {
        var args2 = new Array();
        args2.push(event || window.event);
        args2.pushArray(args, 0);
        args2.pushArray(arguments, 0);
        return self.apply(object, args2);
    }
}


////////////////////////////////////////////////////////////////////////////////
// CustomEvent

ADX.CustomEvent = ADX.Class.create({
    name: 'CustomEvent',
    members: {
        constructor: function() {
            this.subscribers = [];
        },

        subscribe: function(callback) {
            this.subscribers.push(callback);
        },

        unsubscribe: function(callback) {
            for(var i = 0; i < this.subscribers.length; i++) {
                if (subscribers[i] === callback) {
                    subscribers.removeAt(i);
                    return;
                }
            }
        },

        unsubscribeAll: function(callback) {
            for(var i = 0; i < this.subscribers.length; i++) {
                if (subscribers[i] === callback) {
                    subscribers.removeAt(i);
                    return;
                }
            }        
        },

        fire: function() {
            var args = new Array();
            args.pushArray(arguments);

            for(var i = 0; i < this.subscribers.length; i++) {
                // Calling apply on the subscriber's callback function.
                // Note: using this pointer because apply() requires one,
                // but since subscribers will most likely use bind(), it will
                // affect the callback.
                this.subscribers[i].apply(this, args);
            }
        }
    }
});

////////////////////////////////////////////////////////////////////////////////
// Math extensions

Math.bound = function(val, min, max) {
    return Math.min(max, Math.max(min, val));
}

////////////////////////////////////////////////////////////////////////////////
// Array extensions

ADX.isArray = function(obj) {
    return !isNaN(obj.length);
}

Array.prototype.pushArray = function(items, start) {
    var length = items.length;
    if (!start) start = 0;

    if (length != 0) {
        for(var index = start; index < length; index++) {
            this.push(items[index]);
        }
    }
}

Array.prototype.clone = function() {
    var items = [];
    var length = this.length;

    for(var index = 0; index < length; index++) {
        items[index] = this[index];
    }

    return items;
}

Array.prototype.indexOf = function(item) {
    var length = this.length;

    if (length != 0) {
        for(var i = 0; i < length; i++) {
            if (this[i] == item) return i;
        }
    }

    return -1;
}

Array.prototype.contains = function(item) {
    var index = this.indexOf(item);

    return (index >= 0);
}

Array.prototype.insertAt = function(index, item) {
    this.splice(index, 0, item);
}

Array.prototype.swap = function(index1, index2) {
    var temp = this[index1];
    this[index1] = this[index2];
    this[index2] = temp;
}

Array.prototype.remove = function(item) {
    var index = this.indexOf(item);

    if (index >= 0) {
        this.splice(index, 1);
    }
}

Array.prototype.removeAt = function(index) {
    this.splice(index, 1);
}

Array.prototype.removeAll = function() {
    if (this.length > 0) {
        this.splice(0, this.length);
    }
}

Array.prototype.forEach = function(op) {
    for (var i in this) {
        var index = parseInt(i);
        if (index.toString() == i) {
            op(this[i]);
        }
    }
}

Array.prototype.getMatching = function(pred) {
    var items = new Array;
    this.forEach(function(item) {
        if (pred(item)) items.push(item);
    });
    return items;
}

////////////////////////////////////////////////////////////////////////////////
// Date extensions

function dateAdd(interval, number, date) {
   interval = interval.toLowerCase();

   var outDate = new Date();

   outDate.setYear(date.getYear());
   outDate.setMonth(date.getMonth());
   outDate.setDate(date.getDate());
   outDate.setHours(date.getHours());
   outDate.setMinutes(date.getMinutes());
   outDate.setSeconds(date.getSeconds());
   outDate.setMilliseconds(date.getMilliseconds());

   outDate.setUTCMilliseconds(date.getUTCMilliseconds());

   switch (interval) {
      case "yyyy": // year
         outDate.setFullYear(date.getFullYear() + number);
         break; 
      case "q": // quarter
         outDate.setMonth(date.getMonth() + (3 * number));
         break; 
      case "m": // month
         outDate.setMonth(date.getMonth() + number);
         break;
      case "y": // day of year
      case "d": // day
      case "w": // weekday
         outDate.setDate(date.getDate() + number);
         break; 
      case "ww": // week of year
         outDate.setDate(date.getDate() + (7 * number));
         break;
      case "h": // hour
         outDate.setHours(date.getHours() + number);
         break; 
      case "n": // minute
         outDate.setMinutes(date.getMinutes() + number);
         break; 
      case "s": // second
         outDate.setSeconds(date.getSeconds() + number);
         break; 
      case "ms": // milliseconds
         outDate.setMilliseconds(date.getMilliseconds() + number);
         break; 
      default:
         return null;
   }

   return outDate;
}

function dateDiff(interval, date1, date2) {
    interval = interval.toLowerCase();

    var diff;
    switch (interval) {
        case "d": // hour
            diff = (date2 - date1) / (24 * 60 * 60 * 1000);
        case "h": // hour
            diff = (date2 - date1) / (60 * 60 * 1000);
            break;
        case "n": // minute
            diff = (date2 - date1) / (60 * 1000);
            break;
        case "s": // second
            diff = (date2 - date1) / (1000);
            break;
        case "ms": // second
            diff = date2 - date1;
            break;
    }

    return Math.round(diff);
}

////////////////////////////////////////////////////////////////////////////////
// String extensions
ADX.isString = function(obj) {
    return (typeof obj == 'string');
}

ADX.isNullString = function(obj) {
    return (typeof obj == 'string' && obj == '');
}

String.prototype.trimLeft = function() {
    return this.replace(/^\s*/, '');
}

String.prototype.trimRight = function() {
    return this.replace(/\s*$/, '');
}

String.prototype.trim = function() {
    return this.replace(/^\s*|\s*$/g, '');
}

String.prototype.stripTags = function() {
    return this.replace(/<\/?[^>]+>/gi, '');
}

String.prototype.startsWith = function(prefix) {
    return (this.substr(0, prefix.length) == prefix);
}

String.prototype.endsWith = function(suffix) {
    return (this.substr(this.length - suffix.length) == suffix);
}

String.prototype.isLetter = function () {
    return (this >= 'a' && this <= 'z\uffff') ||
        (this >= 'A' && this <= 'Z\uffff');
}

String.prototype.isLowerCase = function () {
    return (this >= 'a' && this <= 'z\uffff');
}

String.prototype.isUpperCase = function () {
    return (this >= 'A' && this <= 'Z\uffff');
}

String.prototype.isDigit = function () {
    return (this >= '0' && this <= '9');
}

String.prototype.leftOf = function(str) {
    var pos = this.indexOf(str);
    return (pos >= 0 ? this.substr(0, pos) : '');
}

String.prototype.rightOf = function(str) {
    var pos = this.indexOf(str);
    var len = str.length;
    return (pos > 0 ? this.substr(pos+len) : '');
}

String.prototype.insert = function(str1, index, str2) {
    var left = str1.leftOf(index);
    var right = str1.rightOf(index);

    return left + str2 + right;
}

// This method should be extended to include other characters as well
String.prototype.compare = function(src) {
    var characters = ' 0123556789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ';

    var str1 = this;
    var str2 = src;

    if (str1 == str2) { return 0; }

    var minLength = Math.min(str1.length, str2.length);

    for(var i = 0; i < minLength; i++) {
        var index1 = characters.indexOf(str1[i]);
        var index2 = characters.indexOf(str2[i]);

        if (index1 < index2) { return -1; }
        if (index1 > index2) { return 1; }
    }

    if (str1.length == str2.length) {
        return 0;
    }
    else {
        return (str1.length < str2.length ? -1 : 1);
    }
}

////////////////////////////////////////////////////////////////////////////////
// Hash extensions
function Hash() {
    this.items = [];
    this.keys = [];
}

Hash.prototype.add = function(item, key) {
    if (item && key) {
        if (this.exists(key)) {
            this.remove(key);
        }

        this.items[key] = item;
        this.keys.push(key);
    }
}

Hash.prototype.insertAt = function(item, key, index) {
    if (item && key && (index >= 0)) {
        if (this.exists(key)) {
            this.remove(key);
        }

        this.items[key] = item;
        this.keys.insertAt(index, key);
    }
}

Hash.prototype.exists = function(key) {
    return (typeof(this.items[key]) == 'undefined' ? false : true);
}

Hash.prototype.keyAt = function(index) {
    return this.keys[index];
}

Hash.prototype.itemAt = function(index) {
    var key = this.keys[index];
    return this.items[key];
}

Hash.prototype.item = function(key) {
    return this.items[key];
}

Hash.prototype.count = function() {
    return this.keys.length;
}

Hash.prototype.removeAt = function(index) {
    var key = this.keys[index];
    this.remove(key);
}

Hash.prototype.remove = function(key) {
    if (this.exists(key)) {
        // Remove from items array
        delete this.items[key];

        // Remove from keys array
        var index = this.keys.indexOf(key);
        this.keys.removeAt(index);
    }
}

Hash.prototype.removeAll = function() {
    this.items = [];
    this.keys = [];
}

