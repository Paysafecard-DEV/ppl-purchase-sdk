package com.paysafe.paylater.exception;

/**
 * Indicates an exception regarding the communication with the Paysafe Pay Later platform such as a connection exception.
 */
@SuppressWarnings("serial")
public class CommunicationException extends RuntimeException {

    public CommunicationException(Exception e) {
        super(e);
    }
}
