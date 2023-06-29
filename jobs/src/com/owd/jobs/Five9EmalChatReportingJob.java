package com.owd.jobs;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.jobobjects.five9.owd.Five9API;
import com.opencsv.CSVReaderHeaderAware;
import org.apache.pdfbox.pdmodel.common.COSDictionaryMap;
import org.hibernate.Query;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by danny on 8/7/2019.
 */
public class Five9EmalChatReportingJob extends OWDStatefulJob{

    public static void main(String args[]){

       /* try{

            DateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
            Date date = sdf.parse("Fri, 9 Aug 2019 11:49:31");
            System.out.println(date);

            DateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            Date date2 = sdf2.parse("9 Aug 2019 11:49:31");
            System.out.println(date2);


        }catch (Exception e){
            e.printStackTrace();
        }*/
        run();
       /* try {
            Date d = getDateFromTime("00:00:30");
           System.out.println((d.getTime() - 21600000)/1000);
            System.out.println(d);

        }catch (Exception e){
            e.printStackTrace();
        }
*/

    }


    public void internalExecute(){
        Map<String,String> campaigns = loadCampaignMap();

        try {
            String s = Five9API.getCurrentEmailChat(3);

            insertRecordsIntoDatabase(s,campaigns);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void insertRecordsIntoDatabase(String data, Map<String,String> campaigns) throws Exception{
        CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new StringReader(data));
        int total = reader.readAll().size();
        reader = new CSVReaderHeaderAware(new StringReader(data));
        System.out.println(total);
        for(int i = 0;i<total;i++){
            Map<String,String> row = reader.readMap();
            System.out.println(i+": "+row);
            insertRecordIntoDatabase(row,campaigns);

        }
    }


    public static void insertRecordIntoDatabase(Map<String,String> data, Map<String,String> campaigns){
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(insertSql);
            if(campaigns.containsKey(data.get("CAMPAIGN"))){
                q.setParameter("client_id",campaigns.get(data.get("CAMPAIGN")));
            }else{
                q.setParameter("client_id",0);
            }

            if(data.get("MEDIA TYPE").equalsIgnoreCase("EMAIL")){
                if(data.get("TO ADDRESS").contains("@")||getSecondsFromTimeString(data.get("RESPONSE TIME"))>0){
                    q.setParameter("outbound_interaction",1);
                }else{
                    q.setParameter("outbound_interaction",0);
                }
            }else{
                q.setParameter("outbound_interaction",0);
            }
            q.setTimestamp("TIMESTAMP", getDateFromTimestamp(data.get("TIMESTAMP")));
            q.setParameter("AGENT", data.get("AGENT"));
            q.setParameter("AGENT_NAME", data.get("AGENT NAME"));
            q.setParameter("ACCOUNT_NAME", data.get("ACCOUNT NAME"));
            q.setParameter("CAMPAIGN", data.get("CAMPAIGN"));
            q.setParameter("CUSTOMER_ID", data.get("CUSTOMER ID"));
            q.setParameter("CUSTOMER_NAME", data.get("CUSTOMER NAME"));
            q.setParameter("DISPOSITION", data.get("DISPOSITION"));
            q.setParameter("FROM_ADDRESS", data.get("FROM ADDRESS"));
            q.setParameter("MEDIA_SUBTYPE", data.get("MEDIA SUBTYPE"));
            q.setParameter("MEDIA_TYPE", data.get("MEDIA TYPE"));
            q.setParameter("SESSION_GUID", data.get("SESSION GUID"));
            q.setParameter("SKILL", data.get("SKILL"));
            q.setParameter("TO_ADDRESS", data.get("TO ADDRESS"));
            q.setParameter("TRANSCRIPT_LINK", data.get("TRANSCRIPT LINK"));
            q.setParameter("WORK_ITEM_STATUS", data.get("WORK ITEM STATUS"));
            q.setParameter("AFTER_CHAT_WORK", data.get("AFTER CHAT WORK"));
            q.setParameter("CHAT_TIME", data.get("CHAT TIME"));
            q.setParameter("EMAIL_QUEUE_TIME", data.get("EMAIL QUEUE TIME"));
            q.setParameter("EMAIL_TIME", data.get("EMAIL TIME"));
            q.setParameter("FCR_TIME", data.get("FCR TIME"));
            q.setParameter("HANDLE_TIME", data.get("HANDLE TIME"));
            q.setParameter("INTERACTION_TIME", data.get("INTERACTION TIME"));

            q.setParameter("PROGRESS_TIME", data.get("PROGRESS TIME"));
            q.setParameter("QUEUE_TIME", data.get("QUEUE TIME"));
            q.setParameter("RESOLUTION_TIME", data.get("RESOLUTION TIME"));
            q.setParameter("RESPONSE_TIME", data.get("RESPONSE TIME"));
            q.setParameter("TIME_TILL_ACCEPT", data.get("TIME TILL ACCEPT"));

            q.setParameter("AFTER_CHAT_WORK_SECONDS", getSecondsFromTimeString(data.get("AFTER CHAT WORK")));
            q.setParameter("CHAT_TIME_SECONDS", getSecondsFromTimeString(data.get("CHAT TIME")));
            q.setParameter("EMAIL_QUEUE_TIME_SECONDS", getSecondsFromTimeString(data.get("EMAIL QUEUE TIME")));
            q.setParameter("EMAIL_TIME_SECONDS", getSecondsFromTimeString(data.get("EMAIL TIME")));
            q.setParameter("FCR_TIME_SECONDS", getSecondsFromTimeString(data.get("FCR TIME")));
            q.setParameter("HANDLE_TIME_SECONDS", getSecondsFromTimeString(data.get("HANDLE TIME")));
            q.setParameter("INTERACTION_TIME_SECONDS", getSecondsFromTimeString(data.get("INTERACTION TIME")));

            q.setParameter("PROGRESS_TIME_SECONDS", getSecondsFromTimeString(data.get("PROGRESS TIME")));
            q.setParameter("QUEUE_TIME_SECONDS", getSecondsFromTimeString(data.get("QUEUE TIME")));
            q.setParameter("RESOLUTION_TIME_SECONDS", getSecondsFromTimeString(data.get("RESOLUTION TIME")));
            q.setParameter("RESPONSE_TIME_SECONDS", getSecondsFromTimeString(data.get("RESPONSE TIME")));
            q.setParameter("TIME_TILL_ACCEPT_SECONDS", getSecondsFromTimeString(data.get("TIME TILL ACCEPT")));



            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());



        }catch (Exception e){


            if(!e.getCause().getMessage().contains("UNIQUE")) {
                e.printStackTrace();
            }
        }





    }
    private static Date getDateFromTimestamp(String time) throws Exception{



        DateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        Date date = sdf.parse(time);
        return date;
    }
    private static long getSecondsFromTimeString(String time)throws Exception{
        Date d = getDateFromTime(time);
        return ((d.getTime() - 21600000)/1000);

    }
    private static Date getDateFromTime(String time) throws Exception{


        if(time.length()==0){
            time = "00:00:00";
        }
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date = sdf.parse(time);
        return date;
    }

    public static Map<String,String> loadCampaignMap(){
        Map<String,String> campaign = new HashMap<>();
        try{
            String sql = "select campaign, client_id from cc_email_campaigns";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            for(Object row:l){
                Object[] data = (Object[]) row;
                campaign.put(data[0].toString(),data[1].toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        return campaign;
    }
    private static String insertSql = "INSERT\n" +
            "INTO\n" +
            "    cc_email_chat_interactions\n" +
            "    (\n" +
            "        \n" +
            "        client_id,\n" +
            "        outbound_interaction,\n" +
            "        TIMESTAMP,\n" +
            "        AGENT,\n" +
            "        AGENT_NAME,\n" +
            "        ACCOUNT_NAME,\n" +
            "        CAMPAIGN,\n" +
            "        CUSTOMER_ID,\n" +
            "        CUSTOMER_NAME,\n" +
            "        DISPOSITION,\n" +
            "        FROM_ADDRESS,\n" +
            "        MEDIA_SUBTYPE,\n" +
            "        MEDIA_TYPE,\n" +
            "        SESSION_GUID,\n" +
            "        SKILL,\n" +
            "        TO_ADDRESS,\n" +
            "        TRANSCRIPT_LINK,\n" +
            "        WORK_ITEM_STATUS,\n" +
            "        AFTER_CHAT_WORK,\n" +
            "        CHAT_TIME,\n" +
            "        EMAIL_QUEUE_TIME,\n" +
            "        EMAIL_TIME,\n" +
            "        FCR_TIME,\n" +
            "        HANDLE_TIME,\n" +
            "        INTERACTION_TIME,\n" +

            "        PROGRESS_TIME,\n" +
            "        QUEUE_TIME,\n" +
            "        RESOLUTION_TIME,\n" +
            "        RESPONSE_TIME,\n" +
            "        TIME_TILL_ACCEPT," +
            "AFTER_CHAT_WORK_SECONDS,\n" +
            "CHAT_TIME_SECONDS,\n" +
            "EMAIL_QUEUE_TIME_SECONDS,\n" +
            "EMAIL_TIME_SECONDS,\n" +
            "FCR_TIME_SECONDS,\n" +
            "HANDLE_TIME_SECONDS,\n" +
            "INTERACTION_TIME_SECONDS,\n" +
            "PROGRESS_TIME_SECONDS,\n" +
            "QUEUE_TIME_SECONDS,\n" +
            "RESOLUTION_TIME_SECONDS,\n" +
            "RESPONSE_TIME_SECONDS," +
            "TIME_TILL_ACCEPT_SECONDS\n" +
            "    )\n" +
            "    VALUES\n" +
            "    (\n" +
            "        \n" +
            "        :client_id,\n" +
            ":outbound_interaction,\n" +
            ":TIMESTAMP,\n" +
            ":AGENT,\n" +
            ":AGENT_NAME,\n" +
            ":ACCOUNT_NAME,\n" +
            ":CAMPAIGN,\n" +
            ":CUSTOMER_ID,\n" +
            ":CUSTOMER_NAME,\n" +
            ":DISPOSITION,\n" +
            ":FROM_ADDRESS,\n" +
            ":MEDIA_SUBTYPE,\n" +
            ":MEDIA_TYPE,\n" +
            ":SESSION_GUID,\n" +
            ":SKILL,\n" +
            ":TO_ADDRESS,\n" +
            ":TRANSCRIPT_LINK,\n" +
            ":WORK_ITEM_STATUS,\n" +
            ":AFTER_CHAT_WORK,\n" +
            ":CHAT_TIME,\n" +
            ":EMAIL_QUEUE_TIME,\n" +
            ":EMAIL_TIME,\n" +
            ":FCR_TIME,\n" +
            ":HANDLE_TIME,\n" +
            ":INTERACTION_TIME,\n" +

            ":PROGRESS_TIME,\n" +
            ":QUEUE_TIME,\n" +
            ":RESOLUTION_TIME,\n" +
            ":RESPONSE_TIME,\n" +
            ":TIME_TILL_ACCEPT,\n" +
            ":AFTER_CHAT_WORK_SECONDS,\n" +
            ":CHAT_TIME_SECONDS,\n" +
            ":EMAIL_QUEUE_TIME_SECONDS,\n" +
            ":EMAIL_TIME_SECONDS,\n" +
            ":FCR_TIME_SECONDS,\n" +
            ":HANDLE_TIME_SECONDS,\n" +
            ":INTERACTION_TIME_SECONDS,\n" +
            ":PROGRESS_TIME_SECONDS,\n" +
            ":QUEUE_TIME_SECONDS,\n" +
            ":RESOLUTION_TIME_SECONDS,\n" +
            ":RESPONSE_TIME_SECONDS," +
            ":TIME_TILL_ACCEPT_SECONDS\n" +
            "    );";
}
