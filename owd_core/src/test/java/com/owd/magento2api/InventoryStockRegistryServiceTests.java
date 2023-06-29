package com.owd.magento2api;

import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.magento2Api.Magento2ApiServiceGenerator;
import com.owd.magento2Api.Models.*;
import com.owd.magento2Api.Services.*;
import okhttp3.ResponseBody;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class InventoryStockRegistryServiceTests {

    private Magento2ApiServiceGenerator magento2ApiServiceGenerator = null;
    private InventoryStockRepository inventoryStockRepository = null;

    private WebsiteRepository websiteRepository = null;
    private StoreRepository storeRepository = null;
    private SourceItemRepository sourceItemRepository = null;
    private StockRepository stockRepository = null;
    private SourceRepository sourceRepository = null;
    private ProductRepository productRepository =null;

    @Before
    public  void setUp() {
        System.setProperty("com.owd.environment","test");
        String FOHServiceRoute = "https://mcstaging.fredericks.com";
        //We are using the token as a constant, other option is use the login\password to generate token by the AdminTokenService
        String token = "mzz3usu7cb3cx6c386idkx26b5i46he2";
        magento2ApiServiceGenerator = new Magento2ApiServiceGenerator(FOHServiceRoute);
        inventoryStockRepository = magento2ApiServiceGenerator.createService(InventoryStockRepository.class, token);
        websiteRepository = magento2ApiServiceGenerator.createService(WebsiteRepository.class, token);
        storeRepository = magento2ApiServiceGenerator.createService(StoreRepository.class, token);
        sourceItemRepository = magento2ApiServiceGenerator.createService(SourceItemRepository.class, token);
        stockRepository = magento2ApiServiceGenerator.createService(StockRepository.class, token);
        sourceRepository = magento2ApiServiceGenerator.createService(SourceRepository.class, token);
        productRepository = magento2ApiServiceGenerator.createService(ProductRepository.class, token);
    }

    @Test
    public void testGetStock() {

        try {
            String productSKU = "P29-1337C.BLKI.DD.10";//"P29-1337C.BLKI.A.10";
            Call<InvontoryStock> callSync = inventoryStockRepository.getStock(productSKU);
            Response<InvontoryStock> response = callSync.execute();
            System.out.println(response.code());
            InvontoryStock stock = response.body();
            System.out.println(stock.getQty());

            assertNotNull(stock);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetStockResponseBody() {

        try {
            String productSKU = "P29-1337C.BLKI.DD.10";//"P29-1337C.BLKI.A.10";
            Call<ResponseBody> responseBody = inventoryStockRepository.getStockResponseBody(productSKU);
            Response<ResponseBody> response = responseBody.execute();
            System.out.println(response.code());
            ResponseBody body = response.body();

            assertNotNull(body);
            System.out.println(body.string());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetSourcesForItems() {

        try {
            String productSKU = "X93-1574.VIOT.1X/2X";
            Call<ResponseBody> responseBody =
                    sourceItemRepository.getSourceItemsResponse("sku",
                            productSKU,
                            "eq");
            Response<ResponseBody> response = responseBody.execute();
            System.out.println(response.code());
            ResponseBody body = response.body();

            assertNotNull(body);
            System.out.println(body.string());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSourceItemsAreSynced() {
        try {
            String productSKU = "FOH";//"X93-1574.VIOT.1X/2X";
            Call<SearchSourceItemResponse> responseBody =
                    sourceItemRepository.getSourceItems("source_code",
                            productSKU,
                            "eq");
            Response<SearchSourceItemResponse> response = responseBody.execute();
            System.out.println(response.code());
            SearchSourceItemResponse body = response.body();
            List<SourceItem> itemsFromMagento = body.getItems();
            HashMap<String, Long> quantitiesInMagento = new HashMap<>();

            for(SourceItem sourceItem: itemsFromMagento) {
                quantitiesInMagento.put(sourceItem.getSku(),sourceItem.getQuantity());
            }

            String sqlStatement ="\n" +
                    "select\n" +
                    "\tINV.inventory_num,\n" +
                    "\tOH.qty_on_hand\n" +
                    "from [dbo].[owd_inventory] as INV\n" +
                    "JOIN [dbo].[owd_inventory_oh] AS OH\n" +
                    "\tON INV.inventory_id = OH.inventory_fkey \n" +
                    "where client_fkey = :clientID and is_active = 1 and inventory_id > 679388;";
            Query q =  HibernateSession.currentSession().createSQLQuery(sqlStatement);
            q.setParameter("clientID",640);

            List results = q.list();

            if(results.size()<=0){
                throw new Exception("Unable to lookup sku's for client");
            }

            List<SourceItem> sourceItemList = getSourceItemsToPost("FOH", results);
            HashMap<String, Long> quantitiesInTest = new HashMap<>();

            for(SourceItem sourceItem: sourceItemList) {
                quantitiesInTest.put(sourceItem.getSku(),sourceItem.getQuantity());
            }


            int notExistedInMagentoCount = 0;
            int qtyInMagentoNotMatch = 0;
            int matchedInMagentoQty = 0;
            for(SourceItem sourceItem: sourceItemList) {
                if(sourceItem.getQuantity()==0)
                    continue;
                if(!quantitiesInMagento.containsKey(sourceItem.getSku())) {
                    notExistedInMagentoCount++;
                    continue;
                }
                if(quantitiesInMagento.get(sourceItem.getSku())!=sourceItem.getQuantity()) {
                    qtyInMagentoNotMatch++;
                    continue;
                }
                matchedInMagentoQty++;
            }

            int notExistedInOWDCount = 0;
            int qtyInOWDNotMatch = 0;
            int matchedInOWDQty = 0;
            System.out.println("not existed in owd: ");
            for(SourceItem sourceItem: itemsFromMagento) {
                if(sourceItem.getQuantity()==0)
                    continue;
                if(!quantitiesInTest.containsKey(sourceItem.getSku())) {
                    System.out.println(sourceItem.getSku()+ "  " +sourceItem.getQuantity());
                    notExistedInOWDCount++;
                    continue;
                }
            }
            for(SourceItem sourceItem: itemsFromMagento) {
                if(sourceItem.getQuantity()==0)
                    continue;
                if(!quantitiesInTest.containsKey(sourceItem.getSku())) {
                    continue;
                }
                if(quantitiesInTest.get(sourceItem.getSku())!=sourceItem.getQuantity()) {
                    qtyInOWDNotMatch++;
                    System.out.println("not match qty for sku: " + sourceItem.getSku() + " QTY Magento: "
                            + sourceItem.getQuantity()
                    + "  OWD: " + quantitiesInTest.get(sourceItem.getSku()) );
                    continue;
                }
            }


            System.out.println("itemsFromMagento count: " + itemsFromMagento.size());
            System.out.println("owd items count: " + sourceItemList.size());
            System.out.println("notExistedInMagentoCount: " + notExistedInMagentoCount);
            System.out.println("qtyInMagentoNotMatch: " + qtyInMagentoNotMatch);
            System.out.println("matchedInMagentoQty: "+matchedInMagentoQty);
            System.out.println("notExistedInOWDCount: " + notExistedInOWDCount);
            System.out.println("qtyInOWDNotMatch: " + qtyInOWDNotMatch);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected List<SourceItem> getSourceItemsToPost(String sourceCode, List results) {
        List<SourceItem> sourceItemList = new ArrayList<>();

        for(Object data: results) {
            Object[] row = (Object[]) data;
            String sku = row[0].toString();
            int qtyOH = Integer.parseInt(row[1].toString());
            SourceItem sourceItem = new SourceItem();
            sourceItem.setSku(sku);
            sourceItem.setQuantity(qtyOH);
            sourceItem.setStatus(0);
            sourceItem.setSource_code(sourceCode);
            sourceItemList.add(sourceItem);
        }
        return sourceItemList;
    }

    @Test
    public void testGetDefaultSourceForStore() {

        try {
            String storeCode = "foh";
            String firstSourceCode = getFirstSourceCodeForStore(storeCode);
            assertNotNull(firstSourceCode);
            System.out.println(firstSourceCode);
            assertTrue(firstSourceCode.equalsIgnoreCase("FOH"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getFirstSourceCodeForStore(String storeCode) throws Exception {
        Store foundedStore = getStoreByCode(storeCode);

        if(foundedStore == null)
            return null;

        WebSite webSite = getWebSiteByWebSiteId(foundedStore);

        if(webSite == null)
            return null;

        Stock stock = getWebSiteFirstStock(webSite);

        if(stock == null)
            return null;

        Source source = getFirstSourceStock(stock);

        if(source == null)
            return null;

        return source.getSource_code();
    }

    private Source getFirstSourceStock(Stock stock) throws Exception {
        Call<List<Source>> sourceResponseBody =
                sourceRepository.
                        getSourcesForStocks(stock.getStock_id());
        Response<List<Source>> sourcesResponse = sourceResponseBody.execute();
        if(sourcesResponse.code() != 200) {
            System.out.println("error when requesting stock source links"+sourcesResponse.code());
            return null;
        }
        List<Source> sources = sourcesResponse.body();

        if (sources.size() > 0)
            return sources.get(0);

        return null;
    }

    private Stock getWebSiteFirstStock(WebSite webSite) throws  Exception {
        Stock stock = null;
        Call<SearchStocksResponse> stockSearchResponseBody =
                stockRepository.getStocksByFilter(null,
                                null,
                                "stock_id",
                                "ASC");
        Response<SearchStocksResponse> stockSearchResponse = stockSearchResponseBody.execute();
        if(stockSearchResponse.code() != 200) {
            System.out.println("error when requesting stocks"+stockSearchResponse.code());
            return null;
        }
        SearchStocksResponse searchStocksResponse = stockSearchResponse.body();


        for (Stock item : searchStocksResponse.getItems()) {
            for (SalesChannel salesChannel : item.getExtension_attributes().getSales_channels())
                if (salesChannel.getType().equalsIgnoreCase("website") &&
                        salesChannel.getCode().equalsIgnoreCase(webSite.getCode())) {
                    stock = item;
                    break;
                }
        }

        return stock;
    }

    private WebSite getWebSiteByWebSiteId(Store store) throws Exception {
        int website_id = store.getWebsite_id();
        Call<List<WebSite>> responseBody = websiteRepository.getWebSites();
        Response<List<WebSite>> response = responseBody.execute();
        if(response.code() != 200) {
            System.out.println("error when requesting webSites"+response.code());
            return null;
        }
        List<WebSite> webSites = response.body();
        WebSite webSite = null;

        for(WebSite item: webSites) {
            if(item.getId() == website_id) {
                webSite = item;
                break;
            }
        }

        return webSite;
    }

    private Store getStoreByCode(String storeCode) throws  Exception {
        Call<List<Store>> storeResponseBody =
                storeRepository.getStoresList();
        Response<List<Store>> storeResponse = storeResponseBody.execute();
        if(storeResponse.code() != 200) {
            System.out.println("error when requesting stores"+storeResponse.code());
            return null;
        }
        List<Store> stores = storeResponse.body();
        Store foundedStore = null;

        for (Store item: stores) {
            if(item.getCode().equalsIgnoreCase(storeCode)) {
                foundedStore = item;
                break;
            }
        }

        return foundedStore;
    }


    @Test
    public void testGetStocks() {

        try {
            Call<SearchStocksResponse> responseBody =
                    stockRepository.getStocksByFilter(null,
                                    null,
                                    "stock_id",
                                    "ASC");
            Response<SearchStocksResponse> response = responseBody.execute();
            System.out.println(response.code());
            SearchStocksResponse body = response.body();

            assertNotNull(body);
            System.out.println(body.getItems().size());
            System.out.println(body.getItems().get(0).getStock_id());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetSourcesForStocks() {

        try {
            Call<List<Source>> responseBody =
                    sourceRepository.getSourcesForStocks(3);
            Response<List<Source>> response = responseBody.execute();
            System.out.println(response.code());
            List<Source> body = response.body();

            assertNotNull(body);
            System.out.println(body.get(0).getSource_code());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetProductsBySKU() {

        try {
            String sku = "P29-1337C.BLKI.A.10";
            Call<ResponseBody> responseBody =
                    productRepository.getProductBySKU(sku);
            Response<ResponseBody> response = responseBody.execute();
            System.out.println(response.code());
            ResponseBody body = response.body();

            assertNotNull(body);
            System.out.println(body.string());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetProducts() {

        try {
            int targetWebSiteId = 5;
            String storeId = "5";
            int batchSize = 1000;
            int batchIndex = 1;
            int maxBatchIndex = 10;
            int totalProcessed = 0;
            Boolean continueProcessing = true;
            HashSet<String> existed = new HashSet<>();
            HashSet<String> all = new HashSet<>();
            long beforems = System.currentTimeMillis();
            long beforemsBatch = System.currentTimeMillis();
            long afterms = 0;

            do {

                Call<SearchProductResponse> responseBody =
                        productRepository.getProducts("sku", "ASC",
                                batchSize, batchIndex);
                Response<SearchProductResponse> response = responseBody.execute();
                System.out.println("batch #: " + batchIndex + " processed in: "+ (System.currentTimeMillis() - beforemsBatch));
                beforemsBatch = System.currentTimeMillis();
                SearchProductResponse body = response.body();

                if(body!=null) {

                    for (Product product : body.getItems()) {
                        for (Integer webSiteId : product.getExtension_attributes().getWebsite_ids()) {
                            if (webSiteId == targetWebSiteId) {
                                existed.add(product.getSku());
                                //System.out.println("founded: " + product.getSku()+" id: "+product.getId());
                                break;
                            }
                        }
                        all.add(product.getSku());
                    }


                    totalProcessed += body.getItems().size();
                    continueProcessing = body.getItems().size() == batchSize && batchIndex++ < maxBatchIndex;
                } else {
                    System.out.println("failed with: " + response.code() + " processed in: "+ (System.currentTimeMillis() - beforemsBatch));
                    beforemsBatch = System.currentTimeMillis();
                    System.out.println("total processed: " + totalProcessed);
                    System.out.println("unique all: " + all.size());
                    System.out.println("founded: " + existed.size());
                }
            }
            while (continueProcessing);
            System.out.println("processed in: " + (System.currentTimeMillis() - beforems));
            System.out.println("total processed: " + totalProcessed);
            System.out.println("unique all: " + all.size());
            System.out.println("founded: " + existed.size());


//
//            Call<SearchProductResponse> responseBody =
//                    productRepository.getProducts("sku","DESC",100,1);
//            Response<SearchProductResponse> response = responseBody.execute();
//            System.out.println(response.code());
//            SearchProductResponse body = response.body();
//
//            assertNotNull(body);
//
//            for(Product product: body.getItems()) {
//                for (Integer webSiteId: product.getExtension_attributes().getWebsite_ids()) {
//                    if(webSiteId == 5) {
//                        System.out.println(product.getSku());
//                        break;
//                    }
//                }
//            }
//            System.out.println(body.getItems().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateLevels() throws Exception {
        int clientId = 640;
        String sku1 = "P29-1337C.BLKI.DD.10AAA";
        String sku2 = "P29-1337C.BLKI.A.10";
        String sku3 = "P29-1337C.BLKI.D.10";
        String sku4 = "P29-1337C.BLKI.B.10";

        boolean updated = syncOwdStocksToMagento(clientId, sku1)
                 && syncOwdStocksToMagento(clientId, sku2)
                 && syncOwdStocksToMagento(clientId, sku3)
                 && syncOwdStocksToMagento(clientId, sku4);

        printAcualQTYInMagento(sku1);
        printAcualQTYInMagento(sku2);
        printAcualQTYInMagento(sku3);
        printAcualQTYInMagento(sku4);
        assertTrue(updated);
    }


    @Test
    public void testUpdateLevelsInBatch() throws Exception {
        String sku1 = "P29-1337C.BLKI.DD.10";
        String sku2 = "P29-1337C.BLKI.A.10";
        String sku3 = "P29-1337C.BLKI.D.10";
        String sku4 = "P29-1337C.BLKI.B.10";

        syncInBatch();

        printAcualQTYInMagento(sku1);
        printAcualQTYInMagento(sku2);
        printAcualQTYInMagento(sku3);
        printAcualQTYInMagento(sku4);
    }

    private void syncInBatch() throws  Exception {
        String sku1 = "P29-1337C.BLKI.DD.10";
        String sku2 = "P29-1337C.BLKI.A.10";
        String sku3 = "P29-1337C.BLKI.D.10";
        String sku4 = "P29-1337C.BLKI.B.10";

        PostSourceItemsRequest postItemRequest = new PostSourceItemsRequest();
        List<SourceItem> sourceItemList = new ArrayList<>();

        SourceItem sourceItem = new SourceItem();
        sourceItem.setSku(sku1);
        sourceItem.setQuantity(60);
        sourceItem.setSource_code("foh");
        sourceItem.setStatus(1);//in stock
        sourceItemList.add(sourceItem);

        sourceItem = new SourceItem();
        sourceItem.setSku(sku2);
        sourceItem.setQuantity(41);
        sourceItem.setSource_code("foh");
        sourceItem.setStatus(1);//in stock
        sourceItemList.add(sourceItem);

        sourceItem = new SourceItem();
        sourceItem.setSku(sku3);
        sourceItem.setQuantity(31);
        sourceItem.setSource_code("foh");
        sourceItem.setStatus(1);//in stock
        sourceItemList.add(sourceItem);

        sourceItem = new SourceItem();
        sourceItem.setSku(sku4);
        sourceItem.setQuantity(21);
        sourceItem.setSource_code("foh");
        sourceItem.setStatus(1);//in stock
        sourceItemList.add(sourceItem);
        postItemRequest.setSourceItems(sourceItemList);

        Call<ResponseBody> callUpdateSync = sourceItemRepository.postSourceItems(postItemRequest);

        Response<ResponseBody> updateStockResponse = callUpdateSync.execute();
        if (updateStockResponse.code() == 404) {
            //No SKU exists in magento - throw exception?
        }
    }

    private boolean syncOwdStocksToMagento(int clientId, String sku) throws Exception {
        OwdInventory item = InventoryManager.getOwdInventoryForClientAndSku(clientId + "", sku);
        long qtyOnHand = item.getOwdInventoryOh().getQtyOnHand();
        boolean result = updateMagentoStocks(sku,qtyOnHand);

        return result;
    }


    private boolean updateMagentoStocks(String productSKU, long qtyOnHand) throws Exception {

        PostSourceItemsRequest postItemRequest = new PostSourceItemsRequest();
        List<SourceItem> sourceItemList = new ArrayList<SourceItem>();
        SourceItem sourceItem = new SourceItem();
        sourceItem.setSku(productSKU);
        sourceItem.setQuantity(qtyOnHand);
        sourceItem.setSource_code("foh");
        sourceItem.setStatus(1);//in stock
        sourceItemList.add(sourceItem);
        postItemRequest.setSourceItems(sourceItemList);

        Call<ResponseBody> callUpdateSync = sourceItemRepository.postSourceItems(postItemRequest);
        Response<ResponseBody> updateStockResponse = callUpdateSync.execute();
        if (updateStockResponse.code() == 404) {
            //No SKU exists in magento - throw exception?
        }
        return updateStockResponse.code() == 200;
    }

    protected void printAcualQTYInMagento(String productSKU) throws Exception {
        Call<ResponseBody> responseBody =
                sourceItemRepository.getSourceItemsResponse("sku",
                        productSKU,
                        "eq");
        Response<ResponseBody> response = responseBody.execute();
        ResponseBody body = response.body();
        System.out.println(productSKU + ' ' + body.string());
    }


    @Test
    public void executeJob() throws Exception {
        int clientId = 640;
        String storeCode = "foh";
        String sourceCode = getFirstSourceCodeForStore(storeCode);
        int batchSize = 37;

        if(sourceCode == null) {
            return;
        }

        String sqlStatement ="\n" +
                "select \n" +
                "\tINV.inventory_num,\n" +
                "\tOH.qty_on_hand\n" +
                "from [dbo].[owd_inventory] as INV\n" +
                "JOIN [dbo].[owd_inventory_oh] AS OH\n" +
                "\tON INV.inventory_id = OH.inventory_fkey \n" +
                "where client_fkey = :clientID and is_active = 1 and inventory_id > 679388;";
        Query q =  HibernateSession.currentSession().createSQLQuery(sqlStatement);
        q.setParameter("clientID",clientId);

        List results = q.list();


        if(results.size()<=0){
            throw new Exception("Unable to lookup order sku's for client");
        }

        List<SourceItem> sourceItemList = new ArrayList<>();

        for(Object data: results) {
            Object[] row = (Object[]) data;
            String sku = row[0].toString();
            int qtyOH = Integer.parseInt(row[1].toString());
            SourceItem sourceItem = new SourceItem();
            sourceItem.setSku(sku);
            sourceItem.setQuantity(qtyOH);
            sourceItem.setStatus(1);
            sourceItem.setSource_code(sourceCode);
            sourceItemList.add(sourceItem);
        }


        for (int i = 0; i < 1000; i++) {
            SourceItem sourceItem = sourceItemList.get(i);

            String productSKU = sourceItem.getSku();
            Call<ResponseBody> responseBody =
                    sourceItemRepository.getSourceItemsResponse("sku",
                            productSKU,
                            "eq");
            Response<ResponseBody> response = responseBody.execute();
            ResponseBody body = response.body();

            System.out.println(productSKU + ' ' + body.string());
        }
     //   saveInBatch(sourceItemList,batchSize);
    }

    private void saveInBatch(List<SourceItem> sourceItemList, int batchSize) throws IOException {

        int batchIndex = 0;
        int totalProcessed = 0;
        int shouldBeProcessed = sourceItemList.size();
        Boolean continueProcessing = true;
        long beforems = System.currentTimeMillis();
        long afterms = 0;

        do {
            int inBatchProcessed = 0;
            List<SourceItem> batch = new ArrayList<>();

            for (int i = batchIndex * batchSize; i < (batchIndex + 1) * batchSize && i < shouldBeProcessed; i++) {
                SourceItem sourceItem = sourceItemList.get(i);
                if (productExists(sourceItem.getSku()))
                    batch.add(sourceItem);
                inBatchProcessed++;
            }
            totalProcessed += inBatchProcessed;
            batchIndex++;
            saveBatch(batch);
            continueProcessing = batchIndex * batchSize < shouldBeProcessed;
        }
        while (continueProcessing);
        System.out.println("processed in: " + (System.currentTimeMillis() - beforems));
    }

    private boolean productExists(String sku) throws IOException {
        Call<ResponseBody> responseBody = productRepository.getProductBySKU(sku);
        Response<ResponseBody> response = responseBody.execute();
        return response.code() == 200;
    }

    private boolean saveBatch(List<SourceItem> batch) throws IOException {
        for (SourceItem item : batch) {
            System.out.println(item.getSku() + " " + item.getQuantity());
        }
//        PostSourceItemsRequest postItemRequest = new PostSourceItemsRequest();
//        postItemRequest.setSourceItems(batch);
//
//        Call<ResponseBody> callUpdateSync = sourceItemsRepository.postSourceItems(postItemRequest);
//        Response<ResponseBody> updateStockResponse = callUpdateSync.execute();
//        if (updateStockResponse.code() != 200) {
//            //No SKU exists in magento - throw exception?
//        }
//        return updateStockResponse.code() == 200;
        return  true;
    }
}
