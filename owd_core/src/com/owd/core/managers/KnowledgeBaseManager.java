package com.owd.core.managers;

import com.owd.core.business.order.Order;
import com.owd.core.ruleAdapters.BaseRuleAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.compiler.DroolsParserException;
import org.drools.definition.KnowledgePackage;
import org.drools.definition.rule.Rule;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;
import org.drools.io.internal.InternalResource;
import org.drools.runtime.StatefulKnowledgeSession;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 11/12/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class KnowledgeBaseManager {
private final static Logger log =  LogManager.getLogger();

    private static String localCacheDir = null;
    private static KnowledgeAgent knowledgeAgent = null;
    static Map<String,KnowledgeBase> packageKnowledgeBaseMap = new HashMap<String,KnowledgeBase>();
    static Map<String,KnowledgeAgent> packageKnowledgeAgentMap = new HashMap<String,KnowledgeAgent>();


    public synchronized static KnowledgeAgent getKnowledgeAgent(String packageName) throws Exception {
        try{
            if(!(packageKnowledgeAgentMap.containsKey(packageName))) {
                System.setProperty("drools.schema.validating","false");
                log.debug(">>>>>>> knowledgeAgent == null");
                log.debug("Applying change set");

                ResourceChangeScannerConfiguration sconf =
                        ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
                sconf.setProperty("drools.resource.scanner.interval", "2");
                ResourceFactory.getResourceChangeScannerService().configure(sconf);
                ResourceFactory.getResourceChangeScannerService().setSystemEventListener(new org.drools.agent.impl.PrintStreamSystemEventListener());

                ResourceFactory.getResourceChangeScannerService().start();

                ResourceFactory.getResourceChangeNotifierService().setSystemEventListener(new org.drools.agent.impl.PrintStreamSystemEventListener());
                ResourceFactory.getResourceChangeNotifierService().start();

                KnowledgeAgentConfiguration aconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
                aconf.setProperty("drools.agent.newInstance", "false");

                knowledgeAgent = KnowledgeAgentFactory.newKnowledgeAgent(packageName+"Agent", aconf);
                knowledgeAgent.addEventListener(new KnowledgeBaseListener());

                knowledgeAgent.applyChangeSet(ResourceFactory.newFileResource(getLocalCacheDir() + "/"+packageName+".xml"));
                knowledgeAgent.monitorResourceChangeEvents(true);

             //   ResourceFactory.getResourceChangeScannerService().subscribeNotifier(ResourceFactory.getResourceChangeNotifierService(),ResourceFactory.newFileResource(getLocalCacheDir() + "/change-set.xml"));
                log.debug("Applied change set: " + getLocalCacheDir() + "/"+packageName+".xml");

                log.debug(">>>>>>> knowledgeAgent == "+knowledgeAgent);

                packageKnowledgeAgentMap.put(packageName,knowledgeAgent);
            }


        }catch (Exception ex)
        {
            log.debug("CAUGHT Exception getKnowledgeAgent()");
            knowledgeAgent = null;
            throw ex;
        }
        return packageKnowledgeAgentMap.get(packageName);

    }


    public static void main(String[] args) throws Exception {

        getKnowledgeAgent("OrderInsert");


    /*  //  System.out.println(Date.parse("Thu, 17 Mar 2016 19:57:06 GMT, Thu, 17 Mar 2016 14:57:06 -0500"));
        System.out.println(Date.parse("Thu, 17 Mar 2016 14:57:06 -0500"));
        System.out.println(Date.parse("Thu, 17 Mar 2016 19:57:06 GMT-0500"));
        UrlResource ir = new UrlResource("http://guvnor.owd.com:8180/guvnor/org.drools.guvnor.Guvnor/package/OrderInsert/1_0_001");
        ir.setBasicAuthentication("enabled");
        ir.setUsername("guest");
        System.out.println("LastRead:"+ir.getLastRead());
        System.out.println("LastModified:"+ir.getLastModified());
      //  System.out.println("GrabLastMod:"+ir.grabLastMod());
        Reader rdr = ir.getReader();
        System.out.println("LastRead:"+ir.getLastRead());
        System.out.println("LastModified:"+ir.getLastModified());

        System.out.println(Long.parseLong("1458244626101"));
        Thread.sleep(15000L);
        System.out.println("LastRead:"+ir.getLastRead());
        System.out.println("LastModified:"+ir.getLastModified());
      //  System.out.println("LastModified:"+ir.getLastModified());*/

    }
    /**
     *
     * @param ruleAdapter
     * @param packageName
     */
    public static void executeRule(BaseRuleAdapter ruleAdapter, String packageName) throws Exception {

        StatefulKnowledgeSession knowledgeSession = null;

        try {
            knowledgeSession = getKnowledgeSession(packageName);
            boolean validated = false;

            validated = validatePackage(packageName, knowledgeSession);

            if(!validated)  {
                log.debug("WAITING 5 SECONDS");
                Thread.sleep(5000L);
                validated = validatePackage(packageName, knowledgeSession);

            }

           if(validated)
           {
            knowledgeSession.insert(ruleAdapter);
            knowledgeSession.fireAllRules();
            knowledgeSession.dispose();
           }else{
               throw new Exception("Unable to initialize rule services");
           }

        } catch (Exception e) {
            System.err.println("Error executing rule - KnowledgeBaseManager.executeRule \n"
                    + packageName + "\n"
                    + e.getLocalizedMessage());
            e.printStackTrace();

            if (knowledgeSession != null) {
                try {
                    knowledgeSession.dispose();
                } catch (Exception ee) {

                }
            }
            log.debug("CAUGHT Exception executeRule()");

            throw new Exception(e);
        }
    }

    private static boolean validatePackage(String packageName, StatefulKnowledgeSession knowledgeSession) {
        boolean validated = false;
        Collection<KnowledgePackage> kpackages = knowledgeSession.getKnowledgeBase().getKnowledgePackages();
        for (KnowledgePackage knowledgePackage : kpackages) {
            System.out.println("Drools package:"+knowledgePackage.getName());
            if(packageName.equalsIgnoreCase(knowledgePackage.getName()))
            {
               validated=true;
            }
            Collection<Rule> rules = knowledgePackage.getRules();
            for (Rule rule : rules) {
                log.debug("****" + rule.getName());
            }
        }
        return validated;
    }



    /**
     * Returns Drools knowledge base.
     *
     * @return
     * @throws DroolsParserException
     * @throws IOException
     */
    private static KnowledgeBase getKnowledgeBase(String packageName)
            throws Exception {

        try {
            if(!(packageKnowledgeBaseMap.containsKey(packageName)))
            {
                KnowledgeBase rootBase = getKnowledgeAgent(packageName).getKnowledgeBase();

                packageKnowledgeBaseMap.put(packageName,rootBase);

            }
            return packageKnowledgeBaseMap.get(packageName);


        } catch (Exception e) {
            log.debug("CAUGHT Exception getKnowledgeBase()");

            System.err.println("Error applying change set: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns Drools knowledge session.
     *
     * @return
     * @throws RuntimeException
     */
    public static StatefulKnowledgeSession getKnowledgeSession(String packageName) throws Exception {

        try {
            KnowledgeBase knowledgeBase = getKnowledgeBase(packageName);

            return knowledgeBase.newStatefulKnowledgeSession();

        } catch (Exception e) {
            log.debug("CAUGHT Exception getKnowledgeSession()");

            System.err.println("getKnowledgeSession: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * Returns the Drools local cache directory, used to load rules from the server.
     *
     * @return
     */
    private static String getLocalCacheDir() {

        String environment = null;
        Properties environmentProperties = new Properties();

        if (localCacheDir == null) {
            try {
                //load a properties file
                environmentProperties = System.getProperties();

                if(environment == null) {
                    log.debug("Environment not found setting to test.");
                    environment="test";
                } else {
                    log.debug("Environment found: " + environment);
                }
                environmentProperties.load(DataSourceManager.class.getClassLoader().getResourceAsStream("com/owd/properties/" + environment + ".properties"));

                //get the property value and print it out
                localCacheDir = environmentProperties.getProperty("localCacheDir");
                log.debug("setting drools cache dir to "+localCacheDir);
                System.setProperty("drools.resource.urlcache", localCacheDir);

            } catch (IOException ex) {
                log.debug("CAUGHT Exception getLocalCacheDir()");

                System.err.println("Error getLocalCacheDir: " + ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        }
        return localCacheDir;
    }


    //KnowledgeAgentEventListener Methods

}
