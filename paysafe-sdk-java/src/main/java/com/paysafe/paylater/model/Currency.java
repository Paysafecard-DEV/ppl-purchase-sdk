/**
 * Purchase API
 * @copyright Copyright (c) 2020 Paysafe Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Paysafe Pay Later OpenAPI specification, version 1.0.0.
 */
package com.paysafe.paylater.model;

public enum Currency {

    EUR("EUR"),
    CHF("CHF"),
    USD("USD"),
    CAD("CAD"),
    GBP("GBP");

    private String value;

    Currency(String value) {
        this.value = value;
    }

    public static Currency fromValue(String value) {
        for (Currency b : Currency.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

