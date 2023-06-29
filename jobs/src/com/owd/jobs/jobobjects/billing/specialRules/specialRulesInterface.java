package com.owd.jobs.jobobjects.billing.specialRules;

import java.util.List;

/**
 * Created by danny on 9/13/2016.
 */
public interface specialRulesInterface {


    public List<String> loadIds(pricingBean pricing,String theDate) throws Exception;

    public void processIds(List<String> ids,pricingBean prices) throws Exception;

    public void processShipping(String shippedOn,pricingBean prices) throws Exception;

}
