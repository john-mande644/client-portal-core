package com.owd.intranet.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.beanutils.DynaBean;

import org.displaytag.decorator.TableDecorator;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 11, 2006
 * Time: 3:08:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdjustmentTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getVoided() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";
        String voided = null;
        try {
            voided = (String) bean.get("void").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (voided.equals("true")) {
            return "<span class='void'>&middot</span>";
        }
        return "";
    }

    public String getPostedlink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";

        Integer done = (Integer) bean.get("Posted");

        if (done.equals(new Integer(1))) {
            return "Posted";
        }
        return "";
    }

    public String getRemovelink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";
        Integer itemId = (Integer) bean.get("id");
        String location = (String) bean.get("location");
        return "<a href=\"javascript:removeLine('" + itemId + "','" + location + "')\">Remove</a>";
    }

     public String getLink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";

        Integer recId = (Integer) bean.get("receive_id");
        String trans = (String) bean.get("transaction_num");


            return "<a href=\"/internal/loadAdjust.do?recId="+recId+"\">"+trans+"</a>";


    }

}

