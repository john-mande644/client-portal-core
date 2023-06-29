
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listDeleteSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listDeleteSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}basicImportSettings">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="listDeleteMode" type="{http://service.admin.ws.five9.com/v2/}listDeleteMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listDeleteSettings", propOrder = {
    "listDeleteMode"
})
public class ListDeleteSettings
    extends BasicImportSettings
{

    protected ListDeleteMode listDeleteMode;

    /**
     * Gets the value of the listDeleteMode property.
     * 
     * @return
     *     possible object is
     *     {@link ListDeleteMode }
     *     
     */
    public ListDeleteMode getListDeleteMode() {
        return listDeleteMode;
    }

    /**
     * Sets the value of the listDeleteMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListDeleteMode }
     *     
     */
    public void setListDeleteMode(ListDeleteMode value) {
        this.listDeleteMode = value;
    }

}
