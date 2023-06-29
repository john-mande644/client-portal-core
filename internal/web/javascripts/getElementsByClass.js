function getElementsByClass(className, tagName, parentNode) {
    parentNode = !isUndefined(parentNode)? getElem(parentNode) : document;
    if (isUndefined(tagName)) tagName = '*';
    return filter(parentNode.getElementsByTagName(tagName),
            function(elem) {
                return hasClass(elem, className)
            });
}

if (!Array.prototype.push) Array.prototype.push = function() {
    for (var i = 0; i < arguments.length; i++) this[this.length] = arguments[i];
    return this.length;
}

Array.prototype.find = function(value, start) {
    start = start || 0;
    for (var i = start; i < this.length; i++)
        if (this[i] == value)
            return i;
    return -1;
}

Array.prototype.has = function(value) {
    return this.find(value) !== -1;
}

function isUndefined(v) {
    var undef;
    return v === undef;
}

function filter(list, func) {
    var result = [];
    func = func || function(v) {
        return v
    };
    map(list, function(v) {
        if (func(v)) result.push(v)
    });
    return result;
}

function map(list, func) {
    var result = [];
    func = func || function(v) {
        return v
    };
    for (var i = 0; i < list.length; i++) result.push(func(list[i], i, list));
    return result;
}


function getElem(elem) {
    if (document.getElementById) {
        if (typeof elem == "string") {
            elem = document.getElementById(elem);
            if (elem === null) throw 'cannot get element: element does not exist';
        } else if (typeof elem != "object") {
            throw 'cannot get element: invalid datatype';
        }
    } else throw 'cannot get element: unsupported DOM';
    return elem;
}

function hasClass(elem, className) {
    return getElem(elem).className.split(' ').has(className);
}