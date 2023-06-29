package com.owd.jobs;

import com.owd.LogableException;
import com.owd.core.Mailer;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class UPICInsuranceNumbersJob extends OWDStatefulJob {
    private final List<String> recipients = Arrays.asList("owditadmin@owd.com", "ktorevell@owd.com", "invoicing@u-pic.com");

    public static void main(String[] args) {
    }

    public void internalExecute() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate lastMonth = LocalDate.now().minusMonths(1);

            PreparedStatement ps = HibernateSession.getPreparedStatement("EXECUTE getInsuredPackages ?, ?");
            ps.setInt(1, lastMonth.getYear());
            ps.setInt(2, lastMonth.getMonthValue());

            ResultSet rs = ps.executeQuery();
            StringBuffer sb = new StringBuffer();

            while (rs.next()) {
                sb.append(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\r\n");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (String recipient : recipients) {
                Mailer.sendMailWithAttachment("One World Direct Monthly Insured Packages Report " + today.format(formatter), "Attached is the package data for insurance services provided for packages shipped the preceeding month. Please contact 605-845-7172 or 605-845-5540 for assistance with any problems.", recipient, sb.toString().getBytes(), "OWDUPICData" + today.format(formatter) + ".tab", "text/csv");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            new LogableException(ex, ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), String.valueOf(OWD_CLIENT_ID), this.getClass().getSimpleName(), LogableException.errorTypes.INTERNAL);
        } finally {
            HibernateSession.closeSession();
        }
    }
}
