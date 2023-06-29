package com.owd.jobs.jobobjects.billing.storage;

import com.owd.LogableException;
import com.owd.core.business.billing.storage.cubicStorageUtilities;
import com.owd.jobs.OWDStatefulJob;

/**
 * Created by danny on 3/21/2020.
 */
public class CubicStorageBillingJob  extends OWDStatefulJob {

    public void internalExecute(){
        try {
            cubicStorageUtilities.insertStorageRecords();
        }catch (Exception e){
            e.printStackTrace();
            LogableException ex = new LogableException(e.getMessage(),"Cubic Storage","All","Processing of Storage Failed",LogableException.errorTypes.INTERNAL);


        }
    }
}
