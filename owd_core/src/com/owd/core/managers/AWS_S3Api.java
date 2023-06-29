package com.owd.core.managers;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/25/12
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class AWS_S3Api {
private final static Logger log =  LogManager.getLogger();

    static final String awsKey = "AKIAIY7XS3NJQSO6AAQA";
    static final String awsSecretKey="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";

    static AmazonS3Client s3;

    static public String scanBucket = "owd.s3.scans";
    static public String audioBucket = "owd.s3.calls";
    static public String returnLabelBucket = "owd.s3.returnlabels";
    static public String kScanTypePackingSlip = "packslip" ;
    static public String kScanTypeReceive = "receive" ;
    static public String kScanTypeReturnLabel = "returnlabel" ;

    static public String kMediaTypePdf = "pdf";
    static public String kMediaTypeMpg = "mpg";
    static public String kMediaTypeOgg = "ogg";
    static public String kMediaTypeWav = "wav";
    static public String kMediaTypePng = "png";

    static public Map<String,String> mediaContentTypeMap = new TreeMap<String,String>();


    static{

        System.setProperty("com.amazonaws.sdk.disableCertChecking","yes");

        s3 = new AmazonS3Client(new AWSCredentials() {

            public String getAWSAccessKeyId() {
                return awsKey;
            }

            public String getAWSSecretKey() {
                return awsSecretKey;
            }
        });


        mediaContentTypeMap.put(kMediaTypeMpg,"") ;
        mediaContentTypeMap.put(kMediaTypeWav,"") ;
        mediaContentTypeMap.put(kMediaTypeOgg,"") ;
        mediaContentTypeMap.put(kMediaTypePdf,"application/pdf") ;



    }
    public static void main(String[] args)  throws Exception
    {

        log.debug("Buckets:");
                for(Bucket bucket:listBuckets())
                {
                    log.debug("Bucket:"+bucket);
                }

        String bucket = "owd.s3.scans";
        bucket = bucket.replaceAll("\\.s3\\.","\\.");
        System.out.println(bucket);
      //  byte[] stuff = getObjectFromBucket("owd.calls","inbound/375/F91430650.wav");

      //  byte[] stuff2 = getObjectFromBucket("owd.s3.calls","inbound/375/F91430650.wav");

       // String url = getTempUrlForObject("owd.s3.calls","inbound/375/F91431021.wav");

      //  log.debug(url);
        /*     log.debug("08.pdf exists? - "+itemPrefixExists("owdtest.pdf","08.pdf"));
                log.debug("xx.pdf exists? - "+itemPrefixExists("owdtest.pdf","xx.pdf"));
        */
       /* putObjectInBucket(scanBucket,
                55,//OWD
                "parentobjectId",
                AWS_S3Api.kScanTypePackingSlip,
                AWS_S3Api.kMediaTypePdf,
                new ByteArrayInputStream("Hello world".getBytes()));
*/
       // log.debug("bytes:"+getObjectFromBucket("owd.scans","packslip/146/4392449_20110822_11:48:19.257.pdf").length);
    }



    public static List<Bucket> listBuckets()
    {
        return s3.listBuckets();
    }

    public static ObjectListing listItemsInBucket(String bucket, String optionalPrefix)
    {
        ListObjectsRequest lor = new ListObjectsRequest().withBucketName(bucket);
        if(optionalPrefix!=null)
        {
            lor.withPrefix(optionalPrefix);
        }
        return  s3.listObjects(lor);


    }

    public static boolean itemPrefixExists(String bucket, String prefix) {
        boolean found = false;

        ListObjectsRequest lor = new ListObjectsRequest().withBucketName(bucket);
        if (prefix != null) {
            lor.withPrefix(prefix);
        }
        found = (s3.listObjects(lor).getObjectSummaries().size() > 0);
        if (!found) {
            if (bucket.contains(".s3.")) {
                bucket = bucket.replaceAll("\\.s3\\.", "\\.");
            }
             lor = new ListObjectsRequest().withBucketName(bucket);
            if (prefix != null) {
                lor.withPrefix(prefix);
            }
            found = (s3.listObjects(lor).getObjectSummaries().size() > 0);
        }


          return found;
    }

    public static String putObjectInBucket(String bucket, int clientId, String filename, String fileType, String contentType, InputStream data)
    {

        String fileName = fileType+"/"+clientId+"/"+filename;

        //pattern: filetype/clientId/parentObjectId_date_time.media

        ObjectMetadata om = new ObjectMetadata();
        om.setContentType(mediaContentTypeMap.get(contentType));


        PutObjectRequest por = new PutObjectRequest(bucket,fileName,data,om);
        PutObjectResult poresult = s3.putObject(por);

        return fileName;

    }

    public static byte[] getObjectFromBucket(String bucket, String fileName) throws Exception
    {
        S3Object object = null;
        try {
             object = s3.getObject(new GetObjectRequest(bucket, fileName));
        }catch(Exception ex) {
            if(bucket.contains(".s3.")) {
                bucket = bucket.replaceAll("\\.s3\\.","\\.");
            }
            object = s3.getObject(new GetObjectRequest(bucket, fileName));
        }
        log.debug("Content-Type: "  + object.getObjectMetadata().getContentType());
        log.debug("Content-Length: "  + object.getObjectMetadata().getContentLength());

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[(int)object.getObjectMetadata().getContentLength()];

        while ((nRead = object.getObjectContent().read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    public static String getTempUrlForObject(String bucket, String fileName) throws Exception
    {
        S3Object object = null;
        try{
         object = s3.getObject(new GetObjectRequest(bucket, fileName));
    }catch(Exception ex) {
        if(bucket.contains("\\.s3\\.")) {
            bucket = bucket.replaceAll("\\.s3\\.","\\.");
        }
        object = s3.getObject(new GetObjectRequest(bucket, fileName));
    }
        log.debug("Content-Type: "  + object.getObjectMetadata().getContentType());
        log.debug("Content-Length: "  + object.getObjectMetadata().getContentLength());

        java.util.Date expiration = new java.util.Date();
        long milliSeconds = expiration.getTime();
        milliSeconds += 1000 * 60 * 60; // Add 1 hour.
        expiration.setTime(milliSeconds);

        URL url = s3.generatePresignedUrl(bucket,fileName,expiration);
       return url.toString();
    }

}
