package com.owd.jobs.clients;

import org.junit.Test;

import java.io.ByteArrayInputStream;

public class LyftJobsTests {
    private final static String ftpHost = "ftp.owd.com";
    private final static String ftpUser = "lyftreports";
    private final static String ftpPass = "lyftFTP!!";
    private final static String fieldSeparator = ",";
    private final static String lineSeparator = System.getProperty("line.separator");

    @Test
    public void lyft_inventory_job_should_load_file() {
        LyftDailyShippedSpreadsheetJob job = new LyftDailyShippedSpreadsheetJob();
        job.internalExecute();
    }
}
