package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import java.util.Properties;

/**
 * Class to represent a POP3 mailbox, used to retrieve and delete messages
 * <p><B>Note that POP3 boxes have timeouts</B> so you must interact with the
 * server at least every 30 seconds or so or the server may close the connection.
 * <p>This class is not threadsafe
 * <p/>
 * <p>Bugs: None known
 * <p/>
 * $Id: POPMailbox.java,v 1.6 2008/01/04 22:30:13 stewart Exp $
 */
public class POPMailbox {
private final static Logger log =  LogManager.getLogger();

    //flag set after connect/disconnect to indicate state
    boolean connected = false;

    //javax.mail objects
    Properties props = null;
    URLName url = null;
    javax.mail.Session mailSession = null;
    Store popStore = null;
    javax.mail.Message[] messages = null;
    Folder inbox = null;

    /**
     * Creates object and configuration for connection and
     * connects to mailbox. This call may take a long time
     * to return if the server is unreachable or slow.
     *
     * @param POPServer   FQDN for POP3 server
     * @param boxName     account name for mailbox
     * @param boxPassword password for mailbox
     * @param debug       flag to put Mail session in debug mode (see javax.mail docs)
     */
    public POPMailbox(String POPServer, String boxName, String boxPassword, boolean debug) throws NoSuchProviderException, MessagingException {
        props = new Properties();
        props.put("mail.smtp.host", OWDConstants.SMTPServer);
        url = new URLName("pop3", POPServer, -1, "INBOX", boxName, boxPassword);
        mailSession = javax.mail.Session.getDefaultInstance(props, null);
        mailSession.setDebug(false);

        popStore = mailSession.getStore(url);

        connect();
    }

    /**
     * Retrieves state of connection to server
     * <p>Note that server may disconnect client without this flag being set...
     *
     * @return connection state
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Retrieves inbox folder for message operations
     *
     * @return Folder object for account inbox
     */
    private Folder getInbox() throws MessagingException {
        if (!isConnected()) throw new MessagingException("Must call connect() before accessing messages");

        if (inbox == null) //lazy instantiation
        {
            inbox = popStore.getDefaultFolder();
            if (inbox == null)
                throw new MessagingException("No default folder");

            inbox = inbox.getFolder("INBOX");
            if (inbox == null)
                throw new MessagingException("Inbox not found");

            inbox.open(Folder.READ_WRITE);
        }

        return inbox;

    }

    /**
     * Retrieve array of Message objects for inbox
     *
     * @return Ordered array of messages - message order is significant and reflects
     *         <p> the server's internal listing
     */
    public Message[] getMessages() throws MessagingException {
        if (messages == null) {
            if (getInbox().getMessageCount() > 0)
                messages = inbox.getMessages();
            if (messages == null)
                return new Message[0];

        }

        return messages;
    }

    /**
     * Returns indexed Message object from inbox
     *
     * @param index zero-based index into Message list
     * @return the Message object
     */
    public Message getMessageAt(int index) throws MessagingException {
        Message[] msgArray = getMessages();

        //log.debug("got messages " + msgArray.length + " for index " + index);

        if (index < 0 || index >= msgArray.length)
            throw new MessagingException("message index out of range");

        return msgArray[index];
    }

    /**
     * Retrieves number of messages
     *
     * @return number of messages in account's inbox
     */
    public int getMessageCount() throws MessagingException {
        return getInbox().getMessageCount();
    }

    /**
     * Deletes the indexed Message from inbox
     * <p>Deletion is not permanent if connection terminates unexpectedly.
     * You must call disconnect() on a live connection to make permanent any deletions
     * during that session.
     *
     * @param index zero-based index into Message list
     */
    public void deleteMessage(int index) throws MessagingException {
        getMessages()[index].setFlag(Flags.Flag.DELETED, true);
    }

    /**
     * Deletes all messages from inbox. See deleteMessage method.
     */
    public void deleteAllMessages() throws MessagingException {
        int count = getMessageCount();

        for (int i = 0; i < count; i++) {
            getMessages()[i].setFlag(Flags.Flag.DELETED, true);
        }
    }

    /**
     * Opens network connection to server
     */
    public void connect() throws MessagingException {
        if (isConnected()) return;

        popStore.connect();

        connected = true;
    }

    /**
     * Properly terminates network connection and resets this object's variables
     * so that connect() can be called again.
     */
    public void disconnect() throws Exception {
        if (isConnected()) {
            try {
                getInbox().close(true);
            } catch (Exception ex) {
                throw ex;
            } finally {	//make sure that the variables are reset
                messages = null;
                inbox = null;
                connected = false;
            }

        }
    }


    static public void main(String[] args) {

        for (int j = 0; j <= 40; j++) {
            //log.debug("Group " + j);
            try {
                //log.debug("connecting...");
                POPMailbox box = new POPMailbox("mail.owd.com", "owditadmin@owd.com", "torque", false);
                //log.debug("connected");
                int count = 500;

                //log.debug("message count = "+count);
                int deleted = 0;

                for (int i = 1; i <= count; i++) {
                   // String[] froms = box.getMessageAt(i).getHeader("Date");
                   // for(int k=0; k<froms.length;k++)
                   // {
                    //	if(froms[k].indexOf("June") <0 )
                    //	{
                  //  //log.debug("d m " + i);
                    box.deleteMessage(i);
                    deleted++;
                   // 	}

                  //  }

                }
                   //log.debug("Deleting "+deleted+" messages...");
                box.disconnect();
                //log.debug("DONE");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
