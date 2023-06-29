/**
* SFSDownloadImporter.java
* importing order information from csv files through a Http connection
*/

package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.crypto.UUDecoder;
import com.owd.jobs.jobobjects.Harvest.HarvestCore;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
*
*
*/

public class HarvestSyncJob extends  OWDStatefulJob
{
private final static Logger log =  LogManager.getLogger();

/**
*/


      public void internalExecute() {
          try{


          HarvestCore.syncProjectsWithClients();
          HarvestCore.pullRecentTimerEvents(10);

          }catch(Exception ex)
          {
              ex.printStackTrace();
              try{
              Mailer.sendMail("Harvest API Exception", ex.getMessage()+"\r\n"+OWDUtilities.getStackTraceAsString(ex), "casetracker@owd.com");
          }catch(Exception ex2)
          {

              ex2.printStackTrace();
          }
          }
    }

    static 	public void main(String[] args) throws Exception
    {

        run();
    }



}
