/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: REFERENCE_1

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
 * REFERENCE_1 class.
 */

public class REFERENCE_1

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "REFERENCE" node name.
     */

    public static final String $REFERENCE = "REFERENCE";


    /**
     * Constant for "CONSIGNEEREFERENCE" node name.
     */

    public static final String $CONSIGNEEREFERENCE = "CONSIGNEEREFERENCE";


    /**
     * Constant for "SHIPPERREFERENCE" node name.
     */

    public static final String $SHIPPERREFERENCE = "SHIPPERREFERENCE";


    protected String _SHIPPERREFERENCE = null;

    protected String _CONSIGNEEREFERENCE = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public REFERENCE_1() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public REFERENCE_1(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the SHIPPERREFERENCE property.
     */

    public String getSHIPPERREFERENCE() {

        return (_SHIPPERREFERENCE);

    }


    /**
     * Set the SHIPPERREFERENCE property.
     */

    public void setSHIPPERREFERENCE(String newValue) {

        _SHIPPERREFERENCE = newValue;

    }


    /**
     * Checks for whether SHIPPERREFERENCE is set or not.
     *
     * @returns true if SHIPPERREFERENCE is set, false if not
     */

    public boolean hasSHIPPERREFERENCE() {

        return (_SHIPPERREFERENCE != null);

    }


    /**
     * Discards SHIPPERREFERENCE's value.
     */

    public void deleteSHIPPERREFERENCE() {

        _SHIPPERREFERENCE = null;

    }


    /**
     * Get the CONSIGNEEREFERENCE property.
     */

    public String getCONSIGNEEREFERENCE() {

        return (_CONSIGNEEREFERENCE);

    }


    /**
     * Set the CONSIGNEEREFERENCE property.
     */

    public void setCONSIGNEEREFERENCE(String newValue) {

        _CONSIGNEEREFERENCE = newValue;

    }


    /**
     * Checks for whether CONSIGNEEREFERENCE is set or not.
     *
     * @returns true if CONSIGNEEREFERENCE is set, false if not
     */

    public boolean hasCONSIGNEEREFERENCE() {

        return (_CONSIGNEEREFERENCE != null);

    }


    /**
     * Discards CONSIGNEEREFERENCE's value.
     */

    public void deleteCONSIGNEEREFERENCE() {

        _CONSIGNEEREFERENCE = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($REFERENCE);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($REFERENCE);

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

            _SHIPPERREFERENCE_validator_ = null;

    transient protected static XmlStringValidator

            _CONSIGNEEREFERENCE_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _SHIPPERREFERENCE_validator_ = new XmlStringValidator("REFERENCE_1.SHIPPERREFERENCE", "Element",

                "REFERENCE/SHIPPERREFERENCE", -1, -1, true);


        _CONSIGNEEREFERENCE_validator_ = new XmlStringValidator("REFERENCE_1.CONSIGNEEREFERENCE", "Element",

                "REFERENCE/CONSIGNEEREFERENCE", -1, -1, true);


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

        e = _SHIPPERREFERENCE_validator_.validate(_SHIPPERREFERENCE);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

        }


        e = _CONSIGNEEREFERENCE_validator_.validate(_CONSIGNEEREFERENCE);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

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


        return (xml.equals($REFERENCE));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static REFERENCE_1 unmarshal(InputStream in) throws Exception {


        REFERENCE_1 obj = new REFERENCE_1();

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


        if (!xml.equals($REFERENCE)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($SHIPPERREFERENCE)) {

            setSHIPPERREFERENCE(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($CONSIGNEEREFERENCE)) {

            setCONSIGNEEREFERENCE(xml.getData());

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


        out.write($SHIPPERREFERENCE,

                _SHIPPERREFERENCE);

        out.write($CONSIGNEEREFERENCE,

                _CONSIGNEEREFERENCE);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2dq:12v3lff");

    }

}
