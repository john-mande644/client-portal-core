import org.apache.commons.exec.*;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Aug 17, 2010
 * Time: 11:21:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class PDFPrintLibClass
{
    private static List<String> paths = null;

    private static List<String> wrapPaths = null;

     //get all available system paths to acrowrap.exe executable - (must be downloaded and installed on system to use)
    public static synchronized String getAcrowrapPath()
    {
        if (wrapPaths == null)
        {
            wrapPaths = getAcroWrapPaths();
        }

        return wrapPaths.get(0);
    }

    private static List<String> foxitPaths = null;

    //get all available system paths to Fox IT executable - assumes it is located at root of Programs folder (must be downloaded and installed on system to use)
    public static synchronized String getFoxitPath()
    {
        if (foxitPaths == null)
        {
            foxitPaths = getFoxitPaths();
        }

        return foxitPaths.get(0);
    }

    //get primary path to acrobat reader installation
    public static synchronized String getAcrobatPath()
    {
        if (paths == null)
        {
            paths = getAcrobatReaderPaths();
        }

        return paths.get(0);
    }

    //print PDF through acrobat reader
    public static void printPDF(byte[] pdfBytes, String pPrinter) throws Exception
    {

        System.out.println("" + getAcrobatPath());
        CommandLine commandLine = new CommandLine(getAcrobatPath());
        commandLine.addArgument("/n");
        commandLine.addArgument("/t");

        File tempFile = writeTempFile(pdfBytes);
        commandLine.addArgument(tempFile.getAbsolutePath());
        commandLine.addArgument("${printer}");
        HashMap map = new HashMap();
        map.put("printer", "\"" + pPrinter + "\"");
        commandLine.setSubstitutionMap(map);

        tempFile.deleteOnExit();

        System.out.println("" + commandLine.toString());
        //  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(3 * 1000);

        Executor executor = new DefaultExecutor();
        // executor.setExitValue(-1);
        executor.setStreamHandler(new PumpStreamHandler());
        executor.setWatchdog(watchdog);
        int exitValue = executor.execute(commandLine);

// watchdog.destroyProcess();
        System.out.println("exit:" + exitValue);
        if (tempFile.exists())
        {
            //  tempFile.deleteOnExit();
            tempFile.delete();
        }
        if (exitValue == -1 || watchdog.killedProcess())
        {
            throw new Exception("Unable to verify successful printing");
        }
    }

    //print PDF through acrowrap.exe (must be downloaded and installed on system to use)
    public static void printWrappedPDF(byte[] pdfBytes, String pPrinter) throws Exception
    {

        System.out.println("" + getAcrowrapPath());
        CommandLine commandLine = new CommandLine(getAcrowrapPath());
        //   commandLine.addArgument("/n");
        commandLine.addArgument("/t");

        File tempFile = writeTempFile(pdfBytes);
        commandLine.addArgument("${filepath}");
        commandLine.addArgument("${printer}");
        HashMap map = new HashMap();
        map.put("printer", "\"" + pPrinter + "\"");
        map.put("filepath", "\"" + tempFile.getAbsolutePath() + "\"");
        commandLine.setSubstitutionMap(map);

        tempFile.deleteOnExit();

        System.out.println("" + commandLine.toString());
        //  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(30 * 1000);

        Executor executor = new DefaultExecutor();
        // executor.setExitValue(-1);
        executor.setStreamHandler(new PumpStreamHandler());
        executor.setWatchdog(watchdog);
        int exitValue = executor.execute(commandLine);

// watchdog.destroyProcess();
        System.out.println("exit:" + exitValue);
        if (tempFile.exists())
        {
            //  tempFile.deleteOnExit();
            tempFile.delete();
        }
        if (exitValue == -1 || watchdog.killedProcess())
        {
            throw new Exception("Unable to verify successful printing");
        }
    }

    //print PDF through Fox IT program - must be downloaded and installed at root of Programs folder to use
    public static void printFoxitPDF(byte[] pdfBytes, String pPrinter) throws Exception
    {

        System.out.println("" + getFoxitPath());
        CommandLine commandLine = new CommandLine(getFoxitPath());
        //   commandLine.addArgument("/n");
        commandLine.addArgument("-t");

        File tempFile = writeTempFile(pdfBytes);
        commandLine.addArgument("${filepath}");
        commandLine.addArgument("${printer}");
        HashMap map = new HashMap();
        map.put("printer", "\"" + pPrinter + "\"");
        map.put("filepath", "\"" + tempFile.getAbsolutePath() + "\"");
        commandLine.setSubstitutionMap(map);

        tempFile.deleteOnExit();

        System.out.println("" + commandLine.toString());
        //  DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

        ExecuteWatchdog watchdog = new ExecuteWatchdog(30 * 1000);

        Executor executor = new DefaultExecutor();
        // executor.setExitValue(-1);
        executor.setStreamHandler(new PumpStreamHandler());
        executor.setWatchdog(watchdog);
        int exitValue = executor.execute(commandLine);

// watchdog.destroyProcess();
        System.out.println("exit:" + exitValue);
        if (tempFile.exists())
        {
            //  tempFile.deleteOnExit();
            tempFile.delete();
        }
        if (exitValue == -1 || watchdog.killedProcess())
        {
            throw new Exception("Unable to verify successful printing");
        }
    }

    /**
     * Creates a new instance of PDFPrintLibClass
     */
    public static void main(String[] args) throws Exception
    {

        for (int i = 1; i < 2; i++)
        {

            File dataFile = new File("I:\\Users\\StewartBuskirk\\AppData\\Local\\Temp\\3884433_20101031_19-00-56.169.pdf");

            FileInputStream in = new FileInputStream(dataFile);
            byte fileContent[] = new byte[(int) dataFile.length()];
            in.read(fileContent);
            in.close();
            System.out.println("tempfile byte array size:" + dataFile.length());
            PDFPrintLibClass.printFoxitPDF(fileContent, "samsung");
        }
    }


    static public String getPathString(List<String> path)
    {
        StringBuffer sb = new StringBuffer();


        for (String level : path)
        {
            boolean quotes = false;
            if (level.contains(" "))
            {
                quotes = false;
            }
            sb.append((quotes ? "\"" : "") + level + (quotes ? "\"" : "") + (level.endsWith(".exe") ? "" : "\\"));
        }
        return sb.toString();
    }

    //get all possible paths to Fox IT executable - searches Programs and Programs (x86) folders.
    public static List<String> getFoxitPaths()
    {

        List<String> commandStrings = new ArrayList<String>();


        List<File> files = Arrays.asList(File.listRoots());
        for (File f : files)
        {
            String s1 = FileSystemView.getFileSystemView().getSystemDisplayName(f);
            String s2 = FileSystemView.getFileSystemView().getSystemTypeDescription(f);

            if (s2.equalsIgnoreCase("Local Disk") && (!(s1.toUpperCase().contains("RECOVERY"))) && (!(s1.toUpperCase().contains("BACKUP"))))
            {
                List<String> path = new ArrayList<String>();
                //  System.out.println("getSystemDisplayName : " + s1);
                // System.out.println("getSystemTypeDescription : " + s2);
                path.add(s1.substring(s1.indexOf(":") - 1, s1.indexOf(":") + 1));

                List<File> topDirs = Arrays.asList(f.listFiles());
                for (File topDir : topDirs)
                {
                    if (FileSystemView.getFileSystemView().getSystemDisplayName(topDir).contains("Program Files"))
                    {
                        //   System.out.println("-getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (topDir));
                        //    System.out.println("getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (topDir));
                        path.add(FileSystemView.getFileSystemView().getSystemDisplayName(topDir));
                        List<File> adobeDirs = Arrays.asList(topDir.listFiles());
                        for (File adobeDir : adobeDirs)
                        {

                            System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir));
                            if (FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir).toUpperCase().startsWith("FOXIT"))
                            {
                                System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir));
                                System.out.println("--getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription(adobeDir));
                                path.add(FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir));

                                commandStrings.add(getPathString(path));

                                path.remove(path.size() - 1);
                            }


                        }
                        path.remove(path.size() - 1);
                    }


                }
                path.remove(path.size() - 1);
            }
        }

        return commandStrings;
    }

    public static List<String> getAcroWrapPaths()
    {

        List<String> commandStrings = new ArrayList<String>();


        List<File> files = Arrays.asList(File.listRoots());
        for (File f : files)
        {
            String s1 = FileSystemView.getFileSystemView().getSystemDisplayName(f);
            String s2 = FileSystemView.getFileSystemView().getSystemTypeDescription(f);

            if (s2.equalsIgnoreCase("Local Disk") && (!(s1.toUpperCase().contains("RECOVERY"))) && (!(s1.toUpperCase().contains("BACKUP"))))
            {
                List<String> path = new ArrayList<String>();
                //  System.out.println("getSystemDisplayName : " + s1);
                // System.out.println("getSystemTypeDescription : " + s2);
                path.add(s1.substring(s1.indexOf(":") - 1, s1.indexOf(":") + 1));

                List<File> topDirs = Arrays.asList(f.listFiles());
                for (File topDir : topDirs)
                {
                    if (FileSystemView.getFileSystemView().getSystemDisplayName(topDir).contains("Program Files"))
                    {
                        //   System.out.println("-getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (topDir));
                        //    System.out.println("getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (topDir));
                        path.add(FileSystemView.getFileSystemView().getSystemDisplayName(topDir));
                        List<File> adobeDirs = Arrays.asList(topDir.listFiles());
                        for (File adobeDir : adobeDirs)
                        {
                            //  System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (adobeDir));
                            if (FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir).toUpperCase().contains("BIOPDF"))
                            {
                                //   System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (adobeDir));
                                //  System.out.println("--getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (adobeDir));
                                path.add(FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir));
                                List<File> reader1Dirs = Arrays.asList(adobeDir.listFiles());
                                for (File reader1Dir : reader1Dirs)
                                {
                                    // System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (reader1Dir));
                                    if (FileSystemView.getFileSystemView().getSystemDisplayName(reader1Dir).toUpperCase().contains("ACROBAT WRAPPER"))
                                    {
                                        //   System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (adobeDir));
                                        //    System.out.println("--getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (reader1Dir));
                                        path.add(FileSystemView.getFileSystemView().getSystemDisplayName(reader1Dir));
                                        List<File> reader2Dirs = Arrays.asList(reader1Dir.listFiles());
                                        for (File reader2Dir : reader2Dirs)
                                        {
                                            System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName(reader2Dir));
                                            if (FileSystemView.getFileSystemView().getSystemDisplayName(reader2Dir).toUpperCase().startsWith("ACROWRAP"))
                                            {
                                                System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir));
                                                System.out.println("--getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription(reader2Dir));
                                                path.add(FileSystemView.getFileSystemView().getSystemDisplayName(reader2Dir));

                                                commandStrings.add(getPathString(path));

                                                path.remove(path.size() - 1);
                                            }

                                        }


                                        path.remove(path.size() - 1);
                                    }


                                }


                                path.remove(path.size() - 1);
                            }


                        }
                        path.remove(path.size() - 1);
                    }


                }
                path.remove(path.size() - 1);
            }
        }

        return commandStrings;
    }

    public static List<String> getAcrobatReaderPaths()
    {

        List<String> commandStrings = new ArrayList<String>();


        List<File> files = Arrays.asList(File.listRoots());
        for (File f : files)
        {
            String s1 = FileSystemView.getFileSystemView().getSystemDisplayName(f);
            String s2 = FileSystemView.getFileSystemView().getSystemTypeDescription(f);

            if (s2.equalsIgnoreCase("Local Disk") && (!(s1.toUpperCase().contains("RECOVERY"))) && (!(s1.toUpperCase().contains("BACKUP"))))
            {
                List<String> path = new ArrayList<String>();
                //  System.out.println("getSystemDisplayName : " + s1);
                // System.out.println("getSystemTypeDescription : " + s2);
                path.add(s1.substring(s1.indexOf(":") - 1, s1.indexOf(":") + 1));

                List<File> topDirs = Arrays.asList(f.listFiles());
                for (File topDir : topDirs)
                {
                    if (FileSystemView.getFileSystemView().getSystemDisplayName(topDir).contains("Program Files"))
                    {
                        //   System.out.println("-getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (topDir));
                        //    System.out.println("getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (topDir));
                        path.add(FileSystemView.getFileSystemView().getSystemDisplayName(topDir));
                        List<File> adobeDirs = Arrays.asList(topDir.listFiles());
                        for (File adobeDir : adobeDirs)
                        {
                            //  System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (adobeDir));
                            if (FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir).toUpperCase().contains("ADOBE"))
                            {
                                //   System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (adobeDir));
                                //  System.out.println("--getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (adobeDir));
                                path.add(FileSystemView.getFileSystemView().getSystemDisplayName(adobeDir));
                                List<File> reader1Dirs = Arrays.asList(adobeDir.listFiles());
                                for (File reader1Dir : reader1Dirs)
                                {
                                    // System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (reader1Dir));
                                    if (FileSystemView.getFileSystemView().getSystemDisplayName(reader1Dir).toUpperCase().contains("READER"))
                                    {
                                        //   System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (adobeDir));
                                        //    System.out.println("--getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (reader1Dir));
                                        path.add(FileSystemView.getFileSystemView().getSystemDisplayName(reader1Dir));
                                        List<File> reader2Dirs = Arrays.asList(reader1Dir.listFiles());
                                        for (File reader2Dir : reader2Dirs)
                                        {
                                            //   System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (reader2Dir));
                                            if (FileSystemView.getFileSystemView().getSystemDisplayName(reader2Dir).toUpperCase().contains("READER"))
                                            {
                                                //   System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (adobeDir));
                                                //    System.out.println("--getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (reader2Dir));
                                                path.add(FileSystemView.getFileSystemView().getSystemDisplayName(reader2Dir));

                                                List<File> acroDirs = Arrays.asList(reader2Dir.listFiles());
                                                for (File acroDir : acroDirs)
                                                {
                                                    //  System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (reader2Dir));
                                                    if (FileSystemView.getFileSystemView().getSystemDisplayName(acroDir).toUpperCase().equals("ACRORD32.EXE"))
                                                    {
                                                        //   System.out.println("--getSystemDisplayName : " + FileSystemView.getFileSystemView().getSystemDisplayName (adobeDir));
                                                        //     System.out.println("--getSystemTypeDescription : " + FileSystemView.getFileSystemView().getSystemTypeDescription (reader2Dir));
                                                        path.add(FileSystemView.getFileSystemView().getSystemDisplayName(acroDir));

                                                        commandStrings.add(getPathString(path));

                                                        path.remove(path.size() - 1);
                                                    }


                                                }

                                                path.remove(path.size() - 1);
                                            }


                                        }


                                        path.remove(path.size() - 1);
                                    }


                                }


                                path.remove(path.size() - 1);
                            }


                        }
                        path.remove(path.size() - 1);
                    }


                }
                path.remove(path.size() - 1);
            }
        }

        return commandStrings;
    }


    private static File writeTempFile(byte[] data) throws Exception
    {
        //create a temp file
        File temp = File.createTempFile("" + Thread.currentThread().getId() + Calendar.getInstance().getTimeInMillis(), ".tmp");
        try
        {
            System.out.println("writing " + data.length + " bytes to file " + temp.getAbsolutePath());
            FileOutputStream out = new FileOutputStream(temp);
            out.write(data, 0, data.length);
            out.flush();
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Done writing file to temp");
        return temp;
    }

    public static class DefaultExecuteResultHandler implements ExecuteResultHandler
    {

        private static final int SLEEP_TIME_MS = 100;

        /**
         * Keep track if the process is still running
         */
        private boolean hasResult;

        /**
         * The exit value of the finished process
         */
        private int exitValue;

        /**
         * Any offending exception
         */
        private ExecuteException exception;

        /**
         * @see org.apache.commons.exec.ExecuteResultHandler#onProcessComplete(int)
         */
        synchronized public void onProcessComplete(int exitValue)
        {
            this.exitValue = exitValue;
            this.hasResult = true;
        }

        /**
         * @see org.apache.commons.exec.ExecuteResultHandler#onProcessFailed(org.apache.commons.exec.ExecuteException)
         */
        synchronized public void onProcessFailed(ExecuteException e)
        {
            this.exception = e;
            exitValue = e.getExitValue();
            this.hasResult = true;
        }

        /**
         * Get the <code>exception<code> causing the process execution to fail.
         *
         * @return Returns the exception.
         * @throws IllegalStateException if the process has not exited yet
         */
        synchronized public ExecuteException getException()
        {
            if (!hasResult)
                throw new IllegalStateException("The process has not exited yet therefore no result is available ...");
            return exception;
        }

        /**
         * Get the <code>exitValue<code> of the process.
         *
         * @return Returns the exitValue.
         * @throws IllegalStateException if the process has not exited yet
         */
        synchronized public int getExitValue()
        {
            if (!hasResult)
                throw new IllegalStateException("The process has not exited yet therefore no result is available ...");
            return exitValue;
        }

        /**
         * Has the process exited and a result is available?
         *
         * @return true if a result of the execution is available
         */
        synchronized public boolean hasResult()
        {
            return hasResult;
        }

        /**
         * Causes the current thread to wait, if necessary, until the
         * process has terminated. This method returns immediately if
         * the process has already terminated. If the process has
         * not yet terminated, the calling thread will be blocked until the
         * process exits.
         *
         * @return the exit value of the process.
         * @throws InterruptedException if the current thread is
         *                              {@linkplain Thread#interrupt() interrupted} by another
         *                              thread while it is waiting, then the wait is ended and
         *                              an {@link InterruptedException} is thrown.
         * @throws ExecuteException     re-thrown exception if the process
         *                              execution has failed due to ExecuteException
         */
        public int waitFor() throws InterruptedException, ExecuteException
        {
            while (!this.hasResult())
            {
                Thread.sleep(SLEEP_TIME_MS);
            }

            if (getException() == null)
            {
                return getExitValue();
            } else
            {
                throw getException();
            }
        }
    }
}
