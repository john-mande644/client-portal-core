package TestGSSMailer;

import com.owd.dc.manifest.Manifestxml.DispatchXml;
import com.owd.dc.manifest.Manifestxml.Manifest;
import org.apache.axis.message.MessageElement;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 12, 2010
 * Time: 4:27:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class messagetestingelement {

    public static void main(String[] args){
      try{
       MessageElement man = new MessageElement(new QName("Manifest"));
        man.addAttribute("","PackageID","222");
        man.addChildElement("Dispatch").addTextNode("hello");



        System.out.println(man.getAsString());

       DispatchXml ds = new DispatchXml();
               ds.setDispatchDateTime();
          Manifest mani = new Manifest();
          mani.setDispatch(ds);

          System.out.println(mani.getXml().getAsString());
          

      }catch (Exception e){
                   e.printStackTrace();

      }

    }
}
