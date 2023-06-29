package com.owd.core.managers

import com.owd.LogableException
import com.owd.core.OWDUtilities
import com.owd.hibernate.HibernateClientReportsSession
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdClient
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ByteArrayBody
import org.apache.http.entity.mime.content.StringBody
import org.codehaus.groovy.runtime.MethodClosure

import java.math.MathContext
import java.sql.ResultSet
import java.text.DecimalFormat

/**
 * Created by stewartbuskirk1 on 7/2/15.
 */
class SlackManager {

     static  String errorMessageUrl = "https://hooks.slack.com/services/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"

    static  String managementUrl = "https://hooks.slack.com/services/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"

    static  String apiToken = "xoxp-2375640259-43327794164-48557028855-5f8e43aab0";

    static String itSymphonyCase = "https://hooks.slack.com/services/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";


    static def daysBetween(def startDate, def endDate) {
        use(groovy.time.TimeCategory) {
            def duration = endDate - startDate
            return duration.days
        }
    }

    public static void main(String[] args) {
          SlackManager api = new SlackManager();
       // api.postFileToChannel("test.txt", "123456".bytes, "C10CULRFT", "testing") ;
        api.notifySymphonyCase("411616","Just a test peeps");

    }


    protected static RESTClient getEndpoint(String rootUrl)
    {

        return new RESTClient(rootUrl);
    }

    public static void postFileToChannel(String fileName, byte[] data, String channelCode, String message) {
        RESTClient endpoint = getEndpoint("https://slack.com/api/files.upload")

      //  endpoint.encoder.putAt("multipart/form-data", new MethodClosure(this, 'encodeMultiPart'))

//here is the method for the encoder added above

//here's how MultipartBody class looks:

        MultipartEntity multipart = new MultipartEntity()
        multipart.addPart("file", new ByteArrayBody(data, fileName))
        multipart.addPart("token", new StringBody(apiToken))
        multipart.addPart("filename", new StringBody(fileName))
        multipart.addPart("initial_comment", new StringBody(message))
        multipart.addPart("channels", new StringBody(channelCode))

        def resp = endpoint.request(Method.POST ) { req ->

            // contentType: 'application/json',
            //  body: json.toString()   ,
            req.entity = multipart
        }






        assert (resp.status == 200 || resp.status == 201)
    }
    public static void notifySlack(LogableException error) {
        RESTClient endpoint = getEndpoint(errorMessageUrl)

        def json = new JsonBuilder()

        String clientname = error.getClientId()
        try{
             OwdClient client = HibernateSession.currentSession().load(OwdClient.class,Integer.parseInt(clientname))
            if(client!=null)
            {
                clientname = client.getCompanyName()
            }
        }catch(Exception ex){}

        json {
            attachments ([{
                              fallback "<http://jobs.owd.com:8085/jobs/ordererrorlist.jsp|"+error.getErrorMessage()+">"
                              //  pretext "*[Urgent]*: <http://url_to_task|Test out Slack message attachments>"
                              title "Jobs Error - "+error.getErrorMessage()
                              title_link "http://jobs.owd.com:8085/jobs/ordererrorlist.jsp"

                              text "```"+(error.getErrorInfo()?error.getErrorInfo():OWDUtilities.getGroovyComparableStackTraceAsString(error))+"```"

                              color "danger"
                              mrkdwn_in "text", "fields"
                              fields([{
                                          title "Type"
                                          value error.getErrorType()
                                          'short' "true"
                                      },
                                      {
                                          title "Order or Ref"
                                          value error.getOrderNum()
                                          'short' "true"
                                      }  ,
                                      {
                                          title "Client"
                                          value clientname
                                          'short' "true"
                                      },

                                      {
                                          title "Request"
                                          value error.getRequestType()
                                          'short' "true"
                                      }
                              ])
                          }])

        }


        println json.toPrettyString()
        groovyx.net.http.HttpResponseDecorator resp = endpoint.post(
                requestContentType: 'application/json',
                // contentType: 'application/json',
                body: json.toString()
        )
        // println resp.getEntity().
        assert (resp.status == 200 || resp.status == 201)
    }

