import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Jun 29, 2010
 * Time: 4:26:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrintServerTest
{
    public static void main(String[] args) throws Exception {

        String rootPath = System.getProperty("user.dir") +

                        System.getProperty("file.separator");

        String filePath = rootPath + "JReport"
                + System.getProperty("file.separator") + "packingslips"
                + System.getProperty("file.separator") + "55" + "-" + "pack" + ".cls";

        System.out.println(""+filePath);
       File file = new File(""+filePath);
        if(file.exists())
        {
            System.out.println("exists");
        }   else
        {
            System.out.println("does not exist");
        }

    }
}
