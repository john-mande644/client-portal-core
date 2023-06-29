/*
	FTPOrderSource.java

	Author:			Stewart Buskirk
	Description:	<describe the FTPOrderSource class here>
*/

package com.owd.core.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public  class FTPManager
{
private final static Logger log =  LogManager.getLogger();
               //todo fix exception throwing!!!



	public FTPManager(String host, String user, String pass)
	{
	    ftpHost = host;
        ftpUser = user;
        ftpPass = pass;
	}

    public Vector getFileNames() throws Exception
    {
          return getFileNames(null);

    }


    public String checkSetDirectory(String checkPath) throws Exception
    {

        FtpBean ftp = new FtpBean();


            ftp.ftpConnect(getFtpHost(), getFtpUser(), getFtpPass());

            ftp.setDirectory(checkPath);


        return  ftp.getDirectory();
    }

    public boolean makeDirectory(String newDirName) throws Exception
    {
        FtpBean ftp = new FtpBean();


        try{

            ftp.ftpConnect(getFtpHost(), getFtpUser(), getFtpPass());

            ftp.setDirectory(getWriteDirectory());
            if(ftp.changeWorkingDirectory(newDirName))
            {
                ftp.toParentDirectory();
                return true;
            }   else
            {
               return ftp.makeDirectory(newDirName);
            }


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;

        }
        finally
        {
            try{ftp.close();}catch (Exception ex){}
        }

    }
    public List<String> getRecordingsInReadDir(int max) throws Exception
    {
        List<String> names = new ArrayList<String>();
        FtpBean ftp = new FtpBean();


        try{

            ftp.ftpConnect(getFtpHost(), getFtpUser(), getFtpPass());

            ftp.setDirectory(getReadDirectory());
            names = getRecordingsInReadDir(ftp,max);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;

        }
        finally
        {
            try{ftp.close();}catch (Exception ex){}
        }

        return names;

    }

    public List<String> getRecordingsInReadDir(FtpBean currFtp, int max) throws Exception
    {
        List<String> names = new ArrayList<String>();


        try{

            List<FTPFile> files = (List<FTPFile>) Arrays.asList(currFtp.getFileEntries());

            log.debug("ftp found " + files.size() + " files!");

            for (FTPFile finfo:files) {
                String fname = finfo.getName();
                log.debug(fname);
                if(finfo.isDirectory())
                {
                    String oldReadDir = currFtp.getDirectory()+"/";
                    log.debug(oldReadDir);
                    currFtp.setDirectory(currFtp.getDirectory()+"/"+fname+"/");
                    log.debug(currFtp.getDirectory()+"/"+fname+"/");
                    names.addAll(getRecordingsInReadDir(currFtp, max));
                    if(names.size()>=max && max>0)
                    {
                        return names;
                    }
                    currFtp.setDirectory(oldReadDir);
                } else
                {
                    if(fname.endsWith(".wav"))
                    {
                        names.add(currFtp.getDirectory()+"/"+finfo.getName());
                    }
                }
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;

        }
        return names;

    }



	public Vector getFileNames(String suffixFilter) throws Exception
	{    FtpBean ftp = new FtpBean();
		Vector fileNames = null;
		try{

			ftp.ftpConnect(getFtpHost(), getFtpUser(), getFtpPass());

			ftp.setDirectory(getReadDirectory());

			fileNames = ftp.getFilesFromDirectory(suffixFilter);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
            throw ex;

        }
		finally
		{
			try{ftp.close();}catch (Exception ex){}
		}

		if(fileNames == null) fileNames = new Vector();
		return fileNames;
	}

	public byte[] getFileData(String fileName)  throws Exception
	{       FtpBean ftp = new FtpBean();
		byte[] fileData = null;
		try
		{
			ftp.ftpConnect(getFtpHost(), getFtpUser(), getFtpPass());
			ftp.setDirectory(getReadDirectory());
			fileData =  ftp.getBinaryFile(fileName);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
            throw ex;

        }
		finally
		{
			try{ftp.close();}catch (Exception ex){}
		}

		if(fileData == null) fileData = new byte[0];
		return fileData;

	}

	public void moveFile(String startPath, String endPath) throws Exception
	{      FtpBean ftp = new FtpBean();
		try
		{
			ftp.ftpConnect(getFtpHost(), getFtpUser(), getFtpPass());
			ftp.setDirectory(getReadDirectory());
			ftp.fileRename(startPath, endPath);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
            throw ex;

        }
		finally
		{
			try{ftp.close();}catch (Exception ex){}
		}
	}

	public void writeFile(String fileName, byte[] fileData) throws Exception
	{
		FtpBean ftp2 = new FtpBean();
		try
		{


			ftp2.ftpConnect(getFtpHost(), getFtpUser(), getFtpPass());
			ftp2.setDirectory(getWriteDirectory());
			ftp2.putBinaryFile(fileName,fileData);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
            throw ex;
		}
		finally
		{
			try{ftp2.close();}catch (Exception ex){}
		}
	}

	public String getFtpHost()
	{
		return ftpHost;
	}

	public void setFtpHost(String value)
	{
		ftpHost = value;
	}

	public String getFtpUser()
	{
		return ftpUser;
	}

	public void setFtpUser(String value)
	{
		ftpUser = value;
	}

	public String getFtpPass()
	{
		return ftpPass;
	}

	public void setFtpPass(String value)
	{
		ftpPass = value;
	}

	public String getReadDirectory()
	{
		return readDirectory;
	}

	public void setReadDirectory(String value)
	{
		readDirectory = value;
	}

	public String getWriteDirectory()
	{
		return writeDirectory;
	}

	public void setWriteDirectory(String value)
	{
		writeDirectory = value;
	}

	public String getFtpSuffix()
	{
		return ftpSuffix;
	}

	public void setFtpSuffix(String value)
	{
		ftpSuffix = value;
	}


    public void deleteFile(String fileName) throws Exception
    {
        FtpBean ftp2 = new FtpBean();
		try
		{


			ftp2.ftpConnect(getFtpHost(), getFtpUser(), getFtpPass());
			ftp2.setDirectory(getWriteDirectory());
			ftp2.fileDelete(fileName);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
            throw ex;

        }
		finally
		{
			try{ftp2.close();}catch (Exception ex){}
		}
    }
//getters/setters


      public static void main(String[] args) throws Exception
      {

          byte[] barray = null;
          FTPManager ftp = new FTPManager("172.16.2.150", "calls", "owdcalls");
          try {
              ftp.setReadDirectory("/calls/inbound");

              ftp.checkSetDirectory("/calls/inbound/");
              barray = ftp.getFileData("F9511679.wav");

              log.debug(barray.length
              );
          } catch (Exception e) {
              e.printStackTrace();
          }



         // ftp.getRecordingsInReadDir(1);
      }

//private fields

	private String ftpHost = "ftp.owd.com";
	private String ftpUser = "anonymous";
	private String ftpPass = "null@owd.com";
	private String readDirectory = "/";
	private String writeDirectory = "/";
	private String ftpSuffix = "";

}


/* FTPOrderSource.java */
