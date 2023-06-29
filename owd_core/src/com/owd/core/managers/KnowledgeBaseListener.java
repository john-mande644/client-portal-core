package com.owd.core.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.event.knowledgeagent.*;

/**
 * Created by stewartbuskirk1 on 3/10/14.
 */

public class KnowledgeBaseListener implements KnowledgeAgentEventListener {
private final static Logger log =  LogManager.getLogger();

    public void beforeChangeSetApplied(BeforeChangeSetAppliedEvent beforeChangeSetAppliedEvent) {
        log.debug("KBEvent:beforeChangeSetApplied");


    }

    public void afterChangeSetApplied(AfterChangeSetAppliedEvent afterChangeSetAppliedEvent) {
        log.debug("KBEvent:afterChangeSetAppliedEvent");

    }

    public void beforeChangeSetProcessed(BeforeChangeSetProcessedEvent beforeChangeSetProcessedEvent) {
        log.debug("KBEvent:beforeChangeSetProcessedEvent");

    }

    public void afterChangeSetProcessed(AfterChangeSetProcessedEvent afterChangeSetProcessedEvent) {
        log.debug("KBEvent:afterChangeSetProcessedEvent");

    }

    public void beforeResourceProcessed(BeforeResourceProcessedEvent beforeResourceProcessedEvent) {
        log.debug("KBEvent:beforeResourceProcessedEvent");

    }

    public void afterResourceProcessed(AfterResourceProcessedEvent afterResourceProcessedEvent) {
        log.debug("KBEvent:afterResourceProcessedEvent");

    }

    public void knowledgeBaseUpdated(KnowledgeBaseUpdatedEvent knowledgeBaseUpdatedEvent) {
        log.debug("KBEvent:knowledgeBaseUpdatedEvent ");

    }

    public void resourceCompilationFailed(ResourceCompilationFailedEvent resourceCompilationFailedEvent) {
        log.debug("KBEvent:resourceCompilationFailedEvent");

    }
}
