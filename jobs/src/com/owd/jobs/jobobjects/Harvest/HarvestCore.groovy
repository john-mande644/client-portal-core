package com.owd.jobs.jobobjects.Harvest

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdClient
import groovy.xml.StreamingMarkupBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import java.sql.PreparedStatement
import java.text.SimpleDateFormat
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/6/12
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
class HarvestCore {

    private final static Logger log =  LogManager.getLogger();


    def static endpoint = new RESTClient("https://oneworlddirect.harvestapp.com/")

    static {
        endpoint.client.addRequestInterceptor(
                new HttpRequestInterceptor() {
                    void process(HttpRequest httpRequest, HttpContext httpContext) {
                        httpRequest.addHeader('Authorization', 'Basic ' + 'owditadmin@owd.com:one7172world'.bytes.encodeBase64().toString())
                        httpRequest.setHeader('Accept', 'application/xml')
                        httpRequest.setHeader('Content-Type', 'application/xml')
                        httpRequest.setHeader('Accept-Encoding', '')
                    }
                });

    }

    public static void main(String[] args) {
        // addTask("Client Requests");

        log.debug("starting HarvestCore");

        // createProject('test5',1,1382482)
        // println "hello"
      //  pullRecentTimerEvents(5)
        println getUsersMap()
        println getProjectsMap()
        Map<Integer, Object> tmap = getTasksMap()
    }




    public static void pullRecentTimerEvents(int daysBack) throws Exception {

        if (daysBack < 0) {
            throw new Exception("days to check must be greater than zero")
        }
        //get lookup data
        Map<Integer, Object> umap = getUsersMap()
        Map<Integer, Object> pmap = getProjectsMap()
        Map<Integer, Object> tmap = getTasksMap()

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")
        SimpleDateFormat centralSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        centralSdf.setTimeZone(TimeZone.getTimeZone("CST"));
        SimpleDateFormat zuluSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        zuluSdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        def today = new Date()
        def yesterday = today - 1
        def start = yesterday - daysBack

        println sdf.format(start)
        println sdf.format(yesterday)

        for (Integer pid: pmap.keySet()) {
            println pid
            endpoint.get(
                    path: 'projects/' + pid + '/entries', query: [from: sdf.format(start), to: sdf.format(yesterday)]) { resp, reader ->

                reader."day-entry".each {

                    println "userid:"+it."user-id".text()

                    println "cid:" + Integer.parseInt(pmap.get(Integer.parseInt(it."project-id".text())).code.text())
                    println "activity:" + tmap.get(Integer.parseInt(it."task-id".text())).name.text()
                    println "seconds:" + ((Double.parseDouble(it.hours.text()) * 3600.00).intValue())
                    println "notes:" + it.notes.text()
                    println "createdby:" + umap.get(Integer.parseInt(it."user-id".text())).email.text().substring(0, umap.get(Integer.parseInt(it."user-id".text())).email.text().indexOf("@"))
                    println "category:" + "harvest." + (it.id.text())
                    println "created:" + it."created-at".text().replaceAll("T", " ").replaceAll("Z", "")



                    log.debug("Time in CST is " + centralSdf.format(zuluSdf.parse(it."created-at".text().replaceAll("T", " ").replaceAll("Z", ""))));

                    println "+++"


                    String sql = "insert into owd_timers (client_fkey,activity_desc,elapsed_seconds,notes,rate,no_charge,created_by,created,category, orig_seconds) " +
                            "select ?,?,?,?,?,?,?,?,?,? where " +
                            "not exists (select * from owd_timers where category=?)";

                    PreparedStatement stmt = HibernateSession.getPreparedStatement(sql);
                    stmt.setInt(1, Integer.parseInt(pmap.get(Integer.parseInt(it."project-id".text())).code.text()));
                    stmt.setString(2, tmap.get(Integer.parseInt(it."task-id".text())).name.text());
                    stmt.setInt(3, ((((double) (Math.ceil((Double.parseDouble(it.hours.text())*4))/4)) * 3600.00).intValue()));
                    stmt.setString(4, it.notes.text());
                    stmt.setDouble(5, 0.00);
                    stmt.setInt(6, 0);
                    stmt.setString(7, umap.get(Integer.parseInt(it."user-id".text())).email.text().substring(0, umap.get(Integer.parseInt(it."user-id".text())).email.text().indexOf("@")));
                    stmt.setString(8, centralSdf.format(zuluSdf.parse(it."created-at".text().replaceAll("T", " ").replaceAll("Z", ""))));
                    stmt.setString(9, "harvest." + (it.id.text()));
                    stmt.setInt(10, ((Double.parseDouble(it.hours.text()) * 3600.00).intValue()));
                    stmt.setString(11, "harvest." + (it.id.text()));


                    int rows = stmt.executeUpdate();
                    HibUtils.commit(HibernateSession.currentSession());
                    HibernateSession.closePreparedStatement()

                }
            }
        }

    }


