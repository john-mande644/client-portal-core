/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: SHIPMENTSERVICEOPTIONS_1

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
 * SHIPMENTSERVICEOPTIONS_1 class.
 */

public class SHIPMENTSERVICEOPTIONS_1

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "PLD_DECLARATION" node name.
     */

    public static final String $PLD_DECLARATION = "PLD_DECLARATION";


    /**
     * Constant for "BILLCONSIGNEE" node name.
     */

    public static final String $BILLCONSIGNEE = "BILLCONSIGNEE";


    /**
     * Constant for "SATDELIVERY" node name.
     */

    public static final String $SATDELIVERY = "SATDELIVERY";


    /**
     * Constant for "SATPICKUP" node name.
     */

    public static final String $SATPICKUP = "SATPICKUP";


    /**
     * Constant for "SHIPMENTSERVICEOPTIONS" node name.
     */

    public static final String $SHIPMENTSERVICEOPTIONS = "SHIPMENTSERVICEOPTIONS";


    /**
     * Constant for "DECLAREDVALUE" node name.
     */

    public static final String $DECLAREDVALUE = "DECLAREDVALUE";


    protected String _SATDELIVERY = null;

    protected String _SATPICKUP = null;

    protected DECLAREDVALUE _DECLAREDVALUE = null;

    protected String _BILLCONSIGNEE = null;

    protected String _PLDDECLARATION = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public SHIPMENTSERVICEOPTIONS_1() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public SHIPMENTSERVICEOPTIONS_1(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the SATDELIVERY property.
     */

    public String getSATDELIVERY() {

        return (_SATDELIVERY);

    }


    /**
     * Set the SATDELIVERY property.
     */

    public void setSATDELIVERY(String newValue) {

        _SATDELIVERY = newValue;

    }


    /**
     * Checks for whether SATDELIVERY is set or not.
     *
     * @returns true if SATDELIVERY is set, false if not
     */

    public boolean hasSATDELIVERY() {

        return (_SATDELIVERY != null);

    }


    /**
     * Discards SATDELIVERY's value.
     */

    public void deleteSATDELIVERY() {

        _SATDELIVERY = null;

    }


    /**
     * Get the SATPICKUP property.
     */

    public String getSATPICKUP() {

        return (_SATPICKUP);

    }


    /**
     * Set the SATPICKUP property.
     */

    public void setSATPICKUP(String newValue) {

        _SATPICKUP = newValue;

    }


    /**
     * Checks for whether SATPICKUP is set or not.
     *
     * @returns true if SATPICKUP is set, false if not
     */

    public boolean hasSATPICKUP() {

        return (_SATPICKUP != null);

    }


    /**
     * Discards SATPICKUP's value.
     */

    public void deleteSATPICKUP() {

        _SATPICKUP = null;

    }


    /**
     * Get the DECLAREDVALUE property.
     */

    public DECLAREDVALUE getDECLAREDVALUE() {

        return (_DECLAREDVALUE);

    }


    /**
     * Set the DECLAREDVALUE property.
     */

    public void setDECLAREDVALUE(DECLAREDVALUE obj) {

        _DECLAREDVALUE = obj;

    }


    protected void setDECLAREDVALUE(XmlElement xml) {


        _DECLAREDVALUE =

                new DECLAREDVALUE(xml);

    }


    /**
     * Checks for whether DECLAREDVALUE is set or not.
     *
     * @returns true if DECLAREDVALUE is set, false if not
     */

    public boolean hasDECLAREDVALUE() {

        return (_DECLAREDVALUE != null);

    }


    /**
     * Discards DECLAREDVALUE's value.
     */

    public void deleteDECLAREDVALUE() {

        _DECLAREDVALUE = null;

    }


    /**
     * Get the BILLCONSIGNEE property.
     */

    public String getBILLCONSIGNEE() {

        return (_BILLCONSIGNEE);

    }


    /**
     * Set the BILLCONSIGNEE property.
     */

    public void setBILLCONSIGNEE(String newValue) {

        _BILLCONSIGNEE = newValue;

    }


    /**
     * Checks for whether BILLCONSIGNEE is set or not.
     *
     * @returns true if BILLCONSIGNEE is set, false if not
     */

    public boolean hasBILLCONSIGNEE() {

        return (_BILLCONSIGNEE != null);

    }


    /**
     * Discards BILLCONSIGNEE's value.
     */

    public void deleteBILLCONSIGNEE() {

        _BILLCONSIGNEE = null;

    }


    /**
     * Get the PLDDECLARATION property.
     */

    public String getPLDDECLARATION() {

        return (_PLDDECLARATION);

    }


    /**
     * Set the PLDDECLARATION property.
     */

    public void setPLDDECLARATION(String newValue) {

        _PLDDECLARATION = newValue;

    }


    /**
     * Checks for whether PLDDECLARATION is set or not.
     *
     * @returns true if PLDDECLARATION is set, false if not
     */

    public boolean hasPLDDECLARATION() {

        return (_PLDDECLARATION != null);

    }


    /**
     * Discards PLDDECLARATION's value.
     */

    public void deletePLDDECLARATION() {

        _PLDDECLARATION = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($SHIPMENTSERVICEOPTIONS);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($SHIPMENTSERVICEOPTIONS);

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

            _DECLAREDVALUE_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _DECLAREDVALUE_validator_ = new XmlValidator("SHIPMENTSERVICEOPTIONS_1.DECLAREDVALUE", "Element",

                "SHIPMENTSERVICEOPTIONS/DECLAREDVALUE", false);


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


        is_valid = _DECLAREDVALUE_validator_.isValid(_DECLAREDVALUE,

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


        return (xml.equals($SHIPMENTSERVICEOPTIONS));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static SHIPMENTSERVICEOPTIONS_1 unmarshal(InputStream in) throws Exception {


        SHIPMENTSERVICEOPTIONS_1 obj = new SHIPMENTSERVICEOPTIONS_1();

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


        if (!xml.equals($SHIPMENTSERVICEOPTIONS)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($SATDELIVERY)) {

            setSATDELIVERY(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($SATPICKUP)) {

            setSATPICKUP(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($DECLAREDVALUE)) {

            setDECLAREDVALUE(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($BILLCONSIGNEE)) {

            setBILLCONSIGNEE(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($PLD_DECLARATION)) {

            setPLDDECLARATION(xml.getData());

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


        out.write($SATDELIVERY,

                _SATDELIVERY);

        out.write($SATPICKUP,

                _SATPICKUP);


        Object DECLAREDVALUE_value = getDECLAREDVALUE();

        if (DECLAREDVALUE_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) DECLAREDVALUE_value,

                    embed_files);

        }

        out.write($BILLCONSIGNEE,

                _BILLCONSIGNEE);

        out.write($PLD_DECLARATION,

                _PLDDECLARATION);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2z5:10e7u02");

    }

}
