package com.owd.jobs.clients;

import com.owd.hibernate.HibernateSession;
import com.owd.jobs.BaseTest;
import com.owd.jobs.OWDStatefulJob;
import com.owd.magento2Api.Models.PostSourceItemsRequest;
import com.owd.magento2Api.Services.SourceItemRepository;
import org.hibernate.Query;
import org.junit.Test;
import retrofit2.Call;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FOHPostInventoryLevelsJobTests extends BaseTest {

    int clientId = 640;
    int postedItemsCount = 0;

    @Override
    public void setUp() {
        super.setUp();
    }


    @Test
    public void When_Execute_Job_Then_Items_Should_Be_Posted() throws Exception {

        System.setProperty("com.owd.environment","test");

        OWDStatefulJob job = null;
        SourceItemRepository sourceItemRepositoryMock = null;
//        sourceItemRepositoryMock = new SourceItemRepository() {
//            @Override
//            public Call<SearchSourceItemResponse> getSourceItems(String field, String value, String type) {
//                return null;
//            }
//
//            @Override
//            public Call<okhttp3.ResponseBody> postSourceItems(PostSourceItemsRequest postSourceItemsRequest) {
//                postedItemsCount = postSourceItemsRequest.getSourceItems().size();
//                return null;
//            }
//        };
        job = new FOHPostInventoryLevelsJob(sourceItemRepositoryMock, 1000000);
        String sqlStatement = "\n" +
                "select\n" +
                "\tINV.inventory_num,\n" +
                "\tOH.qty_on_hand\n" +
                "from [dbo].[owd_inventory] as INV\n" +
                "JOIN [dbo].[owd_inventory_oh] AS OH\n" +
                "\tON INV.inventory_id = OH.inventory_fkey \n" +
                "where client_fkey = :clientID and is_active = 1 and inventory_id > 679388;";
        Query q = HibernateSession.currentSession().createSQLQuery(sqlStatement);
        q.setParameter("clientID", clientId);
        List results = q.list();
        int shouldBePostedCount = results.size();

        job.internalExecute();

        assertEquals(shouldBePostedCount, postedItemsCount);
    }
}
