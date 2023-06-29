package com.owd;

import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.SlackManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 4/7/14
 * Time: 4:06 PM
 * This class is designed to be used for exceptions found in importing order, shipment notification etc.
 *
 * The enum errorTypes contains errors that normally will be caught once and produce an error email, but on subsequent
 * occurrences of identical errors do not need to produce more email notifications. All errors will be logged in the
 * owd_order_error table.
 *
 * To qualify as a one time notification the error message must be on of the defined error types. If the key fields have
 * already been entered, (orderNum, clientId, errorType, errorMessage), then the error count will be incremented instead
 * of inserting a new record.
 */
public class LogableException extends RuntimeException {
private final static Logger log =  LogManager.getLogger();

    //Key fields properties
    private String orderNum = null;
    private String clientId = null;
    private String requestType = null;
    private String errorInfo = null;

    private errorTypes errorType = null;
    private String errorMessage = null;

    private int status = 0;

    public static final int DEFAULT = 0;
    public static final int ACKNOWLEDGED = 1;
    public static final int IGNORE = 2;


    private String hashValue = null;

    private static String mailTo = "servicerequests@owd.com";
    private static String mailFrom = "donotreply@owd.com";
    private static String errorListWebLink = "http://jobs.owd.com:8085/jobs/ordererrorlist.jsp";

//    private static final String UNDEFINED_ERROR = "Undefined Error";

    private int orderErrorId = 0;
    private static boolean test = false;

    /**
     *  Error Types that qualify as a one-time email error.
     */
    public static enum errorTypes {
        ORDER_IMPORT {
            public String toString() {
                return "ORDER IMPORT";
            }
        },
        SHIPMENT_NOTIFICATION {
            public String toString() {
                return "SHIPMENT NOTIFICATION";
            }
        },
        INTERNAL {
            public String toString() {
                return "INTERNAL";
            }
        },  ASN_IMPORT {
            public String toString() {
                return "ASN IMPORT";
            }
        }, BILLING {
            public String toString() {
                return "BILLING ISSUE";
            }
        }, INVENTORY {
            public String toString() {
                return "INVENTORY REPORT ISSUE";
            }
        },
        UNDEFINED_ERROR {
            public String toString() {
                return "Undefined Error";
            }
        } , REPORTS {
            public String toString() { return "REPORT ISSUE"; }
        }

    }

    /**
     *
     * @param args
     */
    public static void main(String[] args)  {
        test = true;

        new LogableException(new Exception(), "Test error - this is the title message", "anOrderReferenceOrTimestamp", "55", "Just Testing", errorTypes.UNDEFINED_ERROR);



//        throw new LogableException("Test error - order import", "aaa", "123", "JohnTest", errorTypes.UNDEFINED_ERROR);
//        throw new LogableException("Test error - order import", "aaa", "123", "JohnTest", errorTypes.UNDEFINED_ERROR);
//        log.debug(LogableException.class.getName());
   /*     Exception ex = new Exception();
        log.debug(OWDUtilities.getStackTraceAsString(ex));
        log.debug(OWDUtilities.getGroovyComparableStackTraceAsString(ex));*/
       // log.debug(hash("leepadg"));

    }


    /**
     * Constructor with key field inputs.
     *
     * @param errorMessage
     * @param orderNum
     * @param clientId
     * @param requestType
     */
    public LogableException(String errorMessage, String orderNum, String clientId, String requestType, errorTypes errorType) {

        try {
            setRequestType(requestType != null ? requestType : "Undetermined");
            setClientId(clientId);
            setOrderNum(orderNum);
            setErrorMessage(errorMessage != null ? errorMessage : this.toString());
            setErrorType(errorType);
            setHashValue(getHash(getClientId() + getOrderNum() + getErrorMessage() + getErrorType() + getRequestType() + OWDUtilities.getGroovyComparableStackTraceAsString(this)));

            if (isExistingError()) {
                updateErrorEntry();
            } else {
                createErrorEntry();
            }
        } catch (Exception e) {
            handleInternalError(e);
        }
    }


    public LogableException(String errorData, String errorMessage, String orderNum, String clientId, String requestType, errorTypes errorType) {

        try {
            setErrorInfo(errorData);
            setRequestType(requestType != null ? requestType : "Undetermined");
            setClientId(clientId);
            setOrderNum(orderNum);
            setErrorMessage(errorMessage != null ? errorMessage : this.toString());
            setErrorType(errorType);
            setHashValue(getHash(getClientId() + getOrderNum() + getErrorMessage() + getErrorType() + getRequestType() + OWDUtilities.getGroovyComparableStackTraceAsString(this)));

            if (isExistingError()) {
                updateErrorEntry();
            } else {
                createErrorEntry();
            }
        } catch (Exception e) {
            handleInternalError(e);
        }
    }

    public static void  logException(Exception exception, String errorMessage, String orderNum, String clientId, String requestType, errorTypes errorType) {
          LogableException le = new LogableException( exception,  errorMessage,  orderNum,  clientId,  requestType,  errorType);
    }

        /**
         * Constructor with key field inputs.
         *
         * @param errorMessage
         * @param orderNum
         * @param clientId
         * @param requestType
         */
    public LogableException(Exception exception, String errorMessage, String orderNum, String clientId, String requestType, errorTypes errorType) {

            super(exception);


        try {


          //  log.debug(OWDUtilities.getStackTraceAsString(this));
         //   log.debug(OWDUtilities.getGroovyComparableStackTraceAsString(this));

            setRequestType(requestType != null ? requestType : "Undetermined");
            setClientId(clientId);
            setOrderNum(orderNum);
            setErrorMessage(errorMessage != null ? errorMessage : this.toString());
            setErrorType(errorType);
            setHashValue(getHash(getClientId() + getOrderNum() + getErrorMessage() + getErrorType() + getRequestType() + OWDUtilities.getGroovyComparableStackTraceAsString(this)));

            if (isExistingError()) {
                updateErrorEntry();
            } else {
                createErrorEntry();
            }
        } catch (Exception e) {
            handleInternalError(e);
        }
    }


