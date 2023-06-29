package com.owd.jobs.jobobjects.excelreports

import com.owd.core.managers.SlackManager

/**
 * Created by stewartbuskirk1 on 4/14/16.
 */
class ExcelReportSlackSender implements ExcelReportSender {

    List<String> channelCodes = new ArrayList<String>();

    public ExcelReportSlackSender(List<String> channelCodes) {

        if (channelCodes != null) {
            for (String channelCode : channelCodes) {
                this.channelCodes.add(channelCode);
            }
        }
    }

    public void sendReports(ReportDefinition report, List<Map<String, Object>> reportData) {

        for (String channel : channelCodes) {
            for (Map<String, Object> data : reportData) {
                SlackManager.postFileToChannel("" + data.get("filename"), (byte[]) (data.get("bytes")), channel, (report.clientName + " " + report.subject).trim());
            }

        }
    }
}