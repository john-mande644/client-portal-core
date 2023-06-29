
package com.owd.core.api.shopify.transationResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionRequest {

    @SerializedName("transaction")
    @Expose
    private Transaction transaction = null;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
