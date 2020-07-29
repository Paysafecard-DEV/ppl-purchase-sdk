/**
 * Purchase API
 * @copyright Copyright (c) 2020 Paysafe Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Paysafe Pay Later OpenAPI specification, version 1.0.0.
 */
package com.paysafe.paylater.model;

public enum AchAccountType {

    SAVINGS("SAVINGS"),
    CHECKING("CHECKING");

    private String value;

    AchAccountType(String value) {
        this.value = value;
    }

    public static AchAccountType fromValue(String value) {
        for (AchAccountType b : AchAccountType.values()) {
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

