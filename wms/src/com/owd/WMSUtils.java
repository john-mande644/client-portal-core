package com.owd;


import com.opensymphony.xwork2.ActionContext;
import com.owd.core.managers.JasperReportPrintManager;
import com.owd.dc.setup.selectList;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 8/6/14
 * Time: 6:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class WMSUtils {



    public static String getOrderNumberFromString(String orderNum){
      String result = orderNum;
        if(orderNum.startsWith("p")){
            Pattern pat = Pattern.compile("\\*(\\d*)R*\\d*\\*");
            Matcher match = pat.matcher(orderNum);
            if(match.find()){
                System.out.println(match.group(1));
                result = match.group(1);
            }

        }else{
            if(orderNum.contains("R")){
                result = orderNum.substring(0,orderNum.indexOf("R"));
            }
        }



         return result;
    }

    public static List<selectList> getSelectListActiveFacilities() throws Exception{
        List<selectList> facs = new ArrayList<selectList>();
        String sql = "select loc_name, loc_code from owd_facilities where is_active = 1 and is_public = 1";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        List results = q.list();
        if (results.size()>0){
           for(Object row:results){
               Object[] data = (Object[]) row;
               selectList sl = new selectList();
               sl.setDisplay(data[0].toString());
               sl.setAction(data[1].toString());
               facs.add(sl);
           }


        } else{
            throw new Exception("Unable to find any active facilites, maybe everyone is sleeping today");
        }




        return facs;
    }

    /**
     *
     * @param userId timeclock Id
     * @param request current HttpServletRequest
     * @return String of facilty code. Looks at database for users that are allowed to pull stored location. If found returns. If null value sets the value using the default facilty methods
     *
     */
     public static String getFacilityFromLoginInDatbase(String userId,HttpServletRequest request){
         //Get the saved location from the datbase for the userID supplies. If it is null grab the default facility and store it
         //must accept fail because record may not exist yet. Still return default.
        try{
          String sql = "select location from handheld_setup where loginId = :loginId and canTeleport = 1";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("loginId",userId);
            List results = q.list();
            if(results.size()>0){
                //They exist so lets see what we have
                if(null!= results.get(0)){
                    return results.get(0).toString();
                } else{
                  //null grab default and save
                   System.out.println("Null in database, default and save");
                     String facility = getFacilityFromRequest(request);
                    String sql2 = "update handheld_setup set location = :location where loginId = :loginId";
                    Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
                    qq.setParameter("location",facility);
                    qq.setParameter("loginId",userId);
                    System.out.println(qq.executeUpdate());
                    HibUtils.commit(HibernateSession.currentSession());
                    return facility;
                }
            }



        }catch(Exception e){
           e.printStackTrace();
        }


         return getFacilityFromRequest(request);
     }
    /**
     *
     * @param request current HttpServletRequest
     * @return String of facility. Gets session attribute owd_current_location, then owd_default_location, then defaults to DC1 if neither exist
     */
    public static String getFacilityFromRequest(HttpServletRequest request){
        String facility = "DC1";
                    try{
                     if(request.getSession(true).getAttribute("owd_current_location")!=null){

                         facility =  request.getSession(true).getAttribute("owd_current_location").toString();
                         System.out.println("Facility Obtained from Request: "+facility);
                     }
                        else{
                           facility = getDefaultFacilityFromRequest(request);
                        }
                    }catch (Exception e){
                        System.out.println("Error getting facility from request: using DC1");
                    }

        return facility;
    }


    /**
     *
     * @param request current Http servlet request
     * @return  String of default facility set in session. If none exists DC1 is used
     */
    public static String getDefaultFacilityFromRequest(HttpServletRequest request){
       String facility = "DC1";
               try{
               if(request.getSession(true).getAttribute("owd_default_location")!=null){
                   facility=request.getSession(true).getAttribute("owd_default_location").toString();
               }
               } catch (Exception e){
                   System.out.println("Error geting default facility from request: using DC1");
               }

        return facility;
    }
    /**
     *
     * @param aContext Current ActionContext from Struts
     * @return String of default facility set In the Session owd_default_location returns DC1 if nothing is found
     */

    public static String getDefaultFacilityFromContext(ActionContext aContext){
        String facility = "DC1";

                try{
                    Map sess = aContext.getSession();

                    if(sess.containsKey("owd_default_location")){
                        facility= sess.get("owd_default_location").toString();
                    }  else{
                        System.out.println("Session does not contain default. That could be a problem");
                    }



                } catch(Exception e){
                   System.out.println("Bad facility fill from action context: using default DC1");
                }


        return facility;
    }

    /**
     *
     * @param aContext Current ActionContext
     * @return String returns facility stored in session. Lookup path, owd_current_location, then getDefaultFacilityFromContext
     */
    public static String getFacilityFromContext(ActionContext aContext){
        String facility = "DC1";

        try{
            Map sess = aContext.getSession();
            if(sess.containsKey("owd_current_location")){
             facility = sess.get("owd_current_location").toString();
            } else{
            facility = getDefaultFacilityFromContext(aContext);

            }



        } catch(Exception e){
            System.out.println("Bad facility fill from action context: using default DC1");
        }


        return facility;
    }


    public static void main(String args[]){

        try{
            byte[] data = JasperReportPrintManager.getCommInvoicePdf("16460400");

            System.out.println("PDF size:"+data.length);
            //System.out.println(getFacilityFromLoginInDatbase("328", request));
        //System.out.println(getDate());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * @param s String you need parsed. Can contain comma, return
     * @return  List of Strings
     */
    public static List<String> splitStringIntoList(String s){
          s= s.replaceAll("\n",",");

                return Arrays.asList(s.split("\\s*,\\s*"));
    }

    public static String getDate() throws Exception{
        String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
       return sdf.format(Calendar.getInstance().getTime());
    }
    public static String getDateAMPM() throws Exception{
        String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss a";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        return sdf.format(Calendar.getInstance().getTime());
    }
    public static String getDateAMPMAddSeconds(int seconds) throws Exception{
        String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss a";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, seconds);
        return sdf.format(c.getTime());
    }

}
