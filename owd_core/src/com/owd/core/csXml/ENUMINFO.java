/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: ENUMINFO

 *           Date: Fri Feb 22 21:58:50 PST 2002

 * Breeze Version: 2.2.1 build 114

 *

 * IMPORTANT: Please see your Breeze license for more information on

 *            where and how this generated code may be used.

 *

 */


package com.owd.core.csXml;


import com.tbf.xml.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;


/**
 * ENUMINFO class.
 */

public class ENUMINFO

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "ENUMINFO" node name.
     */

    public static final String $ENUMINFO = "ENUMINFO";


    /**
     * Constant for "SHIPDATE" node name.
     */

    public static final String $SHIPDATE = "SHIPDATE";


    /**
     * Constant for "SHIPPER" node name.
     */

    public static final String $SHIPPER = "SHIPPER";


    /**
     * Constant for "SC" node name.
     */

    public static final String $SC = "SC";


    protected String _SC = null;

    protected String _SHIPPER = null;

    protected String _SHIPDATE = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public ENUMINFO() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public ENUMINFO(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the SC property.
     */

    public String getSC() {

        return (_SC);

    }


    /**
     * Set the SC property.
     */

    public void setSC(String newValue) {

        _SC = newValue;

    }


    /**
     * Checks for whether SC is set or not.
     *
     * @returns true if SC is set, false if not
     */

    public boolean hasSC() {

        return (_SC != null);

    }


    /**
     * Discards SC's value.
     */

    public void deleteSC() {

        _SC = null;

    }


    /**
     * Get the SHIPPER property.
     */

    public String getSHIPPER() {

        return (_SHIPPER);

    }


    /**
     * Set the SHIPPER property.
     */

    public void setSHIPPER(String newValue) {

        _SHIPPER = newValue;

    }


    /**
     * Checks for whether SHIPPER is set or not.
     *
     * @returns true if SHIPPER is set, false if not
     */

    public boolean hasSHIPPER() {

        return (_SHIPPER != null);

    }


    /**
     * Discards SHIPPER's value.
     */

    public void deleteSHIPPER() {

        _SHIPPER = null;

    }


    /**
     * Get the SHIPDATE property.
     */

    public String getSHIPDATE() {

        return (_SHIPDATE);

    }


    /**
     * Set the SHIPDATE property.
     */

    public void setSHIPDATE(String newValue) {

        _SHIPDATE = newValue;

    }


    /**
     * Checks for whether SHIPDATE is set or not.
     *
     * @returns true if SHIPDATE is set, false if not
     */

    public boolean hasSHIPDATE() {

        return (_SHIPDATE != null);

    }


    /**
     * Discards SHIPDATE's value.
     */

    public void deleteSHIPDATE() {

        _SHIPDATE = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($ENUMINFO);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($ENUMINFO);

    }


    /**
     * Checks this object to see if it will produce valid XML.
     */

    public boolean isValid() {


        if (!(this instanceof Validateable)) {

            return (true);

        }


        Vector errors = ((Validateable) this).getValidationErrors(true);

        if (errors == null || errors.size() < 1) {

            return (true);

        }


        return (false);

    }


    /**
     * Checks each field on the object for validity and
     * <p/>
     * returns a Vector holding the validation errors.
     */

    public Vector getValidationErrors() {

        return (getValidationErrors(false));

    }


    /**
     * Checks each field on the object for validity and
     * <p/>
     * returns a Vector holding the validation errors.
     *
     * @return a Vector containing the validation errors
     * @see com.tbf.xml.XmlValidationError
     */

    public Vector getValidationErrors(boolean return_on_error) {

        return (getValidationErrors(return_on_error, true));

    }


    /**
     * Checks each field on the object for validity and
     * <p/>
     * returns a Vector holding the validation errors.
     *
     * @return a Vector containing the validation errors
     * @see com.tbf.xml.XmlValidationError
     */

    public Vector getValidationErrors(boolean return_on_error, boolean traverse) {

        if (_unexpected_xml_errors_ != null &&

                _unexpected_xml_errors_.size() > 0) {

            Vector errors = (Vector) _unexpected_xml_errors_.clone();

            return (errors);

        }


        return (null);

    }


    /**
     * Checks the XML to see whether it matches the
     * <p/>
     * XML contents of this class.
     */

    public static boolean matches(XmlElement xml) {


        if (xml == null) {

            return (false);

        }


        return (xml.equals($ENUMINFO));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static ENUMINFO unmarshal(InputStream in) throws Exception {


        ENUMINFO obj = new ENUMINFO();

        ObjectFactory.unmarshal(obj, in);

        return (obj);

    }


    /**
     * Populates this object with the values from the
     * <p/>
     * parsed XML.
     */

    public void fromXml(XmlElement xml) {


        if (xml == null) {

            return;

        }


        if (!xml.equals($ENUMINFO)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($SC)) {

            setSC(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($SHIPPER)) {

            setSHIPPER(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($SHIPDATE)) {

            setSHIPDATE(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml != null) {


            _unexpected_xml_errors_ =

                    XmlValidationError.addUnexpectedXmlError(this, _unexpected_xml_errors_, xml);

            xml = xml.next();

        }

    }


    /**
     * This method marshals this object into an
     * <p/>
     * XML instance document.
     */

    public void marshal(OutputStream out) {

        toXml(out);

    }


    /**
     * Serializes this object to Formatted XML.
     */

    public void toXml(OutputStream stream) {

        toXml(stream, true);

    }


    /**
     * Serializes this object to Formatted XML.
     */

    public void toXml(OutputStream stream, String indent, boolean embed_files) {


        if (stream instanceof XmlOutputStream) {

            toXml(stream, embed_files);

        } else {

            FormattedOutputStream out =

                    new FormattedOutputStream(stream);

            out.setIndentString(indent);

            toXml(out, embed_files);

        }

    }


    /**
     * Serializes this object to XML.
     */

    public void toXml(OutputStream stream, boolean embed_files) {


        XmlOutputStream out = null;

        if (stream instanceof XmlOutputStream) {

            out = (XmlOutputStream) stream;

        } else {

            out = new RawOutputStream(stream);

        }


        out.writeStartTag(getXmlTagName(), false);

        out.incrementIndent();


        out.write($SC,

                _SC);

        out.write($SHIPPER,

                _SHIPPER);

        out.write($SHIPDATE,

                _SHIPDATE);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q249:1i09v4u");

    }

}