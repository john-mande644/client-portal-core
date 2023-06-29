package com.owd.jobs.clients;

import org.junit.Test;

public class DomestifyJobsTests {

    @Test
    public void order_status_update_job_should_execute() {
        DomestifyDuoplaneOrderStatusUpdateJob job = new DomestifyDuoplaneOrderStatusUpdateJob();
        job.internalExecute();
    }
}
