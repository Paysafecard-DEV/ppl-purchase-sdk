package com.paysafe.paylater.exception;

import com.paysafe.paylater.model.OperationResult;

/**
 * Represents an error response from the Paysafe Pay Later platform when authorization failed.
 */
@SuppressWarnings("serial")
public class AuthorizationException extends ApiException {

    public AuthorizationException(ResponseException e, OperationResult operationResult) {
        super(e, operationResult);
    }
}
