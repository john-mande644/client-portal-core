package com.owd.dc.inventorycount;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.util.ParamEncoder;
import org.displaytag.tags.TableTagParameters;
import org.apache.commons.beanutils.DynaBean;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 23, 2006
 * Time: 7:20:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryCountDecorator extends TableDecorator {

    public String getLink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";


        String location = (String) bean.get("location");

        Integer done = (Integer) bean.get("closed");

        if (done.equals(new Integer(1))) {
            return "<a href=\"countLocationReopen?location=" + location + "\">Re-Open</a>";
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
        if (done.equals(new Integer(2))) {
            return "Closed";
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

    public String getCloselink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";
        Integer id = (Integer) bean.get("Link");
        Integer posted = (Integer) bean.get("Posted");
        Integer complete = (Integer) bean.get("Complete");


        if (posted.intValue() < 1 && complete.intValue() > 99) {
            System.out.println("in posted if");
            return "<a href=\"closeCount?id=" + id + "&location=close\">Close Count</a>";
        }
        if (posted.intValue() == 2) {
            return "<a href=\"closeCount?id=" + id + "&location=open\">Re-open</a>";
        }
        return "";
    }

    public String getSkuLink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";
        Integer id = (Integer) bean.get("Link");
        String pageId = new ParamEncoder("table1").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        String pageNum = getPageContext().getRequest().getParameter(pageId);
        //getPageContext().getRequest().setAttribute("mainPage",pageId+"="+pageNum);
        if (null == pageNum) {
            return "<a href=\"loadCount?id=" + id + "\">Sku's</a>";

        }
        return "<a href=\"loadCount?id=" + id + "&mainPage=" + pageId + "&mainPageNum=" + pageNum + "\">Sku's</a>";


    }

    public String getLocationsLink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";
        Integer id = (Integer) bean.get("Link");
        String pageId = new ParamEncoder("table1").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        String pageNum = getPageContext().getRequest().getParameter(pageId);
        //getPageContext().getRequest().setAttribute("mainPage",pageId+"="+pageNum);
        if (null == pageNum) {
            return "<a href=\"loadCountLocations?id=" + id + "\">Locations's</a>";

        }
        return "<a href=\"loadCountLocations?id=" + id + "&mainPage=" + pageId + "&mainPageNum=" + pageNum + "\">Location's</a>";


    }

    public String getInventoryLink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";
        Integer id = (Integer) bean.get("inventory_id");
        String pageId = new ParamEncoder("table2").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        String pageNum = getPageContext().getRequest().getParameter(pageId);
        
        //getPageContext().getRequest().setAttribute("mainPage",pageId+"="+pageNum);
        if (null == pageNum) {
            return "<a href=\"loadCountItem?invId=" + id + "\">" + id + "</a>";

        }
        return "<a href=\"loadCountItem?invId=" + id + "&skuPage=" + pageId + "&skuPageNum=" + pageNum + "\">" + id + "</a>";


    }

     public String getLocLink() {

        DynaBean bean = (DynaBean) getCurrentRowObject();
        if (bean == null) return "";
        String id = (String) bean.get("location");
        String pageId = new ParamEncoder("table1").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        String pageNum = getPageContext().getRequest().getParameter(pageId);
        //getPageContext().getRequest().setAttribute("mainPage",pageId+"="+pageNum);
        if (null == pageNum) {
            return "<a href=\"loadCountLocation?location=" + id + "\">" + id + "</a>";

        }
        return "<a href=\"loadCountLocation?location=" + id + "&locPage=" + pageId + "&locPageNum=" + pageNum + "\">" + id + "</a>";


    }

    public String getHistoryPic(){
      historyBean hs = (historyBean) getCurrentRowObject();
        if(hs.getRemovedate()==null){
            return "<img src='/wms/images/greencheck20.png'>";
        }
        return "<img src='/wms/images/redx20.png'>";
    }
    public String getTesting() {
        //System.out.println(getListIndex());
        //System.out.println(getViewIndex());
        System.out.println(getPageContext().getRequest().getParameter(new ParamEncoder("table1").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

        System.out.println(new ParamEncoder("table1").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        return "";
    }
}
