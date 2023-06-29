package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import javax.servlet.jsp.PageContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 18, 2004
 * Time: 11:12:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class StandardDateDecorator extends TableDecorator implements DisplaytagColumnDecorator {
private final static Logger log =  LogManager.getLogger();

    static DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @Override
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum mediaTypeEnum) throws DecoratorException {

        if (columnValue == null) return "";
        try {
            if (columnValue instanceof Date) {

                return df.format(columnValue);
            }

            return columnValue.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
