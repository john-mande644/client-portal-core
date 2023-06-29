package com.owd.results;

import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import org.apache.struts2.ServletActionContext;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 12, 2009
 * Time: 8:58:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class JSONResult implements Result {

    public static final String DEFAULT_PARAM = "classAlias";
    String classAlias;
            public String getClassAlias() {
                return classAlias;
            }

    public void setClassAlias(String classAlias) {
        this.classAlias = classAlias;
    }
    public void execute(ActionInvocation invocation) throws Exception {

        ServletActionContext.getResponse().setContentType("text/plain");
        PrintWriter responseStream = ServletActionContext.getResponse().getWriter();

        ValueStack valueStack = invocation.getStack();

        Object jsonModel = valueStack.findValue("jsonModel");
                              
       XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });

        if (classAlias == null){
            classAlias = "object";
        }
       

        responseStream.println(xstream.toXML(jsonModel));




    }
}