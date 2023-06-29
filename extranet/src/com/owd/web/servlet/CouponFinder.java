package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.Coupon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CouponFinder {
private final static Logger log =  LogManager.getLogger();


    String[] tableColumnNames = {"", "Promo Name", "Issued To", "Code", "SKU", "Expires", "Used"};

    String[] tableLinkStarts = {CouponManager.kParamCouponMgrAction + "=" + CouponManager.kParamCouponEditAction + "&" + CouponManager.kparamCouponID + "=", "", "", "", "", "", ""};

    String[] tableLinkEnds = {"Edit</A>", "", "", "", "", "", ""};

    int[] tableLinkMids = {1, 0, 0, 0, 0, 0, 0};

    int[] tableRenders = {WebTable.kRenderString, WebTable.kRenderString, WebTable.kRenderString, WebTable.kRenderString, WebTable.kRenderString, WebTable.kRenderString, WebTable.kRenderString};

    String[] tableColumnDefs = {Coupon.kdbCouponPKName, Coupon.kdbCouponName, Coupon.kdbCouponUser, Coupon.kdbCouponCode, Coupon.kdbCouponSKU, "CONVERT(varchar," + Coupon.kdbCouponExpDate + ",107)", Coupon.kdbCouponUsed};

    String tableFromStmt = "from " + Coupon.kdbCouponTable + " c ";


    public CouponFinder() {


    }


    public static final String kBlank = "";


    private String name = null;

    private String issued = null;

    private String code = null;

    private String valid = null;

    private String sku = null;

    private int expYear = 2011;

    private int expMonth = 12;

    private int expDay = 31;


    private int nameType = 1;

    private int issuedType = 1;

    private int codeType = 1;

    private int validType = 1;

    private int skuType = 1;


    private static final int kIsType = 1;

    private static final int kInType = 2;

    private static final int kGreaterThanType = 3;

    private static final int kLessThanType = 4;


    public static final String kNameSearchFlag = "doname";

    public static final String kNameSearchValue = "namevalue";

    public static final String kNameSearchType = "nametype";


    public static final String kIssuedSearchFlag = "doissued";

    public static final String kIssuedSearchValue = "issuedvalue";

    public static final String kIssuedSearchType = "issuedtype";


    public static final String kCodeSearchFlag = "docode";

    public static final String kCodeSearchValue = "codevalue";

    public static final String kCodeSearchType = "codetype";


    public static final String kSKUSearchFlag = "dosku";

    public static final String kSKUSearchValue = "skuvalue";

    public static final String kSKUSearchType = "skutype";


    public static final String kValidSearchFlag = "dovalid";

    public static final String kValidSearchValue = "validvalue";

    public static final String kValidSearchType = "validtype";


    public static final String kSubmitType = "findcpns";


    public int getIssuedType() {

        return issuedType;

    }


    public int getNameType() {

        return nameType;

    }


    public int getCodeType() {

        return codeType;

    }


    public int getValidType() {

        return validType;

    }

    public int getSKUType() {

        return skuType;

    }


    public static CouponFinder parseRequest(HttpServletRequest req, HttpServletResponse resp) {

        CouponFinder finder = new CouponFinder();


        if (ExtranetServlet.getIntegerParam(req, kNameSearchFlag, 0) == 1) {

            //skuSearch

            finder.setName(ExtranetServlet.getStringParam(req, kNameSearchValue, ""), ExtranetServlet.getIntegerParam(req, kNameSearchType, 1));


        }


        if (ExtranetServlet.getIntegerParam(req, kIssuedSearchFlag, 0) == 1) {

            //countSearch

            finder.setIssued(kBlank + ExtranetServlet.getStringParam(req, kIssuedSearchValue, ""), ExtranetServlet.getIntegerParam(req, kIssuedSearchType, 1));

        }


        if (ExtranetServlet.getIntegerParam(req, kCodeSearchFlag, 0) == 1) {

            //countSearch

            finder.setCode(kBlank + ExtranetServlet.getStringParam(req, kCodeSearchValue, ""), ExtranetServlet.getIntegerParam(req, kCodeSearchType, 1));

        }

        if (ExtranetServlet.getIntegerParam(req, kSKUSearchFlag, 0) == 1) {

            //countSearch

            finder.setSKU(kBlank + ExtranetServlet.getStringParam(req, kSKUSearchValue, ""), ExtranetServlet.getIntegerParam(req, kSKUSearchType, 1));

        }


        if (ExtranetServlet.getIntegerParam(req, kValidSearchFlag, 0) == 1) {

            //countSearch

            finder.setValid(kBlank + ExtranetServlet.getIntegerParam(req, kValidSearchValue, 0), ExtranetServlet.getIntegerParam(req, kValidSearchType, 1));

        }


        if ("Show All".equals(ExtranetServlet.getStringParam(req, kSubmitType, ""))) {

            //customerSearch

            finder.setShowAll(true);

        }


        return finder;

    }


    public void setShowAll(boolean all) {

        showAll = all;

    }


    public void setName(String value, int findType) {

        if (value == null) return;


        name = value;

        nameType = findType;

    }


    public void setIssued(String value, int findType) {

        if (value == null) return;


        issued = value;

        issuedType = findType;

    }


    public void setSKU(String value, int findType) {

        if (value == null) return;


        sku = value;

        skuType = findType;

    }


    public void setCode(String value, int findType) {

        if (value == null) return;


        code = value;

        codeType = findType;

    }


    public void setValid(String value, int findType) {

        if (value == null) return;


        valid = value;

        validType = findType;

    }


    public String getName() {

        if (isNameSearch())

            return name;

        else

            return kBlank;

    }


    public String getIssued() {

        if (isIssuedSearch())

            return issued;

        else

            return kBlank;

    }


    public String getCode() {

        if (isCodeSearch())

            return code;

        else

            return kBlank;

    }

    public String getSKU() {

        if (isSKUSearch())

            return sku;

        else

            return kBlank;

    }


    public String getValid() {

        if (isValidSearch())

            return valid;

        else

            return kBlank;

    }


    public boolean isNameSearch() {

        if (name == null)

            return false;

        else

            return true;

    }


    public boolean isIssuedSearch() {

        if (issued == null)

            return false;

        else

            return true;

    }


    public boolean isCodeSearch() {

        if (code == null)

            return false;

        else

            return true;

    }

    public boolean isSKUSearch() {

        if (sku == null)

            return false;

        else

            return true;

    }

    public boolean isValidSearch() {

        if (valid == null)

            return false;

        else

            return true;

    }


    private boolean showAll = false;


    public String showResults(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        WebTable table = new WebTable();

        boolean gotSearch = false;

        table.setSQLDefs(tableColumnNames, tableColumnDefs, tableLinkStarts, tableLinkMids, tableLinkEnds, tableRenders, tableFromStmt);


        if (showAll) {

            gotSearch = true;

        } else {

            if (isNameSearch()) {
                gotSearch = true;

                switch (nameType) {

                    case kIsType:

                        table.addCriterium("(coupon_name = \"" + name + "\")");

                        break;

                    case kInType:

                        table.addCriterium("(coupon_name like \"%" + name + "%\")");

                        break;


                    default:

                        table.addCriterium("(coupon_name = \"" + name + "\")");

                        nameType = kIsType;

                }

            }


            if (isIssuedSearch()) {
                gotSearch = true;

                switch (issuedType) {

                    case kIsType:

                        table.addCriterium("(coupon_user = \"" + issued + "\")");

                        break;

                    case kInType:

                        table.addCriterium("(coupon_user like \"%" + issued + "%\")");


                        break;


                    default:

                        table.addCriterium("(coupon_user = \"" + issued + "\")");

                        issuedType = kIsType;

                }

            }


            if (isCodeSearch()) {
                gotSearch = true;

                switch (codeType) {

                    case kIsType:

                        table.addCriterium("(coupon_code = \"" + code + "\")");

                        break;

                    case kInType:

                        table.addCriterium("(coupon_code like \"%" + code + "%\")");


                        break;


                    default:

                        table.addCriterium("(coupon_code = \"" + code + "\")");

                        codeType = kIsType;

                }

            }

            if (isSKUSearch()) {
                gotSearch = true;

                switch (skuType) {

                    case kIsType:

                        table.addCriterium("(sku_value = \"" + sku + "\")");

                        break;

                    case kInType:

                        table.addCriterium("(sku_value like \"%" + sku + "%\")");


                        break;


                    default:

                        table.addCriterium("(sku_value = \"" + sku + "\")");

                        skuType = kIsType;

                }

            }

            if (isValidSearch()) {
                gotSearch = true;

                switch (validType) {

                    case kIsType:

                        table.addCriterium("(exp_date >= \"" + com.owd.core.OWDUtilities.getSQLDateTimeForToday() + "\")");

                        table.addCriterium("(used_count < use_limit OR use_limit < 1)");

                        break;

                    case kInType:

                        table.addCriterium("(exp_date < \"" + com.owd.core.OWDUtilities.getSQLDateTimeForToday() + "\" OR (used_count >= use_limit AND use_limit > 0))");

                        break;


                    default:

                        table.addCriterium("(exp_date >= \"" + com.owd.core.OWDUtilities.getSQLDateTimeForToday() + "\")");

                        table.addCriterium("(used_count < use_limit OR use_limit < 1)");

                        validType = kIsType;

                }

            }

        }

        //if(gotSearch)

        //{

        table.addCriterium("client_fkey=" + ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));



        //}else

        //{

        //	table.addCriterium("client_fkey=0");

        //}



        showAll = false;

        table.setDescription("");

        //check for find criteria



        table.setPageNum(ExtranetServlet.getIntegerParam(req, WebTable.kTableShowPage, 1));

        table.setOrderCol(ExtranetServlet.getIntegerParam(req, WebTable.kTableSortColumn, -1));


        return table.getTable(req, resp);


    }


}
