/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: PACKINGLIST

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
 * PACKINGLIST class.
 */

public class PACKINGLIST

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "ITEM" node name.
     */

    public static final String $ITEM = "ITEM";


    /**
     * Constant for "PRINTPACKINGLIST" node name.
     */

    public static final String $PRINTPACKINGLIST = "PRINTPACKINGLIST";


    /**
     * Constant for "PACKINGLIST" node name.
     */

    public static final String $PACKINGLIST = "PACKINGLIST";


    protected java.util.Vector _ITEM = new java.util.Vector();

    protected PRINTPACKINGLIST _PRINTPACKINGLIST = null;


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public PACKINGLIST() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public PACKINGLIST(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the ITEM property.
     */

    public java.util.Vector getITEM() {

        return (_ITEM);

    }


    public String getITEMAt(int index)

            throws IndexOutOfBoundsException {

        XmlString obj =

                (XmlString) _ITEM.elementAt(index);

        return (obj.getValue());

    }


    /**
     * Get the count of elements in the ITEM property.
     */

    public int getITEMCount() {

        if (_ITEM == null) {

            return (0);

        }


        return (_ITEM.size());

    }


    /**
     * Set the ITEM property.
     */

    public void setITEM(java.util.Vector newList) {

        if (newList == null) {

            _ITEM.removeAllElements();

        } else {

            _ITEM = (java.util.Vector) newList.clone();

        }

    }


    protected void setITEM(XmlElement xml) {


        _ITEM.removeAllElements();


        XmlElement saved_xml = xml;


        while (xml != null &&

                xml.equals($ITEM)) {


            XmlType obj = new XmlString(xml);

            _ITEM.addElement(obj);

            saved_xml.setLastProcessed(xml);

            xml = xml.next();

        }

    }


    public void addITEM(String value) {

        _ITEM.addElement(new XmlString($ITEM, value));

    }


    public void setITEMAt(String value, int index)

            throws IndexOutOfBoundsException {


        XmlString obj =

                new XmlString($ITEM, value);

        _ITEM.setElementAt(obj, index);

    }


    public void removeITEMAt(int index)

            throws IndexOutOfBoundsException {


        _ITEM.removeElementAt(index);

    }


    /**
     * Get the PRINTPACKINGLIST property.
     */

    public PRINTPACKINGLIST getPRINTPACKINGLIST() {

        return (_PRINTPACKINGLIST);

    }


    /**
     * Set the PRINTPACKINGLIST property.
     */

    public void setPRINTPACKINGLIST(PRINTPACKINGLIST obj) {

        _PRINTPACKINGLIST = obj;

    }


    protected void setPRINTPACKINGLIST(XmlElement xml) {


        _PRINTPACKINGLIST =

                new PRINTPACKINGLIST(xml);

    }


    /**
     * Checks for whether PRINTPACKINGLIST is set or not.
     *
     * @returns true if PRINTPACKINGLIST is set, false if not
     */

    public boolean hasPRINTPACKINGLIST() {

        return (_PRINTPACKINGLIST != null);

    }


    /**
     * Discards PRINTPACKINGLIST's value.
     */

    public void deletePRINTPACKINGLIST() {

        _PRINTPACKINGLIST = null;

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($PACKINGLIST);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($PACKINGLIST);

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

            _ITEM_validator_ = null;

    transient protected static XmlValidator

            _PRINTPACKINGLIST_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _ITEM_validator_ = new XmlStringValidator("PACKINGLIST.ITEM", "Element",

                "PACKINGLIST/ITEM", -1, -1, true);


        _PRINTPACKINGLIST_validator_ = new XmlValidator("PACKINGLIST.PRINTPACKINGLIST", "Element",

                "PACKINGLIST/PRINTPACKINGLIST", true);


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

        e = _ITEM_validator_.validate(_ITEM);

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


        is_valid = _PRINTPACKINGLIST_validator_.isValid(_PRINTPACKINGLIST,

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


        return (xml.equals($PACKINGLIST));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static PACKINGLIST unmarshal(InputStream in) throws Exception {


        PACKINGLIST obj = new PACKINGLIST();

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


        if (!xml.equals($PACKINGLIST)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($ITEM)) {

            setITEM(xml);

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($PRINTPACKINGLIST)) {

            setPRINTPACKINGLIST(xml);

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


        out.write(null,

                getITEM(), embed_files);


        Object PRINTPACKINGLIST_value = getPRINTPACKINGLIST();

        if (PRINTPACKINGLIST_value instanceof XmlObject) {

            out.write(null,

                    (XmlObject) PRINTPACKINGLIST_value,

                    embed_files);

        }


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2tb:das8h6");

    }

}