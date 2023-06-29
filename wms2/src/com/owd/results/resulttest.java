package com.owd.results;

import com.owd.dc.beans.jsonTreeBean;
import com.owd.dc.beans.treeBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 17, 2009
 * Time: 10:54:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class resulttest {

    public static void main(String[] args) {
        List<treeBean> jsonModel = new ArrayList<treeBean>();
        treeBean t = new treeBean(true, "1", "All OWD");
        jsonModel.add(t);

        jsonTreeBean m = new jsonTreeBean();

        m.setIdentifier("id");
        m.setLabel("label");
        m.setItems(jsonModel);


        XStream xstream = new XStream(new JettisonMappedXmlDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });

        xstream.alias("options", m.getClass());


        System.out.println(xstream.toXML(m));
        XStream x = new XStream();
        x.alias("OWD", m.getClass());
        x.alias("Tree Bean", treeBean.class);
        System.out.println(x.toXML(m));

        //  String s = xstream.toXML(m);

        // System.out.println(s.replaceAll("\"options\": \\{","").replaceAll("\\}\\}","}"));
        // System.out.println(s);
    }
}