    public static void syncProjectsWithClients() {

        Map<Integer, Object> pmap = getOWDProjectMap()

        println pmap
        List<OwdClient> activeClients = HibernateSession.currentSession().createQuery("from OwdClient where is_active=1  and company_name <> '' and company_name not like '%test%' and company_name not like 'ACME%'").list();
        for (OwdClient client: activeClients) {
            if (!(pmap.containsKey(client.getClientId()))) {
                println "creating project for ["+client.getCompanyName()+"]"
                createProject(client.getCompanyName(), client.getClientId(), 1382482)

            } else if (!("true".equals(pmap.get(client.getClientId()).active.text()))) {
                toggleProject(Integer.parseInt(pmap.get(client.getClientId()).id.text()))

            }
        }

        List<OwdClient> inactiveClients = HibernateSession.currentSession().createQuery("from OwdClient where is_active=0  and company_name <> '' and company_name not like '%test%' and company_name not like 'ACME%'").list();
        for (OwdClient client: inactiveClients) {
            if (pmap.containsKey(client.getClientId()) && ("true".equals(pmap.get(client.getClientId()).active.text()))) {
                toggleProject(Integer.parseInt(pmap.get(client.getClientId()).id.text()))
            }
        }
    }


    public static void toggleProject(int harvestProjectId) throws Exception {

        try {
            def resp = endpoint.put(path: 'projects/' + harvestProjectId + '/toggle')
            assert resp.status == 200
        }catch (Exception ex)
        {
            println "ex:"+ex.getMessage()
            if (!(ex.getMessage().equals("OK")) )
            {
                throw ex
            }
        }


    }

    public static Map<Integer, Object> getOWDProjectMap() {
        //keyed by OWD client ID

        Map<Integer, Object> pmap = new TreeMap<Integer, Object>()

        endpoint.get(
                path: 'projects' ,

                contentType: groovyx.net.http.ContentType.XML
        )
                {resp, reader ->

                    reader."project".each {

                        //   println it.code +' '+it.id.text()
                        pmap.put(Integer.parseInt(it.code.text()), it)
                    }
                }

        return pmap
    }

    public static Map<Integer, Object> getUsersMap() {

        Map<Integer, Object> pmap = new TreeMap<Integer, Object>()

        endpoint.get(
                path: 'people',

                contentType: groovyx.net.http.ContentType.XML
                //  body : [],
                //  requestContentType : 'application/xml'
        )

                {resp, reader ->

                    reader."user".each {

                           println it.id.text()+":"+it.email
                        pmap.put(Integer.parseInt(it.id.text()), it)
                    }
                }

        return pmap
    }

    public static Map<Integer, Object> getTasksMap() {

        Map<Integer, Object> pmap = new TreeMap<Integer, Object>()

        endpoint.get(
                path: 'tasks'   ,

                contentType: groovyx.net.http.ContentType.XML
                //  body : [],
                //  requestContentType : 'application/xml'
        )

                {resp, reader ->

                    reader."task".each {

                        //  println it.name +':'+it.id.text()
                        pmap.put(Integer.parseInt(it.id.text()), it)

                    }
                }

        return pmap
    }

