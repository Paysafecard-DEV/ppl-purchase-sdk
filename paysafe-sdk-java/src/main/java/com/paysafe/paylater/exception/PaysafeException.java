package com.paysafe.paylater.exception;

import com.paysafe.paylater.model.OperationResult;

/**
 * Represents an error response from the Paysafe Pay Later platform when something went wrong at the platform or further downstream.
 */
@SuppressWarnings("serial")
public class PaysafeException extends ApiException {

    public PaysafeException(ResponseException e, OperationResult operationResult) {
        super(e, operationResult);
    }
}
