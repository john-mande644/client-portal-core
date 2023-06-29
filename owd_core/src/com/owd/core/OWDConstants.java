package com.owd.core;

public class OWDConstants {

    public static final String SMTPServer = "172.16.1.131";
    public static final String POP3Server = "mail.owd.com";

    public static final String DefaultReturnAddress = "donotreply@owd.com";
    public static final String DefaultToAddress = "owditadmin@owd.com";
    public static final String kAdminEmail = "owditadmin@owd.com";

    public static final String ksendMailQueueName = "javax.jms.sendMailQueue";
    public static final String ksendMailFactoryName = "jms.connection.sendMailFactory";

    public static final String kunsendableMailQueueName = "javax.jms.unsendableMailQueue";
    public static final String kunsendableMailFactoryName = "jms.connection.unsendableMailFactory";

    public static final String korderImportQueueName = "javax.jms.orderImportQueue";
    public static final String korderImportFactoryName = "jms.connection.orderImportFactory";

    public static final String kReportQueueName = "javax.jms.ReportQueue";
    public static final String kReportFactoryName = "jms.connection.ReportFactory";

    public static final String korderSweeperQueueName = "javax.jms.SweeperQueue";
    public static final String kSweeperFactoryName = "jms.connection.SweeperFactory";

    //OWD_Database.hibernate.OwdOrderAuto status field values
    public static final int kSubscriptionStatusUndetermined = 0;
    public static final int kSubscriptionStatusActive = 1;
    public static final int kSubscriptionStatusNeedsUpdate = 2;
    public static final int kSubscriptionStatusCancelled = 3;

}
