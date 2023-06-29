package com.owd.jobs.jobobjects.excelreports

import com.owd.core.Mailer

/**
 * Created by stewartbuskirk1 on 4/14/16.
 */
class ExcelReportEmailSender implements ExcelReportSender {

    List<String> emails = new ArrayList<String>();

    public ExcelReportEmailSender ( List<String> emails) {

          if (emails!=null) {
              for (String email:emails) {
                  this.emails.add(email);
              }
          }
    }

    public void sendReports(ReportDefinition report, List<Map<String, Object>> reportData) {

        for (String email : emails) {
            Mailer.sendMailWithMultipleAttachments((report.clientName+" "+report.subject).trim(),
            "Report is attached\n\n",
            email, null, null, "donotreply@owd.com",
            reportData);

        }

    }

}
