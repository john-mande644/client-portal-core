package com.owd.core.ruleAdapters;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 12/12/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BaseRuleAdapter {

    /**
     * Adds or updates a property value.
     *
     * @param name
     * @param value
     */
    @DroolsDescriptor(
            description = "Adds or updates a property value.",
            parameterType = "String",
            returnType = "None"
    )
    public void setProperty(String name, String value);

    /**
     * Returns the value of a given property.
     * @param name
     * @return
     */
    @DroolsDescriptor(
            description = "Returns the value of a given property.",
            parameterType = "String",
            returnType = "String"
    )
    public String getProperty(String name);


    /**
     * Returns the value of a given property. If the return value is null the default will be returned.
     * @param name
     * @param defaultValue
     * @return
     */
    @DroolsDescriptor(
            description = "Returns the value of a given property. If the return value is null the default will be returned.",
            parameterType = "String",
            returnType = "String"
    )
    public String getProperty(String name, String defaultValue);

    /**
     *
     * @param title
     * @param message
     * @param emailAddress
     */
    public void sendMail(String title, String message, String emailAddress);

    /**
     * Allows any method to be executed through reflection.
     *
     * @param methodName
     * @param parms
     * @return
     */
    @DroolsDescriptor(
            description = "Allows any method of the referenced object to be executed through reflection.",
            parameterType = "String",
            returnType = "String"
    )
    public Object executeRuleMethod(String methodName, List<Object> parms);

    /**
     *
     * @param startData
     * @param endDate
     * @return
     */
    public boolean withinDateRange(String startData, String endDate);

    /**
     *
     * @param startData
     * @param endDate
     * @return
     */
    public boolean withinDateRangeUsingFormat(String startData, String endDate, String format);

}
