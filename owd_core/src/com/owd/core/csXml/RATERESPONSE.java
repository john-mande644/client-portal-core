/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: RATERESPONSE

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
 * RATERESPONSE class.
 */

public class RATERESPONSE

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "RESPONSE" node name.
     */

    public static final String $RESPONSE = "RESPONSE";


    /**
     * Constant for "RATERESPONSE" node name.
     */

    public static final String $RATERESPONSE = "RATERESPONSE";


    /**
     * Constant for "RATEDSHIPMENT" node name.
     */

    public static final String $RATEDSHIPMENT = "RATEDSHIPMENT";


    protected RESPONSE _RESPONSE = null;

    protected java.util.Vector _RATEDSHIPMENT = new java.util.Vector();


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public RATERESPONSE() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public RATERESPONSE(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the RESPONSE property.
     */

    public RESPONSE getRESPONSE() {

        return (_RESPONSE);

    }


    /**
     * Set the RESPONSE property.
     */

    public void setRESPONSE(RESPONSE obj) {

        _RESPONSE = obj;

    }


    protected void setRESPONSE(XmlElement xml) {


        _RESPONSE =

                new RESPONSE(xml);

    }


    /**
     * Checks for whether RESPONSE is set or not.
     *
     * @returns true if RESPONSE is set, false if not
     */

    public boolean hasRESPONSE() {

        return (_RESPONSE != null);

    }


    /**
     * Discards RESPONSE's value.
     */

    public void deleteRESPONSE() {

        _RESPONSE = null;

    }


    /**
     * Get the RATEDSHIPMENT property.
     */

    public java.util.Vector getRATEDSHIPMENT() {

        return (_RATEDSHIPMENT);

    }


    public RATEDSHIPMENT getRATEDSHIPMENTAt(int index)

            throws IndexOutOfBoundsException {

        return ((RATEDSHIPMENT) _RATEDSHIPMENT.elementAt(index));

    }


    /**
     * Get the count of elements in the RATEDSHIPMENT property.
     */

    public int getRATEDSHIPMENTCount() {

        if (_RATEDSHIPMENT == null) {

            return (0);

        }


        return (_RATEDSHIPMENT.size());

    }


    /**
     * Set the RATEDSHIPMENT property.
     */

    public void setRATEDSHIPMENT(java.util.Vector newList) {


        if (newList == null) {

            _RATEDSHIPMENT.removeAllElements();

        } else {

            _RATEDSHIPMENT = (java.util.Vector) newList.clone();

        }

    }


    public void addRATEDSHIPMENT(RATEDSHIPMENT obj) {

        if (obj == null) {

            return;

        }


        _RATEDSHIPMENT.addElement(obj);

    }


    public void setRATEDSHIPMENTAt(RATEDSHIPMENT obj, int index)

            throws IndexOutOfBoundsException {

        if (obj == null) {

            return;

        }


        _RATEDSHIPMENT.setElementAt(obj, index);

    }


    public void removeRATEDSHIPMENT(RATEDSHIPMENT obj) {

        if (obj == null) {

            return;

        }


        _RATEDSHIPMENT.removeElement(obj);

    }


    public void removeRATEDSHIPMENTAt(int index)

            throws IndexOutOfBoundsException {

        _RATEDSHIPMENT.removeElementAt(index);

    }


    protected void setRATEDSHIPMENT(XmlElement xml) {


        _RATEDSHIPMENT.removeAllElements();

        XmlElement saved_xml = xml;


        while (xml != null &&

                xml.equals(RATEDSHIPMENT.$RATEDSHIPMENT)) {

            Object obj = new RATEDSHIPMENT(xml);

            _RATEDSHIPMENT.addElement(obj);

            saved_xml.setLastProcessed(xml);

            xml = xml.next();

        }

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($RATERESPONSE);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($RATERESPONSE);

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

            _RESPONSE_validator_ = null;

    transient protected static XmlValidator

            _RATEDSHIPMENT_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _RESPONSE_validator_ = new XmlValidator("RATERESPONSE.RESPONSE", "Element",

                "RATERESPONSE/RESPONSE", true);


        _RATEDSHIPMENT_validator_ = new XmlValidator("RATERESPONSE.RATEDSHIPMENT", "Element",

                "RATERESPONSE/RATEDSHIPMENT", false);


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


        is_valid = _RESPONSE_validator_.isValid(_RESPONSE,

                errors, return_on_error, traverse);

        if (!is_valid && return_on_error) {

            return (errors);

        }


        is_valid = _RATEDSHIPMENT_validator_.isValid(_RATEDSHIPMENT,

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


        return (xml.equals($RATERESPONSE));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static RATERESPONSE unmarshal(InputStream in) throws Exception {


        RATERESPONSE obj = new RATERESPONSE();

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


        if (!xml.equals($RATERESPONSE)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($RESPONSE)) {

            setRESPONSE(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($RATEDSHIPMENT)) {

            setRATEDSHIPMENT(xml);

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


        Object RESPONSE_value = getRESPONSE();

        if (RESPONSE_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) RESPONSE_value,

                    embed_files);

        }

        out.write(null,

                getRATEDSHIPMENT(), embed_files);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2p4:ht4dnq");

    }

}
