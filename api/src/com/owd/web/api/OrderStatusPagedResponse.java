package com.owd.web.api;

import org.apache.commons.lang.StringEscapeUtils;

public class OrderStatusPagedResponse extends  OrderStatusResponse {

    public static final String kTotalCount 	= "totalCount";
    public static final String kPageSize    = "pageSize";
    public static final String kPageIndex 	= "pageIndex";

    protected int totalCount;
    protected int pageSize;
    protected int pageIndex;

    public OrderStatusPagedResponse(double api_v, int totalCount, int pageSize, int pageIndex) {
        super(api_v);
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }


    public String getStartTag() {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><"+kTagAPIResponse);
        sb.append(" "+kAttResults+"=\""+results+"\"");

        if(results.equals("ERROR")) {
            sb.append(" "+kAttErrorType+"=\""+error_type+"\"");
            sb.append(" "+kAttErrorResponse+"=\""+ StringEscapeUtils.escapeXml(error_response)+"\"");
        }

        sb.append(" "+kTotalCount+"=\""+totalCount+"\"");
        sb.append(" "+kPageSize+"=\""+pageSize+"\"");
        sb.append(" "+kPageIndex+"=\""+pageIndex+"\"");
        sb.append(">");

        try {
            return new String(sb.toString().getBytes("UTF-8"),"UTF-8");
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }
}
