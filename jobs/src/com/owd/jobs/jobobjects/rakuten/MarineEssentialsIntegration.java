package com.owd.jobs.jobobjects.rakuten;

/**
 * Created by stewartbuskirk1 on 10/9/15.
 */
public class MarineEssentialsIntegration {

    public static RakutenOrderProcessor getRakutenOrderProcessor()
    {
        return new RakutenOrderProcessor(494, "trade.marketplace.buy.com", "kathleen@marined3.com", "Pass123");
    }
}
