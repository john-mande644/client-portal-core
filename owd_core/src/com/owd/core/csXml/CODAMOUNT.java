/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: CODAMOUNT

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
 * CODAMOUNT class.
 */

public class CODAMOUNT

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "CURRENCYCODE" node name.
     */

    public static final String $CURRENCYCODE = "CURRENCYCODE";


    /**
     * Constant for "MONETARYVALUE" node name.
     */

    public static final String $MONETARYVALUE = "MONETARYVALUE";


    /**
     * Constant for "CODAMOUNT" node name.
     */

    public static final String $CODAMOUNT = "CODAMOUNT";


    protected String _CURRENCYCODE = null;

    protected String _MONETARYVALUE = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public CODAMOUNT() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public CODAMOUNT(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the CURRENCYCODE property.
     */

    public String getCURRENCYCODE() {

        return (_CURRENCYCODE);

    }


    /**
     * Set the CURRENCYCODE property.
     */

    public void setCURRENCYCODE(String newValue) {

        _CURRENCYCODE = newValue;

    }


    /**
     * Checks for whether CURRENCYCODE is set or not.
     *
     * @returns true if CURRENCYCODE is set, false if not
     */

    public boolean hasCURRENCYCODE() {

        return (_CURRENCYCODE != null);

    }


    /**
     * Discards CURRENCYCODE's value.
     */

    public void deleteCURRENCYCODE() {

        _CURRENCYCODE = null;

    }


    /**
     * Get the MONETARYVALUE property.
     */

    public String getMONETARYVALUE() {

        return (_MONETARYVALUE);

    }


    /**
     * Set the MONETARYVALUE property.
     */

    public void setMONETARYVALUE(String newValue) {

        _MONETARYVALUE = newValue;

    }


    /**
     * Checks for whether MONETARYVALUE is set or not.
     *
     * @returns true if MONETARYVALUE is set, false if not
     */

    public boolean hasMONETARYVALUE() {

        return (_MONETARYVALUE != null);

    }


    /**
     * Discards MONETARYVALUE's value.
     */

    public void deleteMONETARYVALUE() {

        _MONETARYVALUE = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($CODAMOUNT);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($CODAMOUNT);

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

            _MONETARYVALUE_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _MONETARYVALUE_validator_ = new XmlStringValidator("CODAMOUNT.MONETARYVALUE", "Element",

                "CODAMOUNT/MONETARYVALUE", -1, -1, true);


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

        e = _MONETARYVALUE_validator_.validate(_MONETARYVALUE);

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


        return (xml.equals($CODAMOUNT));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static CODAMOUNT unmarshal(InputStream in) throws Exception {


        CODAMOUNT obj = new CODAMOUNT();

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


        if (!xml.equals($CODAMOUNT)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($CURRENCYCODE)) {

            setCURRENCYCODE(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($MONETARYVALUE)) {

            setMONETARYVALUE(xml.getData());

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


        out.write($CURRENCYCODE,

                _CURRENCYCODE);

        out.write($MONETARYVALUE,

                _MONETARYVALUE);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2h2:11aah4v");

    }

}
