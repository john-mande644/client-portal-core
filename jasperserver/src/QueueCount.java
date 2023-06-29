import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: 12/28/10
 * Time: 8:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueueCount {

  public static Map<String,String> printerMap;
    static public void main(String[] args) {
        //   System.out.println(getQueueCountProgramPath());
        System.out.println(getQueueCount("Samsung"));
    }


    public static int getQueueCount(String printer) {
        StringBuffer output = new StringBuffer();
        BufferedReader input = null;
        File countFile = new File(System.getProperty("user.dir") + "\\printJobs.txt");
            System.out.println(System.getProperty("user.dir"));
        int count = -1;
        printerMap = new TreeMap<String,String>();
        try {
            String line;
            Process p = Runtime.getRuntime().exec
                    ("\"" + System.getProperty("user.dir") + "\\OWDPrintJobCount.exe\"");
            input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                output.append(line);
            }
            input.close();


        if ("0".equals(output)) {
            System.out.println("0 output");
            return 0;
        } else {

            if (countFile.exists()) {
                String countString = getContents(countFile,printer);
                count = Integer.parseInt(countString);
                countFile.delete();
            } else {
                System.out.println("Count file not found!");
            }


        }
             } catch (Exception err) {
            err.printStackTrace();
        }finally
        {
            if(input!=null)
            {
               try{ input.close();  }catch(Exception ex){}

            }
        }
        System.out.println("Queue Count: "+count);
        return count;
    }

    static private String getContents(File aFile,String printer) {
        //...checks on aFile are elided
        StringBuilder contents = new StringBuilder();

        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; //not declared within while loop
                /*
                * readLine is a bit quirky :
                * it returns the content of a line MINUS the newline.
                * it returns null only for the END of the stream.
                * it returns an empty String if two newlines appear in a row.
                */
                while ((line = input.readLine()) != null) {
                   // contents.append(line);
                    String[] splitter = line.split("=");
                    printerMap.put(splitter[0],splitter[1]);
                    // contents.append(System.getProperty("line.separator"));
                }
               //  System.out.println(printerMap);
                if(printerMap.containsKey(printer)){
                    return printerMap.get(printer);
                } else{
                    return "0";
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "0";
    }


}
