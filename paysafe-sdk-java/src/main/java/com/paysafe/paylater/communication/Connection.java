package com.paysafe.paylater.communication;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.paysafe.paylater.exception.CommunicationException;
import com.paysafe.paylater.logging.LoggingCapable;

/**
 * Represents a connection to the Paysafe Pay Later platform server. Thread-safe.
 */
public interface Connection extends Closeable, LoggingCapable {

    /**
     * Releases any system resources associated with this object.
     * Should be called when this object is about to go out of scope.
     */
    @Override
    void close() throws IOException;

    /**
     * Send a POST request to the Paysafe Pay Later platform.
     *
     * @param uri The URI to call, including any necessary query parameters.
     * @param requestHeaders An optional list of request headers.
     * @param body The optional body to send.
     * @param responseHandler A handler for the response.
     * @throws CommunicationException when an exception occurred communicating with the Paysafe Pay Later platform
     */
    <R> R post(URI uri, List<RequestHeader> requestHeaders, String body, ResponseHandler<R> responseHandler);

    /**
     * Send a GET request to the Paysafe Pay Later platform.
     *
     * @param uri The URI to call, including any necessary query parameters.
     * @param requestHeaders An optional list of request headers.
     * @param responseHandler A handler for the response.
     * @throws CommunicationException when an exception occurred communicating with the Paysafe Pay Later platform
     */
    <R> R get(URI uri, List<RequestHeader> requestHeaders, ResponseHandler<R> responseHandler);

    /**
     * Send a PUT request to the Paysafe Pay Later platform.
     *
     * @param uri The URI to call, including any necessary query parameters.
     * @param requestHeaders An optional list of request headers.
     * @param body The optional body to send.
     * @param responseHandler A handler for the response.
     * @throws CommunicationException when an exception occurred communicating with the Paysafe Pay Later platform
     */
    <R> R put(URI uri, List<RequestHeader> requestHeaders, String body, ResponseHandler<R> responseHandler);

    /**
     * Send a DELETE request to the Paysafe Pay Later platform.
     *
     * @param uri The URI to call, including any necessary query parameters.
     * @param requestHeaders An optional list of request headers.
     * @param responseHandler A handler for the response.
     * @throws CommunicationException when an exception occurred communicating with the Paysafe Pay Later platform
     */
    <R> R delete(URI uri, List<RequestHeader> requestHeaders, ResponseHandler<R> responseHandler);
}
