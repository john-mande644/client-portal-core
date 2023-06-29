package com.owd.core.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 10/13/13
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallAudioManager {
    private final static Logger log =  LogManager.getLogger();

    public static String getUrlForCallRecording(String callid, int clientId) throws Exception
    {
        if(AWS_S3Api.itemPrefixExists(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callid+".wav"))
        {
            return AWS_S3Api.getTempUrlForObject(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callid+".wav");

        }   else
        {
            throw new Exception("No recording is available for contact ID "+callid);
        }
    }

    public static byte[] getDataForCallRecording(String callid, int clientId) throws Exception
    {
        if(AWS_S3Api.itemPrefixExists(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callid+".wav"))
        {
            return AWS_S3Api.getObjectFromBucket(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callid+".wav");

        }   else if(AWS_S3Api.itemPrefixExists(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callid+".mp3"))
        {
            return AWS_S3Api.getObjectFromBucket(AWS_S3Api.audioBucket,"inbound/"+clientId+"/"+callid+".mp3");

        }   else
        {
            throw new Exception("No recording is available for contact ID "+callid);
        }
    }

    public static void main(String[] args) throws Exception {
        log.debug(getUrlForCallRecording("F9100013", 505));
    }

}
