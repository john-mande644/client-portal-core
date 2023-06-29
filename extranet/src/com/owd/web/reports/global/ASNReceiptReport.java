package com.owd.web.reports.global;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.web.reports.ReportDefinition;
import com.owd.web.reports.ReportParameter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 3, 2004
 * Time: 9:46:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class ASNReceiptReport extends ReportDefinition {
private final static Logger log =  LogManager.getLogger();

    List paramList = new ArrayList();
    String prepareSQL = "select client_ref as 'Client ASN Ref',asn.id as 'OWD ASN ID',receive_by as 'Receiver',has_blind as 'Blind',is_asn_found as 'Conforming Receipt',is_pack_slip_found as 'Packing Slip Present',\n" +
            "receive.carton_count as 'Cartons',receive.pallet_count as 'Pallets',end_timestamp as 'Completed',is_posted as 'Inventory Posted',convert(varchar(512),receive.notes) as 'Notes', convert(money,driver) as 'Billed Hours'\n" +
            " from receive (NOLOCK) join asn (NOLOCK) on asn.id=asn_fkey join owd_receive owdr on 'OWDRCV-'+convert(varchar,receive.id)=owdr.transaction_num "
            + " where end_timestamp >= ? and end_timestamp < dateadd(day,1,?)  and receive.client_fkey=? "
            + "  order by end_timestamp asc";


    public ASNReceiptReport() {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar now = Calendar.getInstance();

        //init parameter list
        ReportParameter param = new ReportParameter();
        param.setDisplayName("Start Date");
        param.setDescription("<B>Required</B> Date report period begins. Date is compared to date the receive was completed for this report.");
        param.setDefaultValue(formatter.format(now.getTime()));
        param.setCurrentValue(formatter.format(now.getTime()));
        param.setFormValueName("start_date");
        param.setParamDataType(ReportParameter.kParamTypeDate);
        paramList.add(param);


        param = new ReportParameter();
        param.setDisplayName("End Date");
        param.setDefaultValue(formatter.format(now.getTime()));
        param.setCurrentValue("");
        param.setFormValueName("end_date");
        param.setParamDataType(ReportParameter.kParamTypeDate);

        param.setDescription("Date report period ends (leave blank or use same value as start date for one day report)");
        paramList.add(param);

    }

    //customized methods
    public List getParameters() {
        return paramList;

    }

    public String getPrepareSQL() {
        return prepareSQL;

    }


}
