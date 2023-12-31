/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: RATEREQUEST

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
 * RATEREQUEST class.
 */

public class RATEREQUEST

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "DEFATTRIBUTES" node name.
     */

    public static final String $DEFATTRIBUTES = "DEFATTRIBUTES";


    /**
     * Constant for "PACKAGES" node name.
     */

    public static final String $PACKAGES = "PACKAGES";


    /**
     * Constant for "LOGIN" node name.
     */

    public static final String $LOGIN = "LOGIN";


    /**
     * Constant for "RATEREQUEST" node name.
     */

    public static final String $RATEREQUEST = "RATEREQUEST";


    /**
     * Constant for "PKGSERVICE" node name.
     */

    public static final String $PKGSERVICE = "PKGSERVICE";


    /**
     * Constant for "SORT" node name.
     */

    public static final String $SORT = "SORT";


    protected LOGIN _LOGIN = null;

    protected DEFATTRIBUTES _DEFATTRIBUTES = null;

    protected PACKAGES _PACKAGES = null;

    protected PKGSERVICE _PKGSERVICE = null;

    protected String _SORT = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public RATEREQUEST() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public RATEREQUEST(XmlElement xml) {

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
     * Get the DEFATTRIBUTES property.
     */

    public DEFATTRIBUTES getDEFATTRIBUTES() {

        return (_DEFATTRIBUTES);

    }


    /**
     * Set the DEFATTRIBUTES property.
     */

    public void setDEFATTRIBUTES(DEFATTRIBUTES obj) {

        _DEFATTRIBUTES = obj;

    }


    protected void setDEFATTRIBUTES(XmlElement xml) {


        _DEFATTRIBUTES =

                new DEFATTRIBUTES(xml);

    }


    /**
     * Checks for whether DEFATTRIBUTES is set or not.
     *
     * @returns true if DEFATTRIBUTES is set, false if not
     */

    public boolean hasDEFATTRIBUTES() {

        return (_DEFATTRIBUTES != null);

    }


    /**
     * Discards DEFATTRIBUTES's value.
     */

    public void deleteDEFATTRIBUTES() {

        _DEFATTRIBUTES = null;

    }


    /**
     * Get the PACKAGES property.
     */

    public PACKAGES getPACKAGES() {

        return (_PACKAGES);

    }


    /**
     * Set the PACKAGES property.
     */

    public void setPACKAGES(PACKAGES obj) {

        _PACKAGES = obj;

    }


    protected void setPACKAGES(XmlElement xml) {


        _PACKAGES =

                new PACKAGES(xml);

    }


    /**
     * Checks for whether PACKAGES is set or not.
     *
     * @returns true if PACKAGES is set, false if not
     */

    public boolean hasPACKAGES() {

        return (_PACKAGES != null);

    }


    /**
     * Discards PACKAGES's value.
     */

    public void deletePACKAGES() {

        _PACKAGES = null;

    }


    /**
     * Get the PKGSERVICE property.
     */

    public PKGSERVICE getPKGSERVICE() {

        return (_PKGSERVICE);

    }


    /**
     * Set the PKGSERVICE property.
     */

    public void setPKGSERVICE(PKGSERVICE obj) {

        _PKGSERVICE = obj;

    }


    protected void setPKGSERVICE(XmlElement xml) {


        _PKGSERVICE =

                new PKGSERVICE(xml);

    }


    /**
     * Checks for whether PKGSERVICE is set or not.
     *
     * @returns true if PKGSERVICE is set, false if not
     */

    public boolean hasPKGSERVICE() {

        return (_PKGSERVICE != null);

    }


    /**
     * Discards PKGSERVICE's value.
     */

    public void deletePKGSERVICE() {

        _PKGSERVICE = null;

    }


    /**
     * Get the SORT property.
     */

    public String getSORT() {

        return (_SORT);

    }


    /**
     * Set the SORT property.
     */

    public void setSORT(String newValue) {

        _SORT = newValue;

    }


    /**
     * Checks for whether SORT is set or not.
     *
     * @returns true if SORT is set, false if not
     */

    public boolean hasSORT() {

        return (_SORT != null);

    }


    /**
     * Discards SORT's value.
     */

    public void deleteSORT() {

        _SORT = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($RATEREQUEST);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($RATEREQUEST);

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

    transient protected static XmlValidator

            _DEFATTRIBUTES_validator_ = null;

    transient protected static XmlValidator

            _PACKAGES_validator_ = null;

    transient protected static XmlValidator

            _PKGSERVICE_validator_ = null;

    transient protected static XmlStringValidator

            _SORT_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _LOGIN_validator_ = new XmlValidator("RATEREQUEST.LOGIN", "Element",

                "RATEREQUEST/LOGIN", true);


        _DEFATTRIBUTES_validator_ = new XmlValidator("RATEREQUEST.DEFATTRIBUTES", "Element",

                "RATEREQUEST/DEFATTRIBUTES", false);


        _PACKAGES_validator_ = new XmlValidator("RATEREQUEST.PACKAGES", "Element",

                "RATEREQUEST/PACKAGES", true);


        _PKGSERVICE_validator_ = new XmlValidator("RATEREQUEST.PKGSERVICE", "Element",

                "RATEREQUEST/PKGSERVICE", true);


        _SORT_validator_ = new XmlStringValidator("RATEREQUEST.SORT", "Element",

                "RATEREQUEST/SORT", -1, -1, true);


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

        e = _SORT_validator_.validate(_SORT);

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


        is_valid = _DEFATTRIBUTES_validator_.isValid(_DEFATTRIBUTES,

                errors, return_on_error, traverse);

        if (!is_valid && return_on_error) {

            return (errors);

        }


        is_valid = _PACKAGES_validator_.isValid(_PACKAGES,

                errors, return_on_error, traverse);

        if (!is_valid && return_on_error) {

            return (errors);

        }


        is_valid = _PKGSERVICE_validator_.isValid(_PKGSERVICE,

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


        return (xml.equals($RATEREQUEST));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static RATEREQUEST unmarshal(InputStream in) throws Exception {


        RATEREQUEST obj = new RATEREQUEST();

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


        if (!xml.equals($RATEREQUEST)) {

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


        if (xml.equals($DEFATTRIBUTES)) {

            setDEFATTRIBUTES(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($PACKAGES)) {

            setPACKAGES(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($PKGSERVICE)) {

            setPKGSERVICE(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($SORT)) {

            setSORT(xml.getData());

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


        Object DEFATTRIBUTES_value = getDEFATTRIBUTES();

        if (DEFATTRIBUTES_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) DEFATTRIBUTES_value,

                    embed_files);

        }


        Object PACKAGES_value = getPACKAGES();

        if (PACKAGES_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) PACKAGES_value,

                    embed_files);

        }


        Object PKGSERVICE_value = getPKGSERVICE();

        if (PKGSERVICE_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) PKGSERVICE_value,

                    embed_files);

        }

        out.write($SORT,

                _SORT);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2ea:b7qdht");

    }

}
