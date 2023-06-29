/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: HTMLSTATUS

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
 * HTMLSTATUS class.
 */

public class HTMLSTATUS

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "ERRORDESCRIPTION" node name.
     */

    public static final String $ERRORDESCRIPTION = "ERRORDESCRIPTION";


    /**
     * Constant for "ERRORCODE" node name.
     */

    public static final String $ERRORCODE = "ERRORCODE";


    /**
     * Constant for "HTMLSTATUS" node name.
     */

    public static final String $HTMLSTATUS = "HTMLSTATUS";


    protected String _ERRORCODE = null;

    protected String _ERRORDESCRIPTION = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public HTMLSTATUS() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public HTMLSTATUS(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the ERRORCODE property.
     */

    public String getERRORCODE() {

        return (_ERRORCODE);

    }


    /**
     * Set the ERRORCODE property.
     */

    public void setERRORCODE(String newValue) {

        _ERRORCODE = newValue;

    }


    /**
     * Checks for whether ERRORCODE is set or not.
     *
     * @returns true if ERRORCODE is set, false if not
     */

    public boolean hasERRORCODE() {

        return (_ERRORCODE != null);

    }


    /**
     * Discards ERRORCODE's value.
     */

    public void deleteERRORCODE() {

        _ERRORCODE = null;

    }


    /**
     * Get the ERRORDESCRIPTION property.
     */

    public String getERRORDESCRIPTION() {

        return (_ERRORDESCRIPTION);

    }


    /**
     * Set the ERRORDESCRIPTION property.
     */

    public void setERRORDESCRIPTION(String newValue) {

        _ERRORDESCRIPTION = newValue;

    }


    /**
     * Checks for whether ERRORDESCRIPTION is set or not.
     *
     * @returns true if ERRORDESCRIPTION is set, false if not
     */

    public boolean hasERRORDESCRIPTION() {

        return (_ERRORDESCRIPTION != null);

    }


    /**
     * Discards ERRORDESCRIPTION's value.
     */

    public void deleteERRORDESCRIPTION() {

        _ERRORDESCRIPTION = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($HTMLSTATUS);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($HTMLSTATUS);

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

            _ERRORCODE_validator_ = null;

    transient protected static XmlStringValidator

            _ERRORDESCRIPTION_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _ERRORCODE_validator_ = new XmlStringValidator("HTMLSTATUS.ERRORCODE", "Element",

                "HTMLSTATUS/ERRORCODE", -1, -1, true);


        _ERRORDESCRIPTION_validator_ = new XmlStringValidator("HTMLSTATUS.ERRORDESCRIPTION", "Element",

                "HTMLSTATUS/ERRORDESCRIPTION", -1, -1, true);


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

        e = _ERRORCODE_validator_.validate(_ERRORCODE);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

        }


        e = _ERRORDESCRIPTION_validator_.validate(_ERRORDESCRIPTION);

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


        return (xml.equals($HTMLSTATUS));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static HTMLSTATUS unmarshal(InputStream in) throws Exception {


        HTMLSTATUS obj = new HTMLSTATUS();

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


        if (!xml.equals($HTMLSTATUS)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($ERRORCODE)) {

            setERRORCODE(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($ERRORDESCRIPTION)) {

            setERRORDESCRIPTION(xml.getData());

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


        out.write($ERRORCODE,

                _ERRORCODE);

        out.write($ERRORDESCRIPTION,

                _ERRORDESCRIPTION);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2d6:qc5xwu");

    }

}
