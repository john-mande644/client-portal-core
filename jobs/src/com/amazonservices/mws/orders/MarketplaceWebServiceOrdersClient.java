/*******************************************************************************
 *  Copyright 2008-2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 * 
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations under the License.
 * *****************************************************************************
 *    __  _    _  ___
 *   (  )( \/\/ )/ __)
 *   /__\ \    / \__ \
 *  (_)(_) \/\/  (___/
 * 
 *  Marketplace Web Service Orders Java Library
 *  API Version: 2011-01-01
 *  Generated: Tue Jan 25 00:59:48 UTC 2011
 * 
 */

package com.amazonservices.mws.orders;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.HttpsURL;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonservices.mws.orders.model.ErrorResponse;
import com.amazonservices.mws.orders.model.FulfillmentChannelEnum;
import com.amazonservices.mws.orders.model.FulfillmentChannelList;
import com.amazonservices.mws.orders.model.GetOrderRequest;
import com.amazonservices.mws.orders.model.GetOrderResponse;
import com.amazonservices.mws.orders.model.GetServiceStatusRequest;
import com.amazonservices.mws.orders.model.GetServiceStatusResponse;
import com.amazonservices.mws.orders.model.ListOrderItemsByNextTokenRequest;
import com.amazonservices.mws.orders.model.ListOrderItemsByNextTokenResponse;
import com.amazonservices.mws.orders.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders.model.ListOrdersByNextTokenRequest;
import com.amazonservices.mws.orders.model.ListOrdersByNextTokenResponse;
import com.amazonservices.mws.orders.model.ListOrdersRequest;
import com.amazonservices.mws.orders.model.ListOrdersResponse;
import com.amazonservices.mws.orders.model.MarketplaceIdList;
import com.amazonservices.mws.orders.model.OrderIdList;
import com.amazonservices.mws.orders.model.OrderStatusEnum;
import com.amazonservices.mws.orders.model.OrderStatusList;
import com.amazonservices.mws.orders.model.PaymentMethodEnum;
import com.amazonservices.mws.orders.model.PaymentMethodList;

/**
 * This contains the Order Retrieval API section of the Marketplace Web Service.
 * 
 * 
 * 
 * MarketplaceWebServiceOrdersClient is implementation of
 * MarketplaceWebServiceOrders based on the Apache <a
 * href="http://jakarta.apache.org/commons/httpclient/">HttpClient</a>.
 * 
 */
