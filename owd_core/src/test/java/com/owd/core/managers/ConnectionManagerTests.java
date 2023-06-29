package com.owd.core.managers;

import com.owd.core.tests.BaseTest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConnectionManagerTests extends BaseTest {

    @Test
    public  void  When_Get_Count_Of_Orders_Then_Right_Qty_Should_Be_Returend() throws  Exception {
        int clientFkey = 445;
        int clientFkey2 = 545;
        String shippedDate = "2015-03-16";
        String createdDate = "2016-02-10";
        String orderRefNum = "6954-RETURN-CALLTAG";
        String orderRefNum2 = "587";
        String prefixSearch = "TRUE";
        int expectedQtyByClientFkey = 15;
        int exectedQtyByOrderRefNum = 2;
        int expectedQtyByCreatedDate = 86;
        int expectedQtyByShipDate = 28;
        int expectedQtyByPrefixSearch = 10;

        int countByClientFkey = ConnectionManager.getOrdersCount(clientFkey,null,
                null,null,null);
        int countByClientFkeyWithDefaults = ConnectionManager.getOrdersCount(clientFkey,"19000101",
                "19000101","","FALSE");
        int countByOrderNumAndClientFkey = ConnectionManager.getOrdersCount(clientFkey2,null,
                null,orderRefNum,null);
        int countByCreatedDateAndClientFkey = ConnectionManager.getOrdersCount(clientFkey2,null,
                createdDate,null,null);
        int countByShipDateAndClientFkey = ConnectionManager.getOrdersCount(clientFkey2,shippedDate,
                null,null,null);
        int countByClientFkeyAndPrefixSearch = ConnectionManager.getOrdersCount(clientFkey2,null,
                null,orderRefNum2,prefixSearch);

        assertEquals(expectedQtyByClientFkey, countByClientFkey);
        assertEquals(expectedQtyByClientFkey, countByClientFkeyWithDefaults);
        assertEquals(exectedQtyByOrderRefNum, countByOrderNumAndClientFkey);
        assertEquals(expectedQtyByCreatedDate, countByCreatedDateAndClientFkey);
        assertEquals(expectedQtyByShipDate, countByShipDateAndClientFkey);
        assertEquals(expectedQtyByPrefixSearch, countByClientFkeyAndPrefixSearch);
    }

    @Test
    public  void When_Get_Orders_List_With_Paged_Requests_Then_The_Right_Ids_And_Qty_Should_Be_Returned()  throws  Exception {
        int clientFkey = 445;
        int clientFkey2 = 545;
        String shippedDate = "2015-03-16";
        String createdDate = "2016-02-10";
        String orderRefNum = "6954-RETURN-CALLTAG";
        String orderRefNum2 = "587";
        String prefixSearch = "TRUE";
        int expectedQtyByClientFkey = 7;
        int exectedQtyByOrderRefNum = 2;
        int expectedQtyByCreatedDate = 26;
        int expectedQtyByShipDate = 7;
        int expectedQtyByPrefixSearch = 5;
        int expectedQtyWithoutPagingSearch = 10; //Page size = 0

        List countByClientFkeyOrdersList = ConnectionManager.getPagedOrderKeysForClientID(clientFkey,"19000101",
                "19000101","","FALSE",1,8);
        List countByOrderNumAndClientFkeyOrdersList = ConnectionManager.getPagedOrderKeysForClientID(clientFkey2,"19000101",
                "19000101",orderRefNum,"FALSE",0,5);
        List countByCreatedDateAndClientFkeyOrdersList = ConnectionManager.getPagedOrderKeysForClientID(clientFkey2,"19000101",
                createdDate,null,null,2,30);
        List countByShipDateAndClientFkeyOrdersList = ConnectionManager.getPagedOrderKeysForClientID(clientFkey2,shippedDate,
                "19000101",null,null,3,7);
        List countByClientFkeyAndPrefixSearchOrdersList = ConnectionManager.getPagedOrderKeysForClientID(clientFkey2,"19000101",
                "19000101",orderRefNum2,prefixSearch,0,5);
        List page2IdsByPrefixSearch = ConnectionManager.getPagedOrderKeysForClientID(clientFkey2,"19000101",
                "19000101",orderRefNum2,prefixSearch,1,5);
        List outOfPagingList = ConnectionManager.getPagedOrderKeysForClientID(clientFkey2,"19000101",
                "19000101",orderRefNum2,prefixSearch,5,5);
        List noPageingList = ConnectionManager.getPagedOrderKeysForClientID(clientFkey2,"19000101",
                "19000101",orderRefNum2,prefixSearch,5,0);

        assertEquals(expectedQtyByClientFkey, countByClientFkeyOrdersList.size());
        assertEquals(exectedQtyByOrderRefNum, countByOrderNumAndClientFkeyOrdersList.size());
        assertEquals(expectedQtyByCreatedDate, countByCreatedDateAndClientFkeyOrdersList.size());
        assertEquals(expectedQtyByShipDate, countByShipDateAndClientFkeyOrdersList.size());
        assertEquals(expectedQtyByPrefixSearch, countByClientFkeyAndPrefixSearchOrdersList.size());
        assertEquals(0, outOfPagingList.size());
        assertEquals(expectedQtyWithoutPagingSearch, noPageingList.size());
        //Validate id's
        assertEquals("8788455",countByClientFkeyAndPrefixSearchOrdersList.get(0));
        assertEquals("8791272",countByClientFkeyAndPrefixSearchOrdersList.get(1));
        assertEquals("8791800",countByClientFkeyAndPrefixSearchOrdersList.get(2));
        assertEquals("8791801",countByClientFkeyAndPrefixSearchOrdersList.get(3));
        assertEquals("8791955",countByClientFkeyAndPrefixSearchOrdersList.get(4));
        assertEquals("8792156",page2IdsByPrefixSearch.get(0));
        assertEquals("8792597",page2IdsByPrefixSearch.get(1));
        assertEquals("8792687",page2IdsByPrefixSearch.get(2));
        assertEquals("8792688",page2IdsByPrefixSearch.get(3));
        assertEquals("8792995",page2IdsByPrefixSearch.get(4));
    }
}
