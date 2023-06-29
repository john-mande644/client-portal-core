package com.owd.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import javax.servlet.jsp.PageContext;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 18, 2004
 * Time: 11:12:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class StandardMoneyDecorator extends TableDecorator implements DisplaytagColumnDecorator {
private final static Logger log =  LogManager.getLogger();

    static DecimalFormat df = new DecimalFormat("$#,###,##0.00");


    @Override
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum mediaTypeEnum) throws DecoratorException {

        if (columnValue instanceof Number) {
            return df.format(columnValue);
        }

        return columnValue.toString();
    }

}