    public static Map<Integer, Object> getProjectsMap() {

        Map<Integer, Object> pmap = new TreeMap<Integer, Object>()

        endpoint.get(
                path: 'projects'   ,

                contentType: groovyx.net.http.ContentType.XML
                //  body : [],
                //  requestContentType : 'application/xml'
        )

                {resp, reader ->

                    reader."project".each {

                        //  println it.code +' '+it.id.text()
                        pmap.put(Integer.parseInt(it.id.text()), it)

                    }
                }

        return pmap
    }



    public static void createProject(String pname, int pcode, int clientId) throws Exception {

        def xbuilder = new groovy.xml.StreamingMarkupBuilder()
        xbuilder.encoding = 'UTF-8'
        def createProjectRequest =
            {
                mkp.xmlDeclaration()

                project() {
                    name(pname)
                    active(type: 'boolean', 'true')
                    'bill-by'('none')

                    'client-id'(type: 'integer', clientId)

                    //   billable(type:'boolean','true')
                    code(pcode)
                    notes('')

                    'budget'(type: 'decimal', '0')
                    'budget-by'('none')


                }
            }

        def xml = xbuilder.bind(createProjectRequest).toString()
          println xml

        try {


            HttpResponseDecorator resp = endpoint.post(
                    path: 'projects',
                    body: xml,
                    requestContentType: ContentType.XML,
                    contentType: ContentType.XML
            ) as HttpResponseDecorator

                println("Location:"+resp.getAllHeaders())
            println("Status:" + resp.status)
            //   println("Type:"+resp.contentType)

            if (resp.status != 201) {
                throw new Exception("Unable to create project")
            }
        }catch(HttpResponseException ex)
        {
            println "ex-createproject:"+ex.getMessage()

            if (!(ex.getMessage().contains("Created")) )
            {
                throw ex
            }
        }

    }

    //not used currently
    public static void addTask(String pname) {

        def xbuilder = new groovy.xml.StreamingMarkupBuilder()
        xbuilder.encoding = 'UTF-8'
        def createProjectRequest =
            {
                //  mkp.xmlDeclaration()
                task() {
                    'billable-by-default'(type: 'boolean', 'false')
                    'default-hourly-rate'(type: 'decimal', '0')
                    'is-default'(type: 'boolean', 'true')
                    'budget-by'('none')
                    name(pname)


                }
            }

        def xml = xbuilder.bind(createProjectRequest).toString()
        println xml


        HttpResponseDecorator resp = endpoint.post(
                path: 'tasks',
                body: xml,
                requestContentType: ContentType.XML,
                contentType: 'text/plain'
        ) as HttpResponseDecorator

        //  println("Location:"+resp.getAllHeaders())
        println("Status:" + resp.status)
        //  println("Type:"+resp.contentType)
        //   println("Data:"+resp.getData().toString())

        if (resp.status != 201) {
            throw new Exception("Unable to create task")
        }

    }

    /**
     *  pretty prints the GPathResult NodeChild
     */
    def static outputFormattedXml(node) {
        def xml = new StreamingMarkupBuilder().bind {
//            mkp.declareNamespace("":node.namespaceURI())
            mkp.yield(node)
        }

        def factory = TransformerFactory.newInstance()
        def transformer = factory.newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, 'yes')

        // figured this out by looking at Xalan's serializer.jar
        // org/apache/xml/serializer/output_xml.properties
        transformer.setOutputProperty("{http\u003a//xml.apache.org/xalan}indent-amount", "2")
        def result = new StreamResult(new StringWriter())
        transformer.transform(new StreamSource(new ByteArrayInputStream(xml.toString().bytes)), result)

        return result.writer.toString()
    }
}
