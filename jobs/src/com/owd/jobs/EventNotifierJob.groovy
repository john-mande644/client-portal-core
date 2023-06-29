package com.owd.jobs

import com.owd.core.business.EventFeeds
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdExternalEvent
import com.owd.hibernate.generated.OwdOrder
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.hibernate.criterion.Expression

import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 11/30/12
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
class EventNotifierJob extends OWDStatefulJob {

    def endpoint = new RESTClient("https://api.photojojo.com/v1/owd/order-status-change")

    public static void main (String[] args) throws Exception
    {
        run()


    }
    @Override
    void internalExecute() {
        //To change body of implemented methods use File | Settings | File Templates.

        //get list of events to send

        //iterate, process and delete on success

        try{
        List events = HibernateSession.currentSession().createCriteria(OwdExternalEvent.class)
                .add(Expression.eq("clientFkey", 387))
                .add(Expression.eq("eventType", EventFeeds.kOrderStatusEventType))
                .add(Expression.eq("entitytype", EventFeeds.kOrderEntityType))
                .list();

            println "found events "+events.size()
        for(OwdExternalEvent event:events)
        {
        try{
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,event.getEntityId())
            if(postOrderStatusEvent(order,event) )
            {
                println "Posted!"
                HibernateSession.currentSession().delete(event)
                HibUtils.commit(HibernateSession.currentSession())
            }
        }catch(Exception ex)
            {
                if(ex.getMessage().contains("No row with the given identifier exists"))
                {
                    HibernateSession.currentSession().delete(event)
                    HibUtils.commit(HibernateSession.currentSession())
                }   else
                {
                ex.printStackTrace()
                }

            }

        }

        }catch(Exception ex)
        {
            ex.printStackTrace()
        }

        try{
            List events = HibernateSession.currentSession().createCriteria(OwdExternalEvent.class)
                    .add(Expression.not(Expression.eq("clientFkey", 387)))
                    .list();
            for(OwdExternalEvent event:events)
            {
                try{

                        HibernateSession.currentSession().delete(event)
                        HibUtils.commit(HibernateSession.currentSession())

                }catch(Exception ex)
                {
                    ex.printStackTrace()

                }

            }

        }catch(Exception ex)
        {
            ex.printStackTrace()
        }


    }

     static String getFormattedTimestamp(Date localDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(localDate);
    }



    boolean postOrderStatusEvent(OwdOrder order, OwdExternalEvent event)
    {
        HttpResponseDecorator resp = (HttpResponseDecorator) endpoint.post(
              //  path : 'statuschange',
                body : [    id:order.getOrderId(),
                            ref:order.getOrderRefnum(),
                            owdref:order.getOrderNum(),
                            newstatus:event.getEventSubtype(),
                            source:event.getSource(),
                            msg:event.getNote(),
                            eventtime:EventNotifierJob.getFormattedTimestamp(event.getEventTime())
                        ],
                requestContentType : ContentType.URLENC
        )

        println("Status:"+resp.status)
        println("Type:"+resp.contentType)
        println("Data:"+resp.data)

        if(resp.status==200)
        {
           return true
        }
        else
        {
            return false
        }
    }


}
