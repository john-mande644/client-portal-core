
package com.owd.dc.packing.sortation.beans;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.owd.dc.packing.sortation.beans package. 
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

    private final static QName _SortationTracking_QNAME = new QName("", "SortationTracking");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.owd.dc.packing.sortation.beans
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SortationTrackingType }
     * 
     */
    public SortationTrackingType createSortationTrackingType() {
        return new SortationTrackingType();
    }

    /**
     * Create an instance of {@link TrackerType }
     * 
     */
    public TrackerType createTrackerType() {
        return new TrackerType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SortationTrackingType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SortationTracking")
    public JAXBElement<SortationTrackingType> createSortationTracking(SortationTrackingType value) {
        return new JAXBElement<SortationTrackingType>(_SortationTracking_QNAME, SortationTrackingType.class, null, value);
    }

}
