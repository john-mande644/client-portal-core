package com.owd.jobs.jobobjects.excelreports;

import java.util.List;

public class WeeklyClientBillingReportParameters {
    public int clientId;
    public String clientName;
    public List<String> emailList;

    public WeeklyClientBillingReportParameters(int clientId, String clientName, List<String> emailList) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.emailList = emailList;
    }
}
