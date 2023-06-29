
package com.owd.alittlePlaying.LyftProjectThings;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.owd.alittlePlaying.LyftProjectThings package. 
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

    private final static QName _Lookup_QNAME = new QName("", "lookup");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.owd.alittlePlaying.LyftProjectThings
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LookupType }
     * 
     */
    public LookupType createLookupType() {
        return new LookupType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lookup")
    public JAXBElement<LookupType> createLookup(LookupType value) {
        return new JAXBElement<LookupType>(_Lookup_QNAME, LookupType.class, null, value);
    }

}