    public static void testMessage(String message) {
        RESTClient endpoint = getEndpoint(managementUrl)

        def json = new JsonBuilder()


        json {
            attachments ([{
                              // fallback "<http://internal.owd.com:8085/jobs/ordererrorlist.jsp|"+error.getErrorMessage()+">"
                              //  pretext "*[Urgent]*: <http://url_to_task|Test out Slack message attachments>"
                              title "Current Payroll Status - Period Ending 2015-01-04"
                              //  title_link "http://internal.owd.com:8085/jobs/ordererrorlist.jsp"

                              text "25% less than previous period"

                              color "warning"
                              mrkdwn_in "text", "fields"
                                fields([{
                                            title "Notes"
                                            value "This is much easier than I thought it would be."
                                             'short' "false"
                                        },
                                        {
                                            title "Section"
                                            value "Some stuff."
                                            'short' "true"
                                        }  ,
                                        {
                                            title "Section"
                                            value "Some stuff."
                                            'short' "true"
                                        },
                                        {
                                            title "Section"
                                            value "Some stuff."
                                            'short' "true"
                                        },
                                        {
                                            title "Section"
                                            value "Some stuff."
                                            'short' "true"
                                        },
                                        {
                                            title "Section"
                                            value "Some stuff."
                                            'short' "true"
                                        }
                                ])
                            }])

            }


        println json.toPrettyString()
        groovyx.net.http.HttpResponseDecorator resp = endpoint.post(
                requestContentType: 'application/json',
               // contentType: 'application/json',
                body: json.toString()
        )
        // println resp.getEntity().
        assert (resp.status == 200 || resp.status == 201)
    }

    public static void postPayrollReport(String headline, String body, Map<String, String> deptValues,Map<String, String> prevDeptValues) {
        RESTClient endpoint = getEndpoint(managementUrl)

        def json = new JsonBuilder()

        DecimalFormat df = new DecimalFormat('$###,###,000')

        json {
            attachments ([{
                              // fallback "<http://internal.owd.com:8085/jobs/ordererrorlist.jsp|"+error.getErrorMessage()+">"
                              //  pretext "*[Urgent]*: <http://url_to_task|Test out Slack message attachments>"
                              title headline
                              //  title_link "http://internal.owd.com:8085/jobs/ordererrorlist.jsp"

                              text body

                              color "warning"
                              mrkdwn_in "text", "fields"
                              fields deptValues.keySet().collect {  itx ->

                                         [
                                              'title' : itx ,
                                              'value' : deptValues.get(itx)+' *('+((100*((df.parse(deptValues.get(itx))-df.parse(prevDeptValues.get(itx)))/df.parse(prevDeptValues.get(itx)))).round(new MathContext(0)).toInteger())+'%)*' ,
                                              'short' : "false"

                                      ]
                              }

                          }])

        }


        println json.toPrettyString()
        groovyx.net.http.HttpResponseDecorator resp = endpoint.post(
                requestContentType: 'application/json',
                // contentType: 'application/json',
                body: json.toString()
        )
        // println resp.getEntity().
        assert (resp.status == 200 || resp.status == 201)
    }

    public static void notifySymphonyCase(String Case, String theAction,String caseTitle) {
        RESTClient endpoint = getEndpoint(itSymphonyCase)

        def json = new JsonBuilder()


        json {
            attachments ([{
                              fallback "<http://casetracker.owd.com/casetracker/default.asp?"+Case+"|Open Case "+Case+">"

                              title "Case Activity - "+Case + "- "+ caseTitle
                              title_link "http://casetracker.owd.com/casetracker/default.asp?"+Case

                              text "There has been activity on this case \r\n In the type of " + theAction


                             /* mrkdwn_in "text", "fields"
                              fields([{
                                          title "Type"
                                          value error.getErrorType()
                                          'short' "true"
                                      },
                                      {
                                          title "Order or Ref"
                                          value error.getOrderNum()
                                          'short' "true"
                                      }  ,
                                      {
                                          title "Client"
                                          value clientname
                                          'short' "true"
                                      },

                                      {
                                          title "Request"
                                          value error.getRequestType()
                                          'short' "true"
                                      }
                              ])*/
                          }])

        }


        println json.toPrettyString()
        groovyx.net.http.HttpResponseDecorator resp = endpoint.post(
                requestContentType: 'application/json',
                // contentType: 'application/json',
                body: json.toString()
        )
        // println resp.getEntity().
        assert (resp.status == 200 || resp.status == 201)
    }

}


