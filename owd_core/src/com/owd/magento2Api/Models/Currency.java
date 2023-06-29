package com.owd.magento2Api.Models;
import java.util.List;

public class Currency {
    private String baseCurrencySymbol;
    private String defaultDisplayCurrencyCode;
    private String defaultDisplayCurrencySymbol;
    private List<String> availableCurrencyCodes;
    private List<ExchangeRate> exchangeRates;

    private String base_currency_code;

    public String getBase_currency_code() {
        return base_currency_code;
    }

    public void setBase_currency_code(String base_currency_code) {
        this.base_currency_code = base_currency_code;
    }

    /*    private String baseCurrencyCode;
            public String getBaseCurrencyCode() {
                return baseCurrencyCode;
            }

            public void setBaseCurrencyCode(String baseCurrencyCode) {
                this.baseCurrencyCode = baseCurrencyCode;
            }
        */
    public String getBaseCurrencySymbol() {
        return baseCurrencySymbol;
    }

    public void setBaseCurrencySymbol(String baseCurrencySymbol) {
        this.baseCurrencySymbol = baseCurrencySymbol;
    }

    public String getDefaultDisplayCurrencyCode() {
        return defaultDisplayCurrencyCode;
    }

    public void setDefaultDisplayCurrencyCode(String defaultDisplayCurrencyCode) {
        this.defaultDisplayCurrencyCode = defaultDisplayCurrencyCode;
    }

    public String getDefaultDisplayCurrencySymbol() {
        return defaultDisplayCurrencySymbol;
    }

    public void setDefaultDisplayCurrencySymbol(String defaultDisplayCurrencySymbol) {
        this.defaultDisplayCurrencySymbol = defaultDisplayCurrencySymbol;
    }

    public List<String> getAvailableCurrencyCodes() {
        return availableCurrencyCodes;
    }

    public void setAvailableCurrencyCodes(List<String> availableCurrencyCodes) {
        this.availableCurrencyCodes = availableCurrencyCodes;
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }
}

/*public class ExchangeRate {

}
{
        "base_currency_code": "string",
        "base_currency_symbol": "string",
        "default_display_currency_code": "string",
        "default_display_currency_symbol": "string",
        "available_currency_codes": [
        "string"
        ],
        "exchange_rates": [
        {
        "currency_to": "string",
        "rate": 0,
        "extension_attributes": {}
        }
        ],
        "extension_attributes": {}
        }*/