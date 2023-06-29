package com.owd.callcenter.displaytag;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import javax.servlet.jsp.PageContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 16, 2006
 * Time: 2:43:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardDateTimeDecorator extends TableDecorator implements DisplaytagColumnDecorator {

    static DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);



    @Override
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum mediaTypeEnum) throws DecoratorException {

        if (columnValue == null) return "";
        try {
            if (columnValue instanceof Date) {

                return StandardDateTimeDecorator.df.format(columnValue);
            }

            return columnValue.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
