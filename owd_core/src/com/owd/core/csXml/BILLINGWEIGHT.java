/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: BILLINGWEIGHT

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
 * BILLINGWEIGHT class.
 */

public class BILLINGWEIGHT

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "WEIGHTVALUE" node name.
     */

    public static final String $WEIGHTVALUE = "WEIGHTVALUE";


    /**
     * Constant for "WEIGHTUNITS" node name.
     */

    public static final String $WEIGHTUNITS = "WEIGHTUNITS";


    /**
     * Constant for "BILLINGWEIGHT" node name.
     */

    public static final String $BILLINGWEIGHT = "BILLINGWEIGHT";


    protected String _WEIGHTUNITS = null;

    protected String _WEIGHTVALUE = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public BILLINGWEIGHT() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public BILLINGWEIGHT(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the WEIGHTUNITS property.
     */

    public String getWEIGHTUNITS() {

        return (_WEIGHTUNITS);

    }


    /**
     * Set the WEIGHTUNITS property.
     */

    public void setWEIGHTUNITS(String newValue) {

        _WEIGHTUNITS = newValue;

    }


    /**
     * Checks for whether WEIGHTUNITS is set or not.
     *
     * @returns true if WEIGHTUNITS is set, false if not
     */

    public boolean hasWEIGHTUNITS() {

        return (_WEIGHTUNITS != null);

    }


    /**
     * Discards WEIGHTUNITS's value.
     */

    public void deleteWEIGHTUNITS() {

        _WEIGHTUNITS = null;

    }


    /**
     * Get the WEIGHTVALUE property.
     */

    public String getWEIGHTVALUE() {

        return (_WEIGHTVALUE);

    }


    /**
     * Set the WEIGHTVALUE property.
     */

    public void setWEIGHTVALUE(String newValue) {

        _WEIGHTVALUE = newValue;

    }


    /**
     * Checks for whether WEIGHTVALUE is set or not.
     *
     * @returns true if WEIGHTVALUE is set, false if not
     */

    public boolean hasWEIGHTVALUE() {

        return (_WEIGHTVALUE != null);

    }


    /**
     * Discards WEIGHTVALUE's value.
     */

    public void deleteWEIGHTVALUE() {

        _WEIGHTVALUE = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($BILLINGWEIGHT);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($BILLINGWEIGHT);

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

            _WEIGHTUNITS_validator_ = null;

    transient protected static XmlStringValidator

            _WEIGHTVALUE_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _WEIGHTUNITS_validator_ = new XmlStringValidator("BILLINGWEIGHT.WEIGHTUNITS", "Element",

                "BILLINGWEIGHT/WEIGHTUNITS", -1, -1, true);


        _WEIGHTVALUE_validator_ = new XmlStringValidator("BILLINGWEIGHT.WEIGHTVALUE", "Element",

                "BILLINGWEIGHT/WEIGHTVALUE", -1, -1, true);


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

        e = _WEIGHTUNITS_validator_.validate(_WEIGHTUNITS);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

        }


        e = _WEIGHTVALUE_validator_.validate(_WEIGHTVALUE);

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


        return (xml.equals($BILLINGWEIGHT));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static BILLINGWEIGHT unmarshal(InputStream in) throws Exception {


        BILLINGWEIGHT obj = new BILLINGWEIGHT();

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


        if (!xml.equals($BILLINGWEIGHT)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($WEIGHTUNITS)) {

            setWEIGHTUNITS(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($WEIGHTVALUE)) {

            setWEIGHTVALUE(xml.getData());

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


        out.write($WEIGHTUNITS,

                _WEIGHTUNITS);

        out.write($WEIGHTVALUE,

                _WEIGHTVALUE);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2q8:otg8gh");

    }

}
