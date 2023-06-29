package com.owd.jobs.jobobjects.excelreports

/**
 * Created by stewartbuskirk1 on 4/14/16.
 */
interface ExcelReportSender {

    public void sendReports(ReportDefinition report, List<Map<String, Object>> reportData);

}
