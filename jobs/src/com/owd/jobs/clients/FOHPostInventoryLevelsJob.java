package com.owd.jobs.clients;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.magento2Api.Magento2ApiServiceGenerator;
import com.owd.magento2Api.Models.*;
import com.owd.magento2Api.Services.*;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FOHPostInventoryLevelsJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    private final static String token = "559xilb32v9ij7k53wicd78tkaavzmrk";
    private final static String FOHServiceRoute = "https://www.fredericks.com";
    private final static String storeCode = "FOH";
    private final static Integer clientId = 640;

    int batchSize = 1000;
    private Magento2ApiServiceGenerator magento2ApiServiceGenerator = null;
    private WebsiteRepository websiteRepository = null;
    private StoreRepository storeRepository = null;
    private SourceItemRepository sourceItemRepository = null;
    private StockRepository stockRepository = null;
    private SourceRepository sourceRepository = null;
    private ProductRepository productRepository =null;

    public FOHPostInventoryLevelsJob() {
        magento2ApiServiceGenerator = new Magento2ApiServiceGenerator(FOHServiceRoute);
        websiteRepository = magento2ApiServiceGenerator.createService(WebsiteRepository.class, token);
        storeRepository = magento2ApiServiceGenerator.createService(StoreRepository.class, token);
        sourceItemRepository =  magento2ApiServiceGenerator.createService(SourceItemRepository.class, token);
        stockRepository = magento2ApiServiceGenerator.createService(StockRepository.class, token);
        sourceRepository = magento2ApiServiceGenerator.createService(SourceRepository.class, token);
        productRepository = magento2ApiServiceGenerator.createService(ProductRepository.class, token);
    }

    //For testing purposes
    public FOHPostInventoryLevelsJob(SourceItemRepository sourceItemRepository,int batchSize) {
        this();
        this.sourceItemRepository = sourceItemRepository;
        this.batchSize = batchSize;
    }


    public static void main(String[] args) {

        run();
    }

    public void internalExecute() {
        try {
            String sourceCode = getFirstSourceCodeForStore(storeCode);
            int webSiteId = getWebSiteId(storeCode);

            if(sourceCode == null || webSiteId == 0) {
                return;
            }

            String sqlStatement ="\n" +
                    "select\n" +
                    "\tINV.inventory_num,\n" +
                    "\tOH.qty_on_hand\n" +
                    "from [dbo].[owd_inventory] as INV\n" +
                    "JOIN [dbo].[owd_inventory_oh] AS OH\n" +
                    "\tON INV.inventory_id = OH.inventory_fkey \n" +
                    "where client_fkey = :clientID and is_active = 1 ;";
            Query q =  HibernateSession.currentSession().createSQLQuery(sqlStatement);
            q.setParameter("clientID",clientId);

            List results = q.list();

            log.debug("start syncing: "+ results.size()+" items");

            if(results.size()<=0){
                throw new Exception("Unable to lookup sku's for client");
            }

            List<SourceItem> sourceItemList = getSourceItemsToPost(sourceCode, results);
           // HashSet<String> magentoSKUs = getMagentoSKUs(webSiteId, batchSize);
            saveInBatch(sourceItemList, null,batchSize);
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            log.error(ex.getMessage(), ex);
        }
    }

    private HashSet<String> getMagentoSKUs(int webSiteId, int batchSize) throws IOException {
        int batchIndex = 1;
        int maxBatchIndex = 10000;
        int totalProcessed = 0;
        Boolean continueProcessing = true;
        HashSet<String> existed = new HashSet<>();
        long beforemsBatch = System.currentTimeMillis();
        int lastFailBatchIndex = -1;

        do {

            Call<SearchProductResponse> responseBody =
                    productRepository.getProducts("id", "desc",
                            batchSize, batchIndex);
            Response<SearchProductResponse> response = responseBody.execute();
            System.out.println("batch #: " + batchIndex);
            SearchProductResponse body = response.body();
            beforemsBatch = System.currentTimeMillis();

            if(body!=null) {

                for (Product product : body.getItems()) {
                    for (Integer productWebSiteId : product.getExtension_attributes().getWebsite_ids()) {
                        if (productWebSiteId == webSiteId) {
                            existed.add(product.getSku());
                            break;
                        }
                    }
                }


                totalProcessed += body.getItems().size();
                continueProcessing = body.getItems().size() == batchSize && batchIndex++ < maxBatchIndex;
            } else {
                System.out.println("failed with: " + response.code() + " processed in: "+ (System.currentTimeMillis() - beforemsBatch));
                System.out.println("total processed: " + totalProcessed);
                System.out.println("founded: " + existed.size());

                if(lastFailBatchIndex == batchIndex)
                    break;
                else
                    lastFailBatchIndex = batchIndex;
            }
        }
        while (continueProcessing);

        return existed;
    }

    private int getWebSiteId(String storeCode) throws Exception {
        Store foundedStore = getStoreByCode(storeCode);

        if(foundedStore == null)
            return 0;

        WebSite webSite = getWebSiteByWebSiteId(foundedStore);

        if(webSite == null)
            return 0;

        return webSite.getId();
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
            if(qtyOH>0){
                sourceItem.setStatus(1);
            }else{
                sourceItem.setStatus(0);
            }

            sourceItem.setSource_code(sourceCode);
            sourceItemList.add(sourceItem);
        }
        return sourceItemList;
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

    private void saveInBatch(List<SourceItem> sourceItemList, HashSet<String> magentoSKUs, int batchSize) throws IOException {

        int batchIndex = 0;
        int totalProcessed = 0;
        int shouldBeProcessed = sourceItemList.size();
        Boolean continueProcessing = true;
        long beforems = System.currentTimeMillis();

        do {
            int inBatchProcessed = 0;
            List<SourceItem> batch = new ArrayList<>();
            System.out.println("Doing batch: " + batchIndex);

            for (int i = batchIndex * batchSize; i < (batchIndex + 1) * batchSize && i < shouldBeProcessed; i++) {
                SourceItem sourceItem = sourceItemList.get(i);
                //if (productExists(sourceItem.getSku()))//comment this line to post all inventory to source, regardless is it exists as product or not
              //  if (magentoSKUs.contains(sourceItem.getSku()))
                    batch.add(sourceItem);
                inBatchProcessed++;
            }
            totalProcessed += inBatchProcessed;
            batchIndex++;
            System.out.println("processed in: " + (System.currentTimeMillis() - beforems));
            beforems = System.currentTimeMillis();
            saveBatch(batch);
            continueProcessing = batchIndex * batchSize < shouldBeProcessed;
        }
        while (continueProcessing);
        System.out.println("processed in: " + (System.currentTimeMillis() - beforems));
        System.out.println("total processed: " + totalProcessed);
    }

    private boolean productExists(String sku) throws IOException {
        System.out.println("Checking exist for " +sku);
        Call<ResponseBody> responseBody = productRepository.getProductBySKU(sku);
        Response<ResponseBody> response = responseBody.execute();
        return response.code() == 200;
    }

    private boolean saveBatch(List<SourceItem> batch) throws IOException {
        PostSourceItemsRequest postItemRequest = new PostSourceItemsRequest();
        postItemRequest.setSourceItems(batch);

        Call<ResponseBody> callUpdateSync = sourceItemRepository.postSourceItems(postItemRequest);
        Response<ResponseBody> updateStockResponse = callUpdateSync.execute();
        return updateStockResponse.code() == 200;
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
}