package com.owd.jobs.jobobjects.excelreports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by stewartbuskirk1 on 2/11/16.
 */
public class ReportDefinition {
    public String periodTitle = "";
    public String subject;
    public String clientName;
    public List<SheetDefinition> sheets = new ArrayList<>();
    public String clientId = "";


    public ReportDefinition() {

    }


}
