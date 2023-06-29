package com.owd.dc.timeclock;

import org.apache.struts.action.ActionForm;
import com.owd.dc.setup.selectList;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 13, 2008
 * Time: 4:00:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class timeclockForm extends ActionForm {
    private String timeClockId;
    private String jobCode;
    private List<selectList> codeList;


    public String getTimeClockId() {
        return timeClockId;
    }

    public void setTimeClockId(String timeClockId) {
        this.timeClockId = timeClockId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public List<selectList> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<selectList> codeList) {
        this.codeList = codeList;
    }
}
