package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 2:19:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExcelUtils {
private final static Logger log =  LogManager.getLogger();

    public static String getCellValue(String val) {
        if (val == null) return "";
        return "=\"" + val.replace(',', ' ').replace('\r', '\\').replace('\n', '\\') + "\"";
    }


    public static String getCellValue(double val, DecimalFormat decform) {

        return "=" + decform.format(val);
    }

    public static String getCellValue(int val) {

        return "=" + val;
    }
}
