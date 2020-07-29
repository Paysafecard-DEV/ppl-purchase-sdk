package com.paysafe.paylater.api;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.paysafe.paylater.communication.Communicator;
import com.paysafe.paylater.communication.RequestHeader;
import com.paysafe.paylater.exception.ApiException;
import com.paysafe.paylater.exception.AuthorizationException;
import com.paysafe.paylater.exception.PaysafeException;
import com.paysafe.paylater.exception.ReferenceException;
import com.paysafe.paylater.exception.ResponseException;
import com.paysafe.paylater.exception.ValidationException;
import com.paysafe.paylater.logging.CommunicatorLogger;
import com.paysafe.paylater.model.OperationResult;
import com.paysafe.paylater.model.PurchaseOperationResponse;

/**
 * Base class of all Paysafe Pay Later API resources.
 */
public abstract class BaseApi implements Closeable {

    protected final Communicator communicator;

    protected BaseApi(Communicator communicator) {
        if (communicator == null) {
            throw new IllegalArgumentException("communicator is required");
        }
        this.communicator = communicator;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public void enableLogging(CommunicatorLogger logger) {
        communicator.enableLogging(logger);
    }
    public void disableLogging() {
        communicator.disableLogging();
    }

    protected String populateUri(String uri, String key, String value) {
        return uri.replace("{" + key + "}", value);
    }

    protected void addHeaderParam(List<RequestHeader> headerParams, String headerName, String headerValue) {
        if (headerValue != null && !headerValue.trim().isEmpty()) {
            headerParams.add(new RequestHeader(headerName, headerValue));
        }
    }

    protected RuntimeException createException(ResponseException e) {
        String responseBody = e.getBody();
        PurchaseOperationResponse errorObject = communicator.unmarshal(responseBody, PurchaseOperationResponse.class);
        OperationResult result = errorObject != null ? errorObject.getResult() : null;

        switch (e.getResponseStatusCode()) {
            case 400:
                return new ValidationException(e, result);
            case 401:
            case 403:
                return new AuthorizationException(e, result);
            case 404:
            case 409:
            case 410:
                return new ReferenceException(e, result);
            case 500:
            case 502:
            case 503:
                return new PaysafeException(e, result);
            default:
                return new ApiException(e, result);
        }
    }

    @Override
    public void close() throws IOException {
        this.communicator.close();
    }
}
