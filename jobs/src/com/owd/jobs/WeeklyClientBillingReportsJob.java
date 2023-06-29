package com.owd.jobs;

import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;
import com.owd.jobs.jobobjects.excelreports.WeeklyClientBillingReportDefinition;
import com.owd.jobs.jobobjects.excelreports.WeeklyClientBillingReportParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeeklyClientBillingReportsJob extends OWDStatefulJob {
    public static void main(String[] args) {

    }

    public void internalExecute() {
        List<WeeklyClientBillingReportParameters> clients = getSubscriberList();

        for (WeeklyClientBillingReportParameters client : clients) {
            ExcelUtils.deliverReports(Arrays.asList(new WeeklyClientBillingReportDefinition(client.clientId, client.clientName)), Arrays.asList(new ExcelReportEmailSender(client.emailList)));
        }
    }

    private List<WeeklyClientBillingReportParameters> getSubscriberList() {
        List<WeeklyClientBillingReportParameters> subscriberList = new ArrayList();

        subscriberList.add(new WeeklyClientBillingReportParameters(652, "Dr. Livingood", Arrays.asList("tawna@owd.com", "accounting@owd.com", "mrhoades@owd.com")));
        subscriberList.add(new WeeklyClientBillingReportParameters(694, "Recontour", Arrays.asList("tlightle@owd.com", "accounting@owd.com", "mrhoades@owd.com")));

        return subscriberList;
    }
}
