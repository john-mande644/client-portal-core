/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: VOIDREQUEST

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
 * VOIDREQUEST class.
 */

public class VOIDREQUEST

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "LOGIN" node name.
     */

    public static final String $LOGIN = "LOGIN";


    /**
     * Constant for "VOIDREQUEST" node name.
     */

    public static final String $VOIDREQUEST = "VOIDREQUEST";


    /**
     * Constant for "SHIPPER" node name.
     */

    public static final String $SHIPPER = "SHIPPER";


    /**
     * Constant for "MSNLIST" node name.
     */

    public static final String $MSNLIST = "MSNLIST";


    /**
     * Constant for "SC" node name.
     */

    public static final String $SC = "SC";


    protected LOGIN _LOGIN = null;

    protected String _SC = null;

    protected String _SHIPPER = null;

    protected MSNLIST _MSNLIST = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public VOIDREQUEST() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public VOIDREQUEST(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the LOGIN property.
     */

    public LOGIN getLOGIN() {

        return (_LOGIN);

    }


    /**
     * Set the LOGIN property.
     */

    public void setLOGIN(LOGIN obj) {

        _LOGIN = obj;

    }


    protected void setLOGIN(XmlElement xml) {


        _LOGIN =

                new LOGIN(xml);

    }


    /**
     * Checks for whether LOGIN is set or not.
     *
     * @returns true if LOGIN is set, false if not
     */

    public boolean hasLOGIN() {

        return (_LOGIN != null);

    }


    /**
     * Discards LOGIN's value.
     */

    public void deleteLOGIN() {

        _LOGIN = null;

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
     * Get the MSNLIST property.
     */

    public MSNLIST getMSNLIST() {

        return (_MSNLIST);

    }


    /**
     * Set the MSNLIST property.
     */

    public void setMSNLIST(MSNLIST obj) {

        _MSNLIST = obj;

    }


    protected void setMSNLIST(XmlElement xml) {


        _MSNLIST =

                new MSNLIST(xml);

    }


    /**
     * Checks for whether MSNLIST is set or not.
     *
     * @returns true if MSNLIST is set, false if not
     */

    public boolean hasMSNLIST() {

        return (_MSNLIST != null);

    }


    /**
     * Discards MSNLIST's value.
     */

    public void deleteMSNLIST() {

        _MSNLIST = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($VOIDREQUEST);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($VOIDREQUEST);

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

            _LOGIN_validator_ = null;

    transient protected static XmlStringValidator

            _SC_validator_ = null;

    transient protected static XmlStringValidator

            _SHIPPER_validator_ = null;

    transient protected static XmlValidator

            _MSNLIST_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _LOGIN_validator_ = new XmlValidator("VOIDREQUEST.LOGIN", "Element",

                "VOIDREQUEST/LOGIN", true);


        _SC_validator_ = new XmlStringValidator("VOIDREQUEST.SC", "Element",

                "VOIDREQUEST/SC", -1, -1, true);


        _SHIPPER_validator_ = new XmlStringValidator("VOIDREQUEST.SHIPPER", "Element",

                "VOIDREQUEST/SHIPPER", -1, -1, true);


        _MSNLIST_validator_ = new XmlValidator("VOIDREQUEST.MSNLIST", "Element",

                "VOIDREQUEST/MSNLIST", true);


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

        e = _SC_validator_.validate(_SC);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

        }


        e = _SHIPPER_validator_.validate(_SHIPPER);

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


        is_valid = _LOGIN_validator_.isValid(_LOGIN,

                errors, return_on_error, traverse);

        if (!is_valid && return_on_error) {

            return (errors);

        }


        is_valid = _MSNLIST_validator_.isValid(_MSNLIST,

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


        return (xml.equals($VOIDREQUEST));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static VOIDREQUEST unmarshal(InputStream in) throws Exception {


        VOIDREQUEST obj = new VOIDREQUEST();

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


        if (!xml.equals($VOIDREQUEST)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($LOGIN)) {

            setLOGIN(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

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


        if (xml.equals($MSNLIST)) {

            setMSNLIST(xml);

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


        Object LOGIN_value = getLOGIN();

        if (LOGIN_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) LOGIN_value,

                    embed_files);

        }

        out.write($SC,

                _SC);

        out.write($SHIPPER,

                _SHIPPER);


        Object MSNLIST_value = getMSNLIST();

        if (MSNLIST_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) MSNLIST_value,

                    embed_files);

        }


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q38v:1wkgu0k");

    }

}
