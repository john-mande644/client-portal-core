
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Document output result information.
 * 
 * <p>Java class for DocumentOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentOutput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="xmlOutput" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="binaryOutput" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="outputFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imageList" type="{urn:connectship-com:ampcore}ImageItemList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentOutput", propOrder = {
    "xmlOutput",
    "binaryOutput",
    "outputFile",
    "imageList"
})
public class DocumentOutput {

    protected Object xmlOutput;
    protected byte[] binaryOutput;
    protected String outputFile;
    protected ImageItemList imageList;

    /**
     * Gets the value of the xmlOutput property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getXmlOutput() {
        return xmlOutput;
    }

    /**
     * Sets the value of the xmlOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setXmlOutput(Object value) {
        this.xmlOutput = value;
    }

    /**
     * Gets the value of the binaryOutput property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBinaryOutput() {
        return binaryOutput;
    }

    /**
     * Sets the value of the binaryOutput property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBinaryOutput(byte[] value) {
        this.binaryOutput = ((byte[]) value);
    }

    /**
     * Gets the value of the outputFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * Sets the value of the outputFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFile(String value) {
        this.outputFile = value;
    }

    /**
     * Gets the value of the imageList property.
     * 
     * @return
     *     possible object is
     *     {@link ImageItemList }
     *     
     */
    public ImageItemList getImageList() {
        return imageList;
    }

    /**
     * Sets the value of the imageList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageItemList }
     *     
     */
    public void setImageList(ImageItemList value) {
        this.imageList = value;
    }

}
