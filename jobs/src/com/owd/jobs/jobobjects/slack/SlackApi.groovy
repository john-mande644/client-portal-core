package com.owd.jobs.jobobjects.slack

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.LogableException
import groovy.json.JsonBuilder
import groovyx.net.http.RESTClient
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext

/**
 * Created by stewartbuskirk1 on 7/2/15.
 */
class SlackApi {

      String postMessageUrl = "https://hooks.slack.com/services/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"

    def  endpoint


    public static void main(String[] args) {

        SlackApi slack = new SlackApi()

        slack.postSimpleMessage("Hello World!")
    }


    protected  RESTClient getEndpoint(String rootUrl)
    {
        endpoint = new RESTClient(rootUrl)
       // endpoint.ignoreSSLIssues()
      //  endpoint.setClient(getTestHttpClient())


        return endpoint;
    }

    public void notifySlack(LogableException error) {
        RESTClient endpoint = getEndpoint(postMessageUrl)

        def json = new JsonBuilder()


        json {
            attachments ([{
                              fallback "*[Urgent]*: <http://url_to_task|Test out Slack message attachments>"
                              //  pretext "*[Urgent]*: <http://url_to_task|Test out Slack message attachments>"
                              title "Jobs Error - "
                              title_link "http://internal.owd.com:8085/jobs/ordererrorlist.jsp"

                              text "Optional text that appears within the attachment"

                              color "error"
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
        if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}
    }

    public void testMessage(String message) {
        RESTClient endpoint = getEndpoint(postMessageUrl)

        def json = new JsonBuilder()


        json {
            attachments ([{
                                fallback "*[Urgent]*: <http://url_to_task|Test out Slack message attachments>"
                              //  pretext "*[Urgent]*: <http://url_to_task|Test out Slack message attachments>"
                              title "Slack API Documentation"
                              title_link "https://api.slack.com/"

                              text "Optional text that appears within the attachment"

                              color "error"
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
        if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}
    }
}
