/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: DOCUMENTS

 *           Date: Fri Feb 22 21:58:51 PST 2002

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
 * DOCUMENTS class.
 */

public class DOCUMENTS

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "ERRORSTATUS" node name.
     */

    public static final String $ERRORSTATUS = "ERRORSTATUS";


    /**
     * Constant for "DOCUMENTS" node name.
     */

    public static final String $DOCUMENTS = "DOCUMENTS";


    /**
     * Constant for "DOCUMENTURL" node name.
     */

    public static final String $DOCUMENTURL = "DOCUMENTURL";


    /**
     * Constant for "DOCFORMAT" node name.
     */

    public static final String $DOCFORMAT = "DOCFORMAT";


    protected String _DOCFORMAT = null;

    protected ERRORSTATUS_1 _ERRORSTATUS = null;

    protected String _DOCUMENTURL = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public DOCUMENTS() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public DOCUMENTS(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the DOCFORMAT property.
     */

    public String getDOCFORMAT() {

        return (_DOCFORMAT);

    }


    /**
     * Set the DOCFORMAT property.
     */

    public void setDOCFORMAT(String newValue) {

        _DOCFORMAT = newValue;

    }


    /**
     * Checks for whether DOCFORMAT is set or not.
     *
     * @returns true if DOCFORMAT is set, false if not
     */

    public boolean hasDOCFORMAT() {

        return (_DOCFORMAT != null);

    }


    /**
     * Discards DOCFORMAT's value.
     */

    public void deleteDOCFORMAT() {

        _DOCFORMAT = null;

    }


    /**
     * Get the ERRORSTATUS property.
     */

    public ERRORSTATUS_1 getERRORSTATUS() {

        return (_ERRORSTATUS);

    }


    /**
     * Set the ERRORSTATUS property.
     */

    public void setERRORSTATUS(ERRORSTATUS_1 obj) {

        _ERRORSTATUS = obj;

    }


    protected void setERRORSTATUS(XmlElement xml) {


        _ERRORSTATUS =

                new ERRORSTATUS_1(xml);

    }


    /**
     * Checks for whether ERRORSTATUS is set or not.
     *
     * @returns true if ERRORSTATUS is set, false if not
     */

    public boolean hasERRORSTATUS() {

        return (_ERRORSTATUS != null);

    }


    /**
     * Discards ERRORSTATUS's value.
     */

    public void deleteERRORSTATUS() {

        _ERRORSTATUS = null;

    }


    /**
     * Get the DOCUMENTURL property.
     */

    public String getDOCUMENTURL() {

        return (_DOCUMENTURL);

    }


    /**
     * Set the DOCUMENTURL property.
     */

    public void setDOCUMENTURL(String newValue) {

        _DOCUMENTURL = newValue;

    }


    /**
     * Checks for whether DOCUMENTURL is set or not.
     *
     * @returns true if DOCUMENTURL is set, false if not
     */

    public boolean hasDOCUMENTURL() {

        return (_DOCUMENTURL != null);

    }


    /**
     * Discards DOCUMENTURL's value.
     */

    public void deleteDOCUMENTURL() {

        _DOCUMENTURL = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($DOCUMENTS);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($DOCUMENTS);

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

            _DOCFORMAT_validator_ = null;

    transient protected static XmlValidator

            _ERRORSTATUS_validator_ = null;

    transient protected static XmlStringValidator

            _DOCUMENTURL_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _DOCFORMAT_validator_ = new XmlStringValidator("DOCUMENTS.DOCFORMAT", "Element",

                "DOCUMENTS/DOCFORMAT", -1, -1, true);


        _ERRORSTATUS_validator_ = new XmlValidator("DOCUMENTS.ERRORSTATUS", "Element",

                "DOCUMENTS/ERRORSTATUS", true);


        _DOCUMENTURL_validator_ = new XmlStringValidator("DOCUMENTS.DOCUMENTURL", "Element",

                "DOCUMENTS/DOCUMENTURL", -1, -1, true);


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

        e = _DOCFORMAT_validator_.validate(_DOCFORMAT);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

        }


        e = _DOCUMENTURL_validator_.validate(_DOCUMENTURL);

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


        is_valid = _ERRORSTATUS_validator_.isValid(_ERRORSTATUS,

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


        return (xml.equals($DOCUMENTS));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static DOCUMENTS unmarshal(InputStream in) throws Exception {


        DOCUMENTS obj = new DOCUMENTS();

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


        if (!xml.equals($DOCUMENTS)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($DOCFORMAT)) {

            setDOCFORMAT(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($ERRORSTATUS)) {

            setERRORSTATUS(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($DOCUMENTURL)) {

            setDOCUMENTURL(xml.getData());

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


        out.write($DOCFORMAT,

                _DOCFORMAT);


        Object ERRORSTATUS_value = getERRORSTATUS();

        if (ERRORSTATUS_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) ERRORSTATUS_value,

                    embed_files);

        }

        out.write($DOCUMENTURL,

                _DOCUMENTURL);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q37r:1bjga5r");

    }

}
