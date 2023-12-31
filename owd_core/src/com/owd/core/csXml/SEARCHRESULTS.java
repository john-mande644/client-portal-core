/*

 * DO NOT EDIT!

 *

 * This file was generated by the Breeze XML Studio Java Export Wizard.

 *

 *        Project: connectship

 *     Class Name: SEARCHRESULTS

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
 * SEARCHRESULTS class.
 */

public class SEARCHRESULTS

        implements com.tbf.xml.XmlObject, Validateable, java.io.Serializable {
private final static Logger log =  LogManager.getLogger();


    /**
     * Constant for "RETURNCOUNT" node name.
     */

    public static final String $RETURNCOUNT = "RETURNCOUNT";


    /**
     * Constant for "SEARCHRESULTS" node name.
     */

    public static final String $SEARCHRESULTS = "SEARCHRESULTS";


    /**
     * Constant for "RESULTITEM" node name.
     */

    public static final String $RESULTITEM = "RESULTITEM";


    protected String _RETURNCOUNT = null;

    protected java.util.Vector _RESULTITEM = new java.util.Vector();


    /**
     * Storage for UNEXPECTED_XML errors.
     */

    protected Vector _unexpected_xml_errors_ = null;


    /**
     * Default no args constructor.
     */

    public SEARCHRESULTS() {

    }


    /**
     * Creates and populates an instance from the provided parse tree.
     *
     * @param xml the parse tree
     */

    public SEARCHRESULTS(XmlElement xml) {

        fromXml(xml);

    }


    /**
     * Get the RETURNCOUNT property.
     */

    public String getRETURNCOUNT() {

        return (_RETURNCOUNT);

    }


    /**
     * Set the RETURNCOUNT property.
     */

    public void setRETURNCOUNT(String newValue) {

        _RETURNCOUNT = newValue;

    }


    /**
     * Checks for whether RETURNCOUNT is set or not.
     *
     * @returns true if RETURNCOUNT is set, false if not
     */

    public boolean hasRETURNCOUNT() {

        return (_RETURNCOUNT != null);

    }


    /**
     * Discards RETURNCOUNT's value.
     */

    public void deleteRETURNCOUNT() {

        _RETURNCOUNT = null;

    }


    /**
     * Get the RESULTITEM property.
     */

    public java.util.Vector getRESULTITEM() {

        return (_RESULTITEM);

    }


    public RESULTITEM getRESULTITEMAt(int index)

            throws IndexOutOfBoundsException {

        return ((RESULTITEM) _RESULTITEM.elementAt(index));

    }


    /**
     * Get the count of elements in the RESULTITEM property.
     */

    public int getRESULTITEMCount() {

        if (_RESULTITEM == null) {

            return (0);

        }


        return (_RESULTITEM.size());

    }


    /**
     * Set the RESULTITEM property.
     */

    public void setRESULTITEM(java.util.Vector newList) {


        if (newList == null) {

            _RESULTITEM.removeAllElements();

        } else {

            _RESULTITEM = (java.util.Vector) newList.clone();

        }

    }


    public void addRESULTITEM(RESULTITEM obj) {

        if (obj == null) {

            return;

        }


        _RESULTITEM.addElement(obj);

    }


    public void setRESULTITEMAt(RESULTITEM obj, int index)

            throws IndexOutOfBoundsException {

        if (obj == null) {

            return;

        }


        _RESULTITEM.setElementAt(obj, index);

    }


    public void removeRESULTITEM(RESULTITEM obj) {

        if (obj == null) {

            return;

        }


        _RESULTITEM.removeElement(obj);

    }


    public void removeRESULTITEMAt(int index)

            throws IndexOutOfBoundsException {

        _RESULTITEM.removeElementAt(index);

    }


    protected void setRESULTITEM(XmlElement xml) {


        _RESULTITEM.removeAllElements();

        XmlElement saved_xml = xml;


        while (xml != null &&

                xml.equals(RESULTITEM.$RESULTITEM)) {

            Object obj = new RESULTITEM(xml);

            _RESULTITEM.addElement(obj);

            saved_xml.setLastProcessed(xml);

            xml = xml.next();

        }

    }


    /**
     * Gets the XML tag name for this object.
     */

    public String getXmlTagName() {

        return ($SEARCHRESULTS);

    }


    /**
     * Gets the XML tag name for this class.
     */

    public static String getClassXmlTagName() {

        return ($SEARCHRESULTS);

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

            _RETURNCOUNT_validator_ = null;

    transient protected static XmlValidator

            _RESULTITEM_validator_ = null;


    /**
     * Create the validators for this class.
     */

    protected static synchronized void createValidators() {


        if (_validators_created) {

            return;

        }


        _RETURNCOUNT_validator_ = new XmlStringValidator("SEARCHRESULTS.RETURNCOUNT", "Element",

                "SEARCHRESULTS/RETURNCOUNT", -1, -1, true);


        _RESULTITEM_validator_ = new XmlValidator("SEARCHRESULTS.RESULTITEM", "Element",

                "SEARCHRESULTS/RESULTITEM", true);


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

        e = _RETURNCOUNT_validator_.validate(_RETURNCOUNT);

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


        is_valid = _RESULTITEM_validator_.isValid(_RESULTITEM,

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


        return (xml.equals($SEARCHRESULTS));

    }


    /**
     * This method unmarshals an XML document instance
     * <p/>
     * into an instance of this class.
     */

    public static SEARCHRESULTS unmarshal(InputStream in) throws Exception {


        SEARCHRESULTS obj = new SEARCHRESULTS();

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


        if (!xml.equals($SEARCHRESULTS)) {

            return;

        }





        /*

         * Get the contained XmlElement, this is what we process

         */

        xml = xml.getSubElementAt(0);

        if (xml == null) {

            return;

        }


        if (xml.equals($RETURNCOUNT)) {

            setRETURNCOUNT(xml.getData());

            xml = xml.next();

            if (xml == null) {

                return;

            }

        }


        if (xml.equals($RESULTITEM)) {

            setRESULTITEM(xml);

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


        out.write($RETURNCOUNT,

                _RETURNCOUNT);

        out.write(null,

                getRESULTITEM(), embed_files);


        out.decrementIndent();

        out.writeEndTag(getXmlTagName());

    }


    /**
     * Get the  birth certificate for this object.
     */

    public String birthCertificate() {

        return ("1hge137:cy11q2vt:7wljd1");

    }

}
