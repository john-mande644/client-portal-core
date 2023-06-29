import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import static org.junit.Assert.*;

/**
 * Created by danny on 11/20/2018.
 */
public class attachPDFtoOrderTest {

    @Test
    public void sameToOrderTest(){

        try {
            File label = new File("src\\test\\java\\helperFiles\\order.pdf");
            byte[] image = Files.readAllBytes(label.toPath());

            boolean success = attachPDFtoOrder.savePDFtoOrderInputStream("25550675",new ByteArrayInputStream(image));
            assertTrue(success);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
