package com.owd.core;

import com.owd.core.business.ChoiceListManager;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.business.owdChoiceList;
import com.owd.core.crypto.BlowfishCipher;
import com.owd.core.crypto.EncryptedInputStream;
import com.owd.core.crypto.EncryptedOutputStream;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibernateSession;
import ipworksssl.SOAPParam;
import ipworksssl.Soaps;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.Security;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OWDUtilities {
private final static Logger log =  LogManager.getLogger();

    static private String debugFile = "appexceptions.log";

    static private final String kBlowfishKey = "akwhdyen4,68v9b0x-c";


    public static String getIPWorksSSSLRuntimeLicense()
    {
        return "31534A385641315355425241315355423353384132323435000000000000000000000000000000005A584235524A355A00003136484445395942385557410000";
    }
    public static String getIPWorksRuntimeLicense()
    {
        return          "31504A385641315355425241315355423353384132323435000000000000000000000000000000004D5348345546444B0000324A5553415745595037594A0000";
    }
 public static String getIPWorksInshipRuntimeLicense() {
    return "42584A35564131535542524131535542335338413232343500000000000000000000000000000000333945504632484100005546424A43345836443532370000";
}
    public static String getLogiXMLAuthString(String user, String pass, String clientID) throws Exception {
        Vector vec = new Vector();
        vec.add(user);
        vec.add(pass);
        vec.add(clientID);

        return URLEncoder.encode(encryptData(vectorToParseableString(vec)), "UTF-8");
    }



    static public String multiLineToOneLine(String multiLine)
    {

        String fixedString = multiLine;
        if (fixedString==null)  {fixedString="";}

        fixedString = fixedString.replaceAll("[\\s]?[\\t\\n\\r]+[\\s]?"," ");
    //    fixedString = fixedString.replaceAll(" [\\t\\n\\r]","");
     //   fixedString = fixedString.replaceAll("[\\t\\n\\r]+"," ");

      //  fixedString = fixedString.replaceAll("\\r","r").replaceAll("\\n", "n").replaceAll(" ","X");
        return fixedString;
    }

static public String OToS(Object obj) {
long start=System.currentTimeMillis();
String out = null;
if (obj != null) {
try {
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(obj);
out = Base64.encodeBase64String(baos.toByteArray());
} catch (IOException e) {
e.printStackTrace();
return null;
}
}
long end=System.currentTimeMillis();
log.debug("Encode:"+(end-start));
return out;
}

static public Object SToO(String str) {
long start=System.currentTimeMillis();
Object out = null;
if (str != null) {
try {

ByteArrayInputStream bios = new ByteArrayInputStream(Base64.decodeBase64(str));
ObjectInputStream ois = new ObjectInputStream(bios);
out = ois.readObject();
} catch (IOException e) {
e.printStackTrace();
return null;
} catch (ClassNotFoundException e) {
e.printStackTrace();
return null;
}
}
long end=System.currentTimeMillis();
log.debug("Decode:"+(end-start));
return out;
}

    public static boolean isValidEmailAddress(String aEmailAddress){
       if (aEmailAddress == null) return false;
       boolean result = true;
       try {
         InternetAddress emailAddr = new InternetAddress(aEmailAddress);
         if ( ! hasNameAndDomain(aEmailAddress) ) {
           result = false;
         }
       }
       catch (AddressException ex){
         result = false;
       }
       return result;
     }

     private static boolean hasNameAndDomain(String aEmailAddress){
       String[] tokens = aEmailAddress.split("@");
       return (
        tokens.length == 2 &&
        ( tokens[0].length()>0 ) &&
       ( tokens[1].length()>0 )) ;
     }

    public static String getMD5AsHexString(String plaintext) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(plaintext.getBytes());
        return byteArrayToHexString(md.digest());
    }

    static String byteArrayToHexString(byte in[]) {

    byte ch = 0x00;

    int i = 0;

    if (in == null || in.length <= 0)

        return null;



    String pseudo[] = {"0", "1", "2",
"3", "4", "5", "6", "7", "8",
"9", "A", "B", "C", "D", "E",
"F"};

    StringBuffer out = new StringBuffer(in.length * 2);



    while (i < in.length) {

        ch = (byte) (in[i] & 0xF0); // Strip off
//high nibble

        ch = (byte) (ch >>> 4);
     // shift the bits down

        ch = (byte) (ch & 0x0F);
// must do this is high order bit is on!

        out.append(pseudo[ (int) ch]); // convert the
//nibble to a String Character

        ch = (byte) (in[i] & 0x0F); // Strip off
//low nibble

        out.append(pseudo[ (int) ch]); // convert the
//nibble to a String Character

        i++;

    }

    String rslt = new String(out);

    return rslt;

}

      public static owdChoiceList getChoiceList(String listName) throws Exception {
        owdChoiceList rList = new owdChoiceList(listName);

        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            cxn = ConnectionManager.getConnection();


            // OWDUtilities.debugApp("getting list "+listName);
            stmt = cxn.createStatement();
            String esql = "select list_value, reference_num, is_default " +
                    "from owd_lists (NOLOCK) " +
                    "where list_name = \'" + listName + "\' " +
                    "order by list_seq, list_value";
            OWDUtilities.debugApp(esql);

            rs = stmt.executeQuery(esql);

            OWDUtilities.debugApp("executed");
            if (rs != null) {
                // OWDUtilities.debugApp("getting results");

                while (rs.next()) {
                    rList.addElement(rs.getString("list_value"), rs.getString("reference_num"), rs.getBoolean("is_default"));

                }

                rs.close();
                //hasResultSet = stmt.getMoreResults();
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                stmt.close();
            } catch (Exception exc) {
            }

            ConnectionManager.freeConnection(cxn);
        }

        return rList;

    }
    public static String parseISToString(java.io.InputStream is, String lineEndingString) {
        java.io.DataInputStream din = new java.io.DataInputStream(is);
        StringBuffer sb = new StringBuffer();
        try {
            String line;
            while ((line=din.readLine()) != null)
            {
                sb.append(line+lineEndingString);
            }
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
            }
        }
        return sb.toString();
    }
    public static String parseISToString(java.io.InputStream is) {
        return parseISToString(is,"");
    }

    //encoding examples: "UTF-8", "UTF-16", etc.
    public static java.io.InputStream parseStringToIS(String xml, String encoding) {
        if (xml == null) return null;
        xml = xml.trim();
        java.io.InputStream in = null;
        try {
            in = new java.io.ByteArrayInputStream(xml.getBytes(encoding));
        } catch (Exception ex) {
        }
        return in;
    }

    public static Context getDefaultJ2EEContext() throws NamingException {
        //String url       = "t3://172.16.2.103:7001";

        //Properties p = new Properties();

        //p.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
        //p.put(Context.PROVIDER_URL, url);

        //	Hashtable env = new Hashtable();
//  env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
//  env.put(Context.PROVIDER_URL, "172.16.2.199");
// env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces" );

// Context ctx = new InitialContext();

        return new InitialContext();
    }

    public static String encryptData(String cleartext) {

        try {
            BlowfishCipher cipher = new BlowfishCipher(kBlowfishKey);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            EncryptedOutputStream encryptor = new EncryptedOutputStream(cipher, out);
            encryptor.write(cleartext.getBytes(), 0, cleartext.length());
            encryptor.flush();

            return Base64.encodeBase64String(out.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cleartext;

    }

    private static String decryptPHPBlowfish(String input, String key)
             throws Exception {

         byte[] raw = key.getBytes();
         SecretKeySpec secretkeySpec = new SecretKeySpec(hexToBytes(key), "Blowfish");

         // create a cipher based upon Blowfish
         Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");

         // initialise cipher to with secret key
         cipher.init(Cipher.DECRYPT_MODE, secretkeySpec);

    //    log.debug(""+new sun.misc.BASE64Decoder().decodeBuffer(input));
         return(new String(cipher.doFinal(Base64.decodeBase64(input))));

     }



    public static String decryptData(String ciphertext,String blowfishKey) {
           try {
               byte[] cipherbytes = Base64.decodeBase64(ciphertext);
               BlowfishCipher cipher = new BlowfishCipher(blowfishKey);
               ByteArrayInputStream out = new ByteArrayInputStream(cipherbytes);

               EncryptedInputStream decryptor = new EncryptedInputStream(cipher, out);
               int abyte = 0;
               StringBuffer decrypted = new StringBuffer();
               while (abyte != -1) {
                   abyte = decryptor.read();
                   if (abyte >= 0) {
                       decrypted.append((char) abyte);
                   }
               }

               return decrypted.toString();
           } catch (Exception ex) {
               ex.printStackTrace();
           }
           return ciphertext;
       }


    public static String decryptData(String ciphertext) {
        try {
            byte[] cipherbytes =Base64.decodeBase64(ciphertext);
            BlowfishCipher cipher = new BlowfishCipher(kBlowfishKey);
            ByteArrayInputStream out = new ByteArrayInputStream(cipherbytes);

            EncryptedInputStream decryptor = new EncryptedInputStream(cipher, out);
            int abyte = 0;
            StringBuffer decrypted = new StringBuffer();
            while (abyte != -1) {
                abyte = decryptor.read();
                if (abyte >= 0) {
                    decrypted.append((char) abyte);
                }
            }

            return decrypted.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ciphertext;
    }


    public static String padRightHTML(String data, int len) {

        if (data == null)
            return "";
        if (data.length() >= len)
            return data.substring(0, len);

        StringBuffer sb = new StringBuffer();
        sb.append(data);
        int diff = (len - data.length());

        for (int i = 0; i < diff; i++) {
            sb.append("&nbsp;");
        }
        return sb.toString();
    }

    public static String padLeftHTML(String data, int len) {

        if (data == null)
            return "";
        if (data.length() >= len)
            return data.substring(0, len);

        StringBuffer sb = new StringBuffer();

        int diff = (len - data.length());

        for (int i = 0; i < diff; i++) {
            sb.append("&nbsp;");
        }
        sb.append(data);
        return sb.toString();
    }

    public static String padRight(String data, int len) {

        if (data == null)
            return "";
        if (data.length() >= len)
            return data.substring(0, len);

        StringBuffer sb = new StringBuffer();
        sb.append(data);
        int diff = (len - data.length());

        for (int i = 0; i < diff; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String padLeft(String data, int len, char padChar) {

        if (data == null)
            return "";
        if (data.length() >= len)
            return data.substring(0, len);

        StringBuffer sb = new StringBuffer();

        int diff = (len - data.length());

        for (int i = 0; i < diff; i++) {
            sb.append(padChar);
        }
        sb.append(data);
        return sb.toString();
    }

    public static String padLeft(String data, int len) {

        return padLeft(data,len,' ');
    }

    /// Test is a number is even.
    public static boolean even(long n) {
        return (n & 1) == 0;
    }

    /// Test is a number is odd.
    public static boolean odd(long n) {
        return (n & 1) != 0;
    }

    /// Count the number of 1-bits in a byte.
    public static int countOnes(byte n) {
        return countOnes(n & 0xffL);
    }

    /// Count the number of 1-bits in an int.
    public static int countOnes(int n) {
        return countOnes(n & 0xffffffffL);
    }

    /// Count the number of 1-bits in a long.
    public static int countOnes(long n) {
        // There are faster ways to do this, all the way up to looking
        // up bytes in a 256-element table.  But this is not too bad.
        int count = 0;
        while (n != 0) {
            if (odd(n))
                ++count;
            n >>>= 1;
        }
        return count;
    }


    /// A fixed version of java.io.InputStream.read(byte[], int, int).  The
    // standard version catches and ignores IOExceptions from below.
    // This version sends them on to the caller.
    public static int read(InputStream in, byte[] b, int off, int len) throws IOException {
        if (len <= 0)
            return 0;
        int c = in.read();
        if (c == -1)
            return -1;
        if (b != null)
            b[off] = (byte) c;
        int i;
        for (i = 1; i < len; ++i) {
            c = in.read();
            if (c == -1)
                break;
            if (b != null)
                b[off + i] = (byte) c;
        }
        return i;
    }

   
    public static byte[] getByteArrayFromURL(URL file) throws IOException {
         URL url = file;
URLConnection c = url.openConnection();
InputStream in = c.getInputStream();
 
ByteArrayOutputStream baous = new ByteArrayOutputStream();

byte[] buf = new byte[1<<9];
for(int read; (read = in.read(buf)) != -1; baous.write(buf, 0, read));
buf = null;


        return baous.toByteArray();
       }


    /// A version of read that reads the entire requested block, instead
    // of sometimes terminating early.
    // @return -1 on EOF, otherwise len
    public static int readFully(InputStream in, byte[] b, int off, int len) throws IOException {
        int l, r;
        for (l = 0; l < len;) {
            r = read(in, b, l, len - l);
            if (r == -1)
                return -1;
            l += r;
        }
        return len;
    }

    static public String getCurrentUserName() {
        /*try
        {

            return getClassForName(weblogic.security.acl.Security.getCurrentUser().getName();
        }catch(Throwable ex)
        {
            */
        return "System";

        //	}
    }

    static public String getCurrentUserName(String providedName) {
        /*try
        {

            return getClassForName(weblogic.security.acl.Security.getCurrentUser().getName();
        }catch(Throwable ex)
        {
            */

        if (providedName != null) {
            if (providedName.trim().length() < 1) {
                return "System";
            } else {
                return providedName;
            }
        } else {
            return "System";
        }

        //	}
    }

    static public float roundFloat(float f) {
        //    return ((float) (Math.round(f * 100))) / 100;
        //   //log.debug("f:"+f);
        return roundFloat(f, 2);


    }

    static public double roundDouble(double f) {
        //    return ((float) (Math.round(f * 100))) / 100;
        //   //log.debug("f:"+f);
        return roundDouble(f, 2);


    }

    static public double roundDouble(double f, int decimals) {
        // float f2 = ((Math.round((f * (float) Math.pow(10, decimals))))) / ((float) Math.pow(10, decimals));
        BigDecimal floater = new BigDecimal(f);
        int currPlaces = 6;

        while (currPlaces >= decimals) {
            floater = floater.setScale(currPlaces, java.math.BigDecimal.ROUND_HALF_UP);
            currPlaces--;
        }

        return floater.doubleValue();
    }

    static public float roundFloat(float f, int decimals) {
        // float f2 = ((Math.round((f * (float) Math.pow(10, decimals))))) / ((float) Math.pow(10, decimals));
        BigDecimal floater = new BigDecimal(f);
        int currPlaces = 6;

        while (currPlaces >= decimals) {
            floater = floater.setScale(currPlaces, java.math.BigDecimal.ROUND_HALF_UP);
            currPlaces--;
        }

        return floater.floatValue();
    }

    static public void logException(Exception ex) {
        try {
            FileWriter writer = new FileWriter(debugFile, true);
            writer.write(ex.toString() + "\r\n");
            writer.write(ex.getMessage() + "\r\n");
            writer.write(getStackTraceAsString(ex) + "\r\n");
            writer.flush();
            writer.close();
        } catch (IOException exc) {//////////log.debug(exc);
            exc.printStackTrace();
        }
    }


    static public void debugApp(Object message) {

        //try{
        //////////log.debug(message);
        //FileWriter writer = new FileWriter(debugFile, true);
        //writer.write(message+"\r\n");
        //writer.flush();
        //writer.close();
        //}catch(IOException ex){//////////log.debug(ex);ex.printStackTrace();}
    }

    static public String getGroovyComparableStackTraceAsString(Throwable e) {
        // Dump the stack trace to a buffered stream, then return it's contents
        // as a String. This is useful for printing the stack to 'out'.
        try{
        ByteArrayOutputStream ostr = new ByteArrayOutputStream();
        if (e == null)
            return "No exceptions";
        e.printStackTrace(new PrintStream(ostr));
        StringBuilder outStr = new StringBuilder();
        BufferedReader bufReader = new BufferedReader(new StringReader(ostr.toString()));
        String line=null;
        while( (line=bufReader.readLine()) != null )
        {
           if(!(line.contains("org.codehaus.groovy")
                   || line.contains("sun.reflect")
                   || line.contains("groovy.lang")
                   || line.contains("java.lang.reflect")
                   || line.contains("sun.reflect")
                   || (line.contains("...") && line.contains("more"))
                ) )
           {
               outStr.append(line.replaceAll(":\\d+\\)",")")+System.getProperty("line.separator"));

           }
        }


        return (outStr.toString());
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return "";
    }

    static public String getStackTraceAsString(Throwable e) {
        // Dump the stack trace to a buffered stream, then return it's contents
        // as a String. This is useful for printing the stack to 'out'.
        ByteArrayOutputStream ostr = new ByteArrayOutputStream();
        if (e == null)
            return "No exceptions";
        e.printStackTrace(new PrintStream(ostr));
        return (ostr.toString());
    }

    static public String getExpDateStr(int mon, int year) {
        String exp = "";

        if (mon < 10)
            exp = "0" + mon;
        else
            exp = "" + mon;

        if (year < 100) {
            if (year < 10)
                exp = exp + "0" + year;
            else
                exp = exp + year;
        } else {
            String ystr = "" + year;
            exp = exp + ystr.substring(ystr.length() - 2);
        }
        return exp;
    }

    static public float floatFromString(String numstr) {
        try {
            return new Float(numstr).floatValue();
        } catch (Exception ex) {
            return 0.0000f;
        }
    }

    static public int intFromString(String numstr) {
        try {
            return new Integer(numstr).intValue();
        } catch (Exception ex) {
            return 0;
        }
    }

    static public String getLastNameFromWholeName(String fullName) {
        String first = "";
        String middle = "";
        String last = "";
        String lastToken = "";
        int middleCount = 0;

        if (fullName == null) fullName = "";
        fullName = fullName.trim();
     /*   if (fullName.startsWith("Mr") || fullName.startsWith("Miss") || fullName.startsWith("Ms")) {
            try {
                fullName = fullName.substring(fullName.indexOf(" ") + 1);
            } catch (Exception ex) {
            }
        }*/
        StringTokenizer tempTokenizer = new StringTokenizer(fullName.trim());

        if (tempTokenizer.hasMoreTokens())
            first = tempTokenizer.nextToken();

        while (tempTokenizer.hasMoreTokens()) {
            lastToken = tempTokenizer.nextToken();
            if (tempTokenizer.hasMoreTokens()) {
                if (middleCount > 0)
                    middle = middle + " " + lastToken;
                else
                    middle = lastToken;
                middleCount++;
            } else {
                last = lastToken;
            }
        }
        if (last.toUpperCase().indexOf(" JR") >= 0 || last.toUpperCase().indexOf(" IV") >= 0 || last.toUpperCase().indexOf(" II") >= 0) {
            if (middleCount > 1)
                last = getLastNameFromWholeName(middle);
            else
                last = middle;
        }

        return last.trim();
    }

    static public Hashtable getAllNamePartsFromWholeName(String fullName) {
        String first = "";
        String middle = "";
        String last = "";
        String prefix = "";
        String suffix = "";
        String appendage = "";

        Hashtable parsed = new Hashtable();
        Vector parts = new Vector();

        String name = fullName.toUpperCase().trim();

        name = replaceSubString(name, "%2C", " ");
        name = replaceSubString(name, ",", " ");

        name = replaceSubString(name, " M. D.", " M.D.");
        name = replaceSubString(name, ".", "");
        name = replaceSubString(name, " -", "-");
        name = replaceSubString(name, " -", "-");
        name = replaceSubString(name, "MR & MRS", "");
        name = replaceSubString(name, "MR AND MRS", "");
        name=name.trim();

        //log.debug(name);
        StringTokenizer tempTokenizer = new StringTokenizer(name);

        while (tempTokenizer.hasMoreTokens()) {
            parts.addElement(tempTokenizer.nextToken());

        }

        int items = parts.size();


        if (items >= 1) {
            //look for prefix
            String part = (String) parts.elementAt(0);
            if (part.equals("MR") ||
                    part.equals("DR") ||
                    part.equals("REV") ||
                    part.equals("MS") ||
                    part.equals("MS") ||
                    part.equals("MISS") ||
                    part.equals("MRS") ||
                    part.equals("PROFESSOR") ||
                    part.equals("PROF") ||
                    part.equals("DOCTOR")) {
                prefix = part;
                parts.remove(0);
            }
            //look for appendage
            items = parts.size();
            if (items > 1) {
                part = (String) parts.elementAt(items - 1);
                if (part.equals("MD") ||
                        part.equals("PHD") ||
                        part.equals("FAAO") ||
                        part.equals("FACS") ||
                        part.equals("MPH") ||
                        part.equals("PHD") ||
                        part.equals("OD") ||
                        part.equals("FRCS") ||
                        part.equals("CDE") ||
                        part.equals("DMA") ||
                        part.equals("ING") ||
                        part.equals("ETS-G") ||
                        part.equals("MPH") ||
                        part.equals("FOPS") ||
                        part.equals("FASO") ||
                        part.equals("MS") ||
                        part.equals("MA") ||
                        part.equals("MSC") ||
                        part.equals("DDS") ||
                        part.equals("BA") ||
                        part.equals("BS") ||
                        part.equals("LPN") ||
                        part.equals("MSE")) {
                    appendage = part;
                    parts.remove(items - 1);
                }

            }
            //look for suffix
            items = parts.size();
            if (items > 1) {

                part = (String) parts.elementAt(items - 1);
                if (part.equals("JR") ||
                        part.equals("SR") ||
                        part.equals("II") ||
                        part.equals("III") ||
                        part.equals("IV")) {
                    suffix = part;
                    parts.remove(items - 1);
                }


            }
            //separate remaining name

            items = parts.size();
            if (items > 0) {
                first = (String) parts.elementAt(0);
                parts.remove(0);
            }


            items = parts.size();
            if (items > 0) {
                last = (String) parts.elementAt(items - 1);
                parts.remove(items - 1);
                while (parts.size() > 1) {
                    items = parts.size();
                    last = (String) parts.elementAt(items - 1) + " " + last;
                    parts.remove(items - 1);
                }
            }


            items = parts.size();
            if (items > 0) {
                middle = (String) parts.elementAt(0);
            }


        }

        //parsed.put("SOURCE",parts);
        parsed.put("prefix", prefix);
        parsed.put("first", first);
        parsed.put("middle", middle);
        parsed.put("last", last);
        parsed.put("suffix", suffix);
        parsed.put("appendage", appendage);

        return parsed;


    }

    public static void mainx(String[] args) throws Exception{




        try
        {
      String req = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
              "<CV3Data version=\"2.0\">" +
              "  <request>" +
              "    <authenticate>" +
              "      <user>owd</user>" +
              "      <pass>one7172world</pass>" +
              "      <serviceID>ff6cfd4428</serviceID>" +
              "    </authenticate>" +
              "    <requests>" +
              "      <reqOrders>" +
              "        <reqOrderNew/>" +
              "      </reqOrders>" +
              "    </requests>" +
              "  </request>" +
              "</CV3Data>";
        String encodedStr =  Base64.encodeBase64String(req.getBytes());
        Soaps wf = new Soaps();
        wf.setRuntimeLicense(OWDUtilities.getIPWorksSSSLRuntimeLicense());
        wf.setMethodURI("CV3Data");
        wf.setMethod("CV3Data");
        wf.setURL("https://service.commercev3.com");
        wf.addParam("data",encodedStr);

      wf.sendRequest();
            log.debug(wf.getFaultCode());
            log.debug(wf.getFaultString());
            log.debug(wf.getFaultActor());
            log.debug(new String((Base64.decodeBase64(wf.getReturnValue()))));
           
            for(int i=0;i<wf.getParams().size();i++)
            {
              log.debug("p:"+((SOAPParam)wf.getParams().get(i)).getName()+":"+((SOAPParam)wf.getParams().get(i)).getValue());
            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public static byte[] hexToBytes(char[] hex) {
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
          int high = Character.digit(hex[i * 2], 16);
          int low = Character.digit(hex[i * 2 + 1], 16);
          int value = (high << 4) | low;
          if (value > 127)
            value -= 256;
          raw[i] = (byte) value;
        }
        return raw;
      }

      public static byte[] hexToBytes(String hex) {
        return hexToBytes(hex.toCharArray());
      }


    static public void main(String[] args)  throws Exception
    {
        log.debug(" ID:576 Auth:"+encryptData("576"));

    //    log.debug(" ID:517 Auth:"+encryptData("517"));

        List<Integer> clients = new ArrayList<Integer>();
        ResultSet rs = HibernateSession.getResultSet("select client_id from owd_client where is_active=1");
while(rs.next())
{
    clients.add(rs.getInt(1));
}
        for(int client:clients)
        {
            log.debug("if not exists (select client_fkey from owd_client_api_authcodes where client_fkey="+client+") insert into owd_client_api_authcodes (code,client_fkey) values ('"+encryptData(client + "")+"',"+client+") ;");

        }


     //   log.debug(" ID:494 Auth:"+encryptData("494"));

       // log.debug(OrderUtilities.getServiceList());

        log.debug( OrderUtilities.getServiceList().getRefForValue("FedEx International Priority"));

        log.debug(OrderUtilities.getServiceList().getValueForRef(OrderUtilities.getServiceList().getRefForValue("FedEx International Priority")));


        log.debug(ChoiceListManager.getTranslatedString("FedEx International Priority"));

        log.debug(ChoiceListManager.getTranslatedString("TANDATA_FEDEXFSMS.FEDEX.IPRI"));
        //log.debug("isvalid="+CreditCard.isValid(CreditCard.parseDirtyLong(decryptData("e2Z0mZtEU8dspXzXx7oZnwQ="))));

    }

    static public String getFirstNameFromWholeName(String fullName) {
        String first = "";
        String middle = "";
        String last = "";
        String lastToken = "";
        int middleCount = 0;
        if (fullName == null) fullName = "";
        fullName = fullName.trim();
      /*  if (fullName.startsWith("Mr") || fullName.startsWith("Miss") || fullName.startsWith("Mrs")) {
            try {
                fullName = fullName.substring(fullName.indexOf(" ") + 1);
            } catch (Exception ex) {
            }
        }*/
        StringTokenizer tempTokenizer = new StringTokenizer(fullName.trim());

        if (tempTokenizer.hasMoreTokens())
            first = tempTokenizer.nextToken();

        while (tempTokenizer.hasMoreTokens()) {
            lastToken = tempTokenizer.nextToken();
            if (tempTokenizer.hasMoreTokens()) {
                if (middleCount > 0)
                    middle = middle + " " + lastToken;
                else
                    middle = lastToken;

                middleCount++;
            } else {
                last = lastToken;
                if (last.toUpperCase().indexOf(" JR") >= 0 || last.toUpperCase().indexOf(" IV") >= 0 || last.toUpperCase().indexOf(" II") >= 0) {

                    if (middleCount > 1) {
                        first = first + " " + getFirstNameFromWholeName(middle) + " " + last;
                    } else if (middleCount == 1) {
                        first = first + " " + last;
                    } else if (middleCount == 0) {
                        first = first + " " + last;
                    }
                } else {
                    if (middleCount > 0)
                        first = first + " " + middle;
                }

            }
        }


        return first.trim();
    }


    /*
    * @param s - The source string
    * @param search - The substring to search for in 's'
    * @param replace - The string that will replace 'search'
    * @return The changed string
    */
    public static String replaceSubString(String s,
                                          String search,
                                          String replace) {

        int p = 0;

        while (p < s.length() && (p = s.indexOf(search, p)) >= 0) {
            s = s.substring(0, p) + replace +
                    s.substring(p + search.length(), s.length());
            p += replace.length();
        }
        return s;
    }


    static public String getSQLDateFromYYYYMMDD(String str) {

        if (str == null)
            return "1900-1-1";

        if (str.length() < 8)
            return "1900-1-1";

        return "\'" + str.substring(4, 6) + "/" + str.substring(6, 8) + "/" + str.substring(0, 4) + "\'";

    }

    static public String getRawSQLDateStrFromMMDDYYYY(String str) {

        if (str == null)
            return "1900-1-1";

        if (str.length() < 8)
            return "1900-1-1";

        return str.substring(0, 2) + "/" + str.substring(2, 4) + "/" + str.substring(4, 8);

    }

    static public String getRawSQLDateStrFromYYYYMMDD(String str) {

        if (str == null)
            return "1900-1-1";

        if (str.length() < 8 || str.startsWith("0000000"))
            return "1900-1-1";

        return str.substring(4, 6) + "/" + str.substring(6, 8) + "/" + str.substring(0, 4);

    }

    static public String getSQLDateFromUSSlashDate(String str) {

        if (str == null)
            return "";

        if (str.length() < 8)
            return "";
        str = str.trim();
        StringTokenizer tempTokenizer = new StringTokenizer(str, "/");

        String year = "0000";
        String month = "00";
        String day = "00";

        if (tempTokenizer.hasMoreTokens())
            month = tempTokenizer.nextToken();
        if (tempTokenizer.hasMoreTokens())
            day = tempTokenizer.nextToken();
        if (tempTokenizer.hasMoreTokens())
            year = tempTokenizer.nextToken();

        if (year.length() == 2) year = "20" + year;

        return year + "-" + month + "-" + day + " 00:00:00";

    }

    static public String getYYYYMMDDFromSQLDate(String str) {

        if (str == null)
            return "";

        if (str.length() < 8)
            return "";
        str = str.trim();
        if (str.indexOf(" ") > 0)
            str = str.substring(0, str.indexOf(" "));
        StringTokenizer tempTokenizer = new StringTokenizer(str, "-");

        String year = "0000";
        String month = "00";
        String day = "00";

        if (tempTokenizer.hasMoreTokens())
            year = tempTokenizer.nextToken();
        if (tempTokenizer.hasMoreTokens())
            month = tempTokenizer.nextToken();
        if (tempTokenizer.hasMoreTokens())
            day = tempTokenizer.nextToken();

        return year + month + day;

    }

    static public String getSlashDateFromSQLDate(String str) {

        if (str == null)
            return "";

        if (str.length() < 8)
            return "";
        str = str.trim();
        if (str.indexOf(" ") > 0)
            str = str.substring(0, str.indexOf(" "));
        StringTokenizer tempTokenizer = new StringTokenizer(str, "-");

        String year = "0000";
        String month = "00";
        String day = "00";

        if (tempTokenizer.hasMoreTokens())
            year = tempTokenizer.nextToken();
        if (tempTokenizer.hasMoreTokens())
            month = tempTokenizer.nextToken();
        if (tempTokenizer.hasMoreTokens())
            day = tempTokenizer.nextToken();

        return month + "/" + day + "/" + year;

    }

    static public String getSQLDateForToday() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return formatter.format(Calendar.getInstance().getTime());
    }

    static public String getYYYYMMDDDateForToday() {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return formatter.format(Calendar.getInstance().getTime());
    }

    static public String getMMDDYYSlashedDateForToday() {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy", Locale.US);
        return formatter.format(Calendar.getInstance().getTime());
    }

    static public String getYYYYMMDDDateForToday(int offsetDays) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, offsetDays);

        return formatter.format(now.getTime());
    }

    static public String getSQLDateTimeForToday() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:m:ss.SSS", Locale.US);
        return formatter.format(Calendar.getInstance().getTime());
    }

    static public String getSQLDateTimeForAdjustedDate(int offsetDays) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:m:ss.SSS", Locale.US);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, offsetDays);
        return formatter.format(now.getTime());
    }


    static public String getSQLDateNoTimeForToday() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return formatter.format(Calendar.getInstance().getTime()) + " 00:00:00.000";
    }

    static public String getSQLDateNoTimeForAdjustedDate(int calendarType, int value) {
        return getSQLDateNoTimeForAdjustedDate(calendarType, value, false);
    }

    static public String getSQLDateNoTimeForAdjustedDate(int calendarType, int value, boolean includeZeroedTimeString) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.add(calendarType, value);
        return formatter.format(cal.getTime()) + (includeZeroedTimeString ? " 00:00:00.000" : "");
    }


    static public Vector parseableStringToVector(String raw) {

        Vector vec = new Vector();

        if (raw == null)
            return vec;
        if (raw.trim().length() < 1)
            return vec;

        StringTokenizer tempTokenizer = new StringTokenizer(raw, "&");

        while (tempTokenizer.hasMoreTokens()) {
            try {
                vec.addElement(java.net.URLDecoder.decode(tempTokenizer.nextToken()));
            } catch (Exception ex) {
            }
        }

        return vec;
    }

    static public String[] parseableStringToStringArray(String raw) {

        if (raw == null)
            return new String[0];
        if (raw.trim().length() < 1)
            return new String[0];

        StringTokenizer tempTokenizer = new StringTokenizer(raw, "&");

        String[] stray = new String[tempTokenizer.countTokens()];

        for (int i = 0; i < java.lang.reflect.Array.getLength(stray); i++) {
            try {
                stray[i] = java.net.URLDecoder.decode(tempTokenizer.nextToken());
            } catch (Exception ex) {
            }
        }


        return stray;
    }

    /**
     * Calculates the number of days between two calendar days in a manner
     * which is independent of the Calendar type used.
     *
     * @param d1 The first date.
     * @param d2 The second date.
     * @return The number of days between the two dates.  Zero is
     *         returned if the dates are the same, one if the dates are
     *         adjacent, etc.  The order of the dates
     *         does not matter, the value returned is always >= 0.
     *         If Calendar types of d1 and d2
     *         are different, the result may not be accurate.
     */
    public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
        if (d1.after(d2)) {  // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(java.util.Calendar.DAY_OF_YEAR) -
                d1.get(java.util.Calendar.DAY_OF_YEAR);
        int y2 = d2.get(java.util.Calendar.YEAR);
        if (d1.get(java.util.Calendar.YEAR) != y2) {
            d1 = (java.util.Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            } while (d1.get(java.util.Calendar.YEAR) != y2);
        }
        return days;
    } // getDaysBetween()

    static public String vectorToString(Vector vec) {


        if (vec == null)
            return "";

        StringBuffer sb = new StringBuffer();

        if (vec.size() > 0)
            sb.append("\n::" + vec.elementAt(0).toString());

        for (int i = 1; i < vec.size(); i++) {
            sb.append("\n::" + vec.elementAt(i).toString());
        }

        return sb.toString();
    }

    static public String vectorToParseableString(Vector vec) {


        if (vec == null)
            return "";

        StringBuffer sb = new StringBuffer();

        if (vec.size() > 0)
            sb.append(java.net.URLEncoder.encode(vec.elementAt(0).toString()));

        for (int i = 1; i < vec.size(); i++) {
            sb.append("&" + java.net.URLEncoder.encode(vec.elementAt(i).toString()));
        }

        return sb.toString();
    }

    //added on May 23
    public static String getStringParam(HttpServletRequest req, String paramName, String defaultValue) {
        String param = req.getParameter(paramName);

        if (param == null)
            param = defaultValue;

        return param.trim();

    }

    public static Vector getStringVector(HttpServletRequest req, String paramName, Vector defaultValue) {
        String[] param_ay = req.getParameterValues(paramName);
        Vector param = new Vector();

        if (param_ay == null)
            param = defaultValue;
        else {
            if (param_ay.length > 0) {
                for (int i = 0; i < param_ay.length; i++) {
                    param.addElement(param_ay[i]);
                }
            }
        }

        return param;

    }

    public static int getIntegerParam(HttpServletRequest req, String paramName, int defaultValue) {
        int param = 0;

        try {
            param = new Integer(req.getParameter(paramName)).intValue();
        } catch (Exception ex) {
            param = defaultValue;
        }

        return param;

    }

    public static String getFirstMatchingItem(String match, ArrayList model) {
        match = match.toUpperCase().trim();
        int size = model.size();
        //Arrays.sort(c);
        for (int i = 0; i < size; i++) {
            String anItem = (model.get(i).toString()).toUpperCase().trim();
            if (anItem.startsWith(match)) {
                ////log.debug("First matching country: "+aCountry);
                return model.get(i).toString();
            }

        }

        return "";
    }


    public static String getSQLDateString(java.util.Date aDate) throws Exception {
        SimpleDateFormat simple_date_formatter = new SimpleDateFormat("MM/dd/yyyy");
        return simple_date_formatter.format(aDate);
    }

    public static String getSQLDateTimeString(java.util.Date aDate) throws Exception {
        SimpleDateFormat simple_date_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return simple_date_formatter.format(aDate);
    }

    public static java.util.Date getDateForSQLDateString(String aDate) throws Exception {
        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(!(aDate.trim().contains(" ")))
        {
            time_formatter = new SimpleDateFormat("yyyy-MM-dd");
        }
                                         
        return time_formatter.parse(aDate);
    }

    //warning - does not detect int overflow!
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isAuthorizedClient(String client_id, String client_auth_code) {
        if (client_id.equals(decryptData(client_auth_code))) {
            return true;
        }

        return false;

    }

    public static Date setTimeToMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( date );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }


    public static void mailSupport(String title, String message) {
        try {
            Mailer.sendMail(title, message, "owditadmin@owd.com", "support@owd.com");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static URLConnection getConnection(String aLink) throws IOException {
////log.debug("aLink: "+aLink);
        if (aLink.startsWith("https")) {
            System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
//            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        }
        URL url = new URL(aLink);
        URLConnection cxn = null;

        cxn = (URLConnection) url.openConnection();
        cxn.setRequestProperty("Content-Type", "text/xml");
        cxn.setDoInput(true);
        cxn.setDoOutput(true);
        cxn.setUseCaches(false);

        //
        //  cxn.connect();


        return cxn;
    }


}
