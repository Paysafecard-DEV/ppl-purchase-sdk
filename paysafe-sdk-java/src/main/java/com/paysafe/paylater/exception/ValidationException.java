package com.paysafe.paylater.exception;

import com.paysafe.paylater.model.OperationResult;

/**
 * Represents an error response from the Paysafe Pay Later platform when validation of requests failed.
 */
@SuppressWarnings("serial")
public class ValidationException extends ApiException {

    public ValidationException(ResponseException e, OperationResult operationResult) {
        super(e, operationResult);
    }
}
