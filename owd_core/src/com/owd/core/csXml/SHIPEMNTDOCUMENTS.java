/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: SHIPEMNTDOCUMENTS

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
 * SHIPEMNTDOCUMENTS class.
 */

public class SHIPEMNTDOCUMENTS

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "ERROR" node name.
     */

    public static final String $ERROR = "ERROR";


    /**
     * Constant for "DOCUMENTURL" node name.
     */

    public static final String $DOCUMENTURL = "DOCUMENTURL";


    /**
     * Constant for "REFERENCE" node name.
     */

    public static final String $REFERENCE = "REFERENCE";


    /**
     * Constant for "SHIPEMNTDOCUMENTS" node name.
     */

    public static final String $SHIPEMNTDOCUMENTS = "SHIPEMNTDOCUMENTS";


    protected XmlObject _ERROR = null;

    protected REFERENCE_1 _REFERENCE = null;

    protected java.util.Vector _DOCUMENTURL = new java.util.Vector();


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public SHIPEMNTDOCUMENTS() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public SHIPEMNTDOCUMENTS(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the ERROR property.
     */

    public XmlObject getERROR() {

        return (_ERROR);

    }


    /**
     * Set the ERROR property.
     */

    public void setERROR(XmlObject obj) {

        _ERROR = obj;

    }


    protected void setERROR(XmlElement xml) {


        if (xml.getNumSubElements() == 0) {

            String s = xml.getData();

            if (s != null) {

                _ERROR = new XmlString(s);

            } else {

                _ERROR = null;

            }


            return;

        }


        xml = xml.getSubElementAt(0);

        _ERROR =

                ObjectFactory.createObject(xml);

        if (_ERROR == null) {

            _ERROR = xml.getAnyContent($SHIPEMNTDOCUMENTS);

        }

    }


    /**
     * Checks for whether ERROR is set or not.
     *
     * @returns true if ERROR is set, false if not
     */

    public boolean hasERROR() {

        return (_ERROR != null);

    }


    /**
     * Discards ERROR's value.
     */

    public void deleteERROR() {

        _ERROR = null;

    }


    /**
     * Get the REFERENCE property.
     */

    public REFERENCE_1 getREFERENCE() {

        return (_REFERENCE);

    }


    /**
     * Set the REFERENCE property.
     */

    public void setREFERENCE(REFERENCE_1 obj) {

        _REFERENCE = obj;

    }


    protected void setREFERENCE(XmlElement xml) {


        _REFERENCE =

                new REFERENCE_1(xml);

    }


    /**
     * Checks for whether REFERENCE is set or not.
     *
     * @returns true if REFERENCE is set, false if not
     */

    public boolean hasREFERENCE() {

        return (_REFERENCE != null);

    }


    /**
     * Discards REFERENCE's value.
     */

    public void deleteREFERENCE() {

        _REFERENCE = null;

    }


    /**
     * Get the DOCUMENTURL property.
     */

    public java.util.Vector getDOCUMENTURL() {

        return (_DOCUMENTURL);

    }


    public String getDOCUMENTURLAt(int index)

            throws IndexOutOfBoundsException {

        XmlString obj =

                (XmlString) _DOCUMENTURL.elementAt(index);

        return (obj.getValue());

    }


    /**
     * Get the count of elements in the DOCUMENTURL property.
     */

    public int getDOCUMENTURLCount() {

        if (_DOCUMENTURL == null) {

            return (0);

        }


        return (_DOCUMENTURL.size());

    }


    /**
     * Set the DOCUMENTURL property.
     */

    public void setDOCUMENTURL(java.util.Vector newList) {

        if (newList == null) {

            _DOCUMENTURL.removeAllElements();

        } else {

            _DOCUMENTURL = (java.util.Vector) newList.clone();

        }

    }


    protected void setDOCUMENTURL(XmlElement xml) {


        _DOCUMENTURL.removeAllElements();


        XmlElement saved_xml = xml;


        while (xml != null &&

                xml.equals($DOCUMENTURL)) {


            XmlType obj = new XmlString(xml);

            _DOCUMENTURL.addElement(obj);

            saved_xml.setLastProcessed(xml);

            xml = xml.next();

        }

    }


    public void addDOCUMENTURL(String value) {

        _DOCUMENTURL.addElement(new XmlString($DOCUMENTURL, value));

    }


    public void setDOCUMENTURLAt(String value, int index)

            throws IndexOutOfBoundsException {


        XmlString obj =

                new XmlString($DOCUMENTURL, value);

        _DOCUMENTURL.setElementAt(obj, index);

    }


    public void removeDOCUMENTURLAt(int index)

            throws IndexOutOfBoundsException {


        _DOCUMENTURL.removeElementAt(index);

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($SHIPEMNTDOCUMENTS);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($SHIPEMNTDOCUMENTS);

    }


    /**
     * This flag is used to used to check whether
     * <p/>
     * the validators have been created.
     */

    transient protected static boolean _validators_created = false;



    /*

     * XML Validators

     */

    transient protected static XmlValidator

            _ERROR_validator_ = null;

    transient protected static XmlValidator

            _REFERENCE_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _ERROR_validator_ = new XmlValidator("SHIPEMNTDOCUMENTS.ERROR", "ANY",

                "SHIPEMNTDOCUMENTS/ERROR", true);


        _REFERENCE_validator_ = new XmlValidator("SHIPEMNTDOCUMENTS.REFERENCE", "Element",

                "SHIPEMNTDOCUMENTS/REFERENCE", true);


        _validators_created = true;

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


        createValidators();


        Vector errors;

        if (_unexpected_xml_errors_ != null &&

                _unexpected_xml_errors_.size() > 0) {

            errors = (Vector) _unexpected_xml_errors_.clone();

            if (return_on_error) {

                return (errors);

            }

        } else {

            errors = new Vector();

        }


        XmlValidationError e;


        if (!traverse) {

            if (errors.size() < 1) {

                return (null);

            }


            return (errors);

        }


        boolean is_valid;


        is_valid = _ERROR_validator_.isValid(_ERROR,

                errors, return_on_error, traverse);

        if (!is_valid && return_on_error) {

            return (errors);

        }


        is_valid = _REFERENCE_validator_.isValid(_REFERENCE,

                errors, return_on_error, traverse);

        if (!is_valid && return_on_error) {

            return (errors);

        }


        if (errors.size() < 1) {

            return (null);

        }


        return (errors);

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


        return (xml.equals($SHIPEMNTDOCUMENTS));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static SHIPEMNTDOCUMENTS unmarshal(InputStream in) throws Exception {


        SHIPEMNTDOCUMENTS obj = new SHIPEMNTDOCUMENTS();

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


        if (!xml.equals($SHIPEMNTDOCUMENTS)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($ERROR)) {

            setERROR(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($REFERENCE)) {

            setREFERENCE(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($DOCUMENTURL)) {

            setDOCUMENTURL(xml);

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


        Object ERROR_value = getERROR();

        if (ERROR_value instanceof XmlObject) {

            out.write($ERROR,

                    (XmlObject) ERROR_value,

                    embed_files);

        } else if (ERROR_value != null) {

            out.writeln($ERROR, ERROR_value.toString());

        }


        Object REFERENCE_value = getREFERENCE();

        if (REFERENCE_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) REFERENCE_value,

                    embed_files);

        }

        out.write(null,

                getDOCUMENTURL(), embed_files);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2e0:50sg32");

    }

}
