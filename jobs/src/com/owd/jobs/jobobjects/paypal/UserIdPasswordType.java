package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserIdPasswordType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="UserIdPasswordType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AppId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DevId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthCert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Username" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Signature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserIdPasswordType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "appId",
        "devId",
        "authCert",
        "username",
        "password",
        "signature",
        "subject",
        "authToken"
})
public class UserIdPasswordType
{

    @XmlElement(name = "AppId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String appId;
    @XmlElement(name = "DevId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String devId;
    @XmlElement(name = "AuthCert", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String authCert;
    @XmlElement(name = "Username", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String username;
    @XmlElement(name = "Password", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String password;
    @XmlElement(name = "Signature", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String signature;
    @XmlElement(name = "Subject", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String subject;
    @XmlElement(name = "AuthToken", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String authToken;

    /**
     * Gets the value of the appId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAppId()
    {
        return appId;
    }

    /**
     * Sets the value of the appId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAppId(String value)
    {
        this.appId = value;
    }

    /**
     * Gets the value of the devId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDevId()
    {
        return devId;
    }

    /**
     * Sets the value of the devId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDevId(String value)
    {
        this.devId = value;
    }

    /**
     * Gets the value of the authCert property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAuthCert()
    {
        return authCert;
    }

    /**
     * Sets the value of the authCert property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAuthCert(String value)
    {
        this.authCert = value;
    }

    /**
     * Gets the value of the username property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Sets the value of the username property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUsername(String value)
    {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the value of the password property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPassword(String value)
    {
        this.password = value;
    }

    /**
     * Gets the value of the signature property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSignature()
    {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSignature(String value)
    {
        this.signature = value;
    }

    /**
     * Gets the value of the subject property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSubject(String value)
    {
        this.subject = value;
    }

    /**
     * Gets the value of the authToken property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAuthToken()
    {
        return authToken;
    }

    /**
     * Sets the value of the authToken property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAuthToken(String value)
    {
        this.authToken = value;
    }

}
