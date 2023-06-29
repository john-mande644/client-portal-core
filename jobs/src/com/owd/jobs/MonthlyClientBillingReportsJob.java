package com.owd.jobs;

import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;
import com.owd.jobs.jobobjects.excelreports.MonthlyClientBillingReportDefinition;
import com.owd.jobs.jobobjects.excelreports.WeeklyClientBillingReportParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonthlyClientBillingReportsJob extends OWDStatefulJob {
    public void internalExecute() {
        List<WeeklyClientBillingReportParameters> clients = getSubscriberList();

        for (WeeklyClientBillingReportParameters client : clients) {
            ExcelUtils.deliverReports(Arrays.asList(new MonthlyClientBillingReportDefinition(client.clientId, client.clientName)), Arrays.asList(new ExcelReportEmailSender(client.emailList)));
        }
    }

    private List<WeeklyClientBillingReportParameters> getSubscriberList() {
        List<WeeklyClientBillingReportParameters> subscriberList = new ArrayList();

        subscriberList.add(new WeeklyClientBillingReportParameters(680, "Blue Tees Golf", Arrays.asList("accounting@owd.com", "tlightle@owd.com","mrhoades@owd.com", "dlowry@owd.com")));
        subscriberList.add(new WeeklyClientBillingReportParameters(652, "Dr. Livingood", Arrays.asList("accounting@owd.com", "tlightle@owd.com","mrhoades@owd.com", "dlowry@owd.com")));
        subscriberList.add(new WeeklyClientBillingReportParameters(667, "ROVR", Arrays.asList("accounting@owd.com", "mrhoades@owd.com", "dlowry@owd.com")));
        subscriberList.add(new WeeklyClientBillingReportParameters(722, "Bread Beauty", Arrays.asList("accounting@owd.com", "tawna@owd.com", "tlightle@owd.com", "mrhoades@owd.com", "dlowry@owd.com")));

        return subscriberList;
    }
}
