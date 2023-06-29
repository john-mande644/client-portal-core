package com.owd.dc.manifest.Manifestxml;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 8, 2010
 * Time: 4:52:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class gssReportList {
    public List<gssReport> reports = new ArrayList<gssReport>();

    public List<gssReport> getReports() {
        return reports;
    }

    public void setReports(List<gssReport> reports) {
        this.reports = reports;
    }

    public static class gssReport{
        private String name;
        private String pdf;
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
        }
    }
    public static XStream getXStream(){
               XStream x = new XStream();
            x.alias("Report",gssReportList.gssReport.class);
        x.alias("OWDGSSReports",gssReportList.class);
        return x;
        
    }
}
