
package com.owd.dc.packing.beans.Lyft;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.owd.dc.packing.beans.Lyft package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LyftReturnLookup_QNAME = new QName("", "lyftReturnLookup");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.owd.dc.packing.beans.Lyft
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LyftReturnLookupType }
     * 
     */
    public LyftReturnLookupType createLyftReturnLookupType() {
        return new LyftReturnLookupType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LyftReturnLookupType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lyftReturnLookup")
    public JAXBElement<LyftReturnLookupType> createLyftReturnLookup(LyftReturnLookupType value) {
        return new JAXBElement<LyftReturnLookupType>(_LyftReturnLookup_QNAME, LyftReturnLookupType.class, null, value);
    }

}