public class MarketplaceWebServiceOrdersClient implements
MarketplaceWebServiceOrders {
private final static Logger log =  LogManager.getLogger();


    private String awsAccessKeyId = null;
    private String awsSecretAccessKey = null;
    private MarketplaceWebServiceOrdersConfig config = null;
    private HttpClient httpClient = null;
    private static JAXBContext jaxbContext;
    private static ThreadLocal<Unmarshaller> unmarshaller;
    private static Pattern ERROR_PATTERN_ONE = Pattern
    .compile(
            ".*\\<RequestId>(.*)\\</RequestId>.*\\<Error>"
            + "\\<Code>(.*)\\</Code>\\<Message>(.*)\\</Message>\\</Error>.*(\\<Error>)?.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    private static Pattern ERROR_PATTERN_TWO = Pattern
    .compile(
            ".*\\<Error>\\<Code>(.*)\\</Code>\\<Message>(.*)"
            + "\\</Message>\\</Error>.*(\\<Error>)?.*\\<RequestID>(.*)\\</RequestID>.*",
            Pattern.MULTILINE | Pattern.DOTALL);
    private static String DEFAULT_ENCODING = "UTF-8";
    /** Initialize JAXBContext and Unmarshaller **/
    static {
        try {
            jaxbContext = JAXBContext.newInstance(
                    "com.amazonservices.mws.orders.model",
                    MarketplaceWebServiceOrders.class.getClassLoader());
        } catch (JAXBException ex) {
            throw new ExceptionInInitializerError(ex);
        }
        unmarshaller = new ThreadLocal<Unmarshaller>() {
            @Override
            protected synchronized Unmarshaller initialValue() {
                try {
                    return jaxbContext.createUnmarshaller();
                } catch (JAXBException e) {
                    throw new ExceptionInInitializerError(e);
                }
            }
        };
    }

    /**
     * Constructs MarketplaceWebServiceOrdersClient with AWS Access Key ID and
     * AWS Secret Key
     * 
     * @param awsAccessKeyId
     *            AWS Access Key ID
     * @param awsSecretAccessKey
     *            AWS Secret Access Key
     * @param applicationName
     *            Application Name
     * @param applicationVersion
     *            Application Version
     */
    public MarketplaceWebServiceOrdersClient(String awsAccessKeyId,
            String awsSecretAccessKey, String applicationName,
            String applicationVersion) {
        this(awsAccessKeyId, awsSecretAccessKey, applicationName,
                applicationVersion, new MarketplaceWebServiceOrdersConfig());
    }

    /**
     * Constructs MarketplaceWebServiceOrdersClient with AWS Access Key ID, AWS
     * Secret Key and MarketplaceWebServiceOrdersConfig. Use
     * MarketplaceWebServiceOrdersConfig to pass additional configuration that
     * affects how service is being called.
     * 
     * @param awsAccessKeyId
     *            AWS Access Key ID
     * @param awsSecretAccessKey
     *            AWS Secret Access Key
     * @param applicationName
     *            Application Name
     * @param applicationVersion
     *            Application Version
     * @param config
     *            Additional configuration options
     */
    public MarketplaceWebServiceOrdersClient(String awsAccessKeyId,
            String awsSecretAccessKey, String applicationName,
            String applicationVersion, MarketplaceWebServiceOrdersConfig config) {
        this.awsAccessKeyId = awsAccessKeyId;
        this.awsSecretAccessKey = awsSecretAccessKey;
        this.config = config;
        this.httpClient = configureHttpClient(applicationName,
                applicationVersion);
    }

    /**
     * Remove all leading whitespace, trailing whitespace, repeated whitespace
     * and replace any interior whitespace with a single space
     */
    private static String clean(String s) {
        return s.replaceAll("\\s", " ").replaceAll(" {2,}", " ").trim();
    }

    public static String quoteAppName(String s) {
        return clean(s).replace("\\", "\\\\").replace("/", "\\/");
    }

    public static String quoteAppVersion(String s) {
        return clean(s).replace("\\", "\\\\").replace("(", "\\(");
    }

    public static String quoteAttributeName(String s) {
        return clean(s).replace("\\", "\\\\").replace("=", "\\=");
    }

    public static String quoteAttributeValue(String s) {
        return clean(s).replace("\\", "\\\\").replace(";", "\\;").replace(")",
        "\\)");
    }

    private static final String clientVersion = "2011-03-01";

    // Public API ------------------------------------------------------------//

    /**
     * List Orders By Next Token
     * 
     * If ListOrders returns a nextToken, thus indicating that there are more
     * orders than returned that matched the given filter criteria,
     * ListOrdersByNextToken can be used to retrieve those other orders using
     * that nextToken.
     * 
     * @param request
     *            ListOrdersByNextTokenRequest request
     * @return ListOrdersByNextToken Response from the service
     * 
     * @throws MarketplaceWebServiceOrdersException
     */
    public ListOrdersByNextTokenResponse listOrdersByNextToken(
            ListOrdersByNextTokenRequest request)
    throws MarketplaceWebServiceOrdersException {
        return invoke(ListOrdersByNextTokenResponse.class,
                convertListOrdersByNextToken(request));
    }

    /**
     * List Order Items By Next Token
     * 
     * If ListOrderItems cannot return all the order items in one go, it will
     * provide a nextToken. That nextToken can be used with this operation to
     * retrive the next batch of items for that order.
     * 
     * @param request
     *            ListOrderItemsByNextTokenRequest request
     * @return ListOrderItemsByNextToken Response from the service
     * 
     * @throws MarketplaceWebServiceOrdersException
     */
    public ListOrderItemsByNextTokenResponse listOrderItemsByNextToken(
            ListOrderItemsByNextTokenRequest request)
    throws MarketplaceWebServiceOrdersException {
        return invoke(ListOrderItemsByNextTokenResponse.class,
                convertListOrderItemsByNextToken(request));
    }

    /**
     * Get Order
     * 
     * This operation takes up to 50 order ids and returns the corresponding
     * orders.
     * 
     * @param request
     *            GetOrderRequest request
     * @return GetOrder Response from the service
     * 
     * @throws MarketplaceWebServiceOrdersException
     */
    public GetOrderResponse getOrder(GetOrderRequest request)
    throws MarketplaceWebServiceOrdersException {
        return invoke(GetOrderResponse.class, convertGetOrder(request));
    }

    /**
     * List Order Items
     * 
     * This operation can be used to list the items of the order indicated by
     * the given order id (only a single Amazon order id is allowed).
     * 
     * @param request
     *            ListOrderItemsRequest request
     * @return ListOrderItems Response from the service
     * 
     * @throws MarketplaceWebServiceOrdersException
     */
    public ListOrderItemsResponse listOrderItems(ListOrderItemsRequest request)
    throws MarketplaceWebServiceOrdersException {
        return invoke(ListOrderItemsResponse.class,
                convertListOrderItems(request));
    }

    /**
     * List Orders
     * 
     * ListOrders can be used to find orders that meet the specified criteria.
     * 
     * @param request
     *            ListOrdersRequest request
     * @return ListOrders Response from the service
     * 
     * @throws MarketplaceWebServiceOrdersException
     */
    public ListOrdersResponse listOrders(ListOrdersRequest request)
    throws MarketplaceWebServiceOrdersException {
        return invoke(ListOrdersResponse.class, convertListOrders(request));
    }

    /**
     * Get Service Status
     * 
     * Returns the service status of a particular MWS API section. The operation
     * takes no input. All API sections within the API are required to implement
     * this operation.
     * 
     * @param request
     *            GetServiceStatusRequest request
     * @return GetServiceStatus Response from the service
     * 
     * @throws MarketplaceWebServiceOrdersException
     */
    public GetServiceStatusResponse getServiceStatus(
            GetServiceStatusRequest request)
    throws MarketplaceWebServiceOrdersException {
        return invoke(GetServiceStatusResponse.class,
                convertGetServiceStatus(request));
    }

    // Private API
    // ------------------------------------------------------------//

    /**
     * Configure HttpClient with set of defaults as well as configuration from
     * MarketplaceWebServiceOrdersConfig instance
     * 
     */
    private HttpClient configureHttpClient(String applicationName,
            String applicationVersion) {

        /* Set http client parameters */
        HttpClientParams httpClientParams = new HttpClientParams();
        if (config.getUserAgent() == null) {
            config.setUserAgent(quoteAppName(applicationName),
                    quoteAppVersion(applicationVersion),
                    quoteAttributeValue("Java/"
                            + System.getProperty("java.version") + "/"
                            + System.getProperty("java.class.version") + "/"
                            + System.getProperty("java.vendor")),

                            quoteAttributeName("Platform"), quoteAttributeValue(""
                                    + System.getProperty("os.name") + "/"
                                    + System.getProperty("os.arch") + "/"
                                    + System.getProperty("os.version")),

                                    quoteAttributeName("MWSClientVersion"),
                                    quoteAttributeValue(clientVersion));

        }
        httpClientParams.setParameter(HttpMethodParams.USER_AGENT, config
                .getUserAgent());
        httpClientParams.setParameter(HttpClientParams.RETRY_HANDLER,
                new HttpMethodRetryHandler() {

            public boolean retryMethod(HttpMethod method,
                    IOException exception, int executionCount) {
                if (executionCount > 3) {
                    log
                    .debug("Maximum Number of Retry attempts reached, will not retry");
                    return false;
                }
                log
                .debug("Retrying request. Attempt "
                        + executionCount);
                if (exception instanceof NoHttpResponseException) {
                    log.debug("Retrying on NoHttpResponseException");
                    return true;
                }
                if (exception instanceof InterruptedIOException) {
                    log.debug(
                            "Will not retry on InterruptedIOException",
                            exception);
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    log.debug("Will not retry on UnknownHostException",
                            exception);
                    return false;
                }
                if (!method.isRequestSent()) {
                    log.debug("Retrying on failed sent request");
                    return true;
                }
                return false;
            }
        });

        /* Set host configuration */
        HostConfiguration hostConfiguration = new HostConfiguration();

        /* Set connection manager parameters */
        HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
        connectionManagerParams.setConnectionTimeout(50000);
        connectionManagerParams.setSoTimeout(50000);
        connectionManagerParams.setStaleCheckingEnabled(true);
        connectionManagerParams.setTcpNoDelay(true);
        connectionManagerParams.setMaxTotalConnections(config
                .getMaxConnections());
        connectionManagerParams.setMaxConnectionsPerHost(hostConfiguration,
                config.getMaxConnections());

        /* Set connection manager */
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setParams(connectionManagerParams);

        /* Set http client */
        httpClient = new HttpClient(httpClientParams, connectionManager);

        /* Set proxy if configured */
        if (config.isSetProxyHost() && config.isSetProxyPort()) {
            log.info("Configuring Proxy. Proxy Host: " + config.getProxyHost()
                    + "Proxy Port: " + config.getProxyPort());
            hostConfiguration.setProxy(config.getProxyHost(), config
                    .getProxyPort());
            if (config.isSetProxyUsername() && config.isSetProxyPassword()) {
                httpClient.getState()
                .setProxyCredentials(
                        new AuthScope(config.getProxyHost(), config
                                .getProxyPort()),
                                new UsernamePasswordCredentials(config
                                        .getProxyUsername(), config
                                        .getProxyPassword()));

            }
        }

        httpClient.setHostConfiguration(hostConfiguration);
        return httpClient;
    }

    /**
     * Invokes request using parameters from parameters map. Returns response of
     * the T type passed to this method
     */
    private <T> T invoke(Class<T> clazz, Map<String, String> parameters)
    throws MarketplaceWebServiceOrdersException {

        String actionName = parameters.get("Action");
        T response = null;
        String responseBodyString = null;
        PostMethod method = new PostMethod(config.getServiceURL());
        int status = -1;

        log.debug("Invoking" + actionName + " request. Current parameters: "
                + parameters);

        try {

            /* Set content type and encoding */
            log
            .debug("Setting content-type to application/x-www-form-urlencoded; charset="
                    + DEFAULT_ENCODING.toLowerCase());
            method.addRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset="
                    + DEFAULT_ENCODING.toLowerCase());
            /* Set X-Amazon-User-Agent to header */
            method.addRequestHeader("X-Amazon-User-Agent", config
                    .getUserAgent());

            /* Add required request parameters and set request body */
            log.debug("Adding required parameters...");
            addRequiredParametersToRequest(method, parameters);
            log
            .debug("Done adding additional required parameteres. Parameters now: "
                    + parameters);

            boolean shouldRetry = true;
            int retries = 0;
            do {
                log
                .debug("Sending Request to host:  "
                        + config.getServiceURL());

                try {

                    /* Submit request */
                    status = httpClient.executeMethod(method);

                    /* Consume response stream */
                    responseBodyString = getResponsBodyAsString(method
                            .getResponseBodyAsStream());

                    /*
                     * Successful response. Attempting to unmarshal into the
                     * <Action>Response type
                     */
                    if (status == HttpStatus.SC_OK
                            && responseBodyString != null) {
                        shouldRetry = false;
                        log.debug("Received Response. Status: " + status + ". "
                                + "Response Body: " + responseBodyString);
                        log.debug("Attempting to unmarshal into the "
                                + actionName + "Response type...");
                        response = clazz.cast(getUnmarshaller().unmarshal(
                                new StreamSource(new StringReader(
                                        responseBodyString))));

                        log.debug("Unmarshalled response into " + actionName
                                + "Response type.");

                    } else { /*
                     * Unsucessful response. Attempting to unmarshall
                     * into ErrorResponse type
                     */

                        log.debug("Received Response. Status: " + status + ". "
                                + "Response Body: " + responseBodyString);

                        if ((status == HttpStatus.SC_INTERNAL_SERVER_ERROR && pauseIfRetryNeeded(++retries))) {
                            shouldRetry = true;
                        } else {
                            log
                            .debug("Attempting to unmarshal into the ErrorResponse type...");
                            ErrorResponse errorResponse = (ErrorResponse) getUnmarshaller()
                            .unmarshal(
                                    new StreamSource(new StringReader(
                                            responseBodyString)));

                            log
                            .debug("Unmarshalled response into the ErrorResponse type.");

                            com.amazonservices.mws.orders.model.Error error = errorResponse
                            .getError().get(0);
                            if (status == HttpStatus.SC_SERVICE_UNAVAILABLE
                                    && !(error.getCode()
                                            .equals("RequestThrottled"))
                                            && pauseIfRetryNeeded(++retries)) {
                                shouldRetry = true;
                            } else {
                                shouldRetry = false;
                                throw new MarketplaceWebServiceOrdersException(
                                        error.getMessage(), status, error
                                        .getCode(), error.getType(),
                                        errorResponse.getRequestId(),
                                        errorResponse.toXML());
                            }
                        }
                    }
                } catch (JAXBException je) {
                    /*
                     * Response cannot be unmarshalled neither as
                     * <Action>Response or ErrorResponse types. Checking for
                     * other possible errors.
                     */

                    log.debug("Caught JAXBException", je);
                    log.debug("Response cannot be unmarshalled neither as "
                            + actionName + "Response or ErrorResponse types."
                            + "Checking for other possible errors.");

                    MarketplaceWebServiceOrdersException awse = processErrors(
                            responseBodyString, status);

                    throw awse;

                } catch (IOException ioe) {
                    log.debug("Caught IOException exception", ioe);
                    throw new MarketplaceWebServiceOrdersException(
                            "Internal Error", ioe);
                } catch (MarketplaceWebServiceOrdersException e) {
                    throw e;
                } catch (Exception e) {
                    log.debug("Caught Exception", e);
                    throw new MarketplaceWebServiceOrdersException(e);
                } finally {
                    method.releaseConnection();
                }
            } while (shouldRetry);

        } catch (MarketplaceWebServiceOrdersException se) {
            log.debug("Caught MarketplaceWebServiceOrdersException", se);
            throw se;

        } catch (Throwable t) {
            log.debug("Caught Exception", t);
            throw new MarketplaceWebServiceOrdersException(t);
        }
        return response;
    }

    /**
     * Read stream into string
     * 
     * @param input
     *            stream to read
     */
    private String getResponsBodyAsString(InputStream input) throws IOException {
        String responsBodyString = null;
        try {
            Reader reader = new InputStreamReader(input, DEFAULT_ENCODING);
            StringBuilder b = new StringBuilder();
            char[] c = new char[1024];
            int len;
            while (0 < (len = reader.read(c))) {
                b.append(c, 0, len);
            }
            responsBodyString = b.toString();
        } finally {
            input.close();
        }
        return responsBodyString;
    }

    /**
     * Exponential sleep on failed request. Sleeps and returns true if retry
     * needed
     * 
     * @param retries
     *            current retry
     * @throws java.lang.InterruptedException
     */
    private boolean pauseIfRetryNeeded(int retries) throws InterruptedException {
        if (retries <= config.getMaxErrorRetry()) {
            long delay = (long) (Math.pow(4, retries) * 100L);
            log.debug("Retriable error detected, will retry in " + delay
                    + "ms, attempt number: " + retries);
            Thread.sleep(delay);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add authentication related and version parameter and set request body
     * with all of the parameters
     */
    private void addRequiredParametersToRequest(PostMethod method,
            Map<String, String> parameters) throws SignatureException {
        parameters.put("Version", config.getServiceVersion());
        parameters.put("SignatureVersion", config.getSignatureVersion());
        parameters.put("Timestamp", getFormattedTimestamp());
        parameters.put("AWSAccessKeyId", this.awsAccessKeyId);
        parameters.put("Signature", signParameters(parameters,
                this.awsSecretAccessKey));
        for (Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // filter empty parameters
            if (key == null || key.equals("") || value == null
                    || value.equals("")) {
                continue;
            }
            method.addParameter(key, value);
        }
    }

    private MarketplaceWebServiceOrdersException processErrors(
            String responseString, int status) {
        MarketplaceWebServiceOrdersException ex = null;
        Matcher matcher = null;
        if (responseString != null && responseString.startsWith("<")) {
            matcher = ERROR_PATTERN_ONE.matcher(responseString);
            if (matcher.matches()) {
                ex = new MarketplaceWebServiceOrdersException(matcher.group(3),
                        status, matcher.group(2), "Unknown", matcher.group(1),
                        responseString);
            } else {
                matcher = ERROR_PATTERN_TWO.matcher(responseString);
                if (matcher.matches()) {
                    ex = new MarketplaceWebServiceOrdersException(matcher
                            .group(2), status, matcher.group(1), "Unknown",
                            matcher.group(4), responseString);
                } else {
                    ex = new MarketplaceWebServiceOrdersException(
                            "Internal Error", status);
                    log.debug("Service Error. Response Status: " + status);
                }
            }
        } else {
            ex = new MarketplaceWebServiceOrdersException("Internal Error",
                    status);
            log.debug("Service Error. Response Status: " + status);
        }
        return ex;
    }

    /**
     * Formats date as ISO 8601 timestamp
     */
    private String getFormattedTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(new Date());
    }

    /**
     * Computes RFC 2104-compliant HMAC signature for request parameters
     * Implements AWS Signature, as per following spec:
     * 
     * If Signature Version is 0, it signs concatenated Action and Timestamp
     * 
     * If Signature Version is 1, it performs the following:
     * 
     * Sorts all parameters (including SignatureVersion and excluding Signature,
     * the value of which is being created), ignoring case.
     * 
     * Iterate over the sorted list and append the parameter name (in original
     * case) and then its value. It will not URL-encode the parameter values
     * before constructing this string. There are no separators.
     * 
     * If Signature Version is 2, string to sign is based on following:
     * 
     * 1. The HTTP Request Method followed by an ASCII newline (%0A) 2. The HTTP
     * Host header in the form of lowercase host, followed by an ASCII newline.
     * 3. The URL encoded HTTP absolute path component of the URI (up to but not
     * including the query string parameters); if this is empty use a forward
     * '/'. This parameter is followed by an ASCII newline. 4. The concatenation
     * of all query string components (names and values) as UTF-8 characters
     * which are URL encoded as per RFC 3986 (hex characters MUST be uppercase),
     * sorted using lexicographic byte ordering. Parameter names are separated
     * from their values by the '=' character (ASCII character 61), even if the
     * value is empty. Pairs of parameter and values are separated by the '&'
     * character (ASCII code 38).
     * 
     */
    private String signParameters(Map<String, String> parameters, String key)
    throws SignatureException {

        String signatureVersion = parameters.get("SignatureVersion");
        String algorithm = "HmacSHA1";
        String stringToSign = null;
        if ("0".equals(signatureVersion)) {
            stringToSign = calculateStringToSignV0(parameters);
        } else if ("1".equals(signatureVersion)) {
            stringToSign = calculateStringToSignV1(parameters);
        } else if ("2".equals(signatureVersion)) {
            algorithm = config.getSignatureMethod();
            parameters.put("SignatureMethod", algorithm);
            stringToSign = calculateStringToSignV2(parameters);
        } else {
            throw new SignatureException("Invalid Signature Version specified");
        }
        log.debug("Calculated string to sign: " + stringToSign);
        return sign(stringToSign, key, algorithm);
    }

    /**
     * Calculate String to Sign for SignatureVersion 0
     * 
     * @param parameters
     *            request parameters
     * @return String to Sign
     * @throws java.security.SignatureException
     */
    private String calculateStringToSignV0(Map<String, String> parameters) {
        StringBuilder data = new StringBuilder();
        data.append(parameters.get("Action")).append(
                parameters.get("Timestamp"));
        return data.toString();
    }

    /**
     * Calculate String to Sign for SignatureVersion 1
     * 
     * @param parameters
     *            request parameters
     * @return String to Sign
     * @throws java.security.SignatureException
     */
    private String calculateStringToSignV1(Map<String, String> parameters) {
        StringBuilder data = new StringBuilder();
        Map<String, String> sorted = new TreeMap<String, String>(
                String.CASE_INSENSITIVE_ORDER);
        sorted.putAll(parameters);
        Iterator pairs = sorted.entrySet().iterator();
        while (pairs.hasNext()) {
            Map.Entry pair = (Map.Entry) pairs.next();
            data.append(pair.getKey());
            data.append(pair.getValue());
        }
        return data.toString();
    }

    /**
     * Calculate String to Sign for SignatureVersion 2
     * 
     * @param parameters
     *            request parameters
     * @return String to Sign
     * @throws java.security.SignatureException
     */
    private String calculateStringToSignV2(Map<String, String> parameters)
    throws SignatureException {
        StringBuilder data = new StringBuilder();
        data.append("POST");
        data.append("\n");
        URI endpoint = null;
        try {
            endpoint = new URI(config.getServiceURL().toLowerCase());
        } catch (URISyntaxException ex) {
            log.debug("URI Syntax Exception", ex);
            throw new SignatureException("URI Syntax Exception thrown "
                    + "while constructing string to sign", ex);
        }
        data.append(endpoint.getHost());
        if (!usesAStandardPort(config.getServiceURL())) {
            data.append(":");
            data.append(endpoint.getPort());
        }
        data.append("\n");
        String uri = "/Orders/2011-01-01";
        data.append(urlEncode(uri, true));
        data.append("\n");
        Map<String, String> sorted = new TreeMap<String, String>();
        sorted.putAll(parameters);
        Iterator<Map.Entry<String, String>> pairs = sorted.entrySet()
        .iterator();
        while (pairs.hasNext()) {
            Map.Entry<String, String> pair = pairs.next();
            String key = pair.getKey();
            data.append(urlEncode(key, false));
            data.append("=");
            String value = pair.getValue();
            data.append(urlEncode(value, false));
            if (pairs.hasNext()) {
                data.append("&");
            }
        }
        return data.toString();
    }

    private String urlEncode(String value, boolean path) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, DEFAULT_ENCODING).replace("+",
            "%20").replace("*", "%2A").replace("%7E", "~");
            if (path) {
                encoded = encoded.replace("%2F", "/");
            }
        } catch (UnsupportedEncodingException ex) {
            log.debug("Unsupported Encoding Exception", ex);
            throw new RuntimeException(ex);
        }
        return encoded;
    }

    /**
     * Computes RFC 2104-compliant HMAC signature.
     * 
     */
    private String sign(String data, String key, String algorithm)
    throws SignatureException {
        byte[] signature;
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.getBytes(), algorithm));
            signature = Base64.encodeBase64(mac.doFinal(data
                    .getBytes(DEFAULT_ENCODING)));
        } catch (Exception e) {
            throw new SignatureException("Failed to generate signature: "
                    + e.getMessage(), e);
        }

        return new String(signature);
    }

    /**
     * Get unmarshaller for current thread
     */
    private Unmarshaller getUnmarshaller() {
        return unmarshaller.get();
    }

    /**
     * Convert ListOrdersByNextTokenRequest to name value pairs
     */
    private Map<String, String> convertListOrdersByNextToken(
            ListOrdersByNextTokenRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "ListOrdersByNextToken");
        if (request.isSetSellerId()) {
            params.put("SellerId", request.getSellerId());
        }
        if (request.isSetNextToken()) {
            params.put("NextToken", request.getNextToken());
        }

        return params;
    }

    /**
     * Convert ListOrderItemsByNextTokenRequest to name value pairs
     */
    private Map<String, String> convertListOrderItemsByNextToken(
            ListOrderItemsByNextTokenRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "ListOrderItemsByNextToken");
        if (request.isSetSellerId()) {
            params.put("SellerId", request.getSellerId());
        }
        if (request.isSetNextToken()) {
            params.put("NextToken", request.getNextToken());
        }

        return params;
    }

    /**
     * Convert GetOrderRequest to name value pairs
     */
    private Map<String, String> convertGetOrder(GetOrderRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetOrder");
        if (request.isSetSellerId()) {
            params.put("SellerId", request.getSellerId());
        }
        if (request.isSetAmazonOrderId()) {
            OrderIdList amazonOrderId = request.getAmazonOrderId();
            java.util.List<String> idList = amazonOrderId.getId();
            int idListIndex = 1;
            for (String id : idList) {
                params
                .put("AmazonOrderId" + "." + "Id" + "." + idListIndex,
                        id);
                idListIndex++;
            }
        }

        return params;
    }

    /**
     * Convert ListOrderItemsRequest to name value pairs
     */
    private Map<String, String> convertListOrderItems(
            ListOrderItemsRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "ListOrderItems");
        if (request.isSetSellerId()) {
            params.put("SellerId", request.getSellerId());
        }
        if (request.isSetAmazonOrderId()) {
            params.put("AmazonOrderId", request.getAmazonOrderId());
        }

        return params;
    }

    /**
     * Convert ListOrdersRequest to name value pairs
     */
    private Map<String, String> convertListOrders(ListOrdersRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "ListOrders");
        if (request.isSetSellerId()) {
            params.put("SellerId", request.getSellerId());
        }
        if (request.isSetCreatedAfter()) {
            params.put("CreatedAfter", request.getCreatedAfter() + "");
        }
        if (request.isSetCreatedBefore()) {
            params.put("CreatedBefore", request.getCreatedBefore() + "");
        }
        if (request.isSetLastUpdatedAfter()) {
            params.put("LastUpdatedAfter", request.getLastUpdatedAfter() + "");
        }
        if (request.isSetLastUpdatedBefore()) {
            params
            .put("LastUpdatedBefore", request.getLastUpdatedBefore()
                    + "");
        }
        if (request.isSetOrderStatus()) {
            OrderStatusList orderStatus = request.getOrderStatus();
            java.util.List<OrderStatusEnum> statusList = orderStatus
            .getStatus();
            int statusListIndex = 1;
            for (OrderStatusEnum status : statusList) {
                params.put("OrderStatus" + "." + "Status" + "."
                        + statusListIndex, status.value());
                statusListIndex++;
            }
        }
        if (request.isSetMarketplaceId()) {
            MarketplaceIdList marketplaceId = request.getMarketplaceId();
            java.util.List<String> idList = marketplaceId.getId();
            int idListIndex = 1;
            for (String id : idList) {
                params
                .put("MarketplaceId" + "." + "Id" + "." + idListIndex,
                        id);
                idListIndex++;
            }
        }
        if (request.isSetFulfillmentChannel()) {
            FulfillmentChannelList fulfillmentChannel = request
            .getFulfillmentChannel();
            java.util.List<FulfillmentChannelEnum> channelList = fulfillmentChannel
            .getChannel();
            int channelListIndex = 1;
            for (FulfillmentChannelEnum channel : channelList) {
                params.put("FulfillmentChannel" + "." + "Channel" + "."
                        + channelListIndex, channel.value());
                channelListIndex++;
            }
        }
        if (request.isSetPaymentMethod()) {
            PaymentMethodList  paymentMethod = request.getPaymentMethod();
            java.util.List<PaymentMethodEnum> methodList  =  paymentMethod.getMethod();
            int methodListIndex = 1;
            for  (PaymentMethodEnum method : methodList) { 
                params.put("PaymentMethod" + "." + "Method" + "."  + methodListIndex, method.value());
                methodListIndex++;
            }    
        } 
        if (request.isSetBuyerEmail()) {
            params.put("BuyerEmail", request.getBuyerEmail());
        }
        if (request.isSetSellerOrderId()) {
            params.put("SellerOrderId", request.getSellerOrderId());
        }
        if (request.isSetMaxResultsPerPage()) {
            params
            .put("MaxResultsPerPage", request.getMaxResultsPerPage()
                    + "");
        }

        return params;
    }

    /**
     * Convert GetServiceStatusRequest to name value pairs
     */
    private Map<String, String> convertGetServiceStatus(
            GetServiceStatusRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Action", "GetServiceStatus");
        if (request.isSetSellerId()) {
            params.put("SellerId", request.getSellerId());
        }

        return params;
    }

    private static boolean usesHttps(String url) {
        try {
            new HttpsURL(url) /* throws an exception if not HTTPS */;
            return true;
        } catch (URIException e) {
            return false;
        }
    }

    private static int extractPortNumber(String url, boolean usesHttps) {
        try {
            HttpURL httpUrl = usesHttps ? new HttpsURL(url) : new HttpURL(url);
            return httpUrl.getPort();
        } catch (URIException e) {
            throw new RuntimeException("not a URL", e);
        }
    }

    private static boolean usesAStandardPort(String url) {
        boolean usesHttps = usesHttps(url);
        int portNumber = extractPortNumber(url, usesHttps);
        return usesHttps && portNumber == HttpsURL.DEFAULT_PORT || !usesHttps
        && portNumber == HttpURL.DEFAULT_PORT;
    }

    private String urlEncode(String rawValue) {
        return urlEncode(rawValue, false);
    }

}
