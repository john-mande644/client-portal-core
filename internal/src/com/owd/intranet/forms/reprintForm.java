package com.owd.intranet.forms;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts.validator.ValidatorForm;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.intranet.util;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 8:54:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class reprintForm extends ValidatorForm {
private final static Logger log =  LogManager.getLogger();
    private String orderNum;
    private OwdOrder order;
    private String printer;
    private List printerList;

    public List getPrinterList() {
        if (null==printerList) setPrinterList();
        return printerList;
    }

    public void setPrinterList() {
        this.printerList = util.loadPrinters();
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public OwdOrder getOrder() {
        return order;
    }

    public void setOrder(OwdOrder order) {
        this.order = order;
    }
}
