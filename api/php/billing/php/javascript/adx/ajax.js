var Ajax = {}

Ajax.RequestMethod = {
    GET: 1,
    POST: 2
}

Ajax.RequestTimeout = 20000;

Ajax.ReadyState = {
    UNINITIALIZED: 0,  /* before the XMLHttpRequest object begins */
    LOADING: 1,        /* once the XMLHttpRequest object has been initialized */
    LOADED: 2,         /* once the XMLHttpRequest object has received a response from the server */
    INTERACTIVE: 3,    /* while the XMLHttpRequest object is connected to the server */
    COMPLETE: 4        /* after the XMLHttpRequest object is finished working */
}

Ajax.Request = function(requestMethod, requestData, callback, timeoutCB, url) {
    this.requestMethod = requestMethod;
    this.requestData = requestData;
    this.callback = callback;
    this.timeoutCB = timeoutCB;
    this.url = url;
    this.timestamp = new Date();
    this.isProcessing = false;
}

Ajax.Client = function(defaultUrl) {
    this.defaultUrl = defaultUrl;
    this._xhr = this._createXHR();
    this._requestQueue = new Array();

    // Event handlers
    this.onRequest = null;
    this.onUninitialized = null;
    this.onLoading = null;
    this.onLoaded = null;
    this.onInteractive = null;
    this.onComplete = null;
    this.onError = null;
}

Ajax.Client.prototype._createXHR = function() {
    var xhr = null;

    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xhr = new ActiveXObject('Microsoft.XMLHTTP');
    }

    return xhr;
}

Ajax.Client.prototype.doGetRequest = function(requestData, callback, timeoutCB, url) {
    this.sendRequest(Ajax.RequestMethod.GET, requestData, callback, timeoutCB, url);
}

Ajax.Client.prototype.doPostRequest = function(requestData, callback, timeoutCB, url) {
    this.sendRequest(Ajax.RequestMethod.POST, requestData, callback, timeoutCB, url);
}

Ajax.Client.prototype.sendRequest = function(requestMethod, requestData, responseCallback, timeoutCB, url) {
    var request = new Ajax.Request(requestMethod, requestData, responseCallback, timeoutCB, url);
    this._requestQueue.push(request);

    if (this.onRequest) { this.onRequest(); }

    switch(this._xhr.readyState) {
        case undefined:
        case Ajax.ReadyState.UNINITIALIZED:
        case Ajax.ReadyState.COMPLETE:
            this._processNextRequest();
            break;
    }
}

Ajax.Client.prototype.getQueuedRequests = function() {
    return this._requestQueue.length;
}

Ajax.Client.prototype._processNextRequest = function() {
    try {
        if (this._requestQueue.length > 0) {
            var request = this._requestQueue[0];

            if (request.isProcessing) return;

            request.isProcessing = true;

            var url = (request.url ? request.url : this.defaultUrl);

            if (request.requestMethod == Ajax.RequestMethod.GET) {
                if (request.requestData.length > 0) { url += '?' + request.requestData; }

                this._xhr.open('GET', url, true);
                this._xhr.onreadystatechange = this._processStateChange.bind(this);
                this._xhr.send(null);
            }
            else if (request.requestMethod == Ajax.RequestMethod.POST) {
                this._xhr.open('POST', url, true);
                this._xhr.onreadystatechange = this._processStateChange.bind(this);
                this._xhr.send(request.requestData);
            }

            if (request.timeoutCB) {
                this.timerID = window.setTimeout(request.timeoutCB, Ajax.RequestTimeout);
            }
        }
    } catch(e) {
        var request = this._requestQueue[0];
        //Debug.log('AJAX exception: e=' + e + ' request.requestData=' + request.requestData);

        // Something went wrong, so let's create a new XmlHttpRequest object and start over
        if (this.onError) { this.onError(); }

        this._xhr = this._createXHR();

        this._processNextRequest();
    }
}

Ajax.Client.prototype._processStateChange = function() {
    switch(this._xhr.readyState) {
        case Ajax.ReadyState.UNINITIALIZED:
            if (this.onUninitialized) { this.onUninitialized(); }
            break;

        case Ajax.ReadyState.LOADING:
            if (this.onLoading) { this.onLoading(); }
            break;

        case Ajax.ReadyState.LOADED:
            if (this.onLoaded) { this.onLoaded(); }
            break;

        case Ajax.ReadyState.INTERACTIVE:
            if (this.onInteractive) { this.onInteractive(); }
            break;

        case Ajax.ReadyState.COMPLETE:
            if (this.onComplete) { this.onComplete(); }
            this._processResponse();
            break;
    }
}

function evalJSONText(text) {

    var temp = text.replace(/\n/, "|n|");


}

Ajax.Client.prototype._processResponse = function() {
  //  try {
        if (this._xhr.status == 200) {
            if (this.timerID) {
                window.clearTimeout(this.timerID);
                delete this.timerID;
            }

            var request = this._requestQueue[0];

            if (request.callback != null) {
                var responseText = trim(this._xhr.responseText);

                var response = eval('(' + responseText + ')');
                request.callback(response);
            }

            this._requestQueue.shift();

            // Avoid memory leak in MSIE: clean up the onComplete event handler
            this._xhr.onreadystatechange = function() {};

            // Must not reenter Ajax. Schedule next request in 10ms
            window.setTimeout(this._processNextRequest.bind(this), 10);

        } else {
            // There was an error retrieving the data
        }
    //} catch(e) {
        // Something went wrong, so let's create a new XmlHttpRequest object and start over
//        if (this.onError) { this.onError(); }


//        this._xhr = this._createXHR();

        // Must not reenter Ajax. Schedule next request in 10ms
//        window.setTimeout(this._processNextRequest.bind(this), 10);
    //}
}

