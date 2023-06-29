package com.owd.jobs

import com.owd.core.Mailer
import com.owd.core.managers.SlackManager
import com.owd.hibernate.HibernateClientReportsSession
import com.owd.hibernate.HibernateSession
import groovy.time.TimeCategory

import java.math.MathContext
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.text.DecimalFormat
import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 10/23/12
 * Time: 8:32 AM
 * To change this template use File | Settings | File Templates.
 */
class PayrollStatusReportingJob extends OWDStatefulJob {

    public static void main(String... args)
    {
        run();
    }

    @Override
    void internalExecute() {
        SlackManager slack = new SlackManager()

        Map<String,String> dmap = new LinkedHashMap<String,String>()
        DecimalFormat df = new DecimalFormat('$###,###,000')
        long currentTotal = 0;

        def payrollPeriodReferenceStartCal = new GregorianCalendar(2015, Calendar.DECEMBER, 5)

        def now = new Date()



        int daysSinceReference = TimeCategory.minus(now,payrollPeriodReferenceStartCal.time).days
        int daysInCurrentPeriod = (daysSinceReference%14)
        Date currPeriodStart = now-daysInCurrentPeriod
        Date currPeriodEnd = currPeriodStart+daysInCurrentPeriod-1
        Date prevPeriodStart = currPeriodStart-14
        Date prevPeriodEnd = prevPeriodStart+daysInCurrentPeriod-1

        println "Days in Current Period: "+daysInCurrentPeriod

        println "Current period "+currPeriodStart.format("yyyy-MM-dd")+" - "+currPeriodEnd.format("yyyy-MM-dd")
        println "Previous period start"+prevPeriodStart.format("yyyy-MM-dd")
        println "Previous period end"+prevPeriodEnd.format("yyyy-MM-dd")


        ResultSet rs = HibernateClientReportsSession.getResultSet("select dept, round(sum([total payroll]),0)\n" +
                "from  vw_payroll_details where workdate>='"+currPeriodStart.format("yyyy-MM-dd")+"' and workdate<='"+currPeriodEnd.format("yyyy-MM-dd")+"'\n" +
                "and employee not like '% CA %' and employee not like 'OH %'\n" +
                "group by dept\n" +
                "order by round(sum([total payroll]),0) desc")
        while(rs.next())
        {
            dmap.put(rs.getString(1),df.format(rs.getLong(2)))
            currentTotal +=rs.getLong(2)
        }
        dmap.put("Maria Pineda",df.format(992))
        currentTotal+=992



        rs.close()
        Map<String,String> dmapPrev = new LinkedHashMap<String,String>()
        long prevTotal = 0

        rs = HibernateClientReportsSession.getResultSet("select dept, round(sum([total payroll]),0)\n" +
                "from  vw_payroll_details where workdate>='"+prevPeriodStart.format("yyyy-MM-dd")+"' and workdate<='"+(prevPeriodEnd).format("yyyy-MM-dd")+"'\n" +
                "and employee not like '% CA %' and employee not like 'OH %'\n" +
                "group by dept\n" +
                "order by round(sum([total payroll]),0) desc")
        while(rs.next())
        {
            dmapPrev.put(rs.getString(1),df.format(rs.getLong(2)))
            prevTotal +=rs.getLong(2)
        }
        dmapPrev.put("Maria Pineda",df.format(992))
        prevTotal+=992

        println "totals: "+currentTotal+ ":"+prevTotal

        def predictedPayroll = 0
        def prevAllTotal = 0

        rs = HibernateClientReportsSession.getResultSet("select round(sum([total payroll]),0)\n" +
                "from  vw_payroll_details where workdate>='"+prevPeriodStart.format("yyyy-MM-dd")+"' and workdate<='"+(prevPeriodStart+13).format("yyyy-MM-dd")+"'\n" +
                "and employee not like '% CA %' and employee not like 'OH %'")
        while(rs.next())
        {
            prevAllTotal +=rs.getLong(1)
        }
        prevAllTotal+=992

        rs.close()

        int pctDiff = (100*((currentTotal-prevTotal)/prevTotal)).round(new MathContext(0)).toInteger()


        predictedPayroll = prevAllTotal* (1.00+(pctDiff/100))
        println "predictedPayroll="+predictedPayroll

        slack.postPayrollReport("Current Payroll Status - Period Ending "+(currPeriodStart+13).format("yyyy-MM-dd"),"*Total: "+df.format(currentTotal)+"* ("+(pctDiff<0?(-1*pctDiff)+"% less":(pctDiff)+"% more")+" than previous period)\r\n*Predicted Total Payroll: "+df.format(predictedPayroll)+'*',dmap,dmapPrev)

    }

    static def daysBetween(def startDate, def endDate) {
        use(TimeCategory) {
            def duration = endDate - startDate
            return duration.days
        }
    }

}
