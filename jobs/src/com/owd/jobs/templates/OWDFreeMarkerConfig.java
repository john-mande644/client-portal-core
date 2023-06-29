package com.owd.jobs.templates;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * Created by danny on 8/29/2019.
 */
public class OWDFreeMarkerConfig {


    public Configuration getConfiguration(){
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(getClass(),"/template");
        // Recommended settings for new projects:
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return cfg;

    }
}
