/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: RETURN

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
 * RETURN class.
 */

public class RETURN

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "DATA" node name.
     */

    public static final String $DATA = "DATA";


    /**
     * Constant for "ENUMRETURN" node name.
     */

    public static final String $ENUMRETURN = "ENUMRETURN";


    /**
     * Constant for "RETURN" node name.
     */

    public static final String $RETURN = "RETURN";


    /**
     * Constant for "ERRORDESCRIPTION" node name.
     */

    public static final String $ERRORDESCRIPTION = "ERRORDESCRIPTION";


    /**
     * Constant for "ERRORCODE" node name.
     */

    public static final String $ERRORCODE = "ERRORCODE";


    protected String _ENUMRETURN = null;

    protected String _ERRORCODE = null;

    protected String _ERRORDESCRIPTION = null;

    protected java.util.Vector _DATA = new java.util.Vector();


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public RETURN() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public RETURN(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the ENUMRETURN property.
     */

    public String getENUMRETURN() {

        return (_ENUMRETURN);

    }


    /**
     * Set the ENUMRETURN property.
     */

    public void setENUMRETURN(String newValue) {

        _ENUMRETURN = newValue;

    }


    /**
     * Checks for whether ENUMRETURN is set or not.
     *
     * @returns true if ENUMRETURN is set, false if not
     */

    public boolean hasENUMRETURN() {

        return (_ENUMRETURN != null);

    }


    /**
     * Discards ENUMRETURN's value.
     */

    public void deleteENUMRETURN() {

        _ENUMRETURN = null;

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
     * Get the DATA property.
     */

    public java.util.Vector getDATA() {

        return (_DATA);

    }


    public DATA getDATAAt(int index)

            throws IndexOutOfBoundsException {

        return ((DATA) _DATA.elementAt(index));

    }


    /**
     * Get the count of elements in the DATA property.
     */

    public int getDATACount() {

        if (_DATA == null) {

            return (0);

        }


        return (_DATA.size());

    }


    /**
     * Set the DATA property.
     */

    public void setDATA(java.util.Vector newList) {


        if (newList == null) {

            _DATA.removeAllElements();

        } else {

            _DATA = (java.util.Vector) newList.clone();

        }

    }


    public void addDATA(DATA obj) {

        if (obj == null) {

            return;

        }


        _DATA.addElement(obj);

    }


    public void setDATAAt(DATA obj, int index)

            throws IndexOutOfBoundsException {

        if (obj == null) {

            return;

        }


        _DATA.setElementAt(obj, index);

    }


    public void removeDATA(DATA obj) {

        if (obj == null) {

            return;

        }


        _DATA.removeElement(obj);

    }


    public void removeDATAAt(int index)

            throws IndexOutOfBoundsException {

        _DATA.removeElementAt(index);

    }


    protected void setDATA(XmlElement xml) {


        _DATA.removeAllElements();

        XmlElement saved_xml = xml;


        while (xml != null &&

                xml.equals(DATA.$DATA)) {

            Object obj = new DATA(xml);

            _DATA.addElement(obj);

            saved_xml.setLastProcessed(xml);

            xml = xml.next();

        }

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($RETURN);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($RETURN);

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

            _ENUMRETURN_validator_ = null;

    transient protected static XmlStringValidator

            _ERRORCODE_validator_ = null;

    transient protected static XmlStringValidator

            _ERRORDESCRIPTION_validator_ = null;

    transient protected static XmlValidator

            _DATA_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _ENUMRETURN_validator_ = new XmlStringValidator("RETURN.ENUMRETURN", "Element",

                "RETURN/ENUMRETURN", -1, -1, true);


        _ERRORCODE_validator_ = new XmlStringValidator("RETURN.ERRORCODE", "Element",

                "RETURN/ERRORCODE", -1, -1, true);


        _ERRORDESCRIPTION_validator_ = new XmlStringValidator("RETURN.ERRORDESCRIPTION", "Element",

                "RETURN/ERRORDESCRIPTION", -1, -1, true);


        _DATA_validator_ = new XmlValidator("RETURN.DATA", "Element",

                "RETURN/DATA", false);


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

        e = _ENUMRETURN_validator_.validate(_ENUMRETURN);

        if (e != null) {

            errors.addElement(e);

            if (return_on_error) {

                return (errors);

            }

        }


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


        if (!traverse) {

            if (errors.size() < 1) {

                return (null);

            }


            return (errors);

        }


        boolean is_valid;


        is_valid = _DATA_validator_.isValid(_DATA,

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


        return (xml.equals($RETURN));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static RETURN unmarshal(InputStream in) throws Exception {


        RETURN obj = new RETURN();

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


        if (!xml.equals($RETURN)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($ENUMRETURN)) {

            setENUMRETURN(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

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


        if (xml.equals($DATA)) {

            setDATA(xml);

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


        out.write($ENUMRETURN,

                _ENUMRETURN);

        out.write($ERRORCODE,

                _ERRORCODE);

        out.write($ERRORDESCRIPTION,

                _ERRORDESCRIPTION);

        out.write(null,

                getDATA(), embed_files);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q267:mjhffi");

    }

}
