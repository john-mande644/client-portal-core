package com.owd.dc.packing;

import junit.framework.TestCase;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathException;
import java.io.ByteArrayInputStream;

public class UtilTest extends TestCase {
    public void testProcessAdditionalFackItems() {
       /* try {
            String xml = "<OWDPack><packageAddons>" +
                    "<addon>" +
                    "<owdBoxTypeFkey>23143</owdBoxTypeFkey>" +
                    "<supplyTrackingFkey></supplyTrackingFkey>" +
                    "<quantity>2</quantity>" +
                    "</addon>" +
                    "<addon>" +
                    "<owdBoxTypeFkey></owdBoxTypeFkey>" +
                    "<supplyTrackingFkey>43</supplyTrackingFkey>" +
                    "<quantity>4</quantity>" +
                    "</addon>" +
                    "</packageAddons>" +
                    "</OWDPack>";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Now use the factory to create a DOM parser (a.k.a. a DocumentBuilder)
            DocumentBuilder parser = factory.newDocumentBuilder();
            // Parse the file and build a Document tree to represent its content
            Document document = parser.parse(new ByteArrayInputStream(xml.getBytes()));
            //get the order node
            Node owdPack = XPathAPI.selectSingleNode(document, "/OWDPack");
            NodeIterator addons = XPathAPI.selectNodeIterator(owdPack, "./packageAddons/addon");
            try {
                Util.processAdditionalPackageAddons(addons, "gonna doa little thing");
            } catch (XPathException ex) {
                ex.printStackTrace();
                fail(ex.getMessage());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            fail(ex.getMessage());
        }
        assertEquals(true,true);*/
    }
}
