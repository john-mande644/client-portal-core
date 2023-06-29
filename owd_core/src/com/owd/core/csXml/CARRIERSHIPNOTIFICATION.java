/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: CARRIERSHIPNOTIFICATION

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
 * CARRIERSHIPNOTIFICATION class.
 */

public class CARRIERSHIPNOTIFICATION

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "CSNDESCRIPTION" node name.
     */

    public static final String $CSNDESCRIPTION = "CSNDESCRIPTION";


    /**
     * Constant for "CSNTYPE" node name.
     */

    public static final String $CSNTYPE = "CSNTYPE";


    /**
     * Constant for "CARRIERSHIPNOTIFICATION" node name.
     */

    public static final String $CARRIERSHIPNOTIFICATION = "CARRIERSHIPNOTIFICATION";


    /**
     * Constant for "CSNNUMBER_OR_EMAIL" node name.
     */

    public static final String $CSNNUMBER_OR_EMAIL = "CSNNUMBER_OR_EMAIL";


    /**
     * Constant for "CSNADDRESS" node name.
     */

    public static final String $CSNADDRESS = "CSNADDRESS";


    protected String _CSNTYPE = null;

    protected String _CSNNUMBEROREMAIL = null;

    protected CSNADDRESS _CSNADDRESS = null;

    protected String _CSNDESCRIPTION = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public CARRIERSHIPNOTIFICATION() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public CARRIERSHIPNOTIFICATION(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the CSNTYPE property.
     */

    public String getCSNTYPE() {

        return (_CSNTYPE);

    }


    /**
     * Set the CSNTYPE property.
     */

    public void setCSNTYPE(String newValue) {

        _CSNTYPE = newValue;

    }


    /**
     * Checks for whether CSNTYPE is set or not.
     *
     * @returns true if CSNTYPE is set, false if not
     */

    public boolean hasCSNTYPE() {

        return (_CSNTYPE != null);

    }


    /**
     * Discards CSNTYPE's value.
     */

    public void deleteCSNTYPE() {

        _CSNTYPE = null;

    }


    /**
     * Get the CSNNUMBEROREMAIL property.
     */

    public String getCSNNUMBEROREMAIL() {

        return (_CSNNUMBEROREMAIL);

    }


    /**
     * Set the CSNNUMBEROREMAIL property.
     */

    public void setCSNNUMBEROREMAIL(String newValue) {

        _CSNNUMBEROREMAIL = newValue;

    }


    /**
     * Checks for whether CSNNUMBEROREMAIL is set or not.
     *
     * @returns true if CSNNUMBEROREMAIL is set, false if not
     */

    public boolean hasCSNNUMBEROREMAIL() {

        return (_CSNNUMBEROREMAIL != null);

    }


    /**
     * Discards CSNNUMBEROREMAIL's value.
     */

    public void deleteCSNNUMBEROREMAIL() {

        _CSNNUMBEROREMAIL = null;

    }


    /**
     * Get the CSNADDRESS property.
     */

    public CSNADDRESS getCSNADDRESS() {

        return (_CSNADDRESS);

    }


    /**
     * Set the CSNADDRESS property.
     */

    public void setCSNADDRESS(CSNADDRESS obj) {

        _CSNADDRESS = obj;

    }


    protected void setCSNADDRESS(XmlElement xml) {


        _CSNADDRESS =

                new CSNADDRESS(xml);

    }


    /**
     * Checks for whether CSNADDRESS is set or not.
     *
     * @returns true if CSNADDRESS is set, false if not
     */

    public boolean hasCSNADDRESS() {

        return (_CSNADDRESS != null);

    }


    /**
     * Discards CSNADDRESS's value.
     */

    public void deleteCSNADDRESS() {

        _CSNADDRESS = null;

    }


    /**
     * Get the CSNDESCRIPTION property.
     */

    public String getCSNDESCRIPTION() {

        return (_CSNDESCRIPTION);

    }


    /**
     * Set the CSNDESCRIPTION property.
     */

    public void setCSNDESCRIPTION(String newValue) {

        _CSNDESCRIPTION = newValue;

    }


    /**
     * Checks for whether CSNDESCRIPTION is set or not.
     *
     * @returns true if CSNDESCRIPTION is set, false if not
     */

    public boolean hasCSNDESCRIPTION() {

        return (_CSNDESCRIPTION != null);

    }


    /**
     * Discards CSNDESCRIPTION's value.
     */

    public void deleteCSNDESCRIPTION() {

        _CSNDESCRIPTION = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($CARRIERSHIPNOTIFICATION);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($CARRIERSHIPNOTIFICATION);

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

    transient protected static XmlStringValidator

            _CSNTYPE_validator_ = null;

    transient protected static XmlStringValidator

            _CSNNUMBEROREMAIL_validator_ = null;

    transient protected static XmlValidator

            _CSNADDRESS_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _CSNTYPE_validator_ = new XmlStringValidator("CARRIERSHIPNOTIFICATION.CSNTYPE", "Element",

                "CARRIERSHIPNOTIFICATION/CSNTYPE", -1, -1, true);


        _CSNNUMBEROREMAIL_validator_ = new XmlStringValidator("CARRIERSHIPNOTIFICATION.CSNNUMBEROREMAIL", "Element",

                "CARRIERSHIPNOTIFICATION/CSNNUMBER_OR_EMAIL", -1, -1, true);


        _CSNADDRESS_validator_ = new XmlValidator("CARRIERSHIPNOTIFICATION.CSNADDRESS", "Element",

                "CARRIERSHIPNOTIFICATION/CSNADDRESS", false);


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

        e = _CSNTYPE_validator_.validate(_CSNTYPE);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

        }


        e = _CSNNUMBEROREMAIL_validator_.validate(_CSNNUMBEROREMAIL);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

        }


        if (!traverse) {

            if (errors.size() < 1) {

                return (null);

            }


            return (errors);

        }


        boolean is_valid;


        is_valid = _CSNADDRESS_validator_.isValid(_CSNADDRESS,

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


        return (xml.equals($CARRIERSHIPNOTIFICATION));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static CARRIERSHIPNOTIFICATION unmarshal(InputStream in) throws Exception {


        CARRIERSHIPNOTIFICATION obj = new CARRIERSHIPNOTIFICATION();

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


        if (!xml.equals($CARRIERSHIPNOTIFICATION)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($CSNTYPE)) {

            setCSNTYPE(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($CSNNUMBER_OR_EMAIL)) {

            setCSNNUMBEROREMAIL(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($CSNADDRESS)) {

            setCSNADDRESS(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($CSNDESCRIPTION)) {

            setCSNDESCRIPTION(xml.getData());

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


        out.write($CSNTYPE,

                _CSNTYPE);

        out.write($CSNNUMBER_OR_EMAIL,

                _CSNNUMBEROREMAIL);


        Object CSNADDRESS_value = getCSNADDRESS();

        if (CSNADDRESS_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) CSNADDRESS_value,

                    embed_files);

        }

        out.write($CSNDESCRIPTION,

                _CSNDESCRIPTION);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2hm:1gj2mbq");

    }

}