    private void setErrorType(errorTypes type) {
          this.errorType = type;
    }

    /**
     * Determines the number of previous occurrences for a given error. If no previous record is found 0 will be
     * returned indicating a new record and email should be created.
     * @return
     */
    public boolean isExistingError() {

        int errorCount = 0;
        boolean gotResults = false;

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        try {
            StringBuffer result = new StringBuffer();

            String sql = "select order_error_id, status \n" +
                    " from owd_order_error \n" +
                    " where hash = ? \n";

            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement(sql);
            stmt.setString(1, getHashValue());

            stmt.executeQuery();

            rs = stmt.getResultSet();

            while (rs.next()) {
                setOrderErrorId(rs.getInt(1));
                setStatus(rs.getInt(2));

                gotResults = true;
            }
            rs.close();
            stmt.close();
            cxn.rollback();

        } catch (Exception e) {
            handleInternalError(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }
        return gotResults;
    }

    /**
     * Creates and inserts a new order error in the database.
     *
     * @return
     */
    public void createErrorEntry() {

        log.debug("createErrorEntry");

        //Email first error...
        sendNotification();

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        try {
            String sql = "insert into owd_order_error (order_num, client_fkey, error_count, first_recorded, last_recorded, \n" +
                    "error_message, error_type, request_type, stack_trace, hash) \n" +
                    "values (?, ?, 1, getDate(), getDate(), ?, ?, ?, ?, ?)";

            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement(sql);
            stmt.setString(1, getOrderNum());
            stmt.setString(2, getClientId());
            stmt.setString(3, getErrorMessage());
            stmt.setString(4, getErrorType().toString());
            stmt.setString(5, getRequestType());
            stmt.setString(6, OWDUtilities.getGroovyComparableStackTraceAsString(this));
            stmt.setString(7, getHashValue());

            stmt.execute();

            stmt.close();
            cxn.commit();

        } catch (Exception e) {
            handleInternalError(e);
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Updates an existing order error entry, incrementing the count by one and setting the lastUpdated date to the current
     * timestamp.
     */
    public void updateErrorEntry() {
        log.debug("updateErrorEntry");

        int updateStatus = DEFAULT;

        if (getStatus() == IGNORE) {
            updateStatus = IGNORE;
        } else {
            sendNotification();
        }

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;

        try {
            String sql = " update owd_order_error \n" +
                    " set error_count = error_count + 1, \n" +
                    " last_recorded = getDate(), \n" +
                    " status = ? \n" +
                    " where order_error_id = ? \n";

            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement(sql);
            stmt.setInt(1, updateStatus);
            stmt.setInt(2, getOrderErrorId());

            stmt.execute();

            stmt.close();
            cxn.commit();

        } catch (Exception e) {
            handleInternalError(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Calls the sendMail method to notify of a given error.
     */
    public void sendNotification() {

        try {
            sendMail("[ERROR] Log Exception "+this.getRequestType(), formatExceptionMail(this, getErrorMessage()), mailTo, mailFrom);

        } catch (Exception e) {
            System.err.println("LoggableException.sendNotification: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles internal errors within this class sending email notification. But not inserting error into database.
     * @param exception
     */
    public void handleInternalError(Exception exception) {
        System.err.println(exception.getLocalizedMessage());
        exception.printStackTrace();

        try {
            sendMail("[ERROR] LogableException.handleInternalError", formatExceptionMail(exception, "Internal Error"), mailTo, mailFrom);
        } catch (Exception e) {
            System.err.println("handleInternalError: " + exception.getLocalizedMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Formats the body of the email notification.
     * @param exception
     * @param errorMessage
     * @return
     */
    public String formatExceptionMail(Exception exception, String errorMessage) {

            return "LogableException Mail \r\n\r\n" +
                errorMessage + "\r\n\r\n" +
                "View error list: " + errorListWebLink + "\r\n\r\n" +
                "Order Number: " + getOrderNum() + "\r\n" +
                "Client ID: " + getClientId() + "\r\n" +
                "Request Type: " + getRequestType() + "\r\n" +
                "Error Message: " + getErrorMessage() + "\r\n" +
                "Error Type: " + getErrorType() + "\r\n" +
                "Hash Value: " + getHashValue() + "\r\n\r\n" +
                OWDUtilities.getGroovyComparableStackTraceAsString(exception) + "\r\n\r\n";
    }

    /**
     * Sends email notification.
     *
     * @param subject
     * @param message
     * @param to
     * @param from
     * @throws Exception
     */
    public void sendMail(String subject, String message, String to, String from) throws Exception {
        try {
            SlackManager slack = new SlackManager();

            slack.notifySlack(this);

         //   Mailer.sendMail(subject, message, to, from);
        } catch (Exception e) {
            System.err.println("LogableException.sendMail: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    //Properties

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRequestType() {
        return requestType;
    }

    public errorTypes getErrorType() {
        return errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getOrderErrorId() {
        return orderErrorId;
    }

    public void setOrderErrorId(int orderErrorId) {
        this.orderErrorId = orderErrorId;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns hash value for string with key error exception fields.
     *
     * @param input
     * @return
     * @throws Exception
     */
    private static String getHash(String input) throws Exception {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(input.getBytes("UTF-8"));

        return new BigInteger(1, crypt.digest()).toString(16);
    }
}
